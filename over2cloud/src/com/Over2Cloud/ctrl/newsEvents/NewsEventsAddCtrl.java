package com.Over2Cloud.ctrl.newsEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.NewsAlertsPojo;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class NewsEventsAddCtrl extends ActionSupport
{
	private String mainHeaderName;
	private String name;
	private String description;
	private String startDate;
	private String endDate;
	private String byDept;
	private String showFlag;
	private String severity;
	private String module_name;
	
	
	
	private Map<Integer,String> deptList;
	
	
	private List<NewsAlertsPojo> newsAlertsList;
	public String beforeNewsEventsAdd()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				setMainHeaderName("News and Events >> Add");
				deptList=new NewsAlertsHelper().getAllDepts(connectionSpace);
				
				
				
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
	
	public String addAlertsAndNews()
	{boolean valid=ValidateSession.checkSession();
	if(valid)
	{
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			Map session = ActionContext.getContext().getSession();
			String userName=(String)session.get("uName");
			String accountID=(String)session.get("accountid");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
			
			 TableColumes ob1=new TableColumes();
			 ob1=new TableColumes();
			 ob1.setColumnname("newsName");
			 ob1.setDatatype("varchar(255)");
			 ob1.setConstraint("default NULL");
			 Tablecolumesaaa.add(ob1);
			 
			 TableColumes ob2=new TableColumes();
			 ob2=new TableColumes();
			 ob2.setColumnname("newsDesc");
			 ob2.setDatatype("text");
			 ob2.setConstraint("default NULL");
			 Tablecolumesaaa.add(ob2);
			 
			 TableColumes ob3=new TableColumes();
			 ob3=new TableColumes();
			 ob3.setColumnname("startDate");
			 ob3.setDatatype("varchar (255)");
			 ob3.setConstraint("default NULL");
			 Tablecolumesaaa.add(ob3);
			 
			 TableColumes ob4=new TableColumes();
			 ob4=new TableColumes();
			 ob4.setColumnname("endDate");
			 ob4.setDatatype("varchar (255)");
			 ob4.setConstraint("default NULL");
			 Tablecolumesaaa.add(ob4);
			 
			 TableColumes ob5=new TableColumes();
			 ob5=new TableColumes();
			 ob5.setColumnname("byDept");
			 ob5.setDatatype("varchar (255)");
			 ob5.setConstraint("default NULL");
			 Tablecolumesaaa.add(ob5);
			 
			 
			 TableColumes ob6=new TableColumes();
			 ob6=new TableColumes();
			 ob6.setColumnname("showFlag");
			 ob6.setDatatype("varchar (255)");
			 ob6.setConstraint("default NULL");
			 Tablecolumesaaa.add(ob6);
			 
			 TableColumes ob7=new TableColumes();
			 ob7=new TableColumes();
			 ob7.setColumnname("severity");
			 ob7.setDatatype("varchar (255)");
			 ob7.setConstraint("default NULL");
			 Tablecolumesaaa.add(ob7);
			 
			 TableColumes ob8=new TableColumes();
			 ob8=new TableColumes();
			 ob8.setColumnname("userName");
			 ob8.setDatatype("varchar (255)");
			 ob8.setConstraint("default NULL");
			 Tablecolumesaaa.add(ob8);
			 
			 TableColumes ob9=new TableColumes();
			 ob9=new TableColumes();
			 ob9.setColumnname("date");
			 ob9.setDatatype("varchar (255)");
			 ob9.setConstraint("default NULL");
			 Tablecolumesaaa.add(ob9);
			 
			 
			 TableColumes ob10=new TableColumes();
			 ob10=new TableColumes();
			 ob10.setColumnname("time");
			 ob10.setDatatype("varchar (255)");
			 ob10.setConstraint("default NULL");
			 Tablecolumesaaa.add(ob10);
			 
			 TableColumes ob11=new TableColumes();
			 ob11=new TableColumes();
			 ob11.setColumnname("module_name");
			 ob11.setDatatype("varchar (255)");
			 ob11.setConstraint("default NULL");
			 Tablecolumesaaa.add(ob11);
			 
			 
			boolean tableExist= cbt.createTable22("newsandalerts",Tablecolumesaaa,connectionSpace);
			 
			if(tableExist && getName()!=null && getDescription()!=null && getStartDate()!=null && getEndDate()!=null)
			{
				List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
				
				InsertDataTable in1=new InsertDataTable();
				in1.setColName("newsName");
				in1.setDataName(getName());
				insertData.add(in1);
				
				InsertDataTable in2=new InsertDataTable();
				in2.setColName("newsDesc");
				
				
				InsertDataTable in3=new InsertDataTable();
				in3.setColName("startDate");
				in3.setDataName(DateUtil.convertDateToUSFormat(getStartDate()));
				insertData.add(in3);
				
				InsertDataTable in4=new InsertDataTable();
				in4.setColName("endDate");
				in4.setDataName(DateUtil.convertDateToUSFormat(getEndDate()));
				insertData.add(in4);
				
				InsertDataTable in6=new InsertDataTable();
				in6.setColName("showFlag");
				in6.setDataName(getShowFlag());
				insertData.add(in6);
				
				InsertDataTable in7=new InsertDataTable();
				in7.setColName("severity");
				in7.setDataName(getSeverity());
				insertData.add(in7);
				
				InsertDataTable in8=new InsertDataTable();
				in8.setColName("userName");
				in8.setDataName(userName);
				insertData.add(in8);
				
				InsertDataTable in9=new InsertDataTable();
				in9.setColName("date");
				in9.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(in9);
				
				InsertDataTable in10=new InsertDataTable();
				in10.setColName("time");
				in10.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(in10);
				
				
				InsertDataTable in11=new InsertDataTable();
				in11.setColName("module_name");
				in11.setDataName(getModule_name());
				insertData.add(in11);
				
				
				
				InsertDataTable in5=new InsertDataTable();
				in5.setColName("byDept");
				
				String deptArray[]=getByDept().split(",");
				//System.out.println("dept>>>"+deptArray.length);
				for (int i = 0; i < deptArray.length; i++) 
				{
					
					in2.setDataName(getDescription());
					if(insertData.contains(in2))
					{
						insertData.remove(in2);
						insertData.remove(in5);
					}
					insertData.add(in2);
					in5.setDataName(deptArray[i]);
					insertData.add(in5);
					tableExist=cbt.insertIntoTable("newsandalerts",insertData,connectionSpace);
				}
				
				 if(tableExist)
				 {
					 addActionMessage("News Alerts Configured Successfullly !!!");
				 }
			}
			else
			{
				 addActionMessage("Oops there is some problem !!!");
			}
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
	}}
	public String getMainHeaderName() {
		return mainHeaderName;
	}
	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<NewsAlertsPojo> getNewsAlertsList() {
		return newsAlertsList;
	}
	public void setNewsAlertsList(List<NewsAlertsPojo> newsAlertsList) {
		this.newsAlertsList = newsAlertsList;
	}
	public Map<Integer, String> getDeptList() {
		return deptList;
	}
	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}
	public String getByDept() {
		return byDept;
	}
	public void setByDept(String byDept) {
		this.byDept = byDept;
	}
	public String getShowFlag() {
		return showFlag;
	}
	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getModule_name() {
		return module_name;
	}

	public void setModule_name(String moduleName) {
		module_name = moduleName;
	}
	
}
