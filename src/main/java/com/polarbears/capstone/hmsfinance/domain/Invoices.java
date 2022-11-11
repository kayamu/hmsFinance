package com.polarbears.capstone.hmsfinance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polarbears.capstone.hmsfinance.domain.enumeration.INVOICETYPES;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Invoices.
 */
@Entity
@Table(name = "invoices")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Invoices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "contact_address_id")
    private Long contactAddressId;

    @Column(name = "contact_billing_adr_id")
    private Long contactBillingAdrId;

    @Column(name = "cart_id")
    private Long cartId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private INVOICETYPES type;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "last_tranaction_id")
    private Long lastTranactionId;

    @Column(name = "total_cost")
    private Double totalCost;

    @Column(name = "total_profit")
    private Double totalProfit;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "total_taxes")
    private Double totalTaxes;

    @Column(name = "fedaral_taxes_amount")
    private Double fedaralTaxesAmount;

    @Column(name = "provintional_taxes_amount")
    private Double provintionalTaxesAmount;

    @Column(name = "discounts_amount")
    private Double discountsAmount;

    @Column(name = "add_on_amount")
    private Double addOnAmount;

    @Size(max = 1024)
    @Column(name = "message", length = 1024)
    private String message;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(mappedBy = "invoices")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "invoices" }, allowSetters = true)
    private Set<Payments> payments = new HashSet<>();

    @OneToMany(mappedBy = "invoices")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "invoices" }, allowSetters = true)
    private Set<InvoiceTransactions> invoiceTransactions = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_invoices__invoice_details",
        joinColumns = @JoinColumn(name = "invoices_id"),
        inverseJoinColumns = @JoinColumn(name = "invoice_details_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subItems", "invoices" }, allowSetters = true)
    private Set<InvoiceDetails> invoiceDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Invoices id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public Invoices invoiceNumber(String invoiceNumber) {
        this.setInvoiceNumber(invoiceNumber);
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Long getContactId() {
        return this.contactId;
    }

    public Invoices contactId(Long contactId) {
        this.setContactId(contactId);
        return this;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getContactAddressId() {
        return this.contactAddressId;
    }

    public Invoices contactAddressId(Long contactAddressId) {
        this.setContactAddressId(contactAddressId);
        return this;
    }

    public void setContactAddressId(Long contactAddressId) {
        this.contactAddressId = contactAddressId;
    }

    public Long getContactBillingAdrId() {
        return this.contactBillingAdrId;
    }

    public Invoices contactBillingAdrId(Long contactBillingAdrId) {
        this.setContactBillingAdrId(contactBillingAdrId);
        return this;
    }

    public void setContactBillingAdrId(Long contactBillingAdrId) {
        this.contactBillingAdrId = contactBillingAdrId;
    }

    public Long getCartId() {
        return this.cartId;
    }

    public Invoices cartId(Long cartId) {
        this.setCartId(cartId);
        return this;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public INVOICETYPES getType() {
        return this.type;
    }

    public Invoices type(INVOICETYPES type) {
        this.setType(type);
        return this;
    }

    public void setType(INVOICETYPES type) {
        this.type = type;
    }

    public LocalDate getRequestDate() {
        return this.requestDate;
    }

    public Invoices requestDate(LocalDate requestDate) {
        this.setRequestDate(requestDate);
        return this;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getContactName() {
        return this.contactName;
    }

    public Invoices contactName(String contactName) {
        this.setContactName(contactName);
        return this;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public LocalDate getInvoiceDate() {
        return this.invoiceDate;
    }

    public Invoices invoiceDate(LocalDate invoiceDate) {
        this.setInvoiceDate(invoiceDate);
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Long getLastTranactionId() {
        return this.lastTranactionId;
    }

    public Invoices lastTranactionId(Long lastTranactionId) {
        this.setLastTranactionId(lastTranactionId);
        return this;
    }

    public void setLastTranactionId(Long lastTranactionId) {
        this.lastTranactionId = lastTranactionId;
    }

    public Double getTotalCost() {
        return this.totalCost;
    }

    public Invoices totalCost(Double totalCost) {
        this.setTotalCost(totalCost);
        return this;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getTotalProfit() {
        return this.totalProfit;
    }

    public Invoices totalProfit(Double totalProfit) {
        this.setTotalProfit(totalProfit);
        return this;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Double getTotalAmount() {
        return this.totalAmount;
    }

    public Invoices totalAmount(Double totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalTaxes() {
        return this.totalTaxes;
    }

    public Invoices totalTaxes(Double totalTaxes) {
        this.setTotalTaxes(totalTaxes);
        return this;
    }

    public void setTotalTaxes(Double totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public Double getFedaralTaxesAmount() {
        return this.fedaralTaxesAmount;
    }

    public Invoices fedaralTaxesAmount(Double fedaralTaxesAmount) {
        this.setFedaralTaxesAmount(fedaralTaxesAmount);
        return this;
    }

    public void setFedaralTaxesAmount(Double fedaralTaxesAmount) {
        this.fedaralTaxesAmount = fedaralTaxesAmount;
    }

    public Double getProvintionalTaxesAmount() {
        return this.provintionalTaxesAmount;
    }

    public Invoices provintionalTaxesAmount(Double provintionalTaxesAmount) {
        this.setProvintionalTaxesAmount(provintionalTaxesAmount);
        return this;
    }

    public void setProvintionalTaxesAmount(Double provintionalTaxesAmount) {
        this.provintionalTaxesAmount = provintionalTaxesAmount;
    }

    public Double getDiscountsAmount() {
        return this.discountsAmount;
    }

    public Invoices discountsAmount(Double discountsAmount) {
        this.setDiscountsAmount(discountsAmount);
        return this;
    }

    public void setDiscountsAmount(Double discountsAmount) {
        this.discountsAmount = discountsAmount;
    }

    public Double getAddOnAmount() {
        return this.addOnAmount;
    }

    public Invoices addOnAmount(Double addOnAmount) {
        this.setAddOnAmount(addOnAmount);
        return this;
    }

    public void setAddOnAmount(Double addOnAmount) {
        this.addOnAmount = addOnAmount;
    }

    public String getMessage() {
        return this.message;
    }

    public Invoices message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Invoices createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<Payments> getPayments() {
        return this.payments;
    }

    public void setPayments(Set<Payments> payments) {
        if (this.payments != null) {
            this.payments.forEach(i -> i.setInvoices(null));
        }
        if (payments != null) {
            payments.forEach(i -> i.setInvoices(this));
        }
        this.payments = payments;
    }

    public Invoices payments(Set<Payments> payments) {
        this.setPayments(payments);
        return this;
    }

    public Invoices addPayment(Payments payments) {
        this.payments.add(payments);
        payments.setInvoices(this);
        return this;
    }

    public Invoices removePayment(Payments payments) {
        this.payments.remove(payments);
        payments.setInvoices(null);
        return this;
    }

    public Set<InvoiceTransactions> getInvoiceTransactions() {
        return this.invoiceTransactions;
    }

    public void setInvoiceTransactions(Set<InvoiceTransactions> invoiceTransactions) {
        if (this.invoiceTransactions != null) {
            this.invoiceTransactions.forEach(i -> i.setInvoices(null));
        }
        if (invoiceTransactions != null) {
            invoiceTransactions.forEach(i -> i.setInvoices(this));
        }
        this.invoiceTransactions = invoiceTransactions;
    }

    public Invoices invoiceTransactions(Set<InvoiceTransactions> invoiceTransactions) {
        this.setInvoiceTransactions(invoiceTransactions);
        return this;
    }

    public Invoices addInvoiceTransactions(InvoiceTransactions invoiceTransactions) {
        this.invoiceTransactions.add(invoiceTransactions);
        invoiceTransactions.setInvoices(this);
        return this;
    }

    public Invoices removeInvoiceTransactions(InvoiceTransactions invoiceTransactions) {
        this.invoiceTransactions.remove(invoiceTransactions);
        invoiceTransactions.setInvoices(null);
        return this;
    }

    public Set<InvoiceDetails> getInvoiceDetails() {
        return this.invoiceDetails;
    }

    public void setInvoiceDetails(Set<InvoiceDetails> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public Invoices invoiceDetails(Set<InvoiceDetails> invoiceDetails) {
        this.setInvoiceDetails(invoiceDetails);
        return this;
    }

    public Invoices addInvoiceDetails(InvoiceDetails invoiceDetails) {
        this.invoiceDetails.add(invoiceDetails);
        invoiceDetails.getInvoices().add(this);
        return this;
    }

    public Invoices removeInvoiceDetails(InvoiceDetails invoiceDetails) {
        this.invoiceDetails.remove(invoiceDetails);
        invoiceDetails.getInvoices().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoices)) {
            return false;
        }
        return id != null && id.equals(((Invoices) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Invoices{" +
            "id=" + getId() +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", contactId=" + getContactId() +
            ", contactAddressId=" + getContactAddressId() +
            ", contactBillingAdrId=" + getContactBillingAdrId() +
            ", cartId=" + getCartId() +
            ", type='" + getType() + "'" +
            ", requestDate='" + getRequestDate() + "'" +
            ", contactName='" + getContactName() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", lastTranactionId=" + getLastTranactionId() +
            ", totalCost=" + getTotalCost() +
            ", totalProfit=" + getTotalProfit() +
            ", totalAmount=" + getTotalAmount() +
            ", totalTaxes=" + getTotalTaxes() +
            ", fedaralTaxesAmount=" + getFedaralTaxesAmount() +
            ", provintionalTaxesAmount=" + getProvintionalTaxesAmount() +
            ", discountsAmount=" + getDiscountsAmount() +
            ", addOnAmount=" + getAddOnAmount() +
            ", message='" + getMessage() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
