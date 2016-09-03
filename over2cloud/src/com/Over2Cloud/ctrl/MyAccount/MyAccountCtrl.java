package com.Over2Cloud.ctrl.MyAccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.Over2Cloud.BeanUtil.ProductUtilityBean;
import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ObjectFactory;
import com.Over2Cloud.CommonInterface.Addmethod;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.CommonInterface.commanOperation;
import com.Over2Cloud.modal.dao.imp.MyAccount.MyAccountImp;
import com.Over2Cloud.modal.db.Setting.PackConfiguration;
import com.Over2Cloud.modal.db.commom.Requestgeneration;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MyAccountCtrl extends ActionSupport {
	/**
	 * Author By Ankit Singh Date : 12-03-2013
	 */
	private static final long serialVersionUID = 1L;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	static final Log log = LogFactory.getLog(MyAccountCtrl.class);
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String uName = (String) session.get("uName");
	String accountid = (String) session.get("accountid");
	String countryiso = (String) session.get("countryid");
	private List<ProductUtilityBean> griedmodel = new ArrayList<ProductUtilityBean>();
	private List<Requestgeneration> griedmodel1 = new ArrayList<Requestgeneration>();
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
	private Integer id;
	private String orgnizationId;
	private String accountid1;
	private String country;
	private String orgnization;
	private String typeOfRequest;
	private String subject;
	private String description;

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

	@SuppressWarnings("unchecked")
	public String GetAccountViewDetails() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			MyAccountImp myaccountOb = new MyAccountImp();
			List AccountList = myaccountOb.GetAllAccountDetails();
			if (AccountList != null && AccountList.size() > 0) {
				for (Iterator iterator = AccountList.iterator(); iterator
						.hasNext();) {
					Object[] objectCol = (Object[]) iterator.next();
					ProductUtilityBean ob1 = new ProductUtilityBean();
					ob1.setAccountid(objectCol[8].toString().trim() + "-"
							+ objectCol[0].toString().trim());
					ob1.setOrgname(objectCol[1].toString().trim());
					ob1.setOrgRegname(objectCol[2].toString().trim());
					ob1.setAccounttype(objectCol[3].toString().trim());
					ob1.setProductcode(objectCol[4].toString().trim());
					ob1.setProductuser(objectCol[5].toString().trim());
					List<String> asdObj = Arrays.asList(objectCol[5].toString()
							.trim().split("#"));
					int Sum = 0;
					for (String h : asdObj) {
						Sum = Sum + Integer.parseInt(h);
					}
					ob1.setTotaluser(Integer.toString(Sum));
					ob1.setValidityfrom(objectCol[6].toString().trim());
					ob1.setValiudityto(objectCol[7].toString().trim());
					ob1.setCountryid(objectCol[8].toString().trim());
					ob1.setProductlist(objectCol[0].toString().trim());
					ob1.setReqRechargUpperHric(objectCol[0].toString().trim());
					ob1.setMailtoOrgnization(objectCol[0].toString().trim());
					griedmodel.add(ob1);
				}
				setGriedmodel(griedmodel);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}

	public String GetreqforOrgnization() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			List<String> aslist = Arrays.asList(getOrgnizationId().split("_"));
			setCountry(aslist.get(1).toString());
			setOrgnization(aslist.get(2).toString());
			setAccountid1(aslist.get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String GetallRequestPennel() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			Requestgeneration Ob1 = new Requestgeneration("N", getAccountid1(),
					"Y", getDescription(), getCountry(), DateUtil
							.getCurrentDateUSFormat(), DateUtil
							.getCurrentTime(), "No Responce From organization",
					getSubject(), getTypeOfRequest());
			CommonforClass eventDao = new CommanOper();
			if (eventDao.addDetails(Ob1)) {
				log
						.info("Over2Cloud::>> Class:MyAccountCtrl >> Method::>> GetallRequestPennel ::::: Sucessfully Add Request");
				return SUCCESS;
			} else {
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ERROR;
	}

	@SuppressWarnings("unchecked")
	public String ClientResponceSystemTake() {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		Addmethod AddDetails = new ObjectFactory(new Requestgeneration());
		commanOperation ASD = AddDetails.AddDetailsOfAll("Requestgeneration");
		setRecords(ASD.getRecordStatus());
		int to = (getRows() * getPage());
		int from = to - getRows();
		if (to > getRecords())
			to = getRecords();
		MyAccountImp ob1 = new MyAccountImp();
		if (loadonce) {
			griedmodel1 = (List<Requestgeneration>) ob1.ServicesviewData(to,
					from, getSearchField(), getSearchString(), getSearchOper(),
					getSord(), getSidx(), Requestgeneration.class);
			//System.out.println("::::::::::::" + griedmodel1.size());
			setGriedmodel1(griedmodel1);
		} else {
			griedmodel1 = (List<Requestgeneration>) ob1.ServicesviewData(to,
					from, getSearchField(), getSearchString(), getSearchOper(),
					getSord(), getSidx(), Requestgeneration.class);
			//System.out.println("::::::::::::" + griedmodel1.size());
			setGriedmodel1(griedmodel1);
		}
		setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
		return SUCCESS;
	}

	public List<Requestgeneration> getGriedmodel1() {
		return griedmodel1;
	}

	public void setGriedmodel1(List<Requestgeneration> griedmodel1) {
		this.griedmodel1 = griedmodel1;
	}

	public List<ProductUtilityBean> getGriedmodel() {
		return griedmodel;
	}

	public void setGriedmodel(List<ProductUtilityBean> griedmodel) {
		this.griedmodel = griedmodel;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountid1() {
		return accountid1;
	}

	public void setAccountid1(String accountid1) {
		this.accountid1 = accountid1;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOrgnization() {
		return orgnization;
	}

	public void setOrgnization(String orgnization) {
		this.orgnization = orgnization;
	}

	public String getOrgnizationId() {
		return orgnizationId;
	}

	public void setOrgnizationId(String orgnizationId) {
		this.orgnizationId = orgnizationId;
	}

	public String getTypeOfRequest() {
		return typeOfRequest;
	}

	public void setTypeOfRequest(String typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
