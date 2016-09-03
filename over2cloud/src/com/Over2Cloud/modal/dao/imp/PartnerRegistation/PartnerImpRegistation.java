package com.Over2Cloud.modal.dao.imp.PartnerRegistation;

import hibernate.common.HibernateSessionFactory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;

public class PartnerImpRegistation 
{
static final Log log = LogFactory.getLog(LoginImp.class);
	/*
	 *     
	 * */
@SuppressWarnings("unchecked")
public List GetAllUserLevel(String userName,String accountid)
{
	List  existList = null;
    Session session = null;
    try 
	{
			session = HibernateSessionFactory.getSession();
			String listidQuery=	"select clientinfo.username, reg.org_name,reg.accountType,reg.org_reg_name,ProI.productcode, ProI.productuser,ProI.validity_from,ProI.validity_to,ProI.productid,clientinfo.id,reg.country,clientinfo.isLive from client_info as clientinfo  " +
								"inner join product_intrested as ProI  on ProI.accountid=clientinfo.accountid and ProI.uuid=clientinfo.uid and ProI.confirm_key=clientinfo.confim_key " +
								"inner join registation_sinup as reg  on reg.accountid=clientinfo.accountid and reg.uuid=clientinfo.uid and reg.confirm_key=clientinfo.confim_key " +
								"where reg.reg_user='"+userName+"' and  reg.user_accountid='"+accountid+"'";
			Query query =session.createSQLQuery(listidQuery);
			existList=query.list();
	}catch (Exception e) {
		log.error("Over2Cloud::>> Class:signupImp >> Method::>> isExit For Login Perpose",e);
		existList=null;
	} 
	 finally {session.flush();session.close();}
	 return existList;
}



public String GetAllApplicationName(String appcode)
{
	String  existList = null;
    Session session = null;
    try 
	{
		    session = HibernateSessionFactory.getSession();
			String listidQuery=	"select app_name from apps_details where app_code='"+appcode+"'";
			Query query =session.createSQLQuery(listidQuery);
			existList=query.list().get(0).toString();
	}catch (Exception e) {
		log.error("Over2Cloud::>> Class:signupImp >> Method::>> isExit For Login Perpose",e);
		existList=null;
	} 
	 finally {session.flush();session.close();}
	 return existList;
}




}
