package com.Over2Cloud.ctrl.Setting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.BeanDomainName;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.BeanUtil.TableColumes;
import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.TableInfo;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.modal.dao.imp.Setting.SettingDAO;
import com.Over2Cloud.modal.dao.imp.Setting.SingleSpaceImp;
import com.Over2Cloud.modal.dao.imp.signup.BeanCountry;
import com.Over2Cloud.modal.dao.imp.signup.signupImp;
import com.Over2Cloud.modal.db.Setting.ApplicationSetting;
import com.Over2Cloud.modal.db.Setting.ChunkClientDb;
import com.Over2Cloud.modal.db.Setting.ClientDbServer;
import com.Over2Cloud.modal.db.Setting.UserChunkMapping;
import com.Over2Cloud.modal.db.commom.Smtp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SpaceCtrlSetting extends ActionSupport {

	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(SpaceCtrlSetting.class);
	private List<BeanCountry> countryList = new ArrayList<BeanCountry>();
	private String slabfromAccount;
	private String slabtoAccount;
	private String domainIpName;
	private String country;
	Map session = ActionContext.getContext().getSession();
	String uName = (String) session.get("uName");
	String accountid = (String) session.get("accountid");
	String countryiso = (String) session.get("countryid");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	private List<ApplicationSetting> appConfigList = null;
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
	private String editUrl;
	private String deleteUrl;
	private String app_name;
	private String app_code;
	private String iso_country;
	private List<ChunkClientDb> cfcList = null;
	private String dbServerIp_name;
	private String datetime;
	private String isoCountrycode;
	private String status;
	private String mailServer;
	private String mailport;
	private String emailid;
	private String mailpwd;
	private String dbServerip;
	private String dbServerport;
	private String dbusername;
	private String dbpassword;
	private String serverStatus;
	private String countryid;
	private String dbname;
	private Smtp smtpDetails = null;
	private List<BeanCountry> countrylist = new ArrayList<BeanCountry>();
	private List<BeanDomainName> domainNamelist = new ArrayList<BeanDomainName>();
	private String appname;
	private String appcode;

	public String addSpaceConfiguration() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			CommonforClass eventDao = new CommanOper();
			UserChunkMapping ob1 = new UserChunkMapping("NA", DateUtil
					.currentdatetime(), getDomainIpName(), "L", getCountry(),
					getSlabfromAccount(), getSlabtoAccount());
			boolean status = eventDao.addDetails(ob1);
			if (status) {
				log
						.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> addSpaceConfiguration ::::: Sucessfully Add Space Configuration");
				setDomainIpName(getDomainIpName().replace(".", "$"));
				// Getting Space Of Server Configuration Space Connection
				ChunkClientDb chunkOb = new SettingDAO()
						.GettingDynamicConnection(Integer
								.parseInt(getDomainIpName()));

				// Create Dynamic Table in Dynamic Chunk client wise.
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				TableColumes tablecolumes = new TableColumes();

				// Columes field 1
				tablecolumes.setColumnname("accountid");
				tablecolumes.setDatatype("varchar(120)");
				tablecolumes.setConstraint("default NULL");
				Tablecolumesaaa.add(tablecolumes);

				// Columes field 2
				tablecolumes = new TableColumes();
				tablecolumes.setColumnname("Souce_ip_Domain_address");
				tablecolumes.setDatatype("varchar(20)");
				tablecolumes.setConstraint("default NULL");
				Tablecolumesaaa.add(tablecolumes);

				// Columes field 3
				tablecolumes = new TableColumes();
				tablecolumes.setColumnname("insert_time");
				tablecolumes.setDatatype("varchar(50)");
				tablecolumes.setConstraint("default NULL");
				Tablecolumesaaa.add(tablecolumes);

				// Columes field 4
				tablecolumes = new TableColumes();
				tablecolumes.setColumnname("country_iso_code");
				tablecolumes.setDatatype("varchar(5)");
				tablecolumes.setConstraint("default NULL");
				Tablecolumesaaa.add(tablecolumes);

				// Columes field 5
				tablecolumes = new TableColumes();
				tablecolumes.setColumnname("isactive");
				tablecolumes.setDatatype("varchar(10)");
				tablecolumes.setConstraint("default NULL");
				Tablecolumesaaa.add(tablecolumes);

				tablecolumes = new TableColumes();
				tablecolumes.setColumnname("isSpace");
				tablecolumes.setDatatype("varchar(10)");
				tablecolumes.setConstraint("default NULL");

				Tablecolumesaaa.add(tablecolumes);

				String dbbname = "clouddb", ipAddress = null, username = null, paassword = null, port = null;
				ipAddress = chunkOb.getDbServerIp_name();
				port = chunkOb.getDbserverport();
				username = chunkOb.getDbusername();
				paassword = chunkOb.getDbpassword();
				AllConnection Conn = new ConnectionFactory(dbbname, ipAddress,
						username, paassword, port);
				AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
				SessionFactory sessfactNew = Ob1.getSession();

				if (new TableInfo().createTable(getSlabfromAccount() + "_"
						+ getSlabtoAccount() + "_space_configuration",
						Tablecolumesaaa, 1, sessfactNew)) {
					for (int i = Integer.parseInt(getSlabfromAccount().trim()); i <= Integer
							.parseInt(getSlabtoAccount().trim()); i++) {
						List<InsertDataTable> InsertList = new ArrayList<InsertDataTable>();

						InsertDataTable InsOb = new InsertDataTable();
						InsOb.setColName("accountid");
						InsOb.setDataName(i);
						InsertList.add(InsOb);

						InsOb = new InsertDataTable();
						InsOb.setColName("Souce_ip_Domain_address");
						InsOb.setDataName("000.000.000.000");
						InsertList.add(InsOb);

						InsOb = new InsertDataTable();
						InsOb.setColName("insert_time");
						InsOb.setDataName(DateUtil.currentdatetime());
						InsertList.add(InsOb);

						InsOb = new InsertDataTable();
						InsOb.setColName("country_iso_code");
						InsOb.setDataName("NA");
						InsertList.add(InsOb);

						InsOb = new InsertDataTable();
						InsOb.setColName("isactive");
						InsOb.setDataName("A");
						InsertList.add(InsOb);

						// Check the Client Info Record That Client Id Exist Or
						// Not
						if (new SingleSpaceImp().getspaceid(i)) {
							InsOb = new InsertDataTable();
							InsOb.setColName("isSpace");
							InsOb.setDataName("Yes");
							InsertList.add(InsOb);
						} else {
							InsOb = new InsertDataTable();
							InsOb.setColName("isSpace");
							InsOb.setDataName("No");
							InsertList.add(InsOb);
						}
						new TableInfo().insertIntoTable(getSlabfromAccount()
								+ "_" + getSlabtoAccount()
								+ "_space_configuration", InsertList,
								sessfactNew);
						Thread.sleep(200);
					}
					addActionMessage("Space Configuration Setting Save Sucessfully");
					return SUCCESS;
				} else {
					addActionError(" Error Is Space Configuration  Add Space");
					return ERROR;

				}
			} else {
				log
						.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> addSpaceConfiguration :::::  Error in Space Configuration");
				addActionError(" Error Is Space Configuration  Add Space");
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ERROR;
	}

	@SuppressWarnings("unchecked")
	public String clientDbInfoAction() {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
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
				countryList.add(beancountry);
			}
			Collections.sort(countryList, new BeanCountry());
			setCountryList(countryList);
		}
		return SUCCESS;

	}

	public String chunkDbInfoAction() {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
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
				countryList.add(beancountry);
			}
			Collections.sort(countryList, new BeanCountry());
			setCountryList(countryList);
		}
		return SUCCESS;
	}

	public String AddAllClientServer() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			CommonforClass eventDao = new CommanOper();
			ClientDbServer ob1 = new ClientDbServer(DateUtil.currentdatetime(),
					getDbServerip(), getDbpassword(), getDbusername(),
					getCountry(), getServerStatus(), getDbServerport());
			if (eventDao.addDetails(ob1)) {
				log
						.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> AddAllClientServer ::::: Sucessfully Add Client Data Base Space Configuration");
				addActionMessage("Add Client Data Base Information Save Sucessfully");
				return SUCCESS;
			} else {
				log
						.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> AppApplicationForAll :::::  Error in Client Data Base Space Configuration");
				addActionMessage(" Error Is mail Configuration  Add mail");
				return ERROR;
			}

		} catch (Exception e) {
			log
					.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> AppApplicationForAll :::::  Error in Client Data Base Space Configuration");
			return ERROR;
		}
	}

	public String AddAllChunkServer() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			CommonforClass eventDao = new CommanOper();
			ChunkClientDb ob1 = new ChunkClientDb(DateUtil.currentdatetime(),
					getDbServerip(), getDbpassword(), getDbusername(),
					getCountry(), getServerStatus(), getDbServerport(),
					getDbname());
			boolean statuc = eventDao.addDetails(ob1);

			if (statuc) {
				/*
				 * This Concept Is Implement the Version 2 It take the Time.
				 * This Concept Implemement the Concept The stattic Space
				 * Alocation Manually. This Concept Active By Name Of The
				 * Servewr And Connection File Already Make Only these
				 * Connection Use The Dynamic All The User Of the Server
				 * 
				 * 
				 * Dynamic Create The Connection and Data Base May Be These This
				 * is implement the Spring technology Hibernet templet Method
				 * 
				 * 
				 * System.out.println("Server Status:::"+getServerStatus());
				 * System
				 * .out.println("Server Ip: Like :: Local Host"+getDbServerip
				 * ());System.out.println("Server Port Data Is Connected::"+
				 * getDbServerport());
				 * 
				 * System.out.println("Connection Make For Dynamic User");
				 * 
				 * // CREATE DATABASE DB name
				 * System.out.println("Db Name::::"+getDbname());
				 * 
				 * //GRANT ALL PRIVILEGES ON. TO username @'%' IDENTIFIED BY
				 * 'password';
				 * System.out.println("Db User Name::::"+getDbusername());
				 * System.out.println("DB Password::::"+getDbpassword());
				 * //Dynamic Data Base Connection Create DB
				 */

				log
						.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> AddAllChunkServer ::::: Sucessfully Add Client Chunk Space Configuration");
				addActionMessage("Add Client Data Base Information Save Sucessfully");
				return SUCCESS;
			} else {
				log
						.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> AddAllChunkServer :::::  Error in Client Chunk Space Configuration");
				addActionMessage(" Error Is mail Configuration  Add mail");
				return ERROR;
			}

		} catch (Exception e) {
			log
					.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> AddAllChunkServer :::::  Error in Client Chunk Space Configuration");
			return ERROR;
		}
	}

	@SuppressWarnings("unchecked")
	public String serverconfigSpaceLoad() {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
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
				countryList.add(beancountry);
			}
			Collections.sort(countryList, new BeanCountry());
			setCountryList(countryList);
		}
		return SUCCESS;
	}

	public String mailconfigload() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			SettingDAO DAO = new SettingDAO();
			smtpDetails = DAO.objectSMTPDetails();
			setEmailid(smtpDetails.getEmailid());
			setMailServer(smtpDetails.getServer());
			setMailport(smtpDetails.getPort());
			setMailpwd(smtpDetails.getPwd());
			return SUCCESS;
		} catch (Exception e) {
			log
					.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> mailconfigload :::::  Error in Mail Configuration Lode In SMTP DB");
			return ERROR;
		}
	}

	public String addMailconfiguration() {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		try {
			SettingDAO DAO = new SettingDAO();
			CommonforClass eventDao = new CommanOper();
			String user = session.get("uName").toString();
			if (DAO.ChektheSMTPDetails()) {
				smtpDetails = DAO.objectSMTPDetails();
				smtpDetails.setEmailid(getEmailid());
				smtpDetails.setPort(getMailport());
				smtpDetails.setPwd(getMailpwd());
				smtpDetails.setServer(getMailServer());
				smtpDetails.setCreateat(user);
				smtpDetails.setCreateon_date(DateUtil.currentdatetime());
				if (eventDao.UpdateDetails(smtpDetails)) {
					addActionMessage("Mail configuration updated!");
					return SUCCESS;
				} else {
					return ERROR;
				}

			} else {
				Smtp ob1 = new Smtp(user, DateUtil.currentdatetime(),
						getEmailid(), getMailport(), getMailpwd(),
						getMailServer(), "NA");
				if (eventDao.addDetails(ob1)) {
					addActionMessage("Mail configuration added!");
					log
							.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> addMailconfiguration ::::: Sucessfully Add Mail Configuration");
					addActionMessage("Space Configuration Setting Save Sucessfully");
					return SUCCESS;

				} else {
					log
							.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> addMailconfiguration :::::  Error in Mail Configuration");
					addActionMessage(" Error Is mail Configuration  Add mail");
					return ERROR;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ERROR;
	}

	public String addApplicationcontact() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
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
			return SUCCESS;
		} catch (Exception e) {
			log
					.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> mailconfigload :::::  Error in Mail Configuration Lode In SMTP DB");
			return ERROR;
		}
	}

	public String AppApplicationForAll() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			CommonforClass eventDao = new CommanOper();
			ApplicationSetting ob1 = new ApplicationSetting(getAppcode(),
					DateUtil.currentdatetime(), getAppname(), DateUtil
							.currentdatetime(), getCountry());
			if (eventDao.addDetails(ob1)) {
				log
						.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> AppApplicationForAll ::::: Sucessfully Add Application Configuration");
				addActionMessage("Space Configuration Setting Save Sucessfully");
				return SUCCESS;

			} else {
				log
						.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> AppApplicationForAll :::::  Error in Application Configuration");
				addActionMessage("Oops there is some error!");
				return SUCCESS;
			}

		} catch (Exception e) {
			log
					.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> AppApplicationForAll :::::  Error in Application Configuration");
			return ERROR;
		}
	}

	public String getAllCountryDomainAndIp() {

		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			signupImp ob1 = new signupImp();
			List countryDominName = ob1
					.getallSelectedDomaincontry(getCountryid());
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
			log
					.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> AppApplicationForAll :::::  Error in Application Configuration");
			return ERROR;
		}

		return SUCCESS;
	}

	/*
	 * 
	 * Anoop Mearg>>>>>>
	 */

	@SuppressWarnings("unchecked")
	public String modifyAppRegistrationGrid() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}

			CommonforClass cfc = new CommanOper();
			if (oper.equalsIgnoreCase("edit")) {
				ApplicationSetting as = new ApplicationSetting();
				as.setId(id);
				as.setApp_name(app_name);
				as.setApp_code(app_code);
				as.setIso_country(iso_country);
				as.setApp_updatetime(DateUtil.currentdatetime());
				cfc.UpdateDetails(as);
			} else if (oper.equalsIgnoreCase("del")) {
				ApplicationSetting as = new ApplicationSetting();
				as.setId(id);
				cfc.DeleteRecord(as, ApplicationSetting.class);
			}
		} catch (Exception e) {
			log
					.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> viewAppConfigurationGrid :::::  Error in Application Configuration");
			addActionMessage(e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String viewCSCGrid() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}

			// ChunkClientDb
			CommonforClass cfc = new CommanOper();
			records = cfc.getRecordStatus(ChunkClientDb.class);

			if (totalrows != null) {
				records = totalrows;
			}
			int to = (rows * page);
			int from = to - rows;
			if (to > records)
				to = records;

			cfcList = cfc.ServicesviewData(to, from, searchField, searchString,
					searchOper, sord, sidx, "", 0, ChunkClientDb.class);

			// System.out.println("cfcList:"+cfcList.size());
			total = (int) Math.ceil((double) records / (double) rows);

		} catch (Exception e) {
			log
					.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> viewAppConfigurationGrid :::::  Error in Application Configuration");
			addActionMessage(e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String modifyCSCGrid() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}

			CommonforClass cfc = new CommanOper();
			if (oper.equalsIgnoreCase("edit")) {

				ChunkClientDb cc = new ChunkClientDb();
				cc.setId(id);
				cc.setDbServerIp_name(dbServerIp_name);
				cc.setDbserverport(dbServerport);
				cc.setDbname(dbname);
				cc.setDbusername(dbusername);
				cc.setDbpassword(dbpassword);
				cc.setDatetime(DateUtil.currentdatetime());
				cc.setIsoCountrycode(isoCountrycode);
				cc.setStatus(status);

				cfc.UpdateDetails(cc);
			} else if (oper.equalsIgnoreCase("del")) {
				ChunkClientDb as = new ChunkClientDb();
				as.setId(id);
				cfc.DeleteRecord(as, ChunkClientDb.class);
			}

		} catch (Exception e) {
			log
					.info("Over2Cloud::>> Class:SpaceCtrlSetting >> Method::>> viewAppConfigurationGrid :::::  Error in Application Configuration");
			addActionMessage(e.getMessage());
			return ERROR;
		}
		return SUCCESS;
	}

	public String getCountryiso() {
		return countryiso;
	}

	public void setCountryiso(String countryiso) {
		this.countryiso = countryiso;
	}

	public List<ApplicationSetting> getAppConfigList() {
		return appConfigList;
	}

	public void setAppConfigList(List<ApplicationSetting> appConfigList) {
		this.appConfigList = appConfigList;
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

	public String getEditUrl() {
		return editUrl;
	}

	public void setEditUrl(String editUrl) {
		this.editUrl = editUrl;
	}

	public String getDeleteUrl() {
		return deleteUrl;
	}

	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public String getApp_code() {
		return app_code;
	}

	public void setApp_code(String app_code) {
		this.app_code = app_code;
	}

	public String getIso_country() {
		return iso_country;
	}

	public void setIso_country(String iso_country) {
		this.iso_country = iso_country;
	}

	public List<ChunkClientDb> getCfcList() {
		return cfcList;
	}

	public void setCfcList(List<ChunkClientDb> cfcList) {
		this.cfcList = cfcList;
	}

	public String getDbServerIp_name() {
		return dbServerIp_name;
	}

	public void setDbServerIp_name(String dbServerIp_name) {
		this.dbServerIp_name = dbServerIp_name;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getIsoCountrycode() {
		return isoCountrycode;
	}

	public void setIsoCountrycode(String isoCountrycode) {
		this.isoCountrycode = isoCountrycode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public String getSlabfromAccount() {
		return slabfromAccount;
	}

	public void setSlabfromAccount(String slabfromAccount) {
		this.slabfromAccount = slabfromAccount;
	}

	public String getSlabtoAccount() {
		return slabtoAccount;
	}

	public void setSlabtoAccount(String slabtoAccount) {
		this.slabtoAccount = slabtoAccount;
	}

	public String getDomainIpName() {
		return domainIpName;
	}

	public void setDomainIpName(String domainIpName) {
		this.domainIpName = domainIpName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<BeanCountry> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<BeanCountry> countryList) {
		this.countryList = countryList;
	}

	public String getDbServerip() {
		return dbServerip;
	}

	public void setDbServerip(String dbServerip) {
		this.dbServerip = dbServerip;
	}

	public String getDbServerport() {
		return dbServerport;
	}

	public void setDbServerport(String dbServerport) {
		this.dbServerport = dbServerport;
	}

	public String getDbusername() {
		return dbusername;
	}

	public void setDbusername(String dbusername) {
		this.dbusername = dbusername;
	}

	public String getDbpassword() {
		return dbpassword;
	}

	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}

	public String getServerStatus() {
		return serverStatus;
	}

	public void setServerStatus(String serverStatus) {
		this.serverStatus = serverStatus;
	}

	public String getCountryid() {
		return countryid;
	}

	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getMailServer() {
		return mailServer;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}

	public String getMailport() {
		return mailport;
	}

	public void setMailport(String mailport) {
		this.mailport = mailport;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getMailpwd() {
		return mailpwd;
	}

	public void setMailpwd(String mailpwd) {
		this.mailpwd = mailpwd;
	}

	public Smtp getSmtpDetails() {
		return smtpDetails;
	}

	public void setSmtpDetails(Smtp smtpDetails) {
		this.smtpDetails = smtpDetails;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getAppcode() {
		return appcode;
	}

	public void setAppcode(String appcode) {
		this.appcode = appcode;
	}

	public List<BeanDomainName> getDomainNamelist() {
		return domainNamelist;
	}

	public void setDomainNamelist(List<BeanDomainName> domainNamelist) {
		this.domainNamelist = domainNamelist;
	}

	public List<BeanCountry> getCountrylist() {
		return countrylist;
	}

	public void setCountrylist(List<BeanCountry> countrylist) {
		this.countrylist = countrylist;
	}
}
