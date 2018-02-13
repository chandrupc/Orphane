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
	public static RegularUsers getRegUserDetails(String mail) {
		RegularUsers regUser = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from RegularUsers where email_id = :mail");
			query.setParameter("mail", mail);
			regUser = (RegularUsers) query.getSingleResult();
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

	public static Orphanage getOrphanageDetails(String mail) {
		Orphanage orpUser = null;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from Orphanage where email_id = :mail");
			query.setParameter("mail", mail);
			orpUser = (Orphanage) query.getSingleResult();
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

	@SuppressWarnings("unchecked")
	public static List<Orphanage> getOrphanageById(Long id) {
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
}
