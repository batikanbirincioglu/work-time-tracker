package com.wtt.user;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties
@Configuration
@Data
public class UserProperties {
    private AdminConfig adminConfig;

    @Data
    public static class AdminConfig {
        private String name;
        private String email;
        private boolean manager;
        private String username;
        private String password;
    }
}
