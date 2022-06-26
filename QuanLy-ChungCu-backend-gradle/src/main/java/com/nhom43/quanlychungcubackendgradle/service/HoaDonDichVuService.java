package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.HoaDonDichVuDto;
import com.nhom43.quanlychungcubackendgradle.dto.request.HoaDonDichVuRequest;
import com.nhom43.quanlychungcubackendgradle.entity.HoaDonDichVu;
import com.nhom43.quanlychungcubackendgradle.mapper.HoaDonDichVuMapper;
import com.nhom43.quanlychungcubackendgradle.repository.HoaDonDichVuRepository;
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
public class HoaDonDichVuService {
    private final HoaDonDichVuRepository repository;
    private final HoaDonDichVuMapper hoaDonDichVuMapper;

    public HoaDonDichVuDto save(HoaDonDichVuDto hoaDonDichVuDto) {
        HoaDonDichVu entity = hoaDonDichVuMapper.toEntity(hoaDonDichVuDto);
        return hoaDonDichVuMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public HoaDonDichVuDto findById(Long id) {
        return hoaDonDichVuMapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tồn tại id: " + id)));
    }

    public Page<HoaDonDichVuDto> findByCondition(HoaDonDichVuDto hoaDonDichVuDto, Pageable pageable) {
        HoaDonDichVu hoaDonDichVu = hoaDonDichVuMapper.toEntity(hoaDonDichVuDto);
        Page<HoaDonDichVu> entityPage = repository.findAll(pageable);
        List<HoaDonDichVu> entities = entityPage.getContent();
        return new PageImpl<>(hoaDonDichVuMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public HoaDonDichVuDto update(HoaDonDichVuRequest hoaDonDichVuRequest, Long id) {
        HoaDonDichVuDto data = findById(id);
        BeanUtils.copyProperties(hoaDonDichVuRequest, data);
        data.setNgayThanhToan(LocalDateTime.now());
        data.setTrangThai(true);
        return save(data);
    }


    // ------------------------------------------------------------------------------------------------------------- //


    public List<HoaDonDichVuDto> findAll() {
        List<HoaDonDichVu> list = repository.findAllByOrderByTrangThaiAscNgayTaoDesc();
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại hóa đơn nào");
        return hoaDonDichVuMapper.toDto(list);
    }

    public List<HoaDonDichVuDto> findAllByTrangThai(boolean trangThai) {
        List<HoaDonDichVu> list = repository.findAllByOrderByTrangThaiAscNgayTaoDesc();
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại hóa đơn nào");
        return hoaDonDichVuMapper.toDto(list);
    }

    public List<HoaDonDichVuDto> findAllByCanHo_Id(Long id_canHo) {

        List<HoaDonDichVu> list = repository.findAllByCanHo_IdOrderByTrangThaiAscNgayTaoDesc(id_canHo);

        if (list.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chưa có hóa đơn nào với căn hộ này");
        return hoaDonDichVuMapper.toDto(list);
    }

//    public HoaDonDichVuDto thanhToanTienMat(HoaDonDichVuDto hoaDonDichVuDto, Long id) {
//        HoaDonDichVuDto data = findById(id);
//        BeanUtils.copyProperties(hoaDonDichVuDto, data);
//        data.setLoaiHinhThanhToan("Tien Mat");
//        return save(data);
//    }

    // ------------------------------------------------------------------------------------------------------------- //

    public List<HoaDonDichVuDto> findAllByTrangThaiTrongThang(boolean trangThai, String nam, String thang) {
        if (thang.length() == 1) thang = "0"+ thang;
        List<HoaDonDichVu> list = repository.findAllByTrangThaiAndNgayTao_YearAndNgayTao_Month(trangThai, nam, thang);
        if (list.isEmpty())
            throw new ResourceNotFoundException
                    ("Chưa tồn tại hóa đơn nào trong thang " + thang + "/" + nam);
        return hoaDonDichVuMapper.toDto(list);
    }
}