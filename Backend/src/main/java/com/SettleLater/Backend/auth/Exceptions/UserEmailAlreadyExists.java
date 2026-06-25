package com.SettleLater.Backend.auth.Exceptions;

public class UserEmailAlreadyExists extends RuntimeException {
    public UserEmailAlreadyExists(String message) {
        super(message);
    }
}
