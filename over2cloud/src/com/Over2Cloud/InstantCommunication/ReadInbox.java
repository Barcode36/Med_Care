package com.Over2Cloud.InstantCommunication;

import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class ReadInbox extends Thread{
	
	SessionFactory connection=null;
	List emailRegistry=null;
	public ReadInbox (SessionFactory connection, List emailDetail)
	{
		this.connection=connection;
		this.emailRegistry=emailDetail;
	}
	
	ReadingEmail RE = new ReadingEmail();
		
	 //Start Point on calling a run method from main method
	  public void run()
		 {
			try {
				while (true) {
					try {
						System.out.println("Inside Try block on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
						RE.testRead(connection,emailRegistry);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		
	  public static void main(String args[])
		{
			try {
				  SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
				  List registeredEmail = new HelpdeskUniversalHelper().getData4EmailConfig(connection);
			      Thread esc=new Thread(new ReadInbox(connection,registeredEmail));
			      esc.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}