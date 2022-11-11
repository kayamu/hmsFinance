package com.polarbears.capstone.hmsfinance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmsfinance.IntegrationTest;
import com.polarbears.capstone.hmsfinance.domain.InvoiceDetails;
import com.polarbears.capstone.hmsfinance.domain.InvoiceTransactions;
import com.polarbears.capstone.hmsfinance.domain.Invoices;
import com.polarbears.capstone.hmsfinance.domain.Payments;
import com.polarbears.capstone.hmsfinance.domain.enumeration.INVOICETYPES;
import com.polarbears.capstone.hmsfinance.repository.InvoicesRepository;
import com.polarbears.capstone.hmsfinance.service.InvoicesService;
import com.polarbears.capstone.hmsfinance.service.criteria.InvoicesCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.InvoicesDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.InvoicesMapper;
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
 * Integration tests for the {@link InvoicesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InvoicesResourceIT {

    private static final String DEFAULT_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTACT_ID = 1L;
    private static final Long UPDATED_CONTACT_ID = 2L;
    private static final Long SMALLER_CONTACT_ID = 1L - 1L;

    private static final Long DEFAULT_CONTACT_ADDRESS_ID = 1L;
    private static final Long UPDATED_CONTACT_ADDRESS_ID = 2L;
    private static final Long SMALLER_CONTACT_ADDRESS_ID = 1L - 1L;

    private static final Long DEFAULT_CONTACT_BILLING_ADR_ID = 1L;
    private static final Long UPDATED_CONTACT_BILLING_ADR_ID = 2L;
    private static final Long SMALLER_CONTACT_BILLING_ADR_ID = 1L - 1L;

    private static final Long DEFAULT_CART_ID = 1L;
    private static final Long UPDATED_CART_ID = 2L;
    private static final Long SMALLER_CART_ID = 1L - 1L;

    private static final INVOICETYPES DEFAULT_TYPE = INVOICETYPES.PROPOSAL;
    private static final INVOICETYPES UPDATED_TYPE = INVOICETYPES.CANCELLED;

    private static final LocalDate DEFAULT_REQUEST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REQUEST_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REQUEST_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INVOICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_INVOICE_DATE = LocalDate.ofEpochDay(-1L);

    private static final Long DEFAULT_LAST_TRANACTION_ID = 1L;
    private static final Long UPDATED_LAST_TRANACTION_ID = 2L;
    private static final Long SMALLER_LAST_TRANACTION_ID = 1L - 1L;

    private static final Double DEFAULT_TOTAL_COST = 1D;
    private static final Double UPDATED_TOTAL_COST = 2D;
    private static final Double SMALLER_TOTAL_COST = 1D - 1D;

    private static final Double DEFAULT_TOTAL_PROFIT = 1D;
    private static final Double UPDATED_TOTAL_PROFIT = 2D;
    private static final Double SMALLER_TOTAL_PROFIT = 1D - 1D;

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;
    private static final Double SMALLER_TOTAL_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_TOTAL_TAXES = 1D;
    private static final Double UPDATED_TOTAL_TAXES = 2D;
    private static final Double SMALLER_TOTAL_TAXES = 1D - 1D;

    private static final Double DEFAULT_FEDARAL_TAXES_AMOUNT = 1D;
    private static final Double UPDATED_FEDARAL_TAXES_AMOUNT = 2D;
    private static final Double SMALLER_FEDARAL_TAXES_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_PROVINTIONAL_TAXES_AMOUNT = 1D;
    private static final Double UPDATED_PROVINTIONAL_TAXES_AMOUNT = 2D;
    private static final Double SMALLER_PROVINTIONAL_TAXES_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_DISCOUNTS_AMOUNT = 1D;
    private static final Double UPDATED_DISCOUNTS_AMOUNT = 2D;
    private static final Double SMALLER_DISCOUNTS_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_ADD_ON_AMOUNT = 1D;
    private static final Double UPDATED_ADD_ON_AMOUNT = 2D;
    private static final Double SMALLER_ADD_ON_AMOUNT = 1D - 1D;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/invoices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InvoicesRepository invoicesRepository;

    @Mock
    private InvoicesRepository invoicesRepositoryMock;

    @Autowired
    private InvoicesMapper invoicesMapper;

    @Mock
    private InvoicesService invoicesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoicesMockMvc;

    private Invoices invoices;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoices createEntity(EntityManager em) {
        Invoices invoices = new Invoices()
            .invoiceNumber(DEFAULT_INVOICE_NUMBER)
            .contactId(DEFAULT_CONTACT_ID)
            .contactAddressId(DEFAULT_CONTACT_ADDRESS_ID)
            .contactBillingAdrId(DEFAULT_CONTACT_BILLING_ADR_ID)
            .cartId(DEFAULT_CART_ID)
            .type(DEFAULT_TYPE)
            .requestDate(DEFAULT_REQUEST_DATE)
            .contactName(DEFAULT_CONTACT_NAME)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .lastTranactionId(DEFAULT_LAST_TRANACTION_ID)
            .totalCost(DEFAULT_TOTAL_COST)
            .totalProfit(DEFAULT_TOTAL_PROFIT)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .totalTaxes(DEFAULT_TOTAL_TAXES)
            .fedaralTaxesAmount(DEFAULT_FEDARAL_TAXES_AMOUNT)
            .provintionalTaxesAmount(DEFAULT_PROVINTIONAL_TAXES_AMOUNT)
            .discountsAmount(DEFAULT_DISCOUNTS_AMOUNT)
            .addOnAmount(DEFAULT_ADD_ON_AMOUNT)
            .message(DEFAULT_MESSAGE)
            .createdDate(DEFAULT_CREATED_DATE);
        return invoices;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoices createUpdatedEntity(EntityManager em) {
        Invoices invoices = new Invoices()
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .contactId(UPDATED_CONTACT_ID)
            .contactAddressId(UPDATED_CONTACT_ADDRESS_ID)
            .contactBillingAdrId(UPDATED_CONTACT_BILLING_ADR_ID)
            .cartId(UPDATED_CART_ID)
            .type(UPDATED_TYPE)
            .requestDate(UPDATED_REQUEST_DATE)
            .contactName(UPDATED_CONTACT_NAME)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .lastTranactionId(UPDATED_LAST_TRANACTION_ID)
            .totalCost(UPDATED_TOTAL_COST)
            .totalProfit(UPDATED_TOTAL_PROFIT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalTaxes(UPDATED_TOTAL_TAXES)
            .fedaralTaxesAmount(UPDATED_FEDARAL_TAXES_AMOUNT)
            .provintionalTaxesAmount(UPDATED_PROVINTIONAL_TAXES_AMOUNT)
            .discountsAmount(UPDATED_DISCOUNTS_AMOUNT)
            .addOnAmount(UPDATED_ADD_ON_AMOUNT)
            .message(UPDATED_MESSAGE)
            .createdDate(UPDATED_CREATED_DATE);
        return invoices;
    }

    @BeforeEach
    public void initTest() {
        invoices = createEntity(em);
    }

    @Test
    @Transactional
    void createInvoices() throws Exception {
        int databaseSizeBeforeCreate = invoicesRepository.findAll().size();
        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);
        restInvoicesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isCreated());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeCreate + 1);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getInvoiceNumber()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testInvoices.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testInvoices.getContactAddressId()).isEqualTo(DEFAULT_CONTACT_ADDRESS_ID);
        assertThat(testInvoices.getContactBillingAdrId()).isEqualTo(DEFAULT_CONTACT_BILLING_ADR_ID);
        assertThat(testInvoices.getCartId()).isEqualTo(DEFAULT_CART_ID);
        assertThat(testInvoices.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testInvoices.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testInvoices.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testInvoices.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoices.getLastTranactionId()).isEqualTo(DEFAULT_LAST_TRANACTION_ID);
        assertThat(testInvoices.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
        assertThat(testInvoices.getTotalProfit()).isEqualTo(DEFAULT_TOTAL_PROFIT);
        assertThat(testInvoices.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testInvoices.getTotalTaxes()).isEqualTo(DEFAULT_TOTAL_TAXES);
        assertThat(testInvoices.getFedaralTaxesAmount()).isEqualTo(DEFAULT_FEDARAL_TAXES_AMOUNT);
        assertThat(testInvoices.getProvintionalTaxesAmount()).isEqualTo(DEFAULT_PROVINTIONAL_TAXES_AMOUNT);
        assertThat(testInvoices.getDiscountsAmount()).isEqualTo(DEFAULT_DISCOUNTS_AMOUNT);
        assertThat(testInvoices.getAddOnAmount()).isEqualTo(DEFAULT_ADD_ON_AMOUNT);
        assertThat(testInvoices.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testInvoices.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createInvoicesWithExistingId() throws Exception {
        // Create the Invoices with an existing ID
        invoices.setId(1L);
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        int databaseSizeBeforeCreate = invoicesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoicesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList
        restInvoicesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoices.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].contactAddressId").value(hasItem(DEFAULT_CONTACT_ADDRESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].contactBillingAdrId").value(hasItem(DEFAULT_CONTACT_BILLING_ADR_ID.intValue())))
            .andExpect(jsonPath("$.[*].cartId").value(hasItem(DEFAULT_CART_ID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastTranactionId").value(hasItem(DEFAULT_LAST_TRANACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].totalProfit").value(hasItem(DEFAULT_TOTAL_PROFIT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalTaxes").value(hasItem(DEFAULT_TOTAL_TAXES.doubleValue())))
            .andExpect(jsonPath("$.[*].fedaralTaxesAmount").value(hasItem(DEFAULT_FEDARAL_TAXES_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].provintionalTaxesAmount").value(hasItem(DEFAULT_PROVINTIONAL_TAXES_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].discountsAmount").value(hasItem(DEFAULT_DISCOUNTS_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].addOnAmount").value(hasItem(DEFAULT_ADD_ON_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInvoicesWithEagerRelationshipsIsEnabled() throws Exception {
        when(invoicesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInvoicesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(invoicesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInvoicesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(invoicesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInvoicesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(invoicesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get the invoices
        restInvoicesMockMvc
            .perform(get(ENTITY_API_URL_ID, invoices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoices.getId().intValue()))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID.intValue()))
            .andExpect(jsonPath("$.contactAddressId").value(DEFAULT_CONTACT_ADDRESS_ID.intValue()))
            .andExpect(jsonPath("$.contactBillingAdrId").value(DEFAULT_CONTACT_BILLING_ADR_ID.intValue()))
            .andExpect(jsonPath("$.cartId").value(DEFAULT_CART_ID.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.requestDate").value(DEFAULT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.contactName").value(DEFAULT_CONTACT_NAME))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.lastTranactionId").value(DEFAULT_LAST_TRANACTION_ID.intValue()))
            .andExpect(jsonPath("$.totalCost").value(DEFAULT_TOTAL_COST.doubleValue()))
            .andExpect(jsonPath("$.totalProfit").value(DEFAULT_TOTAL_PROFIT.doubleValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalTaxes").value(DEFAULT_TOTAL_TAXES.doubleValue()))
            .andExpect(jsonPath("$.fedaralTaxesAmount").value(DEFAULT_FEDARAL_TAXES_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.provintionalTaxesAmount").value(DEFAULT_PROVINTIONAL_TAXES_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.discountsAmount").value(DEFAULT_DISCOUNTS_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.addOnAmount").value(DEFAULT_ADD_ON_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getInvoicesByIdFiltering() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        Long id = invoices.getId();

        defaultInvoicesShouldBeFound("id.equals=" + id);
        defaultInvoicesShouldNotBeFound("id.notEquals=" + id);

        defaultInvoicesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvoicesShouldNotBeFound("id.greaterThan=" + id);

        defaultInvoicesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvoicesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceNumber equals to DEFAULT_INVOICE_NUMBER
        defaultInvoicesShouldBeFound("invoiceNumber.equals=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoicesList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultInvoicesShouldNotBeFound("invoiceNumber.equals=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceNumber in DEFAULT_INVOICE_NUMBER or UPDATED_INVOICE_NUMBER
        defaultInvoicesShouldBeFound("invoiceNumber.in=" + DEFAULT_INVOICE_NUMBER + "," + UPDATED_INVOICE_NUMBER);

        // Get all the invoicesList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultInvoicesShouldNotBeFound("invoiceNumber.in=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceNumber is not null
        defaultInvoicesShouldBeFound("invoiceNumber.specified=true");

        // Get all the invoicesList where invoiceNumber is null
        defaultInvoicesShouldNotBeFound("invoiceNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceNumberContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceNumber contains DEFAULT_INVOICE_NUMBER
        defaultInvoicesShouldBeFound("invoiceNumber.contains=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoicesList where invoiceNumber contains UPDATED_INVOICE_NUMBER
        defaultInvoicesShouldNotBeFound("invoiceNumber.contains=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceNumberNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceNumber does not contain DEFAULT_INVOICE_NUMBER
        defaultInvoicesShouldNotBeFound("invoiceNumber.doesNotContain=" + DEFAULT_INVOICE_NUMBER);

        // Get all the invoicesList where invoiceNumber does not contain UPDATED_INVOICE_NUMBER
        defaultInvoicesShouldBeFound("invoiceNumber.doesNotContain=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactIdIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactId equals to DEFAULT_CONTACT_ID
        defaultInvoicesShouldBeFound("contactId.equals=" + DEFAULT_CONTACT_ID);

        // Get all the invoicesList where contactId equals to UPDATED_CONTACT_ID
        defaultInvoicesShouldNotBeFound("contactId.equals=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactIdIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactId in DEFAULT_CONTACT_ID or UPDATED_CONTACT_ID
        defaultInvoicesShouldBeFound("contactId.in=" + DEFAULT_CONTACT_ID + "," + UPDATED_CONTACT_ID);

        // Get all the invoicesList where contactId equals to UPDATED_CONTACT_ID
        defaultInvoicesShouldNotBeFound("contactId.in=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactId is not null
        defaultInvoicesShouldBeFound("contactId.specified=true");

        // Get all the invoicesList where contactId is null
        defaultInvoicesShouldNotBeFound("contactId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByContactIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactId is greater than or equal to DEFAULT_CONTACT_ID
        defaultInvoicesShouldBeFound("contactId.greaterThanOrEqual=" + DEFAULT_CONTACT_ID);

        // Get all the invoicesList where contactId is greater than or equal to UPDATED_CONTACT_ID
        defaultInvoicesShouldNotBeFound("contactId.greaterThanOrEqual=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactId is less than or equal to DEFAULT_CONTACT_ID
        defaultInvoicesShouldBeFound("contactId.lessThanOrEqual=" + DEFAULT_CONTACT_ID);

        // Get all the invoicesList where contactId is less than or equal to SMALLER_CONTACT_ID
        defaultInvoicesShouldNotBeFound("contactId.lessThanOrEqual=" + SMALLER_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactIdIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactId is less than DEFAULT_CONTACT_ID
        defaultInvoicesShouldNotBeFound("contactId.lessThan=" + DEFAULT_CONTACT_ID);

        // Get all the invoicesList where contactId is less than UPDATED_CONTACT_ID
        defaultInvoicesShouldBeFound("contactId.lessThan=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactId is greater than DEFAULT_CONTACT_ID
        defaultInvoicesShouldNotBeFound("contactId.greaterThan=" + DEFAULT_CONTACT_ID);

        // Get all the invoicesList where contactId is greater than SMALLER_CONTACT_ID
        defaultInvoicesShouldBeFound("contactId.greaterThan=" + SMALLER_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactAddressIdIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactAddressId equals to DEFAULT_CONTACT_ADDRESS_ID
        defaultInvoicesShouldBeFound("contactAddressId.equals=" + DEFAULT_CONTACT_ADDRESS_ID);

        // Get all the invoicesList where contactAddressId equals to UPDATED_CONTACT_ADDRESS_ID
        defaultInvoicesShouldNotBeFound("contactAddressId.equals=" + UPDATED_CONTACT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactAddressIdIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactAddressId in DEFAULT_CONTACT_ADDRESS_ID or UPDATED_CONTACT_ADDRESS_ID
        defaultInvoicesShouldBeFound("contactAddressId.in=" + DEFAULT_CONTACT_ADDRESS_ID + "," + UPDATED_CONTACT_ADDRESS_ID);

        // Get all the invoicesList where contactAddressId equals to UPDATED_CONTACT_ADDRESS_ID
        defaultInvoicesShouldNotBeFound("contactAddressId.in=" + UPDATED_CONTACT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactAddressIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactAddressId is not null
        defaultInvoicesShouldBeFound("contactAddressId.specified=true");

        // Get all the invoicesList where contactAddressId is null
        defaultInvoicesShouldNotBeFound("contactAddressId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByContactAddressIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactAddressId is greater than or equal to DEFAULT_CONTACT_ADDRESS_ID
        defaultInvoicesShouldBeFound("contactAddressId.greaterThanOrEqual=" + DEFAULT_CONTACT_ADDRESS_ID);

        // Get all the invoicesList where contactAddressId is greater than or equal to UPDATED_CONTACT_ADDRESS_ID
        defaultInvoicesShouldNotBeFound("contactAddressId.greaterThanOrEqual=" + UPDATED_CONTACT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactAddressIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactAddressId is less than or equal to DEFAULT_CONTACT_ADDRESS_ID
        defaultInvoicesShouldBeFound("contactAddressId.lessThanOrEqual=" + DEFAULT_CONTACT_ADDRESS_ID);

        // Get all the invoicesList where contactAddressId is less than or equal to SMALLER_CONTACT_ADDRESS_ID
        defaultInvoicesShouldNotBeFound("contactAddressId.lessThanOrEqual=" + SMALLER_CONTACT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactAddressIdIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactAddressId is less than DEFAULT_CONTACT_ADDRESS_ID
        defaultInvoicesShouldNotBeFound("contactAddressId.lessThan=" + DEFAULT_CONTACT_ADDRESS_ID);

        // Get all the invoicesList where contactAddressId is less than UPDATED_CONTACT_ADDRESS_ID
        defaultInvoicesShouldBeFound("contactAddressId.lessThan=" + UPDATED_CONTACT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactAddressIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactAddressId is greater than DEFAULT_CONTACT_ADDRESS_ID
        defaultInvoicesShouldNotBeFound("contactAddressId.greaterThan=" + DEFAULT_CONTACT_ADDRESS_ID);

        // Get all the invoicesList where contactAddressId is greater than SMALLER_CONTACT_ADDRESS_ID
        defaultInvoicesShouldBeFound("contactAddressId.greaterThan=" + SMALLER_CONTACT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactBillingAdrIdIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactBillingAdrId equals to DEFAULT_CONTACT_BILLING_ADR_ID
        defaultInvoicesShouldBeFound("contactBillingAdrId.equals=" + DEFAULT_CONTACT_BILLING_ADR_ID);

        // Get all the invoicesList where contactBillingAdrId equals to UPDATED_CONTACT_BILLING_ADR_ID
        defaultInvoicesShouldNotBeFound("contactBillingAdrId.equals=" + UPDATED_CONTACT_BILLING_ADR_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactBillingAdrIdIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactBillingAdrId in DEFAULT_CONTACT_BILLING_ADR_ID or UPDATED_CONTACT_BILLING_ADR_ID
        defaultInvoicesShouldBeFound("contactBillingAdrId.in=" + DEFAULT_CONTACT_BILLING_ADR_ID + "," + UPDATED_CONTACT_BILLING_ADR_ID);

        // Get all the invoicesList where contactBillingAdrId equals to UPDATED_CONTACT_BILLING_ADR_ID
        defaultInvoicesShouldNotBeFound("contactBillingAdrId.in=" + UPDATED_CONTACT_BILLING_ADR_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactBillingAdrIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactBillingAdrId is not null
        defaultInvoicesShouldBeFound("contactBillingAdrId.specified=true");

        // Get all the invoicesList where contactBillingAdrId is null
        defaultInvoicesShouldNotBeFound("contactBillingAdrId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByContactBillingAdrIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactBillingAdrId is greater than or equal to DEFAULT_CONTACT_BILLING_ADR_ID
        defaultInvoicesShouldBeFound("contactBillingAdrId.greaterThanOrEqual=" + DEFAULT_CONTACT_BILLING_ADR_ID);

        // Get all the invoicesList where contactBillingAdrId is greater than or equal to UPDATED_CONTACT_BILLING_ADR_ID
        defaultInvoicesShouldNotBeFound("contactBillingAdrId.greaterThanOrEqual=" + UPDATED_CONTACT_BILLING_ADR_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactBillingAdrIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactBillingAdrId is less than or equal to DEFAULT_CONTACT_BILLING_ADR_ID
        defaultInvoicesShouldBeFound("contactBillingAdrId.lessThanOrEqual=" + DEFAULT_CONTACT_BILLING_ADR_ID);

        // Get all the invoicesList where contactBillingAdrId is less than or equal to SMALLER_CONTACT_BILLING_ADR_ID
        defaultInvoicesShouldNotBeFound("contactBillingAdrId.lessThanOrEqual=" + SMALLER_CONTACT_BILLING_ADR_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactBillingAdrIdIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactBillingAdrId is less than DEFAULT_CONTACT_BILLING_ADR_ID
        defaultInvoicesShouldNotBeFound("contactBillingAdrId.lessThan=" + DEFAULT_CONTACT_BILLING_ADR_ID);

        // Get all the invoicesList where contactBillingAdrId is less than UPDATED_CONTACT_BILLING_ADR_ID
        defaultInvoicesShouldBeFound("contactBillingAdrId.lessThan=" + UPDATED_CONTACT_BILLING_ADR_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactBillingAdrIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactBillingAdrId is greater than DEFAULT_CONTACT_BILLING_ADR_ID
        defaultInvoicesShouldNotBeFound("contactBillingAdrId.greaterThan=" + DEFAULT_CONTACT_BILLING_ADR_ID);

        // Get all the invoicesList where contactBillingAdrId is greater than SMALLER_CONTACT_BILLING_ADR_ID
        defaultInvoicesShouldBeFound("contactBillingAdrId.greaterThan=" + SMALLER_CONTACT_BILLING_ADR_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByCartIdIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where cartId equals to DEFAULT_CART_ID
        defaultInvoicesShouldBeFound("cartId.equals=" + DEFAULT_CART_ID);

        // Get all the invoicesList where cartId equals to UPDATED_CART_ID
        defaultInvoicesShouldNotBeFound("cartId.equals=" + UPDATED_CART_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByCartIdIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where cartId in DEFAULT_CART_ID or UPDATED_CART_ID
        defaultInvoicesShouldBeFound("cartId.in=" + DEFAULT_CART_ID + "," + UPDATED_CART_ID);

        // Get all the invoicesList where cartId equals to UPDATED_CART_ID
        defaultInvoicesShouldNotBeFound("cartId.in=" + UPDATED_CART_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByCartIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where cartId is not null
        defaultInvoicesShouldBeFound("cartId.specified=true");

        // Get all the invoicesList where cartId is null
        defaultInvoicesShouldNotBeFound("cartId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByCartIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where cartId is greater than or equal to DEFAULT_CART_ID
        defaultInvoicesShouldBeFound("cartId.greaterThanOrEqual=" + DEFAULT_CART_ID);

        // Get all the invoicesList where cartId is greater than or equal to UPDATED_CART_ID
        defaultInvoicesShouldNotBeFound("cartId.greaterThanOrEqual=" + UPDATED_CART_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByCartIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where cartId is less than or equal to DEFAULT_CART_ID
        defaultInvoicesShouldBeFound("cartId.lessThanOrEqual=" + DEFAULT_CART_ID);

        // Get all the invoicesList where cartId is less than or equal to SMALLER_CART_ID
        defaultInvoicesShouldNotBeFound("cartId.lessThanOrEqual=" + SMALLER_CART_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByCartIdIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where cartId is less than DEFAULT_CART_ID
        defaultInvoicesShouldNotBeFound("cartId.lessThan=" + DEFAULT_CART_ID);

        // Get all the invoicesList where cartId is less than UPDATED_CART_ID
        defaultInvoicesShouldBeFound("cartId.lessThan=" + UPDATED_CART_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByCartIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where cartId is greater than DEFAULT_CART_ID
        defaultInvoicesShouldNotBeFound("cartId.greaterThan=" + DEFAULT_CART_ID);

        // Get all the invoicesList where cartId is greater than SMALLER_CART_ID
        defaultInvoicesShouldBeFound("cartId.greaterThan=" + SMALLER_CART_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where type equals to DEFAULT_TYPE
        defaultInvoicesShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the invoicesList where type equals to UPDATED_TYPE
        defaultInvoicesShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllInvoicesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultInvoicesShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the invoicesList where type equals to UPDATED_TYPE
        defaultInvoicesShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllInvoicesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where type is not null
        defaultInvoicesShouldBeFound("type.specified=true");

        // Get all the invoicesList where type is null
        defaultInvoicesShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByRequestDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where requestDate equals to DEFAULT_REQUEST_DATE
        defaultInvoicesShouldBeFound("requestDate.equals=" + DEFAULT_REQUEST_DATE);

        // Get all the invoicesList where requestDate equals to UPDATED_REQUEST_DATE
        defaultInvoicesShouldNotBeFound("requestDate.equals=" + UPDATED_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByRequestDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where requestDate in DEFAULT_REQUEST_DATE or UPDATED_REQUEST_DATE
        defaultInvoicesShouldBeFound("requestDate.in=" + DEFAULT_REQUEST_DATE + "," + UPDATED_REQUEST_DATE);

        // Get all the invoicesList where requestDate equals to UPDATED_REQUEST_DATE
        defaultInvoicesShouldNotBeFound("requestDate.in=" + UPDATED_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByRequestDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where requestDate is not null
        defaultInvoicesShouldBeFound("requestDate.specified=true");

        // Get all the invoicesList where requestDate is null
        defaultInvoicesShouldNotBeFound("requestDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByRequestDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where requestDate is greater than or equal to DEFAULT_REQUEST_DATE
        defaultInvoicesShouldBeFound("requestDate.greaterThanOrEqual=" + DEFAULT_REQUEST_DATE);

        // Get all the invoicesList where requestDate is greater than or equal to UPDATED_REQUEST_DATE
        defaultInvoicesShouldNotBeFound("requestDate.greaterThanOrEqual=" + UPDATED_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByRequestDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where requestDate is less than or equal to DEFAULT_REQUEST_DATE
        defaultInvoicesShouldBeFound("requestDate.lessThanOrEqual=" + DEFAULT_REQUEST_DATE);

        // Get all the invoicesList where requestDate is less than or equal to SMALLER_REQUEST_DATE
        defaultInvoicesShouldNotBeFound("requestDate.lessThanOrEqual=" + SMALLER_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByRequestDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where requestDate is less than DEFAULT_REQUEST_DATE
        defaultInvoicesShouldNotBeFound("requestDate.lessThan=" + DEFAULT_REQUEST_DATE);

        // Get all the invoicesList where requestDate is less than UPDATED_REQUEST_DATE
        defaultInvoicesShouldBeFound("requestDate.lessThan=" + UPDATED_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByRequestDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where requestDate is greater than DEFAULT_REQUEST_DATE
        defaultInvoicesShouldNotBeFound("requestDate.greaterThan=" + DEFAULT_REQUEST_DATE);

        // Get all the invoicesList where requestDate is greater than SMALLER_REQUEST_DATE
        defaultInvoicesShouldBeFound("requestDate.greaterThan=" + SMALLER_REQUEST_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactName equals to DEFAULT_CONTACT_NAME
        defaultInvoicesShouldBeFound("contactName.equals=" + DEFAULT_CONTACT_NAME);

        // Get all the invoicesList where contactName equals to UPDATED_CONTACT_NAME
        defaultInvoicesShouldNotBeFound("contactName.equals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactName in DEFAULT_CONTACT_NAME or UPDATED_CONTACT_NAME
        defaultInvoicesShouldBeFound("contactName.in=" + DEFAULT_CONTACT_NAME + "," + UPDATED_CONTACT_NAME);

        // Get all the invoicesList where contactName equals to UPDATED_CONTACT_NAME
        defaultInvoicesShouldNotBeFound("contactName.in=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactName is not null
        defaultInvoicesShouldBeFound("contactName.specified=true");

        // Get all the invoicesList where contactName is null
        defaultInvoicesShouldNotBeFound("contactName.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByContactNameContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactName contains DEFAULT_CONTACT_NAME
        defaultInvoicesShouldBeFound("contactName.contains=" + DEFAULT_CONTACT_NAME);

        // Get all the invoicesList where contactName contains UPDATED_CONTACT_NAME
        defaultInvoicesShouldNotBeFound("contactName.contains=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllInvoicesByContactNameNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where contactName does not contain DEFAULT_CONTACT_NAME
        defaultInvoicesShouldNotBeFound("contactName.doesNotContain=" + DEFAULT_CONTACT_NAME);

        // Get all the invoicesList where contactName does not contain UPDATED_CONTACT_NAME
        defaultInvoicesShouldBeFound("contactName.doesNotContain=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceDate equals to DEFAULT_INVOICE_DATE
        defaultInvoicesShouldBeFound("invoiceDate.equals=" + DEFAULT_INVOICE_DATE);

        // Get all the invoicesList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultInvoicesShouldNotBeFound("invoiceDate.equals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceDate in DEFAULT_INVOICE_DATE or UPDATED_INVOICE_DATE
        defaultInvoicesShouldBeFound("invoiceDate.in=" + DEFAULT_INVOICE_DATE + "," + UPDATED_INVOICE_DATE);

        // Get all the invoicesList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultInvoicesShouldNotBeFound("invoiceDate.in=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceDate is not null
        defaultInvoicesShouldBeFound("invoiceDate.specified=true");

        // Get all the invoicesList where invoiceDate is null
        defaultInvoicesShouldNotBeFound("invoiceDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceDate is greater than or equal to DEFAULT_INVOICE_DATE
        defaultInvoicesShouldBeFound("invoiceDate.greaterThanOrEqual=" + DEFAULT_INVOICE_DATE);

        // Get all the invoicesList where invoiceDate is greater than or equal to UPDATED_INVOICE_DATE
        defaultInvoicesShouldNotBeFound("invoiceDate.greaterThanOrEqual=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceDate is less than or equal to DEFAULT_INVOICE_DATE
        defaultInvoicesShouldBeFound("invoiceDate.lessThanOrEqual=" + DEFAULT_INVOICE_DATE);

        // Get all the invoicesList where invoiceDate is less than or equal to SMALLER_INVOICE_DATE
        defaultInvoicesShouldNotBeFound("invoiceDate.lessThanOrEqual=" + SMALLER_INVOICE_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceDate is less than DEFAULT_INVOICE_DATE
        defaultInvoicesShouldNotBeFound("invoiceDate.lessThan=" + DEFAULT_INVOICE_DATE);

        // Get all the invoicesList where invoiceDate is less than UPDATED_INVOICE_DATE
        defaultInvoicesShouldBeFound("invoiceDate.lessThan=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where invoiceDate is greater than DEFAULT_INVOICE_DATE
        defaultInvoicesShouldNotBeFound("invoiceDate.greaterThan=" + DEFAULT_INVOICE_DATE);

        // Get all the invoicesList where invoiceDate is greater than SMALLER_INVOICE_DATE
        defaultInvoicesShouldBeFound("invoiceDate.greaterThan=" + SMALLER_INVOICE_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByLastTranactionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastTranactionId equals to DEFAULT_LAST_TRANACTION_ID
        defaultInvoicesShouldBeFound("lastTranactionId.equals=" + DEFAULT_LAST_TRANACTION_ID);

        // Get all the invoicesList where lastTranactionId equals to UPDATED_LAST_TRANACTION_ID
        defaultInvoicesShouldNotBeFound("lastTranactionId.equals=" + UPDATED_LAST_TRANACTION_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByLastTranactionIdIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastTranactionId in DEFAULT_LAST_TRANACTION_ID or UPDATED_LAST_TRANACTION_ID
        defaultInvoicesShouldBeFound("lastTranactionId.in=" + DEFAULT_LAST_TRANACTION_ID + "," + UPDATED_LAST_TRANACTION_ID);

        // Get all the invoicesList where lastTranactionId equals to UPDATED_LAST_TRANACTION_ID
        defaultInvoicesShouldNotBeFound("lastTranactionId.in=" + UPDATED_LAST_TRANACTION_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByLastTranactionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastTranactionId is not null
        defaultInvoicesShouldBeFound("lastTranactionId.specified=true");

        // Get all the invoicesList where lastTranactionId is null
        defaultInvoicesShouldNotBeFound("lastTranactionId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByLastTranactionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastTranactionId is greater than or equal to DEFAULT_LAST_TRANACTION_ID
        defaultInvoicesShouldBeFound("lastTranactionId.greaterThanOrEqual=" + DEFAULT_LAST_TRANACTION_ID);

        // Get all the invoicesList where lastTranactionId is greater than or equal to UPDATED_LAST_TRANACTION_ID
        defaultInvoicesShouldNotBeFound("lastTranactionId.greaterThanOrEqual=" + UPDATED_LAST_TRANACTION_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByLastTranactionIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastTranactionId is less than or equal to DEFAULT_LAST_TRANACTION_ID
        defaultInvoicesShouldBeFound("lastTranactionId.lessThanOrEqual=" + DEFAULT_LAST_TRANACTION_ID);

        // Get all the invoicesList where lastTranactionId is less than or equal to SMALLER_LAST_TRANACTION_ID
        defaultInvoicesShouldNotBeFound("lastTranactionId.lessThanOrEqual=" + SMALLER_LAST_TRANACTION_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByLastTranactionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastTranactionId is less than DEFAULT_LAST_TRANACTION_ID
        defaultInvoicesShouldNotBeFound("lastTranactionId.lessThan=" + DEFAULT_LAST_TRANACTION_ID);

        // Get all the invoicesList where lastTranactionId is less than UPDATED_LAST_TRANACTION_ID
        defaultInvoicesShouldBeFound("lastTranactionId.lessThan=" + UPDATED_LAST_TRANACTION_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByLastTranactionIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where lastTranactionId is greater than DEFAULT_LAST_TRANACTION_ID
        defaultInvoicesShouldNotBeFound("lastTranactionId.greaterThan=" + DEFAULT_LAST_TRANACTION_ID);

        // Get all the invoicesList where lastTranactionId is greater than SMALLER_LAST_TRANACTION_ID
        defaultInvoicesShouldBeFound("lastTranactionId.greaterThan=" + SMALLER_LAST_TRANACTION_ID);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalCostIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalCost equals to DEFAULT_TOTAL_COST
        defaultInvoicesShouldBeFound("totalCost.equals=" + DEFAULT_TOTAL_COST);

        // Get all the invoicesList where totalCost equals to UPDATED_TOTAL_COST
        defaultInvoicesShouldNotBeFound("totalCost.equals=" + UPDATED_TOTAL_COST);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalCostIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalCost in DEFAULT_TOTAL_COST or UPDATED_TOTAL_COST
        defaultInvoicesShouldBeFound("totalCost.in=" + DEFAULT_TOTAL_COST + "," + UPDATED_TOTAL_COST);

        // Get all the invoicesList where totalCost equals to UPDATED_TOTAL_COST
        defaultInvoicesShouldNotBeFound("totalCost.in=" + UPDATED_TOTAL_COST);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalCost is not null
        defaultInvoicesShouldBeFound("totalCost.specified=true");

        // Get all the invoicesList where totalCost is null
        defaultInvoicesShouldNotBeFound("totalCost.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalCost is greater than or equal to DEFAULT_TOTAL_COST
        defaultInvoicesShouldBeFound("totalCost.greaterThanOrEqual=" + DEFAULT_TOTAL_COST);

        // Get all the invoicesList where totalCost is greater than or equal to UPDATED_TOTAL_COST
        defaultInvoicesShouldNotBeFound("totalCost.greaterThanOrEqual=" + UPDATED_TOTAL_COST);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalCost is less than or equal to DEFAULT_TOTAL_COST
        defaultInvoicesShouldBeFound("totalCost.lessThanOrEqual=" + DEFAULT_TOTAL_COST);

        // Get all the invoicesList where totalCost is less than or equal to SMALLER_TOTAL_COST
        defaultInvoicesShouldNotBeFound("totalCost.lessThanOrEqual=" + SMALLER_TOTAL_COST);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalCostIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalCost is less than DEFAULT_TOTAL_COST
        defaultInvoicesShouldNotBeFound("totalCost.lessThan=" + DEFAULT_TOTAL_COST);

        // Get all the invoicesList where totalCost is less than UPDATED_TOTAL_COST
        defaultInvoicesShouldBeFound("totalCost.lessThan=" + UPDATED_TOTAL_COST);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalCost is greater than DEFAULT_TOTAL_COST
        defaultInvoicesShouldNotBeFound("totalCost.greaterThan=" + DEFAULT_TOTAL_COST);

        // Get all the invoicesList where totalCost is greater than SMALLER_TOTAL_COST
        defaultInvoicesShouldBeFound("totalCost.greaterThan=" + SMALLER_TOTAL_COST);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalProfitIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalProfit equals to DEFAULT_TOTAL_PROFIT
        defaultInvoicesShouldBeFound("totalProfit.equals=" + DEFAULT_TOTAL_PROFIT);

        // Get all the invoicesList where totalProfit equals to UPDATED_TOTAL_PROFIT
        defaultInvoicesShouldNotBeFound("totalProfit.equals=" + UPDATED_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalProfitIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalProfit in DEFAULT_TOTAL_PROFIT or UPDATED_TOTAL_PROFIT
        defaultInvoicesShouldBeFound("totalProfit.in=" + DEFAULT_TOTAL_PROFIT + "," + UPDATED_TOTAL_PROFIT);

        // Get all the invoicesList where totalProfit equals to UPDATED_TOTAL_PROFIT
        defaultInvoicesShouldNotBeFound("totalProfit.in=" + UPDATED_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalProfitIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalProfit is not null
        defaultInvoicesShouldBeFound("totalProfit.specified=true");

        // Get all the invoicesList where totalProfit is null
        defaultInvoicesShouldNotBeFound("totalProfit.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalProfitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalProfit is greater than or equal to DEFAULT_TOTAL_PROFIT
        defaultInvoicesShouldBeFound("totalProfit.greaterThanOrEqual=" + DEFAULT_TOTAL_PROFIT);

        // Get all the invoicesList where totalProfit is greater than or equal to UPDATED_TOTAL_PROFIT
        defaultInvoicesShouldNotBeFound("totalProfit.greaterThanOrEqual=" + UPDATED_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalProfitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalProfit is less than or equal to DEFAULT_TOTAL_PROFIT
        defaultInvoicesShouldBeFound("totalProfit.lessThanOrEqual=" + DEFAULT_TOTAL_PROFIT);

        // Get all the invoicesList where totalProfit is less than or equal to SMALLER_TOTAL_PROFIT
        defaultInvoicesShouldNotBeFound("totalProfit.lessThanOrEqual=" + SMALLER_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalProfitIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalProfit is less than DEFAULT_TOTAL_PROFIT
        defaultInvoicesShouldNotBeFound("totalProfit.lessThan=" + DEFAULT_TOTAL_PROFIT);

        // Get all the invoicesList where totalProfit is less than UPDATED_TOTAL_PROFIT
        defaultInvoicesShouldBeFound("totalProfit.lessThan=" + UPDATED_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalProfitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalProfit is greater than DEFAULT_TOTAL_PROFIT
        defaultInvoicesShouldNotBeFound("totalProfit.greaterThan=" + DEFAULT_TOTAL_PROFIT);

        // Get all the invoicesList where totalProfit is greater than SMALLER_TOTAL_PROFIT
        defaultInvoicesShouldBeFound("totalProfit.greaterThan=" + SMALLER_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultInvoicesShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoicesList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultInvoicesShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultInvoicesShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the invoicesList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultInvoicesShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalAmount is not null
        defaultInvoicesShouldBeFound("totalAmount.specified=true");

        // Get all the invoicesList where totalAmount is null
        defaultInvoicesShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalAmount is greater than or equal to DEFAULT_TOTAL_AMOUNT
        defaultInvoicesShouldBeFound("totalAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoicesList where totalAmount is greater than or equal to UPDATED_TOTAL_AMOUNT
        defaultInvoicesShouldNotBeFound("totalAmount.greaterThanOrEqual=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalAmount is less than or equal to DEFAULT_TOTAL_AMOUNT
        defaultInvoicesShouldBeFound("totalAmount.lessThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoicesList where totalAmount is less than or equal to SMALLER_TOTAL_AMOUNT
        defaultInvoicesShouldNotBeFound("totalAmount.lessThanOrEqual=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalAmount is less than DEFAULT_TOTAL_AMOUNT
        defaultInvoicesShouldNotBeFound("totalAmount.lessThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoicesList where totalAmount is less than UPDATED_TOTAL_AMOUNT
        defaultInvoicesShouldBeFound("totalAmount.lessThan=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalAmount is greater than DEFAULT_TOTAL_AMOUNT
        defaultInvoicesShouldNotBeFound("totalAmount.greaterThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoicesList where totalAmount is greater than SMALLER_TOTAL_AMOUNT
        defaultInvoicesShouldBeFound("totalAmount.greaterThan=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalTaxesIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalTaxes equals to DEFAULT_TOTAL_TAXES
        defaultInvoicesShouldBeFound("totalTaxes.equals=" + DEFAULT_TOTAL_TAXES);

        // Get all the invoicesList where totalTaxes equals to UPDATED_TOTAL_TAXES
        defaultInvoicesShouldNotBeFound("totalTaxes.equals=" + UPDATED_TOTAL_TAXES);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalTaxesIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalTaxes in DEFAULT_TOTAL_TAXES or UPDATED_TOTAL_TAXES
        defaultInvoicesShouldBeFound("totalTaxes.in=" + DEFAULT_TOTAL_TAXES + "," + UPDATED_TOTAL_TAXES);

        // Get all the invoicesList where totalTaxes equals to UPDATED_TOTAL_TAXES
        defaultInvoicesShouldNotBeFound("totalTaxes.in=" + UPDATED_TOTAL_TAXES);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalTaxesIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalTaxes is not null
        defaultInvoicesShouldBeFound("totalTaxes.specified=true");

        // Get all the invoicesList where totalTaxes is null
        defaultInvoicesShouldNotBeFound("totalTaxes.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalTaxesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalTaxes is greater than or equal to DEFAULT_TOTAL_TAXES
        defaultInvoicesShouldBeFound("totalTaxes.greaterThanOrEqual=" + DEFAULT_TOTAL_TAXES);

        // Get all the invoicesList where totalTaxes is greater than or equal to UPDATED_TOTAL_TAXES
        defaultInvoicesShouldNotBeFound("totalTaxes.greaterThanOrEqual=" + UPDATED_TOTAL_TAXES);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalTaxesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalTaxes is less than or equal to DEFAULT_TOTAL_TAXES
        defaultInvoicesShouldBeFound("totalTaxes.lessThanOrEqual=" + DEFAULT_TOTAL_TAXES);

        // Get all the invoicesList where totalTaxes is less than or equal to SMALLER_TOTAL_TAXES
        defaultInvoicesShouldNotBeFound("totalTaxes.lessThanOrEqual=" + SMALLER_TOTAL_TAXES);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalTaxesIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalTaxes is less than DEFAULT_TOTAL_TAXES
        defaultInvoicesShouldNotBeFound("totalTaxes.lessThan=" + DEFAULT_TOTAL_TAXES);

        // Get all the invoicesList where totalTaxes is less than UPDATED_TOTAL_TAXES
        defaultInvoicesShouldBeFound("totalTaxes.lessThan=" + UPDATED_TOTAL_TAXES);
    }

    @Test
    @Transactional
    void getAllInvoicesByTotalTaxesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where totalTaxes is greater than DEFAULT_TOTAL_TAXES
        defaultInvoicesShouldNotBeFound("totalTaxes.greaterThan=" + DEFAULT_TOTAL_TAXES);

        // Get all the invoicesList where totalTaxes is greater than SMALLER_TOTAL_TAXES
        defaultInvoicesShouldBeFound("totalTaxes.greaterThan=" + SMALLER_TOTAL_TAXES);
    }

    @Test
    @Transactional
    void getAllInvoicesByFedaralTaxesAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where fedaralTaxesAmount equals to DEFAULT_FEDARAL_TAXES_AMOUNT
        defaultInvoicesShouldBeFound("fedaralTaxesAmount.equals=" + DEFAULT_FEDARAL_TAXES_AMOUNT);

        // Get all the invoicesList where fedaralTaxesAmount equals to UPDATED_FEDARAL_TAXES_AMOUNT
        defaultInvoicesShouldNotBeFound("fedaralTaxesAmount.equals=" + UPDATED_FEDARAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByFedaralTaxesAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where fedaralTaxesAmount in DEFAULT_FEDARAL_TAXES_AMOUNT or UPDATED_FEDARAL_TAXES_AMOUNT
        defaultInvoicesShouldBeFound("fedaralTaxesAmount.in=" + DEFAULT_FEDARAL_TAXES_AMOUNT + "," + UPDATED_FEDARAL_TAXES_AMOUNT);

        // Get all the invoicesList where fedaralTaxesAmount equals to UPDATED_FEDARAL_TAXES_AMOUNT
        defaultInvoicesShouldNotBeFound("fedaralTaxesAmount.in=" + UPDATED_FEDARAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByFedaralTaxesAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where fedaralTaxesAmount is not null
        defaultInvoicesShouldBeFound("fedaralTaxesAmount.specified=true");

        // Get all the invoicesList where fedaralTaxesAmount is null
        defaultInvoicesShouldNotBeFound("fedaralTaxesAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByFedaralTaxesAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where fedaralTaxesAmount is greater than or equal to DEFAULT_FEDARAL_TAXES_AMOUNT
        defaultInvoicesShouldBeFound("fedaralTaxesAmount.greaterThanOrEqual=" + DEFAULT_FEDARAL_TAXES_AMOUNT);

        // Get all the invoicesList where fedaralTaxesAmount is greater than or equal to UPDATED_FEDARAL_TAXES_AMOUNT
        defaultInvoicesShouldNotBeFound("fedaralTaxesAmount.greaterThanOrEqual=" + UPDATED_FEDARAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByFedaralTaxesAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where fedaralTaxesAmount is less than or equal to DEFAULT_FEDARAL_TAXES_AMOUNT
        defaultInvoicesShouldBeFound("fedaralTaxesAmount.lessThanOrEqual=" + DEFAULT_FEDARAL_TAXES_AMOUNT);

        // Get all the invoicesList where fedaralTaxesAmount is less than or equal to SMALLER_FEDARAL_TAXES_AMOUNT
        defaultInvoicesShouldNotBeFound("fedaralTaxesAmount.lessThanOrEqual=" + SMALLER_FEDARAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByFedaralTaxesAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where fedaralTaxesAmount is less than DEFAULT_FEDARAL_TAXES_AMOUNT
        defaultInvoicesShouldNotBeFound("fedaralTaxesAmount.lessThan=" + DEFAULT_FEDARAL_TAXES_AMOUNT);

        // Get all the invoicesList where fedaralTaxesAmount is less than UPDATED_FEDARAL_TAXES_AMOUNT
        defaultInvoicesShouldBeFound("fedaralTaxesAmount.lessThan=" + UPDATED_FEDARAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByFedaralTaxesAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where fedaralTaxesAmount is greater than DEFAULT_FEDARAL_TAXES_AMOUNT
        defaultInvoicesShouldNotBeFound("fedaralTaxesAmount.greaterThan=" + DEFAULT_FEDARAL_TAXES_AMOUNT);

        // Get all the invoicesList where fedaralTaxesAmount is greater than SMALLER_FEDARAL_TAXES_AMOUNT
        defaultInvoicesShouldBeFound("fedaralTaxesAmount.greaterThan=" + SMALLER_FEDARAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByProvintionalTaxesAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where provintionalTaxesAmount equals to DEFAULT_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoicesShouldBeFound("provintionalTaxesAmount.equals=" + DEFAULT_PROVINTIONAL_TAXES_AMOUNT);

        // Get all the invoicesList where provintionalTaxesAmount equals to UPDATED_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoicesShouldNotBeFound("provintionalTaxesAmount.equals=" + UPDATED_PROVINTIONAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByProvintionalTaxesAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where provintionalTaxesAmount in DEFAULT_PROVINTIONAL_TAXES_AMOUNT or UPDATED_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoicesShouldBeFound(
            "provintionalTaxesAmount.in=" + DEFAULT_PROVINTIONAL_TAXES_AMOUNT + "," + UPDATED_PROVINTIONAL_TAXES_AMOUNT
        );

        // Get all the invoicesList where provintionalTaxesAmount equals to UPDATED_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoicesShouldNotBeFound("provintionalTaxesAmount.in=" + UPDATED_PROVINTIONAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByProvintionalTaxesAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where provintionalTaxesAmount is not null
        defaultInvoicesShouldBeFound("provintionalTaxesAmount.specified=true");

        // Get all the invoicesList where provintionalTaxesAmount is null
        defaultInvoicesShouldNotBeFound("provintionalTaxesAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByProvintionalTaxesAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where provintionalTaxesAmount is greater than or equal to DEFAULT_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoicesShouldBeFound("provintionalTaxesAmount.greaterThanOrEqual=" + DEFAULT_PROVINTIONAL_TAXES_AMOUNT);

        // Get all the invoicesList where provintionalTaxesAmount is greater than or equal to UPDATED_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoicesShouldNotBeFound("provintionalTaxesAmount.greaterThanOrEqual=" + UPDATED_PROVINTIONAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByProvintionalTaxesAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where provintionalTaxesAmount is less than or equal to DEFAULT_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoicesShouldBeFound("provintionalTaxesAmount.lessThanOrEqual=" + DEFAULT_PROVINTIONAL_TAXES_AMOUNT);

        // Get all the invoicesList where provintionalTaxesAmount is less than or equal to SMALLER_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoicesShouldNotBeFound("provintionalTaxesAmount.lessThanOrEqual=" + SMALLER_PROVINTIONAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByProvintionalTaxesAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where provintionalTaxesAmount is less than DEFAULT_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoicesShouldNotBeFound("provintionalTaxesAmount.lessThan=" + DEFAULT_PROVINTIONAL_TAXES_AMOUNT);

        // Get all the invoicesList where provintionalTaxesAmount is less than UPDATED_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoicesShouldBeFound("provintionalTaxesAmount.lessThan=" + UPDATED_PROVINTIONAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByProvintionalTaxesAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where provintionalTaxesAmount is greater than DEFAULT_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoicesShouldNotBeFound("provintionalTaxesAmount.greaterThan=" + DEFAULT_PROVINTIONAL_TAXES_AMOUNT);

        // Get all the invoicesList where provintionalTaxesAmount is greater than SMALLER_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoicesShouldBeFound("provintionalTaxesAmount.greaterThan=" + SMALLER_PROVINTIONAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByDiscountsAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where discountsAmount equals to DEFAULT_DISCOUNTS_AMOUNT
        defaultInvoicesShouldBeFound("discountsAmount.equals=" + DEFAULT_DISCOUNTS_AMOUNT);

        // Get all the invoicesList where discountsAmount equals to UPDATED_DISCOUNTS_AMOUNT
        defaultInvoicesShouldNotBeFound("discountsAmount.equals=" + UPDATED_DISCOUNTS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByDiscountsAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where discountsAmount in DEFAULT_DISCOUNTS_AMOUNT or UPDATED_DISCOUNTS_AMOUNT
        defaultInvoicesShouldBeFound("discountsAmount.in=" + DEFAULT_DISCOUNTS_AMOUNT + "," + UPDATED_DISCOUNTS_AMOUNT);

        // Get all the invoicesList where discountsAmount equals to UPDATED_DISCOUNTS_AMOUNT
        defaultInvoicesShouldNotBeFound("discountsAmount.in=" + UPDATED_DISCOUNTS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByDiscountsAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where discountsAmount is not null
        defaultInvoicesShouldBeFound("discountsAmount.specified=true");

        // Get all the invoicesList where discountsAmount is null
        defaultInvoicesShouldNotBeFound("discountsAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByDiscountsAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where discountsAmount is greater than or equal to DEFAULT_DISCOUNTS_AMOUNT
        defaultInvoicesShouldBeFound("discountsAmount.greaterThanOrEqual=" + DEFAULT_DISCOUNTS_AMOUNT);

        // Get all the invoicesList where discountsAmount is greater than or equal to UPDATED_DISCOUNTS_AMOUNT
        defaultInvoicesShouldNotBeFound("discountsAmount.greaterThanOrEqual=" + UPDATED_DISCOUNTS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByDiscountsAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where discountsAmount is less than or equal to DEFAULT_DISCOUNTS_AMOUNT
        defaultInvoicesShouldBeFound("discountsAmount.lessThanOrEqual=" + DEFAULT_DISCOUNTS_AMOUNT);

        // Get all the invoicesList where discountsAmount is less than or equal to SMALLER_DISCOUNTS_AMOUNT
        defaultInvoicesShouldNotBeFound("discountsAmount.lessThanOrEqual=" + SMALLER_DISCOUNTS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByDiscountsAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where discountsAmount is less than DEFAULT_DISCOUNTS_AMOUNT
        defaultInvoicesShouldNotBeFound("discountsAmount.lessThan=" + DEFAULT_DISCOUNTS_AMOUNT);

        // Get all the invoicesList where discountsAmount is less than UPDATED_DISCOUNTS_AMOUNT
        defaultInvoicesShouldBeFound("discountsAmount.lessThan=" + UPDATED_DISCOUNTS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByDiscountsAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where discountsAmount is greater than DEFAULT_DISCOUNTS_AMOUNT
        defaultInvoicesShouldNotBeFound("discountsAmount.greaterThan=" + DEFAULT_DISCOUNTS_AMOUNT);

        // Get all the invoicesList where discountsAmount is greater than SMALLER_DISCOUNTS_AMOUNT
        defaultInvoicesShouldBeFound("discountsAmount.greaterThan=" + SMALLER_DISCOUNTS_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByAddOnAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where addOnAmount equals to DEFAULT_ADD_ON_AMOUNT
        defaultInvoicesShouldBeFound("addOnAmount.equals=" + DEFAULT_ADD_ON_AMOUNT);

        // Get all the invoicesList where addOnAmount equals to UPDATED_ADD_ON_AMOUNT
        defaultInvoicesShouldNotBeFound("addOnAmount.equals=" + UPDATED_ADD_ON_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByAddOnAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where addOnAmount in DEFAULT_ADD_ON_AMOUNT or UPDATED_ADD_ON_AMOUNT
        defaultInvoicesShouldBeFound("addOnAmount.in=" + DEFAULT_ADD_ON_AMOUNT + "," + UPDATED_ADD_ON_AMOUNT);

        // Get all the invoicesList where addOnAmount equals to UPDATED_ADD_ON_AMOUNT
        defaultInvoicesShouldNotBeFound("addOnAmount.in=" + UPDATED_ADD_ON_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByAddOnAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where addOnAmount is not null
        defaultInvoicesShouldBeFound("addOnAmount.specified=true");

        // Get all the invoicesList where addOnAmount is null
        defaultInvoicesShouldNotBeFound("addOnAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByAddOnAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where addOnAmount is greater than or equal to DEFAULT_ADD_ON_AMOUNT
        defaultInvoicesShouldBeFound("addOnAmount.greaterThanOrEqual=" + DEFAULT_ADD_ON_AMOUNT);

        // Get all the invoicesList where addOnAmount is greater than or equal to UPDATED_ADD_ON_AMOUNT
        defaultInvoicesShouldNotBeFound("addOnAmount.greaterThanOrEqual=" + UPDATED_ADD_ON_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByAddOnAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where addOnAmount is less than or equal to DEFAULT_ADD_ON_AMOUNT
        defaultInvoicesShouldBeFound("addOnAmount.lessThanOrEqual=" + DEFAULT_ADD_ON_AMOUNT);

        // Get all the invoicesList where addOnAmount is less than or equal to SMALLER_ADD_ON_AMOUNT
        defaultInvoicesShouldNotBeFound("addOnAmount.lessThanOrEqual=" + SMALLER_ADD_ON_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByAddOnAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where addOnAmount is less than DEFAULT_ADD_ON_AMOUNT
        defaultInvoicesShouldNotBeFound("addOnAmount.lessThan=" + DEFAULT_ADD_ON_AMOUNT);

        // Get all the invoicesList where addOnAmount is less than UPDATED_ADD_ON_AMOUNT
        defaultInvoicesShouldBeFound("addOnAmount.lessThan=" + UPDATED_ADD_ON_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByAddOnAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where addOnAmount is greater than DEFAULT_ADD_ON_AMOUNT
        defaultInvoicesShouldNotBeFound("addOnAmount.greaterThan=" + DEFAULT_ADD_ON_AMOUNT);

        // Get all the invoicesList where addOnAmount is greater than SMALLER_ADD_ON_AMOUNT
        defaultInvoicesShouldBeFound("addOnAmount.greaterThan=" + SMALLER_ADD_ON_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoicesByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where message equals to DEFAULT_MESSAGE
        defaultInvoicesShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the invoicesList where message equals to UPDATED_MESSAGE
        defaultInvoicesShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllInvoicesByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultInvoicesShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the invoicesList where message equals to UPDATED_MESSAGE
        defaultInvoicesShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllInvoicesByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where message is not null
        defaultInvoicesShouldBeFound("message.specified=true");

        // Get all the invoicesList where message is null
        defaultInvoicesShouldNotBeFound("message.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByMessageContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where message contains DEFAULT_MESSAGE
        defaultInvoicesShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the invoicesList where message contains UPDATED_MESSAGE
        defaultInvoicesShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllInvoicesByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where message does not contain DEFAULT_MESSAGE
        defaultInvoicesShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the invoicesList where message does not contain UPDATED_MESSAGE
        defaultInvoicesShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllInvoicesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where createdDate equals to DEFAULT_CREATED_DATE
        defaultInvoicesShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the invoicesList where createdDate equals to UPDATED_CREATED_DATE
        defaultInvoicesShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultInvoicesShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the invoicesList where createdDate equals to UPDATED_CREATED_DATE
        defaultInvoicesShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where createdDate is not null
        defaultInvoicesShouldBeFound("createdDate.specified=true");

        // Get all the invoicesList where createdDate is null
        defaultInvoicesShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoicesByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultInvoicesShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the invoicesList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultInvoicesShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultInvoicesShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the invoicesList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultInvoicesShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where createdDate is less than DEFAULT_CREATED_DATE
        defaultInvoicesShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the invoicesList where createdDate is less than UPDATED_CREATED_DATE
        defaultInvoicesShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        // Get all the invoicesList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultInvoicesShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the invoicesList where createdDate is greater than SMALLER_CREATED_DATE
        defaultInvoicesShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoicesByPaymentIsEqualToSomething() throws Exception {
        Payments payment;
        if (TestUtil.findAll(em, Payments.class).isEmpty()) {
            invoicesRepository.saveAndFlush(invoices);
            payment = PaymentsResourceIT.createEntity(em);
        } else {
            payment = TestUtil.findAll(em, Payments.class).get(0);
        }
        em.persist(payment);
        em.flush();
        invoices.addPayment(payment);
        invoicesRepository.saveAndFlush(invoices);
        Long paymentId = payment.getId();

        // Get all the invoicesList where payment equals to paymentId
        defaultInvoicesShouldBeFound("paymentId.equals=" + paymentId);

        // Get all the invoicesList where payment equals to (paymentId + 1)
        defaultInvoicesShouldNotBeFound("paymentId.equals=" + (paymentId + 1));
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceTransactionsIsEqualToSomething() throws Exception {
        InvoiceTransactions invoiceTransactions;
        if (TestUtil.findAll(em, InvoiceTransactions.class).isEmpty()) {
            invoicesRepository.saveAndFlush(invoices);
            invoiceTransactions = InvoiceTransactionsResourceIT.createEntity(em);
        } else {
            invoiceTransactions = TestUtil.findAll(em, InvoiceTransactions.class).get(0);
        }
        em.persist(invoiceTransactions);
        em.flush();
        invoices.addInvoiceTransactions(invoiceTransactions);
        invoicesRepository.saveAndFlush(invoices);
        Long invoiceTransactionsId = invoiceTransactions.getId();

        // Get all the invoicesList where invoiceTransactions equals to invoiceTransactionsId
        defaultInvoicesShouldBeFound("invoiceTransactionsId.equals=" + invoiceTransactionsId);

        // Get all the invoicesList where invoiceTransactions equals to (invoiceTransactionsId + 1)
        defaultInvoicesShouldNotBeFound("invoiceTransactionsId.equals=" + (invoiceTransactionsId + 1));
    }

    @Test
    @Transactional
    void getAllInvoicesByInvoiceDetailsIsEqualToSomething() throws Exception {
        InvoiceDetails invoiceDetails;
        if (TestUtil.findAll(em, InvoiceDetails.class).isEmpty()) {
            invoicesRepository.saveAndFlush(invoices);
            invoiceDetails = InvoiceDetailsResourceIT.createEntity(em);
        } else {
            invoiceDetails = TestUtil.findAll(em, InvoiceDetails.class).get(0);
        }
        em.persist(invoiceDetails);
        em.flush();
        invoices.addInvoiceDetails(invoiceDetails);
        invoicesRepository.saveAndFlush(invoices);
        Long invoiceDetailsId = invoiceDetails.getId();

        // Get all the invoicesList where invoiceDetails equals to invoiceDetailsId
        defaultInvoicesShouldBeFound("invoiceDetailsId.equals=" + invoiceDetailsId);

        // Get all the invoicesList where invoiceDetails equals to (invoiceDetailsId + 1)
        defaultInvoicesShouldNotBeFound("invoiceDetailsId.equals=" + (invoiceDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoicesShouldBeFound(String filter) throws Exception {
        restInvoicesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoices.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].contactAddressId").value(hasItem(DEFAULT_CONTACT_ADDRESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].contactBillingAdrId").value(hasItem(DEFAULT_CONTACT_BILLING_ADR_ID.intValue())))
            .andExpect(jsonPath("$.[*].cartId").value(hasItem(DEFAULT_CART_ID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastTranactionId").value(hasItem(DEFAULT_LAST_TRANACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].totalProfit").value(hasItem(DEFAULT_TOTAL_PROFIT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalTaxes").value(hasItem(DEFAULT_TOTAL_TAXES.doubleValue())))
            .andExpect(jsonPath("$.[*].fedaralTaxesAmount").value(hasItem(DEFAULT_FEDARAL_TAXES_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].provintionalTaxesAmount").value(hasItem(DEFAULT_PROVINTIONAL_TAXES_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].discountsAmount").value(hasItem(DEFAULT_DISCOUNTS_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].addOnAmount").value(hasItem(DEFAULT_ADD_ON_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restInvoicesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoicesShouldNotBeFound(String filter) throws Exception {
        restInvoicesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoicesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInvoices() throws Exception {
        // Get the invoices
        restInvoicesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // Update the invoices
        Invoices updatedInvoices = invoicesRepository.findById(invoices.getId()).get();
        // Disconnect from session so that the updates on updatedInvoices are not directly saved in db
        em.detach(updatedInvoices);
        updatedInvoices
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .contactId(UPDATED_CONTACT_ID)
            .contactAddressId(UPDATED_CONTACT_ADDRESS_ID)
            .contactBillingAdrId(UPDATED_CONTACT_BILLING_ADR_ID)
            .cartId(UPDATED_CART_ID)
            .type(UPDATED_TYPE)
            .requestDate(UPDATED_REQUEST_DATE)
            .contactName(UPDATED_CONTACT_NAME)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .lastTranactionId(UPDATED_LAST_TRANACTION_ID)
            .totalCost(UPDATED_TOTAL_COST)
            .totalProfit(UPDATED_TOTAL_PROFIT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalTaxes(UPDATED_TOTAL_TAXES)
            .fedaralTaxesAmount(UPDATED_FEDARAL_TAXES_AMOUNT)
            .provintionalTaxesAmount(UPDATED_PROVINTIONAL_TAXES_AMOUNT)
            .discountsAmount(UPDATED_DISCOUNTS_AMOUNT)
            .addOnAmount(UPDATED_ADD_ON_AMOUNT)
            .message(UPDATED_MESSAGE)
            .createdDate(UPDATED_CREATED_DATE);
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(updatedInvoices);

        restInvoicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoicesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoicesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testInvoices.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testInvoices.getContactAddressId()).isEqualTo(UPDATED_CONTACT_ADDRESS_ID);
        assertThat(testInvoices.getContactBillingAdrId()).isEqualTo(UPDATED_CONTACT_BILLING_ADR_ID);
        assertThat(testInvoices.getCartId()).isEqualTo(UPDATED_CART_ID);
        assertThat(testInvoices.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInvoices.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testInvoices.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testInvoices.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoices.getLastTranactionId()).isEqualTo(UPDATED_LAST_TRANACTION_ID);
        assertThat(testInvoices.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testInvoices.getTotalProfit()).isEqualTo(UPDATED_TOTAL_PROFIT);
        assertThat(testInvoices.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testInvoices.getTotalTaxes()).isEqualTo(UPDATED_TOTAL_TAXES);
        assertThat(testInvoices.getFedaralTaxesAmount()).isEqualTo(UPDATED_FEDARAL_TAXES_AMOUNT);
        assertThat(testInvoices.getProvintionalTaxesAmount()).isEqualTo(UPDATED_PROVINTIONAL_TAXES_AMOUNT);
        assertThat(testInvoices.getDiscountsAmount()).isEqualTo(UPDATED_DISCOUNTS_AMOUNT);
        assertThat(testInvoices.getAddOnAmount()).isEqualTo(UPDATED_ADD_ON_AMOUNT);
        assertThat(testInvoices.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testInvoices.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingInvoices() throws Exception {
        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();
        invoices.setId(count.incrementAndGet());

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoicesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInvoices() throws Exception {
        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();
        invoices.setId(count.incrementAndGet());

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoicesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInvoices() throws Exception {
        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();
        invoices.setId(count.incrementAndGet());

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoicesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoicesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInvoicesWithPatch() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // Update the invoices using partial update
        Invoices partialUpdatedInvoices = new Invoices();
        partialUpdatedInvoices.setId(invoices.getId());

        partialUpdatedInvoices
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .contactId(UPDATED_CONTACT_ID)
            .contactBillingAdrId(UPDATED_CONTACT_BILLING_ADR_ID)
            .cartId(UPDATED_CART_ID)
            .type(UPDATED_TYPE)
            .contactName(UPDATED_CONTACT_NAME)
            .lastTranactionId(UPDATED_LAST_TRANACTION_ID)
            .totalCost(UPDATED_TOTAL_COST)
            .totalProfit(UPDATED_TOTAL_PROFIT)
            .totalTaxes(UPDATED_TOTAL_TAXES)
            .fedaralTaxesAmount(UPDATED_FEDARAL_TAXES_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE);

        restInvoicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoices.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoices))
            )
            .andExpect(status().isOk());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testInvoices.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testInvoices.getContactAddressId()).isEqualTo(DEFAULT_CONTACT_ADDRESS_ID);
        assertThat(testInvoices.getContactBillingAdrId()).isEqualTo(UPDATED_CONTACT_BILLING_ADR_ID);
        assertThat(testInvoices.getCartId()).isEqualTo(UPDATED_CART_ID);
        assertThat(testInvoices.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInvoices.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testInvoices.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testInvoices.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoices.getLastTranactionId()).isEqualTo(UPDATED_LAST_TRANACTION_ID);
        assertThat(testInvoices.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testInvoices.getTotalProfit()).isEqualTo(UPDATED_TOTAL_PROFIT);
        assertThat(testInvoices.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testInvoices.getTotalTaxes()).isEqualTo(UPDATED_TOTAL_TAXES);
        assertThat(testInvoices.getFedaralTaxesAmount()).isEqualTo(UPDATED_FEDARAL_TAXES_AMOUNT);
        assertThat(testInvoices.getProvintionalTaxesAmount()).isEqualTo(DEFAULT_PROVINTIONAL_TAXES_AMOUNT);
        assertThat(testInvoices.getDiscountsAmount()).isEqualTo(DEFAULT_DISCOUNTS_AMOUNT);
        assertThat(testInvoices.getAddOnAmount()).isEqualTo(DEFAULT_ADD_ON_AMOUNT);
        assertThat(testInvoices.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testInvoices.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateInvoicesWithPatch() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();

        // Update the invoices using partial update
        Invoices partialUpdatedInvoices = new Invoices();
        partialUpdatedInvoices.setId(invoices.getId());

        partialUpdatedInvoices
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .contactId(UPDATED_CONTACT_ID)
            .contactAddressId(UPDATED_CONTACT_ADDRESS_ID)
            .contactBillingAdrId(UPDATED_CONTACT_BILLING_ADR_ID)
            .cartId(UPDATED_CART_ID)
            .type(UPDATED_TYPE)
            .requestDate(UPDATED_REQUEST_DATE)
            .contactName(UPDATED_CONTACT_NAME)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .lastTranactionId(UPDATED_LAST_TRANACTION_ID)
            .totalCost(UPDATED_TOTAL_COST)
            .totalProfit(UPDATED_TOTAL_PROFIT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .totalTaxes(UPDATED_TOTAL_TAXES)
            .fedaralTaxesAmount(UPDATED_FEDARAL_TAXES_AMOUNT)
            .provintionalTaxesAmount(UPDATED_PROVINTIONAL_TAXES_AMOUNT)
            .discountsAmount(UPDATED_DISCOUNTS_AMOUNT)
            .addOnAmount(UPDATED_ADD_ON_AMOUNT)
            .message(UPDATED_MESSAGE)
            .createdDate(UPDATED_CREATED_DATE);

        restInvoicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoices.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoices))
            )
            .andExpect(status().isOk());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
        Invoices testInvoices = invoicesList.get(invoicesList.size() - 1);
        assertThat(testInvoices.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testInvoices.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testInvoices.getContactAddressId()).isEqualTo(UPDATED_CONTACT_ADDRESS_ID);
        assertThat(testInvoices.getContactBillingAdrId()).isEqualTo(UPDATED_CONTACT_BILLING_ADR_ID);
        assertThat(testInvoices.getCartId()).isEqualTo(UPDATED_CART_ID);
        assertThat(testInvoices.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testInvoices.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testInvoices.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testInvoices.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoices.getLastTranactionId()).isEqualTo(UPDATED_LAST_TRANACTION_ID);
        assertThat(testInvoices.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testInvoices.getTotalProfit()).isEqualTo(UPDATED_TOTAL_PROFIT);
        assertThat(testInvoices.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testInvoices.getTotalTaxes()).isEqualTo(UPDATED_TOTAL_TAXES);
        assertThat(testInvoices.getFedaralTaxesAmount()).isEqualTo(UPDATED_FEDARAL_TAXES_AMOUNT);
        assertThat(testInvoices.getProvintionalTaxesAmount()).isEqualTo(UPDATED_PROVINTIONAL_TAXES_AMOUNT);
        assertThat(testInvoices.getDiscountsAmount()).isEqualTo(UPDATED_DISCOUNTS_AMOUNT);
        assertThat(testInvoices.getAddOnAmount()).isEqualTo(UPDATED_ADD_ON_AMOUNT);
        assertThat(testInvoices.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testInvoices.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingInvoices() throws Exception {
        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();
        invoices.setId(count.incrementAndGet());

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, invoicesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInvoices() throws Exception {
        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();
        invoices.setId(count.incrementAndGet());

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoicesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoicesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInvoices() throws Exception {
        int databaseSizeBeforeUpdate = invoicesRepository.findAll().size();
        invoices.setId(count.incrementAndGet());

        // Create the Invoices
        InvoicesDTO invoicesDTO = invoicesMapper.toDto(invoices);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoicesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(invoicesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Invoices in the database
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInvoices() throws Exception {
        // Initialize the database
        invoicesRepository.saveAndFlush(invoices);

        int databaseSizeBeforeDelete = invoicesRepository.findAll().size();

        // Delete the invoices
        restInvoicesMockMvc
            .perform(delete(ENTITY_API_URL_ID, invoices.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invoices> invoicesList = invoicesRepository.findAll();
        assertThat(invoicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
