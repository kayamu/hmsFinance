package com.polarbears.capstone.hmsfinance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InvoicesMapperTest {

    private InvoicesMapper invoicesMapper;

    @BeforeEach
    public void setUp() {
        invoicesMapper = new InvoicesMapperImpl();
    }
}
