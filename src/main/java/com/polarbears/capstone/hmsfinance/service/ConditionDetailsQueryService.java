package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.*; // for static metamodels
import com.polarbears.capstone.hmsfinance.domain.ConditionDetails;
import com.polarbears.capstone.hmsfinance.repository.ConditionDetailsRepository;
import com.polarbears.capstone.hmsfinance.service.criteria.ConditionDetailsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.ConditionDetailsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.ConditionDetailsMapper;
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
 * Service for executing complex queries for {@link ConditionDetails} entities in the database.
 * The main input is a {@link ConditionDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConditionDetailsDTO} or a {@link Page} of {@link ConditionDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConditionDetailsQueryService extends QueryService<ConditionDetails> {

    private final Logger log = LoggerFactory.getLogger(ConditionDetailsQueryService.class);

    private final ConditionDetailsRepository conditionDetailsRepository;

    private final ConditionDetailsMapper conditionDetailsMapper;

    public ConditionDetailsQueryService(
        ConditionDetailsRepository conditionDetailsRepository,
        ConditionDetailsMapper conditionDetailsMapper
    ) {
        this.conditionDetailsRepository = conditionDetailsRepository;
        this.conditionDetailsMapper = conditionDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link ConditionDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConditionDetailsDTO> findByCriteria(ConditionDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ConditionDetails> specification = createSpecification(criteria);
        return conditionDetailsMapper.toDto(conditionDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConditionDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConditionDetailsDTO> findByCriteria(ConditionDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConditionDetails> specification = createSpecification(criteria);
        return conditionDetailsRepository.findAll(specification, page).map(conditionDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConditionDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ConditionDetails> specification = createSpecification(criteria);
        return conditionDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link ConditionDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ConditionDetails> createSpecification(ConditionDetailsCriteria criteria) {
        Specification<ConditionDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ConditionDetails_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ConditionDetails_.name));
            }
            if (criteria.getExplanation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExplanation(), ConditionDetails_.explanation));
            }
            if (criteria.getCompareField() != null) {
                specification = specification.and(buildSpecification(criteria.getCompareField(), ConditionDetails_.compareField));
            }
            if (criteria.getOperator() != null) {
                specification = specification.and(buildSpecification(criteria.getOperator(), ConditionDetails_.operator));
            }
            if (criteria.getGroupIndex() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGroupIndex(), ConditionDetails_.groupIndex));
            }
            if (criteria.getCompareValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompareValue(), ConditionDetails_.compareValue));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), ConditionDetails_.createdDate));
            }
            if (criteria.getLineLogicType() != null) {
                specification = specification.and(buildSpecification(criteria.getLineLogicType(), ConditionDetails_.lineLogicType));
            }
            if (criteria.getGroupLogicType() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupLogicType(), ConditionDetails_.groupLogicType));
            }
            if (criteria.getNextCondition() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextCondition(), ConditionDetails_.nextCondition));
            }
            if (criteria.getConditionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getConditionId(),
                            root -> root.join(ConditionDetails_.conditions, JoinType.LEFT).get(Conditions_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
