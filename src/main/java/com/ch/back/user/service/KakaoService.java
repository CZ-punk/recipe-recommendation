package com.ch.back.user.service;

import com.ch.back.user.controller.request.KakaoUserInfoDto;
import com.ch.back.user.domain.PlatformEnum;
import com.ch.back.user.domain.User;
import com.ch.back.basic_setting.jwt.JwtUtils;
import com.ch.back.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "kakao service")
public class KakaoService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;
    @Value("${kakao.redirect.url}")
    private String redirectUrl;


    public void kakaoLogin(String code, HttpServletResponse response) {

        String kakaoAccessToken = getToken(code);
        KakaoUserInfoDto userInfo = getKakaoUserInfo(kakaoAccessToken);

        // 필요시 회원가입 + Jwt token 발급
        User user = registerKakaoUserIfNeeded(userInfo);
        String token = jwtUtils.createToken(user.getUsername(), user.getRole());
        jwtUtils.setTokenInCookie(token, response);
    }


    private String getToken(String code) {

        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoApiKey);
        body.add("redirect_uri", redirectUrl);
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(body);

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        try {
            JsonNode node = new ObjectMapper().readTree(response.getBody());
            return node.get("access_token").toString();
        } catch (JsonProcessingException e) {
            log.error("json parsing error", e);
            throw new RuntimeException(e);
        }
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) {

        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> request = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        try {
            JsonNode node = new ObjectMapper().readTree(response.getBody());
            long id = node.get("id").asLong();
            String username = node.get("kakao_account").get("profile").get("nickname").asText();

            log.info("kakao user info >>>> id: {}, nickname: {}", id, username);
            return new KakaoUserInfoDto(id, username);

        } catch (JsonProcessingException e) {
            log.error("json parsing error", e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    protected User registerKakaoUserIfNeeded(KakaoUserInfoDto userInfo) {

        Long kakaoId = userInfo.getId();
        User user = userRepository.findByPlatformId(kakaoId).orElse(null);

        if (user == null) {
            String username = userInfo.getUsername();
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            try {
                User kakaoUser = new User(kakaoId, username, encodedPassword, PlatformEnum.KAKAO);
                userRepository.save(kakaoUser);
                return kakaoUser;
            } catch (Exception e) {
                log.error("Kakao Login Failure");
            }
        }
        return user;
    }
}
