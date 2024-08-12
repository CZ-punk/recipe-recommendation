package com.ch.back.security;

import com.ch.back.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
                return;
            }

            Claims claims = jwtUtils.getClaimFromToken(token);
            try {
                setAuthentication(claims.getSubject());
                ;
            } catch (Exception e) {
                log.error(e.getMessage());
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
}
