package com.wtt.user.mapper;

import com.wtt.user.dto.CreateUserRequestDto;
import com.wtt.user.dto.UserDto;
import com.wtt.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    User toEntity(CreateUserRequestDto createUserRequestDto);
}
