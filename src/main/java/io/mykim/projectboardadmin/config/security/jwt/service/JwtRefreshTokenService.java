package io.mykim.projectboardadmin.config.security.jwt.service;

import io.mykim.projectboardadmin.config.security.jwt.entity.JwtRefreshToken;
import io.mykim.projectboardadmin.config.security.jwt.repository.JwtRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtRefreshTokenService {
    private final JwtRefreshTokenRepository jwtRefreshTokenRepository;

    @Transactional
    public void insertOrUpdateRefreshToken(String username, String refreshToken) {
        Optional<JwtRefreshToken> findRefreshToken = jwtRefreshTokenRepository.findByUsername(username);

        // 기존에 해당 계정의 refresh token이 존재한다면 refresh token update
        if(findRefreshToken.isPresent()) {
            JwtRefreshToken jwtRefreshToken = findRefreshToken.get();
            jwtRefreshToken.updateRefreshToken(refreshToken);
        } else {
            // 기존에 해당 계정의 refresh token이 존재하지 않는다면 insert
            JwtRefreshToken newRefreshToken = JwtRefreshToken.createRefreshToken(refreshToken, username);
            jwtRefreshTokenRepository.save(newRefreshToken);
        }
    }

}
