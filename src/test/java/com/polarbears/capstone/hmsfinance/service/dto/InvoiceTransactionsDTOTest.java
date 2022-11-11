package com.polarbears.capstone.hmsfinance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InvoiceTransactionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceTransactionsDTO.class);
        InvoiceTransactionsDTO invoiceTransactionsDTO1 = new InvoiceTransactionsDTO();
        invoiceTransactionsDTO1.setId(1L);
        InvoiceTransactionsDTO invoiceTransactionsDTO2 = new InvoiceTransactionsDTO();
        assertThat(invoiceTransactionsDTO1).isNotEqualTo(invoiceTransactionsDTO2);
        invoiceTransactionsDTO2.setId(invoiceTransactionsDTO1.getId());
        assertThat(invoiceTransactionsDTO1).isEqualTo(invoiceTransactionsDTO2);
        invoiceTransactionsDTO2.setId(2L);
        assertThat(invoiceTransactionsDTO1).isNotEqualTo(invoiceTransactionsDTO2);
        invoiceTransactionsDTO1.setId(null);
        assertThat(invoiceTransactionsDTO1).isNotEqualTo(invoiceTransactionsDTO2);
    }
}
