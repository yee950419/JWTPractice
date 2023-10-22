package com.study.jwtPractice.controller;

import com.study.jwtPractice.common.responsemessage.ResponseMessage;
import com.study.jwtPractice.model.UserDto;
import com.study.jwtPractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class AuthController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Map<String, Object> responseMap = new HashMap<>();

        Optional<UserDto> user = userService.login(userDto);

        responseMap.put("result", user);
        return ResponseEntity.ok().body(new ResponseMessage(HttpStatus.OK, "유저 조회 성공", responseMap));
    }
}
