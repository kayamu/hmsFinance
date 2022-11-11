package com.polarbears.capstone.hmsfinance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InvoicesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Invoices.class);
        Invoices invoices1 = new Invoices();
        invoices1.setId(1L);
        Invoices invoices2 = new Invoices();
        invoices2.setId(invoices1.getId());
        assertThat(invoices1).isEqualTo(invoices2);
        invoices2.setId(2L);
        assertThat(invoices1).isNotEqualTo(invoices2);
        invoices1.setId(null);
        assertThat(invoices1).isNotEqualTo(invoices2);
    }
}
