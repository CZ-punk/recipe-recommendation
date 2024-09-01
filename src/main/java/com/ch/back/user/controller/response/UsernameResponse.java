package com.ch.back.user.controller.response;

import com.ch.back.user.domain.User;

public record UsernameResponse(String username) {

    public static UsernameResponse of(String username) {
        return new UsernameResponse(username);
    }
}
