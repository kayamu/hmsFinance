package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.TemplateItems;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TemplateItems entity.
 */
@Repository
public interface TemplateItemsRepository extends JpaRepository<TemplateItems, Long>, JpaSpecificationExecutor<TemplateItems> {
    default Optional<TemplateItems> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TemplateItems> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TemplateItems> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct templateItems from TemplateItems templateItems left join fetch templateItems.conditions",
        countQuery = "select count(distinct templateItems) from TemplateItems templateItems"
    )
    Page<TemplateItems> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct templateItems from TemplateItems templateItems left join fetch templateItems.conditions")
    List<TemplateItems> findAllWithToOneRelationships();

    @Query("select templateItems from TemplateItems templateItems left join fetch templateItems.conditions where templateItems.id =:id")
    Optional<TemplateItems> findOneWithToOneRelationships(@Param("id") Long id);
}
