package com.wtt.user;

import com.wtt.commondependencies.config.JacksonConfig;
import com.wtt.commondependencies.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Import({WebSecurityConfig.class, JacksonConfig.class})
@Configuration
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String defaultEncodingId = "argon2";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(defaultEncodingId, new Argon2PasswordEncoder(16, 32, 8, 1 << 16, 4));
        return new DelegatingPasswordEncoder(defaultEncodingId, encoders);
    }
}
