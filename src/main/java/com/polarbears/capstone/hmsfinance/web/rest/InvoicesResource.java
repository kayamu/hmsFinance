package com.polarbears.capstone.hmsfinance.web.rest;

import com.polarbears.capstone.hmsfinance.repository.InvoicesRepository;
import com.polarbears.capstone.hmsfinance.service.InvoicesQueryService;
import com.polarbears.capstone.hmsfinance.service.InvoicesService;
import com.polarbears.capstone.hmsfinance.service.criteria.InvoicesCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.InvoicesDTO;
import com.polarbears.capstone.hmsfinance.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.polarbears.capstone.hmsfinance.domain.Invoices}.
 */
@RestController
@RequestMapping("/api")
public class InvoicesResource {

    private final Logger log = LoggerFactory.getLogger(InvoicesResource.class);

    private static final String ENTITY_NAME = "hmsfinanceInvoices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvoicesService invoicesService;

    private final InvoicesRepository invoicesRepository;

    private final InvoicesQueryService invoicesQueryService;

    public InvoicesResource(
        InvoicesService invoicesService,
        InvoicesRepository invoicesRepository,
        InvoicesQueryService invoicesQueryService
    ) {
        this.invoicesService = invoicesService;
        this.invoicesRepository = invoicesRepository;
        this.invoicesQueryService = invoicesQueryService;
    }

    /**
     * {@code POST  /invoices} : Create a new invoices.
     *
     * @param invoicesDTO the invoicesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invoicesDTO, or with status {@code 400 (Bad Request)} if the invoices has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invoices")
    public ResponseEntity<InvoicesDTO> createInvoices(@Valid @RequestBody InvoicesDTO invoicesDTO) throws URISyntaxException {
        log.debug("REST request to save Invoices : {}", invoicesDTO);
        if (invoicesDTO.getId() != null) {
            throw new BadRequestAlertException("A new invoices cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoicesDTO result = invoicesService.save(invoicesDTO);
        return ResponseEntity
            .created(new URI("/api/invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invoices/:id} : Updates an existing invoices.
     *
     * @param id the id of the invoicesDTO to save.
     * @param invoicesDTO the invoicesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoicesDTO,
     * or with status {@code 400 (Bad Request)} if the invoicesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invoicesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invoices/{id}")
    public ResponseEntity<InvoicesDTO> updateInvoices(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InvoicesDTO invoicesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Invoices : {}, {}", id, invoicesDTO);
        if (invoicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoicesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoicesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InvoicesDTO result = invoicesService.update(invoicesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoicesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /invoices/:id} : Partial updates given fields of an existing invoices, field will ignore if it is null
     *
     * @param id the id of the invoicesDTO to save.
     * @param invoicesDTO the invoicesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invoicesDTO,
     * or with status {@code 400 (Bad Request)} if the invoicesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the invoicesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the invoicesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/invoices/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InvoicesDTO> partialUpdateInvoices(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InvoicesDTO invoicesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Invoices partially : {}, {}", id, invoicesDTO);
        if (invoicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, invoicesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!invoicesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InvoicesDTO> result = invoicesService.partialUpdate(invoicesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invoicesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /invoices} : get all the invoices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invoices in body.
     */
    @GetMapping("/invoices")
    public ResponseEntity<List<InvoicesDTO>> getAllInvoices(
        InvoicesCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Invoices by criteria: {}", criteria);
        Page<InvoicesDTO> page = invoicesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invoices/count} : count all the invoices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/invoices/count")
    public ResponseEntity<Long> countInvoices(InvoicesCriteria criteria) {
        log.debug("REST request to count Invoices by criteria: {}", criteria);
        return ResponseEntity.ok().body(invoicesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /invoices/:id} : get the "id" invoices.
     *
     * @param id the id of the invoicesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invoicesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invoices/{id}")
    public ResponseEntity<InvoicesDTO> getInvoices(@PathVariable Long id) {
        log.debug("REST request to get Invoices : {}", id);
        Optional<InvoicesDTO> invoicesDTO = invoicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoicesDTO);
    }

    /**
     * {@code DELETE  /invoices/:id} : delete the "id" invoices.
     *
     * @param id the id of the invoicesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<Void> deleteInvoices(@PathVariable Long id) {
        log.debug("REST request to delete Invoices : {}", id);
        invoicesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
