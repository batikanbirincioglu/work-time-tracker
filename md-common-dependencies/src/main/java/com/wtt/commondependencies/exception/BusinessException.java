package com.wtt.commondependencies.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private BusinessError businessError;

    public BusinessException(BusinessError businessError) {
        super(businessError.getString());
        this.businessError = businessError;
    }
}
