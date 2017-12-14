package org.orphane.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HBUtil {

	public static SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable e) {
			System.out.println("Initial Session Factory created failed");
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
