package com.wtt.commondependencies.filter;

import com.wtt.commondependencies.wrapper.CustomHttpRequestWrapper;
import com.wtt.commondependencies.wrapper.CustomHttpResponseWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class GlobalControllerLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        CustomHttpRequestWrapper requestWrapper = new CustomHttpRequestWrapper(request);
        CustomHttpResponseWrapper responseWrapper = new CustomHttpResponseWrapper(response);
        log.info("Request is being logged --> uri: {} parameters: {} body: {}", requestWrapper.getRequestURI(), requestWrapper.getParameterMap(), requestWrapper.getBody());
        filterChain.doFilter(requestWrapper, responseWrapper);
        String responseBody = new String(responseWrapper.getContentAsByteArray());
        responseWrapper.copyBodyToResponse();
        log.info("Response is being logged --> status : {} body:{}", responseWrapper.getStatus(), responseBody);
    }
}
