package org.orphane.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.orphane.model.Orphanage;
import org.orphane.util.HBUtil;

public class OrphanageProfileSerive {
	public static boolean updateOrphanageProfile(Orphanage orp) {
		boolean status = true;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			ses.update(orp);
			ses.getTransaction().commit();
			ses.close();
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		return status;
	}
}
