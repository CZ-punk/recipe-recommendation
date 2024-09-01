package com.ch.back.jwt;

import com.ch.back.support.error.ErrorType;
import com.ch.back.support.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "Jwt Authorization Filter = Token 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = jwtUtils.getTokenFromCookie(request);
        if (StringUtils.hasText(bearerToken)) {
            String token = bearerToken.substring(7);
            if (!jwtUtils.validationToken(token)) {
                log.error("Not Valid Token");
                handleAuthException(request, response);
                return;
            }

            Claims claims = jwtUtils.getClaimFromToken(token);
            try {
                setAuthentication(claims.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                handleServerException(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String username) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        securityContext.setAuthentication(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    private Authentication createAuthentication(String username) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private void handleAuthException(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error("유효하지 않은 토큰. 쿠키를 삭제하고 로그인 페이지로 리다이렉트합니다.");

        jwtUtils.clearCookie(request, response);
        response.sendRedirect("/api/user/login");
    }

    private void handleServerException(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.error("유효하지 않은 토큰. 쿠키를 삭제하고 로그인 페이지로 리다이렉트합니다.");

        jwtUtils.clearCookie(request, response);
        response.sendRedirect("/api/user/login");
    }

}
