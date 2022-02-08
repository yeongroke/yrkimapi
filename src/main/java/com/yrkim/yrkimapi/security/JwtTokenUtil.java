package com.yrkim.yrkimapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -5575805848051132274L;

    @Value(value = "${yrkim.secret_key}")
    private String secret_key;

    // 토큰 유효 시간
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 60 * 60 * 24 * 7;  // 7일
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 5 * 60 * 60; // 5분

    public static final String ACCESS_TOKEN_NAME = "accessToken";
    public static final String REFRESH_TOKEN_NAME = "refreshToken";

    private final UserDetailServiceImpl userDetailService;

    public String extractUsername(String token) {
        return extractClaim(token , Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsByToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsByToken(String token) {
        return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doCreateToken(claims, username , ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String refreshGenerateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doCreateToken(claims, userDetails.getUsername() , REFRESH_TOKEN_EXPIRE_TIME);
    }

    private String doCreateToken(Map<String, Object> claims, String subject , long refreshExfireTime) {
        return Jwts.builder()
                .setClaims(claims) // claim 정보 - 사용자에 대한 값/속성. 회원을 구별 값 첨부
                .setSubject(subject) // 사용자 token 정보
                .setIssuedAt(new Date(System.currentTimeMillis())) // token 생성 날짜
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * refreshExfireTime)) // token 유효시간
                .signWith(SignatureAlgorithm.HS256, secret_key).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
