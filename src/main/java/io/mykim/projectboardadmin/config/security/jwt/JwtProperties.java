package io.mykim.projectboardadmin.config.security.jwt;

public class JwtProperties {
    // todo : 코드에 직접있어서 외부에 드러나지 않게 설정할 필요가 있음
    public static final String SECRET_KEY = "jiS48dIGRtD73A/st4gd8SxL7AHSdkPtNb7oO9p22rI="; // 우리 서버만 알고 있는 비밀값
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING_ACCESS_TOKEN = "Authorization_AccessToken";
    public static final String HEADER_STRING_REFRESH_TOKEN = "Authorization_RefreshToken";
    public static final String COOKIE_NAME_ACCESS_TOKEN = "Cookie_AccessToken";
    public static final String COOKIE_NAME_REFRESH_TOKEN = "Cookie_RefreshToken";
    public static final String TOKEN_AUDIENCE = "PROJECT_BOARD_ADMIN";
    public static final int ACCESS_TOKEN_VALID_MINUTE = 30;
    public static final int REFRESH_TOKEN_VALID_DAY = 30;
}
