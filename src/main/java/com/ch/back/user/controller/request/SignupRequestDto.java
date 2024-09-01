package com.ch.back.user.controller.request;

import lombok.Data;

@Data
public class SignupRequestDto {

    private String username;
    private String password;
}
