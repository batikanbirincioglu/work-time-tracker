package com.wtt.commondependencies.dto;

public enum StatusCode {
    SUCCESS(0),
    FAIL(-1);

    private int value;

    private StatusCode(int value) {
        this.value = value;
    }
}
