package com.Over2Cloud.ctrl.helpdesk.shiftconf;

/*
 * @Author Khushal Singh 
 * */

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

import org.apache.struts2.interceptor.ServletRequestAware;

@SuppressWarnings("serial")
public class EditShiftConf extends GridPropertyBean implements ServletRequestAware{

	private HttpServletRequest request;
	
	private String dataFor;
	private String viewType;
	private List<Object> shiftData;
	private List<Object> subDeptData;
	private List<Object> shiftDataLocation;
	
	
	// Method for setting Column Name before setting (In Use)
	public String beforeShiftView()
	 {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) 
		 {
			try 
			 {
				if (userName==null || userName.equalsIgnoreCase("")) 
					return LOGIN;
				
				if (dataFor!=null && !dataFor.equals("") && viewType!=null && !viewType.equals("") &&(dataFor.equals("HDM"))) {
					setGridColomnNames(viewType);
					returnResult=SUCCESS;	
				}
				else {
					setGridColomnNames(viewType);
					returnResult = "shiftSuccess";
				}
			 } catch (Exception e) {
				e.printStackTrace();
			}
		 }
		return returnResult;
	}
	
	
 // Method for set Grid Columns Name (In Use)
 public void setGridColomnNames(String viewType)
	 {
	   viewPageColumns = new ArrayList<GridDataPropertyView>();
       GridDataPropertyView gdpv = new GridDataPropertyView();
       gdpv.setColomnName("id");
       gdpv.setHeadingName("Id");
       gdpv.setHideOrShow("true");
       viewPageColumns.add(gdpv);
	   if (viewType.equalsIgnoreCase("SD"))
		 {
		    // Column Name set for Sub Departments
			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_subdeprtmentconf","",connectionSpace,"",0,"table_name","subdeprtmentconf");
			if(statusColName!=null && statusColName.size()>0)
			 {
			   for(GridDataPropertyView gdp:statusColName)
				 {
					if (gdp.getColomnName().equals("subdeptname")) {
						gdpv=new GridDataPropertyView();
						gdpv.setColomnName(gdp.getColomnName());
						gdpv.setHeadingName(gdp.getHeadingName());
						gdpv.setEditable(gdp.getEditable());
						gdpv.setSearch(gdp.getSearch());
						gdpv.setAlign("Left");
						gdpv.setWidth(740);
						gdpv.setHideOrShow(gdp.getHideOrShow());
						viewPageColumns.add(gdpv);
					 }
				   }
			   }
			
			// Column Name set for Shift Management
			gdpv = new GridDataPropertyView();
			gdpv.setColomnName("id");
			gdpv.setHeadingName("Id");
			gdpv.setHideOrShow("true");
			viewPageColumns.add(gdpv);
			
			List<GridDataPropertyView> shiftColName=Configuration.getConfigurationData("mapped_shift_configuration","",connectionSpace,"",0,"table_name","shift_configuration");
			if(shiftColName!=null && shiftColName.size()>0)
			 {
				for(GridDataPropertyView gdp:shiftColName)
				 {
					if (!gdp.getColomnName().equalsIgnoreCase("shiftFrom") && !gdp.getColomnName().equalsIgnoreCase("shiftTO") && !gdp.getColomnName().equals("moduleName")) {
						gdpv=new GridDataPropertyView();
						gdpv.setColomnName(gdp.getColomnName());
						gdpv.setHeadingName(gdp.getHeadingName());
						gdpv.setEditable(gdp.getEditable());
						gdpv.setSearch(gdp.getSearch());
						gdpv.setWidth(235);
						gdpv.setHideOrShow(gdp.getHideOrShow());
						viewPageColumns.add(gdpv);
					}
				 }
			  }
		   }
		else {
			List<GridDataPropertyView> shiftColName=Configuration.getConfigurationData("mapped_shift_configuration","",connectionSpace,"",0,"table_name","shift_configuration");
			if(shiftColName!=null&&shiftColName.size()>0)
			{
				for(GridDataPropertyView gdp:shiftColName)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("shiftFrom") && !gdp.getColomnName().equalsIgnoreCase("shiftTO")  && !gdp.getColomnName().equals("moduleName")) {
						gdpv=new GridDataPropertyView();
						gdpv.setColomnName(gdp.getColomnName());
						gdpv.setHeadingName(gdp.getHeadingName());
						gdpv.setEditable(gdp.getEditable());
						gdpv.setSearch(gdp.getSearch());
						gdpv.setHideOrShow(gdp.getHideOrShow());
						viewPageColumns.add(gdpv);
					}
				  }
			   }
		    }
		}
	
	
	// Method for getting Sub department Detail(In Use)
	@SuppressWarnings("unchecked")
	public String viewShiftSubDept()
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		 {
			try
			{
				subDeptData=new ArrayList<Object>();
			    HelpdeskUniversalAction HUA =	new HelpdeskUniversalAction();
			    HelpdeskUniversalHelper HUH =	new HelpdeskUniversalHelper();
				Map<String, List> whereClauseList = new HashMap<String, List>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("subdeptname", "ASC");
				List data=null;
				String dept_id ="";
				
				List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
				if (empData!=null && empData.size()>0) {
					for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						dept_id = object[3].toString();
					}
				}
				
				   if (dept_id!=null && !dept_id.equals("")) 
				   {
					   //shows department on the basis of dept Id
						//List subDeptList = HUA.getDataList("subdepartment", "id", "deptid", dept_id,connectionSpace);
					//	whereClauseList.put("id", subDeptList);
					//data= HUH.getDataFromTable("subdepartment", null, null, whereClauseList, order,searchField,searchString,searchOper, connectionSpace);
					   //shows department on the basis of User Type
					 
					   data=  HUH.getSubDeptListByUIDRetId(session.get("userTpe").toString(), dept_id, "", connectionSpace);
					   
				   }
				
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of colmuns
					//StringBuilder query=new StringBuilder("");
					//query.append("select ");
					List fieldNames=Configuration.getColomnList("mapped_subdeprtmentconf", "", connectionSpace, "subdeprtmentconf");
					fieldNames.remove("mappedOrgnztnId");
					fieldNames.remove("groupId");
					fieldNames.remove("deptid");
					fieldNames.remove("userName");
					fieldNames.remove("date");
					fieldNames.remove("time");
					fieldNames.remove("flag");
					System.out.println("Field nAMES :::: "+fieldNames.size());
					System.out.println("Field nAMES :::: "+data.size());
					if(data!=null&&data.size()>0)
					{
						List<Object> Listhb=new ArrayList<Object>();
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) 
							{
								if(obdata[k]!=null)
								{
									if (fieldNames.get(k)!=null) 
									{
										if(k==0)
										{
											one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
										}
										else
										{
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
										}
									}
								}
							}
							Listhb.add(one);
						}
						setSubDeptData(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				returnResult=ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	 }
	
	
	public String viewShiftDetail()
	{
		String returnResult="";
		try {
			returnResult = viewShiftConf(dataFor,viewType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return	returnResult;
	}
	
	public String viewShiftDetail4HDM()
	{
		String returnResult="";
		try {
			returnResult = viewShiftConf("HDM","SD");
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return	returnResult;
	}
	
	
	@SuppressWarnings("unchecked")
	public String viewShiftConf(String module, String viewtype)
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		 {
			try
			 {
				HelpdeskUniversalHelper HUH =	new HelpdeskUniversalHelper();
				HelpdeskUniversalAction HUA =	new HelpdeskUniversalAction();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("shiftName", "ASC");
				List data=null;
					if (viewtype!=null && !viewtype.equals("") &&  viewtype.equals("SD")) {
						wherClause.put("moduleName", module);
						wherClause.put("dept_subdept", id);
						data= HUH.getDataFromTable("shift_conf", null, wherClause, null, order,searchField,searchString,searchOper, connectionSpace);
					}
					else if (viewtype!=null && !viewtype.equals("") &&  viewtype.equals("D")) {
						List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
						String dept_id ="";
						if (empData!=null && empData.size()>0) {
							for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								dept_id = object[3].toString();
							}
						}
						wherClause.put("moduleName", module);
						wherClause.put("dept_subdept", dept_id);
						data=HUH.getDataFromTable("shift_conf", null, wherClause, null, order,searchField,searchString,searchOper, connectionSpace);
					}
				
			
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					List fieldNames=Configuration.getColomnList("mapped_shift_configuration", "", connectionSpace, "shift_configuration");
					
					if(data!=null && data.size()>0)
					{
						List<Object> Listhb=new ArrayList<Object>();
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++) {
								if(obdata[k]!=null)
								{
								  if(k==0)
								     {
										one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
									 }
								  else
									 {
										one.put(fieldNames.get(k).toString(), obdata[k].toString());
									 }
								}
							}
							Listhb.add(one);
						}
						setShiftData(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				returnResult=ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	 }
	
	@SuppressWarnings("rawtypes")
	public String viewShift4HDMLocation()
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		 {
			try
			 {
					StringBuilder query=new StringBuilder();
					query.append("SELECT flr.floorname,wing.wingsname,room.roomno FROM shift_location_conf as loc ");
					query.append("LEFT JOIN floor_room_detail as room on room.id=loc.room_id ");
					query.append("LEFT JOIN wings_detail as wing on room.wingsname=wing.id ");
					query.append("LEFT JOIN floor_detail as flr on wing.floorname=flr.id ");
					query.append(" WHERE loc.shift_id= "+id);
					System.out.println("::::::::::::::::: "+query.toString());
					List data= new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
			
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
					
					if(data!=null && data.size()>0)
					{
						List<Object> Listhb=new ArrayList<Object>();
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object[] obdata=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							if(obdata[0]!=null && obdata[1]!=null && obdata[2]!=null)
							{
								one.put("floor", obdata[0].toString());
								one.put("wing", obdata[1].toString());
								one.put("roomNo", obdata[2].toString());
							}
							Listhb.add(one);
						}
						setShiftDataLocation(Listhb);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				returnResult=SUCCESS;
			}
			catch(Exception e)
			{
				returnResult=ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	 }
	@SuppressWarnings("unchecked")
	public String modifyShiftConf()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				if(getOper().equalsIgnoreCase("edit"))
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext())
					{
						String parmName = it.next().toString();
						String paramValue=request.getParameter(parmName);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && parmName!=null && !parmName.equalsIgnoreCase("") 
								&& !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("rowid"))
							wherClause.put(parmName, paramValue);
					}
					wherClause.put("userName", userName);
					wherClause.put("date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("time", DateUtil.getCurrentTime());
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("shift_conf", wherClause, condtnBlock,connectionSpace);
				}
				else if(getOper().equalsIgnoreCase("del"))
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					String tempIds[]=getId().split(",");
					StringBuilder condtIds=new StringBuilder();
						int i=1;
						for(String H:tempIds)
						{
							if(i<tempIds.length)
								condtIds.append(H+" ,");
							else
								condtIds.append(H);
							i++;
						}
						cbt.deleteAllRecordForId("shift_conf", "id", condtIds.toString(), connectionSpace);
				}
			}
			catch(Exception e)
			{
				returnResult=ERROR;
				 e.printStackTrace();
			}
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	
	
	public List<Object> getSubDeptData() {
		return subDeptData;
	}
	public void setSubDeptData(List<Object> subDeptData) {
		this.subDeptData = subDeptData;
	}

	public List<Object> getShiftData() {
		return shiftData;
	}

	public void setShiftData(List<Object> shiftData) {
		this.shiftData = shiftData;
	}

	public String getDataFor() {
		return dataFor;
	}
	public void setDataFor(String dataFor) {
		this.dataFor = dataFor;
	}

	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
	public List<Object> getShiftDataLocation()
	{
		return shiftDataLocation;
	}


	public void setShiftDataLocation(List<Object> shiftDataLocation)
	{
		this.shiftDataLocation = shiftDataLocation;
	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
}
