package com.SettleLater.Backend.customer.exceptions;

public class CustomerAlreadyExists extends RuntimeException {
    public CustomerAlreadyExists(String message) {
        super(message);
    }
}
