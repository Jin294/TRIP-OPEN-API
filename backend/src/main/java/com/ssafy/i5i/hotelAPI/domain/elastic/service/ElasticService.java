package com.ssafy.i5i.hotelAPI.domain.elastic.service;

import com.ssafy.i5i.hotelAPI.domain.elastic.dto.SearchAllDto;
import com.ssafy.i5i.hotelAPI.domain.elastic.dto.WikiDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ElasticService {
    private final WebClient webClient;

    public ElasticService(@Value("${url.host}") String baseUrl){
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl+"/elastic/api/wiki")
                .build();
    }

    public List<WikiDto> searchExact(int typeNum, String inputString, boolean reliable, int maxResults) {
        return webClient.get().uri(uriBuilder -> {
            uriBuilder
                    .path("/keyword")
                    .queryParam("typeNum",typeNum)
                    .queryParam("inputString",inputString)
                    .queryParam("reliable",reliable)
                    .queryParam("maxResults",maxResults);
                return uriBuilder.build();
            })
                .retrieve()
                .bodyToFlux(WikiDto.class)
                .collectList()
                .block();
    }

    public List<WikiDto> searchPartial(int typeNum, String inputString, boolean reliable, int maxResults) {
        return webClient.get().uri(uriBuilder -> {
                    uriBuilder
                            .path("/partial")
                            .queryParam("typeNum",typeNum)
                            .queryParam("inputString",inputString)
                            .queryParam("reliable",reliable)
                            .queryParam("maxResults",maxResults);
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToFlux(WikiDto.class)
                .collectList()
                .block();
    }

    public List<WikiDto> fuzzinessSearch(int typeNum, String inputString, boolean reliable, int maxResults, int fuzziness) {
        return webClient.get().uri(uriBuilder -> {
                    uriBuilder
                            .path("/fuzzy")
                            .queryParam("typeNum",typeNum)
                            .queryParam("inputString",inputString)
                            .queryParam("reliable",reliable)
                            .queryParam("maxResults",maxResults)
                            .queryParam("fuzziness", fuzziness);
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToFlux(WikiDto.class)
                .collectList()
                .block();
    }

    public List<WikiDto> searchAll(SearchAllDto searchAllDTO) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/fuzzy").build())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(searchAllDTO))
                .retrieve()
                .bodyToFlux(WikiDto.class)
                .collectList()
                .block();
    }

    public List<WikiDto> searchFuzzyAndNgram(String title, int maxResults, int fuzziness, boolean reliable) {
        return webClient.get().uri(uriBuilder -> {
                    uriBuilder
                            .path("/title/aggregate-search")
                            .queryParam("title",title)
                            .queryParam("maxResults",maxResults)
                            .queryParam("fuzziness",fuzziness)
                            .queryParam("reliable",reliable);
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToFlux(WikiDto.class)
                .collectList()
                .block();
    }

    public WikiDto searchTitleCorrect(String title, boolean reliable) {
        return webClient.get().uri(uriBuilder -> {
                    uriBuilder
                            .path("/title/correct")
                            .queryParam("title",title)
                            .queryParam("reliable",reliable);
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(WikiDto.class)
                .block();
    }

    public List<WikiDto> searchTitleUseFuzzyDto(String title, int maxResults, int fuzziness, boolean reliable) {
        System.out.println(webClient.get());
        return webClient.get().uri(uriBuilder -> {
                    uriBuilder
                            .path("/title/fuzzy")
                            .queryParam("title",title)
                            .queryParam("maxResults",maxResults)
                            .queryParam("fuzziness",fuzziness)
                            .queryParam("reliable",reliable);
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToFlux(WikiDto.class)
                .collectList()
                .block();
    }

    public List<WikiDto> searchTitleUseNgramDto(String title, int maxResults, boolean reliable) {
        return webClient.get().uri(uriBuilder -> {
                    uriBuilder
                            .path("/title/ngram")
                            .queryParam("title",title)
                            .queryParam("maxResults",maxResults)
                            .queryParam("reliable",reliable);
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToFlux(WikiDto.class)
                .collectList()
                .block();
    }

    public Mono<String> test(){
        return webClient.get()
                .retrieve()
                .bodyToMono(String.class);
    }
}
