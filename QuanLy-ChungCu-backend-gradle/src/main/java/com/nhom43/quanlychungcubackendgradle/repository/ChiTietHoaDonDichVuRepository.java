package com.nhom43.quanlychungcubackendgradle.repository;

import com.nhom43.quanlychungcubackendgradle.entity.ChiTietHoaDonDichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietHoaDonDichVuRepository extends JpaRepository<ChiTietHoaDonDichVu, Long>, JpaSpecificationExecutor<ChiTietHoaDonDichVu> {
    List<ChiTietHoaDonDichVu> findByHoaDonDichVu_Id(Long id);
}