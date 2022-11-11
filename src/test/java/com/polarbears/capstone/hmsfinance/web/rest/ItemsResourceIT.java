package com.polarbears.capstone.hmsfinance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmsfinance.IntegrationTest;
import com.polarbears.capstone.hmsfinance.domain.Items;
import com.polarbears.capstone.hmsfinance.domain.Templates;
import com.polarbears.capstone.hmsfinance.domain.enumeration.ITEMTYPES;
import com.polarbears.capstone.hmsfinance.repository.ItemsRepository;
import com.polarbears.capstone.hmsfinance.service.ItemsService;
import com.polarbears.capstone.hmsfinance.service.criteria.ItemsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.ItemsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.ItemsMapper;
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
 * Integration tests for the {@link ItemsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ItemsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ITEM_ID = 1L;
    private static final Long UPDATED_ITEM_ID = 2L;
    private static final Long SMALLER_ITEM_ID = 1L - 1L;

    private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

    private static final ITEMTYPES DEFAULT_TYPE = ITEMTYPES.PRODUCT;
    private static final ITEMTYPES UPDATED_TYPE = ITEMTYPES.SERVICE;

    private static final String DEFAULT_EXPLAIN = "AAAAAAAAAA";
    private static final String UPDATED_EXPLAIN = "BBBBBBBBBB";

    private static final Double DEFAULT_COST = 1D;
    private static final Double UPDATED_COST = 2D;
    private static final Double SMALLER_COST = 1D - 1D;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ItemsRepository itemsRepository;

    @Mock
    private ItemsRepository itemsRepositoryMock;

    @Autowired
    private ItemsMapper itemsMapper;

    @Mock
    private ItemsService itemsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemsMockMvc;

    private Items items;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Items createEntity(EntityManager em) {
        Items items = new Items()
            .name(DEFAULT_NAME)
            .itemId(DEFAULT_ITEM_ID)
            .itemCode(DEFAULT_ITEM_CODE)
            .type(DEFAULT_TYPE)
            .explain(DEFAULT_EXPLAIN)
            .cost(DEFAULT_COST)
            .price(DEFAULT_PRICE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdDate(DEFAULT_CREATED_DATE);
        return items;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Items createUpdatedEntity(EntityManager em) {
        Items items = new Items()
            .name(UPDATED_NAME)
            .itemId(UPDATED_ITEM_ID)
            .itemCode(UPDATED_ITEM_CODE)
            .type(UPDATED_TYPE)
            .explain(UPDATED_EXPLAIN)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE);
        return items;
    }

    @BeforeEach
    public void initTest() {
        items = createEntity(em);
    }

    @Test
    @Transactional
    void createItems() throws Exception {
        int databaseSizeBeforeCreate = itemsRepository.findAll().size();
        // Create the Items
        ItemsDTO itemsDTO = itemsMapper.toDto(items);
        restItemsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemsDTO)))
            .andExpect(status().isCreated());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeCreate + 1);
        Items testItems = itemsList.get(itemsList.size() - 1);
        assertThat(testItems.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testItems.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testItems.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testItems.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testItems.getExplain()).isEqualTo(DEFAULT_EXPLAIN);
        assertThat(testItems.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testItems.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testItems.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testItems.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createItemsWithExistingId() throws Exception {
        // Create the Items with an existing ID
        items.setId(1L);
        ItemsDTO itemsDTO = itemsMapper.toDto(items);

        int databaseSizeBeforeCreate = itemsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllItems() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList
        restItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(items.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].explain").value(hasItem(DEFAULT_EXPLAIN)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllItemsWithEagerRelationshipsIsEnabled() throws Exception {
        when(itemsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restItemsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(itemsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllItemsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(itemsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restItemsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(itemsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getItems() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get the items
        restItemsMockMvc
            .perform(get(ENTITY_API_URL_ID, items.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(items.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.explain").value(DEFAULT_EXPLAIN))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getItemsByIdFiltering() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        Long id = items.getId();

        defaultItemsShouldBeFound("id.equals=" + id);
        defaultItemsShouldNotBeFound("id.notEquals=" + id);

        defaultItemsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemsShouldNotBeFound("id.greaterThan=" + id);

        defaultItemsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllItemsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where name equals to DEFAULT_NAME
        defaultItemsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the itemsList where name equals to UPDATED_NAME
        defaultItemsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllItemsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultItemsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the itemsList where name equals to UPDATED_NAME
        defaultItemsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllItemsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where name is not null
        defaultItemsShouldBeFound("name.specified=true");

        // Get all the itemsList where name is null
        defaultItemsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllItemsByNameContainsSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where name contains DEFAULT_NAME
        defaultItemsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the itemsList where name contains UPDATED_NAME
        defaultItemsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllItemsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where name does not contain DEFAULT_NAME
        defaultItemsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the itemsList where name does not contain UPDATED_NAME
        defaultItemsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllItemsByItemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where itemId equals to DEFAULT_ITEM_ID
        defaultItemsShouldBeFound("itemId.equals=" + DEFAULT_ITEM_ID);

        // Get all the itemsList where itemId equals to UPDATED_ITEM_ID
        defaultItemsShouldNotBeFound("itemId.equals=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllItemsByItemIdIsInShouldWork() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where itemId in DEFAULT_ITEM_ID or UPDATED_ITEM_ID
        defaultItemsShouldBeFound("itemId.in=" + DEFAULT_ITEM_ID + "," + UPDATED_ITEM_ID);

        // Get all the itemsList where itemId equals to UPDATED_ITEM_ID
        defaultItemsShouldNotBeFound("itemId.in=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllItemsByItemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where itemId is not null
        defaultItemsShouldBeFound("itemId.specified=true");

        // Get all the itemsList where itemId is null
        defaultItemsShouldNotBeFound("itemId.specified=false");
    }

    @Test
    @Transactional
    void getAllItemsByItemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where itemId is greater than or equal to DEFAULT_ITEM_ID
        defaultItemsShouldBeFound("itemId.greaterThanOrEqual=" + DEFAULT_ITEM_ID);

        // Get all the itemsList where itemId is greater than or equal to UPDATED_ITEM_ID
        defaultItemsShouldNotBeFound("itemId.greaterThanOrEqual=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllItemsByItemIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where itemId is less than or equal to DEFAULT_ITEM_ID
        defaultItemsShouldBeFound("itemId.lessThanOrEqual=" + DEFAULT_ITEM_ID);

        // Get all the itemsList where itemId is less than or equal to SMALLER_ITEM_ID
        defaultItemsShouldNotBeFound("itemId.lessThanOrEqual=" + SMALLER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllItemsByItemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where itemId is less than DEFAULT_ITEM_ID
        defaultItemsShouldNotBeFound("itemId.lessThan=" + DEFAULT_ITEM_ID);

        // Get all the itemsList where itemId is less than UPDATED_ITEM_ID
        defaultItemsShouldBeFound("itemId.lessThan=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllItemsByItemIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where itemId is greater than DEFAULT_ITEM_ID
        defaultItemsShouldNotBeFound("itemId.greaterThan=" + DEFAULT_ITEM_ID);

        // Get all the itemsList where itemId is greater than SMALLER_ITEM_ID
        defaultItemsShouldBeFound("itemId.greaterThan=" + SMALLER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllItemsByItemCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where itemCode equals to DEFAULT_ITEM_CODE
        defaultItemsShouldBeFound("itemCode.equals=" + DEFAULT_ITEM_CODE);

        // Get all the itemsList where itemCode equals to UPDATED_ITEM_CODE
        defaultItemsShouldNotBeFound("itemCode.equals=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllItemsByItemCodeIsInShouldWork() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where itemCode in DEFAULT_ITEM_CODE or UPDATED_ITEM_CODE
        defaultItemsShouldBeFound("itemCode.in=" + DEFAULT_ITEM_CODE + "," + UPDATED_ITEM_CODE);

        // Get all the itemsList where itemCode equals to UPDATED_ITEM_CODE
        defaultItemsShouldNotBeFound("itemCode.in=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllItemsByItemCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where itemCode is not null
        defaultItemsShouldBeFound("itemCode.specified=true");

        // Get all the itemsList where itemCode is null
        defaultItemsShouldNotBeFound("itemCode.specified=false");
    }

    @Test
    @Transactional
    void getAllItemsByItemCodeContainsSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where itemCode contains DEFAULT_ITEM_CODE
        defaultItemsShouldBeFound("itemCode.contains=" + DEFAULT_ITEM_CODE);

        // Get all the itemsList where itemCode contains UPDATED_ITEM_CODE
        defaultItemsShouldNotBeFound("itemCode.contains=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllItemsByItemCodeNotContainsSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where itemCode does not contain DEFAULT_ITEM_CODE
        defaultItemsShouldNotBeFound("itemCode.doesNotContain=" + DEFAULT_ITEM_CODE);

        // Get all the itemsList where itemCode does not contain UPDATED_ITEM_CODE
        defaultItemsShouldBeFound("itemCode.doesNotContain=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllItemsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where type equals to DEFAULT_TYPE
        defaultItemsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the itemsList where type equals to UPDATED_TYPE
        defaultItemsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllItemsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultItemsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the itemsList where type equals to UPDATED_TYPE
        defaultItemsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllItemsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where type is not null
        defaultItemsShouldBeFound("type.specified=true");

        // Get all the itemsList where type is null
        defaultItemsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllItemsByExplainIsEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where explain equals to DEFAULT_EXPLAIN
        defaultItemsShouldBeFound("explain.equals=" + DEFAULT_EXPLAIN);

        // Get all the itemsList where explain equals to UPDATED_EXPLAIN
        defaultItemsShouldNotBeFound("explain.equals=" + UPDATED_EXPLAIN);
    }

    @Test
    @Transactional
    void getAllItemsByExplainIsInShouldWork() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where explain in DEFAULT_EXPLAIN or UPDATED_EXPLAIN
        defaultItemsShouldBeFound("explain.in=" + DEFAULT_EXPLAIN + "," + UPDATED_EXPLAIN);

        // Get all the itemsList where explain equals to UPDATED_EXPLAIN
        defaultItemsShouldNotBeFound("explain.in=" + UPDATED_EXPLAIN);
    }

    @Test
    @Transactional
    void getAllItemsByExplainIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where explain is not null
        defaultItemsShouldBeFound("explain.specified=true");

        // Get all the itemsList where explain is null
        defaultItemsShouldNotBeFound("explain.specified=false");
    }

    @Test
    @Transactional
    void getAllItemsByExplainContainsSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where explain contains DEFAULT_EXPLAIN
        defaultItemsShouldBeFound("explain.contains=" + DEFAULT_EXPLAIN);

        // Get all the itemsList where explain contains UPDATED_EXPLAIN
        defaultItemsShouldNotBeFound("explain.contains=" + UPDATED_EXPLAIN);
    }

    @Test
    @Transactional
    void getAllItemsByExplainNotContainsSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where explain does not contain DEFAULT_EXPLAIN
        defaultItemsShouldNotBeFound("explain.doesNotContain=" + DEFAULT_EXPLAIN);

        // Get all the itemsList where explain does not contain UPDATED_EXPLAIN
        defaultItemsShouldBeFound("explain.doesNotContain=" + UPDATED_EXPLAIN);
    }

    @Test
    @Transactional
    void getAllItemsByCostIsEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where cost equals to DEFAULT_COST
        defaultItemsShouldBeFound("cost.equals=" + DEFAULT_COST);

        // Get all the itemsList where cost equals to UPDATED_COST
        defaultItemsShouldNotBeFound("cost.equals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllItemsByCostIsInShouldWork() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where cost in DEFAULT_COST or UPDATED_COST
        defaultItemsShouldBeFound("cost.in=" + DEFAULT_COST + "," + UPDATED_COST);

        // Get all the itemsList where cost equals to UPDATED_COST
        defaultItemsShouldNotBeFound("cost.in=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllItemsByCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where cost is not null
        defaultItemsShouldBeFound("cost.specified=true");

        // Get all the itemsList where cost is null
        defaultItemsShouldNotBeFound("cost.specified=false");
    }

    @Test
    @Transactional
    void getAllItemsByCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where cost is greater than or equal to DEFAULT_COST
        defaultItemsShouldBeFound("cost.greaterThanOrEqual=" + DEFAULT_COST);

        // Get all the itemsList where cost is greater than or equal to UPDATED_COST
        defaultItemsShouldNotBeFound("cost.greaterThanOrEqual=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllItemsByCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where cost is less than or equal to DEFAULT_COST
        defaultItemsShouldBeFound("cost.lessThanOrEqual=" + DEFAULT_COST);

        // Get all the itemsList where cost is less than or equal to SMALLER_COST
        defaultItemsShouldNotBeFound("cost.lessThanOrEqual=" + SMALLER_COST);
    }

    @Test
    @Transactional
    void getAllItemsByCostIsLessThanSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where cost is less than DEFAULT_COST
        defaultItemsShouldNotBeFound("cost.lessThan=" + DEFAULT_COST);

        // Get all the itemsList where cost is less than UPDATED_COST
        defaultItemsShouldBeFound("cost.lessThan=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllItemsByCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where cost is greater than DEFAULT_COST
        defaultItemsShouldNotBeFound("cost.greaterThan=" + DEFAULT_COST);

        // Get all the itemsList where cost is greater than SMALLER_COST
        defaultItemsShouldBeFound("cost.greaterThan=" + SMALLER_COST);
    }

    @Test
    @Transactional
    void getAllItemsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where price equals to DEFAULT_PRICE
        defaultItemsShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the itemsList where price equals to UPDATED_PRICE
        defaultItemsShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllItemsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultItemsShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the itemsList where price equals to UPDATED_PRICE
        defaultItemsShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllItemsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where price is not null
        defaultItemsShouldBeFound("price.specified=true");

        // Get all the itemsList where price is null
        defaultItemsShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllItemsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where price is greater than or equal to DEFAULT_PRICE
        defaultItemsShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the itemsList where price is greater than or equal to UPDATED_PRICE
        defaultItemsShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllItemsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where price is less than or equal to DEFAULT_PRICE
        defaultItemsShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the itemsList where price is less than or equal to SMALLER_PRICE
        defaultItemsShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllItemsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where price is less than DEFAULT_PRICE
        defaultItemsShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the itemsList where price is less than UPDATED_PRICE
        defaultItemsShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllItemsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where price is greater than DEFAULT_PRICE
        defaultItemsShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the itemsList where price is greater than SMALLER_PRICE
        defaultItemsShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllItemsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where isActive equals to DEFAULT_IS_ACTIVE
        defaultItemsShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the itemsList where isActive equals to UPDATED_IS_ACTIVE
        defaultItemsShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllItemsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultItemsShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the itemsList where isActive equals to UPDATED_IS_ACTIVE
        defaultItemsShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllItemsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where isActive is not null
        defaultItemsShouldBeFound("isActive.specified=true");

        // Get all the itemsList where isActive is null
        defaultItemsShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllItemsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultItemsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the itemsList where createdDate equals to UPDATED_CREATED_DATE
        defaultItemsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllItemsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultItemsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the itemsList where createdDate equals to UPDATED_CREATED_DATE
        defaultItemsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllItemsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where createdDate is not null
        defaultItemsShouldBeFound("createdDate.specified=true");

        // Get all the itemsList where createdDate is null
        defaultItemsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllItemsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultItemsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the itemsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultItemsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllItemsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultItemsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the itemsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultItemsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllItemsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultItemsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the itemsList where createdDate is less than UPDATED_CREATED_DATE
        defaultItemsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllItemsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        // Get all the itemsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultItemsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the itemsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultItemsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllItemsByTemplatesIsEqualToSomething() throws Exception {
        Templates templates;
        if (TestUtil.findAll(em, Templates.class).isEmpty()) {
            itemsRepository.saveAndFlush(items);
            templates = TemplatesResourceIT.createEntity(em);
        } else {
            templates = TestUtil.findAll(em, Templates.class).get(0);
        }
        em.persist(templates);
        em.flush();
        items.setTemplates(templates);
        itemsRepository.saveAndFlush(items);
        Long templatesId = templates.getId();

        // Get all the itemsList where templates equals to templatesId
        defaultItemsShouldBeFound("templatesId.equals=" + templatesId);

        // Get all the itemsList where templates equals to (templatesId + 1)
        defaultItemsShouldNotBeFound("templatesId.equals=" + (templatesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemsShouldBeFound(String filter) throws Exception {
        restItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(items.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].explain").value(hasItem(DEFAULT_EXPLAIN)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restItemsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemsShouldNotBeFound(String filter) throws Exception {
        restItemsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingItems() throws Exception {
        // Get the items
        restItemsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingItems() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        int databaseSizeBeforeUpdate = itemsRepository.findAll().size();

        // Update the items
        Items updatedItems = itemsRepository.findById(items.getId()).get();
        // Disconnect from session so that the updates on updatedItems are not directly saved in db
        em.detach(updatedItems);
        updatedItems
            .name(UPDATED_NAME)
            .itemId(UPDATED_ITEM_ID)
            .itemCode(UPDATED_ITEM_CODE)
            .type(UPDATED_TYPE)
            .explain(UPDATED_EXPLAIN)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE);
        ItemsDTO itemsDTO = itemsMapper.toDto(updatedItems);

        restItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeUpdate);
        Items testItems = itemsList.get(itemsList.size() - 1);
        assertThat(testItems.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItems.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testItems.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testItems.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testItems.getExplain()).isEqualTo(UPDATED_EXPLAIN);
        assertThat(testItems.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testItems.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testItems.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testItems.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingItems() throws Exception {
        int databaseSizeBeforeUpdate = itemsRepository.findAll().size();
        items.setId(count.incrementAndGet());

        // Create the Items
        ItemsDTO itemsDTO = itemsMapper.toDto(items);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, itemsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchItems() throws Exception {
        int databaseSizeBeforeUpdate = itemsRepository.findAll().size();
        items.setId(count.incrementAndGet());

        // Create the Items
        ItemsDTO itemsDTO = itemsMapper.toDto(items);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(itemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamItems() throws Exception {
        int databaseSizeBeforeUpdate = itemsRepository.findAll().size();
        items.setId(count.incrementAndGet());

        // Create the Items
        ItemsDTO itemsDTO = itemsMapper.toDto(items);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(itemsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateItemsWithPatch() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        int databaseSizeBeforeUpdate = itemsRepository.findAll().size();

        // Update the items using partial update
        Items partialUpdatedItems = new Items();
        partialUpdatedItems.setId(items.getId());

        partialUpdatedItems
            .name(UPDATED_NAME)
            .itemId(UPDATED_ITEM_ID)
            .type(UPDATED_TYPE)
            .explain(UPDATED_EXPLAIN)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE);

        restItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItems))
            )
            .andExpect(status().isOk());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeUpdate);
        Items testItems = itemsList.get(itemsList.size() - 1);
        assertThat(testItems.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItems.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testItems.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testItems.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testItems.getExplain()).isEqualTo(UPDATED_EXPLAIN);
        assertThat(testItems.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testItems.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testItems.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testItems.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateItemsWithPatch() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        int databaseSizeBeforeUpdate = itemsRepository.findAll().size();

        // Update the items using partial update
        Items partialUpdatedItems = new Items();
        partialUpdatedItems.setId(items.getId());

        partialUpdatedItems
            .name(UPDATED_NAME)
            .itemId(UPDATED_ITEM_ID)
            .itemCode(UPDATED_ITEM_CODE)
            .type(UPDATED_TYPE)
            .explain(UPDATED_EXPLAIN)
            .cost(UPDATED_COST)
            .price(UPDATED_PRICE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdDate(UPDATED_CREATED_DATE);

        restItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedItems.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedItems))
            )
            .andExpect(status().isOk());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeUpdate);
        Items testItems = itemsList.get(itemsList.size() - 1);
        assertThat(testItems.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testItems.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testItems.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testItems.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testItems.getExplain()).isEqualTo(UPDATED_EXPLAIN);
        assertThat(testItems.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testItems.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testItems.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testItems.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingItems() throws Exception {
        int databaseSizeBeforeUpdate = itemsRepository.findAll().size();
        items.setId(count.incrementAndGet());

        // Create the Items
        ItemsDTO itemsDTO = itemsMapper.toDto(items);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, itemsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchItems() throws Exception {
        int databaseSizeBeforeUpdate = itemsRepository.findAll().size();
        items.setId(count.incrementAndGet());

        // Create the Items
        ItemsDTO itemsDTO = itemsMapper.toDto(items);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(itemsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamItems() throws Exception {
        int databaseSizeBeforeUpdate = itemsRepository.findAll().size();
        items.setId(count.incrementAndGet());

        // Create the Items
        ItemsDTO itemsDTO = itemsMapper.toDto(items);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restItemsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(itemsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Items in the database
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteItems() throws Exception {
        // Initialize the database
        itemsRepository.saveAndFlush(items);

        int databaseSizeBeforeDelete = itemsRepository.findAll().size();

        // Delete the items
        restItemsMockMvc
            .perform(delete(ENTITY_API_URL_ID, items.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Items> itemsList = itemsRepository.findAll();
        assertThat(itemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
