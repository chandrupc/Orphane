package org.orphane.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "orp_trustee", catalog = "orphane", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ph_num", name = "orp_trustee_ph_num_unq"),
		@UniqueConstraint(columnNames = "alt_ph_num", name = "orp_trustee_alt_ph_num_unq"),
		@UniqueConstraint(columnNames = { "house_no", "street_name", "area_name", "city", "state",
				"pincode" }, name = "orp_trustee_address_unq") })
@AttributeOverrides({
		@AttributeOverride(name = "name", column = @Column(name = "first_name", nullable = false, length = 30)),
		@AttributeOverride(name = "state", column = @Column(name = "tru_state", length = 50, nullable = false)) })

@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Trustee extends Details {

	@Column(name = "last_name", nullable = false, length = 30)
	public String lastName;

	@Embedded
	public Address address;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id", foreignKey = @ForeignKey(name = "orp_trustee_file_id_fk"))
	public FileDetails fileDetails;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public FileDetails getFileDetails() {
		return fileDetails;
	}

	public void setFileDetails(FileDetails fileDetails) {
		this.fileDetails = fileDetails;
	}

}
