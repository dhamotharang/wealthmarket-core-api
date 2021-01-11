package com.wm.core.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "wm_group_type_permission_groups")
public class GroupTypePermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_group_type_permission_groups_seq")
	@SequenceGenerator(name = "wm_group_type_permission_groups_seq", sequenceName = "wm_group_type_permission_groups_seq", initialValue = 1, allocationSize = 1)
	private long id;

	private long permissiongroupId;

	private long grouptypeId;

	public GroupTypePermissionGroup() {
		super();
	}

	public GroupTypePermissionGroup(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public GroupTypePermissionGroup(long permissiongroupId, long grouptypeId) {
		this.permissiongroupId = permissiongroupId;
		this.grouptypeId = grouptypeId;
	}

	public long getPermissiongroupId() {
		return permissiongroupId;
	}

	public void setPermissiongroupId(long permissiongroupId) {
		this.permissiongroupId = permissiongroupId;
	}

	public long getGrouptypeId() {
		return grouptypeId;
	}

	public void setGrouptypeId(long grouptypeId) {
		this.grouptypeId = grouptypeId;
	}

	@Override
	public String toString() {
		return "GroupTypePermissionGroup{" +
				"id=" + id +
				", permissiongroupId=" + permissiongroupId +
				", grouptypeId=" + grouptypeId +
				'}';
	}
}
