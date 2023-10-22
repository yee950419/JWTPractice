package com.study.jwtPractice.common.responsemessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {

    private HttpStatus httpStatus;
    private String msg;
    private Map<String, Object> results;

}
