package com.ch.back.user.controller.response;

import com.ch.back.user.domain.User;

public record UserResponse(String username) {

    public static UserResponse of(User user) {
        return new UserResponse(user.getUsername());
    }
}
