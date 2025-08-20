package com.group2.factory_pattern.Enum;

public enum PaymentType {
    CREDIT_CARD("Credit Card"),
    EWALLET("E-Wallet");

    private final String displayName;

    PaymentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
