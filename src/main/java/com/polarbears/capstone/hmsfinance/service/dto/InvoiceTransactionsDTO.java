package com.polarbears.capstone.hmsfinance.service.dto;

import com.polarbears.capstone.hmsfinance.domain.enumeration.INVOICETYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.polarbears.capstone.hmsfinance.domain.InvoiceTransactions} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoiceTransactionsDTO implements Serializable {

    private Long id;

    private LocalDate statusChangedDate;

    private LocalDate transactionDate;

    private INVOICETYPES type;

    private LocalDate createdDate;

    private InvoicesDTO invoices;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStatusChangedDate() {
        return statusChangedDate;
    }

    public void setStatusChangedDate(LocalDate statusChangedDate) {
        this.statusChangedDate = statusChangedDate;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public INVOICETYPES getType() {
        return type;
    }

    public void setType(INVOICETYPES type) {
        this.type = type;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public InvoicesDTO getInvoices() {
        return invoices;
    }

    public void setInvoices(InvoicesDTO invoices) {
        this.invoices = invoices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceTransactionsDTO)) {
            return false;
        }

        InvoiceTransactionsDTO invoiceTransactionsDTO = (InvoiceTransactionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, invoiceTransactionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceTransactionsDTO{" +
            "id=" + getId() +
            ", statusChangedDate='" + getStatusChangedDate() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", type='" + getType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", invoices=" + getInvoices() +
            "}";
    }
}
