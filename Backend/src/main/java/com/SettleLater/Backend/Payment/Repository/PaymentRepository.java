package com.SettleLater.Backend.Payment.Repository;

import com.SettleLater.Backend.Payment.Model.Payment;
import com.SettleLater.Backend.Payment.Model.PaymentStatus;
import com.SettleLater.Backend.Credit.Model.Credit;
import com.SettleLater.Backend.CreditAccount.Model.CreditAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


    Optional<Payment> findByPaymentId(String paymentId);


    Page<Payment> findByCredit(
            Credit credit,
            Pageable pageable
    );


    Page<Payment> findByCreditAccount(
            CreditAccountEntity creditAccount,
            Pageable pageable
    );


    Page<Payment> findByCreditAndStatus(
            Credit credit,
            PaymentStatus status,
            Pageable pageable
    );


    boolean existsByPaymentId(String paymentId);
}