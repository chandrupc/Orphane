package org.orphane.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.orphane.model.Address;
import org.orphane.model.Orphanage;
import org.orphane.model.RegularUsers;
import org.orphane.util.HBUtil;

@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class FindDuplicates {

	public static String orphanage(Long phoneNo, String website, Address address, Long altNum) {
		String status = null;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from Orphanage");
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

	public static String RegularUsers(RegularUsers reg) {
		String status = null;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from RegularUsers");
			List<RegularUsers> results = query.getResultList();
			if (results != null) {
				for (RegularUsers each : results) {
					if (each.getPhoneNumber().equals(reg.phoneNumber) || (reg.getAltPhoneNumber() != null
							&& each.getPhoneNumber().equals(reg.getAltPhoneNumber()))) {
						status = "Phone Number Found";
					} else if (reg.getAltPhoneNumber() != null) {
						if (each.getAltPhoneNumber().equals(reg.getAltPhoneNumber())
								|| each.getAltPhoneNumber().equals(reg.getPhoneNumber())) {
							status = "Phone Number Found";
						}
					}

					/*
					 * System.out.println(each.getAddress().address.equals((reg.
					 * getAddress().address)));
					 * System.out.println(each.getAddress().getCity().equals(reg
					 * .getAddress().getCity()));
					 * System.out.println(each.getAddress().getState().equals(
					 * reg.getAddress().getState()));
					 * System.out.println(each.getAddress().getPincode().equals(
					 * reg.getAddress().getPincode()));
					 */
					
					if (each.getAddress().address.equals(reg.getAddress().address)
							&& each.getAddress().getCity().equals(reg.getAddress().getCity())
							&& each.getAddress().getPincode().equals(reg.getAddress().getPincode())
							&& each.getAddress().getState().equals(reg.getAddress().getState())) {
						status = "Address Found";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
