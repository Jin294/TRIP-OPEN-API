package com.ssafy.i5i.hotelAPI.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    /**
     * CODE : 4자리 양의 정수 (맨 앞자리는 HTTP 상태코드의 앞 글자)
     * MESSAGE : 예외 메시지
     */
    //4000번은 jwt 와 관련된 에러
    JWT_TOKEN_EXPIRED(4000, "토큰이 만료되었습니다."),
    JWT_PARSER_FAILED(4001, "토큰 파싱에 실패했습니다."),
    //5000번은 데이터와 관련된 에러
    NULL_POINT_EXCEPTION(5001, "데이터가 없습니다."),
    SORTED_TYPE_EXCEPTION(5002,"정렬 타입이 잘못되었습니다."),
    //6000번은 api token 관련 에러
    API_TOKEN_EXCEPTION(6000, "api 토큰이 없습니다.")
    ;

    private final int code;
    private final String message;
}
