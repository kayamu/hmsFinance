package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.TemplateItems;
import com.polarbears.capstone.hmsfinance.repository.TemplateItemsRepository;
import com.polarbears.capstone.hmsfinance.service.dto.TemplateItemsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.TemplateItemsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TemplateItems}.
 */
@Service
@Transactional
public class TemplateItemsService {

    private final Logger log = LoggerFactory.getLogger(TemplateItemsService.class);

    private final TemplateItemsRepository templateItemsRepository;

    private final TemplateItemsMapper templateItemsMapper;

    public TemplateItemsService(TemplateItemsRepository templateItemsRepository, TemplateItemsMapper templateItemsMapper) {
        this.templateItemsRepository = templateItemsRepository;
        this.templateItemsMapper = templateItemsMapper;
    }

    /**
     * Save a templateItems.
     *
     * @param templateItemsDTO the entity to save.
     * @return the persisted entity.
     */
    public TemplateItemsDTO save(TemplateItemsDTO templateItemsDTO) {
        log.debug("Request to save TemplateItems : {}", templateItemsDTO);
        TemplateItems templateItems = templateItemsMapper.toEntity(templateItemsDTO);
        templateItems = templateItemsRepository.save(templateItems);
        return templateItemsMapper.toDto(templateItems);
    }

    /**
     * Update a templateItems.
     *
     * @param templateItemsDTO the entity to save.
     * @return the persisted entity.
     */
    public TemplateItemsDTO update(TemplateItemsDTO templateItemsDTO) {
        log.debug("Request to update TemplateItems : {}", templateItemsDTO);
        TemplateItems templateItems = templateItemsMapper.toEntity(templateItemsDTO);
        templateItems = templateItemsRepository.save(templateItems);
        return templateItemsMapper.toDto(templateItems);
    }

    /**
     * Partially update a templateItems.
     *
     * @param templateItemsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TemplateItemsDTO> partialUpdate(TemplateItemsDTO templateItemsDTO) {
        log.debug("Request to partially update TemplateItems : {}", templateItemsDTO);

        return templateItemsRepository
            .findById(templateItemsDTO.getId())
            .map(existingTemplateItems -> {
                templateItemsMapper.partialUpdate(existingTemplateItems, templateItemsDTO);

                return existingTemplateItems;
            })
            .map(templateItemsRepository::save)
            .map(templateItemsMapper::toDto);
    }

    /**
     * Get all the templateItems.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TemplateItemsDTO> findAll() {
        log.debug("Request to get all TemplateItems");
        return templateItemsRepository.findAll().stream().map(templateItemsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the templateItems with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TemplateItemsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return templateItemsRepository.findAllWithEagerRelationships(pageable).map(templateItemsMapper::toDto);
    }

    /**
     * Get one templateItems by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TemplateItemsDTO> findOne(Long id) {
        log.debug("Request to get TemplateItems : {}", id);
        return templateItemsRepository.findOneWithEagerRelationships(id).map(templateItemsMapper::toDto);
    }

    /**
     * Delete the templateItems by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TemplateItems : {}", id);
        templateItemsRepository.deleteById(id);
    }
}
