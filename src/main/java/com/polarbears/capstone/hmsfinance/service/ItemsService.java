package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.Items;
import com.polarbears.capstone.hmsfinance.repository.ItemsRepository;
import com.polarbears.capstone.hmsfinance.service.dto.ItemsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.ItemsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Items}.
 */
@Service
@Transactional
public class ItemsService {

    private final Logger log = LoggerFactory.getLogger(ItemsService.class);

    private final ItemsRepository itemsRepository;

    private final ItemsMapper itemsMapper;

    public ItemsService(ItemsRepository itemsRepository, ItemsMapper itemsMapper) {
        this.itemsRepository = itemsRepository;
        this.itemsMapper = itemsMapper;
    }

    /**
     * Save a items.
     *
     * @param itemsDTO the entity to save.
     * @return the persisted entity.
     */
    public ItemsDTO save(ItemsDTO itemsDTO) {
        log.debug("Request to save Items : {}", itemsDTO);
        Items items = itemsMapper.toEntity(itemsDTO);
        items = itemsRepository.save(items);
        return itemsMapper.toDto(items);
    }

    /**
     * Update a items.
     *
     * @param itemsDTO the entity to save.
     * @return the persisted entity.
     */
    public ItemsDTO update(ItemsDTO itemsDTO) {
        log.debug("Request to update Items : {}", itemsDTO);
        Items items = itemsMapper.toEntity(itemsDTO);
        items = itemsRepository.save(items);
        return itemsMapper.toDto(items);
    }

    /**
     * Partially update a items.
     *
     * @param itemsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ItemsDTO> partialUpdate(ItemsDTO itemsDTO) {
        log.debug("Request to partially update Items : {}", itemsDTO);

        return itemsRepository
            .findById(itemsDTO.getId())
            .map(existingItems -> {
                itemsMapper.partialUpdate(existingItems, itemsDTO);

                return existingItems;
            })
            .map(itemsRepository::save)
            .map(itemsMapper::toDto);
    }

    /**
     * Get all the items.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Items");
        return itemsRepository.findAll(pageable).map(itemsMapper::toDto);
    }

    /**
     * Get all the items with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ItemsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return itemsRepository.findAllWithEagerRelationships(pageable).map(itemsMapper::toDto);
    }

    /**
     * Get one items by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ItemsDTO> findOne(Long id) {
        log.debug("Request to get Items : {}", id);
        return itemsRepository.findOneWithEagerRelationships(id).map(itemsMapper::toDto);
    }

    /**
     * Delete the items by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Items : {}", id);
        itemsRepository.deleteById(id);
    }
}
