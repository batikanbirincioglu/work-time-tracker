package com.wtt.commondependencies.advice;

import com.wtt.commondependencies.dto.ResponseDto;
import com.wtt.commondependencies.dto.StatusCode;
import com.wtt.commondependencies.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {
    @ExceptionHandler(BusinessException.class)
    public ResponseDto handleBusinessException(BusinessException businessException) {
        log.error("{} occurred.", businessException.getClass().getSimpleName(), businessException);
        return ResponseDto.from(StatusCode.FAIL, null, businessException);
    }
}
