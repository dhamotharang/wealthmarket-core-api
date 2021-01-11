package com.wm.core.model.business;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_business_sectors")
public class BusinessSector {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_business_sectors_seq")
	@SequenceGenerator(name = "wm_business_sectors_seq", sequenceName = "wm_business_sectors_seq", initialValue = 1, allocationSize = 1)
	private long id;

	@NotBlank(message = "Name is mandatory for business sector")
	@Column(unique = true)
	private String name;

	@NotNull(message = "user is mandatory for creating Business Sector")
	private long userId;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	public BusinessSector() {
		super();
	}

	public BusinessSector(@NotBlank(message = "Name is mandatory for business sector") String name, @NotNull(message = "user is mandatory for creating permission group") long userId, Date date_created, Date last_updated) {
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

	@Override
	public String toString() {
		return "BusinessSector{" +
				"id=" + id +
				", name='" + name + '\'' +
				", userId=" + userId +
				", date_created=" + date_created +
				", last_updated=" + last_updated +
				'}';
	}
}
