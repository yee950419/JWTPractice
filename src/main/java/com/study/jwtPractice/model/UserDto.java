package com.study.jwtPractice.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

    private Long userSq;
    private String userId;
    private String userPw;
    private String userName;
    private String userStatus;

    @Builder
    public UserDto(Long userSq, String userId, String userPw, String userName, String userStatus) {
        this.userSq = userSq;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userStatus = userStatus;
    }
}
