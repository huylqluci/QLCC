package com.nhom43.quanlychungcubackendgradle.repository;

import com.nhom43.quanlychungcubackendgradle.entity.TheCuDan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheCuDanRepository extends JpaRepository<TheCuDan, Long>, JpaSpecificationExecutor<TheCuDan> {

    TheCuDan findByMaThe (String maThe);

    List<TheCuDan> findAllByCanHo_Id(Long id);

    List<TheCuDan> findAllByDaXoa(Boolean daXoa);

    List<TheCuDan> findAllByDaXoaAndKichHoat(Boolean daXoa, Boolean kichHoat);

    List<TheCuDan> findAllByCanHo_IdAndDaXoa(Long id_canHo, boolean daXoa);

    List<TheCuDan> findAllByCanHo_IdAndDaXoaAndKichHoat(Long id_canHo, boolean daXoa, boolean kichHoat);
}