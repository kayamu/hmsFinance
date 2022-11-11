package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.Payments;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Payments entity.
 */
@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long>, JpaSpecificationExecutor<Payments> {
    default Optional<Payments> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Payments> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Payments> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct payments from Payments payments left join fetch payments.invoices",
        countQuery = "select count(distinct payments) from Payments payments"
    )
    Page<Payments> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct payments from Payments payments left join fetch payments.invoices")
    List<Payments> findAllWithToOneRelationships();

    @Query("select payments from Payments payments left join fetch payments.invoices where payments.id =:id")
    Optional<Payments> findOneWithToOneRelationships(@Param("id") Long id);
}
