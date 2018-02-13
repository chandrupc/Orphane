package org.orphane.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "details")
public class Details {

	@Id
	@GeneratedValue(generator = "orp_id_generator", strategy = GenerationType.TABLE)
	@TableGenerator(name = "orp_id_generator", table = "orp_id_gen", allocationSize = 1, initialValue = 100, catalog = "orphane", pkColumnName = "orp_id", pkColumnValue = "value", valueColumnName = "cur_id")
	@Column(name = "orp_id")
	public Long id;

	@Column(name = "name", length = 50, nullable = false)
	public String name;

	@Column(name = "ph_num", nullable = false)
	public Long phoneNumber;

	@Column(name = "alt_ph_num", nullable = true)
	public Long altPhoneNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getAltPhoneNumber() {
		return altPhoneNumber;
	}

	public void setAltPhoneNumber(Long altPhoneNumber) {
		this.altPhoneNumber = altPhoneNumber;
	}

}
