package com.polarbears.capstone.hmsfinance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmsfinance.IntegrationTest;
import com.polarbears.capstone.hmsfinance.domain.Items;
import com.polarbears.capstone.hmsfinance.domain.TemplateItems;
import com.polarbears.capstone.hmsfinance.domain.Templates;
import com.polarbears.capstone.hmsfinance.domain.enumeration.ITEMTYPES;
import com.polarbears.capstone.hmsfinance.repository.TemplatesRepository;
import com.polarbears.capstone.hmsfinance.service.TemplatesService;
import com.polarbears.capstone.hmsfinance.service.criteria.TemplatesCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.TemplatesDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.TemplatesMapper;
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
 * Integration tests for the {@link TemplatesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TemplatesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ITEMTYPES DEFAULT_TYPE = ITEMTYPES.PRODUCT;
    private static final ITEMTYPES UPDATED_TYPE = ITEMTYPES.SERVICE;

    private static final String DEFAULT_EXPLANATION = "AAAAAAAAAA";
    private static final String UPDATED_EXPLANATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TemplatesRepository templatesRepository;

    @Mock
    private TemplatesRepository templatesRepositoryMock;

    @Autowired
    private TemplatesMapper templatesMapper;

    @Mock
    private TemplatesService templatesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemplatesMockMvc;

    private Templates templates;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Templates createEntity(EntityManager em) {
        Templates templates = new Templates()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .explanation(DEFAULT_EXPLANATION)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdDate(DEFAULT_CREATED_DATE);
        return templates;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Templates createUpdatedEntity(EntityManager em) {
        Templates templates = new Templates()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .explanation(UPDATED_EXPLANATION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE);
        return templates;
    }

    @BeforeEach
    public void initTest() {
        templates = createEntity(em);
    }

    @Test
    @Transactional
    void createTemplates() throws Exception {
        int databaseSizeBeforeCreate = templatesRepository.findAll().size();
        // Create the Templates
        TemplatesDTO templatesDTO = templatesMapper.toDto(templates);
        restTemplatesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templatesDTO)))
            .andExpect(status().isCreated());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeCreate + 1);
        Templates testTemplates = templatesList.get(templatesList.size() - 1);
        assertThat(testTemplates.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTemplates.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTemplates.getExplanation()).isEqualTo(DEFAULT_EXPLANATION);
        assertThat(testTemplates.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testTemplates.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createTemplatesWithExistingId() throws Exception {
        // Create the Templates with an existing ID
        templates.setId(1L);
        TemplatesDTO templatesDTO = templatesMapper.toDto(templates);

        int databaseSizeBeforeCreate = templatesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplatesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList
        restTemplatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templates.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].explanation").value(hasItem(DEFAULT_EXPLANATION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTemplatesWithEagerRelationshipsIsEnabled() throws Exception {
        when(templatesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTemplatesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(templatesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTemplatesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(templatesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTemplatesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(templatesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get the templates
        restTemplatesMockMvc
            .perform(get(ENTITY_API_URL_ID, templates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(templates.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.explanation").value(DEFAULT_EXPLANATION))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getTemplatesByIdFiltering() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        Long id = templates.getId();

        defaultTemplatesShouldBeFound("id.equals=" + id);
        defaultTemplatesShouldNotBeFound("id.notEquals=" + id);

        defaultTemplatesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTemplatesShouldNotBeFound("id.greaterThan=" + id);

        defaultTemplatesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTemplatesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTemplatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where name equals to DEFAULT_NAME
        defaultTemplatesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the templatesList where name equals to UPDATED_NAME
        defaultTemplatesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTemplatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTemplatesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the templatesList where name equals to UPDATED_NAME
        defaultTemplatesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTemplatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where name is not null
        defaultTemplatesShouldBeFound("name.specified=true");

        // Get all the templatesList where name is null
        defaultTemplatesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplatesByNameContainsSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where name contains DEFAULT_NAME
        defaultTemplatesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the templatesList where name contains UPDATED_NAME
        defaultTemplatesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTemplatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where name does not contain DEFAULT_NAME
        defaultTemplatesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the templatesList where name does not contain UPDATED_NAME
        defaultTemplatesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllTemplatesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where type equals to DEFAULT_TYPE
        defaultTemplatesShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the templatesList where type equals to UPDATED_TYPE
        defaultTemplatesShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllTemplatesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultTemplatesShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the templatesList where type equals to UPDATED_TYPE
        defaultTemplatesShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllTemplatesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where type is not null
        defaultTemplatesShouldBeFound("type.specified=true");

        // Get all the templatesList where type is null
        defaultTemplatesShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplatesByExplanationIsEqualToSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where explanation equals to DEFAULT_EXPLANATION
        defaultTemplatesShouldBeFound("explanation.equals=" + DEFAULT_EXPLANATION);

        // Get all the templatesList where explanation equals to UPDATED_EXPLANATION
        defaultTemplatesShouldNotBeFound("explanation.equals=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllTemplatesByExplanationIsInShouldWork() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where explanation in DEFAULT_EXPLANATION or UPDATED_EXPLANATION
        defaultTemplatesShouldBeFound("explanation.in=" + DEFAULT_EXPLANATION + "," + UPDATED_EXPLANATION);

        // Get all the templatesList where explanation equals to UPDATED_EXPLANATION
        defaultTemplatesShouldNotBeFound("explanation.in=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllTemplatesByExplanationIsNullOrNotNull() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where explanation is not null
        defaultTemplatesShouldBeFound("explanation.specified=true");

        // Get all the templatesList where explanation is null
        defaultTemplatesShouldNotBeFound("explanation.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplatesByExplanationContainsSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where explanation contains DEFAULT_EXPLANATION
        defaultTemplatesShouldBeFound("explanation.contains=" + DEFAULT_EXPLANATION);

        // Get all the templatesList where explanation contains UPDATED_EXPLANATION
        defaultTemplatesShouldNotBeFound("explanation.contains=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllTemplatesByExplanationNotContainsSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where explanation does not contain DEFAULT_EXPLANATION
        defaultTemplatesShouldNotBeFound("explanation.doesNotContain=" + DEFAULT_EXPLANATION);

        // Get all the templatesList where explanation does not contain UPDATED_EXPLANATION
        defaultTemplatesShouldBeFound("explanation.doesNotContain=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllTemplatesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where isActive equals to DEFAULT_IS_ACTIVE
        defaultTemplatesShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the templatesList where isActive equals to UPDATED_IS_ACTIVE
        defaultTemplatesShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTemplatesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultTemplatesShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the templatesList where isActive equals to UPDATED_IS_ACTIVE
        defaultTemplatesShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTemplatesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where isActive is not null
        defaultTemplatesShouldBeFound("isActive.specified=true");

        // Get all the templatesList where isActive is null
        defaultTemplatesShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplatesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where createdDate equals to DEFAULT_CREATED_DATE
        defaultTemplatesShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the templatesList where createdDate equals to UPDATED_CREATED_DATE
        defaultTemplatesShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTemplatesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultTemplatesShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the templatesList where createdDate equals to UPDATED_CREATED_DATE
        defaultTemplatesShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTemplatesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where createdDate is not null
        defaultTemplatesShouldBeFound("createdDate.specified=true");

        // Get all the templatesList where createdDate is null
        defaultTemplatesShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTemplatesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultTemplatesShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the templatesList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultTemplatesShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTemplatesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultTemplatesShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the templatesList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultTemplatesShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTemplatesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where createdDate is less than DEFAULT_CREATED_DATE
        defaultTemplatesShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the templatesList where createdDate is less than UPDATED_CREATED_DATE
        defaultTemplatesShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTemplatesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        // Get all the templatesList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultTemplatesShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the templatesList where createdDate is greater than SMALLER_CREATED_DATE
        defaultTemplatesShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTemplatesByItemsIsEqualToSomething() throws Exception {
        Items items;
        if (TestUtil.findAll(em, Items.class).isEmpty()) {
            templatesRepository.saveAndFlush(templates);
            items = ItemsResourceIT.createEntity(em);
        } else {
            items = TestUtil.findAll(em, Items.class).get(0);
        }
        em.persist(items);
        em.flush();
        templates.addItems(items);
        templatesRepository.saveAndFlush(templates);
        Long itemsId = items.getId();

        // Get all the templatesList where items equals to itemsId
        defaultTemplatesShouldBeFound("itemsId.equals=" + itemsId);

        // Get all the templatesList where items equals to (itemsId + 1)
        defaultTemplatesShouldNotBeFound("itemsId.equals=" + (itemsId + 1));
    }

    @Test
    @Transactional
    void getAllTemplatesByTemplateItemsIsEqualToSomething() throws Exception {
        TemplateItems templateItems;
        if (TestUtil.findAll(em, TemplateItems.class).isEmpty()) {
            templatesRepository.saveAndFlush(templates);
            templateItems = TemplateItemsResourceIT.createEntity(em);
        } else {
            templateItems = TestUtil.findAll(em, TemplateItems.class).get(0);
        }
        em.persist(templateItems);
        em.flush();
        templates.addTemplateItems(templateItems);
        templatesRepository.saveAndFlush(templates);
        Long templateItemsId = templateItems.getId();

        // Get all the templatesList where templateItems equals to templateItemsId
        defaultTemplatesShouldBeFound("templateItemsId.equals=" + templateItemsId);

        // Get all the templatesList where templateItems equals to (templateItemsId + 1)
        defaultTemplatesShouldNotBeFound("templateItemsId.equals=" + (templateItemsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTemplatesShouldBeFound(String filter) throws Exception {
        restTemplatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templates.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].explanation").value(hasItem(DEFAULT_EXPLANATION)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restTemplatesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTemplatesShouldNotBeFound(String filter) throws Exception {
        restTemplatesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTemplatesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTemplates() throws Exception {
        // Get the templates
        restTemplatesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();

        // Update the templates
        Templates updatedTemplates = templatesRepository.findById(templates.getId()).get();
        // Disconnect from session so that the updates on updatedTemplates are not directly saved in db
        em.detach(updatedTemplates);
        updatedTemplates
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .explanation(UPDATED_EXPLANATION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE);
        TemplatesDTO templatesDTO = templatesMapper.toDto(updatedTemplates);

        restTemplatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templatesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templatesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);
        Templates testTemplates = templatesList.get(templatesList.size() - 1);
        assertThat(testTemplates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTemplates.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTemplates.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testTemplates.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testTemplates.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTemplates() throws Exception {
        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();
        templates.setId(count.incrementAndGet());

        // Create the Templates
        TemplatesDTO templatesDTO = templatesMapper.toDto(templates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, templatesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTemplates() throws Exception {
        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();
        templates.setId(count.incrementAndGet());

        // Create the Templates
        TemplatesDTO templatesDTO = templatesMapper.toDto(templates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplatesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(templatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTemplates() throws Exception {
        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();
        templates.setId(count.incrementAndGet());

        // Create the Templates
        TemplatesDTO templatesDTO = templatesMapper.toDto(templates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplatesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(templatesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTemplatesWithPatch() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();

        // Update the templates using partial update
        Templates partialUpdatedTemplates = new Templates();
        partialUpdatedTemplates.setId(templates.getId());

        partialUpdatedTemplates.explanation(UPDATED_EXPLANATION).isActive(UPDATED_IS_ACTIVE);

        restTemplatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplates.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplates))
            )
            .andExpect(status().isOk());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);
        Templates testTemplates = templatesList.get(templatesList.size() - 1);
        assertThat(testTemplates.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTemplates.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTemplates.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testTemplates.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testTemplates.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTemplatesWithPatch() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();

        // Update the templates using partial update
        Templates partialUpdatedTemplates = new Templates();
        partialUpdatedTemplates.setId(templates.getId());

        partialUpdatedTemplates
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .explanation(UPDATED_EXPLANATION)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE);

        restTemplatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTemplates.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTemplates))
            )
            .andExpect(status().isOk());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);
        Templates testTemplates = templatesList.get(templatesList.size() - 1);
        assertThat(testTemplates.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTemplates.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTemplates.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testTemplates.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testTemplates.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTemplates() throws Exception {
        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();
        templates.setId(count.incrementAndGet());

        // Create the Templates
        TemplatesDTO templatesDTO = templatesMapper.toDto(templates);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, templatesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTemplates() throws Exception {
        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();
        templates.setId(count.incrementAndGet());

        // Create the Templates
        TemplatesDTO templatesDTO = templatesMapper.toDto(templates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplatesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(templatesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTemplates() throws Exception {
        int databaseSizeBeforeUpdate = templatesRepository.findAll().size();
        templates.setId(count.incrementAndGet());

        // Create the Templates
        TemplatesDTO templatesDTO = templatesMapper.toDto(templates);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTemplatesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(templatesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Templates in the database
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTemplates() throws Exception {
        // Initialize the database
        templatesRepository.saveAndFlush(templates);

        int databaseSizeBeforeDelete = templatesRepository.findAll().size();

        // Delete the templates
        restTemplatesMockMvc
            .perform(delete(ENTITY_API_URL_ID, templates.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Templates> templatesList = templatesRepository.findAll();
        assertThat(templatesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
