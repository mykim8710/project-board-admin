package io.mykim.projectboardadmin.config.security.handler;

import io.mykim.projectboardadmin.config.response.CommonResponseUtils;
import io.mykim.projectboardadmin.config.response.dto.CommonResponse;
import io.mykim.projectboardadmin.config.response.enums.CustomErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final CommonResponseUtils commonResponseUtils;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("Sign-in Fail >> not valid userinfo");
        commonResponseUtils.sendApiResponse(response, new CommonResponse(CustomErrorCode.NOT_VALID_ACCOUNT));
    }
}