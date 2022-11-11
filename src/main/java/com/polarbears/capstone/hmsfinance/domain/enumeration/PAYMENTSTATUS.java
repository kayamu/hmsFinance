package com.polarbears.capstone.hmsfinance.domain.enumeration;

/**
 * The PAYMENTSTATUS enumeration.
 */
public enum PAYMENTSTATUS {
    PAID("paid"),
    CANCELLED("cancelled"),
    WAITING("waiting");

    private final String value;

    PAYMENTSTATUS(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
