package com.sch.sch_elasticsearch.domain.restaurant.contorller;

import com.sch.sch_elasticsearch.domain.restaurant.dto.ResponseRestaurantDto;
import com.sch.sch_elasticsearch.domain.restaurant.service.RestaurantServiceBasic;
import com.sch.sch_elasticsearch.domain.restaurant.service.RestaurantServiceExtend;
import com.sch.sch_elasticsearch.domain.wiki.dto.ResponseWikiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    RestaurantServiceExtend restaurantServiceExtend;
    RestaurantServiceBasic restaurantServiceBasic;

    //특정 컬럼의 Keyword 매칭 검색
    @GetMapping("/keyword")
    public List<ResponseRestaurantDto> searchExact(
                                                   @RequestParam("inputString") String inputString,
                                                   @RequestParam("maxResults") int maxResults) {
        return restaurantServiceBasic.searchExact(inputString, maxResults);
    }

}
