package com.wm.core.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "wm_sub_group_special_permission_groups")
public class SubGroupSpecialPermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_sub_group_special_permission_groups_seq")
	@SequenceGenerator(name = "wm_sub_group_special_permission_groups_seq", sequenceName = "wm_sub_group_special_permission_groups_seq", initialValue = 1, allocationSize = 1)
	private long id;


	private long permissiongroupId;

	private long subgroupId;

	private String action;

	public SubGroupSpecialPermissionGroup() {
		super();
	}

	public SubGroupSpecialPermissionGroup(String action) {
		this.action = action;
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

	public SubGroupSpecialPermissionGroup(long permissiongroupId, long subgroupId, String action) {
		this.permissiongroupId = permissiongroupId;
		this.subgroupId = subgroupId;
		this.action = action;
	}

	@Override
	public String toString() {
		return "SubGroupSpecialPermissionGroup{" +
				"id=" + id +
				", permissiongroupId=" + permissiongroupId +
				", subgroupId=" + subgroupId +
				", action='" + action + '\'' +
				'}';
	}
}
