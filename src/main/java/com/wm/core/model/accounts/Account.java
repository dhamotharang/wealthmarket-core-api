package com.wm.core.model.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Entity
@Table(name = "wm_accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_account_seq")
	@SequenceGenerator(name = "wm_account_seq", sequenceName = "wm_account_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	@NotBlank(message = "Specify the name of the account")
	private String name;

	@NotBlank(message = "Specify the suitable description/purpose of the account")
	private String description;

	@NotNull(message = "Specify the account type to assign to the account")
	private String accountTypeId;

	@NotNull(message = "Specify the instrument to assign to the account")
	private Long instrumentId;

	@NotNull(message = "Specify the account ledger type")
	private Long subledgerId;

	@NotNull(message = "Specify the account owner")
	private Long objectId;

	private Long createdBy;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	public Account() {
		super();
	}

	public Account(@NotBlank(message = "Specify the name of the account") String name, @NotBlank(message = "Specify the suitable description/purpose of the account") String description, @NotNull(message = "Specify the account type to assign to the account") String accountTypeId, @NotNull(message = "Specify the instrument to assign to the account") Long instrumentId, @NotNull(message = "Specify the account type") Long subledgerId, Long objectId, Long createdBy) {
		this.name = name;
		this.description = description;
		this.accountTypeId = accountTypeId;
		this.instrumentId = instrumentId;
		this.subledgerId = subledgerId;
		this.objectId = objectId;
		this.createdBy = createdBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public Long getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(Long instrumentId) {
		this.instrumentId = instrumentId;
	}

	public Long getSubledgerId() {
		return subledgerId;
	}

	public void setSubledgerId(Long subledgerId) {
		this.subledgerId = subledgerId;
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

	public Account(@NotBlank(message = "Specify the name of the account") String name, @NotBlank(message = "Specify the suitable description/purpose of the account") String description, @NotNull(message = "Specify the account type to assign to the account") String accountTypeId, @NotNull(message = "Specify the instrument to assign to the account") Long instrumentId, @NotNull(message = "Specify the account type") Long subledgerId, @NotNull(message = "Specify the account owner") Long objectId, Long createdBy, Date date_created, Date last_updated) {
		this.name = name;
		this.description = description;
		this.accountTypeId = accountTypeId;
		this.instrumentId = instrumentId;
		this.subledgerId = subledgerId;
		this.objectId = objectId;
		this.createdBy = createdBy;
		this.date_created = date_created;
		this.last_updated = last_updated;
	}
}
