package org.orphane.services;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.orphane.model.CardDetails;
import org.orphane.model.Events;
import org.orphane.model.FileDetails;
import org.orphane.model.NotifyUsers;
import org.orphane.model.Orphanage;
import org.orphane.model.RegularUsers;
import org.orphane.model.Trustee;
import org.orphane.util.HBUtil;

@SuppressWarnings("unchecked")
public class GetAllDetails {

	public static List<Orphanage> getOrphanages() {
		List<Orphanage> orphanages = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses
					.createQuery("from Orphanage where id not in (select o.orpId from RegularUserOrphanages o)");
			orphanages = query.getResultList();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orphanages;
	}

	public static List<Trustee> getTrustee() {
		List<Trustee> trustee = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from Trustee");
			trustee = query.getResultList();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trustee;
	}

	public static List<RegularUsers> getRegUsers() {
		List<RegularUsers> regUsers = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from RegularUsers");
			regUsers = query.getResultList();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return regUsers;
	}

	public static List<CardDetails> getCardDetails() {
		List<CardDetails> cardDetails = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from CardDetails");
			cardDetails = query.getResultList();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cardDetails;
	}

	public static List<NotifyUsers> getNotifyUsers() {
		List<NotifyUsers> notifyUsers = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from NotifyUsers");
			notifyUsers = query.getResultList();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notifyUsers;
	}

	public static List<FileDetails> getFileDetails() {
		List<FileDetails> fileDetails = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from FileDetails");
			fileDetails = query.getResultList();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileDetails;
	}

	public static List<String> getOrphanageState(Long id) {
		List<String> orphanages = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery(
					"select distinct address.state from Orphanage where credential.status='ACTIVATED' and orp_id not in (select orpId from RegularUserOrphanages where regId = :regId)");
			query.setParameter("regId", id);
			orphanages = query.getResultList();
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orphanages;
	}

	public static List<String> getRegularUserSubscribedOrphanageState(Long regId) {
		// System.out.println(regId);
		List<String> orphanages = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery(
					"select distinct address.state from Orphanage where orp_id in (select orpId from RegularUserOrphanages where regId = :regId)");
			query.setParameter("regId", regId);
			orphanages = query.getResultList();
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orphanages;
	}

	// -------------------------GET EVENTS OF USER------------------

	public static List<Events> getEventOfUser(Long regId) {
		// System.out.println(regId);
		List<Events> events = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery(
					"from Events where regularUsers.id = :regId and eventStatus = 'BOOKED' ORDER BY eventDate desc");
			query.setParameter("regId", regId);
			events = query.getResultList();
			/*
			 * for (Events each : events) {
			 * Hibernate.initialize(each.getOrphanage().fileDetails); }
			 */
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return events;
	}
}
