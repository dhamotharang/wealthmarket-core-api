package com.wm.core.model.business;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wm_producers")
public class Producers {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_producers_seq")
	@SequenceGenerator(name = "wm_producers_seq", sequenceName = "wm_producers_seq", initialValue = 1, allocationSize = 1)
	private long id;

	@Column(unique = true)
	private String producer_cac_number;

	private Date date_founded;

	private Date date_created;

	private long email_statusId;

	private long approval_statusId;

	private long approval_userId;

	private long organizationId;

	public Producers() {
		super();
	}

	public Producers(String producer_cac_number, Date date_founded, Date date_created, long email_statusId, long approval_statusId, long approval_userId, long organizationId) {
		this.producer_cac_number = producer_cac_number;
		this.date_founded = date_founded;
		this.date_created = date_created;
		this.email_statusId = email_statusId;
		this.approval_statusId = approval_statusId;
		this.approval_userId = approval_userId;
		this.organizationId = organizationId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProducer_cac_number() {
		return producer_cac_number;
	}

	public void setProducer_cac_number(String producer_cac_number) {
		this.producer_cac_number = producer_cac_number;
	}

	public Date getDate_founded() {
		return date_founded;
	}

	public void setDate_founded(Date date_founded) {
		this.date_founded = date_founded;
	}

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
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

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	@Override
	public String toString() {
		return "Producers{" +
				"id=" + id +
				", producer_cac_number='" + producer_cac_number + '\'' +
				", date_founded=" + date_founded +
				", date_created=" + date_created +
				", email_statusId=" + email_statusId +
				", approval_statusId=" + approval_statusId +
				", approval_userId=" + approval_userId +
				", organizationId=" + organizationId +
				'}';
	}
}
