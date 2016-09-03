package com.Over2Cloud.ctrl.helpdesk.activityboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

 



import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class MorningReport {
	
	FeedbackPojo fstatus;
	@SuppressWarnings("unchecked")
	public void getMorningReportToData( String date,SessionFactory connection,HelpdeskUniversalHelper HUH,CommunicationHelper CH)
	 {
		
		String mailSendDate = null,mailSendTime=null,ids=null;
		List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String querys="SELECT date,time,id FROM compliance_todayTask_config WHERE moduleName='HDM' AND date='"+date+"'";
		//System.out.println("Query is "+querys);
		List dataList = cbt.executeAllSelectQuery("SELECT date,time,id FROM compliance_todayTask_config WHERE moduleName='HDM' AND date='"+date+"'", connection);
		
		////System.out.println("Size  "+dataList.size()+"  "+date);
		if(dataList==null || dataList.size()==0)
		{
			InsertDataTable ob = null;
			boolean status = false;
			TableColumes ob1 = null;
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			ob1 = new TableColumes();
			ob1.setColumnname("date");
			ob1.setDatatype("varchar(255)");
			Tablecolumesaaa.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			Tablecolumesaaa.add(ob1);

			ob1 = new TableColumes();
			ob1.setColumnname("moduleName");
			ob1.setDatatype("varchar(255)");
			Tablecolumesaaa.add(ob1);

			cbt.createTable22("compliance_todayTask_config", Tablecolumesaaa, connection);
			
			
			 		}
		else if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
				{
					mailSendDate = object[0].toString();
					mailSendTime = object[1].toString();
					ids = object[2].toString();
				}
			}
			if(mailSendDate!=null && mailSendTime!=null)
			{
				//System.out.println(mailSendDate+"  "+mailSendTime);
				
				boolean status = DateUtil.compareDateTime(DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimeHourMin(), mailSendDate + " " + mailSendTime);
				if (!status)
				{
					Map<String, Object> setVariables = new HashMap<String, Object>();
					Map<String, Object> whereCondition = new HashMap<String, Object>();
					whereCondition.put("id", ids);
					setVariables.put("date", DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat()));
					boolean flag = cbt.updateTableColomn("compliance_todayTask_config", setVariables, whereCondition, connection);
					if(flag==false)
					{
						mailSendDate = null;
						mailSendTime = null;
					}
				}
				else
				{
					mailSendDate = null;
					mailSendTime = null;
				}
				
			}
		}
