package com.SettleLater.Backend.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentUserResponseDTO {

    private String userId;
    private String email;
    private Boolean isVerified;
}
