package com.Over2Cloud.ConnectionFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DBDynamicConnection4Servicefile {

	private static org.hibernate.SessionFactory sessionFactory;
	private static Configuration cfg = new Configuration();
	private static ServiceRegistry serviceRegistry;
	private static final ThreadLocal<SessionFactory> threadLocal = new ThreadLocal<SessionFactory>();
	private static final ThreadLocal<Session> threadLocals = new ThreadLocal<Session>();
	static {
		try {
			cfg.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver")
	        .setProperty("hibernate.connection.url","jdbc:mysql://"+"127.0.0.1"+":"+"3306"+"/"+"1_clouddb")
	        .setProperty("hibernate.connection.username","dreamsol")
	        .setProperty("hibernate.connection.password","dreamsol")
	        .setProperty("hibernate.dialect","org.hibernate.dialect.MySQLInnoDBDialect")
	        .setProperty("hibernate.current_session_context_class","thread")
	        .setProperty("hibernate.connection.release_mode","auto")
	        .setProperty("hibernate.transaction.auto_close_session","true")
	        
	        .setProperty("hibernate.generate_statistics","false")
	        .setProperty("hibernate.hbm2ddl.auto","update")
		    .setProperty("hibernate.format_sql", "false")
		    .setProperty("hibernate.show_sql", "false")
	         
	        .setProperty("hibernate.connection.provider_class","org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider")
	        .setProperty("hibernate.c3p0.min_size","0")
	        .setProperty("hibernate.c3p0.max_size","1000")
	        .setProperty("hibernate.c3p0.timeout","30")
	        .setProperty("hibernate.c3p0.idle_test_period","60");

			serviceRegistry = new ServiceRegistryBuilder().applySettings(
					cfg.getProperties()).buildServiceRegistry();
			sessionFactory = cfg.buildSessionFactory(serviceRegistry);

		} catch (Throwable ex) {
			System.err.println("Failed to Create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}
	public static SessionFactory getSessionFactory(){
			if (sessionFactory == null) {
				rebuildSessionFactory();}
			else{
			threadLocal.set(sessionFactory);}
		return sessionFactory;
	}
	/**
	 * Rebuild hibernate session factory
	 * 
	 */
	public static void rebuildSessionFactory() {
		try {serviceRegistry = new ServiceRegistryBuilder().applySettings(
				cfg.getProperties()).buildServiceRegistry();
		    sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			System.err.println("Failed to Create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	@SuppressWarnings("deprecation")
	public static SessionFactory getSessionFactory(String dbbname,String localhost,String username,String paassword,String port) throws HibernateException {
		try{
		     serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
				if (sessionFactory == null) {
					rebuildSessionFactory();
				  }
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return sessionFactory;
	}
	@SuppressWarnings("deprecation")
	public static Session getSession(String dbbname,String localhost,String username,String paassword,String port) throws HibernateException {
		Session session = (Session) threadLocals.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession()
					: null;
			threadLocals.set(session);
		}

		return session;
	}
	private DBDynamicConnection4Servicefile(){}
	/**
	 * return session factory
	 * 
	 */
	
	public static Configuration getCfg() {
		return cfg;
	}
	public static void setCfg(Configuration cfg) {
		DBDynamicConnection4Servicefile.cfg = cfg;
		sessionFactory = null;
	}
	
	public static void setSessionFactory(org.hibernate.SessionFactory sessionFactory)
	{
		DBDynamicConnection4Servicefile.sessionFactory = sessionFactory;
	}
	
	
	public static ThreadLocal<SessionFactory> getThreadLocal() {
		return threadLocal;
	}
	public static void main(String[] args) {
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+DBDynamicConnection.getSessionFactory().getCurrentSession().hashCode());
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+DBDynamicConnection.getSessionFactory().getCurrentSession().hashCode());
		// System.out.println(">>>>>>>>>>Interesting<<<<<<<<"+DBDynamicConnection.getSessionFactory("1_clouddb","127.0.0.1","dreamsol","dreamsol","3306").getCurrentSession().hashCode());
		System.out.println(">>>>>>>>>>Interesting<<<<<<<<"+DBDynamicConnection.getSession("1_clouddb","127.0.0.1","dreamsol","dreamsol","3306"));
	}


}
