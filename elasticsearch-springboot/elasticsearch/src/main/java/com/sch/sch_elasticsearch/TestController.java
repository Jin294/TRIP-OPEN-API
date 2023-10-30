package com.sch.sch_elasticsearch;

import com.sch.sch_elasticsearch.domain.residence.entity.Accommodation;
import com.sch.sch_elasticsearch.domain.residence.service.AccommodationService;
import lombok.ToString;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.elasticsearch.client.RequestOptions;

import java.util.List;

@RestController
@ToString
public class TestController {

    RestHighLevelClient client;
    AccommodationService accommodationService;

    public TestController(RestHighLevelClient client, AccommodationService accommodationService) {
        this.client = client;
        this.accommodationService = accommodationService;
    }

    @RequestMapping("bootTest")
    public String Test() {
        try {
            MainResponse response = client.info(RequestOptions.DEFAULT);
            System.out.println("Elasticsearch 연결 성공: " + response.getVersion().toString());
        } catch (Exception e) {
            System.out.println("Elasticsearch 연결 실패: " + e.getMessage());
        }
        return "Well Done";
    }

    @RequestMapping("getall")
    public Iterable<Accommodation> getAll() {
        return accommodationService.getAllResidences();
    }

    @RequestMapping("search/{input}")
    public List<Accommodation> search(@PathVariable String input) {
        return accommodationService.search(input);
    }
}
