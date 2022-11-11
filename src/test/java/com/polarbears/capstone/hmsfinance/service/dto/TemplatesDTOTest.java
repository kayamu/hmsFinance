package com.polarbears.capstone.hmsfinance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TemplatesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplatesDTO.class);
        TemplatesDTO templatesDTO1 = new TemplatesDTO();
        templatesDTO1.setId(1L);
        TemplatesDTO templatesDTO2 = new TemplatesDTO();
        assertThat(templatesDTO1).isNotEqualTo(templatesDTO2);
        templatesDTO2.setId(templatesDTO1.getId());
        assertThat(templatesDTO1).isEqualTo(templatesDTO2);
        templatesDTO2.setId(2L);
        assertThat(templatesDTO1).isNotEqualTo(templatesDTO2);
        templatesDTO1.setId(null);
        assertThat(templatesDTO1).isNotEqualTo(templatesDTO2);
    }
}
