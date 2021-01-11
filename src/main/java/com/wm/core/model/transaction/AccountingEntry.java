package com.wm.core.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_accounting_entries")
public class AccountingEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_accounting_entries_seq")
    @SequenceGenerator(name = "wm_accounting_entries_seq", sequenceName = "wm_accounting_entries_seq", initialValue = 1, allocationSize = 1)
    private long id;

    @NotBlank(message = "description is mandatory for accounting entry")
    private String description;

    @NotNull(message = "indexNo is required")
    @JsonProperty(value = "index_no")
    private long indexNo;

    @NotNull(message = "subLedgerId is required")
    @JsonProperty(value = "subLedger_Id")
    private long subLedgerId;

    @NotBlank(message = "type is mandatory for accounting entry")
    private String type;

    @NotNull(message = "userId is required")
    @JsonProperty(value = "created_by")
    private long createdBy;

    @JsonProperty(value = "date_created")
    private Date dateCreated;

    @JsonProperty(value = "last_updated")
    private Date lastUpdated;

    @NotBlank(message = "transaction party id is mandatory for accounting entry")
    private String transactionPartyId;

    @NotNull(message = "transactionTypeId is required")
    @JsonProperty(value = "transaction_type_id")
    private long transactionTypeId;

    @NotNull(message = "transactionParameterId is required")
    @JsonProperty(value = "transaction_parameter_id")
    private long transactionParameterId;

    public AccountingEntry() {
    }

    public AccountingEntry(@NotBlank(message = "description is mandatory for accounting entry") String description,
                           long indexNo, long subLedgerId,
                           @NotBlank(message = "type is mandatory for accounting entry") String type,
                           @NotNull(message = "userId is required") long createdBy, Date dateCreated, Date lastUpdated,
                           @NotBlank(message = "transaction party id is mandatory for accounting entry") String transactionPartyId,
                           @NotNull(message = "transactionTypeId is required") long transactionTypeId,
                           @NotNull(message = "transactionParameterId is required") long transactionParameterId) {
        this.description = description;
        this.indexNo = indexNo;
        this.subLedgerId = subLedgerId;
        this.type = type;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.transactionPartyId = transactionPartyId;
        this.transactionTypeId = transactionTypeId;
        this.transactionParameterId = transactionParameterId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(long indexNo) {
        this.indexNo = indexNo;
    }

    public long getSubLedgerId() {
        return subLedgerId;
    }

    public void setSubLedgerId(long subLedgerId) {
        this.subLedgerId = subLedgerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getTransactionPartyId() {
        return transactionPartyId;
    }

    public void setTransactionPartyId(String transactionPartyId) {
        this.transactionPartyId = transactionPartyId;
    }

    public long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public long getTransactionParameterId() {
        return transactionParameterId;
    }

    public void setTransactionParameterId(long transactionParameterId) {
        this.transactionParameterId = transactionParameterId;
    }
}