package com.nhom43.quanlychungcubackendgradle.repository;

import com.nhom43.quanlychungcubackendgradle.entity.CanHo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CanHoRepository extends JpaRepository<CanHo, Long>, JpaSpecificationExecutor<CanHo> {

    List<CanHo> findAllByTrangThai(boolean b);

    CanHo getCanHoByIdTaiKhoan(Long idTaiKhoan);
}