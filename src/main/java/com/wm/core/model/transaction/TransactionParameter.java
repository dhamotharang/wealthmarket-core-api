package com.wm.core.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "wm_transaction_parameters")
public class TransactionParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_transaction_parameters_seq")
    @SequenceGenerator(name = "wm_transaction_parameters_seq", sequenceName = "wm_transaction_parameters_seq", initialValue = 1, allocationSize = 1)
    private long id;

    @NotBlank(message = "name is mandatory for transaction parameter")
    private String name;

    @NotBlank(message = "value is mandatory for transaction parameter")
    private String value;

    @NotNull(message = "transactionParameterTypeId is required")
    @JsonProperty(value = "transaction_parameter_type_id")
    private long transactionParameterTypeId;

    @NotNull(message = "userId is required")
    @JsonProperty(value = "created_by")
    private long createdBy;

    @JsonProperty(value = "date_created")
    private Date dateCreated;

    @JsonProperty(value = "last_updated")
    private Date lastUpdated;

    public TransactionParameter() {
    }

    public TransactionParameter(@NotBlank(message = "name is mandatory for transaction parameter") String name,
                                @NotBlank(message = "value is mandatory for transaction parameter") String value,
                                @NotNull(message = "userId is required") long createdBy, Date dateCreated,
                                Date lastUpdated, Set<TransactionParameterType> transactionParameterTypes,
                                long transactionParameterTypeId) {
        this.name = name;
        this.value = value;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
        this.transactionParameterTypeId = transactionParameterTypeId;
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

    public long getTransactionParameterTypeId() {
        return transactionParameterTypeId;
    }

    public void setTransactionParameterTypeId(long transactionParameterTypeId) {
        this.transactionParameterTypeId = transactionParameterTypeId;
    }
}