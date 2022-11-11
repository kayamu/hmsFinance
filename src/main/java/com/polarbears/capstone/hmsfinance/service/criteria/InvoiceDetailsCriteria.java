package com.polarbears.capstone.hmsfinance.service.criteria;

import com.polarbears.capstone.hmsfinance.domain.enumeration.ITEMTYPES;
import com.polarbears.capstone.hmsfinance.domain.enumeration.PAYMENTTYPES;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmsfinance.domain.InvoiceDetails} entity. This class is used
 * in {@link com.polarbears.capstone.hmsfinance.web.rest.InvoiceDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoice-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoiceDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ITEMTYPES
     */
    public static class ITEMTYPESFilter extends Filter<ITEMTYPES> {

        public ITEMTYPESFilter() {}

        public ITEMTYPESFilter(ITEMTYPESFilter filter) {
            super(filter);
        }

        @Override
        public ITEMTYPESFilter copy() {
            return new ITEMTYPESFilter(this);
        }
    }

    /**
     * Class for filtering PAYMENTTYPES
     */
    public static class PAYMENTTYPESFilter extends Filter<PAYMENTTYPES> {

        public PAYMENTTYPESFilter() {}

        public PAYMENTTYPESFilter(PAYMENTTYPESFilter filter) {
            super(filter);
        }

        @Override
        public PAYMENTTYPESFilter copy() {
            return new PAYMENTTYPESFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter contactId;

    private LongFilter cartId;

    private LongFilter itemId;

    private StringFilter itemName;

    private StringFilter itemCode;

    private ITEMTYPESFilter itemType;

    private PAYMENTTYPESFilter paymentType;

    private LocalDateFilter subscriptionStartingDate;

    private IntegerFilter subscriptionDurationWeeks;

    private DoubleFilter detailAmount;

    private IntegerFilter lineNumber;

    private LongFilter nutritionistId;

    private DoubleFilter totalCost;

    private DoubleFilter totalProfit;

    private DoubleFilter nutritionistEarning;

    private DoubleFilter nutritionistPercentage;

    private DoubleFilter fedaralTaxesAmount;

    private DoubleFilter fedaralTaxesPercentage;

    private DoubleFilter provintionalTaxesAmount;

    private DoubleFilter provintionalTaxesPercentage;

    private DoubleFilter totalTaxesAmount;

    private DoubleFilter totalTaxesPercentage;

    private DoubleFilter discountAmount;

    private DoubleFilter discountPercentage;

    private StringFilter addOnCode;

    private DoubleFilter addOnAmount;

    private DoubleFilter addOnPercentage;

    private DoubleFilter totalAmount;

    private LocalDateFilter createdDate;

    private LongFilter subItemsId;

    private LongFilter invoicesId;

    private Boolean distinct;

    public InvoiceDetailsCriteria() {}

    public InvoiceDetailsCriteria(InvoiceDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.contactId = other.contactId == null ? null : other.contactId.copy();
        this.cartId = other.cartId == null ? null : other.cartId.copy();
        this.itemId = other.itemId == null ? null : other.itemId.copy();
        this.itemName = other.itemName == null ? null : other.itemName.copy();
        this.itemCode = other.itemCode == null ? null : other.itemCode.copy();
        this.itemType = other.itemType == null ? null : other.itemType.copy();
        this.paymentType = other.paymentType == null ? null : other.paymentType.copy();
        this.subscriptionStartingDate = other.subscriptionStartingDate == null ? null : other.subscriptionStartingDate.copy();
        this.subscriptionDurationWeeks = other.subscriptionDurationWeeks == null ? null : other.subscriptionDurationWeeks.copy();
        this.detailAmount = other.detailAmount == null ? null : other.detailAmount.copy();
        this.lineNumber = other.lineNumber == null ? null : other.lineNumber.copy();
        this.nutritionistId = other.nutritionistId == null ? null : other.nutritionistId.copy();
        this.totalCost = other.totalCost == null ? null : other.totalCost.copy();
        this.totalProfit = other.totalProfit == null ? null : other.totalProfit.copy();
        this.nutritionistEarning = other.nutritionistEarning == null ? null : other.nutritionistEarning.copy();
        this.nutritionistPercentage = other.nutritionistPercentage == null ? null : other.nutritionistPercentage.copy();
        this.fedaralTaxesAmount = other.fedaralTaxesAmount == null ? null : other.fedaralTaxesAmount.copy();
        this.fedaralTaxesPercentage = other.fedaralTaxesPercentage == null ? null : other.fedaralTaxesPercentage.copy();
        this.provintionalTaxesAmount = other.provintionalTaxesAmount == null ? null : other.provintionalTaxesAmount.copy();
        this.provintionalTaxesPercentage = other.provintionalTaxesPercentage == null ? null : other.provintionalTaxesPercentage.copy();
        this.totalTaxesAmount = other.totalTaxesAmount == null ? null : other.totalTaxesAmount.copy();
        this.totalTaxesPercentage = other.totalTaxesPercentage == null ? null : other.totalTaxesPercentage.copy();
        this.discountAmount = other.discountAmount == null ? null : other.discountAmount.copy();
        this.discountPercentage = other.discountPercentage == null ? null : other.discountPercentage.copy();
        this.addOnCode = other.addOnCode == null ? null : other.addOnCode.copy();
        this.addOnAmount = other.addOnAmount == null ? null : other.addOnAmount.copy();
        this.addOnPercentage = other.addOnPercentage == null ? null : other.addOnPercentage.copy();
        this.totalAmount = other.totalAmount == null ? null : other.totalAmount.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.subItemsId = other.subItemsId == null ? null : other.subItemsId.copy();
        this.invoicesId = other.invoicesId == null ? null : other.invoicesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InvoiceDetailsCriteria copy() {
        return new InvoiceDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getContactId() {
        return contactId;
    }

    public LongFilter contactId() {
        if (contactId == null) {
            contactId = new LongFilter();
        }
        return contactId;
    }

    public void setContactId(LongFilter contactId) {
        this.contactId = contactId;
    }

    public LongFilter getCartId() {
        return cartId;
    }

    public LongFilter cartId() {
        if (cartId == null) {
            cartId = new LongFilter();
        }
        return cartId;
    }

    public void setCartId(LongFilter cartId) {
        this.cartId = cartId;
    }

    public LongFilter getItemId() {
        return itemId;
    }

    public LongFilter itemId() {
        if (itemId == null) {
            itemId = new LongFilter();
        }
        return itemId;
    }

    public void setItemId(LongFilter itemId) {
        this.itemId = itemId;
    }

    public StringFilter getItemName() {
        return itemName;
    }

    public StringFilter itemName() {
        if (itemName == null) {
            itemName = new StringFilter();
        }
        return itemName;
    }

    public void setItemName(StringFilter itemName) {
        this.itemName = itemName;
    }

    public StringFilter getItemCode() {
        return itemCode;
    }

    public StringFilter itemCode() {
        if (itemCode == null) {
            itemCode = new StringFilter();
        }
        return itemCode;
    }

    public void setItemCode(StringFilter itemCode) {
        this.itemCode = itemCode;
    }

    public ITEMTYPESFilter getItemType() {
        return itemType;
    }

    public ITEMTYPESFilter itemType() {
        if (itemType == null) {
            itemType = new ITEMTYPESFilter();
        }
        return itemType;
    }

    public void setItemType(ITEMTYPESFilter itemType) {
        this.itemType = itemType;
    }

    public PAYMENTTYPESFilter getPaymentType() {
        return paymentType;
    }

    public PAYMENTTYPESFilter paymentType() {
        if (paymentType == null) {
            paymentType = new PAYMENTTYPESFilter();
        }
        return paymentType;
    }

    public void setPaymentType(PAYMENTTYPESFilter paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDateFilter getSubscriptionStartingDate() {
        return subscriptionStartingDate;
    }

    public LocalDateFilter subscriptionStartingDate() {
        if (subscriptionStartingDate == null) {
            subscriptionStartingDate = new LocalDateFilter();
        }
        return subscriptionStartingDate;
    }

    public void setSubscriptionStartingDate(LocalDateFilter subscriptionStartingDate) {
        this.subscriptionStartingDate = subscriptionStartingDate;
    }

    public IntegerFilter getSubscriptionDurationWeeks() {
        return subscriptionDurationWeeks;
    }

    public IntegerFilter subscriptionDurationWeeks() {
        if (subscriptionDurationWeeks == null) {
            subscriptionDurationWeeks = new IntegerFilter();
        }
        return subscriptionDurationWeeks;
    }

    public void setSubscriptionDurationWeeks(IntegerFilter subscriptionDurationWeeks) {
        this.subscriptionDurationWeeks = subscriptionDurationWeeks;
    }

    public DoubleFilter getDetailAmount() {
        return detailAmount;
    }

    public DoubleFilter detailAmount() {
        if (detailAmount == null) {
            detailAmount = new DoubleFilter();
        }
        return detailAmount;
    }

    public void setDetailAmount(DoubleFilter detailAmount) {
        this.detailAmount = detailAmount;
    }

    public IntegerFilter getLineNumber() {
        return lineNumber;
    }

    public IntegerFilter lineNumber() {
        if (lineNumber == null) {
            lineNumber = new IntegerFilter();
        }
        return lineNumber;
    }

    public void setLineNumber(IntegerFilter lineNumber) {
        this.lineNumber = lineNumber;
    }

    public LongFilter getNutritionistId() {
        return nutritionistId;
    }

    public LongFilter nutritionistId() {
        if (nutritionistId == null) {
            nutritionistId = new LongFilter();
        }
        return nutritionistId;
    }

    public void setNutritionistId(LongFilter nutritionistId) {
        this.nutritionistId = nutritionistId;
    }

    public DoubleFilter getTotalCost() {
        return totalCost;
    }

    public DoubleFilter totalCost() {
        if (totalCost == null) {
            totalCost = new DoubleFilter();
        }
        return totalCost;
    }

    public void setTotalCost(DoubleFilter totalCost) {
        this.totalCost = totalCost;
    }

    public DoubleFilter getTotalProfit() {
        return totalProfit;
    }

    public DoubleFilter totalProfit() {
        if (totalProfit == null) {
            totalProfit = new DoubleFilter();
        }
        return totalProfit;
    }

    public void setTotalProfit(DoubleFilter totalProfit) {
        this.totalProfit = totalProfit;
    }

    public DoubleFilter getNutritionistEarning() {
        return nutritionistEarning;
    }

    public DoubleFilter nutritionistEarning() {
        if (nutritionistEarning == null) {
            nutritionistEarning = new DoubleFilter();
        }
        return nutritionistEarning;
    }

    public void setNutritionistEarning(DoubleFilter nutritionistEarning) {
        this.nutritionistEarning = nutritionistEarning;
    }

    public DoubleFilter getNutritionistPercentage() {
        return nutritionistPercentage;
    }

    public DoubleFilter nutritionistPercentage() {
        if (nutritionistPercentage == null) {
            nutritionistPercentage = new DoubleFilter();
        }
        return nutritionistPercentage;
    }

    public void setNutritionistPercentage(DoubleFilter nutritionistPercentage) {
        this.nutritionistPercentage = nutritionistPercentage;
    }

    public DoubleFilter getFedaralTaxesAmount() {
        return fedaralTaxesAmount;
    }

    public DoubleFilter fedaralTaxesAmount() {
        if (fedaralTaxesAmount == null) {
            fedaralTaxesAmount = new DoubleFilter();
        }
        return fedaralTaxesAmount;
    }

    public void setFedaralTaxesAmount(DoubleFilter fedaralTaxesAmount) {
        this.fedaralTaxesAmount = fedaralTaxesAmount;
    }

    public DoubleFilter getFedaralTaxesPercentage() {
        return fedaralTaxesPercentage;
    }

    public DoubleFilter fedaralTaxesPercentage() {
        if (fedaralTaxesPercentage == null) {
            fedaralTaxesPercentage = new DoubleFilter();
        }
        return fedaralTaxesPercentage;
    }

    public void setFedaralTaxesPercentage(DoubleFilter fedaralTaxesPercentage) {
        this.fedaralTaxesPercentage = fedaralTaxesPercentage;
    }

    public DoubleFilter getProvintionalTaxesAmount() {
        return provintionalTaxesAmount;
    }

    public DoubleFilter provintionalTaxesAmount() {
        if (provintionalTaxesAmount == null) {
            provintionalTaxesAmount = new DoubleFilter();
        }
        return provintionalTaxesAmount;
    }

    public void setProvintionalTaxesAmount(DoubleFilter provintionalTaxesAmount) {
        this.provintionalTaxesAmount = provintionalTaxesAmount;
    }

    public DoubleFilter getProvintionalTaxesPercentage() {
        return provintionalTaxesPercentage;
    }

    public DoubleFilter provintionalTaxesPercentage() {
        if (provintionalTaxesPercentage == null) {
            provintionalTaxesPercentage = new DoubleFilter();
        }
        return provintionalTaxesPercentage;
    }

    public void setProvintionalTaxesPercentage(DoubleFilter provintionalTaxesPercentage) {
        this.provintionalTaxesPercentage = provintionalTaxesPercentage;
    }

    public DoubleFilter getTotalTaxesAmount() {
        return totalTaxesAmount;
    }

    public DoubleFilter totalTaxesAmount() {
        if (totalTaxesAmount == null) {
            totalTaxesAmount = new DoubleFilter();
        }
        return totalTaxesAmount;
    }

    public void setTotalTaxesAmount(DoubleFilter totalTaxesAmount) {
        this.totalTaxesAmount = totalTaxesAmount;
    }

    public DoubleFilter getTotalTaxesPercentage() {
        return totalTaxesPercentage;
    }

    public DoubleFilter totalTaxesPercentage() {
        if (totalTaxesPercentage == null) {
            totalTaxesPercentage = new DoubleFilter();
        }
        return totalTaxesPercentage;
    }

    public void setTotalTaxesPercentage(DoubleFilter totalTaxesPercentage) {
        this.totalTaxesPercentage = totalTaxesPercentage;
    }

    public DoubleFilter getDiscountAmount() {
        return discountAmount;
    }

    public DoubleFilter discountAmount() {
        if (discountAmount == null) {
            discountAmount = new DoubleFilter();
        }
        return discountAmount;
    }

    public void setDiscountAmount(DoubleFilter discountAmount) {
        this.discountAmount = discountAmount;
    }

    public DoubleFilter getDiscountPercentage() {
        return discountPercentage;
    }

    public DoubleFilter discountPercentage() {
        if (discountPercentage == null) {
            discountPercentage = new DoubleFilter();
        }
        return discountPercentage;
    }

    public void setDiscountPercentage(DoubleFilter discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public StringFilter getAddOnCode() {
        return addOnCode;
    }

    public StringFilter addOnCode() {
        if (addOnCode == null) {
            addOnCode = new StringFilter();
        }
        return addOnCode;
    }

    public void setAddOnCode(StringFilter addOnCode) {
        this.addOnCode = addOnCode;
    }

    public DoubleFilter getAddOnAmount() {
        return addOnAmount;
    }

    public DoubleFilter addOnAmount() {
        if (addOnAmount == null) {
            addOnAmount = new DoubleFilter();
        }
        return addOnAmount;
    }

    public void setAddOnAmount(DoubleFilter addOnAmount) {
        this.addOnAmount = addOnAmount;
    }

    public DoubleFilter getAddOnPercentage() {
        return addOnPercentage;
    }

    public DoubleFilter addOnPercentage() {
        if (addOnPercentage == null) {
            addOnPercentage = new DoubleFilter();
        }
        return addOnPercentage;
    }

    public void setAddOnPercentage(DoubleFilter addOnPercentage) {
        this.addOnPercentage = addOnPercentage;
    }

    public DoubleFilter getTotalAmount() {
        return totalAmount;
    }

    public DoubleFilter totalAmount() {
        if (totalAmount == null) {
            totalAmount = new DoubleFilter();
        }
        return totalAmount;
    }

    public void setTotalAmount(DoubleFilter totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public LocalDateFilter createdDate() {
        if (createdDate == null) {
            createdDate = new LocalDateFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LongFilter getSubItemsId() {
        return subItemsId;
    }

    public LongFilter subItemsId() {
        if (subItemsId == null) {
            subItemsId = new LongFilter();
        }
        return subItemsId;
    }

    public void setSubItemsId(LongFilter subItemsId) {
        this.subItemsId = subItemsId;
    }

    public LongFilter getInvoicesId() {
        return invoicesId;
    }

    public LongFilter invoicesId() {
        if (invoicesId == null) {
            invoicesId = new LongFilter();
        }
        return invoicesId;
    }

    public void setInvoicesId(LongFilter invoicesId) {
        this.invoicesId = invoicesId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InvoiceDetailsCriteria that = (InvoiceDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(contactId, that.contactId) &&
            Objects.equals(cartId, that.cartId) &&
            Objects.equals(itemId, that.itemId) &&
            Objects.equals(itemName, that.itemName) &&
            Objects.equals(itemCode, that.itemCode) &&
            Objects.equals(itemType, that.itemType) &&
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(subscriptionStartingDate, that.subscriptionStartingDate) &&
            Objects.equals(subscriptionDurationWeeks, that.subscriptionDurationWeeks) &&
            Objects.equals(detailAmount, that.detailAmount) &&
            Objects.equals(lineNumber, that.lineNumber) &&
            Objects.equals(nutritionistId, that.nutritionistId) &&
            Objects.equals(totalCost, that.totalCost) &&
            Objects.equals(totalProfit, that.totalProfit) &&
            Objects.equals(nutritionistEarning, that.nutritionistEarning) &&
            Objects.equals(nutritionistPercentage, that.nutritionistPercentage) &&
            Objects.equals(fedaralTaxesAmount, that.fedaralTaxesAmount) &&
            Objects.equals(fedaralTaxesPercentage, that.fedaralTaxesPercentage) &&
            Objects.equals(provintionalTaxesAmount, that.provintionalTaxesAmount) &&
            Objects.equals(provintionalTaxesPercentage, that.provintionalTaxesPercentage) &&
            Objects.equals(totalTaxesAmount, that.totalTaxesAmount) &&
            Objects.equals(totalTaxesPercentage, that.totalTaxesPercentage) &&
            Objects.equals(discountAmount, that.discountAmount) &&
            Objects.equals(discountPercentage, that.discountPercentage) &&
            Objects.equals(addOnCode, that.addOnCode) &&
            Objects.equals(addOnAmount, that.addOnAmount) &&
            Objects.equals(addOnPercentage, that.addOnPercentage) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(subItemsId, that.subItemsId) &&
            Objects.equals(invoicesId, that.invoicesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            contactId,
            cartId,
            itemId,
            itemName,
            itemCode,
            itemType,
            paymentType,
            subscriptionStartingDate,
            subscriptionDurationWeeks,
            detailAmount,
            lineNumber,
            nutritionistId,
            totalCost,
            totalProfit,
            nutritionistEarning,
            nutritionistPercentage,
            fedaralTaxesAmount,
            fedaralTaxesPercentage,
            provintionalTaxesAmount,
            provintionalTaxesPercentage,
            totalTaxesAmount,
            totalTaxesPercentage,
            discountAmount,
            discountPercentage,
            addOnCode,
            addOnAmount,
            addOnPercentage,
            totalAmount,
            createdDate,
            subItemsId,
            invoicesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (contactId != null ? "contactId=" + contactId + ", " : "") +
            (cartId != null ? "cartId=" + cartId + ", " : "") +
            (itemId != null ? "itemId=" + itemId + ", " : "") +
            (itemName != null ? "itemName=" + itemName + ", " : "") +
            (itemCode != null ? "itemCode=" + itemCode + ", " : "") +
            (itemType != null ? "itemType=" + itemType + ", " : "") +
            (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
            (subscriptionStartingDate != null ? "subscriptionStartingDate=" + subscriptionStartingDate + ", " : "") +
            (subscriptionDurationWeeks != null ? "subscriptionDurationWeeks=" + subscriptionDurationWeeks + ", " : "") +
            (detailAmount != null ? "detailAmount=" + detailAmount + ", " : "") +
            (lineNumber != null ? "lineNumber=" + lineNumber + ", " : "") +
            (nutritionistId != null ? "nutritionistId=" + nutritionistId + ", " : "") +
            (totalCost != null ? "totalCost=" + totalCost + ", " : "") +
            (totalProfit != null ? "totalProfit=" + totalProfit + ", " : "") +
            (nutritionistEarning != null ? "nutritionistEarning=" + nutritionistEarning + ", " : "") +
            (nutritionistPercentage != null ? "nutritionistPercentage=" + nutritionistPercentage + ", " : "") +
            (fedaralTaxesAmount != null ? "fedaralTaxesAmount=" + fedaralTaxesAmount + ", " : "") +
            (fedaralTaxesPercentage != null ? "fedaralTaxesPercentage=" + fedaralTaxesPercentage + ", " : "") +
            (provintionalTaxesAmount != null ? "provintionalTaxesAmount=" + provintionalTaxesAmount + ", " : "") +
            (provintionalTaxesPercentage != null ? "provintionalTaxesPercentage=" + provintionalTaxesPercentage + ", " : "") +
            (totalTaxesAmount != null ? "totalTaxesAmount=" + totalTaxesAmount + ", " : "") +
            (totalTaxesPercentage != null ? "totalTaxesPercentage=" + totalTaxesPercentage + ", " : "") +
            (discountAmount != null ? "discountAmount=" + discountAmount + ", " : "") +
            (discountPercentage != null ? "discountPercentage=" + discountPercentage + ", " : "") +
            (addOnCode != null ? "addOnCode=" + addOnCode + ", " : "") +
            (addOnAmount != null ? "addOnAmount=" + addOnAmount + ", " : "") +
            (addOnPercentage != null ? "addOnPercentage=" + addOnPercentage + ", " : "") +
            (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (subItemsId != null ? "subItemsId=" + subItemsId + ", " : "") +
            (invoicesId != null ? "invoicesId=" + invoicesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
