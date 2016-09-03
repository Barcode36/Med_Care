package com.Over2Cloud.ctrl.hr;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MapEmployee extends ActionSupport{

	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private Map<String, String> empListData=new HashMap<String, String>();
	private String emp1;
	private String emp2;
	static final Log log = LogFactory.getLog(MapEmployee.class);
	
	public String execute()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeEmployeeMap()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select id,empName from employee_basic");
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null && data.size()>0)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					 Object[] obdata=(Object[])it.next();
					 empListData.put(obdata[0].toString(),obdata[1].toString());
				}
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeEmployeeMap of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String mapEmployee()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			query.append("select useraccountid from employee_basic where id="+getEmp1());
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			String empUserId=null;
			if(data!=null && data.size()>0)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					 Object obdata=(Object)it.next();
					 if(obdata!=null)
						 empUserId=obdata.toString();
				}
				if(empUserId!=null && !empUserId.equalsIgnoreCase(""))
				{
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					wherClause.put("mapwith", empUserId);
					condtnBlock.put("id",getEmp2());
					cbt.updateTableColomn("employee_basic", wherClause, condtnBlock,connectionSpace);
					addActionMessage("Employee Mapped Successfully!");
				}
				else
				{
					addActionMessage("Mapped With Employee User Dose Not Exist!!!");
				}
				
			}
			else
			{
				addActionMessage("Mapped With Employee User Dose Not Exist!!!");
			}
			
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method mapEmployee of class "+getClass(), e);
			 e.printStackTrace();
			 addActionMessage("Oops there is some problem!!!");
		}
		return SUCCESS;
	}

	public Map<String, String> getEmpListData() {
		return empListData;
	}

	public void setEmpListData(Map<String, String> empListData) {
		this.empListData = empListData;
	}

	public String getEmp1() {
		return emp1;
	}

	public void setEmp1(String emp1) {
		this.emp1 = emp1;
	}

	public String getEmp2() {
		return emp2;
	}

	public void setEmp2(String emp2) {
		this.emp2 = emp2;
	}
	
}
