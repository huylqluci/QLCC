package com.nhom43.quanlychungcubackendgradle.repository;

import com.nhom43.quanlychungcubackendgradle.entity.CanHo;
import com.nhom43.quanlychungcubackendgradle.entity.CuDan;
import com.nhom43.quanlychungcubackendgradle.entity.PhuongTien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhuongTienRepository extends JpaRepository<PhuongTien, Long>, JpaSpecificationExecutor<PhuongTien> {

    List<PhuongTien> findAllByDaXoa(Boolean daXoa);
    List<PhuongTien> findAllByDaXoaAndCanHo_Id(Boolean daXoa, Long id);
    List<PhuongTien> findAllByLoaiXe (Long id);
    List<PhuongTien> findAllByCanHo_Id(Long id);
    int countPhuongTienByCanHoAndLoaiXeAndDaXoa (CanHo canHo, String loaiXe, boolean daXoa);

}