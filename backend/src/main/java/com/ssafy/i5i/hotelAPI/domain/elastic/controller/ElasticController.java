package com.ssafy.i5i.hotelAPI.domain.elastic.controller;

import com.ssafy.i5i.hotelAPI.domain.elastic.service.ElasticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/elastic")
public class ElasticController {
    private final ElasticService elasticService;

    @GetMapping("test")
    public Mono<String> test(){
        return elasticService.test();
    }
}
