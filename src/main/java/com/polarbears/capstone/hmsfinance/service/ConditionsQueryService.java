package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.*; // for static metamodels
import com.polarbears.capstone.hmsfinance.domain.Conditions;
import com.polarbears.capstone.hmsfinance.repository.ConditionsRepository;
import com.polarbears.capstone.hmsfinance.service.criteria.ConditionsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.ConditionsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.ConditionsMapper;
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
 * Service for executing complex queries for {@link Conditions} entities in the database.
 * The main input is a {@link ConditionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConditionsDTO} or a {@link Page} of {@link ConditionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConditionsQueryService extends QueryService<Conditions> {

    private final Logger log = LoggerFactory.getLogger(ConditionsQueryService.class);

    private final ConditionsRepository conditionsRepository;

    private final ConditionsMapper conditionsMapper;

    public ConditionsQueryService(ConditionsRepository conditionsRepository, ConditionsMapper conditionsMapper) {
        this.conditionsRepository = conditionsRepository;
        this.conditionsMapper = conditionsMapper;
    }

    /**
     * Return a {@link List} of {@link ConditionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConditionsDTO> findByCriteria(ConditionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Conditions> specification = createSpecification(criteria);
        return conditionsMapper.toDto(conditionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConditionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConditionsDTO> findByCriteria(ConditionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Conditions> specification = createSpecification(criteria);
        return conditionsRepository.findAll(specification, page).map(conditionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConditionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Conditions> specification = createSpecification(criteria);
        return conditionsRepository.count(specification);
    }

    /**
     * Function to convert {@link ConditionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Conditions> createSpecification(ConditionsCriteria criteria) {
        Specification<Conditions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Conditions_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Conditions_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Conditions_.type));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Conditions_.createdDate));
            }
            if (criteria.getTemplateItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTemplateItemId(),
                            root -> root.join(Conditions_.templateItems, JoinType.LEFT).get(TemplateItems_.id)
                        )
                    );
            }
            if (criteria.getConditionDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getConditionDetailsId(),
                            root -> root.join(Conditions_.conditionDetails, JoinType.LEFT).get(ConditionDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
