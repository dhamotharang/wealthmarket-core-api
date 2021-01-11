package com.wm.core.model.user;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wm_change_requests")
public class ChangeDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_change_requests_seq")
	@SequenceGenerator(name = "wm_change_requests_seq", sequenceName = "wm_change_requests_seq", initialValue = 1, allocationSize = 1)
	private long id;

	private Date date_created;

	private long statusId;

	private String subject;

	private String old_detail;

	private String new_detail;

	private long userId;

	private String reason;

	public ChangeDetails() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOld_detail() {
		return old_detail;
	}

	public void setOld_detail(String old_detail) {
		this.old_detail = old_detail;
	}

	public String getNew_detail() {
		return new_detail;
	}

	public void setNew_detail(String new_detail) {
		this.new_detail = new_detail;
	}

	public String getReason() {
		return reason;
	}

	public ChangeDetails(Date date_created, long statusId, String subject, String old_detail, String new_detail, long userId, String reason) {
		this.date_created = date_created;
		this.statusId = statusId;
		this.subject = subject;
		this.old_detail = old_detail;
		this.new_detail = new_detail;
		this.userId = userId;
		this.reason = reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ChangeDetails{" +
				"id=" + id +
				", date_created=" + date_created +
				", statusId=" + statusId +
				", subject='" + subject + '\'' +
				", old_detail='" + old_detail + '\'' +
				", new_detail='" + new_detail + '\'' +
				", userId=" + userId +
				", reason='" + reason + '\'' +
				'}';
	}
}
