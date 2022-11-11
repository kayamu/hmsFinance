package com.polarbears.capstone.hmsfinance.domain.enumeration;

/**
 * The INVOICETYPES enumeration.
 */
public enum INVOICETYPES {
    PROPOSAL("proposal"),
    CANCELLED("cancelled"),
    BILLED("billed"),
    PAID("paid");

    private final String value;

    INVOICETYPES(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
