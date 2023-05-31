package io.mykim.projectboardadmin.config.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import io.mykim.projectboardadmin.adminuser.repository.AdminUserRepository;
import io.mykim.projectboardadmin.config.security.jwt.enums.TokenType;
import io.mykim.projectboardadmin.config.security.dto.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 토큰 생성, 토큰 복호화 및 정보 추출, 토큰 유효성 검증의 기능이 구현된 클래스이다.
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {
    private SecretKey key;
    private final AdminUserRepository adminUserRepository;

    @PostConstruct
    public void init() {
        byte[] decodeKey = Base64.getDecoder().decode(JwtProperties.SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(decodeKey);
    }

    // issue jwt token : access or refresh
    public String generateToken(TokenType tokenType, Long adminUserId) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(adminUserId));
        long time = tokenType.equals(TokenType.ACCESS) ? Duration.ofMinutes(JwtProperties.ACCESS_TOKEN_VALID_MINUTE).toMillis() : Duration.ofDays(JwtProperties.REFRESH_TOKEN_VALID_DAY).toMillis();

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

    // check token validation
    public boolean validateToken(final String token, TokenType tokenType) {
        try {
            getClaims(token);
            log.info("This token is valid. tokenType={}", tokenType);
            return true;
        }catch (SignatureException e) {
            log.error("Invalid JWT signature: {}, tokenType={}", e.getMessage(), tokenType);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}, tokenType={}", e.getMessage(), tokenType);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}, tokenType={}", e.getMessage(), tokenType);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}, tokenType={}", e.getMessage(), tokenType);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}, tokenType={}", e.getMessage(), tokenType);
        }

        return false;
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

    // get token(access, refresh) from request header
    public String getJwtFromHeader(final HttpServletRequest request, TokenType tokenType){
        String jwtHeader = request.getHeader(tokenType.equals(TokenType.ACCESS) ? JwtProperties.HEADER_STRING_ACCESS_TOKEN : JwtProperties.HEADER_STRING_REFRESH_TOKEN);
        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return null;
        }

        return jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");
    }

    // get token(access, refresh) from cookie
    public String getJwtFromCookie(final HttpServletRequest request, TokenType tokenType){
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0) {
            return null;
        }

        Cookie jwtCookie = Arrays.stream(cookies)
                                    .filter(cookie -> cookie.getName().equals(tokenType.equals(TokenType.ACCESS) ? JwtProperties.COOKIE_NAME_ACCESS_TOKEN : JwtProperties.COOKIE_NAME_REFRESH_TOKEN))
                                    .findFirst()
                                    .orElse(null);

        if(jwtCookie == null) {
            return null;
        }

        return jwtCookie.getValue();
    }

    public void setJwtInCookie(final String token, TokenType tokenType, HttpServletResponse response) {
        String cookieName = tokenType.equals(TokenType.ACCESS) ? JwtProperties.COOKIE_NAME_ACCESS_TOKEN : JwtProperties.COOKIE_NAME_REFRESH_TOKEN;
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600*24*30);
        response.addCookie(cookie);
    }

    public void initJwtCookie(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키 초기화 : delete cookie
        Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME_ACCESS_TOKEN, "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }


    // SecurityContext 에 Authentication 객체를 저장
    public void setAuthentication(String accessToken) {
        Long adminUserId = Long.valueOf(getSubjectFromClaims(accessToken));

        AdminUser adminUser = adminUserRepository.findById(adminUserId).orElseThrow(() -> new UsernameNotFoundException("없는 사용자입니다."));
        PrincipalDetail principalDetail = new PrincipalDetail(adminUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetail, null, principalDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
