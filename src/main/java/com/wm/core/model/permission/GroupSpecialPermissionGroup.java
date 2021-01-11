package com.wm.core.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "wm_group_special_permission_groups")
public class GroupSpecialPermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_group_special_permission_groups_seq")
	@SequenceGenerator(name = "wm_group_special_permission_groups_seq", sequenceName = "wm_group_special_permission_groups_seq", initialValue = 1, allocationSize = 1)
	private long id;

	private long permissiongroupId;

	private long groupId;

	private String action;

	public GroupSpecialPermissionGroup() {
		super();
	}

	public GroupSpecialPermissionGroup(String action) {
		this.action = action;
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public GroupSpecialPermissionGroup(long permissiongroupId, long groupId, String action) {
		this.permissiongroupId = permissiongroupId;
		this.groupId = groupId;
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
		return "GroupSpecialPermissionGroup{" +
				"id=" + id +
				", permissiongroupId=" + permissiongroupId +
				", groupId=" + groupId +
				", action='" + action + '\'' +
				'}';
	}
}
