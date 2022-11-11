package com.polarbears.capstone.hmsfinance.web.rest;

import com.polarbears.capstone.hmsfinance.repository.ConditionsRepository;
import com.polarbears.capstone.hmsfinance.service.ConditionsQueryService;
import com.polarbears.capstone.hmsfinance.service.ConditionsService;
import com.polarbears.capstone.hmsfinance.service.criteria.ConditionsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.ConditionsDTO;
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
 * REST controller for managing {@link com.polarbears.capstone.hmsfinance.domain.Conditions}.
 */
@RestController
@RequestMapping("/api")
public class ConditionsResource {

    private final Logger log = LoggerFactory.getLogger(ConditionsResource.class);

    private static final String ENTITY_NAME = "hmsfinanceConditions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConditionsService conditionsService;

    private final ConditionsRepository conditionsRepository;

    private final ConditionsQueryService conditionsQueryService;

    public ConditionsResource(
        ConditionsService conditionsService,
        ConditionsRepository conditionsRepository,
        ConditionsQueryService conditionsQueryService
    ) {
        this.conditionsService = conditionsService;
        this.conditionsRepository = conditionsRepository;
        this.conditionsQueryService = conditionsQueryService;
    }

    /**
     * {@code POST  /conditions} : Create a new conditions.
     *
     * @param conditionsDTO the conditionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conditionsDTO, or with status {@code 400 (Bad Request)} if the conditions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conditions")
    public ResponseEntity<ConditionsDTO> createConditions(@RequestBody ConditionsDTO conditionsDTO) throws URISyntaxException {
        log.debug("REST request to save Conditions : {}", conditionsDTO);
        if (conditionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new conditions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConditionsDTO result = conditionsService.save(conditionsDTO);
        return ResponseEntity
            .created(new URI("/api/conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conditions/:id} : Updates an existing conditions.
     *
     * @param id the id of the conditionsDTO to save.
     * @param conditionsDTO the conditionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conditionsDTO,
     * or with status {@code 400 (Bad Request)} if the conditionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conditionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conditions/{id}")
    public ResponseEntity<ConditionsDTO> updateConditions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConditionsDTO conditionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Conditions : {}, {}", id, conditionsDTO);
        if (conditionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conditionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conditionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConditionsDTO result = conditionsService.update(conditionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conditionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /conditions/:id} : Partial updates given fields of an existing conditions, field will ignore if it is null
     *
     * @param id the id of the conditionsDTO to save.
     * @param conditionsDTO the conditionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conditionsDTO,
     * or with status {@code 400 (Bad Request)} if the conditionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the conditionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the conditionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/conditions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConditionsDTO> partialUpdateConditions(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ConditionsDTO conditionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Conditions partially : {}, {}", id, conditionsDTO);
        if (conditionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conditionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conditionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConditionsDTO> result = conditionsService.partialUpdate(conditionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conditionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /conditions} : get all the conditions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conditions in body.
     */
    @GetMapping("/conditions")
    public ResponseEntity<List<ConditionsDTO>> getAllConditions(ConditionsCriteria criteria) {
        log.debug("REST request to get Conditions by criteria: {}", criteria);
        List<ConditionsDTO> entityList = conditionsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /conditions/count} : count all the conditions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/conditions/count")
    public ResponseEntity<Long> countConditions(ConditionsCriteria criteria) {
        log.debug("REST request to count Conditions by criteria: {}", criteria);
        return ResponseEntity.ok().body(conditionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /conditions/:id} : get the "id" conditions.
     *
     * @param id the id of the conditionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conditionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conditions/{id}")
    public ResponseEntity<ConditionsDTO> getConditions(@PathVariable Long id) {
        log.debug("REST request to get Conditions : {}", id);
        Optional<ConditionsDTO> conditionsDTO = conditionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conditionsDTO);
    }

    /**
     * {@code DELETE  /conditions/:id} : delete the "id" conditions.
     *
     * @param id the id of the conditionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conditions/{id}")
    public ResponseEntity<Void> deleteConditions(@PathVariable Long id) {
        log.debug("REST request to delete Conditions : {}", id);
        conditionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
