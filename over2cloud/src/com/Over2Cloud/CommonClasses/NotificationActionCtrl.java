package com.Over2Cloud.CommonClasses;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.modal.db.commom.Requestgeneration;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class NotificationActionCtrl extends ActionSupport{
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountidCounty=(String)session.get("accountidCounty");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private List<Requestgeneration>notificationForAction;
	private String id;
	private String desc;
	//notification view
	private Integer             rows             = 0;
	  private Integer             page             = 0;
	  private String              sord;
	  private String              sidx;
	  private String              searchField;
	  private String              searchString;
	  private Integer             totalrows;
	  private String              searchOper;
	  private Integer             total            = 0;
	  private Integer             records          = 0;
	  private boolean             loadonce         = false;
	/**
	 * Notification Works starts from here
	 * @return
	 */
	public String beforeViewNotification()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String viewGridNotification()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonforClass cfc = new CommanOper();
		    records = cfc.getRecordStatus(Requestgeneration.class);
		    if (totalrows != null)
		    {
		      records = totalrows;
		    }
		    int to = (rows * page);
		    int from = to - rows;
		    if (to > records) to = records;
			
		    notificationForAction = cfc.ServicesviewData(to, from, "clientNotification", "Y", "eq", 
		    		sord,sidx, "", 0, Requestgeneration.class, "accountid", accountidCounty);
		    total = (int) Math.ceil((double) records / (double) rows);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String beforeTakeActionOnReuqest()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			setId(getId());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String takeActionOnReuqest()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			boolean status=false;
			CommonforClass cfc = new CommanOper();
			Requestgeneration req=(Requestgeneration)cfc.findRecordForPk(Integer.parseInt(getId()), Requestgeneration.class);
			if(req!=null)
			{
				req.setServerDiscription(getDesc());
				req.setClientNotification("N");
				req.setOver2CloudNoification("Y");
				status=cfc.UpdateDetails(req);
			}
			
			if(status)
				addActionMessage("Action Takeen Successfully!!!");
			else
				addActionError("Ooops there is some problem!!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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
	public List<Requestgeneration> getNotificationForAction() {
		return notificationForAction;
	}
	public void setNotificationForAction(
			List<Requestgeneration> notificationForAction) {
		this.notificationForAction = notificationForAction;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}

	
}
