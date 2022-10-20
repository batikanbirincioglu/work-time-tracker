package com.wtt.user.service;

import com.wtt.commondependencies.dto.ResponseDto;
import com.wtt.commondependencies.dto.StatusCode;
import com.wtt.commondependencies.exception.BusinessException;
import com.wtt.user.dto.CreateUserRequestDto;
import com.wtt.user.entity.User;
import com.wtt.user.mapper.UserMapper;
import com.wtt.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Test
    public void test_createUser_succeeded() {
        when(userRepository.findByUsername(any())).thenReturn(null);
        when(userMapper.toEntity((CreateUserRequestDto) any())).thenReturn(new User());
        CreateUserRequestDto request = CreateUserRequestDto.builder().username("username-1").password("password-1").email("email@email.com").manager(false).name("name-1").build();

        ResponseDto responseDto = userService.createUser(request);

        assertEquals(StatusCode.SUCCESS, responseDto.getStatusCode());
    }

    @Test
    public void test_createUser_failedBecauseThereIsAlreadyUserWithSameUserName() {
        when(userRepository.findByUsername(eq("username-1"))).thenReturn(new User());
        CreateUserRequestDto request = CreateUserRequestDto.builder().username("username-1").password("password-1").email("email@email.com").manager(false).name("name-1").build();

        Assertions.assertThrows(BusinessException.class, () -> userService.createUser(request));
    }
}
