package com.nhom43.quanlychungcubackendgradle.share.service;

import com.nhom43.quanlychungcubackendgradle.entity.RefreshToken;
import com.nhom43.quanlychungcubackendgradle.entity.User;
import com.nhom43.quanlychungcubackendgradle.ex.SpringException;
import com.nhom43.quanlychungcubackendgradle.repository.RefreshTokenRepository;
import com.nhom43.quanlychungcubackendgradle.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public RefreshToken generateRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshTokenRepository.deleteByUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreateDate(Instant.now());
        refreshToken.setUser(user);
        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token, String username) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringException("Mã Token không hợp lệ"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringException("Không tìm thấy tài khoản này"));
        if (!user.getId().equals(refreshToken.getUser().getId())) {
            throw new SpringException("Người dùng không khớp với mã Token");
        }
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

}