if(mailSendDate!=null && mailSendTime!=null)
{
		
		List morninglist=new ArrayList();
		List  reportdatalist = new ArrayList();
		List  reportdatalist1 = new ArrayList();
		StringBuffer query= new StringBuffer();
	 	query.append("SELECT DISTINCT emp.id,emp.emailIdOne,emp.empName,dept.deptName FROM compliance_contacts AS contact");
		query.append(" INNER JOIN employee_basic AS emp ON emp.id=contact.emp_id");
		query.append(" INNER JOIN feedback_status AS complaint ON complaint.to_dept_subdept=contact.forDept_id");
		query.append(" INNER JOIN subdepartment AS subdept ON subdept.id = complaint.to_dept_subdept");
		query.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid");
		query.append(" WHERE complaint.open_date!='"+date+"' AND complaint.status!='Resolved' AND complaint.status!='Ignore' AND complaint.status!='Transfer'");
		query.append(" AND contact.moduleName='HDM' AND complaint.moduleName='HDM' ORDER BY emp.id");
		Session session = null;  
	    Transaction transaction = null;  
	    String id=null,emailid=null,empName=null,departmnt=null;
	    try 
		  {  
			 session=connection.openSession(); 
			 reportdatalist=session.createSQLQuery(query.toString()).list();  
			 	for (Iterator iterator = reportdatalist.iterator(); iterator.hasNext();)
				{
			 		 StringBuffer query1= new StringBuffer();
					 StringBuffer qury= new StringBuffer();
			 		 StringBuffer query2= new StringBuffer();
				     morninglist.clear();
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null)
					{
						id = object[0].toString();
				 	}
					
					if (object[1] != null)
					{
						emailid = object[1].toString();
					}
					if (object[2] != null)
					{
						empName = object[2].toString();
					}
					if (object[3] != null)
					{
						departmnt = object[3].toString();
					}
					
					
					 
						
					query1.append("SELECT DISTINCT contact.forDept_id,contact.emp_id FROM compliance_contacts AS contact");
					query1.append(" INNER JOIN employee_basic AS emp ON emp.id=contact.emp_id");
					query1.append(" INNER JOIN feedback_status AS complaint ON complaint.to_dept_subdept=contact.forDept_id");
					query1.append(" INNER JOIN subdepartment AS subdept ON subdept.id = complaint.to_dept_subdept");
					query1.append(" INNER JOIN department AS dept ON dept.id = subdept.deptid");
					query1.append(" WHERE complaint.open_date!='"+date+"' AND complaint.status!='Resolved' AND complaint.status!='Ignore' AND complaint.status!='Transfer'");
					query1.append(" AND contact.moduleName='HDM' AND complaint.moduleName='HDM' AND emp.id='"+id+"' ORDER BY emp.empName");
					reportdatalist1=session.createSQLQuery(query1.toString()).list();  
				 	 for (Iterator iterator1 = reportdatalist1.iterator(); iterator1.hasNext();)
				 	 {
				 		 Object[] object1 = (Object[]) iterator1.next();
				 		 qury.append(object1[0].toString()+",");
				 	 }
				 	 String forDept_id=qury.substring(0, qury.length()-1);
					 
				 	query2.append("SELECT fstatus.ticket_no,fstatus.open_date,fstatus.open_time,fstatus.feed_by,deptby.deptName AS bydepart,fCat.categoryName,fsubCat.subCategoryName,subdept.subdeptname,emp1.empName,fstatus.status,fstatus.location");
					query2.append(" FROM feedback_status as fstatus");
					query2.append(" LEFT JOIN  employee_basic  AS emp1  ON  emp1.id =fstatus.allot_to"); 
					query2.append(" LEFT JOIN  department  AS deptby  ON  deptby.id =fstatus.by_dept_subdept");
				 	query2.append(" LEFT JOIN  subdepartment AS subdept ON subdept.id =fstatus.to_dept_subdept");
					
				 	query2.append(" LEFT JOIN  feedback_subcategory  AS fsubCat  ON  fsubCat.id =fstatus.subCatg");
					query2.append(" LEFT JOIN  feedback_category  AS fCat  ON  fCat.id =fsubCat.categoryName");
					query2.append(" WHERE to_dept_subdept IN("+forDept_id+") AND level IN('Level1','Level2', 'Level3', 'Level4', 'Level5')AND open_date!='"+date+"' AND status!='Resolved' AND status!='Ignore' AND status!='Transfer' AND moduleName='HDM'");
					//System.out.println("*****query 2 : "+query2);
				 	reportdatalist1=session.createSQLQuery(query2.toString()).list();  
				 	 for (Iterator iterator1 = reportdatalist1.iterator(); iterator1.hasNext();)
				 	 {
						  		fstatus=new FeedbackPojo();
						  		Object[] object1 = (Object[]) iterator1.next();
						  			fstatus.setTicket_no((object1[0]).toString());
							 	if (object1[1] != null)
							 		fstatus.setOpen_date(DateUtil.convertDateToUSFormat(object1[1].toString()));  
							 	if (object1[2] != null)
							 		fstatus.setOpen_time( (object1[2]).toString());
							 	if (object1[3] != null)
							 		fstatus.setFeed_by( (object1[3]).toString());
							 	if (object1[4] != null)
							 		fstatus.setFeedback_by_dept( (object1[4]).toString());
							 	if (object1[5] != null)
							 		fstatus.setFeedback_catg( (object1[5]).toString());
							 	if (object1[6] != null)
							 		fstatus.setFeedback_subcatg( (object1[6]).toString());
							 	if (object1[7] != null)
							 		fstatus.setFeedback_to_dept( (object1[7]).toString());
							 	if (object1[8] != null)
							 		fstatus.setEmpName( (object1[8]).toString());
							 	if (object1[9] != null)
							 		fstatus.setStatus( (object1[9]).toString());
							 	if (object1[10] != null)
							 		fstatus.setLocation((object1[10]).toString());
							 	
							 	morninglist.add(fstatus);
				 	 }
				 	 String mailtext = HUH.getConfigMailForMorningReport(morninglist,empName);
				 	 CH.addMail(empName, departmnt, emailid, "MorningReports", mailtext, "MorningReports", "Pending", "0", "", "HDM", connection);
				}
		  	}
	    	catch (Exception ex)
	    	{
	    		ex.printStackTrace();
	    	} 
			finally
			{
				try 
				{
					session.flush();
					session.close();
				} 
			catch (Exception e) {}
			}
	 }
	}
}