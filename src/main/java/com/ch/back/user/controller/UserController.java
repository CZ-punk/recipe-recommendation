package com.ch.back.user.controller;

import com.ch.back.user.service.KakaoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j(topic = "User Controller")
@RequestMapping("/api/user")
public class UserController {

    private final KakaoService kakaoService;

    @GetMapping("/signup")
    public String signup() {
        return "user/signup";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/login/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        kakaoService.kakaoLogin(code, response);
        return "redirect:/";
    }

}
