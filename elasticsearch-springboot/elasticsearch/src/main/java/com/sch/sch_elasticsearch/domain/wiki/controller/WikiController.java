package com.sch.sch_elasticsearch.domain.wiki.controller;

import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiService;
import com.sch.sch_elasticsearch.exception.CommonException;
import com.sch.sch_elasticsearch.exception.ExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wiki")
public class WikiController {
    private final WikiService wikiService;

    @GetMapping("search-exact")
    public List<Wiki> searchExact(@RequestParam("typeNum") int typeNum, @RequestParam("input") String inputString) {
        return wikiService.searchExact(typeNum, inputString);
    }

    @GetMapping("search-partial")
    public List<Wiki> searchPartial(@RequestParam("typeNum") int typeNum, @RequestParam("inputString") String inputString) {
        return wikiService.searchPartial(typeNum, inputString);
    }
}
