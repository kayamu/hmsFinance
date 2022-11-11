package com.polarbears.capstone.hmsfinance.domain.enumeration;

/**
 * The DETAILTYPES enumeration.
 */
public enum DETAILTYPES {
    EXPENSES("expenses"),
    DISCOUNT("discount"),
    NUTRITIONPAYMENT("nutritionPayment"),
    TAXES("taxes"),
    PROVINCIALTAXES("provincialTaxes"),
    FEDERALTAXES("federalTaxes");

    private final String value;

    DETAILTYPES(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
