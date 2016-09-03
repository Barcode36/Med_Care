package com.Over2Cloud.ctrl.feedback.dashboard;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.feedback.pojo.QualityPojo;
import com.opensymphony.xwork2.ActionContext;

public class QualityScoreCtrl
{
	private String deptId,toDate,fromDate,moduleName;
	private List<QualityPojo> feedbackList;
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
	private Map<String, Object>	userdata;
	
	
	public String redirectToJSP()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				return "success";
			}
			catch (Exception e) 
			{
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	
	public String beforeQualitryScoreView()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				userdata= new LinkedHashMap<String, Object>();
				System.out.println("Dept Id is as "+getDeptId());
				System.out.println("From Date >>"+getFromDate()+"<<To Date >>"+getToDate()+"Module>>>>>"+getModuleName());
				QualityScoreHelper helper=new QualityScoreHelper();
				feedbackList=helper.getAllQualityDetailsData(getDeptId(), connectionSpace);
				setFeedbackList(feedbackList);
				userdata.put("catName", "Total : ");
				int totalR1=0,totalR2=0,totalR3=0,totalR4=0,totalR5=0;
				for(QualityPojo qlty:feedbackList)
				{
					totalR1+=Integer.parseInt(qlty.getRat1());
					totalR2+=Integer.parseInt(qlty.getRat2());
					totalR3+=Integer.parseInt(qlty.getRat3());
					totalR4+=Integer.parseInt(qlty.getRat4());
					totalR5+=Integer.parseInt(qlty.getRat5());
				}
				
				userdata.put("rat1",totalR1);
				userdata.put("rat2",totalR2);
				userdata.put("rat3",totalR3);
				userdata.put("rat4",totalR4);
				userdata.put("rat5",totalR5);
				
				
				return "success";
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	public String getDeptId()
	{
		return deptId;
	}
	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}
	public String getToDate()
	{
		return toDate;
	}
	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}
	public String getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}
	public String getModuleName()
	{
		return moduleName;
	}
	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}
	public List<QualityPojo> getFeedbackList()
	{
		return feedbackList;
	}
	public void setFeedbackList(List<QualityPojo> feedbackList)
	{
		this.feedbackList = feedbackList;
	}
	public Integer getRows()
	{
		return rows;
	}
	public void setRows(Integer rows)
	{
		this.rows = rows;
	}
	public Integer getPage()
	{
		return page;
	}
	public void setPage(Integer page)
	{
		this.page = page;
	}
	public String getSord()
	{
		return sord;
	}
	public void setSord(String sord)
	{
		this.sord = sord;
	}
	public String getSidx()
	{
		return sidx;
	}
	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}
	public String getSearchField()
	{
		return searchField;
	}
	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}
	public String getSearchString()
	{
		return searchString;
	}
	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}
	public String getSearchOper()
	{
		return searchOper;
	}
	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}
	public Integer getTotal()
	{
		return total;
	}
	public void setTotal(Integer total)
	{
		this.total = total;
	}
	public Integer getRecords()
	{
		return records;
	}
	public void setRecords(Integer records)
	{
		this.records = records;
	}
	public boolean isLoadonce()
	{
		return loadonce;
	}
	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}
	public String getOper()
	{
		return oper;
	}
	public void setOper(String oper)
	{
		this.oper = oper;
	}
	public Map<String, Object> getUserdata()
	{
		return userdata;
	}
	public void setUserdata(Map<String, Object> userdata)
	{
		this.userdata = userdata;
	}
}
