package com.Over2Cloud.ctrl.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class SeverityActionHelper 
{
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	public List getServiceDeptByModule(SessionFactory connection, String moduleName)
	{
		List dataList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("SELECT DISTINCT dept.id,dept.deptName FROM department AS dept");
			if(moduleName!=null && moduleName.equalsIgnoreCase("HDM"))
			{
				query.append(" LEFT JOIN subdepartment AS subdept ON subdept.deptid = dept.id");
				query.append(" LEFT JOIN feedback_type AS ftype ON ftype.dept_subdept = subdept.id");
				query.append(" WHERE ftype.moduleName='"+moduleName+"' ORDER BY dept.deptName");
			}
			System.out.println(query.toString());
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	
	public boolean saveData4Severity(Map<String, String> dataMap,SessionFactory connection,String[] severityLevelArr,String[] fromTimeArr,String[] toTimeArr,String deptId)
	{
		boolean status = false; 
		try
		{
			if (dataMap!=null && dataMap.size()>0)
			{
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				for(Map.Entry<String, String> entry : dataMap.entrySet())
				{
					ob = new InsertDataTable();
					ob.setColName(entry.getKey());
					ob.setDataName(entry.getValue());
					insertData.add(ob);
				}
				for (int i = 0; i < fromTimeArr.length; i++) 
				{
					if(fromTimeArr[i]!=null && !fromTimeArr[i].equals(" "))
					{
						ob = new InsertDataTable();
						ob.setColName("severityLevel");
						ob.setDataName(severityLevelArr[i].trim());
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("fromTime");
						ob.setDataName(fromTimeArr[i].trim());
						insertData.add(ob);
						
						ob = new InsertDataTable();
						ob.setColName("toTime");
						ob.setDataName(toTimeArr[i].trim());
						insertData.add(ob);
						List dataList = cbt.executeAllSelectQuery("SELECT id FROM severity_detail WHERE deptName="+deptId+" AND severityLevel="+severityLevelArr[i].trim(), connection);
						if(dataList==null || dataList.size()==0)
							status=cbt.insertIntoTable("severity_detail", insertData, connection);
						
						insertData.remove(insertData.size()-3);
						insertData.remove(insertData.size()-2);
						insertData.remove(insertData.size()-1);
					}
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return status;
	}
	
	
	public boolean createTable4Severity(SessionFactory connection)
	{
		boolean status = false;
		List<TableColumes> tableColume = new ArrayList<TableColumes>();
		try
		{
			TableColumes ob1 = new TableColumes();
			ob1.setColumnname("deptName");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("severityCheckOn");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("severityLevel");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("fromTime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("toTime");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			ob1 = new TableColumes();
			ob1.setColumnname("moduleName");
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
			
			ob1 = new TableColumes();
			ob1.setColumnname("time");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			tableColume.add(ob1);
			
			status = cbt.createTable22("severity_detail", tableColume, connection);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return status;
	}
}
