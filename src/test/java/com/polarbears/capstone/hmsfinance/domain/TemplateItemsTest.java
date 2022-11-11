package com.polarbears.capstone.hmsfinance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TemplateItemsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateItems.class);
        TemplateItems templateItems1 = new TemplateItems();
        templateItems1.setId(1L);
        TemplateItems templateItems2 = new TemplateItems();
        templateItems2.setId(templateItems1.getId());
        assertThat(templateItems1).isEqualTo(templateItems2);
        templateItems2.setId(2L);
        assertThat(templateItems1).isNotEqualTo(templateItems2);
        templateItems1.setId(null);
        assertThat(templateItems1).isNotEqualTo(templateItems2);
    }
}
