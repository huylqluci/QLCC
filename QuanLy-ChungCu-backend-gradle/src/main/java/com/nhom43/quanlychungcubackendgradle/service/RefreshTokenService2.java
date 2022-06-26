package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.RefreshTokenDto;
import com.nhom43.quanlychungcubackendgradle.entity.RefreshToken;
import com.nhom43.quanlychungcubackendgradle.mapper.RefreshTokenMapper;
import com.nhom43.quanlychungcubackendgradle.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class RefreshTokenService2 {
    private final RefreshTokenRepository repository;
    private final RefreshTokenMapper refreshTokenMapper;

    public RefreshTokenDto save(RefreshTokenDto refreshTokenDto) {
        RefreshToken entity = refreshTokenMapper.toEntity(refreshTokenDto);
        return refreshTokenMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public RefreshTokenDto findById(Long id) {
        return refreshTokenMapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tồn tại id: " + id)));
    }

    public Page<RefreshTokenDto> findByCondition(RefreshTokenDto refreshTokenDto, Pageable pageable) {
        RefreshToken refreshToken = refreshTokenMapper.toEntity(refreshTokenDto);
        Page<RefreshToken> entityPage = repository.findAll(pageable);
        List<RefreshToken> entities = entityPage.getContent();
        return new PageImpl<>(refreshTokenMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public RefreshTokenDto update(RefreshTokenDto refreshTokenDto, Long id) {
        RefreshTokenDto data = findById(id);
        BeanUtils.copyProperties(refreshTokenDto, data);
        return save(data);
    }

}