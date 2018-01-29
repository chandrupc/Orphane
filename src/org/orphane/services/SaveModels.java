package org.orphane.services;

import org.hibernate.Session;
import org.orphane.model.CardDetails;
import org.orphane.model.Events;
import org.orphane.model.FileDetails;
import org.orphane.model.NotifyUsers;
import org.orphane.model.Orphanage;
import org.orphane.model.RegularUsers;
import org.orphane.model.Trustee;
import org.orphane.util.HBUtil;

public class SaveModels {

	public static void addCardDetails(CardDetails cardDetails) {
		try {
			Session session = HBUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(cardDetails);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addEvents(Events event) {
		try {
			Session session = HBUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(event);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addFileDetails(FileDetails fileDetails) {
		try {
			Session session = HBUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(fileDetails);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addNotifyUsers(NotifyUsers user) {
		try {
			Session session = HBUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addOrphanage(Orphanage orphanage) {
		try {
			Session session = HBUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(orphanage);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addRegularUsers(RegularUsers regUser) {
		try {
			Session session = HBUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(regUser);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addTrustee(Trustee trustee) {
		try {
			Session session = HBUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(trustee);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
