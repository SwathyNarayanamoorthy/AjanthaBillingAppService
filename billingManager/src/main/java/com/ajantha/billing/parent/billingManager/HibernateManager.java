package com.ajantha.billing.parent.billingManager;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateManager
{
	private static SessionFactory sessionFactory;

	public HibernateManager() {
		//Suppress
	}

	public HibernateManager(HttpServletRequest request) {

	}

	@SuppressWarnings("deprecation")
	public void init(Properties properties) throws Exception {
		try {
			String password = (String) properties.get("hibernate.connection.password");
			String userName = (String) properties.get("hibernate.connection.username");
			if (userName != null && userName.length() == 0) {
				throw new Exception("DB UserName is empty");
			}
			if (password != null && password.length() == 0) {
				throw new Exception("Incorrect DB Password");
			}
			String osName = System.getProperty("os.name").toLowerCase();
			//properties.setProperty(TenantPortalConstants.TenantInfoConstants.PASSWORD, password);
			// Create configuration
			Configuration configuration = null;
			if(osName.startsWith("win")){
				System.out.println("OS Name "+osName);
				configuration = new Configuration().setProperties(getDbConnectionProperties(properties)).configure("com\\ajantha\\billing\\configuration\\hibernate.cfg.xml");
			}
			else{
				System.out.println("OS Name "+osName);
				configuration = new Configuration().setProperties(getDbConnectionProperties(properties)).configure("com/ajantha/billing/configuration/hibernate.cfg.xml");
			}
			configuration.buildMappings();

			// Create service registry
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();

			// Build session factory
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			
			// initialize shutdownHook thread to release session resources when JVM down

		} catch (Exception e) {
			System.err.println("Error Occrued while Initialising Hibernate"+ e);
		}
	}

	/**
	 * method to get the database connection properties.
	 * 
	 * @return
	 * @throws Exception
	 */
	private Properties getDbConnectionProperties(Properties properties) throws Exception {
		Properties dBProperties = new Properties();
		/*dBProperties.put(TenantInfoConstants.DRIVER_CLASS, properties.getProperty(TenantInfoConstants.DRIVER_CLASS));
		dBProperties.put(TenantInfoConstants.CONNECTION_URL, properties.getProperty(TenantInfoConstants.CONNECTION_URL));
		dBProperties.put(TenantInfoConstants.USERNAME, properties.getProperty(TenantInfoConstants.USERNAME));
		dBProperties.put(TenantInfoConstants.PASSWORD, properties.getProperty(TenantInfoConstants.PASSWORD));*/
		return dBProperties;
	}

	/**
	 * method to open hibernate session
	 * 
	 * @return
	 */
	public Session initSession() {
		return sessionFactory.openSession();
	}

	/**
	 * method to begin hibernate transaction
	 * 
	 * @return
	 */
	public Session initTransaction() {
		Session session = initSession();
		session.beginTransaction();
		return session;
	}

	/**
	 * method to commit hibernate transaction
	 * 
	 * @param session
	 */
	public void commitTransaction(Session session) {
		if (session != null) {
			session.getTransaction().commit();
		}
	}

	/**
	 * method to rollback hibernate transaction
	 * 
	 * @param session
	 */
	public void rollBackTransaction(Session session) {
		if (session != null) {
			session.getTransaction().rollback();
		}
	}

	/**
	 * method to close hibernate session
	 * 
	 * @param session
	 */
	public void closeSession(Session session) {
		if (session != null && session.isOpen()) {
			try {
				session.close();
			} catch (Exception e) {
				System.err.println("Error occurred while closing session"+e);
			}
		}
	}
}