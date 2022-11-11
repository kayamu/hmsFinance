package com.polarbears.capstone.hmsfinance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvoiceTransactionsMapperTest {

    private InvoiceTransactionsMapper invoiceTransactionsMapper;

    @BeforeEach
    public void setUp() {
        invoiceTransactionsMapper = new InvoiceTransactionsMapperImpl();
    }
}
