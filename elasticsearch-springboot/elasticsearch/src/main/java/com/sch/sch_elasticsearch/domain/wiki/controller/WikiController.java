package com.sch.sch_elasticsearch.domain.wiki.controller;

import com.sch.sch_elasticsearch.domain.wiki.dto.SearchAllDTO;
import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiServiceBasic;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiServiceExtend;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiServiceTitle;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wiki")
public class WikiController {
    private final WikiServiceBasic wikiServiceBasic;
    private final WikiServiceExtend wikiServiceExtend;
    private final WikiServiceTitle wikiServiceTitle;

    //특정 컬럼의 Keyword 매칭 검색
    @GetMapping("/keyword")
    public List<Wiki> searchExact(@RequestParam("typeNum") int typeNum,
                                  @RequestParam("inputString") String inputString,
                                  @RequestParam("reliable") boolean reliable,
                                  @RequestParam("maxResults") int maxResults) {
        return wikiServiceBasic.searchExact(typeNum, inputString, reliable, maxResults);
    }

    //특정 컬럼의 match-phrase 검색
    @GetMapping("/partial")
    public List<Wiki> searchPartial(@RequestParam("typeNum") int typeNum,
                                    @RequestParam("inputString") String inputString,
                                    @RequestParam("reliable") boolean reliable,
                                    @RequestParam("maxResults") int maxResults) {
        return wikiServiceBasic.searchPartial(typeNum, inputString, reliable, maxResults);
    }

    //특정 칼럼의 match
    @GetMapping("/fuzzy")
    public List<Wiki> searchFuzzy(@RequestParam("typeNum") int typeNum, @RequestParam("inputString") String inputString,
                                  @RequestParam("reliable") boolean reliable, @RequestParam("maxResults") int maxResults ,@RequestParam("fuzziness") int fuzziness) {
        return wikiServiceExtend.fuzzinessSearch(typeNum, inputString, reliable, maxResults, fuzziness);
    }

    //통합 내용 검색(전문 검색)
    @PostMapping("/search")
    public List<Wiki> searchAll(@RequestBody SearchAllDTO searchAllDTO) {
        return wikiServiceExtend.searchAll(searchAllDTO);
    }

    //통합 제목 검색 : 제목 일치 or (Fuzzy + ngram)

    //제목 일치 여부 검색
    @GetMapping("title-correct")
    public Wiki searchTitleCorrect(@RequestParam("title") String title, @RequestParam("reliable") boolean reliable) {
        return wikiServiceTitle.searchTitleCorrect(title, reliable);
    }

    //Fuzzy 제목 검색
    @GetMapping("/title-fuzzy")
    public List<Wiki> searchTitleFuzzy(@RequestParam("title") String title, @RequestParam("maxResults") int maxResults,
                                       @RequestParam("fuzziness") int fuzziness, @RequestParam("reliable") boolean reliable)
    {
        return wikiServiceTitle.searchTitleUseFuzzy(title, maxResults, fuzziness, reliable);
    }
    //ngram 제목 검색
    @GetMapping("/title-ngram")
    public List<Wiki> searchTitleNgram(@RequestParam("title") String title, @RequestParam("maxResults") int maxResults,
                                       @RequestParam("reliable") boolean reliable)
    {
        return wikiServiceTitle.searchTitleUseNgram(title, maxResults, reliable);
    }
}
