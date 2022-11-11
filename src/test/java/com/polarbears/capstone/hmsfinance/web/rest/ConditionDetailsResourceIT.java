package com.polarbears.capstone.hmsfinance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmsfinance.IntegrationTest;
import com.polarbears.capstone.hmsfinance.domain.ConditionDetails;
import com.polarbears.capstone.hmsfinance.domain.Conditions;
import com.polarbears.capstone.hmsfinance.domain.enumeration.FIELDS;
import com.polarbears.capstone.hmsfinance.domain.enumeration.LOGICTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.LOGICTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.OPERATORS;
import com.polarbears.capstone.hmsfinance.repository.ConditionDetailsRepository;
import com.polarbears.capstone.hmsfinance.service.criteria.ConditionDetailsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.ConditionDetailsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.ConditionDetailsMapper;
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
 * Integration tests for the {@link ConditionDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConditionDetailsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXPLANATION = "AAAAAAAAAA";
    private static final String UPDATED_EXPLANATION = "BBBBBBBBBB";

    private static final FIELDS DEFAULT_COMPARE_FIELD = FIELDS.NUTRITION;
    private static final FIELDS UPDATED_COMPARE_FIELD = FIELDS.CONTACT;

    private static final OPERATORS DEFAULT_OPERATOR = OPERATORS.EQUAL;
    private static final OPERATORS UPDATED_OPERATOR = OPERATORS.LESS;

    private static final Integer DEFAULT_GROUP_INDEX = 1;
    private static final Integer UPDATED_GROUP_INDEX = 2;
    private static final Integer SMALLER_GROUP_INDEX = 1 - 1;

    private static final String DEFAULT_COMPARE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_COMPARE_VALUE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final LOGICTYPES DEFAULT_LINE_LOGIC_TYPE = LOGICTYPES.AND;
    private static final LOGICTYPES UPDATED_LINE_LOGIC_TYPE = LOGICTYPES.OR;

    private static final LOGICTYPES DEFAULT_GROUP_LOGIC_TYPE = LOGICTYPES.AND;
    private static final LOGICTYPES UPDATED_GROUP_LOGIC_TYPE = LOGICTYPES.OR;

    private static final Long DEFAULT_NEXT_CONDITION = 1L;
    private static final Long UPDATED_NEXT_CONDITION = 2L;
    private static final Long SMALLER_NEXT_CONDITION = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/condition-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConditionDetailsRepository conditionDetailsRepository;

    @Autowired
    private ConditionDetailsMapper conditionDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConditionDetailsMockMvc;

    private ConditionDetails conditionDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConditionDetails createEntity(EntityManager em) {
        ConditionDetails conditionDetails = new ConditionDetails()
            .name(DEFAULT_NAME)
            .explanation(DEFAULT_EXPLANATION)
            .compareField(DEFAULT_COMPARE_FIELD)
            .operator(DEFAULT_OPERATOR)
            .groupIndex(DEFAULT_GROUP_INDEX)
            .compareValue(DEFAULT_COMPARE_VALUE)
            .createdDate(DEFAULT_CREATED_DATE)
            .lineLogicType(DEFAULT_LINE_LOGIC_TYPE)
            .groupLogicType(DEFAULT_GROUP_LOGIC_TYPE)
            .nextCondition(DEFAULT_NEXT_CONDITION);
        return conditionDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConditionDetails createUpdatedEntity(EntityManager em) {
        ConditionDetails conditionDetails = new ConditionDetails()
            .name(UPDATED_NAME)
            .explanation(UPDATED_EXPLANATION)
            .compareField(UPDATED_COMPARE_FIELD)
            .operator(UPDATED_OPERATOR)
            .groupIndex(UPDATED_GROUP_INDEX)
            .compareValue(UPDATED_COMPARE_VALUE)
            .createdDate(UPDATED_CREATED_DATE)
            .lineLogicType(UPDATED_LINE_LOGIC_TYPE)
            .groupLogicType(UPDATED_GROUP_LOGIC_TYPE)
            .nextCondition(UPDATED_NEXT_CONDITION);
        return conditionDetails;
    }

    @BeforeEach
    public void initTest() {
        conditionDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createConditionDetails() throws Exception {
        int databaseSizeBeforeCreate = conditionDetailsRepository.findAll().size();
        // Create the ConditionDetails
        ConditionDetailsDTO conditionDetailsDTO = conditionDetailsMapper.toDto(conditionDetails);
        restConditionDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conditionDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ConditionDetails testConditionDetails = conditionDetailsList.get(conditionDetailsList.size() - 1);
        assertThat(testConditionDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConditionDetails.getExplanation()).isEqualTo(DEFAULT_EXPLANATION);
        assertThat(testConditionDetails.getCompareField()).isEqualTo(DEFAULT_COMPARE_FIELD);
        assertThat(testConditionDetails.getOperator()).isEqualTo(DEFAULT_OPERATOR);
        assertThat(testConditionDetails.getGroupIndex()).isEqualTo(DEFAULT_GROUP_INDEX);
        assertThat(testConditionDetails.getCompareValue()).isEqualTo(DEFAULT_COMPARE_VALUE);
        assertThat(testConditionDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testConditionDetails.getLineLogicType()).isEqualTo(DEFAULT_LINE_LOGIC_TYPE);
        assertThat(testConditionDetails.getGroupLogicType()).isEqualTo(DEFAULT_GROUP_LOGIC_TYPE);
        assertThat(testConditionDetails.getNextCondition()).isEqualTo(DEFAULT_NEXT_CONDITION);
    }

    @Test
    @Transactional
    void createConditionDetailsWithExistingId() throws Exception {
        // Create the ConditionDetails with an existing ID
        conditionDetails.setId(1L);
        ConditionDetailsDTO conditionDetailsDTO = conditionDetailsMapper.toDto(conditionDetails);

        int databaseSizeBeforeCreate = conditionDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConditionDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conditionDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConditionDetails() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList
        restConditionDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conditionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].explanation").value(hasItem(DEFAULT_EXPLANATION)))
            .andExpect(jsonPath("$.[*].compareField").value(hasItem(DEFAULT_COMPARE_FIELD.toString())))
            .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR.toString())))
            .andExpect(jsonPath("$.[*].groupIndex").value(hasItem(DEFAULT_GROUP_INDEX)))
            .andExpect(jsonPath("$.[*].compareValue").value(hasItem(DEFAULT_COMPARE_VALUE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lineLogicType").value(hasItem(DEFAULT_LINE_LOGIC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].groupLogicType").value(hasItem(DEFAULT_GROUP_LOGIC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nextCondition").value(hasItem(DEFAULT_NEXT_CONDITION.intValue())));
    }

    @Test
    @Transactional
    void getConditionDetails() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get the conditionDetails
        restConditionDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, conditionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conditionDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.explanation").value(DEFAULT_EXPLANATION))
            .andExpect(jsonPath("$.compareField").value(DEFAULT_COMPARE_FIELD.toString()))
            .andExpect(jsonPath("$.operator").value(DEFAULT_OPERATOR.toString()))
            .andExpect(jsonPath("$.groupIndex").value(DEFAULT_GROUP_INDEX))
            .andExpect(jsonPath("$.compareValue").value(DEFAULT_COMPARE_VALUE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lineLogicType").value(DEFAULT_LINE_LOGIC_TYPE.toString()))
            .andExpect(jsonPath("$.groupLogicType").value(DEFAULT_GROUP_LOGIC_TYPE.toString()))
            .andExpect(jsonPath("$.nextCondition").value(DEFAULT_NEXT_CONDITION.intValue()));
    }

    @Test
    @Transactional
    void getConditionDetailsByIdFiltering() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        Long id = conditionDetails.getId();

        defaultConditionDetailsShouldBeFound("id.equals=" + id);
        defaultConditionDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultConditionDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConditionDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultConditionDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConditionDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where name equals to DEFAULT_NAME
        defaultConditionDetailsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the conditionDetailsList where name equals to UPDATED_NAME
        defaultConditionDetailsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultConditionDetailsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the conditionDetailsList where name equals to UPDATED_NAME
        defaultConditionDetailsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where name is not null
        defaultConditionDetailsShouldBeFound("name.specified=true");

        // Get all the conditionDetailsList where name is null
        defaultConditionDetailsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionDetailsByNameContainsSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where name contains DEFAULT_NAME
        defaultConditionDetailsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the conditionDetailsList where name contains UPDATED_NAME
        defaultConditionDetailsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where name does not contain DEFAULT_NAME
        defaultConditionDetailsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the conditionDetailsList where name does not contain UPDATED_NAME
        defaultConditionDetailsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByExplanationIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where explanation equals to DEFAULT_EXPLANATION
        defaultConditionDetailsShouldBeFound("explanation.equals=" + DEFAULT_EXPLANATION);

        // Get all the conditionDetailsList where explanation equals to UPDATED_EXPLANATION
        defaultConditionDetailsShouldNotBeFound("explanation.equals=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByExplanationIsInShouldWork() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where explanation in DEFAULT_EXPLANATION or UPDATED_EXPLANATION
        defaultConditionDetailsShouldBeFound("explanation.in=" + DEFAULT_EXPLANATION + "," + UPDATED_EXPLANATION);

        // Get all the conditionDetailsList where explanation equals to UPDATED_EXPLANATION
        defaultConditionDetailsShouldNotBeFound("explanation.in=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByExplanationIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where explanation is not null
        defaultConditionDetailsShouldBeFound("explanation.specified=true");

        // Get all the conditionDetailsList where explanation is null
        defaultConditionDetailsShouldNotBeFound("explanation.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionDetailsByExplanationContainsSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where explanation contains DEFAULT_EXPLANATION
        defaultConditionDetailsShouldBeFound("explanation.contains=" + DEFAULT_EXPLANATION);

        // Get all the conditionDetailsList where explanation contains UPDATED_EXPLANATION
        defaultConditionDetailsShouldNotBeFound("explanation.contains=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByExplanationNotContainsSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where explanation does not contain DEFAULT_EXPLANATION
        defaultConditionDetailsShouldNotBeFound("explanation.doesNotContain=" + DEFAULT_EXPLANATION);

        // Get all the conditionDetailsList where explanation does not contain UPDATED_EXPLANATION
        defaultConditionDetailsShouldBeFound("explanation.doesNotContain=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCompareFieldIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where compareField equals to DEFAULT_COMPARE_FIELD
        defaultConditionDetailsShouldBeFound("compareField.equals=" + DEFAULT_COMPARE_FIELD);

        // Get all the conditionDetailsList where compareField equals to UPDATED_COMPARE_FIELD
        defaultConditionDetailsShouldNotBeFound("compareField.equals=" + UPDATED_COMPARE_FIELD);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCompareFieldIsInShouldWork() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where compareField in DEFAULT_COMPARE_FIELD or UPDATED_COMPARE_FIELD
        defaultConditionDetailsShouldBeFound("compareField.in=" + DEFAULT_COMPARE_FIELD + "," + UPDATED_COMPARE_FIELD);

        // Get all the conditionDetailsList where compareField equals to UPDATED_COMPARE_FIELD
        defaultConditionDetailsShouldNotBeFound("compareField.in=" + UPDATED_COMPARE_FIELD);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCompareFieldIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where compareField is not null
        defaultConditionDetailsShouldBeFound("compareField.specified=true");

        // Get all the conditionDetailsList where compareField is null
        defaultConditionDetailsShouldNotBeFound("compareField.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionDetailsByOperatorIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where operator equals to DEFAULT_OPERATOR
        defaultConditionDetailsShouldBeFound("operator.equals=" + DEFAULT_OPERATOR);

        // Get all the conditionDetailsList where operator equals to UPDATED_OPERATOR
        defaultConditionDetailsShouldNotBeFound("operator.equals=" + UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByOperatorIsInShouldWork() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where operator in DEFAULT_OPERATOR or UPDATED_OPERATOR
        defaultConditionDetailsShouldBeFound("operator.in=" + DEFAULT_OPERATOR + "," + UPDATED_OPERATOR);

        // Get all the conditionDetailsList where operator equals to UPDATED_OPERATOR
        defaultConditionDetailsShouldNotBeFound("operator.in=" + UPDATED_OPERATOR);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByOperatorIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where operator is not null
        defaultConditionDetailsShouldBeFound("operator.specified=true");

        // Get all the conditionDetailsList where operator is null
        defaultConditionDetailsShouldNotBeFound("operator.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionDetailsByGroupIndexIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where groupIndex equals to DEFAULT_GROUP_INDEX
        defaultConditionDetailsShouldBeFound("groupIndex.equals=" + DEFAULT_GROUP_INDEX);

        // Get all the conditionDetailsList where groupIndex equals to UPDATED_GROUP_INDEX
        defaultConditionDetailsShouldNotBeFound("groupIndex.equals=" + UPDATED_GROUP_INDEX);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByGroupIndexIsInShouldWork() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where groupIndex in DEFAULT_GROUP_INDEX or UPDATED_GROUP_INDEX
        defaultConditionDetailsShouldBeFound("groupIndex.in=" + DEFAULT_GROUP_INDEX + "," + UPDATED_GROUP_INDEX);

        // Get all the conditionDetailsList where groupIndex equals to UPDATED_GROUP_INDEX
        defaultConditionDetailsShouldNotBeFound("groupIndex.in=" + UPDATED_GROUP_INDEX);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByGroupIndexIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where groupIndex is not null
        defaultConditionDetailsShouldBeFound("groupIndex.specified=true");

        // Get all the conditionDetailsList where groupIndex is null
        defaultConditionDetailsShouldNotBeFound("groupIndex.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionDetailsByGroupIndexIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where groupIndex is greater than or equal to DEFAULT_GROUP_INDEX
        defaultConditionDetailsShouldBeFound("groupIndex.greaterThanOrEqual=" + DEFAULT_GROUP_INDEX);

        // Get all the conditionDetailsList where groupIndex is greater than or equal to UPDATED_GROUP_INDEX
        defaultConditionDetailsShouldNotBeFound("groupIndex.greaterThanOrEqual=" + UPDATED_GROUP_INDEX);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByGroupIndexIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where groupIndex is less than or equal to DEFAULT_GROUP_INDEX
        defaultConditionDetailsShouldBeFound("groupIndex.lessThanOrEqual=" + DEFAULT_GROUP_INDEX);

        // Get all the conditionDetailsList where groupIndex is less than or equal to SMALLER_GROUP_INDEX
        defaultConditionDetailsShouldNotBeFound("groupIndex.lessThanOrEqual=" + SMALLER_GROUP_INDEX);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByGroupIndexIsLessThanSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where groupIndex is less than DEFAULT_GROUP_INDEX
        defaultConditionDetailsShouldNotBeFound("groupIndex.lessThan=" + DEFAULT_GROUP_INDEX);

        // Get all the conditionDetailsList where groupIndex is less than UPDATED_GROUP_INDEX
        defaultConditionDetailsShouldBeFound("groupIndex.lessThan=" + UPDATED_GROUP_INDEX);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByGroupIndexIsGreaterThanSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where groupIndex is greater than DEFAULT_GROUP_INDEX
        defaultConditionDetailsShouldNotBeFound("groupIndex.greaterThan=" + DEFAULT_GROUP_INDEX);

        // Get all the conditionDetailsList where groupIndex is greater than SMALLER_GROUP_INDEX
        defaultConditionDetailsShouldBeFound("groupIndex.greaterThan=" + SMALLER_GROUP_INDEX);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCompareValueIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where compareValue equals to DEFAULT_COMPARE_VALUE
        defaultConditionDetailsShouldBeFound("compareValue.equals=" + DEFAULT_COMPARE_VALUE);

        // Get all the conditionDetailsList where compareValue equals to UPDATED_COMPARE_VALUE
        defaultConditionDetailsShouldNotBeFound("compareValue.equals=" + UPDATED_COMPARE_VALUE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCompareValueIsInShouldWork() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where compareValue in DEFAULT_COMPARE_VALUE or UPDATED_COMPARE_VALUE
        defaultConditionDetailsShouldBeFound("compareValue.in=" + DEFAULT_COMPARE_VALUE + "," + UPDATED_COMPARE_VALUE);

        // Get all the conditionDetailsList where compareValue equals to UPDATED_COMPARE_VALUE
        defaultConditionDetailsShouldNotBeFound("compareValue.in=" + UPDATED_COMPARE_VALUE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCompareValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where compareValue is not null
        defaultConditionDetailsShouldBeFound("compareValue.specified=true");

        // Get all the conditionDetailsList where compareValue is null
        defaultConditionDetailsShouldNotBeFound("compareValue.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCompareValueContainsSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where compareValue contains DEFAULT_COMPARE_VALUE
        defaultConditionDetailsShouldBeFound("compareValue.contains=" + DEFAULT_COMPARE_VALUE);

        // Get all the conditionDetailsList where compareValue contains UPDATED_COMPARE_VALUE
        defaultConditionDetailsShouldNotBeFound("compareValue.contains=" + UPDATED_COMPARE_VALUE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCompareValueNotContainsSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where compareValue does not contain DEFAULT_COMPARE_VALUE
        defaultConditionDetailsShouldNotBeFound("compareValue.doesNotContain=" + DEFAULT_COMPARE_VALUE);

        // Get all the conditionDetailsList where compareValue does not contain UPDATED_COMPARE_VALUE
        defaultConditionDetailsShouldBeFound("compareValue.doesNotContain=" + UPDATED_COMPARE_VALUE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultConditionDetailsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the conditionDetailsList where createdDate equals to UPDATED_CREATED_DATE
        defaultConditionDetailsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultConditionDetailsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the conditionDetailsList where createdDate equals to UPDATED_CREATED_DATE
        defaultConditionDetailsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where createdDate is not null
        defaultConditionDetailsShouldBeFound("createdDate.specified=true");

        // Get all the conditionDetailsList where createdDate is null
        defaultConditionDetailsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultConditionDetailsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the conditionDetailsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultConditionDetailsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultConditionDetailsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the conditionDetailsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultConditionDetailsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultConditionDetailsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the conditionDetailsList where createdDate is less than UPDATED_CREATED_DATE
        defaultConditionDetailsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultConditionDetailsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the conditionDetailsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultConditionDetailsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByLineLogicTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where lineLogicType equals to DEFAULT_LINE_LOGIC_TYPE
        defaultConditionDetailsShouldBeFound("lineLogicType.equals=" + DEFAULT_LINE_LOGIC_TYPE);

        // Get all the conditionDetailsList where lineLogicType equals to UPDATED_LINE_LOGIC_TYPE
        defaultConditionDetailsShouldNotBeFound("lineLogicType.equals=" + UPDATED_LINE_LOGIC_TYPE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByLineLogicTypeIsInShouldWork() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where lineLogicType in DEFAULT_LINE_LOGIC_TYPE or UPDATED_LINE_LOGIC_TYPE
        defaultConditionDetailsShouldBeFound("lineLogicType.in=" + DEFAULT_LINE_LOGIC_TYPE + "," + UPDATED_LINE_LOGIC_TYPE);

        // Get all the conditionDetailsList where lineLogicType equals to UPDATED_LINE_LOGIC_TYPE
        defaultConditionDetailsShouldNotBeFound("lineLogicType.in=" + UPDATED_LINE_LOGIC_TYPE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByLineLogicTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where lineLogicType is not null
        defaultConditionDetailsShouldBeFound("lineLogicType.specified=true");

        // Get all the conditionDetailsList where lineLogicType is null
        defaultConditionDetailsShouldNotBeFound("lineLogicType.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionDetailsByGroupLogicTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where groupLogicType equals to DEFAULT_GROUP_LOGIC_TYPE
        defaultConditionDetailsShouldBeFound("groupLogicType.equals=" + DEFAULT_GROUP_LOGIC_TYPE);

        // Get all the conditionDetailsList where groupLogicType equals to UPDATED_GROUP_LOGIC_TYPE
        defaultConditionDetailsShouldNotBeFound("groupLogicType.equals=" + UPDATED_GROUP_LOGIC_TYPE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByGroupLogicTypeIsInShouldWork() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where groupLogicType in DEFAULT_GROUP_LOGIC_TYPE or UPDATED_GROUP_LOGIC_TYPE
        defaultConditionDetailsShouldBeFound("groupLogicType.in=" + DEFAULT_GROUP_LOGIC_TYPE + "," + UPDATED_GROUP_LOGIC_TYPE);

        // Get all the conditionDetailsList where groupLogicType equals to UPDATED_GROUP_LOGIC_TYPE
        defaultConditionDetailsShouldNotBeFound("groupLogicType.in=" + UPDATED_GROUP_LOGIC_TYPE);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByGroupLogicTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where groupLogicType is not null
        defaultConditionDetailsShouldBeFound("groupLogicType.specified=true");

        // Get all the conditionDetailsList where groupLogicType is null
        defaultConditionDetailsShouldNotBeFound("groupLogicType.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionDetailsByNextConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where nextCondition equals to DEFAULT_NEXT_CONDITION
        defaultConditionDetailsShouldBeFound("nextCondition.equals=" + DEFAULT_NEXT_CONDITION);

        // Get all the conditionDetailsList where nextCondition equals to UPDATED_NEXT_CONDITION
        defaultConditionDetailsShouldNotBeFound("nextCondition.equals=" + UPDATED_NEXT_CONDITION);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByNextConditionIsInShouldWork() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where nextCondition in DEFAULT_NEXT_CONDITION or UPDATED_NEXT_CONDITION
        defaultConditionDetailsShouldBeFound("nextCondition.in=" + DEFAULT_NEXT_CONDITION + "," + UPDATED_NEXT_CONDITION);

        // Get all the conditionDetailsList where nextCondition equals to UPDATED_NEXT_CONDITION
        defaultConditionDetailsShouldNotBeFound("nextCondition.in=" + UPDATED_NEXT_CONDITION);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByNextConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where nextCondition is not null
        defaultConditionDetailsShouldBeFound("nextCondition.specified=true");

        // Get all the conditionDetailsList where nextCondition is null
        defaultConditionDetailsShouldNotBeFound("nextCondition.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionDetailsByNextConditionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where nextCondition is greater than or equal to DEFAULT_NEXT_CONDITION
        defaultConditionDetailsShouldBeFound("nextCondition.greaterThanOrEqual=" + DEFAULT_NEXT_CONDITION);

        // Get all the conditionDetailsList where nextCondition is greater than or equal to UPDATED_NEXT_CONDITION
        defaultConditionDetailsShouldNotBeFound("nextCondition.greaterThanOrEqual=" + UPDATED_NEXT_CONDITION);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByNextConditionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where nextCondition is less than or equal to DEFAULT_NEXT_CONDITION
        defaultConditionDetailsShouldBeFound("nextCondition.lessThanOrEqual=" + DEFAULT_NEXT_CONDITION);

        // Get all the conditionDetailsList where nextCondition is less than or equal to SMALLER_NEXT_CONDITION
        defaultConditionDetailsShouldNotBeFound("nextCondition.lessThanOrEqual=" + SMALLER_NEXT_CONDITION);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByNextConditionIsLessThanSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where nextCondition is less than DEFAULT_NEXT_CONDITION
        defaultConditionDetailsShouldNotBeFound("nextCondition.lessThan=" + DEFAULT_NEXT_CONDITION);

        // Get all the conditionDetailsList where nextCondition is less than UPDATED_NEXT_CONDITION
        defaultConditionDetailsShouldBeFound("nextCondition.lessThan=" + UPDATED_NEXT_CONDITION);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByNextConditionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        // Get all the conditionDetailsList where nextCondition is greater than DEFAULT_NEXT_CONDITION
        defaultConditionDetailsShouldNotBeFound("nextCondition.greaterThan=" + DEFAULT_NEXT_CONDITION);

        // Get all the conditionDetailsList where nextCondition is greater than SMALLER_NEXT_CONDITION
        defaultConditionDetailsShouldBeFound("nextCondition.greaterThan=" + SMALLER_NEXT_CONDITION);
    }

    @Test
    @Transactional
    void getAllConditionDetailsByConditionIsEqualToSomething() throws Exception {
        Conditions condition;
        if (TestUtil.findAll(em, Conditions.class).isEmpty()) {
            conditionDetailsRepository.saveAndFlush(conditionDetails);
            condition = ConditionsResourceIT.createEntity(em);
        } else {
            condition = TestUtil.findAll(em, Conditions.class).get(0);
        }
        em.persist(condition);
        em.flush();
        conditionDetails.addCondition(condition);
        conditionDetailsRepository.saveAndFlush(conditionDetails);
        Long conditionId = condition.getId();

        // Get all the conditionDetailsList where condition equals to conditionId
        defaultConditionDetailsShouldBeFound("conditionId.equals=" + conditionId);

        // Get all the conditionDetailsList where condition equals to (conditionId + 1)
        defaultConditionDetailsShouldNotBeFound("conditionId.equals=" + (conditionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConditionDetailsShouldBeFound(String filter) throws Exception {
        restConditionDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conditionDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].explanation").value(hasItem(DEFAULT_EXPLANATION)))
            .andExpect(jsonPath("$.[*].compareField").value(hasItem(DEFAULT_COMPARE_FIELD.toString())))
            .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR.toString())))
            .andExpect(jsonPath("$.[*].groupIndex").value(hasItem(DEFAULT_GROUP_INDEX)))
            .andExpect(jsonPath("$.[*].compareValue").value(hasItem(DEFAULT_COMPARE_VALUE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lineLogicType").value(hasItem(DEFAULT_LINE_LOGIC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].groupLogicType").value(hasItem(DEFAULT_GROUP_LOGIC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nextCondition").value(hasItem(DEFAULT_NEXT_CONDITION.intValue())));

        // Check, that the count call also returns 1
        restConditionDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConditionDetailsShouldNotBeFound(String filter) throws Exception {
        restConditionDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConditionDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConditionDetails() throws Exception {
        // Get the conditionDetails
        restConditionDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConditionDetails() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        int databaseSizeBeforeUpdate = conditionDetailsRepository.findAll().size();

        // Update the conditionDetails
        ConditionDetails updatedConditionDetails = conditionDetailsRepository.findById(conditionDetails.getId()).get();
        // Disconnect from session so that the updates on updatedConditionDetails are not directly saved in db
        em.detach(updatedConditionDetails);
        updatedConditionDetails
            .name(UPDATED_NAME)
            .explanation(UPDATED_EXPLANATION)
            .compareField(UPDATED_COMPARE_FIELD)
            .operator(UPDATED_OPERATOR)
            .groupIndex(UPDATED_GROUP_INDEX)
            .compareValue(UPDATED_COMPARE_VALUE)
            .createdDate(UPDATED_CREATED_DATE)
            .lineLogicType(UPDATED_LINE_LOGIC_TYPE)
            .groupLogicType(UPDATED_GROUP_LOGIC_TYPE)
            .nextCondition(UPDATED_NEXT_CONDITION);
        ConditionDetailsDTO conditionDetailsDTO = conditionDetailsMapper.toDto(updatedConditionDetails);

        restConditionDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conditionDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeUpdate);
        ConditionDetails testConditionDetails = conditionDetailsList.get(conditionDetailsList.size() - 1);
        assertThat(testConditionDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConditionDetails.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testConditionDetails.getCompareField()).isEqualTo(UPDATED_COMPARE_FIELD);
        assertThat(testConditionDetails.getOperator()).isEqualTo(UPDATED_OPERATOR);
        assertThat(testConditionDetails.getGroupIndex()).isEqualTo(UPDATED_GROUP_INDEX);
        assertThat(testConditionDetails.getCompareValue()).isEqualTo(UPDATED_COMPARE_VALUE);
        assertThat(testConditionDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testConditionDetails.getLineLogicType()).isEqualTo(UPDATED_LINE_LOGIC_TYPE);
        assertThat(testConditionDetails.getGroupLogicType()).isEqualTo(UPDATED_GROUP_LOGIC_TYPE);
        assertThat(testConditionDetails.getNextCondition()).isEqualTo(UPDATED_NEXT_CONDITION);
    }

    @Test
    @Transactional
    void putNonExistingConditionDetails() throws Exception {
        int databaseSizeBeforeUpdate = conditionDetailsRepository.findAll().size();
        conditionDetails.setId(count.incrementAndGet());

        // Create the ConditionDetails
        ConditionDetailsDTO conditionDetailsDTO = conditionDetailsMapper.toDto(conditionDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conditionDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConditionDetails() throws Exception {
        int databaseSizeBeforeUpdate = conditionDetailsRepository.findAll().size();
        conditionDetails.setId(count.incrementAndGet());

        // Create the ConditionDetails
        ConditionDetailsDTO conditionDetailsDTO = conditionDetailsMapper.toDto(conditionDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConditionDetails() throws Exception {
        int databaseSizeBeforeUpdate = conditionDetailsRepository.findAll().size();
        conditionDetails.setId(count.incrementAndGet());

        // Create the ConditionDetails
        ConditionDetailsDTO conditionDetailsDTO = conditionDetailsMapper.toDto(conditionDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conditionDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConditionDetailsWithPatch() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        int databaseSizeBeforeUpdate = conditionDetailsRepository.findAll().size();

        // Update the conditionDetails using partial update
        ConditionDetails partialUpdatedConditionDetails = new ConditionDetails();
        partialUpdatedConditionDetails.setId(conditionDetails.getId());

        partialUpdatedConditionDetails
            .name(UPDATED_NAME)
            .explanation(UPDATED_EXPLANATION)
            .compareField(UPDATED_COMPARE_FIELD)
            .groupIndex(UPDATED_GROUP_INDEX)
            .compareValue(UPDATED_COMPARE_VALUE)
            .lineLogicType(UPDATED_LINE_LOGIC_TYPE);

        restConditionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConditionDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConditionDetails))
            )
            .andExpect(status().isOk());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeUpdate);
        ConditionDetails testConditionDetails = conditionDetailsList.get(conditionDetailsList.size() - 1);
        assertThat(testConditionDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConditionDetails.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testConditionDetails.getCompareField()).isEqualTo(UPDATED_COMPARE_FIELD);
        assertThat(testConditionDetails.getOperator()).isEqualTo(DEFAULT_OPERATOR);
        assertThat(testConditionDetails.getGroupIndex()).isEqualTo(UPDATED_GROUP_INDEX);
        assertThat(testConditionDetails.getCompareValue()).isEqualTo(UPDATED_COMPARE_VALUE);
        assertThat(testConditionDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testConditionDetails.getLineLogicType()).isEqualTo(UPDATED_LINE_LOGIC_TYPE);
        assertThat(testConditionDetails.getGroupLogicType()).isEqualTo(DEFAULT_GROUP_LOGIC_TYPE);
        assertThat(testConditionDetails.getNextCondition()).isEqualTo(DEFAULT_NEXT_CONDITION);
    }

    @Test
    @Transactional
    void fullUpdateConditionDetailsWithPatch() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        int databaseSizeBeforeUpdate = conditionDetailsRepository.findAll().size();

        // Update the conditionDetails using partial update
        ConditionDetails partialUpdatedConditionDetails = new ConditionDetails();
        partialUpdatedConditionDetails.setId(conditionDetails.getId());

        partialUpdatedConditionDetails
            .name(UPDATED_NAME)
            .explanation(UPDATED_EXPLANATION)
            .compareField(UPDATED_COMPARE_FIELD)
            .operator(UPDATED_OPERATOR)
            .groupIndex(UPDATED_GROUP_INDEX)
            .compareValue(UPDATED_COMPARE_VALUE)
            .createdDate(UPDATED_CREATED_DATE)
            .lineLogicType(UPDATED_LINE_LOGIC_TYPE)
            .groupLogicType(UPDATED_GROUP_LOGIC_TYPE)
            .nextCondition(UPDATED_NEXT_CONDITION);

        restConditionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConditionDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConditionDetails))
            )
            .andExpect(status().isOk());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeUpdate);
        ConditionDetails testConditionDetails = conditionDetailsList.get(conditionDetailsList.size() - 1);
        assertThat(testConditionDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConditionDetails.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testConditionDetails.getCompareField()).isEqualTo(UPDATED_COMPARE_FIELD);
        assertThat(testConditionDetails.getOperator()).isEqualTo(UPDATED_OPERATOR);
        assertThat(testConditionDetails.getGroupIndex()).isEqualTo(UPDATED_GROUP_INDEX);
        assertThat(testConditionDetails.getCompareValue()).isEqualTo(UPDATED_COMPARE_VALUE);
        assertThat(testConditionDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testConditionDetails.getLineLogicType()).isEqualTo(UPDATED_LINE_LOGIC_TYPE);
        assertThat(testConditionDetails.getGroupLogicType()).isEqualTo(UPDATED_GROUP_LOGIC_TYPE);
        assertThat(testConditionDetails.getNextCondition()).isEqualTo(UPDATED_NEXT_CONDITION);
    }

    @Test
    @Transactional
    void patchNonExistingConditionDetails() throws Exception {
        int databaseSizeBeforeUpdate = conditionDetailsRepository.findAll().size();
        conditionDetails.setId(count.incrementAndGet());

        // Create the ConditionDetails
        ConditionDetailsDTO conditionDetailsDTO = conditionDetailsMapper.toDto(conditionDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conditionDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conditionDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConditionDetails() throws Exception {
        int databaseSizeBeforeUpdate = conditionDetailsRepository.findAll().size();
        conditionDetails.setId(count.incrementAndGet());

        // Create the ConditionDetails
        ConditionDetailsDTO conditionDetailsDTO = conditionDetailsMapper.toDto(conditionDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conditionDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConditionDetails() throws Exception {
        int databaseSizeBeforeUpdate = conditionDetailsRepository.findAll().size();
        conditionDetails.setId(count.incrementAndGet());

        // Create the ConditionDetails
        ConditionDetailsDTO conditionDetailsDTO = conditionDetailsMapper.toDto(conditionDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conditionDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConditionDetails in the database
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConditionDetails() throws Exception {
        // Initialize the database
        conditionDetailsRepository.saveAndFlush(conditionDetails);

        int databaseSizeBeforeDelete = conditionDetailsRepository.findAll().size();

        // Delete the conditionDetails
        restConditionDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, conditionDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConditionDetails> conditionDetailsList = conditionDetailsRepository.findAll();
        assertThat(conditionDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
