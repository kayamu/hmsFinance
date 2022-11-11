package com.polarbears.capstone.hmsfinance.service.dto;

import com.polarbears.capstone.hmsfinance.domain.enumeration.VALUETYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.polarbears.capstone.hmsfinance.domain.Conditions} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConditionsDTO implements Serializable {

    private Long id;

    private String name;

    private VALUETYPES type;

    private LocalDate createdDate;

    private Set<ConditionDetailsDTO> conditionDetails = new HashSet<>();

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

    public VALUETYPES getType() {
        return type;
    }

    public void setType(VALUETYPES type) {
        this.type = type;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<ConditionDetailsDTO> getConditionDetails() {
        return conditionDetails;
    }

    public void setConditionDetails(Set<ConditionDetailsDTO> conditionDetails) {
        this.conditionDetails = conditionDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConditionsDTO)) {
            return false;
        }

        ConditionsDTO conditionsDTO = (ConditionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, conditionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConditionsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", conditionDetails=" + getConditionDetails() +
            "}";
    }
}
