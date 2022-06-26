package com.nhom43.quanlychungcubackendgradle.repository;

import com.nhom43.quanlychungcubackendgradle.entity.HoaDonDichVu;
import com.nhom43.quanlychungcubackendgradle.entity.HoaDonSuaChua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonSuaChuaRepository extends JpaRepository<HoaDonSuaChua, Long>, JpaSpecificationExecutor<HoaDonSuaChua> {
    List<HoaDonSuaChua> findAllByOrderByTrangThaiAscNgayTaoDesc();

    List<HoaDonSuaChua> findAllByCanHo_IdOrderByTrangThaiAscNgayTaoDesc(Long id_canHo);

    @Query("SELECT COUNT (u) FROM HoaDonSuaChua u WHERE " +
            "(u.trangThai = ?1) " +
            " and (SUBSTRING(CURRENT_DATE(), 1, 4) = SUBSTRING(u.ngayTao, 1, 4))" +
            " and (SUBSTRING(CURRENT_DATE(), 6, 2) - SUBSTRING(u.ngayTao, 6, 2)) = 1")
    Integer countHoaDonSuaChuaThangTruocByTrangThai(Boolean trangThai);

    @Query("SELECT COUNT (u) FROM HoaDonSuaChua u WHERE " +
            "(u.trangThai = ?1) " +
            " and (?2 = SUBSTRING(u.ngayTao, 1, 4))" +
            " and (?3 = SUBSTRING(u.ngayTao, 6, 2))")
    Integer countHoaDonSuaChuaByTrangThaiAndNgayTao(Boolean trangThai, String nam, String thang);

    @Query("SELECT u FROM HoaDonSuaChua u WHERE " +
            "(u.trangThai = ?1) " +
            " and (?2 = SUBSTRING(u.ngayTao, 1, 4))" +
            " and (?3 = SUBSTRING(u.ngayTao, 6, 2))")
    List<HoaDonSuaChua> findAllByTrangThaiAndNgayTao_YearAndNgayTao_Month (Boolean trangThai, String nam, String thang);

}