package com.wm.core.model.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_default_user_account")
public class DefaultUserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_default_user_account_seq")
	@SequenceGenerator(name = "wm_default_user_account_seq", sequenceName = "wm_default_user_account_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	private Long accountId;

	private Long userTypeId;

	private Long accountTypeId;

	private Long objectId;

	private Long createdBy;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	public DefaultUserAccount() {
		super();
	}

	public DefaultUserAccount(Long accountId,Long accountTypeId, Long objectId, Long createdBy) {
		this.accountId = accountId;
		this.accountTypeId = accountTypeId;
		this.objectId = objectId;
		this.createdBy = createdBy;
	}

	public DefaultUserAccount(Long accountId, Long userTypeId, Long accountTypeId, Long objectId, Long createdBy, Date date_created, Date last_updated) {
		this.accountId = accountId;
		this.userTypeId = userTypeId;
		this.accountTypeId = accountTypeId;
		this.objectId = objectId;
		this.createdBy = createdBy;
		this.date_created = date_created;
		this.last_updated = last_updated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public Long getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(Long accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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
