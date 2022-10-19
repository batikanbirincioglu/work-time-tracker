package com.wtt.user.service;

import com.wtt.commondependencies.constants.Constants;
import com.wtt.user.dto.AuthenticateRequestDto;
import com.wtt.user.dto.AuthenticateResponseDto;
import com.wtt.user.dto.UserDto;
import com.wtt.user.entity.User;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public AuthenticateResponseDto authenticate(AuthenticateRequestDto request) {
        String username = request.getUsername();
        User user = userService.getUserByUsername(username);
        String rawPassword = request.getPassword();
        String encodedPassword = null;
        if (passwordEncoder.matches(rawPassword, encodedPassword)) {
            Map<String, Object> header = new HashMap<>();
            header.put(Header.TYPE, Header.JWT_TYPE);
            String jwtToken = Jwts.builder().setHeader(header)
                    .claim(Constants.ID, user.getId())
                    .claim(Constants.NAME, user.getName())
                    .claim(Constants.EMAIL, user.getEmail())
                    .claim(Constants.MANAGER, user.isManager())
                    .setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS512, Keys.hmacShaKeyFor(Constants.JWT_SECRET.getBytes(StandardCharsets.UTF_8)))
                    .compact();
            return AuthenticateResponseDto.builder().jwtToken(jwtToken).build();
        } else {
            return AuthenticateResponseDto.builder().build();
        }
    }
}
