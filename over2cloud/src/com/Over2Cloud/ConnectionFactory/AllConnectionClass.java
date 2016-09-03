package com.Over2Cloud.ConnectionFactory;

import hibernate.common.HibernateSessionFactory;


import java.util.List;

import javax.transaction.Synchronization;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
public class AllConnectionClass implements AllConnectionEntry{
   private String connectionName;
   private String dbbname;
   private String ipAddress;
   private String username;
   private String paassword;
   private String port;
 public AllConnectionClass(String connectionName){
	   this.connectionName=connectionName;
   }
   public AllConnectionClass(String dbbname,String ipAddress,String username,String paassword,String port){
	   
	   this.dbbname=dbbname;
	   this.ipAddress=ipAddress;
	   this.username=username;
	   this.paassword=paassword;
	   this.port=port;
   }
   @Override
   public SessionFactory getSession()throws HibernateException {
	    Session session = null;
	    SessionFactory sessionFactory=null;
	    
	    try 
		{
	    	System.out.println("connectionName  ::: "+connectionName);
	    	if(connectionName!=null && connectionName.equals("localhost"))
	        {
	    		sessionFactory = HibernateSessionFactory.getSessionFactory();
	        }
	    	else
	    	{
	    		
	    		sessionFactory=DBDynamicConnection.getSession(dbbname,ipAddress,username,paassword,port);
	    	}
		}
	    catch (Exception e) {e.printStackTrace();} 
	    return sessionFactory;
	}

	
}
