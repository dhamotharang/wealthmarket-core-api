package com.wm.core.model.user;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wm_organizations")
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_organizations_seq")
	@SequenceGenerator(name = "wm_organizations_seq", sequenceName = "wm_organizations_seq", initialValue = 1, allocationSize = 1)
	private long id;

	private String name;

	@Column(unique = true)
	private String email;

	@Column(unique = true)
	private String phone;

	@Column(unique = true)
	private String uniquiId;

	private Date created_date;

	private long creatoruserId;

	private  long owneruserId;

	private long grouptypeId;

	private  long groupId;

	private long subgroupId;

	private long membertypeId;

	private  long submembertypeId;


	public Organization() {
	}

	public Organization(String name, String email, String phone, String uniquiId, Date created_date, long creatoruserId, long owneruserId, long grouptypeId, long groupId, long subgroupId, long membertypeId, long submembertypeId) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.uniquiId = uniquiId;
		this.created_date = created_date;
		this.creatoruserId = creatoruserId;
		this.owneruserId = owneruserId;
		this.grouptypeId = grouptypeId;
		this.groupId = groupId;
		this.subgroupId = subgroupId;
		this.membertypeId = membertypeId;
		this.submembertypeId = submembertypeId;
	}

	public String getUniquiId() {
		return uniquiId;
	}

	public void setUniquiId(String uniquiId) {
		this.uniquiId = uniquiId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public long getCreatoruserId() {
		return creatoruserId;
	}

	public void setCreatoruserId(long creatoruserId) {
		this.creatoruserId = creatoruserId;
	}

	public long getOwneruserId() {
		return owneruserId;
	}

	public void setOwneruserId(long owneruserId) {
		this.owneruserId = owneruserId;
	}

	public long getGrouptypeId() {
		return grouptypeId;
	}

	public void setGrouptypeId(long grouptypeId) {
		this.grouptypeId = grouptypeId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getSubgroupId() {
		return subgroupId;
	}

	public void setSubgroupId(long subgroupId) {
		this.subgroupId = subgroupId;
	}

	public long getMembertypeId() {
		return membertypeId;
	}

	public void setMembertypeId(long membertypeId) {
		this.membertypeId = membertypeId;
	}

	public long getSubmembertypeId() {
		return submembertypeId;
	}

	public void setSubmembertypeId(long submembertypeId) {
		this.submembertypeId = submembertypeId;
	}


	@Override
	public String toString() {
		return "Organization{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", uniquiId='" + uniquiId + '\'' +
				", created_date=" + created_date +
				", creatoruserId=" + creatoruserId +
				", owneruserId=" + owneruserId +
				", grouptypeId=" + grouptypeId +
				", groupId=" + groupId +
				", subgroupId=" + subgroupId +
				", membertypeId=" + membertypeId +
				", submembertypeId=" + submembertypeId +
				'}';
	}
}
