package com.ch.back.user.controller;

import com.ch.back.basic_setting.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        try {
            model.addAttribute("user", userDetails.getUser());
        } catch (NullPointerException e) {
            model.addAttribute("user", null);
        } finally {
            return "index";
        }
    }
}

