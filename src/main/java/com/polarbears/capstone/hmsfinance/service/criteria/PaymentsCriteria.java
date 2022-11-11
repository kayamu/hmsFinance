package com.polarbears.capstone.hmsfinance.service.criteria;

import com.polarbears.capstone.hmsfinance.domain.enumeration.PAYMENTSTATUS;
import com.polarbears.capstone.hmsfinance.domain.enumeration.PAYMENTTYPES;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmsfinance.domain.Payments} entity. This class is used
 * in {@link com.polarbears.capstone.hmsfinance.web.rest.PaymentsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /payments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering PAYMENTTYPES
     */
    public static class PAYMENTTYPESFilter extends Filter<PAYMENTTYPES> {

        public PAYMENTTYPESFilter() {}

        public PAYMENTTYPESFilter(PAYMENTTYPESFilter filter) {
            super(filter);
        }

        @Override
        public PAYMENTTYPESFilter copy() {
            return new PAYMENTTYPESFilter(this);
        }
    }

    /**
     * Class for filtering PAYMENTSTATUS
     */
    public static class PAYMENTSTATUSFilter extends Filter<PAYMENTSTATUS> {

        public PAYMENTSTATUSFilter() {}

        public PAYMENTSTATUSFilter(PAYMENTSTATUSFilter filter) {
            super(filter);
        }

        @Override
        public PAYMENTSTATUSFilter copy() {
            return new PAYMENTSTATUSFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter refNumber;

    private PAYMENTTYPESFilter paymentType;

    private LongFilter contactId;

    private StringFilter explanation;

    private LocalDateFilter operationDate;

    private DoubleFilter amount;

    private PAYMENTSTATUSFilter status;

    private LocalDateFilter createdDate;

    private LongFilter invoicesId;

    private Boolean distinct;

    public PaymentsCriteria() {}

    public PaymentsCriteria(PaymentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.refNumber = other.refNumber == null ? null : other.refNumber.copy();
        this.paymentType = other.paymentType == null ? null : other.paymentType.copy();
        this.contactId = other.contactId == null ? null : other.contactId.copy();
        this.explanation = other.explanation == null ? null : other.explanation.copy();
        this.operationDate = other.operationDate == null ? null : other.operationDate.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.invoicesId = other.invoicesId == null ? null : other.invoicesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PaymentsCriteria copy() {
        return new PaymentsCriteria(this);
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

    public StringFilter getRefNumber() {
        return refNumber;
    }

    public StringFilter refNumber() {
        if (refNumber == null) {
            refNumber = new StringFilter();
        }
        return refNumber;
    }

    public void setRefNumber(StringFilter refNumber) {
        this.refNumber = refNumber;
    }

    public PAYMENTTYPESFilter getPaymentType() {
        return paymentType;
    }

    public PAYMENTTYPESFilter paymentType() {
        if (paymentType == null) {
            paymentType = new PAYMENTTYPESFilter();
        }
        return paymentType;
    }

    public void setPaymentType(PAYMENTTYPESFilter paymentType) {
        this.paymentType = paymentType;
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

    public StringFilter getExplanation() {
        return explanation;
    }

    public StringFilter explanation() {
        if (explanation == null) {
            explanation = new StringFilter();
        }
        return explanation;
    }

    public void setExplanation(StringFilter explanation) {
        this.explanation = explanation;
    }

    public LocalDateFilter getOperationDate() {
        return operationDate;
    }

    public LocalDateFilter operationDate() {
        if (operationDate == null) {
            operationDate = new LocalDateFilter();
        }
        return operationDate;
    }

    public void setOperationDate(LocalDateFilter operationDate) {
        this.operationDate = operationDate;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public DoubleFilter amount() {
        if (amount == null) {
            amount = new DoubleFilter();
        }
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public PAYMENTSTATUSFilter getStatus() {
        return status;
    }

    public PAYMENTSTATUSFilter status() {
        if (status == null) {
            status = new PAYMENTSTATUSFilter();
        }
        return status;
    }

    public void setStatus(PAYMENTSTATUSFilter status) {
        this.status = status;
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

    public LongFilter getInvoicesId() {
        return invoicesId;
    }

    public LongFilter invoicesId() {
        if (invoicesId == null) {
            invoicesId = new LongFilter();
        }
        return invoicesId;
    }

    public void setInvoicesId(LongFilter invoicesId) {
        this.invoicesId = invoicesId;
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
        final PaymentsCriteria that = (PaymentsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(refNumber, that.refNumber) &&
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(contactId, that.contactId) &&
            Objects.equals(explanation, that.explanation) &&
            Objects.equals(operationDate, that.operationDate) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(invoicesId, that.invoicesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            refNumber,
            paymentType,
            contactId,
            explanation,
            operationDate,
            amount,
            status,
            createdDate,
            invoicesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (refNumber != null ? "refNumber=" + refNumber + ", " : "") +
            (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
            (contactId != null ? "contactId=" + contactId + ", " : "") +
            (explanation != null ? "explanation=" + explanation + ", " : "") +
            (operationDate != null ? "operationDate=" + operationDate + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (invoicesId != null ? "invoicesId=" + invoicesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
