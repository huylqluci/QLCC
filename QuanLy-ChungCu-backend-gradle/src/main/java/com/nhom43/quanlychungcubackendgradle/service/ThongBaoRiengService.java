package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.ThongBaoRiengDto;
import com.nhom43.quanlychungcubackendgradle.entity.ThongBaoRieng;
import com.nhom43.quanlychungcubackendgradle.mapper.ThongBaoRiengMapper;
import com.nhom43.quanlychungcubackendgradle.repository.ThongBaoRiengRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ThongBaoRiengService {
    private final ThongBaoRiengRepository repository;
    private final ThongBaoRiengMapper thongBaoRiengMapper;

    public ThongBaoRiengService(ThongBaoRiengRepository repository, ThongBaoRiengMapper thongBaoRiengMapper) {
        this.repository = repository;
        this.thongBaoRiengMapper = thongBaoRiengMapper;
    }

    public ThongBaoRiengDto save(ThongBaoRiengDto thongBaoRiengDto) {
        ThongBaoRieng entity = thongBaoRiengMapper.toEntity(thongBaoRiengDto);
        if (thongBaoRiengDto.getId() != null) {
            if (!repository.existsById(thongBaoRiengDto.getId()))
                entity.setNgayTao(LocalDateTime.now());
        } else
            entity.setNgayTao(LocalDateTime.now());

        String tmp = entity.getNoiDung();
        if (tmp.indexOf("<p>") >= 0)
            entity.setNoiDung(tmp.substring(tmp.indexOf("<p>")+ 3));
        tmp = entity.getNoiDung();
        if (tmp.indexOf("</p>") >= 0)
            entity.setNoiDung(tmp.substring(0, tmp.indexOf("</p>")));

        return thongBaoRiengMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ThongBaoRiengDto findById(Long id) {
        return thongBaoRiengMapper.toDto(
                repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    public Page<ThongBaoRiengDto> findByCondition(ThongBaoRiengDto thongBaoRiengDto, Pageable pageable) {
        ThongBaoRieng thongBaoRieng = thongBaoRiengMapper.toEntity(thongBaoRiengDto);
        Page<ThongBaoRieng> entityPage = repository.findAll(pageable);
        List<ThongBaoRieng> entities = entityPage.getContent();
        return new PageImpl<>(thongBaoRiengMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public ThongBaoRiengDto update(ThongBaoRiengDto thongBaoRiengDto, Long id) {
        ThongBaoRiengDto data = findById(id);
        BeanUtils.copyProperties(thongBaoRiengDto, data);
        return save(data);
    }

    // ------------------------------------------------------------------------------------------------------------- //

    public List<ThongBaoRiengDto> findAll() {
        List<ThongBaoRieng> list = repository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại thông báo nào");
        return thongBaoRiengMapper.toDto(list);
    }

    public List<ThongBaoRiengDto> findAllByCanHo_Id(Long id) {
        List<ThongBaoRieng> list = repository.findAllByCanHo_Id(id);
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại thông báo riêng nào");
        return thongBaoRiengMapper.toDto(list);
    }
    public List<ThongBaoRiengDto> findAllByCanHo_IdOrderByTrangThaiAscNgayTaoDesc(Long id) {
        List<ThongBaoRieng> list = repository.findAllByCanHo_IdOrderByTrangThaiDescNgayTaoDesc(id);
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại thông báo riêng nào");
        return thongBaoRiengMapper.toDto(list);
    }


}