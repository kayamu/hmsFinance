package com.polarbears.capstone.hmsfinance.service.criteria;

import com.polarbears.capstone.hmsfinance.domain.enumeration.VALUETYPES;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmsfinance.domain.Conditions} entity. This class is used
 * in {@link com.polarbears.capstone.hmsfinance.web.rest.ConditionsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /conditions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConditionsCriteria implements Serializable, Criteria {

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

    private VALUETYPESFilter type;

    private LocalDateFilter createdDate;

    private LongFilter templateItemId;

    private LongFilter conditionDetailsId;

    private Boolean distinct;

    public ConditionsCriteria() {}

    public ConditionsCriteria(ConditionsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.templateItemId = other.templateItemId == null ? null : other.templateItemId.copy();
        this.conditionDetailsId = other.conditionDetailsId == null ? null : other.conditionDetailsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ConditionsCriteria copy() {
        return new ConditionsCriteria(this);
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

    public VALUETYPESFilter getType() {
        return type;
    }

    public VALUETYPESFilter type() {
        if (type == null) {
            type = new VALUETYPESFilter();
        }
        return type;
    }

    public void setType(VALUETYPESFilter type) {
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

    public LongFilter getConditionDetailsId() {
        return conditionDetailsId;
    }

    public LongFilter conditionDetailsId() {
        if (conditionDetailsId == null) {
            conditionDetailsId = new LongFilter();
        }
        return conditionDetailsId;
    }

    public void setConditionDetailsId(LongFilter conditionDetailsId) {
        this.conditionDetailsId = conditionDetailsId;
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
        final ConditionsCriteria that = (ConditionsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(templateItemId, that.templateItemId) &&
            Objects.equals(conditionDetailsId, that.conditionDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, createdDate, templateItemId, conditionDetailsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConditionsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (templateItemId != null ? "templateItemId=" + templateItemId + ", " : "") +
            (conditionDetailsId != null ? "conditionDetailsId=" + conditionDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
