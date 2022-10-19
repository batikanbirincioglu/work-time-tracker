package com.wtt.timelog;

import com.wtt.commondependencies.exception.BusinessError;

public enum TimeLogError implements BusinessError {
    ALREADY_PUNCHED_IN_TODAY("TIMELOG-1", "You have already punched in today."),
    NO_PUNCHED_IN_TODAY("TIMELOG-2", "You have not punched in today so you can't punch out."),
    ALREADY_PUNCHED_OUT_TODAY("TIMELOG-3", "You have already punched out today.");

    private String errorCode;
    private String description;

    private TimeLogError(String errorCode, String description) {
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
