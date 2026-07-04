package com.SettleLater.Backend.auth.dto;

public class LoginResponseDTO {
    private String message;
    private String token;
    private boolean emailVerified;

    public LoginResponseDTO(String message, String token, Boolean emailVerified) {
        this.message = message;
        this.token = token;
        this.emailVerified=emailVerified;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
