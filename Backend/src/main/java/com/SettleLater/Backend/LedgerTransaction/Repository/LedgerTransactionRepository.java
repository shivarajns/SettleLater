package com.SettleLater.Backend.LedgerTransaction.Repository;

import com.SettleLater.Backend.LedgerTransaction.Model.LedgerTransaction;
import com.SettleLater.Backend.LedgerTransaction.Model.TransactionType;
import com.SettleLater.Backend.Credit.Model.Credit;
import com.SettleLater.Backend.CreditAccount.Model.CreditAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LedgerTransactionRepository
        extends JpaRepository<LedgerTransaction, Long> {

    /**
     * Find a transaction using its public identifier.
     */
    Optional<LedgerTransaction> findByTransactionId(String transactionId);

    /**
     * Retrieve all transactions belonging to a credit account.
     */
    Page<LedgerTransaction> findByCreditAccount(
            CreditAccountEntity creditAccount,
            Pageable pageable
    );

    /**
     * Retrieve all transactions associated with a specific credit.
     */
    Page<LedgerTransaction> findByCredit(
            Credit credit,
            Pageable pageable
    );

    /**
     * Retrieve transactions of a specific type for an account.
     */
    Page<LedgerTransaction> findByCreditAccountAndTransactionType(
            CreditAccountEntity creditAccount,
            TransactionType transactionType,
            Pageable pageable
    );

    /**
     * Check whether a transaction exists using its public identifier.
     */
    boolean existsByTransactionId(String transactionId);
}