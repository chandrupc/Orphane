package org.orphane.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.orphane.model.Orphanage;
import org.orphane.util.HBUtil;

@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
public class FindDuplicates {

	public static boolean Orphanage(Long phoneNo) {
		boolean status = true;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Query query = ses.createQuery("from Orphanage");
			List<Orphanage> results = query.getResultList();
			if (results != null) {
				System.out.println("entered");
				for (Orphanage res : results) {
					System.out.println(res.getPhoneNumber() + " " + phoneNo);
					if (res.getPhoneNumber().equals(phoneNo)) {
						System.out.println("Phone Number found");
						status = false;
						break;
					} else if (res.getAltPhoneNumber().equals(phoneNo)) {
						System.out.println("Alternate Phone Number found");
						status = false;
						break;
					}
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		System.out.println("returning : " + status);
		return status;
	}
}
