package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.*; // for static metamodels
import com.polarbears.capstone.hmsfinance.domain.InvoiceTransactions;
import com.polarbears.capstone.hmsfinance.repository.InvoiceTransactionsRepository;
import com.polarbears.capstone.hmsfinance.service.criteria.InvoiceTransactionsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.InvoiceTransactionsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.InvoiceTransactionsMapper;
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
 * Service for executing complex queries for {@link InvoiceTransactions} entities in the database.
 * The main input is a {@link InvoiceTransactionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InvoiceTransactionsDTO} or a {@link Page} of {@link InvoiceTransactionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceTransactionsQueryService extends QueryService<InvoiceTransactions> {

    private final Logger log = LoggerFactory.getLogger(InvoiceTransactionsQueryService.class);

    private final InvoiceTransactionsRepository invoiceTransactionsRepository;

    private final InvoiceTransactionsMapper invoiceTransactionsMapper;

    public InvoiceTransactionsQueryService(
        InvoiceTransactionsRepository invoiceTransactionsRepository,
        InvoiceTransactionsMapper invoiceTransactionsMapper
    ) {
        this.invoiceTransactionsRepository = invoiceTransactionsRepository;
        this.invoiceTransactionsMapper = invoiceTransactionsMapper;
    }

    /**
     * Return a {@link List} of {@link InvoiceTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InvoiceTransactionsDTO> findByCriteria(InvoiceTransactionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InvoiceTransactions> specification = createSpecification(criteria);
        return invoiceTransactionsMapper.toDto(invoiceTransactionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InvoiceTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceTransactionsDTO> findByCriteria(InvoiceTransactionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InvoiceTransactions> specification = createSpecification(criteria);
        return invoiceTransactionsRepository.findAll(specification, page).map(invoiceTransactionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoiceTransactionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InvoiceTransactions> specification = createSpecification(criteria);
        return invoiceTransactionsRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoiceTransactionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InvoiceTransactions> createSpecification(InvoiceTransactionsCriteria criteria) {
        Specification<InvoiceTransactions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InvoiceTransactions_.id));
            }
            if (criteria.getStatusChangedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getStatusChangedDate(), InvoiceTransactions_.statusChangedDate));
            }
            if (criteria.getTransactionDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTransactionDate(), InvoiceTransactions_.transactionDate));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), InvoiceTransactions_.type));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), InvoiceTransactions_.createdDate));
            }
            if (criteria.getInvoicesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInvoicesId(),
                            root -> root.join(InvoiceTransactions_.invoices, JoinType.LEFT).get(Invoices_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
