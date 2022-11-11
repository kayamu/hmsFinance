package com.polarbears.capstone.hmsfinance.service.dto;

import com.polarbears.capstone.hmsfinance.domain.enumeration.DETAILTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.VALUETYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.polarbears.capstone.hmsfinance.domain.TemplateItems} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateItemsDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

    private DETAILTYPES type;

    private VALUETYPES valueType;

    private Double amount;

    private String explanation;

    private LocalDate startDate;

    private LocalDate dueDate;

    private Boolean isOnce;

    private LocalDate createdDate;

    private ConditionsDTO conditions;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DETAILTYPES getType() {
        return type;
    }

    public void setType(DETAILTYPES type) {
        this.type = type;
    }

    public VALUETYPES getValueType() {
        return valueType;
    }

    public void setValueType(VALUETYPES valueType) {
        this.valueType = valueType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getIsOnce() {
        return isOnce;
    }

    public void setIsOnce(Boolean isOnce) {
        this.isOnce = isOnce;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public ConditionsDTO getConditions() {
        return conditions;
    }

    public void setConditions(ConditionsDTO conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateItemsDTO)) {
            return false;
        }

        TemplateItemsDTO templateItemsDTO = (TemplateItemsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, templateItemsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateItemsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", type='" + getType() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", amount=" + getAmount() +
            ", explanation='" + getExplanation() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", isOnce='" + getIsOnce() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", conditions=" + getConditions() +
            "}";
    }
}
