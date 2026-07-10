package com.SettleLater.Backend.customer.service;

import com.SettleLater.Backend.common.ApiResponse.ApiResponseDTO;
import com.SettleLater.Backend.customer.dto.CreateCustomerDTO;
import com.SettleLater.Backend.customer.dto.CustomerResponseDTO;

public interface CustomerService {

    ApiResponseDTO createCustomer(String shopId, CreateCustomerDTO request);
}
