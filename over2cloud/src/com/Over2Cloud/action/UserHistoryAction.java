package com.Over2Cloud.action;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;

public class UserHistoryAction
{
	public boolean userHistoryAdd(String user,String curd, String module,String subModule,String actionDesc,String field,int actionID,SessionFactory connectionSpace)
	{
		boolean flag=false;
		try
		{
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
            InsertDataTable ob=null;
            
            ob=new InsertDataTable();
            ob.setColName("action_date");
            ob.setDataName(DateUtil.getCurrentDateUSFormat());
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("action_time");
            ob.setDataName(DateUtil.getCurrentTimeHourMin());
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("userName");
            ob.setDataName(user);
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("curd_operation");
            ob.setDataName(curd);
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("moduleName");
            ob.setDataName(module);
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("sub_module");
            ob.setDataName(subModule);
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("action_description");
            ob.setDataName(actionDesc);
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("field");
            ob.setDataName(field);
            insertData.add(ob);
            
            ob=new InsertDataTable();
            ob.setColName("actionid");
            ob.setDataName(actionID);
            insertData.add(ob);
            
            boolean status=cbt.insertIntoTable("user_action_history",insertData,connectionSpace);  
            if (status)
			{
				flag=true;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("rawtypes")
	public List fetchFields(String fieldValue, CommonOperInterface cbt,SessionFactory connectionSpace,String tableName)
	{
		List dataList=new ArrayList();
		try
		{
			String query="SELECT field_name,field_value FROM "+tableName+" WHERE field_value IN("+fieldValue+")";
		    dataList=cbt.executeAllSelectQuery(query, connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
}