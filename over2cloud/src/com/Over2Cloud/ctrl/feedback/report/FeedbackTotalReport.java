package com.Over2Cloud.ctrl.feedback.report;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.beanUtil.PatientReportPojo;
import com.Over2Cloud.ctrl.feedback.common.TicketActionHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FeedbackTotalReport extends ActionSupport
{
	private String startDate;
	private String endDate;
	private String patType;
	private String smsStat;
	private String mode;
	
	
	private List<PatientReportPojo> masterViewList;
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
	
	public String beforeSearch()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				System.out.println("Start Date>>>>"+getStartDate()+"<<< End Date >>>"+getEndDate()+"<<< Pat Type >>>"+getPatType()+"<<Mode >>>"+getMode());
				return "success";
			}
			catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	
	public String getSearchedDataInGrid()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				masterViewList=new ArrayList<PatientReportPojo>();
				
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuffer query=new StringBuffer("select count(*) from patientinfo");
	   		 	List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	   		 	if(dataCount!=null)
	   		 	{
		   		 	BigInteger count=new BigInteger("3");
	   		 		for(Iterator it=dataCount.iterator(); it.hasNext();)
	   		 		{
	   		 			Object obdata=(Object)it.next();
	   		 			count=(BigInteger)obdata;
	   		 		}
	   		 		setRecords(count.intValue());
		   		 	int to = (getRows() * getPage());
		 			int from = to - getRows();
		 			if (to > getRecords())
		 				to = getRecords();
	   		 	}
				
				masterViewList=new TicketActionHelper().getFeedbackDetailsReport(connectionSpace,getStartDate(),getEndDate(),getPatType(),getSmsStat());
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				
				return "success";
			}
			catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	
	}
	
	public String getSearchedDataInGridFeedback()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			System.out.println("Start Date>>>>"+getStartDate()+"<<< End Date >>>"+getEndDate()+"<<< Pat Type >>>"+getPatType()+"<<Mode >>>"+getMode());
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				masterViewList=new ArrayList<PatientReportPojo>();
				
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuffer query=new StringBuffer("select count(*) from patientinfo");
	   		 	List  dataCount=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
	   		 	if(dataCount!=null)
	   		 	{
		   		 	BigInteger count=new BigInteger("3");
	   		 		for(Iterator it=dataCount.iterator(); it.hasNext();)
	   		 		{
	   		 			Object obdata=(Object)it.next();
	   		 			count=(BigInteger)obdata;
	   		 		}
	   		 		setRecords(count.intValue());
		   		 	int to = (getRows() * getPage());
		 			int from = to - getRows();
		 			if (to > getRecords())
		 				to = getRecords();
	   		 	}
				
				masterViewList=new TicketActionHelper().getFeedbackDetailsReportFeedback(connectionSpace,getStartDate(),getEndDate(),getPatType(),getSmsStat());
				
				System.out.println("Hello dude All is well ");
				
				setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				
				return "success";
			}
			catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	
	
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<PatientReportPojo> getMasterViewList() {
		return masterViewList;
	}
	public void setMasterViewList(List<PatientReportPojo> masterViewList) {
		this.masterViewList = masterViewList;
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
	public String getPatType() {
		return patType;
	}
	public void setPatType(String patType) {
		this.patType = patType;
	}
	public String getSmsStat() {
		return smsStat;
	}
	public void setSmsStat(String smsStat) {
		this.smsStat = smsStat;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
}
