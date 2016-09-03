package com.Over2Cloud.ctrl.feedback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import com.Over2Cloud.modal.dao.imp.reports.ReportsConfigurationDao;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.createTable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.Over2Cloud.BeanUtil.FeedDataPojo;

public class FeedbackDashboardGridViewAction extends ActionSupport
{
	
	private String subDept;
	private String searchFlag;
	private String mainHeaderName;
	
	 private Integer             rows             = 0;

	  // Get the requested page. By default grid sets this to 1.
	  private Integer             page             = 0;

	  // sorting order - asc or desc
	  private String              sord;

	  // get index row - i.e. user click to sort.
	  private String              sidx;

	  // Search Field
	  private String              searchField;

	  // The Search String
	  private String              searchString;

	  // Limit the result when using local data, value form attribute rowTotal
	  private Integer             totalrows;

	  // he Search Operation
	  // ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	  private String              searchOper;

	  // Your Total Pages
	  private Integer             total            = 0;

	  // All Records
	  private Integer             records          = 0;
	  private boolean             loadonce         = false;
	  private List<FeedDataPojo> gridModel;
	  
	  public String beforeDashBoardShow()
	  {
		  boolean valid=ValidateSession.checkSession();
		  String returnString=ERROR;
		  if(valid)
		  {
			  try
			  {
				//  System.out.println("FROR        "+getSubDept());
				 // System.out.println("For>>>>>>>>>>"+getSearchFlag());
				  setMainHeaderName(getSubDept()+">>>>>>>>"+getSearchFlag());
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
	
	  public String beforeDashBoardShowInGrid()
	  {
		  boolean valid=ValidateSession.checkSession();
		  String returnString=ERROR;
		  if(valid)
		  {
			  try
			  {gridModel=new ArrayList<FeedDataPojo>();
				if(getSubDept()!=null && getSearchFlag()!=null)
				{
					//  System.out.println("FROR        "+getSubDept());
					//  System.out.println("For>>>>>>>>>>"+getSearchFlag());

					  Map session = ActionContext.getContext().getSession();
					  SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					  String userName=(String)session.get("uName");
					  
					  String columnName=new ReportsConfigurationDao().getSingleColumnValue("field_value","feedback_data_configuration",getSubDept(),"field_name",connectionSpace);
				//	  System.out.println("COLUMN Name is as >>"+columnName);
					  
					  if(columnName!=null && getSearchFlag()!=null)
					  {
						  StringBuffer buffer=new StringBuffer("select id,name,docterName,mobileNo,emailId,purposeOfVisit,comment,date,time from feedbackdata where "+columnName+"='"+getSearchFlag()+"'");
						  
					//	  System.out.println("Querry is as <>>>>>>>>>>>>>>>>>>>>"+buffer.toString());
						  List data=new createTable().executeAllSelectQuery(buffer.toString(), connectionSpace);
						  
						  if(data!=null && data.size()>0)
						  {
							  
							  for (Iterator iterator = data.iterator(); iterator.hasNext();)
							  {
								Object[] object = (Object[]) iterator.next();
								FeedDataPojo feed=new FeedDataPojo();
								if(object!=null)
								{
									if(object[0]!=null)
									{
										feed.setId(Integer.parseInt(object[0].toString()));
									}
									
									if(object[1]!=null)
									{
										feed.setName(object[1].toString());
									}
									
									if(object[2]!=null)
									{
										feed.setDoctName(object[2].toString());
									}
									
									if(object[3]!=null)
									{
										feed.setMobNo(object[3].toString());
									}
									
									if(object[4]!=null)
									{
										feed.setEmailId(object[4].toString());
									}
									
									if(object[5]!=null)
									{
										feed.setPurpose(object[5].toString());
									}
									
									if(object[6]!=null)
									{
										feed.setCommnts(object[6].toString());
									}
									
									
									if(object[7]!=null)
									{
										feed.setFeedDate(DateUtil.convertDateToIndianFormat(object[7].toString()));
									}
										
									if(object[8]!=null)
									{
										feed.setFeedTime(object[8].toString());
									}
									
									gridModel.add(feed);
								}
							}
						  }
						  setRecords(gridModel.size());
							int to = (getRows() * getPage());
							int from = to - getRows();
							if (to > getRecords())
								to = getRecords();
							
							setGridModel(gridModel);
						//	System.out.println("Grid Model Size >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+getGridModel().size());
						  setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						  return SUCCESS;
					  }
					  else
						{
							return LOGIN;
						}
				}
				else
				{
					return LOGIN;
				}
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
	public String getSubDept() {
		return subDept;
	}

	public void setSubDept(String subDept) {
		this.subDept = subDept;
	}

	public String getSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}

	public String getMainHeaderName() {
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}

	public List<FeedDataPojo> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<FeedDataPojo> gridModel) {
		this.gridModel = gridModel;
	}
}
