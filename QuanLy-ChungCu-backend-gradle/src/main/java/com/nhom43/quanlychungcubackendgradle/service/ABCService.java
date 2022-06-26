package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.ABCDto;
import com.nhom43.quanlychungcubackendgradle.entity.ABC;
import com.nhom43.quanlychungcubackendgradle.mapper.ABCMapper;
import com.nhom43.quanlychungcubackendgradle.repository.ABCRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class ABCService {
    private final ABCRepository repository;
    private final ABCMapper aBCMapper;

    public ABCDto save(ABCDto aBCDto) {
        ABC entity = aBCMapper.toEntity(aBCDto);
        return aBCMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ABCDto findById(Long id) {
        return aBCMapper.toDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public Page<ABCDto> findByCondition(ABCDto aBCDto, Pageable pageable) {
        ABC aBC = aBCMapper.toEntity(aBCDto);
        Page<ABC> entityPage = repository.findAll(pageable);
        List<ABC> entities = entityPage.getContent();
        return new PageImpl<>(aBCMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public ABCDto update(ABCDto aBCDto, Long id) {
        ABCDto data = findById(id);
        ABC entity = aBCMapper.toEntity(aBCDto);
        BeanUtils.copyProperties(data, entity);
        return save(aBCMapper.toDto(entity));
    }
}