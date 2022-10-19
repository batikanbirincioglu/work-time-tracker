package com.wtt.commondependencies.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusCode {
    SUCCESS(0),
    FAIL(-1);

    private int value;

    private StatusCode(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }
}
