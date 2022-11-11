package com.polarbears.capstone.hmsfinance.repository;

import com.polarbears.capstone.hmsfinance.domain.InvoiceDetails;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface InvoiceDetailsRepositoryWithBagRelationships {
    Optional<InvoiceDetails> fetchBagRelationships(Optional<InvoiceDetails> invoiceDetails);

    List<InvoiceDetails> fetchBagRelationships(List<InvoiceDetails> invoiceDetails);

    Page<InvoiceDetails> fetchBagRelationships(Page<InvoiceDetails> invoiceDetails);
}
