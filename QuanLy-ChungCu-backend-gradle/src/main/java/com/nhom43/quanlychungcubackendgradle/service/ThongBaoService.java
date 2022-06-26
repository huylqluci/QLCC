package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.ThongBaoDto;
import com.nhom43.quanlychungcubackendgradle.entity.ThongBao;
import com.nhom43.quanlychungcubackendgradle.mapper.ThongBaoMapper;
import com.nhom43.quanlychungcubackendgradle.repository.ThongBaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class ThongBaoService {
    private final ThongBaoRepository repository;
    private final ThongBaoMapper thongBaoMapper;

    public ThongBaoDto save(ThongBaoDto thongBaoDto) {
        ThongBao entity = thongBaoMapper.toEntity(thongBaoDto);
        if (thongBaoDto.getId() != null) {
            if (!repository.existsById(thongBaoDto.getId()))
                entity.setNgayTao(LocalDateTime.now());
        } else
            entity.setNgayTao(LocalDateTime.now());

        String tmp = entity.getNoiDung();
        if (tmp.indexOf("<p>") >= 0)
            entity.setNoiDung(tmp.substring(tmp.indexOf("<p>")+ 3));
        tmp = entity.getNoiDung();
        if (tmp.indexOf("</p>") >= 0)
            entity.setNoiDung(tmp.substring(0, tmp.indexOf("</p>")));

        return thongBaoMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ThongBaoDto findById(Long id) {
        return thongBaoMapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tồn tại id: " + id)));
    }

    public Page<ThongBaoDto> findByCondition(ThongBaoDto thongBaoDto, Pageable pageable) {
        ThongBao thongBao = thongBaoMapper.toEntity(thongBaoDto);
        Page<ThongBao> entityPage = repository.findAll(pageable);
        List<ThongBao> entities = entityPage.getContent();
        return new PageImpl<>(thongBaoMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public ThongBaoDto update(ThongBaoDto thongBaoDto, Long id) {
        ThongBaoDto data = findById(id);
        BeanUtils.copyProperties(thongBaoDto, data);
        return save(data);
    }

    // ------------------------------------------------------------------------------------------------------------- //

    public List<ThongBaoDto> findAll() {
        List<ThongBao> list = repository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại thông báo nào");
        return thongBaoMapper.toDto(list);
    }
}