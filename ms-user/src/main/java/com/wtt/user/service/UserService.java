package com.wtt.user.service;

import com.wtt.commondependencies.dto.ResponseDto;
import com.wtt.commondependencies.dto.StatusCode;
import com.wtt.user.dto.CreateUserRequest;
import com.wtt.user.dto.UserDto;
import com.wtt.user.entity.User;
import com.wtt.user.mapper.UserMapper;
import com.wtt.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public ResponseDto createUser(CreateUserRequest createUserRequest) {
        User user = userMapper.toEntity(createUserRequest);
        userRepository.save(user);
        UserDto userDto = userMapper.toDto(user);
        return ResponseDto.builder().statusCode(StatusCode.SUCCESS).payload(userDto).build();
    }

    @Transactional(readOnly = true)
    public ResponseDto getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        UserDto userDto = userMapper.toDto(user);
        return ResponseDto.builder().statusCode(StatusCode.SUCCESS).payload(userDto).build();
    }

    @Transactional
    public ResponseDto updateUser(Long id, UserDto userDto) {
        ResponseDto responseDto = ResponseDto.builder().build();
        userRepository.findById(id).ifPresentOrElse(user -> {
            User newUser = userMapper.toEntity(userDto);
            newUser.setId(id);
            userRepository.save(newUser);
            responseDto.setStatusCode(StatusCode.SUCCESS);
        }, () -> {
            responseDto.setStatusCode(StatusCode.FAIL);
            responseDto.setDescription("No user with such id");
        });
        return responseDto;
    }

    @Transactional
    public ResponseDto deleteUser(Long id) {
        userRepository.deleteById(id);
        return ResponseDto.builder().statusCode(StatusCode.SUCCESS).build();
    }

    @Transactional(readOnly = true)
    public ResponseDto getAllUsers() {
        List<UserDto> userDtos = userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseDto.builder().statusCode(StatusCode.SUCCESS).payload(userDtos).build();
    }

    @Transactional(readOnly = true)
    public ResponseDto getAllManagers() {
        List<UserDto> managerDtos = userRepository.findAll()
                .stream()
                .filter(User::isManager)
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseDto.builder().statusCode(StatusCode.SUCCESS).payload(managerDtos).build();
    }

    @Transactional(readOnly = true)
    public ResponseDto getAllEmployees() {
        List<UserDto> employeeDtos = userRepository.findAll()
                .stream()
                .filter(Predicate.not(User::isManager))
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseDto.builder().statusCode(StatusCode.SUCCESS).payload(employeeDtos).build();
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
