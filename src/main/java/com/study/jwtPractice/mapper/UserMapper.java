package com.study.jwtPractice.mapper;

import com.study.jwtPractice.model.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<UserDto> login(UserDto userDto);
}
