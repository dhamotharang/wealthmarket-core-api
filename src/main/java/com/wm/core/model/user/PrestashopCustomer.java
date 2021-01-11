package com.wm.core.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

public class PrestashopCustomer {

	@NotBlank(message = "date of birth is mandatory")
	private LocalDate birthday;

	@NotBlank(message = "email is mandatory")
	@Email(message = "Email must be a valid email address")
	private String email;

	@NotBlank(message = "first name is mandatory")
	private String firstname;

	@NotBlank(message = "gender is mandatory")
	private int gender;

	@NotEmpty(message = "last name is mandatory")
	private String lastname;

	@NotBlank(message = "password is mandatory")
	private String password;

	@NotBlank(message = "please pass wm unique id")
	private String wmuniqueid;

	@NotBlank(message = "please pass phone number")
	private String phone;

	@NotNull(message = "please confirm if user wants to receive mail for offers or not")
	private int receiveoffer;

	@NotNull(message = "please confirm if user wants to receive newsletter or not")
	private int newsletter;

	public PrestashopCustomer() {
		super();
	}

	public PrestashopCustomer(@NotBlank(message = "date of birth is mandatory") LocalDate birthday, @NotBlank(message = "email is mandatory") @Email(message = "Email must be a valid email address") String email, @NotBlank(message = "first name is mandatory") String firstname, @NotBlank(message = "gender is mandatory") int gender, String phone,
							  @NotEmpty(message = "last name is mandatory") String lastname, @NotBlank(message = "password is mandatory") String password, @NotBlank(message = "please pass wm unique id") String wmuniqueid, @NotNull(message = "please confirm if user wants to receive mail for offers or not") int receiveoffer, int newsletter) {
		this.birthday = birthday;
		this.email = email;
		this.firstname = firstname;
		this.gender = gender;
		this.lastname = lastname;
		this.phone = phone;
		this.password = password;
		this.wmuniqueid = wmuniqueid;
		this.receiveoffer = receiveoffer;
		this.newsletter = newsletter;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWmuniqueid() {
		return wmuniqueid;
	}

	public void setWmuniqueid(String wmuniqueid) {
		this.wmuniqueid = wmuniqueid;
	}

	public int getReceiveoffer() {
		return receiveoffer;
	}

	public void setReceiveoffer(int receiveoffer) {
		this.receiveoffer = receiveoffer;
	}

	public int getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(int newsletter) {
		this.newsletter = newsletter;
	}
}