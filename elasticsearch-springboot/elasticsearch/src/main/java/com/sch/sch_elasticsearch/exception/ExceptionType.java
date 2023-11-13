package com.sch.sch_elasticsearch.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    /**
     * CODE : 4자리 양의 정수 (맨 앞자리는 HTTP 상태코드의 앞 글자)
     * MESSAGE : 예외 메시지
     */


    ACCOMMODATION_SAVE_FAIL(1000, "숙소 데이터 저장 실패"),
    SEARCH_SEARCHALL_FAIL(1001, " : 전문 검색에 실해하였습니다 : WikiServiceExtend/searchAll()"),

    SEARCH_ERR(2000, "검색 과정에서 문제 발생"),

    TYPENUM_IS_INVALID(2100, "키워드 검색용 TypeNum 번호가 유효하지 않습니다."),

    SEARCH_NULL_TERMS_FAIL(3000, "Term 필드가 Null인 값들을 가져오는데 실패했습니다. : WikiServiceTermSimilary/findWikiMatchTermIsNull()"),
    UPDATE_NULL_TERMS_NO_WIKICONTENTS_FAIL(3001,
            "term 필드가 null인 값 중 wikicountent가 빈 값들의 term 값 삽입을 실패했습니다. : WikiServiceTermSimilary/checkWikiConentIsNull()"),
    CALCULATE_SIMILARY_TERMS(3002, "분석된 문장들의 용어 유사도 검색에 실패했습니다. : WikiServiceTermSimilary/calculateSimilarityByTerm()"),
    GET_TERMS_USING_ANALYZER_FAIL(3003, "분석기를 사용하여 용어 단위 분석이 실패했습니다. : WikiServiceTermSimilary/useAnalyzerAndGetTokens()"),
    UPDATE_NULL_TERMS_FAIL(3004, "새로운 term 계산 값 갱신에 실패하였습니다. : WikiServiceTermSimilary/updateNewTerms()" ),
    ATTRACTION_NAME_FUZZYSEARCH_FAIL(3005, "Fuzzyniss 제목 유사 검색이 실패하였습니다. : WikiServiceTitle/searchTitleUseFuzzy()"),
    ATTRACTION_NAME_NGRAMSEARCH_FAIL(3006, "Ngram 제목 검색이 실패하였습니다. : WikiServiceTitle/searchTitleUseNgram()"),

    SEARCH_TITLE_CORRECT_FAIL(3007, "일치하는 제목을 찾는 중 오류가 발생했습니다 : WikiServiceTitle/searchTitleCorrect()" ),




    ;
    private final int code;
    private final String message;
}
