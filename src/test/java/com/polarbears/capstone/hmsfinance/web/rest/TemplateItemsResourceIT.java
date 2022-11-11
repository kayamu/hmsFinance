package com.polarbears.capstone.hmsfinance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmsfinance.IntegrationTest;
import com.polarbears.capstone.hmsfinance.domain.Conditions;
import com.polarbears.capstone.hmsfinance.domain.TemplateItems;
import com.polarbears.capstone.hmsfinance.domain.Templates;
import com.polarbears.capstone.hmsfinance.domain.enumeration.DETAILTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.VALUETYPES;
import com.polarbears.capstone.hmsfinance.repository.TemplateItemsRepository;
import com.polarbears.capstone.hmsfinance.service.TemplateItemsService;
import com.polarbears.capstone.hmsfinance.service.criteria.TemplateItemsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.TemplateItemsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.TemplateItemsMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TemplateItemsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TemplateItemsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final DETAILTYPES DEFAULT_TYPE = DETAILTYPES.EXPENSES;
    private static final DETAILTYPES UPDATED_TYPE = DETAILTYPES.DISCOUNT;

    private static final VALUETYPES DEFAULT_VALUE_TYPE = VALUETYPES.PERCENTAGE;
    private static final VALUETYPES UPDATED_VALUE_TYPE = VALUETYPES.AMOUNT;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final String DEFAULT_EXPLANATION = "AAAAAAAAAA";
    private static final String UPDATED_EXPLANATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_IS_ONCE = false;
    private static final Boolean UPDATED_IS_ONCE = true;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/template-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TemplateItemsRepository templateItemsRepository;

    @Mock
    private TemplateItemsRepository templateItemsRepositoryMock;

    @Autowired
    private TemplateItemsMapper templateItemsMapper;

    @Mock
    private TemplateItemsService templateItemsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemplateItemsMockMvc;

    private TemplateItems templateItems;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateItems createEntity(EntityManager em) {
        TemplateItems templateItems = new TemplateItems()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .type(DEFAULT_TYPE)
            .valueType(DEFAULT_VALUE_TYPE)
            .amount(DEFAULT_AMOUNT)
            .explanation(DEFAULT_EXPLANATION)
            .startDate(DEFAULT_START_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .isOnce(DEFAULT_IS_ONCE)
            .createdDate(DEFAULT_CREATED_DATE);
        return templateItems;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateItems createUpdatedEntity(EntityManager em) {
        TemplateItems templateItems = new TemplateItems()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .valueType(UPDATED_VALUE_TYPE)
            .amount(UPDATED_AMOUNT)
            .explanation(UPDATED_EXPLANATION)
            .startDate(UPDATED_START_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .isOnce(UPDATED_IS_ONCE)
            .createdDate(UPDATED_CREATED_DATE);
        return templateItems;
    }

    @BeforeEach
    public void initTest() {
        templateItems = createEntity(em);
    }

    @Test
    @Transactional
    void createTemplateItems() throws Exception {
        int databaseSizeBeforeCreate = templateItemsRepository.findAll().size();
        // Create the TemplateItems
        TemplateItemsDTO templateItemsDTO = templateItemsMapper.toDto(templateItems);
        restTemplateItemsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templateItemsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TemplateItems in the database
        List<TemplateItems> templateItemsList = templateItemsRepository.findAll();
        assertThat(templateItemsList).hasSize(databaseSizeBeforeCreate + 1);
        TemplateItems testTemplateItems = templateItemsList.get(templateItemsList.size() - 1);
        assertThat(testTemplateItems.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTemplateItems.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTemplateItems.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTemplateItems.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testTemplateItems.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTemplateItems.getExplanation()).isEqualTo(DEFAULT_EXPLANATION);
        assertThat(testTemplateItems.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTemplateItems.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testTemplateItems.getIsOnce()).isEqualTo(DEFAULT_IS_ONCE);
        assertThat(testTemplateItems.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createTemplateItemsWithExistingId() throws Exception {
        // Create the TemplateItems with an existing ID
        templateItems.setId(1L);
        TemplateItemsDTO templateItemsDTO = templateItemsMapper.toDto(templateItems);

        int databaseSizeBeforeCreate = templateItemsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateItemsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templateItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateItems in the database
        List<TemplateItems> templateItemsList = templateItemsRepository.findAll();
        assertThat(templateItemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTemplateItems() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList
        restTemplateItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].explanation").value(hasItem(DEFAULT_EXPLANATION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].isOnce").value(hasItem(DEFAULT_IS_ONCE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTemplateItemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(templateItemsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTemplateItemsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(templateItemsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTemplateItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(templateItemsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTemplateItemsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(templateItemsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTemplateItems() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get the templateItems
        restTemplateItemsMockMvc
            .perform(get(ENTITY_API_URL_ID, templateItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(templateItems.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.explanation").value(DEFAULT_EXPLANATION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.isOnce").value(DEFAULT_IS_ONCE.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getTemplateItemsByIdFiltering() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        Long id = templateItems.getId();

        defaultTemplateItemsShouldBeFound("id.equals=" + id);
        defaultTemplateItemsShouldNotBeFound("id.notEquals=" + id);

        defaultTemplateItemsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTemplateItemsShouldNotBeFound("id.greaterThan=" + id);

        defaultTemplateItemsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTemplateItemsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where name equals to DEFAULT_NAME
        defaultTemplateItemsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the templateItemsList where name equals to UPDATED_NAME
        defaultTemplateItemsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTemplateItemsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the templateItemsList where name equals to UPDATED_NAME
        defaultTemplateItemsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where name is not null
        defaultTemplateItemsShouldBeFound("name.specified=true");

        // Get all the templateItemsList where name is null
        defaultTemplateItemsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateItemsByNameContainsSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where name contains DEFAULT_NAME
        defaultTemplateItemsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the templateItemsList where name contains UPDATED_NAME
        defaultTemplateItemsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where name does not contain DEFAULT_NAME
        defaultTemplateItemsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the templateItemsList where name does not contain UPDATED_NAME
        defaultTemplateItemsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where code equals to DEFAULT_CODE
        defaultTemplateItemsShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the templateItemsList where code equals to UPDATED_CODE
        defaultTemplateItemsShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where code in DEFAULT_CODE or UPDATED_CODE
        defaultTemplateItemsShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the templateItemsList where code equals to UPDATED_CODE
        defaultTemplateItemsShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where code is not null
        defaultTemplateItemsShouldBeFound("code.specified=true");

        // Get all the templateItemsList where code is null
        defaultTemplateItemsShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateItemsByCodeContainsSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where code contains DEFAULT_CODE
        defaultTemplateItemsShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the templateItemsList where code contains UPDATED_CODE
        defaultTemplateItemsShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where code does not contain DEFAULT_CODE
        defaultTemplateItemsShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the templateItemsList where code does not contain UPDATED_CODE
        defaultTemplateItemsShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where type equals to DEFAULT_TYPE
        defaultTemplateItemsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the templateItemsList where type equals to UPDATED_TYPE
        defaultTemplateItemsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultTemplateItemsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the templateItemsList where type equals to UPDATED_TYPE
        defaultTemplateItemsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where type is not null
        defaultTemplateItemsShouldBeFound("type.specified=true");

        // Get all the templateItemsList where type is null
        defaultTemplateItemsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateItemsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where valueType equals to DEFAULT_VALUE_TYPE
        defaultTemplateItemsShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the templateItemsList where valueType equals to UPDATED_VALUE_TYPE
        defaultTemplateItemsShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultTemplateItemsShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the templateItemsList where valueType equals to UPDATED_VALUE_TYPE
        defaultTemplateItemsShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where valueType is not null
        defaultTemplateItemsShouldBeFound("valueType.specified=true");

        // Get all the templateItemsList where valueType is null
        defaultTemplateItemsShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateItemsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where amount equals to DEFAULT_AMOUNT
        defaultTemplateItemsShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the templateItemsList where amount equals to UPDATED_AMOUNT
        defaultTemplateItemsShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultTemplateItemsShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the templateItemsList where amount equals to UPDATED_AMOUNT
        defaultTemplateItemsShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where amount is not null
        defaultTemplateItemsShouldBeFound("amount.specified=true");

        // Get all the templateItemsList where amount is null
        defaultTemplateItemsShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateItemsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultTemplateItemsShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the templateItemsList where amount is greater than or equal to UPDATED_AMOUNT
        defaultTemplateItemsShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where amount is less than or equal to DEFAULT_AMOUNT
        defaultTemplateItemsShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the templateItemsList where amount is less than or equal to SMALLER_AMOUNT
        defaultTemplateItemsShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where amount is less than DEFAULT_AMOUNT
        defaultTemplateItemsShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the templateItemsList where amount is less than UPDATED_AMOUNT
        defaultTemplateItemsShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where amount is greater than DEFAULT_AMOUNT
        defaultTemplateItemsShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the templateItemsList where amount is greater than SMALLER_AMOUNT
        defaultTemplateItemsShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByExplanationIsEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where explanation equals to DEFAULT_EXPLANATION
        defaultTemplateItemsShouldBeFound("explanation.equals=" + DEFAULT_EXPLANATION);

        // Get all the templateItemsList where explanation equals to UPDATED_EXPLANATION
        defaultTemplateItemsShouldNotBeFound("explanation.equals=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByExplanationIsInShouldWork() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where explanation in DEFAULT_EXPLANATION or UPDATED_EXPLANATION
        defaultTemplateItemsShouldBeFound("explanation.in=" + DEFAULT_EXPLANATION + "," + UPDATED_EXPLANATION);

        // Get all the templateItemsList where explanation equals to UPDATED_EXPLANATION
        defaultTemplateItemsShouldNotBeFound("explanation.in=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByExplanationIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where explanation is not null
        defaultTemplateItemsShouldBeFound("explanation.specified=true");

        // Get all the templateItemsList where explanation is null
        defaultTemplateItemsShouldNotBeFound("explanation.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateItemsByExplanationContainsSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where explanation contains DEFAULT_EXPLANATION
        defaultTemplateItemsShouldBeFound("explanation.contains=" + DEFAULT_EXPLANATION);

        // Get all the templateItemsList where explanation contains UPDATED_EXPLANATION
        defaultTemplateItemsShouldNotBeFound("explanation.contains=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByExplanationNotContainsSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where explanation does not contain DEFAULT_EXPLANATION
        defaultTemplateItemsShouldNotBeFound("explanation.doesNotContain=" + DEFAULT_EXPLANATION);

        // Get all the templateItemsList where explanation does not contain UPDATED_EXPLANATION
        defaultTemplateItemsShouldBeFound("explanation.doesNotContain=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where startDate equals to DEFAULT_START_DATE
        defaultTemplateItemsShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the templateItemsList where startDate equals to UPDATED_START_DATE
        defaultTemplateItemsShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultTemplateItemsShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the templateItemsList where startDate equals to UPDATED_START_DATE
        defaultTemplateItemsShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where startDate is not null
        defaultTemplateItemsShouldBeFound("startDate.specified=true");

        // Get all the templateItemsList where startDate is null
        defaultTemplateItemsShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateItemsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultTemplateItemsShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the templateItemsList where startDate is greater than or equal to UPDATED_START_DATE
        defaultTemplateItemsShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where startDate is less than or equal to DEFAULT_START_DATE
        defaultTemplateItemsShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the templateItemsList where startDate is less than or equal to SMALLER_START_DATE
        defaultTemplateItemsShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where startDate is less than DEFAULT_START_DATE
        defaultTemplateItemsShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the templateItemsList where startDate is less than UPDATED_START_DATE
        defaultTemplateItemsShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where startDate is greater than DEFAULT_START_DATE
        defaultTemplateItemsShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the templateItemsList where startDate is greater than SMALLER_START_DATE
        defaultTemplateItemsShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where dueDate equals to DEFAULT_DUE_DATE
        defaultTemplateItemsShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the templateItemsList where dueDate equals to UPDATED_DUE_DATE
        defaultTemplateItemsShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultTemplateItemsShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the templateItemsList where dueDate equals to UPDATED_DUE_DATE
        defaultTemplateItemsShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where dueDate is not null
        defaultTemplateItemsShouldBeFound("dueDate.specified=true");

        // Get all the templateItemsList where dueDate is null
        defaultTemplateItemsShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateItemsByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where dueDate is greater than or equal to DEFAULT_DUE_DATE
        defaultTemplateItemsShouldBeFound("dueDate.greaterThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the templateItemsList where dueDate is greater than or equal to UPDATED_DUE_DATE
        defaultTemplateItemsShouldNotBeFound("dueDate.greaterThanOrEqual=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where dueDate is less than or equal to DEFAULT_DUE_DATE
        defaultTemplateItemsShouldBeFound("dueDate.lessThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the templateItemsList where dueDate is less than or equal to SMALLER_DUE_DATE
        defaultTemplateItemsShouldNotBeFound("dueDate.lessThanOrEqual=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where dueDate is less than DEFAULT_DUE_DATE
        defaultTemplateItemsShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the templateItemsList where dueDate is less than UPDATED_DUE_DATE
        defaultTemplateItemsShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where dueDate is greater than DEFAULT_DUE_DATE
        defaultTemplateItemsShouldNotBeFound("dueDate.greaterThan=" + DEFAULT_DUE_DATE);

        // Get all the templateItemsList where dueDate is greater than SMALLER_DUE_DATE
        defaultTemplateItemsShouldBeFound("dueDate.greaterThan=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByIsOnceIsEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where isOnce equals to DEFAULT_IS_ONCE
        defaultTemplateItemsShouldBeFound("isOnce.equals=" + DEFAULT_IS_ONCE);

        // Get all the templateItemsList where isOnce equals to UPDATED_IS_ONCE
        defaultTemplateItemsShouldNotBeFound("isOnce.equals=" + UPDATED_IS_ONCE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByIsOnceIsInShouldWork() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where isOnce in DEFAULT_IS_ONCE or UPDATED_IS_ONCE
        defaultTemplateItemsShouldBeFound("isOnce.in=" + DEFAULT_IS_ONCE + "," + UPDATED_IS_ONCE);

        // Get all the templateItemsList where isOnce equals to UPDATED_IS_ONCE
        defaultTemplateItemsShouldNotBeFound("isOnce.in=" + UPDATED_IS_ONCE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByIsOnceIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where isOnce is not null
        defaultTemplateItemsShouldBeFound("isOnce.specified=true");

        // Get all the templateItemsList where isOnce is null
        defaultTemplateItemsShouldNotBeFound("isOnce.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateItemsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultTemplateItemsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the templateItemsList where createdDate equals to UPDATED_CREATED_DATE
        defaultTemplateItemsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultTemplateItemsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the templateItemsList where createdDate equals to UPDATED_CREATED_DATE
        defaultTemplateItemsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where createdDate is not null
        defaultTemplateItemsShouldBeFound("createdDate.specified=true");

        // Get all the templateItemsList where createdDate is null
        defaultTemplateItemsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplateItemsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultTemplateItemsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the templateItemsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultTemplateItemsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultTemplateItemsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the templateItemsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultTemplateItemsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultTemplateItemsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the templateItemsList where createdDate is less than UPDATED_CREATED_DATE
        defaultTemplateItemsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        // Get all the templateItemsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultTemplateItemsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the templateItemsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultTemplateItemsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTemplateItemsByConditionsIsEqualToSomething() throws Exception {
        Conditions conditions;
        if (TestUtil.findAll(em, Conditions.class).isEmpty()) {
            templateItemsRepository.saveAndFlush(templateItems);
            conditions = ConditionsResourceIT.createEntity(em);
        } else {
            conditions = TestUtil.findAll(em, Conditions.class).get(0);
        }
        em.persist(conditions);
        em.flush();
        templateItems.setConditions(conditions);
        templateItemsRepository.saveAndFlush(templateItems);
        Long conditionsId = conditions.getId();

        // Get all the templateItemsList where conditions equals to conditionsId
        defaultTemplateItemsShouldBeFound("conditionsId.equals=" + conditionsId);

        // Get all the templateItemsList where conditions equals to (conditionsId + 1)
        defaultTemplateItemsShouldNotBeFound("conditionsId.equals=" + (conditionsId + 1));
    }

    @Test
    @Transactional
    void getAllTemplateItemsByTemplatesIsEqualToSomething() throws Exception {
        Templates templates;
        if (TestUtil.findAll(em, Templates.class).isEmpty()) {
            templateItemsRepository.saveAndFlush(templateItems);
            templates = TemplatesResourceIT.createEntity(em);
        } else {
            templates = TestUtil.findAll(em, Templates.class).get(0);
        }
        em.persist(templates);
        em.flush();
        templateItems.addTemplates(templates);
        templateItemsRepository.saveAndFlush(templateItems);
        Long templatesId = templates.getId();

        // Get all the templateItemsList where templates equals to templatesId
        defaultTemplateItemsShouldBeFound("templatesId.equals=" + templatesId);

        // Get all the templateItemsList where templates equals to (templatesId + 1)
        defaultTemplateItemsShouldNotBeFound("templatesId.equals=" + (templatesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTemplateItemsShouldBeFound(String filter) throws Exception {
        restTemplateItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].explanation").value(hasItem(DEFAULT_EXPLANATION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].isOnce").value(hasItem(DEFAULT_IS_ONCE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restTemplateItemsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTemplateItemsShouldNotBeFound(String filter) throws Exception {
        restTemplateItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTemplateItemsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTemplateItems() throws Exception {
        // Get the templateItems
        restTemplateItemsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTemplateItems() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        int databaseSizeBeforeUpdate = templateItemsRepository.findAll().size();

        // Update the templateItems
        TemplateItems updatedTemplateItems = templateItemsRepository.findById(templateItems.getId()).get();
        // Disconnect from session so that the updates on updatedTemplateItems are not directly saved in db
        em.detach(updatedTemplateItems);
        updatedTemplateItems
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .valueType(UPDATED_VALUE_TYPE)
            .amount(UPDATED_AMOUNT)
            .explanation(UPDATED_EXPLANATION)
            .startDate(UPDATED_START_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .isOnce(UPDATED_IS_ONCE)
            .createdDate(UPDATED_CREATED_DATE);
        TemplateItemsDTO templateItemsDTO = templateItemsMapper.toDto(updatedTemplateItems);

        restTemplateItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateItemsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateItemsDTO))
            )
            .andExpect(status().isOk());

        // Validate the TemplateItems in the database
        List<TemplateItems> templateItemsList = templateItemsRepository.findAll();
        assertThat(templateItemsList).hasSize(databaseSizeBeforeUpdate);
        TemplateItems testTemplateItems = templateItemsList.get(templateItemsList.size() - 1);
        assertThat(testTemplateItems.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTemplateItems.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTemplateItems.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTemplateItems.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testTemplateItems.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTemplateItems.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testTemplateItems.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTemplateItems.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testTemplateItems.getIsOnce()).isEqualTo(UPDATED_IS_ONCE);
        assertThat(testTemplateItems.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTemplateItems() throws Exception {
        int databaseSizeBeforeUpdate = templateItemsRepository.findAll().size();
        templateItems.setId(count.incrementAndGet());

        // Create the TemplateItems
        TemplateItemsDTO templateItemsDTO = templateItemsMapper.toDto(templateItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templateItemsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateItems in the database
        List<TemplateItems> templateItemsList = templateItemsRepository.findAll();
        assertThat(templateItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTemplateItems() throws Exception {
        int databaseSizeBeforeUpdate = templateItemsRepository.findAll().size();
        templateItems.setId(count.incrementAndGet());

        // Create the TemplateItems
        TemplateItemsDTO templateItemsDTO = templateItemsMapper.toDto(templateItems);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templateItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateItems in the database
        List<TemplateItems> templateItemsList = templateItemsRepository.findAll();
        assertThat(templateItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTemplateItems() throws Exception {
        int databaseSizeBeforeUpdate = templateItemsRepository.findAll().size();
        templateItems.setId(count.incrementAndGet());

        // Create the TemplateItems
        TemplateItemsDTO templateItemsDTO = templateItemsMapper.toDto(templateItems);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateItemsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templateItemsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemplateItems in the database
        List<TemplateItems> templateItemsList = templateItemsRepository.findAll();
        assertThat(templateItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTemplateItemsWithPatch() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        int databaseSizeBeforeUpdate = templateItemsRepository.findAll().size();

        // Update the templateItems using partial update
        TemplateItems partialUpdatedTemplateItems = new TemplateItems();
        partialUpdatedTemplateItems.setId(templateItems.getId());

        partialUpdatedTemplateItems
            .code(UPDATED_CODE)
            .valueType(UPDATED_VALUE_TYPE)
            .startDate(UPDATED_START_DATE)
            .isOnce(UPDATED_IS_ONCE)
            .createdDate(UPDATED_CREATED_DATE);

        restTemplateItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplateItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplateItems))
            )
            .andExpect(status().isOk());

        // Validate the TemplateItems in the database
        List<TemplateItems> templateItemsList = templateItemsRepository.findAll();
        assertThat(templateItemsList).hasSize(databaseSizeBeforeUpdate);
        TemplateItems testTemplateItems = templateItemsList.get(templateItemsList.size() - 1);
        assertThat(testTemplateItems.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTemplateItems.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTemplateItems.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTemplateItems.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testTemplateItems.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTemplateItems.getExplanation()).isEqualTo(DEFAULT_EXPLANATION);
        assertThat(testTemplateItems.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTemplateItems.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testTemplateItems.getIsOnce()).isEqualTo(UPDATED_IS_ONCE);
        assertThat(testTemplateItems.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTemplateItemsWithPatch() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        int databaseSizeBeforeUpdate = templateItemsRepository.findAll().size();

        // Update the templateItems using partial update
        TemplateItems partialUpdatedTemplateItems = new TemplateItems();
        partialUpdatedTemplateItems.setId(templateItems.getId());

        partialUpdatedTemplateItems
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .valueType(UPDATED_VALUE_TYPE)
            .amount(UPDATED_AMOUNT)
            .explanation(UPDATED_EXPLANATION)
            .startDate(UPDATED_START_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .isOnce(UPDATED_IS_ONCE)
            .createdDate(UPDATED_CREATED_DATE);

        restTemplateItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplateItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplateItems))
            )
            .andExpect(status().isOk());

        // Validate the TemplateItems in the database
        List<TemplateItems> templateItemsList = templateItemsRepository.findAll();
        assertThat(templateItemsList).hasSize(databaseSizeBeforeUpdate);
        TemplateItems testTemplateItems = templateItemsList.get(templateItemsList.size() - 1);
        assertThat(testTemplateItems.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTemplateItems.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTemplateItems.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTemplateItems.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testTemplateItems.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTemplateItems.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testTemplateItems.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTemplateItems.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testTemplateItems.getIsOnce()).isEqualTo(UPDATED_IS_ONCE);
        assertThat(testTemplateItems.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTemplateItems() throws Exception {
        int databaseSizeBeforeUpdate = templateItemsRepository.findAll().size();
        templateItems.setId(count.incrementAndGet());

        // Create the TemplateItems
        TemplateItemsDTO templateItemsDTO = templateItemsMapper.toDto(templateItems);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, templateItemsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateItems in the database
        List<TemplateItems> templateItemsList = templateItemsRepository.findAll();
        assertThat(templateItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTemplateItems() throws Exception {
        int databaseSizeBeforeUpdate = templateItemsRepository.findAll().size();
        templateItems.setId(count.incrementAndGet());

        // Create the TemplateItems
        TemplateItemsDTO templateItemsDTO = templateItemsMapper.toDto(templateItems);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateItemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TemplateItems in the database
        List<TemplateItems> templateItemsList = templateItemsRepository.findAll();
        assertThat(templateItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTemplateItems() throws Exception {
        int databaseSizeBeforeUpdate = templateItemsRepository.findAll().size();
        templateItems.setId(count.incrementAndGet());

        // Create the TemplateItems
        TemplateItemsDTO templateItemsDTO = templateItemsMapper.toDto(templateItems);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplateItemsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templateItemsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TemplateItems in the database
        List<TemplateItems> templateItemsList = templateItemsRepository.findAll();
        assertThat(templateItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTemplateItems() throws Exception {
        // Initialize the database
        templateItemsRepository.saveAndFlush(templateItems);

        int databaseSizeBeforeDelete = templateItemsRepository.findAll().size();

        // Delete the templateItems
        restTemplateItemsMockMvc
            .perform(delete(ENTITY_API_URL_ID, templateItems.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TemplateItems> templateItemsList = templateItemsRepository.findAll();
        assertThat(templateItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
