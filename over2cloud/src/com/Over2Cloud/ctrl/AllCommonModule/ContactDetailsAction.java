package com.Over2Cloud.ctrl.AllCommonModule;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.BeanUtil.TableColumes;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class ContactDetailsAction extends ActionSupport implements ServletRequestAware{
	static final Log log = LogFactory.getLog(ContactDetailsAction.class);
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private int id=0;
	private String mappedtablele=null;
	private String basicdetails_table=null;
	private String addressdetails_table=null;
	private String customdetails_table=null;
	private String descriptive_table=null;
	private String basicfieldsheader=null;
	private String addressfieldsheader=null;
	private String customfieldsheader=null;
	private String descriptionheader=null;
	private String createtbasicdetails_table;
	private String createaddressdetails_table;
	private List<ConfigurationUtilBean>listbasicPerCalendr=null;
	private List<ConfigurationUtilBean>listbasicPerdropdown=null;
	private List<ConfigurationUtilBean>listbasicPerDateCalendr=null;
	private List<ConfigurationUtilBean>listbasicsconfiguration=null;
	private List<ConfigurationUtilBean>listbasicsTextArea=null;
	
	private List<ConfigurationUtilBean>listadressingPerCalendr=null;
	private List<ConfigurationUtilBean>listadressingPerdropdown=null;
	private List<ConfigurationUtilBean>listadressingPerDateCalendr=null;
	private List<ConfigurationUtilBean>listadressingconfiguration=null;
	private List<ConfigurationUtilBean>listadressingTextArea=null;
	

	private Map<String, String> contactListData=new HashMap<String, String>();
	private Map<String, String> groupListData=new HashMap<String, String>();
	private Map<String, String> groupNameListData = new HashMap<String, String>();
	public Map<String, String> getGroupNameListData() {
		return groupNameListData;
	}
	public void setGroupNameListData(Map<String, String> groupNameListData) {
		this.groupNameListData = groupNameListData;
	}
	private List<ConfigurationUtilBean> Listadressingdata=null;
        String[] headtername=null;
        private String customertype=null;
	private HttpServletRequest request;
	private Map<Integer,String> customerTypes=null;
	@SuppressWarnings("unchecked")
	public String beforeContactdetailsAdd(){
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		String resultString=ERROR;
		setId(getId());
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", getId());
			List<String> data=new ArrayList<String>();
			data.add("mappedid");
			data.add("table_name");
			if(getCreatetbasicdetails_table()!=null && getCreateaddressdetails_table().equalsIgnoreCase("")){
			setCreatetbasicdetails_table(getCreatetbasicdetails_table());
			}
			if(getCreateaddressdetails_table()!=null && getCreateaddressdetails_table().equalsIgnoreCase("")){
				setCreateaddressdetails_table(getCreateaddressdetails_table());
			}
			List resultdata = new CommonOperAtion().getSelectTabledata(
					"allcommontabtable", paramMap,connectionSpace);
			for (Iterator iterator = resultdata.iterator(); iterator.hasNext();) {
				Object[] objectCal = (Object[]) iterator.next();
				if (objectCal[5] != null
						&& !objectCal[5].toString().equalsIgnoreCase("")) {
					setMappedtablele(objectCal[5].toString());
					 if(objectCal[11]!=null){
						 headtername=objectCal[11].toString().split("#");
					 }
					 if(headtername.length>=1){
							setBasicfieldsheader(headtername[0].toString());
							}
					if(objectCal[2]!=null && !objectCal[2].toString().equalsIgnoreCase("")){
						Map<String, Object>mapObject=new HashMap<String,Object>();
					mapObject.put("table_name",objectCal[2].toString());
					setBasicdetails_table(objectCal[2].toString());
					List<ConfigurationUtilBean> ListObj=new ArrayList<ConfigurationUtilBean>();
					List<ConfigurationUtilBean> ListtextareaObj=new ArrayList<ConfigurationUtilBean>();
					List<ConfigurationUtilBean> dateListObj=new ArrayList<ConfigurationUtilBean>();
					List<ConfigurationUtilBean> dropdownListObj=new ArrayList<ConfigurationUtilBean>();
					List<ConfigurationUtilBean> TimeListObj=new ArrayList<ConfigurationUtilBean>();
					
				   List selectresultdata = Configuration.getConfigurationIdss(objectCal[5].toString(),data, accountID, mapObject, connectionSpace);
				
				   if(selectresultdata.size()>0){
							for (Object c1 : selectresultdata) {
								Object[] dataC1 = (Object[]) c1;
								ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
								if(dataC1[3].toString().equalsIgnoreCase("Time")){
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									objEjb.setColType(dataC1[3].toString());
									if(dataC1[6].toString()!=null)
										objEjb.setValidationType(dataC1[6].toString());
										else
										objEjb.setValidationType("NA");
									if(dataC1[5]==null)
										objEjb.setMandatory(false);
									else if(dataC1[5].toString().equalsIgnoreCase("0"))
										objEjb.setMandatory(false);
									else if(dataC1[5].toString().equalsIgnoreCase("1"))
										objEjb.setMandatory(true);
									
									TimeListObj.add(objEjb);
								}
								else if(dataC1[3].toString().equalsIgnoreCase("Date")){
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									objEjb.setColType(dataC1[3].toString());
									if(dataC1[6].toString()!=null)
										objEjb.setValidationType(dataC1[6].toString());
										else
										objEjb.setValidationType("NA");
									if(dataC1[5]==null)
										objEjb.setMandatory(false);
									else if(dataC1[5].toString().equalsIgnoreCase("0"))
										objEjb.setMandatory(false);
									else if(dataC1[5].toString().equalsIgnoreCase("1"))
										objEjb.setMandatory(true);
									dateListObj.add(objEjb);
								}
								else if(dataC1[3].toString().equalsIgnoreCase("D")){
									
									List<String>colname=new ArrayList<String>();
									if(dataC1[1].toString().equalsIgnoreCase("customer_type")){
										if(dataC1[0].toString()!=null)
										objEjb.setField_name(dataC1[0].toString().trim());
										else
										 objEjb.setField_name("NA");
										if(dataC1[1].toString()!=null)
										objEjb.setField_value(dataC1[1].toString().trim());
										else
										objEjb.setField_value("NA");
										if(dataC1[3].toString()!=null)
										objEjb.setColType(dataC1[3].toString());
										else
										objEjb.setColType("NA");	
										if(dataC1[3].toString()!=null)
											objEjb.setColType(dataC1[3].toString());
											else
											objEjb.setColType("NA");	
										if(dataC1[6].toString()!=null)
											objEjb.setValidationType(dataC1[6].toString());
											else
											objEjb.setValidationType("NA");
										if(dataC1[5]==null)
											objEjb.setMandatory(false);
										else if(dataC1[5].toString().equalsIgnoreCase("0"))
											objEjb.setMandatory(false);
										else if(dataC1[5].toString().equalsIgnoreCase("1"))
											objEjb.setMandatory(true);
										dropdownListObj.add(objEjb);
									colname.add("id");
									colname.add("customer_type");
									List customertypeData=cbt.viewAllDataEitherSelectOrAll("createcustomertype_table",colname,connectionSpace);
									if(customertypeData!=null){
										customerTypes=new LinkedHashMap<Integer, String>();
										for (Object c : customertypeData) {
											Object[] dataC = (Object[]) c;
											if(dataC[0]!=null && dataC[1]!=null)
											customerTypes.put((Integer)dataC[0],dataC[1].toString());
										}
									  }
									}
								}
								else if(dataC1[3].toString().equalsIgnoreCase("TextArea")){
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									objEjb.setColType(dataC1[3].toString());
									if(dataC1[6].toString()!=null)
										objEjb.setValidationType(dataC1[6].toString());
										else
										objEjb.setValidationType("NA");
									if(dataC1[5]==null)
										objEjb.setMandatory(false);
									else if(dataC1[5].toString().equalsIgnoreCase("0"))
										objEjb.setMandatory(false);
									else if(dataC1[5].toString().equalsIgnoreCase("1"))
										objEjb.setMandatory(true);
									ListtextareaObj.add(objEjb);
								}
								else{
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									objEjb.setColType(dataC1[3].toString());
									if(dataC1[6].toString()!=null)
										objEjb.setValidationType(dataC1[6].toString());
										else
										objEjb.setValidationType("NA");
									if(dataC1[5]==null)
										objEjb.setMandatory(false);
									else if(dataC1[5].toString().equalsIgnoreCase("0"))
										objEjb.setMandatory(false);
									else if(dataC1[5].toString().equalsIgnoreCase("1"))
										objEjb.setMandatory(true);
									ListObj.add(objEjb);
								}	
							}
							setListbasicPerCalendr(TimeListObj);
							setListbasicPerDateCalendr(dateListObj);
							setListbasicsTextArea(ListtextareaObj);
							setListbasicPerdropdown(dropdownListObj);
							setListbasicsconfiguration(ListObj);
						  }
			
					  }
					}
					if(objectCal[3]!=null && !objectCal[3].toString().equalsIgnoreCase("")){
						 if(headtername.length>=2){
								setAddressfieldsheader(headtername[1].toString());
								}
							Map<String, Object>mapObject=new HashMap<String,Object>();
						mapObject.put("table_name",objectCal[3].toString());
						setAddressdetails_table(objectCal[3].toString());
						List<ConfigurationUtilBean> ListObj=new ArrayList<ConfigurationUtilBean>();
						List<ConfigurationUtilBean> ListtextareaObj=new ArrayList<ConfigurationUtilBean>();
						List<ConfigurationUtilBean> dateListObj=new ArrayList<ConfigurationUtilBean>();
						List<ConfigurationUtilBean> dropdownListObj=new ArrayList<ConfigurationUtilBean>();
						List<ConfigurationUtilBean> TimeListObj=new ArrayList<ConfigurationUtilBean>();
					   List selectresultdata = Configuration.getConfigurationIdss(objectCal[5].toString(),data, accountID, mapObject, connectionSpace);
					   if(selectresultdata.size()>0){
							for (Object c1 : selectresultdata) {
									Object[] dataC1 = (Object[]) c1;
									ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
									if(dataC1[3].toString().equalsIgnoreCase("Time")){
										objEjb.setField_name(dataC1[0].toString().trim());
										objEjb.setField_value(dataC1[1].toString().trim());
										objEjb.setColType(dataC1[3].toString());
										if(dataC1[6].toString()!=null)
											objEjb.setValidationType(dataC1[6].toString());
											else
											objEjb.setValidationType("NA");
										if(dataC1[5]==null)
											objEjb.setMandatory(false);
										else if(dataC1[5].toString().equalsIgnoreCase("0"))
											objEjb.setMandatory(false);
										else if(dataC1[5].toString().equalsIgnoreCase("1"))
											objEjb.setMandatory(true);
										TimeListObj.add(objEjb);
									}
									else if(dataC1[3].toString().equalsIgnoreCase("Date")){
										objEjb.setField_name(dataC1[0].toString().trim());
										objEjb.setField_value(dataC1[1].toString().trim());
										objEjb.setColType(dataC1[3].toString());
										if(dataC1[5]==null)
											objEjb.setMandatory(false);
										else if(dataC1[5].toString().equalsIgnoreCase("0"))
											objEjb.setMandatory(false);
										else if(dataC1[5].toString().equalsIgnoreCase("1"))
											objEjb.setMandatory(true);
										dateListObj.add(objEjb);
									}
									else if(dataC1[3].toString().equalsIgnoreCase("D")){
										objEjb.setField_name(dataC1[0].toString().trim());
										objEjb.setField_value(dataC1[1].toString().trim());
										objEjb.setColType(dataC1[3].toString());
										if(dataC1[6].toString()!=null)
											objEjb.setValidationType(dataC1[6].toString());
											else
											objEjb.setValidationType("NA");
										if(dataC1[5]==null)
											objEjb.setMandatory(false);
										else if(dataC1[5].toString().equalsIgnoreCase("0"))
											objEjb.setMandatory(false);
										else if(dataC1[5].toString().equalsIgnoreCase("1"))
											objEjb.setMandatory(true);
										dropdownListObj.add(objEjb);
									}
									else if(dataC1[3].toString().equalsIgnoreCase("TextArea")){
										objEjb.setColType(dataC1[3].toString());
										objEjb.setField_name(dataC1[0].toString().trim());
										objEjb.setField_value(dataC1[1].toString().trim());
										if(dataC1[6].toString()!=null)
											objEjb.setValidationType(dataC1[6].toString());
											else
											objEjb.setValidationType("NA");
										if(dataC1[5]==null)
											objEjb.setMandatory(false);
										else if(dataC1[5].toString().equalsIgnoreCase("0"))
											objEjb.setMandatory(false);
										else if(dataC1[5].toString().equalsIgnoreCase("1"))
											objEjb.setMandatory(true);
										ListtextareaObj.add(objEjb);
									}
									else{
										objEjb.setField_name(dataC1[0].toString().trim());
										objEjb.setField_value(dataC1[1].toString().trim());
										objEjb.setColType(dataC1[3].toString());
										if(dataC1[6].toString()!=null)
											objEjb.setValidationType(dataC1[6].toString());
											else
											objEjb.setValidationType("NA");
										if(dataC1[5]==null)
											objEjb.setMandatory(false);
										else if(dataC1[5].toString().equalsIgnoreCase("0"))
											objEjb.setMandatory(false);
										else if(dataC1[5].toString().equalsIgnoreCase("1"))
											objEjb.setMandatory(true);
										ListObj.add(objEjb);
									}	
								}
								setListadressingPerCalendr(TimeListObj);
								setListadressingPerDateCalendr(dateListObj);
								setListadressingTextArea(ListtextareaObj);
								setListadressingPerdropdown(dropdownListObj);
								setListadressingconfiguration(ListObj);
							  }
					
						  }
				
			    }
	
			resultString=SUCCESS;
		}
		catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method <Method Name> of class "+getClass(), e);
			// TODO: handle exception
		}
		return resultString;
	}
	@SuppressWarnings("unchecked")
	public String addcontactbasicDetails(){
		String resultString=ERROR;
		List<GridDataPropertyView>mappeddetails=null;
		String createtable =null;
		List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
		List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
		try{
			 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			 Collections.sort(requestParameterNames);
			 Iterator listit = requestParameterNames.iterator();
			 while (listit.hasNext()) {
					String Parmname = listit.next().toString();
					String paramValue=request.getParameter(Parmname);
					if(Parmname.equals("mappedtablele")&& paramValue.equals(request.getParameter("mappedtablele"))){
						mappedtablele=paramValue;}
					else if(paramValue.equals(request.getParameter("basicdetails_table"))){
						basicdetails_table=paramValue;}
					else if(paramValue.equals(request.getParameter("addressdetails_table"))){
						addressdetails_table=paramValue;}
					else if(paramValue.equals(request.getParameter("createtbasicdetails_table"))){
						createtable=paramValue;}
					else if(paramValue.equals(request.getParameter("createaddressdetails_table"))){
						createtable=paramValue;}
					else if(paramValue.equals(request.getParameter("sirname"))){
						 TableColumes obJ=new TableColumes();
						 obJ.setColumnname("sirname");
						 obJ.setDatatype("varchar(255)");
						 obJ.setConstraint("default NULL");
						 Tablecolumesaaa.add(obJ);	
						 
						 InsertDataTable ob = new InsertDataTable();
							ob.setColName(Parmname);
							ob.setDataName(paramValue);
							insertData.add(ob);
					    }
						else{
							
						InsertDataTable ob = new InsertDataTable();
						ob.setColName(Parmname);
						ob.setDataName(paramValue);
						insertData.add(ob);
					}
			 }
			if(basicdetails_table!=null){
			 mappeddetails=Configuration.getConfigurationData(mappedtablele,accountID,connectionSpace,"",0,"table_name",basicdetails_table);}
			else{
				mappeddetails=Configuration.getConfigurationData(mappedtablele,accountID,connectionSpace,"",0,"table_name",addressdetails_table);
			  }
			if(mappeddetails!=null){
				//	boolean status=false;
				 for(GridDataPropertyView col:mappeddetails){
					 TableColumes obJ=new TableColumes();
					 obJ.setColumnname(col.getColomnName());
					 obJ.setDatatype("varchar(255)");
					 obJ.setConstraint("default NULL");
					 Tablecolumesaaa.add(obJ);
					}
					CommonforClassdata obhj = new CommonOperAtion();
					boolean status = obhj.Createtabledata(createtable, Tablecolumesaaa, connectionSpace);
					status = obhj.insertIntoTableifNotExit(createtable, insertData,connectionSpace);
					if (status) {
						addActionMessage("Data added SuccessfUlly");
					} else {
						addActionMessage("Opps There is a problem plzz call Mr.Pankaj");
					}
					
			resultString=SUCCESS;
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	 return resultString;
	}
	
	@SuppressWarnings("unchecked")
	public String getContactDataList(){
		String resultString=ERROR;
		try{
		
			CommonforClassdata obhj = new CommonOperAtion();
			Map<String,Object> whereClose=new HashMap<String, Object>();
			List<String> listdata=new ArrayList<String>();
			listdata.add("id");
			listdata.add("first_name");
			listdata.add("last_name");
			listdata.add("mobilenumber");
			List datatemp= obhj.getSelectdataFromSelectedFields("contactbasicdetails",listdata, whereClose, connectionSpace);
			 for (Iterator iterator = datatemp.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				String cont_name=object[1].toString()+" "+object[2].toString();
				contactListData.put(object[0].toString(),cont_name+""+"-"+object[3].toString());
			}
			 Map<String, String> sortedMap = obhj.sortByComparator(contactListData);
			 setContactListData(sortedMap);
			resultString=SUCCESS;
		}
		catch (Exception e) {
		}
		return resultString;
	}
	
	public String getgroupname(){
		String resultString=ERROR;
		try{
			CommonforClassdata obhj = new CommonOperAtion();
			Map<String,Object> whereClose=new HashMap<String, Object>();
			List<String> listdata=new ArrayList<String>();
			
			 
			    listdata.add("id");
				listdata.add("groupName");
				whereClose.put("userName", userName);
				List datatemp= obhj.getSelectdataFromSelectedFields("groupinfo",listdata, whereClose, connectionSpace);
				groupListData.put("all_contactType", "All Contact Type");
				 for (Iterator iterator = datatemp.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					groupListData.put(object[0].toString(),object[1].toString());
				}
				 Map<String, String> sortedMap = obhj.sortByComparator(groupListData);
				 setGroupListData(sortedMap);
				 

					/* Get group name */ 
					
					List<String> listdatas = new ArrayList<String>();
					
					listdatas.add("id");
					listdatas.add("name");
					Map<String,Object> whereCloses=new HashMap<String, Object>();
					whereCloses.put("user", userName);
					List datatempz = obhj.getSelectdataFromSelectedFields("group_name",
							listdatas, whereCloses, connectionSpace);
					for (Iterator iterator = datatempz.iterator(); iterator.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						groupNameListData.put(object[0].toString(), object[1]
								.toString());
					}
					
					Map<String, String> sortedMaps = obhj.sortByComparator(groupNameListData);
					setGroupNameListData(sortedMaps);
				 
			resultString=SUCCESS;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
        @SuppressWarnings("unchecked")
        public String getAdressContactDataListsss(){
	
	   String resultString=ERROR;
	   try{
		   CommonforClassdata obhj = new CommonOperAtion();
		   List<String> listdata=new ArrayList<String>();
		   List<String> data=new ArrayList<String>();
		   Map<String,Object> whereClose=new HashMap<String, Object>();
			Map<String, Object>mapObject=new HashMap<String,Object>();
			mapObject.put("table_name","contactaddressdetail_configuration");
			data.add("mappedid");
			data.add("table_name");
			List<ConfigurationUtilBean> ListObj=new ArrayList<ConfigurationUtilBean>();
			List<ConfigurationUtilBean> ListtextareaObj=new ArrayList<ConfigurationUtilBean>();
		   List selectresultdata = Configuration.getConfigurationIdss("mapped_contactdetails_configuration",data, accountID, mapObject, connectionSpace);
		   if(selectresultdata.size()>0){
				for (Object c1 : selectresultdata) {
					Object[] dataC1 = (Object[]) c1;
					if(dataC1[1].toString().trim().equals("mappedid")){
					}else{
					listdata.add(dataC1[1].toString().trim());
					}
				}
			whereClose.put("mappedid", request.getParameter("mappedid"));
			List datatemp= obhj.getSelectdataFromSelectedFields("contactadressdetails",listdata, whereClose, connectionSpace);
			Object[] objsArray=null;
			if(datatemp.size()>0){
			for (Iterator iterator = datatemp.iterator(); iterator.hasNext();) {
				objsArray = (Object[]) iterator.next();
			 }
				int tepmid=0;
				for (Object c1 : selectresultdata) {
					Object[] dataC1 = (Object[]) c1;
					ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
					if(dataC1[1].toString().trim().equals("mappedid")){
					}
					else if(dataC1[3].toString().equalsIgnoreCase("TextArea")){
						objEjb.setField_name(dataC1[0].toString().trim());
						objEjb.setField_value(dataC1[1].toString().trim());
						if(dataC1[5]==null)
							objEjb.setMandatory(false);
						else if(dataC1[5].toString().equalsIgnoreCase("0"))
							objEjb.setMandatory(false);
						else if(dataC1[5].toString().equalsIgnoreCase("1"))
							objEjb.setMandatory(true);
						
						objEjb.setDefault_value(objsArray[tepmid].toString());
						
						ListtextareaObj.add(objEjb);
					}
					else{
					objEjb.setField_name(dataC1[0].toString().trim());
					objEjb.setField_value(dataC1[1].toString().trim());
					objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
					objEjb.setDefault_value(objsArray[tepmid].toString());
					ListObj.add(objEjb);
					
				}
				tepmid++;
			}
			 }else{
					for (Object c1 : selectresultdata) {
						Object[] dataC1 = (Object[]) c1;
						ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
						if(dataC1[1].toString().trim().equals("mappedid")){
						}
						else if(dataC1[3].toString().equalsIgnoreCase("TextArea")){
							objEjb.setField_name(dataC1[0].toString().trim());
							objEjb.setField_value(dataC1[1].toString().trim());
							objEjb.setColType(dataC1[3].toString());
							if(dataC1[6].toString()!=null)
								objEjb.setValidationType(dataC1[6].toString());
								else
								objEjb.setValidationType("NA");
							if(dataC1[5]==null)
								objEjb.setMandatory(false);
							else if(dataC1[5].toString().equalsIgnoreCase("0"))
								objEjb.setMandatory(false);
							else if(dataC1[5].toString().equalsIgnoreCase("1"))
								objEjb.setMandatory(true);
							ListtextareaObj.add(objEjb);
						}
						else{
						objEjb.setField_name(dataC1[0].toString().trim());
						objEjb.setField_value(dataC1[1].toString().trim());
						objEjb.setColType(dataC1[3].toString());
						if(dataC1[6].toString()!=null)
							objEjb.setValidationType(dataC1[6].toString());
							else
							objEjb.setValidationType("NA");
						if(dataC1[5]==null)
							objEjb.setMandatory(false);
						else if(dataC1[5].toString().equalsIgnoreCase("0"))
							objEjb.setMandatory(false);
						else if(dataC1[5].toString().equalsIgnoreCase("1"))
							objEjb.setMandatory(true);
						ListObj.add(objEjb);
						
					}
				
				}
			 }
			
				setListadressingconfiguration(ListObj);	
				setListadressingTextArea(ListtextareaObj);
				resultString=SUCCESS;
		}
		
	   }
	   catch (Exception e) {
		// TODO: handle exception
	}
	   return resultString;
	   
   }
	@SuppressWarnings("unchecked")
	public String editcontactData(){
		String resultError=ERROR;
		CommonforClassdata obhj = new CommonOperAtion();
		Map<String, Object>setMap=new HashMap<String, Object>();
		Map<String, Object>WhereClouse=new HashMap<String, Object>();
		try{
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			 Collections.sort(requestParameterNames);
			 Iterator listit = requestParameterNames.iterator();
			 while (listit.hasNext()) {
					String Parmname = listit.next().toString();
					String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid") && !Parmname.equalsIgnoreCase("rowid")){
							if(paramValue.equals(request.getParameter("createtbasicdetails_table"))){
								basicdetails_table=paramValue;}else{
							setMap.put(Parmname, paramValue);
							}
						}
			   }
				WhereClouse.put("id", getId());
			   boolean status = obhj.updateIntoTable(basicdetails_table, setMap,WhereClouse,connectionSpace);
				if (status) {
					addActionMessage("Data added SuccessfUlly");
				} else {
					addActionMessage("Opps There is a problem plzz call Mr.Pankaj");
				}
			resultError=SUCCESS;
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return resultError;
	}
	public List<ConfigurationUtilBean> getListadressingdata() {
	return Listadressingdata;
}
public void setListadressingdata(List<ConfigurationUtilBean> listadressingdata) {
	Listadressingdata = listadressingdata;
}
	public Map<String, String> getContactListData() {
		return contactListData;
	}
	public void setContactListData(Map<String, String> contactListData) {
		this.contactListData = contactListData;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ConfigurationUtilBean> getListbasicsconfiguration() {
		return listbasicsconfiguration;
	}
	public void setListbasicsconfiguration(
			List<ConfigurationUtilBean> listbasicsconfiguration) {
		this.listbasicsconfiguration = listbasicsconfiguration;
	}
	public String getBasicdetails_table() {
		return basicdetails_table;
	}
	public void setBasicdetails_table(String basicdetails_table) {
		this.basicdetails_table = basicdetails_table;
	}
	public String getAddressdetails_table() {
		return addressdetails_table;
	}
	public void setAddressdetails_table(String addressdetails_table) {
		this.addressdetails_table = addressdetails_table;
	}
	public String getCustomdetails_table() {
		return customdetails_table;
	}
	public void setCustomdetails_table(String customdetails_table) {
		this.customdetails_table = customdetails_table;
	}
	public String getDescriptive_table() {
		return descriptive_table;
	}
	public void setDescriptive_table(String descriptive_table) {
		this.descriptive_table = descriptive_table;
	}
	

	public String getBasicfieldsheader() {
		return basicfieldsheader;
	}
	public void setBasicfieldsheader(String basicfieldsheader) {
		this.basicfieldsheader = basicfieldsheader;
	}
	public String getAddressfieldsheader() {
		return addressfieldsheader;
	}
	public void setAddressfieldsheader(String addressfieldsheader) {
		this.addressfieldsheader = addressfieldsheader;
	}
	public String getCustomfieldsheader() {
		return customfieldsheader;
	}
	public void setCustomfieldsheader(String customfieldsheader) {
		this.customfieldsheader = customfieldsheader;
	}
	public String getDescriptionheader() {
		return descriptionheader;
	}
	public void setDescriptionheader(String descriptionheader) {
		this.descriptionheader = descriptionheader;
	}

	public String getMappedtablele() {
		return mappedtablele;
	}
	public void setMappedtablele(String mappedtablele) {
		this.mappedtablele = mappedtablele;
	}
	

	
	public List<ConfigurationUtilBean> getListbasicPerCalendr() {
		return listbasicPerCalendr;
	}
	public void setListbasicPerCalendr(
			List<ConfigurationUtilBean> listbasicPerCalendr) {
		this.listbasicPerCalendr = listbasicPerCalendr;
	}
	public List<ConfigurationUtilBean> getListbasicPerdropdown() {
		return listbasicPerdropdown;
	}
	public void setListbasicPerdropdown(
			List<ConfigurationUtilBean> listbasicPerdropdown) {
		this.listbasicPerdropdown = listbasicPerdropdown;
	}
	public List<ConfigurationUtilBean> getListbasicPerDateCalendr() {
		return listbasicPerDateCalendr;
	}
	public void setListbasicPerDateCalendr(
			List<ConfigurationUtilBean> listbasicPerDateCalendr) {
		this.listbasicPerDateCalendr = listbasicPerDateCalendr;
	}
	public List<ConfigurationUtilBean> getListbasicsTextArea() {
		return listbasicsTextArea;
	}
	public void setListbasicsTextArea(List<ConfigurationUtilBean> listbasicsTextArea) {
		this.listbasicsTextArea = listbasicsTextArea;
	}
	public List<ConfigurationUtilBean> getListadressingPerCalendr() {
		return listadressingPerCalendr;
	}
	public void setListadressingPerCalendr(
			List<ConfigurationUtilBean> listadressingPerCalendr) {
		this.listadressingPerCalendr = listadressingPerCalendr;
	}
	public List<ConfigurationUtilBean> getListadressingPerdropdown() {
		return listadressingPerdropdown;
	}
	public void setListadressingPerdropdown(
			List<ConfigurationUtilBean> listadressingPerdropdown) {
		this.listadressingPerdropdown = listadressingPerdropdown;
	}
	public List<ConfigurationUtilBean> getListadressingPerDateCalendr() {
		return listadressingPerDateCalendr;
	}
	public void setListadressingPerDateCalendr(
			List<ConfigurationUtilBean> listadressingPerDateCalendr) {
		this.listadressingPerDateCalendr = listadressingPerDateCalendr;
	}
	public List<ConfigurationUtilBean> getListadressingconfiguration() {
		return listadressingconfiguration;
	}
	public void setListadressingconfiguration(
			List<ConfigurationUtilBean> listadressingconfiguration) {
		this.listadressingconfiguration = listadressingconfiguration;
	}
	public List<ConfigurationUtilBean> getListadressingTextArea() {
		return listadressingTextArea;
	}
	public void setListadressingTextArea(
			List<ConfigurationUtilBean> listadressingTextArea) {
		this.listadressingTextArea = listadressingTextArea;
	}
	public String getCreatetbasicdetails_table() {
		return createtbasicdetails_table;
	}
	public void setCreatetbasicdetails_table(String createtbasicdetails_table) {
		this.createtbasicdetails_table = createtbasicdetails_table;
	}
	public String getCreateaddressdetails_table() {
		return createaddressdetails_table;
	}
	public void setCreateaddressdetails_table(String createaddressdetails_table) {
		this.createaddressdetails_table = createaddressdetails_table;
	}
	
	public Map<Integer, String> getCustomerTypes() {
		return customerTypes;
	}
	public void setCustomerTypes(Map<Integer, String> customerTypes) {
		this.customerTypes = customerTypes;
	}
	public String getCustomertype() {
		return customertype;
	}
	public void setCustomertype(String customertype) {
		this.customertype = customertype;
	}
	
	public Map<String, String> getGroupListData() {
		return groupListData;
	}
	public void setGroupListData(Map<String, String> groupListData) {
		this.groupListData = groupListData;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}
	

}
