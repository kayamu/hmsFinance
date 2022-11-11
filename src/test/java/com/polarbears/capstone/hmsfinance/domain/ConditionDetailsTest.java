package com.polarbears.capstone.hmsfinance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConditionDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConditionDetails.class);
        ConditionDetails conditionDetails1 = new ConditionDetails();
        conditionDetails1.setId(1L);
        ConditionDetails conditionDetails2 = new ConditionDetails();
        conditionDetails2.setId(conditionDetails1.getId());
        assertThat(conditionDetails1).isEqualTo(conditionDetails2);
        conditionDetails2.setId(2L);
        assertThat(conditionDetails1).isNotEqualTo(conditionDetails2);
        conditionDetails1.setId(null);
        assertThat(conditionDetails1).isNotEqualTo(conditionDetails2);
    }
}
