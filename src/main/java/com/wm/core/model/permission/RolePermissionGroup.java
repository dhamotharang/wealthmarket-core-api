package com.wm.core.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "wm_role_permission_groups")
public class RolePermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_role_permission_groups_seq")
	@SequenceGenerator(name = "wm_role_permission_groups_seq", sequenceName = "wm_role_permission_groups_seq", initialValue = 1, allocationSize = 1)
	private long id;

	private long permissiongroupId;


	private long roleId;

	public RolePermissionGroup() {
		super();
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

	public RolePermissionGroup(long permissiongroupId, long roleId) {
		this.permissiongroupId = permissiongroupId;
		this.roleId = roleId;
	}

	public long getPermissiongroupId() {
		return permissiongroupId;
	}

	public void setPermissiongroupId(long permissiongroupId) {
		this.permissiongroupId = permissiongroupId;
	}

	@Override
	public String toString() {
		return "RolePermissionGroup{" +
				"id=" + id +
				", permissiongroupId=" + permissiongroupId +
				", roleId=" + roleId +
				'}';
	}
}
