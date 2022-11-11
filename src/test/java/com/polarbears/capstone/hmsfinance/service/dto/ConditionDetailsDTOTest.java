package com.polarbears.capstone.hmsfinance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConditionDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConditionDetailsDTO.class);
        ConditionDetailsDTO conditionDetailsDTO1 = new ConditionDetailsDTO();
        conditionDetailsDTO1.setId(1L);
        ConditionDetailsDTO conditionDetailsDTO2 = new ConditionDetailsDTO();
        assertThat(conditionDetailsDTO1).isNotEqualTo(conditionDetailsDTO2);
        conditionDetailsDTO2.setId(conditionDetailsDTO1.getId());
        assertThat(conditionDetailsDTO1).isEqualTo(conditionDetailsDTO2);
        conditionDetailsDTO2.setId(2L);
        assertThat(conditionDetailsDTO1).isNotEqualTo(conditionDetailsDTO2);
        conditionDetailsDTO1.setId(null);
        assertThat(conditionDetailsDTO1).isNotEqualTo(conditionDetailsDTO2);
    }
}
