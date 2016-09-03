package com.Over2Cloud.ctrl.group;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.homepage.HomePageActionCtrl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GroupActionCtrl extends ActionSupport{

	static final Log log = LogFactory.getLog(GroupActionCtrl.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String grpLevel;
	private String grpName;
	private String grpDesc;
	private Map<Integer, String> groupNames;
	private Map<Integer, String> mappingType;
	String validApp=(String)session.get("validApp");
	private String mappingTypeSelected;
	private String grpId;
	private Map<Integer, String> mappedData;
	private String gid;
	private String grpNameToBemapped;
	private List<GroupPojoBean>mappedInGroups;
	private String to;
	private String grpMappedID;
	private List<AllgroupView>allGroupView;
	private String empIdofuser=(String)session.get("empIdofuser");//o-100000
	private String grpID;
	private String searchedData;
	private static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private Map<Integer, String> groupMap;
	
	
	public String getSearchedData() {
		return searchedData;
	}

	public void setSearchedData(String searchedData) {
		this.searchedData = searchedData;
	}

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
	
	public String createGroup()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			GroupActionCtrlhelper gah=new GroupActionCtrlhelper();
			boolean status=gah.createGroup(cbt, connectionSpace);
			if(status)
			{
				status=gah.insertGroup(cbt, connectionSpace, getGrpLevel(), getGrpName(), getGrpDesc(), userName);
			}
			if(status)
				addActionMessage("Group created successfully!");
			else
				addActionError("Their is some problem in group creation!!!");
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method createGroup of class "+getClass(), e);
			 e.printStackTrace();
			 addActionError("Their is some problem in group creation!!!");
		}
		return SUCCESS;
	}

	
	public String beforeAllGroups()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			allGroupView=new ArrayList<AllgroupView>();
			String tempempIdofuser[]=empIdofuser.split("-");
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("select id,grpName,grpDesc,userName from groupInfo where userName='"+userName+"' or grpLevel=1");
			if(getSearchedData()!=null && !getSearchedData().equalsIgnoreCase("") && !getSearchedData().equalsIgnoreCase("null"))
			{
				query.append(" and grpName like '%"+getSearchedData()+"%'");
			}
			List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(data!=null)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					Object[] obdata=(Object[])it.next();
					if(obdata!=null && obdata[0]!=null && obdata[1]!=null && obdata[3]!=null)
					{
						AllgroupView alv=new AllgroupView();
						alv.setId((Integer)obdata[0]);
						alv.setGrpName(obdata[1].toString());
						if(obdata[2]!=null)
						{
							alv.setGrpDesc(obdata[2].toString());
						}
						else
						{
							alv.setGrpDesc("No description available");
						}
						if(obdata[3].toString().equalsIgnoreCase(userName))
						{
							alv.setMemberShipType("Owner");
						}
						else
						{
							query.delete(0, query.length());
							query.append("select count(*) from groupInfomapping where grpid="+alv.getId()+" " +
									"and mappedlevelid='1' and mappedid='"+tempempIdofuser[1]+"'");
							List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
							if(dataCount!=null)
							{
								BigInteger count=new BigInteger("3");
								for(Iterator it1=dataCount.iterator(); it1.hasNext();)
								{
									 Object obdataTemp=(Object)it1.next();
									 count=(BigInteger)obdataTemp;
								}
								if(count.intValue()>0)
								{
									alv.setMemberShipType("Member");
								}
								else
								{
									alv.setMemberShipType("No Membership");
								}
							}
							
						}
						alv.setUserName(obdata[3].toString());
						query.delete(0, query.length());
						int countTemp=0;
						try
						{
							query.append("select count(*) from groupInfomapping where grpid="+alv.getId());
							List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
							if(dataCount!=null)
							{
								BigInteger count=new BigInteger("3");
								for(Iterator it1=dataCount.iterator(); it1.hasNext();)
								{
									 Object obdataTemp=(Object)it1.next();
									 count=(BigInteger)obdataTemp;
									 countTemp=count.intValue();
								}
							}
						}
						catch(Exception e)
						{
							
						}
						alv.setTotalMember(countTemp);
						allGroupView.add(alv);
					}
					
				}
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeAllGroups of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String userGroups()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			GroupActionCtrlhelper gah=new GroupActionCtrlhelper();
			groupNames=gah.getAllgroupsWithUsermapped(cbt, connectionSpace, userName);
			if(validApp!=null && !validApp.equalsIgnoreCase(""))
			{
				mappingType=new HashMap<Integer, String>();
				mappingType.put(1, "Employee");
				if(validApp.contains("CIL"))//if client purchased the client app then he can map Lead in groups
				{
					mappingType.put(2, "Lead Contacts");
				}
				if(validApp.contains("CM"))//if client purchased the client app then he can map clients in groups
				{
					mappingType.put(3, "Client Contacts");
				}
				if(validApp.contains("AM"))//if client purchased the client app then he can map associates in groups
				{
					mappingType.put(4, "Associates Contacts");
				}
				
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method userGroups of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String getUnMappedData()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List  data=null;
			setMappingTypeSelected(getMappingTypeSelected());
			mappedData=new HashMap<Integer, String>();
			StringBuilder query=new StringBuilder("");
			StringBuilder idExists=new StringBuilder("");
			try
			{
				query.append("select mappedid from groupInfomapping where mappedlevelid='"+getMappingTypeSelected()+"' and grpid='"+getGrpId()+"'");
				data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					int k=1;
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						Object obdata=(Object)it.next();
						if(obdata!=null)
						{
							if(k<data.size())
								idExists.append("'"+obdata.toString()+"',");
							else
								idExists.append("'"+obdata.toString()+"'");
							k++;
						}
						
					}
				}
			}
			catch(Exception e)
			{
				
			}
			if(getMappingTypeSelected().equalsIgnoreCase("1"))
			{
				//for employee
				data=null;
				try
				{
					if(!idExists.toString().equalsIgnoreCase(""))
					{
						query.delete(0, query.length());
						query.append("select id,empName from employee_basic where id not in("+idExists.toString()+")");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					}
					else
					{
						query.delete(0, query.length());
						query.append("select id,empName from employee_basic");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					}
				}
				catch(Exception e)
				{
					
				}
			}
			else if(getMappingTypeSelected().equalsIgnoreCase("2"))
			{
				//for lead
				data=null;
				try
				{
					if(!idExists.toString().equalsIgnoreCase(""))
					{
						query.delete(0, query.length());
						query.append("select id,leadName from leadgeneration where userName='"+userName+"' and id not in("+idExists.toString()+")");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					}
					else
					{
						query.delete(0, query.length());
						query.append("select id,leadName from leadgeneration where userName='"+userName+"'");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					}
				}
				catch(Exception e)
				{
					
				}
			}
			else if(getMappingTypeSelected().equalsIgnoreCase("3"))
			{
				//for client
				data=null;
				try
				{
					if(!idExists.toString().equalsIgnoreCase(""))
					{
						query.delete(0, query.length());
						query.append("select id,personName from client_contact_data where userName='"+userName+"' and id not in("+idExists.toString()+")");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					}
					else
					{
						query.delete(0, query.length());
						query.append("select id,personName from client_contact_data where userName='"+userName+"'");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					}
				}
				catch(Exception e)
				{
					
				}
			}
			else if(getMappingTypeSelected().equalsIgnoreCase("4"))
			{
				//for associate
				data=null;
				try
				{
					if(!idExists.toString().equalsIgnoreCase(""))
					{
						query.delete(0, query.length());
						query.append("select id,name from prospectiveAssociateContact where userName='"+userName+"' and id not in("+idExists.toString()+")");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					}
					else
					{
						query.delete(0, query.length());
						query.append("select id,name from prospectiveAssociateContact where userName='"+userName+"'");
						data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					}
				}
				catch(Exception e)
				{
					
				}
			}
			
			if(data!=null)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					Object[] obdata=(Object[])it.next();
					if(obdata!=null && obdata[0]!=null && obdata[1]!=null)
						mappedData.put((Integer)obdata[0], obdata[1].toString());
					
				}
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method getUnMappedData of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String mapWithGroup()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			GroupActionCtrlhelper gah=new GroupActionCtrlhelper();
			if(getGid()!=null && !getGid().equalsIgnoreCase("") && getGrpNameToBemapped()!=null && !getGrpNameToBemapped().equalsIgnoreCase("")
					&& getMappingTypeSelected()!=null && !getMappingTypeSelected().equalsIgnoreCase(""))
			{
				
				boolean status=gah.createGroupMapping(cbt, connectionSpace);
				if(status)
				{
					String datap[]=getGid().split(", ");
					for(String H:datap)
					{
						if(!H.equalsIgnoreCase("") && H!=null)
						{
							gah.insertGroupMemberInfo(cbt, connectionSpace, H, getGrpNameToBemapped(), getMappingTypeSelected(), userName);
						}
					}
					addActionMessage("Group member mapped successfully!");
				}
				else
				{
					addActionError("Their is some problem in group mapping!!!");
				}
			}
			else
			{
				addActionError("Their is some problem in group mapping!!!");
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method mapWithGroup of class "+getClass(), e);
			 e.printStackTrace();
			 addActionError("Their is some problem in group mapping!!!");
		}
		return SUCCESS;
	}
	
	
	public String fillGroups()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			GroupActionCtrlhelper gah=new GroupActionCtrlhelper();
			groupNames=gah.getAllgroupsWithUsermapped(cbt, connectionSpace, userName);
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method userGroups of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String fillGroupsData()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			mappedInGroups=new ArrayList<GroupPojoBean>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			
			if(validApp!=null && !validApp.equalsIgnoreCase("") && getGrpId()!=null && !getGrpId().equalsIgnoreCase(""))
			{
				query.append("select gip.id,eb.empName,gp.grpName from employee_basic as eb inner join groupInfomapping as gip " +
						"on gip.mappedid=eb.id inner join groupInfo as gp on gip.grpid=gp.id where gp.id="+getGrpId()+" and gip.userName='"+userName+"' and gip.mappedlevelid='1' limit "+to+",10");
				
				List data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						Object[] obdata=(Object[])it.next();
						if(obdata!=null && obdata[0]!=null && obdata[1]!=null && obdata[2]!=null)
						{
							GroupPojoBean gp=new GroupPojoBean();
							gp.setId((Integer)obdata[0]);
							gp.setMapName(obdata[1].toString());
							gp.setGrpName(obdata[2].toString());
							gp.setTypeOfmap("Employee");
							mappedInGroups.add(gp);
						}
						
					}
				}
				if(validApp.contains("CIL"))//if client purchased the client app then he can map Lead in groups
				{
					query.delete(0, query.length());
					query.append("select gip.id,lg.leadName,gp.grpName from leadgeneration as lg inner join groupInfomapping as gip on gip.mappedid=lg.id " +
							"inner join groupInfo as gp on gip.grpid=gp.id where gp.id="+getGrpId()+" and gip.userName='"+userName+"' and gip.mappedlevelid='2' limit "+to+",10");
					data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null)
					{
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							if(obdata!=null && obdata[0]!=null && obdata[1]!=null && obdata[2]!=null)
							{
								GroupPojoBean gp=new GroupPojoBean();
								gp.setId((Integer)obdata[0]);
								gp.setMapName(obdata[1].toString());
								gp.setGrpName(obdata[2].toString());
								gp.setTypeOfmap("Lead");
								mappedInGroups.add(gp);
							}
							
						}
					}
				}
				if(validApp.contains("CM"))//if client purchased the client app then he can map clients in groups
				{
					query.delete(0, query.length());
					query.append("select gip.id,ccd.personName,gp.grpName from client_contact_data as ccd inner join groupInfomapping as gip on gip.mappedid=ccd.id " +
							"inner join groupInfo as gp on gip.grpid=gp.id where gp.id="+getGrpId()+" and gip.userName='"+userName+"' and gip.mappedlevelid='3' limit "+to+",10");
					data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null)
					{
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							if(obdata!=null && obdata[0]!=null && obdata[1]!=null && obdata[2]!=null)
							{
								GroupPojoBean gp=new GroupPojoBean();
								gp.setId((Integer)obdata[0]);
								gp.setMapName(obdata[1].toString());
								gp.setGrpName(obdata[2].toString());
								gp.setTypeOfmap("Cleint");
								mappedInGroups.add(gp);
							}
							
						}
					}
				}
				if(validApp.contains("AM"))//if client purchased the client app then he can map associates in groups
				{
					query.delete(0, query.length());
					query.append("select gip.id,pa.name,gp.grpName from prospectiveassociatecontact as pa inner join groupInfomapping as gip on gip.mappedid=pa.id " +
							"inner join groupInfo as gp on gip.grpid=gp.id where gp.id="+getGrpId()+" and gip.userName='"+userName+"' and gip.mappedlevelid='4' limit "+to+",10");
					data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null)
					{
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							if(obdata!=null && obdata[0]!=null && obdata[1]!=null && obdata[2]!=null)
							{
								GroupPojoBean gp=new GroupPojoBean();
								gp.setId((Integer)obdata[0]);
								gp.setMapName(obdata[1].toString());
								gp.setGrpName(obdata[2].toString());
								gp.setTypeOfmap("Associate");
								mappedInGroups.add(gp);
							}
							
						}
					}
				}
				int temp=Integer.parseInt(getTo())+10;
				setTo(Integer.toString(temp));
				setGrpId(getGrpId());
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method fillGroupsData of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String removeMemberFromGroup()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			cbt.deleteAllRecordForId("groupInfomapping", "id",getGrpMappedID(), connectionSpace);
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String viewAllMembersOfGroup()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			allGroupView=GroupActionCtrlhelper.getAllMembersOfSelectedGroup(cbt, connectionSpace, getGrpID(),userName,validApp);
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method viewAllMembersOfGroup of class "+getClass(), e);
			 e.printStackTrace();
			 allGroupView=new ArrayList<AllgroupView>();
		}
		return SUCCESS;
	}
	
	
	
	
	public String getGrpLevel() {
		return grpLevel;
	}

	public void setGrpLevel(String grpLevel) {
		this.grpLevel = grpLevel;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public String getGrpDesc() {
		return grpDesc;
	}

	public void setGrpDesc(String grpDesc) {
		this.grpDesc = grpDesc;
	}

	public Map<Integer, String> getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(Map<Integer, String> groupNames) {
		this.groupNames = groupNames;
	}

	public Map<Integer, String> getMappingType() {
		return mappingType;
	}

	public void setMappingType(Map<Integer, String> mappingType) {
		this.mappingType = mappingType;
	}

	public String getMappingTypeSelected() {
		return mappingTypeSelected;
	}

	public void setMappingTypeSelected(String mappingTypeSelected) {
		this.mappingTypeSelected = mappingTypeSelected;
	}

	public String getGrpId() {
		return grpId;
	}

	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	public Map<Integer, String> getMappedData() {
		return mappedData;
	}

	public void setMappedData(Map<Integer, String> mappedData) {
		this.mappedData = mappedData;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGrpNameToBemapped() {
		return grpNameToBemapped;
	}

	public void setGrpNameToBemapped(String grpNameToBemapped) {
		this.grpNameToBemapped = grpNameToBemapped;
	}

	public List<GroupPojoBean> getMappedInGroups() {
		return mappedInGroups;
	}

	public void setMappedInGroups(List<GroupPojoBean> mappedInGroups) {
		this.mappedInGroups = mappedInGroups;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getGrpMappedID() {
		return grpMappedID;
	}

	public void setGrpMappedID(String grpMappedID) {
		this.grpMappedID = grpMappedID;
	}

	public List<AllgroupView> getAllGroupView() {
		return allGroupView;
	}

	public void setAllGroupView(List<AllgroupView> allGroupView) {
		this.allGroupView = allGroupView;
	}

	public String getGrpID() {
		return grpID;
	}

	public void setGrpID(String grpID) {
		this.grpID = grpID;
	}

	public Map<Integer, String> getGroupMap() {
		return groupMap;
	}

	public void setGroupMap(Map<Integer, String> groupMap) {
		this.groupMap = groupMap;
	}
	
	
}
