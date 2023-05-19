package io.mykim.projectboardadmin.config.security.jwt.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "jwt_refresh_token")
public class JwtRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "username", nullable = false)
    private String username;

    @Builder
    private JwtRefreshToken(String refreshToken, String username) {
        this.refreshToken = refreshToken;
        this.username = username;
    }

    public static JwtRefreshToken createRefreshToken(String refreshToken, String username) {
        return JwtRefreshToken.builder()
                .refreshToken(refreshToken)
                .username(username)
                .build();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
