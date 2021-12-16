package com.server.user.common.security;

import com.server.user.users.domain.UserRole;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value(value = "secret.key")
    private String secretKey;

    private final long ACCESS_TOKEN_EXPIRED_TIME = 60 * 60 * 1000L;
    private final long REFRESH_TOKEN_EXPIRED_TIME = 2 * 24 * 60 * 60 * 1000L;

    @PostConstruct
    private void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 엑세스 토큰 생성
     * @param email
     * @param userRole
     * @return String
     */
    public String createAccessToken(String email, UserRole userRole) {
        Date currentTime = new Date();
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", userRole.name());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentTime)
                .setExpiration(new Date(currentTime.getTime() + ACCESS_TOKEN_EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * 리프레시 토큰 생성
     * @return String
     */
    public String createRefreshToken() {
        Date currentTime = new Date();
        return Jwts.builder()
                .setIssuedAt(currentTime)
                .setExpiration(new Date(currentTime.getTime() + REFRESH_TOKEN_EXPIRED_TIME))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * token 형식 검증 및 만료기간 검증
     * @param token
     * @return boolean
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(Exception e) {
            return false;
        }
    }

    /**
     * 토큰 주인 확인
     * @param token
     * @return String
     */
    public String getUserEmail(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * 토큰 권한 확인
     * @param token
     * @return String
     */
    public String getUserRole(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("role")
                .toString();
    }


}
