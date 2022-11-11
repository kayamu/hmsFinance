package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.*; // for static metamodels
import com.polarbears.capstone.hmsfinance.domain.Templates;
import com.polarbears.capstone.hmsfinance.repository.TemplatesRepository;
import com.polarbears.capstone.hmsfinance.service.criteria.TemplatesCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.TemplatesDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.TemplatesMapper;
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
 * Service for executing complex queries for {@link Templates} entities in the database.
 * The main input is a {@link TemplatesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TemplatesDTO} or a {@link Page} of {@link TemplatesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TemplatesQueryService extends QueryService<Templates> {

    private final Logger log = LoggerFactory.getLogger(TemplatesQueryService.class);

    private final TemplatesRepository templatesRepository;

    private final TemplatesMapper templatesMapper;

    public TemplatesQueryService(TemplatesRepository templatesRepository, TemplatesMapper templatesMapper) {
        this.templatesRepository = templatesRepository;
        this.templatesMapper = templatesMapper;
    }

    /**
     * Return a {@link List} of {@link TemplatesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TemplatesDTO> findByCriteria(TemplatesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Templates> specification = createSpecification(criteria);
        return templatesMapper.toDto(templatesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TemplatesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TemplatesDTO> findByCriteria(TemplatesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Templates> specification = createSpecification(criteria);
        return templatesRepository.findAll(specification, page).map(templatesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TemplatesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Templates> specification = createSpecification(criteria);
        return templatesRepository.count(specification);
    }

    /**
     * Function to convert {@link TemplatesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Templates> createSpecification(TemplatesCriteria criteria) {
        Specification<Templates> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Templates_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Templates_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Templates_.type));
            }
            if (criteria.getExplanation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExplanation(), Templates_.explanation));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Templates_.isActive));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Templates_.createdDate));
            }
            if (criteria.getItemsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getItemsId(), root -> root.join(Templates_.items, JoinType.LEFT).get(Items_.id))
                    );
            }
            if (criteria.getTemplateItemsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTemplateItemsId(),
                            root -> root.join(Templates_.templateItems, JoinType.LEFT).get(TemplateItems_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
