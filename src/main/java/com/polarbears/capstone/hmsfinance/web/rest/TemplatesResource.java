package com.polarbears.capstone.hmsfinance.web.rest;

import com.polarbears.capstone.hmsfinance.repository.TemplatesRepository;
import com.polarbears.capstone.hmsfinance.service.TemplatesQueryService;
import com.polarbears.capstone.hmsfinance.service.TemplatesService;
import com.polarbears.capstone.hmsfinance.service.criteria.TemplatesCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.TemplatesDTO;
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
 * REST controller for managing {@link com.polarbears.capstone.hmsfinance.domain.Templates}.
 */
@RestController
@RequestMapping("/api")
public class TemplatesResource {

    private final Logger log = LoggerFactory.getLogger(TemplatesResource.class);

    private static final String ENTITY_NAME = "hmsfinanceTemplates";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TemplatesService templatesService;

    private final TemplatesRepository templatesRepository;

    private final TemplatesQueryService templatesQueryService;

    public TemplatesResource(
        TemplatesService templatesService,
        TemplatesRepository templatesRepository,
        TemplatesQueryService templatesQueryService
    ) {
        this.templatesService = templatesService;
        this.templatesRepository = templatesRepository;
        this.templatesQueryService = templatesQueryService;
    }

    /**
     * {@code POST  /templates} : Create a new templates.
     *
     * @param templatesDTO the templatesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new templatesDTO, or with status {@code 400 (Bad Request)} if the templates has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/templates")
    public ResponseEntity<TemplatesDTO> createTemplates(@RequestBody TemplatesDTO templatesDTO) throws URISyntaxException {
        log.debug("REST request to save Templates : {}", templatesDTO);
        if (templatesDTO.getId() != null) {
            throw new BadRequestAlertException("A new templates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemplatesDTO result = templatesService.save(templatesDTO);
        return ResponseEntity
            .created(new URI("/api/templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /templates/:id} : Updates an existing templates.
     *
     * @param id the id of the templatesDTO to save.
     * @param templatesDTO the templatesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templatesDTO,
     * or with status {@code 400 (Bad Request)} if the templatesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the templatesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/templates/{id}")
    public ResponseEntity<TemplatesDTO> updateTemplates(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TemplatesDTO templatesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Templates : {}, {}", id, templatesDTO);
        if (templatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templatesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TemplatesDTO result = templatesService.update(templatesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /templates/:id} : Partial updates given fields of an existing templates, field will ignore if it is null
     *
     * @param id the id of the templatesDTO to save.
     * @param templatesDTO the templatesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated templatesDTO,
     * or with status {@code 400 (Bad Request)} if the templatesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the templatesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the templatesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/templates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TemplatesDTO> partialUpdateTemplates(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TemplatesDTO templatesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Templates partially : {}, {}", id, templatesDTO);
        if (templatesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, templatesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!templatesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TemplatesDTO> result = templatesService.partialUpdate(templatesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, templatesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /templates} : get all the templates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of templates in body.
     */
    @GetMapping("/templates")
    public ResponseEntity<List<TemplatesDTO>> getAllTemplates(TemplatesCriteria criteria) {
        log.debug("REST request to get Templates by criteria: {}", criteria);
        List<TemplatesDTO> entityList = templatesQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /templates/count} : count all the templates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/templates/count")
    public ResponseEntity<Long> countTemplates(TemplatesCriteria criteria) {
        log.debug("REST request to count Templates by criteria: {}", criteria);
        return ResponseEntity.ok().body(templatesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /templates/:id} : get the "id" templates.
     *
     * @param id the id of the templatesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the templatesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/templates/{id}")
    public ResponseEntity<TemplatesDTO> getTemplates(@PathVariable Long id) {
        log.debug("REST request to get Templates : {}", id);
        Optional<TemplatesDTO> templatesDTO = templatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(templatesDTO);
    }

    /**
     * {@code DELETE  /templates/:id} : delete the "id" templates.
     *
     * @param id the id of the templatesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/templates/{id}")
    public ResponseEntity<Void> deleteTemplates(@PathVariable Long id) {
        log.debug("REST request to delete Templates : {}", id);
        templatesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
