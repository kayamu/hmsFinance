package com.polarbears.capstone.hmsfinance.service.criteria;

import com.polarbears.capstone.hmsfinance.domain.enumeration.FIELDS;
import com.polarbears.capstone.hmsfinance.domain.enumeration.LOGICTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.LOGICTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.OPERATORS;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmsfinance.domain.ConditionDetails} entity. This class is used
 * in {@link com.polarbears.capstone.hmsfinance.web.rest.ConditionDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /condition-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConditionDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FIELDS
     */
    public static class FIELDSFilter extends Filter<FIELDS> {

        public FIELDSFilter() {}

        public FIELDSFilter(FIELDSFilter filter) {
            super(filter);
        }

        @Override
        public FIELDSFilter copy() {
            return new FIELDSFilter(this);
        }
    }

    /**
     * Class for filtering OPERATORS
     */
    public static class OPERATORSFilter extends Filter<OPERATORS> {

        public OPERATORSFilter() {}

        public OPERATORSFilter(OPERATORSFilter filter) {
            super(filter);
        }

        @Override
        public OPERATORSFilter copy() {
            return new OPERATORSFilter(this);
        }
    }

    /**
     * Class for filtering LOGICTYPES
     */
    public static class LOGICTYPESFilter extends Filter<LOGICTYPES> {

        public LOGICTYPESFilter() {}

        public LOGICTYPESFilter(LOGICTYPESFilter filter) {
            super(filter);
        }

        @Override
        public LOGICTYPESFilter copy() {
            return new LOGICTYPESFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter explanation;

    private FIELDSFilter compareField;

    private OPERATORSFilter operator;

    private IntegerFilter groupIndex;

    private StringFilter compareValue;

    private LocalDateFilter createdDate;

    private LOGICTYPESFilter lineLogicType;

    private LOGICTYPESFilter groupLogicType;

    private LongFilter nextCondition;

    private LongFilter conditionId;

    private Boolean distinct;

    public ConditionDetailsCriteria() {}

    public ConditionDetailsCriteria(ConditionDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.explanation = other.explanation == null ? null : other.explanation.copy();
        this.compareField = other.compareField == null ? null : other.compareField.copy();
        this.operator = other.operator == null ? null : other.operator.copy();
        this.groupIndex = other.groupIndex == null ? null : other.groupIndex.copy();
        this.compareValue = other.compareValue == null ? null : other.compareValue.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.lineLogicType = other.lineLogicType == null ? null : other.lineLogicType.copy();
        this.groupLogicType = other.groupLogicType == null ? null : other.groupLogicType.copy();
        this.nextCondition = other.nextCondition == null ? null : other.nextCondition.copy();
        this.conditionId = other.conditionId == null ? null : other.conditionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ConditionDetailsCriteria copy() {
        return new ConditionDetailsCriteria(this);
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

    public FIELDSFilter getCompareField() {
        return compareField;
    }

    public FIELDSFilter compareField() {
        if (compareField == null) {
            compareField = new FIELDSFilter();
        }
        return compareField;
    }

    public void setCompareField(FIELDSFilter compareField) {
        this.compareField = compareField;
    }

    public OPERATORSFilter getOperator() {
        return operator;
    }

    public OPERATORSFilter operator() {
        if (operator == null) {
            operator = new OPERATORSFilter();
        }
        return operator;
    }

    public void setOperator(OPERATORSFilter operator) {
        this.operator = operator;
    }

    public IntegerFilter getGroupIndex() {
        return groupIndex;
    }

    public IntegerFilter groupIndex() {
        if (groupIndex == null) {
            groupIndex = new IntegerFilter();
        }
        return groupIndex;
    }

    public void setGroupIndex(IntegerFilter groupIndex) {
        this.groupIndex = groupIndex;
    }

    public StringFilter getCompareValue() {
        return compareValue;
    }

    public StringFilter compareValue() {
        if (compareValue == null) {
            compareValue = new StringFilter();
        }
        return compareValue;
    }

    public void setCompareValue(StringFilter compareValue) {
        this.compareValue = compareValue;
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

    public LOGICTYPESFilter getLineLogicType() {
        return lineLogicType;
    }

    public LOGICTYPESFilter lineLogicType() {
        if (lineLogicType == null) {
            lineLogicType = new LOGICTYPESFilter();
        }
        return lineLogicType;
    }

    public void setLineLogicType(LOGICTYPESFilter lineLogicType) {
        this.lineLogicType = lineLogicType;
    }

    public LOGICTYPESFilter getGroupLogicType() {
        return groupLogicType;
    }

    public LOGICTYPESFilter groupLogicType() {
        if (groupLogicType == null) {
            groupLogicType = new LOGICTYPESFilter();
        }
        return groupLogicType;
    }

    public void setGroupLogicType(LOGICTYPESFilter groupLogicType) {
        this.groupLogicType = groupLogicType;
    }

    public LongFilter getNextCondition() {
        return nextCondition;
    }

    public LongFilter nextCondition() {
        if (nextCondition == null) {
            nextCondition = new LongFilter();
        }
        return nextCondition;
    }

    public void setNextCondition(LongFilter nextCondition) {
        this.nextCondition = nextCondition;
    }

    public LongFilter getConditionId() {
        return conditionId;
    }

    public LongFilter conditionId() {
        if (conditionId == null) {
            conditionId = new LongFilter();
        }
        return conditionId;
    }

    public void setConditionId(LongFilter conditionId) {
        this.conditionId = conditionId;
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
        final ConditionDetailsCriteria that = (ConditionDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(explanation, that.explanation) &&
            Objects.equals(compareField, that.compareField) &&
            Objects.equals(operator, that.operator) &&
            Objects.equals(groupIndex, that.groupIndex) &&
            Objects.equals(compareValue, that.compareValue) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lineLogicType, that.lineLogicType) &&
            Objects.equals(groupLogicType, that.groupLogicType) &&
            Objects.equals(nextCondition, that.nextCondition) &&
            Objects.equals(conditionId, that.conditionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            explanation,
            compareField,
            operator,
            groupIndex,
            compareValue,
            createdDate,
            lineLogicType,
            groupLogicType,
            nextCondition,
            conditionId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConditionDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (explanation != null ? "explanation=" + explanation + ", " : "") +
            (compareField != null ? "compareField=" + compareField + ", " : "") +
            (operator != null ? "operator=" + operator + ", " : "") +
            (groupIndex != null ? "groupIndex=" + groupIndex + ", " : "") +
            (compareValue != null ? "compareValue=" + compareValue + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (lineLogicType != null ? "lineLogicType=" + lineLogicType + ", " : "") +
            (groupLogicType != null ? "groupLogicType=" + groupLogicType + ", " : "") +
            (nextCondition != null ? "nextCondition=" + nextCondition + ", " : "") +
            (conditionId != null ? "conditionId=" + conditionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
