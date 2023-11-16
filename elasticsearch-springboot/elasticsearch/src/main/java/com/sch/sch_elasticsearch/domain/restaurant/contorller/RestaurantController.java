package com.sch.sch_elasticsearch.domain.restaurant.contorller;

import com.sch.sch_elasticsearch.domain.restaurant.dto.ResponseRestaurantDto;
import com.sch.sch_elasticsearch.domain.restaurant.service.RestaurantServiceBasic;
import com.sch.sch_elasticsearch.domain.restaurant.service.RestaurantServiceExtend;
import com.sch.sch_elasticsearch.domain.wiki.dto.ResponseWikiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 특정 컬럼이나 필드 검색 결과를 조회하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantServiceExtend restaurantServiceExtend;
    private final RestaurantServiceBasic restaurantServiceBasic;


    //특정 필드의 match 쿼리 조회
    @GetMapping("/partial")
    public List<ResponseRestaurantDto> searchPartial(@RequestParam("typeNum") int typeNum,
                                                     @RequestParam("inputString") String inputString,
                                                     @RequestParam("maxResults") int maxResults) {
        return restaurantServiceBasic.searchPartial(typeNum, inputString, maxResults);
    }

    //특정 필드의 Fuzzy 쿼리 조회
    @GetMapping("/fuzzy")
    public List<ResponseRestaurantDto> searchFuzzy(@RequestParam("typeNum") int typeNum,
                                             @RequestParam("inputString") String inputString,
                                             @RequestParam("maxResults") int maxResults,
                                             @RequestParam("fuzziness") int fuzziness) {
        return restaurantServiceExtend.fuzzinessSearch(typeNum, inputString, maxResults, fuzziness);
    }

//    //통합 내용 검색(전문 검색)
//    @PostMapping("/search")
//    public List<ResponseRestaurantDto> searchAll(DTO) {
//        return restaurantServiceExtend.searchAll();
//    }


}
