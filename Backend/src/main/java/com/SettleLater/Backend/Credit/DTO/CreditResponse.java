package com.SettleLater.Backend.Credit.DTO;

import com.SettleLater.Backend.Credit.Model.Credit;
import com.SettleLater.Backend.Credit.Model.CreditStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record CreditResponse(

        String creditId,

        String customerId,

        BigDecimal originalAmount,

        BigDecimal outstandingAmount,

        LocalDate creditDate,

        LocalDate dueDate,

        String description,

        CreditStatus status

) {

    public static CreditResponse from(Credit credit) {

        return new CreditResponse(
                credit.getCreditId(),
                credit.getCreditAccount()
                        .getCustomer()
                        .getCustomerId(),
                credit.getOriginalAmount(),
                credit.getOutstandingAmount(),
                credit.getCreditDate(),
                credit.getDueDate(),
                credit.getDescription(),
                credit.getStatus()
        );
    }
}