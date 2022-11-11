package com.polarbears.capstone.hmsfinance.domain.enumeration;

/**
 * The LOGICTYPES enumeration.
 */
public enum LOGICTYPES {
    AND("and"),
    OR("or");

    private final String value;

    LOGICTYPES(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
