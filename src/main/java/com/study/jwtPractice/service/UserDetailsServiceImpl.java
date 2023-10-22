package com.study.jwtPractice.service;

import com.study.jwtPractice.model.UserDetailsDto;
import com.study.jwtPractice.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        UserDto userDto = UserDto.builder().userId(userId).build();

        // 사용자 정보가 존재하지 않는 경우
        if (userId == null || userId.equals("")) {

            return userService.login(userDto)
                    .map(ud -> new UserDetailsDto(ud, Collections.singleton(new SimpleGrantedAuthority(ud.getUserId()))))
                    .orElseThrow(() -> new AuthenticationServiceException(userId));
        }

        // 비밀번호가 맞지 않는 경우
        else {
            return userService.login(userDto)
                    .map(ud -> new UserDetailsDto(ud, Collections.singleton(new SimpleGrantedAuthority(ud.getUserId()))))
                    .orElseThrow(() -> new BadCredentialsException(userId));
        }
    }
}
