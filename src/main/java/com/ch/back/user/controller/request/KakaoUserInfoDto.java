package com.ch.back.user.controller.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserInfoDto {

    private Long id;
    private String username;

    public KakaoUserInfoDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
