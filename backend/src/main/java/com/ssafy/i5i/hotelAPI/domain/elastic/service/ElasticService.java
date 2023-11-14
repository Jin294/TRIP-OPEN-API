package com.ssafy.i5i.hotelAPI.domain.elastic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ElasticService {
    private final WebClient webClient;
    public ElasticService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://k9b205.p.ssafy.io/elastic/boot")
                .build();
    }

    public Mono<String> test(){
        return webClient.get()
                .retrieve()
                .bodyToMono(String.class);
    }
}
