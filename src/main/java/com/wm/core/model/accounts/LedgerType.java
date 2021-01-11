package com.wm.core.model.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "wm_ledger_type")
public class LedgerType {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_ledger_type_seq")
	@SequenceGenerator(name = "wm_ledger_type_seq", sequenceName = "wm_ledger_type_seq", initialValue = 1, allocationSize = 1)
	private long id;

	@NotBlank(message = "Name is mandatory for ledger type")
	@Column(unique = true)
	private String name;

	private long userId;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	public LedgerType() {
		super();
	}

	public LedgerType(@NotBlank(message = "Name is mandatory for ledger type") String name, long userId, Date date_created, Date last_updated) {
		this.name = name;
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

	public LedgerType(long id, @NotBlank(message = "Name is mandatory for ledger type") String name, long userId, Date date_created, Date last_updated) {
		this.id = id;
		this.name = name;
		this.userId = userId;
		this.date_created = date_created;
		this.last_updated = last_updated;
	}
}
