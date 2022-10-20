package com.wtt.commondependencies.constants;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String MANAGER = "manager";
    public static final String USERNAME = "username";
    public static final String ENCODED_PASSWORD = "encodedPassword";
    public static final String JWT_SECRET = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    public static final String TOKEN_CREATED_DATE = "tokenCreatedDate";
    public static final DateTimeFormatter TOKEN_CREATED_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final long TOKEN_EXPIRY_IN_MINUTES = 15L;
}
