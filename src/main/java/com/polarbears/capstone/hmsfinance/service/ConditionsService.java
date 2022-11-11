package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.Conditions;
import com.polarbears.capstone.hmsfinance.repository.ConditionsRepository;
import com.polarbears.capstone.hmsfinance.service.dto.ConditionsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.ConditionsMapper;
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
 * Service Implementation for managing {@link Conditions}.
 */
@Service
@Transactional
public class ConditionsService {

    private final Logger log = LoggerFactory.getLogger(ConditionsService.class);

    private final ConditionsRepository conditionsRepository;

    private final ConditionsMapper conditionsMapper;

    public ConditionsService(ConditionsRepository conditionsRepository, ConditionsMapper conditionsMapper) {
        this.conditionsRepository = conditionsRepository;
        this.conditionsMapper = conditionsMapper;
    }

    /**
     * Save a conditions.
     *
     * @param conditionsDTO the entity to save.
     * @return the persisted entity.
     */
    public ConditionsDTO save(ConditionsDTO conditionsDTO) {
        log.debug("Request to save Conditions : {}", conditionsDTO);
        Conditions conditions = conditionsMapper.toEntity(conditionsDTO);
        conditions = conditionsRepository.save(conditions);
        return conditionsMapper.toDto(conditions);
    }

    /**
     * Update a conditions.
     *
     * @param conditionsDTO the entity to save.
     * @return the persisted entity.
     */
    public ConditionsDTO update(ConditionsDTO conditionsDTO) {
        log.debug("Request to update Conditions : {}", conditionsDTO);
        Conditions conditions = conditionsMapper.toEntity(conditionsDTO);
        conditions = conditionsRepository.save(conditions);
        return conditionsMapper.toDto(conditions);
    }

    /**
     * Partially update a conditions.
     *
     * @param conditionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConditionsDTO> partialUpdate(ConditionsDTO conditionsDTO) {
        log.debug("Request to partially update Conditions : {}", conditionsDTO);

        return conditionsRepository
            .findById(conditionsDTO.getId())
            .map(existingConditions -> {
                conditionsMapper.partialUpdate(existingConditions, conditionsDTO);

                return existingConditions;
            })
            .map(conditionsRepository::save)
            .map(conditionsMapper::toDto);
    }

    /**
     * Get all the conditions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ConditionsDTO> findAll() {
        log.debug("Request to get all Conditions");
        return conditionsRepository.findAll().stream().map(conditionsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the conditions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ConditionsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return conditionsRepository.findAllWithEagerRelationships(pageable).map(conditionsMapper::toDto);
    }

    /**
     * Get one conditions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConditionsDTO> findOne(Long id) {
        log.debug("Request to get Conditions : {}", id);
        return conditionsRepository.findOneWithEagerRelationships(id).map(conditionsMapper::toDto);
    }

    /**
     * Delete the conditions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Conditions : {}", id);
        conditionsRepository.deleteById(id);
    }
}
