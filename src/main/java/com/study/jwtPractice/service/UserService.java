package com.study.jwtPractice.service;

import com.study.jwtPractice.model.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> login(UserDto userDto);
}
