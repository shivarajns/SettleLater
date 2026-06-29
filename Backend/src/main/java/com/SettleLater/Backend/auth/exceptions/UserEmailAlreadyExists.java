package com.SettleLater.Backend.auth.exceptions;

public class UserEmailAlreadyExists extends RuntimeException {
    public UserEmailAlreadyExists(String message) {
        super(message);
    }
}
