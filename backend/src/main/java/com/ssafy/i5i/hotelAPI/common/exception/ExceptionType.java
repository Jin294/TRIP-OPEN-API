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

    JWT_TOKEN_EXPIRED(4000, "토큰이 만료되었습니다."),
    NULL_POINT_EXCEPTION(4001, "데이터가 없습니다."),
    SORTED_TYPE_EXCEPTION(4002,"정렬 타입이 잘못되었습니다."),
    API_TOKEN_EXCEPTION(4003, "api 토큰이 유효하지 않습니다.")
    ;

    private final int code;
    private final String message;
}
