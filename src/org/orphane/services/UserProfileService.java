package org.orphane.services;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.orphane.model.RegularUsers;
import org.orphane.util.HBUtil;

public class UserProfileService {
	public static RegularUsers getUserProfile(String email) {
		RegularUsers user = null;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("FROM RegularUsers WHERE email_id = :mail");
			query.setParameter("mail", email);
			user = (RegularUsers) query.getSingleResult();
			if (user != null) {
				ses.close();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static boolean updateUserProfile(RegularUsers user) {
		boolean status = true;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			ses.update(user);
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		return status;
	}
}
