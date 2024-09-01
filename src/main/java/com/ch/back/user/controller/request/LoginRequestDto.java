package com.ch.back.user.controller.request;

import lombok.Data;

@Data
public class LoginRequestDto {

    private String username;
    private String password;
}
