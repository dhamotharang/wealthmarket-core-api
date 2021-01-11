package com.wm.core.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NewUser {

	@NotBlank(message = "date of birth is mandatory")
	private String dob;

	@NotBlank(message = "email is mandatory")
	@Email(message = "Email must be a valid email address")
	private String email;

	@NotBlank(message = "first name is mandatory")
	private String firstName;

	@NotBlank(message = "gender is mandatory")
	private String gender;

	@NotEmpty(message = "last name is mandatory")
	private String lastName;

	@NotBlank(message = "username is mandatory")
	private String userName;

	@NotBlank(message = "password is mandatory")
	private String password;

	@NotBlank(message = "phone is mandatory")
	private String phone;

	@NotNull(message = "please confirm if signing up for newsletter or not")
	private boolean accept_newsletter;

	@NotNull(message = "please confirm if signing up for offers or not")
	private boolean accept_offers;

	public NewUser() {
		super();
	}

	public NewUser(String firstName, String lastName, String email, String phone, String gender, String dob,
			String password, boolean accept_newsletter, boolean accept_offers ) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.dob = dob;
		this.password = password;
		this.accept_newsletter = accept_newsletter;
		this.accept_offers = accept_offers;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
		return "NewUser{" +
				"dob='" + dob + '\'' +
				", email='" + email + '\'' +
				", firstName='" + firstName + '\'' +
				", gender='" + gender + '\'' +
				", lastName='" + lastName + '\'' +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", phone='" + phone + '\'' +
				'}';
	}
}
