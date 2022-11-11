package com.polarbears.capstone.hmsfinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmsfinance.domain.enumeration.FIELDS;
import com.polarbears.capstone.hmsfinance.domain.enumeration.LOGICTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.OPERATORS;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ConditionDetails.
 */
@Entity
@Table(name = "condition_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConditionDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "explanation")
    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(name = "compare_field")
    private FIELDS compareField;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator")
    private OPERATORS operator;

    @Column(name = "group_index")
    private Integer groupIndex;

    @Column(name = "compare_value")
    private String compareValue;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "line_logic_type")
    private LOGICTYPES lineLogicType;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_logic_type")
    private LOGICTYPES groupLogicType;

    @Column(name = "next_condition")
    private Long nextCondition;

    @ManyToMany(mappedBy = "conditionDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "templateItems", "conditionDetails" }, allowSetters = true)
    private Set<Conditions> conditions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ConditionDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ConditionDetails name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public ConditionDetails explanation(String explanation) {
        this.setExplanation(explanation);
        return this;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public FIELDS getCompareField() {
        return this.compareField;
    }

    public ConditionDetails compareField(FIELDS compareField) {
        this.setCompareField(compareField);
        return this;
    }

    public void setCompareField(FIELDS compareField) {
        this.compareField = compareField;
    }

    public OPERATORS getOperator() {
        return this.operator;
    }

    public ConditionDetails operator(OPERATORS operator) {
        this.setOperator(operator);
        return this;
    }

    public void setOperator(OPERATORS operator) {
        this.operator = operator;
    }

    public Integer getGroupIndex() {
        return this.groupIndex;
    }

    public ConditionDetails groupIndex(Integer groupIndex) {
        this.setGroupIndex(groupIndex);
        return this;
    }

    public void setGroupIndex(Integer groupIndex) {
        this.groupIndex = groupIndex;
    }

    public String getCompareValue() {
        return this.compareValue;
    }

    public ConditionDetails compareValue(String compareValue) {
        this.setCompareValue(compareValue);
        return this;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public ConditionDetails createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LOGICTYPES getLineLogicType() {
        return this.lineLogicType;
    }

    public ConditionDetails lineLogicType(LOGICTYPES lineLogicType) {
        this.setLineLogicType(lineLogicType);
        return this;
    }

    public void setLineLogicType(LOGICTYPES lineLogicType) {
        this.lineLogicType = lineLogicType;
    }

    public LOGICTYPES getGroupLogicType() {
        return this.groupLogicType;
    }

    public ConditionDetails groupLogicType(LOGICTYPES groupLogicType) {
        this.setGroupLogicType(groupLogicType);
        return this;
    }

    public void setGroupLogicType(LOGICTYPES groupLogicType) {
        this.groupLogicType = groupLogicType;
    }

    public Long getNextCondition() {
        return this.nextCondition;
    }

    public ConditionDetails nextCondition(Long nextCondition) {
        this.setNextCondition(nextCondition);
        return this;
    }

    public void setNextCondition(Long nextCondition) {
        this.nextCondition = nextCondition;
    }

    public Set<Conditions> getConditions() {
        return this.conditions;
    }

    public void setConditions(Set<Conditions> conditions) {
        if (this.conditions != null) {
            this.conditions.forEach(i -> i.removeConditionDetails(this));
        }
        if (conditions != null) {
            conditions.forEach(i -> i.addConditionDetails(this));
        }
        this.conditions = conditions;
    }

    public ConditionDetails conditions(Set<Conditions> conditions) {
        this.setConditions(conditions);
        return this;
    }

    public ConditionDetails addCondition(Conditions conditions) {
        this.conditions.add(conditions);
        conditions.getConditionDetails().add(this);
        return this;
    }

    public ConditionDetails removeCondition(Conditions conditions) {
        this.conditions.remove(conditions);
        conditions.getConditionDetails().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConditionDetails)) {
            return false;
        }
        return id != null && id.equals(((ConditionDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConditionDetails{" +
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
