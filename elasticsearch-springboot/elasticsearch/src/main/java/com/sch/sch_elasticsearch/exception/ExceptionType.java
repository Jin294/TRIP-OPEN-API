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

    JWT_TOKEN_EXPIRED(4000, "토큰이 만료되었습니다."),
    NULL_POINT_EXCEPTION(4001, "데이터가 없습니다."),
    SORTED_TYPE_EXCEPTION(4002,"정렬 타입이 잘못되었습니다."),

    ACCOMMODATION_SAVE_FAIL(1000, "숙소 데이터 저장 실패"),

    SEARCH_ERR(2000, "검색 과정에서 문제 발생"),

    TYPENUM_IS_INVALID(2100, "키워드 검색용 TypeNum 번호가 유효하지 않습니다.")

    ;

    private final int code;
    private final String message;
}
