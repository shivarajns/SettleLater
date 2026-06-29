package com.SettleLater.Backend.shop.service;

import com.SettleLater.Backend.auth.model.User;
import com.SettleLater.Backend.auth.repository.UserRepository;
import com.SettleLater.Backend.shop.dto.ShopRequestDTO;
import com.SettleLater.Backend.shop.dto.ShopResponseDTO;
import com.SettleLater.Backend.shop.model.ShopModel;
import com.SettleLater.Backend.shop.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public ShopServiceImpl(ShopRepository shopRepository,
                           UserRepository userRepository) {
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
    }

    //CREATE SHOP
    @Override
    public ShopResponseDTO createShop(String email, ShopRequestDTO request) {

        //Get User entity
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //Business rule: prevent duplicate shop name per user
        boolean exists = shopRepository
                .existsByUser_UserIdAndNameIgnoreCase(email, request.getName());

        if (exists) {
            throw new RuntimeException("Shop already exists with this name");
        }

        //Map Request → Entity
        ShopModel shop = new ShopModel();

        shop.setUser(user);

        shop.setName(request.getName());
        shop.setPhoneNumber(request.getPhoneNumber());
        shop.setEmail(request.getEmail());
        shop.setBusinessType(request.getBusinessType());
        shop.setGstNumber(request.getGstNumber());

        shop.setAddressLine1(request.getAddressLine1());
        shop.setAddressLine2(request.getAddressLine2());
        shop.setCity(request.getCity());
        shop.setState(request.getState());
        shop.setPincode(request.getPincode());
        shop.setCountry(request.getCountry());

        shop.setCurrency(request.getCurrency());

        //Save
        ShopModel saved = shopRepository.save(shop);

        return mapToResponse(saved);
    }

    //GET SHOPS BY USER
    @Override
    public List<ShopResponseDTO> getShopsByUserId(String userId) {

        return shopRepository.findByUser_UserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    //GET SHOP BY SHOP ID
    @Override
    public ShopResponseDTO getShopByShopId(String userId, String shopId) {

        ShopModel shop = shopRepository.findByShopId(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        // SECURITY CHECK
        if (!shop.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        return mapToResponse(shop);
    }

    //MAPPER
    private ShopResponseDTO mapToResponse(ShopModel shop) {

        ShopResponseDTO dto = new ShopResponseDTO();

        dto.setShopId(shop.getShopId());
        dto.setName(shop.getName());
        dto.setPhoneNumber(shop.getPhoneNumber());
        dto.setEmail(shop.getEmail());
        dto.setBusinessType(shop.getBusinessType());
        dto.setGstNumber(shop.getGstNumber());

        dto.setAddressLine1(shop.getAddressLine1());
        dto.setAddressLine2(shop.getAddressLine2());
        dto.setCity(shop.getCity());
        dto.setState(shop.getState());
        dto.setPincode(shop.getPincode());
        dto.setCountry(shop.getCountry());

        dto.setCurrency(shop.getCurrency());
        dto.setActive(shop.isActive());

        return dto;
    }
}