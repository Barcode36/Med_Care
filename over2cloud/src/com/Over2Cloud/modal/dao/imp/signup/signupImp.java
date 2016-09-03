package com.Over2Cloud.modal.dao.imp.signup;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.ctrl.Signup.SiupAction;
import com.Over2Cloud.modal.db.signup.ClientUserAccount;
import com.Over2Cloud.modal.db.signup.Registation;

import hibernate.common.HibernateSessionFactory;
public class signupImp 
{
	static final Log log = LogFactory.getLog(signupImp.class);
	public List getallcontry()
	{
		List  contryid = null;
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				String countrylistidQuery="select Country,ISO_Code from _countries";
				Query query =session.createSQLQuery(countrylistidQuery);
			    contryid=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {
			 if(session.isOpen())
			{
				session.flush();
				session.close();
			}}
		 return contryid;
	    }
	
	
	public List getalljobFunctionalArea()
	{
		List  contryid = null;
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				String countrylistidQuery="select id,jobfunction from job_area";
				Query query =session.createSQLQuery(countrylistidQuery);
			    contryid=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {if(session.isOpen())
			{
				session.flush();
				session.close();
			}}
		 return contryid;
	    }
	
	
	
	public List getallSelectedDomaincontry(String IsoCountryCode)
	{
		List  contryid = null;
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				String countrylistidQuery="select id,mysqlServerName_Ip from client_chunk_space_configuration_table where isocountrycode='"+IsoCountryCode+"'";
				Query query =session.createSQLQuery(countrylistidQuery);
			    contryid=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {if(session.isOpen())
			{
				session.flush();
				session.close();
			}}
		 return contryid;
	    }
	
