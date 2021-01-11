package com.wm.core.model.user;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wm_guests_visitors")
public class GuestVisitor {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wm_guest_visitor_seq")
	@SequenceGenerator(name = "wm_guest_visitor_seq", sequenceName = "wm_guest_visitor_seq", initialValue = 1, allocationSize = 1)
	private long id;

	private String mac_address;

	private String ipaddress;

	private String location;

	private String email;

	private String phone_number;

	private Date date;

	private long usertypeId;

	public GuestVisitor() {
		super();
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMac_address() {
		return mac_address;
	}

	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}



	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public GuestVisitor(String mac_address, String ipaddress, String location, String email, String phone_number, Date date, long usertypeId) {
		this.mac_address = mac_address;
		this.ipaddress = ipaddress;
		this.location = location;
		this.email = email;
		this.phone_number = phone_number;
		this.date = date;
		this.usertypeId = usertypeId;
	}

	public long getUsertypeId() {
		return usertypeId;
	}

	public void setUsertypeId(long usertypeId) {
		this.usertypeId = usertypeId;
	}

	@Override
	public String toString() {
		return "GuestVisitor{" +
				"id=" + id +
				", mac_address='" + mac_address + '\'' +
				", ipaddress='" + ipaddress + '\'' +
				", location='" + location + '\'' +
				", email='" + email + '\'' +
				", phone_number='" + phone_number + '\'' +
				", date=" + date +
				", usertypeId=" + usertypeId +
				'}';
	}
}
