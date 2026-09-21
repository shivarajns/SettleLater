package com.SettleLater.Backend.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {

    private String customerId;

    private String customerName;

    private String phone;

    private String email;

    private String address;

    private boolean isActive;

    private LocalDateTime createdAt;

    private String ShopName;

    private String shopId;
}
