package com.wm.core.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "wm_roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_roles_seq")
	@SequenceGenerator(name = "wm_roles_seq", sequenceName = "wm_roles_seq", initialValue = 1, allocationSize = 1)
	private long id;

	@NotBlank(message = "Name is mandatory for role")
	private String name;

	private long userId;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	private  long roletypeId;

	private long organizationId;

	public Role() {
		super();
	}

	public Role(@NotBlank(message = "Name is mandatory for role") String name, long userId, Date date_created, Date last_updated, long roletypeId, long organizationId) {
		this.name = name;
		this.userId = userId;
		this.date_created = date_created;
		this.last_updated = last_updated;
		this.roletypeId = roletypeId;
		this.organizationId = organizationId;
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

	public long getRoletypeId() {
		return roletypeId;
	}

	public void setRoletypeId(long roletypeId) {
		this.roletypeId = roletypeId;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", name='" + name + '\'' +
				", userId=" + userId +
				", date_created=" + date_created +
				", last_updated=" + last_updated +
				", roletypeId=" + roletypeId +
				", organizationId=" + organizationId +
				'}';
	}
}
