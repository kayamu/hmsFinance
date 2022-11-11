package com.polarbears.capstone.hmsfinance.service.dto;

import com.polarbears.capstone.hmsfinance.domain.enumeration.PAYMENTSTATUS;
import com.polarbears.capstone.hmsfinance.domain.enumeration.PAYMENTTYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.polarbears.capstone.hmsfinance.domain.Payments} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentsDTO implements Serializable {

    private Long id;

    private String refNumber;

    private PAYMENTTYPES paymentType;

    private Long contactId;

    private String explanation;

    private LocalDate operationDate;

    private Double amount;

    private PAYMENTSTATUS status;

    private LocalDate createdDate;

    private InvoicesDTO invoices;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public PAYMENTTYPES getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PAYMENTTYPES paymentType) {
        this.paymentType = paymentType;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(LocalDate operationDate) {
        this.operationDate = operationDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PAYMENTSTATUS getStatus() {
        return status;
    }

    public void setStatus(PAYMENTSTATUS status) {
        this.status = status;
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
        if (!(o instanceof PaymentsDTO)) {
            return false;
        }

        PaymentsDTO paymentsDTO = (PaymentsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentsDTO{" +
            "id=" + getId() +
            ", refNumber='" + getRefNumber() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", contactId=" + getContactId() +
            ", explanation='" + getExplanation() + "'" +
            ", operationDate='" + getOperationDate() + "'" +
            ", amount=" + getAmount() +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", invoices=" + getInvoices() +
            "}";
    }
}
