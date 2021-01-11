package com.wm.core.model.user;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wm_member_organisations")
public class MemberOrganizations {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_member_organisations_seq")
	@SequenceGenerator(name = "wm_member_organisations_seq", sequenceName = "wm_member_organisations_seq", initialValue = 1, allocationSize = 1)
	private long id;

	private long statusId;

	private long userId;

	private long roleId;

	private Date date_added;

	private long organizationId;

	public MemberOrganizations() {
		super();
	}

	public MemberOrganizations(long statusId, long userId, long roleId, Date date_added, long organizationId) {
		this.statusId = statusId;
		this.userId = userId;
		this.roleId = roleId;
		this.date_added = date_added;
		this.organizationId = organizationId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public Date getDate_added() {
		return date_added;
	}

	public void setDate_added(Date date_added) {
		this.date_added = date_added;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String toString() {
		return "MemberOrganizations{" +
				"id=" + id +
				", statusId=" + statusId +
				", userId=" + userId +
				", roleId=" + roleId +
				", date_added=" + date_added +
				", organizationId=" + organizationId +
				'}';
	}
}
