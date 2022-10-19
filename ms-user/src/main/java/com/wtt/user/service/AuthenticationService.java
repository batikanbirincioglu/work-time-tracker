package com.wtt.user.service;

import com.wtt.commondependencies.constants.Constants;
import com.wtt.commondependencies.dto.ResponseDto;
import com.wtt.commondependencies.dto.StatusCode;
import com.wtt.commondependencies.exception.BusinessException;
import com.wtt.user.UserError;
import com.wtt.user.dto.AuthenticateRequestDto;
import com.wtt.user.dto.AuthenticateResponseDto;
import com.wtt.user.entity.User;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
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

    public ResponseDto<AuthenticateResponseDto> authenticate(AuthenticateRequestDto request) {
        String username = request.getUsername();
        String rawPassword = request.getPassword();
        User user = userService.getUserByUsername(username);
        if (user != null) {
            String encodedPassword = user.getEncodedPassword();
            if (passwordEncoder.matches(rawPassword, encodedPassword)) {
                Map<String, Object> header = new HashMap<>();
                header.put(Header.TYPE, Header.JWT_TYPE);
                String jwtToken = Jwts.builder().setHeader(header)
                        .claim(Constants.ID, user.getId())
                        .claim(Constants.NAME, user.getName())
                        .claim(Constants.EMAIL, user.getEmail())
                        .claim(Constants.MANAGER, user.isManager())
                        .claim(Constants.USERNAME, user.getUsername())
                        .claim(Constants.ENCODED_PASSWORD, user.getEncodedPassword())
                        .setIssuedAt(new Date())
                        .signWith(
                                SignatureAlgorithm.HS256,
                                TextCodec.BASE64.decode(Constants.JWT_SECRET))
                        .compact();
                return ResponseDto.from(StatusCode.SUCCESS, AuthenticateResponseDto
                        .builder()
                        .jwtToken(jwtToken)
                        .build());
            } else {
                throw new BusinessException(UserError.PASSWORD_NOT_CORRECT);
            }
        } else {
            throw new BusinessException(UserError.USER_NOT_FOUND);
        }
    }
}
