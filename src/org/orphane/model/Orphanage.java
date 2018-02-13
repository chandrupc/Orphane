package org.orphane.model;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "orp_orphanage", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ph_num", name = "orp_orphanage_ph_num_unq"),
		@UniqueConstraint(columnNames = "alt_ph_num", name = "orp_orphanage_alt_ph_num_unq"),
		@UniqueConstraint(columnNames = { "address", "city", "state_name",
				"pincode" }, name = "orp_orphanage_address_unq") }, catalog = "orphane")
@AttributeOverrides({
		@AttributeOverride(name = "name", column = @Column(name = "orphanage_name", length = 50, nullable = false)) })
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "orphanage")
public class Orphanage extends Details {

	@SerializedName("@website")
	@Column(name = "website", nullable = true, length = 100)
	public String website;

	@Embedded
	public Address address;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id", foreignKey = @ForeignKey(name = "orp_orphanage_file_id_fk"))
	public FileDetails fileDetails;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "orp_mapping_table", joinColumns = @JoinColumn(name = "orp_id", foreignKey = @ForeignKey(name = "orp_mapping_table_orp_id_fk")), inverseJoinColumns = @JoinColumn(name = "trustee_id", foreignKey = @ForeignKey(name = "orp_mapping_table_trustee_id_fk")))
	public List<Trustee> trustee;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "email_id", foreignKey = @ForeignKey(name = "orp_orphanage_email_id_fk"))
	public Credential credential;

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public List<Trustee> getTrustee() {
		return trustee;
	}

	public void setTrustee(List<Trustee> trustee) {
		this.trustee = trustee;
	}

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	@Override
	public String toString() {
		return "Orphanage [website=" + website + ", address=" + address + ", fileDetails=" + fileDetails + ", trustee="
				+ trustee + ", credential=" + credential + "]";
	}

}
