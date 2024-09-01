package com.ch.back.user.controller;

import com.ch.back.support.response.ApiResponse;
import com.ch.back.user.controller.request.SignupRequestDto;
import com.ch.back.user.controller.response.UsernameResponse;
import com.ch.back.user.service.KakaoService;
import com.ch.back.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "Rest User Controller")
@RequestMapping("/api/user")
public class RestUserController {

    private final UserService userService;

    @GetMapping("/check-auth")
    public ApiResponse<?> checkAuth(HttpServletRequest request) {
        UsernameResponse data = userService.checkAuth(request);
        return ApiResponse.success(data);
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);
        return ApiResponse.success();
    }

    @PostMapping("/signup")
    public ApiResponse<?> signup(@ModelAttribute SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ApiResponse.success();
    }

}
