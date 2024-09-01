package com.ch.back.user.service;

import com.ch.back.support.error.CoreApiException;
import com.ch.back.support.error.ErrorType;
import com.ch.back.support.response.ApiResponse;
import com.ch.back.user.controller.request.SignupRequestDto;
import com.ch.back.user.controller.response.UserResponse;
import com.ch.back.user.domain.User;
import com.ch.back.user.domain.UserRoleEnum;
import com.ch.back.basic_setting.jwt.JwtUtils;
import com.ch.back.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    public UserResponse getUserInfo(String username) {
        return UserResponse.of(userRepository.findByUsername(username).orElseThrow(() -> new CoreApiException(ErrorType.NOT_FOUND_ERROR)));
    }
}
