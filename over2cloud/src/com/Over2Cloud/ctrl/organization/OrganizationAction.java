package com.Over2Cloud.ctrl.organization;

import hibernate.common.HibernateSessionFactory;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.krLibrary.KRActionHelper;
import org.apache.commons.io.FileUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OrganizationAction extends ActionSupport implements ServletRequestAware
{

	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(OrganizationAction.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	
	private int levelOfComp=2;//default level is 2
	private Map<Integer, String>  orgTypeList=new LinkedHashMap<Integer, String>();
	private Map<Integer, String> industryList=new LinkedHashMap<Integer, String>();
	private Map<String, String> countryList=new LinkedHashMap<String, String>();
	//For Level 1 parameteres
	private File companyIcon;
	private String companyIconContentType;
	private String companyIconFileName;
	private String companyName;
	private String companyType;
	private String industryParam;
	private String companyRegAddress;
	private String companyRegCity;
	private String companyRegPinCode;
	private String companyRegContact1;
	private String companyRegCountry;
	private String companyEmail;
	private String orgImgPath;
	//for Level 2 parameters
	private String locCompany;
	private String locCompanyName;
	private String locCompanyRegAddress;
	private String locCompanyRegCountry;
	private String locCompanyRegCity;
	private String locCompanyRegPinCode;
	private String locCompanyRegContact1;
	private String locCompanyRegContact2;
	private String locCompanyRegWeb;
	private String locCompanyRegEmail;
	//for Level 3 parameters
	private String locationCompany;
	private String branchAddress;
	private String branchLandmark;
	private String branchCity;
	private String branchPinCode;
	private String branchFax;
	private String branchContact1;
	private String branchContact2;
	private String branchWeb;
	private String branchEmail;
	//for Level 4 parameters
	private String level3Registration;
	private String level4Address;
	private String level4Landmark;
	private String level4City;
	private String level4PinCode;
	private String level4Fax;
	private String level4Contact1;
	private String level4Contact2;
	private String level4Web;
	private String level4Email;
	//for Level 4 parameters
	private String level4Registration;
	private String level5Address;
	private String level5Landmark;
	private String level5City;
	private String level5PinCode;
	private String level5Fax;
	private String level5Contact1;
	private String level5Contact2;
	private String level5Web;
	private String level5Email;
	//upload logo view
	private String uploadId;
	private InputStream inputStream;
	private String contentType;
	//Grid view
	private String mainHeaderName;
	private String headerName;
	private String headerName1;
	private String headerName2;
	private String headerName3;
	private String headerName4;
	private String modifyPrvlgs;
	ConfigurationUtilBean Conobj=null;
	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// sorting order - asc or desc
	private String sord = "";
	// get index row - i.e. user click to sort.
	private String sidx = "";
	// Search Field
	private String searchField = "";
	// The Search String
	private String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper = "";
	// Your Total Pages
	private Integer total = 0;
	// All Record
	private Integer records = 0;
	private boolean loadonce = false;
	//Grid colomn view
	private String oper;
	private int id;
	private List<GridDataPropertyView>level1ColmnNames=new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView>level2ColmnNames=new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView>level3ColmnNames=new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView>level4ColmnNames=new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView>level5ColmnNames=new ArrayList<GridDataPropertyView>();
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
	//for level1
	private Map<String, String>configuration1=new LinkedHashMap<String,String>();
	private Map<String, String>configuration3=new LinkedHashMap<String,String>();
	private boolean industryParam1=false;
	private boolean companyRegCountry1=false;
	private boolean companyType1=false;
	private String industryParamName=null;
	private String industryParamkey=null;
	private String companyRegCountryname=null;
	private String companyRegCountrykey=null;
	private String companyTypeName=null;
	private String companyTypekey=null;
	//for level 2
	private List<ConfigurationUtilBean>configurationForLevel2= null;
	private boolean organizationLocLevel2=false;
	private boolean organizationRegComLevel2=false;
	private String organizationLocLevel2Name=null;
	private String organizationLocLevel2key=null;
	private String organizationRegComLevel2Name=null;
	private String organizationRegComLevel2key=null;
	//for level 3
	private List<ConfigurationUtilBean>configurationForLevel3=null;
	private String organizationLocLevel3Name=null;
	private String organizationLocLevel3key=null;
	//for level 4
	private List<ConfigurationUtilBean>configurationForLevel4=null;
	private String organizationLocLevel4Name=null;
	private String organizationLocLevel4key=null;
	//for level 5
	private List<ConfigurationUtilBean>configurationForLevel5=null;
	private String organizationLocLevel5Name=null;
	private String organizationLocLevel5key=null;
	private int globalLevel=0;
	private String mappedtablelevel1;
	private String mappedtablelevel2;
	private String mappedtablelevel3;
	private String mappedtablelevel4;
	private String mappedtablelevel5;
	private List<ConfigurationUtilBean> listconfiguration=null;
	private List<ConfigurationUtilBean> listconfiguration1=null;
	private List<ConfigurationUtilBean> listconfiguration2=null;
	private List<ConfigurationUtilBean> listconfiguration3=null;
	private List<ConfigurationUtilBean> listconfiguration4=null;
	private List<ConfigurationUtilBean> listconfiguration5=null;
	//getting the request parameters and there value
	private HttpServletRequest request;
	
	
	private File orgLogo;
	private String orgLogoName;
	private String orgLogoContentType;
	
	
	/*
	 * 
	 * private String krUploadContentType;
	private String krUploadFileName;
	
	 */
	
	private String orgName,orgAddress,orgCity,orgCountry,orgPin,orgContactNo;
	
	//for grid view of all
	private List<Object> level1DataObject;
	/**
	 * Method will used for creating the level of organzation and insert the data in the table
	 * @return
	 */
	public String levelConfiguration()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	 List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	 TableColumes ob1=new TableColumes();
	 // Columes field 2
	 ob1=new TableColumes();
	 ob1.setColumnname("orgLevel");
	 ob1.setDatatype("varchar(130)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	 // Columes field 3
	 ob1=new TableColumes();
	 ob1.setColumnname("propertyName");
	 ob1.setDatatype("text");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	 cbt.createTable22("orgleInfo",Tablecolumesaaa,connectionSpace);
	 List <InsertDataTable> insertDataTable=new ArrayList<InsertDataTable>();
	 InsertDataTable objctData=new InsertDataTable();
	 objctData=new InsertDataTable();
	 objctData.setColName("orgLevel");
	 objctData.setDataName("5");
	 insertDataTable.add(objctData);
	 objctData=new InsertDataTable();
	 objctData.setColName("propertyName");
	 objctData.setDataName("Level5");
	 insertDataTable.add(objctData);
	 cbt.insertIntoTable("orgleInfo",insertDataTable,connectionSpace);
	 
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method levelConfiguration of class "+getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
	

	/**
	 * Getting the list of all mapped indsutry of the organization and the list of the all countries
	 * And getting the level of organization from the configuration part, it will varies from 1 to 5
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String validateLevelOfOrganization()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	
	
	List<String>colname=new ArrayList<String>();
	List<String>country=new ArrayList<String>();
	List<String>industry=new ArrayList<String>();
	List<String>orgType=new ArrayList<String>();
	Map<String, Object> temp=new HashMap<String, Object>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	
	//Countries Data list
	Map<String, Object> order=new HashMap<String, Object>();
	order.put("Country", "ASC");
	country.add("Country");
	List countryData=cbt.viewAllDataEitherSelectOrAllWithLocalConnection("_countries",country,temp,order,"clouddb");
	if(countryData!=null && countryData.size()>0)
	{
	for (Object c : countryData) {
	Object dataC = (Object) c;
	countryList.put((String)dataC, (String)dataC);
	}
	}
	
	//Industry Name list
	industry.add("id");
	industry.add("industry_name");
	List indsList=cbt.viewAllDataEitherSelectOrAllWithOPutUnderWithLocal("industry",colname,"clouddb");
	if(indsList!=null && indsList.size()>0)
	{
	for (Object c : indsList) {
	Object[] dataC = (Object[]) c;
	industryList.put(Integer.parseInt(dataC[0].toString()), (String)dataC[1]);
	}
	}
	
	//Organization type list
	orgType.add("id");
	orgType.add("type");
	SessionFactory sessionNews=HibernateSessionFactory.getSessionFactory();
	List orgData=cbt.viewAllDataEitherSelectOrAllWithLocal("_organizationtype",orgType,temp);
	if(orgData!=null && orgData.size()>0)
	{
	for (Object c : orgData) {
	Object[] dataC = (Object[]) c;
	orgTypeList.put(Integer.parseInt(dataC[0].toString()), (String)dataC[1]);
	}
	}
	
	//getting the current logined user organization level
	colname.add("orgLevel");
	colname.add("levelName");
	int level =0;
	List orgntnlevel=cbt.viewAllDataEitherSelectOrAll("mapped_orgleinfo_level",colname,temp,connectionSpace);
	if(orgntnlevel!=null && orgntnlevel.size()>0)
	{
	for (Object c : orgntnlevel)
	{
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
	Map<String, String>configuration2=new LinkedHashMap<String,String>();
	List<String> data=new ArrayList<String>();
	data.add("mappedid");
	data.add("table_name");
	if(level>0)
	{
	Map<String, String>configurationtemp=new LinkedHashMap<String,String>();
	
	configurationtemp=Configuration.getConfigurationIds("mapped_organizconf1", data, accountID, temp, connectionSpace, "T");
	for (Map.Entry<String, String> entry : configurationtemp.entrySet())
	{
	if(!entry.getKey().trim().equalsIgnoreCase("userName") && !entry.getKey().trim().equalsIgnoreCase("date") && !entry.getKey().trim().equalsIgnoreCase("time"))
	    configuration1.put(entry.getKey().trim(), entry.getValue());
	}
	configuration2=Configuration.getConfigurationIds("mapped_organizconf1", data, accountID, temp, connectionSpace, "D");
	configuration3=Configuration.getConfigurationIds("mapped_organizconf1", data, accountID, temp, connectionSpace, "F");
	for (Map.Entry<String, String> entry : configuration2.entrySet())
	{
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("industryParam"))
	{
	industryParam1=true;
	industryParamName=entry.getValue();
	}
	if(tmpString.equalsIgnoreCase("companyRegCountry"))
	{
	companyRegCountry1=true;
	companyRegCountryname=entry.getValue();
	}
	if(tmpString.equalsIgnoreCase("companyType"))
	{
	companyType1=true;
	companyTypeName=entry.getValue();
	}
	}
	}
	//mapped_organizconf_level2#mapped_organizconf_level3#mapped_organizconf_level4#mapped_organizconf_level5#
	if(level>1)
	{
	// vvv
	//for level 2
	List<ConfigurationUtilBean>confiObjeList=new ArrayList<ConfigurationUtilBean>();
	List configurationtemp2=Configuration.getConfigurationIdss("mapped_organizconf_level2", data, accountID, temp, connectionSpace);
	for (Object c1 : configurationtemp2){
	Object[] dataC1 = (Object[]) c1;
	 Conobj=new ConfigurationUtilBean();
	if(dataC1[1].toString().equalsIgnoreCase("locCompany"))
	{
	organizationLocLevel2=true;
	organizationLocLevel2Name=dataC1[0].toString().trim();
	}
	else if(dataC1[1].toString().equalsIgnoreCase("locCompanyRegCountry"))
	{
	organizationRegComLevel2=true;
	organizationRegComLevel2Name=dataC1[0].toString().trim();
	}
	else if(dataC1[1].toString().equalsIgnoreCase("userName")){}
	else if(dataC1[1].toString().equalsIgnoreCase("date")){}
	else if(dataC1[1].toString().equalsIgnoreCase("time")){}
	else{
	if(dataC1[3].toString().equalsIgnoreCase("T")){
	Conobj.setKey(dataC1[1].toString().trim());
	Conobj.setValue(dataC1[0].toString().trim());
	Conobj.setColType(dataC1[3].toString().trim());
	if(dataC1[6]!=null){
	Conobj.setValidationType(dataC1[6].toString());
	}else{
	Conobj.setValidationType("NA");
	}
	if(dataC1[5]==null)
	Conobj.setMandatory(false);
	else if(dataC1[5].toString().equalsIgnoreCase("0"))
	Conobj.setMandatory(false);
	else if(dataC1[5].toString().equalsIgnoreCase("1"))
	Conobj.setMandatory(true);
	confiObjeList.add(Conobj);
	}
	else if(dataC1[3].toString().equalsIgnoreCase("D")){
	    
	}
	            }
	}
	    setConfigurationForLevel2(confiObjeList);

	    // vvv
	}
	if(level>2)
	{
	//for level3
	List<ConfigurationUtilBean>ObjList3=new ArrayList<ConfigurationUtilBean>();
	List configurationtemp3=Configuration.getConfigurationIdss("mapped_organizconf_level3", data, accountID, temp, connectionSpace);
	for (Object c1 : configurationtemp3){
	Object[] dataC1 = (Object[]) c1;
	 Conobj=new ConfigurationUtilBean();
	if(dataC1[1].toString().equalsIgnoreCase("locationCompany")){
	organizationLocLevel3Name=dataC1[0].toString().trim();
	}else{
	if(dataC1[3].toString().equalsIgnoreCase("T")){
	Conobj.setKey(dataC1[1].toString().trim());
	Conobj.setValue(dataC1[0].toString().trim());
	Conobj.setColType(dataC1[3].toString().trim());
	if(dataC1[6]!=null){
	Conobj.setValidationType(dataC1[6].toString());
	}else{
	Conobj.setValidationType("NA");
	}
	if(dataC1[5]==null)
	Conobj.setMandatory(false);
	else if(dataC1[5].toString().equalsIgnoreCase("0"))
	Conobj.setMandatory(false);
	else if(dataC1[5].toString().equalsIgnoreCase("1"))
	Conobj.setMandatory(true);
	ObjList3.add(Conobj);
	}
	      }
	setConfigurationForLevel3(ObjList3);
	}
	

	}
	if(level>3)
	{
	//for level3
	List<ConfigurationUtilBean>ObjList4=new ArrayList<ConfigurationUtilBean>();
	List configurationtemp4=Configuration.getConfigurationIdss("mapped_organizconf_level4", data, accountID, temp, connectionSpace);
	for (Object c1 : configurationtemp4){
	Object[] dataC1 = (Object[]) c1;
	 Conobj=new ConfigurationUtilBean();
	if(dataC1[1].toString().equalsIgnoreCase("level3Registration")){
	    organizationLocLevel4Name=dataC1[0].toString().trim();
	}else{
	if(dataC1[3].toString().equalsIgnoreCase("T")){
	Conobj.setKey(dataC1[1].toString().trim());
	Conobj.setValue(dataC1[0].toString().trim());
	Conobj.setColType(dataC1[3].toString().trim());
	if(dataC1[6]!=null){
	Conobj.setValidationType(dataC1[6].toString());
	}else{
	Conobj.setValidationType("NA");
	}
	if(dataC1[5]==null)
	Conobj.setMandatory(false);
	else if(dataC1[5].toString().equalsIgnoreCase("0"))
	Conobj.setMandatory(false);
	else if(dataC1[5].toString().equalsIgnoreCase("1"))
	Conobj.setMandatory(true);
	ObjList4.add(Conobj);
	}
	      }
	setConfigurationForLevel4(ObjList4);
	}
	}
	if(level>4)
	{
	//for level5
	List<ConfigurationUtilBean>ObjList5=new ArrayList<ConfigurationUtilBean>();
	List configurationtemp5=Configuration.getConfigurationIdss("mapped_organizconf_level5", data, accountID, temp, connectionSpace);
	for (Object c1 : configurationtemp5){
	Object[] dataC1 = (Object[]) c1;
	 Conobj=new ConfigurationUtilBean();
	if(dataC1[1].toString().equalsIgnoreCase("level4Registration")){
	    organizationLocLevel5Name=dataC1[0].toString().trim();
	}else{
	if(dataC1[3].toString().equalsIgnoreCase("T")){
	Conobj.setKey(dataC1[1].toString().trim());
	Conobj.setValue(dataC1[0].toString().trim());
	Conobj.setColType(dataC1[3].toString().trim());
	if(dataC1[6]!=null){
	Conobj.setValidationType(dataC1[6].toString());
	}else{
	Conobj.setValidationType("NA");
	}
	if(dataC1[5]==null)
	Conobj.setMandatory(false);
	else if(dataC1[5].toString().equalsIgnoreCase("0"))
	Conobj.setMandatory(false);
	else if(dataC1[5].toString().equalsIgnoreCase("1"))
	Conobj.setMandatory(true);
	ObjList5.add(Conobj);
	}
	      }
	setConfigurationForLevel5(ObjList5);
	}
	}
	globalLevel=level;
	return "success";
	}
	/*
	 * level1Name;
	 */
	return  "1";
	}
	else
	{
	return "1";
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method validateLevelOfOrganization of class "+getClass(), e);
	e.printStackTrace();
	return  "1";
	}
	}
	
	/**
	 * level one organization details entry,
	 * Only the one entry of organization will enter for head value that is main organization which is one time entry,
	 * organzization logo also uploaded which is also one time
	 * @return
	 */
	public String createOrganizationLevel1()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	//checking first if company entry exist no multiple time creation for single level
	List org=new ArrayList<String>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List countryData=null;
	try
	{
	countryData=cbt.viewAllDataEitherSelectOrAll("organization_level1",org,connectionSpace);
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	if(countryData==null)
	{
	//Organization is not exist so create the table and insert the data
	 List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	 TableColumes ob1=new TableColumes();
	 // Columes field 2
	 ob1.setColumnname("companyIcon");
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	 // Columes field 3
	 ob1=new TableColumes();
	 ob1.setColumnname("companyName");
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	// Columes field 4
	 ob1=new TableColumes();
	 ob1.setColumnname("companyType");
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	// Columes field 5
	 ob1=new TableColumes();
	 ob1.setColumnname("industryParam");
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	// Columes field 6
	 ob1=new TableColumes();
	 ob1.setColumnname("companyRegAddress");
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	// Columes field 7
	 ob1=new TableColumes();
	 ob1.setColumnname("companyRegCity");
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	// Columes field 8
	 ob1=new TableColumes();
	 ob1.setColumnname("companyRegCountry");
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	// Columes field 9
	 ob1=new TableColumes();
	 ob1.setColumnname("companyRegPinCode");
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	// Columes field 10
	 ob1=new TableColumes();
	 ob1.setColumnname("companyRegContact1");
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	 boolean status=cbt.createTable22("organization_level1",Tablecolumesaaa,connectionSpace);
	 if(status)
	 {
	 //inserting data in the organization data
	 com.Over2Cloud.CommonInterface.CommonforClass eventDao = new com.Over2Cloud.CommonClasses.CommanOper();
	   byte[] logoBuf=null;
	   Avatar avatar = new Avatar();
	if(companyIcon!=null)
	{
	logoBuf=new byte[(int)companyIcon.length()]; 
	if(logoBuf!=null && logoBuf.length>0)
	{
	FileInputStream fileInStream=new FileInputStream(companyIcon);	
	fileInStream.read(logoBuf);
	fileInStream.close();
	}
	avatar.setImage(logoBuf);
	avatar.setContentType(companyIconContentType);
	eventDao.addDetails(avatar);
	}
	 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	 InsertDataTable ob=new InsertDataTable();
	 if(avatar.getAvatarId()!=0)
	 {
	 ob.setColName("companyIcon");
	 ob.setDataName(avatar.getAvatarId());//saving id in for reference
	 insertData.add(ob);
	 }
	 
	 if(getCompanyName()!=null)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("companyName");
	 ob.setDataName(getCompanyName());
	 insertData.add(ob);
	 }
	 if(getCompanyType()!=null)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("companyType");
	 ob.setDataName(getCompanyType());
	 insertData.add(ob);
	 }
	 if(getIndustryParam()!=null)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("industryParam");
	 ob.setDataName(getIndustryParam());
	 insertData.add(ob);
	 }
	 if(getCompanyRegAddress()!=null)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("companyRegAddress");
	 ob.setDataName(getCompanyRegAddress());
	 insertData.add(ob);
	 }
	 if(getCompanyRegCity()!=null)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("companyRegCity");
	 ob.setDataName(getCompanyRegCity());
	 insertData.add(ob);
	 }
	 if(getCompanyRegPinCode()!=null)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("companyRegPinCode");
	 ob.setDataName(getCompanyRegPinCode());
	 insertData.add(ob);
	 }
	 if(getCompanyRegContact1()!=null)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("companyRegContact1");
	 ob.setDataName(getCompanyRegContact1());
	 insertData.add(ob);
	 }
	 if(getCompanyRegCountry()!=null)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("companyRegCountry");
	 ob.setDataName(getCompanyRegCountry());
	 insertData.add(ob);
	 }
	 status=cbt.insertIntoTable("organization_level1",insertData,connectionSpace);
	 if(status)
	 {
	 addActionMessage("Organization Registered Successfully!!!");
	 }
	 else
	 {
	 addActionMessage("Ooops There Is Some Problem In Organization Creation!");
	 }
	 }
	 else
	 {
	 addActionMessage("Ooops There Is Some Problem In Organization Creation!");
	 }
	
	}
	else
	{
	
	addActionMessage("Organization Is already Registered!!!");
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method createOrganizationLevel1 of class "+getClass(), e);
	 addActionMessage("Ooops There Is Some Problem In Organization Creation!");
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	/**
	 * Getting the details of level1 organization, i.e the name of the organization 
	 * @return
	 */
	public String organizationDetails()
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List oegName=null;
	List org=new ArrayList<String>();
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	org.add("companyName");
	org.add("companyRegCountry");
	oegName=cbt.viewAllDataEitherSelectOrAll("organization_level1",org,connectionSpace);
	if(oegName!=null && oegName.size()>0)
	{
	for (Object c : oegName) {
	Object[] dataC = (Object[]) c;
	setCompanyName(dataC[0].toString());
	setCompanyRegCountry(dataC[1].toString());
	}
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method organizationDetails of class "+getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	/**
	 * Creating the LEVEL2 organization ,
	 * Only one entry with a name exist in the DB, no name same entry is allowed for creation
	 * @return
	 */
	public String createOrganizationLevel2()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	
	//checking first if company entry exist no multiple time creation for single level
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List level2Exist=null;
	Map<String, String> temp=new HashMap<String, String>();
	boolean statusExist=true;
	try
	{
	temp.put("locCompany",getLocCompany());
	level2Exist=cbt.viewAllDataEitherSelectOrAll("organization_level2",temp,connectionSpace);
	if(level2Exist==null)
	statusExist=true;
	else if(level2Exist.size()>0)
	statusExist=false;
	}
	catch(Exception e)
	{
	
	}
	if(statusExist)
	{
	//if status is true it means u can create organization
	List<GridDataPropertyView>org2=Configuration.getConfigurationData("mapped_organizconf_level2",accountID,connectionSpace,"",0,"table_name","organizconf_level2");
	    boolean userTrue=false;
	boolean dateTrue=false;
	boolean timeTrue=false;	
	if(org2!=null)
	{
	//create table query based on configuration
	List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	for(GridDataPropertyView gdp:org2)
	{
	 TableColumes ob1=new TableColumes();
	 ob1=new TableColumes();
	 ob1.setColumnname(gdp.getColomnName());
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	 if(gdp.getColomnName().equalsIgnoreCase("userName"))
	 userTrue=true;
	 else if(gdp.getColomnName().equalsIgnoreCase("date"))
	 dateTrue=true;
	 else if(gdp.getColomnName().equalsIgnoreCase("time"))
	 timeTrue=true;
	}
	cbt.createTable22("organization_level2",Tablecolumesaaa,connectionSpace);
	}
	//getting the parameters nd setting their value using loop
	List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	Collections.sort(requestParameterNames);
	Iterator it = requestParameterNames.iterator();
	 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	 InsertDataTable ob=null;
	while (it.hasNext()) {
	String Parmname = it.next().toString();
	String paramValue=request.getParameter(Parmname);
	if(paramValue!=null && !paramValue.equalsIgnoreCase(""))
	{
	 ob=new InsertDataTable();
	 ob.setColName(Parmname);
	 ob.setDataName(paramValue);
	 insertData.add(ob);
	}
	}
	
	if(userTrue)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("userName");
	 ob.setDataName(userName);
	 insertData.add(ob);
	 }
	 if(dateTrue)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("date");
	 ob.setDataName(DateUtil.getCurrentDateUSFormat());
	 insertData.add(ob);
	 }
	 if(timeTrue)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("time");
	 ob.setDataName(DateUtil.getCurrentTime());
	 insertData.add(ob);
	 }
	boolean status=cbt.insertIntoTable("organization_level2",insertData,connectionSpace);
	if(status)
	 {
	 addActionMessage("Organization Registered Successfully!!!");
	 }
	 else
	 {
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 }
	}
	else
	{
	 addActionMessage("Organization Already Exist!!!");
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method createOrganizationLevel2 of class "+getClass(), e);
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String registredLevel2InDropDowns()
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	List org=new ArrayList<String>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List level2List=null;
	try
	{
	org.add("id");
	org.add("locCompany");
	level2List=cbt.viewAllDataEitherSelectOrAll("organization_level2",org,connectionSpace);
	if(level2List!=null)
	{
	for (Object c : level2List) {
	Object[] dataC = (Object[]) c;
	countryList.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method registredLevel2InDropDowns of class "+getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
	public String createOrganizationLevel3()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	//checking first if company entry exist no multiple time creation for single level
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List level2Exist=null;
	boolean statusExist=true;
	Map<String, String> temp=new HashMap<String, String>();
	try
	{
	temp.put("branchAddress",getBranchAddress());
	level2Exist=cbt.viewAllDataEitherSelectOrAll("organization_level3",temp,connectionSpace);
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	if(level2Exist==null)
	statusExist=true;
	else if(level2Exist.size()>0)
	statusExist=false;
	if(statusExist)
	{
	//if status is true it means u can create organization
	    boolean userTrue=false;
	boolean dateTrue=false;
	boolean timeTrue=false;	
	List<GridDataPropertyView>org3=Configuration.getConfigurationData("mapped_organizconf_level3",accountID,connectionSpace,"",0,"table_name","organizconf_level3");
	if(org3!=null)
	{
	//create table query based on configuration
	List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	for(GridDataPropertyView gdp:org3)
	{
	 TableColumes ob1=new TableColumes();
	 ob1=new TableColumes();
	 ob1.setColumnname(gdp.getColomnName());
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	 if(gdp.getColomnName().equalsIgnoreCase("userName"))
	 userTrue=true;
	 else if(gdp.getColomnName().equalsIgnoreCase("date"))
	 dateTrue=true;
	 else if(gdp.getColomnName().equalsIgnoreCase("time"))
	 timeTrue=true;
	}
	cbt.createTable22("organization_level3",Tablecolumesaaa,connectionSpace);
	}
	 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	 Collections.sort(requestParameterNames);
	 Iterator it = requestParameterNames.iterator();
	 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	 InsertDataTable ob=null;
	 while (it.hasNext()) {
	String Parmname = it.next().toString();
	String paramValue=request.getParameter(Parmname);
	if(paramValue!=null && !paramValue.equalsIgnoreCase(""))
	{
	 ob=new InsertDataTable();
	 ob.setColName(Parmname);
	 ob.setDataName(paramValue);
	 insertData.add(ob);
	}
	}
	 
	 if(userTrue)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("userName");
	 ob.setDataName(userName);
	 insertData.add(ob);
	 }
	 if(dateTrue)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("date");
	 ob.setDataName(DateUtil.getCurrentDateUSFormat());
	 insertData.add(ob);
	 }
	 if(timeTrue)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("time");
	 ob.setDataName(DateUtil.getCurrentTime());
	 insertData.add(ob);
	 }
	 
	boolean status=cbt.insertIntoTable("organization_level3",insertData,connectionSpace);
	if(status)
	 {
	 addActionMessage("Organization Registered Successfully!!!");
	 }
	 else
	 {
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 }
	}
	else
	{
	 addActionMessage("Organization Already Exist!!!");
	}
	
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method createOrganizationLevel3 of class "+getClass(), e);
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String getallLevel3Regsitration()
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	List org=new ArrayList<String>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List level2List=null;
	try
	{
	org.add("id");
	org.add("branchAddress");
	level2List=cbt.viewAllDataEitherSelectOrAll("organization_level3",org,connectionSpace);
	if(level2List!=null)
	{
	for (Object c : level2List) {
	Object[] dataC = (Object[]) c;
	countryList.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method getallLevel3Regsitration of class "+getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String createOrganizationLevel4()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	//checking first if company entry exist no multiple time creation for single level
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	boolean statusExist=true;
	List level4Exist=null;
	Map<String, String> temp=new HashMap<String, String>();
	try
	{
	temp.put("level4Address",getLevel4Address());
	level4Exist=cbt.viewAllDataEitherSelectOrAll("organization_level4",temp,connectionSpace);
	}
	catch(Exception e)
	{
	
	}
	
	if(level4Exist==null)
	statusExist=true;
	else if(level4Exist.size()>0)
	statusExist=false;
	if(statusExist)
	{
	    boolean userTrue=false;
	boolean dateTrue=false;
	boolean timeTrue=false;	
	
	//if status is true it means u can create organization
	List<GridDataPropertyView>org3=Configuration.getConfigurationData("mapped_organizconf_level4",accountID,connectionSpace,"",0,"table_name","organizconf_level4");
	if(org3!=null)
	{
	//create table query based on configuration
	List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	for(GridDataPropertyView gdp:org3)
	{
	 TableColumes ob1=new TableColumes();
	 ob1=new TableColumes();
	 ob1.setColumnname(gdp.getColomnName());
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	 if(gdp.getColomnName().equalsIgnoreCase("userName"))
	 userTrue=true;
	 else if(gdp.getColomnName().equalsIgnoreCase("date"))
	 dateTrue=true;
	 else if(gdp.getColomnName().equalsIgnoreCase("time"))
	 timeTrue=true;
	}
	cbt.createTable22("organization_level4",Tablecolumesaaa,connectionSpace);
	}
	 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	 Collections.sort(requestParameterNames);
	 Iterator it = requestParameterNames.iterator();
	 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	 InsertDataTable ob=null;
	 while (it.hasNext()) {
	String Parmname = it.next().toString();
	String paramValue=request.getParameter(Parmname);
	if(paramValue!=null && !paramValue.equalsIgnoreCase(""))
	{
	 ob=new InsertDataTable();
	 ob.setColName(Parmname);
	 ob.setDataName(paramValue);
	 insertData.add(ob);
	}
	}
	 
	 if(userTrue)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("userName");
	 ob.setDataName(userName);
	 insertData.add(ob);
	 }
	 if(dateTrue)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("date");
	 ob.setDataName(DateUtil.getCurrentDateUSFormat());
	 insertData.add(ob);
	 }
	 if(timeTrue)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("time");
	 ob.setDataName(DateUtil.getCurrentTime());
	 insertData.add(ob);
	 }
	boolean status=cbt.insertIntoTable("organization_level4",insertData,connectionSpace);
	if(status)
	 {
	 addActionMessage("Organization Registered Successfully!!!");
	 }
	 else
	 {
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 }
	}
	else
	{
	 addActionMessage("Organization Already Exist!!!");
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method createOrganizationLevel4 of class "+getClass(), e);
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	
	public String getallLevel4Regsitration()
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	List org=new ArrayList<String>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List level2List=null;
	try
	{
	org.add("id");
	org.add("level4Address");
	level2List=cbt.viewAllDataEitherSelectOrAll("organization_level4",org,connectionSpace);
	if(level2List!=null)
	{
	for (Object c : level2List) {
	Object[] dataC = (Object[]) c;
	countryList.put(dataC[0].toString(), dataC[1].toString());
	}
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method getallLevel4Regsitration of class "+getClass(), e);
	e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String createOrganizationLevel5()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	//checking first if company entry exist no multiple time creation for single level
	boolean statusExist=true;
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List level5Exist=null;
	Map<String, String> temp=new HashMap<String, String>();
	try
	{
	temp.put("level5Address",getLevel5Address());
	level5Exist=cbt.viewAllDataEitherSelectOrAll("organization_level5",temp,connectionSpace);
	}
	catch(Exception e)
	{
	
	}
	if(level5Exist==null)
	statusExist=true;
	else if(level5Exist.size()>0)
	statusExist=false;
	if(statusExist)
	{
	boolean userTrue=false;
	boolean dateTrue=false;
	boolean timeTrue=false;	
	//if status is true it means u can create organization
	List<GridDataPropertyView>org3=Configuration.getConfigurationData("mapped_organizconf_level5",accountID,connectionSpace,"",0,"table_name","organizconf_level5");
	if(org3!=null)
	{
	//create table query based on configuration
	List <TableColumes> Tablecolumesaaa=new ArrayList<TableColumes>();
	for(GridDataPropertyView gdp:org3)
	{
	 TableColumes ob1=new TableColumes();
	 ob1=new TableColumes();
	 ob1.setColumnname(gdp.getColomnName());
	 ob1.setDatatype("varchar(255)");
	 ob1.setConstraint("default NULL");
	 Tablecolumesaaa.add(ob1);
	 if(gdp.getColomnName().equalsIgnoreCase("userName"))
	 userTrue=true;
	 else if(gdp.getColomnName().equalsIgnoreCase("date"))
	 dateTrue=true;
	 else if(gdp.getColomnName().equalsIgnoreCase("time"))
	 timeTrue=true;
	}
	cbt.createTable22("organization_level5",Tablecolumesaaa,connectionSpace);
	}
	 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	 Collections.sort(requestParameterNames);
	 Iterator it = requestParameterNames.iterator();
	 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	 InsertDataTable ob=null;
	 while (it.hasNext()) {
	String Parmname = it.next().toString();
	String paramValue=request.getParameter(Parmname);
	if(paramValue!=null && !paramValue.equalsIgnoreCase(""))
	{
	 ob=new InsertDataTable();
	 ob.setColName(Parmname);
	 ob.setDataName(paramValue);
	 insertData.add(ob);
	}
	}
	 
	 if(userTrue)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("userName");
	 ob.setDataName(userName);
	 insertData.add(ob);
	 }
	 if(dateTrue)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("date");
	 ob.setDataName(DateUtil.getCurrentDateUSFormat());
	 insertData.add(ob);
	 }
	 if(timeTrue)
	 {
	 ob=new InsertDataTable();
	 ob.setColName("time");
	 ob.setDataName(DateUtil.getCurrentTime());
	 insertData.add(ob);
	 }
	boolean status=cbt.insertIntoTable("organization_level5",insertData,connectionSpace);
	if(status)
	 {
	 addActionMessage("Organization Registered Successfully!!!");
	 }
	 else
	 {
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 }
	}
	else
	{
	 addActionMessage("Organization Already Exist!!!");
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method createOrganizationLevel5 of class "+getClass(), e);
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	/*public String uploadOrgLogo()
	{
	boolean validFlag=ValidateSession.checkSession();
	if(validFlag)
	{
	try
	{
	String filePath = request.getSession().getServletContext().getRealPath("/images/orgLogo");
	//System.out.println("Server path:" + filePath);
	//System.out.println("Content Type "+getOrgLogoContentType());
	            orgLogoName = new KRActionHelper().getFileExtenstion(getOrgLogoContentType());
	            
	            orgLogoName = "orgLogo"+ orgLogoName;
	           // System.out.println("orgLogoName:"+orgLogoName);
	            File fileToCreate = new File(filePath, orgLogoName);
	            FileUtils.copyFile(this.orgLogo, fileToCreate);
	            orgLogoName = "images/orgLogo/"+ orgLogoName;
	           // System.out.println("orgLogoName:"+orgLogoName);
	            
	            
	            
	            
	            
	//ohfhfhd
	
	
	
	String filePath = request.getSession().getServletContext().getRealPath("/images/orgLogo");
	            System.out.println("Server path:" + filePath);
	            orgLogoName = new KRActionHelper().getFileExtenstion(getBrowseContentType());
	            
	
	
	System.out.println("Org Logo is as >>>>>>>>>>> :::::::::::"+getOrgLogo());
	BufferedImage bufferImage=null;
	try
	{
	bufferImage=ImageIO.read(getOrgLogo());
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
	ImageIO.write(bufferImage, "png", baos);
	baos.flush();
	byte[] imageInByte = baos.toByteArray();
	baos.close();
	int updated=new createTable().executeAllUpdateQuery("update organization_level1 set companyIcon='"+imageInByte+"'", connectionSpace);
	if(updated>0)
	{
	addActionMessage("Organization Logo Uploaded Successfully !!!");
	}
	else
	{
	addActionError("Opps there is some issue please upload png image only.");
	}
	}
	catch (IOException e) {
	e.printStackTrace();
	}
	return SUCCESS;
	
	
	return SUCCESS;
	
	}
	catch (Exception e) {
	e.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}*/
	
	
	public String uploadOrgLogo()
    {
    boolean validFlag=ValidateSession.checkSession();
    if(validFlag)
    {
    try
    {
    String filePath = request.getSession().getServletContext().getRealPath("/images/orgLogo");
    //System.out.println("Server path:" + filePath);
    //System.out.println("Content Type "+getOrgLogoContentType());
                orgLogoName = new KRActionHelper().getFileExtenstion(getOrgLogoContentType());
                
                orgLogoName = "orgLogo".concat(accountID)+ orgLogoName;
               //System.out.println("orgLogoName:"+orgLogoName);
                File fileToCreate = new File(filePath, orgLogoName);
                FileUtils.copyFile(this.orgLogo, fileToCreate);
                orgLogoName = "images/orgLogo/"+ orgLogoName;
              //  System.out.println("OrganizationAction.uploadOrgLogo()" +getOrgLogoName());
               int updated=new createTable().executeAllUpdateQuery("update organization_level1 set companyIcon='"+orgLogoName+"'", connectionSpace);
    
             //  System.out.println("********************** updated "+updated);
    /*
    
    String filePath = request.getSession().getServletContext().getRealPath("/images/orgLogo");
                Sys5tem.out.println("Server path:" + filePath);
                orgLogoName = new KRActionHelper().getFileExtenstion(getBrowseContentType());
                
    
    
    System.out.println("Org Logo is as >>>>>>>>>>> :::::::::::"+getOrgLogo());
    BufferedImage bufferImage=null;
    try
    {
    bufferImage=ImageIO.read(getOrgLogo());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
    ImageIO.write(bufferImage, "png", baos);
    baos.flush();
    byte[] imageInByte = baos.toByteArray();
    baos.close();
    int updated=new createTable().executeAllUpdateQuery("update organization_level1 set companyIcon='"+imageInByte+"'", connectionSpace);
    if(updated>0)
    {
    addActionMessage("Organization Logo Uploaded Successfully !!!");
    }
    else
    {
    addActionError("Opps there is some issue please upload png image only.");
    }
    }
    catch (IOException e) {
    e.printStackTrace();
    }
    return SUCCESS;
    */
    
    return SUCCESS;
    
    }
    catch (Exception e) {
    e.printStackTrace();
    return ERROR;
    }
    }
    else
    {
    return LOGIN;
    }
    }
	public String updateOrgInfoByTable()
	{
	boolean validFlag=ValidateSession.checkSession();
	if(validFlag)
	{
	boolean updated=false;
	
	Map<String, Object>wherClause=new HashMap<String, Object>();
	Map<String, Object>condtnBlock=new HashMap<String, Object>();
	
	condtnBlock.put("id",getId());
	if(getOrgName()!=null)
	{
	wherClause.put("companyName", getOrgName());
	}
	
	if(getOrgAddress()!=null)
	{
	wherClause.put("companyRegAddress", getOrgAddress());
	}
	
	if(getOrgCity()!=null)
	{
	wherClause.put("companyRegCity", getOrgCity());
	}
	if(getOrgCountry()!=null)
	{
	wherClause.put("companyRegCountry", getOrgCountry());
	}
	if(getOrgPin()!=null)
	{
	wherClause.put("companyRegPinCode", getOrgPin());
	}
	
	if(getOrgContactNo()!=null)
	{
	wherClause.put("companyRegContact1", getOrgContactNo());
	}
	if(getCompanyEmail()!=null)
	{
	wherClause.put("email", getCompanyEmail());
	}
	
	updated=new FeedbackUniversalHelper().updateTableColomn("organization_level1", wherClause, condtnBlock,connectionSpace);
	if(updated)
	{
	addActionMessage("Details Updated Successfully !!!");
	}
	else
	{
	addActionMessage("There Is Some Problem !!!");
	}
	return SUCCESS;
	}
	else
	{
	return LOGIN;
	}
	}
	
	
	public String getOrganizationViewInTabularForm()
	{
	boolean validFlag=ValidateSession.checkSession();
	if(validFlag)
	{
	try
	{
		setOrgImgPath(new
		CommonWork().getOrganizationImage((SessionFactory)session.get("connectionSpace")));
	List dataList=new createTable().executeAllSelectQuery("select companyName,companyRegAddress,companyRegCity,companyRegCountry,companyRegPinCode,companyRegContact1,id,email from organization_level1 limit 1",connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	for (Iterator iterator = dataList.iterator(); iterator .hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	
	if(object[0]!=null)
	{
	setOrgName(object[0].toString());
	}
	
	if(object[1]!=null)
	{
	setOrgAddress(object[1].toString());
	}
	
	if(object[2]!=null)
	{
	setOrgCity(object[2].toString());
	}
	
	if(object[3]!=null)
	{
	setOrgCountry(object[3].toString());
	}
	
	if(object[4]!=null)
	{
	setOrgPin(object[4].toString());
	}
	
	if(object[5]!=null)
	{
	setOrgContactNo(object[5].toString());
	}
	if (object[6]!=null) 
	{
	setId(Integer.parseInt(object[6].toString()));
	}
	if(object[7]!=null)
	{
	setCompanyEmail(object[7].toString());
	}
	}
	}
	return SUCCESS;
	}
	catch (Exception e) {
	e.printStackTrace();
	return ERROR;
	}
	}
	else
	{
	return LOGIN;
	}
	}
	
	
	
	/**
	 * Level 1 Data View Method
	 * @return
	 */

	public String beforeOrganizationView()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	
	modifyPrvlgs="false";
	
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List<String>colname=new ArrayList<String>();
	colname.add("orgLevel");
	colname.add("levelName");
	List orgntnlevel=cbt.viewAllDataEitherSelectOrAll("mapped_orgleinfo_level",colname,connectionSpace);
	if(orgntnlevel!=null && orgntnlevel.size()>0)
	{
	for (Object c : orgntnlevel) {
	
	Object[] dataC = (Object[]) c;
	String namesOfLevel=(String)dataC[1];
	
	String split[]=namesOfLevel.split("#");
	levelOfComp =Integer.parseInt((String)dataC[0]);
	if(levelOfComp>0)
	setLevel1Name(split[0]);
	/*if(levelOfComp>1)
	setLevel2Name(split[1]);
	if(levelOfComp>2)
	setLevel3Name(split[2]);
	if(levelOfComp>3)
	setLevel4Name(split[3]);
	if(levelOfComp>4)
	setLevel5Name(split[4]);*/
	}
	}
	else
	{
	levelOfComp=Integer.parseInt("1");
	}
	setGridColomnNamesForLevel1();
	mainHeaderName="Organization";
	headerName= level1Name+" Organization";
	headerName1=level2Name+" Organization";
	headerName2=level3Name+" Organization";
	headerName3=level4Name+" Organization";
	headerName4=level5Name+" Organization";
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method beforeOrganizationView of class "+getClass(), e);
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String beforeOrganizationModify()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	
	modifyPrvlgs="true";
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	List<String>colname=new ArrayList<String>();
	colname.add("orgLevel");
	colname.add("levelName");
	List orgntnlevel=cbt.viewAllDataEitherSelectOrAll("mapped_orgleinfo_level",colname,connectionSpace);
	if(orgntnlevel!=null && orgntnlevel.size()>0)
	{
	for (Object c : orgntnlevel) {
	
	Object[] dataC = (Object[]) c;
	String namesOfLevel=(String)dataC[1];
	
	String split[]=namesOfLevel.split("#");
	levelOfComp =Integer.parseInt((String)dataC[0]);
	if(levelOfComp>0)
	setLevel1Name(split[0]);
	if(levelOfComp>1)
	setLevel2Name(split[1]);
	if(levelOfComp>2)
	setLevel3Name(split[2]);
	if(levelOfComp>3)
	setLevel4Name(split[3]);
	if(levelOfComp>4)
	setLevel5Name(split[4]);
	}
	}
	else
	{
	levelOfComp=Integer.parseInt("1");
	}
	setGridColomnNamesForLevel1();
	mainHeaderName="Organization >> Modify";
	headerName= level1Name+" Organization >> Modify";
	headerName1=level2Name+" Organization >> Modify";
	headerName2=level3Name+" Organization >> Modify";
	headerName3=level4Name+" Organization >> Modify";
	headerName4=level5Name+" Organization >> Modify";
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method beforeOrganizationModify of class "+getClass(), e);
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String vieworganizationOfLevel1()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	
	setRecords(1);
	int to = (getRows() * getPage());
	int from = to - getRows();
	if (to > getRecords())
	to = getRecords();
	
	
	//getting the list of colmuns
	StringBuilder query=new StringBuilder("");
	query.append("select ");
	List fieldNames=Configuration.getColomnList("mapped_organizconf1", accountID, connectionSpace, "organizconf_level1");
	List<Object> Listhb=new ArrayList<Object>();
	int i=0;
	for(Iterator it=fieldNames.iterator(); it.hasNext();)
	{
	//generating the dyanamic query based on selected fields
	    Object obdata=(Object)it.next();
	    if(obdata!=null)
	    {
	    if(i<fieldNames.size()-1)
	    	query.append(obdata.toString()+",");
	    else
	    	query.append(obdata.toString());
	    }
	    i++;
	
	}
	query.append(" from organization_level1");
	
	if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
	{
	//add search  query based on the search operation
	
	query.append(" where ");
	
	if(getSearchOper().equalsIgnoreCase("eq"))
	{
	query.append(" "+getSearchField()+" = '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("cn"))
	{
	query.append(" "+getSearchField()+" like '%"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("bw"))
	{
	query.append(" "+getSearchField()+" like '"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("ne"))
	{
	query.append(" "+getSearchField()+" <> '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("ew"))
	{
	query.append(" "+getSearchField()+" like '%"+getSearchString()+"'");
	}
	
	}
	
	if (getSord() != null && !getSord().equalsIgnoreCase(""))
	    {
	if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
	{
	query.append(" order by "+getSidx());
	}
	    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
	    	    {
	    	    	query.append(" order by "+getSidx()+" DESC");
	    	    }
	    }
	
	query.append(" limit "+from+","+to);
	
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	/**
	 * **************************checking for colomon change due to configuration if then alter table
	 */
	cbt.checkTableColmnAndAltertable(fieldNames,"organization_level1",connectionSpace);
	
	List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	if(data!=null)
	{
	//level1Data
	for(Iterator it=data.iterator(); it.hasNext();)
	{
	Object[] obdata=(Object[])it.next();
	Map<String, Object> one=new HashMap<String, Object>();
	for (int k = 0; k < fieldNames.size(); k++) {
	if (obdata[k] == null
	|| obdata[k].toString()
	.equalsIgnoreCase("")) {
	obdata[k] = "NA";
	}
	if(obdata[k]!=null)
	{
	if(k==0)
	one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
	else{
	
	if(fieldNames.get(k).toString()!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
	{
	one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
	}
	else
	{
	one.put(fieldNames.get(k).toString(),obdata[k].toString());
	}
	}
	}
	}
	Listhb.add(one);
	}
	setLevel1DataObject(Listhb);
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method vieworganizationOfLevel1 of class "+getClass(), e);
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewLevel2Org()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	
	
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder query1=new StringBuilder("");
	query1.append("select count(*) from organization_level2");
	List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
	if(dataCount!=null)
	{
	BigInteger count=new BigInteger("0");
	for(Iterator it=dataCount.iterator(); it.hasNext();)
	{
	 Object obdata=(Object)it.next();
	 count=(BigInteger)obdata;
	}
	setRecords(count.intValue());
	int to = (getRows() * getPage());
	int from = to - getRows();
	if (to > getRecords())
	to = getRecords();
	
	//getting the list of colmuns
	StringBuilder query=new StringBuilder("");
	query.append("select ");
	List fieldNames=Configuration.getColomnList("mapped_organizconf_level2", accountID, connectionSpace, "organizconf_level2");
	List<Object> Listhb=new ArrayList<Object>();
	int i=0;
	for(Iterator it=fieldNames.iterator(); it.hasNext();)
	{
	//generating the dyanamic query based on selected fields
	    Object obdata=(Object)it.next();
	    if(obdata!=null)
	    {
	    if(i<fieldNames.size()-1)
	    	query.append(obdata.toString()+",");
	    else
	    	query.append(obdata.toString());
	    i++;
	    }
	
	}
	
	
	query.append(" from organization_level2");
	if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
	{
	//add search  query based on the search operation
	
	query.append(" where ");
	
	if(getSearchOper().equalsIgnoreCase("eq"))
	{
	query.append(" "+getSearchField()+" = '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("cn"))
	{
	query.append(" "+getSearchField()+" like '%"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("bw"))
	{
	query.append(" "+getSearchField()+" like '"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("ne"))
	{
	query.append(" "+getSearchField()+" <> '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("ew"))
	{
	query.append(" "+getSearchField()+" like '%"+getSearchString()+"'");
	}
	
	}
	
	if (getSord() != null && !getSord().equalsIgnoreCase(""))
	    {
	if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
	{
	query.append(" order by "+getSidx());
	}
	    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
	    	    {
	    	    	query.append(" order by "+getSidx()+" DESC");
	    	    }
	    }
	
	
	query.append(" limit "+from+","+to);
	
	/**
	 * **************************checking for colomon change due to configuration if then alter table
	 */
	cbt.checkTableColmnAndAltertable(fieldNames,"organization_level2",connectionSpace);
	
	
	List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	if(data!=null)
	{
	//level2Data
	for(Iterator it=data.iterator(); it.hasNext();)
	{
	Object[] obdata=(Object[])it.next();
	Map<String, Object> one=new HashMap<String, Object>();
	for (int k = 0; k < fieldNames.size(); k++) {
	if(obdata[k]!=null)
	{
	if(k==0)
	one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
	else
	{
	
	if(fieldNames.get(k).toString()!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
	{
	one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
	}
	else
	{
	one.put(fieldNames.get(k).toString(), DateUtil.makeTitle(obdata[k].toString()));
	}
	}
	}
	}
	Listhb.add(one);
	}
	setLevel1DataObject(Listhb);
	
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	}
	
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method viewLevel2Org of class "+getClass(), e);
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewLevel3Org()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder query1=new StringBuilder("");
	query1.append("select count(*) from organization_level3 as level3 " +
	"where level3.locationCompany="+getId());
	List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
	BigInteger count=new BigInteger("0");
	for(Iterator it=dataCount.iterator(); it.hasNext();)
	{
	 Object obdata=(Object)it.next();
	 count=(BigInteger)obdata;
	}
	setRecords(count.intValue());
	int to = (getRows() * getPage());
	int from = to - getRows();
	if (to > getRecords())
	to = getRecords();
	//getting the list of colmuns
	StringBuilder query=new StringBuilder("");
	query.append("select ");
	List fieldNames=Configuration.getColomnList("mapped_organizconf_level3", accountID, connectionSpace, "organizconf_level3");
	List<Object> Listhb=new ArrayList<Object>();
	int i=0;
	for(Iterator it=fieldNames.iterator(); it.hasNext();)
	{
	//generating the dyanamic query based on selected fields
	    Object obdata=(Object)it.next();
	    if(obdata!=null)
	    {
	    	if(obdata.toString().equalsIgnoreCase("locationCompany"))
	    	{
	    if(i<fieldNames.size()-1)
	    	query.append("level2.locCompany,");
	    else
	    	query.append("level2.locCompany");
	    	}
	    	else
	    	{
	    	if(i<fieldNames.size()-1)
	    	query.append("level3."+obdata.toString()+",");
	    else
	    	query.append("level3."+obdata.toString());
	    	}
	    }
	    i++;
	
	}
	
	query.append(" from organization_level3 as level3 " +
	"inner join  organization_level2 as level2 on  " +
	"level3.locationCompany=level2.id where level3.locationCompany="+getId());
	if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
	{
	//add search  query based on the search operation
	if(getSearchOper().equalsIgnoreCase("eq"))
	{
	query.append(" and level3."+getSearchField()+" = '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("cn"))
	{
	query.append(" and level3."+getSearchField()+" like '%"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("bw"))
	{
	query.append(" and level3."+getSearchField()+" like '"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("ne"))
	{
	query.append(" and level3."+getSearchField()+" <> '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("ew"))
	{
	query.append(" and level3."+getSearchField()+" like '%"+getSearchString()+"'");
	}
	
	}
	
	if (getSord() != null && !getSord().equalsIgnoreCase(""))
	    {
	if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
	{
	query.append(" order by level3."+getSidx());
	}
	    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
	    	    {
	    	    	query.append(" order by level3."+getSidx()+" DESC");
	    	    }
	    }
	
	query.append(" limit "+from+","+to);
	
	
	/**
	 * **************************checking for colomon change due to configuration if then alter table
	 */
	cbt.checkTableColmnAndAltertable(fieldNames,"organization_level3",connectionSpace);
	
	List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	if(data!=null)
	{
	for(Iterator it=data.iterator(); it.hasNext();)
	{
	Object[] obdata=(Object[])it.next();
	Map<String, Object> one=new HashMap<String, Object>();
	for (int k = 0; k < fieldNames.size(); k++) {
	if(obdata[k]!=null)
	{
	if(k==0)
	one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
	else{
	
	if(fieldNames.get(k).toString()!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
	{
	one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
	}
	else
	{
	one.put(fieldNames.get(k).toString(), DateUtil.makeTitle(obdata[k].toString()));
	}
	}
	}
	}
	Listhb.add(one);
	}
	setLevel1DataObject(Listhb);
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	}
	
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method viewLevel3Org of class "+getClass(), e);
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewLevel4Org()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder query1=new StringBuilder("");
	query1.append("select count(*) from organization_level4 as level4 " +
	"where level4.level3Registration="+getId());
	List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
	
	BigInteger count=new BigInteger("0");
	for(Iterator it=dataCount.iterator(); it.hasNext();)
	{
	 Object obdata=(Object)it.next();
	 count=(BigInteger)obdata;
	}
	setRecords(count.intValue());
	int to = (getRows() * getPage());
	int from = to - getRows();
	if (to > getRecords())
	to = getRecords();
	
	//getting the list of colmuns
	StringBuilder query=new StringBuilder("");
	query.append("select ");
	List fieldNames=Configuration.getColomnList("mapped_organizconf_level4", accountID, connectionSpace, "organizconf_level4");
	List<Object> Listhb=new ArrayList<Object>();
	int i=0;
	for(Iterator it=fieldNames.iterator(); it.hasNext();)
	{
	//generating the dyanamic query based on selected fields
	    Object obdata=(Object)it.next();
	    if(obdata!=null)
	    {
	    	if(obdata.toString().equalsIgnoreCase("level3Registration"))
	    	{
	    if(i<fieldNames.size()-1)
	    	query.append("level3.branchAddress,");
	    else
	    	query.append("level3.branchAddress");
	    	}
	    	else
	    	{
	    	if(i<fieldNames.size()-1)
	    	query.append("level4."+obdata.toString()+",");
	    else
	    	query.append("level4."+obdata.toString());
	    	}
	    }
	    i++;
	
	}
	
	query.append(" from "
	 +"organization_level4 as level4 inner join organization_level3 as level3" +
	" on level4.level3Registration=level3.id where level4.level3Registration="+getId());
	if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
	{
	//add search  query based on the search operation
	if(getSearchOper().equalsIgnoreCase("eq"))
	{
	query.append(" and level4."+getSearchField()+" = '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("cn"))
	{
	query.append(" and level4."+getSearchField()+" like '%"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("bw"))
	{
	query.append(" and level4."+getSearchField()+" like '"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("ne"))
	{
	query.append(" and level4."+getSearchField()+" <> '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("ew"))
	{
	query.append(" and level4."+getSearchField()+" like '%"+getSearchString()+"'");
	}
	
	}
	
	
	if (getSord() != null && !getSord().equalsIgnoreCase(""))
	    {
	if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
	{
	query.append(" order by level4."+getSidx());
	}
	    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
	    	    {
	    	    	query.append(" order by level4."+getSidx()+" DESC");
	    	    }
	    }
	
	
	query.append(" limit "+from+","+to);
	
	
	/**
	 * **************************checking for colomon change due to configuration if then alter table
	 */
	cbt.checkTableColmnAndAltertable(fieldNames,"organization_level4",connectionSpace);
	
	
	List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	if(data!=null)
	{
	
	for(Iterator it=data.iterator(); it.hasNext();)
	{
	Object[] obdata=(Object[])it.next();
	Map<String, Object> one=new HashMap<String, Object>();
	for (int k = 0; k < fieldNames.size(); k++) {
	if(obdata[k]!=null)
	{
	if(k==0)
	one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
	else{
	
	if(fieldNames.get(k).toString()!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
	{
	one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
	}
	else
	{
	one.put(fieldNames.get(k).toString(), DateUtil.makeTitle(obdata[k].toString()));
	}
	}
	}
	}
	Listhb.add(one);
	}
	setLevel1DataObject(Listhb);
	
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	}
	
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method viewLevel4Org of class "+getClass(), e);
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewLevel5Org()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder query1=new StringBuilder("");
	query1.append("select count(*) from organization_level5 as level5 " +
	"where level5.level4Registration="+getId());
	List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
	
	BigInteger count=new BigInteger("0");
	for(Iterator it=dataCount.iterator(); it.hasNext();)
	{
	 Object obdata=(Object)it.next();
	 count=(BigInteger)obdata;
	}
	setRecords(count.intValue());
	int to = (getRows() * getPage());
	int from = to - getRows();
	if (to > getRecords())
	to = getRecords();
	
	//getting the list of colmuns
	StringBuilder query=new StringBuilder("");
	query.append("select ");
	List fieldNames=Configuration.getColomnList("mapped_organizconf_level5", accountID, connectionSpace, "organizconf_level5");
	List<Object> Listhb=new ArrayList<Object>();
	int i=0;
	for(Iterator it=fieldNames.iterator(); it.hasNext();)
	{
	//generating the dyanamic query based on selected fields
	    Object obdata=(Object)it.next();
	    if(obdata!=null)
	    {
	    	if(obdata.toString().equalsIgnoreCase("level4Registration"))
	    	{
	    if(i<fieldNames.size()-1)
	    	query.append("level4.level4Address,");
	    else
	    	query.append("level4.level4Address");
	    	}
	    	else
	    	{
	    	if(i<fieldNames.size()-1)
	    	query.append("level5."+obdata.toString()+",");
	    else
	    	query.append("level5."+obdata.toString());
	    	}
	    }
	    i++;
	
	}
	
	query.append(" from "
	 +"organization_level5 as level5 inner join organization_level4 as level4" +
	" on level5.level4Registration=level4.id where level5.level4Registration="+getId());
	if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
	{
	//add search  query based on the search operation
	if(getSearchOper().equalsIgnoreCase("eq"))
	{
	query.append(" and level5."+getSearchField()+" = '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("cn"))
	{
	query.append(" and level5."+getSearchField()+" like '%"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("bw"))
	{
	query.append(" and level5."+getSearchField()+" like '"+getSearchString()+"%'");
	}
	else if(getSearchOper().equalsIgnoreCase("ne"))
	{
	query.append(" and level5."+getSearchField()+" <> '"+getSearchString()+"'");
	}
	else if(getSearchOper().equalsIgnoreCase("ew"))
	{
	query.append(" and level5."+getSearchField()+" like '%"+getSearchString()+"'");
	}
	
	}
	
	if (getSord() != null && !getSord().equalsIgnoreCase(""))
	    {
	if(getSord().equals("asc") && getSidx()!=null && !getSidx().equals(""))
	{
	query.append(" order by level5."+getSidx());
	}
	    	    if(getSord().equals("desc") && getSidx()!=null && !getSidx().equals(""))
	    	    {
	    	    	query.append(" order by level5."+getSidx()+" DESC");
	    	    }
	    }
	
	
	query.append(" limit "+from+","+to);
	
	/**
	 * **************************checking for colomon change due to configuration if then alter table
	 */
	cbt.checkTableColmnAndAltertable(fieldNames,"organization_level5",connectionSpace);
	
	
	List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	if(data!=null)
	{
	for(Iterator it=data.iterator(); it.hasNext();)
	{
	Object[] obdata=(Object[])it.next();
	Map<String, Object> one=new HashMap<String, Object>();
	for (int k = 0; k < fieldNames.size(); k++) {
	if(obdata[k]!=null)
	{
	if(k==0)
	one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);

	else
	{
	
	if(fieldNames.get(k).toString()!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
	{
	one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
	}
	else
	{
	one.put(fieldNames.get(k).toString(), DateUtil.makeTitle(obdata[k].toString()));
	}
	}
	}
	}
	Listhb.add(one);
	}
	setLevel1DataObject(Listhb);
	
	setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
	}
	
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method viewLevel5Org of class "+getClass(), e);
	 addActionMessage("Ooops There Is Some Problem In Organization Registration!");
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	/**
	 * Organization Level 1 to Level 5 Data Modification
	 */
	
	public String viewModifyLevel1Org()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	if(getOper().equalsIgnoreCase("edit"))
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	Map<String, Object>wherClause=new HashMap<String, Object>();
	Map<String, Object>condtnBlock=new HashMap<String, Object>();
	List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	Collections.sort(requestParameterNames);
	Iterator it = requestParameterNames.iterator();
	List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	while (it.hasNext()) {
	String Parmname = it.next().toString();
	String paramValue=request.getParameter(Parmname);
	if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
	&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
	wherClause.put(Parmname, paramValue);
	}
	
	condtnBlock.put("id",getId());
	cbt.updateTableColomn("organization_level1", wherClause, condtnBlock,connectionSpace);
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method viewModifyLevel1Org of class "+getClass(), e);
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewModifyLevel2Org()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	if(getOper().equalsIgnoreCase("edit"))
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	Map<String, Object>wherClause=new HashMap<String, Object>();
	Map<String, Object>condtnBlock=new HashMap<String, Object>();
	List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	Collections.sort(requestParameterNames);
	Iterator it = requestParameterNames.iterator();
	List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	while (it.hasNext()) {
	String Parmname = it.next().toString();
	String paramValue=request.getParameter(Parmname);
	if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
	&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
	wherClause.put(Parmname, paramValue);
	}
	condtnBlock.put("id",getId());
	cbt.updateTableColomn("organization_level2", wherClause, condtnBlock,connectionSpace);
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method viewModifyLevel2Org of class "+getClass(), e);
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewModifyLevel3Org()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	if(getOper().equalsIgnoreCase("edit"))
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	Map<String, Object>wherClause=new HashMap<String, Object>();
	Map<String, Object>condtnBlock=new HashMap<String, Object>();
	List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	Collections.sort(requestParameterNames);
	Iterator it = requestParameterNames.iterator();
	List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	while (it.hasNext()) {
	String Parmname = it.next().toString();
	String paramValue=request.getParameter(Parmname);
	if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
	&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
	wherClause.put(Parmname, paramValue);
	}
	
	condtnBlock.put("id",getId());
	cbt.updateTableColomn("organization_level3", wherClause, condtnBlock,connectionSpace);
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method viewModifyLevel3Org of class "+getClass(), e);
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewModifyLevel4Org()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	if(getOper().equalsIgnoreCase("edit"))
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	Map<String, Object>wherClause=new HashMap<String, Object>();
	Map<String, Object>condtnBlock=new HashMap<String, Object>();
	List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	Collections.sort(requestParameterNames);
	Iterator it = requestParameterNames.iterator();
	 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	 InsertDataTable ob=null;
	while (it.hasNext()) {
	String Parmname = it.next().toString();
	String paramValue=request.getParameter(Parmname);
	if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
	&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper")&& !Parmname.equalsIgnoreCase("rowid"))
	wherClause.put(Parmname, paramValue);
	}
	condtnBlock.put("id",getId());
	cbt.updateTableColomn("organization_level4", wherClause, condtnBlock,connectionSpace);
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method viewModifyLevel4Org of class "+getClass(), e);
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public String viewModifyLevel5Org()
	{
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	if(getOper().equalsIgnoreCase("edit"))
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	 Map<String, Object>wherClause=new HashMap<String, Object>();
	 Map<String, Object>condtnBlock=new HashMap<String, Object>();
	 List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
	 Collections.sort(requestParameterNames);
	 Iterator it = requestParameterNames.iterator();
	 List <InsertDataTable> insertData=new ArrayList<InsertDataTable>();
	 InsertDataTable ob=null;
	while (it.hasNext()) {
	String Parmname = it.next().toString();
	String paramValue=request.getParameter(Parmname);
	if(paramValue!=null && !paramValue.equalsIgnoreCase("") && Parmname!=null && !Parmname.equalsIgnoreCase("") 
	&& !Parmname.equalsIgnoreCase("id")&& !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
	wherClause.put(Parmname, paramValue);
	}
	condtnBlock.put("id",getId());
	cbt.updateTableColomn("organization_level5", wherClause, condtnBlock,connectionSpace);
	}
	}
	catch(Exception e)
	{
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method viewModifyLevel5Org of class "+getClass(), e);
	 e.printStackTrace();
	}
	return SUCCESS;
	}
	
	public void setGridColomnNamesForLevel1()
	{
	GridDataPropertyView gpv=new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Registration Id");
	gpv.setHideOrShow("true");
	level1ColmnNames.add(gpv);
	
	List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_organizconf1",accountID,connectionSpace,"",0,"table_name","organizconf_level1");
	for(GridDataPropertyView gdp:returnResult)
	{
	gpv=new GridDataPropertyView();
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	gpv.setWidth(gdp.getWidth());
	level1ColmnNames.add(gpv);
	}
	
	
	/**
	 * level 2 grid colomn view
	 */
	if(levelOfComp>1)
	{
	gpv=new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Registration Id");
	gpv.setHideOrShow("true");
	level2ColmnNames.add(gpv);
	returnResult=Configuration.getConfigurationData("mapped_organizconf_level2",accountID,connectionSpace,"",0,"table_name","organizconf_level2");
	for(GridDataPropertyView gdp:returnResult)
	{
	gpv=new GridDataPropertyView();
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	gpv.setWidth(gdp.getWidth());
	level2ColmnNames.add(gpv);
	}
	
	}
	
	if(levelOfComp>2)
	{
	/**
	 * level 3 configuration
	 */
	gpv=new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Registration Id");
	gpv.setHideOrShow("true");
	level3ColmnNames.add(gpv);
	returnResult=Configuration.getConfigurationData("mapped_organizconf_level3",accountID,connectionSpace,"",0,"table_name","organizconf_level3");
	for(GridDataPropertyView gdp:returnResult)
	{
	gpv=new GridDataPropertyView();
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	level3ColmnNames.add(gpv);
	}
	
	}
	
	if(levelOfComp>3)
	{
	/**
	 * level 4 configuration
	 */
	gpv=new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Registration Id");
	gpv.setHideOrShow("true");
	level4ColmnNames.add(gpv);
	returnResult=Configuration.getConfigurationData("mapped_organizconf_level4",accountID,connectionSpace,"",0,"table_name","organizconf_level4");
	
	for(GridDataPropertyView gdp:returnResult)
	{
	gpv=new GridDataPropertyView();
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	level4ColmnNames.add(gpv);
	}
	}
	
	if(levelOfComp>4)
	{
	/**
	 * level 5 configuration
	 */
	gpv=new GridDataPropertyView();
	gpv.setColomnName("id");
	gpv.setHeadingName("Registration Id");
	gpv.setHideOrShow("true");
	level5ColmnNames.add(gpv);
	returnResult=Configuration.getConfigurationData("mapped_organizconf_level5",accountID,connectionSpace,"",0,"table_name","organizconf_level5");
	for(GridDataPropertyView gdp:returnResult)
	{
	gpv=new GridDataPropertyView();
	gpv.setColomnName(gdp.getColomnName());
	gpv.setHeadingName(gdp.getHeadingName());
	gpv.setEditable(gdp.getEditable());
	gpv.setSearch(gdp.getSearch());
	gpv.setHideOrShow(gdp.getHideOrShow());
	level5ColmnNames.add(gpv);
	}
	
	
	}
	
	}
	
	
	/**
	 * Attached Logo View
	 * @return
	 */
	
	public String getUploads()
	 {
	try {
	         	    
	              if(getUploadId()!=null)
	                {
	            	   CommonforClass eventDao = new CommanOper();
	            	   Avatar commonUpload = (Avatar)eventDao.findRecordForPk(Integer.parseInt(getUploadId()), Avatar.class);
	            	   if(commonUpload!=null && commonUpload.getImage()!=null)
	            	   {
	               setInputStream(new ByteArrayInputStream(commonUpload.getImage()));
	                   setContentType(commonUpload.getContentType()); 
	            	   }
	                   //System.out.println(getUploadId()+">>>>>>>>>uplosdfsff");	
	                }
	   } 
	catch(Exception e)
	   {
	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
	"Problem in Over2Cloud  method getUploads of class "+getClass(), e);
	e.printStackTrace();
	  	addActionError("Oops there is some problem!!!");
	return ERROR;
	   }
	return SUCCESS;
	}
	
	
	public String orgLeveonfigurationEditsss(){
	try
	{
	if(userName==null || userName.equalsIgnoreCase(""))
	return LOGIN;
	List<String>colname=new ArrayList<String>();
	Map<String, Object> temp=new HashMap<String, Object>();
	CommonOperInterface cbt=new CommonConFactory().createInterface();

	List<String> data=new ArrayList<String>();
	data.add("mappedid");
	data.add("table_name");
	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put("id", getId());
	setId(getId());
	List resultdata = new CommonOperAtion().getSelectTabledata(
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
	Map<String, String>configuration2=new HashMap<String,String>();
	if(level>0){
	setMappedtablelevel1(objectCal[5].toString());
	Map<String, String>configurationmap=new HashMap<String,String>();
	List<ConfigurationUtilBean> ListObj=new ArrayList<ConfigurationUtilBean>();
	List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
	List organizationLoList1=Configuration.getConfigurationIdss(objectCal[5].toString(), data, accountID, temp, connectionSpace);	
	for (Object c1 : organizationLoList1) {
	Object[] dataC1 = (Object[]) c1;
	if(dataC1[3].toString().equalsIgnoreCase("T")){
	
	ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
	objEjb.setActives(dataC1[4].toString().trim());
	objEjb.setField_name(dataC1[0].toString().trim());
	objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
	ListObj.add(objEjb);
	     }
	if(dataC1[3].toString().equalsIgnoreCase("D")){
	 configuration2.put(dataC1[1].toString().trim(), dataC1[2].toString().trim());
	 configurationmap.put(dataC1[1].toString().trim(), dataC1[0].toString().trim());
	}
	if(dataC1[3].toString().equalsIgnoreCase("F")){
	ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
	objEjb.setField_name(dataC1[0].toString().trim());
	objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
	objEjb.setActives(dataC1[4].toString().trim());
	ListObjs.add(objEjb);
	}
                
	}
	setListconfiguration(ListObj);
	    setListconfiguration1(ListObjs);
	       
	for (Map.Entry<String, String> entry : configurationmap.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("industryParam")){
	industryParam1=true;
	industryParamName=entry.getValue();}
	if(tmpString.equalsIgnoreCase("companyRegCountry")){
	companyRegCountry1=true;
	companyRegCountryname=entry.getValue();}
	if(tmpString.equalsIgnoreCase("companyType")){
	companyType1=true;
	companyTypeName=entry.getValue();}}
	for (Map.Entry<String, String> entry : configuration2.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("industryParam")){
	industryParamkey=entry.getValue();}
	if(tmpString.equalsIgnoreCase("companyRegCountry")){
	companyRegCountrykey=entry.getValue();}
	if(tmpString.equalsIgnoreCase("companyType")){
	companyTypekey=entry.getValue();}
	}
	
	}
	
	if(objectCal[12]!=null && !objectCal[12].toString().equalsIgnoreCase("")){
	String[] levalemappedtable=objectCal[12].toString().split("#");
	if(level>1){
	setMappedtablelevel2(levalemappedtable[0]);
	//for level 2
	Map<String, String>configurationmap=new HashMap<String,String>();
	List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
	List configurationForLevl2List=Configuration.getConfigurationIdss(levalemappedtable[0], data, accountID, temp, connectionSpace);
	for (Object c1 : configurationForLevl2List) {
	Object[] dataC1 = (Object[]) c1;
	if(dataC1[3].toString().equalsIgnoreCase("T")){
	ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
	objEjb.setField_name(dataC1[0].toString().trim());
	objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
	objEjb.setActives(dataC1[4].toString().trim());
	ListObjs.add(objEjb);
	}
	if(dataC1[3].toString().equalsIgnoreCase("D")){
	 configuration2.put(dataC1[1].toString().trim(), dataC1[2].toString().trim());
	 configurationmap.put(dataC1[1].toString().trim(), dataC1[0].toString().trim());
	}
	}
	  setListconfiguration2(ListObjs);
	for (Map.Entry<String, String> entry : configurationmap.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("locCompany")){
	organizationLocLevel2=true;
	organizationLocLevel2Name=entry.getValue();
	}
	if(tmpString.equalsIgnoreCase("locCompanyRegCountry")){
	organizationRegComLevel2=true;
	organizationRegComLevel2Name=entry.getValue();
	}
	
	}
	for (Map.Entry<String, String> entry : configuration2.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("locCompany")){
	organizationLocLevel2key=entry.getValue();}
	if(tmpString.equalsIgnoreCase("locCompanyRegCountry")){
	organizationRegComLevel2key=entry.getValue();
	}
	
	}
	}
	if(level>2){
	Map<String, String>configurationmap=new HashMap<String,String>();
	//for level3
	setMappedtablelevel3(levalemappedtable[1]);
	List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
	List configurationForLevLIst=Configuration.getConfigurationIdss(levalemappedtable[1], data, accountID, temp, connectionSpace);
	for (Object c1 : configurationForLevLIst) {
	Object[] dataC1 = (Object[]) c1;
	if(dataC1[3].toString().equalsIgnoreCase("T")){
	ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
	objEjb.setField_name(dataC1[0].toString().trim());
	objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
	objEjb.setActives(dataC1[4].toString().trim());
	ListObjs.add(objEjb);
	
	}
	if(dataC1[3].toString().equalsIgnoreCase("D")){
	 configuration2.put(dataC1[1].toString().trim(), dataC1[2].toString().trim()); 
	configurationmap.put(dataC1[1].toString().trim(), dataC1[0].toString().trim());
	}
	}
	setListconfiguration3(ListObjs);
	for (Map.Entry<String, String> entry : configurationmap.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("locationCompany"))
	{
	organizationLocLevel3Name=entry.getValue();
	}
	}
	for (Map.Entry<String, String> entry : configuration2.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("locationCompany"))
	{
	organizationLocLevel3key=entry.getValue();
	}
	}
	}
	if(level>3){
	setMappedtablelevel4(levalemappedtable[2]);
	//for level4
	Map<String, String>configurationmap=new HashMap<String,String>();
	List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
	List configurationForList4=Configuration.getConfigurationIdss(levalemappedtable[2], data, accountID, temp, connectionSpace);
	for (Object c1 : configurationForList4) {
	Object[] dataC1 = (Object[]) c1;
	if(dataC1[3].toString().equalsIgnoreCase("T")){
	ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
	objEjb.setField_name(dataC1[0].toString().trim());
	objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
	objEjb.setActives(dataC1[4].toString().trim());
	ListObjs.add(objEjb);
	
	}
	if(dataC1[3].toString().equalsIgnoreCase("D")){
	 configuration2.put(dataC1[1].toString().trim(), dataC1[2].toString().trim()); 
	configurationmap.put(dataC1[1].toString().trim(), dataC1[0].toString().trim());
	}
	}
	setListconfiguration4(ListObjs);
	for (Map.Entry<String, String> entry : configurationmap.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("level3Registration"))
	{
	organizationLocLevel4Name=entry.getValue();
	}
	}
	for (Map.Entry<String, String> entry : configuration2.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("level3Registration"))
	{
	organizationLocLevel4key=entry.getValue();
	}
	}
	}
	if(level>4){
	setMappedtablelevel5(levalemappedtable[3]);
	Map<String, String>configurationmap=new HashMap<String,String>();
	List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
	List configurationForList4=Configuration.getConfigurationIdss(levalemappedtable[3], data, accountID, temp, connectionSpace);
	
	for (Object c1 : configurationForList4) {
	Object[] dataC1 = (Object[]) c1;
	if(dataC1[3].toString().equalsIgnoreCase("T")){
	ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
	objEjb.setField_name(dataC1[0].toString().trim());
	objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
	objEjb.setActives(dataC1[4].toString().trim());
	ListObjs.add(objEjb);
	
	  }
	   if(dataC1[3].toString().equalsIgnoreCase("D")){
	   configuration2.put(dataC1[1].toString().trim(), dataC1[2].toString().trim()); 
	   configurationmap.put(dataC1[1].toString().trim(), dataC1[0].toString().trim());
	}
	for (Map.Entry<String, String> entry : configurationmap.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("level4Registration"))
	{
	organizationLocLevel5Name=entry.getValue();
	}
	}
	for (Map.Entry<String, String> entry : configuration2.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("level4Registration"))
	{
	organizationLocLevel5key=entry.getValue();
	}
	}
	}
	setListconfiguration5(ListObjs);
	}
	globalLevel=level;
	return "success";
	   }
	/*
	 * level1Name;
	 */
	return  "1";
	 	    }
	  }
	else{
	  return "1";}
	 }else{
	setMappedtablelevel1(objectCal[5].toString());
	if(objectCal[2]!=null){
	Map<String, String>configuration2=new HashMap<String,String>();
	Map<String, Object>mapObject=new HashMap<String,Object>();
	mapObject.put("table_name",objectCal[2].toString());
	
	String mappedIds=null;
	String mappedTable=null;
	                 resultdata = new CommonOperAtion().getSelectTabledata(objectCal[5].toString(), mapObject,connectionSpace);	
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
	Map<String, String>configurationmap=new HashMap<String,String>();
	List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
	for (Object c1 : configurationForList) {
	Object[] dataC1 = (Object[]) c1;
	if(dataC1[3].toString().equalsIgnoreCase("T")){
	ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
	objEjb.setField_name(dataC1[0].toString().trim());
	objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
	objEjb.setActives(dataC1[4].toString().trim());
	ListObjs.add(objEjb);
	}
	if(dataC1[3].toString().equalsIgnoreCase("D")){
	 configuration2.put(dataC1[1].toString().trim(), dataC1[2].toString().trim()); 
	configurationmap.put(dataC1[1].toString().trim(), dataC1[0].toString().trim());
	}
	}
	setListconfiguration4(ListObjs);
	for (Map.Entry<String, String> entry : configurationmap.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("level3Registration"))
	{
	organizationLocLevel4Name=entry.getValue();
	}
	}
	for (Map.Entry<String, String> entry : configuration2.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("level3Registration"))
	{
	organizationLocLevel4key=entry.getValue();
	}
	}
	}
	if(objectCal[3]!=null){
	Map<String, String>configuration2=new HashMap<String,String>();
	Map<String, Object>mapObject=new HashMap<String,Object>();
	mapObject.put("table_name", objectCal[3].toString());
	String mappedIds=null;
	String mappedTable=null;
	resultdata = new CommonOperAtion().getSelectTabledata(objectCal[5].toString(), mapObject,connectionSpace);	
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
	Map<String, String>configurationmap=new HashMap<String,String>();
	List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
	for (Object c1 : configurationForList2) {
	Object[] dataC1 = (Object[]) c1;
	if(dataC1[3].toString().equalsIgnoreCase("T")){
	ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
	objEjb.setField_name(dataC1[0].toString().trim());
	objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
	objEjb.setActives(dataC1[4].toString().trim());
	ListObjs.add(objEjb);
	
	}
	if(dataC1[3].toString().equalsIgnoreCase("D")){
	 configuration2.put(dataC1[1].toString().trim(), dataC1[2].toString().trim()); 
	configurationmap.put(dataC1[1].toString().trim(), dataC1[0].toString().trim());
	}
	}
	setListconfiguration4(ListObjs);
	for (Map.Entry<String, String> entry : configurationmap.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("level3Registration"))
	{
	organizationLocLevel4Name=entry.getValue();
	}
	}
	for (Map.Entry<String, String> entry : configuration2.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("level3Registration"))
	{
	organizationLocLevel4key=entry.getValue();
	}
	}
	}
	 
	                  if(objectCal[4]!=null){
	              	Map<String, String>configuration2=new HashMap<String,String>();
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
	
	Map<String, String>configurationmap=new HashMap<String,String>();
	List<ConfigurationUtilBean> ListObjs=new ArrayList<ConfigurationUtilBean>();
	for (Object c1 : configurationForList3) {
	Object[] dataC1 = (Object[]) c1;
	if(dataC1[3].toString().equalsIgnoreCase("T")){
	ConfigurationUtilBean objEjb=new ConfigurationUtilBean();
	objEjb.setField_name(dataC1[0].toString().trim());
	objEjb.setId(Integer.parseInt(dataC1[2].toString().trim()));
	objEjb.setActives(dataC1[4].toString().trim());
	ListObjs.add(objEjb);
	
	}
	if(dataC1[3].toString().equalsIgnoreCase("D")){
	 configuration2.put(dataC1[1].toString().trim(), dataC1[2].toString().trim()); 
	configurationmap.put(dataC1[1].toString().trim(), dataC1[0].toString().trim());
	}
	}
	setListconfiguration4(ListObjs);
	for (Map.Entry<String, String> entry : configurationmap.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("level3Registration"))
	{
	organizationLocLevel4Name=entry.getValue();
	}
	}
	for (Map.Entry<String, String> entry : configuration2.entrySet()) {
	String tmpString=entry.getKey().trim();
	if(tmpString.equalsIgnoreCase("level3Registration"))
	{
	organizationLocLevel4key=entry.getValue();
	}
	}
	
	}
	 
	     return "successssssss";
	 
	                  }
	 }
	}
	
	}
	catch(Exception e)
	{
	e.printStackTrace();
	return  "1";
	}
	return "1";
	}
	
	
	
	
	
	public InputStream getInputStream() {
	return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
	this.inputStream = inputStream;
	}

	public String getUploadId() {
	return uploadId;
	}

	public void setUploadId(String uploadId) {
	this.uploadId = uploadId;
	}

	public int getLevelOfComp() {
	return levelOfComp;
	}

	public void setLevelOfComp(int levelOfComp) {
	this.levelOfComp = levelOfComp;
	}

	public Map<String, String> getCountryList() {
	return countryList;
	}

	public void setCountryList(Map<String, String> countryList) {
	this.countryList = countryList;
	}

	public Map<Integer, String> getIndustryList() {
	return industryList;
	}

	public void setIndustryList(Map<Integer, String> industryList) {
	this.industryList = industryList;
	}

	public Map<Integer, String> getOrgTypeList() {
	return orgTypeList;
	}

	public void setOrgTypeList(Map<Integer, String> orgTypeList) {
	this.orgTypeList = orgTypeList;
	}

	public File getCompanyIcon() {
	return companyIcon;
	}

	public void setCompanyIcon(File companyIcon) {
	this.companyIcon = companyIcon;
	}

	public String getCompanyIconContentType() {
	return companyIconContentType;
	}

	public void setCompanyIconContentType(String companyIconContentType) {
	this.companyIconContentType = companyIconContentType;
	}

	public String getCompanyIconFileName() {
	return companyIconFileName;
	}

	public void setCompanyIconFileName(String companyIconFileName) {
	this.companyIconFileName = companyIconFileName;
	}

	public String getCompanyName() {
	return companyName;
	}

	public void setCompanyName(String companyName) {
	this.companyName = companyName;
	}

	public String getCompanyType() {
	return companyType;
	}

	public void setCompanyType(String companyType) {
	this.companyType = companyType;
	}

	public String getIndustryParam() {
	return industryParam;
	}

	public void setIndustryParam(String industryParam) {
	this.industryParam = industryParam;
	}

	public String getCompanyRegAddress() {
	return companyRegAddress;
	}

	public void setCompanyRegAddress(String companyRegAddress) {
	this.companyRegAddress = companyRegAddress;
	}

	public String getCompanyRegCity() {
	return companyRegCity;
	}

	public void setCompanyRegCity(String companyRegCity) {
	this.companyRegCity = companyRegCity;
	}

	public String getCompanyRegPinCode() {
	return companyRegPinCode;
	}

	public void setCompanyRegPinCode(String companyRegPinCode) {
	this.companyRegPinCode = companyRegPinCode;
	}

	public String getCompanyRegContact1() {
	return companyRegContact1;
	}

	public void setCompanyRegContact1(String companyRegContact1) {
	this.companyRegContact1 = companyRegContact1;
	}

	public String getCompanyRegCountry() {
	return companyRegCountry;
	}

	public void setCompanyRegCountry(String companyRegCountry) {
	this.companyRegCountry = companyRegCountry;
	}

	public String getLocCompany() {
	return locCompany;
	}

	public void setLocCompany(String locCompany) {
	this.locCompany = locCompany;
	}

	public String getLocCompanyName() {
	return locCompanyName;
	}

	public void setLocCompanyName(String locCompanyName) {
	this.locCompanyName = locCompanyName;
	}

	public String getLocCompanyRegAddress() {
	return locCompanyRegAddress;
	}

	public void setLocCompanyRegAddress(String locCompanyRegAddress) {
	this.locCompanyRegAddress = locCompanyRegAddress;
	}

	public String getLocCompanyRegCountry() {
	return locCompanyRegCountry;
	}

	public void setLocCompanyRegCountry(String locCompanyRegCountry) {
	this.locCompanyRegCountry = locCompanyRegCountry;
	}

	public String getLocCompanyRegCity() {
	return locCompanyRegCity;
	}

	public void setLocCompanyRegCity(String locCompanyRegCity) {
	this.locCompanyRegCity = locCompanyRegCity;
	}

	public String getLocCompanyRegPinCode() {
	return locCompanyRegPinCode;
	}

	public void setLocCompanyRegPinCode(String locCompanyRegPinCode) {
	this.locCompanyRegPinCode = locCompanyRegPinCode;
	}

	public String getLocCompanyRegContact1() {
	return locCompanyRegContact1;
	}

	public void setLocCompanyRegContact1(String locCompanyRegContact1) {
	this.locCompanyRegContact1 = locCompanyRegContact1;
	}

	public String getLocCompanyRegContact2() {
	return locCompanyRegContact2;
	}

	public void setLocCompanyRegContact2(String locCompanyRegContact2) {
	this.locCompanyRegContact2 = locCompanyRegContact2;
	}

	public String getLocCompanyRegWeb() {
	return locCompanyRegWeb;
	}

	public void setLocCompanyRegWeb(String locCompanyRegWeb) {
	this.locCompanyRegWeb = locCompanyRegWeb;
	}

	public String getLocCompanyRegEmail() {
	return locCompanyRegEmail;
	}

	public void setLocCompanyRegEmail(String locCompanyRegEmail) {
	this.locCompanyRegEmail = locCompanyRegEmail;
	}

	public String getLocationCompany() {
	return locationCompany;
	}

	public void setLocationCompany(String locationCompany) {
	this.locationCompany = locationCompany;
	}

	public String getBranchAddress() {
	return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
	this.branchAddress = branchAddress;
	}

	public String getBranchLandmark() {
	return branchLandmark;
	}

	public void setBranchLandmark(String branchLandmark) {
	this.branchLandmark = branchLandmark;
	}

	public String getBranchCity() {
	return branchCity;
	}

	public void setBranchCity(String branchCity) {
	this.branchCity = branchCity;
	}

	public String getBranchPinCode() {
	return branchPinCode;
	}

	public void setBranchPinCode(String branchPinCode) {
	this.branchPinCode = branchPinCode;
	}

	public String getBranchFax() {
	return branchFax;
	}

	public void setBranchFax(String branchFax) {
	this.branchFax = branchFax;
	}

	public String getBranchContact1() {
	return branchContact1;
	}

	public void setBranchContact1(String branchContact1) {
	this.branchContact1 = branchContact1;
	}

	public String getBranchContact2() {
	return branchContact2;
	}

	public void setBranchContact2(String branchContact2) {
	this.branchContact2 = branchContact2;
	}

	public String getBranchWeb() {
	return branchWeb;
	}

	public void setBranchWeb(String branchWeb) {
	this.branchWeb = branchWeb;
	}

	public String getBranchEmail() {
	return branchEmail;
	}

	public void setBranchEmail(String branchEmail) {
	this.branchEmail = branchEmail;
	}

	public String getLevel3Registration() {
	return level3Registration;
	}

	public void setLevel3Registration(String level3Registration) {
	this.level3Registration = level3Registration;
	}

	public String getLevel4Address() {
	return level4Address;
	}

	public void setLevel4Address(String level4Address) {
	this.level4Address = level4Address;
	}

	public String getLevel4Landmark() {
	return level4Landmark;
	}

	public void setLevel4Landmark(String level4Landmark) {
	this.level4Landmark = level4Landmark;
	}

	public String getLevel4City() {
	return level4City;
	}

	public void setLevel4City(String level4City) {
	this.level4City = level4City;
	}

	public String getLevel4PinCode() {
	return level4PinCode;
	}

	public void setLevel4PinCode(String level4PinCode) {
	this.level4PinCode = level4PinCode;
	}

	public String getLevel4Fax() {
	return level4Fax;
	}

	public void setLevel4Fax(String level4Fax) {
	this.level4Fax = level4Fax;
	}

	public String getLevel4Contact1() {
	return level4Contact1;
	}

	public void setLevel4Contact1(String level4Contact1) {
	this.level4Contact1 = level4Contact1;
	}

	public String getLevel4Contact2() {
	return level4Contact2;
	}

	public void setLevel4Contact2(String level4Contact2) {
	this.level4Contact2 = level4Contact2;
	}

	public String getLevel4Web() {
	return level4Web;
	}

	public void setLevel4Web(String level4Web) {
	this.level4Web = level4Web;
	}

	public String getLevel4Email() {
	return level4Email;
	}

	public void setLevel4Email(String level4Email) {
	this.level4Email = level4Email;
	}

	public String getLevel4Registration() {
	return level4Registration;
	}

	public void setLevel4Registration(String level4Registration) {
	this.level4Registration = level4Registration;
	}

	public String getLevel5Address() {
	return level5Address;
	}

	public void setLevel5Address(String level5Address) {
	this.level5Address = level5Address;
	}

	public String getLevel5Landmark() {
	return level5Landmark;
	}

	public void setLevel5Landmark(String level5Landmark) {
	this.level5Landmark = level5Landmark;
	}

	public String getLevel5City() {
	return level5City;
	}

	public void setLevel5City(String level5City) {
	this.level5City = level5City;
	}

	public String getLevel5PinCode() {
	return level5PinCode;
	}

	public void setLevel5PinCode(String level5PinCode) {
	this.level5PinCode = level5PinCode;
	}

	public String getLevel5Fax() {
	return level5Fax;
	}

	public void setLevel5Fax(String level5Fax) {
	this.level5Fax = level5Fax;
	}

	public String getLevel5Contact1() {
	return level5Contact1;
	}

	public void setLevel5Contact1(String level5Contact1) {
	this.level5Contact1 = level5Contact1;
	}

	public String getLevel5Contact2() {
	return level5Contact2;
	}

	public void setLevel5Contact2(String level5Contact2) {
	this.level5Contact2 = level5Contact2;
	}

	public String getLevel5Web() {
	return level5Web;
	}

	public void setLevel5Web(String level5Web) {
	this.level5Web = level5Web;
	}

	public String getLevel5Email() {
	return level5Email;
	}

	public void setLevel5Email(String level5Email) {
	this.level5Email = level5Email;
	}



	public String getHeaderName() {
	return headerName;
	}


	public void setHeaderName(String headerName) {
	this.headerName = headerName;
	}


	public String getModifyPrvlgs() {
	return modifyPrvlgs;
	}


	public void setModifyPrvlgs(String modifyPrvlgs) {
	this.modifyPrvlgs = modifyPrvlgs;
	}


	public Integer getRows() {
	return rows;
	}


	public void setRows(Integer rows) {
	this.rows = rows;
	}


	public Integer getPage() {
	return page;
	}


	public void setPage(Integer page) {
	this.page = page;
	}


	public String getSord() {
	return sord;
	}


	public void setSord(String sord) {
	this.sord = sord;
	}


	public String getSidx() {
	return sidx;
	}


	public void setSidx(String sidx) {
	this.sidx = sidx;
	}


	public String getSearchField() {
	return searchField;
	}


	public void setSearchField(String searchField) {
	this.searchField = searchField;
	}


	public String getSearchString() {
	return searchString;
	}


	public void setSearchString(String searchString) {
	this.searchString = searchString;
	}


	public String getSearchOper() {
	return searchOper;
	}


	public void setSearchOper(String searchOper) {
	this.searchOper = searchOper;
	}


	public Integer getTotal() {
	return total;
	}


	public void setTotal(Integer total) {
	this.total = total;
	}


	public Integer getRecords() {
	return records;
	}


	public void setRecords(Integer records) {
	this.records = records;
	}


	public boolean isLoadonce() {
	return loadonce;
	}


	public void setLoadonce(boolean loadonce) {
	this.loadonce = loadonce;
	}


	public String getContentType() {
	return contentType;
	}


	public void setContentType(String contentType) {
	this.contentType = contentType;
	}


	public List<GridDataPropertyView> getLevel1ColmnNames() {
	return level1ColmnNames;
	}


	public void setLevel1ColmnNames(List<GridDataPropertyView> level1ColmnNames) {
	this.level1ColmnNames = level1ColmnNames;
	}


	public List<GridDataPropertyView> getLevel2ColmnNames() {
	return level2ColmnNames;
	}


	public void setLevel2ColmnNames(List<GridDataPropertyView> level2ColmnNames) {
	this.level2ColmnNames = level2ColmnNames;
	}


	public List<GridDataPropertyView> getLevel3ColmnNames() {
	return level3ColmnNames;
	}


	public void setLevel3ColmnNames(List<GridDataPropertyView> level3ColmnNames) {
	this.level3ColmnNames = level3ColmnNames;
	}


	public List<GridDataPropertyView> getLevel4ColmnNames() {
	return level4ColmnNames;
	}


	public void setLevel4ColmnNames(List<GridDataPropertyView> level4ColmnNames) {
	this.level4ColmnNames = level4ColmnNames;
	}


	public List<GridDataPropertyView> getLevel5ColmnNames() {
	return level5ColmnNames;
	}


	public void setLevel5ColmnNames(List<GridDataPropertyView> level5ColmnNames) {
	this.level5ColmnNames = level5ColmnNames;
	}


	public int getId() {
	return id;
	}


	public void setId(int id) {
	this.id = id;
	}


	public String getOper() {
	return oper;
	}


	public void setOper(String oper) {
	this.oper = oper;
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

	public String getHeaderName1() {
	return headerName1;
	}

	public void setHeaderName1(String headerName1) {
	this.headerName1 = headerName1;
	}

	public String getHeaderName2() {
	return headerName2;
	}

	public void setHeaderName2(String headerName2) {
	this.headerName2 = headerName2;
	}

	public String getHeaderName3() {
	return headerName3;
	}

	public void setHeaderName3(String headerName3) {
	this.headerName3 = headerName3;
	}

	public String getHeaderName4() {
	return headerName4;
	}

	public void setHeaderName4(String headerName4) {
	this.headerName4 = headerName4;
	}

	public String getMainHeaderName() {
	return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName) {
	this.mainHeaderName = mainHeaderName;
	}

	public Map<String, String> getConfiguration1() {
	return configuration1;
	}

	public void setConfiguration1(Map<String, String> configuration1) {
	this.configuration1 = configuration1;
	}


	public Map<String, String> getConfiguration3() {
	return configuration3;
	}

	public void setConfiguration3(Map<String, String> configuration3) {
	this.configuration3 = configuration3;
	}

	public String[] getDropDownStatus() {
	return dropDownStatus;
	}

	public void setDropDownStatus(String[] dropDownStatus) {
	this.dropDownStatus = dropDownStatus;
	}

	public boolean isIndustryParam1() {
	return industryParam1;
	}

	public void setIndustryParam1(boolean industryParam1) {
	this.industryParam1 = industryParam1;
	}

	public boolean isCompanyRegCountry1() {
	return companyRegCountry1;
	}

	public void setCompanyRegCountry1(boolean companyRegCountry1) {
	this.companyRegCountry1 = companyRegCountry1;
	}

	public boolean isCompanyType1() {
	return companyType1;
	}

	public void setCompanyType1(boolean companyType1) {
	this.companyType1 = companyType1;
	}

	public String getIndustryParamName() {
	return industryParamName;
	}

	public void setIndustryParamName(String industryParamName) {
	this.industryParamName = industryParamName;
	}

	public String getCompanyRegCountryname() {
	return companyRegCountryname;
	}

	public void setCompanyRegCountryname(String companyRegCountryname) {
	this.companyRegCountryname = companyRegCountryname;
	}

	public String getCompanyTypeName() {
	return companyTypeName;
	}

	public void setCompanyTypeName(String companyTypeName) {
	this.companyTypeName = companyTypeName;
	}

	public List<ConfigurationUtilBean> getConfigurationForLevel2() {
	return configurationForLevel2;
	}

	public void setConfigurationForLevel2(List<ConfigurationUtilBean> configurationForLevel2) {
	this.configurationForLevel2 = configurationForLevel2;
	}

	public boolean isOrganizationLocLevel2() {
	return organizationLocLevel2;
	}

	public void setOrganizationLocLevel2(boolean organizationLocLevel2) {
	this.organizationLocLevel2 = organizationLocLevel2;
	}

	public boolean isOrganizationRegComLevel2() {
	return organizationRegComLevel2;
	}

	public void setOrganizationRegComLevel2(boolean organizationRegComLevel2) {
	this.organizationRegComLevel2 = organizationRegComLevel2;
	}

	public String getOrganizationLocLevel2Name() {
	return organizationLocLevel2Name;
	}

	public void setOrganizationLocLevel2Name(String organizationLocLevel2Name) {
	this.organizationLocLevel2Name = organizationLocLevel2Name;
	}

	public String getOrganizationRegComLevel2Name() {
	return organizationRegComLevel2Name;
	}

	public void setOrganizationRegComLevel2Name(String organizationRegComLevel2Name) {
	this.organizationRegComLevel2Name = organizationRegComLevel2Name;
	}



	public List<ConfigurationUtilBean> getConfigurationForLevel3() {
	return configurationForLevel3;
	}


	public void setConfigurationForLevel3(
	List<ConfigurationUtilBean> configurationForLevel3) {
	this.configurationForLevel3 = configurationForLevel3;
	}


	public String getOrganizationLocLevel3Name() {
	return organizationLocLevel3Name;
	}


	public void setOrganizationLocLevel3Name(String organizationLocLevel3Name) {
	this.organizationLocLevel3Name = organizationLocLevel3Name;
	}


	

	public List<ConfigurationUtilBean> getConfigurationForLevel4() {
	    return configurationForLevel4;
	}


	public void setConfigurationForLevel4(
	List<ConfigurationUtilBean> configurationForLevel4) {
	    this.configurationForLevel4 = configurationForLevel4;
	}


	public String getOrganizationLocLevel4Name() {
	return organizationLocLevel4Name;
	}


	public void setOrganizationLocLevel4Name(String organizationLocLevel4Name) {
	this.organizationLocLevel4Name = organizationLocLevel4Name;
	}





	public List<ConfigurationUtilBean> getConfigurationForLevel5() {
	    return configurationForLevel5;
	}


	public void setConfigurationForLevel5(
	List<ConfigurationUtilBean> configurationForLevel5) {
	    this.configurationForLevel5 = configurationForLevel5;
	}


	public String getOrganizationLocLevel5Name() {
	return organizationLocLevel5Name;
	}


	public void setOrganizationLocLevel5Name(String organizationLocLevel5Name) {
	this.organizationLocLevel5Name = organizationLocLevel5Name;
	}


	public int getGlobalLevel() {
	return globalLevel;
	}


	public void setGlobalLevel(int globalLevel) {
	this.globalLevel = globalLevel;
	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
	this.request=request;
	
	}


	public String getIndustryParamkey() {
	return industryParamkey;
	}


	public void setIndustryParamkey(String industryParamkey) {
	this.industryParamkey = industryParamkey;
	}


	public String getCompanyRegCountrykey() {
	return companyRegCountrykey;
	}


	public void setCompanyRegCountrykey(String companyRegCountrykey) {
	this.companyRegCountrykey = companyRegCountrykey;
	}


	public String getCompanyTypekey() {
	return companyTypekey;
	}


	public void setCompanyTypekey(String companyTypekey) {
	this.companyTypekey = companyTypekey;
	}


	public String getOrganizationLocLevel2key() {
	return organizationLocLevel2key;
	}


	public void setOrganizationLocLevel2key(String organizationLocLevel2key) {
	this.organizationLocLevel2key = organizationLocLevel2key;
	}


	public String getOrganizationRegComLevel2key() {
	return organizationRegComLevel2key;
	}


	public void setOrganizationRegComLevel2key(String organizationRegComLevel2key) {
	this.organizationRegComLevel2key = organizationRegComLevel2key;
	}


	public String getOrganizationLocLevel3key() {
	return organizationLocLevel3key;
	}


	public void setOrganizationLocLevel3key(String organizationLocLevel3key) {
	this.organizationLocLevel3key = organizationLocLevel3key;
	}


	public String getOrganizationLocLevel4key() {
	return organizationLocLevel4key;
	}


	public void setOrganizationLocLevel4key(String organizationLocLevel4key) {
	this.organizationLocLevel4key = organizationLocLevel4key;
	}


	public String getOrganizationLocLevel5key() {
	return organizationLocLevel5key;
	}


	public void setOrganizationLocLevel5key(String organizationLocLevel5key) {
	this.organizationLocLevel5key = organizationLocLevel5key;
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
	return listconfiguration;
	}
	public void setListconfiguration(List<ConfigurationUtilBean> listconfiguration) {
	this.listconfiguration = listconfiguration;
	}
	public List<ConfigurationUtilBean> getListconfiguration1() {
	return listconfiguration1;
	}
	public void setListconfiguration1(List<ConfigurationUtilBean> listconfiguration1) {
	this.listconfiguration1 = listconfiguration1;
	}
	public List<ConfigurationUtilBean> getListconfiguration2() {
	return listconfiguration2;
	}
	public void setListconfiguration2(List<ConfigurationUtilBean> listconfiguration2) {
	this.listconfiguration2 = listconfiguration2;
	}
	public List<ConfigurationUtilBean> getListconfiguration3() {
	return listconfiguration3;
	}
	public void setListconfiguration3(List<ConfigurationUtilBean> listconfiguration3) {
	this.listconfiguration3 = listconfiguration3;
	}
	public List<ConfigurationUtilBean> getListconfiguration4() {
	return listconfiguration4;
	}
	public void setListconfiguration4(List<ConfigurationUtilBean> listconfiguration4) {
	this.listconfiguration4 = listconfiguration4;
	}
	public List<ConfigurationUtilBean> getListconfiguration5() {
	return listconfiguration5;
	}
	public void setListconfiguration5(List<ConfigurationUtilBean> listconfiguration5) {
	this.listconfiguration5 = listconfiguration5;
	}
	public List<Object> getLevel1DataObject() {
	return level1DataObject;
	}
	public void setLevel1DataObject(List<Object> level1DataObject) {
	this.level1DataObject = level1DataObject;
	}
	public File getOrgLogo() {
	return orgLogo;
	}
	public void setOrgLogo(File orgLogo) {
	this.orgLogo = orgLogo;
	}


	public String getOrgName() {
	return orgName;
	}


	public void setOrgName(String orgName) {
	this.orgName = orgName;
	}


	public String getOrgAddress() {
	return orgAddress;
	}


	public void setOrgAddress(String orgAddress) {
	this.orgAddress = orgAddress;
	}


	public String getOrgCity() {
	return orgCity;
	}


	public void setOrgCity(String orgCity) {
	this.orgCity = orgCity;
	}


	public String getOrgCountry() {
	return orgCountry;
	}


	public void setOrgCountry(String orgCountry) {
	this.orgCountry = orgCountry;
	}


	public String getOrgPin() {
	return orgPin;
	}


	public void setOrgPin(String orgPin) {
	this.orgPin = orgPin;
	}


	public String getOrgContactNo() {
	return orgContactNo;
	}


	public void setOrgContactNo(String orgContactNo) {
	this.orgContactNo = orgContactNo;
	}


	public String getOrgLogoName() {
	return orgLogoName;
	}


	public void setOrgLogoName(String orgLogoName) {
	this.orgLogoName = orgLogoName;
	}


	public String getOrgLogoContentType() {
	return orgLogoContentType;
	}


	public void setOrgLogoContentType(String orgLogoContentType) {
	this.orgLogoContentType = orgLogoContentType;
	}
	public String getCompanyEmail() {
	return companyEmail;
	}


	public void setCompanyEmail(String companyEmail) {
	this.companyEmail = companyEmail;
	}


	public void setOrgImgPath(String orgImgPath) {
		this.orgImgPath = orgImgPath;
	}


	public String getOrgImgPath() {
		return orgImgPath;
	}


	
}
