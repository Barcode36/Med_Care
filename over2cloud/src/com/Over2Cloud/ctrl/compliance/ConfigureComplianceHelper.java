package com.Over2Cloud.ctrl.compliance;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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


public class ConfigureComplianceHelper 
{
	@SuppressWarnings("rawtypes")
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
			List <TableColumes> tableColume=new ArrayList<TableColumes>();
			for(GridDataPropertyView gdp:statusColName)
			{
				 TableColumes ob1=null;
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
}
