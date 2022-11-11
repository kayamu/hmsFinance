package com.polarbears.capstone.hmsfinance.web.rest;

import com.polarbears.capstone.hmsfinance.repository.TemplateItemsRepository;
import com.polarbears.capstone.hmsfinance.service.TemplateItemsQueryService;
import com.polarbears.capstone.hmsfinance.service.TemplateItemsService;
import com.polarbears.capstone.hmsfinance.service.criteria.TemplateItemsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.TemplateItemsDTO;
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
 * REST controller for managing {@link com.polarbears.capstone.hmsfinance.domain.TemplateItems}.
 */
@RestController
@RequestMapping("/api")
public class TemplateItemsResource {

    private final Logger log = LoggerFactory.getLogger(TemplateItemsResource.class);

    private static final String ENTITY_NAME = "hmsfinanceTemplateItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplateItemsService templateItemsService;

    private final TemplateItemsRepository templateItemsRepository;

    private final TemplateItemsQueryService templateItemsQueryService;

    public TemplateItemsResource(
        TemplateItemsService templateItemsService,
        TemplateItemsRepository templateItemsRepository,
        TemplateItemsQueryService templateItemsQueryService
    ) {
        this.templateItemsService = templateItemsService;
        this.templateItemsRepository = templateItemsRepository;
        this.templateItemsQueryService = templateItemsQueryService;
    }

    /**
     * {@code POST  /template-items} : Create a new templateItems.
     *
     * @param templateItemsDTO the templateItemsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templateItemsDTO, or with status {@code 400 (Bad Request)} if the templateItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/template-items")
    public ResponseEntity<TemplateItemsDTO> createTemplateItems(@RequestBody TemplateItemsDTO templateItemsDTO) throws URISyntaxException {
        log.debug("REST request to save TemplateItems : {}", templateItemsDTO);
        if (templateItemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new templateItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplateItemsDTO result = templateItemsService.save(templateItemsDTO);
        return ResponseEntity
            .created(new URI("/api/template-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /template-items/:id} : Updates an existing templateItems.
     *
     * @param id the id of the templateItemsDTO to save.
     * @param templateItemsDTO the templateItemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateItemsDTO,
     * or with status {@code 400 (Bad Request)} if the templateItemsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templateItemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/template-items/{id}")
    public ResponseEntity<TemplateItemsDTO> updateTemplateItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TemplateItemsDTO templateItemsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TemplateItems : {}, {}", id, templateItemsDTO);
        if (templateItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateItemsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TemplateItemsDTO result = templateItemsService.update(templateItemsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateItemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /template-items/:id} : Partial updates given fields of an existing templateItems, field will ignore if it is null
     *
     * @param id the id of the templateItemsDTO to save.
     * @param templateItemsDTO the templateItemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templateItemsDTO,
     * or with status {@code 400 (Bad Request)} if the templateItemsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the templateItemsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the templateItemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/template-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TemplateItemsDTO> partialUpdateTemplateItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TemplateItemsDTO templateItemsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TemplateItems partially : {}, {}", id, templateItemsDTO);
        if (templateItemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templateItemsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templateItemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TemplateItemsDTO> result = templateItemsService.partialUpdate(templateItemsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templateItemsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /template-items} : get all the templateItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templateItems in body.
     */
    @GetMapping("/template-items")
    public ResponseEntity<List<TemplateItemsDTO>> getAllTemplateItems(TemplateItemsCriteria criteria) {
        log.debug("REST request to get TemplateItems by criteria: {}", criteria);
        List<TemplateItemsDTO> entityList = templateItemsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /template-items/count} : count all the templateItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/template-items/count")
    public ResponseEntity<Long> countTemplateItems(TemplateItemsCriteria criteria) {
        log.debug("REST request to count TemplateItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(templateItemsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /template-items/:id} : get the "id" templateItems.
     *
     * @param id the id of the templateItemsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templateItemsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/template-items/{id}")
    public ResponseEntity<TemplateItemsDTO> getTemplateItems(@PathVariable Long id) {
        log.debug("REST request to get TemplateItems : {}", id);
        Optional<TemplateItemsDTO> templateItemsDTO = templateItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templateItemsDTO);
    }

    /**
     * {@code DELETE  /template-items/:id} : delete the "id" templateItems.
     *
     * @param id the id of the templateItemsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/template-items/{id}")
    public ResponseEntity<Void> deleteTemplateItems(@PathVariable Long id) {
        log.debug("REST request to delete TemplateItems : {}", id);
        templateItemsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
