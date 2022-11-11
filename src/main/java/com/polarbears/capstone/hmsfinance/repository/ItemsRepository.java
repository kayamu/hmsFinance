package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.Items;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Items entity.
 */
@Repository
public interface ItemsRepository extends JpaRepository<Items, Long>, JpaSpecificationExecutor<Items> {
    default Optional<Items> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Items> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Items> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct items from Items items left join fetch items.templates",
        countQuery = "select count(distinct items) from Items items"
    )
    Page<Items> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct items from Items items left join fetch items.templates")
    List<Items> findAllWithToOneRelationships();

    @Query("select items from Items items left join fetch items.templates where items.id =:id")
    Optional<Items> findOneWithToOneRelationships(@Param("id") Long id);
}
