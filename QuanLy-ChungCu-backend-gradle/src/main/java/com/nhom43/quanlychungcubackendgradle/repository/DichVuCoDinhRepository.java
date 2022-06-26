package com.nhom43.quanlychungcubackendgradle.repository;

import com.nhom43.quanlychungcubackendgradle.entity.DichVuCoDinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DichVuCoDinhRepository extends JpaRepository<DichVuCoDinh, Long>, JpaSpecificationExecutor<DichVuCoDinh> {
    DichVuCoDinh findFirstByTen (String ten);
}