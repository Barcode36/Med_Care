package com.Over2Cloud.ctrl.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.Rnd.createTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EditorConfiguration extends ActionSupport{
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String level1Name;
	private String level2Name;
	private String level3Name;
	private String level4Name;
	private String level5Name;
	private String dropDownStatus[]=new String[10];
	boolean level1Status[]=new boolean[50];
	boolean level2Status[]=new boolean[50];
	boolean level3Status[]=new boolean[50];
	boolean level4Status[]=new boolean[50];
	boolean level5Status[]=new boolean[50];

	private int globalLevel=0;
	private String mappedtablelevel1;
	private String mappedtablelevel2;
	private String mappedtablelevel3;
	private String mappedtablelevel4;
	private String mappedtablelevel5;
	private List<ConfigurationUtilBean> Listconfiguration=null;
	private List<ConfigurationUtilBean> Listconfiguration1=null;
	private List<ConfigurationUtilBean> Listconfiguration2=null;
	private List<ConfigurationUtilBean> Listconfiguration3=null;
	private List<ConfigurationUtilBean> Listconfiguration4=null;
	private List<ConfigurationUtilBean> Listconfiguration5=null;
	
	private int id;
	private String filed_name=null;
	private String commonMappedtablele=null;
	
	public String geteditorconfiguration(){
		try{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
		List<String>colname=new ArrayList<String>();
		Map<String, Object> temp=new HashMap<String, Object>();
		createTable cbt=new createTable();
		List resultdata=null;
		List<String> data=new ArrayList<String>();
		data.add("mappedid");
		data.add("table_name");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", getId());
		setId(getId());
		setFiled_name(getFiled_name());
		//System.out.println("Filed_name"+getFiled_name());
		resultdata = new CommonOperAtion().getSelectTabledata(
				"allcommontabtable", paramMap,connectionSpace);
		for (Iterator iterator = resultdata.iterator(); iterator.hasNext();) {
			Object[] objectCal = (Object[]) iterator.next();
			Map<String, Object> paramMaps = new HashMap<String, Object>();

			if (objectCal[7] != null
					&& !objectCal[7].toString().equalsIgnoreCase("")) {

		//getting the current logined user organization level
		colname.add("orgLevel");
		colname.add("levelName");
		int level =0;
		List orgntnlevel=cbt.viewAllDataEitherSelectOrAll(objectCal[7].toString(),colname,temp,connectionSpace);
		if(orgntnlevel.size()>0)
		{
			for (Object c : orgntnlevel) {
				Object[] dataC = (Object[]) c;
				String namesOfLevel=(String)dataC[1];
				
				String split[]=namesOfLevel.split("#");
				level =Integer.parseInt((String)dataC[0]);
				if(level>0)
					setLevel1Name(split[0]);
				if(level>1)
					setLevel2Name(split[1]);
				if(level>2)
					setLevel3Name(split[2]);
				if(level>3)
					setLevel4Name(split[3]);
				if(level>4)
					setLevel5Name(split[4]);
				
				/**
				 * Setting the levels names and setting the visibility of fields in add page for level 1
				 */
				if(level>0){
					setMappedtablelevel1(objectCal[5].toString());
					List<ConfigurationUtilBean> ListObj=new ArrayList<ConfigurationUtilBean>();
					List organizationLoList1=Configuration.getConfigurationIdss(objectCal[5].toString(), data, accountID, temp, connectionSpace);	
					for (Object c1 : organizationLoList1) {
						Object[] dataC1 = (Object[]) c1;
						
							ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
							objEjb.setActives(dataC1[4].toString().trim());
							objEjb.setField_name(dataC1[0].toString().trim());
							objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
							if(dataC1[5]==null)
								objEjb.setMandatory(false);
							else if(dataC1[5].toString().equalsIgnoreCase("0"))
								objEjb.setMandatory(false);
							else if(dataC1[5].toString().equalsIgnoreCase("1"))
								objEjb.setMandatory(true);
							ListObj.add(objEjb);
					 
					}
					setListconfiguration(ListObj);	
				}
				
				if(objectCal[12]!=null && !objectCal[12].toString().equalsIgnoreCase("")){
				String[] levalemappedtable=objectCal[12].toString().split("#");
				if(level>1){
					setMappedtablelevel2(levalemappedtable[0]);
					//for level 2
					List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
					List configurationForLevl2List=Configuration.getConfigurationIdss(levalemappedtable[0], data, accountID, temp, connectionSpace);
					for (Object c1 : configurationForLevl2List) {
						Object[] dataC1 = (Object[]) c1;

							ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
							objEjb.setField_name(dataC1[0].toString().trim());
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
					  setListconfiguration2(ListObjs);
					
				}
				if(level>2){
					//for level3
					setMappedtablelevel3(levalemappedtable[1]);
					List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
					List configurationForLevLIst=Configuration.getConfigurationIdss(levalemappedtable[1], data, accountID, temp, connectionSpace);
					for (Object c1 : configurationForLevLIst) {
						Object[] dataC1 = (Object[]) c1;
							ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
							objEjb.setField_name(dataC1[0].toString().trim());
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
					setListconfiguration3(ListObjs);
					}
				if(level>3){
					setMappedtablelevel4(levalemappedtable[2]);
					//for level4
					List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
					List configurationForList4=Configuration.getConfigurationIdss(levalemappedtable[2], data, accountID, temp, connectionSpace);
					for (Object c1 : configurationForList4) {
						Object[] dataC1 = (Object[]) c1;
					
							ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
							objEjb.setField_name(dataC1[0].toString().trim());
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
						
					setListconfiguration4(ListObjs);
				}
				if(level>4){
					setMappedtablelevel5(levalemappedtable[3]);
					List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
					List configurationForList4=Configuration.getConfigurationIdss(levalemappedtable[3], data, accountID, temp, connectionSpace);
					
					for (Object c1 : configurationForList4) {
						Object[] dataC1 = (Object[]) c1;
							ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
							objEjb.setField_name(dataC1[0].toString().trim());
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
					  
					setListconfiguration5(ListObjs);
				}
				globalLevel=level;
				return "success";
			}
				}
			}
	
			 }else{
					    setCommonMappedtablele(objectCal[5].toString());
						String[] headtername=objectCal[11].toString().split("#");
						if (objectCal[2] != null
								&& !objectCal[2].toString().equalsIgnoreCase("")) {
							if(headtername.length>=1){
								setLevel1Name(headtername[0].toString());
								}
							setMappedtablelevel1(objectCal[2].toString());
						
							Map<String, Object>mapObject=new HashMap<String,Object>();
							mapObject.put("table_name",objectCal[2].toString());
							String mappedIds=null;
							String mappedTable=null;
						  resultdata = new CommonOperAtion().getSelectTabledata(objectCal[5].toString(), mapObject,connectionSpace);	
						  if(resultdata!=null){
						  for (Object c1 : resultdata) {
							Object[] dataC1 = (Object[]) c1;
							mappedIds=dataC1[1].toString().trim();
							mappedTable=dataC1[2].toString().trim();
						}
						String tempMappedIds[]=mappedIds.split("#");
						StringBuffer mappedIdsList=new StringBuffer();
						int i=1;
						for(String H:tempMappedIds)
						{
							if(i<tempMappedIds.length)
								mappedIdsList.append("'"+H.trim()+"' ,");
							else
								mappedIdsList.append("'"+H.trim()+"'");
							i++;
						}
						List configurationForList=Configuration.getConfigurationListAppendid(mappedTable, mappedIdsList.toString(),connectionSpace);
					
						List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
						for (Object c1 : configurationForList) {
							Object[] dataC1 = (Object[]) c1;
						
								ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
								objEjb.setField_name(dataC1[0].toString().trim());
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
							
						  setListconfiguration1(ListObjs);
						   }
						}
						if (objectCal[3] != null
								&& !objectCal[3].toString().equalsIgnoreCase("")) {
							if(headtername.length>=2){
								setLevel2Name(headtername[1].toString());
								}
							
							setMappedtablelevel2(objectCal[3].toString());
							Map<String, Object>mapObject=new HashMap<String,Object>();
							mapObject.put("table_name", objectCal[3].toString());
							String mappedIds=null;
							String mappedTable=null;
							resultdata = new CommonOperAtion().getSelectTabledata(objectCal[5].toString(), mapObject,connectionSpace);	
							if(resultdata.size()!=0){
							for (Object c1 : resultdata) {
								Object[] dataC1 = (Object[]) c1;
								mappedIds=dataC1[1].toString().trim();
								mappedTable=dataC1[2].toString().trim();
							}
							String tempMappedIds[]=mappedIds.split("#");
							StringBuffer mappedIdsList=new StringBuffer();
							int i=1;
							for(String H:tempMappedIds)
							{
								if(i<tempMappedIds.length)
									mappedIdsList.append("'"+H.trim()+"' ,");
								else
									mappedIdsList.append("'"+H.trim()+"'");
								i++;
							}
							List configurationForList2=Configuration.getConfigurationListAppendid(mappedTable, mappedIdsList.toString(),connectionSpace);
							List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
							for (Object c1 : configurationForList2) {
								Object[] dataC1 = (Object[]) c1;
							
									ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
									objEjb.setField_name(dataC1[0].toString().trim());
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
							 setListconfiguration2(ListObjs);
							 }
			                }	
						if (objectCal[4] != null
								&& !objectCal[4].toString().equalsIgnoreCase("")) {
	                		setMappedtablelevel3(objectCal[4].toString());
	                		if(headtername.length>=3){
	                			setLevel3Name(headtername[2].toString());
								}
							Map<String, Object>mapObject=new HashMap<String,Object>();
							mapObject.put("table_name", objectCal[4].toString());
			
							String mappedIds=null;
							String mappedTable=null;
							resultdata = new CommonOperAtion().getSelectTabledata(objectCal[5].toString(), mapObject,connectionSpace);	
					
							if(resultdata.size()!=0){
							for (Object c1 : resultdata) {
								Object[] dataC1 = (Object[]) c1;
								mappedIds=dataC1[1].toString().trim();
								mappedTable=dataC1[2].toString().trim();
							}
							String tempMappedIds[]=mappedIds.split("#");
							StringBuffer mappedIdsList=new StringBuffer();
							int i=1;
							for(String H:tempMappedIds)
							{
								if(i<tempMappedIds.length)
									mappedIdsList.append("'"+H.trim()+"' ,");
								else
									mappedIdsList.append("'"+H.trim()+"'");
								i++;
							}
							List configurationForList3=Configuration.getConfigurationListAppendid(mappedTable, mappedIdsList.toString(),connectionSpace);
							List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
							for (Object c1 : configurationForList3) {
								Object[] dataC1 = (Object[]) c1;
					
									ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
									objEjb.setField_name(dataC1[0].toString().trim());
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
							 setListconfiguration3(ListObjs);
							  }	
							}
						if (objectCal[10] != null
								&& !objectCal[10].toString().equalsIgnoreCase("")) {
									setMappedtablelevel4(objectCal[10].toString());
									if(headtername.length>=4){
			                		 setLevel4Name(headtername[3].toString());
									}
									Map<String, Object>mapObject=new HashMap<String,Object>();
									mapObject.put("table_name", objectCal[10].toString());
									String mappedIds=null;
									String mappedTable=null;
									resultdata = new CommonOperAtion().getSelectTabledata(objectCal[5].toString(), mapObject,connectionSpace);	
									if(resultdata.size()!=0){
									for (Object c1 : resultdata) {
										Object[] dataC1 = (Object[]) c1;
										mappedIds=dataC1[1].toString().trim();
										mappedTable=dataC1[2].toString().trim();
									}
									String tempMappedIds[]=mappedIds.split("#");
									StringBuffer mappedIdsList=new StringBuffer();
									int i=1;
									for(String H:tempMappedIds)
									{
										if(i<tempMappedIds.length)
											mappedIdsList.append("'"+H.trim()+"' ,");
										else
											mappedIdsList.append("'"+H.trim()+"'");
										i++;
									}
									List configurationForList3=Configuration.getConfigurationListAppendid(mappedTable, mappedIdsList.toString(),connectionSpace);
									List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
									for (Object c1 : configurationForList3) {
										Object[] dataC1 = (Object[]) c1;
								
											ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
											objEjb.setField_name(dataC1[0].toString().trim());
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
										
									  setListconfiguration4(ListObjs);
						   }
				
			 
	                  }}
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
	
		}
		   return "successwithoutLevel";
	}
	public String getLevel1Name() {
		return level1Name;
	}

	public void setLevel1Name(String level1Name) {
		this.level1Name = level1Name;
	}

	public String getLevel2Name() {
		return level2Name;
	}

	public void setLevel2Name(String level2Name) {
		this.level2Name = level2Name;
	}

	public String getLevel3Name() {
		return level3Name;
	}

	public void setLevel3Name(String level3Name) {
		this.level3Name = level3Name;
	}

	public String getLevel4Name() {
		return level4Name;
	}

	public void setLevel4Name(String level4Name) {
		this.level4Name = level4Name;
	}

	public String getLevel5Name() {
		return level5Name;
	}

	public void setLevel5Name(String level5Name) {
		this.level5Name = level5Name;
	}

	public String[] getDropDownStatus() {
		return dropDownStatus;
	}

	public void setDropDownStatus(String[] dropDownStatus) {
		this.dropDownStatus = dropDownStatus;
	}

	public int getGlobalLevel() {
		return globalLevel;
	}

	public void setGlobalLevel(int globalLevel) {
		this.globalLevel = globalLevel;
	}
	public String getMappedtablelevel1() {
		return mappedtablelevel1;
	}
	public void setMappedtablelevel1(String mappedtablelevel1) {
		this.mappedtablelevel1 = mappedtablelevel1;
	}


	public String getMappedtablelevel2() {
		return mappedtablelevel2;
	}


	public void setMappedtablelevel2(String mappedtablelevel2) {
		this.mappedtablelevel2 = mappedtablelevel2;
	}


	public String getMappedtablelevel3() {
		return mappedtablelevel3;
	}


	public void setMappedtablelevel3(String mappedtablelevel3) {
		this.mappedtablelevel3 = mappedtablelevel3;
	}


	public String getMappedtablelevel4() {
		return mappedtablelevel4;
	}


	public void setMappedtablelevel4(String mappedtablelevel4) {
		this.mappedtablelevel4 = mappedtablelevel4;
	}


	public String getMappedtablelevel5() {
		return mappedtablelevel5;
	}


	public void setMappedtablelevel5(String mappedtablelevel5) {
		this.mappedtablelevel5 = mappedtablelevel5;
	}


	public List<ConfigurationUtilBean> getListconfiguration() {
		return Listconfiguration;
	}


	public void setListconfiguration(List<ConfigurationUtilBean> listconfiguration) {
		Listconfiguration = listconfiguration;
	}


	public List<ConfigurationUtilBean> getListconfiguration1() {
		return Listconfiguration1;
	}


	public void setListconfiguration1(List<ConfigurationUtilBean> listconfiguration1) {
		Listconfiguration1 = listconfiguration1;
	}


	public List<ConfigurationUtilBean> getListconfiguration2() {
		return Listconfiguration2;
	}


	public void setListconfiguration2(List<ConfigurationUtilBean> listconfiguration2) {
		Listconfiguration2 = listconfiguration2;
	}


	public List<ConfigurationUtilBean> getListconfiguration3() {
		return Listconfiguration3;
	}


	public void setListconfiguration3(List<ConfigurationUtilBean> listconfiguration3) {
		Listconfiguration3 = listconfiguration3;
	}


	public List<ConfigurationUtilBean> getListconfiguration4() {
		return Listconfiguration4;
	}


	public void setListconfiguration4(List<ConfigurationUtilBean> listconfiguration4) {
		Listconfiguration4 = listconfiguration4;
	}


	public List<ConfigurationUtilBean> getListconfiguration5() {
		return Listconfiguration5;
	}


	
	public String getCommonMappedtablele() {
		return commonMappedtablele;
	}
	public void setCommonMappedtablele(String commonMappedtablele) {
		this.commonMappedtablele = commonMappedtablele;
	}
	public void setListconfiguration5(List<ConfigurationUtilBean> listconfiguration5) {
		Listconfiguration5 = listconfiguration5;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFiled_name() {
		return filed_name;
	}
	public void setFiled_name(String filed_name) {
		this.filed_name = filed_name;
	}

}
