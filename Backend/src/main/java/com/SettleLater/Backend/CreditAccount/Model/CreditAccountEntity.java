package com.SettleLater.Backend.CreditAccount.Model;

import com.SettleLater.Backend.customer.model.Customer;
import com.SettleLater.Backend.shop.model.ShopModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "credit_accounts",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_credit_account_customer",
                        columnNames = "customer_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "account_id",
            nullable = false,
            unique = true,
            updatable = false
    )
    private String accountId;

    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "customer_id",
            nullable = false,
            unique = true,
            referencedColumnName = "customer_id"
    )
    private Customer customer;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "shop_id",
            nullable = false,
            referencedColumnName = "shop_id"
    )
    private ShopModel shop;

    @Column(
            name = "total_credit",
            nullable = false,
            precision = 15,
            scale = 2
    )
    @Builder.Default
    private BigDecimal totalCredit = BigDecimal.ZERO;

    @Column(
            name = "total_paid",
            nullable = false,
            precision = 15,
            scale = 2
    )
    @Builder.Default
    private BigDecimal totalPaid = BigDecimal.ZERO;

    @Column(
            name = "outstanding_balance",
            nullable = false,
            precision = 15,
            scale = 2
    )
    @Builder.Default
    private BigDecimal outstandingBalance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false
    )
    @Builder.Default
    private CreditAccountStatus status = CreditAccountStatus.ACTIVE;

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

        if (accountId == null) {
            accountId = UUID.randomUUID().toString();
        }

        if (totalCredit == null) {
            totalCredit = BigDecimal.ZERO;
        }

        if (totalPaid == null) {
            totalPaid = BigDecimal.ZERO;
        }

        if (outstandingBalance == null) {
            outstandingBalance = BigDecimal.ZERO;
        }

        if (status == null) {
            status = CreditAccountStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    public enum CreditAccountStatus {

        ACTIVE,
        CLOSED
    }
}