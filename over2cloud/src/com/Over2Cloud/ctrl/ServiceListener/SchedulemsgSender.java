package com.Over2Cloud.ctrl.ServiceListener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.ConnectionFactory.DBDynamicConnection;

public class SchedulemsgSender {
	
	// this method used for all type of method to send
	public int sendSMS(String msisdn,String fmsg,String clientid,String userName,String password){
		int revertCode = 0;
		try {
		      String encodedMsg = URLEncoder.encode(fmsg);
		       if((msisdn != null &&  !msisdn.equals("")) && msisdn.length() == 10 && ((msisdn.startsWith("9") || msisdn.startsWith("8") || msisdn.startsWith("7")) && fmsg!= null && !fmsg.equals("")))
		        {
		    	   String URL = null;
		    	   try {
				          StringBuffer url = new StringBuffer();
						  URL = "http://115.112.185.85:6060/urldreamclient/dreamurl?userName="+userName+"&password="+password;
				          URL=URL+"&clientid="+clientid+"&to=";
			    	   
				          url.append(URL);
	   				      url.append(msisdn);
	   				      url.append("&text=");
	   				      url.append(encodedMsg);
						  
						  URL codedURL = new URL(url.toString());
						  HttpURLConnection HURLC =(HttpURLConnection)codedURL.openConnection();
						  HURLC.connect();
						  revertCode= HURLC.getResponseCode();
						  HURLC.disconnect();
						}
					catch (Exception E)
					  {
						URL = null;
						E.printStackTrace();
					 }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return revertCode;
	}	
	
	//for Testing Purpose this Particular method i have been used it..! 
	public int insertSMS(String msisdn,String fmsg,String clientid,String userName,String password){
		SessionFactory hsessionFactory=DBDynamicConnection.getSessionFactory();
		int returnflag=0;
		StringBuilder sql = new StringBuilder("insert into msg_stats (msisdn, msg,client_id) values ( ");
		sql.append("'"+msisdn+"',");
		sql.append("'"+fmsg+"',");
		sql.append("'"+clientid+"'");
		sql.append(" )");
		Transaction hTransaction=null;
		try{
			Session session=hsessionFactory.getCurrentSession();
			hTransaction = session.beginTransaction();  
			session.createSQLQuery(sql.toString()).executeUpdate();  
			hTransaction.commit(); 
		}catch (Exception e) {
			e.printStackTrace();
			hTransaction.rollback();
			// TODO: handle exception
		}
		System.out.println("returnflag"+returnflag);
		return returnflag;
	}
	
	
}
