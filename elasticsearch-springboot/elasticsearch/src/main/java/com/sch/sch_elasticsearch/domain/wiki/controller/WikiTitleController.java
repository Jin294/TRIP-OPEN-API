package com.sch.sch_elasticsearch.domain.wiki.controller;

import com.sch.sch_elasticsearch.domain.global.DataResponse;
import com.sch.sch_elasticsearch.domain.wiki.dto.ResponseWikiDto;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiServiceBasic;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiServiceExtend;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiServiceTitle;
import com.sch.sch_elasticsearch.exception.CommonException;
import com.sch.sch_elasticsearch.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 제목 검색과 관련된 기능 제공 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/wiki")
public class WikiTitleController {
    private final WikiServiceBasic wikiServiceBasic;
    private final WikiServiceExtend wikiServiceExtend;
    private final WikiServiceTitle wikiServiceTitle;

    //통합 제목 검색 : 제목 일치 or (Fuzzy + ngram)
    @GetMapping("/title/aggregate-search")
    public DataResponse<List<ResponseWikiDto>> searchTitleComprehensive(@RequestParam("title") String title,
                                                                        @RequestParam("maxResults") int maxResults,
                                                                        @RequestParam("fuzziness") int fuzziness,
                                                                        @RequestParam("reliable") boolean reliable,
                                                                        @RequestParam("fuzzyPrimary") boolean fuzzyPrimary)
    {
        try {
            ResponseWikiDto wiki = wikiServiceTitle.searchTitleCorrect(title, reliable);
            if (wiki != null) {
                List<ResponseWikiDto> data = new ArrayList<>();
                data.add(wiki);
                return new DataResponse<>(200, "success", data);
            } //1. 일치 제목 검색이 있다면 이를 리스트에 추가 후 리턴
            List<ResponseWikiDto> data = wikiServiceTitle.searchFuzzyAndNgram(title, maxResults, fuzziness, reliable, fuzzyPrimary); //2. 아니라면 두개 검색 비교후 리턴
            return new DataResponse<>(200, "success", data);
        } catch (Exception e) {
            log.error("[ERR LOG] {}", e);
            throw new CommonException(ExceptionType.WIKI_AGGREGATE_TITLE_SEARCH_FAIL);
        }
    }

    //제목 일치 여부 검색
    @GetMapping("/title/correct")
    public DataResponse<ResponseWikiDto> searchTitleCorrect(@RequestParam("title") String title, @RequestParam("reliable") boolean reliable) {
        ResponseWikiDto data = wikiServiceTitle.searchTitleCorrect(title, reliable);
        return new DataResponse<>(200, "success", data);
    }

    //Fuzzy 제목 검색
    @GetMapping("/title/fuzzy")
    public DataResponse<List<ResponseWikiDto>> searchTitleFuzzy(@RequestParam("title") String title, @RequestParam("maxResults") int maxResults,
                                                  @RequestParam("fuzziness") int fuzziness, @RequestParam("reliable") boolean reliable)
    {
        List<ResponseWikiDto> data = wikiServiceTitle.searchTitleUseFuzzyDto(title, maxResults, fuzziness, reliable);
        return new DataResponse<>(200, "success", data);
    }
    //ngram 제목 검색
    @GetMapping("/title/ngram")
    public DataResponse<List<ResponseWikiDto>> searchTitleNgram(@RequestParam("title") String title, @RequestParam("maxResults") int maxResults,
                                                  @RequestParam("reliable") boolean reliable)
    {
        List<ResponseWikiDto> data = wikiServiceTitle.searchTitleUseNgramDto(title, maxResults, reliable);
        return new DataResponse<>(200, "success", data);
    }
}
