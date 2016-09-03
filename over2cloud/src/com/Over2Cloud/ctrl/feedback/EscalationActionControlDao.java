package com.Over2Cloud.ctrl.feedback;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;

public class EscalationActionControlDao
{
	
	public int getAdminDeptSubCatIdPos(SessionFactory session,String deptId)
	{
		int id=0;
		
		StringBuffer buffer=new StringBuffer("select feedback_subcategory.id from feedback_subcategory"
				+ " inner join feedback_category on feedback_category.id=feedback_subcategory.categoryName"
				+ " inner join feedback_type on feedback_type.id=feedback_category.fbType"
				+ " where moduleName='FM' and dept_subdept='"+deptId+"' and feedback_type.fbType='Appreciation' limit 1");
		
		System.out.println(">>>>> Positive QUERY >"+buffer);
			id=getAllSingleCounter(session,buffer.toString());
		return id;
	}
	
	public int getAdminDeptSubCatId(SessionFactory session,String deptId)
	{
		int id=0;
		
		StringBuffer buffer=new StringBuffer("select feedback_subcategory.id from feedback_subcategory"
				+ " inner join feedback_category on feedback_category.id=feedback_subcategory.categoryName"
				+ " inner join feedback_type on feedback_type.id=feedback_category.fbType"
				+ " where moduleName='FM' and dept_subdept='"+deptId+"' and feedback_type.fbType='Complaint' limit 1");
		System.out.println(">>>Negative Query >>>"+buffer);
		id=getAllSingleCounter(session,buffer.toString());
		return id;
	}
	
	public int getAdminDeptPosSubCatId(SessionFactory session,String deptId)
	{
		int id=0;
		
		StringBuffer buffer=new StringBuffer("select feedback_subcategory.id from feedback_subcategory"
				+ " inner join feedback_category on feedback_category.id=feedback_subcategory.categoryName"
				+ " inner join feedback_type on feedback_type.id=feedback_category.fbType"
				+ " where moduleName='FM' and dept_subdept='"+deptId+"'");
		
		id=getAllSingleCounter(session,buffer.toString());
		return id;
	}
	
