package com.Over2Cloud.ctrl.communication.autoPushReport;




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
public class AutoPushReportModule  extends ActionSupport implements ServletRequestAware {
	
	
	private HttpServletRequest request;
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	
	private List<GridDataPropertyView>viewAutoPushReportGrid  = new ArrayList<GridDataPropertyView>();
	private List<ConfigurationUtilBean>mobileNum = null;
	private List<ConfigurationUtilBean> messageTextTextBox = null;
	private List<ConfigurationUtilBean> messageTypeDropDown = null;
	private List<ConfigurationUtilBean> fromTextBox = null;
	private List<ConfigurationUtilBean> toTextBox = null;
	
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
	
	
	public String beforeAutoPushReportView() {
		System.out.println("inside method....");
		try {
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			setAutoPushReportView() ;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}
	private void setAutoPushReportView() {
		
		System.out.println("inside This method....");
		try {
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session
					.get("connectionSpace");
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewAutoPushReportGrid .add(gpv);

			List<GridDataPropertyView> returnResult = Configuration
			.getConfigurationData("mapped_instant_msg_configuration",
					accountID, connectionSpace, "", 0, "table_name",
					"instant_msg_configuration");
			for (GridDataPropertyView gdp : returnResult) {
				if (!gdp.getColomnName().equalsIgnoreCase("frequency")&&!gdp.getColomnName().equalsIgnoreCase("name")&&!gdp.getColomnName().equalsIgnoreCase("msgtype"))

				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					viewAutoPushReportGrid .add(gpv);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public String getAutoPushReport() {
		System.out.println("insideee method====");
		try {
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			setAutoPushReport();
			

		} catch (Exception e) {

			addActionError("Ooops There Is Some Problem !");
			e.printStackTrace();
		}

		return SUCCESS;

	}

	private void setAutoPushReport() {
		System.out.println(",,,,,,,,,,,,,,,,,,,,,,,");
		try {
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session
					.get("connectionSpace");
			mobileNum = new ArrayList<ConfigurationUtilBean>();
			messageTextTextBox = new ArrayList<ConfigurationUtilBean>();
			messageTypeDropDown = new ArrayList<ConfigurationUtilBean>();
			fromTextBox = new ArrayList<ConfigurationUtilBean>();
			toTextBox = new ArrayList<ConfigurationUtilBean>();
			
			List<GridDataPropertyView> group = Configuration
					.getConfigurationData("mapped_auto_push_report_configuration",
							accountID, connectionSpace, "", 0, "table_name",
							"auto_push_report_configuration");
			if (group != null) {
				for (GridDataPropertyView gdp : group) {
					ConfigurationUtilBean obj = new ConfigurationUtilBean();
					if (gdp.getColType().trim().equalsIgnoreCase("T")
							
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName().equalsIgnoreCase(
									"messagetext")) {
						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}

						mobileNum.add(obj);
					}  else if (gdp.getColType().trim().equalsIgnoreCase("T")
							
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName().equalsIgnoreCase(
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
						messageTextTextBox.add(obj);
					}
						else if (gdp.getColType().trim().equalsIgnoreCase("D")
							
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							) {

						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						messageTypeDropDown.add(obj);
					}
                         else if (gdp.getColType().trim().equalsIgnoreCase("Date")
							
							&& !gdp.getColomnName().equalsIgnoreCase("date")
							&& !gdp.getColomnName().equalsIgnoreCase("time")
							&& !gdp.getColomnName().equalsIgnoreCase(
							"to")) {

						obj.setValue(gdp.getHeadingName());
						obj.setKey(gdp.getColomnName());
						obj.setValidationType(gdp.getValidationType());
						obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1")) {
							obj.setMandatory(true);
						} else {
							obj.setMandatory(false);
						}
						fromTextBox.add(obj);
					}
                         else if (gdp.getColType().trim().equalsIgnoreCase("Date")
     							
     							&& !gdp.getColomnName().equalsIgnoreCase("date")
     							&& !gdp.getColomnName().equalsIgnoreCase(
									"from")){

     						obj.setValue(gdp.getHeadingName());
     						obj.setKey(gdp.getColomnName());
     						obj.setValidationType(gdp.getValidationType());
     						obj.setColType(gdp.getColType());
     						if (gdp.getMandatroy().toString().equals("1")) {
     							obj.setMandatory(true);
     						} else {
     							obj.setMandatory(false);
     						}
     						toTextBox.add(obj);
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
	
	
	
	
	
	
	
	public List<ConfigurationUtilBean> getFromTextBox() {
		return fromTextBox;
	}
	public void setFromTextBox(List<ConfigurationUtilBean> fromTextBox) {
		this.fromTextBox = fromTextBox;
	}
	public List<ConfigurationUtilBean> getToTextBox() {
		return toTextBox;
	}
	public void setToTextBox(List<ConfigurationUtilBean> toTextBox) {
		this.toTextBox = toTextBox;
	}
	public List<ConfigurationUtilBean> getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(List<ConfigurationUtilBean> mobileNum) {
		this.mobileNum = mobileNum;
	}
	public List<ConfigurationUtilBean> getMessageTextTextBox() {
		return messageTextTextBox;
	}
	public void setMessageTextTextBox(List<ConfigurationUtilBean> messageTextTextBox) {
		this.messageTextTextBox = messageTextTextBox;
	}
	public List<ConfigurationUtilBean> getMessageTypeDropDown() {
		return messageTypeDropDown;
	}
	public void setMessageTypeDropDown(
			List<ConfigurationUtilBean> messageTypeDropDown) {
		this.messageTypeDropDown = messageTypeDropDown;
	}
	public List<GridDataPropertyView> getViewAutoPushReportGrid() {
		return viewAutoPushReportGrid;
	}
	public void setViewAutoPushReportGrid(
			List<GridDataPropertyView> viewAutoPushReportGrid) {
		this.viewAutoPushReportGrid = viewAutoPushReportGrid;
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

	

}