package com.Over2Cloud.InstantCommunication;

import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class TestMail implements Runnable{
	SessionFactory connection=null;
	HelpdeskUniversalHelper HUH=null;
	
	public TestMail(SessionFactory connection,HelpdeskUniversalHelper HUHObj)
	{
		this.connection=connection;
		this.HUH=HUHObj;
	}
	
	@Override
	public void run() {
     try {
		  while (true)
		   {
			 try {
                    GenericMailSender GMS = new GenericMailSender();
					//GMS.sendMail("khushals@dreamsol.biz", "Testing", "Testing ucessful", "","smtp.gmail.com","465","blkhelpdeskmanager@gmail.com","dreamsol");
			       
			        Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping****..........*****............"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
					Thread.sleep(60*1000);
					System.out.println("Woke Up*****..........-----............."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	} catch (Exception e) {
		e.printStackTrace();
	}		
	finally
	 {
		try {
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	}

	public static void main(String args[])
		{
			try{
				 SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
				 HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				 Thread uclient=new Thread(new TestMail(connection,HUH));
				 uclient.start();
				 Thread.sleep(1000*1);
			}catch(Exception E){
				E.printStackTrace();
			}
		}
}
