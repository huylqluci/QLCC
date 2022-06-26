package com.nhom43.quanlychungcubackendgradle.repository;

import com.nhom43.quanlychungcubackendgradle.entity.ThongBaoRieng;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongBaoRiengRepository extends JpaRepository<ThongBaoRieng, Long>, JpaSpecificationExecutor<ThongBaoRieng> {

    List<ThongBaoRieng> findAllByCanHo_Id(Long id);


    List<ThongBaoRieng> findAllByCanHo_IdOrderByTrangThaiDescNgayTaoDesc(Long id);

}