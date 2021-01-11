package com.wm.core.model.permission;

import javax.persistence.*;

@Entity
@Table(name = "wm_organization_special_permission_groups")
public class OrganizationSpecialPermissionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_organization_special_permission_groups_seq")
	@SequenceGenerator(name = "wm_organization_special_permission_groups_seq", sequenceName = "wm_organization_special_permission_groups_seq", initialValue = 1, allocationSize = 1)
	private long id;

	private long permissiongroupId;


	private long organizationId;

	private String action;

	public OrganizationSpecialPermissionGroup() {
		super();
	}

	public OrganizationSpecialPermissionGroup(String action) {
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

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

	public long getPermissiongroupId() {
		return permissiongroupId;
	}

	public void setPermissiongroupId(long permissiongroupId) {
		this.permissiongroupId = permissiongroupId;
	}

	public OrganizationSpecialPermissionGroup(long permissiongroupId, long organizationId, String action) {
		this.permissiongroupId = permissiongroupId;
		this.organizationId = organizationId;
		this.action = action;
	}

	@Override
	public String toString() {
		return "OrganizationSpecialPermissionGroup{" +
				"id=" + id +
				", permissiongroupId=" + permissiongroupId +
				", organizationId=" + organizationId +
				", action='" + action + '\'' +
				'}';
	}
}
