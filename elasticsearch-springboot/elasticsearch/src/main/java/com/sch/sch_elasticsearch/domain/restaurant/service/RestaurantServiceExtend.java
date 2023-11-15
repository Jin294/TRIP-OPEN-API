package com.sch.sch_elasticsearch.domain.restaurant.service;

import com.sch.sch_elasticsearch.domain.wiki.dto.ResponseWikiDto;
import com.sch.sch_elasticsearch.exception.CommonException;
import com.sch.sch_elasticsearch.exception.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RestaurantServiceExtend {
    public List<ResponseWikiDto> fuzzinessSearch(int typeNum, String inputString, int maxResults, int fuzziness) {
        try {

            return null;
        } catch (Exception e) {
            log.info("[ERR LOG] {}", e.getMessage());
            throw new CommonException(ExceptionType.RESTAURANT_FUZZINESS_SEARCH_FAIL);
        }
    }
}
