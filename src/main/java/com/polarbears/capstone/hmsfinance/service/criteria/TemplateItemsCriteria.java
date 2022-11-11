package com.polarbears.capstone.hmsfinance.service.criteria;

import com.polarbears.capstone.hmsfinance.domain.enumeration.DETAILTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.VALUETYPES;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmsfinance.domain.TemplateItems} entity. This class is used
 * in {@link com.polarbears.capstone.hmsfinance.web.rest.TemplateItemsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /template-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateItemsCriteria implements Serializable, Criteria {

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

    private StringFilter code;

    private DETAILTYPESFilter type;

    private VALUETYPESFilter valueType;

    private DoubleFilter amount;

    private StringFilter explanation;

    private LocalDateFilter startDate;

    private LocalDateFilter dueDate;

    private BooleanFilter isOnce;

    private LocalDateFilter createdDate;

    private LongFilter conditionsId;

    private LongFilter templatesId;

    private Boolean distinct;

    public TemplateItemsCriteria() {}

    public TemplateItemsCriteria(TemplateItemsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.valueType = other.valueType == null ? null : other.valueType.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.explanation = other.explanation == null ? null : other.explanation.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.dueDate = other.dueDate == null ? null : other.dueDate.copy();
        this.isOnce = other.isOnce == null ? null : other.isOnce.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.conditionsId = other.conditionsId == null ? null : other.conditionsId.copy();
        this.templatesId = other.templatesId == null ? null : other.templatesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TemplateItemsCriteria copy() {
        return new TemplateItemsCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
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

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public LocalDateFilter startDate() {
        if (startDate == null) {
            startDate = new LocalDateFilter();
        }
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public LocalDateFilter dueDate() {
        if (dueDate == null) {
            dueDate = new LocalDateFilter();
        }
        return dueDate;
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
    }

    public BooleanFilter getIsOnce() {
        return isOnce;
    }

    public BooleanFilter isOnce() {
        if (isOnce == null) {
            isOnce = new BooleanFilter();
        }
        return isOnce;
    }

    public void setIsOnce(BooleanFilter isOnce) {
        this.isOnce = isOnce;
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

    public LongFilter getConditionsId() {
        return conditionsId;
    }

    public LongFilter conditionsId() {
        if (conditionsId == null) {
            conditionsId = new LongFilter();
        }
        return conditionsId;
    }

    public void setConditionsId(LongFilter conditionsId) {
        this.conditionsId = conditionsId;
    }

    public LongFilter getTemplatesId() {
        return templatesId;
    }

    public LongFilter templatesId() {
        if (templatesId == null) {
            templatesId = new LongFilter();
        }
        return templatesId;
    }

    public void setTemplatesId(LongFilter templatesId) {
        this.templatesId = templatesId;
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
        final TemplateItemsCriteria that = (TemplateItemsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(type, that.type) &&
            Objects.equals(valueType, that.valueType) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(explanation, that.explanation) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(isOnce, that.isOnce) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(conditionsId, that.conditionsId) &&
            Objects.equals(templatesId, that.templatesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            code,
            type,
            valueType,
            amount,
            explanation,
            startDate,
            dueDate,
            isOnce,
            createdDate,
            conditionsId,
            templatesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateItemsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (valueType != null ? "valueType=" + valueType + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (explanation != null ? "explanation=" + explanation + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
            (isOnce != null ? "isOnce=" + isOnce + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (conditionsId != null ? "conditionsId=" + conditionsId + ", " : "") +
            (templatesId != null ? "templatesId=" + templatesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
