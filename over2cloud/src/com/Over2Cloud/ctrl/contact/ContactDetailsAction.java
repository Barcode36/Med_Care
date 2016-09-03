package com.Over2Cloud.ctrl.contact;


import hibernate.common.HibernateSessionFactory;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.regexp.internal.recompile;

public class ContactDetailsAction extends ActionSupport implements ServletRequestAware{
	static final Log log = LogFactory.getLog(ContactDetailsAction.class);
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private int id=0;
	private List<ConfigurationUtilBean> Listbasicsconfiguration=null;
	private List<ConfigurationUtilBean> Listadressconfiguration=null;
	private List<ConfigurationUtilBean> Listcustomconfiguration=null;
	private List<ConfigurationUtilBean> Listdescriptconfiguration=null;
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
	private List<ConfigurationUtilBean>conatcbasicPerCalendr=null;
	private List<ConfigurationUtilBean>conatcbasicPerdropdown=null;
	private List<ConfigurationUtilBean>conatcaddressPerCalendr=null;
	private List<ConfigurationUtilBean>conatcaddressPerdropdown=null;
	private List<ConfigurationUtilBean>conatccustomPerCalendr=null;
	private List<ConfigurationUtilBean>conatccustomPerdropdown=null;
	private List<ConfigurationUtilBean>conatcdescriptnPerCalendr=null;
	private List<ConfigurationUtilBean>conatcdescriptnPerdropdown=null;
	private Map<String, String> contactListData=new HashMap<String, String>();
	private List<ConfigurationUtilBean> Listadressingdata=null;
    String[] headtername=null;
	private HttpServletRequest request;
	@SuppressWarnings("unchecked")
	public String beforeContactdetailsAdd(){
		
		String resultString=ERROR;
		try{
			//System.out.println("id"+getId());
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
					List<ConfigurationUtilBean> dateListObj=new ArrayList<ConfigurationUtilBean>();
					List<ConfigurationUtilBean> dropdownListObj=new ArrayList<ConfigurationUtilBean>();
				   List selectresultdata = Configuration.getConfigurationIdss(objectCal[5].toString(),data, accountID, mapObject, connectionSpace);
				   if(selectresultdata.size()>0){
						for (Object c1 : selectresultdata) {
							Object[] dataC1 = (Object[]) c1;
								ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
								if(dataC1[3].toString().equalsIgnoreCase("Time")){
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									dateListObj.add(objEjb);
								}
								else{
								objEjb.setField_name(dataC1[0].toString().trim());
								objEjb.setField_value(dataC1[1].toString().trim());
								ListObj.add(objEjb);
								}
						}
						setConatcbasicPerCalendr(dateListObj);
						setConatcbasicPerdropdown(dropdownListObj);
						setListbasicsconfiguration(ListObj);	
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
						List<ConfigurationUtilBean> dateListObj=new ArrayList<ConfigurationUtilBean>();
					   List selectresultdata = Configuration.getConfigurationIdss(objectCal[5].toString(),data, accountID, mapObject, connectionSpace);
					   if(selectresultdata.size()>0){
							for (Object c1 : selectresultdata) {
								Object[] dataC1 = (Object[]) c1;
								ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
								if(dataC1[3].toString().equalsIgnoreCase("Time")){
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									dateListObj.add(objEjb);
								}else{
						
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									ListObj.add(objEjb);
								}	
							}
							setListadressconfiguration(ListObj);
							setConatcaddressPerCalendr(dateListObj);
						  }
					}
					if(objectCal[4]!=null && !objectCal[4].toString().equalsIgnoreCase("")){
						 if(headtername.length>=3){
								setCustomfieldsheader(headtername[2].toString());
								}
							Map<String, Object>mapObject=new HashMap<String,Object>();
						mapObject.put("table_name",objectCal[4].toString());
						setAddressdetails_table(objectCal[3].toString());
						List<ConfigurationUtilBean> ListObj=new ArrayList<ConfigurationUtilBean>();
						List<ConfigurationUtilBean> dateListObj=new ArrayList<ConfigurationUtilBean>();
					   List selectresultdata = Configuration.getConfigurationIdss(objectCal[5].toString(),data, accountID, mapObject, connectionSpace);
					   if(selectresultdata.size()>0){
							for (Object c1 : selectresultdata) {
								Object[] dataC1 = (Object[]) c1;
								ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
								if(dataC1[3].toString().equalsIgnoreCase("Time")){
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									dateListObj.add(objEjb);
								}else{
						
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									ListObj.add(objEjb);
								}	
							}
							setListcustomconfiguration(ListObj);
							setConatccustomPerCalendr(dateListObj);
						  }
					}
					if(objectCal[10]!=null && !objectCal[10].toString().equalsIgnoreCase("")){
						 if(headtername.length>=3){
								setCustomfieldsheader(headtername[10].toString());
								}
							Map<String, Object>mapObject=new HashMap<String,Object>();
						mapObject.put("table_name",objectCal[10].toString());
						setAddressdetails_table(objectCal[3].toString());
						List<ConfigurationUtilBean> ListObj=new ArrayList<ConfigurationUtilBean>();
						List<ConfigurationUtilBean> dateListObj=new ArrayList<ConfigurationUtilBean>();
					   List selectresultdata = Configuration.getConfigurationIdss(objectCal[5].toString(),data, accountID, mapObject, connectionSpace);
					   if(selectresultdata.size()>0){
							for (Object c1 : selectresultdata) {
								Object[] dataC1 = (Object[]) c1;
								ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
								if(dataC1[3].toString().equalsIgnoreCase("Time")){
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									dateListObj.add(objEjb);
								}else{
						
									objEjb.setField_name(dataC1[0].toString().trim());
									objEjb.setField_value(dataC1[1].toString().trim());
									ListObj.add(objEjb);
								}	
							}
							setListdescriptconfiguration(ListObj);
							setConatcdescriptnPerCalendr(dateListObj);
						  }
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
					status = obhj.insertIntoTable(createtable, insertData,connectionSpace);
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
			List datatemp= obhj.getSelectdataFromSelectedFields("contactbasicdetails",listdata, whereClose, connectionSpace);
			 for (Iterator iterator = datatemp.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				String cont_name=object[1].toString()+" "+object[2].toString();
				contactListData.put(object[0].toString(),cont_name);
			}
			 Map<String, String> sortedMap = obhj.sortByComparator(contactListData);
			 setContactListData(sortedMap);
			resultString=SUCCESS;
		}
		catch (Exception e) {
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
					}else{
					objEjb.setField_name(dataC1[0].toString().trim());
					objEjb.setField_value(dataC1[1].toString().trim());
					objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
					objEjb.setDefault_value(objsArray[tepmid].toString());
					ListObj.add(objEjb);
					tepmid++;
				}}
			}else{
				for (Object c1 : selectresultdata) {
					Object[] dataC1 = (Object[]) c1;
					ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
					if(dataC1[1].toString().trim().equals("mappedid")){
					}else{
					objEjb.setField_name(dataC1[0].toString().trim());
					objEjb.setField_value(dataC1[1].toString().trim());
					objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
					ListObj.add(objEjb);
				  }}
				}
				setListadressingdata(ListObj);	
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
		Map<String, Object>WhereClouses=new HashMap<String, Object>();
		List<String> tablelist=new ArrayList<String>();
		try{
			List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
			 Collections.sort(requestParameterNames);
			 Iterator listit = requestParameterNames.iterator();
			 while (listit.hasNext()) {
					String Parmname = listit.next().toString();
					String paramValue=request.getParameter(Parmname);
						if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
								&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid")){
							setMap.put(Parmname, paramValue);
						}
			  }
				WhereClouse.put("id", getId());
				WhereClouses.put("mappedid", getId());
				tablelist.add("contactbasicdetails");
				tablelist.add("contactadressdetails");
			   boolean status = obhj.updateIntomultipleTable(tablelist, setMap,WhereClouse,WhereClouses,connectionSpace);
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
		return Listbasicsconfiguration;
	}
	public void setListbasicsconfiguration(
			List<ConfigurationUtilBean> listbasicsconfiguration) {
		Listbasicsconfiguration = listbasicsconfiguration;
	}
	public List<ConfigurationUtilBean> getListadressconfiguration() {
		return Listadressconfiguration;
	}
	public void setListadressconfiguration(
			List<ConfigurationUtilBean> listadressconfiguration) {
		Listadressconfiguration = listadressconfiguration;
	}
	public List<ConfigurationUtilBean> getListcustomconfiguration() {
		return Listcustomconfiguration;
	}
	public void setListcustomconfiguration(
			List<ConfigurationUtilBean> listcustomconfiguration) {
		Listcustomconfiguration = listcustomconfiguration;
	}
	public List<ConfigurationUtilBean> getListdescriptconfiguration() {
		return Listdescriptconfiguration;
	}
	public void setListdescriptconfiguration(
			List<ConfigurationUtilBean> listdescriptconfiguration) {
		Listdescriptconfiguration = listdescriptconfiguration;
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
	

	public List<ConfigurationUtilBean> getConatcbasicPerCalendr() {
		return conatcbasicPerCalendr;
	}
	public void setConatcbasicPerCalendr(
			List<ConfigurationUtilBean> conatcbasicPerCalendr) {
		this.conatcbasicPerCalendr = conatcbasicPerCalendr;
	}
	public List<ConfigurationUtilBean> getConatcbasicPerdropdown() {
		return conatcbasicPerdropdown;
	}
	public void setConatcbasicPerdropdown(
			List<ConfigurationUtilBean> conatcbasicPerdropdown) {
		this.conatcbasicPerdropdown = conatcbasicPerdropdown;
	}
	public List<ConfigurationUtilBean> getConatcaddressPerCalendr() {
		return conatcaddressPerCalendr;
	}
	public void setConatcaddressPerCalendr(
			List<ConfigurationUtilBean> conatcaddressPerCalendr) {
		this.conatcaddressPerCalendr = conatcaddressPerCalendr;
	}
	public List<ConfigurationUtilBean> getConatcaddressPerdropdown() {
		return conatcaddressPerdropdown;
	}
	public void setConatcaddressPerdropdown(
			List<ConfigurationUtilBean> conatcaddressPerdropdown) {
		this.conatcaddressPerdropdown = conatcaddressPerdropdown;
	}
	public List<ConfigurationUtilBean> getConatccustomPerCalendr() {
		return conatccustomPerCalendr;
	}
	public void setConatccustomPerCalendr(
			List<ConfigurationUtilBean> conatccustomPerCalendr) {
		this.conatccustomPerCalendr = conatccustomPerCalendr;
	}
	public List<ConfigurationUtilBean> getConatccustomPerdropdown() {
		return conatccustomPerdropdown;
	}
	public void setConatccustomPerdropdown(
			List<ConfigurationUtilBean> conatccustomPerdropdown) {
		this.conatccustomPerdropdown = conatccustomPerdropdown;
	}
	public List<ConfigurationUtilBean> getConatcdescriptnPerCalendr() {
		return conatcdescriptnPerCalendr;
	}
	public void setConatcdescriptnPerCalendr(
			List<ConfigurationUtilBean> conatcdescriptnPerCalendr) {
		this.conatcdescriptnPerCalendr = conatcdescriptnPerCalendr;
	}
	public List<ConfigurationUtilBean> getConatcdescriptnPerdropdown() {
		return conatcdescriptnPerdropdown;
	}
	public void setConatcdescriptnPerdropdown(
			List<ConfigurationUtilBean> conatcdescriptnPerdropdown) {
		this.conatcdescriptnPerdropdown = conatcdescriptnPerdropdown;
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
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request=request;
	}
	

}
