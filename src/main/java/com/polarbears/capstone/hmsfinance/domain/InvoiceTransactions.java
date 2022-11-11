package com.polarbears.capstone.hmsfinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmsfinance.domain.enumeration.INVOICETYPES;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InvoiceTransactions.
 */
@Entity
@Table(name = "invoice_transactions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoiceTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "status_changed_date")
    private LocalDate statusChangedDate;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private INVOICETYPES type;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "payments", "invoiceTransactions", "invoiceDetails" }, allowSetters = true)
    private Invoices invoices;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InvoiceTransactions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStatusChangedDate() {
        return this.statusChangedDate;
    }

    public InvoiceTransactions statusChangedDate(LocalDate statusChangedDate) {
        this.setStatusChangedDate(statusChangedDate);
        return this;
    }

    public void setStatusChangedDate(LocalDate statusChangedDate) {
        this.statusChangedDate = statusChangedDate;
    }

    public LocalDate getTransactionDate() {
        return this.transactionDate;
    }

    public InvoiceTransactions transactionDate(LocalDate transactionDate) {
        this.setTransactionDate(transactionDate);
        return this;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public INVOICETYPES getType() {
        return this.type;
    }

    public InvoiceTransactions type(INVOICETYPES type) {
        this.setType(type);
        return this;
    }

    public void setType(INVOICETYPES type) {
        this.type = type;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public InvoiceTransactions createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Invoices getInvoices() {
        return this.invoices;
    }

    public void setInvoices(Invoices invoices) {
        this.invoices = invoices;
    }

    public InvoiceTransactions invoices(Invoices invoices) {
        this.setInvoices(invoices);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceTransactions)) {
            return false;
        }
        return id != null && id.equals(((InvoiceTransactions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceTransactions{" +
            "id=" + getId() +
            ", statusChangedDate='" + getStatusChangedDate() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", type='" + getType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
