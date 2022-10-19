package com.wtt.timelog;

import com.wtt.commondependencies.config.CommonConfig;
import com.wtt.commondependencies.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebSecurityConfig.class, CommonConfig.class})
public class TimeLogApplication {
    public static void main(String[] args) {
        SpringApplication.run(TimeLogApplication.class, args);
    }
}
