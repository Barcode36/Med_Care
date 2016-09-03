package com.Over2Cloud.CommonClasses;

import java.util.Map;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;

public class ValidateSession implements HttpSessionListener
{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	
	public static boolean checkSession()
	{
		boolean sessionFlag=false;
		Map session = ActionContext.getContext().getSession();
		if(session!=null)
		 {
		  try
			{
			  
				String userName=null;
				String clientId=null;
				if(session.containsKey("uName") && session.containsKey("accountid"))
				 {
					
					userName=(String)session.get("uName");
					clientId=(String)session.get("accountid");
					if(userName!=null && clientId!=null)
					 {sessionFlag=true;}
					else
					 {sessionFlag=false;}
				 }
			}
			catch (Exception e)
			{
				sessionFlag=false;
			}
		}
		return sessionFlag;
	}


	@Override
	public void sessionCreated(HttpSessionEvent arg0)
	{
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0)
	{
	
		System.out.println("ValidateSession.sessionDestroyed()");
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		 try {
		String userName= (String) arg0.getSession().getAttribute("uName");
	
	     String qry = " update useraccount set loginStatus='0' where userID='"+Cryptography.encrypt(userName ,DES_ENCRYPTION_KEY)+"' ";
				 	cbt.executeAllUpdateQuery(qry,(SessionFactory) arg0.getSession().getAttribute("connectionSpace"));
				 	
		 } catch (Exception e) {
		    	e.printStackTrace();
		    } 
		
		
	}
}