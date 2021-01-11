package com.wm.core.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "wm_group_type_special_permission_groups")
public class GroupTypeSpecialPermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_group_type_special_permission_groups_seq")
	@SequenceGenerator(name = "wm_group_type_special_permission_groups_seq", sequenceName = "wm_group_type_special_permission_groups_seq", initialValue = 1, allocationSize = 1)
	private long id;


	private long grouptypeId;

	private long permissiongroupId;

	private String action;

	public GroupTypeSpecialPermissionGroup() {
		super();
	}

	public GroupTypeSpecialPermissionGroup(String action) {
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

	public GroupTypeSpecialPermissionGroup(long grouptypeId, long permissiongroupId, String action) {
		this.grouptypeId = grouptypeId;
		this.permissiongroupId = permissiongroupId;
		this.action = action;
	}

	public long getGrouptypeId() {
		return grouptypeId;
	}

	public void setGrouptypeId(long grouptypeId) {
		this.grouptypeId = grouptypeId;
	}

	public long getPermissiongroupId() {
		return permissiongroupId;
	}

	public void setPermissiongroupId(long permissiongroupId) {
		this.permissiongroupId = permissiongroupId;
	}

	@Override
	public String toString() {
		return "GroupTypeSpecialPermissionGroup{" +
				"id=" + id +
				", grouptypeId=" + grouptypeId +
				", permissiongroupId=" + permissiongroupId +
				", action='" + action + '\'' +
				'}';
	}
}
