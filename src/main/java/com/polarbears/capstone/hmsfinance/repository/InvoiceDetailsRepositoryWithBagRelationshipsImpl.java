package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.InvoiceDetails;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class InvoiceDetailsRepositoryWithBagRelationshipsImpl implements InvoiceDetailsRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<InvoiceDetails> fetchBagRelationships(Optional<InvoiceDetails> invoiceDetails) {
        return invoiceDetails.map(this::fetchSubItems);
    }

    @Override
    public Page<InvoiceDetails> fetchBagRelationships(Page<InvoiceDetails> invoiceDetails) {
        return new PageImpl<>(
            fetchBagRelationships(invoiceDetails.getContent()),
            invoiceDetails.getPageable(),
            invoiceDetails.getTotalElements()
        );
    }

    @Override
    public List<InvoiceDetails> fetchBagRelationships(List<InvoiceDetails> invoiceDetails) {
        return Optional.of(invoiceDetails).map(this::fetchSubItems).orElse(Collections.emptyList());
    }

    InvoiceDetails fetchSubItems(InvoiceDetails result) {
        return entityManager
            .createQuery(
                "select invoiceDetails from InvoiceDetails invoiceDetails left join fetch invoiceDetails.subItems where invoiceDetails is :invoiceDetails",
                InvoiceDetails.class
            )
            .setParameter("invoiceDetails", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<InvoiceDetails> fetchSubItems(List<InvoiceDetails> invoiceDetails) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, invoiceDetails.size()).forEach(index -> order.put(invoiceDetails.get(index).getId(), index));
        List<InvoiceDetails> result = entityManager
            .createQuery(
                "select distinct invoiceDetails from InvoiceDetails invoiceDetails left join fetch invoiceDetails.subItems where invoiceDetails in :invoiceDetails",
                InvoiceDetails.class
            )
            .setParameter("invoiceDetails", invoiceDetails)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
