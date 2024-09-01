package com.ch.back.user.controller;

import com.ch.back.basic_setting.jwt.UserDetailsImpl;
import com.ch.back.support.response.ApiResponse;
import com.ch.back.user.controller.request.SignupRequestDto;
import com.ch.back.user.controller.response.UserResponse;
import com.ch.back.user.service.KakaoService;
import com.ch.back.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j(topic = "User Controller")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    @ResponseBody
    @GetMapping("/info")
    public ApiResponse<?> getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponse data = userService.getUserInfo(userDetails.getUsername());
        return ApiResponse.success(data);
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);
    }

    @GetMapping("/login/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        kakaoService.kakaoLogin(code, response);
        log.debug("카카오 콜백 메서드 수행");
        return "redirect:/";

    }
}
