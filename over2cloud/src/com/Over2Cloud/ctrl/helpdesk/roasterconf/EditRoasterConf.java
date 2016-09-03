package com.Over2Cloud.ctrl.helpdesk.roasterconf;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.DateUtil;

import javax.servlet.http.HttpServletRequest;

import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.floorsetting.FloorSettingHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

@SuppressWarnings("serial")
public class EditRoasterConf extends GridPropertyBean implements ServletRequestAware{
	
	private HttpServletRequest request;
	
	private String addEmpHeader;
	private String applyRoasterHeader;
	private String modifyFlag;
	private String flag;
	private String dataFor;
	private String viewType;
	
	//private List<RoasterConfPojo> roasterConfData1 = new ArrayList<RoasterConfPojo>();
	private List<Object> roasterConfData;
	List<GridDataPropertyView> addEmpRoaster = null;
	List<GridDataPropertyView> applyEmpRoaster = null;
	
	private Map<Integer, String> serviceDeptList = null;
	
	@SuppressWarnings("unchecked")
	public String beforeRoaster()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try {
			  if (userName==null || userName.equalsIgnoreCase("")) 
				return LOGIN;
			
			  if (getFlag()!= null && getFlag().equals("add")) {
				setGridColomnNames(getFlag());
				returnResult="addRoaster";	
			  }
			  else if (getFlag()!= null && getFlag().equals("apply")) {
				setGridColomnNames(getFlag());
				returnResult = "applyRoaster";
			  }
			  else if (getFlag()!= null && getFlag().equalsIgnoreCase("upload")) 
			   {
				  serviceDeptList = new LinkedHashMap<Integer, String>();
					if (dataFor!=null && !dataFor.equals("")) {
						List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2",orgLevel, orgId,dataFor, connectionSpace);
						if (departmentlist != null && departmentlist.size() > 0) {
							for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();) {
								Object[] object1 = (Object[]) iterator.next();
								serviceDeptList.put((Integer) object1[0], object1[1].toString());
							}
						  }
						}
					setPagefields(viewType);
					returnResult = "uploadRoaster";
			  }
			  
		   } catch (Exception e) {
			e.printStackTrace();
		  }
		}
		return returnResult;
	}
	
	
	 public void setPagefields(String pageType) {
			ConfigurationUtilBean obj;
			pageFieldsColumns = new ArrayList<ConfigurationUtilBean>();
			
			List<GridDataPropertyView> deptList = Configuration.getConfigurationData("mapped_dept_config_param", accountID,connectionSpace, "", 0, "table_name","dept_config_param");
			if (deptList != null && deptList.size() > 0) {
				for (GridDataPropertyView gdv : deptList) {
					 obj = new ConfigurationUtilBean();
					if (gdv.getColomnName().equalsIgnoreCase("deptName")) {
						obj.setKey(gdv.getColomnName());
						obj.setValue(gdv.getHeadingName());
						obj.setValidationType(gdv.getValidationType());
						obj.setColType("D");
						obj.setMandatory(true);
						pageFieldsColumns.add(obj);
					}
				}
			}
			
			if (pageType.equals("SD")) 
			 {
				List<GridDataPropertyView> subdept_deptList = Configuration.getConfigurationData("mapped_subdeprtmentconf", accountID,connectionSpace, "", 0, "table_name","subdeprtmentconf");
				if (subdept_deptList != null && subdept_deptList.size() > 0) {
					for (GridDataPropertyView gdv1 : subdept_deptList) {
						obj = new ConfigurationUtilBean();
						if (gdv1.getColomnName().equalsIgnoreCase("subdeptname")) {
							obj.setKey(gdv1.getColomnName());
							obj.setValue(gdv1.getHeadingName());
							obj.setValidationType(gdv1.getValidationType());
							obj.setColType("D");
							obj.setMandatory(true);
							pageFieldsColumns.add(obj);
						}
					}
				}
			 }
		   }
	
	
	public void setGridColomnNames(String flag){
		if (flag.equals("add")) {
			addEmpRoaster = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gdpvadd=new GridDataPropertyView();
			gdpvadd.setColomnName("id");
			gdpvadd.setHeadingName("id");
			gdpvadd.setHideOrShow("true");
			addEmpRoaster.add(gdpvadd);
			
			gdpvadd=new GridDataPropertyView();
			gdpvadd.setColomnName("level");
			gdpvadd.setHeadingName("Level");
			gdpvadd.setEditable("left");
			gdpvadd.setAlign("center");
			gdpvadd.setWidth(200);
			addEmpRoaster.add(gdpvadd);
			
			gdpvadd=new GridDataPropertyView();
			gdpvadd.setColomnName("dept_subdept");
			gdpvadd.setHeadingName("Sub Department");
			gdpvadd.setEditable("false");
			gdpvadd.setAlign("left");
			gdpvadd.setSearch("true");
			gdpvadd.setWidth(200);
			addEmpRoaster.add(gdpvadd);
			
			gdpvadd=new GridDataPropertyView();
			gdpvadd.setColomnName("empName");
			gdpvadd.setHeadingName("Emp Name");
			gdpvadd.setEditable("false");
			gdpvadd.setAlign("center");
			gdpvadd.setSearch("true");
			gdpvadd.setWidth(250);
			addEmpRoaster.add(gdpvadd);
			
			gdpvadd=new GridDataPropertyView();
			gdpvadd.setColomnName("mobOne");
			gdpvadd.setHeadingName("Mobile No");
			gdpvadd.setEditable("false");
			gdpvadd.setAlign("center");
			gdpvadd.setSearch("true");
			gdpvadd.setWidth(200);
			addEmpRoaster.add(gdpvadd);
			
			gdpvadd=new GridDataPropertyView();
			gdpvadd.setColomnName("emailIdOne");
			gdpvadd.setHeadingName("Email ID");
			gdpvadd.setEditable("left");
			gdpvadd.setAlign("center");
			gdpvadd.setSearch("true");
			gdpvadd.setWidth(300);
			addEmpRoaster.add(gdpvadd);
			
		 }
		else if (flag.equals("apply"))
		 {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataList = new FloorSettingHelper().getFloor(connectionSpace, cbt, "Floor Detail");
			StringBuilder floor = new StringBuilder();
			StringBuilder tempBuilder = new StringBuilder();
			if(dataList!=null && dataList.size()>0)
			{
				floor.append("{value:'");
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if(object[0]!=null && object[1]!=null)
					{
						tempBuilder.append(object[0].toString()+":"+object[1].toString()+";");
					}
				}
				floor.append(tempBuilder.substring(0, tempBuilder.toString().length()-1));
				floor.append("'};");
			}
			
			applyEmpRoaster = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("id");
			gdpvapply.setHeadingName("id");
			gdpvapply.setHideOrShow("true");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("dept_subdept");
			gdpvapply.setHeadingName("Sub department");
			gdpvapply.setEditable("false");
			gdpvapply.setAlign("left");
			gdpvapply.setWidth(100);
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("level");
			gdpvapply.setHeadingName("Level");
			gdpvapply.setEditable("false");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(60);
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("empName");
			gdpvapply.setHeadingName("Emp Name");
			gdpvapply.setEditable("false");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(100);
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("attendance");
			gdpvapply.setHeadingName("Attendance");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("select");
			gdpvapply.setEditoptions("{value:'Present:Present;Absent:Absent'}");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(90);
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("status");
			gdpvapply.setHeadingName("Status");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("select");
			gdpvapply.setEditoptions("{value:'Active:Active;D-Active:D-Active'}");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(90);
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("floor");
			gdpvapply.setHeadingName("Floor");
			gdpvapply.setEditable("false");
			gdpvapply.setEdittype("select");
			//gdpvapply.setEditoptions("{value:'Basment:Basment;Grd Flr:Grd Flr;1st Flr:1st Flr;2nd Flr:2nd Flr;3rd Flr:3rd Flr;4th Flr:4th Flr;5th Flr:5th Flr;6th Flr:6th Flr;7th Flr:7th Flr'}");
			gdpvapply.setEditoptions(floor.toString());
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(90);
			gdpvapply.setHideOrShow("true");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("01_date");
			gdpvapply.setHeadingName("1");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("02_date");
			gdpvapply.setHeadingName("2");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("03_date");
			gdpvapply.setHeadingName("3");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("04_date");
			gdpvapply.setHeadingName("4");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("05_date");
			gdpvapply.setHeadingName("5");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("06_date");
			gdpvapply.setHeadingName("6");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("07_date");
			gdpvapply.setHeadingName("7");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("08_date");
			gdpvapply.setHeadingName("8");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("09_date");
			gdpvapply.setHeadingName("9");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("10_date");
			gdpvapply.setHeadingName("10");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("11_date");
			gdpvapply.setHeadingName("11");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("12_date");
			gdpvapply.setHeadingName("12");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("13_date");
			gdpvapply.setHeadingName("13");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("14_date");
			gdpvapply.setHeadingName("14");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("15_date");
			gdpvapply.setHeadingName("15");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("16_date");
			gdpvapply.setHeadingName("16");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("17_date");
			gdpvapply.setHeadingName("17");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("18_date");
			gdpvapply.setHeadingName("18");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("19_date");
			gdpvapply.setHeadingName("19");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("20_date");
			gdpvapply.setHeadingName("20");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("21_date");
			gdpvapply.setHeadingName("21");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("22_date");
			gdpvapply.setHeadingName("22");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("23_date");
			gdpvapply.setHeadingName("23");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("24_date");
			gdpvapply.setHeadingName("24");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("25_date");
			gdpvapply.setHeadingName("25");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("26_date");
			gdpvapply.setHeadingName("26");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("27_date");
			gdpvapply.setHeadingName("27");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("28_date");
			gdpvapply.setHeadingName("28");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("29_date");
			gdpvapply.setHeadingName("29");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("30_date");
			gdpvapply.setHeadingName("30");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
			
			gdpvapply=new GridDataPropertyView();
			gdpvapply.setColomnName("31_date");
			gdpvapply.setHeadingName("31");
			gdpvapply.setEditable("true");
			gdpvapply.setEdittype("text");
			gdpvapply.setAlign("center");
			gdpvapply.setWidth(40);
			gdpvapply.setSearch("false");
			applyEmpRoaster.add(gdpvapply);
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String addEmpInRoaster()
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		 {
			try
			{
				roasterConfData=new ArrayList<Object>();
				List data=null;
				
				
					List empData = new HelpdeskUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
					String dept_id ="";
					if (empData!=null && empData.size()>0)
					{
						for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							dept_id = object[3].toString();
						}
					}
					String usert= (String)session.get("userTpe");
					data=new HelpdeskUniversalHelper().getContacts4Roaster(dept_id, deptLevel, searchField, searchString, searchOper,dataFor,viewType, connectionSpace,usert);
				
					if(data!=null&&data.size()>0)
					 {
						setRecords(data.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
					 }
				
					List fieldNames= new ArrayList();
					fieldNames.add("id");
					fieldNames.add("level");
					fieldNames.add("dept_subdept");
					fieldNames.add("empName");
					fieldNames.add("mobOne");
					fieldNames.add("emailIdOne");
					
					if(data!=null&&data.size()>0)
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
											if (fieldNames.get(k).toString().equals("level")) {
												one.put(fieldNames.get(k).toString(), "Level"+obdata[k].toString());
											}
											else {
												one.put(fieldNames.get(k).toString(), obdata[k].toString());
											}
										}
								}
							}
							Listhb.add(one);
						}
						setRoasterConfData(Listhb);
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
	public String applyRoaster()
	 {
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				roasterConfData=new ArrayList<Object>();
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				List<String> colmName = new ArrayList<String>();
				colmName.add("roaster.id as id");
				colmName.add("roaster.empName");
				colmName.add("roaster.attendance");
				colmName.add("roaster.status");
				colmName.add("flr.floorname");
				colmName.add("comp.level");
				colmName.add("01_date");
				colmName.add("02_date");
				colmName.add("03_date");
				colmName.add("04_date");
				colmName.add("05_date");	
				colmName.add("06_date");
				colmName.add("07_date");
				colmName.add("08_date");
				colmName.add("09_date");
				colmName.add("10_date");
				colmName.add("11_date");
				colmName.add("12_date");
				colmName.add("13_date");
				colmName.add("14_date");
				colmName.add("15_date");
				colmName.add("16_date");
				colmName.add("17_date");
				colmName.add("18_date");
				colmName.add("19_date");
				colmName.add("20_date");
				colmName.add("21_date");
				colmName.add("22_date");
				colmName.add("23_date");
				colmName.add("24_date");
				colmName.add("25_date");
				colmName.add("26_date");
				colmName.add("27_date");
				colmName.add("28_date");
				colmName.add("29_date");
				colmName.add("30_date");
				colmName.add("31_date");
				if (viewType!=null && !viewType.equals("") && viewType.equals("SD")) {
					colmName.add("subdepartment.subdeptname as dept_subdept");
				}
				else if (viewType!=null && !viewType.equals("") && viewType.equals("D")) {
					colmName.add("department.deptName as dept_subdept");
				}
				
				
				Map<String, List> wherClauseList = new HashMap<String, List>();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> order = new HashMap<String, Object>();
				order.put("empName", "ASC");
				//int count = 0 ;
				List data=null;
				
						List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
						String dept_id ="";
						if (empData!=null && empData.size()>0) {
							for (Iterator iterator = empData.iterator(); iterator.hasNext();) {
								Object[] object = (Object[]) iterator.next();
								dept_id = object[3].toString();
							}
						}
						
						//List subDeptList = HUA.getDataList("subdepartment", "id", "deptid", dept_id,connectionSpace);
						/*wherClauseList.put("dept_subdept", subDeptList);*/
						if(!session.get("userTpe").toString().equalsIgnoreCase("M")){
						wherClause.put("department.id", dept_id);
						}
						wherClause.put("comp.moduleName", dataFor);
						wherClause.put("comp.work_status", "3");
						//count = new HelpdeskUniversalHelper().getDataCountFromTable("roaster_conf", wherClause, null, connectionSpace);
						if (searchField!=null && !searchField.equals("") && searchField.equals("empName")) {
							searchField="roaster"+"."+searchField;
							//System.out.println("Search Field Value  "+searchField);
						}
					
						data=new HelpdeskUniversalHelper().getDataForRoasterApply("roaster_conf", colmName, wherClause, wherClauseList, order,searchField,searchString,searchOper,viewType, connectionSpace);
				if(data!=null && data.size()>0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();
				}
					//getting the list of colmuns
					StringBuilder query=new StringBuilder("");
					query.append("select ");
					List fieldNames= new ArrayList();
					fieldNames.add("id");fieldNames.add("empName");fieldNames.add("attendance");fieldNames.add("status");fieldNames.add("floor");fieldNames.add("level");fieldNames.add("01_date");fieldNames.add("02_date");
					fieldNames.add("03_date");fieldNames.add("04_date");fieldNames.add("05_date");fieldNames.add("06_date");fieldNames.add("07_date");fieldNames.add("08_date");fieldNames.add("09_date");
					fieldNames.add("10_date");fieldNames.add("11_date");fieldNames.add("12_date");fieldNames.add("13_date");fieldNames.add("14_date");fieldNames.add("15_date");
					fieldNames.add("16_date");fieldNames.add("17_date");fieldNames.add("18_date");fieldNames.add("19_date");fieldNames.add("20_date");
					fieldNames.add("21_date");fieldNames.add("22_date");fieldNames.add("23_date");fieldNames.add("24_date");fieldNames.add("25_date");fieldNames.add("26_date");fieldNames.add("27_date");fieldNames.add("28_date");fieldNames.add("29_date");fieldNames.add("30_date");fieldNames.add("31_date");fieldNames.add("dept_subdept");
					
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
											if (fieldNames.get(k).toString().equals("level")) {
												one.put(fieldNames.get(k).toString(), "Level"+obdata[k].toString());
											}
											else {
												one.put(fieldNames.get(k).toString(), obdata[k].toString());
											}
										}
								}
							}
							Listhb.add(one);
						}
						setRoasterConfData(Listhb);
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
	public String modifyRoaster()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				String userName=(String)session.get("uName");
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
								&& !parmName.equalsIgnoreCase("flr.floorname") && !parmName.equalsIgnoreCase("id")&& !parmName.equalsIgnoreCase("oper")&& !parmName.equalsIgnoreCase("rowid"))
							wherClause.put(parmName, paramValue);
					}
					if(request.getParameter("flr.floorname")!=null)
					{
						String qury="SELECT id FROM floor_detail WHERE floorname='"+request.getParameter("flr.floorname")+"'";
						List dataList = cbt.executeAllSelectQuery(qury, connectionSpace);
						if(dataList!=null && dataList.size()>0)
						{
							wherClause.put("floor", dataList.get(0).toString());
						}
					}
					wherClause.put("userName", userName);
					wherClause.put("date", DateUtil.getCurrentDateUSFormat());
					wherClause.put("time", DateUtil.getCurrentTime());
					
					condtnBlock.put("id",getId());
					cbt.updateTableColomn("roaster_conf", wherClause, condtnBlock,connectionSpace);
					returnResult=SUCCESS;
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
						cbt.deleteAllRecordForId("roaster_conf", "id", condtIds.toString(), connectionSpace);
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
	
	
	public String getAddEmpHeader() {
		return addEmpHeader;
	}

	public void setAddEmpHeader(String addEmpHeader) {
		this.addEmpHeader = addEmpHeader;
	}

	public String getApplyRoasterHeader() {
		return applyRoasterHeader;
	}

	public void setApplyRoasterHeader(String applyRoasterHeader) {
		this.applyRoasterHeader = applyRoasterHeader;
	}

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<GridDataPropertyView> getAddEmpRoaster() {
		return addEmpRoaster;
	}

	public void setAddEmpRoaster(List<GridDataPropertyView> addEmpRoaster) {
		this.addEmpRoaster = addEmpRoaster;
	}

	public List<GridDataPropertyView> getApplyEmpRoaster() {
		return applyEmpRoaster;
	}

	public void setApplyEmpRoaster(List<GridDataPropertyView> applyEmpRoaster) {
		this.applyEmpRoaster = applyEmpRoaster;
	}
	

	public List<Object> getRoasterConfData() {
		return roasterConfData;
	}

	public void setRoasterConfData(List<Object> roasterConfData) {
		this.roasterConfData = roasterConfData;
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

	
	

	public Map<Integer, String> getServiceDeptList() {
		return serviceDeptList;
	}


	public void setServiceDeptList(Map<Integer, String> serviceDeptList) {
		this.serviceDeptList = serviceDeptList;
	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}

}
