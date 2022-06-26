package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.TheCuDanDto;
import com.nhom43.quanlychungcubackendgradle.entity.TheCuDan;
import com.nhom43.quanlychungcubackendgradle.mapper.TheCuDanMapper;
import com.nhom43.quanlychungcubackendgradle.repository.TheCuDanRepository;
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
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class TheCuDanService {
    private final TheCuDanRepository repository;
    private final TheCuDanMapper theCuDanMapper;

    public TheCuDanDto save(TheCuDanDto theCuDanDto) {
        TheCuDan entity = theCuDanMapper.toEntity(theCuDanDto);
        entity.setDaXoa(false);
        entity.setNgayTao(LocalDate.now());
        return theCuDanMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public TheCuDanDto findById(Long id) {
        return theCuDanMapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tồn tại id: " + id)));
    }

    public Page<TheCuDanDto> findByCondition(TheCuDanDto theCuDanDto, Pageable pageable) {
        TheCuDan theCuDan = theCuDanMapper.toEntity(theCuDanDto);
        Page<TheCuDan> entityPage = repository.findAll(pageable);
        List<TheCuDan> entities = entityPage.getContent();
        return new PageImpl<>(theCuDanMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public TheCuDanDto update(TheCuDanDto theCuDanDto, Long id) {
        TheCuDanDto data = findById(id);
        BeanUtils.copyProperties(theCuDanDto, data);
        return save(data);
    }


    // ------------------------------------------------------------------------------------------------------------- //

    public List<TheCuDanDto> findAll() {
        List<TheCuDan> list = repository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại thẻ cư dân nào");
        return theCuDanMapper.toDto(list);
    }

    public List<TheCuDanDto> findAllByCanHo_Id(Long id_canHo) {

        List<TheCuDan> list = repository.findAllByCanHo_Id(id_canHo);

        if (list.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chưa có thẻ cư dân nào cho căn hộ này");
        return theCuDanMapper.toDto(list);
    }


    // ------------------------------------------------------------------------------------------------------------- //

    public List<TheCuDanDto> findAllByDaXoa(boolean daXoa) {
        List<TheCuDan> list = repository.findAllByDaXoa(daXoa);
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại thẻ cư dân nào Trạng thái Đã xóa = "+ daXoa);
        return theCuDanMapper.toDto(list);
    }

    public List<TheCuDanDto> findAllByDaXoaAndKichHoat(boolean daXoa, boolean kichHoat) {
        List<TheCuDan> list = repository.findAllByDaXoaAndKichHoat(daXoa, kichHoat);
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại thẻ cư dân nào Trạng thái Đã xóa = "+ daXoa);
        return theCuDanMapper.toDto(list);
    }

    public List<TheCuDanDto> findAllByCanHo_IdAndDaXoa(Long id_canHo, boolean daXoa) {

        List<TheCuDan> list = repository.findAllByCanHo_IdAndDaXoa(id_canHo, daXoa);

        if (list.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chưa có thẻ cư dân nào cho căn hộ này");
        return theCuDanMapper.toDto(list);
    }

    public List<TheCuDanDto> findAllByCanHo_IdAndDaXoaAndKichHoat(Long id_canHo, boolean daXoa, boolean kichHoat) {

        List<TheCuDan> list = repository.findAllByCanHo_IdAndDaXoaAndKichHoat(id_canHo, daXoa, kichHoat);

        if (list.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chưa có thẻ cư dân nào cho căn hộ này");
        return theCuDanMapper.toDto(list);
    }
}