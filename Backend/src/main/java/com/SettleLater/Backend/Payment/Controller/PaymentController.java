package com.SettleLater.Backend.Payment.Controller;

import com.SettleLater.Backend.Payment.DTO.CreatePaymentRequest;
import com.SettleLater.Backend.Payment.DTO.PaymentResponse;
import com.SettleLater.Backend.Payment.Model.Payment;
import com.SettleLater.Backend.Payment.Service.PaymentService;
import com.SettleLater.Backend.common.ApiResponse.ApiResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        "/api/shops/{shopId}/customers/{customerId}/credits/{creditId}/payments"
)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<PaymentResponse>> createPayment(
            @PathVariable String shopId,
            @PathVariable String customerId,
            @PathVariable String creditId,
            @Valid @RequestBody CreatePaymentRequest request
    ) {

        Payment payment = paymentService.createPayment(
                shopId,
                customerId,
                creditId,
                request
        );

        PaymentResponse paymentResponse =
                PaymentResponse.from(payment);

        ApiResponseDTO<PaymentResponse> response =
                ApiResponseDTO.<PaymentResponse>builder()
                        .success(true)
                        .message("Payment recorded successfully")
                        .data(paymentResponse)
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
