package com.wm.core.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_transaction_parameter_types")
public class TransactionParameterType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_transaction_parameter_types_seq")
    @SequenceGenerator(name = "wm_transaction_parameter_types_seq", sequenceName = "wm_transaction_parameter_types_seq", initialValue = 1, allocationSize = 1)
    private long id;

    @NotBlank(message = "name is mandatory for transaction parameter type")
    private String name;

    @NotBlank(message = "description is mandatory for transaction parameter type")
    private String description;

    @NotNull(message = "userId is required")
    @JsonProperty(value = "created_by")
    private long createdBy;

    @JsonProperty(value = "date_created")
    private Date dateCreated;

    @JsonProperty(value = "last_updated")
    private Date lastUpdated;

    public TransactionParameterType() {
    }

    public TransactionParameterType(@NotBlank(message = "name is mandatory for transaction parameter type") String name,
                                    @NotBlank(message = "description is mandatory for transaction parameter type") String description,
                                    @NotNull(message = "userId is required") long createdBy, Date dateCreated, Date lastUpdated) {
        this.name = name;
        this.description = description;
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