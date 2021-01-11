package com.wm.core.model.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "wm_account_type")
public class AccountType {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_account_type_seq")
	@SequenceGenerator(name = "wm_account_type_seq", sequenceName = "wm_account_type_seq", initialValue = 1, allocationSize = 1)
	private long id;

	@NotBlank(message = "Name is mandatory for Account type")
	private String name;

	@NotBlank(message = "Description is mandatory for Account type")
	private String description;

	private long userId;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	public AccountType() {
		super();
	}

	public AccountType(@NotBlank(message = "Name is mandatory for Account type") String name, @NotBlank(message = "Description is mandatory for Account type") String description, long userId, Date date_created, Date last_updated) {
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.date_created = date_created;
		this.last_updated = last_updated;
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
}
