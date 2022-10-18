package com.wtt.user.mapper;

import com.wtt.user.dto.CreateUserRequest;
import com.wtt.user.dto.UserDto;
import com.wtt.user.dto.UserDto.UserDtoBuilder;
import com.wtt.user.entity.User;
import com.wtt.user.entity.User.UserBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-19T00:18:58+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.15 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.name( user.getName() );
        userDto.email( user.getEmail() );
        userDto.manager( user.isManager() );

        return userDto.build();
    }

    @Override
    public User toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.id( userDto.getId() );
        user.name( userDto.getName() );
        user.email( userDto.getEmail() );
        user.manager( userDto.isManager() );

        return user.build();
    }

    @Override
    public User toEntity(CreateUserRequest createUserRequest) {
        if ( createUserRequest == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.name( createUserRequest.getName() );
        user.email( createUserRequest.getEmail() );
        user.manager( createUserRequest.isManager() );

        return user.build();
    }
}
