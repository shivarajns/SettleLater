package com.SettleLater.Backend.auth.Exceptions;

public class TokenNotFound extends RuntimeException {
    public TokenNotFound(String message) {
        super(message);
    }
}
