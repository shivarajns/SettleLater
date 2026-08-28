package com.SettleLater.Backend.CreditAccount.Exceptions;

public class CreditAccountNotFound extends RuntimeException {
    public CreditAccountNotFound(String message) {
        super(message);
    }
}
