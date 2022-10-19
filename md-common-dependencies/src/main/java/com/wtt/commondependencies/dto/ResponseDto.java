package com.wtt.commondependencies.dto;

import com.wtt.commondependencies.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private StatusCode statusCode;
    private String errorCode;
    private String description;
    private T payload;

    public static <K> ResponseDto<K> from(StatusCode statusCode, K payload) {
        ResponseDto<K> responseDto = new ResponseDto<K>();
        responseDto.setStatusCode(statusCode);
        responseDto.setPayload(payload);
        return responseDto;
    }

    public static <K> ResponseDto<K> from(StatusCode statusCode, String errorCode, String description, K payload) {
        ResponseDto<K> responseDto = new ResponseDto<K>();
        responseDto.setStatusCode(statusCode);
        responseDto.setErrorCode(errorCode);
        responseDto.setDescription(description);
        responseDto.setPayload(payload);
        return responseDto;
    }

    public static <K> ResponseDto<K> from(StatusCode statusCode, K payload, BusinessException businessException) {
        return from(statusCode, businessException.getBusinessError().getErrorCode(), businessException.getBusinessError().getDescription(), payload);
    }
}