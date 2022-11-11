package com.polarbears.capstone.hmsfinance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubItemsMapperTest {

    private SubItemsMapper subItemsMapper;

    @BeforeEach
    public void setUp() {
        subItemsMapper = new SubItemsMapperImpl();
    }
}
