/*
 * @author Harit
 * Used for Compliance
 * 
 */
package com.Over2Cloud.ctrl.compliance;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;

public class ComplianceDBOperations {
	
	static final Log log = LogFactory.getLog(ConfigureComplianceAction.class);
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private List<GridDataPropertyView> masterViewProp=new ArrayList<GridDataPropertyView>();

	 @SuppressWarnings("rawtypes")
	public int getMaximumComplId()
	 {
		 int maxComplId = 0;
		 try{
			 	List complList=null;
			 	CommonOperInterface cbt=new CommonConFactory().createInterface();
			 	StringBuilder qryString=new StringBuilder();
			 	qryString.append("select max(id) from compliance_details");
			 	complList=cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
			 	for (int i = 0; i < complList.size(); i++) 
			 	{
				 	 maxComplId=(Integer) complList.get(0);
			 	}
		 	}
		 	catch(Exception exp)
		 	{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Over2Cloud  method getMaximumComplId of class "+getClass(), exp);
		 		exp.printStackTrace();
		 	}
		 	return maxComplId;
	 }
	
	 @SuppressWarnings("rawtypes")
	public String getSubDepartmentId(String empId)
	 {
		 String subDeptID=null;
		 List complList=null;
		 try
		 {
			 if(empId!=null)
			 {
				 CommonOperInterface cbt=new CommonConFactory().createInterface();
				 StringBuilder qryString=new StringBuilder();
				 qryString.append("select id,subdept from employee_basic where id");
				 qryString.append("=");
				 qryString.append("'");
				 qryString.append(empId);
				 qryString.append("'");
				 complList=cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
				 if(complList!=null && complList.size()>0)
					{
						for (Iterator iterator = complList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							if(object[1]!=null && object[1]!=null)
							{
								subDeptID=object[1].toString();
							}
						}
					}
				 return subDeptID;
			  
			 }
		 }
		 catch (Exception exp)
		 {
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Over2Cloud  method getSubDepartmentId of class "+getClass(), exp);
			 exp.printStackTrace();
		}
		 return subDeptID;
	 }
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Set<Integer> getCompliancesIdsByDate(String upCommingDuesType) 
	{
	 	boolean currentDateCompliance=false;
	 	boolean currentWeekCompliance=false;
	 	boolean currentMonthsCompliance=false;
		Set complSet=null;
	 	Set<Integer> ids=new TreeSet<Integer>();
		complSet = new TreeSet();
		List<String> msgIdList=new ArrayList<String>();
		String date=DateUtil.getCurrentDateIndianFormat();
	 	try
	 	{
	 		CommonOperInterface cbt=new CommonConFactory().createInterface();
		 	if(upCommingDuesType!=null && upCommingDuesType.equalsIgnoreCase("CurrentDay"))
		 	{
		 		currentDateCompliance=true;
		 	}
		 	else if(upCommingDuesType!=null && upCommingDuesType.equalsIgnoreCase("CurrentWeek"))
		 	{
		 		currentWeekCompliance=true;
		 	}
		 	else if(upCommingDuesType!=null && upCommingDuesType.equalsIgnoreCase("CurrentMonth"))
		 	{
		 		currentMonthsCompliance=true;
		 	}
		 	StringBuilder qryField=new StringBuilder();
		 	qryField.append("select id from compliance_details where dueDate");
		 	
			if(currentDateCompliance)
			{
				qryField.append("=");
				qryField.append("'");
				qryField.append(DateUtil.convertDateToUSFormat(date));
				qryField.append("'");
			}
			if (currentWeekCompliance) 
			{
				String weekDate=DateUtil.getNextDateAfter(6);
				qryField.append(" between ");
				qryField.append("'");
				qryField.append(DateUtil.convertDateToUSFormat(date));
				qryField.append("'");
				qryField.append(" and ");
				qryField.append("'");
				qryField.append(weekDate);
				qryField.append("'");
			}
			if (currentMonthsCompliance) 
			{
				if(DateUtil.getCurrentMonth()!=10 && DateUtil.getCurrentMonth()!=11 && DateUtil.getCurrentMonth()!=12)
				{
					qryField.append(" like ");
					qryField.append("'");
					qryField.append("%");
					qryField.append("-");
					qryField.append("0");
					qryField.append(DateUtil.getCurrentMonth());
					qryField.append("-");
					qryField.append("%");
					qryField.append("'");
				}
				else 
				{
					qryField.append(" like ");
					qryField.append("'");
					qryField.append("%");
					qryField.append("-");
					qryField.append(DateUtil.getCurrentMonth());
					qryField.append("-");
					qryField.append("%");
					qryField.append("'");
				}
			}
			msgIdList=cbt.executeAllSelectQuery(qryField.toString(), connectionSpace);
			complSet.addAll(msgIdList);
				
			if(complSet!=null && complSet.size()>0)
			{
				int compID = 0;
				for (Iterator iterator = complSet.iterator(); iterator.hasNext();)
				{
					String object = (String) iterator.next().toString();
					if(object!=null)
					{
						compID = Integer.valueOf(object);
					}
					else
					{
						compID = Integer.valueOf(object);
					}
					if(object!=null )
					{
						ids.add(compID);
					}
				}
			}
			return ids;
	 	}
	 	catch (Exception exp) 
	 	{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Over2Cloud  method getCompliancesIdsByDate of class "+getClass(), exp);
	 		exp.printStackTrace();
		}
	 	return ids;
    }
    
