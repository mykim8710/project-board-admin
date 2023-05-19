package io.mykim.projectboardadmin.config.security.handler;

import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import io.mykim.projectboardadmin.config.security.dto.PrincipalDetail;
import io.mykim.projectboardadmin.config.security.jwt.JwtProperties;
import io.mykim.projectboardadmin.config.security.jwt.JwtProvider;
import io.mykim.projectboardadmin.config.security.jwt.entity.JwtRefreshToken;
import io.mykim.projectboardadmin.config.security.jwt.enums.TokenType;
import io.mykim.projectboardadmin.config.security.jwt.repository.JwtRefreshTokenRepository;
import io.mykim.projectboardadmin.config.security.jwt.service.JwtRefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final JwtRefreshTokenService jwtRefreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Sign-in Success");

        // sign-in 실패 했었을 때, session에 에러 지우기
        clearAuthenticationAttributes(request);

        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
        AdminUser adminUser = principalDetail.getAdminUser();
        Long userId = adminUser.getId();

        // jwt logic
        // create token - access, refresh
        final String accessToken = jwtProvider.issueToken(TokenType.ACCESS, userId);
        final String refreshToken = jwtProvider.issueToken(TokenType.REFRESH, userId);

        // refresh Token save or update
        jwtRefreshTokenService.insertOrUpdateRefreshToken(adminUser.getUsername(), refreshToken);

        // set access token in response header
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX +accessToken);
        response.setStatus(HttpStatus.OK.value());

        // todo : 로그인 성공 redirect 경로 설정해야됨
        //response.sendRedirect("/");
    }

    /*
     * Spring Security는 로그인하는 과정에서 로그인이 실패한 경우에 세션에 관련 에러를 저장
     * 로그인이 실패한 상황이 한번이라도 발생했으면 에러가 세션에 저장되어 있을것
     * 세션을 받아와서 WebAttributes.AUTHENTICATION_EXCEPTION 변수에 정의된 이름으로 된 세션 값을 지운다.
     */
    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);

        if (httpSession == null) {
            return;
        }

        httpSession.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}