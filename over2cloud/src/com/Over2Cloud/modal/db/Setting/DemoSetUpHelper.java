package com.Over2Cloud.modal.db.Setting;

import hibernate.common.HibernateSessionFactory;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.SQLGrammarException;

public class DemoSetUpHelper {

	public static List getUnConfiguredDemoAccount()
	{
		List data=null;
		Session session = null;  
		Transaction transaction = null;  
		try {  
			    SessionFactory connection=HibernateSessionFactory.getSessionFactory();
	            session=connection.openSession();
				transaction = session.beginTransaction();  
				data=session.createSQLQuery("select id,org_reg_name,org_name,contact_emailid,country from registation_sinup where demoAccount='1' and accountType='D'").list();  
				transaction.commit(); 
			}
		catch (SQLGrammarException ex) {
			//ex.printStackTrace();
			transaction.rollback();
			} 
		catch (Exception ex) {
				ex.printStackTrace();
				transaction.rollback();
				} 
		return data;
	}
	
	public static List getDemoIdDetails(int id)
	{
		List data=null;
		Session session = null;  
		Transaction transaction = null;  
		try {  
			    SessionFactory connection=HibernateSessionFactory.getSessionFactory();
	            session=connection.openSession();
				transaction = session.beginTransaction();  
				data=session.createSQLQuery("select org_name,city,country,pincode,contactNo,contact_emailid from registation_sinup where id="+id).list();  
				transaction.commit(); 
			}
		catch (SQLGrammarException ex) {
			//ex.printStackTrace();
			transaction.rollback();
			} 
		catch (Exception ex) {
				ex.printStackTrace();
				transaction.rollback();
				} 
		return data;
	}
	
}
