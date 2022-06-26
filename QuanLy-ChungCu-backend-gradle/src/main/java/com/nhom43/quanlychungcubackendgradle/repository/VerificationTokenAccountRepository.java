package com.nhom43.quanlychungcubackendgradle.repository;

import com.nhom43.quanlychungcubackendgradle.entity.VerificationTokenAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenAccountRepository extends JpaRepository<VerificationTokenAccount, Long> {
    Optional<VerificationTokenAccount> findByToken(String token);
}