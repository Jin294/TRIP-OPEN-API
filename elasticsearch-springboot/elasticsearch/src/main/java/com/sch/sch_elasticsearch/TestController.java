package com.sch.sch_elasticsearch;

import com.sch.sch_elasticsearch.domain.accommodation.entity.Accommodation;
import com.sch.sch_elasticsearch.domain.accommodation.service.AccommodationService;
import com.sch.sch_elasticsearch.domain.test.TestDTO;
import com.sch.sch_elasticsearch.domain.test.TestService;
import lombok.ToString;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.springframework.web.bind.annotation.*;
import org.elasticsearch.client.RequestOptions;

import java.util.List;

@RestController
public class TestController {

    RestHighLevelClient client;
    AccommodationService accommodationService;
    TestService testService;

    public TestController(RestHighLevelClient client, AccommodationService accommodationService, TestService testService) {
        this.client = client;
        this.accommodationService = accommodationService;
        this.testService = testService;
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

//    @GetMapping("getall")
//    public Iterable<Accommodation> getAll() {
//        return accommodationService.getAllResidences();
//    }

//    @GetMapping("search/{input}")
//    public List<Accommodation> search(@PathVariable String input) {
//        return accommodationService.search(input);
//    }
//
//    @GetMapping("querytest/{input}")
//    public List<Accommodation> test(@PathVariable String input) {return accommodationService.querytest(input);}

    @PostMapping("t1")
    public void t1(@RequestBody TestDTO testDTO) {
        testService.saveTest(testDTO);
    }
}
