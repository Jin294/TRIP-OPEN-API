package com.sch.sch_elasticsearch.domain.wiki.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 *  스케줄러를 돌릴 서비스 로직 모음
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class WikiSchedulerService {
    private final RestHighLevelClient restHighLevelClient;

    //2단어 길이 이상 필터링된 인덱스
    @Value("${info.index.wiki.gt-two-token}")
    String twoIndex;

    @Value("${info.analyzer.nori}")
    String noriAnalyzer;


    public String calculateSimilarityByTerm(HashMap<String, Integer> hashMapA, HashMap<String, Integer> hashMapB) throws IOException {
        int totalTermCount = 0; //전체 용어 수
        int matchTermCount = 0; //맞는 용어 수

        //1. 전체 토큰 세기
        // HashMap의 values() 메서드를 사용하여 값들을 순회하고 합산
        for (Integer value : hashMapA.values()) {
            totalTermCount += value;
        }
        for (Integer value : hashMapB.values()) {
            totalTermCount += value;
        }

        //2. 맞는 용어 수를 세기
        for(String key : hashMapA.keySet()) {
            if(hashMapB.containsKey(key)) {
                System.out.println("Key 동일 확인 : " + key);
                System.out.print("카운트 추가 : " + matchTermCount);
                matchTermCount += hashMapA.get(key);
                matchTermCount += hashMapB.get(key);

                System.out.println(" " + hashMapA.get(key) + " " + hashMapB.get(key) + "  = " + matchTermCount);
            }
        }

        return "전체 용어와 일치하는 용어 = " + totalTermCount + " : " + matchTermCount;
    }

    /**
     * 문자열을 입력받아 분석기를 사용해 토큰화된 값을 String : Integer 결과로 리턴
     * @param inputString
     * @return HashMap<String, Integer>
     * @throws IOException
     */
    public HashMap<String, Integer> useAnalyzerAndGetTokens(String inputString) throws IOException {
        HashMap<String, Integer> hashMap = new HashMap<>();
        AnalyzeRequest request = AnalyzeRequest.withIndexAnalyzer(twoIndex, noriAnalyzer, inputString);
        AnalyzeResponse response = restHighLevelClient.indices().analyze(request, RequestOptions.DEFAULT);
        List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens(); // 결과에서 토큰화된 텍스트 가져오기

        // tokens 리스트를 반복하여 HashMap에 저장 후 출력
        tokens.forEach(token -> {
            String term = token.getTerm();
            log.info("[useAnalyzer] Term 정보 : {}", term);

            if(hashMap.containsKey(term)) {
                hashMap.put(term, hashMap.get(term) + 1);
            }
            else hashMap.put(term, 1);
        });
        return hashMap;
    }


}
