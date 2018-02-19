package org.orphane.services;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.orphane.model.Orphanage;
import org.orphane.model.OrphanagePost;
import org.orphane.model.RegularUserOrphanages;
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
					"select o.id,o.name,o.address.state,o.phoneNumber from Orphanage o where address.state = :state_name and credential.status = 'ACTIVATED' and id not in (select orpId from RegularUserOrphanages)");
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
					"select o.id,o.name,o.address.state,o.phoneNumber from Orphanage o where address.state = :state_name and credential.status = 'ACTIVATED' and id in (select orpId from RegularUserOrphanages where regId = :regularId)");
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

	/*--------------------------------------------TO GET ORPHANAGE NAME BY ITS ID--------------------------------*/
	@SuppressWarnings("deprecation")
	public static String getOrphanageNameById(Long orpId) {
		String orpName = null;
		Orphanage orp = null;
		try {
			System.out.println(orpId);
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Criteria criteria = ses.createCriteria(Orphanage.class);
			criteria.add(Restrictions.eq("id", orpId));
			orp = (Orphanage) criteria.uniqueResult();
			orpName = orp.getName();
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orpName;
	}

	/*--------------------------------------------TO SHOW NOTIFICATION--------------------------------*/

	@SuppressWarnings("unchecked")
	public static String showNotifcations(String email) {
		String notifications = "";
		Long userId = null;
		Long orpId = null;
		String orpName = null;
		System.out.println(email);
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			RegularUsers user = FetchContent.getRegUserDetails(email);
			// System.out.println(user);
			userId = user.getId();
			// System.out.println(userId);
			Query query = ses.createQuery("FROM RegularUserOrphanages WHERE regId =:id");
			query.setParameter("id", userId);
			List<RegularUserOrphanages> results = query.getResultList();
			// System.out.println(results);
			if (results == null || results.size() == 0) {
				System.out.println("not orp");
				notifications = "NO ORPHANAGES ADDED YET";
			} else {
				Query query1 = ses.createQuery(
						"FROM OrphanagePost WHERE orpId in (SELECT orpId FROM RegularUserOrphanages where regId =:userid) ORDER BY postDate desc");
				query1.setParameter("userid", userId);
				List<OrphanagePost> orpPost = query1.getResultList();
				// System.out.println(orpPost);
				if (orpPost == null || orpPost.size() == 0) {
					notifications = "NO NOTIFICATIONS";
					System.out.println(notifications);
				} else {
					for (OrphanagePost each : orpPost) {
						orpId = each.getOrpId();
						orpName = FetchContent.getOrphanageNameById(orpId);
						notifications += "{\"Name\":\"" + orpName + "\",\"Post\":\"" + each.getPost() + "\",\"Date\":\""
								+ each.getPostDate() + "\",\"Time\":\"" + each.getPostTime() + "\"},";
					}
					notifications = "[" + (notifications.substring(0, notifications.length() - 1)) + "]";

				}

			}
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notifications;
	}
}
