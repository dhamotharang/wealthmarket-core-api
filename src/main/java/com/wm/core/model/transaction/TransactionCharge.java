package com.wm.core.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_transaction_charges")
public class TransactionCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_transaction_charges_seq")
    @SequenceGenerator(name = "wm_transaction_charges_seq", sequenceName = "wm_transaction_charges_seq", initialValue = 1, allocationSize = 1)
    private long id;

    @NotBlank(message = "name is mandatory for transaction charge")
    private String name;

    @NotBlank(message = "description is mandatory for transaction charge")
    private String description;

    @NotBlank(message = "value is mandatory for transaction charge")
    private String value;

    @NotNull(message = "userId is required")
    @JsonProperty(value = "created_by")
    private long createdBy;

    @JsonProperty(value = "date_created")
    private Date dateCreated;

    @JsonProperty(value = "last_updated")
    private Date lastUpdated;

    public TransactionCharge() {
    }

    public TransactionCharge(@NotBlank(message = "name is mandatory for transaction charge") String name,
                             @NotBlank(message = "description is mandatory for transaction charge") String description,
                             @NotBlank(message = "value is mandatory for transaction charge") String value,
                             @NotNull(message = "userId is required") long createdBy, Date dateCreated, Date lastUpdated) {
        this.name = name;
        this.description = description;
        this.value = value;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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