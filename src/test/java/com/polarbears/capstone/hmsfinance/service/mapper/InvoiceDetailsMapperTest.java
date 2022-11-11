package com.polarbears.capstone.hmsfinance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvoiceDetailsMapperTest {

    private InvoiceDetailsMapper invoiceDetailsMapper;

    @BeforeEach
    public void setUp() {
        invoiceDetailsMapper = new InvoiceDetailsMapperImpl();
    }
}
