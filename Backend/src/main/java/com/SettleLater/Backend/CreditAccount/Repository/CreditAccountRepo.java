package com.SettleLater.Backend.CreditAccount.Repository;

import com.SettleLater.Backend.CreditAccount.Model.CreditAccountEntity;
import com.SettleLater.Backend.customer.model.Customer;
import com.SettleLater.Backend.shop.model.ShopModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CreditAccountRepo extends JpaRepository<CreditAccountEntity, Long> {

    Optional<CreditAccountEntity> findByAccountId(UUID accountId);

    Optional<CreditAccountEntity> findByCustomer(Customer customer);

    Optional<CreditAccountEntity> findByShopAndCustomer(ShopModel shop, Customer customer);

    boolean existsByCustomer(Customer customer);

    boolean existsByShopAndCustomer(ShopModel shop, Customer customer);
}