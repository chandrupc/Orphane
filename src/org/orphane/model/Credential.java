package org.orphane.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.orphane.modelenum.UserStatus;
import org.orphane.modelenum.UserType;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "orp_credential", catalog = "orphane")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Credential {

	@Id
	@Column(name = "email", nullable = false, length = 50)
	public String email;

	@Column(name = "pass", nullable = false, length = 30)
	public String password;

	@Column(name = "auth_key", length = 30, nullable = false)
	public String authKey;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, name = "user_type", nullable = false)
	public UserType userType;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	public UserStatus status;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

}
