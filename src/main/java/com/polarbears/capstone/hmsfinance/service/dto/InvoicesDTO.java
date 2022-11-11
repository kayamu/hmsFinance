package com.polarbears.capstone.hmsfinance.service.dto;

import com.polarbears.capstone.hmsfinance.domain.enumeration.INVOICETYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.polarbears.capstone.hmsfinance.domain.Invoices} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoicesDTO implements Serializable {

    private Long id;

    private String invoiceNumber;

    private Long contactId;

    private Long contactAddressId;

    private Long contactBillingAdrId;

    private Long cartId;

    private INVOICETYPES type;

    private LocalDate requestDate;

    private String contactName;

    private LocalDate invoiceDate;

    private Long lastTranactionId;

    private Double totalCost;

    private Double totalProfit;

    private Double totalAmount;

    private Double totalTaxes;

    private Double fedaralTaxesAmount;

    private Double provintionalTaxesAmount;

    private Double discountsAmount;

    private Double addOnAmount;

    @Size(max = 1024)
    private String message;

    private LocalDate createdDate;

    private Set<InvoiceDetailsDTO> invoiceDetails = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getContactAddressId() {
        return contactAddressId;
    }

    public void setContactAddressId(Long contactAddressId) {
        this.contactAddressId = contactAddressId;
    }

    public Long getContactBillingAdrId() {
        return contactBillingAdrId;
    }

    public void setContactBillingAdrId(Long contactBillingAdrId) {
        this.contactBillingAdrId = contactBillingAdrId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public INVOICETYPES getType() {
        return type;
    }

    public void setType(INVOICETYPES type) {
        this.type = type;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Long getLastTranactionId() {
        return lastTranactionId;
    }

    public void setLastTranactionId(Long lastTranactionId) {
        this.lastTranactionId = lastTranactionId;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalTaxes() {
        return totalTaxes;
    }

    public void setTotalTaxes(Double totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public Double getFedaralTaxesAmount() {
        return fedaralTaxesAmount;
    }

    public void setFedaralTaxesAmount(Double fedaralTaxesAmount) {
        this.fedaralTaxesAmount = fedaralTaxesAmount;
    }

    public Double getProvintionalTaxesAmount() {
        return provintionalTaxesAmount;
    }

    public void setProvintionalTaxesAmount(Double provintionalTaxesAmount) {
        this.provintionalTaxesAmount = provintionalTaxesAmount;
    }

    public Double getDiscountsAmount() {
        return discountsAmount;
    }

    public void setDiscountsAmount(Double discountsAmount) {
        this.discountsAmount = discountsAmount;
    }

    public Double getAddOnAmount() {
        return addOnAmount;
    }

    public void setAddOnAmount(Double addOnAmount) {
        this.addOnAmount = addOnAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<InvoiceDetailsDTO> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(Set<InvoiceDetailsDTO> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoicesDTO)) {
            return false;
        }

        InvoicesDTO invoicesDTO = (InvoicesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, invoicesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoicesDTO{" +
            "id=" + getId() +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", contactId=" + getContactId() +
            ", contactAddressId=" + getContactAddressId() +
            ", contactBillingAdrId=" + getContactBillingAdrId() +
            ", cartId=" + getCartId() +
            ", type='" + getType() + "'" +
            ", requestDate='" + getRequestDate() + "'" +
            ", contactName='" + getContactName() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", lastTranactionId=" + getLastTranactionId() +
            ", totalCost=" + getTotalCost() +
            ", totalProfit=" + getTotalProfit() +
            ", totalAmount=" + getTotalAmount() +
            ", totalTaxes=" + getTotalTaxes() +
            ", fedaralTaxesAmount=" + getFedaralTaxesAmount() +
            ", provintionalTaxesAmount=" + getProvintionalTaxesAmount() +
            ", discountsAmount=" + getDiscountsAmount() +
            ", addOnAmount=" + getAddOnAmount() +
            ", message='" + getMessage() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", invoiceDetails=" + getInvoiceDetails() +
            "}";
    }
}
