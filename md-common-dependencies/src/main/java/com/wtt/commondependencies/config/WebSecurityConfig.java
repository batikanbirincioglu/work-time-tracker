package com.wtt.commondependencies.config;

import com.wtt.commondependencies.filter.JwtAuthenticationFilter;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import java.nio.charset.StandardCharsets;

@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig implements ApplicationContextAware {
    private static final String[] WHITE_LISTED_URLS = {"/authenticate"};

    private ApplicationContext applicationContext;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(WHITE_LISTED_URLS).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private Filter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(applicationContext.getBean(JwtParser.class));
    }

    @Bean
    public JwtParser parser() {
        return Jwts.parser()
                .setSigningKey("ppZn8yl3AcIRqLe5KQ1t0M5nyv2ixpWQVUZ9TScTExEsIMHe0V7iX6xYR5NuFC6TOB01mrnFS58LNjJ1SlWbcR6DXiYIk7t"
                        .getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
