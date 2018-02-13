package org.orphane.services;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.orphane.model.CardDetails;
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

	public static List<String> getOrphanageState() {
		List<String> orphanages = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("select distinct address.state from Orphanage");
			orphanages = query.getResultList();
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orphanages;
	}

	public static List<Orphanage> fetchSelectedOrphanageState(String stateName) {
		List<Orphanage> orphanages = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery(
					"select o.id,o.name,o.address.state,o.credential.email from Orphanage o where address.state = :state_name and credential.status = 'ACTIVATED' and id not in (select orpId from RegularUserOrphanages)");
			query.setParameter("state_name", stateName);
			orphanages = query.getResultList();
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orphanages;
	}

	public static List<Orphanage> fetchAddedOrphanageState(String stateName, Long regularId) {
		List<Orphanage> orphanages = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery(
					"select o.id,o.name,o.address.state,o.credential.email from Orphanage o where address.state = :state_name and credential.status = 'ACTIVATED' and id in (select orpId from RegularUserOrphanages where regId = :regularId)");
			query.setParameter("state_name", stateName);
			query.setParameter("regularId", regularId);
			orphanages = query.getResultList();
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orphanages;
	}
}
