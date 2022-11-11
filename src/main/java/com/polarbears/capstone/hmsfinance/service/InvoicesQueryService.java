package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.*; // for static metamodels
import com.polarbears.capstone.hmsfinance.domain.Invoices;
import com.polarbears.capstone.hmsfinance.repository.InvoicesRepository;
import com.polarbears.capstone.hmsfinance.service.criteria.InvoicesCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.InvoicesDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.InvoicesMapper;
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
 * Service for executing complex queries for {@link Invoices} entities in the database.
 * The main input is a {@link InvoicesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InvoicesDTO} or a {@link Page} of {@link InvoicesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoicesQueryService extends QueryService<Invoices> {

    private final Logger log = LoggerFactory.getLogger(InvoicesQueryService.class);

    private final InvoicesRepository invoicesRepository;

    private final InvoicesMapper invoicesMapper;

    public InvoicesQueryService(InvoicesRepository invoicesRepository, InvoicesMapper invoicesMapper) {
        this.invoicesRepository = invoicesRepository;
        this.invoicesMapper = invoicesMapper;
    }

    /**
     * Return a {@link List} of {@link InvoicesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InvoicesDTO> findByCriteria(InvoicesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Invoices> specification = createSpecification(criteria);
        return invoicesMapper.toDto(invoicesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InvoicesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoicesDTO> findByCriteria(InvoicesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Invoices> specification = createSpecification(criteria);
        return invoicesRepository.findAll(specification, page).map(invoicesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoicesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Invoices> specification = createSpecification(criteria);
        return invoicesRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoicesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Invoices> createSpecification(InvoicesCriteria criteria) {
        Specification<Invoices> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Invoices_.id));
            }
            if (criteria.getInvoiceNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceNumber(), Invoices_.invoiceNumber));
            }
            if (criteria.getContactId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactId(), Invoices_.contactId));
            }
            if (criteria.getContactAddressId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactAddressId(), Invoices_.contactAddressId));
            }
            if (criteria.getContactBillingAdrId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getContactBillingAdrId(), Invoices_.contactBillingAdrId));
            }
            if (criteria.getCartId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCartId(), Invoices_.cartId));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Invoices_.type));
            }
            if (criteria.getRequestDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequestDate(), Invoices_.requestDate));
            }
            if (criteria.getContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactName(), Invoices_.contactName));
            }
            if (criteria.getInvoiceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceDate(), Invoices_.invoiceDate));
            }
            if (criteria.getLastTranactionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastTranactionId(), Invoices_.lastTranactionId));
            }
            if (criteria.getTotalCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalCost(), Invoices_.totalCost));
            }
            if (criteria.getTotalProfit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalProfit(), Invoices_.totalProfit));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), Invoices_.totalAmount));
            }
            if (criteria.getTotalTaxes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalTaxes(), Invoices_.totalTaxes));
            }
            if (criteria.getFedaralTaxesAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFedaralTaxesAmount(), Invoices_.fedaralTaxesAmount));
            }
            if (criteria.getProvintionalTaxesAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getProvintionalTaxesAmount(), Invoices_.provintionalTaxesAmount));
            }
            if (criteria.getDiscountsAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountsAmount(), Invoices_.discountsAmount));
            }
            if (criteria.getAddOnAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAddOnAmount(), Invoices_.addOnAmount));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), Invoices_.message));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Invoices_.createdDate));
            }
            if (criteria.getPaymentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPaymentId(), root -> root.join(Invoices_.payments, JoinType.LEFT).get(Payments_.id))
                    );
            }
            if (criteria.getInvoiceTransactionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInvoiceTransactionsId(),
                            root -> root.join(Invoices_.invoiceTransactions, JoinType.LEFT).get(InvoiceTransactions_.id)
                        )
                    );
            }
            if (criteria.getInvoiceDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInvoiceDetailsId(),
                            root -> root.join(Invoices_.invoiceDetails, JoinType.LEFT).get(InvoiceDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
