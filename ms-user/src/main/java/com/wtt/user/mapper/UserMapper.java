package com.wtt.user.mapper;

import com.wtt.user.dto.CreateUserRequest;
import com.wtt.user.dto.UserDto;
import com.wtt.user.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    User toEntity(CreateUserRequest createUserRequest);
}
