package com.Over2Cloud.modal.dao.impl.setting;

import hibernate.common.HibernateSessionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.modal.databean.setting.Smtp;

public class SmtpDao{
	
	// Update Method for SMTP Detail
	   public boolean updateSMTP(String id,String server,String port,String emailid,String subject,String password, String user) {

				// Initialsing Objects
		    Session USSess = null;
			boolean USFlag = false;
			try {
				// Getting Session

				USSess = HibernateSessionFactory.getSession();
				Smtp SB = (Smtp) USSess.get(Smtp.class,Integer.parseInt(id));
				SB.setServer(server);
				SB.setPort(port);
				SB.setEmailid(emailid);
				SB.setPwd(password);
				SB.setSubject(subject);
				//SB.setUser(user);
				SB.setCreateon_date(DateUtil.getCurrentDateUSFormat());
				SB.setCreateat(DateUtil.getCurrentTime());
				
				USSess.beginTransaction();
				USSess.update(SB);
				USSess.getTransaction().commit();
				USFlag = true;
			}
			catch (Exception e) 
			{e.printStackTrace();}
			finally {
				try {HibernateSessionFactory.closeSession();} 
				catch (Exception e2){e2.printStackTrace();}
					
			}		return USFlag;
		}
	

}
