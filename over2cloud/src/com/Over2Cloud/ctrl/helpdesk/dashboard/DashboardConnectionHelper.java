package com.Over2Cloud.ctrl.helpdesk.dashboard;

import org.hibernate.SessionFactory;

import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;

public class DashboardConnectionHelper {
	   private static SessionFactory hsession = null;
    
	   protected DashboardConnectionHelper(){
		   AllConnection Conn=new ConnectionFactory("4_clouddb","192.168.1.118","dreamsolSlave","dreamsolSlave","3306");
		   AllConnectionEntry Ob1=Conn.GetAllCollectionwithArg();
		   hsession=Ob1.getSession();
	   }
	
	   public SessionFactory getInstance()
	   {
	      if(hsession == null) {
	    	  AllConnection Conn=new ConnectionFactory("4_clouddb","192.168.1.118","dreamsolSlave","dreamsolSlave","3306");
			   AllConnectionEntry Ob1=Conn.GetAllCollectionwithArg();
			   hsession=Ob1.getSession();
	      }
	      return hsession;
	   }
	   


}
