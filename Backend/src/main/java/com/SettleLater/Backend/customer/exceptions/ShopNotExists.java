package com.SettleLater.Backend.customer.exceptions;

public class ShopNotExists extends RuntimeException {
    public ShopNotExists(String message) {
        super(message);
    }
}
