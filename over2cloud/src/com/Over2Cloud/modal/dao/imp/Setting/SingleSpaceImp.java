package com.Over2Cloud.modal.dao.imp.Setting;

import hibernate.common.HibernateSessionFactory;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
public class SingleSpaceImp 
{
	public List AllSpaceConnection()
	{
		List  AccountIdList = null;
	    Session session = null;
	    try 
		{     
	    	    
				session=HibernateSessionFactory.getSession();
				String AccountIdlistQuery= "select UCM.user_chunk_slab_from,UCM.user_chunk_slab_to,CCSCT.mysqlServerName_Ip,CCSCT.mysql_port,CCSCT.mysql_user_name,CCSCT.mysql_password from user_chunk_mapping as UCM " +
										   "inner join client_chunk_space_configuration_table as CCSCT on CCSCT.id=UCM.Souce_ip_Domain_address where UCM.iscity_iscountry_Blocking='L' and CCSCT.isStatus='A'";
				Query query =session.createSQLQuery(AccountIdlistQuery);
				AccountIdList=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {session.flush();session.close();}
		 return AccountIdList;
	    }
	
	public String ChuckConnection(String id)
	{
		String  AccountIdList = null;
	    Session session = null;
	    try 
		{     
				session=HibernateSessionFactory.getSession();
				String AccountIdlistQuery= "select CCSCT.mysqlServerName_Ip,CCSCT.mysql_port,CCSCT.mysql_user_name,CCSCT.mysql_password from user_chunk_mapping as UCM " +
										   "inner join client_chunk_space_configuration_table as CCSCT on CCSCT.id=UCM.Souce_ip_Domain_address where UCM.Souce_ip_Domain_address="+id+" ";
				Query query =session.createSQLQuery(AccountIdlistQuery);
				List tempDatas=query.list();
				 for (Object c : tempDatas) {
	     				Object[] objectCol = (Object[]) c;
	     				String ipAddress=null,username=null,paassword=null,port=null;
	     				ipAddress=objectCol[0].toString();
	     				port=objectCol[1].toString();
	      				username=objectCol[2].toString();
	      				paassword=objectCol[3].toString();
	      				AccountIdList=ipAddress+","+username+","+paassword+","+port;
				 }
		}catch (Exception e) {e.printStackTrace();} 
		 finally {session.flush();session.close();}
		 return AccountIdList;
	}
	
	public String ContryName(String countryISOCode)
	{

		String  AccountIdList = null;
	    Session session = null;
	    try 
		{     
	    	  
				session=HibernateSessionFactory.getSession();
				String AccountIdlistQuery= "select Country from _countries where ISO_Code='"+countryISOCode+"' ";
				Query query =session.createSQLQuery(AccountIdlistQuery);
				List data=query.list();
                if(data!=null && data.size()>0)
                {
                        Object ob=data.get(0);
                        if(ob!=null)
                        {
                                AccountIdList=ob.toString();
                                
                        }
                        else
                        {
                        	AccountIdList="No Country";
                        }
                }
		}catch (Exception e) {e.printStackTrace();} 
		 finally {session.flush();session.close();}
		 return AccountIdList;
	}
	public String ApplicationName(String appscode)
	{

		String  AccountIdList = null;
	    Session session = null;
	    try 
		{     
				session=HibernateSessionFactory.getSession();
				String AccountIdlistQuery= "select app_name from apps_details where app_code='"+appscode+"'";
				Query query =session.createSQLQuery(AccountIdlistQuery);
				AccountIdList=query.list().get(0).toString();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {session.flush();session.close();}
		 return AccountIdList;
	}
	
	
	
	public List AllRecordDataInformationCome(String Accountid)
	{
		List  AccountIdList = null;
	    Session session = null;
	    try 
		{     
				session=HibernateSessionFactory.getSession();
				String AccountIdlistQuery= "select " +
											"reg.org_name as companyName,  " +
											"reg.org_type as companyType,  " +
											"reg.industry as  industryParam ," +
										    "regAddress as companyRegAddress,  " +
										    "reg.city as companyRegCity ,    " +
										    "con.country as companyRegCountry , " +
										    "reg.pincode as companyRegPinCode , " +
										    "reg.contactNo as companyRegContact1," +
										    "clientinfo.username as userID," +
										    "clientinfo.pwd  as password," +
										    "ProI.validity_from as ValidityFrom, " +
										    "ProI.validity_to as validityto," +
										    "ProI.productcode as ProductCode, " +
										    "ProI.productuser as ProductUser," +
										    "reg.org_reg_name,reg.contact_emailid ,reg.contactNo,reg.country " +
										    "from client_info as clientinfo " +
										    "inner join product_intrested as ProI  on ProI.accountid=clientinfo.accountid and ProI.uuid=clientinfo.uid and ProI.confirm_key=clientinfo.confim_key " +
										    "inner join registation_sinup as reg  on reg.accountid=clientinfo.accountid and reg.uuid=clientinfo.uid and reg.confirm_key=clientinfo.confim_key " +
										    " inner join  _countries as con on con.ISO_Code=reg.country " +
										    " where clientinfo.id='"+Accountid+"' ";
				Query query =session.createSQLQuery(AccountIdlistQuery);
				AccountIdList=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {session.flush();session.close();}
		 return AccountIdList;
	    
		
	}
	
	public boolean getspaceid(int accountid)
	{
		List  AccountIdList = null;
		boolean flag=false;
	    Session session = null;
	    try 
		{     
	    	   
				session=HibernateSessionFactory.getSession();
				String AccountIdlistQuery= "select id,uid from client_info where id="+accountid+";";
				Query query =session.createSQLQuery(AccountIdlistQuery);
				AccountIdList=query.list();
				if(AccountIdList.size()>0)
				{
					flag=true;
				}
				else
				{
					flag=false;
				}
		}catch (Exception e) {e.printStackTrace();} 
		 finally {session.flush();session.close();}
		 return flag;
	    }
	
	public List SingleSpaceBlockUserInfo()
	{
		List  AccountIdList = null;
	    Session session = null;
	    try 
		{     
	    	   
				session=HibernateSessionFactory.getSession();
				String AccountIdlistQuery= "select  clientinfo.id,reg.org_reg_name,reg.org_name,con.Country,con.ISO_Code,clientinfo.isLive from client_info as clientinfo " +
										   " inner join registation_sinup as reg  on reg.accountid=clientinfo.accountid and reg.uuid=clientinfo.uid and reg.confirm_key=clientinfo.confim_key" +
										   " inner join  _countries as con on con.ISO_Code=reg.country";
				Query query =session.createSQLQuery(AccountIdlistQuery);
				AccountIdList=query.list();
		}catch (Exception e) {e.printStackTrace();} 
		 finally {session.flush();session.close();}
		 return AccountIdList;
	
	}
}