	public List getallSingleSelectedDomaincontry(String IsoCountryCode)
	{
		List  contryid = null;
	    Session session = null;
	    try 
		{
	    	    session = HibernateSessionFactory.getSession();
				String countrylistidQuery="select id,mysqlServerName_Ip from server_db_table where isocountrycode='"+IsoCountryCode+"' and isStatus='A' ";
				Query query =session.createSQLQuery(countrylistidQuery);
			    contryid=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {if(session.isOpen())
			{
				session.flush();
				session.close();
			}}
		 return contryid;
	    }
	
	
	public List getconnectionString(int id)
	{
		List  connection = null;
	    Session session = null;
	    try 
		{
	    	    session = HibernateSessionFactory.getSession();
				String countrylistidQuery="select mysqlServerName_Ip,mysql_port,mysql_user_name,mysql_password  from server_db_table where id='"+id+"'";
				Query query =session.createSQLQuery(countrylistidQuery);
				connection=query.list();
				
		}catch (Exception e) {e.printStackTrace();} 
		 finally {if(session.isOpen())
			{
				session.flush();
				session.close();
			}}
		 return connection;
	    }
	
	
	
	
	
	public List getallSingleSpaceServer()
	{
		List  contryid = null;
	    Session session = null;
	    try 
		{
			    session=HibernateSessionFactory.getSession();
				String countrylistidQuery="select id,mysqlServerName_Ip from server_db_table where isStatus='A'";
				Query query =session.createSQLQuery(countrylistidQuery);
			    contryid=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {if(session.isOpen())
			{
				session.flush();
				session.close();
			}}
		 return contryid;
	    }
	public List getAllApplication()
	{
		List applicationList  = null;
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				String appslistidQuery="select App_name,App_code from apps_details ";
				Query query =session.createSQLQuery(appslistidQuery);
				applicationList=query.list();
		}catch (Exception e) { log.error("Over2Cloud::>> Class:signupImp >> Method:: getAllApplication >>  ",e); } 
		 finally {if(session.isOpen())
			{
				session.flush();
				session.close();
			}}
		 return applicationList;
	    }

	
	
	
	
	
	public List getallindustry()
	{
		List  industryid = null;
	    Session session = null;
	    
	    try 
		{
				session = HibernateSessionFactory.getSession();
				String listidQuery="select industry_name,industry_code from industry";
				Query query =session.createSQLQuery(listidQuery);
				industryid=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {if(session.isOpen())
			{
				session.flush();
				session.close();
			}}
		 return industryid;
	    }
	
	public List isVerified(String uuid ,String confirm_key,String accountid,String id) {
		List  verificationList = null;
	    Session session = null;
	    boolean isExistFlag = false;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				String listidQuery="select uuid,accountid,confirm_key,id,country from registation_sinup where uuid='"+uuid+"' and accountid='"+accountid+"' and confirm_key='"+confirm_key+"' and id="+id+"";
				Query query =session.createSQLQuery(listidQuery);
				verificationList=query.list();
				/*if (verificationList != null && verificationList.size() > 0) {
						isExistFlag = true;}*/
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);	
		} 
		 finally {if(session.isOpen())
			{
				session.flush();
				session.close();
			}}
		 return verificationList;
	}
	public List isVerifiedstep1(String uuid ,String confirm_key,String accountid,String id) {
		List  verificationList = null;
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				String listidQuery="select uuid,accountid,confirm_key,id,country from registation_sinup where uuid='"+uuid+"' and accountid='"+accountid+"' and confirm_key='"+confirm_key+"' and id="+id+" and signupstep='N'";
				Query query =session.createSQLQuery(listidQuery);
				verificationList=query.list();
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);	
		} 
		 finally {if(session.isOpen())
			{
				session.flush();
				session.close();
			}}
		 return verificationList;
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean isVerified1(Map VarifiedClick) {
		boolean isExistFlag = false;
		List<Long> verificationList = null;
		Criteria criteria = null;
		Transaction hTransaction;
		Session session = null;
		try {
			 session = HibernateSessionFactory.getSession();
			  hTransaction = session.beginTransaction();
			  criteria = session.createCriteria(Registation.class).setProjection(
			  Projections.rowCount()).add(
			  Restrictions.allEq(VarifiedClick));
			  verificationList = criteria.list();
			if (verificationList != null && verificationList.size() > 0) {
				if ((Long) verificationList.get(0) != 0)
					isExistFlag = true;
			}
			hTransaction.commit();
		} catch (Exception e) {
			session = HibernateSessionFactory.getSession();
		    log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);
		} finally{
			if(session.isOpen())
			{
				session.flush();
				session.close();
			}
		}
		
		return isExistFlag;
	}

	public Registation getRegistationObject(String uuid,String accountid,String confirm_key) 
	 {
			Session session = null;
			Registation nsbC = null;
			List<Registation> tempList = null;
			try {
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(Registation.class);
				criteria.add(Restrictions.eq("uuid",uuid));
				criteria.add(Restrictions.eq("accountid",accountid));
				criteria.add(Restrictions.eq("confirm_key",confirm_key));
				tempList = criteria.list();
				tx.commit();
			} catch (Exception e) {
				e.getMessage();
			} finally {
				if(session.isOpen())
				{
					session.flush();
					session.close();
				}
			}
			if (tempList != null && tempList.size() > 0) {
				nsbC = tempList.get(0);
			} else {
				nsbC = new Registation();
			}
			return nsbC;
		}
	
	
	public ClientUserAccount getClientUserAccountObject(int id,String countryid) 
	 {
			Session session = null;
			ClientUserAccount nsbC = null;
			List<ClientUserAccount> tempList = null;
			try {
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(ClientUserAccount.class);
				criteria.add(Restrictions.eq("id",id));
				criteria.add(Restrictions.eq("countryid",countryid));
				tempList = criteria.list();
				tx.commit();
			} catch (Exception e) {
				e.getMessage();
			} finally {
				if(session.isOpen())
				{
					session.flush();
					session.close();
				}
			}
			if (tempList != null && tempList.size() > 0) {
				nsbC = tempList.get(0);
			} else {
				nsbC = new ClientUserAccount();
			}
			return nsbC;
		}
	
	
	public List getAllProduct_Signup()
	{
		List  contryid = null;
	    Session session = null;
	    try 
		{
	    	   session = HibernateSessionFactory.getSession();
				String countrylistidQuery="select orgnizationName, regUserName,URL from signup_product where isStatus='N'";
				Query query =session.createSQLQuery(countrylistidQuery);
			    contryid=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {
			 if(session.isOpen())
			{
				session.flush();
				session.close();
			}}
		 return contryid;
	    }
	public List getAllPackageWithPCode(String pCode) {
		List  verificationList = null;
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				StringBuilder listidQuery=new StringBuilder("select id,cost,timein,usercounter from packconfiguration where applicationcode='"+pCode+"'" +
						" and offerFrom <='"+DateUtil.getCurrentDateUSFormat()+"' and offerTo >='"+DateUtil.getCurrentDateUSFormat()+"'");
				Query query =session.createSQLQuery(listidQuery.toString());
				verificationList=query.list();
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);	
		} 
		 finally {
			 if(session.isOpen())
			 {
				 session.flush();session.close();
			 }
			 
		 }
		 return verificationList;
	}
	
	public List getPackageInfo(int pCode) {
		List  verificationList = null;
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				StringBuilder listidQuery=new StringBuilder("select timein,cost,currency from packconfiguration where id="+pCode+"");
				Query query =session.createSQLQuery(listidQuery.toString());
				verificationList=query.list();
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);	
		} 
		 finally {
			 if(session.isOpen())
			 {
				 session.flush();session.close();
			 }
			 
		 }
		 return verificationList;
	}
	
	public List getAllPackageWith(String pCode) {
		List  verificationList = null;
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				StringBuilder listidQuery=new StringBuilder("select usercounter from packconfiguration where id in("+pCode+")");
				Query query =session.createSQLQuery(listidQuery.toString());
				verificationList=query.list();
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);	
		} 
		 finally {
			 if(session.isOpen())
			 {
				 session.flush();session.close();
			 }
			 
		 }
		 return verificationList;
	}
	public List appList() {
		List  verificationList = null;
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				StringBuilder listidQuery=new StringBuilder("select app_name,app_code from apps_details");
				Query query =session.createSQLQuery(listidQuery.toString());
				verificationList=query.list();
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);	
		} 
		 finally {
			 if(session.isOpen())
			 {
				 session.flush();session.close();
			 }
			 
		 }
		 return verificationList;
	}
	
	public static boolean checkEmailID(String email) {
		List  verificationList = null;
	    Session session = null;
	    boolean isExistFlag = true;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				String listidQuery="select contact_emailid from registation_sinup where contact_emailid='"+email+"'";
				Query query =session.createSQLQuery(listidQuery);
				verificationList=query.list();
				if (verificationList != null && verificationList.size() > 0) {
						isExistFlag = false;}
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);	
		} 
		 finally {
			 if(session.isOpen())
			 {
				 session.flush();session.close();
			 }
			 
		 }
		 return isExistFlag;
	}
	/**
	 * um, kcf, id, uid
	 * @param uuid
	 * @param confirm_key
	 * @param accountid
	 * @param id
	 * @return
	 */
	public static List isVerifiedDemo(String uuid ,String confirm_key,String accountid,String id) {
		List  verificationList = null;
	    Session session = null;
	    boolean isExistFlag = false;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				String listidQuery="select org_reg_name,contactNo,contact_emailid from registation_sinup where uuid='"+uuid+"' and accountid='"+accountid+"' and confirm_key='"+confirm_key+"' and id="+id+" and demoAccount='0'";
				Query query =session.createSQLQuery(listidQuery);
				verificationList=query.list();
				/*if (verificationList != null && verificationList.size() > 0) {
						isExistFlag = true;}*/
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);	
		} 
		 finally {
			 if(session.isOpen())
			 {
				 session.flush();session.close();
			 }
			 
		 }
		 return verificationList;
	}
	
	public static void updateDemoAccountFlag(String uuid ,String confirm_key,String accountid,String id,String flag) {
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				String listidQuery="update registation_sinup set demoAccount='"+flag+"' where uuid='"+uuid+"' and accountid='"+accountid+"' and confirm_key='"+confirm_key+"' and id="+id+"";
				Query query =session.createSQLQuery(listidQuery);
				query.executeUpdate();
				tx.commit();
				/*if (verificationList != null && verificationList.size() > 0) {
						isExistFlag = true;}*/
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);	
		} 
		 finally {
			 if(session.isOpen())
			 {
				 session.flush();session.close();
			 }
			 
		 }
	}
	public static void updateDemoAccountFlag(String id,String flag) {
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				String listidQuery="update registation_sinup set demoAccount='"+flag+"' where id="+id+"";
				Query query =session.createSQLQuery(listidQuery);
				query.executeUpdate();
				tx.commit();
				/*if (verificationList != null && verificationList.size() > 0) {
						isExistFlag = true;}*/
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);	
		} 
		 finally {
			 if(session.isOpen())
			 {
				 session.flush();session.close();
			 }
			 
		 }
	}
	
	/**
	 * DEMO ACCOUNT WORK STARTED BY SANDEEP ON 30-04-2013
	 * 
	 */
	
	public List isVerified(String emailID) {
		List  verificationList = null;
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				String listidQuery="select uuid,accountid,confirm_key,id,country from registation_sinup where contact_emailid='"+emailID+"' and demoAccount='2'";
				Query query =session.createSQLQuery(listidQuery);
				verificationList=query.list();
				/*if (verificationList != null && verificationList.size() > 0) {
						isExistFlag = true;}*/
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);	
		} 
		 finally {
			 if(session.isOpen())
			 {
				 session.flush();session.close();
			 }
			 
		 }
		 return verificationList;
	}
	
	public List isVerifiedstep1(String emailID) {
		List  verificationList = null;
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				String listidQuery="select uuid,accountid,confirm_key,id,country from registation_sinup where contact_emailid='"+emailID+"'";
				Query query =session.createSQLQuery(listidQuery);
				verificationList=query.list();
		}catch (Exception e) {
			log.error("Over2Cloud::>> Class:signupImp >> Method::>> isVerified For User Exist Or Not",e);	
		} 
		 finally {
			 if(session.isOpen())
			 {
				 session.flush();session.close();
			 }
			 
		 }
		 return verificationList;
	}
	
	public static List getDemoClientFalgValue(String queryTemp)
	{
		List  contryid = null;
	    Session session = null;
	    try 
		{
				session = HibernateSessionFactory.getSession();
				Query query =session.createSQLQuery(queryTemp);
			    contryid=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {
			 if(session.isOpen())
			 {
				 session.flush();session.close();
			 }
			 
		 }
		 return contryid;
	    }
	
	public Registation getRegistrationAccount(String uid,String accountid) 
	 {
			Session session = null;
			Registation nsbC = null;
			List<Registation> tempList = null;
			try {
				session = HibernateSessionFactory.getSession();
				Transaction tx = session.beginTransaction();
				Criteria criteria = session.createCriteria(Registation.class);
				criteria.add(Restrictions.eq("uuid",uid));
				criteria.add(Restrictions.eq("accountid",accountid));
				tempList = criteria.list();
				tx.commit();
			} catch (Exception e) {
				e.getMessage();
			} finally {
				 if(session.isOpen())
				 {
					 session.flush();session.close();
				 }
				 
			 }
			if (tempList != null && tempList.size() > 0) {
				nsbC = tempList.get(0);
			} else {
				nsbC = new Registation();
			}
			return nsbC;
		}
	
}
