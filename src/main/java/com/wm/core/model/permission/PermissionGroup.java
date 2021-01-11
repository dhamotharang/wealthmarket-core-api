package com.wm.core.model.permission;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "wm_permission_groups")
public class PermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_permission_groups_seq")
	@SequenceGenerator(name = "wm_permission_groups_seq", sequenceName = "wm_permission_groups_seq", initialValue = 1, allocationSize = 1)
	private long id;

	@NotBlank(message = "name is mandatory for creating permission group")
	private String name;

	private long userId;

	@JsonProperty(value = "date_created")
	private Date date_created;

	@JsonProperty(value = "last_updated")
	private Date last_updated;

	private long organizationId;

	public PermissionGroup() {
	}

	public PermissionGroup(String name, Date date_created, Date last_updated) {
		this.name = name;
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

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(Date last_updated) {
		this.last_updated = last_updated;
	}

	public PermissionGroup(@NotBlank(message = "name is mandatory for creating permission group") String name, long userId, Date date_created, Date last_updated, long organizationId) {
		this.name = name;
		this.userId = userId;
		this.date_created = date_created;
		this.last_updated = last_updated;
		this.organizationId = organizationId;
	}

	@Override
	public String toString() {
		return "PermissionGroup{" +
				"id=" + id +
				", name='" + name + '\'' +
				", userId=" + userId +
				", date_created=" + date_created +
				", last_updated=" + last_updated +
				", organizationId=" + organizationId +
				'}';
	}
}
