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
 * A SubItems.
 */
@Entity
@Table(name = "sub_items")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubItems implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "actual_value")
    private Double actualValue;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "base_value")
    private Double baseValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DETAILTYPES type;

    @Enumerated(EnumType.STRING)
    @Column(name = "value_type")
    private VALUETYPES valueType;

    @Column(name = "calculated_value")
    private Double calculatedValue;

    @Column(name = "template_item_id")
    private Long templateItemId;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToMany(mappedBy = "subItems")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subItems", "invoices" }, allowSetters = true)
    private Set<InvoiceDetails> invoiceDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubItems id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SubItems name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getActualValue() {
        return this.actualValue;
    }

    public SubItems actualValue(Double actualValue) {
        this.setActualValue(actualValue);
        return this;
    }

    public void setActualValue(Double actualValue) {
        this.actualValue = actualValue;
    }

    public Double getPercentage() {
        return this.percentage;
    }

    public SubItems percentage(Double percentage) {
        this.setPercentage(percentage);
        return this;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getBaseValue() {
        return this.baseValue;
    }

    public SubItems baseValue(Double baseValue) {
        this.setBaseValue(baseValue);
        return this;
    }

    public void setBaseValue(Double baseValue) {
        this.baseValue = baseValue;
    }

    public DETAILTYPES getType() {
        return this.type;
    }

    public SubItems type(DETAILTYPES type) {
        this.setType(type);
        return this;
    }

    public void setType(DETAILTYPES type) {
        this.type = type;
    }

    public VALUETYPES getValueType() {
        return this.valueType;
    }

    public SubItems valueType(VALUETYPES valueType) {
        this.setValueType(valueType);
        return this;
    }

    public void setValueType(VALUETYPES valueType) {
        this.valueType = valueType;
    }

    public Double getCalculatedValue() {
        return this.calculatedValue;
    }

    public SubItems calculatedValue(Double calculatedValue) {
        this.setCalculatedValue(calculatedValue);
        return this;
    }

    public void setCalculatedValue(Double calculatedValue) {
        this.calculatedValue = calculatedValue;
    }

    public Long getTemplateItemId() {
        return this.templateItemId;
    }

    public SubItems templateItemId(Long templateItemId) {
        this.setTemplateItemId(templateItemId);
        return this;
    }

    public void setTemplateItemId(Long templateItemId) {
        this.templateItemId = templateItemId;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public SubItems createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<InvoiceDetails> getInvoiceDetails() {
        return this.invoiceDetails;
    }

    public void setInvoiceDetails(Set<InvoiceDetails> invoiceDetails) {
        if (this.invoiceDetails != null) {
            this.invoiceDetails.forEach(i -> i.removeSubItems(this));
        }
        if (invoiceDetails != null) {
            invoiceDetails.forEach(i -> i.addSubItems(this));
        }
        this.invoiceDetails = invoiceDetails;
    }

    public SubItems invoiceDetails(Set<InvoiceDetails> invoiceDetails) {
        this.setInvoiceDetails(invoiceDetails);
        return this;
    }

    public SubItems addInvoiceDetail(InvoiceDetails invoiceDetails) {
        this.invoiceDetails.add(invoiceDetails);
        invoiceDetails.getSubItems().add(this);
        return this;
    }

    public SubItems removeInvoiceDetail(InvoiceDetails invoiceDetails) {
        this.invoiceDetails.remove(invoiceDetails);
        invoiceDetails.getSubItems().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubItems)) {
            return false;
        }
        return id != null && id.equals(((SubItems) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubItems{" +
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
