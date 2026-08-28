package com.SettleLater.Backend.LedgerTransaction.Model;

import com.SettleLater.Backend.Credit.Model.Credit;
import com.SettleLater.Backend.CreditAccount.Model.CreditAccountEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "ledger_transactions",
        indexes = {
                @Index(
                        name = "idx_ledger_account_id",
                        columnList = "credit_account_id"
                ),
                @Index(
                        name = "idx_ledger_credit_id",
                        columnList = "credit_id"
                ),
                @Index(
                        name = "idx_ledger_transaction_type",
                        columnList = "transaction_type"
                ),
                @Index(
                        name = "idx_ledger_transaction_date",
                        columnList = "transaction_date"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Public identifier for the transaction.
     */
    @Column(
            name = "transaction_id",
            nullable = false,
            unique = true,
            updatable = false,
            length = 36
    )
    private String transactionId;

    /**
     * The account to which this transaction belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "credit_account_id",
            nullable = false
    )
    private CreditAccountEntity creditAccount;

    /**
     * The individual credit associated with this transaction.
     *
     * Nullable because some future account-level transactions
     * may not belong to a specific Credit.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "credit_id"
    )
    private Credit credit;


    /**
     * Type of financial transaction.
     */
    @Enumerated(EnumType.STRING)
    @Column(
            name = "transaction_type",
            nullable = false,
            length = 30
    )
    private TransactionType transactionType;

    /**
     * Amount involved in this transaction.
     */
    @Column(
            name = "amount",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal amount;

    /**
     * Date and time when the transaction occurred.
     */
    @Column(
            name = "transaction_date",
            nullable = false
    )
    private LocalDateTime transactionDate;

    /**
     * Optional description associated with the transaction.
     */
    @Column(
            name = "description",
            length = 500
    )
    private String description;

    /**
     * Used when a transaction is reversed.
     *
     * Example:
     *
     * Original PAYMENT
     *        ↓
     * REVERSAL
     *        ↓
     * referenceTransactionId = original transaction
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "reference_transaction_id"
    )
    private LedgerTransaction referenceTransaction;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;

        if (transactionId == null) {
            transactionId = UUID.randomUUID().toString();
        }

        if (transactionDate == null) {
            transactionDate = now;
        }
    }
}