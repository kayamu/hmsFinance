package com.polarbears.capstone.hmsfinance.web.rest;

import com.polarbears.capstone.hmsfinance.repository.ItemsRepository;
import com.polarbears.capstone.hmsfinance.service.ItemsQueryService;
import com.polarbears.capstone.hmsfinance.service.ItemsService;
import com.polarbears.capstone.hmsfinance.service.criteria.ItemsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.ItemsDTO;
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
 * REST controller for managing {@link com.polarbears.capstone.hmsfinance.domain.Items}.
 */
@RestController
@RequestMapping("/api")
public class ItemsResource {

    private final Logger log = LoggerFactory.getLogger(ItemsResource.class);

    private static final String ENTITY_NAME = "hmsfinanceItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemsService itemsService;

    private final ItemsRepository itemsRepository;

    private final ItemsQueryService itemsQueryService;

    public ItemsResource(ItemsService itemsService, ItemsRepository itemsRepository, ItemsQueryService itemsQueryService) {
        this.itemsService = itemsService;
        this.itemsRepository = itemsRepository;
        this.itemsQueryService = itemsQueryService;
    }

    /**
     * {@code POST  /items} : Create a new items.
     *
     * @param itemsDTO the itemsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemsDTO, or with status {@code 400 (Bad Request)} if the items has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/items")
    public ResponseEntity<ItemsDTO> createItems(@RequestBody ItemsDTO itemsDTO) throws URISyntaxException {
        log.debug("REST request to save Items : {}", itemsDTO);
        if (itemsDTO.getId() != null) {
            throw new BadRequestAlertException("A new items cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemsDTO result = itemsService.save(itemsDTO);
        return ResponseEntity
            .created(new URI("/api/items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /items/:id} : Updates an existing items.
     *
     * @param id the id of the itemsDTO to save.
     * @param itemsDTO the itemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemsDTO,
     * or with status {@code 400 (Bad Request)} if the itemsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/items/{id}")
    public ResponseEntity<ItemsDTO> updateItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ItemsDTO itemsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Items : {}, {}", id, itemsDTO);
        if (itemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ItemsDTO result = itemsService.update(itemsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /items/:id} : Partial updates given fields of an existing items, field will ignore if it is null
     *
     * @param id the id of the itemsDTO to save.
     * @param itemsDTO the itemsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemsDTO,
     * or with status {@code 400 (Bad Request)} if the itemsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the itemsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the itemsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ItemsDTO> partialUpdateItems(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ItemsDTO itemsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Items partially : {}, {}", id, itemsDTO);
        if (itemsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, itemsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!itemsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ItemsDTO> result = itemsService.partialUpdate(itemsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, itemsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /items} : get all the items.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of items in body.
     */
    @GetMapping("/items")
    public ResponseEntity<List<ItemsDTO>> getAllItems(
        ItemsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Items by criteria: {}", criteria);
        Page<ItemsDTO> page = itemsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /items/count} : count all the items.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/items/count")
    public ResponseEntity<Long> countItems(ItemsCriteria criteria) {
        log.debug("REST request to count Items by criteria: {}", criteria);
        return ResponseEntity.ok().body(itemsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /items/:id} : get the "id" items.
     *
     * @param id the id of the itemsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/items/{id}")
    public ResponseEntity<ItemsDTO> getItems(@PathVariable Long id) {
        log.debug("REST request to get Items : {}", id);
        Optional<ItemsDTO> itemsDTO = itemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(itemsDTO);
    }

    /**
     * {@code DELETE  /items/:id} : delete the "id" items.
     *
     * @param id the id of the itemsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItems(@PathVariable Long id) {
        log.debug("REST request to delete Items : {}", id);
        itemsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
