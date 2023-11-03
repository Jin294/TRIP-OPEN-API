package com.sch.sch_elasticsearch.domain.wiki.service;

import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import com.sch.sch_elasticsearch.domain.wiki.repository.WikiRepository;
import com.sch.sch_elasticsearch.exception.CommonException;
import com.sch.sch_elasticsearch.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WikiService {
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 정확한 keyword 검색용 쿼리문 : 제목, 타이틀, content_id
     * @param typeNum (타입 넘버)
     * @param inputString (찾을 검색어)
     * @return List<Wiki> 결과값
     */
    public List<Wiki> searchExact(int typeNum, String inputString) {
        String type = getType(typeNum);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                // .keyword 붙일 것. 정확한 검색을 위해 필요하다.
                .withQuery(QueryBuilders.termQuery(type, inputString))
                .build();

        // Elasticsearch에서 쿼리 실행
        return getListBySearchHits(elasticsearchRestTemplate.search(searchQuery, Wiki.class));
    }

    /**
     * 입력 파라미터의 부분 검색(전문 검색) 수행 (attraction_name), (wiki_content)
     * @param typeNum (타입 넘버)
     * @param inputString (찾을 검색어)
     * @return List<Wiki> 결과값
     */
    public List<Wiki> searchPartial(int typeNum, String inputString) {
        String type = getType(typeNum);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery(type, inputString))
                .build();

        // Elasticsearch에서 쿼리 실행 후 결과값 가져오기
        return getListBySearchHits(elasticsearchRestTemplate.search(searchQuery, Wiki.class));
    }

    /**
     * SearchHits 에서 Content 가져와서 리스트로 변환 후 출력
     * @param SearchHits<Wiki> result
     * @return List<Wiki>
     */
    public List<Wiki> getListBySearchHits(SearchHits<Wiki> result) {
        // SearchHits 내부 Content를 List<Wiki>로 변환
        List<Wiki> wikiList = new ArrayList<>();
        for (SearchHit<Wiki> hit : result) {
            wikiList.add(hit.getContent());
        }
        return wikiList;
    }

    /**
     * TypeNum : Keyword change
     * @param typeNum (input num)
     * @return type (String keyword)
     * @throws CommonException 유효하지 않은 typeNum이 입력될 경우
     */
    public String getType(int typeNum) {
        String type = "";
        switch (typeNum) {
            case 0:
                type = "attraction_name.keyword";
                break;
            case 1:
                type = "wiki_title.keyword";
                break;
            case 2:
                type = "content_id";
                break;
            case 3:
                type = "attraction_name";
                break;
            case 4:
                type = "wiki_content";
                break;
            default:
                throw new CommonException(ExceptionType.TYPENUM_IS_INVALID);
        }
        return type;
    }


}
