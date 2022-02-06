package com.yrkim.yrkimapi.utils;

import com.yrkim.yrkimapi.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class CookieUtil {

    private final JwtTokenUtil jwtTokenUtil;

    public Cookie createCookie(String cookieName, String value, int age){
        Cookie token = new Cookie(cookieName,value);
        token.setHttpOnly(true); // 쿠키로 클라이언트의 스크립트에 접근을 막는다, 또는 악성 스크립트(XSS 공격)에 의한 위험을 완화함
        token.setMaxAge(age); // 쿠키 유효기간 설정
        token.setPath("/"); // 모든 경로에서 접근 설정 옵션
        return token;
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName){
        final Cookie[] cookies = req.getCookies();

        if(cookies==null) {
            return null;
        }

        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }
        return null;
    }

    /*public Cookie AccessToken(String subject) {
        final String createJwt = jwtTokenUtil.generateToken(subject);
        Cookie accessToken = this.createCookie(
                jwtTokenUtil.ACCESS_TOKEN_NAME
                , createJwt
                , (int) jwtTokenUtil.ACCESS_TOKEN_EXPIRE_TIME
        );
        return accessToken;
    }*/
}
