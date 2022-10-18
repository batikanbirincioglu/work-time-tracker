package com.wtt.commondependencies.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private StatusCode statusCode;
    private int errorCode;
    private String description;
    private Object payload;
}