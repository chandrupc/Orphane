package org.orphane.services;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.orphane.model.Credential;
import org.orphane.modelenum.UserStatus;
import org.orphane.modelenum.UserType;
import org.orphane.util.AUTHIDGen;
import org.orphane.util.HBUtil;

public class CredentialService {

	public static boolean updatePassword(String email, String oldPassword, String newPassword) {
		boolean status = false;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Credential user = ses.get(Credential.class, email);
			if (user != null) {
				user.setPassword(newPassword);
				ses.update(user);
				ses.getTransaction().commit();
				ses.close();
				status = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public static boolean updateUser(Credential credential) {
		boolean status = false;
		try {
			// System.out.println(credential);
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			ses.update(credential);
			ses.getTransaction().commit();
			ses.close();
			status = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public static boolean checkUserAvailability(String email) {
		boolean status = false;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Credential user = ses.get(Credential.class, email);
			if (user == null) {
				status = true;
			}
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public static boolean addNewCredential(String email, String password, String type, String authKey) {
		boolean status = false;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Credential user = new Credential();
			user.setEmail(email);
			user.setPassword(password);
			user.setAuthKey(authKey);
			if (type.equalsIgnoreCase("ORPHANAGE")) {
				user.setUserType(UserType.ORPHANAGE);
			} else if (type.equalsIgnoreCase("REGULAR")) {
				user.setUserType(UserType.REGULAR);
			}
			user.setStatus(UserStatus.NOT_ACTIVATED);
			ses.save(user);
			ses.getTransaction().commit();
			ses.close();
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public static boolean activateUser(String email, String authKey) {
		boolean status = false;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Credential user = ses.get(Credential.class, email);
			// System.out.println(user);
			if (user != null) {
				if (email.equals(user.getEmail()) && authKey.equals(user.getAuthKey())) {
					// System.out.println("user account activated");
					user.setStatus(UserStatus.ACTIVATED);
					user.setAuthKey(AUTHIDGen.generateKey(30));
					ses.update(user);
					ses.getTransaction().commit();
					ses.close();
					status = true;
				}
			}
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		return status;
	}

	public static void requestReset(String email) {
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Credential user = ses.get(Credential.class, email);
			if (user != null) {
				String key = AUTHIDGen.generateKey(30);
				user.setAuthKey(key);
				user.setPassword(key);
				ses.update(user);
			}
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static boolean resetPassword(String email, String newPassword,
	 * String authKey) { boolean status = false; try { SessionFactory sf =
	 * HBUtil.getSessionFactory(); Session ses = sf.openSession();
	 * ses.beginTransaction(); Credential user = ses.get(Credential.class,
	 * email); if (user != null && user.getAuthKey().equals(authKey)) {
	 * user.setPassword(newPassword);
	 * user.setAuthKey(AUTHIDGen.generateKey(30));
	 * user.setStatus(UserStatus.ACTIVATED); ses.update(user);
	 * ses.getTransaction().commit(); ses.close(); status = true; } } catch
	 * (Exception e) { e.printStackTrace(); } return status; }
	 */

	public static Credential getUser(String email) {
		Credential user = null;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			user = ses.get(Credential.class, email);
			if (user != null) {
				ses.getTransaction().commit();
				ses.close();
				return user;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/*---------------------------------DELETE ACCOUNT(SET AS NOT ACTIVATED)-------------------------*/

	public static boolean deleteAccount(String email) {
		boolean status = false;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			@SuppressWarnings("deprecation")
			Criteria criteria = ses.createCriteria(Credential.class);
			criteria.add(Restrictions.eq("email", email));
			Credential credential = (Credential) criteria.uniqueResult();
			System.out.println(credential);
			if (credential != null) {
				credential.setStatus(UserStatus.NOT_ACTIVATED);
				ses.update(credential);
				status = true;
			}
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}