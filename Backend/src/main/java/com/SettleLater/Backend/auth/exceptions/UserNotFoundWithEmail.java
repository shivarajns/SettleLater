package com.SettleLater.Backend.auth.exceptions;

public class UserNotFoundWithEmail extends RuntimeException {
    public UserNotFoundWithEmail(String message) {
        super(message);
    }
}
