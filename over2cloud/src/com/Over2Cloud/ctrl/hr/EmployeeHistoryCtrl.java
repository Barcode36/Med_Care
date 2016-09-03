package com.Over2Cloud.ctrl.hr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EmployeeHistoryCtrl extends ActionSupport{

	static final Log log = LogFactory.getLog(EmployeeHistoryCtrl.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	String dbName=(String)session.get("Dbname");
	private String empName;
	private Map<String, String> empListData=new HashMap<String, String>();
	private Map<String, String> empListData1=new HashMap<String, String>();
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
			 addActionError("Ooops There Is Some Problem !");
			 return ERROR;
		}
		return SUCCESS;
	}
	
	public String swapEmp()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method swapEmp of class "+getClass(), e);
			 addActionError("Ooops There Is Some Problem In Sub-Department Creation!");
			 return ERROR;
		}
		return SUCCESS;
	}
	
	public String makeHistoryOfUser()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface commonOperVar=new CommonConFactory().createInterface();
			 Map<String, Object>wherClause=new HashMap<String, Object>();
			 Map<String, Object>condtnBlock=new HashMap<String, Object>();
			 wherClause.put("status", "1");
			 condtnBlock.put("id",getEmpName());
			 commonOperVar.updateTableColomn("employee_basic", wherClause, condtnBlock,connectionSpace);
			//insert data in the employee history data
			 createTableForHistory();
			 List <InsertDataTable> insertDataTemp=new ArrayList<InsertDataTable>();
			 InsertDataTable ob=new InsertDataTable();
			 ob.setColName("empId");
			 ob.setDataName(getEmpName());
			 insertDataTemp.add(ob);
			 ob=new InsertDataTable();
			 ob.setColName("userName");
			 ob.setDataName(userName);
			 insertDataTemp.add(ob);
			 ob=new InsertDataTable();
			 ob.setColName("date");
			 ob.setDataName(DateUtil.getCurrentDateUSFormat());
			 insertDataTemp.add(ob);
			 ob=new InsertDataTable();
			 ob.setColName("time");
			 ob.setDataName(DateUtil.getCurrentTime());
			 insertDataTemp.add(ob);
			 commonOperVar.insertIntoTable("emp_history",insertDataTemp,connectionSpace);
			 addActionMessage("User Moved To History!!!");
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method makeHistoryOfUser of class "+getClass(), e);
			 addActionError("Ooops There Is Some Problem In Sub-Department Creation!");
			 return ERROR;
		}
		return SUCCESS;
	}

	public String getAllSwapAndSwappedWithDataFromAction()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List empSelectParam=new ArrayList<String>();
			empSelectParam.add("id");
			empSelectParam.add("empName");
			Map<String, Object> temp=new HashMap<String, Object>();
				temp.put("status","1");
			empSelectParam=cbt.viewAllDataEitherSelectOrAll("employee_basic",empSelectParam,temp,connectionSpace);
			if(empSelectParam.size()>0)
			{
				for (Object c : empSelectParam) {
					Object[] dataC = (Object[]) c;
							empListData.put(Integer.toString((Integer)dataC[0]), dataC[1].toString());
				}
			}
			Map<String, Object> temp1=new HashMap<String, Object>();
			temp1.put("status","0");
			List empSelectParam1=new ArrayList<String>();
			empSelectParam1.add("id");
			empSelectParam1.add("empName");
			empSelectParam1=cbt.viewAllDataEitherSelectOrAll("employee_basic",empSelectParam1,temp1,connectionSpace);
			if(empSelectParam1.size()>0)
			{
				for (Object c : empSelectParam1) {
					Object[] dataC = (Object[]) c;
							empListData1.put(Integer.toString((Integer)dataC[0]), dataC[1].toString());
				}
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method getAllSwapAndSwappedWithDataFromAction of class "+getClass(), e);
			 e.printStackTrace();
			 addActionError("Ooops There Is Some Problem In Sub-Department Creation!");
			 return ERROR;
		}
		return SUCCESS;
	}
	
	public void createTableForHistory()
	{
		 CommonOperInterface cbt=new CommonConFactory().createInterface();
		 List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
		 TableColumes ob1=new TableColumes();
		 // Columes field 1
		 ob1=new TableColumes();
		 ob1.setColumnname("empId");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 // Columes field 2
		 ob1=new TableColumes();
		 ob1.setColumnname("userName");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		// Columes field 3
		 ob1=new TableColumes();
		 ob1.setColumnname("date");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		// Columes field 4
		 ob1=new TableColumes();
		 ob1.setColumnname("time");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 boolean status=cbt.createTable22("emp_history",Tablecolumesaaa,connectionSpace);
	}
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Map<String, String> getEmpListData() {
		return empListData;
	}

	public void setEmpListData(Map<String, String> empListData) {
		this.empListData = empListData;
	}

	public Map<String, String> getEmpListData1() {
		return empListData1;
	}

	public void setEmpListData1(Map<String, String> empListData1) {
		this.empListData1 = empListData1;
	}
	
}
