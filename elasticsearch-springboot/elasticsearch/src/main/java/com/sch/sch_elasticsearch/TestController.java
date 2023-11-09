package com.sch.sch_elasticsearch;

import com.sch.sch_elasticsearch.domain.accommodation.service.AccommodationService;
import com.sch.sch_elasticsearch.domain.test.TestService;
import com.sch.sch_elasticsearch.domain.wiki.entity.Wiki;
import com.sch.sch_elasticsearch.domain.wiki.service.ToolsForWikiService;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiSchedulerService;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiServiceBasic;
import com.sch.sch_elasticsearch.domain.wiki.service.WikiServiceExtend;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.springframework.web.bind.annotation.*;
import org.elasticsearch.client.RequestOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class TestController {


    private final WikiServiceBasic wikiService;
    private final WikiServiceExtend wikiServiceExtend;
    private final RestHighLevelClient client;
    private final ToolsForWikiService toolsForWikiService;
    private final WikiSchedulerService wikiSchedulerService;

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

    @GetMapping("t4")
    public List<Wiki> t4(@RequestParam("typeNum") int typeNum, @RequestParam("inputString") String inputString) {
//        return wikiService.searchPartial(typeNum, inputString);
        return wikiServiceExtend.fuzzinessSearch(inputString, 0, 1);
    }

    @PostMapping("t5")
    public String t5(@RequestParam("inputString") String s1, @RequestBody String s2) throws IOException {
        long startTime = System.nanoTime();

        //문자열의 토큰 HM으로 저장하기
        HashMap<String, Integer> h1 = wikiSchedulerService.useAnalyzerAndGetTokens(s1);
        HashMap<String, Integer> h2 = wikiSchedulerService.useAnalyzerAndGetTokens(s2);

        String result = wikiSchedulerService.calculateSimilarityByTerm(h1, h2);

        long endTime = System.nanoTime();
        long duration = endTime - startTime; // 실행 시간 측정 (나노초)
        long durationInMs = TimeUnit.NANOSECONDS.toMillis(duration); // 밀리초로 변환
        return result + " , 실행 시간 : " + durationInMs + "ms";
    }
}
