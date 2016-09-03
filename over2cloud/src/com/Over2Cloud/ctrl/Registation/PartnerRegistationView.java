package com.Over2Cloud.ctrl.Registation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.Over2Cloud.BeanUtil.RegHieInformation;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.modal.dao.imp.PartnerRegistation.PartnerImpRegistation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PartnerRegistationView extends ActionSupport {

	Map session = ActionContext.getContext().getSession();
	String uName = (String) session.get("uName");
	String accountid = (String) session.get("accountid");
	String countryiso = (String) session.get("countryid");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private List<RegHieInformation> regHries = new ArrayList<RegHieInformation>();
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
	private String id;

	public String ViewOurClienturlGrid() {
		try {

			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			List ViewAllReportInfo = new PartnerImpRegistation()
					.GetAllUserLevel(uName, accountid);
			if (ViewAllReportInfo != null && ViewAllReportInfo.size() > 0) {
				for (Iterator iterator = ViewAllReportInfo.iterator(); iterator
						.hasNext();) {
					RegHieInformation obbb = new RegHieInformation();
					Object[] objectCol = (Object[]) iterator.next();
					obbb.setAccounttype(objectCol[2].toString());
					obbb.setOrgname(objectCol[1].toString());
					obbb.setOrgusername(objectCol[3].toString());
					obbb.setProductcode(objectCol[4].toString());
					obbb.setProductappuser(objectCol[5].toString());
					List<String> Productuser = Arrays.asList(objectCol[5]
							.toString().split("#"));
					int count1 = 0;
					for (String h : Productuser) {
						int count = Integer.parseInt(h);
						count1 = count1 + count;
					}
					obbb.setProductuser(count1);
					obbb.setUsername(Cryptography.decrypt(objectCol[0]
							.toString(), DES_ENCRYPTION_KEY));
					obbb.setValidifyfrom(objectCol[6].toString());
					obbb.setValididyto(objectCol[7].toString());
					obbb
							.setProductid(Integer.parseInt(objectCol[8]
									.toString()));
					obbb.setCombinekey(Cryptography.decrypt(objectCol[0]
							.toString(), DES_ENCRYPTION_KEY)
							+ "_" + objectCol[9].toString());
					obbb.setAccountid(objectCol[10].toString() + "-"
							+ objectCol[9].toString());
					obbb.setIsblock(objectCol[11].toString());
					regHries.add(obbb);
				}
				// System.out.println();
				setRegHries(regHries);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("going back");
		return SUCCESS;
	}

	public List<RegHieInformation> getRegHries() {
		return regHries;
	}

	public void setRegHries(List<RegHieInformation> regHries) {
		this.regHries = regHries;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
