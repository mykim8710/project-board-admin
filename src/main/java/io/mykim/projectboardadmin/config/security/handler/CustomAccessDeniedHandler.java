package io.mykim.projectboardadmin.config.security.handler;

import io.mykim.projectboardadmin.config.response.CommonResponseUtils;
import io.mykim.projectboardadmin.config.response.dto.CommonResponse;
import io.mykim.projectboardadmin.config.response.enums.CustomErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final String PREFIX_REST_API = "/api";
    private static final String REDIRECT_URL = "/main";
    private final CommonResponseUtils commonResponseUtils;

    // 403 error
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Access Denied");
        String requestURI = request.getRequestURI();

        // rest api
        if(requestURI.startsWith(PREFIX_REST_API)) {
            commonResponseUtils.sendApiResponse(response, new CommonResponse(CustomErrorCode.ACCESS_DENIED, REDIRECT_URL));
        } else {
            // view
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);   // 403 error
            response.sendRedirect(REDIRECT_URL);            // "/main" >> main page after authentication
        }
    }
}