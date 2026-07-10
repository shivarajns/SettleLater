package com.SettleLater.Backend.customer.repository;

import com.SettleLater.Backend.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByShop_ShopId(String shopId);

    List<Customer> findByShop_ShopIdAndCustomerName(String shopId, String customerName);

    List<Customer> findByShop_ShopIdAndPhone(String shopId, String phone);

    boolean existsByShop_ShopIdAndPhone(String shopId, String phone);

    Page<Customer> findByShop_ShopIdAndIsActiveTrue(String shopId, Pageable pageable);
}
