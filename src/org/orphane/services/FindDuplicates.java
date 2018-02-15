package org.orphane.services;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.orphane.model.Address;
import org.orphane.model.Events;
import org.orphane.model.NotifyUsers;
import org.orphane.model.Orphanage;
import org.orphane.model.RegularUsers;
import org.orphane.util.HBUtil;

public class FindDuplicates {

	// ------------------------------------------FORM VALIDATION DUPLICATE
	// CHECKING---------------------------
	public static String orphanage(Long phoneNo, String website, Address address, Long altNum) {
		String status = null;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from Orphanage");
			@SuppressWarnings("unchecked")
			List<Orphanage> results = query.getResultList();
			if (results != null) {
				// System.out.println("entered");
				for (Orphanage res : results) {
					System.out.println(res.getPhoneNumber() + " " + phoneNo);
					if (res.getPhoneNumber().equals(phoneNo) || (altNum != 0 && res.getPhoneNumber().equals(altNum))) {
						System.out.println("Phone Number found");
						status = "Phone Number found";
						break;
					} else if (res.getAltPhoneNumber() != null) {
						if (res.getAltPhoneNumber().equals(phoneNo)
								|| (altNum != 0 && res.getAltPhoneNumber().equals(altNum))) {
							System.out.println("Alternate Phone Number found");
							status = "Phone Number found";
							break;
						}
					}
					if (website.isEmpty() == false && website != null) {
						if (res.getWebsite().equals(website)) {
							status = "Website found";
							break;
						}
					}
					/*
					 * System.out.println(res.getAddress().address.equals((
					 * address.getAddress())));
					 * System.out.println(res.getAddress().getCity().equals(
					 * address.getCity()));
					 * System.out.println(res.getAddress().getState().equals(
					 * address.getState()));
					 * System.out.println(res.getAddress().getPincode().equals(
					 * address.getPincode()));
					 */
					if (res.getAddress().address.equals((address.getAddress()))
							&& res.getAddress().getCity().equals(address.getCity())
							&& res.getAddress().getPincode().equals(address.getPincode())
							&& res.getAddress().getState().equals(address.getState())) {
						status = "Address found";
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("returning : " + status);
		return status;
	}

	// ------------------------------------------FORM VALIDATION DUPLICATE
	// CHECKING---------------------------

	public static String RegularUsers(RegularUsers reg) {
		String status = null;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from RegularUsers");
			@SuppressWarnings("unchecked")
			List<RegularUsers> results = query.getResultList();
			if (results != null) {
				for (RegularUsers each : results) {
					if (each.getPhoneNumber().equals(reg.phoneNumber) || (reg.getAltPhoneNumber() != null
							&& each.getPhoneNumber().equals(reg.getAltPhoneNumber()))) {
						status = "Phone Number Taken";
					} else if (reg.getAltPhoneNumber() != null) {
						if (each.getAltPhoneNumber().equals(reg.getAltPhoneNumber())
								|| each.getAltPhoneNumber().equals(reg.getPhoneNumber())) {
							status = "Phone Number Taken";
						}
					}
					// System.out.println(each.getAddress().address + " " +
					// reg.getAddress().address);
					// System.out.println(each.getAddress().getCity() + " " +
					// reg.getAddress().getCity());
					// System.out.println(each.getAddress().getState() + " " +
					// reg.getAddress().getState());
					// System.out.println(each.getAddress().getPincode() + " " +
					// reg.getAddress().getPincode());
					// System.out.println(each.getAddress().address.equals((reg.getAddress().address)));
					// System.out.println(each.getAddress().getCity().equals(reg.getAddress().getCity()));
					// System.out.println(each.getAddress().getState().equals(reg.getAddress().getState()));
					// System.out.println(each.getAddress().getPincode().equals(reg.getAddress().getPincode()));

					if (each.getAddress().address.equals(reg.getAddress().address)
							&& each.getAddress().getCity().equals(reg.getAddress().getCity())
							&& each.getAddress().getPincode().equals(reg.getAddress().getPincode())
							&& each.getAddress().getState().equals(reg.getAddress().getState())) {
						status = "Address Already Taken";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(status);
		return status;
	}

	public static boolean notifyUsers(String mail) {
		boolean status = true;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			NotifyUsers obj = ses.get(NotifyUsers.class, mail);
			if (obj != null) {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	/*--------------------------UPDATE USER PROFILE DUPLICATE FINDING-------------------*/

	public static String inRegularUsers(RegularUsers reg, String email) {
		String status = null;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from RegularUsers WHERE email_id != :mail");
			query.setParameter("mail", email);
			@SuppressWarnings("unchecked")
			List<RegularUsers> results = query.getResultList();
			System.out.println(results);
			if (results != null) {
				for (RegularUsers each : results) {
					if (each.getPhoneNumber().equals(reg.phoneNumber) || (reg.getAltPhoneNumber() != null
							&& each.getPhoneNumber().equals(reg.getAltPhoneNumber()))) {
						System.out.println(each.getPhoneNumber() + " " + reg.phoneNumber);
						status = "numberTaken";
						ses.close();
						return status;
					} else if (reg.getAltPhoneNumber() != null && each.getAltPhoneNumber() != null) {
						if (each.getAltPhoneNumber().equals(reg.getAltPhoneNumber())
								|| each.getAltPhoneNumber().equals(reg.getPhoneNumber())) {
							System.out.println(each.getAltPhoneNumber() + " " + reg.altPhoneNumber);
							status = "altNumberTaken";
							ses.close();
							return status;
						}
					}
					// System.out.println(each.getAddress().address + " " +
					// reg.getAddress().address);
					// System.out.println(each.getAddress().getCity() + " " +
					// reg.getAddress().getCity());
					// System.out.println(each.getAddress().getState() + " " +
					// reg.getAddress().getState());
					// System.out.println(each.getAddress().getPincode() + " " +
					// reg.getAddress().getPincode());
					// System.out.println(each.getAddress().address.equals((reg.getAddress().address)));
					// System.out.println(each.getAddress().getCity().equals(reg.getAddress().getCity()));
					// System.out.println(each.getAddress().getState().equals(reg.getAddress().getState()));
					// System.out.println(each.getAddress().getPincode().equals(reg.getAddress().getPincode()));

					if (each.getAddress().address.equals(reg.getAddress().address)
							&& each.getAddress().getCity().equals(reg.getAddress().getCity())
							&& each.getAddress().getPincode().equals(reg.getAddress().getPincode())
							&& each.getAddress().getState().equals(reg.getAddress().getState())) {
						System.out.println(each.getAddress().getAddress() + " " + reg.getAddress().address);
						status = "AddressTaken";
						ses.close();
						return status;
					}
				}
				status = "success";
				ses.close();
			}
		} catch (Exception e) {
			status = "failure";
			e.printStackTrace();
		}
		System.out.println(status);
		return status;
	}

	/*--------------------------UPDATE ORPHANAGE PROFILE DUPLICATE FINDING-------------------*/

	public static String inOrphanage(Orphanage orp, String email) {
		String status = null;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from Orphanage WHERE email_id != :mail");
			query.setParameter("mail", email);
			@SuppressWarnings("unchecked")
			List<Orphanage> results = query.getResultList();
			System.out.println(results);
			if (results != null) {
				for (Orphanage each : results) {
					if (each.getPhoneNumber().equals(orp.phoneNumber) || (orp.getAltPhoneNumber() != null
							&& each.getPhoneNumber().equals(orp.getAltPhoneNumber()))) {
						System.out.println(each.getPhoneNumber() + " " + orp.phoneNumber);
						status = "numberTaken";
						ses.close();
						return status;
					} else if (orp.getAltPhoneNumber() != null && each.getAltPhoneNumber() != null) {
						if (each.getAltPhoneNumber().equals(orp.getAltPhoneNumber())
								|| each.getAltPhoneNumber().equals(orp.getPhoneNumber())) {
							System.out.println(each.getAltPhoneNumber() + " " + orp.altPhoneNumber);
							status = "altNumberTaken";
							ses.close();
							return status;
						}
					}

					else if (orp.getWebsite() != null && each.getWebsite() != null) {
						if (each.getWebsite().equals(orp.getWebsite())) {
							status = "websiteTaken";
							ses.close();
							return status;
						}
					}
					// System.out.println(each.getAddress().address + " " +
					// reg.getAddress().address);
					// System.out.println(each.getAddress().getCity() + " " +
					// reg.getAddress().getCity());
					// System.out.println(each.getAddress().getState() + " " +
					// reg.getAddress().getState());
					// System.out.println(each.getAddress().getPincode() + " " +
					// reg.getAddress().getPincode());
					// System.out.println(each.getAddress().address.equals((reg.getAddress().address)));
					// System.out.println(each.getAddress().getCity().equals(reg.getAddress().getCity()));
					// System.out.println(each.getAddress().getState().equals(reg.getAddress().getState()));
					// System.out.println(each.getAddress().getPincode().equals(reg.getAddress().getPincode()));

					if (each.getAddress().address.equals(orp.getAddress().address)
							&& each.getAddress().getCity().equals(orp.getAddress().getCity())
							&& each.getAddress().getPincode().equals(orp.getAddress().getPincode())
							&& each.getAddress().getState().equals(orp.getAddress().getState())) {
						System.out.println(each.getAddress().getAddress() + " " + orp.getAddress().address);
						status = "AddressTaken";
						ses.close();
						return status;
					}
				}
				status = "success";
				ses.close();
			}
		} catch (Exception e) {
			status = "failure";
			e.printStackTrace();
		}
		System.out.println(status);
		return status;
	}

	// -------------------FIND EVENT DUPLICATES----------------------------

	public static boolean checkEventFound(Events event) {
		boolean status = false;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			/*
			 * Criteria criteria = ses.createCriteria(Events.class);
			 * criteria.add(Restrictions.and(Restrictions.eq("eventDate",
			 * event.getEventDate()), Restrictions.eq("eventName",
			 * event.getEventName())));
			 */
			// criteria.add(Restrictions.eq("orphanage", event.getOrphanage()));
			Query query = ses.createQuery(
					"from Events where eventDate = :eventDate and eventName = :eventName and orphanage.id = :id and eventStatus = 'BOOKED'");
			query.setParameter("eventName", event.getEventName());
			query.setParameter("eventDate", event.getEventDate());
			query.setParameter("id", event.getOrphanage().getId());
			@SuppressWarnings("unchecked")
			List<Events> result = (List<Events>) query.getResultList();
			// System.out.println(result + "\n\n\n\n");
			// System.out.println(result.size());
			if (result == null || result.size() == 0) {
				status = true;
			}
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
}
