package com.wtt.user.service;

import com.wtt.commondependencies.dto.ResponseDto;
import com.wtt.commondependencies.dto.StatusCode;
import com.wtt.commondependencies.exception.BusinessException;
import com.wtt.user.UserError;
import com.wtt.user.dto.CreateUserRequestDto;
import com.wtt.user.dto.UserDto;
import com.wtt.user.entity.User;
import com.wtt.user.mapper.UserMapper;
import com.wtt.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseDto<Void> createUser(CreateUserRequestDto request) {
        if (userRepository.findByUsername(request.getUsername()) == null) {
            User user = userMapper.toEntity(request);
            user.setEncodedPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            return ResponseDto.from(StatusCode.SUCCESS, null);
        } else {
            throw new BusinessException(UserError.USERNAME_ALREADY_OWNED);
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<UserDto> getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        UserDto userDto = userMapper.toDto(user);
        return ResponseDto.from(StatusCode.SUCCESS, userDto);
    }

    @Transactional
    public ResponseDto<Void> updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new BusinessException(UserError.USER_NOT_FOUND));
        User newUser = userMapper.toEntity(userDto);
        newUser.setId(id);
        newUser.setEncodedPassword(passwordEncoder.encode(userDto.getEncodedPassword()));
        userRepository.save(newUser);
        return ResponseDto.from(StatusCode.SUCCESS, null);
    }

    @Transactional
    public ResponseDto deleteUser(Long id) {
        userRepository.deleteById(id);
        return ResponseDto.from(StatusCode.SUCCESS, null);
    }

    @Transactional(readOnly = true)
    public ResponseDto getAllUsers() {
        List<UserDto> userDtos = userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseDto.from(StatusCode.SUCCESS, userDtos);
    }

    @Transactional(readOnly = true)
    public ResponseDto getAllManagers() {
        List<UserDto> managerDtos = userRepository.findAll()
                .stream()
                .filter(User::isManager)
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseDto.from(StatusCode.SUCCESS, managerDtos);
    }

    @Transactional(readOnly = true)
    public ResponseDto getAllEmployees() {
        List<UserDto> employeeDtos = userRepository.findAll()
                .stream()
                .filter(Predicate.not(User::isManager))
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseDto.from(StatusCode.SUCCESS, employeeDtos);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
