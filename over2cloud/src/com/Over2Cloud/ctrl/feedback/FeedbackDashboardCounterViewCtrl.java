package com.Over2Cloud.ctrl.feedback;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FeedbackDashboardCounterViewCtrl extends ActionSupport
{
	public String headerName;
	public String counterType;
	public String searchFiled;
	private String deptName;
	
	private List<GridDataPropertyView>masterViewProp;
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
	//Grid colomn view
	private String oper;
	private String id;
	private List<Object> masterViewList;
	
	
	public String showCounterDataInGrid()
	{

		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String accountID=(String)session.get("accountid");
				
			//	System.out.println(getHeaderName().equalsIgnoreCase("Feedback") && getDeptName()!=null && getSearchFiled()!=null);
				String userName=session.get("uName").toString();
				if(getHeaderName()!=null)
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					StringBuilder query1=new StringBuilder("");
					if(getHeaderName().equalsIgnoreCase("Feedback") && getCounterType()!=null)
					{
						query1.append("select count(*) from feedbackdata where id!=0 && targetOn='"+getCounterType()+"'");
					}
					else if(getHeaderName().equalsIgnoreCase("Feedback") && getDeptName()!=null && getSearchFiled()!=null)
					{
						if(getDeptName().equalsIgnoreCase("Front Office"))
						{
							query1.append("select count(*) from feedbackdata where id!=0 && cleanliness='"+getSearchFiled()+"'");
						}
						else if(getDeptName().equalsIgnoreCase("Medical Care"))
						{
							query1.append("select count(*) from feedbackdata where id!=0 && staffBehaviour='"+getSearchFiled()+"'");
						}
						else if(getDeptName().equalsIgnoreCase("Diagnostics"))
						{
							query1.append("select count(*) from feedbackdata where id!=0 && treatment='"+getSearchFiled()+"'");
						}
						else if(getDeptName().equalsIgnoreCase("Pharmacy"))
						{
							query1.append("select count(*) from feedbackdata where id!=0 && ambience='"+getSearchFiled()+"'");
						}
						else if(getDeptName().equalsIgnoreCase("Billing Services"))
						{
							query1.append("select count(*) from feedbackdata where id!=0 && billingServices='"+getSearchFiled()+"'");
						}
						else if(getDeptName().equalsIgnoreCase("Overall Services"))
						{
							query1.append("select count(*) from feedbackdata where id!=0 && resultSatis='"+getSearchFiled()+"'");
						}
						else if(getDeptName().equalsIgnoreCase("Other Facilities"))
						{
							query1.append("select count(*) from feedbackdata where id!=0 && overAll='"+getSearchFiled()+"'");
						}
					}
					List  dataCount=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
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
						
						//getting the list of colmuns
						StringBuilder query=new StringBuilder("");
						query.append("select ");
						List fieldNames=Configuration.getColomnList("mapped_feedback_configuration", accountID, connectionSpace, "feedback_data_configuration");
						List<Object> Listhb=new ArrayList<Object>();
						int i=0;
						for(Iterator it=fieldNames.iterator(); it.hasNext();)
						{
							//generating the dyanamic query based on selected fields
							    Object obdata=(Object)it.next();
							    if(obdata!=null)
							    {
								    if(i<fieldNames.size()-1)
								    	query.append(obdata.toString()+",");
								    else
								    	query.append(obdata.toString());
							    }
							    i++;
							
						}
						if(getHeaderName().equalsIgnoreCase("Feedback") && getCounterType()!=null)
						{
							query.append(" from feedbackdata where id!=0 && targetOn='"+getCounterType()+"'");
						}
						else if(getHeaderName().equalsIgnoreCase("Feedback") && getDeptName()!=null && getSearchFiled()!=null)
						{
							if(getDeptName().equalsIgnoreCase("Front Office"))
							{
								
								query.append(" from feedbackdata where id!=0 && cleanliness='"+getSearchFiled()+"'");
							}
							else if(getDeptName().equalsIgnoreCase("Medical Care"))
							{
								query.append(" from feedbackdata where id!=0 && staffBehaviour='"+getSearchFiled()+"'");
							}
							else if(getDeptName().equalsIgnoreCase("Diagnostics"))
							{
								query.append(" from feedbackdata where id!=0 && treatment='"+getSearchFiled()+"'");
							}
							else if(getDeptName().equalsIgnoreCase("Pharmacy"))
							{
								query.append(" from feedbackdata where id!=0 && ambience='"+getSearchFiled()+"'");
							}
							else if(getDeptName().equalsIgnoreCase("Billing Services"))
							{
								query.append(" from feedbackdata where id!=0 && billingServices='"+getSearchFiled()+"'");
							}
							else if(getDeptName().equalsIgnoreCase("Overall Services"))
							{
								query.append(" from feedbackdata where id!=0 && resultSatis='"+getSearchFiled()+"'");
							}
							else if(getDeptName().equalsIgnoreCase("Other Facilities"))
							{
								query.append(" from feedbackdata where id!=0 && overAll='"+getSearchFiled()+"'");
							}
						}
						List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
						Collections.reverse(data);
						if(data!=null)
						{
							for(Iterator it=data.iterator(); it.hasNext();)
							{
								Object[] obdata=(Object[])it.next();
								Map<String, Object> one=new HashMap<String, Object>();
								for (int k = 0; k < fieldNames.size(); k++) {
									if(obdata[k]!=null)
									{
											if(k==0)
											{
												one.put(fieldNames.get(k).toString(), (Integer)obdata[k]);
											}
											else
											{
												if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("date"))
												{
													one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
												}
												else if(fieldNames.get(k)!=null && fieldNames.get(k).toString().equalsIgnoreCase("time"))
												{
													one.put(fieldNames.get(k).toString(),(obdata[k].toString().substring(0,5)));
												}
												else
												{
											//		System.out.println("fieldNames.get(k).toString() is as >"+fieldNames.get(k).toString()+">> obdata[k].toString()"+ obdata[k].toString());
													one.put(fieldNames.get(k).toString(), obdata[k].toString());
												}
											}
									}
								}
								Listhb.add(one);
							}
							setMasterViewList(Listhb);
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
						
					}
				}
				return SUCCESS;
			}
			catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	
	}
	public String beforeGridDataView()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				String userName=session.get("uName").toString();
				String accountID=(String)session.get("accountid");
				setGridHeaderNames(connectionSpace,getHeaderName(),getCounterType(),accountID);
				return SUCCESS;
			}
			catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	public void setGridHeaderNames(SessionFactory connectionSpace,String headerName,String counterType,String accountID)
	{

		masterViewProp=new ArrayList<GridDataPropertyView>();
		GridDataPropertyView gpv=new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		masterViewProp.add(gpv);
		
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_feedback_configuration",accountID,connectionSpace,"",0,"table_name","feedback_data_configuration");
		for(GridDataPropertyView gdp:returnResult)
		{
			gpv=new GridDataPropertyView();
			gpv.setColomnName(gdp.getColomnName());
			gpv.setHeadingName(gdp.getHeadingName());
			gpv.setEditable(gdp.getEditable());
			gpv.setSearch(gdp.getSearch());
			gpv.setHideOrShow(gdp.getHideOrShow());
			gpv.setFormatter(gdp.getFormatter());
			gpv.setWidth(100);
			masterViewProp.add(gpv);
		}
	
	}
	public String getHeaderName() {
		return headerName;
	}
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
	public String getCounterType() {
		return counterType;
	}
	public void setCounterType(String counterType) {
		this.counterType = counterType;
	}
	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
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
	public List<Object> getMasterViewList() {
		return masterViewList;
	}
	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getSearchFiled() {
		return searchFiled;
	}
	public void setSearchFiled(String searchFiled) {
		this.searchFiled = searchFiled;
	}
}
