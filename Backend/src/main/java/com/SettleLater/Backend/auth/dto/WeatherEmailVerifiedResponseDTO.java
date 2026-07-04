package com.SettleLater.Backend.auth.dto;

public class WeatherEmailVerifiedResponseDTO {
    private String message;
    private boolean isVerified;

    public WeatherEmailVerifiedResponseDTO(String message, boolean isVerified) {
        this.message = message;
        this.isVerified = isVerified;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
