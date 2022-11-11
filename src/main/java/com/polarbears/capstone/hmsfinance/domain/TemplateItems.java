package com.polarbears.capstone.hmsfinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmsfinance.domain.enumeration.DETAILTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.VALUETYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TemplateItems.
 */
@Entity
@Table(name = "template_items")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplateItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DETAILTYPES type;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type")
    private VALUETYPES valueType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "is_once")
    private Boolean isOnce;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "templateItems", "conditionDetails" }, allowSetters = true)
    private Conditions conditions;

    @ManyToMany(mappedBy = "templateItems")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "items", "templateItems" }, allowSetters = true)
    private Set<Templates> templates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TemplateItems id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public TemplateItems name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public TemplateItems code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DETAILTYPES getType() {
        return this.type;
    }

    public TemplateItems type(DETAILTYPES type) {
        this.setType(type);
        return this;
    }

    public void setType(DETAILTYPES type) {
        this.type = type;
    }

    public VALUETYPES getValueType() {
        return this.valueType;
    }

    public TemplateItems valueType(VALUETYPES valueType) {
        this.setValueType(valueType);
        return this;
    }

    public void setValueType(VALUETYPES valueType) {
        this.valueType = valueType;
    }

    public Double getAmount() {
        return this.amount;
    }

    public TemplateItems amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public TemplateItems explanation(String explanation) {
        this.setExplanation(explanation);
        return this;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public TemplateItems startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public TemplateItems dueDate(LocalDate dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getIsOnce() {
        return this.isOnce;
    }

    public TemplateItems isOnce(Boolean isOnce) {
        this.setIsOnce(isOnce);
        return this;
    }

    public void setIsOnce(Boolean isOnce) {
        this.isOnce = isOnce;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public TemplateItems createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Conditions getConditions() {
        return this.conditions;
    }

    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }

    public TemplateItems conditions(Conditions conditions) {
        this.setConditions(conditions);
        return this;
    }

    public Set<Templates> getTemplates() {
        return this.templates;
    }

    public void setTemplates(Set<Templates> templates) {
        if (this.templates != null) {
            this.templates.forEach(i -> i.removeTemplateItems(this));
        }
        if (templates != null) {
            templates.forEach(i -> i.addTemplateItems(this));
        }
        this.templates = templates;
    }

    public TemplateItems templates(Set<Templates> templates) {
        this.setTemplates(templates);
        return this;
    }

    public TemplateItems addTemplates(Templates templates) {
        this.templates.add(templates);
        templates.getTemplateItems().add(this);
        return this;
    }

    public TemplateItems removeTemplates(Templates templates) {
        this.templates.remove(templates);
        templates.getTemplateItems().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TemplateItems)) {
            return false;
        }
        return id != null && id.equals(((TemplateItems) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateItems{" +
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
            "}";
    }
}
