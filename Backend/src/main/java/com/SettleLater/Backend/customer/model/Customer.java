package com.SettleLater.Backend.customer.model;

import com.SettleLater.Backend.shop.model.ShopModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers",
        uniqueConstraints = {
            @UniqueConstraint(name = "uk_customer_shop_phone", columnNames = {"shop_id", "phone"})
        },
        indexes = {
            @Index(name = "idx_customer_shop_id", columnList = "shop_id"),
            @Index(name = "idx_customer_name", columnList = "customer_name"),
            @Index(name = "idx_customer_phone", columnList = "phone"),
            @Index(name = "idx_customer_created_at", columnList = "created_at"),
            @Index(name = "idx_customer_is_active", columnList = "is_active"),
        }
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id",
            nullable = false,
            unique = true,
            updatable = false
    )
    private String customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id",
                nullable = false,
                referencedColumnName = "shop_id",
                foreignKey = @ForeignKey(name = "fk_customer_shop")
    )
    private ShopModel shop;

    @Column(name = "customer_name",
            nullable = false
    )
    private String customerName;

    @Column(
            name = "phone",
            nullable = false
    )
    private String phone;

    @Email(message = "Email Not Valid")
    @Column(
            name = "email",
            nullable = true
    )
    private String email;

    @Column(
            name = "address",
            nullable = true
    )
    private String address;

    @Column(
            name = "created_at",
            updatable = false
    )
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            updatable = true
    )
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private boolean isActive;

    @PrePersist
    void prePersist(){
        if(customerId == null){
            this.customerId = UUID.randomUUID().toString();
        }

        if(!isActive){
            this.isActive = true;
        }
    }

    @PreUpdate
    void postUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
