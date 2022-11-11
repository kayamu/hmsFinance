package com.polarbears.capstone.hmsfinance.service.dto;

import com.polarbears.capstone.hmsfinance.domain.enumeration.ITEMTYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.polarbears.capstone.hmsfinance.domain.Items} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemsDTO implements Serializable {

    private Long id;

    private String name;

    private Long itemId;

    private String itemCode;

    private ITEMTYPES type;

    private String explain;

    private Double cost;

    private Double price;

    private Boolean isActive;

    private LocalDate createdDate;

    private TemplatesDTO templates;

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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public ITEMTYPES getType() {
        return type;
    }

    public void setType(ITEMTYPES type) {
        this.type = type;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public TemplatesDTO getTemplates() {
        return templates;
    }

    public void setTemplates(TemplatesDTO templates) {
        this.templates = templates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemsDTO)) {
            return false;
        }

        ItemsDTO itemsDTO = (ItemsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, itemsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", itemId=" + getItemId() +
            ", itemCode='" + getItemCode() + "'" +
            ", type='" + getType() + "'" +
            ", explain='" + getExplain() + "'" +
            ", cost=" + getCost() +
            ", price=" + getPrice() +
            ", isActive='" + getIsActive() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", templates=" + getTemplates() +
            "}";
    }
}
