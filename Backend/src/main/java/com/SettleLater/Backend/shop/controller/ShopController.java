package com.SettleLater.Backend.shop.controller;

import com.SettleLater.Backend.shop.dto.ShopRequestDTO;
import com.SettleLater.Backend.shop.dto.ShopResponseDTO;
import com.SettleLater.Backend.shop.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    // CREATE SHOP
    @PostMapping
    public ResponseEntity<ShopResponseDTO> createShop(
            Authentication authentication,
            @RequestBody ShopRequestDTO request
    ) {

        String email = authentication.getName();
        ShopResponseDTO response = shopService.createShop(email, request);

        return ResponseEntity.ok(response);
    }

    //GET ALL SHOPS
    @GetMapping
    public ResponseEntity<List<ShopResponseDTO>> getAllShops(
            Authentication authentication
    ) {

        String userId = authentication.getName();

        List<ShopResponseDTO> shops = shopService.getShopsByUserId(userId);

        return ResponseEntity.ok(shops);
    }

    //GET SHOP BY SHOP ID
    @GetMapping("/{shopId}")
    public ResponseEntity<ShopResponseDTO> getShopById(
            Authentication authentication,
            @PathVariable String shopId
    ) {

        String userId = authentication.getName();

        ShopResponseDTO shop = shopService.getShopByShopId(userId, shopId);

        return ResponseEntity.ok(shop);
    }
}