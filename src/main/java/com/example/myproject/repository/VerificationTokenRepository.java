package com.example.myproject.repository;

import com.example.myproject.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Integer> {
    Optional<VerificationToken> findByToken(String token);
}
