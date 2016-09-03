package com.Over2Cloud.ctrl.configuration;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SettingConfiguration extends ActionSupport{
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String id;
	private String commonMappedtablele=null;
	private String mappedtablele=null;
	private String header_name=null;
	private List<ConfigurationUtilBean>settingconfigBean=null;

	private List<ConfigurationUtilBean>listsettingCalendrTimeconfiguration=null;
	private List<ConfigurationUtilBean>listsettingPerdropdownconfiguration=null;
	private List<ConfigurationUtilBean>listsettingPerCalendrconfiguration=null;
	private List<ConfigurationUtilBean> listsettingtextconfiguration=null;
	private List<ConfigurationUtilBean> listsettingtextAreaconfiguration=null;
	/** levelId name */
	private String levelId = null;
	/** mapedtable name */
	private String mapedtable = null;
	/** active name */
	private boolean active;
	/** inactive name */
	private boolean inactive;
	/** field_value name */
	private String field_value=null;
	/** level_name name */
	private String level_name=null;
	/** field_type name */
	private String field_type=null;
	 
	/** Mandatory name */
	private boolean mandatory;

	/** Mandatory name */
	private boolean mandatorynot;
	
	@SuppressWarnings("unchecked")
	public String settingmenuconfiguration(){
		String returnResult=null;
		List resultdata=null;
		List resultdatass=null;
		try{
			if(userName==null || userName.equalsIgnoreCase(""))return LOGIN;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", getId());
			List<String> listdata=new ArrayList<String>();
			List<ConfigurationUtilBean> Listdata=new ArrayList<ConfigurationUtilBean>();
			listdata.add("id");
			listdata.add("mappedtable");
			listdata.add("module_name");
			resultdata = new CommonOperAtion().getSelectdataFromSelectedFields("allcommontabtable",listdata,paramMap,connectionSpace);
			if(resultdata!=null){
			for (Iterator iterator = resultdata.iterator(); iterator.hasNext();) {
				Object[] objectCal = (Object[]) iterator.next();
				if (objectCal[1].toString() != null && !objectCal[1].toString().equalsIgnoreCase("")) {
					Map<String, Object> paramMaps = new HashMap<String, Object>();
					
					resultdatass = new CommonOperAtion().getSelectTabledata(objectCal[1].toString(),paramMaps,connectionSpace);		
					
					List<String> paramMapss = new ArrayList<String>();
					if(resultdatass!=null){
						StringBuffer mappedIdsList=new StringBuffer();
						  for (Iterator iteratorss = resultdatass.iterator(); iteratorss.hasNext();) {
							  Object[] objectCalss = (Object[]) iteratorss.next();
								
							  if(objectCalss[1]!=null){
							   String[] ArrayId= objectCalss[1].toString().split("#");
									int j=1;
									for(String H:ArrayId){
										if(j<ArrayId.length)
											mappedIdsList.append("'"+H.trim()+"' ,");
										else
											mappedIdsList.append("'"+H.trim()+"'");
										j++;
									}
								}
							  setCommonMappedtablele(objectCalss[2].toString());
							 resultdatass = new CommonOperAtion().getSelectTabledatafromListId(objectCalss[2].toString(),mappedIdsList.toString(),connectionSpace);			
						    
						  }
						 }
					if(resultdatass!=null){
						  for (Iterator iteratorss = resultdatass.iterator(); iteratorss.hasNext();) {
								Object[] objectCals = (Object[]) iteratorss.next();
								
								    ConfigurationUtilBean ConfigBeanObj = new ConfigurationUtilBean();
									ConfigBeanObj.setId(Integer.parseInt(objectCals[0].toString()));
									ConfigBeanObj.setField_value(objectCals[1].toString());
									ConfigBeanObj.setQueryNames(objectCals[3].toString().trim());
									Listdata.add(ConfigBeanObj);
								  }
								setSettingconfigBean(Listdata);
								}
				              }
							} 
			           }
			returnResult=SUCCESS;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return returnResult;
	}

	public String createAssetsettingconfiguration(){
		//System.out.println("karnikaabhhghfd====");
		String returnResult=null;
		List resultdata=null;
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", getId());
			setId(getId());
	        setMapedtable(getMapedtable());
			resultdata = new CommonOperAtion().getSelectTabledata(
					"configuration_assetsetting", paramMap,connectionSpace);
			for (Iterator iterator = resultdata.iterator(); iterator.hasNext();) {
				Object[] objectCal = (Object[]) iterator.next();
				
						setHeader_name(objectCal[1].toString());
						if (objectCal[2].toString() != null && !objectCal[2].toString().equalsIgnoreCase("")) {
							Map<String, Object> whereClous = new HashMap<String, Object>();
							setMappedtablele(objectCal[2].toString());
							List<String> Listdata = new ArrayList<String>();
							Listdata.add("id");
							Listdata.add("field_name");
							Listdata.add("field_value");
							Listdata.add("colType");
							Listdata.add("validationType");
							Listdata.add("activeType");
							Listdata.add("mandatroy");
					   List configurationForList = new CommonOperAtion().getSelectdataFromSelectedFields(objectCal[2].toString(),Listdata,whereClous,connectionSpace);	
					   List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
					   List<ConfigurationUtilBean> ListtextareaObjs=new ArrayList<ConfigurationUtilBean>();
					   List<ConfigurationUtilBean> ListdateObjs=new ArrayList<ConfigurationUtilBean>();
					   List<ConfigurationUtilBean> ListtimeObjs=new ArrayList<ConfigurationUtilBean>();
					   List<ConfigurationUtilBean> ListdropdownObjs=new ArrayList<ConfigurationUtilBean>();
					   if(configurationForList!=null){
					   for (Object c1 : configurationForList) {
						Object[] dataC1 = (Object[]) c1;
						ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
						try{
							if(dataC1[3].toString().equalsIgnoreCase("Text")){
								objEjb.setField_name(dataC1[1].toString().trim());
								objEjb.setField_value(dataC1[2].toString().trim());
								objEjb.setColType(dataC1[3].toString().trim());
								objEjb.setValidationType(dataC1[4].toString().trim());
								if(dataC1[6]==null)
									objEjb.setMandatory(false);
								else if(dataC1[6].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[6].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								
								ListObjs.add(objEjb);
							  }
							 }catch (Exception e) {
								// TODO: handle exception
							}
							try{
							if(dataC1[3].toString().equalsIgnoreCase("Date")){
								objEjb.setField_name(dataC1[1].toString().trim());
								objEjb.setField_value(dataC1[2].toString().trim());
								objEjb.setColType(dataC1[3].toString().trim());
								objEjb.setValidationType(dataC1[4].toString().trim());
								if(dataC1[6]==null)
									objEjb.setMandatory(false);
								else if(dataC1[6].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[6].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								
								ListdateObjs.add(objEjb);
							 } }catch (Exception e) {
									// TODO: handle exception
								}
							 try{
                           if(dataC1[3].toString().equalsIgnoreCase("Time")){
                            	objEjb.setField_name(dataC1[1].toString().trim());
								objEjb.setField_value(dataC1[2].toString().trim());
								objEjb.setColType(dataC1[3].toString().trim());
								objEjb.setValidationType(dataC1[4].toString().trim());
								if(dataC1[6]==null)
									objEjb.setMandatory(false);
								else if(dataC1[6].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[6].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								
								ListtimeObjs.add(objEjb);
							}}catch (Exception e) {
								// TODO: handle exception
							}
							 try{
                           if(dataC1[3].toString().equalsIgnoreCase("D")){
                            	objEjb.setField_name(dataC1[1].toString().trim());
								objEjb.setField_value(dataC1[2].toString().trim());
								objEjb.setColType(dataC1[3].toString().trim());
								objEjb.setValidationType(dataC1[4].toString().trim());
								if(dataC1[6]==null)
									objEjb.setMandatory(false);
								else if(dataC1[6].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[6].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								
								ListdropdownObjs.add(objEjb);
							 }}catch (Exception e) {
									// TODO: handle exception
								}
							 try{
                           if(dataC1[3].toString().equalsIgnoreCase("TextArea")){
								objEjb.setField_name(dataC1[1].toString().trim());
								objEjb.setField_value(dataC1[2].toString().trim());
								objEjb.setColType(dataC1[3].toString().trim());
								objEjb.setValidationType(dataC1[4].toString().trim());
								if(dataC1[6]==null)
									objEjb.setMandatory(false);
								else if(dataC1[6].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[6].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								
								ListtextareaObjs.add(objEjb);
								
							}
						}catch (Exception e) {
							// TODO: handle exception
						}
					   }
						 setListsettingtextAreaconfiguration(ListtextareaObjs);
						 setListsettingPerCalendrconfiguration(ListdateObjs);
						 setListsettingPerdropdownconfiguration(ListdropdownObjs);
						 setListsettingCalendrTimeconfiguration(ListtimeObjs);
					     setListsettingtextconfiguration(ListObjs);
					   }
			   }
		   }
			returnResult=SUCCESS;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return returnResult;
	}
	public String createsettingconfiguration(){
		String returnResult=null;
		List resultdata=null;
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", getId());
			setId(getId());
	        setMapedtable(getMapedtable());
			resultdata = new CommonOperAtion().getSelectTabledata(
					getCommonMappedtablele(), paramMap,connectionSpace);
			for (Iterator iterator = resultdata.iterator(); iterator.hasNext();) {
				Object[] objectCal = (Object[]) iterator.next();
				
						setHeader_name(objectCal[1].toString());
						if (objectCal[2].toString() != null && !objectCal[2].toString().equalsIgnoreCase("")) {
							Map<String, Object> whereClous = new HashMap<String, Object>();
							setMappedtablele(objectCal[2].toString());
							List<String> Listdata = new ArrayList<String>();
							Listdata.add("id");
							Listdata.add("field_name");
							Listdata.add("field_value");
							Listdata.add("colType");
							Listdata.add("activeType");
							Listdata.add("mandatroy");
					   List configurationForList = new CommonOperAtion().getSelectdataFromSelectedFields(objectCal[2].toString(),Listdata,whereClous,connectionSpace);	
					 // System.out.println("======"+configurationForList.size());
					   List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
					   List<ConfigurationUtilBean> ListtextareaObjs=new ArrayList<ConfigurationUtilBean>();
					   List<ConfigurationUtilBean> ListdateObjs=new ArrayList<ConfigurationUtilBean>();
					   List<ConfigurationUtilBean> ListtimeObjs=new ArrayList<ConfigurationUtilBean>();
					   List<ConfigurationUtilBean> ListdropdownObjs=new ArrayList<ConfigurationUtilBean>();
					   if(configurationForList!=null){
					   for (Object c1 : configurationForList) {
						Object[] dataC1 = (Object[]) c1;
						ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
						try{
							if(dataC1[3].toString().equalsIgnoreCase("Text")){
								objEjb.setField_name(dataC1[1].toString().trim());
								objEjb.setField_value(dataC1[2].toString().trim());
								if(dataC1[5]==null)
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								ListObjs.add(objEjb);
							  }
							 }catch (Exception e) {
								// TODO: handle exception
							}
							try{
							if(dataC1[3].toString().equalsIgnoreCase("Date")){
								objEjb.setField_name(dataC1[1].toString().trim());
								objEjb.setField_value(dataC1[2].toString().trim());
								if(dataC1[5]==null)
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								ListdateObjs.add(objEjb);
							 } }catch (Exception e) {
									// TODO: handle exception
								}
							 try{
                           if(dataC1[3].toString().equalsIgnoreCase("Time")){
                            	objEjb.setField_name(dataC1[1].toString().trim());
								objEjb.setField_value(dataC1[2].toString().trim());
								if(dataC1[5]==null)
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								ListtimeObjs.add(objEjb);
							}}catch (Exception e) {
								// TODO: handle exception
							}
							 try{
                           if(dataC1[3].toString().equalsIgnoreCase("D")){
                            	objEjb.setField_name(dataC1[1].toString().trim());
								objEjb.setField_value(dataC1[2].toString().trim());
								if(dataC1[5]==null)
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								ListdropdownObjs.add(objEjb);
							 }}catch (Exception e) {
									// TODO: handle exception
								}
							 try{
                           if(dataC1[3].toString().equalsIgnoreCase("TextArea")){
								objEjb.setField_name(dataC1[1].toString().trim());
								objEjb.setField_value(dataC1[2].toString().trim());
								if(dataC1[5]==null)
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								ListtextareaObjs.add(objEjb);
								
							}
						}catch (Exception e) {
							// TODO: handle exception
						}
					   }
						 setListsettingtextAreaconfiguration(ListtextareaObjs);
						 setListsettingPerCalendrconfiguration(ListdateObjs);
						 setListsettingPerdropdownconfiguration(ListdropdownObjs);
						 setListsettingCalendrTimeconfiguration(ListtimeObjs);
					     setListsettingtextconfiguration(ListObjs);
					   }
			   }
		   }
			returnResult=SUCCESS;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return returnResult;
	}
	public String editorsettingconfiguration(){
		String returnResult=null;
		List resultdata=null;
		try{
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", getId());
			setId(getId());
	        setMapedtable(getMapedtable());
			resultdata = new CommonOperAtion().getSelectTabledata(
					getCommonMappedtablele(), paramMap,connectionSpace);
			for (Iterator iterator = resultdata.iterator(); iterator.hasNext();) {
				Object[] objectCal = (Object[]) iterator.next();
				
						setHeader_name(objectCal[1].toString());
						if (objectCal[2].toString() != null && !objectCal[2].toString().equalsIgnoreCase("")) {
							List<String> Listdata = new ArrayList<String>();
							Listdata.add("field_name");
							Listdata.add("field_value");
							Listdata.add("id");
							Listdata.add("colType");
							Listdata.add("activeType");
							Listdata.add("mandatroy");
							setMappedtablele(objectCal[2].toString());
							Map<String, Object> whereClous = new HashMap<String, Object>();
							setMappedtablele(objectCal[2].toString());
							  List configurationForList = new CommonOperAtion().getSelectdataFromSelectedFields(objectCal[2].toString(),Listdata,whereClous,connectionSpace);
					   List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
					   List<ConfigurationUtilBean> ListtextareaObjs=new ArrayList<ConfigurationUtilBean>();
					   List<ConfigurationUtilBean> ListdateObjs=new ArrayList<ConfigurationUtilBean>();
					   List<ConfigurationUtilBean> ListtimeObjs=new ArrayList<ConfigurationUtilBean>();
					   List<ConfigurationUtilBean> ListdropdownObjs=new ArrayList<ConfigurationUtilBean>();
					   if(configurationForList!=null){
					   for (Object c1 : configurationForList) {
						Object[] dataC1 = (Object[]) c1;
						ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
							if(dataC1[3].toString().equalsIgnoreCase("Text")){
								//System.out.println("Field_name"+dataC1[0].toString().trim());
								objEjb.setField_name(dataC1[0].toString().trim());
								objEjb.setField_value(dataC1[1].toString().trim());
								objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
								objEjb.setActives(dataC1[4].toString().trim());
								if(dataC1[5]==null)
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								ListObjs.add(objEjb);
							 }
							if(dataC1[3].toString().equalsIgnoreCase("Date")){
							//	System.out.println("Field_name"+dataC1[0].toString().trim());
								objEjb.setField_name(dataC1[0].toString().trim());
								objEjb.setField_value(dataC1[1].toString().trim());
								objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
								objEjb.setActives(dataC1[4].toString().trim());
								if(dataC1[5]==null)
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								ListdateObjs.add(objEjb);
							 }
                           if(dataC1[3].toString().equalsIgnoreCase("Time")){
                        		//System.out.println("Field_name"+dataC1[0].toString().trim());
                        	   objEjb.setField_name(dataC1[0].toString().trim());
                        	   objEjb.setField_value(dataC1[1].toString().trim());
								objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
								objEjb.setActives(dataC1[4].toString().trim());
								if(dataC1[5]==null)
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								ListtimeObjs.add(objEjb);
							}
                           if(dataC1[3].toString().equalsIgnoreCase("D")){
                        	//	System.out.println("Field_name"+dataC1[0].toString().trim());
                        	   objEjb.setField_name(dataC1[0].toString().trim());
                        	   objEjb.setField_value(dataC1[1].toString().trim());
								objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
								objEjb.setActives(dataC1[4].toString().trim());
								if(dataC1[5]==null)
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								ListdropdownObjs.add(objEjb);
							 }
                           if(dataC1[3].toString().equalsIgnoreCase("TextArea")){
                        		//System.out.println("Field_name"+dataC1[0].toString().trim());
                        	   objEjb.setField_name(dataC1[0].toString().trim());
                        	   objEjb.setField_value(dataC1[1].toString().trim());
								objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
								objEjb.setActives(dataC1[4].toString().trim());
								if(dataC1[5]==null)
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("0"))
									objEjb.setMandatory(false);
								else if(dataC1[5].toString().equalsIgnoreCase("1"))
									objEjb.setMandatory(true);
								ListtextareaObjs.add(objEjb);
								
							}
						}
					   
						 setListsettingtextAreaconfiguration(ListtextareaObjs);
						 setListsettingPerCalendrconfiguration(ListdateObjs);
						 setListsettingPerdropdownconfiguration(ListdropdownObjs);
						 setListsettingCalendrTimeconfiguration(ListtimeObjs);
					     setListsettingtextconfiguration(ListObjs);
					   }
			   }
		   }
			returnResult=SUCCESS;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return returnResult;
	}
	
	@SuppressWarnings("unchecked")
	public String editesettingfieldOperation(){
		String returnResult=null;
		Map<String, Object> setMap = new HashMap<String, Object>();
		/** HashMap Object name to set WhereClouse Parameter*/
		Map<String, Object> WhereClouse = new HashMap<String, Object>();
		try{
			/**checked level  name value can not be null or blank*/
			 if(getLevel_name()!=null && getField_value().equalsIgnoreCase("") && !active && !inactive && !mandatory && !mandatorynot){
					 //all common data
				   setMap.put("field_name", getLevel_name());
					WhereClouse.put("id",getId());
					boolean flag=new CommonOperAtion().updateIntoTable(mapedtable, setMap,WhereClouse,connectionSpace);
					if(flag){
						addActionMessage("Update Record Sucess Fully!!!");
					}else{
						addActionError("Opps There is some Problem!");
					}
				}
				if(getField_value()!=null && !getField_value().equalsIgnoreCase("")&& !active && !inactive && !mandatory && !mandatorynot){
				
					InsertDataTable ob = new InsertDataTable();
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					ob = new InsertDataTable();
					ob.setColName("field_value");
					ob.setDataName(getField_value().replace(' ','_').toLowerCase());
					insertData.add(ob);
					ob = new InsertDataTable();
					ob.setColName("field_name");
					ob.setDataName(getField_value());
					insertData.add(ob);
					ob = new InsertDataTable();
					ob.setColName("colType");
					ob.setDataName(getField_type());
					insertData.add(ob);
					List<Object[]> queryResult =  new CommonOperAtion().insertIntoTableReturnId(mapedtable, insertData,connectionSpace);
						if(queryResult!=null){
							addActionMessage("Update Record Sucessfully");
						}else{
							addActionError("Opps There is some Problem!");
						}
					}
				try{
				if (active && getLevel_name()!=null && (getField_value().equalsIgnoreCase(null) ||getField_value().equalsIgnoreCase(""))) {
					if(getId()!=null){
						WhereClouse.put("id", getId());
						setMap.put("activeType","true");
						 boolean flag=new CommonOperAtion().updateIntoTable(mapedtable, setMap,WhereClouse,connectionSpace);
							if(flag){
								addActionMessage("Active Record SucessFully");
							}else{
								addActionError("Opps There is some Problem!");
							}
					}
				 }
				}
				catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				try{
					if (inactive && getLevel_name()!=null && (getField_value().equalsIgnoreCase(null) ||getField_value().equalsIgnoreCase(""))) {
						if(getId()!=null){
							WhereClouse.put("id", getId());
							setMap.put("activeType","false");
							 boolean flag=new CommonOperAtion().updateIntoTable(mapedtable, setMap,WhereClouse,connectionSpace);
								if(flag){
									addActionMessage("Inactive Record SucessFully");
								}else{
									addActionError("Opps There is some Problem!");
								}
					        }
						}
				   }
				catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
					try{
					if (mandatory && getLevel_name()!=null && (getField_value().equalsIgnoreCase(null) ||getField_value().equalsIgnoreCase(""))) {
						if(getId()!=null){
							WhereClouse.put("id", getId());
							setMap.put("mandatroy","1");
							 boolean flag=new CommonOperAtion().updateIntoTable(mapedtable, setMap,WhereClouse,connectionSpace);
								if(flag){
									addActionMessage("Mandatory Record SucessFully");
								}else{
									addActionError("Opps There is some Problem!");
								}
					        }
						}
					}
					catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					try{
						if (mandatorynot && getLevel_name()!=null && (getField_value().equalsIgnoreCase(null) ||getField_value().equalsIgnoreCase(""))) {
							if(getId()!=null){
								WhereClouse.put("id", getId());
								setMap.put("mandatroy","0");
								 boolean flag=new CommonOperAtion().updateIntoTable(mapedtable, setMap,WhereClouse,connectionSpace);
									if(flag){
										addActionMessage("Unmandatory Record SucessFully");
									}else{
										addActionError("Opps There is Some Problem!");
									}
						        }
							}
						}
						catch (Exception e) {
							e.printStackTrace();
							// TODO: handle exception
						}
			returnResult=SUCCESS;
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return returnResult;
	}
	

	public String getId() {
		return id;
	}

	public String getHeader_name() {
		return header_name;
	}

	public void setHeader_name(String header_name) {
		this.header_name = header_name;
	}

	public boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public List<ConfigurationUtilBean> getListsettingCalendrTimeconfiguration() {
		return listsettingCalendrTimeconfiguration;
	}

	public void setListsettingCalendrTimeconfiguration(
			List<ConfigurationUtilBean> listsettingCalendrTimeconfiguration) {
		this.listsettingCalendrTimeconfiguration = listsettingCalendrTimeconfiguration;
	}

	public List<ConfigurationUtilBean> getListsettingPerdropdownconfiguration() {
		return listsettingPerdropdownconfiguration;
	}

	public void setListsettingPerdropdownconfiguration(
			List<ConfigurationUtilBean> listsettingPerdropdownconfiguration) {
		this.listsettingPerdropdownconfiguration = listsettingPerdropdownconfiguration;
	}

	public List<ConfigurationUtilBean> getListsettingPerCalendrconfiguration() {
		return listsettingPerCalendrconfiguration;
	}

	public void setListsettingPerCalendrconfiguration(
			List<ConfigurationUtilBean> listsettingPerCalendrconfiguration) {
		this.listsettingPerCalendrconfiguration = listsettingPerCalendrconfiguration;
	}

	public List<ConfigurationUtilBean> getListsettingtextconfiguration() {
		return listsettingtextconfiguration;
	}

	public void setListsettingtextconfiguration(
			List<ConfigurationUtilBean> listsettingtextconfiguration) {
		this.listsettingtextconfiguration = listsettingtextconfiguration;
	}

	public List<ConfigurationUtilBean> getListsettingtextAreaconfiguration() {
		return listsettingtextAreaconfiguration;
	}

	public void setListsettingtextAreaconfiguration(
			List<ConfigurationUtilBean> listsettingtextAreaconfiguration) {
		this.listsettingtextAreaconfiguration = listsettingtextAreaconfiguration;
	}

	public List<ConfigurationUtilBean> getSettingconfigBean() {
		return settingconfigBean;
	}
	public void setSettingconfigBean(List<ConfigurationUtilBean> settingconfigBean) {
		this.settingconfigBean = settingconfigBean;
	}
	public String getCommonMappedtablele() {
		return commonMappedtablele;
	}

	public void setCommonMappedtablele(String commonMappedtablele) {
		this.commonMappedtablele = commonMappedtablele;
	}

	public String getMappedtablele() {
		return mappedtablele;
	}

	public void setMappedtablele(String mappedtablele) {
		this.mappedtablele = mappedtablele;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getMapedtable() {
		return mapedtable;
	}

	public void setMapedtable(String mapedtable) {
		this.mapedtable = mapedtable;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	public String getField_value() {
		return field_value;
	}

	public void setField_value(String field_value) {
		this.field_value = field_value;
	}

	public String getLevel_name() {
		return level_name;
	}

	public void setLevel_name(String level_name) {
		this.level_name = level_name;
	}

	public String getField_type() {
		return field_type;
	}

	public void setField_type(String field_type) {
		this.field_type = field_type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isMandatorynot() {
		return mandatorynot;
	}

	public void setMandatorynot(boolean mandatorynot) {
		this.mandatorynot = mandatorynot;
	}
	
	
}