package com.pms.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	public static SessionFactory pmsSessionFactory;

	static {
		try {
			if (pmsSessionFactory == null) {
				Configuration configuration = new Configuration();
				pmsSessionFactory = configuration.configure("hibernate.cfg.xml").buildSessionFactory();
			}
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return pmsSessionFactory;
	}
}
