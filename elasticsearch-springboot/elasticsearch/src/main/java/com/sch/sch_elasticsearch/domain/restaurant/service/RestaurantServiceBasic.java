package com.sch.sch_elasticsearch.domain.restaurant.service;

import com.sch.sch_elasticsearch.domain.restaurant.dto.ResponseRestaurantDto;
import com.sch.sch_elasticsearch.domain.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RestaurantServiceBasic {

    private ElasticsearchOperations elasticsearchOperations;
    public RestaurantServiceBasic(@Qualifier("ElasticsearchOperationsBean") ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    private ToolsForRestauantService toolsForRestauantService;
    public List<ResponseRestaurantDto> searchExact(String inputString, int maxResults) {
        String type = ToolsForRestauantService.getType(0);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery(type, inputString))
                .withPageable(PageRequest.of(0, maxResults))
                .build();

        return toolsForRestauantService.getListBySearchHits(elasticsearchOperations.search(searchQuery, Restaurant.class));
    }
}
