package io.mykim.projectboardadmin.config.security.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtTokenResponseDto {
    private String accessToken;
    private String refreshToken;

    @Builder
    public JwtTokenResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
