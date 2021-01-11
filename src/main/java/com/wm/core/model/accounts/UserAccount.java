package com.wm.core.model.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wm_user_accounts")
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_user_account_seq")
	@SequenceGenerator(name = "wm_user_account_seq", sequenceName = "wm_user_account_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	private Long objectId;

	private Long accountId;

	private Long accountTypeId;

	private Long statusId;

	private Double amount = 0.00;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	public UserAccount() {
		super();
	}

	public UserAccount(Long objectId, Long accountId, Long accountTypeId, Long statusId, Double amount) {
		this.objectId = objectId;
		this.accountId = accountId;
		this.accountTypeId = accountTypeId;
		this.statusId = statusId;
		this.amount = amount;
	}

	public UserAccount(Long objectId, Long accountId, Long accountTypeId, Long statusId, Double amount, Date date_created, Date last_updated) {
		this.objectId = objectId;
		this.accountId = accountId;
		this.accountTypeId = accountTypeId;
		this.statusId = statusId;
		this.amount = amount;
		this.date_created = date_created;
		this.last_updated = last_updated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(Long accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

}
