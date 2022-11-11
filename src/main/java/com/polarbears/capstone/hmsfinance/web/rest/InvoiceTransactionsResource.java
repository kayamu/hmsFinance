package com.polarbears.capstone.hmsfinance.web.rest;

import com.polarbears.capstone.hmsfinance.repository.InvoiceTransactionsRepository;
import com.polarbears.capstone.hmsfinance.service.InvoiceTransactionsQueryService;
import com.polarbears.capstone.hmsfinance.service.InvoiceTransactionsService;
import com.polarbears.capstone.hmsfinance.service.criteria.InvoiceTransactionsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.InvoiceTransactionsDTO;
import com.polarbears.capstone.hmsfinance.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.polarbears.capstone.hmsfinance.domain.InvoiceTransactions}.
 */
@RestController
@RequestMapping("/api")
public class InvoiceTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceTransactionsResource.class);

    private static final String ENTITY_NAME = "hmsfinanceInvoiceTransactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceTransactionsService invoiceTransactionsService;

    private final InvoiceTransactionsRepository invoiceTransactionsRepository;

    private final InvoiceTransactionsQueryService invoiceTransactionsQueryService;

    public InvoiceTransactionsResource(
        InvoiceTransactionsService invoiceTransactionsService,
        InvoiceTransactionsRepository invoiceTransactionsRepository,
        InvoiceTransactionsQueryService invoiceTransactionsQueryService
    ) {
        this.invoiceTransactionsService = invoiceTransactionsService;
        this.invoiceTransactionsRepository = invoiceTransactionsRepository;
        this.invoiceTransactionsQueryService = invoiceTransactionsQueryService;
    }

    /**
     * {@code POST  /invoice-transactions} : Create a new invoiceTransactions.
     *
     * @param invoiceTransactionsDTO the invoiceTransactionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceTransactionsDTO, or with status {@code 400 (Bad Request)} if the invoiceTransactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoice-transactions")
    public ResponseEntity<InvoiceTransactionsDTO> createInvoiceTransactions(@RequestBody InvoiceTransactionsDTO invoiceTransactionsDTO)
        throws URISyntaxException {
        log.debug("REST request to save InvoiceTransactions : {}", invoiceTransactionsDTO);
        if (invoiceTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new invoiceTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceTransactionsDTO result = invoiceTransactionsService.save(invoiceTransactionsDTO);
        return ResponseEntity
            .created(new URI("/api/invoice-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoice-transactions/:id} : Updates an existing invoiceTransactions.
     *
     * @param id the id of the invoiceTransactionsDTO to save.
     * @param invoiceTransactionsDTO the invoiceTransactionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceTransactionsDTO,
     * or with status {@code 400 (Bad Request)} if the invoiceTransactionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceTransactionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoice-transactions/{id}")
    public ResponseEntity<InvoiceTransactionsDTO> updateInvoiceTransactions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InvoiceTransactionsDTO invoiceTransactionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InvoiceTransactions : {}, {}", id, invoiceTransactionsDTO);
        if (invoiceTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoiceTransactionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoiceTransactionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InvoiceTransactionsDTO result = invoiceTransactionsService.update(invoiceTransactionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /invoice-transactions/:id} : Partial updates given fields of an existing invoiceTransactions, field will ignore if it is null
     *
     * @param id the id of the invoiceTransactionsDTO to save.
     * @param invoiceTransactionsDTO the invoiceTransactionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceTransactionsDTO,
     * or with status {@code 400 (Bad Request)} if the invoiceTransactionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the invoiceTransactionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the invoiceTransactionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/invoice-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InvoiceTransactionsDTO> partialUpdateInvoiceTransactions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InvoiceTransactionsDTO invoiceTransactionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InvoiceTransactions partially : {}, {}", id, invoiceTransactionsDTO);
        if (invoiceTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoiceTransactionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoiceTransactionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InvoiceTransactionsDTO> result = invoiceTransactionsService.partialUpdate(invoiceTransactionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceTransactionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /invoice-transactions} : get all the invoiceTransactions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceTransactions in body.
     */
    @GetMapping("/invoice-transactions")
    public ResponseEntity<List<InvoiceTransactionsDTO>> getAllInvoiceTransactions(
        InvoiceTransactionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get InvoiceTransactions by criteria: {}", criteria);
        Page<InvoiceTransactionsDTO> page = invoiceTransactionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invoice-transactions/count} : count all the invoiceTransactions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/invoice-transactions/count")
    public ResponseEntity<Long> countInvoiceTransactions(InvoiceTransactionsCriteria criteria) {
        log.debug("REST request to count InvoiceTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(invoiceTransactionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /invoice-transactions/:id} : get the "id" invoiceTransactions.
     *
     * @param id the id of the invoiceTransactionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceTransactionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoice-transactions/{id}")
    public ResponseEntity<InvoiceTransactionsDTO> getInvoiceTransactions(@PathVariable Long id) {
        log.debug("REST request to get InvoiceTransactions : {}", id);
        Optional<InvoiceTransactionsDTO> invoiceTransactionsDTO = invoiceTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceTransactionsDTO);
    }

    /**
     * {@code DELETE  /invoice-transactions/:id} : delete the "id" invoiceTransactions.
     *
     * @param id the id of the invoiceTransactionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoice-transactions/{id}")
    public ResponseEntity<Void> deleteInvoiceTransactions(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceTransactions : {}", id);
        invoiceTransactionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
