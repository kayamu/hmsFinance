package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.SubItems;
import com.polarbears.capstone.hmsfinance.repository.SubItemsRepository;
import com.polarbears.capstone.hmsfinance.service.dto.SubItemsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.SubItemsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SubItems}.
 */
@Service
@Transactional
public class SubItemsService {

    private final Logger log = LoggerFactory.getLogger(SubItemsService.class);

    private final SubItemsRepository subItemsRepository;

    private final SubItemsMapper subItemsMapper;

    public SubItemsService(SubItemsRepository subItemsRepository, SubItemsMapper subItemsMapper) {
        this.subItemsRepository = subItemsRepository;
        this.subItemsMapper = subItemsMapper;
    }

    /**
     * Save a subItems.
     *
     * @param subItemsDTO the entity to save.
     * @return the persisted entity.
     */
    public SubItemsDTO save(SubItemsDTO subItemsDTO) {
        log.debug("Request to save SubItems : {}", subItemsDTO);
        SubItems subItems = subItemsMapper.toEntity(subItemsDTO);
        subItems = subItemsRepository.save(subItems);
        return subItemsMapper.toDto(subItems);
    }

    /**
     * Update a subItems.
     *
     * @param subItemsDTO the entity to save.
     * @return the persisted entity.
     */
    public SubItemsDTO update(SubItemsDTO subItemsDTO) {
        log.debug("Request to update SubItems : {}", subItemsDTO);
        SubItems subItems = subItemsMapper.toEntity(subItemsDTO);
        subItems = subItemsRepository.save(subItems);
        return subItemsMapper.toDto(subItems);
    }

    /**
     * Partially update a subItems.
     *
     * @param subItemsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubItemsDTO> partialUpdate(SubItemsDTO subItemsDTO) {
        log.debug("Request to partially update SubItems : {}", subItemsDTO);

        return subItemsRepository
            .findById(subItemsDTO.getId())
            .map(existingSubItems -> {
                subItemsMapper.partialUpdate(existingSubItems, subItemsDTO);

                return existingSubItems;
            })
            .map(subItemsRepository::save)
            .map(subItemsMapper::toDto);
    }

    /**
     * Get all the subItems.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubItemsDTO> findAll() {
        log.debug("Request to get all SubItems");
        return subItemsRepository.findAll().stream().map(subItemsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one subItems by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubItemsDTO> findOne(Long id) {
        log.debug("Request to get SubItems : {}", id);
        return subItemsRepository.findById(id).map(subItemsMapper::toDto);
    }

    /**
     * Delete the subItems by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SubItems : {}", id);
        subItemsRepository.deleteById(id);
    }
}
