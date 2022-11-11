package com.polarbears.capstone.hmsfinance.service.criteria;

import com.polarbears.capstone.hmsfinance.domain.enumeration.ITEMTYPES;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.polarbears.capstone.hmsfinance.domain.Templates} entity. This class is used
 * in {@link com.polarbears.capstone.hmsfinance.web.rest.TemplatesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /templates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TemplatesCriteria implements Serializable, Criteria {

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

    private ITEMTYPESFilter type;

    private StringFilter explanation;

    private BooleanFilter isActive;

    private LocalDateFilter createdDate;

    private LongFilter itemsId;

    private LongFilter templateItemsId;

    private Boolean distinct;

    public TemplatesCriteria() {}

    public TemplatesCriteria(TemplatesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.explanation = other.explanation == null ? null : other.explanation.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.itemsId = other.itemsId == null ? null : other.itemsId.copy();
        this.templateItemsId = other.templateItemsId == null ? null : other.templateItemsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TemplatesCriteria copy() {
        return new TemplatesCriteria(this);
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

    public StringFilter getExplanation() {
        return explanation;
    }

    public StringFilter explanation() {
        if (explanation == null) {
            explanation = new StringFilter();
        }
        return explanation;
    }

    public void setExplanation(StringFilter explanation) {
        this.explanation = explanation;
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

    public LongFilter getItemsId() {
        return itemsId;
    }

    public LongFilter itemsId() {
        if (itemsId == null) {
            itemsId = new LongFilter();
        }
        return itemsId;
    }

    public void setItemsId(LongFilter itemsId) {
        this.itemsId = itemsId;
    }

    public LongFilter getTemplateItemsId() {
        return templateItemsId;
    }

    public LongFilter templateItemsId() {
        if (templateItemsId == null) {
            templateItemsId = new LongFilter();
        }
        return templateItemsId;
    }

    public void setTemplateItemsId(LongFilter templateItemsId) {
        this.templateItemsId = templateItemsId;
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
        final TemplatesCriteria that = (TemplatesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(explanation, that.explanation) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(itemsId, that.itemsId) &&
            Objects.equals(templateItemsId, that.templateItemsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, explanation, isActive, createdDate, itemsId, templateItemsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplatesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (explanation != null ? "explanation=" + explanation + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (itemsId != null ? "itemsId=" + itemsId + ", " : "") +
            (templateItemsId != null ? "templateItemsId=" + templateItemsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
