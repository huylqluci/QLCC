package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.HoaDonDichVuDto;
import com.nhom43.quanlychungcubackendgradle.dto.HoaDonSuaChuaDto;
import com.nhom43.quanlychungcubackendgradle.dto.request.HoaDonSuaChuaRequest;
import com.nhom43.quanlychungcubackendgradle.entity.HoaDonSuaChua;
import com.nhom43.quanlychungcubackendgradle.mapper.HoaDonSuaChuaMapper;
import com.nhom43.quanlychungcubackendgradle.repository.HoaDonSuaChuaRepository;
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
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class HoaDonSuaChuaService {
    private final HoaDonSuaChuaRepository repository;
    private final HoaDonSuaChuaMapper hoaDonSuaChuaMapper;

    public HoaDonSuaChuaDto save(HoaDonSuaChuaDto hoaDonSuaChuaDto) {
        HoaDonSuaChua entity = hoaDonSuaChuaMapper.toEntity(hoaDonSuaChuaDto);
        if (hoaDonSuaChuaDto.getId() != null) {
            if (!repository.existsById(hoaDonSuaChuaDto.getId()))
                entity.setNgayTao(LocalDateTime.now());
        } else
            entity.setNgayTao(LocalDateTime.now());
        return hoaDonSuaChuaMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public HoaDonSuaChuaDto findById(Long id) {
        return hoaDonSuaChuaMapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tồn tại id: " + id)));
    }

    public Page<HoaDonSuaChuaDto> findByCondition(HoaDonSuaChuaDto hoaDonSuaChuaDto, Pageable pageable) {
        HoaDonSuaChua hoaDonSuaChua = hoaDonSuaChuaMapper.toEntity(hoaDonSuaChuaDto);
        Page<HoaDonSuaChua> entityPage = repository.findAll(pageable);
        List<HoaDonSuaChua> entities = entityPage.getContent();
        return new PageImpl<>(hoaDonSuaChuaMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public HoaDonSuaChuaDto update(HoaDonSuaChuaRequest hoaDonSuaChuaRequest, Long id) {
        HoaDonSuaChuaDto data = findById(id);
        BeanUtils.copyProperties(hoaDonSuaChuaRequest, data);
        data.setNgayThanhToan(LocalDateTime.now());
        data.setTrangThai(true);
        return save(data);
    }


    // ------------------------------------------------------------------------------------------------------------- //


    public List<HoaDonSuaChuaDto> findAll() {
        List<HoaDonSuaChua> list = repository.findAllByOrderByTrangThaiAscNgayTaoDesc();
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại hóa đơn sửa chữa nào");
        return hoaDonSuaChuaMapper.toDto(list);
    }

    public List<HoaDonSuaChuaDto> findAllByTrangThai(boolean trangThai) {
        List<HoaDonSuaChua> list = repository.findAllByOrderByTrangThaiAscNgayTaoDesc();
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại hóa đơn sửa chữa nào");
        return hoaDonSuaChuaMapper.toDto(list);
    }

    public List<HoaDonSuaChuaDto> findAllByCanHo_Id(Long id_canHo) {

        List<HoaDonSuaChua> list = repository.findAllByCanHo_IdOrderByTrangThaiAscNgayTaoDesc(id_canHo);

        if (list.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chưa có hóa đơn nào với căn hộ này");
        return hoaDonSuaChuaMapper.toDto(list);
    }

    // ------------------------------------------------------------------------------------------------------------- //

    public List<HoaDonSuaChuaDto> findAllByTrangThaiTrongThang(boolean trangThai, String nam, String thang) {
        if (thang.length() == 1) thang = "0" + thang;
        List<HoaDonSuaChua> list = repository.findAllByTrangThaiAndNgayTao_YearAndNgayTao_Month(trangThai, nam, thang);
        if (list.isEmpty())
            throw new ResourceNotFoundException
                    ("Chưa tồn tại hóa đơn nào trong thang " + thang + "/" + nam);
        return hoaDonSuaChuaMapper.toDto(list);
    }

}