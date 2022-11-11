package com.polarbears.capstone.hmsfinance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbears.capstone.hmsfinance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubItemsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubItems.class);
        SubItems subItems1 = new SubItems();
        subItems1.setId(1L);
        SubItems subItems2 = new SubItems();
        subItems2.setId(subItems1.getId());
        assertThat(subItems1).isEqualTo(subItems2);
        subItems2.setId(2L);
        assertThat(subItems1).isNotEqualTo(subItems2);
        subItems1.setId(null);
        assertThat(subItems1).isNotEqualTo(subItems2);
    }
}
