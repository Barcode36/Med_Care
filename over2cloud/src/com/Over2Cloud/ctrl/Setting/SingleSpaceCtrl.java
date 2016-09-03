package com.Over2Cloud.ctrl.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.BeanDomainName;
import com.Over2Cloud.BeanUtil.BlockUserBean;
import com.Over2Cloud.BeanUtil.ChunkAccountId;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.DistributedTableInfo;
import com.Over2Cloud.CommonClasses.GenerateUUID;
import com.Over2Cloud.CommonClasses.IPAddress;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.CommonClasses.RandomNumberGenerator;
import com.Over2Cloud.CommonClasses.TableInfo;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.modal.dao.imp.Setting.SingleSpaceImp;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
import com.Over2Cloud.modal.dao.imp.signup.BeanCountry;
import com.Over2Cloud.modal.dao.imp.signup.SendHttpSMS;
import com.Over2Cloud.modal.dao.imp.signup.signupImp;
import com.Over2Cloud.modal.db.commom.Smtp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class SingleSpaceCtrl extends ActionSupport implements ServletRequestAware{
	/**
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Hello!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * >>>>>>>>>>>>>>>>>>Using Single Space Configuration
	 * >>>>>>>>>>>>>>>>>>>>>>>>
	 * 
	 */
	static final Log log = LogFactory.getLog(SingleSpaceCtrl.class);
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String uName = (String) session.get("uName");
	String accountid = (String) session.get("accountid");
	String countryiso = (String) session.get("countryid");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	private List<ChunkAccountId> gridmodel = new ArrayList<ChunkAccountId>();
	private List<BlockUserBean> blockGridmodel = new ArrayList<BlockUserBean>();
	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;

	// sorting order - asc or desc
	private String sord;

	// get index row - i.e. user click to sort.
	private String sidx;

	// Search Field
	private String searchField;

	// The Search String
	private String searchString;

	// Limit the result when using local data, value form attribute rowTotal
	private Integer totalrows;

	// he Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper;

	// Your Total Pages
	private Integer total = 0;

	// All Records
	private Integer records = 0;

	private boolean loadonce = false;
	private String oper;
	private int id;
	private String spaceAddress;
	private String domainIpName;
	private String country;
	private List<BeanCountry> countrylist = new ArrayList<BeanCountry>();
	private List<BeanDomainName> domainNamelist = new ArrayList<BeanDomainName>();
	private String countryid;
    private String demoServerBefore;
    public HttpServletRequest request;
    
    
	public String clientsingleSpacereg() {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String AllSingleSapceConfiguration() {

		/*
		 * select
		 * UCM.user_chunk_slab_from,UCM.user_chunk_slab_to,CCSCT.appServerName
		 * from user_chunk_mapping as UCM inner join
		 * client_chunk_space_configuration_table as CCSCT on
		 * CCSCT.id=UCM.Souce_ip_Domain_address
		 */
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			// Getting All Connection
			List ConnectionListWithAccountid = new SingleSpaceImp()
					.AllSpaceConnection();
			if (ConnectionListWithAccountid != null
					&& ConnectionListWithAccountid.size() > 0) {
				for (Iterator iterator = ConnectionListWithAccountid.iterator(); iterator
						.hasNext();) {
					Object[] objectCol = (Object[]) iterator.next();
					if (objectCol[0] == null) {
						objectCol[0] = "0";
					}
					if (objectCol[1] == null) {
						objectCol[1] = "0";
					}
					if (objectCol[2] == null) {
						objectCol[2] = "NA";
					}
					List<String> colmName = new ArrayList<String>();
					colmName.add("accountid");
					colmName.add("insert_time");
					colmName.add("id");
					Map<String, String> wherClause = new HashMap<String, String>();
					wherClause
							.put("Souce_ip_Domain_address", "000.000.000.000");
					wherClause.put("country_iso_code", "NA");
					wherClause.put("isSpace", "Yes");
					String dbbname = "clouddb", ipAddress = null, username = null, paassword = null, port = null;
					ipAddress = objectCol[2].toString();
					username = objectCol[4].toString();
					paassword = objectCol[5].toString();
					port = objectCol[3].toString();
					AllConnection Conn = new ConnectionFactory(dbbname,
							ipAddress, username, paassword, port);
					AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
					SessionFactory sessfactNew = Ob1.getSession();
					@SuppressWarnings("unused")
					List Chunkoflist = new TableInfo()
							.viewAllDataEitherSelectOrAll(objectCol[0]
									.toString().trim()
									+ "_"
									+ objectCol[1].toString().trim()
									+ "_space_configuration", colmName,
									wherClause, sessfactNew);
					if (Chunkoflist != null && Chunkoflist.size() > 0) {
						for (Iterator iterator1 = Chunkoflist.iterator(); iterator1
								.hasNext();) {
							Object[] objectCol1 = (Object[]) iterator1.next();
							ChunkAccountId AccountList = new ChunkAccountId();
							AccountList.setAccountid(objectCol1[0].toString());
							AccountList.setDatetime(objectCol1[1].toString());
							AccountList.setId(Integer.parseInt(objectCol1[2]
									.toString()));
							AccountList.setChunkspace(objectCol[0].toString()
									.trim()
									+ "_" + objectCol[1].toString().trim());
							AccountList
									.setSpaceaddress(objectCol[2].toString());
							gridmodel.add(AccountList);
						}
					}
				}
				setGridmodel(gridmodel);
				/*
				 * Collections.sort(countryList,new BeanCountry());
				 * setCountryList(countryList);
				 */
			}

			/*
			 * records = cfc.getRecordStatus(ApplicationSetting.class); if
			 * (totalrows != null) { records = totalrows; } int to = (rows
			 * page); int from = to - rows; if (to > records) to = records;
			 * 
			 * appConfigList = cfc.ServicesviewData(to,from,searchField,
			 * searchString,searchOper,sord,sidx,"",0,ApplicationSetting.class);
			 * 
			 * //System.out.println("appConfigList:"+appConfigList.size());
			 * total = (int) Math.ceil((double) records / (double) rows);
			 */
		} catch (Exception e) {/*
								 * log.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> viewAppConfigurationGrid :::::  Error in Application Configuration"
								 * );return ERROR;
								 */
			e.printStackTrace();
		}
		return SUCCESS;

	}

	@SuppressWarnings("unchecked")
	public String allSingleSpaceActionAllot() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			if (getIdList().equals(null) || getIdList() == null
					|| getIdList().equals("") || getIdList() == "") {
				return SUCCESS;
			} 
			else if(getDemoServerBefore()!=null)
			{
				//in case of demo account configuration
				signupImp ob1 = new signupImp();
				List countrylist1 = ob1.getallcontry();
				if (countrylist1 != null && countrylist1.size() > 0) {
					for (Iterator iterator = countrylist1.iterator(); iterator
							.hasNext();) {
						Object[] objectCol = (Object[]) iterator.next();
						BeanCountry beancountry = new BeanCountry();
						if (objectCol[0] == null) {
							beancountry.setContryName("NA");
						} else {
							beancountry.setContryName(objectCol[0].toString());
						}
						if (objectCol[1] == null) {
							beancountry.setIso_code("NA");
						} else {
							beancountry.setIso_code((objectCol[1]).toString());
						}
						countrylist.add(beancountry);
					}
					Collections.sort(countrylist, new BeanCountry());
					setCountrylist(countrylist);
				}
				
				List<String> Spacelist = Arrays.asList(getIdList().split(","));
				for (String h : Spacelist) {
					ChunkAccountId AccountSingleList = new ChunkAccountId();
					AccountSingleList.setCombinekey(h.toString());
					AccountSingleList.setAccountid(h.toString());
					gridmodel.add(AccountSingleList);
				}
				return SUCCESS;
			}
			else {
				
				//for regular account work
				List<String> Spacelist = Arrays.asList(getIdList().split(","));
				for (String h : Spacelist) {
					List<String> PerSpaceList = Arrays.asList(h.split(":"));
					ChunkAccountId AccountSingleList = new ChunkAccountId();
					AccountSingleList.setCombinekey(h.toString());
					AccountSingleList.setId(Integer.parseInt(PerSpaceList
							.get(0)));
					AccountSingleList.setAccountid(PerSpaceList.get(3));
					AccountSingleList.setChunkspace(PerSpaceList.get(2));
					AccountSingleList.setSpaceaddress(PerSpaceList.get(1));
					gridmodel.add(AccountSingleList);
				}
				setGridmodel(gridmodel);

				// Add Single Space Server Add
				signupImp ob1 = new signupImp();
				@SuppressWarnings("unused")
				List dominName = ob1.getallSingleSpaceServer();
				List countrylist1 = ob1.getallcontry();
				if (countrylist1 != null && countrylist1.size() > 0) {
					for (Iterator iterator = countrylist1.iterator(); iterator
							.hasNext();) {
						Object[] objectCol = (Object[]) iterator.next();
						BeanCountry beancountry = new BeanCountry();
						if (objectCol[0] == null) {
							beancountry.setContryName("NA");
						} else {
							beancountry.setContryName(objectCol[0].toString());
						}
						if (objectCol[1] == null) {
							beancountry.setIso_code("NA");
						} else {
							beancountry.setIso_code((objectCol[1]).toString());
						}
						countrylist.add(beancountry);
					}
					Collections.sort(countrylist, new BeanCountry());
					setCountrylist(countrylist);
				}
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ERROR;
	}

	public String getAllCountrySingleDomainAndIp() {

		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			signupImp ob1 = new signupImp();
			List countryDominName = ob1
					.getallSingleSelectedDomaincontry(getCountryid());
			if (countryDominName != null && countryDominName.size() > 0) {
				for (Iterator iterator = countryDominName.iterator(); iterator
						.hasNext();) {
					Object[] objectCol = (Object[]) iterator.next();
					BeanDomainName beanDomainName = new BeanDomainName();
					if (objectCol[0] == null) {
						beanDomainName.setId("0");
					} else {
						beanDomainName.setId(objectCol[0].toString());
					}
					if (objectCol[1] == null) {
						beanDomainName.setDomainname("NA");
					} else {
						beanDomainName.setDomainname((objectCol[1]).toString());
					}
					domainNamelist.add(beanDomainName);
				}
				Collections.sort(domainNamelist, new BeanDomainName());
				setDomainNamelist(domainNamelist);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	@SuppressWarnings( { "unchecked", "static-access" })
	public String AddallSingleSpaceInDistributed() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			
			System.out.println("::::::::::::::::::::::::::::AddallSingleSpaceInDistributed::::::");
			int accoutnStatusFlag=0;
			LoginImp ob1=new LoginImp();
			List<String> Spaceallotlist = Arrays.asList(getSpaceAddress().split(","));
			for (String h : Spaceallotlist) {
				List<String> PerSpaceList = Arrays.asList(h.trim().split(":"));
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				// Which Value Update
				wherClause.put("Souce_ip_Domain_address", getDomainIpName());
				wherClause.put("country_iso_code", getCountry());
				// Condition For Update
				condtnBlock.put("id", Integer.parseInt(PerSpaceList.get(0)));
				condtnBlock.put("accountid", PerSpaceList.get(3));
				
				SessionFactory sessfactNew = null;
				List Connection = new signupImp().getconnectionString(Integer.parseInt(getDomainIpName()));
				SessionFactory sessfactClientDB = null;
				for (Object c : Connection) {
					Object[] objectCol = (Object[]) c;
					String dbbname = "clouddb", ipAddress = null, username = null, paassword = null, port = null;
					ipAddress = objectCol[0].toString();
					port = objectCol[1].toString();
					username = objectCol[2].toString();
					paassword = objectCol[3].toString();
					AllConnection Conn = new ConnectionFactory(dbbname,
							ipAddress, username, paassword, port);
					AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
					sessfactClientDB = Ob1.getSession();
				}
				
				//System.out.println("PerSpaceList.get(3)  "+PerSpaceList.get(3)+"getCountry() "+getCountry());
				List ChunkSpace=ob1.chunkDomainName(PerSpaceList.get(3), getCountry());
				if(ChunkSpace != null && !ChunkSpace.isEmpty() &&  ChunkSpace.size()>0)
		    	  {
					String dbbname = "clouddb", ipAddress = null, username = null, paassword = null, port = null;
					for (Object c : ChunkSpace) {
						Object[] objectCol = (Object[]) c;
						ipAddress=objectCol[2].toString();
						port=objectCol[3].toString();
						username=objectCol[4].toString();
						paassword=objectCol[5].toString();
						AllConnection Conn = new ConnectionFactory(dbbname,ipAddress, username, paassword, port);
						AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
						sessfactNew = Ob1.getSession();
					}
		    	  }
				//System.out.println(":::::::::sessfactNew  "+sessfactNew);
				String LoginId = null;
				String Pwd = null;
				String Mobileno = null;
				String orgname = null;
				String countrycode = null;
				// There is two thingh 1st is Space Connection Jaha per Db
				// Create Hona hai
				// 2nd is the Update Connection jaha per se Select ho raha hai
				/**
				 * Need chunk server session factory so we can update the chunk server client id details
				 * 
				 */
				boolean data = new TableInfo().updateTableColumn(PerSpaceList.get(2)+ "_space_configuration", wherClause, condtnBlock,sessfactNew);
				if (data) {
					String Name = "NA";
					String ContactEmailid = "NA";

					/**************************************************************************************************************
					 * * insert into 10004_clouddb.`basic` (`name`, `fname`,
					 * `lastname`) values('ankit1','singh','okk1'); * Account
					 * Id::: getDomainIpName() * Single Space Allot The All
					 * Client for Every Client. * With Data BAse and Dynamic
					 * Table Every Client to Every Client * *
					 * PerSpaceList.get(3)==id of 
					 **************************************************************************************************************/
					//System.out.println(" client info id  "+PerSpaceList.get(3));
					StringBuilder query=new StringBuilder("select accountFlag from client_info where id="+PerSpaceList.get(3));
					List tempData=signupImp.getDemoClientFalgValue(query.toString());
					int flagValueOfAccountype=0;
					if(tempData!=null)
					{
						for(Iterator it=tempData.iterator();it.hasNext();)
						{
							Object temp=(Object)it.next();
							String tempValue=temp.toString();
							if(tempValue.equalsIgnoreCase("1"))
								flagValueOfAccountype=1;
						}
					}
					/**
					 * flagValueOfAccountype==0 means a normal regular account request
					 * flagValueOfAccountype==1 means a normal account request from a demo account request
					 */
					
					if(flagValueOfAccountype==0)
					{
						//for regular account
						
						setUpClientDB(PerSpaceList.get(3)+ "_clouddb", sessfactClientDB);
						DistributedTableInfo tdi = new DistributedTableInfo();
						// Fatch All Data Record On List
						List AllDataOfParticularDbUser = new SingleSpaceImp()
								.AllRecordDataInformationCome(PerSpaceList.get(3));
						if (AllDataOfParticularDbUser != null
								&& AllDataOfParticularDbUser.size() > 0) {
							for (Iterator iterator = AllDataOfParticularDbUser
									.iterator(); iterator.hasNext();) {
								Object[] objectCol = (Object[]) iterator.next();
								List<InsertDataTable> Tablecolumesaaa = new ArrayList<InsertDataTable>();
								LoginId = objectCol[8].toString();
								Pwd = objectCol[9].toString();

								// Orgnization lavel Data Done
								InsertDataTable InsOb = new InsertDataTable();
								InsOb.setColName("companyIcon");
								InsOb.setDataName("NA");
								Tablecolumesaaa.add(InsOb);

								InsOb = new InsertDataTable();
								InsOb.setColName("companyName");
								InsOb.setDataName(objectCol[0].toString());
								Tablecolumesaaa.add(InsOb);
								orgname = objectCol[0].toString();
								InsOb = new InsertDataTable();
								InsOb.setColName("companyType");
								InsOb.setDataName(objectCol[1].toString());
								Tablecolumesaaa.add(InsOb);

								InsOb = new InsertDataTable();
								InsOb.setColName("industryParam");
								InsOb.setDataName(objectCol[2].toString());
								Tablecolumesaaa.add(InsOb);

								InsOb = new InsertDataTable();
								InsOb.setColName("companyRegAddress");
								InsOb.setDataName(objectCol[3].toString());
								Tablecolumesaaa.add(InsOb);

								InsOb = new InsertDataTable();
								InsOb.setColName("companyRegCity");
								InsOb.setDataName(objectCol[4].toString());
								Tablecolumesaaa.add(InsOb);

								InsOb = new InsertDataTable();
								InsOb.setColName("companyRegCountry");
								InsOb.setDataName(objectCol[5].toString());
								Tablecolumesaaa.add(InsOb);

								InsOb = new InsertDataTable();
								InsOb.setColName("companyRegPinCode");
								InsOb.setDataName(objectCol[6].toString());
								Tablecolumesaaa.add(InsOb);

								InsOb = new InsertDataTable();
								InsOb.setColName("companyRegContact1");
								InsOb.setDataName(objectCol[7].toString());
								Tablecolumesaaa.add(InsOb);

								InsOb = new InsertDataTable();
								InsOb.setColName("datetime");
								InsOb.setDataName(DateUtil.currentdatetime());
								Tablecolumesaaa.add(InsOb);
								tdi.insertDataInDistributedTable(PerSpaceList.get(3)+ "_clouddb", "organization_level1",Tablecolumesaaa, sessfactClientDB);
								Thread.sleep(300);
								// Product Data Inser with validity
								Tablecolumesaaa.clear();

								InsOb = new InsertDataTable();
								InsOb.setColName("productCategory");
								InsOb.setDataName(objectCol[12].toString());
								Tablecolumesaaa.add(InsOb);

								InsOb = new InsertDataTable();
								InsOb.setColName("productuser");
								InsOb.setDataName(objectCol[13].toString());
								Tablecolumesaaa.add(InsOb);

								InsOb = new InsertDataTable();
								InsOb.setColName("DateTime");
								InsOb.setDataName(DateUtil.currentdatetime());
								Tablecolumesaaa.add(InsOb);

								InsOb = new InsertDataTable();
								InsOb.setColName("validityFrom");
								InsOb.setDataName(objectCol[10].toString());
								Tablecolumesaaa.add(InsOb);

								InsOb = new InsertDataTable();
								InsOb.setColName("validityto");
								InsOb.setDataName(objectCol[11].toString());
								Tablecolumesaaa.add(InsOb);

								tdi.insertDataInDistributedTable(PerSpaceList.get(3)+ "_clouddb", "client_product",Tablecolumesaaa, sessfactClientDB);
								Thread.sleep(300);

								Tablecolumesaaa.clear();

								//Anoop, 6-8-2013
								//Start: Give all user rights to client login
								//String rights = ob1.getAllClientRights();
								String rights = ob1.getAllClientRights(objectCol[12].toString()+"Common#");
        	 			        System.out.println("SingleSpaceCtrl#rights:"+rights);
								//End:Give all user rights to client login
								
								// User Account Data Inserted
								// Done.................
								InsOb = new InsertDataTable();
								InsOb.setColName("mobileNo");
								InsOb.setDataName("NA");
								Tablecolumesaaa.add(InsOb);

								// Done>>>>>>>>>>>>>
								InsOb = new InsertDataTable();
								InsOb.setColName("name");
								InsOb.setDataName(objectCol[14].toString());
								Tablecolumesaaa.add(InsOb);

								// Done.....................
								InsOb = new InsertDataTable();
								InsOb.setColName("userID");
								InsOb.setDataName(objectCol[8].toString());
								Tablecolumesaaa.add(InsOb);

								// Done.................
								InsOb = new InsertDataTable();
								InsOb.setColName("linkVal");
								//InsOb.setDataName("NA");//Old, Edited by Anoop, 6-8-2013
								InsOb.setDataName((rights == null || rights.equalsIgnoreCase("") ? "NA" : rights));//New, Edited by Anoop, 6-8-2013
								Tablecolumesaaa.add(InsOb);

								// Done.............
								InsOb = new InsertDataTable();
								InsOb.setColName("sublinkVal");
								InsOb.setDataName("NA");
								Tablecolumesaaa.add(InsOb);

								// Done...........................
								InsOb = new InsertDataTable();
								InsOb.setColName("password");
								InsOb.setDataName(objectCol[9].toString());
								Tablecolumesaaa.add(InsOb);

								// Done...............
								InsOb = new InsertDataTable();
								InsOb.setColName("date");
								InsOb.setDataName(DateUtil.currentdatetime());
								Tablecolumesaaa.add(InsOb);

								// Done.....................
								InsOb = new InsertDataTable();
								InsOb.setColName("time");
								InsOb.setDataName(DateUtil.currentdatetime());
								Tablecolumesaaa.add(InsOb);

								// Done......................
								InsOb = new InsertDataTable();
								InsOb.setColName("userName");
								InsOb.setDataName("Admin");
								Tablecolumesaaa.add(InsOb);
								tdi.insertDataInDistributedTable(PerSpaceList.get(3)+ "_clouddb", "useraccount",Tablecolumesaaa, sessfactClientDB);
								Thread.sleep(300);
								Name = objectCol[14].toString().trim();
								ContactEmailid = objectCol[15].toString().trim();
								Mobileno = objectCol[16].toString().trim();
								countrycode = objectCol[17].toString().trim();
							}

						}
					}
					else
					{
						//from demo account to regular account
						/**
						 * @author Sandeep
						 * first get demo account DB server details based on these details get the DB session factory so we
						 * can run query on the data base and drop the same
						 * 
						 */
						query.delete(0, query.length());
						query.append("select rs.id from registation_sinup as rs inner join client_info as cp on rs.confirm_key=cp.confim_key  " +
								"and rs.accountid=cp.accountid and rs.uuid=cp.uid where cp.id="+PerSpaceList.get(3).trim());
						List demoClientInfo=signupImp.getDemoClientFalgValue(query.toString());
						int clientSignUpId=0;
						if(demoClientInfo!=null)
						{
							for(Iterator it=demoClientInfo.iterator();it.hasNext();)
							{
								Object temp=(Object)it.next();
								clientSignUpId=(Integer)temp;
							}
						}
						if(clientSignUpId!=0)
						{
							//move client demo account DB to real DB and delete the demo account
							//get client demo server session factory
							query.delete(0, query.length());
							query.append("select serverId from demo_client_space where clienID='"+clientSignUpId+"'");//demo server id
							List demoServerDetails=signupImp.getDemoClientFalgValue(query.toString());
							if(demoServerDetails!=null)
							{
								String clientChunkServerIDForDemo=null;
								
								for(Iterator it=demoServerDetails.iterator();it.hasNext();)
								{
									Object temp=(Object)it.next();
									clientChunkServerIDForDemo=temp.toString();
								}
								
								if(clientChunkServerIDForDemo!=null)
								{
									String dbbname=clientSignUpId+"_demo_clouddb";
									String ipAddress=null;
									String username=null;
									String paassword=null;
									String port=null;
									List singleSpceServer=ob1.GetClientspace(clientChunkServerIDForDemo);
									
									if(singleSpceServer!=null)
									{
							       		 for (Object c : singleSpceServer) {
							    				Object[] dataC = (Object[]) c;
							    				ipAddress=dataC[0].toString();
							    				username=dataC[2].toString();
							    				paassword=dataC[3].toString();
							    				port=dataC[1].toString();
							       		 }
							       		AllConnection Conn=new ConnectionFactory(dbbname,ipAddress,username,paassword,port);
								        AllConnectionEntry Ob1=Conn.GetAllCollectionwithArg();
								        SessionFactory demoSessFact=Ob1.getSession();//client demo server details
								        setUpClientDBFromDemoToRegular(dbbname,demoSessFact,PerSpaceList.get(3)+ "_clouddb");
								        
								        //delete demo account set up id from demo server mapping entry table
								        
								        List AllDataOfParticularDbUser = new SingleSpaceImp().AllRecordDataInformationCome(PerSpaceList.get(3));
										if (AllDataOfParticularDbUser != null&& AllDataOfParticularDbUser.size() > 0) {
											for (Iterator iterator = AllDataOfParticularDbUser.iterator(); iterator.hasNext();) {
												Object[] objectCol = (Object[]) iterator.next();
												LoginId = objectCol[8].toString();
												Pwd = objectCol[9].toString();
												Name = objectCol[14].toString().trim();
												ContactEmailid = objectCol[15].toString().trim();
												Mobileno = objectCol[16].toString().trim();
												countrycode = objectCol[17].toString().trim();
											}
										}
								        accoutnStatusFlag=1;
									}
									else
									{
										addActionError("Oops there is some problem in DB server!");
										return ERROR;
									}
								}
								else
								{
									addActionError("Oops demo server details not found, please contact development team!");
									return ERROR;
								}
							}
							else
							{
								addActionError("Oops demo server details not found, please contact development team!");
								return ERROR;
							}
						}
						else
						{
							addActionError("Oops demo account details not found, please contact development team!");
							return ERROR;
						}
						
					}


					/***********************************************************************************
					 * Concept implement The All User and Orgnization Data
					 * Submited By User * Space and related Product Intrested
					 * Also Copy of The User Space * Mail Go after deploy the
					 * all Client. *
					 ***********************************************************************************/
					Mailtest mt = new Mailtest();
					CommonforClass eventDao = new CommanOper();
					StringBuilder mailtext = new StringBuilder("");
					List<Smtp> Ob3 = (List<Smtp>) eventDao.cloudgetDropdownvalue("id", Smtp.class);
					mailtext.append("<font face ='TIMESROMAN' size='3'><b>Dear  "+ Name+ ",<br><b><br>Your account is activated. Please login below link to use your account"
									+ "<br>URL: www.over2cloud.com"+ "<br><b><br>Thanks</b><br>Team Over2cloud.com<br>"+ "</FONT>");
					
					
					/**
					 * **********************************FOR DEMO ACCOUNT WORK ONLY*************************
					 */
					if(accoutnStatusFlag==1)
					{
						//in case of demo account for user name password setting
						String accountID=countrycode+ "-"+ PerSpaceList.get(3);
						String encyploginID=Cryptography.encrypt(LoginId, DES_ENCRYPTION_KEY);
						String encypPwd=Cryptography.encrypt(Pwd, DES_ENCRYPTION_KEY);
						String ip = new IPAddress().getIPAddress();
						String UnqueMessageid = new GenerateUUID().UUID().toString();
						int uniqueid = new RandomNumberGenerator().getNDigitRandomNumber(8);
						String UrlMail = "http://" + ip+ request.getContextPath()+ "/linkverifyForDemoPwdUser?um=" + UnqueMessageid+ 
						"&id="+ uniqueid + "&tempLAAOAAIAAD=" + encyploginID+ "&tempAAPAAWAAD=" + encypPwd+"&tempAAAAACAAOAAUAANAANOAA="+accountID;
						
						mailtext.append("<br>"+UrlMail);
					}
					
					
					mailtext.append("<font face ='TIMESROMAN' size='2'>"+ "<b>Disclaimer:- </b> This is a system generated email. Please do not reply to this email."
									+ "*** This message is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged information. If you have received this message in error, please notify the sender immediately and delete this message from your system *** "
									+ "</FONT>");
					
					
					String Dreamsolsms = "Hi, " + Name + ", Org. Name: "+ orgname + " Mobile No. " + Mobileno+ ", Email ID: " + ContactEmailid+ " has been activated .";
					for (Smtp smMail : Ob3) {
						mt.confMailHTML(smMail.getServer(),smMail.getPort(),smMail.getEmailid(),smMail.getPwd(),ContactEmailid,
										"Congratulations !!!! Your Account is Activated "+ DateUtil.getCurrentDateIndianFormat(),mailtext.toString());
						// Mail Go to DREAMsOL PANNEL
						mt.confMailHTML(smMail.getServer(),smMail.getPort(),smMail.getEmailid(),smMail.getPwd(),
										"support@dreamsol.biz","Congratulations !!!! Activated Account Id : "+ countrycode+ "-"+ PerSpaceList.get(3)+ " "+ DateUtil.getCurrentDateIndianFormat(),Dreamsolsms);
					}
					// Dreamsol
					new SendHttpSMS().sendSMS("9250400311", Dreamsolsms);
					new SendHttpSMS().sendSMS("9250400314", Dreamsolsms);
					new SendHttpSMS().sendSMS("9250400315", Dreamsolsms);
					addActionMessage("Action Taken Successfully!!!");
				} else {
					log
							.info("Over2Cloud::>> Class:SingleSpaceCtrl >> Method::>> AddallSingleSpaceInDistributed ::::: Client Space Is not Allot The Specific Server"
									+ PerSpaceList.get(3));
					addActionError("Oops there is some problem!");
					return ERROR;
				}
			}
		} catch (Exception e) {
			addActionError("Oops there is some problem!");
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * EMTHOD WRITTEN BY SANDEEP FOR GERENRATING DYANANMIC DB OF RESPECTED CLIENT EITHER FOR DEMO ACCOUNT OR FOR REGULAR ACCOUNT
	 * @param dbName
	 * @param sessfactNew
	 */
	public void setUpClientDB(String dbName,SessionFactory sessfactNew)
	{
		try
		{
			DistributedTableInfo tdi = new DistributedTableInfo();
			tdi.createDataBasesInDistributedSpace(dbName, sessfactNew);
	
			/**
			 * **************GETTING THE ALL TABLES FROM THE configurationdb and based on getting all table create them inside client DB and insert default data in client DB***********
			 * dbDetals.append(hostName+","+userName+","+password+","+port);
			 */
			
			String dbDetals = new IPAddress().getDBDetails();
			String details[]=dbDetals.split(",");
			AllConnection Conn = new ConnectionFactory("configurationdb",details[0], details[1], details[2], details[3]);
			AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
			SessionFactory configurDBSession = Ob1.getSession();
			
			String query=new String("show tables");
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List alltableName=cbt.executeAllSelectQuery(query, configurDBSession);
			for(Iterator it=alltableName.iterator();it.hasNext();)
			{
				Object tempName=(Object)it.next();
				tdi.createTableInDistributedSpace(dbName, tempName.toString(), "configurationdb",tempName.toString(), sessfactNew);
				Thread.sleep(100);
				tdi.insertBulkData(dbName,tempName.toString(), "configurationdb",tempName.toString(), sessfactNew);
				Thread.sleep(200);
			}
		}
		catch(Exception e)
		{
			
		}
	}
	
	
	public void setUpClientDBFromDemoToRegular(String orgnalDbName,SessionFactory sessfactNew,String clientDemoDBName)
	{
		try
		{
			DistributedTableInfo tdi = new DistributedTableInfo();
			tdi.createDataBasesInDistributedSpace(clientDemoDBName, sessfactNew);
	
			/**
			 * **************GETTING THE ALL TABLES FROM THE configurationdb and based on getting all table create them inside client DB and insert default data in client DB***********
			 * dbDetals.append(hostName+","+userName+","+password+","+port);
			 */
			
			String query=new String("show tables");
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			List alltableName=cbt.executeAllSelectQuery(query, sessfactNew);
			for(Iterator it=alltableName.iterator();it.hasNext();)
			{
				Object tempName=(Object)it.next();
				tdi.createTableInDistributedSpace(clientDemoDBName, tempName.toString(), orgnalDbName,tempName.toString(), sessfactNew);
				Thread.sleep(100);
				tdi.insertBulkData(clientDemoDBName,tempName.toString(), orgnalDbName,tempName.toString(), sessfactNew);
				Thread.sleep(200);
			}
			tdi.deleteDemoClientDBAfterBackup(orgnalDbName, sessfactNew);
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Override
	public String execute() throws Exception {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return ERROR;
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String SingleUserAddBlockSpace() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}

			List GetAllAccountidInfo = new SingleSpaceImp()
					.SingleSpaceBlockUserInfo();
			if (GetAllAccountidInfo != null && GetAllAccountidInfo.size() > 0) {
				for (Iterator iterator = GetAllAccountidInfo.iterator(); iterator
						.hasNext();) {
					Object[] objectCol = (Object[]) iterator.next();
					BlockUserBean BUB = new BlockUserBean();
					BUB.setAccountid(Integer.parseInt(objectCol[0].toString()));
					BUB.setOrg_name(objectCol[2].toString());
					BUB.setUser_name(objectCol[1].toString());
					BUB.setCountry(objectCol[3].toString());
					BUB.setIsLive(objectCol[5].toString());
					List ChunkInfo = new LoginImp().chunkDomainName(
							objectCol[0].toString(), objectCol[4].toString());
					if (ChunkInfo != null && ChunkInfo.size() > 0) {
						for (Iterator iterator1 = ChunkInfo.iterator(); iterator1
								.hasNext();) {
							Object[] objectCol1 = (Object[]) iterator1.next();
							BUB.setSpacename(objectCol1[0].toString().trim()
									+ "_" + objectCol1[1].toString().trim());
							BUB.setConnectionName(objectCol1[2].toString()
									+ "*" + objectCol1[3].toString() + "*"
									+ objectCol1[4].toString() + "*"
									+ objectCol1[5].toString());
						}
					}
					blockGridmodel.add(BUB);
				}
				setBlockGridmodel(blockGridmodel);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return ERROR;
		}
		return SUCCESS;
	}

	public String allBlockClientAdd() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			if (getIdList().equals(null) || getIdList() == null
					|| getIdList().equals("") || getIdList() == "") {
				return SUCCESS;
			} else {
				List<String> Spacelist = Arrays.asList(getIdList().split(","));
				for (String h : Spacelist) {
					List<String> PerSpaceList = Arrays.asList(h.split(":"));
					BlockUserBean ob1 = new BlockUserBean();
					ob1.setAccountid(Integer.parseInt(PerSpaceList.get(0)
							.toString()));
					ob1.setConnectionName(PerSpaceList.get(1).toString());
					ob1.setSpacename(PerSpaceList.get(2).toString());
					ob1.setOrg_name(PerSpaceList.get(3).toString());
					ob1.setCombinekey(PerSpaceList.get(0).toString().trim()
							+ ":" + PerSpaceList.get(1).toString().trim() + ":"
							+ PerSpaceList.get(2).toString().trim() + ":"
							+ PerSpaceList.get(4).toString().trim());
					blockGridmodel.add(ob1);
				}
				setBlockGridmodel(blockGridmodel);
				return SUCCESS;
			}
		} catch (Exception e) {
		}
		return ERROR;
	}

	public String blockallSingleSpaceInDistributed() {
		try {

			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			List<String> blockSpaceallotlist = Arrays.asList(getSpaceAddress()
					.split(","));
			for (String h : blockSpaceallotlist) {

				List<String> PerSpaceList = Arrays.asList(h.trim().split(":"));
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				// Which Value Update
				if (PerSpaceList.get(3).toString().equals("BlockClient")) {
					wherClause.put("isactive", "B");
				}
				if (PerSpaceList.get(3).toString().equals("LiveClient")) {
					wherClause.put("isactive", "A");
				}
				// Condition For Update 10001:localhost:10001_10050
				condtnBlock.put("accountid", PerSpaceList.get(0));
				String connecionParam[] = PerSpaceList.get(1).toString()
						.replace("*", ",").split(",");
				String dbbname = "clouddb", ipAddress = null, username = null, paassword = null, port = null;
				ipAddress = connecionParam[0];
				port = connecionParam[1];
				username = connecionParam[2];
				paassword = connecionParam[3];
				AllConnection Conn = new ConnectionFactory(dbbname, ipAddress,
						username, paassword, port);
				AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
				SessionFactory sessfactNew = Ob1.getSession();
				boolean data = new TableInfo().updateTableColumn(PerSpaceList
						.get(2).toString()
						+ "_space_configuration", wherClause, condtnBlock,
						sessfactNew);
				if (data) {
					Map<String, Object> wherClause1 = new HashMap<String, Object>();
					Map<String, Object> condtnBlock1 = new HashMap<String, Object>();
					// Which Value Update
					if (PerSpaceList.get(3).toString().equals("BlockClient")) {
						wherClause1.put("isLive", "Block");
					}
					if (PerSpaceList.get(3).toString().equals("LiveClient")) {
						wherClause1.put("isLive", "Live");
					}
					// Condition For Update 10001:localhost:10001_10050
					condtnBlock1.put("id", PerSpaceList.get(0));
					boolean data1 = new TableInfo().updateTableColumn(
							"client_info", wherClause1, condtnBlock1,
							connectionSpace);
					if (data1) {
						Thread.sleep(200);
					}
				} else {
					log
							.info("Over2Cloud::>> Class:SingleSpaceCtrl >> Method::>> blockallSingleSpaceInDistributed ::::: Block Client Space Is not Allot The Specific Server"
									+ PerSpaceList.get(3));
					System.out.println("Client Not Make a Space");
					addActionError("Oops There is some problem!");
					return ERROR;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		addActionMessage("Action Taken Successfully!!!");
		return SUCCESS;
	}

	public List<ChunkAccountId> getGridmodel() {
		return gridmodel;
	}

	public void setGridmodel(List<ChunkAccountId> gridmodel) {
		this.gridmodel = gridmodel;
	}

	public String getUName() {
		return uName;
	}

	public void setUName(String name) {
		uName = name;
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

	public Integer getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
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

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<BlockUserBean> getBlockGridmodel() {
		return blockGridmodel;
	}

	public void setBlockGridmodel(List<BlockUserBean> blockGridmodel) {
		this.blockGridmodel = blockGridmodel;
	}

	public List<BeanCountry> getCountrylist() {
		return countrylist;
	}

	public void setCountrylist(List<BeanCountry> countrylist) {
		this.countrylist = countrylist;
	}

	private String idList;

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getCountryid() {
		return countryid;
	}

	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}

	public List<BeanDomainName> getDomainNamelist() {
		return domainNamelist;
	}

	public void setDomainNamelist(List<BeanDomainName> domainNamelist) {
		this.domainNamelist = domainNamelist;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSpaceAddress() {
		return spaceAddress;
	}

	public void setSpaceAddress(String spaceAddress) {
		this.spaceAddress = spaceAddress;
	}

	public String getDomainIpName() {
		return domainIpName;
	}

	public void setDomainIpName(String domainIpName) {
		this.domainIpName = domainIpName;
	}

	public String getDemoServerBefore() {
		return demoServerBefore;
	}

	public void setDemoServerBefore(String demoServerBefore) {
		this.demoServerBefore = demoServerBefore;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
}
