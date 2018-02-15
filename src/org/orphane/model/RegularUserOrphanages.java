package org.orphane.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "orp_reg_user_orphanages")
public class RegularUserOrphanages {

	@Id
	@GeneratedValue(generator = "orp_reg_id", strategy = GenerationType.TABLE)
	@TableGenerator(name = "orp_reg_id", allocationSize = 1, initialValue = 100, pkColumnName = "reg_user_orp_id", pkColumnValue = "curr_id", table = "orp_reg_user_orp_id_gen", valueColumnName = "next_id")
	@Column(name = "post_id")
	public Long genId;

	@Column(name = "orp_id", nullable = false)
	public Long orpId;

	@Column(name = "reg_id", nullable = false)
	public Long regId;

	public Long getGenId() {
		return genId;
	}

	public void setGenId(Long genId) {
		this.genId = genId;
	}

	public Long getOrpId() {
		return orpId;
	}

	public void setOrpId(Long orpId) {
		this.orpId = orpId;
	}

	public Long getRegId() {
		return regId;
	}

	public void setRegId(Long regId) {
		this.regId = regId;
	}

	@Override
	public String toString() {
		return "RegularUserOrphanages [genId=" + genId + ", orpId=" + orpId + ", regId=" + regId + "]";
	}

}