 @SuppressWarnings("rawtypes")
public List getComplianceByID(String cmplID)
 {
	 CommonOperInterface cbt=new CommonConFactory().createInterface();
	 List currentCompl=new ArrayList();
	 try
	 {
		 
		 StringBuilder qryString=new StringBuilder();
		 if(cmplID!=null)
		 {
			 qryString.append("Select id,taskName,taskBrief,reminder_for,frequency,remindMode from compliance_details");
			 qryString.append(" where ");
			 qryString.append(" id");
			 qryString.append("=");
			 qryString.append("'");
			 qryString.append(cmplID);
			 qryString.append("'");
			 currentCompl=cbt.executeAllSelectQuery(qryString.toString(),connectionSpace);
		 }
	 }
	 catch (Exception exp)
	 {
		log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Over2Cloud  method getCompliancesIdsByDate of class "+getClass(), exp);
		exp.printStackTrace();
	 }
	 return currentCompl;
 }
 

@SuppressWarnings("rawtypes")
public List getComplianceByIDForMail(String cmplID)
{
	 CommonOperInterface cbt=new CommonConFactory().createInterface();
	 List currentCompl=new ArrayList();
	 try
	 {
		 SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
		 
		 StringBuilder qryString=new StringBuilder();
		 if(cmplID!=null)
		 {
			 qryString.append("Select id,taskName,taskBrief,reminder_for,frequency,remindMode from compliance_details");
			 qryString.append(" where ");
			 qryString.append(" id");
			 qryString.append("=");
			 qryString.append("'");
			 qryString.append(cmplID);
			 qryString.append("'");
			 currentCompl=cbt.executeAllSelectQuery(qryString.toString(),connection);
		 }
	 }
	 catch (Exception exp)
	 {
		log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Over2Cloud  method getCompliancesIdsByDate of class "+getClass(), exp);
		exp.printStackTrace();
	 }
	 return currentCompl;
 }
 
//get compliance by ID
 @SuppressWarnings("rawtypes")
public List getComplianceByIDForMail(String cmplID,SessionFactory connection)
 {
	 CommonOperInterface cbt=new CommonConFactory().createInterface();
	 List currentCompl=new ArrayList();
	 try
	 {
		 StringBuilder qryString=new StringBuilder();
		 if(cmplID!=null)
		 {
			 qryString.append("Select id,taskName,taskBrief,reminder_for,frequency,remindMode from compliance_details");
			 qryString.append(" where ");
			 qryString.append(" id");
			 qryString.append("=");
			 qryString.append("'");
			 qryString.append(cmplID);
			 qryString.append("'");
			 currentCompl=cbt.executeAllSelectQuery(qryString.toString(),connection);
		 }
	 }
	 catch (Exception exp)
	 {
		log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Over2Cloud  method getCompliancesIdsByDate of class "+getClass(), exp);
		exp.printStackTrace();
	 }
	 return currentCompl;
 }
 
