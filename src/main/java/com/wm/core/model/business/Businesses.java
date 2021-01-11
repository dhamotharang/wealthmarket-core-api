package com.wm.core.model.business;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wm_businesses")
public class Businesses {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_businesses_seq")
	@SequenceGenerator(name = "wm_businesses_seq", sequenceName = "wm_businesses_seq", initialValue = 1, allocationSize = 1)
	private long id;

	@Lob
	private String business_description;

	private long businesssectorId;

	private long businesstypeId;

	private Date datecreated;

	private long email_statusId;

	private long approval_statusId;

	private long approval_userId;

	@Column(unique = true)
	private String business_cac_number;

	private Date date_founded;

	private long organizationId;

	private String website_link;

	public Businesses() {
		super();
	}

	public Businesses(String business_description, long businesssectorId, long businesstypeId, Date datecreated, long email_statusId, long approval_statusId, long approval_userId, String business_cac_number, Date date_founded, long organizationId, String website_link) {
		this.business_description = business_description;
		this.businesssectorId = businesssectorId;
		this.businesstypeId = businesstypeId;
		this.datecreated = datecreated;
		this.email_statusId = email_statusId;
		this.approval_statusId = approval_statusId;
		this.approval_userId = approval_userId;
		this.business_cac_number = business_cac_number;
		this.date_founded = date_founded;
		this.organizationId = organizationId;
		this.website_link = website_link;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBusiness_description() {
		return business_description;
	}

	public void setBusiness_description(String business_description) {
		this.business_description = business_description;
	}

	public long getBusinesssectorId() {
		return businesssectorId;
	}

	public void setBusinesssectorId(long businesssectorId) {
		this.businesssectorId = businesssectorId;
	}

	public long getBusinesstypeId() {
		return businesstypeId;
	}

	public void setBusinesstypeId(long businesstypeId) {
		this.businesstypeId = businesstypeId;
	}

	public Date getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public long getEmail_statusId() {
		return email_statusId;
	}

	public void setEmail_statusId(long email_statusId) {
		this.email_statusId = email_statusId;
	}

	public long getApproval_statusId() {
		return approval_statusId;
	}

	public void setApproval_statusId(long approval_statusId) {
		this.approval_statusId = approval_statusId;
	}

	public long getApproval_userId() {
		return approval_userId;
	}

	public void setApproval_userId(long approval_userId) {
		this.approval_userId = approval_userId;
	}

	public String getBusiness_cac_number() {
		return business_cac_number;
	}

	public void setBusiness_cac_number(String business_cac_number) {
		this.business_cac_number = business_cac_number;
	}

	public Date getDate_founded() {
		return date_founded;
	}

	public void setDate_founded(Date date_founded) {
		this.date_founded = date_founded;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public String getWebsite_link() {
		return website_link;
	}

	public void setWebsite_link(String website_link) {
		this.website_link = website_link;
	}

	@Override
	public String toString() {
		return "Businesses{" +
				"id=" + id +
				", business_description='" + business_description + '\'' +
				", businesssectorId=" + businesssectorId +
				", businesstypeId=" + businesstypeId +
				", datecreated=" + datecreated +
				", email_statusId=" + email_statusId +
				", approval_statusId=" + approval_statusId +
				", approval_userId=" + approval_userId +
				", business_cac_number='" + business_cac_number + '\'' +
				", date_founded=" + date_founded +
				", organizationId=" + organizationId +
				", website_link='" + website_link + '\'' +
				'}';
	}
}
