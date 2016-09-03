package com.Over2Cloud.ctrl.communication.totalReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


@SuppressWarnings("serial")
public class TotalReport extends ActionSupport implements ServletRequestAware {
	
	
	private HttpServletRequest request;
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	
	private List<GridDataPropertyView>viewTotalReportGrid  = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean> mobileTextBox = null;
	private List<ConfigurationUtilBean> writemessageTextBox = null;
	private List<ConfigurationUtilBean> messageNameDropdown = null;
	
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
	// Grid colomn view
	private String oper;
	private String id;
	
	
	public String totalReport() {
		System.out.println("inside method....");
		try {
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			setTotalReports();

		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}
	private void setTotalReports() {
		
		System.out.println("inside This method....");
		try {
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session
					.get("connectionSpace");
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewTotalReportGrid.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration
					.getConfigurationData("mapped_instant_msg_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"instant_msg_configuration");
			for (GridDataPropertyView gdp : returnResult) 
			{
            if (!gdp.getColomnName().equalsIgnoreCase("frequency")&&!gdp.getColomnName().equalsIgnoreCase("name")) 
				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					viewTotalReportGrid.add(gpv);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String beforeInstantMessageAdd()
	{
		
		System.out.println("insideee method====");
		try {
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			setInstantMessageDataFields();

		} catch (Exception e) {

			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;
	}
	private void setInstantMessageDataFields() {
		
		
		
		System.out.println(",,,,,,,,,,,,,,,,,,,,,,,");
		try {
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session
					.get("connectionSpace");
			mobileTextBox = new ArrayList<ConfigurationUtilBean>();
			writemessageTextBox = new ArrayList<ConfigurationUtilBean>();
			messageNameDropdown = new ArrayList<ConfigurationUtilBean>();
			
			List<GridDataPropertyView> returnResult = Configuration
			.getConfigurationData("mapped_instant_msg_configuration",
					accountID, connectionSpace, "", 0, "table_name",
					"instant_msg_configuration");
			if (returnResult != null) {
				for (GridDataPropertyView gdp : returnResult) {
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")
							
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"status")&& !gdp.getColomnName().equalsIgnoreCase(
									"name")&& !gdp.getColomnName().equalsIgnoreCase(
									"writeMessage")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}

						mobileTextBox.add(obj);
					}  else if (gdp.getColType().trim().equalsIgnoreCase("T")
							
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"status")&& !gdp.getColomnName().equalsIgnoreCase(
									"name")&& !gdp.getColomnName().equalsIgnoreCase(
									"mobileNo")) {

						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						writemessageTextBox.add(obj);
				
					}
					
					else if (gdp.getColType().trim().equalsIgnoreCase("D")
							&& !gdp.getColomnName()
									.equalsIgnoreCase("userName")
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							) {
						System.out.println("column name TTTTTTTT======"
								+ gdp.getColomnName());
						System.out.println("ccccccc==" + gdp.getColType());
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						messageNameDropdown.add(obj);
					}
					
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	

	@Override
	
	
	
	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		
	}
	public List<ConfigurationUtilBean> getMobileTextBox() {
		return mobileTextBox;
	}
	public void setMobileTextBox(List<ConfigurationUtilBean> mobileTextBox) {
		this.mobileTextBox = mobileTextBox;
	}
	public List<ConfigurationUtilBean> getWritemessageTextBox() {
		return writemessageTextBox;
	}
	public void setWritemessageTextBox(
			List<ConfigurationUtilBean> writemessageTextBox) {
		this.writemessageTextBox = writemessageTextBox;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public Map getSession() {
		return session;
	}
	public void setSession(Map session) {
		this.session = session;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public List<GridDataPropertyView> getViewTotalReportGrid() {
		return viewTotalReportGrid;
	}
	public void setViewTotalReportGrid(
			List<GridDataPropertyView> viewTotalReportGrid) {
		this.viewTotalReportGrid = viewTotalReportGrid;
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
	public List<ConfigurationUtilBean> getMessageNameDropdown() {
		return messageNameDropdown;
	}
	public void setMessageNameDropdown(
			List<ConfigurationUtilBean> messageNameDropdown) {
		this.messageNameDropdown = messageNameDropdown;
	}

	

}
