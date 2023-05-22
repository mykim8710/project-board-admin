package io.mykim.projectboardadmin.config.security.jwt.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "jwt_refresh_token")
public class JwtRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    private JwtRefreshToken(String refreshToken, Long userId) {
        this.refreshToken = refreshToken;
        this.userId = userId;
    }

    public static JwtRefreshToken createRefreshToken(String refreshToken, Long userId) {
        return JwtRefreshToken.builder()
                .refreshToken(refreshToken)
                .userId(userId)
                .build();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
