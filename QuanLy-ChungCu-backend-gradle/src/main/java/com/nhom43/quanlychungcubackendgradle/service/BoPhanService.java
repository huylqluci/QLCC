package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.BoPhanDto;
import com.nhom43.quanlychungcubackendgradle.dto.CuDanDto;
import com.nhom43.quanlychungcubackendgradle.entity.BoPhan;
import com.nhom43.quanlychungcubackendgradle.entity.CuDan;
import com.nhom43.quanlychungcubackendgradle.mapper.BoPhanMapper;
import com.nhom43.quanlychungcubackendgradle.repository.BoPhanRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import org.springframework.beans.BeanUtils;

@AllArgsConstructor
@Service
@Transactional
public class BoPhanService {
    private final BoPhanRepository repository;
    private final BoPhanMapper boPhanMapper;

    public BoPhanDto save(BoPhanDto boPhanDto) {
        BoPhan entity = boPhanMapper.toEntity(boPhanDto);
        return boPhanMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public BoPhanDto findById(Long id) {
//        return boPhanMapper.toDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
        return boPhanMapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tồn tại id: " + id)));
    }

    public Page<BoPhanDto> findByCondition(BoPhanDto boPhanDto, Pageable pageable) {
        BoPhan boPhan = boPhanMapper.toEntity(boPhanDto);
        Page<BoPhan> entityPage = repository.findAll(pageable);
        List<BoPhan> entities = entityPage.getContent();
        return new PageImpl<>(boPhanMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public BoPhanDto update(BoPhanDto boPhanDto, Long id) {
        BoPhanDto data = findById(id);
        BeanUtils.copyProperties(boPhanDto, data);
        return save(data);
    }


    // ------------------------------------------------------------------------------------------------------------- //


    public List<BoPhanDto> findAll() {
        List<BoPhan> list = repository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại bộ phận nào");
        return boPhanMapper.toDto(list);
    }
}