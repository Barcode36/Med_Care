package com.Over2Cloud.ctrl.Setting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.AppsList;
import com.Over2Cloud.BeanUtil.BeanDomainName;
import com.Over2Cloud.BeanUtil.Industrycode;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ObjectFactory;
import com.Over2Cloud.CommonClasses.TableInfo;
import com.Over2Cloud.CommonInterface.Addmethod;
import com.Over2Cloud.CommonInterface.commanOperation;
import com.Over2Cloud.modal.dao.imp.Setting.PackImplementation;
import com.Over2Cloud.modal.dao.imp.signup.BeanCountry;
import com.Over2Cloud.modal.dao.imp.signup.signupImp;
import com.Over2Cloud.modal.db.Setting.PackConfiguration;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PcakCtrl extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5923225816392483283L;
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String uName = (String) session.get("uName");
	String accountid = (String) session.get("accountid");
	String countryiso = (String) session.get("countryid");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	private List<PackConfiguration> gridModel;
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
	private List<BeanCountry> countrylist = new ArrayList<BeanCountry>();
	private String country;
	private String applicationName;
	private String timeperiod;
	private int timein;
	private int appusercounter;
	private String currency;
	private double cost;
	private String offerFrom;
	private String offerTo;
	private String countryid;
	private List<AppsList> apps = new ArrayList<AppsList>();

	@Override
	public String execute() throws Exception {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		return SUCCESS;
	}

	public String GetConfigurationpack() {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getAllApplication() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			PackImplementation ob1 = new PackImplementation();
			List AppslistName = ob1.GetAllAppslist(getCountryid());
			if (AppslistName != null && AppslistName.size() > 0) {
				for (Iterator iterator = AppslistName.iterator(); iterator
						.hasNext();) {
					Object[] objectCol = (Object[]) iterator.next();
					AppsList beanappsName = new AppsList();
					if (objectCol[0] == null) {
						beanappsName.setApplicationName("NA");
					} else {
						beanappsName
								.setApplicationName(objectCol[0].toString());
						if (objectCol[1] == null) {
							beanappsName.setAppsCode("NA");
						} else {
							beanappsName.setAppsCode((objectCol[1]).toString());
						}
						apps.add(beanappsName);
					}
					Collections.sort(apps, new AppsList());
					setApps(apps);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String AddPackConfigurationApps() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			List<InsertDataTable> InsertList = new ArrayList<InsertDataTable>();
			InsertDataTable InsOb = new InsertDataTable();
			InsOb.setColName("countryIsoCode");
			InsOb.setDataName(getCountry());
			InsertList.add(InsOb);

			InsOb = new InsertDataTable();
			InsOb.setColName("applicationcode");
			InsOb.setDataName(getApplicationName());
			InsertList.add(InsOb);

			InsOb = new InsertDataTable();
			InsOb.setColName("timeperiod");
			InsOb.setDataName(getTimeperiod());
			InsertList.add(InsOb);

			InsOb = new InsertDataTable();
			InsOb.setColName("timein");
			InsOb.setDataName(getTimein());
			InsertList.add(InsOb);

			InsOb = new InsertDataTable();
			InsOb.setColName("usercounter");
			InsOb.setDataName(getAppusercounter());
			InsertList.add(InsOb);

			InsOb = new InsertDataTable();
			InsOb.setColName("currency");
			InsOb.setDataName(getCurrency());
			InsertList.add(InsOb);

			InsOb = new InsertDataTable();
			InsOb.setColName("cost");
			InsOb.setDataName(getCost());
			InsertList.add(InsOb);

			InsOb = new InsertDataTable();
			InsOb.setColName("offerFrom");
			String Offerfrom[] = getOfferFrom().split("-");
			InsOb.setDataName(Offerfrom[2] + "-" + Offerfrom[1] + "-"
					+ Offerfrom[0]);
			InsertList.add(InsOb);

			InsOb = new InsertDataTable();
			InsOb.setColName("offerTo");
			String Offerto[] = getOfferTo().split("-");
			InsOb.setDataName(Offerto[2] + "-" + Offerto[1] + "-" + Offerto[0]);
			InsertList.add(InsOb);

			InsOb = new InsertDataTable();
			InsOb.setColName("insertTimeDate");
			InsOb.setDataName(DateUtil.currentdatetime());
			InsertList.add(InsOb);
			boolean status=new TableInfo().insertIntoTable("packconfiguration", InsertList,connectionSpace);
		      if(status){
			  addActionMessage("Data Added SuccessFully");
		      }else{
			  addActionMessage("Errors: Opps There is some problem for added Data");
		      }
		
		} 
		
		  catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String ViewPackArea() {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		Addmethod AddDetails = new ObjectFactory(new PackConfiguration());
		commanOperation ASD = AddDetails.AddDetailsOfAll("PackConfiguration");
		setRecords(ASD.getRecordStatus());
		int to = (getRows() * getPage());
		int from = to - getRows();
		if (to > getRecords())
			to = getRecords();
		if (loadonce) {
			gridModel = (List<PackConfiguration>) ASD.ServicesviewData(to,
					from, getSearchField(), getSearchString(), getSearchOper(),
					getSord(), getSidx());
			setGridModel(gridModel);
		} else {
			gridModel = (List<PackConfiguration>) ASD.ServicesviewData(to,
					from, getSearchField(), getSearchString(), getSearchOper(),
					getSord(), getSidx());
			setGridModel(gridModel);
		}
		setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
		return SUCCESS;
	}

	public String deleteRecord() {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		if (new TableInfo().deleteRecord("packconfiguration", "id", getId(),
				connectionSpace)) {
			return SUCCESS;
		} else {
			return ERROR;
		}
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

	public List<PackConfiguration> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<PackConfiguration> gridModel) {
		this.gridModel = gridModel;
	}

	public List<BeanCountry> getCountrylist() {
		return countrylist;
	}

	public void setCountrylist(List<BeanCountry> countrylist) {
		this.countrylist = countrylist;
	}

	public String getCountryid() {
		return countryid;
	}

	public void setCountryid(String countryid) {
		this.countryid = countryid;
	}

	public List<AppsList> getApps() {
		return apps;
	}

	public void setApps(List<AppsList> apps) {
		this.apps = apps;
	}

	public String getOfferFrom() {
		return offerFrom;
	}

	public void setOfferFrom(String offerFrom) {
		this.offerFrom = offerFrom;
	}

	public String getOfferTo() {
		return offerTo;
	}

	public void setOfferTo(String offerTo) {
		this.offerTo = offerTo;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getTimeperiod() {
		return timeperiod;
	}

	public void setTimeperiod(String timeperiod) {
		this.timeperiod = timeperiod;
	}

	public int getTimein() {
		return timein;
	}

	public void setTimein(int timein) {
		this.timein = timein;
	}

	public int getAppusercounter() {
		return appusercounter;
	}

	public void setAppusercounter(int appusercounter) {
		this.appusercounter = appusercounter;
	}
}
