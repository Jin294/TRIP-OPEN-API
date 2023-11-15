package com.sch.sch_elasticsearch.domain.wiki.service;

import com.sch.sch_elasticsearch.domain.wiki.dto.ResponseWikiDto;
import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import com.sch.sch_elasticsearch.exception.CommonException;
import com.sch.sch_elasticsearch.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 제목 검색과 관련된 서비스 로직
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WikiServiceTitle {
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final ToolsForWikiService toolsForWikiService;

    @Value("${info.analyzer.nori-ngram}")
    String ngramAnalyzer;

    /**
     * 입력값에 존재하는 제목이 있다면 리턴,아니라면 null 값 리턴
     * @param title
     * @param reliable
     * @return
     */
    public ResponseWikiDto searchTitleCorrect(String title, boolean reliable) {
        try {
            QueryBuilder queryBuilder = new MatchQueryBuilder("attraction_name.keyword", title);
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(queryBuilder)
                    .build();

            SearchHits<Wiki> searchHits = elasticsearchRestTemplate.search(searchQuery, Wiki.class);
            List<Wiki> result = toolsForWikiService.getListBySearchHits(searchHits, reliable);
            if(result.size() == 0) return null;
            return result.get(0).toDto();
        } catch (Exception e) {
            log.error("[ERR LOG]{}", e);
            throw new CommonException(ExceptionType.SEARCH_TITLE_CORRECT_FAIL);
        }
    }

    /**
     * Fuzzy를 사용한 제목 유사도 검색
     * @param title
     * @param maxResults
     * @param fuzziness
     * @param reliable
     * @return
     */
    public List<Wiki> searchTitleUseFuzzy(String title, int maxResults, int fuzziness, boolean reliable) {
        try {
            // fuzziness 설정
            Fuzziness fuzzinessLevel;
            if (fuzziness > 0) {
                fuzzinessLevel = Fuzziness.build(fuzziness);
            } else {
                fuzzinessLevel = Fuzziness.AUTO;
            }

            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(
                            new FuzzyQueryBuilder("attraction_name.keyword", title)
                                    .fuzziness(fuzzinessLevel)
                    )
                    .withPageable(PageRequest.of(0, maxResults)) // 결과 개수 제한
                    .withCollapseField("content_id")
                    .build();

            SearchHits<Wiki> searchHits = elasticsearchRestTemplate.search(searchQuery, Wiki.class);
            return toolsForWikiService.getListBySearchHits(searchHits, reliable);
        } catch (Exception e) {
            log.error("[ERR LOG]{}", e);
            throw new CommonException(ExceptionType.ATTRACTION_NAME_FUZZYSEARCH_FAIL);
        }
    }


    public List<ResponseWikiDto> searchTitleUseFuzzyDto(String title, int maxResults, int fuzziness, boolean reliable) {
        try {
            // fuzziness 설정
            Fuzziness fuzzinessLevel;
            if (fuzziness > 0) {
                fuzzinessLevel = Fuzziness.build(fuzziness);
            } else {
                fuzzinessLevel = Fuzziness.AUTO;
            }

            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(
                            new FuzzyQueryBuilder("attraction_name.keyword", title)
                                    .fuzziness(fuzzinessLevel)
                    )
                    .withPageable(PageRequest.of(0, maxResults)) // 결과 개수 제한
                    .withCollapseField("content_id")
                    .build();

            SearchHits<Wiki> searchHits = elasticsearchRestTemplate.search(searchQuery, Wiki.class);
            return toolsForWikiService.getListBySearchHits(searchHits, reliable)
                    .stream()
                    .map(wiki -> wiki.toDto())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("[ERR LOG]{}", e);
            throw new CommonException(ExceptionType.ATTRACTION_NAME_FUZZYSEARCH_FAIL);
        }
    }

    /** Ngram 방식을 사용한 제목 유사도 검색
     *
     * @param title
     * @param maxResults
     * @param reliable
     * @return
     */
    public List<ResponseWikiDto> searchTitleUseNgramDto(String title, int maxResults, boolean reliable) {
        try {
            QueryBuilder queryBuilder = new QueryStringQueryBuilder(title)
                    .defaultField("attraction_name")
                    .analyzer(ngramAnalyzer);
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(queryBuilder)
                    .withPageable(PageRequest.of(0, maxResults)) // 결과 개수 제한
                    .withCollapseField("content_id")
                    .build();

            SearchHits<Wiki> searchHits = elasticsearchRestTemplate.search(searchQuery, Wiki.class);
            return toolsForWikiService.getListBySearchHits(searchHits, reliable)
                    .stream()
                    .map(wiki -> wiki.toDto())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("[ERR LOG]{}", e.getMessage());
            throw new CommonException(ExceptionType.ATTRACTION_NAME_NGRAMSEARCH_FAIL);
        }
    }

    public List<Wiki> searchTitleUseNgram(String title, int maxResults, boolean reliable) {
        try {
            QueryBuilder queryBuilder = new QueryStringQueryBuilder(title)
                    .defaultField("attraction_name")
                    .analyzer(ngramAnalyzer);
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(queryBuilder)
                    .withPageable(PageRequest.of(0, maxResults)) // 결과 개수 제한
                    .withCollapseField("content_id")
                    .build();

            SearchHits<Wiki> searchHits = elasticsearchRestTemplate.search(searchQuery, Wiki.class);
            return toolsForWikiService.getListBySearchHits(searchHits, reliable);
        } catch (Exception e) {
            log.error("[ERR LOG]{}", e.getMessage());
            throw new CommonException(ExceptionType.ATTRACTION_NAME_NGRAMSEARCH_FAIL);
        }
    }

    /**
     * Fuzzy와 Ngram 둘 다 사용하여 집계. 가장 스코어가 높은 기준대로 리턴
     * @param title
     * @param maxResults
     * @param fuzziness
     * @param reliable
     * @return
     */
    public List<ResponseWikiDto> searchFuzzyAndNgram(String title, int maxResults, int fuzziness, boolean reliable) {
        try {
            List<Wiki> fuzzyList = searchTitleUseFuzzy(title, maxResults, fuzziness, reliable);
            List<Wiki> ngramList = searchTitleUseNgram(title, maxResults, reliable);


            //두개 리스트를 새로 합치고, 스코어가 있다면 가중. < 지명 : 스코어 > 로 집계
            HashMap<String, Float> alladdHashMap = new HashMap<>();
            for(int i = 0; i < fuzzyList.size(); i++) { //FuzzyList 결과 삽입
                Wiki wiki = fuzzyList.get(i);
                alladdHashMap.put(wiki.getAttractionName(), wiki.getScore());
            }

            for (int i = 0; i < ngramList.size(); i++) { //Ngram 결과 추가 삽입 및 집계
                Wiki wiki = ngramList.get(i);
                if(alladdHashMap.containsKey(wiki.getAttractionName())) {
                    alladdHashMap.put(wiki.getAttractionName(), alladdHashMap.get(wiki.getAttractionName()) + wiki.getScore());
                } else alladdHashMap.put(wiki.getAttractionName(), wiki.getScore());
            }

            //스코어링 기준으로 해시맵 스트림 정렬
            List<String> resultAttractionName =  alladdHashMap.entrySet()
                    .stream() //HashMap을 스트림으로 변환
                    .sorted(Map.Entry.<String, Float>comparingByValue().reversed()) // value을 기준으로 내림차순 정렬
                    .limit(maxResults)
                    .map(Map.Entry::getKey) //각 Entry에서 key(String) 추출
                    .collect(Collectors.toList()); //결과를 리스트화


            //추려진 값들을 should를 사용하여 동시적으로 조회
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            for (String name : resultAttractionName) {
                boolQueryBuilder.should(QueryBuilders.matchQuery("attraction_name", name));
            }
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(boolQueryBuilder)
                    .withCollapseField("content_id")
                    .build();

            SearchHits<Wiki> searchHits = elasticsearchRestTemplate.search(searchQuery, Wiki.class);
            List<Wiki> searchResult =  toolsForWikiService.getListBySearchHits(searchHits, reliable);

            /**
             * AttractionName List대로 재정렬 로직 구현
             * 1. resultAttractionName 리스트를 순회 (소스)
             * 2. 중간 연산 : 각 name에 대해 searchResults 스트림을 열고 attraction Name이 일치하는 객체 필터링
             * 3. 종단 연산 : 결과를 List<Wiki>로 수집
             */
            return resultAttractionName.stream()
                    .flatMap(name -> searchResult.stream()
                            .filter(wiki -> wiki.getAttractionName().equals(name)))
                    .map(wiki -> wiki.toDto())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("[ERR LOG] {}", e.getMessage());
            throw new CommonException(ExceptionType.SEARCH_FUZZY_AND_NGRAM_FAIL);
        }
    }
}
