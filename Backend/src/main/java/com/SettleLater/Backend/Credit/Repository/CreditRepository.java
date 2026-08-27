package com.SettleLater.Backend.Credit.Repository;

import com.SettleLater.Backend.Credit.Model.Credit;
import com.SettleLater.Backend.Credit.Model.CreditStatus;
import com.SettleLater.Backend.CreditAccount.Model.CreditAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditRepository extends JpaRepository<Credit, Long> {

    /**
     * Find a credit using its public UUID identifier.
     */
    Optional<Credit> findByCreditId(String creditId);

    /**
     * Find all credits belonging to a specific credit account.
     */
    Page<Credit> findByCreditAccount(
            CreditAccountEntity creditAccount,
            Pageable pageable
    );

    /**
     * Find credits belonging to an account with a specific status.
     */
    Page<Credit> findByCreditAccountAndStatus(
            CreditAccountEntity creditAccount,
            CreditStatus status,
            Pageable pageable
    );

    /**
     * Check whether a credit exists using its public identifier.
     */
    boolean existsByCreditId(String creditId);
}