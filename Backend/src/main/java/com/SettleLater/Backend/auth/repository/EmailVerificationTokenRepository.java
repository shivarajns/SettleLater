package com.SettleLater.Backend.auth.repository;

import com.SettleLater.Backend.auth.model.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
    Optional<EmailVerificationToken> findByUser_UserId(String userId);
    Optional<EmailVerificationToken> findByToken(String token);
    boolean existsByUser_UserId(String userId);
    boolean existsByToken(String token);
}
