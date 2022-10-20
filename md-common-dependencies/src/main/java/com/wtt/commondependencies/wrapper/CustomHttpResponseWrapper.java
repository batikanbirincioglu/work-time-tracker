package com.wtt.commondependencies.wrapper;

import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;

public class CustomHttpResponseWrapper extends ContentCachingResponseWrapper {
    public CustomHttpResponseWrapper(HttpServletResponse response) {
        super(response);
    }
}
