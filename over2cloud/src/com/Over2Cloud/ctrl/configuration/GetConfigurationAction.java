package com.Over2Cloud.ctrl.configuration;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.BeanUtil.ListConfigbean;
import com.Over2Cloud.BeanUtil.TableColumes;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.Rnd.createTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author pankaj
 */
public class GetConfigurationAction extends ActionSupport {

	Map session = ActionContext.getContext().getSession();
	String accountID = (String) session.get("accountid");
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	String dbName=(String)session.get("Dbname");
	/** id name */
	private String id = null;
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
	
	/** field_type name */
	private String validation_type=null;
	private String create_table=null;
	
	/** Mandatory name */
	private boolean mandatory;
	
	private String comonmaped=null;
	/** id name */
	/** Mandatory name */
	private boolean mandatorynot;
	private static final long serialVersionUID = 1L;
	private Map<ListConfigbean, List<ConfigurationUtilBean>> dataList = new LinkedHashMap<ListConfigbean, List<ConfigurationUtilBean>>();
	private Map<String, List<ConfigurationUtilBean>> dataList1 = new LinkedHashMap<String, List<ConfigurationUtilBean>>();

	
	public String GetLevelConfiguration() {
		
		if(userName==null || userName.equalsIgnoreCase(""))
			return LOGIN;
		String returnString = ERROR;
		List resultdata = null;
		String[] ListArray = null;
		String[] ListArrayTemp = null;
		String[] ListArrayTemp1 = null;
		try {
			setLevelId("string"+getId());
			Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
			Map<String, Object> paramMaps = new LinkedHashMap<String, Object>();
			paramMap.put("level_congtable", mapedtable);
			// all common data
			resultdata = new CommonOperAtion().getSelectTabledata(
					"allcommontabtable", paramMap,connectionSpace);
			for (Iterator iterator = resultdata.iterator(); iterator.hasNext();) {
				Object[] objectCal = (Object[]) iterator.next();
				if (objectCal[9] != null
						&& !objectCal[9].toString().equalsIgnoreCase("")
						&& objectCal[12] != null
						&& !objectCal[12].toString().equalsIgnoreCase("")) {
					ListArray = objectCal[9].toString().split("#");
					ListArrayTemp = objectCal[12].toString().split("#");
					ListArrayTemp1 = objectCal[13].toString().split("#");
					int levlLght = Integer.parseInt(getId());
					for (int i = 0; i < levlLght; i++) {
						
						List<ConfigurationUtilBean> tempDataConfi = new ArrayList<ConfigurationUtilBean>();
						
						List listresultdata = new CommonOperAtion()
								.getSelectTabledata(ListArray[i], paramMaps,connectionSpace);
						for (Object c : listresultdata) {
							Object[] dataC = (Object[]) c;
							ConfigurationUtilBean ctb = new ConfigurationUtilBean();
							ctb.setId(Integer.parseInt(dataC[0].toString()));
							ctb.setField_name(dataC[1].toString());
							ctb.setField_value(dataC[2].toString());
							if (dataC[11] == null) {
								ctb.setMandatory(false);
							} else if (dataC[11].toString().equalsIgnoreCase("1")){
								ctb.setMandatory(true);
							}
							else if (dataC[11].toString().equalsIgnoreCase("0")){
								ctb.setMandatory(false);
							}
							tempDataConfi.add(ctb);
						}
						ListConfigbean ltb = new ListConfigbean();
						ltb.setConfigTable(ListArray[i]);
						ltb.setMappedConfigLevelView(ListArrayTemp1[i]);
						ltb.setMappedConfigDataTable(ListArrayTemp[i]);
						dataList.put(ltb, tempDataConfi);
					}

				}
			}

			returnString = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnString;
	}

	@SuppressWarnings("unchecked")
	public String editeconfiguration() {
		if(userName==null || userName.equalsIgnoreCase(""))
			return LOGIN;
		String returnString = ERROR;
		try {
			/** HashMap Object name to put Parameter*/
			Map<String, Object> paramMaps = new HashMap<String, Object>();
			/** HashMap Object name to set Parameter*/
			Map<String, Object> setMap = new HashMap<String, Object>();
			/** HashMap Object name to set WhereClouse Parameter*/
			Map<String, Object> WhereClouse = new HashMap<String, Object>();
			List<String> data=new ArrayList<String>();
			/** to Set table column to get value of these Parameter*/
			data.add("mappedid");
			/** mappedTabless name */
			data.add("table_name");
			/** mappedTabless name */
			boolean mappedTabless=false;
			/** StringBuilder Object */
			StringBuilder Objectdata= new StringBuilder();
			createTable cbt=new createTable();
			/** checked mapped table can not be null for any cases */
			if(getMapedtable()!=null){
				/**get mapped table name*/
			List getMappedTableForConfg=cbt.viewAllDataEitherSelectOrAll(getMapedtable(),data,setMap,connectionSpace);
			String commonmapedtabe=getMapedtable();
			if(getMappedTableForConfg!=null){
			for (Object c1 : getMappedTableForConfg) {
				Object[] dataC1 = (Object[]) c1;
				mapedtable=dataC1[1].toString().trim();
				mappedTabless=true;
			}
		}
			
		 	/**checked level  name value can not be null or blank*/
		 try{
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
			}}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			if(getField_value()!=null && !getField_value().equalsIgnoreCase("")&& !active && !inactive && !mandatory && !mandatorynot){
				List resultdata;
				InsertDataTable ob = new InsertDataTable();
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
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
				
			
				
				ob = new InsertDataTable();
				ob.setColName("validationType");
				ob.setDataName(getValidation_type());
				insertData.add(ob);
				
				 TableColumes obJ=new TableColumes();
				 obJ.setColumnname(getField_value().replace(' ','_').toLowerCase());
				 obJ.setDatatype("varchar(255)");
				 obJ.setConstraint("default NULL");
				 Tablecolumesaaa.add(obJ);
				 
				List<Object[]> queryResult =  new CommonOperAtion().insertIntoTableReturnId(mapedtable, insertData,connectionSpace);
				boolean flag;
				if(mappedTabless){
					try{
					 resultdata = new CommonOperAtion().getSelectTabledata(commonmapedtabe, paramMaps,connectionSpace);
					 
					 if(resultdata!=null){
						for (Iterator literator = resultdata.iterator(); literator.hasNext();) {
							Object[] objectCalg = (Object[]) literator.next();
							String[] ArrayobjectCal= objectCalg[1].toString().split("#");
						
							if(objectCalg[3].toString()!=null){
								//System.out.println("objectCalg[3]"+objectCalg[3].toString());
							create_table=objectCalg[3].toString();}
							for (int i = 0; i < ArrayobjectCal.length; i++) {
									 Objectdata.append(ArrayobjectCal[i]);
									 Objectdata.append("#");
								 }
							Objectdata.append(queryResult.get(0));
							Objectdata.append("#");
							WhereClouse.put("id", objectCalg[0].toString());
							setMap.put("mappedid", Objectdata.toString());
							}
					    }
					}catch (Exception e) {
						// TODO: handle exception
					}
					CommonforClassdata obhj = new CommonOperAtion();
					boolean status = obhj.Createtabledata(create_table, Tablecolumesaaa, connectionSpace);
			      flag=new CommonOperAtion().updateIntoTable(commonmapedtabe, setMap,WhereClouse,connectionSpace);
			      
				}else{
					Map<String, Object> paramMapss = new HashMap<String, Object>();
					paramMapss.put("table_name",mapedtable);
				  resultdata = new CommonOperAtion().getSelectTabledata(comonmaped, paramMapss,connectionSpace);
				  if(resultdata!=null){
					for (Iterator literator = resultdata.iterator(); literator.hasNext();) {
						Object[] objectCalg = (Object[]) literator.next();
						String[] ArrayobjectCal= objectCalg[1].toString().split("#");
						if(objectCalg[3].toString()!=null){
							//System.out.println("objectCalg[3]"+objectCalg[3].toString());
						create_table=objectCalg[3].toString();}
						for (int i = 0; i < ArrayobjectCal.length; i++) {
								 Objectdata.append(ArrayobjectCal[i]);
								 Objectdata.append("#");
							 }
						Objectdata.append(queryResult.get(0));
						Objectdata.append("#");
						WhereClouse.put("id", objectCalg[0].toString());
						setMap.put("mappedid", Objectdata.toString());
						}
				    }
					CommonforClassdata obhj = new CommonOperAtion();
					boolean status = obhj.Createtabledata(create_table, Tablecolumesaaa, connectionSpace);
					flag=new CommonOperAtion().updateIntoTable(comonmaped, setMap,WhereClouse,connectionSpace);
				  }
					if(flag){
						addActionMessage("Update Record Sucessfully");
					}else{
						addActionError("Opps There is some Problem!");
					}
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
			}catch (Exception e) {
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
			}catch (Exception e) {
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
			
			returnString = SUCCESS;
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnString;

	}

	public String addproperty() {
		
		if(userName==null || userName.equalsIgnoreCase(""))
			return LOGIN;
		String returnString = ERROR;
		try {

			returnString = SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnString;

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

	public String getMapedtable() {
		return mapedtable;
	}

	public void setMapedtable(String mapedtable) {
		this.mapedtable = mapedtable;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public Map<String, List<ConfigurationUtilBean>> getDataList1() {
		return dataList1;
	}

	public void setDataList1(Map<String, List<ConfigurationUtilBean>> dataList1) {
		this.dataList1 = dataList1;
	}

	public Map<ListConfigbean, List<ConfigurationUtilBean>> getDataList() {
		return dataList;
	}

	public void setDataList(
			Map<ListConfigbean, List<ConfigurationUtilBean>> dataList) {
		this.dataList = dataList;
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

	public String getComonmaped() {
		return comonmaped;
	}

	public void setComonmaped(String comonmaped) {
		this.comonmaped = comonmaped;
	}
	public boolean isMandatorynot() {
		return mandatorynot;
	}

	public void setMandatorynot(boolean mandatorynot) {
		this.mandatorynot = mandatorynot;
	}
	public boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getValidation_type() {
		return validation_type;
	}

	public void setValidation_type(String validation_type) {
		this.validation_type = validation_type;
	}

	public String getCreate_table() {
		return create_table;
	}

	public void setCreate_table(String create_table) {
		this.create_table = create_table;
	}
	
	
}
