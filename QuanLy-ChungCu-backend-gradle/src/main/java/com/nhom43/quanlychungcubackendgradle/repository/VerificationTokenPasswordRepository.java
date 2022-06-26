package com.nhom43.quanlychungcubackendgradle.repository;

import com.nhom43.quanlychungcubackendgradle.entity.VerificationTokenPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationTokenPasswordRepository extends JpaRepository<VerificationTokenPassword, Long> {
    Optional<VerificationTokenPassword> findByToken(String token);

    List<VerificationTokenPassword> findByUser_Id(Long id);
}
