package com.SettleLater.Backend.customer.service;

import com.SettleLater.Backend.common.ApiResponse.ApiResponseDTO;
import com.SettleLater.Backend.customer.dto.CreateCustomerDTO;
import com.SettleLater.Backend.customer.dto.CustomerListResponseDTO;
import com.SettleLater.Backend.customer.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

    ApiResponseDTO<CustomerResponseDTO> createCustomer(String shopId, CreateCustomerDTO request);

    ApiResponseDTO<CustomerListResponseDTO> getCustomers(String shopId, int page, int size);
}
