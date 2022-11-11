package com.polarbears.capstone.hmsfinance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConditionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConditionsDTO.class);
        ConditionsDTO conditionsDTO1 = new ConditionsDTO();
        conditionsDTO1.setId(1L);
        ConditionsDTO conditionsDTO2 = new ConditionsDTO();
        assertThat(conditionsDTO1).isNotEqualTo(conditionsDTO2);
        conditionsDTO2.setId(conditionsDTO1.getId());
        assertThat(conditionsDTO1).isEqualTo(conditionsDTO2);
        conditionsDTO2.setId(2L);
        assertThat(conditionsDTO1).isNotEqualTo(conditionsDTO2);
        conditionsDTO1.setId(null);
        assertThat(conditionsDTO1).isNotEqualTo(conditionsDTO2);
    }
}
