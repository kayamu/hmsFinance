package com.polarbears.capstone.hmsfinance.web.rest;

import com.polarbears.capstone.hmsfinance.repository.ConditionDetailsRepository;
import com.polarbears.capstone.hmsfinance.service.ConditionDetailsQueryService;
import com.polarbears.capstone.hmsfinance.service.ConditionDetailsService;
import com.polarbears.capstone.hmsfinance.service.criteria.ConditionDetailsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.ConditionDetailsDTO;
import com.polarbears.capstone.hmsfinance.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.polarbears.capstone.hmsfinance.domain.ConditionDetails}.
 */
@RestController
@RequestMapping("/api")
public class ConditionDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ConditionDetailsResource.class);

    private static final String ENTITY_NAME = "hmsfinanceConditionDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConditionDetailsService conditionDetailsService;

    private final ConditionDetailsRepository conditionDetailsRepository;

    private final ConditionDetailsQueryService conditionDetailsQueryService;

    public ConditionDetailsResource(
        ConditionDetailsService conditionDetailsService,
        ConditionDetailsRepository conditionDetailsRepository,
        ConditionDetailsQueryService conditionDetailsQueryService
    ) {
        this.conditionDetailsService = conditionDetailsService;
        this.conditionDetailsRepository = conditionDetailsRepository;
        this.conditionDetailsQueryService = conditionDetailsQueryService;
    }

    /**
     * {@code POST  /condition-details} : Create a new conditionDetails.
     *
     * @param conditionDetailsDTO the conditionDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conditionDetailsDTO, or with status {@code 400 (Bad Request)} if the conditionDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/condition-details")
    public ResponseEntity<ConditionDetailsDTO> createConditionDetails(@RequestBody ConditionDetailsDTO conditionDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save ConditionDetails : {}", conditionDetailsDTO);
        if (conditionDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new conditionDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConditionDetailsDTO result = conditionDetailsService.save(conditionDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/condition-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /condition-details/:id} : Updates an existing conditionDetails.
     *
     * @param id the id of the conditionDetailsDTO to save.
     * @param conditionDetailsDTO the conditionDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conditionDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the conditionDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conditionDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/condition-details/{id}")
    public ResponseEntity<ConditionDetailsDTO> updateConditionDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConditionDetailsDTO conditionDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ConditionDetails : {}, {}", id, conditionDetailsDTO);
        if (conditionDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conditionDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conditionDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConditionDetailsDTO result = conditionDetailsService.update(conditionDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conditionDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /condition-details/:id} : Partial updates given fields of an existing conditionDetails, field will ignore if it is null
     *
     * @param id the id of the conditionDetailsDTO to save.
     * @param conditionDetailsDTO the conditionDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conditionDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the conditionDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the conditionDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the conditionDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/condition-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConditionDetailsDTO> partialUpdateConditionDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConditionDetailsDTO conditionDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConditionDetails partially : {}, {}", id, conditionDetailsDTO);
        if (conditionDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conditionDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conditionDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConditionDetailsDTO> result = conditionDetailsService.partialUpdate(conditionDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conditionDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /condition-details} : get all the conditionDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conditionDetails in body.
     */
    @GetMapping("/condition-details")
    public ResponseEntity<List<ConditionDetailsDTO>> getAllConditionDetails(ConditionDetailsCriteria criteria) {
        log.debug("REST request to get ConditionDetails by criteria: {}", criteria);
        List<ConditionDetailsDTO> entityList = conditionDetailsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /condition-details/count} : count all the conditionDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/condition-details/count")
    public ResponseEntity<Long> countConditionDetails(ConditionDetailsCriteria criteria) {
        log.debug("REST request to count ConditionDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(conditionDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /condition-details/:id} : get the "id" conditionDetails.
     *
     * @param id the id of the conditionDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conditionDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/condition-details/{id}")
    public ResponseEntity<ConditionDetailsDTO> getConditionDetails(@PathVariable Long id) {
        log.debug("REST request to get ConditionDetails : {}", id);
        Optional<ConditionDetailsDTO> conditionDetailsDTO = conditionDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conditionDetailsDTO);
    }

    /**
     * {@code DELETE  /condition-details/:id} : delete the "id" conditionDetails.
     *
     * @param id the id of the conditionDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/condition-details/{id}")
    public ResponseEntity<Void> deleteConditionDetails(@PathVariable Long id) {
        log.debug("REST request to delete ConditionDetails : {}", id);
        conditionDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
