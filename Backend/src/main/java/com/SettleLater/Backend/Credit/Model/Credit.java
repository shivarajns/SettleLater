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

    @Column(
            name = "credit_id",
            nullable = false,
            unique = true,
            updatable = false,
            length = 36
    )
    private String creditId;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "credit_account_id",
            nullable = false,
            referencedColumnName = "account_id"
    )
    private CreditAccountEntity creditAccount;


    @Column(
            name = "original_amount",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal originalAmount;


    @Column(
            name = "outstanding_amount",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal outstandingAmount;


    @Column(
            name = "credit_date",
            nullable = false
    )
    private LocalDate creditDate;


    @Column(
            name = "due_date",
            nullable = false
    )
    private LocalDate dueDate;


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