package org.orphane.services;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.orphane.model.Events;
import org.orphane.model.Orphanage;
import org.orphane.model.RegularUserOrphanages;
import org.orphane.model.RegularUsers;
import org.orphane.modelenum.EventStatus;
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
			Query query = ses.createQuery("from RegularUserOrphanages where regId =  :regId)");
			query.setParameter("regId", reg.getId());
			@SuppressWarnings("unchecked")
			List<RegularUserOrphanages> temp = query.getResultList();
			if (temp != null && temp.size() != 0) {
				for (RegularUserOrphanages each : temp) {
					ses.delete(each);
				}
			}
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
			Orphanage orp = (Orphanage) criteria.uniqueResult();
			Query query = ses.createQuery("from RegularUserOrphanages where orpId =  :orpId)");
			query.setParameter("orpId", orp.getId());
			@SuppressWarnings("unchecked")
			List<RegularUserOrphanages> temp = query.getResultList();
			if (temp != null && temp.size() != 0) {
				for (RegularUserOrphanages each : temp) {
					ses.delete(each);
				}
			}
			ses.delete(orp);
			ses.getTransaction().commit();
			ses.close();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public static boolean deleteEvent(Long id) {
		boolean status = false;
		try {
			Session ses = HBUtil.getSessionFactory().openSession();
			ses.beginTransaction();
			@SuppressWarnings("deprecation")
			Criteria criteria = ses.createCriteria(Events.class);
			criteria.add(Restrictions.eq("eventId", id));
			Events event = (Events) criteria.uniqueResult();
			event.setEventStatus(EventStatus.CANCELLED);
			ses.update(event);
			ses.getTransaction().commit();
			ses.close();
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}