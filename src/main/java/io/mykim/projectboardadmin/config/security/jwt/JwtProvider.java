package io.mykim.projectboardadmin.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.mykim.projectboardadmin.config.security.jwt.enums.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 토큰 생성, 토큰 복호화 및 정보 추출, 토큰 유효성 검증의 기능이 구현된 클래스이다.
 */

@Slf4j
@Component
public class JwtProvider {
    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] decodeKey = Base64.getDecoder().decode(JwtProperties.SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(decodeKey);
    }


    // issue jwt token : access or refresh
    public String issueToken(TokenType tokenType, Long adminUserId) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(adminUserId));
        long time = tokenType.equals(TokenType.ACCESS) ? Duration.ofMinutes(30).toMillis() : Duration.ofDays(30).toMillis();

        Date tokenIssuedDate = new Date();
        Date tokenExpirationDate = new Date(tokenIssuedDate.getTime() + time);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(tokenIssuedDate)
                .setAudience(JwtProperties.TOKEN_AUDIENCE)
                .setExpiration(tokenExpirationDate)
                .signWith(key)
                .compact();
    }

    // check valid token expireDate
    public boolean validateTokenDate(final String token){
        try{
            Jws<Claims> claims = getClaims(token.trim());
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception e){
            return false;
        }
    }

    // get Claims
    public Jws<Claims> getClaims(final String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(token);
    }

    // get Subject from Claims
    public String getSubjectFromClaims(final String token){
        Jws<Claims> claims = getClaims(token);
        return claims.getBody().getSubject();
    }

    // get jwt from Header
    public String getJwtFromHeader(final HttpServletRequest request){
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return null;
        }

        return jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");
    }



}
