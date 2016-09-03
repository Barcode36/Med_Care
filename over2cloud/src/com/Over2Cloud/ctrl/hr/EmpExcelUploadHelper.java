package com.Over2Cloud.ctrl.hr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;

public class EmpExcelUploadHelper 
{
	Map session = ActionContext.getContext().getSession();
	String accountID=(String)session.get("accountid");
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	
	public int saveData(String mappedTable,String configTable,String dataTable,Map<String,String> dataWithColumnName)
	{

		List<GridDataPropertyView> statusColName=null;
		int maxId=0;
		statusColName=Configuration.getConfigurationData(mappedTable,accountID,connectionSpace,"",0,"table_name",configTable);
		if(statusColName!=null&&statusColName.size()>0)
		{
			boolean userTrue=false;
			boolean dateTrue=false;
			boolean timeTrue=false;
			InsertDataTable ob=null;
			//create table query based on configuration
			List <TableColumes> tableColume=new ArrayList<TableColumes>();
			
			for(GridDataPropertyView gdp:statusColName)
			{
				TableColumes ob1=new TableColumes();
				 ob1=new TableColumes();
				 ob1.setColumnname(gdp.getColomnName());
				 ob1.setDatatype("varchar(255)");
				 ob1.setConstraint("default NULL");
				 tableColume.add(ob1);
				 if(gdp.getColomnName().equalsIgnoreCase("userName"))
					 userTrue=true;
				 else if(gdp.getColomnName().equalsIgnoreCase("date"))
					 dateTrue=true;
				 else if(gdp.getColomnName().equalsIgnoreCase("time"))
					 timeTrue=true;
			}
			List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
			cbt.createTable22(dataTable,tableColume,connectionSpace);
			Iterator<Entry<String, String>> hiterator=dataWithColumnName.entrySet().iterator();
			while (hiterator.hasNext()) 
			{
				    Map.Entry<String, String> paramPair = (Entry<String, String>) hiterator.next();
				    ob=new InsertDataTable();
					ob.setColName(paramPair.getKey().toString());
					if(paramPair.getValue()!=null)
						ob.setDataName(paramPair.getValue().toString());
					
					insertData.add(ob);
			}
			 
			if(userTrue)
			{
				 ob=new InsertDataTable();
				 ob.setColName("userName");
				 ob.setDataName(userName);
				 insertData.add(ob);
			}
			if(dateTrue)
			{
				 ob=new InsertDataTable();
				 ob.setColName("date");
				 ob.setDataName(DateUtil.getCurrentDateUSFormat());
				 insertData.add(ob);
			}
			if(timeTrue)
			{
				 ob=new InsertDataTable();
				 ob.setColName("time");
				 ob.setDataName(DateUtil.getCurrentTime());
				 insertData.add(ob);
			}
			maxId=cbt.insertDataReturnId(dataTable,insertData,connectionSpace);
		}
 
		return maxId;
	}
	public String isExist(String tableName, List<String> colmName,Map<String, Object>wherClause)
	{
		String id=null;
		StringBuilder selectTableData = new StringBuilder("");  
		if(colmName!=null && colmName.size()>0)
		{
			selectTableData.append("select ");
			int i=1;
			for(String H:colmName)
			{
				if(i<colmName.size())
					selectTableData.append(H+" ,");
				else
					selectTableData.append(H+" from " +tableName);
				i++;
			}
		}
		else
		{
			selectTableData.append("select * from " +tableName);
		}
		if(wherClause!=null)
		{
			if(wherClause.size()>0)
			{
				selectTableData.append(" where ");
			}
			int size=1;
			Set set =wherClause.entrySet(); 
			Iterator i = set.iterator();
			while(i.hasNext())
			{ 
				Map.Entry me = (Map.Entry)i.next();
				if(size<wherClause.size())
					selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"' and ");
				else
					selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
				size++;
			} 
		}
		//List data=cbt.viewAllDataEitherSelectOrAll(tableName,colmName,wherClause);
		//System.out.println(selectTableData.toString());
		List data=cbt.executeAllSelectQuery(selectTableData.toString(),connectionSpace);
		try
		{
			if(data!=null && data.size()>0)
			{
				id=data.get(0).toString();
			}
		}
		catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		return id;
		
	}
}
