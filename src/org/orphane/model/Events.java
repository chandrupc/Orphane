package org.orphane.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.orphane.modelenum.EventName;
import org.orphane.modelenum.EventStatus;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "orp_event_mgmt", catalog = "orphane")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Events {

	@Id
	@GeneratedValue(generator = "event_id_generator", strategy = GenerationType.TABLE)
	@TableGenerator(name = "event_id_generator", table = "orp_event_id_gen", allocationSize = 1, initialValue = 100, catalog = "orphane", pkColumnName = "event_id", pkColumnValue = "value", valueColumnName = "cur_id")
	@Column(name = "event_id")
	public Long eventId;

	@Enumerated(EnumType.STRING)
	@Column(name = "event_name", nullable = false, length = 50)
	public EventName eventName;

	@Temporal(TemporalType.DATE)
	@Column(name = "event_date", nullable = false)
	public Date eventDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "event_status", nullable = false, length = 50)
	public EventStatus eventStatus;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "reg_user_id", foreignKey = @ForeignKey(name = "orp_event_mgmt_reg_user_id_fk"), nullable = false)
	public RegularUsers regularUsers;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "orp_id", foreignKey = @ForeignKey(name = "orp_event_mgmt_orp_id_fk"), nullable = false)
	public Orphanage orphanage;

	@Column(name = "event_description", nullable = true)
	public String description;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public EventName getEventName() {
		return eventName;
	}

	public void setEventName(EventName eventName) {
		this.eventName = eventName;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public EventStatus getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}

	public RegularUsers getRegularUsers() {
		return regularUsers;
	}

	public void setRegularUsers(RegularUsers regularUsers) {
		this.regularUsers = regularUsers;
	}

	public Orphanage getOrphanage() {
		return orphanage;
	}

	public void setOrphanage(Orphanage orphanage) {
		this.orphanage = orphanage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Events [eventId=" + eventId + ", eventName=" + eventName + ", eventDate=" + eventDate + ", eventStatus="
				+ eventStatus + ", regularUsers=" + regularUsers + ", orphanage=" + orphanage + ", description="
				+ description + "]";
	}

}
