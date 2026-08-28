package com.SettleLater.Backend.Credit.Repository;

import com.SettleLater.Backend.Credit.Model.Credit;
import com.SettleLater.Backend.Credit.Model.CreditStatus;
import com.SettleLater.Backend.CreditAccount.Model.CreditAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    Optional<Credit> findByCreditId(String creditId);

    Page<Credit> findByCreditAccount(
            CreditAccountEntity creditAccount,
            Pageable pageable
    );

    Page<Credit> findByCreditAccountAndStatus(
            CreditAccountEntity creditAccount,
            CreditStatus status,
            Pageable pageable
    );

    boolean existsByCreditId(String creditId);
}