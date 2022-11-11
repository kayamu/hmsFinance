package com.polarbears.capstone.hmsfinance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmsfinance.IntegrationTest;
import com.polarbears.capstone.hmsfinance.domain.ConditionDetails;
import com.polarbears.capstone.hmsfinance.domain.Conditions;
import com.polarbears.capstone.hmsfinance.domain.TemplateItems;
import com.polarbears.capstone.hmsfinance.domain.enumeration.VALUETYPES;
import com.polarbears.capstone.hmsfinance.repository.ConditionsRepository;
import com.polarbears.capstone.hmsfinance.service.ConditionsService;
import com.polarbears.capstone.hmsfinance.service.criteria.ConditionsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.ConditionsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.ConditionsMapper;
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
 * Integration tests for the {@link ConditionsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ConditionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final VALUETYPES DEFAULT_TYPE = VALUETYPES.PERCENTAGE;
    private static final VALUETYPES UPDATED_TYPE = VALUETYPES.AMOUNT;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/conditions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConditionsRepository conditionsRepository;

    @Mock
    private ConditionsRepository conditionsRepositoryMock;

    @Autowired
    private ConditionsMapper conditionsMapper;

    @Mock
    private ConditionsService conditionsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConditionsMockMvc;

    private Conditions conditions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conditions createEntity(EntityManager em) {
        Conditions conditions = new Conditions().name(DEFAULT_NAME).type(DEFAULT_TYPE).createdDate(DEFAULT_CREATED_DATE);
        return conditions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conditions createUpdatedEntity(EntityManager em) {
        Conditions conditions = new Conditions().name(UPDATED_NAME).type(UPDATED_TYPE).createdDate(UPDATED_CREATED_DATE);
        return conditions;
    }

    @BeforeEach
    public void initTest() {
        conditions = createEntity(em);
    }

    @Test
    @Transactional
    void createConditions() throws Exception {
        int databaseSizeBeforeCreate = conditionsRepository.findAll().size();
        // Create the Conditions
        ConditionsDTO conditionsDTO = conditionsMapper.toDto(conditions);
        restConditionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conditionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeCreate + 1);
        Conditions testConditions = conditionsList.get(conditionsList.size() - 1);
        assertThat(testConditions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testConditions.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testConditions.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createConditionsWithExistingId() throws Exception {
        // Create the Conditions with an existing ID
        conditions.setId(1L);
        ConditionsDTO conditionsDTO = conditionsMapper.toDto(conditions);

        int databaseSizeBeforeCreate = conditionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConditionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conditionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConditions() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList
        restConditionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conditions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConditionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(conditionsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConditionsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(conditionsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllConditionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(conditionsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restConditionsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(conditionsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getConditions() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get the conditions
        restConditionsMockMvc
            .perform(get(ENTITY_API_URL_ID, conditions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conditions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getConditionsByIdFiltering() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        Long id = conditions.getId();

        defaultConditionsShouldBeFound("id.equals=" + id);
        defaultConditionsShouldNotBeFound("id.notEquals=" + id);

        defaultConditionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConditionsShouldNotBeFound("id.greaterThan=" + id);

        defaultConditionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConditionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllConditionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where name equals to DEFAULT_NAME
        defaultConditionsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the conditionsList where name equals to UPDATED_NAME
        defaultConditionsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConditionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultConditionsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the conditionsList where name equals to UPDATED_NAME
        defaultConditionsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConditionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where name is not null
        defaultConditionsShouldBeFound("name.specified=true");

        // Get all the conditionsList where name is null
        defaultConditionsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionsByNameContainsSomething() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where name contains DEFAULT_NAME
        defaultConditionsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the conditionsList where name contains UPDATED_NAME
        defaultConditionsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConditionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where name does not contain DEFAULT_NAME
        defaultConditionsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the conditionsList where name does not contain UPDATED_NAME
        defaultConditionsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllConditionsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where type equals to DEFAULT_TYPE
        defaultConditionsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the conditionsList where type equals to UPDATED_TYPE
        defaultConditionsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllConditionsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultConditionsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the conditionsList where type equals to UPDATED_TYPE
        defaultConditionsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllConditionsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where type is not null
        defaultConditionsShouldBeFound("type.specified=true");

        // Get all the conditionsList where type is null
        defaultConditionsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultConditionsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the conditionsList where createdDate equals to UPDATED_CREATED_DATE
        defaultConditionsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConditionsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultConditionsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the conditionsList where createdDate equals to UPDATED_CREATED_DATE
        defaultConditionsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConditionsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where createdDate is not null
        defaultConditionsShouldBeFound("createdDate.specified=true");

        // Get all the conditionsList where createdDate is null
        defaultConditionsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllConditionsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultConditionsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the conditionsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultConditionsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConditionsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultConditionsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the conditionsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultConditionsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConditionsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultConditionsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the conditionsList where createdDate is less than UPDATED_CREATED_DATE
        defaultConditionsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConditionsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        // Get all the conditionsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultConditionsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the conditionsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultConditionsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllConditionsByTemplateItemIsEqualToSomething() throws Exception {
        TemplateItems templateItem;
        if (TestUtil.findAll(em, TemplateItems.class).isEmpty()) {
            conditionsRepository.saveAndFlush(conditions);
            templateItem = TemplateItemsResourceIT.createEntity(em);
        } else {
            templateItem = TestUtil.findAll(em, TemplateItems.class).get(0);
        }
        em.persist(templateItem);
        em.flush();
        conditions.addTemplateItem(templateItem);
        conditionsRepository.saveAndFlush(conditions);
        Long templateItemId = templateItem.getId();

        // Get all the conditionsList where templateItem equals to templateItemId
        defaultConditionsShouldBeFound("templateItemId.equals=" + templateItemId);

        // Get all the conditionsList where templateItem equals to (templateItemId + 1)
        defaultConditionsShouldNotBeFound("templateItemId.equals=" + (templateItemId + 1));
    }

    @Test
    @Transactional
    void getAllConditionsByConditionDetailsIsEqualToSomething() throws Exception {
        ConditionDetails conditionDetails;
        if (TestUtil.findAll(em, ConditionDetails.class).isEmpty()) {
            conditionsRepository.saveAndFlush(conditions);
            conditionDetails = ConditionDetailsResourceIT.createEntity(em);
        } else {
            conditionDetails = TestUtil.findAll(em, ConditionDetails.class).get(0);
        }
        em.persist(conditionDetails);
        em.flush();
        conditions.addConditionDetails(conditionDetails);
        conditionsRepository.saveAndFlush(conditions);
        Long conditionDetailsId = conditionDetails.getId();

        // Get all the conditionsList where conditionDetails equals to conditionDetailsId
        defaultConditionsShouldBeFound("conditionDetailsId.equals=" + conditionDetailsId);

        // Get all the conditionsList where conditionDetails equals to (conditionDetailsId + 1)
        defaultConditionsShouldNotBeFound("conditionDetailsId.equals=" + (conditionDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConditionsShouldBeFound(String filter) throws Exception {
        restConditionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conditions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restConditionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConditionsShouldNotBeFound(String filter) throws Exception {
        restConditionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConditionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingConditions() throws Exception {
        // Get the conditions
        restConditionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConditions() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        int databaseSizeBeforeUpdate = conditionsRepository.findAll().size();

        // Update the conditions
        Conditions updatedConditions = conditionsRepository.findById(conditions.getId()).get();
        // Disconnect from session so that the updates on updatedConditions are not directly saved in db
        em.detach(updatedConditions);
        updatedConditions.name(UPDATED_NAME).type(UPDATED_TYPE).createdDate(UPDATED_CREATED_DATE);
        ConditionsDTO conditionsDTO = conditionsMapper.toDto(updatedConditions);

        restConditionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conditionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeUpdate);
        Conditions testConditions = conditionsList.get(conditionsList.size() - 1);
        assertThat(testConditions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConditions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConditions.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingConditions() throws Exception {
        int databaseSizeBeforeUpdate = conditionsRepository.findAll().size();
        conditions.setId(count.incrementAndGet());

        // Create the Conditions
        ConditionsDTO conditionsDTO = conditionsMapper.toDto(conditions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conditionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConditions() throws Exception {
        int databaseSizeBeforeUpdate = conditionsRepository.findAll().size();
        conditions.setId(count.incrementAndGet());

        // Create the Conditions
        ConditionsDTO conditionsDTO = conditionsMapper.toDto(conditions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConditions() throws Exception {
        int databaseSizeBeforeUpdate = conditionsRepository.findAll().size();
        conditions.setId(count.incrementAndGet());

        // Create the Conditions
        ConditionsDTO conditionsDTO = conditionsMapper.toDto(conditions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conditionsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConditionsWithPatch() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        int databaseSizeBeforeUpdate = conditionsRepository.findAll().size();

        // Update the conditions using partial update
        Conditions partialUpdatedConditions = new Conditions();
        partialUpdatedConditions.setId(conditions.getId());

        partialUpdatedConditions.name(UPDATED_NAME).type(UPDATED_TYPE).createdDate(UPDATED_CREATED_DATE);

        restConditionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConditions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConditions))
            )
            .andExpect(status().isOk());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeUpdate);
        Conditions testConditions = conditionsList.get(conditionsList.size() - 1);
        assertThat(testConditions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConditions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConditions.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateConditionsWithPatch() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        int databaseSizeBeforeUpdate = conditionsRepository.findAll().size();

        // Update the conditions using partial update
        Conditions partialUpdatedConditions = new Conditions();
        partialUpdatedConditions.setId(conditions.getId());

        partialUpdatedConditions.name(UPDATED_NAME).type(UPDATED_TYPE).createdDate(UPDATED_CREATED_DATE);

        restConditionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConditions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConditions))
            )
            .andExpect(status().isOk());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeUpdate);
        Conditions testConditions = conditionsList.get(conditionsList.size() - 1);
        assertThat(testConditions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testConditions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testConditions.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingConditions() throws Exception {
        int databaseSizeBeforeUpdate = conditionsRepository.findAll().size();
        conditions.setId(count.incrementAndGet());

        // Create the Conditions
        ConditionsDTO conditionsDTO = conditionsMapper.toDto(conditions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conditionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConditions() throws Exception {
        int databaseSizeBeforeUpdate = conditionsRepository.findAll().size();
        conditions.setId(count.incrementAndGet());

        // Create the Conditions
        ConditionsDTO conditionsDTO = conditionsMapper.toDto(conditions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conditionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConditions() throws Exception {
        int databaseSizeBeforeUpdate = conditionsRepository.findAll().size();
        conditions.setId(count.incrementAndGet());

        // Create the Conditions
        ConditionsDTO conditionsDTO = conditionsMapper.toDto(conditions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConditionsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(conditionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conditions in the database
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConditions() throws Exception {
        // Initialize the database
        conditionsRepository.saveAndFlush(conditions);

        int databaseSizeBeforeDelete = conditionsRepository.findAll().size();

        // Delete the conditions
        restConditionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, conditions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conditions> conditionsList = conditionsRepository.findAll();
        assertThat(conditionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
