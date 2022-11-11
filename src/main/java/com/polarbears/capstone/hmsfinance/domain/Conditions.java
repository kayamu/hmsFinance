package com.polarbears.capstone.hmsfinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmsfinance.domain.enumeration.VALUETYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Conditions.
 */
@Entity
@Table(name = "conditions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Conditions implements Serializable {

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
    private VALUETYPES type;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(mappedBy = "conditions")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "conditions", "templates" }, allowSetters = true)
    private Set<TemplateItems> templateItems = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_conditions__condition_details",
        joinColumns = @JoinColumn(name = "conditions_id"),
        inverseJoinColumns = @JoinColumn(name = "condition_details_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "conditions" }, allowSetters = true)
    private Set<ConditionDetails> conditionDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Conditions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Conditions name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VALUETYPES getType() {
        return this.type;
    }

    public Conditions type(VALUETYPES type) {
        this.setType(type);
        return this;
    }

    public void setType(VALUETYPES type) {
        this.type = type;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Conditions createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<TemplateItems> getTemplateItems() {
        return this.templateItems;
    }

    public void setTemplateItems(Set<TemplateItems> templateItems) {
        if (this.templateItems != null) {
            this.templateItems.forEach(i -> i.setConditions(null));
        }
        if (templateItems != null) {
            templateItems.forEach(i -> i.setConditions(this));
        }
        this.templateItems = templateItems;
    }

    public Conditions templateItems(Set<TemplateItems> templateItems) {
        this.setTemplateItems(templateItems);
        return this;
    }

    public Conditions addTemplateItem(TemplateItems templateItems) {
        this.templateItems.add(templateItems);
        templateItems.setConditions(this);
        return this;
    }

    public Conditions removeTemplateItem(TemplateItems templateItems) {
        this.templateItems.remove(templateItems);
        templateItems.setConditions(null);
        return this;
    }

    public Set<ConditionDetails> getConditionDetails() {
        return this.conditionDetails;
    }

    public void setConditionDetails(Set<ConditionDetails> conditionDetails) {
        this.conditionDetails = conditionDetails;
    }

    public Conditions conditionDetails(Set<ConditionDetails> conditionDetails) {
        this.setConditionDetails(conditionDetails);
        return this;
    }

    public Conditions addConditionDetails(ConditionDetails conditionDetails) {
        this.conditionDetails.add(conditionDetails);
        conditionDetails.getConditions().add(this);
        return this;
    }

    public Conditions removeConditionDetails(ConditionDetails conditionDetails) {
        this.conditionDetails.remove(conditionDetails);
        conditionDetails.getConditions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conditions)) {
            return false;
        }
        return id != null && id.equals(((Conditions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Conditions{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
