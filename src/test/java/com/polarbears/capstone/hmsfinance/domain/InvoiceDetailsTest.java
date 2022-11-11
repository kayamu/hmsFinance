package com.polarbears.capstone.hmsfinance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InvoiceDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceDetails.class);
        InvoiceDetails invoiceDetails1 = new InvoiceDetails();
        invoiceDetails1.setId(1L);
        InvoiceDetails invoiceDetails2 = new InvoiceDetails();
        invoiceDetails2.setId(invoiceDetails1.getId());
        assertThat(invoiceDetails1).isEqualTo(invoiceDetails2);
        invoiceDetails2.setId(2L);
        assertThat(invoiceDetails1).isNotEqualTo(invoiceDetails2);
        invoiceDetails1.setId(null);
        assertThat(invoiceDetails1).isNotEqualTo(invoiceDetails2);
    }
}
