package com.wtt.user;

import com.wtt.commondependencies.config.JacksonConfig;
import com.wtt.commondependencies.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebSecurityConfig.class, JacksonConfig.class})
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
