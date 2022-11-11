package com.polarbears.capstone.hmsfinance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.polarbears.capstone.hmsfinance.IntegrationTest;
import com.polarbears.capstone.hmsfinance.domain.Invoices;
import com.polarbears.capstone.hmsfinance.domain.Payments;
import com.polarbears.capstone.hmsfinance.domain.enumeration.PAYMENTSTATUS;
import com.polarbears.capstone.hmsfinance.domain.enumeration.PAYMENTTYPES;
import com.polarbears.capstone.hmsfinance.repository.PaymentsRepository;
import com.polarbears.capstone.hmsfinance.service.PaymentsService;
import com.polarbears.capstone.hmsfinance.service.criteria.PaymentsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.PaymentsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.PaymentsMapper;
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
 * Integration tests for the {@link PaymentsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PaymentsResourceIT {

    private static final String DEFAULT_REF_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REF_NUMBER = "BBBBBBBBBB";

    private static final PAYMENTTYPES DEFAULT_PAYMENT_TYPE = PAYMENTTYPES.IN;
    private static final PAYMENTTYPES UPDATED_PAYMENT_TYPE = PAYMENTTYPES.OUT;

    private static final Long DEFAULT_CONTACT_ID = 1L;
    private static final Long UPDATED_CONTACT_ID = 2L;
    private static final Long SMALLER_CONTACT_ID = 1L - 1L;

    private static final String DEFAULT_EXPLANATION = "AAAAAAAAAA";
    private static final String UPDATED_EXPLANATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_OPERATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OPERATION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_OPERATION_DATE = LocalDate.ofEpochDay(-1L);

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final PAYMENTSTATUS DEFAULT_STATUS = PAYMENTSTATUS.PAID;
    private static final PAYMENTSTATUS UPDATED_STATUS = PAYMENTSTATUS.CANCELLED;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Mock
    private PaymentsRepository paymentsRepositoryMock;

    @Autowired
    private PaymentsMapper paymentsMapper;

    @Mock
    private PaymentsService paymentsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentsMockMvc;

    private Payments payments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payments createEntity(EntityManager em) {
        Payments payments = new Payments()
            .refNumber(DEFAULT_REF_NUMBER)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .contactId(DEFAULT_CONTACT_ID)
            .explanation(DEFAULT_EXPLANATION)
            .operationDate(DEFAULT_OPERATION_DATE)
            .amount(DEFAULT_AMOUNT)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE);
        return payments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payments createUpdatedEntity(EntityManager em) {
        Payments payments = new Payments()
            .refNumber(UPDATED_REF_NUMBER)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .contactId(UPDATED_CONTACT_ID)
            .explanation(UPDATED_EXPLANATION)
            .operationDate(UPDATED_OPERATION_DATE)
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE);
        return payments;
    }

    @BeforeEach
    public void initTest() {
        payments = createEntity(em);
    }

    @Test
    @Transactional
    void createPayments() throws Exception {
        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();
        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);
        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate + 1);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getRefNumber()).isEqualTo(DEFAULT_REF_NUMBER);
        assertThat(testPayments.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testPayments.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testPayments.getExplanation()).isEqualTo(DEFAULT_EXPLANATION);
        assertThat(testPayments.getOperationDate()).isEqualTo(DEFAULT_OPERATION_DATE);
        assertThat(testPayments.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPayments.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPayments.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createPaymentsWithExistingId() throws Exception {
        // Create the Payments with an existing ID
        payments.setId(1L);
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        int databaseSizeBeforeCreate = paymentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList
        restPaymentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payments.getId().intValue())))
            .andExpect(jsonPath("$.[*].refNumber").value(hasItem(DEFAULT_REF_NUMBER)))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].explanation").value(hasItem(DEFAULT_EXPLANATION)))
            .andExpect(jsonPath("$.[*].operationDate").value(hasItem(DEFAULT_OPERATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPaymentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(paymentsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaymentsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(paymentsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPaymentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(paymentsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaymentsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(paymentsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get the payments
        restPaymentsMockMvc
            .perform(get(ENTITY_API_URL_ID, payments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payments.getId().intValue()))
            .andExpect(jsonPath("$.refNumber").value(DEFAULT_REF_NUMBER))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID.intValue()))
            .andExpect(jsonPath("$.explanation").value(DEFAULT_EXPLANATION))
            .andExpect(jsonPath("$.operationDate").value(DEFAULT_OPERATION_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getPaymentsByIdFiltering() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        Long id = payments.getId();

        defaultPaymentsShouldBeFound("id.equals=" + id);
        defaultPaymentsShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentsShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaymentsByRefNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where refNumber equals to DEFAULT_REF_NUMBER
        defaultPaymentsShouldBeFound("refNumber.equals=" + DEFAULT_REF_NUMBER);

        // Get all the paymentsList where refNumber equals to UPDATED_REF_NUMBER
        defaultPaymentsShouldNotBeFound("refNumber.equals=" + UPDATED_REF_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentsByRefNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where refNumber in DEFAULT_REF_NUMBER or UPDATED_REF_NUMBER
        defaultPaymentsShouldBeFound("refNumber.in=" + DEFAULT_REF_NUMBER + "," + UPDATED_REF_NUMBER);

        // Get all the paymentsList where refNumber equals to UPDATED_REF_NUMBER
        defaultPaymentsShouldNotBeFound("refNumber.in=" + UPDATED_REF_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentsByRefNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where refNumber is not null
        defaultPaymentsShouldBeFound("refNumber.specified=true");

        // Get all the paymentsList where refNumber is null
        defaultPaymentsShouldNotBeFound("refNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByRefNumberContainsSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where refNumber contains DEFAULT_REF_NUMBER
        defaultPaymentsShouldBeFound("refNumber.contains=" + DEFAULT_REF_NUMBER);

        // Get all the paymentsList where refNumber contains UPDATED_REF_NUMBER
        defaultPaymentsShouldNotBeFound("refNumber.contains=" + UPDATED_REF_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentsByRefNumberNotContainsSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where refNumber does not contain DEFAULT_REF_NUMBER
        defaultPaymentsShouldNotBeFound("refNumber.doesNotContain=" + DEFAULT_REF_NUMBER);

        // Get all the paymentsList where refNumber does not contain UPDATED_REF_NUMBER
        defaultPaymentsShouldBeFound("refNumber.doesNotContain=" + UPDATED_REF_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentsByPaymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where paymentType equals to DEFAULT_PAYMENT_TYPE
        defaultPaymentsShouldBeFound("paymentType.equals=" + DEFAULT_PAYMENT_TYPE);

        // Get all the paymentsList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultPaymentsShouldNotBeFound("paymentType.equals=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllPaymentsByPaymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where paymentType in DEFAULT_PAYMENT_TYPE or UPDATED_PAYMENT_TYPE
        defaultPaymentsShouldBeFound("paymentType.in=" + DEFAULT_PAYMENT_TYPE + "," + UPDATED_PAYMENT_TYPE);

        // Get all the paymentsList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultPaymentsShouldNotBeFound("paymentType.in=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllPaymentsByPaymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where paymentType is not null
        defaultPaymentsShouldBeFound("paymentType.specified=true");

        // Get all the paymentsList where paymentType is null
        defaultPaymentsShouldNotBeFound("paymentType.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByContactIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where contactId equals to DEFAULT_CONTACT_ID
        defaultPaymentsShouldBeFound("contactId.equals=" + DEFAULT_CONTACT_ID);

        // Get all the paymentsList where contactId equals to UPDATED_CONTACT_ID
        defaultPaymentsShouldNotBeFound("contactId.equals=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllPaymentsByContactIdIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where contactId in DEFAULT_CONTACT_ID or UPDATED_CONTACT_ID
        defaultPaymentsShouldBeFound("contactId.in=" + DEFAULT_CONTACT_ID + "," + UPDATED_CONTACT_ID);

        // Get all the paymentsList where contactId equals to UPDATED_CONTACT_ID
        defaultPaymentsShouldNotBeFound("contactId.in=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllPaymentsByContactIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where contactId is not null
        defaultPaymentsShouldBeFound("contactId.specified=true");

        // Get all the paymentsList where contactId is null
        defaultPaymentsShouldNotBeFound("contactId.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByContactIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where contactId is greater than or equal to DEFAULT_CONTACT_ID
        defaultPaymentsShouldBeFound("contactId.greaterThanOrEqual=" + DEFAULT_CONTACT_ID);

        // Get all the paymentsList where contactId is greater than or equal to UPDATED_CONTACT_ID
        defaultPaymentsShouldNotBeFound("contactId.greaterThanOrEqual=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllPaymentsByContactIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where contactId is less than or equal to DEFAULT_CONTACT_ID
        defaultPaymentsShouldBeFound("contactId.lessThanOrEqual=" + DEFAULT_CONTACT_ID);

        // Get all the paymentsList where contactId is less than or equal to SMALLER_CONTACT_ID
        defaultPaymentsShouldNotBeFound("contactId.lessThanOrEqual=" + SMALLER_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllPaymentsByContactIdIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where contactId is less than DEFAULT_CONTACT_ID
        defaultPaymentsShouldNotBeFound("contactId.lessThan=" + DEFAULT_CONTACT_ID);

        // Get all the paymentsList where contactId is less than UPDATED_CONTACT_ID
        defaultPaymentsShouldBeFound("contactId.lessThan=" + UPDATED_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllPaymentsByContactIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where contactId is greater than DEFAULT_CONTACT_ID
        defaultPaymentsShouldNotBeFound("contactId.greaterThan=" + DEFAULT_CONTACT_ID);

        // Get all the paymentsList where contactId is greater than SMALLER_CONTACT_ID
        defaultPaymentsShouldBeFound("contactId.greaterThan=" + SMALLER_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllPaymentsByExplanationIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where explanation equals to DEFAULT_EXPLANATION
        defaultPaymentsShouldBeFound("explanation.equals=" + DEFAULT_EXPLANATION);

        // Get all the paymentsList where explanation equals to UPDATED_EXPLANATION
        defaultPaymentsShouldNotBeFound("explanation.equals=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllPaymentsByExplanationIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where explanation in DEFAULT_EXPLANATION or UPDATED_EXPLANATION
        defaultPaymentsShouldBeFound("explanation.in=" + DEFAULT_EXPLANATION + "," + UPDATED_EXPLANATION);

        // Get all the paymentsList where explanation equals to UPDATED_EXPLANATION
        defaultPaymentsShouldNotBeFound("explanation.in=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllPaymentsByExplanationIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where explanation is not null
        defaultPaymentsShouldBeFound("explanation.specified=true");

        // Get all the paymentsList where explanation is null
        defaultPaymentsShouldNotBeFound("explanation.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByExplanationContainsSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where explanation contains DEFAULT_EXPLANATION
        defaultPaymentsShouldBeFound("explanation.contains=" + DEFAULT_EXPLANATION);

        // Get all the paymentsList where explanation contains UPDATED_EXPLANATION
        defaultPaymentsShouldNotBeFound("explanation.contains=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllPaymentsByExplanationNotContainsSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where explanation does not contain DEFAULT_EXPLANATION
        defaultPaymentsShouldNotBeFound("explanation.doesNotContain=" + DEFAULT_EXPLANATION);

        // Get all the paymentsList where explanation does not contain UPDATED_EXPLANATION
        defaultPaymentsShouldBeFound("explanation.doesNotContain=" + UPDATED_EXPLANATION);
    }

    @Test
    @Transactional
    void getAllPaymentsByOperationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where operationDate equals to DEFAULT_OPERATION_DATE
        defaultPaymentsShouldBeFound("operationDate.equals=" + DEFAULT_OPERATION_DATE);

        // Get all the paymentsList where operationDate equals to UPDATED_OPERATION_DATE
        defaultPaymentsShouldNotBeFound("operationDate.equals=" + UPDATED_OPERATION_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByOperationDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where operationDate in DEFAULT_OPERATION_DATE or UPDATED_OPERATION_DATE
        defaultPaymentsShouldBeFound("operationDate.in=" + DEFAULT_OPERATION_DATE + "," + UPDATED_OPERATION_DATE);

        // Get all the paymentsList where operationDate equals to UPDATED_OPERATION_DATE
        defaultPaymentsShouldNotBeFound("operationDate.in=" + UPDATED_OPERATION_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByOperationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where operationDate is not null
        defaultPaymentsShouldBeFound("operationDate.specified=true");

        // Get all the paymentsList where operationDate is null
        defaultPaymentsShouldNotBeFound("operationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByOperationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where operationDate is greater than or equal to DEFAULT_OPERATION_DATE
        defaultPaymentsShouldBeFound("operationDate.greaterThanOrEqual=" + DEFAULT_OPERATION_DATE);

        // Get all the paymentsList where operationDate is greater than or equal to UPDATED_OPERATION_DATE
        defaultPaymentsShouldNotBeFound("operationDate.greaterThanOrEqual=" + UPDATED_OPERATION_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByOperationDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where operationDate is less than or equal to DEFAULT_OPERATION_DATE
        defaultPaymentsShouldBeFound("operationDate.lessThanOrEqual=" + DEFAULT_OPERATION_DATE);

        // Get all the paymentsList where operationDate is less than or equal to SMALLER_OPERATION_DATE
        defaultPaymentsShouldNotBeFound("operationDate.lessThanOrEqual=" + SMALLER_OPERATION_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByOperationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where operationDate is less than DEFAULT_OPERATION_DATE
        defaultPaymentsShouldNotBeFound("operationDate.lessThan=" + DEFAULT_OPERATION_DATE);

        // Get all the paymentsList where operationDate is less than UPDATED_OPERATION_DATE
        defaultPaymentsShouldBeFound("operationDate.lessThan=" + UPDATED_OPERATION_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByOperationDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where operationDate is greater than DEFAULT_OPERATION_DATE
        defaultPaymentsShouldNotBeFound("operationDate.greaterThan=" + DEFAULT_OPERATION_DATE);

        // Get all the paymentsList where operationDate is greater than SMALLER_OPERATION_DATE
        defaultPaymentsShouldBeFound("operationDate.greaterThan=" + SMALLER_OPERATION_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where amount equals to DEFAULT_AMOUNT
        defaultPaymentsShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the paymentsList where amount equals to UPDATED_AMOUNT
        defaultPaymentsShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultPaymentsShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the paymentsList where amount equals to UPDATED_AMOUNT
        defaultPaymentsShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where amount is not null
        defaultPaymentsShouldBeFound("amount.specified=true");

        // Get all the paymentsList where amount is null
        defaultPaymentsShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultPaymentsShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the paymentsList where amount is greater than or equal to UPDATED_AMOUNT
        defaultPaymentsShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where amount is less than or equal to DEFAULT_AMOUNT
        defaultPaymentsShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the paymentsList where amount is less than or equal to SMALLER_AMOUNT
        defaultPaymentsShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where amount is less than DEFAULT_AMOUNT
        defaultPaymentsShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the paymentsList where amount is less than UPDATED_AMOUNT
        defaultPaymentsShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where amount is greater than DEFAULT_AMOUNT
        defaultPaymentsShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the paymentsList where amount is greater than SMALLER_AMOUNT
        defaultPaymentsShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where status equals to DEFAULT_STATUS
        defaultPaymentsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the paymentsList where status equals to UPDATED_STATUS
        defaultPaymentsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPaymentsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the paymentsList where status equals to UPDATED_STATUS
        defaultPaymentsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where status is not null
        defaultPaymentsShouldBeFound("status.specified=true");

        // Get all the paymentsList where status is null
        defaultPaymentsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultPaymentsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the paymentsList where createdDate equals to UPDATED_CREATED_DATE
        defaultPaymentsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultPaymentsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the paymentsList where createdDate equals to UPDATED_CREATED_DATE
        defaultPaymentsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where createdDate is not null
        defaultPaymentsShouldBeFound("createdDate.specified=true");

        // Get all the paymentsList where createdDate is null
        defaultPaymentsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where createdDate is greater than or equal to DEFAULT_CREATED_DATE
        defaultPaymentsShouldBeFound("createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the paymentsList where createdDate is greater than or equal to UPDATED_CREATED_DATE
        defaultPaymentsShouldNotBeFound("createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where createdDate is less than or equal to DEFAULT_CREATED_DATE
        defaultPaymentsShouldBeFound("createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE);

        // Get all the paymentsList where createdDate is less than or equal to SMALLER_CREATED_DATE
        defaultPaymentsShouldNotBeFound("createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where createdDate is less than DEFAULT_CREATED_DATE
        defaultPaymentsShouldNotBeFound("createdDate.lessThan=" + DEFAULT_CREATED_DATE);

        // Get all the paymentsList where createdDate is less than UPDATED_CREATED_DATE
        defaultPaymentsShouldBeFound("createdDate.lessThan=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        // Get all the paymentsList where createdDate is greater than DEFAULT_CREATED_DATE
        defaultPaymentsShouldNotBeFound("createdDate.greaterThan=" + DEFAULT_CREATED_DATE);

        // Get all the paymentsList where createdDate is greater than SMALLER_CREATED_DATE
        defaultPaymentsShouldBeFound("createdDate.greaterThan=" + SMALLER_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByInvoicesIsEqualToSomething() throws Exception {
        Invoices invoices;
        if (TestUtil.findAll(em, Invoices.class).isEmpty()) {
            paymentsRepository.saveAndFlush(payments);
            invoices = InvoicesResourceIT.createEntity(em);
        } else {
            invoices = TestUtil.findAll(em, Invoices.class).get(0);
        }
        em.persist(invoices);
        em.flush();
        payments.setInvoices(invoices);
        paymentsRepository.saveAndFlush(payments);
        Long invoicesId = invoices.getId();

        // Get all the paymentsList where invoices equals to invoicesId
        defaultPaymentsShouldBeFound("invoicesId.equals=" + invoicesId);

        // Get all the paymentsList where invoices equals to (invoicesId + 1)
        defaultPaymentsShouldNotBeFound("invoicesId.equals=" + (invoicesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentsShouldBeFound(String filter) throws Exception {
        restPaymentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payments.getId().intValue())))
            .andExpect(jsonPath("$.[*].refNumber").value(hasItem(DEFAULT_REF_NUMBER)))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].explanation").value(hasItem(DEFAULT_EXPLANATION)))
            .andExpect(jsonPath("$.[*].operationDate").value(hasItem(DEFAULT_OPERATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restPaymentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentsShouldNotBeFound(String filter) throws Exception {
        restPaymentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPayments() throws Exception {
        // Get the payments
        restPaymentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments
        Payments updatedPayments = paymentsRepository.findById(payments.getId()).get();
        // Disconnect from session so that the updates on updatedPayments are not directly saved in db
        em.detach(updatedPayments);
        updatedPayments
            .refNumber(UPDATED_REF_NUMBER)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .contactId(UPDATED_CONTACT_ID)
            .explanation(UPDATED_EXPLANATION)
            .operationDate(UPDATED_OPERATION_DATE)
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE);
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(updatedPayments);

        restPaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getRefNumber()).isEqualTo(UPDATED_REF_NUMBER);
        assertThat(testPayments.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPayments.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testPayments.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testPayments.getOperationDate()).isEqualTo(UPDATED_OPERATION_DATE);
        assertThat(testPayments.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayments.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayments.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentsWithPatch() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments using partial update
        Payments partialUpdatedPayments = new Payments();
        partialUpdatedPayments.setId(payments.getId());

        partialUpdatedPayments
            .refNumber(UPDATED_REF_NUMBER)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .operationDate(UPDATED_OPERATION_DATE)
            .amount(UPDATED_AMOUNT);

        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayments))
            )
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getRefNumber()).isEqualTo(UPDATED_REF_NUMBER);
        assertThat(testPayments.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPayments.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testPayments.getExplanation()).isEqualTo(DEFAULT_EXPLANATION);
        assertThat(testPayments.getOperationDate()).isEqualTo(UPDATED_OPERATION_DATE);
        assertThat(testPayments.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayments.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPayments.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePaymentsWithPatch() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();

        // Update the payments using partial update
        Payments partialUpdatedPayments = new Payments();
        partialUpdatedPayments.setId(payments.getId());

        partialUpdatedPayments
            .refNumber(UPDATED_REF_NUMBER)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .contactId(UPDATED_CONTACT_ID)
            .explanation(UPDATED_EXPLANATION)
            .operationDate(UPDATED_OPERATION_DATE)
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE);

        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayments))
            )
            .andExpect(status().isOk());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
        Payments testPayments = paymentsList.get(paymentsList.size() - 1);
        assertThat(testPayments.getRefNumber()).isEqualTo(UPDATED_REF_NUMBER);
        assertThat(testPayments.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPayments.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testPayments.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testPayments.getOperationDate()).isEqualTo(UPDATED_OPERATION_DATE);
        assertThat(testPayments.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayments.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayments.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayments() throws Exception {
        int databaseSizeBeforeUpdate = paymentsRepository.findAll().size();
        payments.setId(count.incrementAndGet());

        // Create the Payments
        PaymentsDTO paymentsDTO = paymentsMapper.toDto(payments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payments in the database
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayments() throws Exception {
        // Initialize the database
        paymentsRepository.saveAndFlush(payments);

        int databaseSizeBeforeDelete = paymentsRepository.findAll().size();

        // Delete the payments
        restPaymentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, payments.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payments> paymentsList = paymentsRepository.findAll();
        assertThat(paymentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
