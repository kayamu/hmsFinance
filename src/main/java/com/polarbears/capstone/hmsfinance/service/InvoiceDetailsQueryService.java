package com.polarbears.capstone.hmsfinance.service;

import com.polarbears.capstone.hmsfinance.domain.*; // for static metamodels
import com.polarbears.capstone.hmsfinance.domain.InvoiceDetails;
import com.polarbears.capstone.hmsfinance.repository.InvoiceDetailsRepository;
import com.polarbears.capstone.hmsfinance.service.criteria.InvoiceDetailsCriteria;
import com.polarbears.capstone.hmsfinance.service.dto.InvoiceDetailsDTO;
import com.polarbears.capstone.hmsfinance.service.mapper.InvoiceDetailsMapper;
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
 * Service for executing complex queries for {@link InvoiceDetails} entities in the database.
 * The main input is a {@link InvoiceDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InvoiceDetailsDTO} or a {@link Page} of {@link InvoiceDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InvoiceDetailsQueryService extends QueryService<InvoiceDetails> {

    private final Logger log = LoggerFactory.getLogger(InvoiceDetailsQueryService.class);

    private final InvoiceDetailsRepository invoiceDetailsRepository;

    private final InvoiceDetailsMapper invoiceDetailsMapper;

    public InvoiceDetailsQueryService(InvoiceDetailsRepository invoiceDetailsRepository, InvoiceDetailsMapper invoiceDetailsMapper) {
        this.invoiceDetailsRepository = invoiceDetailsRepository;
        this.invoiceDetailsMapper = invoiceDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link InvoiceDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InvoiceDetailsDTO> findByCriteria(InvoiceDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InvoiceDetails> specification = createSpecification(criteria);
        return invoiceDetailsMapper.toDto(invoiceDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InvoiceDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InvoiceDetailsDTO> findByCriteria(InvoiceDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InvoiceDetails> specification = createSpecification(criteria);
        return invoiceDetailsRepository.findAll(specification, page).map(invoiceDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InvoiceDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InvoiceDetails> specification = createSpecification(criteria);
        return invoiceDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link InvoiceDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InvoiceDetails> createSpecification(InvoiceDetailsCriteria criteria) {
        Specification<InvoiceDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InvoiceDetails_.id));
            }
            if (criteria.getContactId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactId(), InvoiceDetails_.contactId));
            }
            if (criteria.getCartId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCartId(), InvoiceDetails_.cartId));
            }
            if (criteria.getItemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemId(), InvoiceDetails_.itemId));
            }
            if (criteria.getItemName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemName(), InvoiceDetails_.itemName));
            }
            if (criteria.getItemCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemCode(), InvoiceDetails_.itemCode));
            }
            if (criteria.getItemType() != null) {
                specification = specification.and(buildSpecification(criteria.getItemType(), InvoiceDetails_.itemType));
            }
            if (criteria.getPaymentType() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentType(), InvoiceDetails_.paymentType));
            }
            if (criteria.getSubscriptionStartingDate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getSubscriptionStartingDate(), InvoiceDetails_.subscriptionStartingDate)
                    );
            }
            if (criteria.getSubscriptionDurationWeeks() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getSubscriptionDurationWeeks(), InvoiceDetails_.subscriptionDurationWeeks)
                    );
            }
            if (criteria.getDetailAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDetailAmount(), InvoiceDetails_.detailAmount));
            }
            if (criteria.getLineNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineNumber(), InvoiceDetails_.lineNumber));
            }
            if (criteria.getNutritionistId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNutritionistId(), InvoiceDetails_.nutritionistId));
            }
            if (criteria.getTotalCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalCost(), InvoiceDetails_.totalCost));
            }
            if (criteria.getTotalProfit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalProfit(), InvoiceDetails_.totalProfit));
            }
            if (criteria.getNutritionistEarning() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNutritionistEarning(), InvoiceDetails_.nutritionistEarning));
            }
            if (criteria.getNutritionistPercentage() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getNutritionistPercentage(), InvoiceDetails_.nutritionistPercentage)
                    );
            }
            if (criteria.getFedaralTaxesAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getFedaralTaxesAmount(), InvoiceDetails_.fedaralTaxesAmount));
            }
            if (criteria.getFedaralTaxesPercentage() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getFedaralTaxesPercentage(), InvoiceDetails_.fedaralTaxesPercentage)
                    );
            }
            if (criteria.getProvintionalTaxesAmount() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getProvintionalTaxesAmount(), InvoiceDetails_.provintionalTaxesAmount)
                    );
            }
            if (criteria.getProvintionalTaxesPercentage() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getProvintionalTaxesPercentage(), InvoiceDetails_.provintionalTaxesPercentage)
                    );
            }
            if (criteria.getTotalTaxesAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalTaxesAmount(), InvoiceDetails_.totalTaxesAmount));
            }
            if (criteria.getTotalTaxesPercentage() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalTaxesPercentage(), InvoiceDetails_.totalTaxesPercentage));
            }
            if (criteria.getDiscountAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscountAmount(), InvoiceDetails_.discountAmount));
            }
            if (criteria.getDiscountPercentage() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDiscountPercentage(), InvoiceDetails_.discountPercentage));
            }
            if (criteria.getAddOnCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddOnCode(), InvoiceDetails_.addOnCode));
            }
            if (criteria.getAddOnAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAddOnAmount(), InvoiceDetails_.addOnAmount));
            }
            if (criteria.getAddOnPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAddOnPercentage(), InvoiceDetails_.addOnPercentage));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), InvoiceDetails_.totalAmount));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), InvoiceDetails_.createdDate));
            }
            if (criteria.getSubItemsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSubItemsId(),
                            root -> root.join(InvoiceDetails_.subItems, JoinType.LEFT).get(SubItems_.id)
                        )
                    );
            }
            if (criteria.getInvoicesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInvoicesId(),
                            root -> root.join(InvoiceDetails_.invoices, JoinType.LEFT).get(Invoices_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
