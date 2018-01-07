package org.orphane.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

	@Column(name = "address", nullable = false)
	public String address;

	@Column(name = "city", length = 50, nullable = false)
	public String city;

	@Column(name = "state_name", length = 50, nullable = false)
	public String state;

	@Column(name = "pincode", nullable = false)
	public Integer pincode;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

}
