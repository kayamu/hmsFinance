package com.polarbears.capstone.hmsfinance.domain.enumeration;

/**
 * The VALUETYPES enumeration.
 */
public enum VALUETYPES {
    PERCENTAGE("percentage"),
    AMOUNT("amount");

    private final String value;

    VALUETYPES(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
