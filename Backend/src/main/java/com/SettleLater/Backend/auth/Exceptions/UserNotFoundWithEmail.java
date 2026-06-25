package com.SettleLater.Backend.auth.Exceptions;

public class UserNotFoundWithEmail extends RuntimeException {
    public UserNotFoundWithEmail(String message) {
        super(message);
    }
}
