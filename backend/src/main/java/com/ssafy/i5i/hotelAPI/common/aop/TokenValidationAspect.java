package com.ssafy.i5i.hotelAPI.common.aop;

import com.ssafy.i5i.hotelAPI.domain.user.service.TokenService;
import com.ssafy.i5i.hotelAPI.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class TokenValidationAspect {
    private final TokenService tokenService;
    private final UserService userService;

    //com.ssafy.i5i.hotelAPI.domain.hotel.controller
    @Before("execution(* com.ssafy.i5i.hotelAPI.domain..*.*(..)) && args(request, response, ..)")
    public void tokenCheck(HttpServletRequest request, HttpServletResponse response) {
        String tokenId = extractBearerToken(request);
        if(!tokenService.maxCheck(tokenId)) {
            throw new RuntimeException("토큰 횟수 초과");
        }
        else if(tokenService.checkValidToken(tokenId) && tokenService.maxCheck(tokenId)) {
            return;
        }

        if(userService.isValidToken(tokenId) == false) {
            throw new RuntimeException("401");
        }
        else {
            tokenService.saveToken(tokenId);
        }
    }

    @AfterReturning("execution(* com.ssafy.i5i.hotelAPI.domain..*.*(..)) && args(request, response, ..)")
    public void updateTokenCount(HttpServletRequest request, HttpServletResponse response) {
        String tokenId = extractBearerToken(request);
        tokenService.incrementTokenCount(tokenId);
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
