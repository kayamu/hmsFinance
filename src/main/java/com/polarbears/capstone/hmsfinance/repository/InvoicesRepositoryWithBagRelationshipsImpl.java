package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.Invoices;
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
public class InvoicesRepositoryWithBagRelationshipsImpl implements InvoicesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Invoices> fetchBagRelationships(Optional<Invoices> invoices) {
        return invoices.map(this::fetchInvoiceDetails);
    }

    @Override
    public Page<Invoices> fetchBagRelationships(Page<Invoices> invoices) {
        return new PageImpl<>(fetchBagRelationships(invoices.getContent()), invoices.getPageable(), invoices.getTotalElements());
    }

    @Override
    public List<Invoices> fetchBagRelationships(List<Invoices> invoices) {
        return Optional.of(invoices).map(this::fetchInvoiceDetails).orElse(Collections.emptyList());
    }

    Invoices fetchInvoiceDetails(Invoices result) {
        return entityManager
            .createQuery(
                "select invoices from Invoices invoices left join fetch invoices.invoiceDetails where invoices is :invoices",
                Invoices.class
            )
            .setParameter("invoices", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Invoices> fetchInvoiceDetails(List<Invoices> invoices) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, invoices.size()).forEach(index -> order.put(invoices.get(index).getId(), index));
        List<Invoices> result = entityManager
            .createQuery(
                "select distinct invoices from Invoices invoices left join fetch invoices.invoiceDetails where invoices in :invoices",
                Invoices.class
            )
            .setParameter("invoices", invoices)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
