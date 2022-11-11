package com.polarbears.capstone.hmsfinance.service.dto;

import com.polarbears.capstone.hmsfinance.domain.enumeration.FIELDS;
import com.polarbears.capstone.hmsfinance.domain.enumeration.LOGICTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.OPERATORS;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.polarbears.capstone.hmsfinance.domain.ConditionDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConditionDetailsDTO implements Serializable {

    private Long id;

    private String name;

    private String explanation;

    private FIELDS compareField;

    private OPERATORS operator;

    private Integer groupIndex;

    private String compareValue;

    private LocalDate createdDate;

    private LOGICTYPES lineLogicType;

    private LOGICTYPES groupLogicType;

    private Long nextCondition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public FIELDS getCompareField() {
        return compareField;
    }

    public void setCompareField(FIELDS compareField) {
        this.compareField = compareField;
    }

    public OPERATORS getOperator() {
        return operator;
    }

    public void setOperator(OPERATORS operator) {
        this.operator = operator;
    }

    public Integer getGroupIndex() {
        return groupIndex;
    }

    public void setGroupIndex(Integer groupIndex) {
        this.groupIndex = groupIndex;
    }

    public String getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LOGICTYPES getLineLogicType() {
        return lineLogicType;
    }

    public void setLineLogicType(LOGICTYPES lineLogicType) {
        this.lineLogicType = lineLogicType;
    }

    public LOGICTYPES getGroupLogicType() {
        return groupLogicType;
    }

    public void setGroupLogicType(LOGICTYPES groupLogicType) {
        this.groupLogicType = groupLogicType;
    }

    public Long getNextCondition() {
        return nextCondition;
    }

    public void setNextCondition(Long nextCondition) {
        this.nextCondition = nextCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConditionDetailsDTO)) {
            return false;
        }

        ConditionDetailsDTO conditionDetailsDTO = (ConditionDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, conditionDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConditionDetailsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", explanation='" + getExplanation() + "'" +
            ", compareField='" + getCompareField() + "'" +
            ", operator='" + getOperator() + "'" +
            ", groupIndex=" + getGroupIndex() +
            ", compareValue='" + getCompareValue() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lineLogicType='" + getLineLogicType() + "'" +
            ", groupLogicType='" + getGroupLogicType() + "'" +
            ", nextCondition=" + getNextCondition() +
            "}";
    }
}
