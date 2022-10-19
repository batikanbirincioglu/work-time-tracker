package com.wtt.user.controller;

import com.wtt.user.dto.AuthenticateRequestDto;
import com.wtt.user.dto.AuthenticateResponseDto;
import com.wtt.user.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticateResponseDto authenticate(AuthenticateRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
