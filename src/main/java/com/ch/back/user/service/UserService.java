package com.ch.back.user.service;

import com.ch.back.support.error.CoreApiException;
import com.ch.back.support.error.ErrorType;
import com.ch.back.user.controller.request.SignupRequestDto;
import com.ch.back.user.controller.response.UsernameResponse;
import com.ch.back.user.domain.User;
import com.ch.back.user.domain.UserRoleEnum;
import com.ch.back.jwt.JwtUtils;
import com.ch.back.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Transactional(readOnly = true)
    public UsernameResponse checkAuth(HttpServletRequest request) {
        String bearerToken = jwtUtils.getTokenFromCookie(request);
        String token = jwtUtils.subStringToken(bearerToken);
        if (!jwtUtils.validationToken(token)) throw new CoreApiException(ErrorType.TOKEN_ERROR);
        return UsernameResponse.of(jwtUtils.getClaimFromToken(token).getSubject());
    }

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        if (userRepository.findByUsername(username).orElse(null) != null)
            throw new CoreApiException(ErrorType.BAD_REQUEST_ERROR);
        userRepository.save(new User(username, encodedPassword, UserRoleEnum.USER));
    }

    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        jwtUtils.clearCookie(request, response);
    }

}
