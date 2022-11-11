package com.polarbears.capstone.hmsfinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmsfinance.domain.enumeration.ITEMTYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Templates.
 */
@Entity
@Table(name = "templates")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Templates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ITEMTYPES type;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(mappedBy = "templates")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "templates" }, allowSetters = true)
    private Set<Items> items = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_templates__template_items",
        joinColumns = @JoinColumn(name = "templates_id"),
        inverseJoinColumns = @JoinColumn(name = "template_items_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "conditions", "templates" }, allowSetters = true)
    private Set<TemplateItems> templateItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Templates id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Templates name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ITEMTYPES getType() {
        return this.type;
    }

    public Templates type(ITEMTYPES type) {
        this.setType(type);
        return this;
    }

    public void setType(ITEMTYPES type) {
        this.type = type;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public Templates explanation(String explanation) {
        this.setExplanation(explanation);
        return this;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Templates isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Templates createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Items> getItems() {
        return this.items;
    }

    public void setItems(Set<Items> items) {
        if (this.items != null) {
            this.items.forEach(i -> i.setTemplates(null));
        }
        if (items != null) {
            items.forEach(i -> i.setTemplates(this));
        }
        this.items = items;
    }

    public Templates items(Set<Items> items) {
        this.setItems(items);
        return this;
    }

    public Templates addItems(Items items) {
        this.items.add(items);
        items.setTemplates(this);
        return this;
    }

    public Templates removeItems(Items items) {
        this.items.remove(items);
        items.setTemplates(null);
        return this;
    }

    public Set<TemplateItems> getTemplateItems() {
        return this.templateItems;
    }

    public void setTemplateItems(Set<TemplateItems> templateItems) {
        this.templateItems = templateItems;
    }

    public Templates templateItems(Set<TemplateItems> templateItems) {
        this.setTemplateItems(templateItems);
        return this;
    }

    public Templates addTemplateItems(TemplateItems templateItems) {
        this.templateItems.add(templateItems);
        templateItems.getTemplates().add(this);
        return this;
    }

    public Templates removeTemplateItems(TemplateItems templateItems) {
        this.templateItems.remove(templateItems);
        templateItems.getTemplates().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Templates)) {
            return false;
        }
        return id != null && id.equals(((Templates) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Templates{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", explanation='" + getExplanation() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
