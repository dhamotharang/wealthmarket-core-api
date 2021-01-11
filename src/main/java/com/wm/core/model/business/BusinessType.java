package com.wm.core.model.business;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "wm_business_types")
public class BusinessType {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_business_types_seq")
	@SequenceGenerator(name = "wm_business_types_seq", sequenceName = "wm_business_types_seq", initialValue = 1, allocationSize = 1)
	private long id;

	@NotBlank(message = "name is mandatory for business type")
	@Column(unique = true)
	private String name;

	@NotNull(message = "user is mandatory for creating Business type")
	private long userId;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	private long businesssectorId;

	public BusinessType() {
		super();
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

	public long getBusinesssectorId() {
		return businesssectorId;
	}

	public void setBusinesssectorId(long businesssectorId) {
		this.businesssectorId = businesssectorId;
	}

	public BusinessType(@NotBlank(message = "name is mandatory for business type") String name, @NotNull(message = "user is mandatory for creating Business type") long userId, Date date_created, Date last_updated, long businesssectorId) {
		this.name = name;
		this.userId = userId;
		this.date_created = date_created;
		this.last_updated = last_updated;
		this.businesssectorId = businesssectorId;
	}

	@Override
	public String toString() {
		return "BusinessType{" +
				"id=" + id +
				", name='" + name + '\'' +
				", userId=" + userId +
				", date_created=" + date_created +
				", last_updated=" + last_updated +
				", businesssectorId=" + businesssectorId +
				'}';
	}
}
