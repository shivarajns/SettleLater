package com.SettleLater.Backend.auth.exceptions;

public class TokenNotFound extends RuntimeException {
    public TokenNotFound(String message) {
        super(message);
    }
}
