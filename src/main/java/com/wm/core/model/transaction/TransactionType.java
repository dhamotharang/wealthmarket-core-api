package com.wm.core.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "wm_transaction_types")
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_transaction_types_seq")
    @SequenceGenerator(name = "wm_transaction_types_seq", sequenceName = "wm_transaction_types_seq", initialValue = 1, allocationSize = 1)
    private long id;

    @NotBlank(message = "name is mandatory for transaction type")
    private String name;

    @NotBlank(message = "transaction code is mandatory for transaction type")
    private String transactionCode;

    @NotBlank(message = "description is mandatory for transaction type")
    private String description;

    @NotNull
    @JsonProperty(value = "charged")
    private Boolean charged;

    @NotBlank(message = "regulatory issues is mandatory for transaction type")
    private String regulatoryIssues;

    @NotBlank(message = "risks controls is mandatory for transaction type")
    private String risksControls;

    @NotBlank(message = "notes is mandatory for transaction type")
    private String notes;

    @NotNull(message = "userId is required")
    @JsonProperty(value = "created_by")
    private long createdBy;

    @JsonProperty(value = "date_created")
    private Date dateCreated;

    @JsonProperty(value = "last_updated")
    private Date lastUpdated;

    public TransactionType() {
    }

    public TransactionType(@NotBlank(message = "name is mandatory for transaction type") String name,
                           @NotBlank(message = "transaction code is mandatory for transaction type") String transactionCode,
                           @NotBlank(message = "description is mandatory for transaction type") String description,
                           @NotNull Boolean charged,
                           @NotBlank(message = "regulatory issues is mandatory for transaction type") String regulatoryIssues,
                           @NotBlank(message = "risks controls is mandatory for transaction type") String risksControls,
                           @NotBlank(message = "notes is mandatory for transaction type") String notes,
                           @NotNull(message = "userId is required") long createdBy, Date dateCreated, Date lastUpdated) {
        this.name = name;
        this.transactionCode = transactionCode;
        this.description = description;
        this.charged = charged;
        this.regulatoryIssues = regulatoryIssues;
        this.risksControls = risksControls;
        this.notes = notes;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCharged() {
        return charged;
    }

    public void setCharged(Boolean charged) {
        this.charged = charged;
    }

    public String getRegulatoryIssues() {
        return regulatoryIssues;
    }

    public void setRegulatoryIssues(String regulatoryIssues) {
        this.regulatoryIssues = regulatoryIssues;
    }

    public String getRisksControls() {
        return risksControls;
    }

    public void setRisksControls(String risksControls) {
        this.risksControls = risksControls;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
}