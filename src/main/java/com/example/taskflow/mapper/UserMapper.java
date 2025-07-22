package com.example.taskflow.mapper;

import com.example.taskflow.dto.UserDto;
import com.example.taskflow.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDto toDto(User user){
        UserDto userDto = new UserDto();

        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setCreatedAt(user.getCreatedAt());

        return userDto;
    }
}
