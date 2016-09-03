package com.Over2Cloud.ctrl.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;
import com.Over2Cloud.BeanUtil.Industrycode;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.TableInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class IndustryCtrl extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String uName = (String) session.get("uName");
	String accountid = (String) session.get("accountid");
	String countryiso = (String) session.get("countryid");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
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
	private String indusName;
	private String indusbrief;
	private List<Industrycode> gridmodel = new ArrayList<Industrycode>();

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

	public String AddIndustry() {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		List<InsertDataTable> InsertList = new ArrayList<InsertDataTable>();
		InsertDataTable InsOb = new InsertDataTable();
		InsOb.setColName("industry_name");
		InsOb.setDataName(getIndusName());
		InsertList.add(InsOb);
		InsOb = new InsertDataTable();
		InsOb.setColName("industry_code");
		InsOb.setDataName(getIndusbrief());
		InsertList.add(InsOb);
		InsOb = new InsertDataTable();
		InsOb.setColName("insert_date_time");
		InsOb.setDataName(DateUtil.currentdatetime());
		InsertList.add(InsOb);
		new TableInfo()
				.insertIntoTable("industry", InsertList, connectionSpace);
		return SUCCESS;

	}

	@SuppressWarnings("unchecked")
	public String ViewIndustry() {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		List<String> colmName = new ArrayList<String>();
		colmName.add("industry_name");
		colmName.add("industry_code");
		colmName.add("insert_date_time");
		colmName.add("id");
		Map<String, String> wherClause = new HashMap<String, String>();
		List Industrylist = new TableInfo().viewAllDataEitherSelectOrAll(
				"industry", colmName, wherClause, connectionSpace);
		if (Industrylist != null && Industrylist.size() > 0) {
			for (Iterator iterator1 = Industrylist.iterator(); iterator1
					.hasNext();) {
				Object[] objectCol1 = (Object[]) iterator1.next();
				Industrycode AccountList = new Industrycode();
				AccountList.setName(objectCol1[0].toString());
				AccountList.setCode(objectCol1[1].toString());
				AccountList.setDate(objectCol1[2].toString());
				AccountList.setId(Integer.parseInt(objectCol1[3].toString()));
				gridmodel.add(AccountList);
			}
			setGridmodel(gridmodel);
		}
		return SUCCESS;
	}

	public String deleteRecord() {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		if (new TableInfo().deleteRecord("industry", "id", getId(),
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

	public String getIndusName() {
		return indusName;
	}

	public void setIndusName(String indusName) {
		this.indusName = indusName;
	}

	public String getIndusbrief() {
		return indusbrief;
	}

	public void setIndusbrief(String indusbrief) {
		this.indusbrief = indusbrief;
	}

	public List<Industrycode> getGridmodel() {
		return gridmodel;
	}

	public void setGridmodel(List<Industrycode> gridmodel) {
		this.gridmodel = gridmodel;
	}
}
