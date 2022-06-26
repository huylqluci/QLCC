package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.ChiTietHoaDonSuaChuaDto;
import com.nhom43.quanlychungcubackendgradle.entity.ChiTietHoaDonSuaChua;
import com.nhom43.quanlychungcubackendgradle.mapper.ChiTietHoaDonSuaChuaMapper;
import com.nhom43.quanlychungcubackendgradle.repository.ChiTietHoaDonSuaChuaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class ChiTietHoaDonSuaChuaService {
    private final ChiTietHoaDonSuaChuaRepository repository;
    private final ChiTietHoaDonSuaChuaMapper chiTietHoaDonSuaChuaMapper;

    public ChiTietHoaDonSuaChuaDto save(ChiTietHoaDonSuaChuaDto chiTietHoaDonSuaChuaDto) {
        ChiTietHoaDonSuaChua entity = chiTietHoaDonSuaChuaMapper.toEntity(chiTietHoaDonSuaChuaDto);
        return chiTietHoaDonSuaChuaMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ChiTietHoaDonSuaChuaDto findById(Long id) {
        return chiTietHoaDonSuaChuaMapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tồn tại id: " + id)));
    }

    public Page<ChiTietHoaDonSuaChuaDto> findByCondition(ChiTietHoaDonSuaChuaDto chiTietHoaDonSuaChuaDto, Pageable pageable) {
        ChiTietHoaDonSuaChua chiTietHoaDonSuaChua = chiTietHoaDonSuaChuaMapper.toEntity(chiTietHoaDonSuaChuaDto);
        Page<ChiTietHoaDonSuaChua> entityPage = repository.findAll(pageable);
        List<ChiTietHoaDonSuaChua> entities = entityPage.getContent();
        return new PageImpl<>(chiTietHoaDonSuaChuaMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public ChiTietHoaDonSuaChuaDto update(ChiTietHoaDonSuaChuaDto chiTietHoaDonSuaChuaDto, Long id) {
        ChiTietHoaDonSuaChuaDto data = findById(id);
        BeanUtils.copyProperties(chiTietHoaDonSuaChuaDto, data);
        return save(data);
    }

    public List<ChiTietHoaDonSuaChuaDto> findByHoaDonSuaChua_Id(Long id) {
        List<ChiTietHoaDonSuaChua> list = repository.findByHoaDonSuaChua_Id(id);
        if (list.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chưa có hóa đơn chi tiết nào trong hóa đơn này");
        return chiTietHoaDonSuaChuaMapper.toDto(list);
    }
}