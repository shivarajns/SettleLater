package com.SettleLater.Backend.auth.repository;

import com.SettleLater.Backend.auth.model.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
    Optional<EmailVerificationToken> findByUserId(int userId);
    boolean existsByUserId(String userId);
    boolean existsByToken(String token);
}
