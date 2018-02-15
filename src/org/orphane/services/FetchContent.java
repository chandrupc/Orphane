package org.orphane.services;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.orphane.model.Orphanage;
import org.orphane.model.RegularUsers;
import org.orphane.util.HBUtil;

public class FetchContent {

	// Fetch a single regular user where the email matches

	public static RegularUsers getRegUserDetails(String mail) {
		RegularUsers regUser = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			@SuppressWarnings("deprecation")
			Criteria criteria = ses.createCriteria(RegularUsers.class);
			criteria.add(Restrictions.eq("credential.email", mail));
			regUser = (RegularUsers) criteria.uniqueResult();
			// System.out.println(regUser);
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			regUser = null;
			e.printStackTrace();
		}
		// System.out.println(regUser);
		return regUser;
	}

	// Fetch a single Orphanage where the email matches

	public static Orphanage getOrphanageDetails(String mail) {
		Orphanage orpUser = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			@SuppressWarnings("deprecation")
			Criteria criteria = ses.createCriteria(Orphanage.class);
			criteria.add(Restrictions.eq("credential.email", mail));
			orpUser = (Orphanage) criteria.uniqueResult();
			// System.out.println(orpUser);
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			orpUser = null;
			e.printStackTrace();
		}
		// System.out.println(orpUser);
		return orpUser;
	}

	// Returns a single Orphanage where the Id is matched with the name and
	// state

	public static Orphanage getOrphanageId(String name, String state) {
		Orphanage orphanage = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			@SuppressWarnings("deprecation")
			Criteria criteria = ses.createCriteria(Orphanage.class);
			criteria.add(Restrictions.and(Restrictions.eq("name", name), Restrictions.eq("address.state", state)));
			orphanage = (Orphanage) criteria.uniqueResult();
			System.out.println(orphanage);
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orphanage;
	}

	// Returns a list of Orphanages where the id is found in orp reg mapped
	// table

	@SuppressWarnings("unchecked")
	public static List<Orphanage> getSubscribedOrphanage(Long id) {
		List<Orphanage> orp = null;
		try {
			System.out.println(id);
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery(
					"from Orphanage where id in (select orpId from RegularUserOrphanages where regId = :regularID)");
			query.setParameter("regularID", id);
			orp = query.getResultList();
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orp;
	}

	// Retrun Orphanages not in orp reg subscribed table
	@SuppressWarnings("unchecked")
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

	// Retrun Orphanages in orp reg subscribed table

	@SuppressWarnings("unchecked")
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

	// -----------------------------------GET ORPHANAGE BY NAME
	// -------------------------------------
	@SuppressWarnings("deprecation")
	public static Orphanage getOrphanageDetailsById(Long id) {
		Orphanage orpUser = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Criteria criteria = ses.createCriteria(Orphanage.class);
			criteria.add(Restrictions.eq("id", id));
			orpUser = (Orphanage) criteria.uniqueResult();
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			orpUser = null;
			e.printStackTrace();
		}
		// System.out.println(orpUser);
		return orpUser;
	}

}
