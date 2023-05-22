package io.mykim.projectboardadmin.config.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import io.mykim.projectboardadmin.adminuser.repository.AdminUserRepository;
import io.mykim.projectboardadmin.config.security.dto.PrincipalDetail;
import io.mykim.projectboardadmin.config.security.jwt.enums.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public boolean validateToken(final String token) {
        try {
            getClaims(token);
            log.info("This token is valid.");
            return true;
        }catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
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

    // get access token from request header
    public String getJwtFromHeader(final HttpServletRequest request, TokenType tokenType){
        String jwtHeader = request.getHeader( tokenType.equals(TokenType.ACCESS) ? JwtProperties.HEADER_STRING_ACCESS_TOKEN : JwtProperties.HEADER_STRING_REFRESH_TOKEN);
        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return null;
        }

        return jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");
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
