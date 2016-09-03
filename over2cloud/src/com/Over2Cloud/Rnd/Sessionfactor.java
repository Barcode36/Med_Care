package com.Over2Cloud.Rnd;
import hibernate.common.HibernateSessionFactory;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class Sessionfactor 
{
	
	 public boolean connectionget() 
		{
		  boolean flage=true;
	       Session session = null;
	    
			try 
			{
				session = HibernateSessionFactory.getSession();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.flush();
				session.close();
			 }
			return flage;
		}

	 public List OutletSwapUserView(String Username)
		{
			   List  empuser = null;
		       Session session = null;
				try 
				{
					session = HibernateSessionFactory.getSession();
					String emplistidQuery="select * from emp_basic";
					Query query =session.createSQLQuery(emplistidQuery);
					empuser=query.list();
					System.out.println(empuser);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					session.flush();
					session.close();
				 }
				return empuser;
		    }
	 
	 public static void main(String[] args) 
	 {
		 List SwapoutletList=new Sessionfactor().OutletSwapUserView("'nisha'");
			if(SwapoutletList != null && SwapoutletList.size() > 0)
			{
				for (Iterator iterator = SwapoutletList.iterator(); iterator.hasNext();){
			    	Object[] objectCol = (Object[]) iterator.next();
			    	System.out.println("**************************");
			    
			    	}
				
			}
	 }
}
