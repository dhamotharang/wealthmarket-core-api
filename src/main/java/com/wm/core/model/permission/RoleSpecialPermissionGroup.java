package com.wm.core.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "wm_role_special_permission_groups")
public class RoleSpecialPermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_role_special_permission_groups_seq")
	@SequenceGenerator(name = "wm_role_special_permission_groups_seq", sequenceName = "wm_role_special_permission_groups_seq", initialValue = 1, allocationSize = 1)
	private long id;


	private long roleId;

	private long permissiongroupId;

	private String action;

	public RoleSpecialPermissionGroup() {
		super();
	}

	public RoleSpecialPermissionGroup(String action) {
		this.action = action;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public RoleSpecialPermissionGroup(long roleId, long permissiongroupId, String action) {
		this.roleId = roleId;
		this.permissiongroupId = permissiongroupId;
		this.action = action;
	}

	public long getPermissiongroupId() {
		return permissiongroupId;
	}

	public void setPermissiongroupId(long permissiongroupId) {
		this.permissiongroupId = permissiongroupId;
	}

	@Override
	public String toString() {
		return "RoleSpecialPermissionGroup{" +
				"id=" + id +
				", roleId=" + roleId +
				", permissiongroupId=" + permissiongroupId +
				", action='" + action + '\'' +
				'}';
	}
}
