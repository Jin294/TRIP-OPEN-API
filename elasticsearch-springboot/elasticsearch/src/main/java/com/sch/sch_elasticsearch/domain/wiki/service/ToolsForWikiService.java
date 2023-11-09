package com.sch.sch_elasticsearch.domain.wiki.service;

import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import com.sch.sch_elasticsearch.exception.CommonException;
import com.sch.sch_elasticsearch.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 다수 서비스에서 사용하는 유용한 공통 메서드들의 모음입니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ToolsForWikiService {




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
