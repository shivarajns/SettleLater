package com.SettleLater.Backend.Credit.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateCreditRequest(

        @NotNull(message = "Credit amount is required")
        @DecimalMin(
                value = "0.01",
                message = "Credit amount must be greater than zero"
        )
        BigDecimal amount,

        @NotNull(message = "Due date is required")
        LocalDate dueDate,

        @Size(
                max = 500,
                message = "Description cannot exceed 500 characters"
        )
        String description

) {
}