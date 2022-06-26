package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.DichVuCoDinhDto;
import com.nhom43.quanlychungcubackendgradle.entity.DichVuCoDinh;
import com.nhom43.quanlychungcubackendgradle.mapper.DichVuCoDinhMapper;
import com.nhom43.quanlychungcubackendgradle.repository.DichVuCoDinhRepository;
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
public class DichVuCoDinhService {
    private final DichVuCoDinhRepository repository;
    private final DichVuCoDinhMapper dichVuCoDinhMapper;

    public DichVuCoDinhDto save(DichVuCoDinhDto dichVuCoDinhDto) {
        DichVuCoDinh entity = dichVuCoDinhMapper.toEntity(dichVuCoDinhDto);
        return dichVuCoDinhMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public DichVuCoDinhDto findById(Long id) {
        return dichVuCoDinhMapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tồn tại id: " + id)));
    }

    public Page<DichVuCoDinhDto> findByCondition(DichVuCoDinhDto dichVuCoDinhDto, Pageable pageable) {
        DichVuCoDinh dichVuCoDinh = dichVuCoDinhMapper.toEntity(dichVuCoDinhDto);
        Page<DichVuCoDinh> entityPage = repository.findAll(pageable);
        List<DichVuCoDinh> entities = entityPage.getContent();
        return new PageImpl<>(dichVuCoDinhMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public DichVuCoDinhDto update(DichVuCoDinhDto dichVuCoDinhDto, Long id) {
        DichVuCoDinhDto data = findById(id);
        BeanUtils.copyProperties(dichVuCoDinhDto, data);
        return save(data);
    }

    public List<DichVuCoDinhDto> findAll() {
        List<DichVuCoDinh> list = repository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tạo loại dịch vụ cố định");
       return dichVuCoDinhMapper.toDto(list);
    }

}