package com.wm.core.model.agency;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wm_agencies")
public class Agencies {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_agencies_seq")
	@SequenceGenerator(name = "wm_agencies_seq", sequenceName = "wm_agencies_seq", initialValue = 1, allocationSize = 1)
	private long id;

	private long license_statusId;

	private Date licence_issued_date;

	private long email_statusId;

	private long licensor_userId;

	private Date date;

	@Column(unique = true)
	private String agency_cac_number;

	private long organizationId;

	public Agencies() {
	}

	public Agencies(long license_statusId, Date licence_issued_date, long email_statusId, long licensor_userId, Date date, String agency_cac_number, long organizationId) {
		this.license_statusId = license_statusId;
		this.licence_issued_date = licence_issued_date;
		this.email_statusId = email_statusId;
		this.licensor_userId = licensor_userId;
		this.date = date;
		this.agency_cac_number = agency_cac_number;
		this.organizationId = organizationId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLicense_statusId() {
		return license_statusId;
	}

	public void setLicense_statusId(long license_statusId) {
		this.license_statusId = license_statusId;
	}

	public Date getLicence_issued_date() {
		return licence_issued_date;
	}

	public void setLicence_issued_date(Date licence_issued_date) {
		this.licence_issued_date = licence_issued_date;
	}

	public long getLicensor_userId() {
		return licensor_userId;
	}

	public void setLicensor_userId(long licensor_userId) {
		this.licensor_userId = licensor_userId;
	}

	public String getAgency_cac_number() {
		return agency_cac_number;
	}

	public void setAgency_cac_number(String agency_cac_number) {
		this.agency_cac_number = agency_cac_number;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public long getEmail_statusId() {
		return email_statusId;
	}

	public void setEmail_statusId(long email_statusId) {
		this.email_statusId = email_statusId;
	}

	@Override
	public String toString() {
		return "Agencies{" +
				"id=" + id +
				", license_statusId=" + license_statusId +
				", licence_issued_date=" + licence_issued_date +
				", email_statusId=" + email_statusId +
				", licensor_userId=" + licensor_userId +
				", date=" + date +
				", agency_cac_number='" + agency_cac_number + '\'' +
				", organizationId=" + organizationId +
				'}';
	}
}
