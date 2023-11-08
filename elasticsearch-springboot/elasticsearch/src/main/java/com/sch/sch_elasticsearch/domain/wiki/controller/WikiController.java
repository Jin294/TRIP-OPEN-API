package com.sch.sch_elasticsearch.domain.wiki.controller;

import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiServiceBasic;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wiki")
public class WikiController {
    private final WikiServiceBasic wikiServiceBasic;

    //특정 컬럼의 Keyword 매칭 검색
    @GetMapping("search-exact")
    public List<Wiki> searchExact(@RequestParam("typeNum") int typeNum, @RequestParam("input") String inputString) {
        return wikiServiceBasic.searchExact(typeNum, inputString);
    }

    //특정 컬럼의 match-phrase 검색
    @GetMapping("search-partial")
    public List<Wiki> searchPartial(@RequestParam("typeNum") int typeNum, @RequestParam("inputString") String inputString) {
        return wikiServiceBasic.searchPartial(typeNum, inputString);
    }

}
