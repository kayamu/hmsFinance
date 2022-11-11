package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.*; // for static metamodels
import com.polarbears.capstone.hmsfinance.domain.SubItems;
import com.polarbears.capstone.hmsfinance.repository.SubItemsRepository;
import com.polarbears.capstone.hmsfinance.service.criteria.SubItemsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.SubItemsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.SubItemsMapper;
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
 * Service for executing complex queries for {@link SubItems} entities in the database.
 * The main input is a {@link SubItemsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SubItemsDTO} or a {@link Page} of {@link SubItemsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubItemsQueryService extends QueryService<SubItems> {

    private final Logger log = LoggerFactory.getLogger(SubItemsQueryService.class);

    private final SubItemsRepository subItemsRepository;

    private final SubItemsMapper subItemsMapper;

    public SubItemsQueryService(SubItemsRepository subItemsRepository, SubItemsMapper subItemsMapper) {
        this.subItemsRepository = subItemsRepository;
        this.subItemsMapper = subItemsMapper;
    }

    /**
     * Return a {@link List} of {@link SubItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SubItemsDTO> findByCriteria(SubItemsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SubItems> specification = createSpecification(criteria);
        return subItemsMapper.toDto(subItemsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SubItemsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubItemsDTO> findByCriteria(SubItemsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SubItems> specification = createSpecification(criteria);
        return subItemsRepository.findAll(specification, page).map(subItemsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubItemsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SubItems> specification = createSpecification(criteria);
        return subItemsRepository.count(specification);
    }

    /**
     * Function to convert {@link SubItemsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SubItems> createSpecification(SubItemsCriteria criteria) {
        Specification<SubItems> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SubItems_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SubItems_.name));
            }
            if (criteria.getActualValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualValue(), SubItems_.actualValue));
            }
            if (criteria.getPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentage(), SubItems_.percentage));
            }
            if (criteria.getBaseValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBaseValue(), SubItems_.baseValue));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), SubItems_.type));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildSpecification(criteria.getValueType(), SubItems_.valueType));
            }
            if (criteria.getCalculatedValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCalculatedValue(), SubItems_.calculatedValue));
            }
            if (criteria.getTemplateItemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTemplateItemId(), SubItems_.templateItemId));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), SubItems_.createdDate));
            }
            if (criteria.getInvoiceDetailId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInvoiceDetailId(),
                            root -> root.join(SubItems_.invoiceDetails, JoinType.LEFT).get(InvoiceDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
