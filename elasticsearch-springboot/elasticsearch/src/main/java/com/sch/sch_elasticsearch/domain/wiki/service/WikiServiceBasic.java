package com.sch.sch_elasticsearch.domain.wiki.service;

import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 비교적 간단한 일치 쿼리 등을 조회합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WikiServiceBasic {
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final ToolsForWikiService toolsForWikiService;

    /**
     * 정확한 keyword 검색용 쿼리문 : attraction_name, wiki_title, content_id
     * @param typeNum (타입 넘버)
     * @param inputString (찾을 검색어)
     * @return List<Wiki> 결과값
     */
    public List<Wiki> searchExact(int typeNum, String inputString) {
        String type = toolsForWikiService.getType(typeNum);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                // .keyword 붙일 것. 정확한 검색을 위해 필요하다.
                .withQuery(QueryBuilders.termQuery(type, inputString))
                .build();

        // Elasticsearch에서 쿼리 실행
        return toolsForWikiService.getListBySearchHits(elasticsearchRestTemplate.search(searchQuery, Wiki.class));
    }

    /**
     * 입력 파라미터의 부분 검색(전문 검색) 수행 (attraction_name), (wiki_content)
     * @param typeNum (타입 넘버)
     * @param inputString (찾을 검색어)
     * @return List<Wiki> 결과값
     */
    public List<Wiki> searchPartial(int typeNum, String inputString) {
        String type = toolsForWikiService.getType(typeNum);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery(type, inputString))
                .build();

        // Elasticsearch에서 쿼리 실행 후 결과값 가져오기
        return toolsForWikiService.getListBySearchHits(elasticsearchRestTemplate.search(searchQuery, Wiki.class));
    }




}
