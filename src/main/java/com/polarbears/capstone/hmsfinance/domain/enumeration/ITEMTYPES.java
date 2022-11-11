package com.polarbears.capstone.hmsfinance.domain.enumeration;

/**
 * The ITEMTYPES enumeration.
 */
public enum ITEMTYPES {
    PRODUCT("Product"),
    SERVICE("Service");

    private final String value;

    ITEMTYPES(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
