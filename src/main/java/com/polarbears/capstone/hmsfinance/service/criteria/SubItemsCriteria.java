package com.polarbears.capstone.hmsfinance.service.criteria;

import com.polarbears.capstone.hmsfinance.domain.enumeration.DETAILTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.VALUETYPES;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmsfinance.domain.SubItems} entity. This class is used
 * in {@link com.polarbears.capstone.hmsfinance.web.rest.SubItemsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sub-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubItemsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering DETAILTYPES
     */
    public static class DETAILTYPESFilter extends Filter<DETAILTYPES> {

        public DETAILTYPESFilter() {}

        public DETAILTYPESFilter(DETAILTYPESFilter filter) {
            super(filter);
        }

        @Override
        public DETAILTYPESFilter copy() {
            return new DETAILTYPESFilter(this);
        }
    }

    /**
     * Class for filtering VALUETYPES
     */
    public static class VALUETYPESFilter extends Filter<VALUETYPES> {

        public VALUETYPESFilter() {}

        public VALUETYPESFilter(VALUETYPESFilter filter) {
            super(filter);
        }

        @Override
        public VALUETYPESFilter copy() {
            return new VALUETYPESFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private DoubleFilter actualValue;

    private DoubleFilter percentage;

    private DoubleFilter baseValue;

    private DETAILTYPESFilter type;

    private VALUETYPESFilter valueType;

    private DoubleFilter calculatedValue;

    private LongFilter templateItemId;

    private LocalDateFilter createdDate;

    private LongFilter invoiceDetailId;

    private Boolean distinct;

    public SubItemsCriteria() {}

    public SubItemsCriteria(SubItemsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.actualValue = other.actualValue == null ? null : other.actualValue.copy();
        this.percentage = other.percentage == null ? null : other.percentage.copy();
        this.baseValue = other.baseValue == null ? null : other.baseValue.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.valueType = other.valueType == null ? null : other.valueType.copy();
        this.calculatedValue = other.calculatedValue == null ? null : other.calculatedValue.copy();
        this.templateItemId = other.templateItemId == null ? null : other.templateItemId.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.invoiceDetailId = other.invoiceDetailId == null ? null : other.invoiceDetailId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SubItemsCriteria copy() {
        return new SubItemsCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public DoubleFilter getActualValue() {
        return actualValue;
    }

    public DoubleFilter actualValue() {
        if (actualValue == null) {
            actualValue = new DoubleFilter();
        }
        return actualValue;
    }

    public void setActualValue(DoubleFilter actualValue) {
        this.actualValue = actualValue;
    }

    public DoubleFilter getPercentage() {
        return percentage;
    }

    public DoubleFilter percentage() {
        if (percentage == null) {
            percentage = new DoubleFilter();
        }
        return percentage;
    }

    public void setPercentage(DoubleFilter percentage) {
        this.percentage = percentage;
    }

    public DoubleFilter getBaseValue() {
        return baseValue;
    }

    public DoubleFilter baseValue() {
        if (baseValue == null) {
            baseValue = new DoubleFilter();
        }
        return baseValue;
    }

    public void setBaseValue(DoubleFilter baseValue) {
        this.baseValue = baseValue;
    }

    public DETAILTYPESFilter getType() {
        return type;
    }

    public DETAILTYPESFilter type() {
        if (type == null) {
            type = new DETAILTYPESFilter();
        }
        return type;
    }

    public void setType(DETAILTYPESFilter type) {
        this.type = type;
    }

    public VALUETYPESFilter getValueType() {
        return valueType;
    }

    public VALUETYPESFilter valueType() {
        if (valueType == null) {
            valueType = new VALUETYPESFilter();
        }
        return valueType;
    }

    public void setValueType(VALUETYPESFilter valueType) {
        this.valueType = valueType;
    }

    public DoubleFilter getCalculatedValue() {
        return calculatedValue;
    }

    public DoubleFilter calculatedValue() {
        if (calculatedValue == null) {
            calculatedValue = new DoubleFilter();
        }
        return calculatedValue;
    }

    public void setCalculatedValue(DoubleFilter calculatedValue) {
        this.calculatedValue = calculatedValue;
    }

    public LongFilter getTemplateItemId() {
        return templateItemId;
    }

    public LongFilter templateItemId() {
        if (templateItemId == null) {
            templateItemId = new LongFilter();
        }
        return templateItemId;
    }

    public void setTemplateItemId(LongFilter templateItemId) {
        this.templateItemId = templateItemId;
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

    public LongFilter getInvoiceDetailId() {
        return invoiceDetailId;
    }

    public LongFilter invoiceDetailId() {
        if (invoiceDetailId == null) {
            invoiceDetailId = new LongFilter();
        }
        return invoiceDetailId;
    }

    public void setInvoiceDetailId(LongFilter invoiceDetailId) {
        this.invoiceDetailId = invoiceDetailId;
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
        final SubItemsCriteria that = (SubItemsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(actualValue, that.actualValue) &&
            Objects.equals(percentage, that.percentage) &&
            Objects.equals(baseValue, that.baseValue) &&
            Objects.equals(type, that.type) &&
            Objects.equals(valueType, that.valueType) &&
            Objects.equals(calculatedValue, that.calculatedValue) &&
            Objects.equals(templateItemId, that.templateItemId) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(invoiceDetailId, that.invoiceDetailId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            actualValue,
            percentage,
            baseValue,
            type,
            valueType,
            calculatedValue,
            templateItemId,
            createdDate,
            invoiceDetailId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubItemsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (actualValue != null ? "actualValue=" + actualValue + ", " : "") +
            (percentage != null ? "percentage=" + percentage + ", " : "") +
            (baseValue != null ? "baseValue=" + baseValue + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (valueType != null ? "valueType=" + valueType + ", " : "") +
            (calculatedValue != null ? "calculatedValue=" + calculatedValue + ", " : "") +
            (templateItemId != null ? "templateItemId=" + templateItemId + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (invoiceDetailId != null ? "invoiceDetailId=" + invoiceDetailId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
