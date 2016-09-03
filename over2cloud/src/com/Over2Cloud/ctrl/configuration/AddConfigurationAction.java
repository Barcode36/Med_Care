package com.Over2Cloud.ctrl.configuration;

/**
 * @author Pankaj
 * Class coded by pankaj for adding both type of configuration based on level wise configuration and the with out level wise configuration 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.BeanUtil.TableColumes;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddConfigurationAction extends ActionSupport {

	Map session = ActionContext.getContext().getSession();
	String accountID = (String) session.get("accountid");
	String dbName=(String)session.get("Dbname");
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	/** id name */
	private String id = null;
	/** titles name */
	private String titles = null;
	/** queryNames name */
	private String queryNames = null;
	/** configBean name */
	private String mapedtable = null;
	private String level = null;

	
	public String addconfigurationsliding() {
		
		if(userName==null || userName.equalsIgnoreCase(""))
			return LOGIN;
		String returnResult = ERROR;
		try {
			//System.out.println("getTitles"+getTitles());
			String[] ArrayList = getTitles().toString().split(",");
			StringBuilder StringObject = new StringBuilder();
			List<String> listdata = new ArrayList<String>();
			for (int i = 0; i < ArrayList.length; i++) {
				listdata.add(ArrayList[i]);
			}
			String lastEl = listdata.get(listdata.size() - 1);
			String[] Elsmnt = lastEl.toString().split("-");
			for (int i = 0; i < ArrayList.length; i++) {
				String[] Slitstr = ArrayList[i].toString().split("-");

				if (Elsmnt[1].toString()
						.equalsIgnoreCase(Slitstr[1].toString())) {
					StringObject.append(Slitstr[0] + "#");

				}
			}
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			TableColumes ob1 = new TableColumes();
			// Columes field 1
			ob1 = new TableColumes();
			ob1.setColumnname("mappedid");
			ob1.setDatatype("text");
			ob1.setConstraint("");
			Tablecolumesaaa.add(ob1);
			// Columes field 2
			ob1 = new TableColumes();
			ob1.setColumnname("table_name");// for seletected organization id based on configure level
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			// Columes field 4
			ob1 = new TableColumes();
			ob1.setColumnname("clientid");// for seletected organization id based on configure level
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			CommonforClassdata obhj = new CommonOperAtion();
			boolean status = obhj.Createtabledata(mapedtable.trim(),Tablecolumesaaa,connectionSpace);
			if(status)
			{
				//check wheather the table exist or not with the table name and mapped table name
				//fresh data entry
				CommonOperInterface commonOperVar=new CommonConFactory().createInterface();
				status=commonOperVar.checkExitOfTable(mapedtable.trim(),connectionSpace,"table_name",Elsmnt[1].toString());
				if(status==false)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = new InsertDataTable();
					ob.setColName("mappedid");
					ob.setDataName(StringObject.toString());
					insertData.add(ob);
					ob = new InsertDataTable();
					ob.setColName("table_name");
					ob.setDataName(Elsmnt[1].toString());
					insertData.add(ob);
					ob = new InsertDataTable();
					ob.setColName("clientid");
					ob.setDataName("Pankaj");
					insertData.add(ob);
					status = obhj.insertIntoTable(mapedtable.trim(), insertData,connectionSpace);
				}
				else
				{
					//update the exsiting data structure
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					wherClause.put("mappedid", StringObject.toString());
					//condtnBlock.put("id",getId());
					status=commonOperVar.updateTableColomn(mapedtable.trim(), wherClause, condtnBlock,connectionSpace);
					if (status) {
						addActionMessage("Data updated SuccessfUlly");
					} else {
						addActionMessage("Opps There is a problem");
					}
					return SUCCESS;
				}
			}
			
			if (status) {
				addActionMessage("Data added SuccessfUlly");
			} else {
				addActionMessage("Opps There is a problem");
			}
			returnResult = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}

	public String addLevelConfiguration() {
		
		if(userName==null || userName.equalsIgnoreCase(""))
			return LOGIN;
		
		String returnResult = ERROR;
		try {
			String[] ArrayList = getId().toString().split(",");
			StringBuilder StringObject = new StringBuilder();
			for (int i = 0; i < ArrayList.length; i++) {
				StringObject.append(ArrayList[i] + "#");
			}
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			TableColumes ob1 = new TableColumes();
			// Columes field 1
			ob1 = new TableColumes();
			ob1.setColumnname("orgLevel");
			ob1.setDatatype("varchar(255)");
			ob1.setConstraint("default NULL");
			Tablecolumesaaa.add(ob1);
			// Columes field 2
			ob1 = new TableColumes();
			ob1.setColumnname("propertyName");// for seletected organization id based on configure level
			ob1.setDatatype("text");
			ob1.setConstraint(" ");
			Tablecolumesaaa.add(ob1);
			// Columes field 4
			ob1 = new TableColumes();
			ob1.setColumnname("levelName");// for seletected organization id based on configure level
			ob1.setDatatype("text");
			ob1.setConstraint(" ");
			Tablecolumesaaa.add(ob1);

			CommonforClassdata obhj = new CommonOperAtion();
			boolean status = obhj.Createtabledata(mapedtable.trim(), Tablecolumesaaa,connectionSpace);

			if(status)
			{
				//adding the fresh entry
				status=new createTable().checkExitOfTable(mapedtable.trim(),connectionSpace,null,null);
				if(status==false)
				{
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					InsertDataTable ob = new InsertDataTable();
					ob.setColName("orgLevel");
					int id=Integer.parseInt(getLevel())+1;
					ob.setDataName(id);
					insertData.add(ob);
					ob = new InsertDataTable();
					ob.setColName("propertyName");
					ob.setDataName(null);
					ob = new InsertDataTable();
					ob.setColName("levelName");
					ob.setDataName(StringObject.toString());
					insertData.add(ob);
					status = obhj.insertIntoTable(mapedtable.trim(), insertData,connectionSpace);
				}
				else
				{
					//updating the exsiting structure
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					int id=Integer.parseInt(getLevel())+1;
					wherClause.put("orgLevel", id);
					wherClause.put("levelName", StringObject.toString());
					//condtnBlock.put("id",getId());
					status=new createTable().updateTableColomn(mapedtable.trim(), wherClause, condtnBlock,connectionSpace);
					if (status) {
						addActionMessage("Data updated SuccessfUlly");
					} else {
						addActionMessage("Opps There is a problem");
					}
					return SUCCESS;
				}
			}
			
			if (status) {
				addActionMessage("Data added SuccessfUlly");
			} else {
				addActionMessage("Opps There is a problem");
			}
			returnResult = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getQueryNames() {
		return queryNames;
	}

	public void setQueryNames(String queryNames) {
		this.queryNames = queryNames;
	}

	public String getMapedtable() {
		return mapedtable;
	}

	public void setMapedtable(String mapedtable) {
		this.mapedtable = mapedtable;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
