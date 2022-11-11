package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.*; // for static metamodels
import com.polarbears.capstone.hmsfinance.domain.Payments;
import com.polarbears.capstone.hmsfinance.repository.PaymentsRepository;
import com.polarbears.capstone.hmsfinance.service.criteria.PaymentsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.PaymentsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.PaymentsMapper;
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
 * Service for executing complex queries for {@link Payments} entities in the database.
 * The main input is a {@link PaymentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentsDTO} or a {@link Page} of {@link PaymentsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentsQueryService extends QueryService<Payments> {

    private final Logger log = LoggerFactory.getLogger(PaymentsQueryService.class);

    private final PaymentsRepository paymentsRepository;

    private final PaymentsMapper paymentsMapper;

    public PaymentsQueryService(PaymentsRepository paymentsRepository, PaymentsMapper paymentsMapper) {
        this.paymentsRepository = paymentsRepository;
        this.paymentsMapper = paymentsMapper;
    }

    /**
     * Return a {@link List} of {@link PaymentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentsDTO> findByCriteria(PaymentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Payments> specification = createSpecification(criteria);
        return paymentsMapper.toDto(paymentsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentsDTO> findByCriteria(PaymentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Payments> specification = createSpecification(criteria);
        return paymentsRepository.findAll(specification, page).map(paymentsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Payments> specification = createSpecification(criteria);
        return paymentsRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Payments> createSpecification(PaymentsCriteria criteria) {
        Specification<Payments> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Payments_.id));
            }
            if (criteria.getRefNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefNumber(), Payments_.refNumber));
            }
            if (criteria.getPaymentType() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentType(), Payments_.paymentType));
            }
            if (criteria.getContactId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactId(), Payments_.contactId));
            }
            if (criteria.getExplanation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExplanation(), Payments_.explanation));
            }
            if (criteria.getOperationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOperationDate(), Payments_.operationDate));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Payments_.amount));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Payments_.status));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Payments_.createdDate));
            }
            if (criteria.getInvoicesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getInvoicesId(), root -> root.join(Payments_.invoices, JoinType.LEFT).get(Invoices_.id))
                    );
            }
        }
        return specification;
    }
}
