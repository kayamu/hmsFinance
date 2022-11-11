package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.Invoices;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface InvoicesRepositoryWithBagRelationships {
    Optional<Invoices> fetchBagRelationships(Optional<Invoices> invoices);

    List<Invoices> fetchBagRelationships(List<Invoices> invoices);

    Page<Invoices> fetchBagRelationships(Page<Invoices> invoices);
}
