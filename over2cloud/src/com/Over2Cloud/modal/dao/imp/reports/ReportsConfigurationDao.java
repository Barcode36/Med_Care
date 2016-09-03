package com.Over2Cloud.modal.dao.imp.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hibernate.common.HibernateSessionFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.modal.dao.imp.Setting.SettingDAO;

public class ReportsConfigurationDao
{
	static final Log log = LogFactory.getLog(ReportsConfigurationDao.class);

	public String getSelectedDataFromTable(SessionFactory sessionFactory,String querry)
	{

		String result=null;
		List list=null;
		try
		{
			Session session=sessionFactory.openSession();
			org.hibernate.Transaction tx=session.beginTransaction();
			Query qry=session.createSQLQuery(querry);
			list=qry.list();
			tx.commit();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		if(list!=null && list.size()>0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					result=object.toString();
				}
				else
				{
					result="NA";
				}
			}
		}
		return result;
	}
	
	public String getVirtualNo(SessionFactory sessionFactory,String modName)
	{
		String virNo=null;
		List list=null;
		try
		{
			Session session=sessionFactory.openSession();
			org.hibernate.Transaction tx=session.beginTransaction();
			Query qry=session.createSQLQuery("select distinct mobNo from feedback_sms_config  limit 1");
			list=qry.list();
			tx.commit();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		if(list!=null && list.size()>0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					virNo=object.toString();
				}
				else
				{
					virNo="NA";
				}
			}
		}
		return virNo;
	}
	
	
	public String getKeyWord(SessionFactory sessionFactory,String modName,String type)
	{
		String keyWord=null;
		List list=null;
		try
		{
			Session session=sessionFactory.openSession();
			org.hibernate.Transaction tx=session.beginTransaction();
			Query qry=session.createSQLQuery("select keyword from feedback_sms_config where type='"+type+"'");
			list=qry.list();
			tx.commit();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		if(list!=null && list.size()>0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					keyWord=object.toString();
				}
				else
				{
					keyWord="NA";
				}
			}
		}
		return keyWord;
	}
	
	public String getOrganizationName(SessionFactory sessionFactory)
	{
		String orgName=null;
		List list=null;
		try
		{
			Session session=sessionFactory.openSession();
			org.hibernate.Transaction tx=session.beginTransaction();
			Query qry=session.createSQLQuery("select companyName from organization_level1");
			list=qry.list();
			tx.commit();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		if(list!=null && list.size()>0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					orgName=object.toString();
				}
				else
				{
					orgName="NA";
				}
			}
		}
		return orgName;
	}
	
	public String getColumnNamesById(String tableName,String id,SessionFactory sessionFactory)
	{
		String name=null;
		Session session=null;
		List l=null;
		try
		{
			session=sessionFactory.openSession();
			System.out.println("Querry is as <><><><><>"+"select field_value from "+tableName+" where id="+id+" and activeType='true'");
			Query query=session.createSQLQuery("select field_value from "+tableName+" where id="+id);
			l=query.list();
		}
		catch (Exception e) {
			System.out.println("Exception in class getColumnNamesById of class "+getClass());
			//e.printStackTrace();
		}
		finally
		{
			try
			{
				session.flush();
				session.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(l!=null && l.size()>0)
		{
			for (Iterator iterator = l.iterator(); iterator.hasNext();) {
				String object = (String) iterator.next();
				if(object!=null)
				{
					name=object.toString();
				}
			}
		}
		
		return name;
	}
	
	public String getSingleColumnValue(String getColumnVal ,String tableName,String columnName,String columnVal,SessionFactory session)
	{
		String val=null;
		Session sesion=null;
		List dataList=null;
		try
		{
			sesion=session.openSession();
			System.out.println(" Querry is as <>><<>><<><>"+"select "+getColumnVal+" from "+tableName+" where  "+columnVal+" = '"+columnName+"'");
			Query qry=sesion.createSQLQuery("select "+getColumnVal+" from "+tableName+" where  "+columnVal+" = '"+columnName+"'");
			dataList=qry.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				sesion.flush();
				sesion.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					val=object.toString();
				}
			}
		}
		
		return val;
	}
	
	public boolean isMappeIdExists(String tableName,String columnName,String columnVal,SessionFactory session)
	{
		boolean found=false;
		Session sesion=null;
		List dataList=null;
		try
		{
			sesion=session.openSession();
			Query qry=sesion.createSQLQuery("select count(*) from "+tableName+" where  "+columnVal+" in(select replace("+columnName+",'#',',') from "+tableName+" where id!=0)");
			dataList=qry.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				sesion.flush();
				sesion.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(dataList!=null)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					if(Integer.parseInt(object.toString())>0)
					{
						found=true;
					}
				}
			}
		}
		
		
		return found;
	}
	
	public String getTableColumnVal(String tableName,String getColumnVal ,String whereColumnName,String columnVal,SessionFactory session)
	{
		String value=null;
		Session sesion=null;
		List dataList=null;
		StringBuffer buffer=new StringBuffer("select "+getColumnVal+" from "+tableName);
		if(whereColumnName!=null && columnVal!=null)
		{
			buffer.append(" where "+whereColumnName+"='"+columnVal+"'");
		}
		try
		{
			sesion=session.openSession();
			Query qry=sesion.createSQLQuery(buffer.toString());
			System.out.println("Querry ius"+buffer.toString());
			dataList=qry.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				sesion.flush();
				sesion.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					value=object.toString();
				}
			}
		}
		return value;
	}
	
	public List<String> getFeedbackStatusColumnNames(String tableName,SessionFactory sessionFactory)
	{
		List<String> columnNameList=new ArrayList<String>();
		List dataList=null;
		Session session=null;
		try
		{
			session=sessionFactory.openSession();
			Query qry=session.createSQLQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+tableName+"'");
			
			dataList=qry.list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				session.flush();
				session.close();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				
				if(object!=null)
				{
			//		System.out.println("ColumnName is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+object.toString());
					columnNameList.add(object.toString());
				}
			}
		}
		
		return columnNameList;
	}
	
	
	
	public String getCoulmnIdsById(String id,SessionFactory sessionFactory)
	{
		String querryString=null;
		List dataList=null;
		Session session=null;
		try
		{
			session=sessionFactory.openSession();
			Query qry=session.createSQLQuery("select columnName from savedreports where id='"+id+"'");
			dataList=qry.list();
		}
		catch (Exception e) {
		//	e.printStackTrace();
		}
		finally
		{
			try
			{
				session.flush();
				session.close();
			}
			catch (Exception e) {
			//	e.printStackTrace();
			}
		}
		
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				
				if(object!=null)
				{
					querryString=object.toString();
				}
			}
		}
		return querryString;
	}
	
	public String getSavedQuerryById(String id,SessionFactory sessionFactory)
	{
		String querryString=null;
		List dataList=null;
		Session session=null;
		try
		{
			session=sessionFactory.openSession();
			Query qry=session.createSQLQuery("select querry from savedreports where id='"+id+"'");
			dataList=qry.list();
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally
		{
			try
			{
				session.flush();
				session.close();
			}
			catch (Exception e) {
			//	e.printStackTrace();
			}
		}
		
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				
				if(object!=null)
				{
					querryString=object.toString();
				}
			}
		}
		return querryString;
	}
	
	
	public List getSQLQuerryResult(String query,SessionFactory sessionFactory)
	{
		List dataList=null;
		Session session=null;
		try
		{
			session=sessionFactory.openSession();
			Query qry=session.createSQLQuery(query);
			System.out.println("Querry going to execute is as <>>>>>>>>>>>>>>>>>>>>>>>"+query);
			dataList=qry.list();
			
		}
		catch (Exception e) {
		//	e.printStackTrace();
		}
		finally
		{
			try
			{
				session.flush();
				session.close();
			}
			catch (Exception e) {
			//	e.printStackTrace();
			}
		}
		
		return dataList;
	}
	
	
	
	public String getColumnNamesById(String tableName,String id,SessionFactory sessionFactory,String columnName)
	{
		String name=null;
		Session session=null;
		List l=null;
		try
		{
			session=sessionFactory.openSession();
		//	System.out.println("Querry is as <><><><><>"+"select field_value from "+tableName+" where id="+id+" and activeType='true'");
			Query query=session.createSQLQuery("select "+columnName+" from "+tableName+" where id="+id);
		//	System.out.println("<<><><><><><><><<><><><"+"select field_value from "+tableName+" where id="+id);
			l=query.list();
		}
		catch (Exception e) {
			//System.out.println("Exception in class getColumnNamesById of class "+getClass());
		//	e.printStackTrace();
		}
		finally
		{
			try
			{
				session.flush();
				session.close();
			}
			catch (Exception e) {
			//	e.printStackTrace();
			}
		}
		
		if(l!=null && l.size()>0)
		{
			for (Iterator iterator = l.iterator(); iterator.hasNext();) {
				String object = (String) iterator.next();
				if(object!=null)
				{
					name=object.toString();
				}
			}
		}
		return name;
	}
	
	public String getColumnNamesAndIds(String confgTableName,SessionFactory sessionFactory)
	{
		String tableName=null;
		Session session=null;
		List l=null;
		try
		{
			session=sessionFactory.openSession();
			
			Query query=session.createSQLQuery("select table_name from "+confgTableName);
			System.out.println("Querry is as ><<><><><><><><>"+"select table_name from "+confgTableName);
			l=query.list();
		}
		catch (Exception e) {
			System.out.println("Exceptin in method getColumnNamesAndIds of class "+getClass());
		//	e.printStackTrace();
		}
		finally
		{
			try
			{
				session.flush();
				session.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(l!=null && l.size()>0)
		{
			for (Iterator iterator = l.iterator(); iterator.hasNext();) {
				String object = (String) iterator.next();
				if(object!=null)
				{
					tableName=object.toString();
				}
			}
		}
		return tableName;
	}
	
	public boolean isColumnNameExists(String tableName,String columnName,SessionFactory sessionFactory)
	{
		boolean flag=false;
		Session session=null;
		List list=null;
		try
		{
			session=sessionFactory.openSession();
			Query qry=session.createQuery("desc "+tableName);
			list=qry.list();
			
			/*
			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getColumns(null, null, "table_name", "column_name");
			if (rs.next()) {
			     //Column in table exist
			   }
			*/
		}
		catch (Exception e) {
			flag=false;
		//	e.printStackTrace();
		}
		finally
		{
			try
			{
				session.flush();
				session.close();
				flag=false;
			}
			catch (Exception e) {
			//	e.printStackTrace();
			}
		}
		
		if(list!=null)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				System.out.println("Object is as <>><<><><><><>"+object.toString());
			}
		}
		
		
		
		return flag;
	}
	
	public String getRealColumnName(String tableName,String normsColName,SessionFactory sessionFactory)
	{
		String colName=null;
		Session session=null;
		List l=null;
		try
		{
			session=sessionFactory.openSession();
			Query query=session.createSQLQuery("select "+normsColName+" table_name from "+tableName);
			l=query.list();
		}
		catch (Exception e) {
		//	e.printStackTrace();
		}
		finally
		{
			try
			{
				session.flush();
				session.close();
			}
			catch (Exception e) {
				//e.printStackTrace();
			}
		}
		
		if(l!=null && l.size()>0)
		{
			for (Iterator iterator = l.iterator(); iterator.hasNext();) {
				String object = (String) iterator.next();
				if(object!=null)
				{
					tableName=object.toString();
				}
			}
		}
		
		return colName;
	}
	
	public static void main(String args[])
	{
		String str="select address,ccontactNo,city,designation,doj_date,emailIdTwo,empId,userName from employee_basic";
		
		int lastIndex=str.lastIndexOf(" ");
		
		
		System.out.println("Last Index is as <>><<><><>"+lastIndex);
		
		System.out.println("Table Name is as <>>>>>>>>>>>>>>>>>>>>>  "+str.substring(lastIndex,str.length()).trim());
	}
}
