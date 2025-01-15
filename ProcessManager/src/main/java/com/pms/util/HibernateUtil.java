//package com.pms.util;
//
//import java.io.IOException;
//import java.util.Properties;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//
//public class HibernateUtil {
//	public static SessionFactory pmsSessionFactory;
//	static {
//		try {
//			if (pmsSessionFactory == null) {
//				Configuration configuration = new Configuration();
//				Properties properties = new Properties();
//				try {
//					properties.load(HibernateUtil.class.getClassLoader().getResourceAsStream("db.properties"));
//				} catch (IOException e) {
//					System.out.println(e);
//				}
//				configuration.addProperties(properties);
//				pmsSessionFactory = configuration.configure("hibernate.cfg.xml").buildSessionFactory();
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
//}

package com.pms.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	public static SessionFactory pmsSessionFactory;

	static {
		try {
			System.out.println("Initializing HibernateUtil...");

			if (pmsSessionFactory == null) {
				System.out.println("SessionFactory is null, creating a new one...");

				// Create the Configuration instance and load hibernate.cfg.xml
				Configuration configuration = new Configuration();

				// Configure using hibernate.cfg.xml (no need to load db.properties separately)
				pmsSessionFactory = configuration.configure("hibernate.cfg.xml").buildSessionFactory();

				System.out.println("SessionFactory created successfully.");
			} else {
				System.out.println("SessionFactory already exists.");
			}
		} catch (Exception e) {
			System.out.println("Error during Hibernate initialization: " + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return pmsSessionFactory;
	}
}
