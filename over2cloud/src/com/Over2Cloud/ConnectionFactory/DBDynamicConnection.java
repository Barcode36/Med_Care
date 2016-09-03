package com.Over2Cloud.ConnectionFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
/*
 * @Author: Name
 * @Date: dd-mm-yy HH:MM
 * 
 * 
 */
public class DBDynamicConnection {
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
		    .setProperty("hibernate.show_sql", "true")
	         
	        .setProperty("hibernate.connection.provider_class","org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider")
	        .setProperty("hibernate.c3p0.min_size","0")
	        .setProperty("hibernate.c3p0.max_size","10000")
	        .setProperty("hibernate.c3p0.timeout","60")
	        .setProperty("hibernate.c3p0.max_statements","50")
	        .setProperty("hibernate.c3p0.idle_test_period","120");

			serviceRegistry = new ServiceRegistryBuilder().applySettings(
					cfg.getProperties()).buildServiceRegistry();
			sessionFactory = cfg.buildSessionFactory(serviceRegistry);

		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

	}
		public static Session getSession() throws HibernateException {
			Session session = (Session) threadLocal.get();

			if (session == null || !session.isOpen()) {
				if (sessionFactory == null) {
					rebuildSessionFactory();
				}
				session = (sessionFactory != null) ? sessionFactory.openSession(): null;
				threadLocals.set(session);
			}

			return session;
		}
		public static SessionFactory getSessionFactory(){
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			else{
			threadLocal.set(sessionFactory);
			}
		return sessionFactory;
	}
		/**
		 * Rebuild hibernate session factory
		 * 
		 */
		public static void rebuildSessionFactory() {
			try {
				
				serviceRegistry = new ServiceRegistryBuilder().applySettings(
						cfg.getProperties()).buildServiceRegistry();
				sessionFactory = cfg.buildSessionFactory(serviceRegistry);
				

			} catch (Throwable ex) {
				System.err.println("Failed to create sessionFactory object." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
	public static SessionFactory getSession(String dbbname,String localhost,String username,String paassword,String port) throws HibernateException {try{
		
		
		 cfg.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver")
       .setProperty("hibernate.connection.url","jdbc:mysql://"+localhost+":"+port+"/"+dbbname)
       .setProperty("hibernate.connection.username",username)
       .setProperty("hibernate.connection.password",paassword)
       .setProperty("hibernate.dialect","org.hibernate.dialect.MySQLInnoDBDialect")
       .setProperty("hibernate.current_session_context_class","thread")
       .setProperty("hibernate.connection.release_mode","auto")
       .setProperty("hibernate.transaction.auto_close_session","true")
       
       .setProperty("hibernate.generate_statistics","true")
       .setProperty("hibernate.hbm2ddl.auto","update")
	    .setProperty("hibernate.format_sql", "false")
	    .setProperty("hibernate.show_sql", "true")
       .setProperty("hibernate.connection.characterEncoding","UTF-8")
        
       .setProperty("hibernate.connection.provider_class","org.hibernate.service.jdbc.connections.internal.C3P0ConnectionProvider")
       .setProperty("hibernate.c3p0.min_size","0")
       .setProperty("hibernate.c3p0.max_size","10000")
       .setProperty("hibernate.c3p0.timeout","20")
       .setProperty("hibernate.c3p0.max_statements","250")
       .setProperty("hibernate.c3p0.idle_test_period","30")
       .setProperty("hibernate.c3p0.acquireRetryAttempts","30")
       .setProperty("hibernate.c3p0.acquireIncrement","3")
		 

		 .setProperty("hibernate.cache.use_second_level_cache","true")
				       .setProperty("hibernate.cache.use_structured_entries","true")
		 .setProperty("hibernate.cache.provider_class ","org.hibernate.cache.EhCacheProvider");
       
      
    
		 serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		 sessionFactory = cfg.buildSessionFactory(serviceRegistry);

		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return sessionFactory;
	}
	private DBDynamicConnection() {
	}
	
}