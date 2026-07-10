package com.SettleLater.Backend.customer.service;

import com.SettleLater.Backend.common.ApiResponse.ApiResponseDTO;
import com.SettleLater.Backend.customer.dto.CreateCustomerDTO;
import com.SettleLater.Backend.customer.dto.CustomerListResponseDTO;
import com.SettleLater.Backend.customer.dto.CustomerResponseDTO;
import com.SettleLater.Backend.customer.exceptions.CustomerAlreadyExists;
import com.SettleLater.Backend.customer.exceptions.ShopNotExists;
import com.SettleLater.Backend.customer.model.Customer;
import com.SettleLater.Backend.customer.repository.CustomerRepository;
import com.SettleLater.Backend.shop.model.ShopModel;
import com.SettleLater.Backend.shop.repository.ShopRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;

    public CustomerServiceImp(CustomerRepository customerRepository, ShopRepository shopRepository) {
        this.customerRepository = customerRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    @Transactional
    public ApiResponseDTO<CustomerResponseDTO> createCustomer(String shopId, CreateCustomerDTO request) {

        if (customerRepository.existsByShop_ShopIdAndPhone(shopId, request.getPhone())) {
            throw new CustomerAlreadyExists("Customer Already Exists with Phone number");
        }

        ShopModel shop = shopRepository.findByShopId(shopId)
                .orElseThrow(() -> new ShopNotExists("Shop Not Found"));

        Customer customer = new Customer();
        customer.setShop(shop);
        customer.setCustomerName(request.getCustomerName());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());

        Customer savedCustomer = customerRepository.save(customer);

        CustomerResponseDTO response = CustomerResponseDTO.builder()
                .customerId(savedCustomer.getCustomerId())
                .customerName(savedCustomer.getCustomerName())
                .phone(savedCustomer.getPhone())
                .email(savedCustomer.getEmail())
                .address(savedCustomer.getAddress())
                .isActive(savedCustomer.isActive())
                .createdAt(savedCustomer.getCreatedAt())
                .build();

        return ApiResponseDTO.<CustomerResponseDTO>builder()
                .success(true)
                .message("Customer created successfully")
                .data(response)
                .build();

    }

    @Override
    public ApiResponseDTO<CustomerListResponseDTO> getCustomers(String shopId, int page, int size) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<Customer> customerPage = customerRepository.findByShop_ShopIdAndIsActiveTrue(
                shopId,
                pageable
        );

        List<CustomerResponseDTO> customers =
                customerPage.getContent()
                        .stream()
                        .map(c -> CustomerResponseDTO.builder()
                                .customerId(c.getCustomerId())
                                .customerName(c.getCustomerName())
                                .phone(c.getPhone())
                                .email(c.getEmail())
                                .address(c.getAddress())
                                .isActive(c.isActive())
                                .createdAt(c.getCreatedAt())
                                .build()
                        ).toList();

        CustomerListResponseDTO response =
                CustomerListResponseDTO.builder()
                        .customers(customers)
                        .currentPage(customerPage.getNumber())
                        .totalPages(customerPage.getTotalPages())
                        .totalElements(customerPage.getTotalElements())
                        .hasNext(customerPage.hasNext())
                        .hasPrevious(customerPage.hasPrevious())
                        .build();


        return ApiResponseDTO.<CustomerListResponseDTO>builder()
                .success(true)
                .message("Customers fetched successfully")
                .data(response)
                .build();
    }
}
