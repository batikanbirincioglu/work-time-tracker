package com.wtt.commondependencies.exception;

public interface BusinessError {
    public String getErrorCode();
    public String getDescription();

    default String getString() {
        return String.format("%s <--> %s", getErrorCode(), getDescription());
    }
}
