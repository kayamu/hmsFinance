package com.polarbears.capstone.hmsfinance.service.criteria;

import com.polarbears.capstone.hmsfinance.domain.enumeration.INVOICETYPES;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmsfinance.domain.InvoiceTransactions} entity. This class is used
 * in {@link com.polarbears.capstone.hmsfinance.web.rest.InvoiceTransactionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoice-transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoiceTransactionsCriteria implements Serializable, Criteria {

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

    private LocalDateFilter statusChangedDate;

    private LocalDateFilter transactionDate;

    private INVOICETYPESFilter type;

    private LocalDateFilter createdDate;

    private LongFilter invoicesId;

    private Boolean distinct;

    public InvoiceTransactionsCriteria() {}

    public InvoiceTransactionsCriteria(InvoiceTransactionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.statusChangedDate = other.statusChangedDate == null ? null : other.statusChangedDate.copy();
        this.transactionDate = other.transactionDate == null ? null : other.transactionDate.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.invoicesId = other.invoicesId == null ? null : other.invoicesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InvoiceTransactionsCriteria copy() {
        return new InvoiceTransactionsCriteria(this);
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

    public LocalDateFilter getStatusChangedDate() {
        return statusChangedDate;
    }

    public LocalDateFilter statusChangedDate() {
        if (statusChangedDate == null) {
            statusChangedDate = new LocalDateFilter();
        }
        return statusChangedDate;
    }

    public void setStatusChangedDate(LocalDateFilter statusChangedDate) {
        this.statusChangedDate = statusChangedDate;
    }

    public LocalDateFilter getTransactionDate() {
        return transactionDate;
    }

    public LocalDateFilter transactionDate() {
        if (transactionDate == null) {
            transactionDate = new LocalDateFilter();
        }
        return transactionDate;
    }

    public void setTransactionDate(LocalDateFilter transactionDate) {
        this.transactionDate = transactionDate;
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
        final InvoiceTransactionsCriteria that = (InvoiceTransactionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(statusChangedDate, that.statusChangedDate) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(type, that.type) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(invoicesId, that.invoicesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusChangedDate, transactionDate, type, createdDate, invoicesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceTransactionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (statusChangedDate != null ? "statusChangedDate=" + statusChangedDate + ", " : "") +
            (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (invoicesId != null ? "invoicesId=" + invoicesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
