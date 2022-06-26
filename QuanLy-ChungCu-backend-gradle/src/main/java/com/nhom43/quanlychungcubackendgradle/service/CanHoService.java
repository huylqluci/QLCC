package com.nhom43.quanlychungcubackendgradle.service;

import com.nhom43.quanlychungcubackendgradle.dto.CanHoDto;
import com.nhom43.quanlychungcubackendgradle.entity.CanHo;
import com.nhom43.quanlychungcubackendgradle.entity.CuDan;
import com.nhom43.quanlychungcubackendgradle.entity.User;
import com.nhom43.quanlychungcubackendgradle.mapper.CanHoMapper;
import com.nhom43.quanlychungcubackendgradle.repository.CanHoRepository;
import com.nhom43.quanlychungcubackendgradle.repository.CuDanRepository;
import com.nhom43.quanlychungcubackendgradle.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class CanHoService {
    private final CanHoRepository repository;
    private final CuDanRepository cuDanRepository;
    private final CanHoMapper canHoMapper;
    private final UserRepository userRepository;

    public CanHoDto save(CanHoDto canHoDto) {
        CanHo entity = canHoMapper.toEntity(canHoDto);
        return canHoMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public CanHoDto findById(Long id) {
        CanHo canHo = repository.findById(id).get();
        CanHoDto canHoDto;
        if (canHo == null) {
            throw new ResourceNotFoundException("Chưa tồn tại căn hộ");
        }
        canHoDto = canHoMapper.toDto(canHo);
        boolean trangThai = canHo.getTrangThai();
        if (trangThai == true) {
            CuDan chuCanHo = cuDanRepository.findByCanHoAndChuCanHo(canHo, true);
            canHoDto.setChuCanHo(chuCanHo);
        }
        return canHoDto;
    }

    public Page<CanHoDto> findByCondition(CanHoDto canHoDto, Pageable pageable) {
        CanHo canHo = canHoMapper.toEntity(canHoDto);
        Page<CanHo> entityPage = repository.findAll(pageable);
        List<CanHo> entities = entityPage.getContent();
        return new PageImpl<>(canHoMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

//    public CanHoDto update(CanHoDto canHoDto, Long id) {
//        CanHoDto data = findById(id);
//        CanHo entity = canHoMapper.toEntity(canHoDto);
//        BeanUtils.copyProperties(data, entity);
//        return save(canHoMapper.toDto(entity));
//    }

    public CanHoDto update(CanHoDto canHoDto, Long id) {
        CanHoDto data = findById(id);
        BeanUtils.copyProperties(canHoDto, data);
        return save(data);
    }

    // ------------------------------------------------------------------------------------------------------------- //


    public List<CanHoDto> findAll() {
        List<CanHo> canHoList = repository.findAll();
        if (canHoList.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại căn hộ");
//        if (canHoList.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chưa tồn tại căn hộ");
        return canHoMapper.toDto(canHoList);
    }

    public List<CanHoDto> findAllByTrangThai(boolean trangThai) {
        List<CanHo> canHoList = repository.findAllByTrangThai(trangThai);
        System.out.println(canHoList.size());
        if (trangThai == true) {

            List<CanHoDto> canHoListDto = new ArrayList<>();
            for (CanHo canHo : canHoList) {
                CuDan chuCanHo = cuDanRepository.findByCanHoAndChuCanHo(canHo, true);
//                System.out.println(chuCanHo.toString());
                CanHoDto canHoDto = canHoMapper.toDto(canHo);
                Long setSoLuongCuDan = cuDanRepository.countByCanHoAndDaXoa(canHo, false);
                String emailTaiKhoan = null;
                if (canHoDto.getIdTaiKhoan() != null) {
                    emailTaiKhoan = userRepository.findById(canHoDto.getIdTaiKhoan()).get().getEmail();
                    canHoDto.setTaiKhoan(userRepository.findById(canHoDto.getIdTaiKhoan()).get());
                } else {
                    canHoDto.setTaiKhoan(null);
                }
                canHoDto.setChuCanHo(chuCanHo);
                canHoDto.setSoLuongCuDan(setSoLuongCuDan);
                canHoDto.setEmailTaiKhoan(emailTaiKhoan);
                canHoListDto.add(canHoDto);
            }
            if (canHoList.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại căn hộ");
            return canHoListDto;
        } else {
            if (canHoList.isEmpty()) throw new ResourceNotFoundException("Chưa tồn tại căn hộ");
            return canHoMapper.toDto(canHoList);
        }
    }


}