package com.Over2Cloud.Rnd;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.common.HisHibernateSessionFactory;

public class DBConTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List data=null;
		Session session=null;
		try
		{
			session=HisHibernateSessionFactory.getSession();
			System.out.println("Hello session is as "+session);
			Query qry=session.createSQLQuery("select count(*) from patients;");
			data=qry.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				session.close();
			}
			catch (Exception e) {
				
			}
		}
		
		if(data!=null && data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				System.out.println(">>>>>>>>>>>>>>>>>>>"+object);
			}
		}
	}

}
