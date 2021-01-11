package com.wm.core.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "wm_permission_group_permissions")
public class PermissionGroupPermission {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_permission_group_permissions_seq")
	@SequenceGenerator(name = "wm_permission_group_permissions_seq", sequenceName = "wm_permission_group_permissions_seq", initialValue = 1, allocationSize = 1)
	private long id;

	private long permissionId;

	private long permissiongroupId;

	public PermissionGroupPermission() {
		super();
	}

	public PermissionGroupPermission(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(long permissionId) {
		this.permissionId = permissionId;
	}


	public PermissionGroupPermission(long permissionId, long permissiongroupId) {
		this.permissionId = permissionId;
		this.permissiongroupId = permissiongroupId;
	}

	public long getPermissiongroupId() {
		return permissiongroupId;
	}

	public void setPermissiongroupId(long permissiongroupId) {
		this.permissiongroupId = permissiongroupId;
	}

	@Override
	public String toString() {
		return "PermissionGroupPermission{" +
				"id=" + id +
				", permissionId=" + permissionId +
				", permissiongroupId=" + permissiongroupId +
				'}';
	}
}
