package com.polarbears.capstone.hmsfinance.service.criteria;

import com.polarbears.capstone.hmsfinance.domain.enumeration.INVOICETYPES;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmsfinance.domain.Invoices} entity. This class is used
 * in {@link com.polarbears.capstone.hmsfinance.web.rest.InvoicesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoicesCriteria implements Serializable, Criteria {

    /**
     * Class for filtering INVOICETYPES
     */
    public static class INVOICETYPESFilter extends Filter<INVOICETYPES> {

        public INVOICETYPESFilter() {}

        public INVOICETYPESFilter(INVOICETYPESFilter filter) {
            super(filter);
        }

        @Override
        public INVOICETYPESFilter copy() {
            return new INVOICETYPESFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter invoiceNumber;

    private LongFilter contactId;

    private LongFilter contactAddressId;

    private LongFilter contactBillingAdrId;

    private LongFilter cartId;

    private INVOICETYPESFilter type;

    private LocalDateFilter requestDate;

    private StringFilter contactName;

    private LocalDateFilter invoiceDate;

    private LongFilter lastTranactionId;

    private DoubleFilter totalCost;

    private DoubleFilter totalProfit;

    private DoubleFilter totalAmount;

    private DoubleFilter totalTaxes;

    private DoubleFilter fedaralTaxesAmount;

    private DoubleFilter provintionalTaxesAmount;

    private DoubleFilter discountsAmount;

    private DoubleFilter addOnAmount;

    private StringFilter message;

    private LocalDateFilter createdDate;

    private LongFilter paymentId;

    private LongFilter invoiceTransactionsId;

    private LongFilter invoiceDetailsId;

    private Boolean distinct;

    public InvoicesCriteria() {}

    public InvoicesCriteria(InvoicesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.invoiceNumber = other.invoiceNumber == null ? null : other.invoiceNumber.copy();
        this.contactId = other.contactId == null ? null : other.contactId.copy();
        this.contactAddressId = other.contactAddressId == null ? null : other.contactAddressId.copy();
        this.contactBillingAdrId = other.contactBillingAdrId == null ? null : other.contactBillingAdrId.copy();
        this.cartId = other.cartId == null ? null : other.cartId.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.requestDate = other.requestDate == null ? null : other.requestDate.copy();
        this.contactName = other.contactName == null ? null : other.contactName.copy();
        this.invoiceDate = other.invoiceDate == null ? null : other.invoiceDate.copy();
        this.lastTranactionId = other.lastTranactionId == null ? null : other.lastTranactionId.copy();
        this.totalCost = other.totalCost == null ? null : other.totalCost.copy();
        this.totalProfit = other.totalProfit == null ? null : other.totalProfit.copy();
        this.totalAmount = other.totalAmount == null ? null : other.totalAmount.copy();
        this.totalTaxes = other.totalTaxes == null ? null : other.totalTaxes.copy();
        this.fedaralTaxesAmount = other.fedaralTaxesAmount == null ? null : other.fedaralTaxesAmount.copy();
        this.provintionalTaxesAmount = other.provintionalTaxesAmount == null ? null : other.provintionalTaxesAmount.copy();
        this.discountsAmount = other.discountsAmount == null ? null : other.discountsAmount.copy();
        this.addOnAmount = other.addOnAmount == null ? null : other.addOnAmount.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.paymentId = other.paymentId == null ? null : other.paymentId.copy();
        this.invoiceTransactionsId = other.invoiceTransactionsId == null ? null : other.invoiceTransactionsId.copy();
        this.invoiceDetailsId = other.invoiceDetailsId == null ? null : other.invoiceDetailsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InvoicesCriteria copy() {
        return new InvoicesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getInvoiceNumber() {
        return invoiceNumber;
    }

    public StringFilter invoiceNumber() {
        if (invoiceNumber == null) {
            invoiceNumber = new StringFilter();
        }
        return invoiceNumber;
    }

    public void setInvoiceNumber(StringFilter invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LongFilter getContactId() {
        return contactId;
    }

    public LongFilter contactId() {
        if (contactId == null) {
            contactId = new LongFilter();
        }
        return contactId;
    }

    public void setContactId(LongFilter contactId) {
        this.contactId = contactId;
    }

    public LongFilter getContactAddressId() {
        return contactAddressId;
    }

    public LongFilter contactAddressId() {
        if (contactAddressId == null) {
            contactAddressId = new LongFilter();
        }
        return contactAddressId;
    }

    public void setContactAddressId(LongFilter contactAddressId) {
        this.contactAddressId = contactAddressId;
    }

    public LongFilter getContactBillingAdrId() {
        return contactBillingAdrId;
    }

    public LongFilter contactBillingAdrId() {
        if (contactBillingAdrId == null) {
            contactBillingAdrId = new LongFilter();
        }
        return contactBillingAdrId;
    }

    public void setContactBillingAdrId(LongFilter contactBillingAdrId) {
        this.contactBillingAdrId = contactBillingAdrId;
    }

    public LongFilter getCartId() {
        return cartId;
    }

    public LongFilter cartId() {
        if (cartId == null) {
            cartId = new LongFilter();
        }
        return cartId;
    }

    public void setCartId(LongFilter cartId) {
        this.cartId = cartId;
    }

    public INVOICETYPESFilter getType() {
        return type;
    }

    public INVOICETYPESFilter type() {
        if (type == null) {
            type = new INVOICETYPESFilter();
        }
        return type;
    }

    public void setType(INVOICETYPESFilter type) {
        this.type = type;
    }

    public LocalDateFilter getRequestDate() {
        return requestDate;
    }

    public LocalDateFilter requestDate() {
        if (requestDate == null) {
            requestDate = new LocalDateFilter();
        }
        return requestDate;
    }

    public void setRequestDate(LocalDateFilter requestDate) {
        this.requestDate = requestDate;
    }

    public StringFilter getContactName() {
        return contactName;
    }

    public StringFilter contactName() {
        if (contactName == null) {
            contactName = new StringFilter();
        }
        return contactName;
    }

    public void setContactName(StringFilter contactName) {
        this.contactName = contactName;
    }

    public LocalDateFilter getInvoiceDate() {
        return invoiceDate;
    }

    public LocalDateFilter invoiceDate() {
        if (invoiceDate == null) {
            invoiceDate = new LocalDateFilter();
        }
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateFilter invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LongFilter getLastTranactionId() {
        return lastTranactionId;
    }

    public LongFilter lastTranactionId() {
        if (lastTranactionId == null) {
            lastTranactionId = new LongFilter();
        }
        return lastTranactionId;
    }

    public void setLastTranactionId(LongFilter lastTranactionId) {
        this.lastTranactionId = lastTranactionId;
    }

    public DoubleFilter getTotalCost() {
        return totalCost;
    }

    public DoubleFilter totalCost() {
        if (totalCost == null) {
            totalCost = new DoubleFilter();
        }
        return totalCost;
    }

    public void setTotalCost(DoubleFilter totalCost) {
        this.totalCost = totalCost;
    }

    public DoubleFilter getTotalProfit() {
        return totalProfit;
    }

    public DoubleFilter totalProfit() {
        if (totalProfit == null) {
            totalProfit = new DoubleFilter();
        }
        return totalProfit;
    }

    public void setTotalProfit(DoubleFilter totalProfit) {
        this.totalProfit = totalProfit;
    }

    public DoubleFilter getTotalAmount() {
        return totalAmount;
    }

    public DoubleFilter totalAmount() {
        if (totalAmount == null) {
            totalAmount = new DoubleFilter();
        }
        return totalAmount;
    }

    public void setTotalAmount(DoubleFilter totalAmount) {
        this.totalAmount = totalAmount;
    }

    public DoubleFilter getTotalTaxes() {
        return totalTaxes;
    }

    public DoubleFilter totalTaxes() {
        if (totalTaxes == null) {
            totalTaxes = new DoubleFilter();
        }
        return totalTaxes;
    }

    public void setTotalTaxes(DoubleFilter totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public DoubleFilter getFedaralTaxesAmount() {
        return fedaralTaxesAmount;
    }

    public DoubleFilter fedaralTaxesAmount() {
        if (fedaralTaxesAmount == null) {
            fedaralTaxesAmount = new DoubleFilter();
        }
        return fedaralTaxesAmount;
    }

    public void setFedaralTaxesAmount(DoubleFilter fedaralTaxesAmount) {
        this.fedaralTaxesAmount = fedaralTaxesAmount;
    }

    public DoubleFilter getProvintionalTaxesAmount() {
        return provintionalTaxesAmount;
    }

    public DoubleFilter provintionalTaxesAmount() {
        if (provintionalTaxesAmount == null) {
            provintionalTaxesAmount = new DoubleFilter();
        }
        return provintionalTaxesAmount;
    }

    public void setProvintionalTaxesAmount(DoubleFilter provintionalTaxesAmount) {
        this.provintionalTaxesAmount = provintionalTaxesAmount;
    }

    public DoubleFilter getDiscountsAmount() {
        return discountsAmount;
    }

    public DoubleFilter discountsAmount() {
        if (discountsAmount == null) {
            discountsAmount = new DoubleFilter();
        }
        return discountsAmount;
    }

    public void setDiscountsAmount(DoubleFilter discountsAmount) {
        this.discountsAmount = discountsAmount;
    }

    public DoubleFilter getAddOnAmount() {
        return addOnAmount;
    }

    public DoubleFilter addOnAmount() {
        if (addOnAmount == null) {
            addOnAmount = new DoubleFilter();
        }
        return addOnAmount;
    }

    public void setAddOnAmount(DoubleFilter addOnAmount) {
        this.addOnAmount = addOnAmount;
    }

    public StringFilter getMessage() {
        return message;
    }

    public StringFilter message() {
        if (message == null) {
            message = new StringFilter();
        }
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public LocalDateFilter createdDate() {
        if (createdDate == null) {
            createdDate = new LocalDateFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LongFilter getPaymentId() {
        return paymentId;
    }

    public LongFilter paymentId() {
        if (paymentId == null) {
            paymentId = new LongFilter();
        }
        return paymentId;
    }

    public void setPaymentId(LongFilter paymentId) {
        this.paymentId = paymentId;
    }

    public LongFilter getInvoiceTransactionsId() {
        return invoiceTransactionsId;
    }

    public LongFilter invoiceTransactionsId() {
        if (invoiceTransactionsId == null) {
            invoiceTransactionsId = new LongFilter();
        }
        return invoiceTransactionsId;
    }

    public void setInvoiceTransactionsId(LongFilter invoiceTransactionsId) {
        this.invoiceTransactionsId = invoiceTransactionsId;
    }

    public LongFilter getInvoiceDetailsId() {
        return invoiceDetailsId;
    }

    public LongFilter invoiceDetailsId() {
        if (invoiceDetailsId == null) {
            invoiceDetailsId = new LongFilter();
        }
        return invoiceDetailsId;
    }

    public void setInvoiceDetailsId(LongFilter invoiceDetailsId) {
        this.invoiceDetailsId = invoiceDetailsId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InvoicesCriteria that = (InvoicesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceNumber, that.invoiceNumber) &&
            Objects.equals(contactId, that.contactId) &&
            Objects.equals(contactAddressId, that.contactAddressId) &&
            Objects.equals(contactBillingAdrId, that.contactBillingAdrId) &&
            Objects.equals(cartId, that.cartId) &&
            Objects.equals(type, that.type) &&
            Objects.equals(requestDate, that.requestDate) &&
            Objects.equals(contactName, that.contactName) &&
            Objects.equals(invoiceDate, that.invoiceDate) &&
            Objects.equals(lastTranactionId, that.lastTranactionId) &&
            Objects.equals(totalCost, that.totalCost) &&
            Objects.equals(totalProfit, that.totalProfit) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(totalTaxes, that.totalTaxes) &&
            Objects.equals(fedaralTaxesAmount, that.fedaralTaxesAmount) &&
            Objects.equals(provintionalTaxesAmount, that.provintionalTaxesAmount) &&
            Objects.equals(discountsAmount, that.discountsAmount) &&
            Objects.equals(addOnAmount, that.addOnAmount) &&
            Objects.equals(message, that.message) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(paymentId, that.paymentId) &&
            Objects.equals(invoiceTransactionsId, that.invoiceTransactionsId) &&
            Objects.equals(invoiceDetailsId, that.invoiceDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            invoiceNumber,
            contactId,
            contactAddressId,
            contactBillingAdrId,
            cartId,
            type,
            requestDate,
            contactName,
            invoiceDate,
            lastTranactionId,
            totalCost,
            totalProfit,
            totalAmount,
            totalTaxes,
            fedaralTaxesAmount,
            provintionalTaxesAmount,
            discountsAmount,
            addOnAmount,
            message,
            createdDate,
            paymentId,
            invoiceTransactionsId,
            invoiceDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoicesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber + ", " : "") +
            (contactId != null ? "contactId=" + contactId + ", " : "") +
            (contactAddressId != null ? "contactAddressId=" + contactAddressId + ", " : "") +
            (contactBillingAdrId != null ? "contactBillingAdrId=" + contactBillingAdrId + ", " : "") +
            (cartId != null ? "cartId=" + cartId + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (requestDate != null ? "requestDate=" + requestDate + ", " : "") +
            (contactName != null ? "contactName=" + contactName + ", " : "") +
            (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", " : "") +
            (lastTranactionId != null ? "lastTranactionId=" + lastTranactionId + ", " : "") +
            (totalCost != null ? "totalCost=" + totalCost + ", " : "") +
            (totalProfit != null ? "totalProfit=" + totalProfit + ", " : "") +
            (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
            (totalTaxes != null ? "totalTaxes=" + totalTaxes + ", " : "") +
            (fedaralTaxesAmount != null ? "fedaralTaxesAmount=" + fedaralTaxesAmount + ", " : "") +
            (provintionalTaxesAmount != null ? "provintionalTaxesAmount=" + provintionalTaxesAmount + ", " : "") +
            (discountsAmount != null ? "discountsAmount=" + discountsAmount + ", " : "") +
            (addOnAmount != null ? "addOnAmount=" + addOnAmount + ", " : "") +
            (message != null ? "message=" + message + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (paymentId != null ? "paymentId=" + paymentId + ", " : "") +
            (invoiceTransactionsId != null ? "invoiceTransactionsId=" + invoiceTransactionsId + ", " : "") +
            (invoiceDetailsId != null ? "invoiceDetailsId=" + invoiceDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
