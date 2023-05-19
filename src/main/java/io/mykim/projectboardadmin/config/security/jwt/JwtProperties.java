package io.mykim.projectboardadmin.config.security.jwt;

public class JwtProperties {
    public static final String SECRET_KEY = "jiS48dIGRtD73A/st4gd8SxL7AHSdkPtNb7oO9p22rI="; // 우리 서버만 알고 있는 비밀값
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_AUDIENCE = "PROJECT_BOARD_ADMIN";
}
