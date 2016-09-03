package com.Over2Cloud.ctrl.Setting;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.modal.db.Setting.ApplicationSetting;
import com.Over2Cloud.modal.db.Setting.ChunkClientDb;
import com.Over2Cloud.modal.db.Setting.ClientDbServer;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SpaceCtrlSettingView extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8952937848669719474L;
	Map session = ActionContext.getContext().getSession();
	String uName = (String) session.get("uName");
	String accountid = (String) session.get("accountid");
	String countryiso = (String) session.get("countryid");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	private List<ApplicationSetting> appConfigList = null;
	private List<ChunkClientDb> cfcList = null;
	private List<ClientDbServer> clientDbName = null;

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

	@SuppressWarnings("unchecked")
	public String viewAppRegistrationGrid() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			CommonforClass cfc = new CommanOper();
			records = cfc.getRecordStatus(ApplicationSetting.class);
			if (totalrows != null) {
				records = totalrows;
			}
			int to = (rows * page);
			int from = to - rows;
			if (to > records)
				to = records;

			appConfigList = cfc.ServicesviewData(to, from, searchField,
					searchString, searchOper, sord, sidx, "", 0,
					ApplicationSetting.class);

			// System.out.println("appConfigList:"+appConfigList.size());
			total = (int) Math.ceil((double) records / (double) rows);
		} catch (Exception e) {
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
			return ERROR;
		}
		return SUCCESS;
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

	public List<ChunkClientDb> getCfcList() {
		return cfcList;
	}

	public void setCfcList(List<ChunkClientDb> cfcList) {
		this.cfcList = cfcList;
	}

	public List<ClientDbServer> getClientDbName() {
		return clientDbName;
	}

	public void setClientDbName(List<ClientDbServer> clientDbName) {
		this.clientDbName = clientDbName;
	}

}
