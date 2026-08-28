package com.SettleLater.Backend.CreditAccount.Exceptions;

public class CustomerDoesNotBelongsToShop extends RuntimeException {
    public CustomerDoesNotBelongsToShop(String message) {
        super(message);
    }
}
