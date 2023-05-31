package io.mykim.projectboardadmin.config.security.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignInRequestDto {
    private String username;
    private String password;

    @Builder
    public SignInRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
