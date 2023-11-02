package com.sch.sch_elasticsearch.domain.wiki.controller;

import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiService;
import com.sch.sch_elasticsearch.exception.CommonException;
import com.sch.sch_elasticsearch.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wiki")
public class WikiController {
    private final WikiService wikiService;

    //검색어 일치의 경우 반환
    @GetMapping("/name-correct")
    public List<Wiki> searchExactName(@RequestParam("input") String input) {
        try {
            return wikiService.searchExactName(input);
        }
        catch (Exception e) {
            throw new CommonException(ExceptionType.SEARCH_ERR);
        }
    }

    //부분 검색 반환
    @GetMapping("/name-partial")
    public List<Wiki> searchPartialName(@RequestBody String input) {
        return wikiService.searchPartialName(input);
//        try {
//            return wikiService.searchPartialName(input);
//        }
//        catch (Exception e) {
//            throw new CommonException(ExceptionType.SEARCH_ERR);
//        }
    }
}
