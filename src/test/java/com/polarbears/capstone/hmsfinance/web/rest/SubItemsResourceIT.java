package com.polarbears.capstone.hmsfinance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmsfinance.IntegrationTest;
import com.polarbears.capstone.hmsfinance.domain.InvoiceDetails;
import com.polarbears.capstone.hmsfinance.domain.SubItems;
import com.polarbears.capstone.hmsfinance.domain.enumeration.DETAILTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.VALUETYPES;
import com.polarbears.capstone.hmsfinance.repository.SubItemsRepository;
import com.polarbears.capstone.hmsfinance.service.criteria.SubItemsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.SubItemsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.SubItemsMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubItemsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubItemsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_ACTUAL_VALUE = 1D;
    private static final Double UPDATED_ACTUAL_VALUE = 2D;
    private static final Double SMALLER_ACTUAL_VALUE = 1D - 1D;

    private static final Double DEFAULT_PERCENTAGE = 1D;
    private static final Double UPDATED_PERCENTAGE = 2D;
    private static final Double SMALLER_PERCENTAGE = 1D - 1D;

    private static final Double DEFAULT_BASE_VALUE = 1D;
    private static final Double UPDATED_BASE_VALUE = 2D;
    private static final Double SMALLER_BASE_VALUE = 1D - 1D;

    private static final DETAILTYPES DEFAULT_TYPE = DETAILTYPES.EXPENSES;
    private static final DETAILTYPES UPDATED_TYPE = DETAILTYPES.DISCOUNT;

    private static final VALUETYPES DEFAULT_VALUE_TYPE = VALUETYPES.PERCENTAGE;
    private static final VALUETYPES UPDATED_VALUE_TYPE = VALUETYPES.AMOUNT;

    private static final Double DEFAULT_CALCULATED_VALUE = 1D;
    private static final Double UPDATED_CALCULATED_VALUE = 2D;
    private static final Double SMALLER_CALCULATED_VALUE = 1D - 1D;

    private static final Long DEFAULT_TEMPLATE_ITEM_ID = 1L;
    private static final Long UPDATED_TEMPLATE_ITEM_ID = 2L;
    private static final Long SMALLER_TEMPLATE_ITEM_ID = 1L - 1L;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/sub-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubItemsRepository subItemsRepository;

    @Autowired
    private SubItemsMapper subItemsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubItemsMockMvc;

    private SubItems subItems;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubItems createEntity(EntityManager em) {
        SubItems subItems = new SubItems()
            .name(DEFAULT_NAME)
            .actualValue(DEFAULT_ACTUAL_VALUE)
            .percentage(DEFAULT_PERCENTAGE)
            .baseValue(DEFAULT_BASE_VALUE)
            .type(DEFAULT_TYPE)
            .valueType(DEFAULT_VALUE_TYPE)
            .calculatedValue(DEFAULT_CALCULATED_VALUE)
            .templateItemId(DEFAULT_TEMPLATE_ITEM_ID)
            .createdDate(DEFAULT_CREATED_DATE);
        return subItems;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubItems createUpdatedEntity(EntityManager em) {
        SubItems subItems = new SubItems()
            .name(UPDATED_NAME)
            .actualValue(UPDATED_ACTUAL_VALUE)
            .percentage(UPDATED_PERCENTAGE)
            .baseValue(UPDATED_BASE_VALUE)
            .type(UPDATED_TYPE)
            .valueType(UPDATED_VALUE_TYPE)
            .calculatedValue(UPDATED_CALCULATED_VALUE)
            .templateItemId(UPDATED_TEMPLATE_ITEM_ID)
            .createdDate(UPDATED_CREATED_DATE);
        return subItems;
    }

    @BeforeEach
    public void initTest() {
        subItems = createEntity(em);
    }

    @Test
    @Transactional
    void createSubItems() throws Exception {
        int databaseSizeBeforeCreate = subItemsRepository.findAll().size();
        // Create the SubItems
        SubItemsDTO subItemsDTO = subItemsMapper.toDto(subItems);
        restSubItemsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subItemsDTO)))
            .andExpect(status().isCreated());

        // Validate the SubItems in the database
        List<SubItems> subItemsList = subItemsRepository.findAll();
        assertThat(subItemsList).hasSize(databaseSizeBeforeCreate + 1);
        SubItems testSubItems = subItemsList.get(subItemsList.size() - 1);
        assertThat(testSubItems.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSubItems.getActualValue()).isEqualTo(DEFAULT_ACTUAL_VALUE);
        assertThat(testSubItems.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testSubItems.getBaseValue()).isEqualTo(DEFAULT_BASE_VALUE);
        assertThat(testSubItems.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSubItems.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testSubItems.getCalculatedValue()).isEqualTo(DEFAULT_CALCULATED_VALUE);
        assertThat(testSubItems.getTemplateItemId()).isEqualTo(DEFAULT_TEMPLATE_ITEM_ID);
        assertThat(testSubItems.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createSubItemsWithExistingId() throws Exception {
        // Create the SubItems with an existing ID
        subItems.setId(1L);
        SubItemsDTO subItemsDTO = subItemsMapper.toDto(subItems);

        int databaseSizeBeforeCreate = subItemsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubItemsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subItemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubItems in the database
        List<SubItems> subItemsList = subItemsRepository.findAll();
        assertThat(subItemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubItems() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList
        restSubItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].actualValue").value(hasItem(DEFAULT_ACTUAL_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].baseValue").value(hasItem(DEFAULT_BASE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].calculatedValue").value(hasItem(DEFAULT_CALCULATED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].templateItemId").value(hasItem(DEFAULT_TEMPLATE_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getSubItems() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get the subItems
        restSubItemsMockMvc
            .perform(get(ENTITY_API_URL_ID, subItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subItems.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.actualValue").value(DEFAULT_ACTUAL_VALUE.doubleValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.baseValue").value(DEFAULT_BASE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE.toString()))
            .andExpect(jsonPath("$.calculatedValue").value(DEFAULT_CALCULATED_VALUE.doubleValue()))
            .andExpect(jsonPath("$.templateItemId").value(DEFAULT_TEMPLATE_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getSubItemsByIdFiltering() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        Long id = subItems.getId();

        defaultSubItemsShouldBeFound("id.equals=" + id);
        defaultSubItemsShouldNotBeFound("id.notEquals=" + id);

        defaultSubItemsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubItemsShouldNotBeFound("id.greaterThan=" + id);

        defaultSubItemsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubItemsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSubItemsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where name equals to DEFAULT_NAME
        defaultSubItemsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the subItemsList where name equals to UPDATED_NAME
        defaultSubItemsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSubItemsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSubItemsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the subItemsList where name equals to UPDATED_NAME
        defaultSubItemsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSubItemsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where name is not null
        defaultSubItemsShouldBeFound("name.specified=true");

        // Get all the subItemsList where name is null
        defaultSubItemsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllSubItemsByNameContainsSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where name contains DEFAULT_NAME
        defaultSubItemsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the subItemsList where name contains UPDATED_NAME
        defaultSubItemsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSubItemsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where name does not contain DEFAULT_NAME
        defaultSubItemsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the subItemsList where name does not contain UPDATED_NAME
        defaultSubItemsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSubItemsByActualValueIsEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where actualValue equals to DEFAULT_ACTUAL_VALUE
        defaultSubItemsShouldBeFound("actualValue.equals=" + DEFAULT_ACTUAL_VALUE);

        // Get all the subItemsList where actualValue equals to UPDATED_ACTUAL_VALUE
        defaultSubItemsShouldNotBeFound("actualValue.equals=" + UPDATED_ACTUAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByActualValueIsInShouldWork() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where actualValue in DEFAULT_ACTUAL_VALUE or UPDATED_ACTUAL_VALUE
        defaultSubItemsShouldBeFound("actualValue.in=" + DEFAULT_ACTUAL_VALUE + "," + UPDATED_ACTUAL_VALUE);

        // Get all the subItemsList where actualValue equals to UPDATED_ACTUAL_VALUE
        defaultSubItemsShouldNotBeFound("actualValue.in=" + UPDATED_ACTUAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByActualValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where actualValue is not null
        defaultSubItemsShouldBeFound("actualValue.specified=true");

        // Get all the subItemsList where actualValue is null
        defaultSubItemsShouldNotBeFound("actualValue.specified=false");
    }

    @Test
    @Transactional
    void getAllSubItemsByActualValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where actualValue is greater than or equal to DEFAULT_ACTUAL_VALUE
        defaultSubItemsShouldBeFound("actualValue.greaterThanOrEqual=" + DEFAULT_ACTUAL_VALUE);

        // Get all the subItemsList where actualValue is greater than or equal to UPDATED_ACTUAL_VALUE
        defaultSubItemsShouldNotBeFound("actualValue.greaterThanOrEqual=" + UPDATED_ACTUAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByActualValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where actualValue is less than or equal to DEFAULT_ACTUAL_VALUE
        defaultSubItemsShouldBeFound("actualValue.lessThanOrEqual=" + DEFAULT_ACTUAL_VALUE);

        // Get all the subItemsList where actualValue is less than or equal to SMALLER_ACTUAL_VALUE
        defaultSubItemsShouldNotBeFound("actualValue.lessThanOrEqual=" + SMALLER_ACTUAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByActualValueIsLessThanSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where actualValue is less than DEFAULT_ACTUAL_VALUE
        defaultSubItemsShouldNotBeFound("actualValue.lessThan=" + DEFAULT_ACTUAL_VALUE);

        // Get all the subItemsList where actualValue is less than UPDATED_ACTUAL_VALUE
        defaultSubItemsShouldBeFound("actualValue.lessThan=" + UPDATED_ACTUAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByActualValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where actualValue is greater than DEFAULT_ACTUAL_VALUE
        defaultSubItemsShouldNotBeFound("actualValue.greaterThan=" + DEFAULT_ACTUAL_VALUE);

        // Get all the subItemsList where actualValue is greater than SMALLER_ACTUAL_VALUE
        defaultSubItemsShouldBeFound("actualValue.greaterThan=" + SMALLER_ACTUAL_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where percentage equals to DEFAULT_PERCENTAGE
        defaultSubItemsShouldBeFound("percentage.equals=" + DEFAULT_PERCENTAGE);

        // Get all the subItemsList where percentage equals to UPDATED_PERCENTAGE
        defaultSubItemsShouldNotBeFound("percentage.equals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSubItemsByPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where percentage in DEFAULT_PERCENTAGE or UPDATED_PERCENTAGE
        defaultSubItemsShouldBeFound("percentage.in=" + DEFAULT_PERCENTAGE + "," + UPDATED_PERCENTAGE);

        // Get all the subItemsList where percentage equals to UPDATED_PERCENTAGE
        defaultSubItemsShouldNotBeFound("percentage.in=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSubItemsByPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where percentage is not null
        defaultSubItemsShouldBeFound("percentage.specified=true");

        // Get all the subItemsList where percentage is null
        defaultSubItemsShouldNotBeFound("percentage.specified=false");
    }

    @Test
    @Transactional
    void getAllSubItemsByPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where percentage is greater than or equal to DEFAULT_PERCENTAGE
        defaultSubItemsShouldBeFound("percentage.greaterThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the subItemsList where percentage is greater than or equal to UPDATED_PERCENTAGE
        defaultSubItemsShouldNotBeFound("percentage.greaterThanOrEqual=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSubItemsByPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where percentage is less than or equal to DEFAULT_PERCENTAGE
        defaultSubItemsShouldBeFound("percentage.lessThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the subItemsList where percentage is less than or equal to SMALLER_PERCENTAGE
        defaultSubItemsShouldNotBeFound("percentage.lessThanOrEqual=" + SMALLER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSubItemsByPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where percentage is less than DEFAULT_PERCENTAGE
        defaultSubItemsShouldNotBeFound("percentage.lessThan=" + DEFAULT_PERCENTAGE);

        // Get all the subItemsList where percentage is less than UPDATED_PERCENTAGE
        defaultSubItemsShouldBeFound("percentage.lessThan=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSubItemsByPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where percentage is greater than DEFAULT_PERCENTAGE
        defaultSubItemsShouldNotBeFound("percentage.greaterThan=" + DEFAULT_PERCENTAGE);

        // Get all the subItemsList where percentage is greater than SMALLER_PERCENTAGE
        defaultSubItemsShouldBeFound("percentage.greaterThan=" + SMALLER_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllSubItemsByBaseValueIsEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where baseValue equals to DEFAULT_BASE_VALUE
        defaultSubItemsShouldBeFound("baseValue.equals=" + DEFAULT_BASE_VALUE);

        // Get all the subItemsList where baseValue equals to UPDATED_BASE_VALUE
        defaultSubItemsShouldNotBeFound("baseValue.equals=" + UPDATED_BASE_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByBaseValueIsInShouldWork() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where baseValue in DEFAULT_BASE_VALUE or UPDATED_BASE_VALUE
        defaultSubItemsShouldBeFound("baseValue.in=" + DEFAULT_BASE_VALUE + "," + UPDATED_BASE_VALUE);

        // Get all the subItemsList where baseValue equals to UPDATED_BASE_VALUE
        defaultSubItemsShouldNotBeFound("baseValue.in=" + UPDATED_BASE_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByBaseValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where baseValue is not null
        defaultSubItemsShouldBeFound("baseValue.specified=true");

        // Get all the subItemsList where baseValue is null
        defaultSubItemsShouldNotBeFound("baseValue.specified=false");
    }

    @Test
    @Transactional
    void getAllSubItemsByBaseValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where baseValue is greater than or equal to DEFAULT_BASE_VALUE
        defaultSubItemsShouldBeFound("baseValue.greaterThanOrEqual=" + DEFAULT_BASE_VALUE);

        // Get all the subItemsList where baseValue is greater than or equal to UPDATED_BASE_VALUE
        defaultSubItemsShouldNotBeFound("baseValue.greaterThanOrEqual=" + UPDATED_BASE_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByBaseValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where baseValue is less than or equal to DEFAULT_BASE_VALUE
        defaultSubItemsShouldBeFound("baseValue.lessThanOrEqual=" + DEFAULT_BASE_VALUE);

        // Get all the subItemsList where baseValue is less than or equal to SMALLER_BASE_VALUE
        defaultSubItemsShouldNotBeFound("baseValue.lessThanOrEqual=" + SMALLER_BASE_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByBaseValueIsLessThanSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where baseValue is less than DEFAULT_BASE_VALUE
        defaultSubItemsShouldNotBeFound("baseValue.lessThan=" + DEFAULT_BASE_VALUE);

        // Get all the subItemsList where baseValue is less than UPDATED_BASE_VALUE
        defaultSubItemsShouldBeFound("baseValue.lessThan=" + UPDATED_BASE_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByBaseValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where baseValue is greater than DEFAULT_BASE_VALUE
        defaultSubItemsShouldNotBeFound("baseValue.greaterThan=" + DEFAULT_BASE_VALUE);

        // Get all the subItemsList where baseValue is greater than SMALLER_BASE_VALUE
        defaultSubItemsShouldBeFound("baseValue.greaterThan=" + SMALLER_BASE_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where type equals to DEFAULT_TYPE
        defaultSubItemsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the subItemsList where type equals to UPDATED_TYPE
        defaultSubItemsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSubItemsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultSubItemsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the subItemsList where type equals to UPDATED_TYPE
        defaultSubItemsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSubItemsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where type is not null
        defaultSubItemsShouldBeFound("type.specified=true");

        // Get all the subItemsList where type is null
        defaultSubItemsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllSubItemsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where valueType equals to DEFAULT_VALUE_TYPE
        defaultSubItemsShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the subItemsList where valueType equals to UPDATED_VALUE_TYPE
        defaultSubItemsShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllSubItemsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultSubItemsShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the subItemsList where valueType equals to UPDATED_VALUE_TYPE
        defaultSubItemsShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllSubItemsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where valueType is not null
        defaultSubItemsShouldBeFound("valueType.specified=true");

        // Get all the subItemsList where valueType is null
        defaultSubItemsShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllSubItemsByCalculatedValueIsEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where calculatedValue equals to DEFAULT_CALCULATED_VALUE
        defaultSubItemsShouldBeFound("calculatedValue.equals=" + DEFAULT_CALCULATED_VALUE);

        // Get all the subItemsList where calculatedValue equals to UPDATED_CALCULATED_VALUE
        defaultSubItemsShouldNotBeFound("calculatedValue.equals=" + UPDATED_CALCULATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByCalculatedValueIsInShouldWork() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where calculatedValue in DEFAULT_CALCULATED_VALUE or UPDATED_CALCULATED_VALUE
        defaultSubItemsShouldBeFound("calculatedValue.in=" + DEFAULT_CALCULATED_VALUE + "," + UPDATED_CALCULATED_VALUE);

        // Get all the subItemsList where calculatedValue equals to UPDATED_CALCULATED_VALUE
        defaultSubItemsShouldNotBeFound("calculatedValue.in=" + UPDATED_CALCULATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByCalculatedValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where calculatedValue is not null
        defaultSubItemsShouldBeFound("calculatedValue.specified=true");

        // Get all the subItemsList where calculatedValue is null
        defaultSubItemsShouldNotBeFound("calculatedValue.specified=false");
    }

    @Test
    @Transactional
    void getAllSubItemsByCalculatedValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where calculatedValue is greater than or equal to DEFAULT_CALCULATED_VALUE
        defaultSubItemsShouldBeFound("calculatedValue.greaterThanOrEqual=" + DEFAULT_CALCULATED_VALUE);

        // Get all the subItemsList where calculatedValue is greater than or equal to UPDATED_CALCULATED_VALUE
        defaultSubItemsShouldNotBeFound("calculatedValue.greaterThanOrEqual=" + UPDATED_CALCULATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByCalculatedValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where calculatedValue is less than or equal to DEFAULT_CALCULATED_VALUE
        defaultSubItemsShouldBeFound("calculatedValue.lessThanOrEqual=" + DEFAULT_CALCULATED_VALUE);

        // Get all the subItemsList where calculatedValue is less than or equal to SMALLER_CALCULATED_VALUE
        defaultSubItemsShouldNotBeFound("calculatedValue.lessThanOrEqual=" + SMALLER_CALCULATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByCalculatedValueIsLessThanSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where calculatedValue is less than DEFAULT_CALCULATED_VALUE
        defaultSubItemsShouldNotBeFound("calculatedValue.lessThan=" + DEFAULT_CALCULATED_VALUE);

        // Get all the subItemsList where calculatedValue is less than UPDATED_CALCULATED_VALUE
        defaultSubItemsShouldBeFound("calculatedValue.lessThan=" + UPDATED_CALCULATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByCalculatedValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where calculatedValue is greater than DEFAULT_CALCULATED_VALUE
        defaultSubItemsShouldNotBeFound("calculatedValue.greaterThan=" + DEFAULT_CALCULATED_VALUE);

        // Get all the subItemsList where calculatedValue is greater than SMALLER_CALCULATED_VALUE
        defaultSubItemsShouldBeFound("calculatedValue.greaterThan=" + SMALLER_CALCULATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSubItemsByTemplateItemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where templateItemId equals to DEFAULT_TEMPLATE_ITEM_ID
        defaultSubItemsShouldBeFound("templateItemId.equals=" + DEFAULT_TEMPLATE_ITEM_ID);

        // Get all the subItemsList where templateItemId equals to UPDATED_TEMPLATE_ITEM_ID
        defaultSubItemsShouldNotBeFound("templateItemId.equals=" + UPDATED_TEMPLATE_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSubItemsByTemplateItemIdIsInShouldWork() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where templateItemId in DEFAULT_TEMPLATE_ITEM_ID or UPDATED_TEMPLATE_ITEM_ID
        defaultSubItemsShouldBeFound("templateItemId.in=" + DEFAULT_TEMPLATE_ITEM_ID + "," + UPDATED_TEMPLATE_ITEM_ID);

        // Get all the subItemsList where templateItemId equals to UPDATED_TEMPLATE_ITEM_ID
        defaultSubItemsShouldNotBeFound("templateItemId.in=" + UPDATED_TEMPLATE_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSubItemsByTemplateItemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where templateItemId is not null
        defaultSubItemsShouldBeFound("templateItemId.specified=true");

        // Get all the subItemsList where templateItemId is null
        defaultSubItemsShouldNotBeFound("templateItemId.specified=false");
    }

    @Test
    @Transactional
    void getAllSubItemsByTemplateItemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where templateItemId is greater than or equal to DEFAULT_TEMPLATE_ITEM_ID
        defaultSubItemsShouldBeFound("templateItemId.greaterThanOrEqual=" + DEFAULT_TEMPLATE_ITEM_ID);

        // Get all the subItemsList where templateItemId is greater than or equal to UPDATED_TEMPLATE_ITEM_ID
        defaultSubItemsShouldNotBeFound("templateItemId.greaterThanOrEqual=" + UPDATED_TEMPLATE_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSubItemsByTemplateItemIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where templateItemId is less than or equal to DEFAULT_TEMPLATE_ITEM_ID
        defaultSubItemsShouldBeFound("templateItemId.lessThanOrEqual=" + DEFAULT_TEMPLATE_ITEM_ID);

        // Get all the subItemsList where templateItemId is less than or equal to SMALLER_TEMPLATE_ITEM_ID
        defaultSubItemsShouldNotBeFound("templateItemId.lessThanOrEqual=" + SMALLER_TEMPLATE_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSubItemsByTemplateItemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where templateItemId is less than DEFAULT_TEMPLATE_ITEM_ID
        defaultSubItemsShouldNotBeFound("templateItemId.lessThan=" + DEFAULT_TEMPLATE_ITEM_ID);

        // Get all the subItemsList where templateItemId is less than UPDATED_TEMPLATE_ITEM_ID
        defaultSubItemsShouldBeFound("templateItemId.lessThan=" + UPDATED_TEMPLATE_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSubItemsByTemplateItemIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where templateItemId is greater than DEFAULT_TEMPLATE_ITEM_ID
        defaultSubItemsShouldNotBeFound("templateItemId.greaterThan=" + DEFAULT_TEMPLATE_ITEM_ID);

        // Get all the subItemsList where templateItemId is greater than SMALLER_TEMPLATE_ITEM_ID
        defaultSubItemsShouldBeFound("templateItemId.greaterThan=" + SMALLER_TEMPLATE_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllSubItemsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultSubItemsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the subItemsList where createdDate equals to UPDATED_CREATED_DATE
        defaultSubItemsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSubItemsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultSubItemsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the subItemsList where createdDate equals to UPDATED_CREATED_DATE
        defaultSubItemsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSubItemsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where createdDate is not null
        defaultSubItemsShouldBeFound("createdDate.specified=true");

        // Get all the subItemsList where createdDate is null
        defaultSubItemsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSubItemsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultSubItemsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the subItemsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultSubItemsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSubItemsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultSubItemsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the subItemsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultSubItemsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSubItemsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultSubItemsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the subItemsList where createdDate is less than UPDATED_CREATED_DATE
        defaultSubItemsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSubItemsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        // Get all the subItemsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultSubItemsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the subItemsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultSubItemsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSubItemsByInvoiceDetailIsEqualToSomething() throws Exception {
        InvoiceDetails invoiceDetail;
        if (TestUtil.findAll(em, InvoiceDetails.class).isEmpty()) {
            subItemsRepository.saveAndFlush(subItems);
            invoiceDetail = InvoiceDetailsResourceIT.createEntity(em);
        } else {
            invoiceDetail = TestUtil.findAll(em, InvoiceDetails.class).get(0);
        }
        em.persist(invoiceDetail);
        em.flush();
        subItems.addInvoiceDetail(invoiceDetail);
        subItemsRepository.saveAndFlush(subItems);
        Long invoiceDetailId = invoiceDetail.getId();

        // Get all the subItemsList where invoiceDetail equals to invoiceDetailId
        defaultSubItemsShouldBeFound("invoiceDetailId.equals=" + invoiceDetailId);

        // Get all the subItemsList where invoiceDetail equals to (invoiceDetailId + 1)
        defaultSubItemsShouldNotBeFound("invoiceDetailId.equals=" + (invoiceDetailId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubItemsShouldBeFound(String filter) throws Exception {
        restSubItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].actualValue").value(hasItem(DEFAULT_ACTUAL_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].baseValue").value(hasItem(DEFAULT_BASE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].calculatedValue").value(hasItem(DEFAULT_CALCULATED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].templateItemId").value(hasItem(DEFAULT_TEMPLATE_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restSubItemsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubItemsShouldNotBeFound(String filter) throws Exception {
        restSubItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubItemsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSubItems() throws Exception {
        // Get the subItems
        restSubItemsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubItems() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        int databaseSizeBeforeUpdate = subItemsRepository.findAll().size();

        // Update the subItems
        SubItems updatedSubItems = subItemsRepository.findById(subItems.getId()).get();
        // Disconnect from session so that the updates on updatedSubItems are not directly saved in db
        em.detach(updatedSubItems);
        updatedSubItems
            .name(UPDATED_NAME)
            .actualValue(UPDATED_ACTUAL_VALUE)
            .percentage(UPDATED_PERCENTAGE)
            .baseValue(UPDATED_BASE_VALUE)
            .type(UPDATED_TYPE)
            .valueType(UPDATED_VALUE_TYPE)
            .calculatedValue(UPDATED_CALCULATED_VALUE)
            .templateItemId(UPDATED_TEMPLATE_ITEM_ID)
            .createdDate(UPDATED_CREATED_DATE);
        SubItemsDTO subItemsDTO = subItemsMapper.toDto(updatedSubItems);

        restSubItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subItemsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subItemsDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubItems in the database
        List<SubItems> subItemsList = subItemsRepository.findAll();
        assertThat(subItemsList).hasSize(databaseSizeBeforeUpdate);
        SubItems testSubItems = subItemsList.get(subItemsList.size() - 1);
        assertThat(testSubItems.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubItems.getActualValue()).isEqualTo(UPDATED_ACTUAL_VALUE);
        assertThat(testSubItems.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testSubItems.getBaseValue()).isEqualTo(UPDATED_BASE_VALUE);
        assertThat(testSubItems.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSubItems.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testSubItems.getCalculatedValue()).isEqualTo(UPDATED_CALCULATED_VALUE);
        assertThat(testSubItems.getTemplateItemId()).isEqualTo(UPDATED_TEMPLATE_ITEM_ID);
        assertThat(testSubItems.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSubItems() throws Exception {
        int databaseSizeBeforeUpdate = subItemsRepository.findAll().size();
        subItems.setId(count.incrementAndGet());

        // Create the SubItems
        SubItemsDTO subItemsDTO = subItemsMapper.toDto(subItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subItemsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubItems in the database
        List<SubItems> subItemsList = subItemsRepository.findAll();
        assertThat(subItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubItems() throws Exception {
        int databaseSizeBeforeUpdate = subItemsRepository.findAll().size();
        subItems.setId(count.incrementAndGet());

        // Create the SubItems
        SubItemsDTO subItemsDTO = subItemsMapper.toDto(subItems);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubItems in the database
        List<SubItems> subItemsList = subItemsRepository.findAll();
        assertThat(subItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubItems() throws Exception {
        int databaseSizeBeforeUpdate = subItemsRepository.findAll().size();
        subItems.setId(count.incrementAndGet());

        // Create the SubItems
        SubItemsDTO subItemsDTO = subItemsMapper.toDto(subItems);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubItemsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subItemsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubItems in the database
        List<SubItems> subItemsList = subItemsRepository.findAll();
        assertThat(subItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubItemsWithPatch() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        int databaseSizeBeforeUpdate = subItemsRepository.findAll().size();

        // Update the subItems using partial update
        SubItems partialUpdatedSubItems = new SubItems();
        partialUpdatedSubItems.setId(subItems.getId());

        partialUpdatedSubItems
            .actualValue(UPDATED_ACTUAL_VALUE)
            .baseValue(UPDATED_BASE_VALUE)
            .type(UPDATED_TYPE)
            .templateItemId(UPDATED_TEMPLATE_ITEM_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restSubItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubItems))
            )
            .andExpect(status().isOk());

        // Validate the SubItems in the database
        List<SubItems> subItemsList = subItemsRepository.findAll();
        assertThat(subItemsList).hasSize(databaseSizeBeforeUpdate);
        SubItems testSubItems = subItemsList.get(subItemsList.size() - 1);
        assertThat(testSubItems.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSubItems.getActualValue()).isEqualTo(UPDATED_ACTUAL_VALUE);
        assertThat(testSubItems.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testSubItems.getBaseValue()).isEqualTo(UPDATED_BASE_VALUE);
        assertThat(testSubItems.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSubItems.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testSubItems.getCalculatedValue()).isEqualTo(DEFAULT_CALCULATED_VALUE);
        assertThat(testSubItems.getTemplateItemId()).isEqualTo(UPDATED_TEMPLATE_ITEM_ID);
        assertThat(testSubItems.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSubItemsWithPatch() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        int databaseSizeBeforeUpdate = subItemsRepository.findAll().size();

        // Update the subItems using partial update
        SubItems partialUpdatedSubItems = new SubItems();
        partialUpdatedSubItems.setId(subItems.getId());

        partialUpdatedSubItems
            .name(UPDATED_NAME)
            .actualValue(UPDATED_ACTUAL_VALUE)
            .percentage(UPDATED_PERCENTAGE)
            .baseValue(UPDATED_BASE_VALUE)
            .type(UPDATED_TYPE)
            .valueType(UPDATED_VALUE_TYPE)
            .calculatedValue(UPDATED_CALCULATED_VALUE)
            .templateItemId(UPDATED_TEMPLATE_ITEM_ID)
            .createdDate(UPDATED_CREATED_DATE);

        restSubItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubItems))
            )
            .andExpect(status().isOk());

        // Validate the SubItems in the database
        List<SubItems> subItemsList = subItemsRepository.findAll();
        assertThat(subItemsList).hasSize(databaseSizeBeforeUpdate);
        SubItems testSubItems = subItemsList.get(subItemsList.size() - 1);
        assertThat(testSubItems.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubItems.getActualValue()).isEqualTo(UPDATED_ACTUAL_VALUE);
        assertThat(testSubItems.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testSubItems.getBaseValue()).isEqualTo(UPDATED_BASE_VALUE);
        assertThat(testSubItems.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSubItems.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testSubItems.getCalculatedValue()).isEqualTo(UPDATED_CALCULATED_VALUE);
        assertThat(testSubItems.getTemplateItemId()).isEqualTo(UPDATED_TEMPLATE_ITEM_ID);
        assertThat(testSubItems.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSubItems() throws Exception {
        int databaseSizeBeforeUpdate = subItemsRepository.findAll().size();
        subItems.setId(count.incrementAndGet());

        // Create the SubItems
        SubItemsDTO subItemsDTO = subItemsMapper.toDto(subItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subItemsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubItems in the database
        List<SubItems> subItemsList = subItemsRepository.findAll();
        assertThat(subItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubItems() throws Exception {
        int databaseSizeBeforeUpdate = subItemsRepository.findAll().size();
        subItems.setId(count.incrementAndGet());

        // Create the SubItems
        SubItemsDTO subItemsDTO = subItemsMapper.toDto(subItems);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubItems in the database
        List<SubItems> subItemsList = subItemsRepository.findAll();
        assertThat(subItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubItems() throws Exception {
        int databaseSizeBeforeUpdate = subItemsRepository.findAll().size();
        subItems.setId(count.incrementAndGet());

        // Create the SubItems
        SubItemsDTO subItemsDTO = subItemsMapper.toDto(subItems);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubItemsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subItemsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubItems in the database
        List<SubItems> subItemsList = subItemsRepository.findAll();
        assertThat(subItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubItems() throws Exception {
        // Initialize the database
        subItemsRepository.saveAndFlush(subItems);

        int databaseSizeBeforeDelete = subItemsRepository.findAll().size();

        // Delete the subItems
        restSubItemsMockMvc
            .perform(delete(ENTITY_API_URL_ID, subItems.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubItems> subItemsList = subItemsRepository.findAll();
        assertThat(subItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
