package com.Over2Cloud.ctrl.feedback.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.opensymphony.xwork2.ActionSupport;

public class DashboardCounters extends GridPropertyBean{
/**
	 * 
	 */
	private static final long serialVersionUID = -5451825444982480834L;
private String deptId;
private String status;
private String mode;
private String period;
private String target;
private String dataFor;
private String fromDate;//to be merged 31-10-2014
private String toDate;//to be merged 31-10-2014
private String ratingFlag;
private List<GridDataPropertyView> masterViewProp;
private 	List<Object> 	masterViewList;

public String beforeCountersShow()
{
	boolean valid=ValidateSession.checkSession();
	if(valid)
	{
		try
		{
			return SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
	}
	else
	{
		return LOGIN;
	}
}
public String getFeedbackModeData()
{
	boolean valid=ValidateSession.checkSession();
	if(valid)
	{
		try
		{
			//System.out.println(">>>>>>>>>>>");
			getColumnNames();
			return SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
	}
	else
	{
		return LOGIN;
	}
}
@SuppressWarnings("unchecked")
private void getColumnNames() 
{
	try 
	{
		masterViewProp=new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("ticket_no");
		gpv.setHeadingName("Ticket No");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(80);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("clientId");
		gpv.setHeadingName("MRD No");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(60);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("patientId");
		gpv.setHeadingName("Patient ID");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(60);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("feed_by");
		gpv.setHeadingName("Patient Name");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(120);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("open_date");
		gpv.setHeadingName("Date");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("open_time");
		gpv.setHeadingName("Time");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("true");
		gpv.setWidth(80);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("location");
		gpv.setHeadingName("Room/Bed No");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(60);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("patMobNo");
		gpv.setHeadingName("Mobile No");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("patEmailId");
		gpv.setHeadingName("Email ID");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("allot_to");
		gpv.setHeadingName("Allot To");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(120);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("deptName");
		gpv.setHeadingName("Department");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("categoryName");
		gpv.setHeadingName("Category");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("subCategoryName");
		gpv.setHeadingName("Sub Category");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("status");
		gpv.setHeadingName("Status");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(60);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("patient_type");
		gpv.setHeadingName("Patient Type");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(40);
		masterViewProp.add(gpv);
		
		gpv = new GridDataPropertyView();
		gpv.setColomnName("admit_consultant");
		gpv.setHeadingName("Treating Doctor");
		gpv.setEditable("false");
		gpv.setSearch("true");
		gpv.setHideOrShow("false");
		gpv.setWidth(100);
		masterViewProp.add(gpv);
	
		/*List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_feedback_configuration",accountID,connectionSpace,"",0,"table_name","feedback_data_configuration");
		for(GridDataPropertyView gdp:returnResult)
		{
			if(gdp.getColomnName().equals("clientId") || gdp.getColomnName().equals("clientName") || gdp.getColomnName().equals("compType")
					|| gdp.getColomnName().equals("centerCode") || gdp.getColomnName().equals("mobNo") || gdp.getColomnName().equals("emailId") || gdp.getColomnName().equals("centreName") || gdp.getColomnName().equals("serviceBy")
					|| gdp.getColomnName().equals("level") )
			{
				gpv=new GridDataPropertyView();
				gpv.setColomnName(gdp.getColomnName());
				gpv.setHeadingName(gdp.getHeadingName());
				gpv.setEditable(gdp.getEditable());
				gpv.setSearch(gdp.getSearch());
				gpv.setHideOrShow(gdp.getHideOrShow());
				gpv.setWidth(gdp.getWidth());
				masterViewProp.add(gpv);
			}
			
		}*/
		session.put("masterViewProp", masterViewProp);
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
}
@SuppressWarnings({ "unchecked", "rawtypes" })
public String dataInGridDashboard()
{
	String returnResult=ERROR;
	boolean sessionFlag=ValidateSession.checkSession();
	if(sessionFlag)
	{
		try
		{
			List  data=new ArrayList();;
			List list=null;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List<Object> Listhb=new ArrayList<Object>();
			StringBuilder query1=new StringBuilder("");
			query1.append("select count(*) from feedback_status");
			List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			
			if(dataCount!=null && dataCount.size()>0)
			{
				List<GridDataPropertyView> fieldNames=(List<GridDataPropertyView>) session.get("masterViewProp");
				session.remove("masterViewProp");
				if((mode.equalsIgnoreCase("Paper") && ratingFlag.equalsIgnoreCase("1")) || ("109".equalsIgnoreCase(deptId) || "".equalsIgnoreCase(deptId)))
				{
					data.add(getDataForGrid("feedback_status_feed_paperRating",fieldNames,cbt,connectionSpace));
				}
				data.add(getDataForGrid("feedback_status",fieldNames,cbt,connectionSpace));
					String time="";
					if(data!=null && data.size()>0)
					{
						for(Iterator it1=data.iterator(); it1.hasNext();)
						{
							List dataList=(List) it1.next();
							for(Iterator it=dataList.iterator(); it.hasNext();)
							{
								int k=0;
								Object[] obdata=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for(GridDataPropertyView gpv:fieldNames)
								{
									if(obdata[k]!=null)
									{
										if(obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{
											
											if(obdata[k+1]!=null){
												time=obdata[k+1].toString().substring(0,5);
											}
											one.put(gpv.getColomnName(),DateUtil.convertDateToIndianFormat(obdata[k].toString())+", "+time);
										}
										else
										{
											one.put(gpv.getColomnName(),obdata[k].toString());
										}
									 }
									 else
									 {
										one.put(gpv.getColomnName().toString(), "NA");
									 }
									 k++;
								  }
								  Listhb.add(one);
							}
						  }
							setMasterViewList(Listhb);
							setRecords(masterViewList.size());
							int to = (getRows() * getPage());
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
					}
			returnResult=SUCCESS;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
}
else
{
	returnResult=LOGIN;
}
	return returnResult;
}


public List getDataForGrid(String tableName,List<GridDataPropertyView> fieldNames, CommonOperInterface cbt, SessionFactory connectionSpace){
	List list=null;
	StringBuilder query=new StringBuilder("");
	if(fieldNames!=null && fieldNames.size()>0)
	{
		query.append(" select feedbck.id,feedbck.ticket_no,feedbck.clientId,feedbck.patientId ,feedbck.feed_by,feedbck.open_date,feedbck.open_time,feedbck.location,feedbck.patMobNo,feedbck.patEmailId,emp.empName,dept.deptName,catg.categoryName,subcatg.subCategoryName,feedbck.status,patinfo.patient_type,patinfo.admit_consultant ");
		query.append(" from "+tableName+" AS feedbck ");
		query.append(" inner join patientinfo as patinfo on patinfo.cr_number=feedbck.clientId ");
		query.append(" inner join department dept on feedbck.to_dept_subdept= dept.id ");
		query.append(" inner join feedback_subcategory subcatg on feedbck.subcatg = subcatg.id ");
		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id ");
		query.append(" LEFT JOIN employee_basic as emp on emp.id=feedbck.allot_to ");
		query.append(" WHERE patinfo.patient_type='IPD' and feedbck.open_date between '"+DateUtil.convertDateToUSFormat(fromDate)+"' and '"+DateUtil.convertDateToUSFormat(toDate)+"' ");
		if(dataFor!=null && dataFor.equalsIgnoreCase("feedbackMode"))
		{	
			if(mode!=null)
			{
				query.append(" AND feedbck.via_from='"+mode+"' ");
			}
			if(status!=null )	
			{
				if(status.equalsIgnoreCase("Positive"))
				{
					query.append(" AND feedbck.escalation_date='NA' ");
				}
				else if(status.equalsIgnoreCase("Negative"))
				{
					query.append(" AND feedbck.escalation_date!='NA' ");
				}
				else
				{
					query.append(" AND feedbck.status='"+status+"' and feedbck.escalation_date!='NA'");
				}
			}
		}
		else if(dataFor!=null && dataFor.equalsIgnoreCase("deptAction"))
		{
			if(deptId!=null && !deptId.equalsIgnoreCase(""))
			{
				if(tableName.equalsIgnoreCase("feedback_status"))
				{
					query.append(" AND feedbck.to_dept_subdept="+deptId );
				}
				else
				{
					query.append(" AND feedbck.by_dept_subdept="+deptId );
				}
				
			}
			if(mode!=null && mode.equalsIgnoreCase("Action"))
			{
				query.append(" AND feedbck.status NOT IN ('Pending') AND feedbck.stage='2' ");
			}
			else if(mode!=null && mode.equalsIgnoreCase("Capa"))
			{
				query.append(" AND resolve_remark IS NOT NULL ");
			}
		}
		else if (status!=null)
		{
			if(mode!=null&&status.equalsIgnoreCase("Total Seek"))
			{
				query.append(" AND feedbck.via_from ='"+mode+"' and feedbck.status='pending' and subCatg='469'");
			}
			else if(mode!=null&&status.equalsIgnoreCase("Stage1Total"))
			{
				if(tableName.equalsIgnoreCase("feedback_status_feed_paperRating"))
				{
					query.append(" and feedbck.via_from ='"+mode+"'  and (feedbck.feed_brief  like '%Poor' or feedbck.feed_brief  like '%Average')");
				}
				else
				{
					query.append(" and feedbck.via_from ='"+mode+"' and feedbck.escalation_date!='NA' and feedbck.stage='1'");
				}
			}
			else if(mode!=null&&status.equalsIgnoreCase("Stage1Pending"))
			{
				if(tableName.equalsIgnoreCase("feedback_status_feed_paperRating"))
				{
					query.append(" and feedbck.via_from ='"+mode+"' and feedbck.status ='pending' and feedbck.stage='1' and (feedbck.feed_brief  like '%Poor' or feedbck.feed_brief  like '%Average')");
				}
				else
				{
					query.append(" and feedbck.via_from ='"+mode+"' and feedbck.status ='pending' and feedbck.escalation_date!='NA' and feedbck.stage='1'");
				}
			}
			else if(mode!=null&&status.equalsIgnoreCase("Stage1ActionTaken"))
			{
				if(tableName.equalsIgnoreCase("feedback_status_feed_paperRating"))
				{
					query.append(" and feedbck.via_from ='"+mode+"'  and feedbck.stage='2' ");
				}
				else
				{
					query.append(" and feedbck.via_from ='"+mode+"' and feedbck.status IN('Ticket Opened','No Further Action Required') and feedbck.escalation_date!='NA' ");
				}
			}
			else if(mode!=null&&status.equalsIgnoreCase("Stage1NoFurtherAction")&&ratingFlag.equalsIgnoreCase("1"))
			{
				if(tableName.equalsIgnoreCase("feedback_status_feed_paperRating"))
				{
					query.append(" and feedbck.via_from='"+mode+"' and feedbck.status='No Further Action Required' ");
				}
				else
				{
					query.append(" and feedbck.via_from='"+mode+"' and feedbck.status='No Further Action Required' and feedbck.escalation_date!='NA' ");
				}
			}
			else if(mode!=null&&status.equalsIgnoreCase("Stage2TicketOpened")&&ratingFlag.equalsIgnoreCase("0"))
			{
				query.append(" AND feedbck.via_from ='"+mode+"' and feedbck.status NOT IN('Ticket Opened','No Further Action Required')  and feedbck.escalation_date!='NA' and feedbck.stage='2' ");
			}
			else if(mode!=null&&status.equalsIgnoreCase("Stage2Pending")&&ratingFlag.equalsIgnoreCase("0"))
			{
				query.append(" AND feedbck.via_from='"+mode+"' and feedbck.escalation_date!='NA' and feedbck.status='Pending' and stage='2' ");
			}
			else if(mode!=null&&status.equalsIgnoreCase("Stage2ActionTaken")&&ratingFlag.equalsIgnoreCase("0"))
			{
				query.append(" AND feedbck.via_from='"+mode+"' and feedbck.escalation_date!='NA' and feedbck.status NOT IN('Pending','Ticket Opened','No Further Action Required')  and stage='2' ");
			}
		}
		else if(dataFor.equalsIgnoreCase("FeedbackAnalysis"))
		{
			if(mode!=null && !mode.equalsIgnoreCase("Suggestion"))
			{
				query.append(" AND feedtype.fbType='"+mode+"'");
			}
			else if(mode!=null)
			{
				query.append("  AND feedtype.fbType='"+mode+"'");
			}
		}
		query.append(" AND feedbck.moduleName='FM' group by feedbck.id ");
		//System.out.println("QUERY IS AS :::   "+query.toString());
		list=cbt.executeAllSelectQuery(query.toString(),connectionSpace );
	}
	return list;
}
public String getDeptId() {
	return deptId;
}
public void setDeptId(String deptId) {
	this.deptId = deptId;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getMode() {
	return mode;
}
public void setMode(String mode) {
	this.mode = mode;
}
public String getPeriod() {
	return period;
}
public void setPeriod(String period) {
	this.period = period;
}
public String getTarget() {
	return target;
}
public void setTarget(String target) {
	this.target = target;
}
public String getDataFor() {
	return dataFor;
}
public void setDataFor(String dataFor) {
	this.dataFor = dataFor;
}
public List<GridDataPropertyView> getMasterViewProp() {
	return masterViewProp;
}
public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
	this.masterViewProp = masterViewProp;
}
public List<Object> getMasterViewList() {
	return masterViewList;
}
public void setMasterViewList(List<Object> masterViewList) {
	this.masterViewList = masterViewList;
}
public String getFromDate() {
	return fromDate;
}
public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
}
public String getToDate() {
	return toDate;
}
public void setToDate(String toDate) {
	this.toDate = toDate;
}
public String getRatingFlag()
{
	return ratingFlag;
}
public void setRatingFlag(String ratingFlag)
{
	this.ratingFlag = ratingFlag;
}


}