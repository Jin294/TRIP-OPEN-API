package com.sch.sch_elasticsearch.domain.wiki.service;

import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Script 등 2차 가공 이상 복잡한 쿼리 로직의 집합입니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WikiServiceExtend {
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final ToolsForWikiService toolsForWikiService;


    /**
     * Attraction_name과 Wiki_Title이 입력 검색어와 같은 쿼리 반환
     * @return List<Wiki>
     */
    public List<Wiki> getSameAttNameAndWikiTitle(String inputString) {

        // Script 문자열을 정의한다.
        String scriptStr = "if (doc['attraction_name.keyword'].size() != 0 && doc['wiki_title.keyword'].size() != 0) { " +
                "String attName = doc['attraction_name.keyword'].value.replace(' ', ''); " +
                "String wikiTitle = doc['wiki_title.keyword'].value.replace(' ', ''); " +
                "return attName.equals(wikiTitle) && attName.equals(params.inputString); " +
                "} return false;";

        // 파라미터 맵을 생성하고 inputString을 추가한다. painless에서는 이렇게 param을 통해 값을 넣어야한다. (pstmt를 지원하지 않는다..)
        Map<String, Object> params = new HashMap<>();
        params.put("inputString", inputString);

        // Script 객체를 생성한다. (params를 넘겨줘야 해서 인자를 변경해줄 것)
        Script script = new Script(ScriptType.INLINE, "painless", scriptStr, params);

        // 스크립트 쿼리 빌더를 생성한다.
        QueryBuilder scriptQueryBuilder = QueryBuilders.scriptQuery(script);

        // Elasticsearch의 bool 쿼리를 생성한다.
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(scriptQueryBuilder);

        // NativeSearchQuery를 생성한다.
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withSourceFilter(new FetchSourceFilter(new String[]{"pk_id","attraction_name", "content_id", "wiki_title", "wiki_content"}, null)) // 필요한 필드만 가져오기 위한 설정
                .build();

        // Elasticsearch에서 쿼리 실행 후 결과값 가져오기
        SearchHits<Wiki> searchHits = elasticsearchRestTemplate.search(searchQuery, Wiki.class);
        return toolsForWikiService.getListBySearchHits(searchHits);
    }

    /**
     * fuzziness 유사도 검색
     * @param inputString
     * @param typeNum
     * @param fuzziness
     * @return List<Wiki>
     */
    public List<Wiki> fuzzinessSearch(String inputString, int typeNum, int fuzziness) {
        String type = toolsForWikiService.getType(typeNum);

        // fuzziness를 설정하는 방법. 'AUTO'로 설정하면 Elasticsearch가 자동으로 fuzziness 수준을 결정합니다.
        Fuzziness fuzzinessLevel;
        if (fuzziness > 0) {
            fuzzinessLevel = Fuzziness.build(fuzziness);
        } else {
            fuzzinessLevel = Fuzziness.AUTO;
        }

        QueryBuilder fuzzyBuilder = new FuzzyQueryBuilder(type, inputString)
                .fuzziness(fuzzinessLevel);
        //NativeQuery 생성
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(fuzzyBuilder)
                .build();

        // Elasticsearch에서 쿼리 실행 후 결과값 가져오기
        SearchHits<Wiki> searchHits = elasticsearchRestTemplate.search(searchQuery, Wiki.class);
        return toolsForWikiService.getListBySearchHits(searchHits);
    }

}