	public int getAllSingleCounter(SessionFactory session,String qru)
	{
		int counter=0;
		List data=null;
		Session hSession=null;
		Transaction ts=null;
		try
		{
			hSession=session.openSession();
			ts=hSession.beginTransaction();
			data=hSession.createSQLQuery(qru).list();
			ts.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if(data!=null && data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					counter=Integer.parseInt(object.toString());
				}
			}
		}
		//System.out.println("Counter is as >>>>>"+counter);
		return counter;
	}
	
	public int feedStatusIdbyFeedDataId(SessionFactory session, String tableName,String colName1,String colVal1,String colName)
	{

		int id=0;
		List data=null;
		Session hSession=null;
		
		try
		{
			hSession=session.openSession();
			String qru="select "+colName+" from "+tableName+" where "+colName1+"='"+colVal1+"'";
		//	System.out.println("Querry is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+qru);
			data=hSession.createSQLQuery(qru).list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			hSession.flush();
			hSession.close();
		}
		
		if(data!=null && data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					id=Integer.parseInt(object.toString());
				}
			}
		}
		return id;
	
	}
	
	public boolean updateFeedStatForFeedback(SessionFactory session,String status,int id)
	{
		boolean flag=false;
		Session hSession=null;
		Transaction transaction = null;
		try
		{
			StringBuffer buffer=new StringBuffer("update feedback_status set status='"+status+"' where id='"+id+"'");
			hSession=session.openSession();
			transaction=hSession.beginTransaction();
		//	System.out.println("Querry is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+buffer.toString());
			org.hibernate.Query qry=hSession.createSQLQuery(buffer.toString());
			int count=qry.executeUpdate();
			transaction.commit();
		}
		catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			// TODO: handle exception
		}
		return flag;
	}
	
	public int dashboardTicketCounter(SessionFactory session,String status,String level,String via)
	{
		int counter=0;

		StringBuffer buffer=new StringBuffer("select count(*) from feedback_ticket as feedbt inner join  feedback_status as feedbck on feedbck.id=feedbt.feed_stat_id inner join feedbackdata as feedData on feedData.id=feedbt.feed_data_id where  feedbck.id!=0");
		
		
		if(status!=null && !(status.equalsIgnoreCase("")))
		{
			buffer.append(" and feedbck.status='"+status+"'");
		}
		
		if(level!=null && !(level.equalsIgnoreCase("")))
		{
			buffer.append(" and feedbck.level='"+level+"'");
		}
		
		if(via!=null && !(via.equalsIgnoreCase("")))
		{
			buffer.append(" and feedbt.feedTicketNo like '"+via+"%'");
		}
		
		
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		//System.out.println("Querry is a status : "+status+", VIA : "+via+" <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><"+buffer.toString());
		List data=cbt.executeAllSelectQuery(buffer.toString(), session);
		
		if(data!=null && data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					counter=Integer.parseInt(object.toString());
				}
			}
		}
		
		return counter;
	}
	
	public int dashboardCounter(SessionFactory session, String tableName,String colName1,String colVal1,String colName2,String colVal2)
	{

		int counter=0;
		List data=null;
		Session hSession=null;
		
		try
		{
			hSession=session.openSession();
			String qru="select count(*) from "+tableName+" where "+colName1+"='"+colVal1+"' && "+colName2+"='"+colVal2+"'";
		//	System.out.println("Querry Current Date Positive Counter <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+qru);
			data=hSession.createSQLQuery(qru).list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			hSession.flush();
			hSession.close();
		}
		
		if(data!=null && data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					counter=Integer.parseInt(object.toString());
				}
			}
		}
		return counter;
	
	}
	
	
	public List getDataForReport(SessionFactory session, String tableName,String columnName,String columnVal,String startDate,String endDate)
	{
		List l=new ArrayList();
		Session hSession=null;
		try
		{
			hSession=session.openSession();
			StringBuffer qru=new StringBuffer("select * from "+tableName+" where id!=0");
			if(columnVal!=null && (columnVal.equalsIgnoreCase("Yes") || columnVal.equalsIgnoreCase("No")))
			{
				qru.append(" && "+columnName+"='"+columnVal+"'");
			}
			
			if(startDate!=null && endDate!=null && !startDate.equalsIgnoreCase("") && !endDate.equalsIgnoreCase(""))
			{
				qru.append(" && date between '"+DateUtil.convertDateToUSFormat(startDate)+"' and '"+DateUtil.convertDateToUSFormat(endDate)+"'");
			}
		//	System.out.println("String Querry is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+qru.toString());
			l=hSession.createSQLQuery(qru.toString()).list();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally
		{
			hSession.flush();
			hSession.close();
		}
		
		return l;
	}

	public int getGraphDataCounter(SessionFactory session, String tableName,String colName,String colVal)
	{
		int counter=0;
		List data=null;
		Session hSession=null;
		
		try
		{
			hSession=session.openSession();
			String qru="select count(*) from "+tableName+" where "+colName+"='"+colVal+"'";
		//	System.out.println("Querry is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+qru);
			data=hSession.createSQLQuery(qru).list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			hSession.flush();
			hSession.close();
		}
		
		if(data!=null && data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					counter=Integer.parseInt(object.toString());
				}
			}
		}
		return counter;
	}
	
	
	public List<String> getGraphData(SessionFactory session, String tableName,String colName,String opr)
	{
		List<String> dataList=new ArrayList<String>();
		List data=null;
		Session hSession=null;
		
		try
		{
			hSession=session.openSession();
			String qru=opr+" distinct "+colName+" from "+tableName+" order by "+colName+" asc";
		//	System.out.println("Querry is as <>>>>>>>>>>>>>>>>>>>>>>>>"+qru);
			data=hSession.createSQLQuery(qru).list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			hSession.flush();
			hSession.close();
		}
		
		if(data!=null && data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					dataList.add(object.toString());
				}
			}
		}
		return dataList;
	}
	
	public String getL1EmpIdSubDept(SessionFactory session,String subDeptName)
	{
		String id=null;
		List data=null;
		Session hSession=null;
		try
		{
			hSession=session.openSession();
			StringBuffer buffer=new StringBuffer("select emp.id from employee_basic as emp inner join designation as desg on desg.id=emp.designation inner join subdepartment as subDept on subDept.id=emp.subdept where desg.levelofhierarchy='Level1' and subDept.subdeptname='"+subDeptName.trim()+"'");
			data=hSession.createSQLQuery(buffer.toString()).list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				hSession.flush();
				hSession.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(data!=null && data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					id=object.toString();
				}
			}
		}
		return id;
	}
	public List getAllSubDept(SessionFactory connectionSpace) {
		Session session = null;
		List list = new  ArrayList();
		try {
			session = connectionSpace.openSession();
			String query = "select id,subdeptname from subdepartment";
			list = session.createSQLQuery(query).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			session.close();
		}
		return list;
	}
	
	public int getOpenTicketStatus(SessionFactory connectionSpace, String date, String status, String subDept) {
		Session session = null;
		List list = new ArrayList();
		int i = 0;
		try {
			session = connectionSpace.openSession();
			String qurey = "select count(*) from feedback_status where open_date='"+date+"' and status='"+status+"' and to_dept_subdept='"+subDept+"'";
			list = session.createSQLQuery(qurey).list();
			
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				BigInteger object = (BigInteger) iterator.next();
				i = ((BigInteger)object).intValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			session.clear();
			session.close();
		}
		return i;
	}
}
