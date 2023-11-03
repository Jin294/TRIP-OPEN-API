package com.sch.sch_elasticsearch;

import com.sch.sch_elasticsearch.domain.accommodation.service.AccommodationService;
import com.sch.sch_elasticsearch.domain.test.TestDTO;
import com.sch.sch_elasticsearch.domain.test.TestService;
import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiService;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.*;
import org.elasticsearch.client.RequestOptions;

import java.util.List;

@RestController
public class TestController {

    RestHighLevelClient client;
    AccommodationService accommodationService;
    TestService testService;

    private final WikiService wikiService;

    public TestController(RestHighLevelClient client, AccommodationService accommodationService, TestService testService, WikiService wikiService) {
        this.client = client;
        this.accommodationService = accommodationService;
        this.testService = testService;
        this.wikiService = wikiService;
    }

    @RequestMapping("boot")
    public String Test() {
        try {
            MainResponse response = client.info(RequestOptions.DEFAULT);
            System.out.println("Elasticsearch 연결 성공: " + response.getVersion().toString());
            return "Well Done";
        } catch (Exception e) {
            System.out.println("Elasticsearch 연결 실패: " + e.getMessage());
            return "fail to boot";
        }
    }

    @GetMapping("t2")
    public List<Wiki> t2(@RequestParam("typeNum") int typeNum, @RequestParam("input") String inputString) {
        return wikiService.searchExact(typeNum, inputString);
    }

    @GetMapping("t3")
    public List<Wiki> t3(@RequestParam("typeNum") int typeNum, @RequestParam("inputString") String inputString) {
        return wikiService.searchPartial(typeNum, inputString);
    }
}
