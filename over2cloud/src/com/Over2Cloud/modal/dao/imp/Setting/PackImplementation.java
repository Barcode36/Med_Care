package com.Over2Cloud.modal.dao.imp.Setting;
import hibernate.common.HibernateSessionFactory;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
public class PackImplementation 
{
	@SuppressWarnings("unchecked")
	public List GetAllAppslist(String countrycode)
	{
		List  IdList = null;
	    Session session = null;
	    try 
		{     
				session=HibernateSessionFactory.getSession();
				String AccountIdlistQuery= "select app_name,app_code from apps_details where iso_country='"+countrycode+"'";
				Query query =session.createSQLQuery(AccountIdlistQuery);
				IdList=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {session.flush();session.close();}
		 return IdList;
	    }

}
