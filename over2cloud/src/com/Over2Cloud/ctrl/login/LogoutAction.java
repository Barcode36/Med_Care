package com.Over2Cloud.ctrl.login;

import java.security.InvalidKeyException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.SessionAware;




import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionSupport;

public class LogoutAction extends ActionSupport implements SessionAware {
	
	static final Log log = LogFactory.getLog(LogoutAction.class);
	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	private Map<String,Object> session;
	@SuppressWarnings("unchecked")
	public String execute(){
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		 try {
		String userName= (String) session.get("uName");
	     String qry = " update useraccount set loginStatus='0' where userID='"+Cryptography.encrypt(userName ,DES_ENCRYPTION_KEY)+"' ";
				 	cbt.executeAllUpdateQuery(qry,(SessionFactory)session.get("connectionSpace"));
				 	
		 } catch (Exception e) {
		    	log.error("@ERP::>> Problem in LogoutAction", e);
		    } 
			
		session.clear();
		if(!session.isEmpty())
		{
		session.remove("uName");
		session.remove("accountid");
		}
		if (session instanceof org.apache.struts2.dispatcher.SessionMap) {
		    try {
		        ((org.apache.struts2.dispatcher.SessionMap) session).invalidate();
		    	 
		    } catch (IllegalStateException e) {
		    	log.error("@ERP::>> Problem in LogoutAction", e);
		    }
		}
		return SUCCESS;
	}
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String,Object> session) {
		this.session = session;
	}
}
