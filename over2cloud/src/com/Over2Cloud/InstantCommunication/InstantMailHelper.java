package com.Over2Cloud.InstantCommunication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;

public class InstantMailHelper {

	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();

	
	@SuppressWarnings("unchecked")
	public void EmailSend(SessionFactory connection)
	{
		try {
	    	    GenericMailSender GMS = null;
	    		List listMail=new ArrayList();
	    		List emaildata = new ArrayList();
	    		Map<String, Object> wherClause=new HashMap<String, Object>();
	    		emaildata.add("id");
	    		emaildata.add("emailid");
	    		emaildata.add("subject");
	    		emaildata.add("mail_text");
	    		wherClause.put("flag", "0");
	    		
	    		
	    		// Variables for  Update row
	    		Map<String, Object> replaceValue=new HashMap<String, Object>();
	    		Map<String, Object> conditionValue=new HashMap<String, Object>();
	    		
	    		
	    		
	    		
	    		listMail = new HelpdeskUniversalHelper().getDataFromTable("feedback_email", emaildata, wherClause, null, null, connection);
	    		
	    		if(listMail.size()>0)
	    		{
	    			for (Iterator iterator = listMail.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						GMS = new GenericMailSender();
						replaceValue.put("flag", "3");
			    		conditionValue.put("id", object[0].toString());
						new HelpdeskUniversalHelper().updateTableColomn("feedback_email", replaceValue, conditionValue,connection);
						//GMS.sendMail(object[0].toString(), object[1].toString(), object[2].toString(), "");
						
					}
	    		}
		       
		       Runtime rt = Runtime.getRuntime();
				rt.gc();
				System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
				Thread.sleep(5000);
				System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
		       
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
