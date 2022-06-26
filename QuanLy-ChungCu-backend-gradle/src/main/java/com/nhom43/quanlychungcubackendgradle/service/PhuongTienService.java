package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.PhuongTienDto;
import com.nhom43.quanlychungcubackendgradle.entity.PhuongTien;
import com.nhom43.quanlychungcubackendgradle.entity.TheCuDan;
import com.nhom43.quanlychungcubackendgradle.mapper.PhuongTienMapper;
import com.nhom43.quanlychungcubackendgradle.repository.PhuongTienRepository;
import com.nhom43.quanlychungcubackendgradle.repository.TheCuDanRepository;
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
public class PhuongTienService {

    private final PhuongTienRepository repository;
    private final PhuongTienMapper phuongTienMapper;
    private final TheCuDanRepository theCuDanRepository;

    public PhuongTienDto save(PhuongTienDto phuongTienDto) {
        PhuongTien entity = phuongTienMapper.toEntity(phuongTienDto);
        PhuongTien phuongTien = repository.save(entity);
        TheCuDan theCuDan = theCuDanRepository.findByMaThe(phuongTien.getTheCuDan().getMaThe());
        theCuDan.setKichHoat(true);
//        System.out.println(theCuDan.toString());
        return phuongTienMapper.toDto(phuongTien);
    }

    public void deleteById(Long id) {
        PhuongTien phuongTien = repository.findById(id).get();
        TheCuDan theCuDan = theCuDanRepository.findByMaThe(phuongTien.getTheCuDan().getMaThe());
        theCuDan.setKichHoat(false);
        repository.deleteById(id);
    }

    public PhuongTienDto findById(Long id) {
        return phuongTienMapper.toDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tồn tại id: " + id)
        ));
    }

    public Page<PhuongTienDto> findByCondition(PhuongTienDto phuongTienDto, Pageable pageable) {
        PhuongTien phuongTien = phuongTienMapper.toEntity(phuongTienDto);
        Page<PhuongTien> entityPage = repository.findAll(pageable);
        List<PhuongTien> entities = entityPage.getContent();
        return new PageImpl<>(phuongTienMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public PhuongTienDto update(PhuongTienDto phuongTienDto, Long id) {
        PhuongTienDto data = findById(id);
        BeanUtils.copyProperties(phuongTienDto, data);
        return save(data);
    }


    // ------------------------------------------------------------------------------------------------------------- //

    public List<PhuongTienDto> findAll() {
        List<PhuongTien> list = repository.findAll();
        if (list.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại phương tiện nào");
        return phuongTienMapper.toDto(list);
    }

//    public List<PhuongTienDto> findAllByPhuongTien_Id(Long id) {
//
//        List<PhuongTien> list = repository.findAllByPhuongTien_Id(id);
//
//        if (list.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,  "Không tìm thấy phương tiện với id: "+ id);
//        return phuongTienMapper.toDto(list);
//    }


    // ------------------------------------------------------------------------------------------------------------- //

    public List<PhuongTienDto> findAllByDaXoa(boolean daXoa) {
        List<PhuongTien> list = repository.findAllByDaXoa(daXoa);
        if (list.isEmpty()) throw new ResourceNotFoundException("Không có Phương tiện nào Trạng thái \"ĐÃ XÓA\" : " + daXoa);
        return phuongTienMapper.toDto(list);
    }

    public List<PhuongTienDto> findAllByDaXoaAndCanHo_Id(boolean daXoa, Long id) {
        List<PhuongTien> list = repository.findAllByDaXoaAndCanHo_Id(daXoa, id);
        if (list.isEmpty()) throw new ResourceNotFoundException("Tại Căn Hộ ID: " + id + ", Không có Phương tiện nào Trạng thái \"ĐÃ XÓA\" : " + daXoa);
        return phuongTienMapper.toDto(list);
    }

}