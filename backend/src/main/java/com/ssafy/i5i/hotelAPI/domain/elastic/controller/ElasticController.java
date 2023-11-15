package com.ssafy.i5i.hotelAPI.domain.elastic.controller;

import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
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
    public List<WikiDto> searchExact(@RequestParam("typeNum") int typeNum,
                                     @RequestParam("inputString") String inputString,
                                     @RequestParam("reliable") boolean reliable,
                                     @RequestParam("maxResults") int maxResults) {
        return elasticService.searchExact(typeNum, inputString, reliable, maxResults);

    }

    //특정 컬럼의 match 검색
    @GetMapping("/partial")
    public List<WikiDto> searchPartial(@RequestParam("typeNum") int typeNum,
                                               @RequestParam("inputString") String inputString,
                                               @RequestParam("reliable") boolean reliable,
                                               @RequestParam("maxResults") int maxResults) {
        return elasticService.searchPartial(typeNum, inputString, reliable, maxResults);
    }

    //특정 칼럼의 fuzzy 검색
    @GetMapping("/fuzzy")
    public List<WikiDto> searchFuzzy(@RequestParam("typeNum") int typeNum, @RequestParam("inputString") String inputString,
                                             @RequestParam("reliable") boolean reliable, @RequestParam("maxResults") int maxResults ,@RequestParam("fuzziness") int fuzziness) {
        return elasticService.fuzzinessSearch(typeNum, inputString, reliable, maxResults, fuzziness);
    }

    //통합 내용 검색(전문 검색)
    @PostMapping("/search")
    public List<WikiDto> searchAll(@RequestBody SearchAllDto searchAllDTO) {
        return elasticService.searchAll(searchAllDTO);
    }

    //통합 제목 검색 : 제목 일치 or (Fuzzy + ngram)
    @GetMapping("/title/aggregate-search")
    public List<WikiDto> searchTitleComprehensive(@RequestParam("title") String title, @RequestParam("maxResults") int maxResults,
                                                          @RequestParam("fuzziness") int fuzziness, @RequestParam("reliable") boolean reliable){
        return elasticService.searchFuzzyAndNgram(title, maxResults, fuzziness, reliable);
    }

    //제목 일치 여부 검색
    @GetMapping("/title/correct")
    public WikiDto searchTitleCorrect(@RequestParam("title") String title, @RequestParam("reliable") boolean reliable) {
        return elasticService.searchTitleCorrect(title, reliable);
    }

    //Fuzzy 제목 검색
    @GetMapping("/title/fuzzy")
    public List<WikiDto> searchTitleFuzzy(@RequestParam("title") String title, @RequestParam("maxResults") int maxResults,
                                                  @RequestParam("fuzziness") int fuzziness, @RequestParam("reliable") boolean reliable)
    {
        return elasticService.searchTitleUseFuzzyDto(title, maxResults, fuzziness, reliable);
    }
    //ngram 제목 검색
    @GetMapping("/title/ngram")
    public List<WikiDto> searchTitleNgram(@RequestParam("title") String title, @RequestParam("maxResults") int maxResults,
                                                  @RequestParam("reliable") boolean reliable)
    {
        return elasticService.searchTitleUseNgramDto(title, maxResults, reliable);
    }

}
