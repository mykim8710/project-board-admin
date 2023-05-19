package io.mykim.projectboardadmin.config.security.handler;

import io.mykim.projectboardadmin.config.response.enums.CustomErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("Sign-in Fail >> not valid userinfo");

        String errorMessage = CustomErrorCode.NOT_VALID_ACCOUNT.getMessage();
        errorMessage= URLEncoder.encode(errorMessage,"UTF-8"); // 한글 인코딩 깨지는 문제방지

        // todo : 로그인 실패(로그인 정보가 틀림, db 검증 실패) 시 이동할 url 설정 필요
//        setDefaultFailureUrl("/sign-in?error=true&exception=" +errorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }
}