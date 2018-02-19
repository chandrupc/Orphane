package org.orphane.services;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.orphane.model.Orphanage;
import org.orphane.model.OrphanagePost;
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

	public static String viewPost(String email) {
		String post = "";
		long id = 0;
		try {
			SessionFactory sf = HBUtil.getSessionFactory();
			Session ses = sf.openSession();
			ses.beginTransaction();
			Orphanage orp = FetchContent.getOrphanageDetails(email);
			id = orp.getId();
			Query query = ses.createQuery("FROM OrphanagePost WHERE orpId=:id ORDER BY postDate desc");
			query.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<OrphanagePost> results = query.getResultList();
			if (results == null || results.size() == 0) {
				post = "NO POSTS";
			} else {
				for (OrphanagePost each : results) {
					post += "{\"postId\":\"" + each.getPostId() + "\",\"post\":\"" + each.getPost()
							+ "\",\"postDate\":\"" + each.getPostDate()  + "\",\"postTime\":\"" + each.getPostTime() + "\"},";
				}
				post = "[" + (post.substring(0, post.length() - 1)) + "]";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return post;
	}
}
