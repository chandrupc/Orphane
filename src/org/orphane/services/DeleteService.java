package org.orphane.services;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.orphane.model.Orphanage;
import org.orphane.model.RegularUserOrphanages;
import org.orphane.model.RegularUsers;
import org.orphane.util.HBUtil;

public class DeleteService {

	@SuppressWarnings("deprecation")
	public static boolean deleteOrpRegUserMapTable(Long id) {
		boolean status = false;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Criteria criteria = ses.createCriteria(RegularUserOrphanages.class);
			criteria.add(Restrictions.eq("orpId", id));
			ses.delete(criteria.uniqueResult());
			ses.getTransaction().commit();
			ses.close();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@SuppressWarnings("deprecation")
	public static boolean deleteRegularUserProfile(String email) {
		boolean status = false;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Criteria criteria = ses.createCriteria(RegularUsers.class);
			criteria.add(Restrictions.eq("credential.email", email));
			RegularUsers reg = (RegularUsers) criteria.uniqueResult();
			Query query = ses.createQuery("delete from RegularUserOrphanages where regId = :regId");
			// System.out.println(reg.getId());
			query.setParameter("regId", reg.getId());
			// ses.delete();
			ses.delete(reg);
			ses.getTransaction().commit();
			ses.close();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@SuppressWarnings("deprecation")
	public static boolean deleteOrphanageProfile(String email) {
		boolean status = false;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Criteria criteria = ses.createCriteria(Orphanage.class);
			criteria.add(Restrictions.eq("credential.email", email));
			Orphanage reg = (Orphanage) criteria.uniqueResult();
			Criteria criteriaOne = ses.createCriteria(RegularUserOrphanages.class);
			criteriaOne.add(Restrictions.eq("orpId", reg.getId()));
			ses.delete(reg);
			@SuppressWarnings("unchecked")
			List<Orphanage> orp = criteriaOne.list();
			for (Orphanage each : orp) {
				ses.delete(each);
			}
			ses.close();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}