package com.polarbears.capstone.hmsfinance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmsfinance.IntegrationTest;
import com.polarbears.capstone.hmsfinance.domain.InvoiceDetails;
import com.polarbears.capstone.hmsfinance.domain.Invoices;
import com.polarbears.capstone.hmsfinance.domain.SubItems;
import com.polarbears.capstone.hmsfinance.domain.enumeration.ITEMTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.PAYMENTTYPES;
import com.polarbears.capstone.hmsfinance.repository.InvoiceDetailsRepository;
import com.polarbears.capstone.hmsfinance.service.InvoiceDetailsService;
import com.polarbears.capstone.hmsfinance.service.criteria.InvoiceDetailsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.InvoiceDetailsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.InvoiceDetailsMapper;
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
 * Integration tests for the {@link InvoiceDetailsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InvoiceDetailsResourceIT {

    private static final Long DEFAULT_CONTACT_ID = 1L;
    private static final Long UPDATED_CONTACT_ID = 2L;
    private static final Long SMALLER_CONTACT_ID = 1L - 1L;

    private static final Long DEFAULT_CART_ID = 1L;
    private static final Long UPDATED_CART_ID = 2L;
    private static final Long SMALLER_CART_ID = 1L - 1L;

    private static final Long DEFAULT_ITEM_ID = 1L;
    private static final Long UPDATED_ITEM_ID = 2L;
    private static final Long SMALLER_ITEM_ID = 1L - 1L;

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

    private static final ITEMTYPES DEFAULT_ITEM_TYPE = ITEMTYPES.PRODUCT;
    private static final ITEMTYPES UPDATED_ITEM_TYPE = ITEMTYPES.SERVICE;

    private static final PAYMENTTYPES DEFAULT_PAYMENT_TYPE = PAYMENTTYPES.IN;
    private static final PAYMENTTYPES UPDATED_PAYMENT_TYPE = PAYMENTTYPES.OUT;

    private static final LocalDate DEFAULT_SUBSCRIPTION_STARTING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBSCRIPTION_STARTING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_SUBSCRIPTION_STARTING_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_SUBSCRIPTION_DURATION_WEEKS = 1;
    private static final Integer UPDATED_SUBSCRIPTION_DURATION_WEEKS = 2;
    private static final Integer SMALLER_SUBSCRIPTION_DURATION_WEEKS = 1 - 1;

    private static final Double DEFAULT_DETAIL_AMOUNT = 1D;
    private static final Double UPDATED_DETAIL_AMOUNT = 2D;
    private static final Double SMALLER_DETAIL_AMOUNT = 1D - 1D;

    private static final Integer DEFAULT_LINE_NUMBER = 1;
    private static final Integer UPDATED_LINE_NUMBER = 2;
    private static final Integer SMALLER_LINE_NUMBER = 1 - 1;

    private static final Long DEFAULT_NUTRITIONIST_ID = 1L;
    private static final Long UPDATED_NUTRITIONIST_ID = 2L;
    private static final Long SMALLER_NUTRITIONIST_ID = 1L - 1L;

    private static final Double DEFAULT_TOTAL_COST = 1D;
    private static final Double UPDATED_TOTAL_COST = 2D;
    private static final Double SMALLER_TOTAL_COST = 1D - 1D;

    private static final Double DEFAULT_TOTAL_PROFIT = 1D;
    private static final Double UPDATED_TOTAL_PROFIT = 2D;
    private static final Double SMALLER_TOTAL_PROFIT = 1D - 1D;

    private static final Double DEFAULT_NUTRITIONIST_EARNING = 1D;
    private static final Double UPDATED_NUTRITIONIST_EARNING = 2D;
    private static final Double SMALLER_NUTRITIONIST_EARNING = 1D - 1D;

    private static final Double DEFAULT_NUTRITIONIST_PERCENTAGE = 1D;
    private static final Double UPDATED_NUTRITIONIST_PERCENTAGE = 2D;
    private static final Double SMALLER_NUTRITIONIST_PERCENTAGE = 1D - 1D;

    private static final Double DEFAULT_FEDARAL_TAXES_AMOUNT = 1D;
    private static final Double UPDATED_FEDARAL_TAXES_AMOUNT = 2D;
    private static final Double SMALLER_FEDARAL_TAXES_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_FEDARAL_TAXES_PERCENTAGE = 1D;
    private static final Double UPDATED_FEDARAL_TAXES_PERCENTAGE = 2D;
    private static final Double SMALLER_FEDARAL_TAXES_PERCENTAGE = 1D - 1D;

    private static final Double DEFAULT_PROVINTIONAL_TAXES_AMOUNT = 1D;
    private static final Double UPDATED_PROVINTIONAL_TAXES_AMOUNT = 2D;
    private static final Double SMALLER_PROVINTIONAL_TAXES_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE = 1D;
    private static final Double UPDATED_PROVINTIONAL_TAXES_PERCENTAGE = 2D;
    private static final Double SMALLER_PROVINTIONAL_TAXES_PERCENTAGE = 1D - 1D;

    private static final Double DEFAULT_TOTAL_TAXES_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_TAXES_AMOUNT = 2D;
    private static final Double SMALLER_TOTAL_TAXES_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_TOTAL_TAXES_PERCENTAGE = 1D;
    private static final Double UPDATED_TOTAL_TAXES_PERCENTAGE = 2D;
    private static final Double SMALLER_TOTAL_TAXES_PERCENTAGE = 1D - 1D;

    private static final Double DEFAULT_DISCOUNT_AMOUNT = 1D;
    private static final Double UPDATED_DISCOUNT_AMOUNT = 2D;
    private static final Double SMALLER_DISCOUNT_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_DISCOUNT_PERCENTAGE = 1D;
    private static final Double UPDATED_DISCOUNT_PERCENTAGE = 2D;
    private static final Double SMALLER_DISCOUNT_PERCENTAGE = 1D - 1D;

    private static final String DEFAULT_ADD_ON_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ADD_ON_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_ADD_ON_AMOUNT = 1D;
    private static final Double UPDATED_ADD_ON_AMOUNT = 2D;
    private static final Double SMALLER_ADD_ON_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_ADD_ON_PERCENTAGE = 1D;
    private static final Double UPDATED_ADD_ON_PERCENTAGE = 2D;
    private static final Double SMALLER_ADD_ON_PERCENTAGE = 1D - 1D;

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;
    private static final Double SMALLER_TOTAL_AMOUNT = 1D - 1D;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/invoice-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;

    @Mock
    private InvoiceDetailsRepository invoiceDetailsRepositoryMock;

    @Autowired
    private InvoiceDetailsMapper invoiceDetailsMapper;

    @Mock
    private InvoiceDetailsService invoiceDetailsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceDetailsMockMvc;

    private InvoiceDetails invoiceDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceDetails createEntity(EntityManager em) {
        InvoiceDetails invoiceDetails = new InvoiceDetails()
            .contactId(DEFAULT_CONTACT_ID)
            .cartId(DEFAULT_CART_ID)
            .itemId(DEFAULT_ITEM_ID)
            .itemName(DEFAULT_ITEM_NAME)
            .itemCode(DEFAULT_ITEM_CODE)
            .itemType(DEFAULT_ITEM_TYPE)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .subscriptionStartingDate(DEFAULT_SUBSCRIPTION_STARTING_DATE)
            .subscriptionDurationWeeks(DEFAULT_SUBSCRIPTION_DURATION_WEEKS)
            .detailAmount(DEFAULT_DETAIL_AMOUNT)
            .lineNumber(DEFAULT_LINE_NUMBER)
            .nutritionistId(DEFAULT_NUTRITIONIST_ID)
            .totalCost(DEFAULT_TOTAL_COST)
            .totalProfit(DEFAULT_TOTAL_PROFIT)
            .nutritionistEarning(DEFAULT_NUTRITIONIST_EARNING)
            .nutritionistPercentage(DEFAULT_NUTRITIONIST_PERCENTAGE)
            .fedaralTaxesAmount(DEFAULT_FEDARAL_TAXES_AMOUNT)
            .fedaralTaxesPercentage(DEFAULT_FEDARAL_TAXES_PERCENTAGE)
            .provintionalTaxesAmount(DEFAULT_PROVINTIONAL_TAXES_AMOUNT)
            .provintionalTaxesPercentage(DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE)
            .totalTaxesAmount(DEFAULT_TOTAL_TAXES_AMOUNT)
            .totalTaxesPercentage(DEFAULT_TOTAL_TAXES_PERCENTAGE)
            .discountAmount(DEFAULT_DISCOUNT_AMOUNT)
            .discountPercentage(DEFAULT_DISCOUNT_PERCENTAGE)
            .addOnCode(DEFAULT_ADD_ON_CODE)
            .addOnAmount(DEFAULT_ADD_ON_AMOUNT)
            .addOnPercentage(DEFAULT_ADD_ON_PERCENTAGE)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .createdDate(DEFAULT_CREATED_DATE);
        return invoiceDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceDetails createUpdatedEntity(EntityManager em) {
        InvoiceDetails invoiceDetails = new InvoiceDetails()
            .contactId(UPDATED_CONTACT_ID)
            .cartId(UPDATED_CART_ID)
            .itemId(UPDATED_ITEM_ID)
            .itemName(UPDATED_ITEM_NAME)
            .itemCode(UPDATED_ITEM_CODE)
            .itemType(UPDATED_ITEM_TYPE)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .subscriptionStartingDate(UPDATED_SUBSCRIPTION_STARTING_DATE)
            .subscriptionDurationWeeks(UPDATED_SUBSCRIPTION_DURATION_WEEKS)
            .detailAmount(UPDATED_DETAIL_AMOUNT)
            .lineNumber(UPDATED_LINE_NUMBER)
            .nutritionistId(UPDATED_NUTRITIONIST_ID)
            .totalCost(UPDATED_TOTAL_COST)
            .totalProfit(UPDATED_TOTAL_PROFIT)
            .nutritionistEarning(UPDATED_NUTRITIONIST_EARNING)
            .nutritionistPercentage(UPDATED_NUTRITIONIST_PERCENTAGE)
            .fedaralTaxesAmount(UPDATED_FEDARAL_TAXES_AMOUNT)
            .fedaralTaxesPercentage(UPDATED_FEDARAL_TAXES_PERCENTAGE)
            .provintionalTaxesAmount(UPDATED_PROVINTIONAL_TAXES_AMOUNT)
            .provintionalTaxesPercentage(UPDATED_PROVINTIONAL_TAXES_PERCENTAGE)
            .totalTaxesAmount(UPDATED_TOTAL_TAXES_AMOUNT)
            .totalTaxesPercentage(UPDATED_TOTAL_TAXES_PERCENTAGE)
            .discountAmount(UPDATED_DISCOUNT_AMOUNT)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .addOnCode(UPDATED_ADD_ON_CODE)
            .addOnAmount(UPDATED_ADD_ON_AMOUNT)
            .addOnPercentage(UPDATED_ADD_ON_PERCENTAGE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE);
        return invoiceDetails;
    }

    @BeforeEach
    public void initTest() {
        invoiceDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createInvoiceDetails() throws Exception {
        int databaseSizeBeforeCreate = invoiceDetailsRepository.findAll().size();
        // Create the InvoiceDetails
        InvoiceDetailsDTO invoiceDetailsDTO = invoiceDetailsMapper.toDto(invoiceDetails);
        restInvoiceDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoiceDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InvoiceDetails in the database
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAll();
        assertThat(invoiceDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceDetails testInvoiceDetails = invoiceDetailsList.get(invoiceDetailsList.size() - 1);
        assertThat(testInvoiceDetails.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testInvoiceDetails.getCartId()).isEqualTo(DEFAULT_CART_ID);
        assertThat(testInvoiceDetails.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testInvoiceDetails.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testInvoiceDetails.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testInvoiceDetails.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testInvoiceDetails.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testInvoiceDetails.getSubscriptionStartingDate()).isEqualTo(DEFAULT_SUBSCRIPTION_STARTING_DATE);
        assertThat(testInvoiceDetails.getSubscriptionDurationWeeks()).isEqualTo(DEFAULT_SUBSCRIPTION_DURATION_WEEKS);
        assertThat(testInvoiceDetails.getDetailAmount()).isEqualTo(DEFAULT_DETAIL_AMOUNT);
        assertThat(testInvoiceDetails.getLineNumber()).isEqualTo(DEFAULT_LINE_NUMBER);
        assertThat(testInvoiceDetails.getNutritionistId()).isEqualTo(DEFAULT_NUTRITIONIST_ID);
        assertThat(testInvoiceDetails.getTotalCost()).isEqualTo(DEFAULT_TOTAL_COST);
        assertThat(testInvoiceDetails.getTotalProfit()).isEqualTo(DEFAULT_TOTAL_PROFIT);
        assertThat(testInvoiceDetails.getNutritionistEarning()).isEqualTo(DEFAULT_NUTRITIONIST_EARNING);
        assertThat(testInvoiceDetails.getNutritionistPercentage()).isEqualTo(DEFAULT_NUTRITIONIST_PERCENTAGE);
        assertThat(testInvoiceDetails.getFedaralTaxesAmount()).isEqualTo(DEFAULT_FEDARAL_TAXES_AMOUNT);
        assertThat(testInvoiceDetails.getFedaralTaxesPercentage()).isEqualTo(DEFAULT_FEDARAL_TAXES_PERCENTAGE);
        assertThat(testInvoiceDetails.getProvintionalTaxesAmount()).isEqualTo(DEFAULT_PROVINTIONAL_TAXES_AMOUNT);
        assertThat(testInvoiceDetails.getProvintionalTaxesPercentage()).isEqualTo(DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE);
        assertThat(testInvoiceDetails.getTotalTaxesAmount()).isEqualTo(DEFAULT_TOTAL_TAXES_AMOUNT);
        assertThat(testInvoiceDetails.getTotalTaxesPercentage()).isEqualTo(DEFAULT_TOTAL_TAXES_PERCENTAGE);
        assertThat(testInvoiceDetails.getDiscountAmount()).isEqualTo(DEFAULT_DISCOUNT_AMOUNT);
        assertThat(testInvoiceDetails.getDiscountPercentage()).isEqualTo(DEFAULT_DISCOUNT_PERCENTAGE);
        assertThat(testInvoiceDetails.getAddOnCode()).isEqualTo(DEFAULT_ADD_ON_CODE);
        assertThat(testInvoiceDetails.getAddOnAmount()).isEqualTo(DEFAULT_ADD_ON_AMOUNT);
        assertThat(testInvoiceDetails.getAddOnPercentage()).isEqualTo(DEFAULT_ADD_ON_PERCENTAGE);
        assertThat(testInvoiceDetails.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testInvoiceDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createInvoiceDetailsWithExistingId() throws Exception {
        // Create the InvoiceDetails with an existing ID
        invoiceDetails.setId(1L);
        InvoiceDetailsDTO invoiceDetailsDTO = invoiceDetailsMapper.toDto(invoiceDetails);

        int databaseSizeBeforeCreate = invoiceDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoiceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceDetails in the database
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAll();
        assertThat(invoiceDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInvoiceDetails() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList
        restInvoiceDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].cartId").value(hasItem(DEFAULT_CART_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].subscriptionStartingDate").value(hasItem(DEFAULT_SUBSCRIPTION_STARTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].subscriptionDurationWeeks").value(hasItem(DEFAULT_SUBSCRIPTION_DURATION_WEEKS)))
            .andExpect(jsonPath("$.[*].detailAmount").value(hasItem(DEFAULT_DETAIL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].nutritionistId").value(hasItem(DEFAULT_NUTRITIONIST_ID.intValue())))
            .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].totalProfit").value(hasItem(DEFAULT_TOTAL_PROFIT.doubleValue())))
            .andExpect(jsonPath("$.[*].nutritionistEarning").value(hasItem(DEFAULT_NUTRITIONIST_EARNING.doubleValue())))
            .andExpect(jsonPath("$.[*].nutritionistPercentage").value(hasItem(DEFAULT_NUTRITIONIST_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fedaralTaxesAmount").value(hasItem(DEFAULT_FEDARAL_TAXES_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].fedaralTaxesPercentage").value(hasItem(DEFAULT_FEDARAL_TAXES_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].provintionalTaxesAmount").value(hasItem(DEFAULT_PROVINTIONAL_TAXES_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].provintionalTaxesPercentage").value(hasItem(DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalTaxesAmount").value(hasItem(DEFAULT_TOTAL_TAXES_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalTaxesPercentage").value(hasItem(DEFAULT_TOTAL_TAXES_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].discountAmount").value(hasItem(DEFAULT_DISCOUNT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].discountPercentage").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].addOnCode").value(hasItem(DEFAULT_ADD_ON_CODE)))
            .andExpect(jsonPath("$.[*].addOnAmount").value(hasItem(DEFAULT_ADD_ON_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].addOnPercentage").value(hasItem(DEFAULT_ADD_ON_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInvoiceDetailsWithEagerRelationshipsIsEnabled() throws Exception {
        when(invoiceDetailsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInvoiceDetailsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(invoiceDetailsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInvoiceDetailsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(invoiceDetailsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInvoiceDetailsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(invoiceDetailsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInvoiceDetails() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get the invoiceDetails
        restInvoiceDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, invoiceDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceDetails.getId().intValue()))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID.intValue()))
            .andExpect(jsonPath("$.cartId").value(DEFAULT_CART_ID.intValue()))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME))
            .andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE.toString()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.subscriptionStartingDate").value(DEFAULT_SUBSCRIPTION_STARTING_DATE.toString()))
            .andExpect(jsonPath("$.subscriptionDurationWeeks").value(DEFAULT_SUBSCRIPTION_DURATION_WEEKS))
            .andExpect(jsonPath("$.detailAmount").value(DEFAULT_DETAIL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.lineNumber").value(DEFAULT_LINE_NUMBER))
            .andExpect(jsonPath("$.nutritionistId").value(DEFAULT_NUTRITIONIST_ID.intValue()))
            .andExpect(jsonPath("$.totalCost").value(DEFAULT_TOTAL_COST.doubleValue()))
            .andExpect(jsonPath("$.totalProfit").value(DEFAULT_TOTAL_PROFIT.doubleValue()))
            .andExpect(jsonPath("$.nutritionistEarning").value(DEFAULT_NUTRITIONIST_EARNING.doubleValue()))
            .andExpect(jsonPath("$.nutritionistPercentage").value(DEFAULT_NUTRITIONIST_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.fedaralTaxesAmount").value(DEFAULT_FEDARAL_TAXES_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.fedaralTaxesPercentage").value(DEFAULT_FEDARAL_TAXES_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.provintionalTaxesAmount").value(DEFAULT_PROVINTIONAL_TAXES_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.provintionalTaxesPercentage").value(DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.totalTaxesAmount").value(DEFAULT_TOTAL_TAXES_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalTaxesPercentage").value(DEFAULT_TOTAL_TAXES_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.discountAmount").value(DEFAULT_DISCOUNT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.discountPercentage").value(DEFAULT_DISCOUNT_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.addOnCode").value(DEFAULT_ADD_ON_CODE))
            .andExpect(jsonPath("$.addOnAmount").value(DEFAULT_ADD_ON_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.addOnPercentage").value(DEFAULT_ADD_ON_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getInvoiceDetailsByIdFiltering() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        Long id = invoiceDetails.getId();

        defaultInvoiceDetailsShouldBeFound("id.equals=" + id);
        defaultInvoiceDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultInvoiceDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvoiceDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultInvoiceDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvoiceDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByContactIdIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where contactId equals to DEFAULT_CONTACT_ID
        defaultInvoiceDetailsShouldBeFound("contactId.equals=" + DEFAULT_CONTACT_ID);

        // Get all the invoiceDetailsList where contactId equals to UPDATED_CONTACT_ID
        defaultInvoiceDetailsShouldNotBeFound("contactId.equals=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByContactIdIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where contactId in DEFAULT_CONTACT_ID or UPDATED_CONTACT_ID
        defaultInvoiceDetailsShouldBeFound("contactId.in=" + DEFAULT_CONTACT_ID + "," + UPDATED_CONTACT_ID);

        // Get all the invoiceDetailsList where contactId equals to UPDATED_CONTACT_ID
        defaultInvoiceDetailsShouldNotBeFound("contactId.in=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByContactIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where contactId is not null
        defaultInvoiceDetailsShouldBeFound("contactId.specified=true");

        // Get all the invoiceDetailsList where contactId is null
        defaultInvoiceDetailsShouldNotBeFound("contactId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByContactIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where contactId is greater than or equal to DEFAULT_CONTACT_ID
        defaultInvoiceDetailsShouldBeFound("contactId.greaterThanOrEqual=" + DEFAULT_CONTACT_ID);

        // Get all the invoiceDetailsList where contactId is greater than or equal to UPDATED_CONTACT_ID
        defaultInvoiceDetailsShouldNotBeFound("contactId.greaterThanOrEqual=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByContactIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where contactId is less than or equal to DEFAULT_CONTACT_ID
        defaultInvoiceDetailsShouldBeFound("contactId.lessThanOrEqual=" + DEFAULT_CONTACT_ID);

        // Get all the invoiceDetailsList where contactId is less than or equal to SMALLER_CONTACT_ID
        defaultInvoiceDetailsShouldNotBeFound("contactId.lessThanOrEqual=" + SMALLER_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByContactIdIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where contactId is less than DEFAULT_CONTACT_ID
        defaultInvoiceDetailsShouldNotBeFound("contactId.lessThan=" + DEFAULT_CONTACT_ID);

        // Get all the invoiceDetailsList where contactId is less than UPDATED_CONTACT_ID
        defaultInvoiceDetailsShouldBeFound("contactId.lessThan=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByContactIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where contactId is greater than DEFAULT_CONTACT_ID
        defaultInvoiceDetailsShouldNotBeFound("contactId.greaterThan=" + DEFAULT_CONTACT_ID);

        // Get all the invoiceDetailsList where contactId is greater than SMALLER_CONTACT_ID
        defaultInvoiceDetailsShouldBeFound("contactId.greaterThan=" + SMALLER_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCartIdIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where cartId equals to DEFAULT_CART_ID
        defaultInvoiceDetailsShouldBeFound("cartId.equals=" + DEFAULT_CART_ID);

        // Get all the invoiceDetailsList where cartId equals to UPDATED_CART_ID
        defaultInvoiceDetailsShouldNotBeFound("cartId.equals=" + UPDATED_CART_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCartIdIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where cartId in DEFAULT_CART_ID or UPDATED_CART_ID
        defaultInvoiceDetailsShouldBeFound("cartId.in=" + DEFAULT_CART_ID + "," + UPDATED_CART_ID);

        // Get all the invoiceDetailsList where cartId equals to UPDATED_CART_ID
        defaultInvoiceDetailsShouldNotBeFound("cartId.in=" + UPDATED_CART_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCartIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where cartId is not null
        defaultInvoiceDetailsShouldBeFound("cartId.specified=true");

        // Get all the invoiceDetailsList where cartId is null
        defaultInvoiceDetailsShouldNotBeFound("cartId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCartIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where cartId is greater than or equal to DEFAULT_CART_ID
        defaultInvoiceDetailsShouldBeFound("cartId.greaterThanOrEqual=" + DEFAULT_CART_ID);

        // Get all the invoiceDetailsList where cartId is greater than or equal to UPDATED_CART_ID
        defaultInvoiceDetailsShouldNotBeFound("cartId.greaterThanOrEqual=" + UPDATED_CART_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCartIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where cartId is less than or equal to DEFAULT_CART_ID
        defaultInvoiceDetailsShouldBeFound("cartId.lessThanOrEqual=" + DEFAULT_CART_ID);

        // Get all the invoiceDetailsList where cartId is less than or equal to SMALLER_CART_ID
        defaultInvoiceDetailsShouldNotBeFound("cartId.lessThanOrEqual=" + SMALLER_CART_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCartIdIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where cartId is less than DEFAULT_CART_ID
        defaultInvoiceDetailsShouldNotBeFound("cartId.lessThan=" + DEFAULT_CART_ID);

        // Get all the invoiceDetailsList where cartId is less than UPDATED_CART_ID
        defaultInvoiceDetailsShouldBeFound("cartId.lessThan=" + UPDATED_CART_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCartIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where cartId is greater than DEFAULT_CART_ID
        defaultInvoiceDetailsShouldNotBeFound("cartId.greaterThan=" + DEFAULT_CART_ID);

        // Get all the invoiceDetailsList where cartId is greater than SMALLER_CART_ID
        defaultInvoiceDetailsShouldBeFound("cartId.greaterThan=" + SMALLER_CART_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemId equals to DEFAULT_ITEM_ID
        defaultInvoiceDetailsShouldBeFound("itemId.equals=" + DEFAULT_ITEM_ID);

        // Get all the invoiceDetailsList where itemId equals to UPDATED_ITEM_ID
        defaultInvoiceDetailsShouldNotBeFound("itemId.equals=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemIdIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemId in DEFAULT_ITEM_ID or UPDATED_ITEM_ID
        defaultInvoiceDetailsShouldBeFound("itemId.in=" + DEFAULT_ITEM_ID + "," + UPDATED_ITEM_ID);

        // Get all the invoiceDetailsList where itemId equals to UPDATED_ITEM_ID
        defaultInvoiceDetailsShouldNotBeFound("itemId.in=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemId is not null
        defaultInvoiceDetailsShouldBeFound("itemId.specified=true");

        // Get all the invoiceDetailsList where itemId is null
        defaultInvoiceDetailsShouldNotBeFound("itemId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemId is greater than or equal to DEFAULT_ITEM_ID
        defaultInvoiceDetailsShouldBeFound("itemId.greaterThanOrEqual=" + DEFAULT_ITEM_ID);

        // Get all the invoiceDetailsList where itemId is greater than or equal to UPDATED_ITEM_ID
        defaultInvoiceDetailsShouldNotBeFound("itemId.greaterThanOrEqual=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemId is less than or equal to DEFAULT_ITEM_ID
        defaultInvoiceDetailsShouldBeFound("itemId.lessThanOrEqual=" + DEFAULT_ITEM_ID);

        // Get all the invoiceDetailsList where itemId is less than or equal to SMALLER_ITEM_ID
        defaultInvoiceDetailsShouldNotBeFound("itemId.lessThanOrEqual=" + SMALLER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemId is less than DEFAULT_ITEM_ID
        defaultInvoiceDetailsShouldNotBeFound("itemId.lessThan=" + DEFAULT_ITEM_ID);

        // Get all the invoiceDetailsList where itemId is less than UPDATED_ITEM_ID
        defaultInvoiceDetailsShouldBeFound("itemId.lessThan=" + UPDATED_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemId is greater than DEFAULT_ITEM_ID
        defaultInvoiceDetailsShouldNotBeFound("itemId.greaterThan=" + DEFAULT_ITEM_ID);

        // Get all the invoiceDetailsList where itemId is greater than SMALLER_ITEM_ID
        defaultInvoiceDetailsShouldBeFound("itemId.greaterThan=" + SMALLER_ITEM_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemNameIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemName equals to DEFAULT_ITEM_NAME
        defaultInvoiceDetailsShouldBeFound("itemName.equals=" + DEFAULT_ITEM_NAME);

        // Get all the invoiceDetailsList where itemName equals to UPDATED_ITEM_NAME
        defaultInvoiceDetailsShouldNotBeFound("itemName.equals=" + UPDATED_ITEM_NAME);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemNameIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemName in DEFAULT_ITEM_NAME or UPDATED_ITEM_NAME
        defaultInvoiceDetailsShouldBeFound("itemName.in=" + DEFAULT_ITEM_NAME + "," + UPDATED_ITEM_NAME);

        // Get all the invoiceDetailsList where itemName equals to UPDATED_ITEM_NAME
        defaultInvoiceDetailsShouldNotBeFound("itemName.in=" + UPDATED_ITEM_NAME);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemName is not null
        defaultInvoiceDetailsShouldBeFound("itemName.specified=true");

        // Get all the invoiceDetailsList where itemName is null
        defaultInvoiceDetailsShouldNotBeFound("itemName.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemNameContainsSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemName contains DEFAULT_ITEM_NAME
        defaultInvoiceDetailsShouldBeFound("itemName.contains=" + DEFAULT_ITEM_NAME);

        // Get all the invoiceDetailsList where itemName contains UPDATED_ITEM_NAME
        defaultInvoiceDetailsShouldNotBeFound("itemName.contains=" + UPDATED_ITEM_NAME);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemNameNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemName does not contain DEFAULT_ITEM_NAME
        defaultInvoiceDetailsShouldNotBeFound("itemName.doesNotContain=" + DEFAULT_ITEM_NAME);

        // Get all the invoiceDetailsList where itemName does not contain UPDATED_ITEM_NAME
        defaultInvoiceDetailsShouldBeFound("itemName.doesNotContain=" + UPDATED_ITEM_NAME);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemCode equals to DEFAULT_ITEM_CODE
        defaultInvoiceDetailsShouldBeFound("itemCode.equals=" + DEFAULT_ITEM_CODE);

        // Get all the invoiceDetailsList where itemCode equals to UPDATED_ITEM_CODE
        defaultInvoiceDetailsShouldNotBeFound("itemCode.equals=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemCodeIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemCode in DEFAULT_ITEM_CODE or UPDATED_ITEM_CODE
        defaultInvoiceDetailsShouldBeFound("itemCode.in=" + DEFAULT_ITEM_CODE + "," + UPDATED_ITEM_CODE);

        // Get all the invoiceDetailsList where itemCode equals to UPDATED_ITEM_CODE
        defaultInvoiceDetailsShouldNotBeFound("itemCode.in=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemCode is not null
        defaultInvoiceDetailsShouldBeFound("itemCode.specified=true");

        // Get all the invoiceDetailsList where itemCode is null
        defaultInvoiceDetailsShouldNotBeFound("itemCode.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemCodeContainsSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemCode contains DEFAULT_ITEM_CODE
        defaultInvoiceDetailsShouldBeFound("itemCode.contains=" + DEFAULT_ITEM_CODE);

        // Get all the invoiceDetailsList where itemCode contains UPDATED_ITEM_CODE
        defaultInvoiceDetailsShouldNotBeFound("itemCode.contains=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemCodeNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemCode does not contain DEFAULT_ITEM_CODE
        defaultInvoiceDetailsShouldNotBeFound("itemCode.doesNotContain=" + DEFAULT_ITEM_CODE);

        // Get all the invoiceDetailsList where itemCode does not contain UPDATED_ITEM_CODE
        defaultInvoiceDetailsShouldBeFound("itemCode.doesNotContain=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemType equals to DEFAULT_ITEM_TYPE
        defaultInvoiceDetailsShouldBeFound("itemType.equals=" + DEFAULT_ITEM_TYPE);

        // Get all the invoiceDetailsList where itemType equals to UPDATED_ITEM_TYPE
        defaultInvoiceDetailsShouldNotBeFound("itemType.equals=" + UPDATED_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemTypeIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemType in DEFAULT_ITEM_TYPE or UPDATED_ITEM_TYPE
        defaultInvoiceDetailsShouldBeFound("itemType.in=" + DEFAULT_ITEM_TYPE + "," + UPDATED_ITEM_TYPE);

        // Get all the invoiceDetailsList where itemType equals to UPDATED_ITEM_TYPE
        defaultInvoiceDetailsShouldNotBeFound("itemType.in=" + UPDATED_ITEM_TYPE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByItemTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where itemType is not null
        defaultInvoiceDetailsShouldBeFound("itemType.specified=true");

        // Get all the invoiceDetailsList where itemType is null
        defaultInvoiceDetailsShouldNotBeFound("itemType.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByPaymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where paymentType equals to DEFAULT_PAYMENT_TYPE
        defaultInvoiceDetailsShouldBeFound("paymentType.equals=" + DEFAULT_PAYMENT_TYPE);

        // Get all the invoiceDetailsList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultInvoiceDetailsShouldNotBeFound("paymentType.equals=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByPaymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where paymentType in DEFAULT_PAYMENT_TYPE or UPDATED_PAYMENT_TYPE
        defaultInvoiceDetailsShouldBeFound("paymentType.in=" + DEFAULT_PAYMENT_TYPE + "," + UPDATED_PAYMENT_TYPE);

        // Get all the invoiceDetailsList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultInvoiceDetailsShouldNotBeFound("paymentType.in=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByPaymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where paymentType is not null
        defaultInvoiceDetailsShouldBeFound("paymentType.specified=true");

        // Get all the invoiceDetailsList where paymentType is null
        defaultInvoiceDetailsShouldNotBeFound("paymentType.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionStartingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionStartingDate equals to DEFAULT_SUBSCRIPTION_STARTING_DATE
        defaultInvoiceDetailsShouldBeFound("subscriptionStartingDate.equals=" + DEFAULT_SUBSCRIPTION_STARTING_DATE);

        // Get all the invoiceDetailsList where subscriptionStartingDate equals to UPDATED_SUBSCRIPTION_STARTING_DATE
        defaultInvoiceDetailsShouldNotBeFound("subscriptionStartingDate.equals=" + UPDATED_SUBSCRIPTION_STARTING_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionStartingDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionStartingDate in DEFAULT_SUBSCRIPTION_STARTING_DATE or UPDATED_SUBSCRIPTION_STARTING_DATE
        defaultInvoiceDetailsShouldBeFound(
            "subscriptionStartingDate.in=" + DEFAULT_SUBSCRIPTION_STARTING_DATE + "," + UPDATED_SUBSCRIPTION_STARTING_DATE
        );

        // Get all the invoiceDetailsList where subscriptionStartingDate equals to UPDATED_SUBSCRIPTION_STARTING_DATE
        defaultInvoiceDetailsShouldNotBeFound("subscriptionStartingDate.in=" + UPDATED_SUBSCRIPTION_STARTING_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionStartingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionStartingDate is not null
        defaultInvoiceDetailsShouldBeFound("subscriptionStartingDate.specified=true");

        // Get all the invoiceDetailsList where subscriptionStartingDate is null
        defaultInvoiceDetailsShouldNotBeFound("subscriptionStartingDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionStartingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionStartingDate is greater than or equal to DEFAULT_SUBSCRIPTION_STARTING_DATE
        defaultInvoiceDetailsShouldBeFound("subscriptionStartingDate.greaterThanOrEqual=" + DEFAULT_SUBSCRIPTION_STARTING_DATE);

        // Get all the invoiceDetailsList where subscriptionStartingDate is greater than or equal to UPDATED_SUBSCRIPTION_STARTING_DATE
        defaultInvoiceDetailsShouldNotBeFound("subscriptionStartingDate.greaterThanOrEqual=" + UPDATED_SUBSCRIPTION_STARTING_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionStartingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionStartingDate is less than or equal to DEFAULT_SUBSCRIPTION_STARTING_DATE
        defaultInvoiceDetailsShouldBeFound("subscriptionStartingDate.lessThanOrEqual=" + DEFAULT_SUBSCRIPTION_STARTING_DATE);

        // Get all the invoiceDetailsList where subscriptionStartingDate is less than or equal to SMALLER_SUBSCRIPTION_STARTING_DATE
        defaultInvoiceDetailsShouldNotBeFound("subscriptionStartingDate.lessThanOrEqual=" + SMALLER_SUBSCRIPTION_STARTING_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionStartingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionStartingDate is less than DEFAULT_SUBSCRIPTION_STARTING_DATE
        defaultInvoiceDetailsShouldNotBeFound("subscriptionStartingDate.lessThan=" + DEFAULT_SUBSCRIPTION_STARTING_DATE);

        // Get all the invoiceDetailsList where subscriptionStartingDate is less than UPDATED_SUBSCRIPTION_STARTING_DATE
        defaultInvoiceDetailsShouldBeFound("subscriptionStartingDate.lessThan=" + UPDATED_SUBSCRIPTION_STARTING_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionStartingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionStartingDate is greater than DEFAULT_SUBSCRIPTION_STARTING_DATE
        defaultInvoiceDetailsShouldNotBeFound("subscriptionStartingDate.greaterThan=" + DEFAULT_SUBSCRIPTION_STARTING_DATE);

        // Get all the invoiceDetailsList where subscriptionStartingDate is greater than SMALLER_SUBSCRIPTION_STARTING_DATE
        defaultInvoiceDetailsShouldBeFound("subscriptionStartingDate.greaterThan=" + SMALLER_SUBSCRIPTION_STARTING_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionDurationWeeksIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionDurationWeeks equals to DEFAULT_SUBSCRIPTION_DURATION_WEEKS
        defaultInvoiceDetailsShouldBeFound("subscriptionDurationWeeks.equals=" + DEFAULT_SUBSCRIPTION_DURATION_WEEKS);

        // Get all the invoiceDetailsList where subscriptionDurationWeeks equals to UPDATED_SUBSCRIPTION_DURATION_WEEKS
        defaultInvoiceDetailsShouldNotBeFound("subscriptionDurationWeeks.equals=" + UPDATED_SUBSCRIPTION_DURATION_WEEKS);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionDurationWeeksIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionDurationWeeks in DEFAULT_SUBSCRIPTION_DURATION_WEEKS or UPDATED_SUBSCRIPTION_DURATION_WEEKS
        defaultInvoiceDetailsShouldBeFound(
            "subscriptionDurationWeeks.in=" + DEFAULT_SUBSCRIPTION_DURATION_WEEKS + "," + UPDATED_SUBSCRIPTION_DURATION_WEEKS
        );

        // Get all the invoiceDetailsList where subscriptionDurationWeeks equals to UPDATED_SUBSCRIPTION_DURATION_WEEKS
        defaultInvoiceDetailsShouldNotBeFound("subscriptionDurationWeeks.in=" + UPDATED_SUBSCRIPTION_DURATION_WEEKS);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionDurationWeeksIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionDurationWeeks is not null
        defaultInvoiceDetailsShouldBeFound("subscriptionDurationWeeks.specified=true");

        // Get all the invoiceDetailsList where subscriptionDurationWeeks is null
        defaultInvoiceDetailsShouldNotBeFound("subscriptionDurationWeeks.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionDurationWeeksIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionDurationWeeks is greater than or equal to DEFAULT_SUBSCRIPTION_DURATION_WEEKS
        defaultInvoiceDetailsShouldBeFound("subscriptionDurationWeeks.greaterThanOrEqual=" + DEFAULT_SUBSCRIPTION_DURATION_WEEKS);

        // Get all the invoiceDetailsList where subscriptionDurationWeeks is greater than or equal to UPDATED_SUBSCRIPTION_DURATION_WEEKS
        defaultInvoiceDetailsShouldNotBeFound("subscriptionDurationWeeks.greaterThanOrEqual=" + UPDATED_SUBSCRIPTION_DURATION_WEEKS);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionDurationWeeksIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionDurationWeeks is less than or equal to DEFAULT_SUBSCRIPTION_DURATION_WEEKS
        defaultInvoiceDetailsShouldBeFound("subscriptionDurationWeeks.lessThanOrEqual=" + DEFAULT_SUBSCRIPTION_DURATION_WEEKS);

        // Get all the invoiceDetailsList where subscriptionDurationWeeks is less than or equal to SMALLER_SUBSCRIPTION_DURATION_WEEKS
        defaultInvoiceDetailsShouldNotBeFound("subscriptionDurationWeeks.lessThanOrEqual=" + SMALLER_SUBSCRIPTION_DURATION_WEEKS);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionDurationWeeksIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionDurationWeeks is less than DEFAULT_SUBSCRIPTION_DURATION_WEEKS
        defaultInvoiceDetailsShouldNotBeFound("subscriptionDurationWeeks.lessThan=" + DEFAULT_SUBSCRIPTION_DURATION_WEEKS);

        // Get all the invoiceDetailsList where subscriptionDurationWeeks is less than UPDATED_SUBSCRIPTION_DURATION_WEEKS
        defaultInvoiceDetailsShouldBeFound("subscriptionDurationWeeks.lessThan=" + UPDATED_SUBSCRIPTION_DURATION_WEEKS);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubscriptionDurationWeeksIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where subscriptionDurationWeeks is greater than DEFAULT_SUBSCRIPTION_DURATION_WEEKS
        defaultInvoiceDetailsShouldNotBeFound("subscriptionDurationWeeks.greaterThan=" + DEFAULT_SUBSCRIPTION_DURATION_WEEKS);

        // Get all the invoiceDetailsList where subscriptionDurationWeeks is greater than SMALLER_SUBSCRIPTION_DURATION_WEEKS
        defaultInvoiceDetailsShouldBeFound("subscriptionDurationWeeks.greaterThan=" + SMALLER_SUBSCRIPTION_DURATION_WEEKS);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDetailAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where detailAmount equals to DEFAULT_DETAIL_AMOUNT
        defaultInvoiceDetailsShouldBeFound("detailAmount.equals=" + DEFAULT_DETAIL_AMOUNT);

        // Get all the invoiceDetailsList where detailAmount equals to UPDATED_DETAIL_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("detailAmount.equals=" + UPDATED_DETAIL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDetailAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where detailAmount in DEFAULT_DETAIL_AMOUNT or UPDATED_DETAIL_AMOUNT
        defaultInvoiceDetailsShouldBeFound("detailAmount.in=" + DEFAULT_DETAIL_AMOUNT + "," + UPDATED_DETAIL_AMOUNT);

        // Get all the invoiceDetailsList where detailAmount equals to UPDATED_DETAIL_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("detailAmount.in=" + UPDATED_DETAIL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDetailAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where detailAmount is not null
        defaultInvoiceDetailsShouldBeFound("detailAmount.specified=true");

        // Get all the invoiceDetailsList where detailAmount is null
        defaultInvoiceDetailsShouldNotBeFound("detailAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDetailAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where detailAmount is greater than or equal to DEFAULT_DETAIL_AMOUNT
        defaultInvoiceDetailsShouldBeFound("detailAmount.greaterThanOrEqual=" + DEFAULT_DETAIL_AMOUNT);

        // Get all the invoiceDetailsList where detailAmount is greater than or equal to UPDATED_DETAIL_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("detailAmount.greaterThanOrEqual=" + UPDATED_DETAIL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDetailAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where detailAmount is less than or equal to DEFAULT_DETAIL_AMOUNT
        defaultInvoiceDetailsShouldBeFound("detailAmount.lessThanOrEqual=" + DEFAULT_DETAIL_AMOUNT);

        // Get all the invoiceDetailsList where detailAmount is less than or equal to SMALLER_DETAIL_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("detailAmount.lessThanOrEqual=" + SMALLER_DETAIL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDetailAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where detailAmount is less than DEFAULT_DETAIL_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("detailAmount.lessThan=" + DEFAULT_DETAIL_AMOUNT);

        // Get all the invoiceDetailsList where detailAmount is less than UPDATED_DETAIL_AMOUNT
        defaultInvoiceDetailsShouldBeFound("detailAmount.lessThan=" + UPDATED_DETAIL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDetailAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where detailAmount is greater than DEFAULT_DETAIL_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("detailAmount.greaterThan=" + DEFAULT_DETAIL_AMOUNT);

        // Get all the invoiceDetailsList where detailAmount is greater than SMALLER_DETAIL_AMOUNT
        defaultInvoiceDetailsShouldBeFound("detailAmount.greaterThan=" + SMALLER_DETAIL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByLineNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where lineNumber equals to DEFAULT_LINE_NUMBER
        defaultInvoiceDetailsShouldBeFound("lineNumber.equals=" + DEFAULT_LINE_NUMBER);

        // Get all the invoiceDetailsList where lineNumber equals to UPDATED_LINE_NUMBER
        defaultInvoiceDetailsShouldNotBeFound("lineNumber.equals=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByLineNumberIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where lineNumber in DEFAULT_LINE_NUMBER or UPDATED_LINE_NUMBER
        defaultInvoiceDetailsShouldBeFound("lineNumber.in=" + DEFAULT_LINE_NUMBER + "," + UPDATED_LINE_NUMBER);

        // Get all the invoiceDetailsList where lineNumber equals to UPDATED_LINE_NUMBER
        defaultInvoiceDetailsShouldNotBeFound("lineNumber.in=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByLineNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where lineNumber is not null
        defaultInvoiceDetailsShouldBeFound("lineNumber.specified=true");

        // Get all the invoiceDetailsList where lineNumber is null
        defaultInvoiceDetailsShouldNotBeFound("lineNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByLineNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where lineNumber is greater than or equal to DEFAULT_LINE_NUMBER
        defaultInvoiceDetailsShouldBeFound("lineNumber.greaterThanOrEqual=" + DEFAULT_LINE_NUMBER);

        // Get all the invoiceDetailsList where lineNumber is greater than or equal to UPDATED_LINE_NUMBER
        defaultInvoiceDetailsShouldNotBeFound("lineNumber.greaterThanOrEqual=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByLineNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where lineNumber is less than or equal to DEFAULT_LINE_NUMBER
        defaultInvoiceDetailsShouldBeFound("lineNumber.lessThanOrEqual=" + DEFAULT_LINE_NUMBER);

        // Get all the invoiceDetailsList where lineNumber is less than or equal to SMALLER_LINE_NUMBER
        defaultInvoiceDetailsShouldNotBeFound("lineNumber.lessThanOrEqual=" + SMALLER_LINE_NUMBER);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByLineNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where lineNumber is less than DEFAULT_LINE_NUMBER
        defaultInvoiceDetailsShouldNotBeFound("lineNumber.lessThan=" + DEFAULT_LINE_NUMBER);

        // Get all the invoiceDetailsList where lineNumber is less than UPDATED_LINE_NUMBER
        defaultInvoiceDetailsShouldBeFound("lineNumber.lessThan=" + UPDATED_LINE_NUMBER);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByLineNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where lineNumber is greater than DEFAULT_LINE_NUMBER
        defaultInvoiceDetailsShouldNotBeFound("lineNumber.greaterThan=" + DEFAULT_LINE_NUMBER);

        // Get all the invoiceDetailsList where lineNumber is greater than SMALLER_LINE_NUMBER
        defaultInvoiceDetailsShouldBeFound("lineNumber.greaterThan=" + SMALLER_LINE_NUMBER);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistIdIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistId equals to DEFAULT_NUTRITIONIST_ID
        defaultInvoiceDetailsShouldBeFound("nutritionistId.equals=" + DEFAULT_NUTRITIONIST_ID);

        // Get all the invoiceDetailsList where nutritionistId equals to UPDATED_NUTRITIONIST_ID
        defaultInvoiceDetailsShouldNotBeFound("nutritionistId.equals=" + UPDATED_NUTRITIONIST_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistIdIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistId in DEFAULT_NUTRITIONIST_ID or UPDATED_NUTRITIONIST_ID
        defaultInvoiceDetailsShouldBeFound("nutritionistId.in=" + DEFAULT_NUTRITIONIST_ID + "," + UPDATED_NUTRITIONIST_ID);

        // Get all the invoiceDetailsList where nutritionistId equals to UPDATED_NUTRITIONIST_ID
        defaultInvoiceDetailsShouldNotBeFound("nutritionistId.in=" + UPDATED_NUTRITIONIST_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistId is not null
        defaultInvoiceDetailsShouldBeFound("nutritionistId.specified=true");

        // Get all the invoiceDetailsList where nutritionistId is null
        defaultInvoiceDetailsShouldNotBeFound("nutritionistId.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistId is greater than or equal to DEFAULT_NUTRITIONIST_ID
        defaultInvoiceDetailsShouldBeFound("nutritionistId.greaterThanOrEqual=" + DEFAULT_NUTRITIONIST_ID);

        // Get all the invoiceDetailsList where nutritionistId is greater than or equal to UPDATED_NUTRITIONIST_ID
        defaultInvoiceDetailsShouldNotBeFound("nutritionistId.greaterThanOrEqual=" + UPDATED_NUTRITIONIST_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistId is less than or equal to DEFAULT_NUTRITIONIST_ID
        defaultInvoiceDetailsShouldBeFound("nutritionistId.lessThanOrEqual=" + DEFAULT_NUTRITIONIST_ID);

        // Get all the invoiceDetailsList where nutritionistId is less than or equal to SMALLER_NUTRITIONIST_ID
        defaultInvoiceDetailsShouldNotBeFound("nutritionistId.lessThanOrEqual=" + SMALLER_NUTRITIONIST_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistIdIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistId is less than DEFAULT_NUTRITIONIST_ID
        defaultInvoiceDetailsShouldNotBeFound("nutritionistId.lessThan=" + DEFAULT_NUTRITIONIST_ID);

        // Get all the invoiceDetailsList where nutritionistId is less than UPDATED_NUTRITIONIST_ID
        defaultInvoiceDetailsShouldBeFound("nutritionistId.lessThan=" + UPDATED_NUTRITIONIST_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistId is greater than DEFAULT_NUTRITIONIST_ID
        defaultInvoiceDetailsShouldNotBeFound("nutritionistId.greaterThan=" + DEFAULT_NUTRITIONIST_ID);

        // Get all the invoiceDetailsList where nutritionistId is greater than SMALLER_NUTRITIONIST_ID
        defaultInvoiceDetailsShouldBeFound("nutritionistId.greaterThan=" + SMALLER_NUTRITIONIST_ID);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalCostIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalCost equals to DEFAULT_TOTAL_COST
        defaultInvoiceDetailsShouldBeFound("totalCost.equals=" + DEFAULT_TOTAL_COST);

        // Get all the invoiceDetailsList where totalCost equals to UPDATED_TOTAL_COST
        defaultInvoiceDetailsShouldNotBeFound("totalCost.equals=" + UPDATED_TOTAL_COST);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalCostIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalCost in DEFAULT_TOTAL_COST or UPDATED_TOTAL_COST
        defaultInvoiceDetailsShouldBeFound("totalCost.in=" + DEFAULT_TOTAL_COST + "," + UPDATED_TOTAL_COST);

        // Get all the invoiceDetailsList where totalCost equals to UPDATED_TOTAL_COST
        defaultInvoiceDetailsShouldNotBeFound("totalCost.in=" + UPDATED_TOTAL_COST);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalCost is not null
        defaultInvoiceDetailsShouldBeFound("totalCost.specified=true");

        // Get all the invoiceDetailsList where totalCost is null
        defaultInvoiceDetailsShouldNotBeFound("totalCost.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalCost is greater than or equal to DEFAULT_TOTAL_COST
        defaultInvoiceDetailsShouldBeFound("totalCost.greaterThanOrEqual=" + DEFAULT_TOTAL_COST);

        // Get all the invoiceDetailsList where totalCost is greater than or equal to UPDATED_TOTAL_COST
        defaultInvoiceDetailsShouldNotBeFound("totalCost.greaterThanOrEqual=" + UPDATED_TOTAL_COST);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalCost is less than or equal to DEFAULT_TOTAL_COST
        defaultInvoiceDetailsShouldBeFound("totalCost.lessThanOrEqual=" + DEFAULT_TOTAL_COST);

        // Get all the invoiceDetailsList where totalCost is less than or equal to SMALLER_TOTAL_COST
        defaultInvoiceDetailsShouldNotBeFound("totalCost.lessThanOrEqual=" + SMALLER_TOTAL_COST);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalCostIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalCost is less than DEFAULT_TOTAL_COST
        defaultInvoiceDetailsShouldNotBeFound("totalCost.lessThan=" + DEFAULT_TOTAL_COST);

        // Get all the invoiceDetailsList where totalCost is less than UPDATED_TOTAL_COST
        defaultInvoiceDetailsShouldBeFound("totalCost.lessThan=" + UPDATED_TOTAL_COST);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalCost is greater than DEFAULT_TOTAL_COST
        defaultInvoiceDetailsShouldNotBeFound("totalCost.greaterThan=" + DEFAULT_TOTAL_COST);

        // Get all the invoiceDetailsList where totalCost is greater than SMALLER_TOTAL_COST
        defaultInvoiceDetailsShouldBeFound("totalCost.greaterThan=" + SMALLER_TOTAL_COST);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalProfitIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalProfit equals to DEFAULT_TOTAL_PROFIT
        defaultInvoiceDetailsShouldBeFound("totalProfit.equals=" + DEFAULT_TOTAL_PROFIT);

        // Get all the invoiceDetailsList where totalProfit equals to UPDATED_TOTAL_PROFIT
        defaultInvoiceDetailsShouldNotBeFound("totalProfit.equals=" + UPDATED_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalProfitIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalProfit in DEFAULT_TOTAL_PROFIT or UPDATED_TOTAL_PROFIT
        defaultInvoiceDetailsShouldBeFound("totalProfit.in=" + DEFAULT_TOTAL_PROFIT + "," + UPDATED_TOTAL_PROFIT);

        // Get all the invoiceDetailsList where totalProfit equals to UPDATED_TOTAL_PROFIT
        defaultInvoiceDetailsShouldNotBeFound("totalProfit.in=" + UPDATED_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalProfitIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalProfit is not null
        defaultInvoiceDetailsShouldBeFound("totalProfit.specified=true");

        // Get all the invoiceDetailsList where totalProfit is null
        defaultInvoiceDetailsShouldNotBeFound("totalProfit.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalProfitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalProfit is greater than or equal to DEFAULT_TOTAL_PROFIT
        defaultInvoiceDetailsShouldBeFound("totalProfit.greaterThanOrEqual=" + DEFAULT_TOTAL_PROFIT);

        // Get all the invoiceDetailsList where totalProfit is greater than or equal to UPDATED_TOTAL_PROFIT
        defaultInvoiceDetailsShouldNotBeFound("totalProfit.greaterThanOrEqual=" + UPDATED_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalProfitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalProfit is less than or equal to DEFAULT_TOTAL_PROFIT
        defaultInvoiceDetailsShouldBeFound("totalProfit.lessThanOrEqual=" + DEFAULT_TOTAL_PROFIT);

        // Get all the invoiceDetailsList where totalProfit is less than or equal to SMALLER_TOTAL_PROFIT
        defaultInvoiceDetailsShouldNotBeFound("totalProfit.lessThanOrEqual=" + SMALLER_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalProfitIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalProfit is less than DEFAULT_TOTAL_PROFIT
        defaultInvoiceDetailsShouldNotBeFound("totalProfit.lessThan=" + DEFAULT_TOTAL_PROFIT);

        // Get all the invoiceDetailsList where totalProfit is less than UPDATED_TOTAL_PROFIT
        defaultInvoiceDetailsShouldBeFound("totalProfit.lessThan=" + UPDATED_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalProfitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalProfit is greater than DEFAULT_TOTAL_PROFIT
        defaultInvoiceDetailsShouldNotBeFound("totalProfit.greaterThan=" + DEFAULT_TOTAL_PROFIT);

        // Get all the invoiceDetailsList where totalProfit is greater than SMALLER_TOTAL_PROFIT
        defaultInvoiceDetailsShouldBeFound("totalProfit.greaterThan=" + SMALLER_TOTAL_PROFIT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistEarningIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistEarning equals to DEFAULT_NUTRITIONIST_EARNING
        defaultInvoiceDetailsShouldBeFound("nutritionistEarning.equals=" + DEFAULT_NUTRITIONIST_EARNING);

        // Get all the invoiceDetailsList where nutritionistEarning equals to UPDATED_NUTRITIONIST_EARNING
        defaultInvoiceDetailsShouldNotBeFound("nutritionistEarning.equals=" + UPDATED_NUTRITIONIST_EARNING);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistEarningIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistEarning in DEFAULT_NUTRITIONIST_EARNING or UPDATED_NUTRITIONIST_EARNING
        defaultInvoiceDetailsShouldBeFound("nutritionistEarning.in=" + DEFAULT_NUTRITIONIST_EARNING + "," + UPDATED_NUTRITIONIST_EARNING);

        // Get all the invoiceDetailsList where nutritionistEarning equals to UPDATED_NUTRITIONIST_EARNING
        defaultInvoiceDetailsShouldNotBeFound("nutritionistEarning.in=" + UPDATED_NUTRITIONIST_EARNING);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistEarningIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistEarning is not null
        defaultInvoiceDetailsShouldBeFound("nutritionistEarning.specified=true");

        // Get all the invoiceDetailsList where nutritionistEarning is null
        defaultInvoiceDetailsShouldNotBeFound("nutritionistEarning.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistEarningIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistEarning is greater than or equal to DEFAULT_NUTRITIONIST_EARNING
        defaultInvoiceDetailsShouldBeFound("nutritionistEarning.greaterThanOrEqual=" + DEFAULT_NUTRITIONIST_EARNING);

        // Get all the invoiceDetailsList where nutritionistEarning is greater than or equal to UPDATED_NUTRITIONIST_EARNING
        defaultInvoiceDetailsShouldNotBeFound("nutritionistEarning.greaterThanOrEqual=" + UPDATED_NUTRITIONIST_EARNING);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistEarningIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistEarning is less than or equal to DEFAULT_NUTRITIONIST_EARNING
        defaultInvoiceDetailsShouldBeFound("nutritionistEarning.lessThanOrEqual=" + DEFAULT_NUTRITIONIST_EARNING);

        // Get all the invoiceDetailsList where nutritionistEarning is less than or equal to SMALLER_NUTRITIONIST_EARNING
        defaultInvoiceDetailsShouldNotBeFound("nutritionistEarning.lessThanOrEqual=" + SMALLER_NUTRITIONIST_EARNING);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistEarningIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistEarning is less than DEFAULT_NUTRITIONIST_EARNING
        defaultInvoiceDetailsShouldNotBeFound("nutritionistEarning.lessThan=" + DEFAULT_NUTRITIONIST_EARNING);

        // Get all the invoiceDetailsList where nutritionistEarning is less than UPDATED_NUTRITIONIST_EARNING
        defaultInvoiceDetailsShouldBeFound("nutritionistEarning.lessThan=" + UPDATED_NUTRITIONIST_EARNING);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistEarningIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistEarning is greater than DEFAULT_NUTRITIONIST_EARNING
        defaultInvoiceDetailsShouldNotBeFound("nutritionistEarning.greaterThan=" + DEFAULT_NUTRITIONIST_EARNING);

        // Get all the invoiceDetailsList where nutritionistEarning is greater than SMALLER_NUTRITIONIST_EARNING
        defaultInvoiceDetailsShouldBeFound("nutritionistEarning.greaterThan=" + SMALLER_NUTRITIONIST_EARNING);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistPercentage equals to DEFAULT_NUTRITIONIST_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("nutritionistPercentage.equals=" + DEFAULT_NUTRITIONIST_PERCENTAGE);

        // Get all the invoiceDetailsList where nutritionistPercentage equals to UPDATED_NUTRITIONIST_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("nutritionistPercentage.equals=" + UPDATED_NUTRITIONIST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistPercentage in DEFAULT_NUTRITIONIST_PERCENTAGE or UPDATED_NUTRITIONIST_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound(
            "nutritionistPercentage.in=" + DEFAULT_NUTRITIONIST_PERCENTAGE + "," + UPDATED_NUTRITIONIST_PERCENTAGE
        );

        // Get all the invoiceDetailsList where nutritionistPercentage equals to UPDATED_NUTRITIONIST_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("nutritionistPercentage.in=" + UPDATED_NUTRITIONIST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistPercentage is not null
        defaultInvoiceDetailsShouldBeFound("nutritionistPercentage.specified=true");

        // Get all the invoiceDetailsList where nutritionistPercentage is null
        defaultInvoiceDetailsShouldNotBeFound("nutritionistPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistPercentage is greater than or equal to DEFAULT_NUTRITIONIST_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("nutritionistPercentage.greaterThanOrEqual=" + DEFAULT_NUTRITIONIST_PERCENTAGE);

        // Get all the invoiceDetailsList where nutritionistPercentage is greater than or equal to UPDATED_NUTRITIONIST_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("nutritionistPercentage.greaterThanOrEqual=" + UPDATED_NUTRITIONIST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistPercentage is less than or equal to DEFAULT_NUTRITIONIST_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("nutritionistPercentage.lessThanOrEqual=" + DEFAULT_NUTRITIONIST_PERCENTAGE);

        // Get all the invoiceDetailsList where nutritionistPercentage is less than or equal to SMALLER_NUTRITIONIST_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("nutritionistPercentage.lessThanOrEqual=" + SMALLER_NUTRITIONIST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistPercentage is less than DEFAULT_NUTRITIONIST_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("nutritionistPercentage.lessThan=" + DEFAULT_NUTRITIONIST_PERCENTAGE);

        // Get all the invoiceDetailsList where nutritionistPercentage is less than UPDATED_NUTRITIONIST_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("nutritionistPercentage.lessThan=" + UPDATED_NUTRITIONIST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByNutritionistPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where nutritionistPercentage is greater than DEFAULT_NUTRITIONIST_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("nutritionistPercentage.greaterThan=" + DEFAULT_NUTRITIONIST_PERCENTAGE);

        // Get all the invoiceDetailsList where nutritionistPercentage is greater than SMALLER_NUTRITIONIST_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("nutritionistPercentage.greaterThan=" + SMALLER_NUTRITIONIST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesAmount equals to DEFAULT_FEDARAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesAmount.equals=" + DEFAULT_FEDARAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where fedaralTaxesAmount equals to UPDATED_FEDARAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesAmount.equals=" + UPDATED_FEDARAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesAmount in DEFAULT_FEDARAL_TAXES_AMOUNT or UPDATED_FEDARAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesAmount.in=" + DEFAULT_FEDARAL_TAXES_AMOUNT + "," + UPDATED_FEDARAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where fedaralTaxesAmount equals to UPDATED_FEDARAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesAmount.in=" + UPDATED_FEDARAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesAmount is not null
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesAmount.specified=true");

        // Get all the invoiceDetailsList where fedaralTaxesAmount is null
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesAmount is greater than or equal to DEFAULT_FEDARAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesAmount.greaterThanOrEqual=" + DEFAULT_FEDARAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where fedaralTaxesAmount is greater than or equal to UPDATED_FEDARAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesAmount.greaterThanOrEqual=" + UPDATED_FEDARAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesAmount is less than or equal to DEFAULT_FEDARAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesAmount.lessThanOrEqual=" + DEFAULT_FEDARAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where fedaralTaxesAmount is less than or equal to SMALLER_FEDARAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesAmount.lessThanOrEqual=" + SMALLER_FEDARAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesAmount is less than DEFAULT_FEDARAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesAmount.lessThan=" + DEFAULT_FEDARAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where fedaralTaxesAmount is less than UPDATED_FEDARAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesAmount.lessThan=" + UPDATED_FEDARAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesAmount is greater than DEFAULT_FEDARAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesAmount.greaterThan=" + DEFAULT_FEDARAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where fedaralTaxesAmount is greater than SMALLER_FEDARAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesAmount.greaterThan=" + SMALLER_FEDARAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesPercentage equals to DEFAULT_FEDARAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesPercentage.equals=" + DEFAULT_FEDARAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where fedaralTaxesPercentage equals to UPDATED_FEDARAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesPercentage.equals=" + UPDATED_FEDARAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesPercentage in DEFAULT_FEDARAL_TAXES_PERCENTAGE or UPDATED_FEDARAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound(
            "fedaralTaxesPercentage.in=" + DEFAULT_FEDARAL_TAXES_PERCENTAGE + "," + UPDATED_FEDARAL_TAXES_PERCENTAGE
        );

        // Get all the invoiceDetailsList where fedaralTaxesPercentage equals to UPDATED_FEDARAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesPercentage.in=" + UPDATED_FEDARAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesPercentage is not null
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesPercentage.specified=true");

        // Get all the invoiceDetailsList where fedaralTaxesPercentage is null
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesPercentage is greater than or equal to DEFAULT_FEDARAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesPercentage.greaterThanOrEqual=" + DEFAULT_FEDARAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where fedaralTaxesPercentage is greater than or equal to UPDATED_FEDARAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesPercentage.greaterThanOrEqual=" + UPDATED_FEDARAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesPercentage is less than or equal to DEFAULT_FEDARAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesPercentage.lessThanOrEqual=" + DEFAULT_FEDARAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where fedaralTaxesPercentage is less than or equal to SMALLER_FEDARAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesPercentage.lessThanOrEqual=" + SMALLER_FEDARAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesPercentage is less than DEFAULT_FEDARAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesPercentage.lessThan=" + DEFAULT_FEDARAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where fedaralTaxesPercentage is less than UPDATED_FEDARAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesPercentage.lessThan=" + UPDATED_FEDARAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByFedaralTaxesPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where fedaralTaxesPercentage is greater than DEFAULT_FEDARAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("fedaralTaxesPercentage.greaterThan=" + DEFAULT_FEDARAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where fedaralTaxesPercentage is greater than SMALLER_FEDARAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("fedaralTaxesPercentage.greaterThan=" + SMALLER_FEDARAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesAmount equals to DEFAULT_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("provintionalTaxesAmount.equals=" + DEFAULT_PROVINTIONAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where provintionalTaxesAmount equals to UPDATED_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesAmount.equals=" + UPDATED_PROVINTIONAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesAmount in DEFAULT_PROVINTIONAL_TAXES_AMOUNT or UPDATED_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound(
            "provintionalTaxesAmount.in=" + DEFAULT_PROVINTIONAL_TAXES_AMOUNT + "," + UPDATED_PROVINTIONAL_TAXES_AMOUNT
        );

        // Get all the invoiceDetailsList where provintionalTaxesAmount equals to UPDATED_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesAmount.in=" + UPDATED_PROVINTIONAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesAmount is not null
        defaultInvoiceDetailsShouldBeFound("provintionalTaxesAmount.specified=true");

        // Get all the invoiceDetailsList where provintionalTaxesAmount is null
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesAmount is greater than or equal to DEFAULT_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("provintionalTaxesAmount.greaterThanOrEqual=" + DEFAULT_PROVINTIONAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where provintionalTaxesAmount is greater than or equal to UPDATED_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesAmount.greaterThanOrEqual=" + UPDATED_PROVINTIONAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesAmount is less than or equal to DEFAULT_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("provintionalTaxesAmount.lessThanOrEqual=" + DEFAULT_PROVINTIONAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where provintionalTaxesAmount is less than or equal to SMALLER_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesAmount.lessThanOrEqual=" + SMALLER_PROVINTIONAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesAmount is less than DEFAULT_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesAmount.lessThan=" + DEFAULT_PROVINTIONAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where provintionalTaxesAmount is less than UPDATED_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("provintionalTaxesAmount.lessThan=" + UPDATED_PROVINTIONAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesAmount is greater than DEFAULT_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesAmount.greaterThan=" + DEFAULT_PROVINTIONAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where provintionalTaxesAmount is greater than SMALLER_PROVINTIONAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("provintionalTaxesAmount.greaterThan=" + SMALLER_PROVINTIONAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesPercentage equals to DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("provintionalTaxesPercentage.equals=" + DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where provintionalTaxesPercentage equals to UPDATED_PROVINTIONAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesPercentage.equals=" + UPDATED_PROVINTIONAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesPercentage in DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE or UPDATED_PROVINTIONAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound(
            "provintionalTaxesPercentage.in=" + DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE + "," + UPDATED_PROVINTIONAL_TAXES_PERCENTAGE
        );

        // Get all the invoiceDetailsList where provintionalTaxesPercentage equals to UPDATED_PROVINTIONAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesPercentage.in=" + UPDATED_PROVINTIONAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesPercentage is not null
        defaultInvoiceDetailsShouldBeFound("provintionalTaxesPercentage.specified=true");

        // Get all the invoiceDetailsList where provintionalTaxesPercentage is null
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesPercentage is greater than or equal to DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("provintionalTaxesPercentage.greaterThanOrEqual=" + DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where provintionalTaxesPercentage is greater than or equal to UPDATED_PROVINTIONAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesPercentage.greaterThanOrEqual=" + UPDATED_PROVINTIONAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesPercentage is less than or equal to DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("provintionalTaxesPercentage.lessThanOrEqual=" + DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where provintionalTaxesPercentage is less than or equal to SMALLER_PROVINTIONAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesPercentage.lessThanOrEqual=" + SMALLER_PROVINTIONAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesPercentage is less than DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesPercentage.lessThan=" + DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where provintionalTaxesPercentage is less than UPDATED_PROVINTIONAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("provintionalTaxesPercentage.lessThan=" + UPDATED_PROVINTIONAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByProvintionalTaxesPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where provintionalTaxesPercentage is greater than DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("provintionalTaxesPercentage.greaterThan=" + DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where provintionalTaxesPercentage is greater than SMALLER_PROVINTIONAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("provintionalTaxesPercentage.greaterThan=" + SMALLER_PROVINTIONAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesAmount equals to DEFAULT_TOTAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("totalTaxesAmount.equals=" + DEFAULT_TOTAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where totalTaxesAmount equals to UPDATED_TOTAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesAmount.equals=" + UPDATED_TOTAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesAmount in DEFAULT_TOTAL_TAXES_AMOUNT or UPDATED_TOTAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("totalTaxesAmount.in=" + DEFAULT_TOTAL_TAXES_AMOUNT + "," + UPDATED_TOTAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where totalTaxesAmount equals to UPDATED_TOTAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesAmount.in=" + UPDATED_TOTAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesAmount is not null
        defaultInvoiceDetailsShouldBeFound("totalTaxesAmount.specified=true");

        // Get all the invoiceDetailsList where totalTaxesAmount is null
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesAmount is greater than or equal to DEFAULT_TOTAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("totalTaxesAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where totalTaxesAmount is greater than or equal to UPDATED_TOTAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesAmount.greaterThanOrEqual=" + UPDATED_TOTAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesAmount is less than or equal to DEFAULT_TOTAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("totalTaxesAmount.lessThanOrEqual=" + DEFAULT_TOTAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where totalTaxesAmount is less than or equal to SMALLER_TOTAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesAmount.lessThanOrEqual=" + SMALLER_TOTAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesAmount is less than DEFAULT_TOTAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesAmount.lessThan=" + DEFAULT_TOTAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where totalTaxesAmount is less than UPDATED_TOTAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("totalTaxesAmount.lessThan=" + UPDATED_TOTAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesAmount is greater than DEFAULT_TOTAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesAmount.greaterThan=" + DEFAULT_TOTAL_TAXES_AMOUNT);

        // Get all the invoiceDetailsList where totalTaxesAmount is greater than SMALLER_TOTAL_TAXES_AMOUNT
        defaultInvoiceDetailsShouldBeFound("totalTaxesAmount.greaterThan=" + SMALLER_TOTAL_TAXES_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesPercentage equals to DEFAULT_TOTAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("totalTaxesPercentage.equals=" + DEFAULT_TOTAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where totalTaxesPercentage equals to UPDATED_TOTAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesPercentage.equals=" + UPDATED_TOTAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesPercentage in DEFAULT_TOTAL_TAXES_PERCENTAGE or UPDATED_TOTAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound(
            "totalTaxesPercentage.in=" + DEFAULT_TOTAL_TAXES_PERCENTAGE + "," + UPDATED_TOTAL_TAXES_PERCENTAGE
        );

        // Get all the invoiceDetailsList where totalTaxesPercentage equals to UPDATED_TOTAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesPercentage.in=" + UPDATED_TOTAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesPercentage is not null
        defaultInvoiceDetailsShouldBeFound("totalTaxesPercentage.specified=true");

        // Get all the invoiceDetailsList where totalTaxesPercentage is null
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesPercentage is greater than or equal to DEFAULT_TOTAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("totalTaxesPercentage.greaterThanOrEqual=" + DEFAULT_TOTAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where totalTaxesPercentage is greater than or equal to UPDATED_TOTAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesPercentage.greaterThanOrEqual=" + UPDATED_TOTAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesPercentage is less than or equal to DEFAULT_TOTAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("totalTaxesPercentage.lessThanOrEqual=" + DEFAULT_TOTAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where totalTaxesPercentage is less than or equal to SMALLER_TOTAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesPercentage.lessThanOrEqual=" + SMALLER_TOTAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesPercentage is less than DEFAULT_TOTAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesPercentage.lessThan=" + DEFAULT_TOTAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where totalTaxesPercentage is less than UPDATED_TOTAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("totalTaxesPercentage.lessThan=" + UPDATED_TOTAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalTaxesPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalTaxesPercentage is greater than DEFAULT_TOTAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("totalTaxesPercentage.greaterThan=" + DEFAULT_TOTAL_TAXES_PERCENTAGE);

        // Get all the invoiceDetailsList where totalTaxesPercentage is greater than SMALLER_TOTAL_TAXES_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("totalTaxesPercentage.greaterThan=" + SMALLER_TOTAL_TAXES_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountAmount equals to DEFAULT_DISCOUNT_AMOUNT
        defaultInvoiceDetailsShouldBeFound("discountAmount.equals=" + DEFAULT_DISCOUNT_AMOUNT);

        // Get all the invoiceDetailsList where discountAmount equals to UPDATED_DISCOUNT_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("discountAmount.equals=" + UPDATED_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountAmount in DEFAULT_DISCOUNT_AMOUNT or UPDATED_DISCOUNT_AMOUNT
        defaultInvoiceDetailsShouldBeFound("discountAmount.in=" + DEFAULT_DISCOUNT_AMOUNT + "," + UPDATED_DISCOUNT_AMOUNT);

        // Get all the invoiceDetailsList where discountAmount equals to UPDATED_DISCOUNT_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("discountAmount.in=" + UPDATED_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountAmount is not null
        defaultInvoiceDetailsShouldBeFound("discountAmount.specified=true");

        // Get all the invoiceDetailsList where discountAmount is null
        defaultInvoiceDetailsShouldNotBeFound("discountAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountAmount is greater than or equal to DEFAULT_DISCOUNT_AMOUNT
        defaultInvoiceDetailsShouldBeFound("discountAmount.greaterThanOrEqual=" + DEFAULT_DISCOUNT_AMOUNT);

        // Get all the invoiceDetailsList where discountAmount is greater than or equal to UPDATED_DISCOUNT_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("discountAmount.greaterThanOrEqual=" + UPDATED_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountAmount is less than or equal to DEFAULT_DISCOUNT_AMOUNT
        defaultInvoiceDetailsShouldBeFound("discountAmount.lessThanOrEqual=" + DEFAULT_DISCOUNT_AMOUNT);

        // Get all the invoiceDetailsList where discountAmount is less than or equal to SMALLER_DISCOUNT_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("discountAmount.lessThanOrEqual=" + SMALLER_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountAmount is less than DEFAULT_DISCOUNT_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("discountAmount.lessThan=" + DEFAULT_DISCOUNT_AMOUNT);

        // Get all the invoiceDetailsList where discountAmount is less than UPDATED_DISCOUNT_AMOUNT
        defaultInvoiceDetailsShouldBeFound("discountAmount.lessThan=" + UPDATED_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountAmount is greater than DEFAULT_DISCOUNT_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("discountAmount.greaterThan=" + DEFAULT_DISCOUNT_AMOUNT);

        // Get all the invoiceDetailsList where discountAmount is greater than SMALLER_DISCOUNT_AMOUNT
        defaultInvoiceDetailsShouldBeFound("discountAmount.greaterThan=" + SMALLER_DISCOUNT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountPercentage equals to DEFAULT_DISCOUNT_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("discountPercentage.equals=" + DEFAULT_DISCOUNT_PERCENTAGE);

        // Get all the invoiceDetailsList where discountPercentage equals to UPDATED_DISCOUNT_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("discountPercentage.equals=" + UPDATED_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountPercentage in DEFAULT_DISCOUNT_PERCENTAGE or UPDATED_DISCOUNT_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("discountPercentage.in=" + DEFAULT_DISCOUNT_PERCENTAGE + "," + UPDATED_DISCOUNT_PERCENTAGE);

        // Get all the invoiceDetailsList where discountPercentage equals to UPDATED_DISCOUNT_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("discountPercentage.in=" + UPDATED_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountPercentage is not null
        defaultInvoiceDetailsShouldBeFound("discountPercentage.specified=true");

        // Get all the invoiceDetailsList where discountPercentage is null
        defaultInvoiceDetailsShouldNotBeFound("discountPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountPercentage is greater than or equal to DEFAULT_DISCOUNT_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("discountPercentage.greaterThanOrEqual=" + DEFAULT_DISCOUNT_PERCENTAGE);

        // Get all the invoiceDetailsList where discountPercentage is greater than or equal to UPDATED_DISCOUNT_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("discountPercentage.greaterThanOrEqual=" + UPDATED_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountPercentage is less than or equal to DEFAULT_DISCOUNT_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("discountPercentage.lessThanOrEqual=" + DEFAULT_DISCOUNT_PERCENTAGE);

        // Get all the invoiceDetailsList where discountPercentage is less than or equal to SMALLER_DISCOUNT_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("discountPercentage.lessThanOrEqual=" + SMALLER_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountPercentage is less than DEFAULT_DISCOUNT_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("discountPercentage.lessThan=" + DEFAULT_DISCOUNT_PERCENTAGE);

        // Get all the invoiceDetailsList where discountPercentage is less than UPDATED_DISCOUNT_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("discountPercentage.lessThan=" + UPDATED_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByDiscountPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where discountPercentage is greater than DEFAULT_DISCOUNT_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("discountPercentage.greaterThan=" + DEFAULT_DISCOUNT_PERCENTAGE);

        // Get all the invoiceDetailsList where discountPercentage is greater than SMALLER_DISCOUNT_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("discountPercentage.greaterThan=" + SMALLER_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnCode equals to DEFAULT_ADD_ON_CODE
        defaultInvoiceDetailsShouldBeFound("addOnCode.equals=" + DEFAULT_ADD_ON_CODE);

        // Get all the invoiceDetailsList where addOnCode equals to UPDATED_ADD_ON_CODE
        defaultInvoiceDetailsShouldNotBeFound("addOnCode.equals=" + UPDATED_ADD_ON_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnCodeIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnCode in DEFAULT_ADD_ON_CODE or UPDATED_ADD_ON_CODE
        defaultInvoiceDetailsShouldBeFound("addOnCode.in=" + DEFAULT_ADD_ON_CODE + "," + UPDATED_ADD_ON_CODE);

        // Get all the invoiceDetailsList where addOnCode equals to UPDATED_ADD_ON_CODE
        defaultInvoiceDetailsShouldNotBeFound("addOnCode.in=" + UPDATED_ADD_ON_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnCode is not null
        defaultInvoiceDetailsShouldBeFound("addOnCode.specified=true");

        // Get all the invoiceDetailsList where addOnCode is null
        defaultInvoiceDetailsShouldNotBeFound("addOnCode.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnCodeContainsSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnCode contains DEFAULT_ADD_ON_CODE
        defaultInvoiceDetailsShouldBeFound("addOnCode.contains=" + DEFAULT_ADD_ON_CODE);

        // Get all the invoiceDetailsList where addOnCode contains UPDATED_ADD_ON_CODE
        defaultInvoiceDetailsShouldNotBeFound("addOnCode.contains=" + UPDATED_ADD_ON_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnCodeNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnCode does not contain DEFAULT_ADD_ON_CODE
        defaultInvoiceDetailsShouldNotBeFound("addOnCode.doesNotContain=" + DEFAULT_ADD_ON_CODE);

        // Get all the invoiceDetailsList where addOnCode does not contain UPDATED_ADD_ON_CODE
        defaultInvoiceDetailsShouldBeFound("addOnCode.doesNotContain=" + UPDATED_ADD_ON_CODE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnAmount equals to DEFAULT_ADD_ON_AMOUNT
        defaultInvoiceDetailsShouldBeFound("addOnAmount.equals=" + DEFAULT_ADD_ON_AMOUNT);

        // Get all the invoiceDetailsList where addOnAmount equals to UPDATED_ADD_ON_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("addOnAmount.equals=" + UPDATED_ADD_ON_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnAmount in DEFAULT_ADD_ON_AMOUNT or UPDATED_ADD_ON_AMOUNT
        defaultInvoiceDetailsShouldBeFound("addOnAmount.in=" + DEFAULT_ADD_ON_AMOUNT + "," + UPDATED_ADD_ON_AMOUNT);

        // Get all the invoiceDetailsList where addOnAmount equals to UPDATED_ADD_ON_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("addOnAmount.in=" + UPDATED_ADD_ON_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnAmount is not null
        defaultInvoiceDetailsShouldBeFound("addOnAmount.specified=true");

        // Get all the invoiceDetailsList where addOnAmount is null
        defaultInvoiceDetailsShouldNotBeFound("addOnAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnAmount is greater than or equal to DEFAULT_ADD_ON_AMOUNT
        defaultInvoiceDetailsShouldBeFound("addOnAmount.greaterThanOrEqual=" + DEFAULT_ADD_ON_AMOUNT);

        // Get all the invoiceDetailsList where addOnAmount is greater than or equal to UPDATED_ADD_ON_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("addOnAmount.greaterThanOrEqual=" + UPDATED_ADD_ON_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnAmount is less than or equal to DEFAULT_ADD_ON_AMOUNT
        defaultInvoiceDetailsShouldBeFound("addOnAmount.lessThanOrEqual=" + DEFAULT_ADD_ON_AMOUNT);

        // Get all the invoiceDetailsList where addOnAmount is less than or equal to SMALLER_ADD_ON_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("addOnAmount.lessThanOrEqual=" + SMALLER_ADD_ON_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnAmount is less than DEFAULT_ADD_ON_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("addOnAmount.lessThan=" + DEFAULT_ADD_ON_AMOUNT);

        // Get all the invoiceDetailsList where addOnAmount is less than UPDATED_ADD_ON_AMOUNT
        defaultInvoiceDetailsShouldBeFound("addOnAmount.lessThan=" + UPDATED_ADD_ON_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnAmount is greater than DEFAULT_ADD_ON_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("addOnAmount.greaterThan=" + DEFAULT_ADD_ON_AMOUNT);

        // Get all the invoiceDetailsList where addOnAmount is greater than SMALLER_ADD_ON_AMOUNT
        defaultInvoiceDetailsShouldBeFound("addOnAmount.greaterThan=" + SMALLER_ADD_ON_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnPercentage equals to DEFAULT_ADD_ON_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("addOnPercentage.equals=" + DEFAULT_ADD_ON_PERCENTAGE);

        // Get all the invoiceDetailsList where addOnPercentage equals to UPDATED_ADD_ON_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("addOnPercentage.equals=" + UPDATED_ADD_ON_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnPercentage in DEFAULT_ADD_ON_PERCENTAGE or UPDATED_ADD_ON_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("addOnPercentage.in=" + DEFAULT_ADD_ON_PERCENTAGE + "," + UPDATED_ADD_ON_PERCENTAGE);

        // Get all the invoiceDetailsList where addOnPercentage equals to UPDATED_ADD_ON_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("addOnPercentage.in=" + UPDATED_ADD_ON_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnPercentage is not null
        defaultInvoiceDetailsShouldBeFound("addOnPercentage.specified=true");

        // Get all the invoiceDetailsList where addOnPercentage is null
        defaultInvoiceDetailsShouldNotBeFound("addOnPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnPercentage is greater than or equal to DEFAULT_ADD_ON_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("addOnPercentage.greaterThanOrEqual=" + DEFAULT_ADD_ON_PERCENTAGE);

        // Get all the invoiceDetailsList where addOnPercentage is greater than or equal to UPDATED_ADD_ON_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("addOnPercentage.greaterThanOrEqual=" + UPDATED_ADD_ON_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnPercentage is less than or equal to DEFAULT_ADD_ON_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("addOnPercentage.lessThanOrEqual=" + DEFAULT_ADD_ON_PERCENTAGE);

        // Get all the invoiceDetailsList where addOnPercentage is less than or equal to SMALLER_ADD_ON_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("addOnPercentage.lessThanOrEqual=" + SMALLER_ADD_ON_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnPercentage is less than DEFAULT_ADD_ON_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("addOnPercentage.lessThan=" + DEFAULT_ADD_ON_PERCENTAGE);

        // Get all the invoiceDetailsList where addOnPercentage is less than UPDATED_ADD_ON_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("addOnPercentage.lessThan=" + UPDATED_ADD_ON_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByAddOnPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where addOnPercentage is greater than DEFAULT_ADD_ON_PERCENTAGE
        defaultInvoiceDetailsShouldNotBeFound("addOnPercentage.greaterThan=" + DEFAULT_ADD_ON_PERCENTAGE);

        // Get all the invoiceDetailsList where addOnPercentage is greater than SMALLER_ADD_ON_PERCENTAGE
        defaultInvoiceDetailsShouldBeFound("addOnPercentage.greaterThan=" + SMALLER_ADD_ON_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultInvoiceDetailsShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoiceDetailsList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultInvoiceDetailsShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the invoiceDetailsList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalAmount is not null
        defaultInvoiceDetailsShouldBeFound("totalAmount.specified=true");

        // Get all the invoiceDetailsList where totalAmount is null
        defaultInvoiceDetailsShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalAmount is greater than or equal to DEFAULT_TOTAL_AMOUNT
        defaultInvoiceDetailsShouldBeFound("totalAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoiceDetailsList where totalAmount is greater than or equal to UPDATED_TOTAL_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("totalAmount.greaterThanOrEqual=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalAmount is less than or equal to DEFAULT_TOTAL_AMOUNT
        defaultInvoiceDetailsShouldBeFound("totalAmount.lessThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoiceDetailsList where totalAmount is less than or equal to SMALLER_TOTAL_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("totalAmount.lessThanOrEqual=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalAmount is less than DEFAULT_TOTAL_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("totalAmount.lessThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoiceDetailsList where totalAmount is less than UPDATED_TOTAL_AMOUNT
        defaultInvoiceDetailsShouldBeFound("totalAmount.lessThan=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByTotalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where totalAmount is greater than DEFAULT_TOTAL_AMOUNT
        defaultInvoiceDetailsShouldNotBeFound("totalAmount.greaterThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the invoiceDetailsList where totalAmount is greater than SMALLER_TOTAL_AMOUNT
        defaultInvoiceDetailsShouldBeFound("totalAmount.greaterThan=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultInvoiceDetailsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceDetailsList where createdDate equals to UPDATED_CREATED_DATE
        defaultInvoiceDetailsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultInvoiceDetailsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the invoiceDetailsList where createdDate equals to UPDATED_CREATED_DATE
        defaultInvoiceDetailsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where createdDate is not null
        defaultInvoiceDetailsShouldBeFound("createdDate.specified=true");

        // Get all the invoiceDetailsList where createdDate is null
        defaultInvoiceDetailsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultInvoiceDetailsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceDetailsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultInvoiceDetailsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultInvoiceDetailsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceDetailsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultInvoiceDetailsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultInvoiceDetailsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceDetailsList where createdDate is less than UPDATED_CREATED_DATE
        defaultInvoiceDetailsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        // Get all the invoiceDetailsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultInvoiceDetailsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the invoiceDetailsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultInvoiceDetailsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsBySubItemsIsEqualToSomething() throws Exception {
        SubItems subItems;
        if (TestUtil.findAll(em, SubItems.class).isEmpty()) {
            invoiceDetailsRepository.saveAndFlush(invoiceDetails);
            subItems = SubItemsResourceIT.createEntity(em);
        } else {
            subItems = TestUtil.findAll(em, SubItems.class).get(0);
        }
        em.persist(subItems);
        em.flush();
        invoiceDetails.addSubItems(subItems);
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);
        Long subItemsId = subItems.getId();

        // Get all the invoiceDetailsList where subItems equals to subItemsId
        defaultInvoiceDetailsShouldBeFound("subItemsId.equals=" + subItemsId);

        // Get all the invoiceDetailsList where subItems equals to (subItemsId + 1)
        defaultInvoiceDetailsShouldNotBeFound("subItemsId.equals=" + (subItemsId + 1));
    }

    @Test
    @Transactional
    void getAllInvoiceDetailsByInvoicesIsEqualToSomething() throws Exception {
        Invoices invoices;
        if (TestUtil.findAll(em, Invoices.class).isEmpty()) {
            invoiceDetailsRepository.saveAndFlush(invoiceDetails);
            invoices = InvoicesResourceIT.createEntity(em);
        } else {
            invoices = TestUtil.findAll(em, Invoices.class).get(0);
        }
        em.persist(invoices);
        em.flush();
        invoiceDetails.addInvoices(invoices);
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);
        Long invoicesId = invoices.getId();

        // Get all the invoiceDetailsList where invoices equals to invoicesId
        defaultInvoiceDetailsShouldBeFound("invoicesId.equals=" + invoicesId);

        // Get all the invoiceDetailsList where invoices equals to (invoicesId + 1)
        defaultInvoiceDetailsShouldNotBeFound("invoicesId.equals=" + (invoicesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoiceDetailsShouldBeFound(String filter) throws Exception {
        restInvoiceDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].cartId").value(hasItem(DEFAULT_CART_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].subscriptionStartingDate").value(hasItem(DEFAULT_SUBSCRIPTION_STARTING_DATE.toString())))
            .andExpect(jsonPath("$.[*].subscriptionDurationWeeks").value(hasItem(DEFAULT_SUBSCRIPTION_DURATION_WEEKS)))
            .andExpect(jsonPath("$.[*].detailAmount").value(hasItem(DEFAULT_DETAIL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].lineNumber").value(hasItem(DEFAULT_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].nutritionistId").value(hasItem(DEFAULT_NUTRITIONIST_ID.intValue())))
            .andExpect(jsonPath("$.[*].totalCost").value(hasItem(DEFAULT_TOTAL_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].totalProfit").value(hasItem(DEFAULT_TOTAL_PROFIT.doubleValue())))
            .andExpect(jsonPath("$.[*].nutritionistEarning").value(hasItem(DEFAULT_NUTRITIONIST_EARNING.doubleValue())))
            .andExpect(jsonPath("$.[*].nutritionistPercentage").value(hasItem(DEFAULT_NUTRITIONIST_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].fedaralTaxesAmount").value(hasItem(DEFAULT_FEDARAL_TAXES_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].fedaralTaxesPercentage").value(hasItem(DEFAULT_FEDARAL_TAXES_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].provintionalTaxesAmount").value(hasItem(DEFAULT_PROVINTIONAL_TAXES_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].provintionalTaxesPercentage").value(hasItem(DEFAULT_PROVINTIONAL_TAXES_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalTaxesAmount").value(hasItem(DEFAULT_TOTAL_TAXES_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalTaxesPercentage").value(hasItem(DEFAULT_TOTAL_TAXES_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].discountAmount").value(hasItem(DEFAULT_DISCOUNT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].discountPercentage").value(hasItem(DEFAULT_DISCOUNT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].addOnCode").value(hasItem(DEFAULT_ADD_ON_CODE)))
            .andExpect(jsonPath("$.[*].addOnAmount").value(hasItem(DEFAULT_ADD_ON_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].addOnPercentage").value(hasItem(DEFAULT_ADD_ON_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restInvoiceDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoiceDetailsShouldNotBeFound(String filter) throws Exception {
        restInvoiceDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoiceDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInvoiceDetails() throws Exception {
        // Get the invoiceDetails
        restInvoiceDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInvoiceDetails() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        int databaseSizeBeforeUpdate = invoiceDetailsRepository.findAll().size();

        // Update the invoiceDetails
        InvoiceDetails updatedInvoiceDetails = invoiceDetailsRepository.findById(invoiceDetails.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceDetails are not directly saved in db
        em.detach(updatedInvoiceDetails);
        updatedInvoiceDetails
            .contactId(UPDATED_CONTACT_ID)
            .cartId(UPDATED_CART_ID)
            .itemId(UPDATED_ITEM_ID)
            .itemName(UPDATED_ITEM_NAME)
            .itemCode(UPDATED_ITEM_CODE)
            .itemType(UPDATED_ITEM_TYPE)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .subscriptionStartingDate(UPDATED_SUBSCRIPTION_STARTING_DATE)
            .subscriptionDurationWeeks(UPDATED_SUBSCRIPTION_DURATION_WEEKS)
            .detailAmount(UPDATED_DETAIL_AMOUNT)
            .lineNumber(UPDATED_LINE_NUMBER)
            .nutritionistId(UPDATED_NUTRITIONIST_ID)
            .totalCost(UPDATED_TOTAL_COST)
            .totalProfit(UPDATED_TOTAL_PROFIT)
            .nutritionistEarning(UPDATED_NUTRITIONIST_EARNING)
            .nutritionistPercentage(UPDATED_NUTRITIONIST_PERCENTAGE)
            .fedaralTaxesAmount(UPDATED_FEDARAL_TAXES_AMOUNT)
            .fedaralTaxesPercentage(UPDATED_FEDARAL_TAXES_PERCENTAGE)
            .provintionalTaxesAmount(UPDATED_PROVINTIONAL_TAXES_AMOUNT)
            .provintionalTaxesPercentage(UPDATED_PROVINTIONAL_TAXES_PERCENTAGE)
            .totalTaxesAmount(UPDATED_TOTAL_TAXES_AMOUNT)
            .totalTaxesPercentage(UPDATED_TOTAL_TAXES_PERCENTAGE)
            .discountAmount(UPDATED_DISCOUNT_AMOUNT)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .addOnCode(UPDATED_ADD_ON_CODE)
            .addOnAmount(UPDATED_ADD_ON_AMOUNT)
            .addOnPercentage(UPDATED_ADD_ON_PERCENTAGE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE);
        InvoiceDetailsDTO invoiceDetailsDTO = invoiceDetailsMapper.toDto(updatedInvoiceDetails);

        restInvoiceDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoiceDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceDetails in the database
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAll();
        assertThat(invoiceDetailsList).hasSize(databaseSizeBeforeUpdate);
        InvoiceDetails testInvoiceDetails = invoiceDetailsList.get(invoiceDetailsList.size() - 1);
        assertThat(testInvoiceDetails.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testInvoiceDetails.getCartId()).isEqualTo(UPDATED_CART_ID);
        assertThat(testInvoiceDetails.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testInvoiceDetails.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testInvoiceDetails.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testInvoiceDetails.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testInvoiceDetails.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testInvoiceDetails.getSubscriptionStartingDate()).isEqualTo(UPDATED_SUBSCRIPTION_STARTING_DATE);
        assertThat(testInvoiceDetails.getSubscriptionDurationWeeks()).isEqualTo(UPDATED_SUBSCRIPTION_DURATION_WEEKS);
        assertThat(testInvoiceDetails.getDetailAmount()).isEqualTo(UPDATED_DETAIL_AMOUNT);
        assertThat(testInvoiceDetails.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testInvoiceDetails.getNutritionistId()).isEqualTo(UPDATED_NUTRITIONIST_ID);
        assertThat(testInvoiceDetails.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testInvoiceDetails.getTotalProfit()).isEqualTo(UPDATED_TOTAL_PROFIT);
        assertThat(testInvoiceDetails.getNutritionistEarning()).isEqualTo(UPDATED_NUTRITIONIST_EARNING);
        assertThat(testInvoiceDetails.getNutritionistPercentage()).isEqualTo(UPDATED_NUTRITIONIST_PERCENTAGE);
        assertThat(testInvoiceDetails.getFedaralTaxesAmount()).isEqualTo(UPDATED_FEDARAL_TAXES_AMOUNT);
        assertThat(testInvoiceDetails.getFedaralTaxesPercentage()).isEqualTo(UPDATED_FEDARAL_TAXES_PERCENTAGE);
        assertThat(testInvoiceDetails.getProvintionalTaxesAmount()).isEqualTo(UPDATED_PROVINTIONAL_TAXES_AMOUNT);
        assertThat(testInvoiceDetails.getProvintionalTaxesPercentage()).isEqualTo(UPDATED_PROVINTIONAL_TAXES_PERCENTAGE);
        assertThat(testInvoiceDetails.getTotalTaxesAmount()).isEqualTo(UPDATED_TOTAL_TAXES_AMOUNT);
        assertThat(testInvoiceDetails.getTotalTaxesPercentage()).isEqualTo(UPDATED_TOTAL_TAXES_PERCENTAGE);
        assertThat(testInvoiceDetails.getDiscountAmount()).isEqualTo(UPDATED_DISCOUNT_AMOUNT);
        assertThat(testInvoiceDetails.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testInvoiceDetails.getAddOnCode()).isEqualTo(UPDATED_ADD_ON_CODE);
        assertThat(testInvoiceDetails.getAddOnAmount()).isEqualTo(UPDATED_ADD_ON_AMOUNT);
        assertThat(testInvoiceDetails.getAddOnPercentage()).isEqualTo(UPDATED_ADD_ON_PERCENTAGE);
        assertThat(testInvoiceDetails.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testInvoiceDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingInvoiceDetails() throws Exception {
        int databaseSizeBeforeUpdate = invoiceDetailsRepository.findAll().size();
        invoiceDetails.setId(count.incrementAndGet());

        // Create the InvoiceDetails
        InvoiceDetailsDTO invoiceDetailsDTO = invoiceDetailsMapper.toDto(invoiceDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, invoiceDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceDetails in the database
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAll();
        assertThat(invoiceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInvoiceDetails() throws Exception {
        int databaseSizeBeforeUpdate = invoiceDetailsRepository.findAll().size();
        invoiceDetails.setId(count.incrementAndGet());

        // Create the InvoiceDetails
        InvoiceDetailsDTO invoiceDetailsDTO = invoiceDetailsMapper.toDto(invoiceDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(invoiceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceDetails in the database
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAll();
        assertThat(invoiceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInvoiceDetails() throws Exception {
        int databaseSizeBeforeUpdate = invoiceDetailsRepository.findAll().size();
        invoiceDetails.setId(count.incrementAndGet());

        // Create the InvoiceDetails
        InvoiceDetailsDTO invoiceDetailsDTO = invoiceDetailsMapper.toDto(invoiceDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invoiceDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceDetails in the database
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAll();
        assertThat(invoiceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInvoiceDetailsWithPatch() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        int databaseSizeBeforeUpdate = invoiceDetailsRepository.findAll().size();

        // Update the invoiceDetails using partial update
        InvoiceDetails partialUpdatedInvoiceDetails = new InvoiceDetails();
        partialUpdatedInvoiceDetails.setId(invoiceDetails.getId());

        partialUpdatedInvoiceDetails
            .contactId(UPDATED_CONTACT_ID)
            .itemId(UPDATED_ITEM_ID)
            .itemName(UPDATED_ITEM_NAME)
            .itemType(UPDATED_ITEM_TYPE)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .subscriptionDurationWeeks(UPDATED_SUBSCRIPTION_DURATION_WEEKS)
            .totalCost(UPDATED_TOTAL_COST)
            .nutritionistPercentage(UPDATED_NUTRITIONIST_PERCENTAGE)
            .fedaralTaxesAmount(UPDATED_FEDARAL_TAXES_AMOUNT)
            .fedaralTaxesPercentage(UPDATED_FEDARAL_TAXES_PERCENTAGE)
            .provintionalTaxesPercentage(UPDATED_PROVINTIONAL_TAXES_PERCENTAGE)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .addOnAmount(UPDATED_ADD_ON_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE);

        restInvoiceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoiceDetails))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceDetails in the database
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAll();
        assertThat(invoiceDetailsList).hasSize(databaseSizeBeforeUpdate);
        InvoiceDetails testInvoiceDetails = invoiceDetailsList.get(invoiceDetailsList.size() - 1);
        assertThat(testInvoiceDetails.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testInvoiceDetails.getCartId()).isEqualTo(DEFAULT_CART_ID);
        assertThat(testInvoiceDetails.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testInvoiceDetails.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testInvoiceDetails.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testInvoiceDetails.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testInvoiceDetails.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testInvoiceDetails.getSubscriptionStartingDate()).isEqualTo(DEFAULT_SUBSCRIPTION_STARTING_DATE);
        assertThat(testInvoiceDetails.getSubscriptionDurationWeeks()).isEqualTo(UPDATED_SUBSCRIPTION_DURATION_WEEKS);
        assertThat(testInvoiceDetails.getDetailAmount()).isEqualTo(DEFAULT_DETAIL_AMOUNT);
        assertThat(testInvoiceDetails.getLineNumber()).isEqualTo(DEFAULT_LINE_NUMBER);
        assertThat(testInvoiceDetails.getNutritionistId()).isEqualTo(DEFAULT_NUTRITIONIST_ID);
        assertThat(testInvoiceDetails.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testInvoiceDetails.getTotalProfit()).isEqualTo(DEFAULT_TOTAL_PROFIT);
        assertThat(testInvoiceDetails.getNutritionistEarning()).isEqualTo(DEFAULT_NUTRITIONIST_EARNING);
        assertThat(testInvoiceDetails.getNutritionistPercentage()).isEqualTo(UPDATED_NUTRITIONIST_PERCENTAGE);
        assertThat(testInvoiceDetails.getFedaralTaxesAmount()).isEqualTo(UPDATED_FEDARAL_TAXES_AMOUNT);
        assertThat(testInvoiceDetails.getFedaralTaxesPercentage()).isEqualTo(UPDATED_FEDARAL_TAXES_PERCENTAGE);
        assertThat(testInvoiceDetails.getProvintionalTaxesAmount()).isEqualTo(DEFAULT_PROVINTIONAL_TAXES_AMOUNT);
        assertThat(testInvoiceDetails.getProvintionalTaxesPercentage()).isEqualTo(UPDATED_PROVINTIONAL_TAXES_PERCENTAGE);
        assertThat(testInvoiceDetails.getTotalTaxesAmount()).isEqualTo(DEFAULT_TOTAL_TAXES_AMOUNT);
        assertThat(testInvoiceDetails.getTotalTaxesPercentage()).isEqualTo(DEFAULT_TOTAL_TAXES_PERCENTAGE);
        assertThat(testInvoiceDetails.getDiscountAmount()).isEqualTo(DEFAULT_DISCOUNT_AMOUNT);
        assertThat(testInvoiceDetails.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testInvoiceDetails.getAddOnCode()).isEqualTo(DEFAULT_ADD_ON_CODE);
        assertThat(testInvoiceDetails.getAddOnAmount()).isEqualTo(UPDATED_ADD_ON_AMOUNT);
        assertThat(testInvoiceDetails.getAddOnPercentage()).isEqualTo(DEFAULT_ADD_ON_PERCENTAGE);
        assertThat(testInvoiceDetails.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testInvoiceDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateInvoiceDetailsWithPatch() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        int databaseSizeBeforeUpdate = invoiceDetailsRepository.findAll().size();

        // Update the invoiceDetails using partial update
        InvoiceDetails partialUpdatedInvoiceDetails = new InvoiceDetails();
        partialUpdatedInvoiceDetails.setId(invoiceDetails.getId());

        partialUpdatedInvoiceDetails
            .contactId(UPDATED_CONTACT_ID)
            .cartId(UPDATED_CART_ID)
            .itemId(UPDATED_ITEM_ID)
            .itemName(UPDATED_ITEM_NAME)
            .itemCode(UPDATED_ITEM_CODE)
            .itemType(UPDATED_ITEM_TYPE)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .subscriptionStartingDate(UPDATED_SUBSCRIPTION_STARTING_DATE)
            .subscriptionDurationWeeks(UPDATED_SUBSCRIPTION_DURATION_WEEKS)
            .detailAmount(UPDATED_DETAIL_AMOUNT)
            .lineNumber(UPDATED_LINE_NUMBER)
            .nutritionistId(UPDATED_NUTRITIONIST_ID)
            .totalCost(UPDATED_TOTAL_COST)
            .totalProfit(UPDATED_TOTAL_PROFIT)
            .nutritionistEarning(UPDATED_NUTRITIONIST_EARNING)
            .nutritionistPercentage(UPDATED_NUTRITIONIST_PERCENTAGE)
            .fedaralTaxesAmount(UPDATED_FEDARAL_TAXES_AMOUNT)
            .fedaralTaxesPercentage(UPDATED_FEDARAL_TAXES_PERCENTAGE)
            .provintionalTaxesAmount(UPDATED_PROVINTIONAL_TAXES_AMOUNT)
            .provintionalTaxesPercentage(UPDATED_PROVINTIONAL_TAXES_PERCENTAGE)
            .totalTaxesAmount(UPDATED_TOTAL_TAXES_AMOUNT)
            .totalTaxesPercentage(UPDATED_TOTAL_TAXES_PERCENTAGE)
            .discountAmount(UPDATED_DISCOUNT_AMOUNT)
            .discountPercentage(UPDATED_DISCOUNT_PERCENTAGE)
            .addOnCode(UPDATED_ADD_ON_CODE)
            .addOnAmount(UPDATED_ADD_ON_AMOUNT)
            .addOnPercentage(UPDATED_ADD_ON_PERCENTAGE)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .createdDate(UPDATED_CREATED_DATE);

        restInvoiceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInvoiceDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInvoiceDetails))
            )
            .andExpect(status().isOk());

        // Validate the InvoiceDetails in the database
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAll();
        assertThat(invoiceDetailsList).hasSize(databaseSizeBeforeUpdate);
        InvoiceDetails testInvoiceDetails = invoiceDetailsList.get(invoiceDetailsList.size() - 1);
        assertThat(testInvoiceDetails.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testInvoiceDetails.getCartId()).isEqualTo(UPDATED_CART_ID);
        assertThat(testInvoiceDetails.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testInvoiceDetails.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testInvoiceDetails.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testInvoiceDetails.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testInvoiceDetails.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testInvoiceDetails.getSubscriptionStartingDate()).isEqualTo(UPDATED_SUBSCRIPTION_STARTING_DATE);
        assertThat(testInvoiceDetails.getSubscriptionDurationWeeks()).isEqualTo(UPDATED_SUBSCRIPTION_DURATION_WEEKS);
        assertThat(testInvoiceDetails.getDetailAmount()).isEqualTo(UPDATED_DETAIL_AMOUNT);
        assertThat(testInvoiceDetails.getLineNumber()).isEqualTo(UPDATED_LINE_NUMBER);
        assertThat(testInvoiceDetails.getNutritionistId()).isEqualTo(UPDATED_NUTRITIONIST_ID);
        assertThat(testInvoiceDetails.getTotalCost()).isEqualTo(UPDATED_TOTAL_COST);
        assertThat(testInvoiceDetails.getTotalProfit()).isEqualTo(UPDATED_TOTAL_PROFIT);
        assertThat(testInvoiceDetails.getNutritionistEarning()).isEqualTo(UPDATED_NUTRITIONIST_EARNING);
        assertThat(testInvoiceDetails.getNutritionistPercentage()).isEqualTo(UPDATED_NUTRITIONIST_PERCENTAGE);
        assertThat(testInvoiceDetails.getFedaralTaxesAmount()).isEqualTo(UPDATED_FEDARAL_TAXES_AMOUNT);
        assertThat(testInvoiceDetails.getFedaralTaxesPercentage()).isEqualTo(UPDATED_FEDARAL_TAXES_PERCENTAGE);
        assertThat(testInvoiceDetails.getProvintionalTaxesAmount()).isEqualTo(UPDATED_PROVINTIONAL_TAXES_AMOUNT);
        assertThat(testInvoiceDetails.getProvintionalTaxesPercentage()).isEqualTo(UPDATED_PROVINTIONAL_TAXES_PERCENTAGE);
        assertThat(testInvoiceDetails.getTotalTaxesAmount()).isEqualTo(UPDATED_TOTAL_TAXES_AMOUNT);
        assertThat(testInvoiceDetails.getTotalTaxesPercentage()).isEqualTo(UPDATED_TOTAL_TAXES_PERCENTAGE);
        assertThat(testInvoiceDetails.getDiscountAmount()).isEqualTo(UPDATED_DISCOUNT_AMOUNT);
        assertThat(testInvoiceDetails.getDiscountPercentage()).isEqualTo(UPDATED_DISCOUNT_PERCENTAGE);
        assertThat(testInvoiceDetails.getAddOnCode()).isEqualTo(UPDATED_ADD_ON_CODE);
        assertThat(testInvoiceDetails.getAddOnAmount()).isEqualTo(UPDATED_ADD_ON_AMOUNT);
        assertThat(testInvoiceDetails.getAddOnPercentage()).isEqualTo(UPDATED_ADD_ON_PERCENTAGE);
        assertThat(testInvoiceDetails.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testInvoiceDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingInvoiceDetails() throws Exception {
        int databaseSizeBeforeUpdate = invoiceDetailsRepository.findAll().size();
        invoiceDetails.setId(count.incrementAndGet());

        // Create the InvoiceDetails
        InvoiceDetailsDTO invoiceDetailsDTO = invoiceDetailsMapper.toDto(invoiceDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, invoiceDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceDetails in the database
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAll();
        assertThat(invoiceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInvoiceDetails() throws Exception {
        int databaseSizeBeforeUpdate = invoiceDetailsRepository.findAll().size();
        invoiceDetails.setId(count.incrementAndGet());

        // Create the InvoiceDetails
        InvoiceDetailsDTO invoiceDetailsDTO = invoiceDetailsMapper.toDto(invoiceDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InvoiceDetails in the database
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAll();
        assertThat(invoiceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInvoiceDetails() throws Exception {
        int databaseSizeBeforeUpdate = invoiceDetailsRepository.findAll().size();
        invoiceDetails.setId(count.incrementAndGet());

        // Create the InvoiceDetails
        InvoiceDetailsDTO invoiceDetailsDTO = invoiceDetailsMapper.toDto(invoiceDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInvoiceDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(invoiceDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InvoiceDetails in the database
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAll();
        assertThat(invoiceDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInvoiceDetails() throws Exception {
        // Initialize the database
        invoiceDetailsRepository.saveAndFlush(invoiceDetails);

        int databaseSizeBeforeDelete = invoiceDetailsRepository.findAll().size();

        // Delete the invoiceDetails
        restInvoiceDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, invoiceDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceDetails> invoiceDetailsList = invoiceDetailsRepository.findAll();
        assertThat(invoiceDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
