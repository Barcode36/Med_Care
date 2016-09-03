package com.Over2Cloud.ctrl.feedback.report;

import java.math.BigInteger;
import java.util.ArrayList;
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
import com.Over2Cloud.ctrl.feedback.common.TicketActionHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class TicketActionReport implements Action
{

	private String ticketNo;
	private List<GridDataPropertyView> masterViewProp;
	private List<Object> masterViewList;
	
	
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
	
	public String showCompleteDetails()
	{
		boolean validSession=ValidateSession.checkSession();
		if(validSession)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
	   		 	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	   		 	String accountID=(String)session.get("accountid");
				
	   		 	CommonOperInterface cbt=new CommonConFactory().createInterface();
	   		 
	   		 	StringBuffer query=new StringBuffer("select count(*) from feedback_status where ticket_no like '"+ticketNo+"%' && moduleName='FM'");
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
		 			
		 			List data=new TicketActionHelper().getFullDetailsForTicket(getTicketNo(), connectionSpace);
		 			List<Object> listhb=new ArrayList<Object>();
		 			
		 			if(data!=null)
		 			{
		 				for (Iterator iterator = data.iterator(); iterator .hasNext();)
		 				{
		 					Object[] obdata = (Object[]) iterator.next();
		 					FeedbackPojo  fbp = new FeedbackPojo();
		 					
		 					fbp.setId((Integer)obdata[0]);
		 					
		 					if (obdata[1]!=null && !obdata[1].toString().equals(""))
		 					{
		 						fbp.setTicket_no(obdata[1].toString());
		 					}
		 					else
		 					{
		 						fbp.setTicket_no("NA");
		 					}
		 					
		 					if (obdata[2]!=null && !obdata[2].toString().equals(""))
		 					{
		 						fbp.setFeedback_by_dept(obdata[2].toString());
		 					}
		 					else
		 					{
		 						fbp.setFeedback_by_dept("NA");
		 					}
		 					
		 					if (obdata[3]!=null && !obdata[3].toString().equals(""))
		 					{
		 						fbp.setFeed_by(obdata[3].toString());
		 					}
		 					else
		 					{
		 						fbp.setFeed_by("NA");
		 					}
		 		 
		 					if (obdata[4]!=null && !obdata[4].toString().equals("")) 
		 					{
		 						fbp.setFeedback_by_mobno(obdata[4].toString());
		 					}
		 					else
		 					{
		 						fbp.setFeedback_by_mobno("NA");
		 					}
		 		
		 					if (obdata[5]!=null && !obdata[5].toString().equals("")) 
		 					{
		 						fbp.setFeedback_by_emailid(obdata[5].toString());
		 					}
		 					else
		 					{
		 						fbp.setFeedback_by_emailid("NA");
		 					}
		 					
		 					if (obdata[6]!=null && !obdata[6].toString().equals("")) 
		 					{
		 						fbp.setFeedback_to_dept(obdata[6].toString());
		 					}
		 					else
		 					{
		 						fbp.setFeedback_to_dept("NA");
		 					}
		 					
		 					
		 					if (obdata[7]!=null && !obdata[7].toString().equals("")) 
		 					{
		 						fbp.setFeedback_allot_to(obdata[7].toString());
		 					}
		 					else
		 					{
		 						fbp.setFeedback_allot_to("NA");
		 					}
		 					
		 					if (obdata[8]!=null && !obdata[8].toString().equals("")) 
		 					{
		 						fbp.setFeedback_catg(obdata[8].toString());
		 					}
		 					else
		 					{
		 						fbp.setFeedback_catg("NA");
		 					}
		 					
		 					if (obdata[9]!=null && !obdata[9].toString().equals("")) 
		 					{
		 						fbp.setFeedback_subcatg(obdata[9].toString());
		 					}
		 					else
		 					{
		 						fbp.setFeedback_subcatg("NA");
		 					}
		 					
		 					
		 					if (obdata[10]!=null && !obdata[10].toString().equals(""))
		 					{
		 						fbp.setFeed_brief(obdata[10].toString());
		 					}
		 					else
		 					{
		 						fbp.setFeed_brief("NA");
		 					}

		 					if (obdata[11]!=null && !obdata[11].toString().equals(""))
		 					{
		 						fbp.setFeedaddressing_time(obdata[11].toString());
		 					}
		 					else
		 					{
		 						fbp.setFeedaddressing_time("NA");
		 					}
		 		
		 					if (obdata[11]!=null && !obdata[11].toString().equals("") && obdata[12]!=null && !obdata[12].toString().equals("") && obdata[13]!=null && !obdata[13].toString().equals(""))
		 					{
		 						String addr_date_time = DateUtil.newdate_AfterTime(obdata[12].toString(), obdata[13].toString(), obdata[11].toString());
		 						String[] add_date_time = addr_date_time.split("#");
		 						for (int i = 0; i < add_date_time.length; i++)
		 						{
		 							fbp.setFeedaddressing_date(DateUtil.convertDateToIndianFormat(add_date_time[0]));
		 							fbp.setFeedaddressing_time(add_date_time[1].substring(0, 5));
		 						}
		 					}
		 					else
		 					{
		 						fbp.setFeedaddressing_date("NA");
		 						fbp.setFeedaddressing_time("NA");
		 					}
		 		
		 					if (obdata[12]!=null && !obdata[12].toString().equals(""))
		 					{
		 						fbp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[12].toString()));
		 					}
		 					else
		 					{
		 						fbp.setOpen_date("NA");
		 					}
		 					
		 					if (obdata[13]!=null && !obdata[13].toString().equals(""))
		 					{
		 						fbp.setOpen_time(obdata[13].toString().substring(0, 5));
		 					}
		 					else
		 					{
		 						fbp.setOpen_time("NA");
		 					}
		 					
		 					
		 					if(obdata[14]!=null)
		 					{
		 						fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[14].toString()));
		 					}
		 					else
		 					{
		 						fbp.setEscalation_date("NA");
		 					}
		 					
		 					if(obdata[15]!=null)
		 					{
		 						fbp.setEscalation_time(obdata[15].toString().substring(0, 5));
		 					}
		 					else
		 					{
		 						fbp.setEscalation_time("NA");
		 					}
		 					
		 					if(obdata[16]!=null)
		 					{
		 						fbp.setLevel(obdata[16].toString());
		 					}
		 					else
		 					{
		 						fbp.setLevel("NA");
		 					}
		 		  
		 					if(obdata[17]!=null)
		 					{
		 						fbp.setStatus(obdata[17].toString());
		 					}
		 					else
		 					{
		 						fbp.setStatus("NA");
		 					}
		 					
		 					if(obdata[18]!=null)
		 					{
		 						fbp.setVia_from(obdata[18].toString());
		 					}
		 					else
		 					{
		 						fbp.setVia_from("NA");
		 					}
		 					
		 					if(obdata[19]!=null)
		 					{
		 						fbp.setFeed_registerby(DateUtil.makeTitle(obdata[19].toString()));
		 					}
		 					else
		 					{
		 						fbp.setFeed_registerby("NA");
		 					}
		 					
		 					if(obdata[24]!=null && !obdata[24].toString().equals(""))
		 					{
		 						fbp.setLocation(obdata[24].toString());
		 					}
		 					
		 					if(obdata[25]!=null && !obdata[25].toString().equals(""))
		 					{
		 						fbp.setFeedback_remarks(obdata[25].toString());
		 					}
		 					
		 					if (obdata[26]!=null && !obdata[26].toString().equals("")) 
		 					{
		 						fbp.setResolve_date(DateUtil.convertDateToIndianFormat(obdata[26].toString()));
		 					}
		 					else
		 					{
		 						fbp.setResolve_date("NA");
		 					}
		 					
		 					if (obdata[27]!=null && !obdata[27].toString().equals(""))
		 					{
		 						fbp.setResolve_time(obdata[27].toString().substring(0, 5));
		 					}
		 					else
		 					{
		 						fbp.setResolve_time("NA");
		 					}
		 		
		 					if (obdata[28]!=null && !obdata[28].equals(""))
		 					{
		 						fbp.setResolve_duration(obdata[28].toString());
		 					}
		 					else
		 					{
		 						if (obdata[17]!=null && !obdata[17].toString().equals("") && obdata[18]!=null && !obdata[18].toString().equals("") && obdata[26]!=null && !obdata[26].toString().equals("") && obdata[27]!=null && !obdata[27].toString().equals("")) 
		 						{
		 							String dura1=DateUtil.time_difference(obdata[17].toString(), obdata[18].toString(), obdata[26].toString(), obdata[27].toString());
		 							fbp.setResolve_duration(dura1);
		 						}
		 						else
		 						{
		 							fbp.setResolve_duration("NA");
		 						}
		 					}
		 		 
		 					if (obdata[29]!=null && !obdata[29].toString().equals("")) 
		 					{
		 						fbp.setResolve_remark(obdata[29].toString());
		 					}
		 					else 
		 					{
		 						fbp.setResolve_remark("NA");
		 					}
		 						
		 					fbp.setResolve_by(obdata[30].toString());
		 					fbp.setSpare_used(obdata[31].toString());
		 					
		 					if (obdata[23]!=null && !obdata[23].toString().equals("")) 
		 					{
		 						fbp.setAction_by(DateUtil.makeTitle(obdata[23].toString()));
		 					}
		 					else
		 					{
		 						fbp.setAction_by("NA");
		 					}
		 					
		 					if(obdata[24]!=null)
		 					{
		 						fbp.setLocation(obdata[24].toString());
		 					}
		 					
		 					listhb.add(fbp);
		 				}
		 				
		 				setMasterViewList(listhb);
	                    setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
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
	
	
	public void setActionDetailsViewProp()
    {
    	try
    	{

    		Map session=ActionContext.getContext().getSession();
   		 	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
   		 	String accountID=(String)session.get("accountid");
   		 	
    		masterViewProp=new ArrayList<GridDataPropertyView>();
            GridDataPropertyView gpv=new GridDataPropertyView();
            gpv.setColomnName("id");
            gpv.setHeadingName("Id");
            gpv.setHideOrShow("true");
            masterViewProp.add(gpv);
            
            List<GridDataPropertyView>returnResult=Configuration.getConfigurationData("mapped_feedback_ticket_lodge_configuration",accountID,connectionSpace,"",0,"table_name","feedback_ticket_lodge_configuration");
            
          //  System.out.println("returnResult is as >>>>>>>>>>>>>>>>>>>>>>>>>>>>."+returnResult.size());
            for(GridDataPropertyView gdp:returnResult)
            {
            	gpv=new GridDataPropertyView();
                    gpv.setColomnName(gdp.getColomnName());
                    gpv.setHeadingName(gdp.getHeadingName());
                    gpv.setEditable(gdp.getEditable());
                    gpv.setSearch(gdp.getSearch());
                    gpv.setHideOrShow(gdp.getHideOrShow());
                    gpv.setFormatter(gdp.getFormatter());
                    gpv.setWidth(gdp.getWidth());
                    masterViewProp.add(gpv);
            }
    	}
    	catch (Exception e) 
    	{
    		e.printStackTrace();
    	}
    }
	
	
	public String beforeTicketDetailsView()
	{
		boolean session=ValidateSession.checkSession();
		if(session)
		{
			try
			{
				setActionDetailsViewProp();
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

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return SUCCESS;
	}
	public List<GridDataPropertyView> getMasterViewProp() {
		return masterViewProp;
	}
	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp) {
		this.masterViewProp = masterViewProp;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
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
	public List<Object> getMasterViewList() {
		return masterViewList;
	}
	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}
}
