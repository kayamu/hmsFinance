package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.ConditionDetails;
import com.polarbears.capstone.hmsfinance.repository.ConditionDetailsRepository;
import com.polarbears.capstone.hmsfinance.service.dto.ConditionDetailsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.ConditionDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ConditionDetails}.
 */
@Service
@Transactional
public class ConditionDetailsService {

    private final Logger log = LoggerFactory.getLogger(ConditionDetailsService.class);

    private final ConditionDetailsRepository conditionDetailsRepository;

    private final ConditionDetailsMapper conditionDetailsMapper;

    public ConditionDetailsService(ConditionDetailsRepository conditionDetailsRepository, ConditionDetailsMapper conditionDetailsMapper) {
        this.conditionDetailsRepository = conditionDetailsRepository;
        this.conditionDetailsMapper = conditionDetailsMapper;
    }

    /**
     * Save a conditionDetails.
     *
     * @param conditionDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public ConditionDetailsDTO save(ConditionDetailsDTO conditionDetailsDTO) {
        log.debug("Request to save ConditionDetails : {}", conditionDetailsDTO);
        ConditionDetails conditionDetails = conditionDetailsMapper.toEntity(conditionDetailsDTO);
        conditionDetails = conditionDetailsRepository.save(conditionDetails);
        return conditionDetailsMapper.toDto(conditionDetails);
    }

    /**
     * Update a conditionDetails.
     *
     * @param conditionDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public ConditionDetailsDTO update(ConditionDetailsDTO conditionDetailsDTO) {
        log.debug("Request to update ConditionDetails : {}", conditionDetailsDTO);
        ConditionDetails conditionDetails = conditionDetailsMapper.toEntity(conditionDetailsDTO);
        conditionDetails = conditionDetailsRepository.save(conditionDetails);
        return conditionDetailsMapper.toDto(conditionDetails);
    }

    /**
     * Partially update a conditionDetails.
     *
     * @param conditionDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConditionDetailsDTO> partialUpdate(ConditionDetailsDTO conditionDetailsDTO) {
        log.debug("Request to partially update ConditionDetails : {}", conditionDetailsDTO);

        return conditionDetailsRepository
            .findById(conditionDetailsDTO.getId())
            .map(existingConditionDetails -> {
                conditionDetailsMapper.partialUpdate(existingConditionDetails, conditionDetailsDTO);

                return existingConditionDetails;
            })
            .map(conditionDetailsRepository::save)
            .map(conditionDetailsMapper::toDto);
    }

    /**
     * Get all the conditionDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ConditionDetailsDTO> findAll() {
        log.debug("Request to get all ConditionDetails");
        return conditionDetailsRepository
            .findAll()
            .stream()
            .map(conditionDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one conditionDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConditionDetailsDTO> findOne(Long id) {
        log.debug("Request to get ConditionDetails : {}", id);
        return conditionDetailsRepository.findById(id).map(conditionDetailsMapper::toDto);
    }

    /**
     * Delete the conditionDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConditionDetails : {}", id);
        conditionDetailsRepository.deleteById(id);
    }
}
