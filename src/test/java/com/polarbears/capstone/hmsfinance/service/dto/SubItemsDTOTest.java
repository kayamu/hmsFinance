package com.polarbears.capstone.hmsfinance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubItemsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubItemsDTO.class);
        SubItemsDTO subItemsDTO1 = new SubItemsDTO();
        subItemsDTO1.setId(1L);
        SubItemsDTO subItemsDTO2 = new SubItemsDTO();
        assertThat(subItemsDTO1).isNotEqualTo(subItemsDTO2);
        subItemsDTO2.setId(subItemsDTO1.getId());
        assertThat(subItemsDTO1).isEqualTo(subItemsDTO2);
        subItemsDTO2.setId(2L);
        assertThat(subItemsDTO1).isNotEqualTo(subItemsDTO2);
        subItemsDTO1.setId(null);
        assertThat(subItemsDTO1).isNotEqualTo(subItemsDTO2);
    }
}
