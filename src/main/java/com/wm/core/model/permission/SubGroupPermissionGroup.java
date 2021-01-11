package com.wm.core.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "wm_sub_group_permission_groups")
public class SubGroupPermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_sub_group_permission_groups_seq")
	@SequenceGenerator(name = "wm_sub_group_permission_groups_seq", sequenceName = "wm_sub_group_permission_groups_seq", initialValue = 1, allocationSize = 1)
	private long id;


	private long permissiongroupId;


	private long subgroupId;


	public SubGroupPermissionGroup() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public SubGroupPermissionGroup(long permissiongroupId, long subgroupId) {
		this.permissiongroupId = permissiongroupId;
		this.subgroupId = subgroupId;
	}

	public long getPermissiongroupId() {
		return permissiongroupId;
	}

	public void setPermissiongroupId(long permissiongroupId) {
		this.permissiongroupId = permissiongroupId;
	}

	public long getSubgroupId() {
		return subgroupId;
	}

	public void setSubgroupId(long subgroupId) {
		this.subgroupId = subgroupId;
	}

	@Override
	public String toString() {
		return "SubGroupPermissionGroup{" +
				"id=" + id +
				", permissiongroupId=" + permissiongroupId +
				", subgroupId=" + subgroupId +
				'}';
	}
}
