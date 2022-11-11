package com.polarbears.capstone.hmsfinance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InvoiceTransactionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceTransactions.class);
        InvoiceTransactions invoiceTransactions1 = new InvoiceTransactions();
        invoiceTransactions1.setId(1L);
        InvoiceTransactions invoiceTransactions2 = new InvoiceTransactions();
        invoiceTransactions2.setId(invoiceTransactions1.getId());
        assertThat(invoiceTransactions1).isEqualTo(invoiceTransactions2);
        invoiceTransactions2.setId(2L);
        assertThat(invoiceTransactions1).isNotEqualTo(invoiceTransactions2);
        invoiceTransactions1.setId(null);
        assertThat(invoiceTransactions1).isNotEqualTo(invoiceTransactions2);
    }
}
