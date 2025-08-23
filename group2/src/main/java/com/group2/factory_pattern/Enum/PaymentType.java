package com.group2.factory_pattern.Enum;

import com.group2.factory_pattern.Exception.PaymentException;

public enum PaymentType {
    CREDIT_CARD("Credit Card"),
    EWALLET("E-Wallet");

    private final String displayName;

    public static PaymentType from(String s) {
        return switch (s.trim().toUpperCase()) {
            case "CREDIT", "CREDIT_CARD", "CARD" -> CREDIT_CARD;
            case "EWALLET", "E-WALLET", "WALLET" -> EWALLET;
            default -> throw new PaymentException("Unsupported payment method: " + s);
        };
    }

    PaymentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
