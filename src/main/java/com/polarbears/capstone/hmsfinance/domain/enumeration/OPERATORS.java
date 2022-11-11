package com.polarbears.capstone.hmsfinance.domain.enumeration;

/**
 * The OPERATORS enumeration.
 */
public enum OPERATORS {
    EQUAL("equal"),
    LESS("less"),
    GRATER("grater"),
    LESSEQUAL("lessEqual"),
    GRATEREQUAL("graterEqual"),
    NOTEQUAL("notEqual");

    private final String value;

    OPERATORS(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
