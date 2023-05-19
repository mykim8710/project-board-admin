package io.mykim.projectboardadmin.config.security.jwt.repository;

import io.mykim.projectboardadmin.config.security.jwt.entity.JwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, Long> {
    Optional<JwtRefreshToken> findByUsername(String username);
}
