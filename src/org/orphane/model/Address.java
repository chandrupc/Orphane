package org.orphane.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

	@Column(name = "house_no", length = 10, nullable = false)
	public String houseNumber;

	@Column(name = "street_name", length = 50, nullable = false)
	public String streetName;

	@Column(name = "area_name", length = 50, nullable = false)
	public String areaName;

	@Column(name = "city", length = 50, nullable = false)
	public String city;

	@Column(name = "state", length = 50, nullable = false)
	public String state;

	@Column(name = "pincode", nullable = false)
	public Integer pincode;

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
