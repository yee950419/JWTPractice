package com.study.jwtPractice.service;

import com.study.jwtPractice.mapper.UserMapper;
import com.study.jwtPractice.model.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;

    @Override
    public Optional<UserDto> login(UserDto userDto) {

        return userMapper.login(userDto);
    }
}
