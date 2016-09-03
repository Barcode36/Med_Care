package com.Over2Cloud.ctrl.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class GroupActionCtrlhelper {

	public boolean createGroup(CommonOperInterface cbt,SessionFactory connectionSpace)
	{
		boolean status=false;
		List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
		 TableColumes ob1=new TableColumes();
		 ob1.setColumnname("grpName");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("grpDesc");
		 ob1.setDatatype("Text");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("grpLevel");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("userName");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("date");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("time");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 status= cbt.createTable22("groupInfo",Tablecolumesaaa,connectionSpace);
		 return status;
	}
	public boolean insertGroup(CommonOperInterface cbt,SessionFactory connectionSpace,String grpLevel,String grpName,String grpDesc,String userName)
	{
		 boolean status=false;
		 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		 InsertDataTable ob=new InsertDataTable();
		 ob.setColName("grpName");
		 ob.setDataName(grpName);
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("grpLevel");
		 ob.setDataName(grpLevel);
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("grpDesc");
		 ob.setDataName(grpDesc);
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("userName");
		 ob.setDataName(userName);
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("date");
		 ob.setDataName(DateUtil.getCurrentDateUSFormat());
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("time");
		 ob.setDataName(DateUtil.getCurrentTime());
		 insertData.add(ob);
		 
		status=cbt.insertIntoTable("groupInfo",insertData,connectionSpace);
		return status;
	}
	public Map<Integer, String> getAllgroupsWithUsermapped(CommonOperInterface cbt,SessionFactory connectionSpace,String userName)
	{
		Map<Integer, String> groupNames=new HashMap<Integer, String>();
		StringBuilder query=new StringBuilder("select id,grpName from groupInfo where userName='"+userName+"' or grpLevel='1'");
		List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(data!=null)
		{
			for(Iterator it=data.iterator(); it.hasNext();)
			{
				Object[] obdata=(Object[])it.next();
				if(obdata!=null && obdata[0]!=null && obdata[1]!=null)
					groupNames.put((Integer)obdata[0], obdata[1].toString());
				
			}
		}
		
		return groupNames;
	}
	
	public boolean createGroupMapping(CommonOperInterface cbt,SessionFactory connectionSpace)
	{
		boolean status=false;
		List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
		 TableColumes ob1=new TableColumes();
		 ob1.setColumnname("mappedid");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("grpid");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("mappedlevelid");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("userName");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("date");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 
		 ob1=new TableColumes();
		 ob1.setColumnname("time");
		 ob1.setDatatype("varchar(255)");
		 ob1.setConstraint("default NULL");
		 Tablecolumesaaa.add(ob1);
		 status= cbt.createTable22("groupInfomapping",Tablecolumesaaa,connectionSpace);
		 return status;
	}
	
	public boolean insertGroupMemberInfo(CommonOperInterface cbt,SessionFactory connectionSpace,
			String mappedid,String grpid,String mappedlevelid,String userName)
	{
		 boolean status=false;
		 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		 InsertDataTable ob=new InsertDataTable();
		 ob.setColName("mappedid");
		 ob.setDataName(mappedid);
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("grpid");
		 ob.setDataName(grpid);
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("mappedlevelid");
		 ob.setDataName(mappedlevelid);
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("userName");
		 ob.setDataName(userName);
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("date");
		 ob.setDataName(DateUtil.getCurrentDateUSFormat());
		 insertData.add(ob);
		 
		 ob=new InsertDataTable();
		 ob.setColName("time");
		 ob.setDataName(DateUtil.getCurrentTime());
		 insertData.add(ob);
		 
		status=cbt.insertIntoTable("groupInfomapping",insertData,connectionSpace);
		return status;
	}
	
	public static List<AllgroupView> getAllMembersOfSelectedGroup(CommonOperInterface cbt,SessionFactory connectionSpace,String grpID,String userName,String validApp)
	{
		List<AllgroupView>allGroupView=new ArrayList<AllgroupView>();
		StringBuilder query=new StringBuilder("");
		if(validApp!=null && !validApp.equalsIgnoreCase("") && grpID!=null && !grpID.equalsIgnoreCase(""))
		{
			query.append("select " +
					"gip.id,eb.empName,gip.userName,gip.date,gip.time,gp.grpLevel from employee_basic as eb inner join groupInfomapping as gip " +
					"on gip.mappedid=eb.id inner join groupInfo as gp on gip.grpid=gp.id where gp.id="+grpID+" and gip.mappedlevelid='1'");
			List data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					Object[] obdata=(Object[])it.next();
					if(obdata!=null && obdata[0]!=null && obdata[1]!=null && obdata[2]!=null
							&& obdata[3]!=null && obdata[4]!=null && obdata[5]!=null)
					{
						AllgroupView gp=new AllgroupView();
						gp.setId((Integer)obdata[0]);
						gp.setGrpDesc(obdata[1].toString());
						gp.setUserName(obdata[2].toString());
						if(gp.getUserName().equalsIgnoreCase(userName))
							gp.setUserTrue("true");
						else
							gp.setUserTrue("false");
						gp.setDate(DateUtil.convertDateToIndianFormat(obdata[3].toString()));
						gp.setTime(obdata[4].toString());
						if(obdata[5].toString().equalsIgnoreCase("1"))
						{
							gp.setGrpLevel("Public");
						}
						else
						{
							gp.setGrpLevel("Private");
						}
						allGroupView.add(gp);
					}
					
				}
			}
			if(validApp.contains("CIL"))//if client purchased the client app then he can map Lead in groups
			{
				try
				{
					query.delete(0, query.length());
					query.append("select gip.id,lg.leadName,gip.userName,gip.date,gip.time,gp.grpLevel" +
							" from leadgeneration as lg inner join groupInfomapping as gip on gip.mappedid=lg.id " +
							"inner join groupInfo as gp on gip.grpid=gp.id where gp.id="+grpID+" and gip.mappedlevelid='2'");
					data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null)
					{
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							if(obdata!=null && obdata[0]!=null && obdata[1]!=null && obdata[2]!=null
									&& obdata[3]!=null && obdata[4]!=null && obdata[5]!=null)
							{
								AllgroupView gp=new AllgroupView();
								gp.setId((Integer)obdata[0]);
								gp.setGrpDesc(obdata[1].toString());
								gp.setUserName(obdata[2].toString());
								if(gp.getUserName().equalsIgnoreCase(userName))
									gp.setUserTrue("true");
								else
									gp.setUserTrue("false");
								gp.setDate(DateUtil.convertDateToIndianFormat(obdata[3].toString()));
								gp.setTime(obdata[4].toString());
								if(obdata[5].toString().equalsIgnoreCase("1"))
								{
									gp.setGrpLevel("Public");
								}
								else
								{
									gp.setGrpLevel("Private");
								}
								allGroupView.add(gp);
							}
							
						}
					}
				}
				catch(Exception e)
				{
					
				}
			}
			if(validApp.contains("CM"))//if client purchased the client app then he can map clients in groups
			{
				try
				{
					query.delete(0, query.length());
					query.append("select gip.id,ccd.personName,gip.userName,gip.date,gip.time,gp.grpLevel" +
							" from client_contact_data as ccd inner join groupInfomapping as gip on gip.mappedid=ccd.id " +
							"inner join groupInfo as gp on gip.grpid=gp.id where gp.id="+grpID+" and gip.mappedlevelid='3'");
					data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null)
					{
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							if(obdata!=null && obdata[0]!=null && obdata[1]!=null && obdata[2]!=null
									&& obdata[3]!=null && obdata[4]!=null && obdata[5]!=null)
							{
								AllgroupView gp=new AllgroupView();
								gp.setId((Integer)obdata[0]);
								gp.setGrpDesc(obdata[1].toString());
								gp.setUserName(obdata[2].toString());
								if(gp.getUserName().equalsIgnoreCase(userName))
									gp.setUserTrue("true");
								else
									gp.setUserTrue("false");
								gp.setDate(DateUtil.convertDateToIndianFormat(obdata[3].toString()));
								gp.setTime(obdata[4].toString());
								if(obdata[5].toString().equalsIgnoreCase("1"))
								{
									gp.setGrpLevel("Public");
								}
								else
								{
									gp.setGrpLevel("Private");
								}
								allGroupView.add(gp);
							}
							
						}
					}
				}
				catch(Exception e)
				{
					
				}
			}
			if(validApp.contains("AM"))//if client purchased the client app then he can map associates in groups
			{
				
				try
				{
					query.delete(0, query.length());
					query.append("select gip.id,pa.name,gip.userName,gip.date,gip.time,gp.grpLevel" +
							" from prospectiveAssociateContact as pa inner join groupInfomapping as gip on gip.mappedid=pa.id " +
							"inner join groupInfo as gp on gip.grpid=gp.id where gp.id="+grpID+" and gip.mappedlevelid='4'");
					data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null)
					{
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							if(obdata!=null && obdata[0]!=null && obdata[1]!=null && obdata[2]!=null
									&& obdata[3]!=null && obdata[4]!=null && obdata[5]!=null)
							{
								AllgroupView gp=new AllgroupView();
								gp.setId((Integer)obdata[0]);
								gp.setGrpDesc(obdata[1].toString());
								gp.setUserName(obdata[2].toString());
								if(gp.getUserName().equalsIgnoreCase(userName))
									gp.setUserTrue("true");
								else
									gp.setUserTrue("false");
								gp.setDate(DateUtil.convertDateToIndianFormat(obdata[3].toString()));
								gp.setTime(obdata[4].toString());
								if(obdata[5].toString().equalsIgnoreCase("1"))
								{
									gp.setGrpLevel("Public");
								}
								else
								{
									gp.setGrpLevel("Private");
								}
								allGroupView.add(gp);
							}
							
						}
					}
				}
				catch(Exception e)
				{
					
				}
			}
		}
		return allGroupView;
	}
}
