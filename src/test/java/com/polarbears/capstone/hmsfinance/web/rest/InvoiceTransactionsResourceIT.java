package com.polarbears.capstone.hmsfinance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmsfinance.IntegrationTest;
import com.polarbears.capstone.hmsfinance.domain.InvoiceTransactions;
import com.polarbears.capstone.hmsfinance.domain.Invoices;
import com.polarbears.capstone.hmsfinance.domain.enumeration.INVOICETYPES;
import com.polarbears.capstone.hmsfinance.repository.InvoiceTransactionsRepository;
import com.polarbears.capstone.hmsfinance.service.InvoiceTransactionsService;
import com.polarbears.capstone.hmsfinance.service.criteria.InvoiceTransactionsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.InvoiceTransactionsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.InvoiceTransactionsMapper;
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
 * Integration tests for the {@link InvoiceTransactionsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InvoiceTransactionsResourceIT {

    private static final LocalDate DEFAULT_STATUS_CHANGED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STATUS_CHANGED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_STATUS_CHANGED_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TRANSACTION_DATE = LocalDate.ofEpochDay(-1L);

    private static final INVOICETYPES DEFAULT_TYPE = INVOICETYPES.PROPOSAL;
    private static final INVOICETYPES UPDATED_TYPE = INVOICETYPES.CANCELLED;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/invoice-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InvoiceTransactionsRepository invoiceTransactionsRepository;

    @Mock
    private InvoiceTransactionsRepository invoiceTransactionsRepositoryMock;

    @Autowired
    private InvoiceTransactionsMapper invoiceTransactionsMapper;

    @Mock
    private InvoiceTransactionsService invoiceTransactionsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceTransactionsMockMvc;

    private InvoiceTransactions invoiceTransactions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceTransactions createEntity(EntityManager em) {
        InvoiceTransactions invoiceTransactions = new InvoiceTransactions()
            .statusChangedDate(DEFAULT_STATUS_CHANGED_DATE)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .type(DEFAULT_TYPE)
            .createdDate(DEFAULT_CREATED_DATE);
        return invoiceTransactions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceTransactions createUpdatedEntity(EntityManager em) {
        InvoiceTransactions invoiceTransactions = new InvoiceTransactions()
            .statusChangedDate(UPDATED_STATUS_CHANGED_DATE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .type(UPDATED_TYPE)
            .createdDate(UPDATED_CREATED_DATE);
        return invoiceTransactions;
    }

    @BeforeEach
    public void initTest() {
        invoiceTransactions = createEntity(em);
    }

    @Test
    @Transactional
    void createInvoiceTransactions() throws Exception {
        int databaseSizeBeforeCreate = invoiceTransactionsRepository.findAll().size();
        // Create the InvoiceTransactions
        InvoiceTransactionsDTO invoiceTransactionsDTO = invoiceTransactionsMapper.toDto(invoiceTransactions);
        restInvoiceTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceTransactionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InvoiceTransactions in the database
        List<InvoiceTransactions> invoiceTransactionsList = invoiceTransactionsRepository.findAll();
        assertThat(invoiceTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceTransactions testInvoiceTransactions = invoiceTransactionsList.get(invoiceTransactionsList.size() - 1);
        assertThat(testInvoiceTransactions.getStatusChangedDate()).isEqualTo(DEFAULT_STATUS_CHANGED_DATE);
        assertThat(testInvoiceTransactions.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testInvoiceTransactions.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInvoiceTransactions.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createInvoiceTransactionsWithExistingId() throws Exception {
        // Create the InvoiceTransactions with an existing ID
        invoiceTransactions.setId(1L);
        InvoiceTransactionsDTO invoiceTransactionsDTO = invoiceTransactionsMapper.toDto(invoiceTransactions);

        int databaseSizeBeforeCreate = invoiceTransactionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceTransactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceTransactions in the database
        List<InvoiceTransactions> invoiceTransactionsList = invoiceTransactionsRepository.findAll();
        assertThat(invoiceTransactionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactions() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList
        restInvoiceTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusChangedDate").value(hasItem(DEFAULT_STATUS_CHANGED_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInvoiceTransactionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(invoiceTransactionsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInvoiceTransactionsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(invoiceTransactionsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInvoiceTransactionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(invoiceTransactionsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInvoiceTransactionsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(invoiceTransactionsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInvoiceTransactions() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get the invoiceTransactions
        restInvoiceTransactionsMockMvc
            .perform(get(ENTITY_API_URL_ID, invoiceTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceTransactions.getId().intValue()))
            .andExpect(jsonPath("$.statusChangedDate").value(DEFAULT_STATUS_CHANGED_DATE.toString()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getInvoiceTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        Long id = invoiceTransactions.getId();

        defaultInvoiceTransactionsShouldBeFound("id.equals=" + id);
        defaultInvoiceTransactionsShouldNotBeFound("id.notEquals=" + id);

        defaultInvoiceTransactionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvoiceTransactionsShouldNotBeFound("id.greaterThan=" + id);

        defaultInvoiceTransactionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvoiceTransactionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByStatusChangedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where statusChangedDate equals to DEFAULT_STATUS_CHANGED_DATE
        defaultInvoiceTransactionsShouldBeFound("statusChangedDate.equals=" + DEFAULT_STATUS_CHANGED_DATE);

        // Get all the invoiceTransactionsList where statusChangedDate equals to UPDATED_STATUS_CHANGED_DATE
        defaultInvoiceTransactionsShouldNotBeFound("statusChangedDate.equals=" + UPDATED_STATUS_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByStatusChangedDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where statusChangedDate in DEFAULT_STATUS_CHANGED_DATE or UPDATED_STATUS_CHANGED_DATE
        defaultInvoiceTransactionsShouldBeFound("statusChangedDate.in=" + DEFAULT_STATUS_CHANGED_DATE + "," + UPDATED_STATUS_CHANGED_DATE);

        // Get all the invoiceTransactionsList where statusChangedDate equals to UPDATED_STATUS_CHANGED_DATE
        defaultInvoiceTransactionsShouldNotBeFound("statusChangedDate.in=" + UPDATED_STATUS_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByStatusChangedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where statusChangedDate is not null
        defaultInvoiceTransactionsShouldBeFound("statusChangedDate.specified=true");

        // Get all the invoiceTransactionsList where statusChangedDate is null
        defaultInvoiceTransactionsShouldNotBeFound("statusChangedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByStatusChangedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where statusChangedDate is greater than or equal to DEFAULT_STATUS_CHANGED_DATE
        defaultInvoiceTransactionsShouldBeFound("statusChangedDate.greaterThanOrEqual=" + DEFAULT_STATUS_CHANGED_DATE);

        // Get all the invoiceTransactionsList where statusChangedDate is greater than or equal to UPDATED_STATUS_CHANGED_DATE
        defaultInvoiceTransactionsShouldNotBeFound("statusChangedDate.greaterThanOrEqual=" + UPDATED_STATUS_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByStatusChangedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where statusChangedDate is less than or equal to DEFAULT_STATUS_CHANGED_DATE
        defaultInvoiceTransactionsShouldBeFound("statusChangedDate.lessThanOrEqual=" + DEFAULT_STATUS_CHANGED_DATE);

        // Get all the invoiceTransactionsList where statusChangedDate is less than or equal to SMALLER_STATUS_CHANGED_DATE
        defaultInvoiceTransactionsShouldNotBeFound("statusChangedDate.lessThanOrEqual=" + SMALLER_STATUS_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByStatusChangedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where statusChangedDate is less than DEFAULT_STATUS_CHANGED_DATE
        defaultInvoiceTransactionsShouldNotBeFound("statusChangedDate.lessThan=" + DEFAULT_STATUS_CHANGED_DATE);

        // Get all the invoiceTransactionsList where statusChangedDate is less than UPDATED_STATUS_CHANGED_DATE
        defaultInvoiceTransactionsShouldBeFound("statusChangedDate.lessThan=" + UPDATED_STATUS_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByStatusChangedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where statusChangedDate is greater than DEFAULT_STATUS_CHANGED_DATE
        defaultInvoiceTransactionsShouldNotBeFound("statusChangedDate.greaterThan=" + DEFAULT_STATUS_CHANGED_DATE);

        // Get all the invoiceTransactionsList where statusChangedDate is greater than SMALLER_STATUS_CHANGED_DATE
        defaultInvoiceTransactionsShouldBeFound("statusChangedDate.greaterThan=" + SMALLER_STATUS_CHANGED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where transactionDate equals to DEFAULT_TRANSACTION_DATE
        defaultInvoiceTransactionsShouldBeFound("transactionDate.equals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the invoiceTransactionsList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultInvoiceTransactionsShouldNotBeFound("transactionDate.equals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where transactionDate in DEFAULT_TRANSACTION_DATE or UPDATED_TRANSACTION_DATE
        defaultInvoiceTransactionsShouldBeFound("transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE);

        // Get all the invoiceTransactionsList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultInvoiceTransactionsShouldNotBeFound("transactionDate.in=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where transactionDate is not null
        defaultInvoiceTransactionsShouldBeFound("transactionDate.specified=true");

        // Get all the invoiceTransactionsList where transactionDate is null
        defaultInvoiceTransactionsShouldNotBeFound("transactionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByTransactionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where transactionDate is greater than or equal to DEFAULT_TRANSACTION_DATE
        defaultInvoiceTransactionsShouldBeFound("transactionDate.greaterThanOrEqual=" + DEFAULT_TRANSACTION_DATE);

        // Get all the invoiceTransactionsList where transactionDate is greater than or equal to UPDATED_TRANSACTION_DATE
        defaultInvoiceTransactionsShouldNotBeFound("transactionDate.greaterThanOrEqual=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByTransactionDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where transactionDate is less than or equal to DEFAULT_TRANSACTION_DATE
        defaultInvoiceTransactionsShouldBeFound("transactionDate.lessThanOrEqual=" + DEFAULT_TRANSACTION_DATE);

        // Get all the invoiceTransactionsList where transactionDate is less than or equal to SMALLER_TRANSACTION_DATE
        defaultInvoiceTransactionsShouldNotBeFound("transactionDate.lessThanOrEqual=" + SMALLER_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByTransactionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where transactionDate is less than DEFAULT_TRANSACTION_DATE
        defaultInvoiceTransactionsShouldNotBeFound("transactionDate.lessThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the invoiceTransactionsList where transactionDate is less than UPDATED_TRANSACTION_DATE
        defaultInvoiceTransactionsShouldBeFound("transactionDate.lessThan=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByTransactionDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where transactionDate is greater than DEFAULT_TRANSACTION_DATE
        defaultInvoiceTransactionsShouldNotBeFound("transactionDate.greaterThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the invoiceTransactionsList where transactionDate is greater than SMALLER_TRANSACTION_DATE
        defaultInvoiceTransactionsShouldBeFound("transactionDate.greaterThan=" + SMALLER_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where type equals to DEFAULT_TYPE
        defaultInvoiceTransactionsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the invoiceTransactionsList where type equals to UPDATED_TYPE
        defaultInvoiceTransactionsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultInvoiceTransactionsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the invoiceTransactionsList where type equals to UPDATED_TYPE
        defaultInvoiceTransactionsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where type is not null
        defaultInvoiceTransactionsShouldBeFound("type.specified=true");

        // Get all the invoiceTransactionsList where type is null
        defaultInvoiceTransactionsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultInvoiceTransactionsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceTransactionsList where createdDate equals to UPDATED_CREATED_DATE
        defaultInvoiceTransactionsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultInvoiceTransactionsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the invoiceTransactionsList where createdDate equals to UPDATED_CREATED_DATE
        defaultInvoiceTransactionsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where createdDate is not null
        defaultInvoiceTransactionsShouldBeFound("createdDate.specified=true");

        // Get all the invoiceTransactionsList where createdDate is null
        defaultInvoiceTransactionsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultInvoiceTransactionsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceTransactionsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultInvoiceTransactionsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultInvoiceTransactionsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceTransactionsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultInvoiceTransactionsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultInvoiceTransactionsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceTransactionsList where createdDate is less than UPDATED_CREATED_DATE
        defaultInvoiceTransactionsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        // Get all the invoiceTransactionsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultInvoiceTransactionsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceTransactionsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultInvoiceTransactionsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceTransactionsByInvoicesIsEqualToSomething() throws Exception {
        Invoices invoices;
        if (TestUtil.findAll(em, Invoices.class).isEmpty()) {
            invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);
            invoices = InvoicesResourceIT.createEntity(em);
        } else {
            invoices = TestUtil.findAll(em, Invoices.class).get(0);
        }
        em.persist(invoices);
        em.flush();
        invoiceTransactions.setInvoices(invoices);
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);
        Long invoicesId = invoices.getId();

        // Get all the invoiceTransactionsList where invoices equals to invoicesId
        defaultInvoiceTransactionsShouldBeFound("invoicesId.equals=" + invoicesId);

        // Get all the invoiceTransactionsList where invoices equals to (invoicesId + 1)
        defaultInvoiceTransactionsShouldNotBeFound("invoicesId.equals=" + (invoicesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoiceTransactionsShouldBeFound(String filter) throws Exception {
        restInvoiceTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusChangedDate").value(hasItem(DEFAULT_STATUS_CHANGED_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restInvoiceTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoiceTransactionsShouldNotBeFound(String filter) throws Exception {
        restInvoiceTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoiceTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInvoiceTransactions() throws Exception {
        // Get the invoiceTransactions
        restInvoiceTransactionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInvoiceTransactions() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        int databaseSizeBeforeUpdate = invoiceTransactionsRepository.findAll().size();

        // Update the invoiceTransactions
        InvoiceTransactions updatedInvoiceTransactions = invoiceTransactionsRepository.findById(invoiceTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceTransactions are not directly saved in db
        em.detach(updatedInvoiceTransactions);
        updatedInvoiceTransactions
            .statusChangedDate(UPDATED_STATUS_CHANGED_DATE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .type(UPDATED_TYPE)
            .createdDate(UPDATED_CREATED_DATE);
        InvoiceTransactionsDTO invoiceTransactionsDTO = invoiceTransactionsMapper.toDto(updatedInvoiceTransactions);

        restInvoiceTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoiceTransactionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceTransactionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceTransactions in the database
        List<InvoiceTransactions> invoiceTransactionsList = invoiceTransactionsRepository.findAll();
        assertThat(invoiceTransactionsList).hasSize(databaseSizeBeforeUpdate);
        InvoiceTransactions testInvoiceTransactions = invoiceTransactionsList.get(invoiceTransactionsList.size() - 1);
        assertThat(testInvoiceTransactions.getStatusChangedDate()).isEqualTo(UPDATED_STATUS_CHANGED_DATE);
        assertThat(testInvoiceTransactions.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testInvoiceTransactions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInvoiceTransactions.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingInvoiceTransactions() throws Exception {
        int databaseSizeBeforeUpdate = invoiceTransactionsRepository.findAll().size();
        invoiceTransactions.setId(count.incrementAndGet());

        // Create the InvoiceTransactions
        InvoiceTransactionsDTO invoiceTransactionsDTO = invoiceTransactionsMapper.toDto(invoiceTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoiceTransactionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceTransactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceTransactions in the database
        List<InvoiceTransactions> invoiceTransactionsList = invoiceTransactionsRepository.findAll();
        assertThat(invoiceTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInvoiceTransactions() throws Exception {
        int databaseSizeBeforeUpdate = invoiceTransactionsRepository.findAll().size();
        invoiceTransactions.setId(count.incrementAndGet());

        // Create the InvoiceTransactions
        InvoiceTransactionsDTO invoiceTransactionsDTO = invoiceTransactionsMapper.toDto(invoiceTransactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceTransactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceTransactions in the database
        List<InvoiceTransactions> invoiceTransactionsList = invoiceTransactionsRepository.findAll();
        assertThat(invoiceTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInvoiceTransactions() throws Exception {
        int databaseSizeBeforeUpdate = invoiceTransactionsRepository.findAll().size();
        invoiceTransactions.setId(count.incrementAndGet());

        // Create the InvoiceTransactions
        InvoiceTransactionsDTO invoiceTransactionsDTO = invoiceTransactionsMapper.toDto(invoiceTransactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceTransactionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceTransactions in the database
        List<InvoiceTransactions> invoiceTransactionsList = invoiceTransactionsRepository.findAll();
        assertThat(invoiceTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInvoiceTransactionsWithPatch() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        int databaseSizeBeforeUpdate = invoiceTransactionsRepository.findAll().size();

        // Update the invoiceTransactions using partial update
        InvoiceTransactions partialUpdatedInvoiceTransactions = new InvoiceTransactions();
        partialUpdatedInvoiceTransactions.setId(invoiceTransactions.getId());

        partialUpdatedInvoiceTransactions.transactionDate(UPDATED_TRANSACTION_DATE).createdDate(UPDATED_CREATED_DATE);

        restInvoiceTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoiceTransactions))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceTransactions in the database
        List<InvoiceTransactions> invoiceTransactionsList = invoiceTransactionsRepository.findAll();
        assertThat(invoiceTransactionsList).hasSize(databaseSizeBeforeUpdate);
        InvoiceTransactions testInvoiceTransactions = invoiceTransactionsList.get(invoiceTransactionsList.size() - 1);
        assertThat(testInvoiceTransactions.getStatusChangedDate()).isEqualTo(DEFAULT_STATUS_CHANGED_DATE);
        assertThat(testInvoiceTransactions.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testInvoiceTransactions.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInvoiceTransactions.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateInvoiceTransactionsWithPatch() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        int databaseSizeBeforeUpdate = invoiceTransactionsRepository.findAll().size();

        // Update the invoiceTransactions using partial update
        InvoiceTransactions partialUpdatedInvoiceTransactions = new InvoiceTransactions();
        partialUpdatedInvoiceTransactions.setId(invoiceTransactions.getId());

        partialUpdatedInvoiceTransactions
            .statusChangedDate(UPDATED_STATUS_CHANGED_DATE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .type(UPDATED_TYPE)
            .createdDate(UPDATED_CREATED_DATE);

        restInvoiceTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoiceTransactions))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceTransactions in the database
        List<InvoiceTransactions> invoiceTransactionsList = invoiceTransactionsRepository.findAll();
        assertThat(invoiceTransactionsList).hasSize(databaseSizeBeforeUpdate);
        InvoiceTransactions testInvoiceTransactions = invoiceTransactionsList.get(invoiceTransactionsList.size() - 1);
        assertThat(testInvoiceTransactions.getStatusChangedDate()).isEqualTo(UPDATED_STATUS_CHANGED_DATE);
        assertThat(testInvoiceTransactions.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testInvoiceTransactions.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInvoiceTransactions.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingInvoiceTransactions() throws Exception {
        int databaseSizeBeforeUpdate = invoiceTransactionsRepository.findAll().size();
        invoiceTransactions.setId(count.incrementAndGet());

        // Create the InvoiceTransactions
        InvoiceTransactionsDTO invoiceTransactionsDTO = invoiceTransactionsMapper.toDto(invoiceTransactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, invoiceTransactionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceTransactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceTransactions in the database
        List<InvoiceTransactions> invoiceTransactionsList = invoiceTransactionsRepository.findAll();
        assertThat(invoiceTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInvoiceTransactions() throws Exception {
        int databaseSizeBeforeUpdate = invoiceTransactionsRepository.findAll().size();
        invoiceTransactions.setId(count.incrementAndGet());

        // Create the InvoiceTransactions
        InvoiceTransactionsDTO invoiceTransactionsDTO = invoiceTransactionsMapper.toDto(invoiceTransactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceTransactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceTransactions in the database
        List<InvoiceTransactions> invoiceTransactionsList = invoiceTransactionsRepository.findAll();
        assertThat(invoiceTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInvoiceTransactions() throws Exception {
        int databaseSizeBeforeUpdate = invoiceTransactionsRepository.findAll().size();
        invoiceTransactions.setId(count.incrementAndGet());

        // Create the InvoiceTransactions
        InvoiceTransactionsDTO invoiceTransactionsDTO = invoiceTransactionsMapper.toDto(invoiceTransactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceTransactionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceTransactions in the database
        List<InvoiceTransactions> invoiceTransactionsList = invoiceTransactionsRepository.findAll();
        assertThat(invoiceTransactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInvoiceTransactions() throws Exception {
        // Initialize the database
        invoiceTransactionsRepository.saveAndFlush(invoiceTransactions);

        int databaseSizeBeforeDelete = invoiceTransactionsRepository.findAll().size();

        // Delete the invoiceTransactions
        restInvoiceTransactionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, invoiceTransactions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceTransactions> invoiceTransactionsList = invoiceTransactionsRepository.findAll();
        assertThat(invoiceTransactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
