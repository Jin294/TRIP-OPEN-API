package com.sch.sch_elasticsearch.domain.wiki.service;

import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import com.sch.sch_elasticsearch.exception.CommonException;
import com.sch.sch_elasticsearch.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Wiki searchTitleCorrect(String title, boolean reliable) {
        try {
            QueryBuilder queryBuilder = new MatchQueryBuilder("attraction_name.keyword", title);
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(queryBuilder)
                    .build();

            SearchHits<Wiki> searchHits = elasticsearchRestTemplate.search(searchQuery, Wiki.class);
            List<Wiki> result = toolsForWikiService.getListBySearchHits(searchHits, reliable);
            if(result.size() == 0) return null;
            return result.get(0);
        } catch (Exception e) {
            log.error("[ERR LOG]{}", e);
            throw new CommonException(ExceptionType.SEARCH_TITLE_CORRECT_FAIL);
        }
    }

    //제목 Fuzzy 검색
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

    //제목 Ngram 검색
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
            log.error("[ERR LOG]{}", e);
            throw new CommonException(ExceptionType.ATTRACTION_NAME_NGRAMSEARCH_FAIL);
        }
    }

}
