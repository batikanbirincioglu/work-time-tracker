package com.wtt.user;

import com.wtt.commondependencies.config.CommonConfig;
import com.wtt.commondependencies.config.MethodSecurityConfig;
import com.wtt.commondependencies.config.WebSecurityConfig;
import com.wtt.user.dto.CreateUserRequestDto;
import com.wtt.user.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Import({WebSecurityConfig.class, CommonConfig.class, MethodSecurityConfig.class})
@Configuration
public class UserApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(UserApplication.class, args);
        createAdminUser(context);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String defaultEncodingId = "argon2";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(defaultEncodingId, new Argon2PasswordEncoder(16, 32, 8, 1 << 16, 4));
        return new DelegatingPasswordEncoder(defaultEncodingId, encoders);
    }

    private static void createAdminUser(ApplicationContext context) {
        UserService userService = context.getBean(UserService.class);
        UserProperties userProperties = context.getBean(UserProperties.class);
        UserProperties.AdminConfig adminConfig = userProperties.getAdminConfig();
        userService.createUser(CreateUserRequestDto
                .builder()
                .name(adminConfig.getName())
                .email(adminConfig.getEmail())
                .manager(adminConfig.isManager())
                .username(adminConfig.getUsername())
                .password(adminConfig.getPassword())
                .build());
    }
}
