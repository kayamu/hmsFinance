package com.polarbears.capstone.hmsfinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmsfinance.domain.enumeration.PAYMENTSTATUS;
import com.polarbears.capstone.hmsfinance.domain.enumeration.PAYMENTTYPES;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payments.
 */
@Entity
@Table(name = "payments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Payments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ref_number")
    private String refNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PAYMENTTYPES paymentType;

    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "operation_date")
    private LocalDate operationDate;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PAYMENTSTATUS status;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "payments", "invoiceTransactions", "invoiceDetails" }, allowSetters = true)
    private Invoices invoices;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payments id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefNumber() {
        return this.refNumber;
    }

    public Payments refNumber(String refNumber) {
        this.setRefNumber(refNumber);
        return this;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public PAYMENTTYPES getPaymentType() {
        return this.paymentType;
    }

    public Payments paymentType(PAYMENTTYPES paymentType) {
        this.setPaymentType(paymentType);
        return this;
    }

    public void setPaymentType(PAYMENTTYPES paymentType) {
        this.paymentType = paymentType;
    }

    public Long getContactId() {
        return this.contactId;
    }

    public Payments contactId(Long contactId) {
        this.setContactId(contactId);
        return this;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public Payments explanation(String explanation) {
        this.setExplanation(explanation);
        return this;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public LocalDate getOperationDate() {
        return this.operationDate;
    }

    public Payments operationDate(LocalDate operationDate) {
        this.setOperationDate(operationDate);
        return this;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public Double getAmount() {
        return this.amount;
    }

    public Payments amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PAYMENTSTATUS getStatus() {
        return this.status;
    }

    public Payments status(PAYMENTSTATUS status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PAYMENTSTATUS status) {
        this.status = status;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Payments createdDate(LocalDate createdDate) {
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

    public Payments invoices(Invoices invoices) {
        this.setInvoices(invoices);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payments)) {
            return false;
        }
        return id != null && id.equals(((Payments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payments{" +
            "id=" + getId() +
            ", refNumber='" + getRefNumber() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", contactId=" + getContactId() +
            ", explanation='" + getExplanation() + "'" +
            ", operationDate='" + getOperationDate() + "'" +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
