package com.ssafy.i5i.hotelAPI.domain.elastic.controller;

import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
import com.ssafy.i5i.hotelAPI.common.response.DataResponse;
import com.ssafy.i5i.hotelAPI.domain.elastic.dto.SearchAllDto;
import com.ssafy.i5i.hotelAPI.domain.elastic.dto.WikiDto;
import com.ssafy.i5i.hotelAPI.domain.elastic.service.ElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/elastic")
public class ElasticController {
    private final ElasticService elasticService;

    @GetMapping("/test")
    public Mono<String> test(){
        return elasticService.test();
    }

    //특정 컬럼의 Keyword 매칭 검색 ->
    @GetMapping("/keyword")
    public DataResponse<List<WikiDto>> searchExact(@RequestParam("typeNum") int typeNum,
                                     @RequestParam("inputString") String inputString,
                                     @RequestParam("reliable") boolean reliable,
                                     @RequestParam("maxResults") int maxResults) {
        List<WikiDto> data = elasticService.searchExact(typeNum, inputString, reliable, maxResults);
        return new DataResponse<>(200, "success", data);
    }

    //특정 컬럼의 match 검색
    @GetMapping("/partial")
    public DataResponse<List<WikiDto>> searchPartial(@RequestParam("typeNum") int typeNum,
                                               @RequestParam("inputString") String inputString,
                                               @RequestParam("reliable") boolean reliable,
                                               @RequestParam("maxResults") int maxResults) {
        List<WikiDto> data = elasticService.searchPartial(typeNum, inputString, reliable, maxResults);
        return new DataResponse<>(200, "success", data);
    }

    //특정 칼럼의 fuzzy 검색
    @GetMapping("/fuzzy")
    public DataResponse<List<WikiDto>> searchFuzzy(@RequestParam("typeNum") int typeNum, @RequestParam("inputString") String inputString,
                                             @RequestParam("reliable") boolean reliable, @RequestParam("maxResults") int maxResults ,@RequestParam("fuzziness") int fuzziness) {
        List<WikiDto> data = elasticService.fuzzinessSearch(typeNum, inputString, reliable, maxResults, fuzziness);
        return new DataResponse<>(200, "success", data);
    }

    //통합 내용 검색(전문 검색)
    @PostMapping("/search")
    public DataResponse<List<WikiDto>> searchAll(@RequestBody SearchAllDto searchAllDTO) {
        List<WikiDto> data = elasticService.searchAll(searchAllDTO);
        return new DataResponse<>(200, "success", data);
    }

    //통합 제목 검색 : 제목 일치 or (Fuzzy + ngram)
    @GetMapping("/title/aggregate-search")
    public DataResponse<List<WikiDto>> searchTitleComprehensive(@RequestParam("title") String title, @RequestParam("maxResults") int maxResults,
                                                          @RequestParam("fuzziness") int fuzziness, @RequestParam("reliable") boolean reliable){
        List<WikiDto> data = elasticService.searchFuzzyAndNgram(title, maxResults, fuzziness, reliable);
        return new DataResponse<>(200, "success", data);
    }

    //제목 일치 여부 검색
    @GetMapping("/title/correct")
    public DataResponse<WikiDto> searchTitleCorrect(@RequestParam("title") String title, @RequestParam("reliable") boolean reliable) {
        WikiDto data = elasticService.searchTitleCorrect(title, reliable);
        return new DataResponse<>(200, "success", data);
    }

    //Fuzzy 제목 검색
    @GetMapping("/title/fuzzy")
    public DataResponse<List<WikiDto>> searchTitleFuzzy(@RequestParam("title") String title, @RequestParam("maxResults") int maxResults,
                                                  @RequestParam("fuzziness") int fuzziness, @RequestParam("reliable") boolean reliable)
    {
        List<WikiDto> data =  elasticService.searchTitleUseFuzzyDto(title, maxResults, fuzziness, reliable);
        return new DataResponse<>(200, "success", data);
    }
    //ngram 제목 검색
    @GetMapping("/title/ngram")
    public DataResponse<List<WikiDto>> searchTitleNgram(@RequestParam("title") String title, @RequestParam("maxResults") int maxResults,
                                                  @RequestParam("reliable") boolean reliable)
    {
        List<WikiDto> data =  elasticService.searchTitleUseNgramDto(title, maxResults, reliable);
        return new DataResponse<>(200, "success", data);
    }

}
