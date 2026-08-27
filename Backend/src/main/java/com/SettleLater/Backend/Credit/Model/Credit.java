package com.SettleLater.Backend.Credit.Model;


import com.SettleLater.Backend.CreditAccount.Model.CreditAccountEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "credits",
        indexes = {
                @Index(
                        name = "idx_credit_account_id",
                        columnList = "credit_account_id"
                ),
                @Index(
                        name = "idx_credit_due_date",
                        columnList = "due_date"
                ),
                @Index(
                        name = "idx_credit_status",
                        columnList = "status"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Public identifier for the credit.
     * The internal database ID should not be exposed through APIs.
     */
    @Column(
            name = "credit_id",
            nullable = false,
            unique = true,
            updatable = false,
            length = 36
    )
    private String creditId;

    /**
     * Credit belongs to exactly one CreditAccount.
     * A CreditAccount can contain multiple Credit records.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "credit_account_id",
            nullable = false
    )
    private CreditAccountEntity creditAccount;

    /**
     * Original amount of the credit.
     * This value represents the amount recorded when the credit was created
     * and should not be directly modified after creation.
     */
    @Column(
            name = "original_amount",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal originalAmount;

    /**
     * Current unpaid amount for this credit.
     */
    @Column(
            name = "outstanding_amount",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal outstandingAmount;

    /**
     * Date on which the credit was created.
     */
    @Column(
            name = "credit_date",
            nullable = false
    )
    private LocalDate creditDate;

    /**
     * Mandatory date by which the credit is expected to be paid.
     */
    @Column(
            name = "due_date",
            nullable = false
    )
    private LocalDate dueDate;

    /**
     * Optional description of the credit.
     * Example: Grocery Items, Household Items, etc.
     */
    @Column(
            name = "description",
            length = 500
    )
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 30
    )
    @Builder.Default
    private CreditStatus status = CreditStatus.UNSETTLED;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        if (creditId == null) {
            creditId = UUID.randomUUID().toString();
        }

        if (status == null) {
            status = CreditStatus.UNSETTLED;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}