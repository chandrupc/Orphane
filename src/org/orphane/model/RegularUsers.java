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
@Table(name = "orp_reg_user", catalog = "orphane", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ph_num", name = "orp_reg_user_ph_num_unq"),
		@UniqueConstraint(columnNames = "alt_ph_num", name = "orp_reg_user_alt_ph_num_unq"),
		@UniqueConstraint(columnNames = { "address", "city", "state",
				"pincode" }, name = "orp_reg_user_address_unq") })
@AttributeOverrides({
		@AttributeOverride(name = "name", column = @Column(name = "first_name", nullable = false, length = 30)),
		@AttributeOverride(name = "state", column = @Column(name = "reg_state", length = 50, nullable = false)) 
})
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class RegularUsers extends Details {

	@Column(name = "last_name", nullable = false, length = 30)
	public String lastName;

	@Embedded
	public Address address;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "card_id", foreignKey = @ForeignKey(name = "orp_reg_user_card_id_fk"))
	public CardDetails cardDetails;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id", foreignKey = @ForeignKey(name = "orp_reg_user_file_id_fk"))
	public FileDetails fileDetails;

	public String getLastName() {
		return lastName;
	}

	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "email_id",foreignKey = @ForeignKey(name = "orp_reg_user_email_id_fk"))
	public Credential credential;
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public CardDetails getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(CardDetails cardDetails) {
		this.cardDetails = cardDetails;
	}

	public FileDetails getFileDetails() {
		return fileDetails;
	}

	public void setFileDetails(FileDetails fileDetails) {
		this.fileDetails = fileDetails;
	}

}
