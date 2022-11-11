package com.polarbears.capstone.hmsfinance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InvoiceDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvoiceDetailsDTO.class);
        InvoiceDetailsDTO invoiceDetailsDTO1 = new InvoiceDetailsDTO();
        invoiceDetailsDTO1.setId(1L);
        InvoiceDetailsDTO invoiceDetailsDTO2 = new InvoiceDetailsDTO();
        assertThat(invoiceDetailsDTO1).isNotEqualTo(invoiceDetailsDTO2);
        invoiceDetailsDTO2.setId(invoiceDetailsDTO1.getId());
        assertThat(invoiceDetailsDTO1).isEqualTo(invoiceDetailsDTO2);
        invoiceDetailsDTO2.setId(2L);
        assertThat(invoiceDetailsDTO1).isNotEqualTo(invoiceDetailsDTO2);
        invoiceDetailsDTO1.setId(null);
        assertThat(invoiceDetailsDTO1).isNotEqualTo(invoiceDetailsDTO2);
    }
}
