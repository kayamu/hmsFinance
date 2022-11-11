package com.polarbears.capstone.hmsfinance.service.dto;

import com.polarbears.capstone.hmsfinance.domain.enumeration.ITEMTYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.polarbears.capstone.hmsfinance.domain.Templates} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplatesDTO implements Serializable {

    private Long id;

    private String name;

    private ITEMTYPES type;

    private String explanation;

    private Boolean isActive;

    private LocalDate createdDate;

    private Set<TemplateItemsDTO> templateItems = new HashSet<>();

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

    public ITEMTYPES getType() {
        return type;
    }

    public void setType(ITEMTYPES type) {
        this.type = type;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<TemplateItemsDTO> getTemplateItems() {
        return templateItems;
    }

    public void setTemplateItems(Set<TemplateItemsDTO> templateItems) {
        this.templateItems = templateItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplatesDTO)) {
            return false;
        }

        TemplatesDTO templatesDTO = (TemplatesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, templatesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplatesDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", explanation='" + getExplanation() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", templateItems=" + getTemplateItems() +
            "}";
    }
}
