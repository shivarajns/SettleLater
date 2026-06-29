package com.SettleLater.Backend.shop.service;

import com.SettleLater.Backend.shop.dto.ShopRequestDTO;
import com.SettleLater.Backend.shop.dto.ShopResponseDTO;

import java.util.List;

public interface ShopService {

    // Create shop
    ShopResponseDTO createShop(String userId, ShopRequestDTO request);

    // Get all shops of a user
    List<ShopResponseDTO> getShopsByUserId(String userId);

    // Get shop by public shopId
    ShopResponseDTO getShopByShopId(String userId, String shopId);
}