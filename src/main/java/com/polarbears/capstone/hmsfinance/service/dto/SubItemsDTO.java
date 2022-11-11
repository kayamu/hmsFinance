package com.polarbears.capstone.hmsfinance.service.dto;

import com.polarbears.capstone.hmsfinance.domain.enumeration.DETAILTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.VALUETYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.polarbears.capstone.hmsfinance.domain.SubItems} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubItemsDTO implements Serializable {

    private Long id;

    private String name;

    private Double actualValue;

    private Double percentage;

    private Double baseValue;

    private DETAILTYPES type;

    private VALUETYPES valueType;

    private Double calculatedValue;

    private Long templateItemId;

    private LocalDate createdDate;

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

    public Double getActualValue() {
        return actualValue;
    }

    public void setActualValue(Double actualValue) {
        this.actualValue = actualValue;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(Double baseValue) {
        this.baseValue = baseValue;
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

    public Double getCalculatedValue() {
        return calculatedValue;
    }

    public void setCalculatedValue(Double calculatedValue) {
        this.calculatedValue = calculatedValue;
    }

    public Long getTemplateItemId() {
        return templateItemId;
    }

    public void setTemplateItemId(Long templateItemId) {
        this.templateItemId = templateItemId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubItemsDTO)) {
            return false;
        }

        SubItemsDTO subItemsDTO = (SubItemsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subItemsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubItemsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", actualValue=" + getActualValue() +
            ", percentage=" + getPercentage() +
            ", baseValue=" + getBaseValue() +
            ", type='" + getType() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", calculatedValue=" + getCalculatedValue() +
            ", templateItemId=" + getTemplateItemId() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
