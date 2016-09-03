package com.Over2Cloud.modal.dao.imp.login;

import hibernate.common.HibernateSessionFactory;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;

public class LoginImp
{
	static final Log log = LogFactory.getLog(LoginImp.class);
	@SuppressWarnings("unchecked")
	public boolean isExit(String Clientid ,String countryid ) {
		List  existList = null;
		boolean flag=false;
	    Session session = null;
	    Transaction hTransaction=null;
	    try 
		{
	    	session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
	    	if(!session.isOpen())
	    	{
	    		session=HibernateSessionFactory.getSession();
	    	}
				hTransaction=session.beginTransaction();
				String listidQuery="select * from client_info where id='"+Clientid+"' and countryid='"+countryid+"'";
				Query query =session.createSQLQuery(listidQuery);
				existList=query.list();
				hTransaction.commit();
				if(existList.size()>0)
				{flag=true;}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Over2Cloud::>> Class:LoginImp >> Method::>> isExit For Login Perpose",e);
			flag=false;
		} 
		finally{
			if(session.isOpen())
			{
				try {
					session.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		 return flag;
	}
	
	
	@SuppressWarnings("unchecked")
	public String GetAccountTypeOf_User(String Accountid,String countryId)
	{
		String  clientType = null;
	    Session session = null;
	    Transaction hTransaction=null;
	    try 
		{
	    	session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
	    	hTransaction=session.beginTransaction();
				String listidQuery=	"select reg.accountType from client_info as clientinfo " +
									"inner join registation_sinup as reg on reg.accountid=clientinfo.accountid and reg.uuid=clientinfo.uid and reg.confirm_key=clientinfo.confim_key " +
									"where clientinfo.id='"+Accountid+"' and clientinfo.countryid='"+countryId+"'";
				Query query =session.createSQLQuery(listidQuery);
				
				clientType=query.list().get(0).toString();
				hTransaction.commit();
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:LoginImp >> Method::>> GetAccountTypeOf_User For Login Perpose",e);
			
		} 
		 return clientType;
	}
	public List GetClientspace(String Spaceid)
	{
		List  clientspace = null;
	    Session session = null;
	    Transaction hTransaction=null;
	    try 
		{
	    	session = HibernateSessionFactory.getSession();
	    	hTransaction=session.beginTransaction();
				String listidQuery=	"select  mysqlServerName_Ip,mysql_port,mysql_user_name,mysql_password from server_db_table where id='"+Spaceid+"' and isStatus='A'";
				Query query =session.createSQLQuery(listidQuery);
				clientspace=query.list();
				hTransaction.commit();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Over2Cloud::>> Class:LoginImp >> Method::>> GetClientspace For Login Perpose",e);
		} 
		finally{
			try {
				if (session.isOpen()) {
					session.flush();
					session.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		 return clientspace;
	
	}
	
	
	
	public boolean isUserExit(String Clientid ,String countryid,String username,String pwd ) {
		List  existList = null;
		boolean flag=false;
	    Session session = null;
	    Transaction hTransaction=null;
	    try 
		{
	    	session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
	    	hTransaction=session.beginTransaction();
				String listidQuery="select * from client_info where id='"+Clientid+"' and countryid='"+countryid+"' and username='"+username+"' and pwd='"+pwd+"'";
				Query query =session.createSQLQuery(listidQuery);
				existList=query.list();
				hTransaction.commit();
				if(existList.size()>0)
				{flag=true;}
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:LoginImp >> Method::>> isUserExit For Login Perpose",e);
			flag=false;
		} 
		 return flag;
	}


	public List getUserInfomation(String Clientid ,String countryid,String username,String pwd ) {
		List  existList = null;
	    Session session = null;
	    Transaction hTransaction=null;
	    try 
		{
	    	session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
	    	hTransaction=session.beginTransaction();
				String listidQuery="select reg.org_name,reg.accountType,reg.org_reg_name,ProI.productcode, ProI.productuser,ProI.validity_from,ProI.validity_to from client_info as clientinfo " +
						"inner join product_intrested as ProI  on ProI.accountid=clientinfo.accountid and ProI.uuid=clientinfo.uid and ProI.confirm_key=clientinfo.confim_key " +
						"inner join registation_sinup as reg  on reg.accountid=clientinfo.accountid and reg.uuid=clientinfo.uid and reg.confirm_key=clientinfo.confim_key "+ 
						"where clientinfo.id='"+Clientid+"' and clientinfo.countryid='"+countryid+"' and clientinfo.username='"+username+"' and clientinfo.pwd='"+pwd+"'";
				Query query =session.createSQLQuery(listidQuery);
				existList=query.list();
				hTransaction.commit();
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:LoginImp >> Method::>> getUserInfomation For Login Perpose",e);
			existList=null;
		} 
		 return existList;
	}
	
	 public String getAppsName(String AppsCode)
	 {
			String  asListString = null;
		    Session session = null;
		    Transaction hTransaction=null;
		    try 
			{
		    	session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		    	hTransaction=session.beginTransaction();
					String listidQuery="select App_name from apps_details where App_code='"+AppsCode+"'";
					Query query =session.createSQLQuery(listidQuery);
					asListString=query.list().toString();
					hTransaction.commit();
			}catch (Exception e) {
				log.error("Over2Cloud::>> Class:LoginImp >> Method::>> getAppsName For Login Perpose",e);
				asListString=null;
			} 
			 return asListString;
		}
	/*
     Doing Start Work on The Distributed Artitechter Login
	  So Make Chunk Maping Concept validated
	 * 
	 * 
	 * */
	
	 @SuppressWarnings("unchecked")
	public List chunkDomainName(String Accountid,String isoCountrycode)
	 {
		   List<Object> result=null;
	       Session session = null;
	       Transaction hTransaction=null;
			try 
			{
				session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
				hTransaction=session.beginTransaction();
				String TargetCategoryQuery=     "select ucm.user_chunk_slab_from as SlabFrom, ucm.user_chunk_slab_to as SlabTo,CCSCT.mysqlServerName_Ip,CCSCT.mysql_port,CCSCT.mysql_user_name,CCSCT.mysql_password from user_chunk_mapping as ucm " +
						                    	"inner join client_chunk_space_configuration_table as CCSCT on CCSCT.id=ucm.Souce_ip_Domain_address " +
						                    	"where  ucm.user_chunk_slab_from <= "+Accountid+" and ucm.user_chunk_slab_to>="+Accountid+" and ucm.country_iso_code ='"+isoCountrycode+"' and CCSCT.isStatus='A' and ucm.iscity_iscountry_Blocking='L';";
				Query query =session.createSQLQuery(TargetCategoryQuery);
				result=query.list();
				hTransaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result ;
	 }
	

	 @SuppressWarnings("unchecked")
	public boolean isUserExitForLocalOrg(String username,String pwd,SessionFactory ConnectionName) {
			List  existList = null;
			boolean flag=false;
		    try 
			   {
		    		StringBuilder query1=new StringBuilder("");
					query1.append("select * from useraccount where " +
							"userID='"+username+"' and password='"+pwd+"' and active='1'");
					existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
					//System.out.println("Access Querry"+query1);
					if(existList.size()>0)
					   {
						 flag=true;
					   }
			    }catch (Exception e) {
				log.error("Over2Cloud::>> Class:LoginImp >> Method::>> isUserExitForLocalOrg For Login Perpose",e);
				flag=false;
			} 
			return flag;
		}
 	 @SuppressWarnings("unchecked")
		public boolean isUserLoginForLocalOrg(String username,String pwd,SessionFactory ConnectionName) {
 		//public boolean isUserLoginForLocalOrg(String username,String pwd,SessionFactory ConnectionName,String rights) {
 					List  existList = null;
				boolean flag=false;
			    try 
				   {
			     		StringBuilder query1=new StringBuilder("");
			    		
			    		if(username.equalsIgnoreCase("rHbPMbdR6K0=") ||username.equalsIgnoreCase("NLWm/temLhHSldXn5yhOQA==")||username.equalsIgnoreCase("1kChLofgAis="))
			     		
			     		
			    //<<<<<<<  For control multiple login....Master login can do multiple login    :::Sanjay KUmar Soni  >>>>>>>> 		
			     		
			      //		if(rights.contains("masterLogin"))
			     	 		query1.append("select * from useraccount where " +
			     	 		"userID='"+username+"' and password='"+pwd+"' and active='1'");
			    			
			     	 		else
						 		query1.append("select * from useraccount where " +
								"userID='"+username+"' and password='"+pwd+"' and active='1' and loginStatus='0'");
					 	
			     		
			     		existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
						//System.out.println("Access Querry"+query1);
						if(existList.size()>0)
						   {
							 flag=true;
						   }
				    }catch (Exception e) {
					log.error("Over2Cloud::>> Class:LoginImp >> Method::>> isUserExitForLocalOrg For Login Perpose",e);
					flag=false;
				} 
				return flag;
			}
		 
	 
	 @SuppressWarnings("unchecked")
		public String getUserType(String username,String pwd,SessionFactory ConnectionName) {
				List  existList = null;
				String userType=null;
			    try 
				   {
			    		StringBuilder query1=new StringBuilder("");
						query1.append("select userType from useraccount where " +
								"userID='"+username+"' and password='"+pwd+"' and active='1'");
						existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
						
						if(existList!=null && existList.size()>0 && existList.get(0)!=null)
						{
							userType=existList.get(0).toString();
						}
						
				    }catch (Exception e) {
					log.error("Over2Cloud::>> Class:LoginImp >> Method::>> isUserExitForLocalOrg For Login Perpose",e);
				} 
				return userType;
			}
	 public List getproductDetails(SessionFactory ConnectionName) {
			List  productData = null;
			boolean flag=false;
		    try 
			{
		    		StringBuilder query1=new StringBuilder("");
					query1.append("select * from client_product");
					productData=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Over2Cloud::>> Class:LoginImp >> Method::>> getproductDetails For Login Perpose",e);
				flag=false;
			} 
			
			 return productData;
		}
	 public boolean isClientBlock(String Clientid ,String countryid ) {
			List  existList = null;
			boolean flag=false;
		    Session session = null;
		    Transaction hTransaction=null;
		    try 
			{
		    	session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		    	hTransaction=session.beginTransaction();
					String listidQuery="select * from client_info where id='"+Clientid+"' and countryid='"+countryid+"' and isLive='Live'";
					Query query =session.createSQLQuery(listidQuery);
					existList=query.list();
					hTransaction.commit();
					if(existList.size()>0)
					{flag=true;}
			}catch (Exception e) {
				log.error("Over2Cloud::>> Class:LoginImp >> Method::>> isClientBlock For Login Perpose",e);
				flag=false;
			} 
			 return flag;
		}
	 
	 public List getUserInfomation(String Clientid ,String countryid) {
			List  existList = null;
		    Session session = null;
		    Transaction hTransaction=null;
		    try 
			 {
		    	session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
		    	hTransaction=session.beginTransaction();
				String listidQuery="select reg.org_name,reg.accountType,reg.org_reg_name,ProI.productcode, ProI.productuser,reg.regAddress,reg.city,reg.pincode from client_info as clientinfo " +
				"inner join product_intrested as ProI  on ProI.accountid=clientinfo.accountid and ProI.uuid=clientinfo.uid and ProI.confirm_key=clientinfo.confim_key " +
				"inner join registation_sinup as reg  on reg.accountid=clientinfo.accountid and reg.uuid=clientinfo.uid and reg.confirm_key=clientinfo.confim_key "+ 
				"where clientinfo.id='"+Clientid+"' and clientinfo.countryid='"+countryid+"'";
				
				Query query =session.createSQLQuery(listidQuery);
			    existList=query.list();
			    hTransaction.commit();
			 }
		    catch (Exception e) {
				e.printStackTrace();
				log.error("Over2Cloud::>> Class:LoginImp >> Method::>> getUserInfomation For Login Perpose",e);
				existList=null;
			} 
			 return existList;
		 }
	 
	 @SuppressWarnings("unchecked")
		public List chunkBlockError(String Accountid,String isoCountrycode)
		 {
			   List<Object> result=null;
		       Session session = null;
		       Transaction hTransaction=null;
				try 
				{
					session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
					hTransaction=session.beginTransaction();
					StringBuilder queryda=new StringBuilder("select chunkclientblock.regionForBlock from user_chunk_mapping as ucm"+
					" inner join client_chunk_space_configuration_table as CCSCT on CCSCT.id=ucm.Souce_ip_Domain_address"+ 
					" inner join chunkclientblock as chunkclientblock on chunkclientblock.id=chunkclientblock.chunkid "+
					" where  ucm.user_chunk_slab_from <= "+Accountid+" and ucm.user_chunk_slab_to>="+Accountid+
					" and ucm.country_iso_code  ='"+isoCountrycode+"' and CCSCT.isStatus='A' and ucm.iscity_iscountry_Blocking='B' " +
							"and chunkclientblock.regionStatus='1'");
					Query query =session.createSQLQuery(queryda.toString());
					result=query.list();
					hTransaction.commit();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					session.flush();
					session.close();
				 }
				return result ;
		 }
	 
	 public static List getDemoAccountDetails(String emailid) {
			List  productData = null;
			boolean flag=false;
		    try 
			{
		    		StringBuilder query1=new StringBuilder("");
					query1.append("select org_reg_name,org_name,accountType,country,id from registation_sinup where contact_emailid='"+emailid+"'  and demoAccount='2'");
					SessionFactory ConnectionName=HibernateSessionFactory.getSessionFactory();
				//	System.out.println("getDemoAccountDetails method"+query1);
					productData=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Over2Cloud::>> Class:LoginImp >> Method::>> getDemoAccountDetails For Login Perpose",e);
				flag=false;
			} 
			 return productData;
		}
	 
	 public static List getMappedDemoServer(int id) {
			List  productData = null;
			boolean flag=false;
		    try 
			{
		    		StringBuilder query1=new StringBuilder("");
					query1.append("select serverId from demo_client_space where clienID="+id);
					SessionFactory ConnectionName=HibernateSessionFactory.getSessionFactory();
					productData=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Over2Cloud::>> Class:LoginImp >> Method::>> getMappedDemoServer For Login Perpose",e);
				flag=false;
			} 
			 return productData;
		}
	 
	 public static boolean checkDemoClientAccountExpire(String emailid) {
			List  productData = null;
			boolean flag=true;
		    try 
			{
		    		StringBuilder query1=new StringBuilder("");
					query1.append("select date from registation_sinup where contact_emailid='"+emailid+"'");
					SessionFactory ConnectionName=HibernateSessionFactory.getSessionFactory();
					productData=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
					for(Iterator it=productData.iterator();it.hasNext();)
					{
						Object date=(Object)it.next();
						int days=new DateUtil().getNoOfDays(DateUtil.getCurrentDateUSFormat(), date.toString());
						if(days>30)
							flag=false;
					}
					
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Over2Cloud::>> Class:LoginImp >> Method::>> checkDemoClientAccountExpire For Login Perpose",e);
				flag=false;
			} 
			 return flag;
		}
	 
	 public static int checkDemoClientRemainingDays(String emailid) {
			List  productData = null;
			int days=0;
		    try 
			{
		    		StringBuilder query1=new StringBuilder("");
					query1.append("select date from registation_sinup where contact_emailid='"+emailid+"'");
					SessionFactory ConnectionName=HibernateSessionFactory.getSessionFactory();
					productData=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
					for(Iterator it=productData.iterator();it.hasNext();)
					{
						Object date=(Object)it.next();
						days=new DateUtil().getNoOfDays(DateUtil.getCurrentDateUSFormat(), date.toString());
						days=7-days;//for remaining days
					}
					
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Over2Cloud::>> Class:LoginImp >> Method::>> checkDemoClientRemainingDays For Login Perpose",e);
			} 
			 return days;
		}
	 
	 public static String getAllproductname() {
		   StringBuilder productQuery=new StringBuilder("");
			List  productData = null;
			boolean flag=false;
		    try 
			{
		    		StringBuilder query1=new StringBuilder("");
					query1.append("select app_code from apps_details");
					SessionFactory ConnectionName=HibernateSessionFactory.getSessionFactory();
					productData=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
					for(Iterator it=productData.iterator();it.hasNext();)
					{
						Object obt=(Object)it.next();
						productQuery.append(obt.toString()+"#");
					}
			}catch (Exception e) {
				e.printStackTrace();
				log.error("Over2Cloud::>> Class:LoginImp >> Method::>> getDemoAccountDetails For Login Perpose",e);
				flag=false;
			} 
			 return productQuery.toString();
		}
	 public boolean insertUser(SessionFactory connection,String mob,String name,String userid,String pwd,String active)
	 {
		 Session session=null;
		 Transaction transaction = null;
		 try
		 {
			 session=connection.openSession();
			 transaction=session.beginTransaction(); 
			 StringBuilder query1=new StringBuilder("");
			query1.append("insert into useraccount (mobileNo,name,userID,password,active) values('"+mob+"','"+name+"','"+userid+"','"+pwd+"','1')");
			Query query=session.createSQLQuery(query1.toString()); 
			query.executeUpdate();
			transaction.commit();
			return true;
		 }
		 catch (Exception e) {
			 e.printStackTrace();
			 return false;
		}
	 }
	 /*
	  * Anoop, 5-8-2013
	  * Get all rights for client login
	  */
	 public String getAllClientRights(String validApp)
	 {
		 String data = "";
		try 
		{
			if(validApp != null && !validApp.equalsIgnoreCase(""))
			{
				SessionFactory ConnectionName = HibernateSessionFactory.getSessionFactory();
				CommonOperInterface coi = new CommonConFactory().createInterface();

				String[] appsArray = validApp.split("#");
				for(String app:appsArray)
				{
					if(!app.equalsIgnoreCase(""))
					{
						String query = "select rights_name from user_rights where module_abbreviation = '"+app+"'";
						List list = coi.executeAllSelectQuery(query, ConnectionName);
						if(list != null && list.size() > 0)
						{
							for(Object ob:list)
							{
								data += ob.toString()+"_ADD#";
								data += ob.toString()+"_VIEW#";
								data += ob.toString()+"_MODIFY#";
								data += ob.toString()+"_DELETE#";
							}
						}
					}
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	 }
	 
	 /*
	  * Anoop, 6-8-2013
	  * Get all rights for client login from clouddb
	  */
	 public String getAllClientRights()
	 {
		 String data = "";
		try 
		{
			SessionFactory ConnectionName = HibernateSessionFactory.getSessionFactory();
			CommonOperInterface coi = new CommonConFactory().createInterface();

			String query = "select rights_name from user_rights";
			List list = coi.executeAllSelectQuery(query, ConnectionName);
			if(list != null && list.size() > 0)
			{
				for(Object ob:list)
				{
					data += ob.toString()+"_ADD#";
					data += ob.toString()+"_VIEW#";
					data += ob.toString()+"_MODIFY#";
					data += ob.toString()+"_DELETE#";
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	 }
	 
	 
	 
}