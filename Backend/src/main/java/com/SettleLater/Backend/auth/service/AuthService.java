package com.SettleLater.Backend.auth.service;

import com.SettleLater.Backend.auth.dto.CurrentUserResponseDTO;

public interface AuthService {
    CurrentUserResponseDTO getCurrentUser();
}