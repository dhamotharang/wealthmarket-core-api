package com.wm.core.model.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_subledger_accounts")
public class SubLedgerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_sub_ledger_accounts_seq")
    @SequenceGenerator(name = "wm_sub_ledger_accounts_seq", sequenceName = "wm_sub_ledger_accounts_seq", initialValue = 1, allocationSize = 1)
    private long id;

    @NotBlank(message = "name is mandatory for sub-ledger account")
    private String name;

    private long ledgerId;

    private long userId;

    @JsonProperty(value = "date_created")
    private Date date_created;

    @JsonProperty(value = "last_updated")
    private Date last_updated;

    public SubLedgerAccount() {
        super();
    }

    public SubLedgerAccount(@NotBlank(message = "name is mandatory for sub-ledger account") String name) {
        this.name = name;
    }

    public SubLedgerAccount(long id, @NotBlank(message = "name is mandatory for sub-ledger account") String name) {
        this.id = id;
        this.name = name;
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

    public long getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(long ledgerId) {
        this.ledgerId = ledgerId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }

    public SubLedgerAccount(@NotBlank(message = "name is mandatory for sub-ledger account") String name, long ledgerId, @NotNull(message = "user is mandatory for creating sub-ledger account") long userId, Date date_created, Date last_updated) {
        this.name = name;
        this.ledgerId = ledgerId;
        this.userId = userId;
        this.date_created = date_created;
        this.last_updated = last_updated;
    }
}
