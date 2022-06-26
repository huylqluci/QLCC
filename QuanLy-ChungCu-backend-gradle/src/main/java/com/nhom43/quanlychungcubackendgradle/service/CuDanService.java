package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.CuDanDto;
import com.nhom43.quanlychungcubackendgradle.entity.CuDan;
import com.nhom43.quanlychungcubackendgradle.mapper.CanHoMapper;
import com.nhom43.quanlychungcubackendgradle.mapper.CuDanMapper;
import com.nhom43.quanlychungcubackendgradle.repository.CuDanRepository;
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
public class CuDanService {

    private final CuDanRepository repository;
    private final CuDanMapper cuDanMapper;

    public CuDanDto save(CuDanDto cuDanDto) {
        CuDan entity = cuDanMapper.toEntity(cuDanDto);
        return cuDanMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public CuDanDto findById(Long id) {
        return cuDanMapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tồn tại id: " + id)));
    }

    public Page<CuDanDto> findByCondition(CuDanDto cuDanDto, Pageable pageable) {
        CuDan cuDan = cuDanMapper.toEntity(cuDanDto);
        Page<CuDan> entityPage = repository.findAll(pageable);
        List<CuDan> entities = entityPage.getContent();
        return new PageImpl<>(cuDanMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public CuDanDto update(CuDanDto cuDanDto, Long id) {
        CuDanDto data = findById(id);
        BeanUtils.copyProperties(cuDanDto, data);
        return save(data);
    }

    // ------------------------------------------------------------------------------------------------------------- //


    public List<CuDanDto> findAll() {
        List<CuDan> list = repository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại cư dân nào");
        return cuDanMapper.toDto(list);
    }

    public List<CuDanDto> findAllByCanHo_Id(Long id_canHo) {

        List<CuDan> list = repository.findByCanHo_Id(id_canHo);

        if (list.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chưa có cư dân trong căn hộ này");
        return cuDanMapper.toDto(list);
    }

    // ------------------------------------------------------------------------------------------------------------- //

    public List<CuDanDto> findAllByDaXoa(boolean daXoa) {
        List<CuDan> cuDanList = repository.findAllByDaXoa(daXoa);
        if (cuDanList.isEmpty()) throw new ResourceNotFoundException("Không có cư dân nào Trạng thái \"ĐÃ XÓA\" : " + daXoa);
        return cuDanMapper.toDto(cuDanList);
    }

}