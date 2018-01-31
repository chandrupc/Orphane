package org.orphane.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.orphane.modelenum.SubscriptionStatus;

@Entity
@Table(name = "orp_notify")
public class NotifyUsers {

	@Id
	@Column(name = "email", length = 50, nullable = false)
	public String email;

	@Column(name = "username", length = 30, nullable = false)
	public String name;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_status", length = 20, nullable = false)
	public SubscriptionStatus status;

	public SubscriptionStatus getStatus() {
		return status;
	}

	public void setStatus(SubscriptionStatus status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
