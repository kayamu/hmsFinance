package com.polarbears.capstone.hmsfinance.service.dto;

import com.polarbears.capstone.hmsfinance.domain.enumeration.ITEMTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.PAYMENTTYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.polarbears.capstone.hmsfinance.domain.InvoiceDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoiceDetailsDTO implements Serializable {

    private Long id;

    private Long contactId;

    private Long cartId;

    private Long itemId;

    private String itemName;

    private String itemCode;

    private ITEMTYPES itemType;

    private PAYMENTTYPES paymentType;

    private LocalDate subscriptionStartingDate;

    private Integer subscriptionDurationWeeks;

    private Double detailAmount;

    private Integer lineNumber;

    private Long nutritionistId;

    private Double totalCost;

    private Double totalProfit;

    private Double nutritionistEarning;

    private Double nutritionistPercentage;

    private Double fedaralTaxesAmount;

    private Double fedaralTaxesPercentage;

    private Double provintionalTaxesAmount;

    private Double provintionalTaxesPercentage;

    private Double totalTaxesAmount;

    private Double totalTaxesPercentage;

    private Double discountAmount;

    private Double discountPercentage;

    private String addOnCode;

    private Double addOnAmount;

    private Double addOnPercentage;

    private Double totalAmount;

    private LocalDate createdDate;

    private Set<SubItemsDTO> subItems = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public ITEMTYPES getItemType() {
        return itemType;
    }

    public void setItemType(ITEMTYPES itemType) {
        this.itemType = itemType;
    }

    public PAYMENTTYPES getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PAYMENTTYPES paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDate getSubscriptionStartingDate() {
        return subscriptionStartingDate;
    }

    public void setSubscriptionStartingDate(LocalDate subscriptionStartingDate) {
        this.subscriptionStartingDate = subscriptionStartingDate;
    }

    public Integer getSubscriptionDurationWeeks() {
        return subscriptionDurationWeeks;
    }

    public void setSubscriptionDurationWeeks(Integer subscriptionDurationWeeks) {
        this.subscriptionDurationWeeks = subscriptionDurationWeeks;
    }

    public Double getDetailAmount() {
        return detailAmount;
    }

    public void setDetailAmount(Double detailAmount) {
        this.detailAmount = detailAmount;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Long getNutritionistId() {
        return nutritionistId;
    }

    public void setNutritionistId(Long nutritionistId) {
        this.nutritionistId = nutritionistId;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Double getNutritionistEarning() {
        return nutritionistEarning;
    }

    public void setNutritionistEarning(Double nutritionistEarning) {
        this.nutritionistEarning = nutritionistEarning;
    }

    public Double getNutritionistPercentage() {
        return nutritionistPercentage;
    }

    public void setNutritionistPercentage(Double nutritionistPercentage) {
        this.nutritionistPercentage = nutritionistPercentage;
    }

    public Double getFedaralTaxesAmount() {
        return fedaralTaxesAmount;
    }

    public void setFedaralTaxesAmount(Double fedaralTaxesAmount) {
        this.fedaralTaxesAmount = fedaralTaxesAmount;
    }

    public Double getFedaralTaxesPercentage() {
        return fedaralTaxesPercentage;
    }

    public void setFedaralTaxesPercentage(Double fedaralTaxesPercentage) {
        this.fedaralTaxesPercentage = fedaralTaxesPercentage;
    }

    public Double getProvintionalTaxesAmount() {
        return provintionalTaxesAmount;
    }

    public void setProvintionalTaxesAmount(Double provintionalTaxesAmount) {
        this.provintionalTaxesAmount = provintionalTaxesAmount;
    }

    public Double getProvintionalTaxesPercentage() {
        return provintionalTaxesPercentage;
    }

    public void setProvintionalTaxesPercentage(Double provintionalTaxesPercentage) {
        this.provintionalTaxesPercentage = provintionalTaxesPercentage;
    }

    public Double getTotalTaxesAmount() {
        return totalTaxesAmount;
    }

    public void setTotalTaxesAmount(Double totalTaxesAmount) {
        this.totalTaxesAmount = totalTaxesAmount;
    }

    public Double getTotalTaxesPercentage() {
        return totalTaxesPercentage;
    }

    public void setTotalTaxesPercentage(Double totalTaxesPercentage) {
        this.totalTaxesPercentage = totalTaxesPercentage;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getAddOnCode() {
        return addOnCode;
    }

    public void setAddOnCode(String addOnCode) {
        this.addOnCode = addOnCode;
    }

    public Double getAddOnAmount() {
        return addOnAmount;
    }

    public void setAddOnAmount(Double addOnAmount) {
        this.addOnAmount = addOnAmount;
    }

    public Double getAddOnPercentage() {
        return addOnPercentage;
    }

    public void setAddOnPercentage(Double addOnPercentage) {
        this.addOnPercentage = addOnPercentage;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<SubItemsDTO> getSubItems() {
        return subItems;
    }

    public void setSubItems(Set<SubItemsDTO> subItems) {
        this.subItems = subItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceDetailsDTO)) {
            return false;
        }

        InvoiceDetailsDTO invoiceDetailsDTO = (InvoiceDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, invoiceDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceDetailsDTO{" +
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
            ", subItems=" + getSubItems() +
            "}";
    }
}
