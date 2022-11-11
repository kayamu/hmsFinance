package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.InvoiceTransactions;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InvoiceTransactions entity.
 */
@Repository
public interface InvoiceTransactionsRepository
    extends JpaRepository<InvoiceTransactions, Long>, JpaSpecificationExecutor<InvoiceTransactions> {
    default Optional<InvoiceTransactions> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<InvoiceTransactions> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<InvoiceTransactions> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct invoiceTransactions from InvoiceTransactions invoiceTransactions left join fetch invoiceTransactions.invoices",
        countQuery = "select count(distinct invoiceTransactions) from InvoiceTransactions invoiceTransactions"
    )
    Page<InvoiceTransactions> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct invoiceTransactions from InvoiceTransactions invoiceTransactions left join fetch invoiceTransactions.invoices")
    List<InvoiceTransactions> findAllWithToOneRelationships();

    @Query(
        "select invoiceTransactions from InvoiceTransactions invoiceTransactions left join fetch invoiceTransactions.invoices where invoiceTransactions.id =:id"
    )
    Optional<InvoiceTransactions> findOneWithToOneRelationships(@Param("id") Long id);
}
