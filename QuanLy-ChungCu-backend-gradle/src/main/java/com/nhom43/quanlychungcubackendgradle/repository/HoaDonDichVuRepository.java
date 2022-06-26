package com.nhom43.quanlychungcubackendgradle.repository;

import com.nhom43.quanlychungcubackendgradle.entity.HoaDonDichVu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Repository
public interface HoaDonDichVuRepository extends JpaRepository<HoaDonDichVu, Long>, JpaSpecificationExecutor<HoaDonDichVu> {

    List<HoaDonDichVu> findAllByOrderByTrangThaiAscNgayTaoDesc();

    List<HoaDonDichVu> findAllByCanHo_IdOrderByTrangThaiAscNgayTaoDesc(Long idCanHo);

    @Query("SELECT COUNT (u) FROM HoaDonDichVu u WHERE " +
            "(u.trangThai = ?1) " +
            " and (SUBSTRING(CURRENT_DATE(), 1, 4) = SUBSTRING(u.ngayTao, 1, 4))" +
            " and (SUBSTRING(CURRENT_DATE(), 6, 2) - SUBSTRING(u.ngayTao, 6, 2)) = 1")
    Integer countHoaDonDichVuThangTruocByTrangThai(Boolean trangThai);


    @Query("SELECT COUNT (u) FROM HoaDonDichVu u WHERE " +
            "(u.trangThai = ?1) " +
            " and (?2 = SUBSTRING(u.ngayTao, 1, 4))" +
            " and (?3 = SUBSTRING(u.ngayTao, 6, 2))")
    Integer countHoaDonDichVuByTrangThaiAndNgayTao(Boolean trangThai, String nam, String thang);

    @Query("SELECT u FROM HoaDonDichVu u WHERE " +
            "(u.trangThai = ?1) " +
            " and (?2 = SUBSTRING(u.ngayTao, 1, 4))" +
            " and (?3 = SUBSTRING(u.ngayTao, 6, 2))")
    List<HoaDonDichVu> findAllByTrangThaiAndNgayTao_YearAndNgayTao_Month (Boolean trangThai, String nam, String thang);

}