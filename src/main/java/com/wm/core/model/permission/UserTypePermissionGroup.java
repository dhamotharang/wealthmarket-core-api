package com.wm.core.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "wm_user_type_permission_groups")
public class UserTypePermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_user_type_permission_groups_seq")
	@SequenceGenerator(name = "wm_user_type_permission_groups_seq", sequenceName = "wm_user_type_permission_groups_seq", initialValue = 1, allocationSize = 1)
	private long id;

	private long permissiongroupId;


	private long usertypeId;

	public UserTypePermissionGroup() {
		super();
	}

	public UserTypePermissionGroup(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserTypePermissionGroup(long permissiongroupId, long usertypeId) {
		this.permissiongroupId = permissiongroupId;
		this.usertypeId = usertypeId;
	}

	public long getPermissiongroupId() {
		return permissiongroupId;
	}

	public void setPermissiongroupId(long permissiongroupId) {
		this.permissiongroupId = permissiongroupId;
	}

	public long getUsertypeId() {
		return usertypeId;
	}

	public void setUsertypeId(long usertypeId) {
		this.usertypeId = usertypeId;
	}


	@Override
	public String toString() {
		return "UserTypePermissionGroup{" +
				"id=" + id +
				", permissiongroupId=" + permissiongroupId +
				", usertypeId=" + usertypeId +
				'}';
	}
}
