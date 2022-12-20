package com.spring.gugu.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 프론트 측에서 요청 헤더에 토큰을 넣어 보내면 이 필터가 검증해 줄 것이다
@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtHeader = ((HttpServletRequest)request).getHeader(JwtProperties.HEADER_STRING);
        System.out.println("JwtRequestFilter 진입");

        // header 가 정상적인 형식인지 확인
        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // jwt 토큰을 검증해서 정상적인 사용자인지 확인
        String token = jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");

        Long userCode = null;
        String accessToken = null;
        String refreshToken = null;

        try {
            userCode = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                    .getClaim("id").asLong();
            accessToken = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
            		.getClaim("access_token").asString();
            refreshToken = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
            		.getClaim("refresh_token").asString();

        } catch (TokenExpiredException e) {
            e.printStackTrace();
            request.setAttribute(JwtProperties.HEADER_STRING, "토큰이 만료되었습니다.");
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            request.setAttribute(JwtProperties.HEADER_STRING, "유효하지 않은 토큰입니다.");
        }

        request.setAttribute("userCode", userCode);
        request.setAttribute("accessToken", accessToken);
        request.setAttribute("refreshToken", accessToken);

        filterChain.doFilter(request, response);
    }
}