package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.*; // for static metamodels
import com.polarbears.capstone.hmsfinance.domain.TemplateItems;
import com.polarbears.capstone.hmsfinance.repository.TemplateItemsRepository;
import com.polarbears.capstone.hmsfinance.service.criteria.TemplateItemsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.TemplateItemsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.TemplateItemsMapper;
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
 * Service for executing complex queries for {@link TemplateItems} entities in the database.
 * The main input is a {@link TemplateItemsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TemplateItemsDTO} or a {@link Page} of {@link TemplateItemsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TemplateItemsQueryService extends QueryService<TemplateItems> {

    private final Logger log = LoggerFactory.getLogger(TemplateItemsQueryService.class);

    private final TemplateItemsRepository templateItemsRepository;

    private final TemplateItemsMapper templateItemsMapper;

    public TemplateItemsQueryService(TemplateItemsRepository templateItemsRepository, TemplateItemsMapper templateItemsMapper) {
        this.templateItemsRepository = templateItemsRepository;
        this.templateItemsMapper = templateItemsMapper;
    }

    /**
     * Return a {@link List} of {@link TemplateItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TemplateItemsDTO> findByCriteria(TemplateItemsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TemplateItems> specification = createSpecification(criteria);
        return templateItemsMapper.toDto(templateItemsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TemplateItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TemplateItemsDTO> findByCriteria(TemplateItemsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TemplateItems> specification = createSpecification(criteria);
        return templateItemsRepository.findAll(specification, page).map(templateItemsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TemplateItemsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TemplateItems> specification = createSpecification(criteria);
        return templateItemsRepository.count(specification);
    }

    /**
     * Function to convert {@link TemplateItemsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TemplateItems> createSpecification(TemplateItemsCriteria criteria) {
        Specification<TemplateItems> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TemplateItems_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TemplateItems_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), TemplateItems_.code));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), TemplateItems_.type));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildSpecification(criteria.getValueType(), TemplateItems_.valueType));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), TemplateItems_.amount));
            }
            if (criteria.getExplanation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExplanation(), TemplateItems_.explanation));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), TemplateItems_.startDate));
            }
            if (criteria.getDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDueDate(), TemplateItems_.dueDate));
            }
            if (criteria.getIsOnce() != null) {
                specification = specification.and(buildSpecification(criteria.getIsOnce(), TemplateItems_.isOnce));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), TemplateItems_.createdDate));
            }
            if (criteria.getConditionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getConditionsId(),
                            root -> root.join(TemplateItems_.conditions, JoinType.LEFT).get(Conditions_.id)
                        )
                    );
            }
            if (criteria.getTemplatesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTemplatesId(),
                            root -> root.join(TemplateItems_.templates, JoinType.LEFT).get(Templates_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
