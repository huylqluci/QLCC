package com.nhom43.quanlychungcubackendgradle.repository;

import com.nhom43.quanlychungcubackendgradle.entity.ChiTietHoaDonSuaChua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietHoaDonSuaChuaRepository extends JpaRepository<ChiTietHoaDonSuaChua, Long>, JpaSpecificationExecutor<ChiTietHoaDonSuaChua> {
    List<ChiTietHoaDonSuaChua> findByHoaDonSuaChua_Id(Long id);
}