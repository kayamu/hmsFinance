package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.*; // for static metamodels
import com.polarbears.capstone.hmsfinance.domain.Items;
import com.polarbears.capstone.hmsfinance.repository.ItemsRepository;
import com.polarbears.capstone.hmsfinance.service.criteria.ItemsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.ItemsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.ItemsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Items} entities in the database.
 * The main input is a {@link ItemsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ItemsDTO} or a {@link Page} of {@link ItemsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ItemsQueryService extends QueryService<Items> {

    private final Logger log = LoggerFactory.getLogger(ItemsQueryService.class);

    private final ItemsRepository itemsRepository;

    private final ItemsMapper itemsMapper;

    public ItemsQueryService(ItemsRepository itemsRepository, ItemsMapper itemsMapper) {
        this.itemsRepository = itemsRepository;
        this.itemsMapper = itemsMapper;
    }

    /**
     * Return a {@link List} of {@link ItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ItemsDTO> findByCriteria(ItemsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Items> specification = createSpecification(criteria);
        return itemsMapper.toDto(itemsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ItemsDTO> findByCriteria(ItemsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Items> specification = createSpecification(criteria);
        return itemsRepository.findAll(specification, page).map(itemsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ItemsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Items> specification = createSpecification(criteria);
        return itemsRepository.count(specification);
    }

    /**
     * Function to convert {@link ItemsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Items> createSpecification(ItemsCriteria criteria) {
        Specification<Items> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Items_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Items_.name));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemId(), Items_.itemId));
            }
            if (criteria.getItemCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemCode(), Items_.itemCode));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Items_.type));
            }
            if (criteria.getExplain() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExplain(), Items_.explain));
            }
            if (criteria.getCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCost(), Items_.cost));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Items_.price));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Items_.isActive));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Items_.createdDate));
            }
            if (criteria.getTemplatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTemplatesId(), root -> root.join(Items_.templates, JoinType.LEFT).get(Templates_.id))
                    );
            }
        }
        return specification;
    }
}
