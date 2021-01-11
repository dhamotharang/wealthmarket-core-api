package com.wm.core.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_transaction_parties")
public class TransactionParty {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_transaction_parties_seq")
    @SequenceGenerator(name = "wm_transaction_parties_seq", sequenceName = "wm_transaction_parties_seq", initialValue = 1, allocationSize = 1)
    private long id;

    @NotBlank(message = "name is mandatory for transaction party")
    private String name;

    @NotNull(message = "userId is required")
    @JsonProperty(value = "created_by")
    private long createdBy;

    @JsonProperty(value = "date_created")
    private Date dateCreated;

    @JsonProperty(value = "last_updated")
    private Date lastUpdated;

    public TransactionParty() {
    }

    public TransactionParty(@NotBlank(message = "name is mandatory for transaction party") String name,
                            @NotNull(message = "userId is required") long createdBy,
                            Date dateCreated, Date lastUpdated) {
        this.name = name;
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