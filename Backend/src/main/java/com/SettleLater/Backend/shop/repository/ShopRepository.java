package com.SettleLater.Backend.shop.repository;

import com.SettleLater.Backend.shop.model.ShopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<ShopModel, Long> {

    //Get all shops of a user
    List<ShopModel> findByUser_UserId(String userId);

    // Find shop by public shopId
    Optional<ShopModel> findByShopId(String shopId);


    // 4. Check duplicate shop name per user
    boolean existsByUser_UserIdAndNameIgnoreCase(
            String userId,
            String name
    );
}