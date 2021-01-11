package com.wm.core.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "wm_users_special_permission_groups")
public class UserSpecialPermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_users_special_permission_groups_seq")
	@SequenceGenerator(name = "wm_users_special_permission_groups_seq", sequenceName = "wm_users_special_permission_groups_seq", initialValue = 1, allocationSize = 1)
	private long id;

	private long userId;

	private long permissiongroupId;

	private String action;

	public UserSpecialPermissionGroup() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getPermissiongroupId() {
		return permissiongroupId;
	}

	public void setPermissiongroupId(long permissiongroupId) {
		this.permissiongroupId = permissiongroupId;
	}

	public UserSpecialPermissionGroup(long userId, long permissiongroupId, String action) {
		this.userId = userId;
		this.permissiongroupId = permissiongroupId;
		this.action = action;
	}

	@Override
	public String toString() {
		return "UserSpecialPermissionGroup{" +
				"id=" + id +
				", userId=" + userId +
				", permissiongroupId=" + permissiongroupId +
				", action='" + action + '\'' +
				'}';
	}
}
