package com.polarbears.capstone.hmsfinance.service.criteria;

import com.polarbears.capstone.hmsfinance.domain.enumeration.ITEMTYPES;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmsfinance.domain.Items} entity. This class is used
 * in {@link com.polarbears.capstone.hmsfinance.web.rest.ItemsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItemsCriteria implements Serializable, Criteria {

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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter itemId;

    private StringFilter itemCode;

    private ITEMTYPESFilter type;

    private StringFilter explain;

    private DoubleFilter cost;

    private DoubleFilter price;

    private BooleanFilter isActive;

    private LocalDateFilter createdDate;

    private LongFilter templatesId;

    private Boolean distinct;

    public ItemsCriteria() {}

    public ItemsCriteria(ItemsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.itemId = other.itemId == null ? null : other.itemId.copy();
        this.itemCode = other.itemCode == null ? null : other.itemCode.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.explain = other.explain == null ? null : other.explain.copy();
        this.cost = other.cost == null ? null : other.cost.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.templatesId = other.templatesId == null ? null : other.templatesId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ItemsCriteria copy() {
        return new ItemsCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public ITEMTYPESFilter getType() {
        return type;
    }

    public ITEMTYPESFilter type() {
        if (type == null) {
            type = new ITEMTYPESFilter();
        }
        return type;
    }

    public void setType(ITEMTYPESFilter type) {
        this.type = type;
    }

    public StringFilter getExplain() {
        return explain;
    }

    public StringFilter explain() {
        if (explain == null) {
            explain = new StringFilter();
        }
        return explain;
    }

    public void setExplain(StringFilter explain) {
        this.explain = explain;
    }

    public DoubleFilter getCost() {
        return cost;
    }

    public DoubleFilter cost() {
        if (cost == null) {
            cost = new DoubleFilter();
        }
        return cost;
    }

    public void setCost(DoubleFilter cost) {
        this.cost = cost;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public DoubleFilter price() {
        if (price == null) {
            price = new DoubleFilter();
        }
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            isActive = new BooleanFilter();
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
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

    public LongFilter getTemplatesId() {
        return templatesId;
    }

    public LongFilter templatesId() {
        if (templatesId == null) {
            templatesId = new LongFilter();
        }
        return templatesId;
    }

    public void setTemplatesId(LongFilter templatesId) {
        this.templatesId = templatesId;
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
        final ItemsCriteria that = (ItemsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(itemId, that.itemId) &&
            Objects.equals(itemCode, that.itemCode) &&
            Objects.equals(type, that.type) &&
            Objects.equals(explain, that.explain) &&
            Objects.equals(cost, that.cost) &&
            Objects.equals(price, that.price) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(templatesId, that.templatesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, itemId, itemCode, type, explain, cost, price, isActive, createdDate, templatesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (itemId != null ? "itemId=" + itemId + ", " : "") +
            (itemCode != null ? "itemCode=" + itemCode + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (explain != null ? "explain=" + explain + ", " : "") +
            (cost != null ? "cost=" + cost + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (templatesId != null ? "templatesId=" + templatesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
