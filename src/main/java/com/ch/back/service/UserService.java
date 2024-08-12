package com.ch.back.service;

import com.ch.back.dto.LoginRequestDto;
import com.ch.back.dto.SignupRequestDto;
import com.ch.back.entity.User;
import com.ch.back.entity.UserRoleEnum;
import com.ch.back.jwt.JwtUtils;
import com.ch.back.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    public void signup(SignupRequestDto requestDto) {

        String username = requestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new IllegalArgumentException("duplicated username!");
        }
        User saveUser = new User(username, encodedPassword, UserRoleEnum.USER);
        userRepository.save(saveUser);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        jwtUtils.clearCookie(request, response);
    }
}
