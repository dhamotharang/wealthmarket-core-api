
package com.wm.core.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "wm_users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_users_seq")
	@SequenceGenerator(name = "wm_users_seq", sequenceName = "wm_users_seq", initialValue = 1, allocationSize = 1)
	private long id;

	@Column(unique = true, nullable = false)
	@Email
	private String email;

	@Column(unique = true, nullable = false)
	private String phone_number;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private long statusId;

	private String gender;

	private String first_name;

	private String last_name;

	@Column(unique = true)
	private String username;

	private int progress_level = 50;

	@Column(unique = true)
	private String unique_id;

	private int age;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dob;

	@CreationTimestamp
	private Date reg_date;

	private int verification_level = 0;

	private int blocked_status = 0;

	private long membertypeId;

	private long usertypeId;

	@NotNull(message = "please confirm if signing up for newsletter or not")
	private boolean accept_newsletter;

	@NotNull(message = "please confirm if signing up for offers or not")
	private boolean accept_offers;

	public User() {
	}

	public User(@Email String email, String phone_number, String password, long statusId, String gender, String first_name, String last_name, String username, int progress_level, String unique_id, int age, LocalDate dob, Date reg_date, int verification_level,
				int blocked_status, long membertypeId, long usertypeId, boolean accept_newsletter, boolean accept_offers) {
		this.email = email;
		this.phone_number = phone_number;
		this.password = password;
		this.statusId = statusId;
		this.gender = gender;
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.progress_level = progress_level;
		this.unique_id = unique_id;
		this.age = age;
		this.dob = dob;
		this.reg_date = reg_date;
		this.verification_level = verification_level;
		this.blocked_status = blocked_status;
		this.membertypeId = membertypeId;
		this.usertypeId = usertypeId;
		this.accept_newsletter = accept_newsletter;
		this.accept_offers = accept_offers;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getProgress_level() {
		return progress_level;
	}

	public void setProgress_level(int progress_level) {
		this.progress_level = progress_level;
	}

	public String getUnique_id() {
		return unique_id;
	}

	public void setUnique_id(String unique_id) {
		this.unique_id = unique_id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public int getVerification_level() {
		return verification_level;
	}

	public void setVerification_level(int verification_level) {
		this.verification_level = verification_level;
	}

	public int getBlocked_status() {
		return blocked_status;
	}

	public void setBlocked_status(int blocked_status) {
		this.blocked_status = blocked_status;
	}

	public long getMembertypeId() {
		return membertypeId;
	}

	public void setMembertypeId(long membertypeId) {
		this.membertypeId = membertypeId;
	}

	public long getUsertypeId() {
		return usertypeId;
	}

	public void setUsertypeId(long usertypeId) {
		this.usertypeId = usertypeId;
	}

	public boolean isAccept_newsletter() {
		return accept_newsletter;
	}

	public void setAccept_newsletter(boolean accept_newsletter) {
		this.accept_newsletter = accept_newsletter;
	}

	public boolean isAccept_offers() {
		return accept_offers;
	}

	public void setAccept_offers(boolean accept_offers) {
		this.accept_offers = accept_offers;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", phone_number='" + phone_number + '\'' +
				", password='" + password + '\'' +
				", statusId=" + statusId +
				", gender='" + gender + '\'' +
				", first_name='" + first_name + '\'' +
				", last_name='" + last_name + '\'' +
				", username='" + username + '\'' +
				", progress_level=" + progress_level +
				", unique_id='" + unique_id + '\'' +
				", age=" + age +
				", dob=" + dob +
				", reg_date=" + reg_date +
				", verification_level=" + verification_level +
				", blocked_status=" + blocked_status +
				", membertypeId=" + membertypeId +
				", usertypeId=" + usertypeId +
				'}';
	}
}
