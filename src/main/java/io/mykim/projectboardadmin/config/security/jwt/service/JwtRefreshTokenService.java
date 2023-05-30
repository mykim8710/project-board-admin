package io.mykim.projectboardadmin.config.security.jwt.service;

import io.mykim.projectboardadmin.config.security.jwt.JwtProvider;
import io.mykim.projectboardadmin.config.security.jwt.entity.JwtRefreshToken;
import io.mykim.projectboardadmin.config.security.jwt.enums.TokenType;
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
    private final JwtProvider jwtProvider;

    @Transactional
    public void insertOrUpdateRefreshToken(Long userId, String refreshToken) {
        Optional<JwtRefreshToken> findRefreshToken = jwtRefreshTokenRepository.findByUserId(userId);

        // 기존에 해당 계정의 refresh token이 존재한다면 refresh token update
        if(findRefreshToken.isPresent()) {
            JwtRefreshToken jwtRefreshToken = findRefreshToken.get();
            jwtRefreshToken.updateRefreshToken(refreshToken);
        } else {
            // 기존에 해당 계정의 refresh token이 존재하지 않는다면 insert
            JwtRefreshToken newRefreshToken = JwtRefreshToken.createRefreshToken(refreshToken, userId);
            jwtRefreshTokenRepository.save(newRefreshToken);
        }
    }

    @Transactional(readOnly = true)
    public boolean validateRefreshToken(String refreshToken) {
        if(!jwtProvider.validateToken(refreshToken, TokenType.REFRESH)) {
            return false;
        }

        Long adminUserId = Long.valueOf(jwtProvider.getSubjectFromClaims(refreshToken));
        Optional<JwtRefreshToken> findJwtRefreshToken = jwtRefreshTokenRepository.findByUserId(adminUserId);
        return findJwtRefreshToken.isPresent() && refreshToken.equals(findJwtRefreshToken.get().getRefreshToken());
    }

}
