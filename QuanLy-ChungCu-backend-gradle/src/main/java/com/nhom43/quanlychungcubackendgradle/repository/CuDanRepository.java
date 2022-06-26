package com.nhom43.quanlychungcubackendgradle.repository;

import com.nhom43.quanlychungcubackendgradle.entity.CanHo;
import com.nhom43.quanlychungcubackendgradle.entity.CuDan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuDanRepository extends JpaRepository<CuDan, Long>, JpaSpecificationExecutor<CuDan> {

    List<CuDan> findByCanHo_Id(Long id);

    CuDan findByCanHoAndChuCanHo(CanHo canHo, boolean chuCanHo);

    Long countByCanHoAndDaXoa(CanHo canHo, boolean trangThai);

//    @Query(value = "SELECT u FROM CuDan u WHERE u.CuDan.CanHo = ?1 and u.status = 'Processing' or u.house.homeStay = ?1 and u.status = 'Refunded' or u.house.homeStay = ?1 and u.status = 'Cancel' ")
//    List<CuDan> findAllByCanHo_Id2(Long id);

    List<CuDan> findAllByDaXoa(Boolean daXoa);

    @Query("SELECT COUNT (u) FROM CuDan u WHERE " +
            "(SUBSTRING(CURRENT_DATE(), 1, 4) - SUBSTRING(u.ngaySinh, 1, 4) < 60) " +
            " and (SUBSTRING(CURRENT_DATE(), 1, 4) - SUBSTRING(u.ngaySinh, 1, 4) > 18)")
    Integer countCuDanByNgaySinh1860();

    @Query("SELECT COUNT (u) FROM CuDan u WHERE " +
            "(SUBSTRING(CURRENT_DATE(), 1, 4) - SUBSTRING(u.ngaySinh, 1, 4) <= 18)")
    Integer countCuDanByNgaySinh18();

    @Query("SELECT COUNT (u) FROM CuDan u WHERE " +
            "(SUBSTRING(CURRENT_DATE(), 1, 4) - SUBSTRING(u.ngaySinh, 1, 4) >= 60)")
    Integer countCuDanByNgaySinh60();

    @Query("SELECT COUNT (u) FROM CuDan u WHERE " +
            "(SUBSTRING(CURRENT_DATE(), 1, 4) - SUBSTRING(u.ngaySinh, 1, 4) <= 18) " +
            " and (SUBSTRING(CURRENT_DATE(), 6, 2) = SUBSTRING(u.ngaySinh, 6, 2))")
    Integer countCuDanByTuoiNhoHon18_CoSinhNhatTrongThang();

    @Query("SELECT u FROM CuDan u WHERE " +
            "(SUBSTRING(CURRENT_DATE(), 6, 2) = SUBSTRING(u.ngaySinh, 6, 2))")
    List<CuDan> findAllBySinhNhatThangNay();
}