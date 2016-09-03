package com.Over2Cloud.ctrl.feedback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.CustomerPojo;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FeedbackResponseModesCtrl extends ActionSupport
{
	static final Log log = LogFactory.getLog(FeedbackResponseModesCtrl.class);
	private String mainHeaderName;
	private List<GridDataPropertyView> CRMColumnNames;
	private String mode;
	private List<CustomerPojo> custDataList;
	
	public String beforeResModeView()
    {
        boolean valid=ValidateSession.checkSession();
        if(valid)
        {
            try
            {
            	if(getMode()!=null && getMode().equalsIgnoreCase("App"))
            	{
            		setMainHeaderName("Feedback >>Response Mobile App "); 
                	setGridColomnNamesNegative();
            	}
            	else if(getMode()!=null && getMode().equalsIgnoreCase("Online"))
            	{
            		setMainHeaderName("Feedback >>Response Online "); 
                	setGridColomnNamesNegative();
            	}
            	else if(getMode()!=null && getMode().equalsIgnoreCase("SMS"))
            	{
            		setMainHeaderName("Feedback >>Response SMS "); 
                	setGridColomnNamesNegative();
            	}
            	else if(getMode()!=null && getMode().equalsIgnoreCase("Voice"))
            	{
            		setMainHeaderName("Feedback >>Response Voice "); 
                	setGridColomnNamesNegative();
            	}
                return SUCCESS;
            }
            catch(Exception e)
            {
                 e.printStackTrace();
                 log.error("Problem in method beforeResModeView of class"+getClass()+" on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimeHourMin(),e);
                 return ERROR;
            }
        }
        else
        {
            return LOGIN;
        }
    }
	
	public String viewCustomerResInGrid()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{

                CommonOperInterface cbt=new CommonConFactory().createInterface();
                {
                	custDataList=new ArrayList<CustomerPojo>();
                	Map session = ActionContext.getContext().getSession();
                	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
                	
                     StringBuilder queryNew2=new StringBuilder("");
                     if(getMode()!=null)
                     {
                    	// System.out.println(">>>>>>>>>>>>Online"+getMode());
                    	 queryNew2.append("select feedbt.id as feedback_ticketId,feedbt.feedTicketNo,feedData.openDate,feedData.openTime," +
                    	 		" feedbck.escalation_date,feedbck.escalation_time,feedbck.level,feedData.clientId,feedData.clientName,feedData.mobNo," +
                    	 		" feedData.emailId,feedData.refNo,feedData.centerCode,feedData.centreName, feedData.id,feedData.problem," +
                    	 		" feedData.compType,feedData.handledBy,feedData.remarks," +
                    	 		" feedData.kword,feedData.ip from feedback_ticket as feedbt" +
                    	 		" inner join feedback_status as feedbck on feedbt.feed_stat_id=feedbck.id " +
                    	 		" inner join feedbackdata as feedData on feedbt.feed_data_id=feedData.id where (feedData.targetOn='No' || feedData.targetOn='Yes') && feedData.mode='"+getMode()+"'");
                    	 
                    	 queryNew2.append(" order by feedData.openDate DESC");
                     }
                  //   System.out.println("Querry is as >>>>>>>>>>>>>>>>"+queryNew2);
                     List ticketDataList=cbt.executeAllSelectQuery(queryNew2.toString(),connectionSpace);
                    // System.out.println("size of the list ticketDataList>>>>>>"+ticketDataList.size());
                     List<CustomerPojo> tempList=new ArrayList<CustomerPojo>();
                     int i=1;
                     if(ticketDataList!=null && ticketDataList.size()>0)
                        {
                            for (Iterator iterator = ticketDataList.iterator(); iterator .hasNext();) 
                            {
                            	CustomerPojo feed=new CustomerPojo();
                                Object[] type = (Object[]) iterator.next();

                            	
                            	if(type[14]!=null)
                            	{
                            		feed.setId(Integer.parseInt(type[14].toString()));
                            	}
                            	
                            	if(type[0]!=null)
                            	{
                            		feed.setFeedTicketId(Integer.parseInt(type[0].toString()));
                            	}
                            	if(type[1]!=null)
                            	{
                            		feed.setTicketNo(type[1].toString());
                            	}
                            	if(type[2]!=null)
                            	{
                            		feed.setOpenDate(DateUtil.convertDateToIndianFormat(type[2].toString()));
                            	}
                            	
                            	if(type[3]!=null)
                            	{
                            		feed.setOpenTime(type[3].toString());
                            	}
                            	
                            	if(type[4]!=null)
                            	{
                            		feed.setEscalationDate(DateUtil.convertDateToIndianFormat(type[4].toString()));
                            	}
                            	
                            	if(type[5]!=null)
                            	{
                            		feed.setEscalationTime(type[5].toString());
                            	}
                            	
                            	if(type[6]!=null)
                            	{
                            		feed.setLevel(type[6].toString());
                            	}
                            	
                            	if(type[7]!=null)
                            	{
                            		feed.setClientId(type[7].toString());
                            	}
                            	
                            	if(type[8]!=null)
                            	{
                            		feed.setClientName(type[8].toString());
                            	}
                            	
                            	if(type[9]!=null)
                            	{
                            		feed.setMobNo(type[9].toString());
                            	}
                            	
                            	if(type[10]!=null)
                            	{
                            		feed.setEmailId(type[10].toString());
                            	}
                            	
                            	if(type[11]!=null)
                            	{
                            		feed.setRefNo(type[12].toString());
                            	}
                            	
                            	if(type[12]!=null)
                            	{
                            		feed.setCentreCode(type[12].toString());
                            	}
                            	
                            	if(type[13]!=null)
                            	{
                            		feed.setCentreName(type[13].toString());
                            	}
                            	
                            	if(type[15]!=null)
                            	{
                            		feed.setProblem(type[15].toString());
                            	}
                            	
                            	if(type[16]!=null)
                            	{
                            		feed.setComplaintType(type[16].toString());
                            	}
                            	
                            	if(type[17]!=null)
                            	{
                            		feed.setHandledBy(type[17].toString());
                            	}
                            	
                            	if(type[18]!=null)
                            	{
                            		feed.setRemarks(type[18].toString());
                            	}
                            	
                            	if(type[19]!=null)
                            	{
                            		feed.setKeyWord(type[19].toString());
                            	}
                            	
                            	if(type[20]!=null)
                            	{
                            		feed.setIp(type[20].toString());
                            	}
                            
                                tempList.add(feed);
                            }
                            setCustDataList(tempList);
                            }
                        }
                return SUCCESS;
			}
			catch (Exception e) {
				e.printStackTrace();
                log.error("Problem in method viewCustomerResInGrid of class "+getClass()+" on "+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTimeHourMin(),e);
                return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	private void setGridColomnNamesNegative()
	{

    	CRMColumnNames=new ArrayList<GridDataPropertyView>();
    	GridDataPropertyView gpv=new GridDataPropertyView();
	
    	gpv.setColomnName("id");
		gpv.setHeadingName("ID");
		gpv.setHideOrShow("true");
		gpv.setAlign("center");
		gpv.setFrozenValue("false") ;
		CRMColumnNames.add(gpv); 
		
		gpv.setColomnName("feedback_ticketId");
		gpv.setHeadingName("id");
		gpv.setKey("true");
		gpv.setHideOrShow("true");
		gpv.setAlign("center");
		gpv.setFrozenValue("false") ;
		CRMColumnNames.add(gpv); 
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("ticketNo");
		gpv.setHeadingName("Ticket No");
		gpv.setFrozenValue("false");
		gpv.setAlign("center");
		gpv.setWidth(100);
		gpv.setFormatter("ticketNo2");
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("openDate");
		gpv.setHeadingName("Open Date");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("openTime");
		gpv.setHeadingName("Open Time");
		gpv.setAlign("center");
		gpv.setWidth(70);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("escalationDate");
		gpv.setHeadingName("Esc Date");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("escalationTime");
		gpv.setHeadingName("Esc Time");
		gpv.setAlign("center");
		gpv.setWidth(70);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("level");
		gpv.setHeadingName("Level");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("clientId");
		gpv.setHeadingName("Customer Id");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("clientName");
		gpv.setHeadingName("Customer Name");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("mobNo");
		gpv.setHeadingName("Mobile No");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("emailId");
		gpv.setHeadingName("Email");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("refNo");
		gpv.setHeadingName("Reference No");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("centreCode");
		gpv.setHeadingName("Centre Code");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("centreName");
		gpv.setHeadingName("Centre Name");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("problem");
		gpv.setHeadingName("Problem");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("complaintType");
		gpv.setHeadingName("Complaint Type");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("handledBy");
		gpv.setHeadingName("Handled By");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("remarks");
		gpv.setHeadingName("Remarks");
		gpv.setAlign("center");
		gpv.setWidth(100);
		CRMColumnNames.add(gpv);
		
		gpv=new GridDataPropertyView();
		gpv.setColomnName("keyWord");
		gpv.setHeadingName("Keyword");
		gpv.setAlign("center");
		gpv.setWidth(70);
		CRMColumnNames.add(gpv);
	}
	public String getMainHeaderName() {
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}

	public List<GridDataPropertyView> getCRMColumnNames() {
		return CRMColumnNames;
	}

	public void setCRMColumnNames(List<GridDataPropertyView> columnNames) {
		CRMColumnNames = columnNames;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<CustomerPojo> getCustDataList() {
		return custDataList;
	}

	public void setCustDataList(List<CustomerPojo> custDataList) {
		this.custDataList = custDataList;
	}
}
