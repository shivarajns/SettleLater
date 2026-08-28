package com.SettleLater.Backend.Payment.DTO;

import com.SettleLater.Backend.Payment.Model.Payment;
import com.SettleLater.Backend.Payment.Model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(

        String paymentId,

        String creditId,

        String customerId,

        BigDecimal amount,

        PaymentStatus status,

        LocalDateTime paymentDate,

        String description,

        LocalDateTime createdAt

) {

    public static PaymentResponse from(Payment payment) {

        return new PaymentResponse(
                payment.getPaymentId(),

                payment.getCredit()
                        .getCreditId(),

                payment.getCredit()
                        .getCreditAccount()
                        .getCustomer()
                        .getCustomerId(),

                payment.getAmount(),

                payment.getStatus(),

                payment.getPaymentDate(),

                payment.getDescription(),

                payment.getCreatedAt()
        );
    }
}