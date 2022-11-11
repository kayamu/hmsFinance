package com.polarbears.capstone.hmsfinance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConditionsMapperTest {

    private ConditionsMapper conditionsMapper;

    @BeforeEach
    public void setUp() {
        conditionsMapper = new ConditionsMapperImpl();
    }
}
