package com.polarbears.capstone.hmsfinance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConditionDetailsMapperTest {

    private ConditionDetailsMapper conditionDetailsMapper;

    @BeforeEach
    public void setUp() {
        conditionDetailsMapper = new ConditionDetailsMapperImpl();
    }
}
