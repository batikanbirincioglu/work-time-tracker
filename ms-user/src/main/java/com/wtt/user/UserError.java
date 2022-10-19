package com.wtt.user;

import com.wtt.commondependencies.exception.BusinessError;

public enum UserError implements BusinessError {
    USER_NOT_FOUND("USER-1", "User could not be found."),
    PASSWORD_NOT_CORRECT("USER-2", "Password is not correct."),
    USERNAME_ALREADY_OWNED("USER-3", "This username is already being used.");

    private String errorCode;
    private String description;

    private UserError(String errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
