package com.Over2Cloud.ctrl.helpdesk.EscalationConfig;

import java.util.ArrayList;
import java.util.List;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.action.GridPropertyBean;

@SuppressWarnings("serial")
public class MapShiftWIthWings extends GridPropertyBean {
private String shift_name;
private String fromShift;
private String toShift;
private String shiftemployeemappedwing;
private String deptId;
private String shiftID;
private String mployeemappedwing,empId;

public String addShiftWithWingMap() 
{
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
		
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			boolean status = createShiftTable(cbt);
			if (status) 
			{
				ob = new InsertDataTable();
				ob.setColName("shiftName");
				ob.setDataName(shift_name);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("fromShift");
				ob.setDataName(fromShift);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("toShift");
				ob.setDataName(toShift);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("dept_id");
				ob.setDataName(deptId);
				insertData.add(ob);
				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(userName);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);
				int shiftID=cbt.insertDataReturnId("shift_with_emp_wing", insertData, connectionSpace);
				insertData.clear();
				
				if (shiftID!=0)
				{
					ob = new InsertDataTable();
					ob.setColName("shiftId");
					ob.setDataName(shiftID);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("userName");
					ob.setDataName(userName);
					insertData.add(ob);
					
					ob = new InsertDataTable();
					ob.setColName("date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
					insertData.add(ob);
				
					if(shiftemployeemappedwing!=null && !shiftemployeemappedwing.equalsIgnoreCase(""))
					{
						String ab[] =shiftemployeemappedwing.split(",");
						if (ab!=null && !ab.equals("")) 
						{
							for (int i = 0; i < ab.length; i++) 
							{
								ob = new InsertDataTable();
								ob.setColName("empName");
								ob.setDataName(ab[i].split("#")[0]);
								insertData.add(ob);
								
								ob = new InsertDataTable();
								ob.setColName("wingsname");
								ob.setDataName(ab[i].split("#")[1]);
								insertData.add(ob);
								
								cbt.insertIntoTable("emp_wing_mapping", insertData, connectionSpace);
								insertData.remove(insertData.size()-1);
								insertData.remove(insertData.size()-1);
							}
						}
					}
					addActionMessage("Data saved sucessfully.");
				}
			}
			return SUCCESS;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SUCCESS;
		}
	}
	else
	{
		return LOGIN;
	}
}

public String addShiftWithWingMapSave(){
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = null;
			boolean status = createShiftTable(cbt);
			if (status) 
			{
				ob = new InsertDataTable();
				ob.setColName("shiftId");
				ob.setDataName(shiftID);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("userName");
				ob.setDataName(userName);
				insertData.add(ob);
				
				ob = new InsertDataTable();
				ob.setColName("date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat()+" "+DateUtil.getCurrentTimeHourMin());
				insertData.add(ob);
			
				if(shiftemployeemappedwing!=null && !shiftemployeemappedwing.equalsIgnoreCase(""))
				{
					String ab[] =shiftemployeemappedwing.split(",");
					if (ab!=null && !ab.equals("")) 
					{
						for (int i = 0; i < ab.length; i++) 
						{
							ob = new InsertDataTable();
							ob.setColName("empName");
							ob.setDataName(ab[i].split("#")[0]);
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("wingsname");
							ob.setDataName(ab[i].split("#")[1]);
							insertData.add(ob);
							cbt.insertIntoTable("emp_wing_mapping", insertData, connectionSpace);
							insertData.remove(insertData.size()-1);
							insertData.remove(insertData.size()-1);
						}
					}
				}
				else if(mployeemappedwing!=null && !mployeemappedwing.equalsIgnoreCase(""))
				{
					String ab[] =mployeemappedwing.split(",");
					if (ab!=null && !ab.equals("")) 
					{
						for (int i = 0; i < ab.length; i++) 
						{
							ob = new InsertDataTable();
							ob.setColName("empName");
							ob.setDataName(empId);
							insertData.add(ob);
							
							ob = new InsertDataTable();
							ob.setColName("wingsname");
							ob.setDataName(ab[i]);
							insertData.add(ob);
							cbt.insertIntoTable("emp_wing_mapping", insertData, connectionSpace);
							insertData.remove(insertData.size()-1);
							insertData.remove(insertData.size()-1);
						}
					}
				}
				addActionMessage("Data saved sucessfully.");
			}
			return SUCCESS;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SUCCESS;
		}
	}
	else
	{
		return LOGIN;
	}
}

private boolean createShiftTable(CommonOperInterface cbt) 
{
	boolean flag=false;
	List<TableColumes> tableColume = new ArrayList<TableColumes>();
	
	TableColumes ob1 = new TableColumes();
	
	ob1 = new TableColumes();
	ob1.setColumnname("shiftName");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	
	ob1 = new TableColumes();
	ob1.setColumnname("fromShift");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	
	ob1 = new TableColumes();
	ob1.setColumnname("toShift");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	
	ob1 = new TableColumes();
	ob1.setColumnname("dept_id");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	
	ob1 = new TableColumes();
	ob1.setColumnname("userName");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	
	ob1 = new TableColumes();
	ob1.setColumnname("date");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	
	flag=cbt.createTable22("shift_with_emp_wing", tableColume, connectionSpace);
	tableColume.clear();
	
	ob1 = new TableColumes();
	ob1.setColumnname("empName");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	
	ob1 = new TableColumes();
	ob1.setColumnname("wingsname");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	
	ob1 = new TableColumes();
	ob1.setColumnname("shiftId");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	
	ob1 = new TableColumes();
	ob1.setColumnname("userName");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	
	ob1 = new TableColumes();
	ob1.setColumnname("date");
	ob1.setDatatype("varchar(255)");
	ob1.setConstraint("default NULL");
	tableColume.add(ob1);
	
	cbt.createTable22("emp_wing_mapping", tableColume, connectionSpace);
	
	return flag;
}

public String getShift_name() {
	return shift_name;
}

public void setShift_name(String shift_name) {
	this.shift_name = shift_name;
}

public String getFromShift() {
	return fromShift;
}

public void setFromShift(String fromShift) {
	this.fromShift = fromShift;
}

public String getToShift() {
	return toShift;
}

public void setToShift(String toShift) {
	this.toShift = toShift;
}

public String getShiftemployeemappedwing() {
	return shiftemployeemappedwing;
}

public void setShiftemployeemappedwing(String shiftemployeemappedwing) {
	this.shiftemployeemappedwing = shiftemployeemappedwing;
}

public String getDeptId() {
	return deptId;
}

public void setDeptId(String deptId) {
	this.deptId = deptId;
}

public String getShiftID() {
	return shiftID;
}

public void setShiftID(String shiftID) {
	this.shiftID = shiftID;
}

public String getMployeemappedwing() {
	return mployeemappedwing;
}

public void setMployeemappedwing(String mployeemappedwing) {
	this.mployeemappedwing = mployeemappedwing;
}

public String getEmpId() {
	return empId;
}

public void setEmpId(String empId) {
	this.empId = empId;
}



}
