package com.polarbears.capstone.hmsfinance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ItemsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemsDTO.class);
        ItemsDTO itemsDTO1 = new ItemsDTO();
        itemsDTO1.setId(1L);
        ItemsDTO itemsDTO2 = new ItemsDTO();
        assertThat(itemsDTO1).isNotEqualTo(itemsDTO2);
        itemsDTO2.setId(itemsDTO1.getId());
        assertThat(itemsDTO1).isEqualTo(itemsDTO2);
        itemsDTO2.setId(2L);
        assertThat(itemsDTO1).isNotEqualTo(itemsDTO2);
        itemsDTO1.setId(null);
        assertThat(itemsDTO1).isNotEqualTo(itemsDTO2);
    }
}
