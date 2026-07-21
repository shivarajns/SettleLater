package com.SettleLater.Backend.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
public record CustomerListResponseDTO(
        List<CustomerResponseDTO> customers,
        int currentPage,
        int totalPages,
        long totalElements,
        boolean hasNext,
        boolean hasPrevious
) {}