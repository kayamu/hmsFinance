package com.polarbears.capstone.hmsfinance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TemplateItemsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateItemsDTO.class);
        TemplateItemsDTO templateItemsDTO1 = new TemplateItemsDTO();
        templateItemsDTO1.setId(1L);
        TemplateItemsDTO templateItemsDTO2 = new TemplateItemsDTO();
        assertThat(templateItemsDTO1).isNotEqualTo(templateItemsDTO2);
        templateItemsDTO2.setId(templateItemsDTO1.getId());
        assertThat(templateItemsDTO1).isEqualTo(templateItemsDTO2);
        templateItemsDTO2.setId(2L);
        assertThat(templateItemsDTO1).isNotEqualTo(templateItemsDTO2);
        templateItemsDTO1.setId(null);
        assertThat(templateItemsDTO1).isNotEqualTo(templateItemsDTO2);
    }
}
