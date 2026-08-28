package com.SettleLater.Backend.Credit.Controller;

import com.SettleLater.Backend.Credit.DTO.CreateCreditRequest;
import com.SettleLater.Backend.Credit.DTO.CreditResponse;
import com.SettleLater.Backend.Credit.Model.Credit;
import com.SettleLater.Backend.Credit.Service.CreditService;
import com.SettleLater.Backend.common.ApiResponse.ApiResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops/{shopId}/customers/{customerId}/credits")
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<CreditResponse>> createCredit(
            @PathVariable String shopId,
            @PathVariable String customerId,
            @Valid @RequestBody CreateCreditRequest request
    ) {

        Credit credit = creditService.createCredit(
                shopId,
                customerId,
                request
        );

        CreditResponse responseData =
                CreditResponse.from(credit);

        ApiResponseDTO<CreditResponse> response =
                ApiResponseDTO.<CreditResponse>builder()
                        .success(true)
                        .message("Credit created successfully")
                        .data(responseData)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<CreditResponse>>> getAllCredits(
            @PathVariable String shopId,
            @PathVariable String customerId
    ) {

        List<CreditResponse> credits =
                creditService.getAllCredits(
                        shopId,
                        customerId
                );

        ApiResponseDTO<List<CreditResponse>> response =
                ApiResponseDTO.<List<CreditResponse>>builder()
                        .success(true)
                        .message(
                                credits.isEmpty()
                                        ? "No credits found"
                                        : "Credits retrieved successfully"
                        )
                        .data(credits)
                        .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{creditId}")
    public ResponseEntity<ApiResponseDTO<CreditResponse>> getCreditById(
            @PathVariable String shopId,
            @PathVariable String customerId,
            @PathVariable String creditId
    ) {

        CreditResponse creditResponse =
                creditService.getCreditById(
                        shopId,
                        customerId,
                        creditId
                );

        ApiResponseDTO<CreditResponse> response =
                ApiResponseDTO.<CreditResponse>builder()
                        .success(true)
                        .message("Credit retrieved successfully")
                        .data(creditResponse)
                        .build();

        return ResponseEntity.ok(response);
    }
}