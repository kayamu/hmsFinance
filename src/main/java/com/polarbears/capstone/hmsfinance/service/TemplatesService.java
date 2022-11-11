package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.Templates;
import com.polarbears.capstone.hmsfinance.repository.TemplatesRepository;
import com.polarbears.capstone.hmsfinance.service.dto.TemplatesDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.TemplatesMapper;
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
 * Service Implementation for managing {@link Templates}.
 */
@Service
@Transactional
public class TemplatesService {

    private final Logger log = LoggerFactory.getLogger(TemplatesService.class);

    private final TemplatesRepository templatesRepository;

    private final TemplatesMapper templatesMapper;

    public TemplatesService(TemplatesRepository templatesRepository, TemplatesMapper templatesMapper) {
        this.templatesRepository = templatesRepository;
        this.templatesMapper = templatesMapper;
    }

    /**
     * Save a templates.
     *
     * @param templatesDTO the entity to save.
     * @return the persisted entity.
     */
    public TemplatesDTO save(TemplatesDTO templatesDTO) {
        log.debug("Request to save Templates : {}", templatesDTO);
        Templates templates = templatesMapper.toEntity(templatesDTO);
        templates = templatesRepository.save(templates);
        return templatesMapper.toDto(templates);
    }

    /**
     * Update a templates.
     *
     * @param templatesDTO the entity to save.
     * @return the persisted entity.
     */
    public TemplatesDTO update(TemplatesDTO templatesDTO) {
        log.debug("Request to update Templates : {}", templatesDTO);
        Templates templates = templatesMapper.toEntity(templatesDTO);
        templates = templatesRepository.save(templates);
        return templatesMapper.toDto(templates);
    }

    /**
     * Partially update a templates.
     *
     * @param templatesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TemplatesDTO> partialUpdate(TemplatesDTO templatesDTO) {
        log.debug("Request to partially update Templates : {}", templatesDTO);

        return templatesRepository
            .findById(templatesDTO.getId())
            .map(existingTemplates -> {
                templatesMapper.partialUpdate(existingTemplates, templatesDTO);

                return existingTemplates;
            })
            .map(templatesRepository::save)
            .map(templatesMapper::toDto);
    }

    /**
     * Get all the templates.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TemplatesDTO> findAll() {
        log.debug("Request to get all Templates");
        return templatesRepository.findAll().stream().map(templatesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the templates with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TemplatesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return templatesRepository.findAllWithEagerRelationships(pageable).map(templatesMapper::toDto);
    }

    /**
     * Get one templates by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TemplatesDTO> findOne(Long id) {
        log.debug("Request to get Templates : {}", id);
        return templatesRepository.findOneWithEagerRelationships(id).map(templatesMapper::toDto);
    }

    /**
     * Delete the templates by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Templates : {}", id);
        templatesRepository.deleteById(id);
    }
}
