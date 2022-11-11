package com.polarbears.capstone.hmsfinance.web.rest;

import com.polarbears.capstone.hmsfinance.repository.InvoiceDetailsRepository;
import com.polarbears.capstone.hmsfinance.service.InvoiceDetailsQueryService;
import com.polarbears.capstone.hmsfinance.service.InvoiceDetailsService;
import com.polarbears.capstone.hmsfinance.service.criteria.InvoiceDetailsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.InvoiceDetailsDTO;
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
 * REST controller for managing {@link com.polarbears.capstone.hmsfinance.domain.InvoiceDetails}.
 */
@RestController
@RequestMapping("/api")
public class InvoiceDetailsResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceDetailsResource.class);

    private static final String ENTITY_NAME = "hmsfinanceInvoiceDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoiceDetailsService invoiceDetailsService;

    private final InvoiceDetailsRepository invoiceDetailsRepository;

    private final InvoiceDetailsQueryService invoiceDetailsQueryService;

    public InvoiceDetailsResource(
        InvoiceDetailsService invoiceDetailsService,
        InvoiceDetailsRepository invoiceDetailsRepository,
        InvoiceDetailsQueryService invoiceDetailsQueryService
    ) {
        this.invoiceDetailsService = invoiceDetailsService;
        this.invoiceDetailsRepository = invoiceDetailsRepository;
        this.invoiceDetailsQueryService = invoiceDetailsQueryService;
    }

    /**
     * {@code POST  /invoice-details} : Create a new invoiceDetails.
     *
     * @param invoiceDetailsDTO the invoiceDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoiceDetailsDTO, or with status {@code 400 (Bad Request)} if the invoiceDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoice-details")
    public ResponseEntity<InvoiceDetailsDTO> createInvoiceDetails(@RequestBody InvoiceDetailsDTO invoiceDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save InvoiceDetails : {}", invoiceDetailsDTO);
        if (invoiceDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new invoiceDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceDetailsDTO result = invoiceDetailsService.save(invoiceDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/invoice-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoice-details/:id} : Updates an existing invoiceDetails.
     *
     * @param id the id of the invoiceDetailsDTO to save.
     * @param invoiceDetailsDTO the invoiceDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the invoiceDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoiceDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoice-details/{id}")
    public ResponseEntity<InvoiceDetailsDTO> updateInvoiceDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InvoiceDetailsDTO invoiceDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InvoiceDetails : {}, {}", id, invoiceDetailsDTO);
        if (invoiceDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoiceDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoiceDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InvoiceDetailsDTO result = invoiceDetailsService.update(invoiceDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /invoice-details/:id} : Partial updates given fields of an existing invoiceDetails, field will ignore if it is null
     *
     * @param id the id of the invoiceDetailsDTO to save.
     * @param invoiceDetailsDTO the invoiceDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoiceDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the invoiceDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the invoiceDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the invoiceDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/invoice-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InvoiceDetailsDTO> partialUpdateInvoiceDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InvoiceDetailsDTO invoiceDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InvoiceDetails partially : {}, {}", id, invoiceDetailsDTO);
        if (invoiceDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoiceDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoiceDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InvoiceDetailsDTO> result = invoiceDetailsService.partialUpdate(invoiceDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoiceDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /invoice-details} : get all the invoiceDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoiceDetails in body.
     */
    @GetMapping("/invoice-details")
    public ResponseEntity<List<InvoiceDetailsDTO>> getAllInvoiceDetails(
        InvoiceDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get InvoiceDetails by criteria: {}", criteria);
        Page<InvoiceDetailsDTO> page = invoiceDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invoice-details/count} : count all the invoiceDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/invoice-details/count")
    public ResponseEntity<Long> countInvoiceDetails(InvoiceDetailsCriteria criteria) {
        log.debug("REST request to count InvoiceDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(invoiceDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /invoice-details/:id} : get the "id" invoiceDetails.
     *
     * @param id the id of the invoiceDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoiceDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoice-details/{id}")
    public ResponseEntity<InvoiceDetailsDTO> getInvoiceDetails(@PathVariable Long id) {
        log.debug("REST request to get InvoiceDetails : {}", id);
        Optional<InvoiceDetailsDTO> invoiceDetailsDTO = invoiceDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceDetailsDTO);
    }

    /**
     * {@code DELETE  /invoice-details/:id} : delete the "id" invoiceDetails.
     *
     * @param id the id of the invoiceDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoice-details/{id}")
    public ResponseEntity<Void> deleteInvoiceDetails(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceDetails : {}", id);
        invoiceDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
