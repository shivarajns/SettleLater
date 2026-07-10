package com.SettleLater.Backend.customer.controller;

import com.SettleLater.Backend.common.ApiResponse.ApiResponseDTO;
import com.SettleLater.Backend.customer.dto.CreateCustomerDTO;
import com.SettleLater.Backend.customer.dto.CustomerListResponseDTO;
import com.SettleLater.Backend.customer.dto.CustomerResponseDTO;
import com.SettleLater.Backend.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shops")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/{shopId}/customer")
    public ResponseEntity<ApiResponseDTO<CustomerResponseDTO>> createCustomer(
            @PathVariable String shopId,
            @Valid @RequestBody CreateCustomerDTO request
    ){
        ApiResponseDTO<CustomerResponseDTO> response = customerService.createCustomer(shopId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{shopId}/customer")
    public ResponseEntity<ApiResponseDTO<CustomerListResponseDTO>> getShopCustomers(
            @PathVariable String shopId,
            @RequestParam int page,
            @RequestParam int size
    ) {
        ApiResponseDTO<CustomerListResponseDTO> response = customerService.getCustomers(shopId, page, size);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
