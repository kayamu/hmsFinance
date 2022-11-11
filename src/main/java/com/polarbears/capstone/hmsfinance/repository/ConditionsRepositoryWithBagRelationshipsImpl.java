package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.Conditions;
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
public class ConditionsRepositoryWithBagRelationshipsImpl implements ConditionsRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Conditions> fetchBagRelationships(Optional<Conditions> conditions) {
        return conditions.map(this::fetchConditionDetails);
    }

    @Override
    public Page<Conditions> fetchBagRelationships(Page<Conditions> conditions) {
        return new PageImpl<>(fetchBagRelationships(conditions.getContent()), conditions.getPageable(), conditions.getTotalElements());
    }

    @Override
    public List<Conditions> fetchBagRelationships(List<Conditions> conditions) {
        return Optional.of(conditions).map(this::fetchConditionDetails).orElse(Collections.emptyList());
    }

    Conditions fetchConditionDetails(Conditions result) {
        return entityManager
            .createQuery(
                "select conditions from Conditions conditions left join fetch conditions.conditionDetails where conditions is :conditions",
                Conditions.class
            )
            .setParameter("conditions", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Conditions> fetchConditionDetails(List<Conditions> conditions) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, conditions.size()).forEach(index -> order.put(conditions.get(index).getId(), index));
        List<Conditions> result = entityManager
            .createQuery(
                "select distinct conditions from Conditions conditions left join fetch conditions.conditionDetails where conditions in :conditions",
                Conditions.class
            )
            .setParameter("conditions", conditions)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
