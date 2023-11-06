package com.ssafy.i5i.hotelAPI.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
import com.ssafy.i5i.hotelAPI.domain.user.service.TokenService;
import com.ssafy.i5i.hotelAPI.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class TokenValidationAspect {
    private final ObjectMapper objectMapper;

    private final TokenService tokenService;
    private final UserService userService;

    //com.ssafy.i5i.hotelAPI.domain.hotel.controller
    @Before("execution(* com.ssafy.i5i.hotelAPI.domain..*.*(..)) && args(request, response, ..)")
    public void tokenCheck(HttpServletRequest request, HttpServletResponse response) {
        String tokenId = extractBearerToken(request);

    }


    private void notValidToken(HttpServletResponse response, HttpServletRequest request) throws IOException {
        log.error("토큰 정보가 db에 없습니다. 유효하지 않은 토큰입니다.");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        CommonException commonException = new CommonException(ExceptionType.API_TOKEN_EXCEPTION);
        String result = objectMapper.writeValueAsString(commonException);

        response.getWriter().write(result);
    }

    // HttpServletRequest를 이용하여 현재 요청의 URL을 추출합니다.
    private String extractURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        if (queryString != null) {
            requestURL.append("?").append(queryString);
        }
        return requestURL.toString();
    }

    // HttpServletRequest를 사용하여 Bearer 토큰을 추출하는 메서드
    private String extractBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // "Bearer " 다음의 문자열이 토큰 값.
            String token = authorizationHeader.substring(7);
            return token;
        }

        return null; // Bearer 토큰을 찾을 수 없는 경우 null을 반환
    }
}