  public boolean updateSmsForCompliance(String complID,String remMsg,String schDate,String schTime,String multiTimeValue)
  {
	 boolean returnFlag=false;
	 try
	 {
		 HelpdeskUniversalHelper helpdeskHelper=new HelpdeskUniversalHelper();
		 Map<String, Object> setVariables=new HashMap<String, Object>();
		 Map<String, Object> whereCondition=new HashMap<String, Object>();
		 String takeADate=DateUtil.getCurrentDateUSFormat();
		 String takeATime=DateUtil.getCurrentTime();
		 whereCondition.put("id", complID);
		 setVariables.put("msg", remMsg);
		 setVariables.put("msg_date",DateUtil.convertDateToUSFormat(schDate));
		 setVariables.put("msg_time", schTime);
		 //setting frequency
		 if(multiTimeValue.equalsIgnoreCase("daily"))
		 {
			 setVariables.put("frequency", "D");	 
		 }
		 else if(multiTimeValue.equalsIgnoreCase("weekly") && multiTimeValue!=null)
		 {
			 setVariables.put("frequency", "W");	 
		 } 
		 else if(multiTimeValue.equalsIgnoreCase("monthly") && multiTimeValue!=null)
		 {
			 setVariables.put("frequency", "M");	 
		 } 
		 else if(multiTimeValue.equalsIgnoreCase("quarterly") && multiTimeValue!=null)
		 {
			 setVariables.put("frequency", "Q");	 
		 } 
		 else if(multiTimeValue.equalsIgnoreCase("halfEarly") && multiTimeValue!=null)
		 {
			 setVariables.put("frequency", "HY");	 
		 }
		 else if(multiTimeValue.equalsIgnoreCase("halfEarly") && multiTimeValue!=null)
		 {
			 setVariables.put("frequency", "Y");	 
		 } 
		 setVariables.put("date", takeADate);
		 setVariables.put("time", takeATime);
		 helpdeskHelper.updateTableColomn("schedule_msg", setVariables, whereCondition, connectionSpace);
	 }
	 catch(Exception exp)
	 {
		 log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Over2Cloud  method updateSmsForCompliance of class "+getClass(), exp);
		 exp.printStackTrace();
	 }
	 return returnFlag;
 }
 
 	@SuppressWarnings("rawtypes")
	public List getAllComplianceID(){
 		
 		List cmplID=null;
 		try 
 		{
 		 	CommonOperInterface cbt=new CommonConFactory().createInterface();
 			StringBuilder qryString=new StringBuilder();
 			qryString.append("select id from compliance_details");
 			cmplID=cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
		} 
 		catch (Exception exp) 
 		{
 			 log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " + "Problem in Over2Cloud  method getAllComplianceID of class "+getClass(), exp);
			exp.printStackTrace();
		}
		return cmplID;
 	}
 
 	@SuppressWarnings("rawtypes")
	public List getEmployeeDetails(String empID)
 	{
		
	 	CommonOperInterface cbt=new CommonConFactory().createInterface();
		List emplDetails=new ArrayList(); 
		try
		{
			if(empID!=null)
			{
				String queryIS="select id,empName,mobOne,emailIdOne from employee_basic where id='"+empID+"'";
				emplDetails=cbt.executeAllSelectQuery(queryIS, connectionSpace);
			}
		}
		catch (Exception exp) 
		{
			exp.printStackTrace();
		}
		return emplDetails;
	}
 	
 	@SuppressWarnings("rawtypes")
	public List getEmployeeDetailsFromContact(String empID){
		
	 	CommonOperInterface cbt=new CommonConFactory().createInterface();
	 	SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-1");
		List emplDetails=new ArrayList(); 
		try
		{
			if(empID!=null)
			{
				String queryIS="select id,mobileNo,emailId from compliance_contacts where id='"+empID+"'";
				emplDetails=cbt.executeAllSelectQuery(queryIS, connection);
			}
		}
		catch (Exception exp) 
		{
			exp.printStackTrace();
		}
		return emplDetails;
	}
 
 	
	@SuppressWarnings("rawtypes")
	public boolean updateTableColomnFromMail(String tableName,Map<String, Object>parameterClause,Map<String, Object>condtnBlock,SessionFactory connection)
	 {
		boolean Data=false;
		StringBuilder selectTableData = new StringBuilder("");  
		
		selectTableData.append("update " + tableName+" set ");
		int size=1;
		Set set =parameterClause.entrySet(); 
		Iterator i = set.iterator();
		while(i.hasNext())
		{ 
			Map.Entry me = (Map.Entry)i.next();
			if(size<parameterClause.size())
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"' , ");
			else
				selectTableData.append(me.getKey()+" = '"+me.getValue()+"'");
			size++;
		} 
		
		if(condtnBlock.size()>0)
		{
			selectTableData.append(" where ");
		}
		size=1;
		set =condtnBlock.entrySet(); 
		i = set.iterator();
		while(i.hasNext())
		{ 
			Map.Entry me = (Map.Entry)i.next();
			if(size<condtnBlock.size())
				selectTableData.append(me.getKey()+" = "+me.getValue()+" and ");
			else
				selectTableData.append(me.getKey()+" = "+me.getValue()+"");
			size++;
		} 
		selectTableData.append(";");
		Session session = null;  
		Transaction transaction = null;  
		try {  
	            session=connection.openSession();
				transaction = session.beginTransaction();  
				int count=session.createSQLQuery(selectTableData.toString()).executeUpdate();
				if(count>0)
					Data=true;
				transaction.commit(); 
			}catch (Exception ex) {transaction.rollback();} 
			finally{
				session.close();
			}
		return Data;
	}
 	
	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}
}
