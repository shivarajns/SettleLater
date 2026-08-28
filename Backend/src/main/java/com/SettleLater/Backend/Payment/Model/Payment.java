package com.SettleLater.Backend.Payment.Model;

import com.SettleLater.Backend.Credit.Model.Credit;
import com.SettleLater.Backend.CreditAccount.Model.CreditAccountEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "payments",
        indexes = {
                @Index(
                        name = "idx_payment_credit_id",
                        columnList = "credit_id"
                ),
                @Index(
                        name = "idx_payment_account_id",
                        columnList = "credit_account_id"
                ),
                @Index(
                        name = "idx_payment_status",
                        columnList = "status"
                ),
                @Index(
                        name = "idx_payment_date",
                        columnList = "payment_date"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "payment_id",
            nullable = false,
            unique = true,
            updatable = false,
            length = 36
    )
    private String paymentId;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "credit_id",
            nullable = false,
            referencedColumnName = "credit_id"
    )
    private Credit credit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "credit_account_id",
            nullable = false,
            referencedColumnName = "account_id"
    )
    private CreditAccountEntity creditAccount;


    @Column(
            name = "amount",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal amount;


    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 20
    )
    @Builder.Default
    private PaymentStatus status = PaymentStatus.RECORDED;


    @Column(
            name = "payment_date",
            nullable = false
    )
    private LocalDateTime paymentDate;


    @Column(
            name = "description",
            length = 500
    )
    private String description;


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

        if (paymentId == null) {
            paymentId = UUID.randomUUID().toString();
        }

        if (paymentDate == null) {
            paymentDate = now;
        }

        if (status == null) {
            status = PaymentStatus.RECORDED;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}