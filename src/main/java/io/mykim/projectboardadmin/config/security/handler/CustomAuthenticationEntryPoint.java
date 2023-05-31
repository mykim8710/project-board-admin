package io.mykim.projectboardadmin.config.security.handler;

import io.mykim.projectboardadmin.global.response.CommonResponseUtils;
import io.mykim.projectboardadmin.global.response.dto.CommonResponse;
import io.mykim.projectboardadmin.global.response.enums.CustomErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final String PREFIX_REST_API = "/api";
    private static final String REDIRECT_URL = "/";
    private final CommonResponseUtils commonResponseUtils;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Authentication check error");
        String requestURI = request.getRequestURI();

        // rest api
        if(requestURI.startsWith(PREFIX_REST_API)) {
            commonResponseUtils.sendApiResponse(response, new CommonResponse(CustomErrorCode.UN_AUTHORIZED_USER, REDIRECT_URL));
        } else {
            // view
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 error
            response.sendRedirect(REDIRECT_URL);             // "/" >> login page url
        }
    }
}