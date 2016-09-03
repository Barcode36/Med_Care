package com.Over2Cloud.ctrl.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.BeanUtil.TableColumes;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ConfigurationAction extends ActionSupport {

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
	private String basictabe = new String();
	/** configBean name */
	private String adresstabe = new String();
	/** configBean name */
	private String customtabe = new String();
	/** configBean name */
	private String desictabe = new String();
	/** configBean name */
	private String content1 = new String();
	/** configBean name */
	private String content2 = new String();
	/** configBean name */
	private String content3 = new String();
	/** configBean name */
	private String content4 = new String();
	/** configBean name */
	private String mapedtable = null;
	private String maped = null;
	private String conf_level = null;
	private String conf_table = null;
	private String fieldname = null;
	private List<ConfigurationUtilBean> basicconfigBean = null;
	private List<ConfigurationUtilBean> addressconfigBean = null;
	private List<ConfigurationUtilBean> customconfigBean = null;
	private List<ConfigurationUtilBean> descriptiveBean = null;
	private Map<Integer,String>putLevel=new HashMap<Integer, String>();
	private boolean mandatoryFalg;
	
	/**
	 * @Author Pankaj Rajput
	 * @METHODB addConfigurationdata
	 * @return Resultdata Object
	 */
	
	public String execute()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(isMandatoryFalg())
			 setMandatoryFalg(false);
			else if(!isMandatoryFalg())
				 setMandatoryFalg(true);
		}
		catch(Exception e)
		{
			
		}
		return SUCCESS;
	}
	public String addConfigurationdata() {
		
		if(userName==null || userName.equalsIgnoreCase(""))
			return LOGIN;
		
		String returnResult = ERROR;
		try {
			String[] ArrayList = getTitles().toString().split(",");
			String[] ArrayListtable = getQueryNames().toString().split(",");

			StringBuilder StringObject = new StringBuilder();

			for (int i = 0; i < ArrayList.length; i++) {
				StringObject.append(ArrayList[i] + "#");
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
			boolean status = obhj.Createtabledata(mapedtable.trim(), Tablecolumesaaa,connectionSpace);

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();
			ob.setColName("mappedid");
			ob.setDataName(StringObject.toString());
			insertData.add(ob);
			ob = new InsertDataTable();
			ob.setColName("table_name");
			ob.setDataName(ArrayListtable[0].toString());
			insertData.add(ob);
			ob = new InsertDataTable();
			ob = new InsertDataTable();
			ob.setColName("clientid");
			ob.setDataName("Pankaj");
			insertData.add(ob);
			status = obhj.insertIntoTable(mapedtable.trim(), insertData,connectionSpace);
			if (status) {
				addActionMessage("Data added SuccessfUlly");
			} else {
				addActionMessage("Opps There is a problem plzz call Mr.Pankaj");
			}
			returnResult = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return returnResult;

	}

	/**
	 * @Author Pankaj Rajput
	 * @METHODB operationwithfieldAction
	 * @return Resultdata Object
	 */
	public String operationwithfieldAction() {
		
		if(userName==null || userName.equalsIgnoreCase(""))
			return LOGIN;
	
		String returnResult = ERROR;
		try {

			String aSD[] = id.split("-");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", aSD[0]);
			List<ConfigurationUtilBean> Listdata = new ArrayList<ConfigurationUtilBean>();
			if (aSD[1] != null && !aSD[1].toString().equalsIgnoreCase("")) {
				
				List Confresultdata = new CommonOperAtion().getSelectTabledata(
						aSD[1].toString() + "w", paramMap,connectionSpace);
				for (Iterator iterator = Confresultdata.iterator(); iterator
						.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					ConfigurationUtilBean ConfigBeanObj = new ConfigurationUtilBean();
					/** id name */
					if (object[0] == null) {
						ConfigBeanObj.setId(Integer.parseInt(object[0]
								.toString()));
					} else {
						ConfigBeanObj.setId(Integer.parseInt(object[0]
								.toString()));
					}
					/** Field_value name */
					if (object[1] == null) {
						ConfigBeanObj.setField_value("NA");
					} else {
						ConfigBeanObj.setField_value(object[1].toString());
					}
					/** Field_value name */
					if (object[3] == null) {
						ConfigBeanObj.setDefault_value("");
					} else {
						ConfigBeanObj.setDefault_value(object[3].toString());
					}
					

					Listdata.add(ConfigBeanObj);
				}
				/** Set List data Object */
				setBasicconfigBean(Listdata);

			}
			returnResult = SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnResult;
	}

	/**
	 * @Author Pankaj Rajput
	 * @METHODB ShowConfigurationdetails
	 * @return Resultdata Object
	 */
public String ShowConfigurationdetails() {
		
		if(userName==null || userName.equalsIgnoreCase(""))
			return LOGIN;
		
		String returnResult = ERROR;
		List resultdata = null;
		try {
			setContent1("content1" + getId());
			setContent2("content2" + getId());
			setContent3("content3" + getId());
			setContent4("content4" + getId());
			List<ConfigurationUtilBean> Listdata = new ArrayList<ConfigurationUtilBean>();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", getId());
			setId(getId());
			resultdata = new CommonOperAtion().getSelectTabledata(
					"allcommontabtable", paramMap,connectionSpace);
			for (Iterator iterator = resultdata.iterator(); iterator.hasNext();) {
				Object[] objectCal = (Object[]) iterator.next();
				Map<String, Object> paramMaps = new HashMap<String, Object>();

				if (objectCal[5] != null
						&& !objectCal[5].toString().equalsIgnoreCase("")) {
					setMapedtable(objectCal[5].toString());
				}
				/**
				 * Basic Details Object
				 * 
				 */
				if (objectCal[2]!= null
						&& !objectCal[2].toString().equalsIgnoreCase("")) {
					List Confresultdata = new CommonOperAtion()
							.getSelectTabledata(objectCal[2].toString(),
									paramMaps,connectionSpace);
					if(Confresultdata!=null){
					for (Iterator iterators = Confresultdata.iterator(); iterators
							.hasNext();) {
						ConfigurationUtilBean ConfigBeanObj = new ConfigurationUtilBean();
						Object[] objectCall = (Object[]) iterators.next();
						/** id name */
						if (objectCall[0] == null) {
							ConfigBeanObj.setId(Integer.parseInt(objectCall[0]
									.toString()));
						} else {
							ConfigBeanObj.setId(Integer.parseInt(objectCall[0]
									.toString()));
						}
						/** Field_value name */
						if (objectCall[1] == null) {
							ConfigBeanObj.setField_value("NA");
						} else {
							ConfigBeanObj.setField_value(objectCall[1]
									.toString());
						}
						
						if(objectCall.length > 10){
						/**Field mandatory or not*/
						
						if (objectCall[11] == null) {
							ConfigBeanObj.setMandatory(false);
						} else if (objectCall[11].toString().equalsIgnoreCase("1")){
							ConfigBeanObj.setMandatory(true);
						}
						else if (objectCall[11].toString().equalsIgnoreCase("0")){
							ConfigBeanObj.setMandatory(false);
						}
						}
						/** add List data Object */
						ConfigBeanObj.setQueryNames(objectCal[2].toString());
						Listdata.add(ConfigBeanObj);
					}
				}
					/** Set List data Object */
					setBasicconfigBean(Listdata);

				}
				/**
				 * Addressing Details Object
				 */
				if (objectCal[3] != null
						&& !objectCal[3].toString().equalsIgnoreCase("")) {

					List<ConfigurationUtilBean> addressListdata = new ArrayList<ConfigurationUtilBean>();
					// Lead for address Information
					Map<String, Object> paramMapadd = new HashMap<String, Object>();
					/**
					 * List Details Object
					 */
					List addressingdata = new CommonOperAtion()
							.getSelectTabledata(objectCal[3].toString(),
									paramMapadd,connectionSpace);
                    if(addressingdata!=null){
					for (Iterator iterator2 = addressingdata.iterator(); iterator2
							.hasNext();) {
						ConfigurationUtilBean ConfigBeanObj = new ConfigurationUtilBean();
						Object[] objectCall = (Object[]) iterator2.next();
						/** id name */
						if (objectCall[0] == null) {
							ConfigBeanObj.setId(Integer.parseInt(objectCall[0]
									.toString()));
						} else {
							ConfigBeanObj.setId(Integer.parseInt(objectCall[0]
									.toString()));
						}
						/** Field_value name */
						if (objectCall[1] == null) {
							ConfigBeanObj.setField_value("NA");
						} else {
							ConfigBeanObj.setField_value(objectCall[1]
									.toString());
						}
						
                       /**Field mandatory or not*/
						
						if (objectCall[11] == null) {
							ConfigBeanObj.setMandatory(false);
						} else if (objectCall[11].toString().equalsIgnoreCase("1")){
							ConfigBeanObj.setMandatory(true);
						}
						else if (objectCall[11].toString().equalsIgnoreCase("0")){
							ConfigBeanObj.setMandatory(false);
						}
						/** add List data Object */
						ConfigBeanObj.setQueryNames(objectCal[3].toString());
						addressListdata.add(ConfigBeanObj);
					}
                   }
					/** Set List data Object */
					setAddressconfigBean(addressListdata);

				}
				/**
				 * Custom Details Object
				 */
				if (objectCal[4] != null
						&& !objectCal[4].toString().equalsIgnoreCase("")) {

					List<ConfigurationUtilBean> customListdata = new ArrayList<ConfigurationUtilBean>();
					/**
					 * List Details Object
					 */
					Map<String, Object> paramMapcust = new HashMap<String, Object>();
					List customLdata = new CommonOperAtion().getSelectTabledata(
							objectCal[4].toString(), paramMapcust,connectionSpace);
                     if(customLdata!=null){
					for (Iterator iterator1 = customLdata.iterator(); iterator1
							.hasNext();) {
						ConfigurationUtilBean ConfigBeanObj = new ConfigurationUtilBean();
						Object[] objectCall = (Object[]) iterator1.next();
						/** id name */
						if (objectCall[0] == null) {
							ConfigBeanObj.setId(Integer.parseInt(objectCall[0]
									.toString()));
						} else {
							ConfigBeanObj.setId(Integer.parseInt(objectCall[0]
									.toString()));
						}
						/** Field_value name */
						if (objectCall[1] == null) {
							ConfigBeanObj.setField_value("NA");
						} else {
							ConfigBeanObj.setField_value(objectCall[1]
									.toString());
						}

						/**Field mandatory or not*/
						
						if (objectCall[11] == null) {
							ConfigBeanObj.setMandatory(false);
						} else if (objectCall[11].toString().equalsIgnoreCase("1")){
							ConfigBeanObj.setMandatory(true);
						}
						else if (objectCall[11].toString().equalsIgnoreCase("0")){
							ConfigBeanObj.setMandatory(false);
						}
						/** add List data Object */
						ConfigBeanObj.setQueryNames(objectCal[4].toString());
						customListdata.add(ConfigBeanObj);
					}
                  }
					/** Set List data Object */
					setCustomconfigBean(customListdata);
				}
				if (objectCal[6] == null ) {
					setConf_level("false");
				} else {
					setConf_level("true");
				}
				if (objectCal[7] != null) {
					setConf_table(objectCal[7].toString());
				}
				
				//set hierarchy data
				if (objectCal[8] != null
						&& !objectCal[8].toString().equalsIgnoreCase("")) {
					int idTemp=Integer.parseInt(objectCal[8].toString());
					int m=2;
					for(int k=1;k<=idTemp;k++)
					{
						putLevel.put(k,"Level "+m);
						m++;
					}
				}
				
				/**
				 * Descriptive data on 10 the index of object
				 */
				if (objectCal[10] != null
						&& !objectCal[10].toString().equalsIgnoreCase("")) {

					List<ConfigurationUtilBean> customListdata = new ArrayList<ConfigurationUtilBean>();
					/**
					 * List Details Object
					 * 
					 * @return List Object data
					 */
					Map<String, Object> paramMapcust = new HashMap<String, Object>();
					List customLdata = new CommonOperAtion().getSelectTabledata(
							objectCal[10].toString(), paramMapcust,connectionSpace);
                     if(customLdata!=null){
					for (Iterator iterator1 = customLdata.iterator(); iterator1
							.hasNext();) {
						ConfigurationUtilBean ConfigBeanObj = new ConfigurationUtilBean();
						Object[] objectCall = (Object[]) iterator1.next();
						/** id name */
						if (objectCall[0] == null) {
							ConfigBeanObj.setId(Integer.parseInt(objectCall[0]
									.toString()));
						} else {
							ConfigBeanObj.setId(Integer.parseInt(objectCall[0]
									.toString()));
						}
						/** Field_value name */
						if (objectCall[1] == null) {
							ConfigBeanObj.setField_value("NA");
						} else {
							ConfigBeanObj.setField_value(objectCall[1]
									.toString());
						}
						
						/**Field mandatory or not*/
						
						if (objectCall[11] == null) {
							ConfigBeanObj.setMandatory(false);
						} else if (objectCall[11].toString().equalsIgnoreCase("1")){
							ConfigBeanObj.setMandatory(true);
						}
						else if (objectCall[11].toString().equalsIgnoreCase("0")){
							ConfigBeanObj.setMandatory(false);
						}

						/** add List data Object */
						ConfigBeanObj.setQueryNames(objectCal[10].toString());
						customListdata.add(ConfigBeanObj);
					}
                  }
					/** Set List data Object */
                     setDescriptiveBean(customListdata);
				}			
				
				/**
				 * Set dynamic header of accordian
				 * 
				 * 
				 */
					if (objectCal[11] != null) {
						String temp[]=objectCal[11].toString().split("#");
						if(temp.length>0)
						{
							if(fieldname!=null)
								setBasictabe(fieldname + " " + temp[0]);
							else
								setBasictabe(temp[0]);
						}
						if(temp.length>1)
						{
							if(fieldname!=null)
								setAdresstabe(fieldname + " " + temp[1]);
							else
								setAdresstabe(temp[1]);
						}
						if(temp.length>2)
						{
							if(fieldname!=null)
								setCustomtabe(fieldname + " " + temp[2]);
							else
								setCustomtabe(temp[2]);
						}
						if(temp.length>3)
						{
							if(fieldname!=null)
								setDesictabe(fieldname + " " + temp[3]);
							else
								setDesictabe(temp[3]);
						}
					}
					
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		returnResult = SUCCESS;

		return returnResult;
	}

	public String getQueryNames() {
		return queryNames;
	}

	public void setQueryNames(String queryNames) {
		this.queryNames = queryNames;
	}

	public List<ConfigurationUtilBean> getBasicconfigBean() {
		return basicconfigBean;
	}

	public void setBasicconfigBean(List<ConfigurationUtilBean> basicconfigBean) {
		this.basicconfigBean = basicconfigBean;
	}

	public List<ConfigurationUtilBean> getAddressconfigBean() {
		return addressconfigBean;
	}

	public void setAddressconfigBean(
			List<ConfigurationUtilBean> addressconfigBean) {
		this.addressconfigBean = addressconfigBean;
	}

	public List<ConfigurationUtilBean> getCustomconfigBean() {
		return customconfigBean;
	}

	public void setCustomconfigBean(List<ConfigurationUtilBean> customconfigBean) {
		this.customconfigBean = customconfigBean;
	}

	public String getTitles() {
		return titles;
	}

	public void setTitles(String titles) {
		this.titles = titles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBasictabe() {
		return basictabe;
	}

	public void setBasictabe(String basictabe) {
		this.basictabe = basictabe;
	}

	public String getAdresstabe() {
		return adresstabe;
	}

	public void setAdresstabe(String adresstabe) {
		this.adresstabe = adresstabe;
	}

	public String getCustomtabe() {
		return customtabe;
	}

	public void setCustomtabe(String customtabe) {
		this.customtabe = customtabe;
	}

	public String getDesictabe() {
		return desictabe;
	}

	public void setDesictabe(String desictabe) {
		this.desictabe = desictabe;
	}

	public String getContent1() {
		return content1;
	}

	public void setContent1(String content1) {
		this.content1 = content1;
	}

	public String getContent2() {
		return content2;
	}

	public void setContent2(String content2) {
		this.content2 = content2;
	}

	public String getContent3() {
		return content3;
	}

	public void setContent3(String content3) {
		this.content3 = content3;
	}

	public String getContent4() {
		return content4;
	}

	public void setContent4(String content4) {
		this.content4 = content4;
	}

	public String getMapedtable() {
		return mapedtable;
	}

	public void setMapedtable(String mapedtable) {
		this.mapedtable = mapedtable;
	}

	public String getMaped() {
		return maped;
	}

	public void setMaped(String maped) {
		this.maped = maped;
	}

	public String getConf_level() {
		return conf_level;
	}

	public void setConf_level(String conf_level) {
		this.conf_level = conf_level;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getConf_table() {
		return conf_table;
	}

	public void setConf_table(String conf_table) {
		this.conf_table = conf_table;
	}

	public Map<Integer, String> getPutLevel() {
		return putLevel;
	}

	public void setPutLevel(Map<Integer, String> putLevel) {
		this.putLevel = putLevel;
	}

	public List<ConfigurationUtilBean> getDescriptiveBean() {
		return descriptiveBean;
	}

	public void setDescriptiveBean(List<ConfigurationUtilBean> descriptiveBean) {
		this.descriptiveBean = descriptiveBean;
	}
	public boolean isMandatoryFalg() {
		return mandatoryFalg;
	}
	public void setMandatoryFalg(boolean mandatoryFalg) {
		this.mandatoryFalg = mandatoryFalg;
	}

}
