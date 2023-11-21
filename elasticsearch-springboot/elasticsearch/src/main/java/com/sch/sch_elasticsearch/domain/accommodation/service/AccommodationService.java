package com.sch.sch_elasticsearch.domain.accommodation.service;

import com.sch.sch_elasticsearch.domain.accommodation.dto.AccommodationDTO;
import com.sch.sch_elasticsearch.domain.accommodation.entity.Accommodation;
import com.sch.sch_elasticsearch.domain.accommodation.repository.AccommodationRepository;
import com.sch.sch_elasticsearch.domain.restaurant.entity.Restaurant;
import com.sch.sch_elasticsearch.domain.shared_query.ToolsForQuery;
import com.sch.sch_elasticsearch.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sch.sch_elasticsearch.exception.ExceptionType.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final ToolsForQuery toolsForQuery;
    private final ToolsForAccommodationService toolsForAccommodationService;

    @Value("${info.analyzer.nori-ngram}")
    String ngramAnalyzer;

    /**
     * Fuzzy를 사용한 유사도 검색
     * @param title
     * @param maxResults
     * @param fuzziness
     * @return
     */
    public List<AccommodationDTO> searchTitleUseFuzzyDto(String title, int maxResults, int fuzziness)
    {
        try {
            NativeSearchQuery searchQuery = toolsForQuery.fuzzyQuery("accommodation_name.keyword", title, fuzziness, maxResults);
            return toolsForAccommodationService.getListBySearchHits(elasticsearchRestTemplate.search(searchQuery, Accommodation.class));
        } catch (Exception e) {
            log.error("[ERR LOG] {}", e.getMessage());
            throw new CommonException(ACCOMMODATION_SEARCH_FUZZY_TITLE_FAIL);
        }
    }

    /** Ngram 방식을 사용한 제목 유사도 검색
     *
     * @param title
     * @param maxResults
     * @return
     */
    public List<AccommodationDTO> searchTitleUseNgramDto(String title, int maxResults) {

        try {
            QueryBuilder queryBuilder = new QueryStringQueryBuilder(title)
                    .defaultField("accommodation_name")
                    .analyzer(ngramAnalyzer);
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(queryBuilder)
                    .withPageable(PageRequest.of(0, maxResults)) // 결과 개수 제한
                    .build();
            return toolsForAccommodationService.getListBySearchHits(elasticsearchRestTemplate.search(searchQuery, Accommodation.class));
        } catch (Exception e) {
            log.error("[ERR LOG] {}", e.getMessage());
            throw new CommonException(ACCOMMODATION_SEARCH_NGRAM_TITLE_FAIL);
        }
   }

    /**
     * Fuzzy와 Ngram을 동시 수행하고, 집계 후 가장 유사도가 높은 결과를 반환하는 검색
     * @param title
     * @param maxResults
     * @param fuzziness
     * @param fuzzyPrimary
     * @return
     */
    public List<AccommodationDTO> searchFuzzyAndNgram(String title, int maxResults, int fuzziness, boolean fuzzyPrimary) {
        try {
            int searchCount = Math.max(maxResults, 40); //최소 40개로 검색 결과를 보장 : 10개 정도라면 비슷한 값만 나올 수도 있음.
            int fuzzyWeight, ngramWeight; //가중치 여부 : fuzzyPrimary에 따라 다름
            if(fuzzyPrimary) { //가중치 부여
                fuzzyWeight = 7;
                ngramWeight = 3;
            } else {
                fuzzyWeight = 3;
                ngramWeight = 7;
            }
            List<AccommodationDTO> fuzzyList = searchTitleUseFuzzyDto(title, searchCount, fuzziness);
            List<AccommodationDTO> ngramList = searchTitleUseNgramDto(title, searchCount);


            //두 리스트를 합치고 스코어가 있다면 가중
            HashMap<String, Float> alladdHashMap = new HashMap<>();
            //1. fuzzy 삽입
            for (AccommodationDTO dto : fuzzyList) {
                alladdHashMap.put(dto.getAccommodationName(), (dto.getScore() * fuzzyWeight));
            }
            //2. ngram 삽입, fuzzy와 동일한 지명이 있다면 스코어 가중
            for (AccommodationDTO dto : ngramList) {
                if(alladdHashMap.containsKey(dto.getAccommodationName())) {
                    alladdHashMap.put(dto.getAccommodationName(),
                            alladdHashMap.get(dto.getAccommodationName()) + (dto.getScore() * ngramWeight));
                } else alladdHashMap.put(dto.getAccommodationName(), (dto.getScore() * ngramWeight));
            }

            //스트림을 통해 해시맵 내림차순 정렬 후 리스트화
            List<String> nameList = alladdHashMap.entrySet()
                    .stream() //HashMap을 스트림으로 변환
                    .sorted(Map.Entry.<String, Float>comparingByValue().reversed()) //value를 기준으로 내림차순 정렬
                    .limit(maxResults)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            //추려진 값들을 should를 사용하여 동시적으로 조회 (리스트 내부에 있는 제목의 검색 결과는 리턴됨)
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            for (String name : nameList) {
                boolQueryBuilder.should(QueryBuilders.termQuery("accommodation_name.keyword", name));
            }
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(boolQueryBuilder)
                    .withPageable(PageRequest.of(0, maxResults)) // maxResults만큼 결과
                    .build();

            List<AccommodationDTO> queryResult = toolsForAccommodationService.getListBySearchHits(elasticsearchRestTemplate.search(searchQuery, Accommodation.class));

            //최종 합산 결과 저장, 이전 스코어 순서대로 재정렬
            List<AccommodationDTO> results = new ArrayList<>();
            for (String name : nameList) {
                queryResult.stream()  // queryResult 리스트를 스트림으로 변환
                        .filter(dto -> dto.getAccommodationName().equals(name)) // 현재 이름과 일치하는 ResponseRestaurantDto 객체 필터링
                        .findFirst() // 필터링된 스트림에서 첫 번째 요소 찾기 (일치하는 첫 번째 객체)
                        .ifPresent(results::add); // 일치하는 객체가 존재하면 sortedResults 리스트에 추가
            }
            return results;

        } catch (Exception e) {
            log.error("[ERR LOG] {}", e.getMessage());
            throw new CommonException(ACCOMMODATION_SEARCH_FUZZY_AND_NGRAM_TITLE_FAIL);
        }

   }

}
