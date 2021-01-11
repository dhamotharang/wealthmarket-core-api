package com.wm.core.model.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "wm_instruments")
@Entity
public class Instrument {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_monetary_instrument_seq")
	@SequenceGenerator(name = "wm_monetary_instrument_seq", sequenceName = "wm_monetary_instrument_seq", initialValue = 1, allocationSize = 1)
	private Long id;

	@NotBlank(message = "Specify the name of the instrument")
	private String name;

	@NotBlank(message = "Specify the suitable description")
	private String description;

	@NotNull(message = "user is mandatory for creating Instrument")
	private long userId;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	public Instrument() {
		super();
	}

	public Instrument(@NotBlank(message = "Specify the name of the account") String name) {
		this.name = name;
	}

	public Instrument(Long id, @NotBlank(message = "Specify the name of the account") String name) {
		this.id = id;
		this.name = name;
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

	public Instrument(Long id, @NotBlank(message = "Specify the name of the instrument") String name, @NotBlank(message = "Specify the suitable description") String description, @NotNull(message = "user is mandatory for creating Instrument") long userId, Date date_created, Date last_updated) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.date_created = date_created;
		this.last_updated = last_updated;
	}
}
