package com.ch.back.controller;

import com.ch.back.dto.SignupRequestDto;
import com.ch.back.service.KakaoService;
import com.ch.back.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j(topic = "User Controller")
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;


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
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        userService.logout(request, response);
        return "index";
    }

    @GetMapping("/login/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        kakaoService.kakaoLogin(code, response);
        log.info("카카오 콜백 메서드 수행");
        return "redirect:/";

    }
}
