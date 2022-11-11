package com.polarbears.capstone.hmsfinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmsfinance.domain.enumeration.ITEMTYPES;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Items.
 */
@Entity
@Table(name = "items")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_code")
    private String itemCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ITEMTYPES type;

    @Column(name = "explain")
    private String explain;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "items", "templateItems" }, allowSetters = true)
    private Templates templates;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Items id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Items name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public Items itemId(Long itemId) {
        this.setItemId(itemId);
        return this;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public Items itemCode(String itemCode) {
        this.setItemCode(itemCode);
        return this;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public ITEMTYPES getType() {
        return this.type;
    }

    public Items type(ITEMTYPES type) {
        this.setType(type);
        return this;
    }

    public void setType(ITEMTYPES type) {
        this.type = type;
    }

    public String getExplain() {
        return this.explain;
    }

    public Items explain(String explain) {
        this.setExplain(explain);
        return this;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Double getCost() {
        return this.cost;
    }

    public Items cost(Double cost) {
        this.setCost(cost);
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPrice() {
        return this.price;
    }

    public Items price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Items isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Items createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Templates getTemplates() {
        return this.templates;
    }

    public void setTemplates(Templates templates) {
        this.templates = templates;
    }

    public Items templates(Templates templates) {
        this.setTemplates(templates);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Items)) {
            return false;
        }
        return id != null && id.equals(((Items) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Items{" +
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
            "}";
    }
}
