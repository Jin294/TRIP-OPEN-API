package com.sch.sch_elasticsearch.interceptor;

import com.sch.sch_elasticsearch.exception.CommonException;
import com.sch.sch_elasticsearch.exception.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CheckParameterInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String paramValue = request.getParameter("maxResults");
        if (paramValue != null && Integer.parseInt(paramValue) > 100) {
            throw new CommonException(ExceptionType.INTERCEPTOR_TOO_MANY_MAX_RESULTS);
        }
        return true;
    }

    //postHandle 필요 시 추가할 것
}
