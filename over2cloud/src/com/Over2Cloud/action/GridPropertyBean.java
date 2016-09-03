package com.Over2Cloud.action;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GridPropertyBean extends ActionSupport implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	
	@SuppressWarnings("unchecked")
	public Map session = ActionContext.getContext().getSession();
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	public String userName = (String) session.get("uName");
	public String accountID = (String) session.get("accountid");
	public String orgLevel = (String) session.get("orgnztnlevel");
	public String orgId = (String) session.get("mappedOrgnztnId");
	public String deptLevel = (String) session.get("userDeptLevel");
	public SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	public  String userEmpId=Integer.toString(CommonWork.userEmpid((SessionFactory)session.get("connectionSpace"),userName));
    ///////////////////////////////////////////////////////////////
	public List<ConfigurationUtilBean>  pageFieldsColumns  = null;
	public List<GridDataPropertyView> viewPageColumns=null;
	//////////////////////////////////////////////////////////////
	
	public Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	public Integer page = 0;
	// sorting order - asc or desc
	public String sord = "";
	// get index row - i.e. user click to sort.
	public String sidx = "";
	// Search Field
	public String searchField = "";
	// The Search String
	public String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	public String searchOper = "";
	// Your Total Pages
	public Integer total = 0;
	// All Record
	public Integer records = 0;
	public boolean loadonce = true;
	// Grid colomn view
	public String oper;
	public String id;
	
	
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
	
	public String getOper()
	{
		return oper;
	}
	public void setOper(String oper)
	{
		this.oper = oper;
	}
	public boolean isLoadonce()
	{
		return loadonce;
	}
	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public List<ConfigurationUtilBean> getPageFieldsColumns() {
		return pageFieldsColumns;
	}
	public void setPageFieldsColumns(List<ConfigurationUtilBean> pageFieldsColumns) {
		this.pageFieldsColumns = pageFieldsColumns;
	}
	public List<GridDataPropertyView> getViewPageColumns() {
		return viewPageColumns;
	}
	public void setViewPageColumns(List<GridDataPropertyView> viewPageColumns) {
		this.viewPageColumns = viewPageColumns;
	}
}
