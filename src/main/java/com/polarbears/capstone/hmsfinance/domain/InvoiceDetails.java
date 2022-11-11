package com.polarbears.capstone.hmsfinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmsfinance.domain.enumeration.ITEMTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.PAYMENTTYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InvoiceDetails.
 */
@Entity
@Table(name = "invoice_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoiceDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_code")
    private String itemCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ITEMTYPES itemType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PAYMENTTYPES paymentType;

    @Column(name = "subscription_starting_date")
    private LocalDate subscriptionStartingDate;

    @Column(name = "subscription_duration_weeks")
    private Integer subscriptionDurationWeeks;

    @Column(name = "detail_amount")
    private Double detailAmount;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "nutritionist_id")
    private Long nutritionistId;

    @Column(name = "total_cost")
    private Double totalCost;

    @Column(name = "total_profit")
    private Double totalProfit;

    @Column(name = "nutritionist_earning")
    private Double nutritionistEarning;

    @Column(name = "nutritionist_percentage")
    private Double nutritionistPercentage;

    @Column(name = "fedaral_taxes_amount")
    private Double fedaralTaxesAmount;

    @Column(name = "fedaral_taxes_percentage")
    private Double fedaralTaxesPercentage;

    @Column(name = "provintional_taxes_amount")
    private Double provintionalTaxesAmount;

    @Column(name = "provintional_taxes_percentage")
    private Double provintionalTaxesPercentage;

    @Column(name = "total_taxes_amount")
    private Double totalTaxesAmount;

    @Column(name = "total_taxes_percentage")
    private Double totalTaxesPercentage;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @Column(name = "add_on_code")
    private String addOnCode;

    @Column(name = "add_on_amount")
    private Double addOnAmount;

    @Column(name = "add_on_percentage")
    private Double addOnPercentage;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @ManyToMany
    @JoinTable(
        name = "rel_invoice_details__sub_items",
        joinColumns = @JoinColumn(name = "invoice_details_id"),
        inverseJoinColumns = @JoinColumn(name = "sub_items_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "invoiceDetails" }, allowSetters = true)
    private Set<SubItems> subItems = new HashSet<>();

    @ManyToMany(mappedBy = "invoiceDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "payments", "invoiceTransactions", "invoiceDetails" }, allowSetters = true)
    private Set<Invoices> invoices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InvoiceDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return this.contactId;
    }

    public InvoiceDetails contactId(Long contactId) {
        this.setContactId(contactId);
        return this;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getCartId() {
        return this.cartId;
    }

    public InvoiceDetails cartId(Long cartId) {
        this.setCartId(cartId);
        return this;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public InvoiceDetails itemId(Long itemId) {
        this.setItemId(itemId);
        return this;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public InvoiceDetails itemName(String itemName) {
        this.setItemName(itemName);
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public InvoiceDetails itemCode(String itemCode) {
        this.setItemCode(itemCode);
        return this;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public ITEMTYPES getItemType() {
        return this.itemType;
    }

    public InvoiceDetails itemType(ITEMTYPES itemType) {
        this.setItemType(itemType);
        return this;
    }

    public void setItemType(ITEMTYPES itemType) {
        this.itemType = itemType;
    }

    public PAYMENTTYPES getPaymentType() {
        return this.paymentType;
    }

    public InvoiceDetails paymentType(PAYMENTTYPES paymentType) {
        this.setPaymentType(paymentType);
        return this;
    }

    public void setPaymentType(PAYMENTTYPES paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDate getSubscriptionStartingDate() {
        return this.subscriptionStartingDate;
    }

    public InvoiceDetails subscriptionStartingDate(LocalDate subscriptionStartingDate) {
        this.setSubscriptionStartingDate(subscriptionStartingDate);
        return this;
    }

    public void setSubscriptionStartingDate(LocalDate subscriptionStartingDate) {
        this.subscriptionStartingDate = subscriptionStartingDate;
    }

    public Integer getSubscriptionDurationWeeks() {
        return this.subscriptionDurationWeeks;
    }

    public InvoiceDetails subscriptionDurationWeeks(Integer subscriptionDurationWeeks) {
        this.setSubscriptionDurationWeeks(subscriptionDurationWeeks);
        return this;
    }

    public void setSubscriptionDurationWeeks(Integer subscriptionDurationWeeks) {
        this.subscriptionDurationWeeks = subscriptionDurationWeeks;
    }

    public Double getDetailAmount() {
        return this.detailAmount;
    }

    public InvoiceDetails detailAmount(Double detailAmount) {
        this.setDetailAmount(detailAmount);
        return this;
    }

    public void setDetailAmount(Double detailAmount) {
        this.detailAmount = detailAmount;
    }

    public Integer getLineNumber() {
        return this.lineNumber;
    }

    public InvoiceDetails lineNumber(Integer lineNumber) {
        this.setLineNumber(lineNumber);
        return this;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Long getNutritionistId() {
        return this.nutritionistId;
    }

    public InvoiceDetails nutritionistId(Long nutritionistId) {
        this.setNutritionistId(nutritionistId);
        return this;
    }

    public void setNutritionistId(Long nutritionistId) {
        this.nutritionistId = nutritionistId;
    }

    public Double getTotalCost() {
        return this.totalCost;
    }

    public InvoiceDetails totalCost(Double totalCost) {
        this.setTotalCost(totalCost);
        return this;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getTotalProfit() {
        return this.totalProfit;
    }

    public InvoiceDetails totalProfit(Double totalProfit) {
        this.setTotalProfit(totalProfit);
        return this;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Double getNutritionistEarning() {
        return this.nutritionistEarning;
    }

    public InvoiceDetails nutritionistEarning(Double nutritionistEarning) {
        this.setNutritionistEarning(nutritionistEarning);
        return this;
    }

    public void setNutritionistEarning(Double nutritionistEarning) {
        this.nutritionistEarning = nutritionistEarning;
    }

    public Double getNutritionistPercentage() {
        return this.nutritionistPercentage;
    }

    public InvoiceDetails nutritionistPercentage(Double nutritionistPercentage) {
        this.setNutritionistPercentage(nutritionistPercentage);
        return this;
    }

    public void setNutritionistPercentage(Double nutritionistPercentage) {
        this.nutritionistPercentage = nutritionistPercentage;
    }

    public Double getFedaralTaxesAmount() {
        return this.fedaralTaxesAmount;
    }

    public InvoiceDetails fedaralTaxesAmount(Double fedaralTaxesAmount) {
        this.setFedaralTaxesAmount(fedaralTaxesAmount);
        return this;
    }

    public void setFedaralTaxesAmount(Double fedaralTaxesAmount) {
        this.fedaralTaxesAmount = fedaralTaxesAmount;
    }

    public Double getFedaralTaxesPercentage() {
        return this.fedaralTaxesPercentage;
    }

    public InvoiceDetails fedaralTaxesPercentage(Double fedaralTaxesPercentage) {
        this.setFedaralTaxesPercentage(fedaralTaxesPercentage);
        return this;
    }

    public void setFedaralTaxesPercentage(Double fedaralTaxesPercentage) {
        this.fedaralTaxesPercentage = fedaralTaxesPercentage;
    }

    public Double getProvintionalTaxesAmount() {
        return this.provintionalTaxesAmount;
    }

    public InvoiceDetails provintionalTaxesAmount(Double provintionalTaxesAmount) {
        this.setProvintionalTaxesAmount(provintionalTaxesAmount);
        return this;
    }

    public void setProvintionalTaxesAmount(Double provintionalTaxesAmount) {
        this.provintionalTaxesAmount = provintionalTaxesAmount;
    }

    public Double getProvintionalTaxesPercentage() {
        return this.provintionalTaxesPercentage;
    }

    public InvoiceDetails provintionalTaxesPercentage(Double provintionalTaxesPercentage) {
        this.setProvintionalTaxesPercentage(provintionalTaxesPercentage);
        return this;
    }

    public void setProvintionalTaxesPercentage(Double provintionalTaxesPercentage) {
        this.provintionalTaxesPercentage = provintionalTaxesPercentage;
    }

    public Double getTotalTaxesAmount() {
        return this.totalTaxesAmount;
    }

    public InvoiceDetails totalTaxesAmount(Double totalTaxesAmount) {
        this.setTotalTaxesAmount(totalTaxesAmount);
        return this;
    }

    public void setTotalTaxesAmount(Double totalTaxesAmount) {
        this.totalTaxesAmount = totalTaxesAmount;
    }

    public Double getTotalTaxesPercentage() {
        return this.totalTaxesPercentage;
    }

    public InvoiceDetails totalTaxesPercentage(Double totalTaxesPercentage) {
        this.setTotalTaxesPercentage(totalTaxesPercentage);
        return this;
    }

    public void setTotalTaxesPercentage(Double totalTaxesPercentage) {
        this.totalTaxesPercentage = totalTaxesPercentage;
    }

    public Double getDiscountAmount() {
        return this.discountAmount;
    }

    public InvoiceDetails discountAmount(Double discountAmount) {
        this.setDiscountAmount(discountAmount);
        return this;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getDiscountPercentage() {
        return this.discountPercentage;
    }

    public InvoiceDetails discountPercentage(Double discountPercentage) {
        this.setDiscountPercentage(discountPercentage);
        return this;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getAddOnCode() {
        return this.addOnCode;
    }

    public InvoiceDetails addOnCode(String addOnCode) {
        this.setAddOnCode(addOnCode);
        return this;
    }

    public void setAddOnCode(String addOnCode) {
        this.addOnCode = addOnCode;
    }

    public Double getAddOnAmount() {
        return this.addOnAmount;
    }

    public InvoiceDetails addOnAmount(Double addOnAmount) {
        this.setAddOnAmount(addOnAmount);
        return this;
    }

    public void setAddOnAmount(Double addOnAmount) {
        this.addOnAmount = addOnAmount;
    }

    public Double getAddOnPercentage() {
        return this.addOnPercentage;
    }

    public InvoiceDetails addOnPercentage(Double addOnPercentage) {
        this.setAddOnPercentage(addOnPercentage);
        return this;
    }

    public void setAddOnPercentage(Double addOnPercentage) {
        this.addOnPercentage = addOnPercentage;
    }

    public Double getTotalAmount() {
        return this.totalAmount;
    }

    public InvoiceDetails totalAmount(Double totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public InvoiceDetails createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<SubItems> getSubItems() {
        return this.subItems;
    }

    public void setSubItems(Set<SubItems> subItems) {
        this.subItems = subItems;
    }

    public InvoiceDetails subItems(Set<SubItems> subItems) {
        this.setSubItems(subItems);
        return this;
    }

    public InvoiceDetails addSubItems(SubItems subItems) {
        this.subItems.add(subItems);
        subItems.getInvoiceDetails().add(this);
        return this;
    }

    public InvoiceDetails removeSubItems(SubItems subItems) {
        this.subItems.remove(subItems);
        subItems.getInvoiceDetails().remove(this);
        return this;
    }

    public Set<Invoices> getInvoices() {
        return this.invoices;
    }

    public void setInvoices(Set<Invoices> invoices) {
        if (this.invoices != null) {
            this.invoices.forEach(i -> i.removeInvoiceDetails(this));
        }
        if (invoices != null) {
            invoices.forEach(i -> i.addInvoiceDetails(this));
        }
        this.invoices = invoices;
    }

    public InvoiceDetails invoices(Set<Invoices> invoices) {
        this.setInvoices(invoices);
        return this;
    }

    public InvoiceDetails addInvoices(Invoices invoices) {
        this.invoices.add(invoices);
        invoices.getInvoiceDetails().add(this);
        return this;
    }

    public InvoiceDetails removeInvoices(Invoices invoices) {
        this.invoices.remove(invoices);
        invoices.getInvoiceDetails().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceDetails)) {
            return false;
        }
        return id != null && id.equals(((InvoiceDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceDetails{" +
            "id=" + getId() +
            ", contactId=" + getContactId() +
            ", cartId=" + getCartId() +
            ", itemId=" + getItemId() +
            ", itemName='" + getItemName() + "'" +
            ", itemCode='" + getItemCode() + "'" +
            ", itemType='" + getItemType() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", subscriptionStartingDate='" + getSubscriptionStartingDate() + "'" +
            ", subscriptionDurationWeeks=" + getSubscriptionDurationWeeks() +
            ", detailAmount=" + getDetailAmount() +
            ", lineNumber=" + getLineNumber() +
            ", nutritionistId=" + getNutritionistId() +
            ", totalCost=" + getTotalCost() +
            ", totalProfit=" + getTotalProfit() +
            ", nutritionistEarning=" + getNutritionistEarning() +
            ", nutritionistPercentage=" + getNutritionistPercentage() +
            ", fedaralTaxesAmount=" + getFedaralTaxesAmount() +
            ", fedaralTaxesPercentage=" + getFedaralTaxesPercentage() +
            ", provintionalTaxesAmount=" + getProvintionalTaxesAmount() +
            ", provintionalTaxesPercentage=" + getProvintionalTaxesPercentage() +
            ", totalTaxesAmount=" + getTotalTaxesAmount() +
            ", totalTaxesPercentage=" + getTotalTaxesPercentage() +
            ", discountAmount=" + getDiscountAmount() +
            ", discountPercentage=" + getDiscountPercentage() +
            ", addOnCode='" + getAddOnCode() + "'" +
            ", addOnAmount=" + getAddOnAmount() +
            ", addOnPercentage=" + getAddOnPercentage() +
            ", totalAmount=" + getTotalAmount() +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
