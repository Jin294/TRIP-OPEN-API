package com.ssafy.i5i.hotelAPI.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.i5i.hotelAPI.common.exception.CommonException;
import com.ssafy.i5i.hotelAPI.common.exception.ExceptionType;
import com.ssafy.i5i.hotelAPI.domain.user.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class ApiTokenCheckFilter implements Filter {
    private final TokenService tokenService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private String[] checkUrl = {
            "/api",
            "/api/1",
            "/api/*"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestURL = extractURL(httpServletRequest);
        String token = extractBearerToken(httpServletRequest);

        //url 검증 여부 확인. 검증 필요 없으면 넘어가고 검증 필요하면 체크
        if(!isTokenCheckPath(requestURL)) {
            chain.doFilter(request, response);
        }
        //토큰 유효성 체크, 유효한 토큰 아니면 예외처리
        if(!tokenService.checkValidToken(token)) {
            notValidToken(httpServletResponse);
            return;
        }

        //토큰 사용량 체크, 레디스 사용



        chain.doFilter(request, response);
    }

    private void notValidToken(HttpServletResponse httpServletResponse) throws IOException {
        log.error("토큰 정보가 db에 없습니다. 유효하지 않은 토큰입니다.");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        CommonException commonException = new CommonException(ExceptionType.API_TOKEN_EXCEPTION);
        String result = objectMapper.writeValueAsString(commonException);

        httpServletResponse.getWriter().write(result);
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

    private boolean isTokenCheckPath(String requestURI) {
        return PatternMatchUtils.simpleMatch(checkUrl, requestURI);
    }
}
