package com.ssafy.i5i.hotelAPI.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
import com.ssafy.i5i.hotelAPI.domain.user.service.TokenService;
import com.ssafy.i5i.hotelAPI.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class ApiTokenCheckFilter implements Filter {
    private final TokenService tokenService;
    private final UserService userService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private String[] checkUrl = {
            "/api/**"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestURI = httpServletRequest.getRequestURI();
        String token = extractBearerToken(httpServletRequest);
        log.info("filter check, url = {}", requestURI);
        log.info("filter check, token = {}", token);
        //url 검증 여부 확인. 검증 필요 없으면 넘어가고 검증 필요하면 체크
        if(!isTokenCheckPath(requestURI)) {
            chain.doFilter(request, response);
            return;
        }
        //토큰 유효성 체크, 유효한 토큰 아니면 예외처리
        //토큰 사용량 체크, 레디스 사용
        if (token == null || !checkTokenValid(token)) {
            log.error("invalid token");
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.getWriter().write("access denied");
            return;
        }
        // token 요청 횟수 증가
        tokenService.incrementTokenCount(token);
        chain.doFilter(request, response);
    }

    //token 유효성 확인
    private boolean checkTokenValid(String token) {
        if(!tokenService.maxCheck(token)) return false;
        if(tokenService.checkValidToken(token)) return false;
        if(!userService.isValidToken(token)) return false;
        tokenService.saveToken(token);
        return true;
    }

    // HttpServletRequest에서 Bearer 토큰을 추출하는 메서드
    private String extractBearerToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // "Bearer " 다음의 문자열이 토큰 값.
            String token = authorizationHeader.substring(7);
            return token;
        }
        // Bearer 토큰을 찾을 수 없는 경우 null을 반환
        return null;
    }

    //토큰 검사 경로 확인
    private boolean isTokenCheckPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(checkUrl, requestURI);
    }
}
