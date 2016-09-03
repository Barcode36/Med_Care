package com.Over2Cloud.modal.dao.impl.common;
import hibernate.common.HibernateSessionFactory;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;

public class onlineWebImp {
	@SuppressWarnings("unchecked")
	public List GetallOnlineWeb(String onlineType)
	{
		List  getallOnlineList = null;
	    Session session = null;
	    try 
		{     
	    	   
				session=HibernateSessionFactory.getSession();
				String AccountIdlistQuery=null;
				if(onlineType.equalsIgnoreCase("WEB")){
				AccountIdlistQuery= "select clientinfo.id, reg.org_name,reg.org_reg_name,reg.city,reg.country,reg.signupstep,reg.accountType,clientinfo.isLive from registation_sinup as reg left join  client_info as clientinfo on reg.accountid=clientinfo.accountid and reg.uuid=clientinfo.uid and reg.confirm_key=clientinfo.confim_key where reg.reg_user='WEB'";
				}
				else if(onlineType.equalsIgnoreCase("Asso"))
				{
					AccountIdlistQuery= "select clientinfo.id, reg.org_name,reg.org_reg_name,reg.city,reg.country,reg.signupstep,reg.accountType,clientinfo.isLive from registation_sinup as reg left join  client_info as clientinfo on reg.accountid=clientinfo.accountid and reg.uuid=clientinfo.uid and reg.confirm_key=clientinfo.confim_key where reg.accountType='CAA';";
				}
				else if(onlineType.equalsIgnoreCase("orgdetails"))
				{
					AccountIdlistQuery= "select clientinfo.id, reg.org_name,reg.org_reg_name,reg.city,reg.country,reg.signupstep,reg.accountType,clientinfo.isLive from registation_sinup as reg left join  client_info as clientinfo on reg.accountid=clientinfo.accountid and reg.uuid=clientinfo.uid and reg.confirm_key=clientinfo.confim_key where reg.accountType='COA';";
				}
				Query query =session.createSQLQuery(AccountIdlistQuery);
				getallOnlineList=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {session.flush();session.close();}
		 return getallOnlineList;
	}
	
}
