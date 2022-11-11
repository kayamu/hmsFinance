package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.Templates;
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
public class TemplatesRepositoryWithBagRelationshipsImpl implements TemplatesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Templates> fetchBagRelationships(Optional<Templates> templates) {
        return templates.map(this::fetchTemplateItems);
    }

    @Override
    public Page<Templates> fetchBagRelationships(Page<Templates> templates) {
        return new PageImpl<>(fetchBagRelationships(templates.getContent()), templates.getPageable(), templates.getTotalElements());
    }

    @Override
    public List<Templates> fetchBagRelationships(List<Templates> templates) {
        return Optional.of(templates).map(this::fetchTemplateItems).orElse(Collections.emptyList());
    }

    Templates fetchTemplateItems(Templates result) {
        return entityManager
            .createQuery(
                "select templates from Templates templates left join fetch templates.templateItems where templates is :templates",
                Templates.class
            )
            .setParameter("templates", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Templates> fetchTemplateItems(List<Templates> templates) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, templates.size()).forEach(index -> order.put(templates.get(index).getId(), index));
        List<Templates> result = entityManager
            .createQuery(
                "select distinct templates from Templates templates left join fetch templates.templateItems where templates in :templates",
                Templates.class
            )
            .setParameter("templates", templates)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
