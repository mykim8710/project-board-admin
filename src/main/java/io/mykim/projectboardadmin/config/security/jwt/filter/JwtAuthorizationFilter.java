package io.mykim.projectboardadmin.config.security.jwt.filter;


// Authorization :  권한부여, 액세스 제어로 사용자가 읽기, 수정, 삭제를 허용하는지 여부를 확인하는 것, 사용자의 신원이 성공적으로 인증 된 후에 발생

import io.mykim.projectboardadmin.config.security.jwt.JwtProperties;
import io.mykim.projectboardadmin.config.security.jwt.JwtProvider;
import io.mykim.projectboardadmin.config.security.jwt.enums.TokenType;
import io.mykim.projectboardadmin.config.security.jwt.service.JwtRefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    // jwt 토큰 검증이 필요없는 url list
    private static final List<String> EXCLUDED_URLS = List.of("/", "/sign-out", "/images/**", "/css/**", "/js/**");
    private JwtProvider jwtProvider;
    private JwtRefreshTokenService jwtRefreshTokenService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider, JwtRefreshTokenService jwtRefreshTokenService) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
        this.jwtRefreshTokenService = jwtRefreshTokenService;
    }

    /**
     * 인증이나 권한이 필요한 url요청 시 이 필터를 타게됨
     * => jwt 토큰 검증이 필요한 api, url 요청 시 작동
     * => jwt 토큰 검증이 필요없는 api, url 설정가능
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JwtAuthorizationFilter 동작 > jwt 유효성체크");

        // access token 검증
        final String accessToken = jwtProvider.getJwtFromCookie(request, TokenType.ACCESS);

        if(StringUtils.hasText(accessToken)) {
            if(jwtProvider.validateToken(accessToken, TokenType.ACCESS)) {
                jwtProvider.setAuthentication(accessToken);
            } else {
                // access token이 유효하지 않다면
                final String refreshToken = jwtProvider.getJwtFromCookie(request, TokenType.REFRESH);

                // refresh token이 존재하고 유효하다면
                if(StringUtils.hasText(refreshToken) && jwtRefreshTokenService.validateRefreshToken(refreshToken)) {
                    // 새로운 access token 발급 및 Header Setting
                    Long adminUserId = Long.valueOf(jwtProvider.getSubjectFromClaims(refreshToken));
                    String newAccessToken = jwtProvider.generateToken(TokenType.ACCESS, adminUserId);
                    response.setHeader(JwtProperties.HEADER_STRING_ACCESS_TOKEN, newAccessToken);
                    jwtProvider.setAuthentication(newAccessToken);
                }
            }
        }

        chain.doFilter(request, response);
    }

    // jwt 토큰 검증이 필요없는 url 추가
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return EXCLUDED_URLS.stream()
                            .anyMatch(excludeUrl -> antPathMatcher.match(excludeUrl, request.getServletPath()));
    }
}
