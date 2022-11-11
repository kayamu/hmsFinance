package com.polarbears.capstone.hmsfinance.web.rest;

import com.polarbears.capstone.hmsfinance.repository.SubItemsRepository;
import com.polarbears.capstone.hmsfinance.service.SubItemsQueryService;
import com.polarbears.capstone.hmsfinance.service.SubItemsService;
import com.polarbears.capstone.hmsfinance.service.criteria.SubItemsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.SubItemsDTO;
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
 * REST controller for managing {@link com.polarbears.capstone.hmsfinance.domain.SubItems}.
 */
@RestController
@RequestMapping("/api")
public class SubItemsResource {

    private final Logger log = LoggerFactory.getLogger(SubItemsResource.class);

    private static final String ENTITY_NAME = "hmsfinanceSubItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubItemsService subItemsService;

    private final SubItemsRepository subItemsRepository;

    private final SubItemsQueryService subItemsQueryService;

    public SubItemsResource(
        SubItemsService subItemsService,
        SubItemsRepository subItemsRepository,
        SubItemsQueryService subItemsQueryService
    ) {
        this.subItemsService = subItemsService;
        this.subItemsRepository = subItemsRepository;
        this.subItemsQueryService = subItemsQueryService;
    }

    /**
     * {@code POST  /sub-items} : Create a new subItems.
     *
     * @param subItemsDTO the subItemsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subItemsDTO, or with status {@code 400 (Bad Request)} if the subItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-items")
    public ResponseEntity<SubItemsDTO> createSubItems(@RequestBody SubItemsDTO subItemsDTO) throws URISyntaxException {
        log.debug("REST request to save SubItems : {}", subItemsDTO);
        if (subItemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new subItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubItemsDTO result = subItemsService.save(subItemsDTO);
        return ResponseEntity
            .created(new URI("/api/sub-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-items/:id} : Updates an existing subItems.
     *
     * @param id the id of the subItemsDTO to save.
     * @param subItemsDTO the subItemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subItemsDTO,
     * or with status {@code 400 (Bad Request)} if the subItemsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subItemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-items/{id}")
    public ResponseEntity<SubItemsDTO> updateSubItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubItemsDTO subItemsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SubItems : {}, {}", id, subItemsDTO);
        if (subItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subItemsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubItemsDTO result = subItemsService.update(subItemsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subItemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sub-items/:id} : Partial updates given fields of an existing subItems, field will ignore if it is null
     *
     * @param id the id of the subItemsDTO to save.
     * @param subItemsDTO the subItemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subItemsDTO,
     * or with status {@code 400 (Bad Request)} if the subItemsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subItemsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subItemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sub-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubItemsDTO> partialUpdateSubItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubItemsDTO subItemsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubItems partially : {}, {}", id, subItemsDTO);
        if (subItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subItemsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubItemsDTO> result = subItemsService.partialUpdate(subItemsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subItemsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-items} : get all the subItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subItems in body.
     */
    @GetMapping("/sub-items")
    public ResponseEntity<List<SubItemsDTO>> getAllSubItems(SubItemsCriteria criteria) {
        log.debug("REST request to get SubItems by criteria: {}", criteria);
        List<SubItemsDTO> entityList = subItemsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /sub-items/count} : count all the subItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sub-items/count")
    public ResponseEntity<Long> countSubItems(SubItemsCriteria criteria) {
        log.debug("REST request to count SubItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(subItemsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sub-items/:id} : get the "id" subItems.
     *
     * @param id the id of the subItemsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subItemsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-items/{id}")
    public ResponseEntity<SubItemsDTO> getSubItems(@PathVariable Long id) {
        log.debug("REST request to get SubItems : {}", id);
        Optional<SubItemsDTO> subItemsDTO = subItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subItemsDTO);
    }

    /**
     * {@code DELETE  /sub-items/:id} : delete the "id" subItems.
     *
     * @param id the id of the subItemsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-items/{id}")
    public ResponseEntity<Void> deleteSubItems(@PathVariable Long id) {
        log.debug("REST request to delete SubItems : {}", id);
        subItemsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
