package com.wm.core.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "wm_group_permission_groups")
public class GroupPermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_group_permission_groups_seq")
	@SequenceGenerator(name = "wm_group_permission_groups_seq", sequenceName = "wm_group_permission_groups_seq", initialValue = 1, allocationSize = 1)
	private long id;


	private long permissiongroupId;

	private long groupId;


	public GroupPermissionGroup() {
		super();
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getPermissiongroupId() {
		return permissiongroupId;
	}

	public void setPermissiongroupId(long permissiongroupId) {
		this.permissiongroupId = permissiongroupId;
	}

	public GroupPermissionGroup(long permissiongroupId, long groupId) {
		this.permissiongroupId = permissiongroupId;
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "GroupPermissionGroup{" +
				"id=" + id +
				", permissiongroupId=" + permissiongroupId +
				", groupId=" + groupId +
				'}';
	}
}
