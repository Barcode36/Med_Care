package com.Over2Cloud.ctrl.feedback.report;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FeedbackActionReport extends ActionSupport
{
	private String id;
	private String ticketNo;
	private String status;
	private String patName;
	private String crNo;
	private String mobNo;
	
	private final static CommonOperInterface cbt=new CommonConFactory().createInterface();
	
	
	List<GridDataPropertyView> feedbackColumnNames = new ArrayList<GridDataPropertyView>();
	List<FeedbackPojo> feedbackList = new ArrayList<FeedbackPojo>();
	
	public String actionTakenForFeedback()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				
				
				List nameData=cbt.executeAllSelectQuery("select clientName,clientId,mobNo from feedbackdata where id='"+getId()+"'", connectionSpace);
				if(nameData!=null && nameData.size()>0)
				{
					
					for (Iterator iterator = nameData.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						if(object!=null)
						{
							if(object[0]!=null)
							{
								patName=object[0].toString();
							}
							
							if(object[1]!=null)
							{
								crNo=object[1].toString();
							}
							
							if(object[2]!=null)
							{
								mobNo=object[2].toString();
							}
						}
					}
				}
				
				List data=cbt.executeAllSelectQuery("select distinct feedTicketNo from feedback_ticket where feed_data_id='"+getId()+"'", connectionSpace);
				if(data!=null && data.size()>0 && data.get(0)!=null)
				{
					ticketNo=data.get(0).toString();
				}
				
				if(ticketNo!=null)
				{
					List dataList=cbt.executeAllSelectQuery("select distinct status from feedback_status where ticket_no='S1000'", connectionSpace);
					if(dataList!=null & dataList.size()>0 && dataList.get(0)!=null)
					{
						status=dataList.get(0).toString();
					}
				}
				
				System.out.println("Status is as :::: "+getStatus());
				System.out.println("Ticket No is as ::"+getTicketNo());
				
				setGridColomnNames();
				
				return SUCCESS;
			}
			catch (Exception e) 
			{
				return ERROR;
			}
			
		}
		else
		{
			return LOGIN;
		}
	}
	
	public void setGridColomnNames()
	{
		try
		{

			GridDataPropertyView gpv=new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("ID");
			gpv.setKey("true");
			gpv.setHideOrShow("true");
			gpv.setAlign("center");
			gpv.setFrozenValue("false");
			feedbackColumnNames.add(gpv); 
			
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("ticket_no");
			gpv.setHeadingName("Ticket No");
			gpv.setFrozenValue("false");
			gpv.setAlign("center");
			gpv.setWidth(70);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("status");
			gpv.setHeadingName("Status");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("open_date");
			gpv.setHeadingName("Open Date");
			gpv.setAlign("center");
			gpv.setWidth(70);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("open_time");
			gpv.setHeadingName("Open Time");
			gpv.setAlign("center");
			gpv.setWidth(70);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("feedaddressing_date");
			gpv.setHeadingName("Response Date");
			gpv.setAlign("center");
			gpv.setWidth(70);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("feedaddressing_time");
			gpv.setHeadingName("Response Time");
			gpv.setAlign("center");
			gpv.setWidth(70);
			feedbackColumnNames.add(gpv);
			
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("escalation_date");
			gpv.setHeadingName("Esc Date");
			gpv.setAlign("center");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("escalation_time");
			gpv.setHeadingName("Esc Time");
			gpv.setAlign("center");
			gpv.setWidth(80);
			feedbackColumnNames.add(gpv);
			
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("feed_by");
			gpv.setHeadingName("Patient Name");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("location");
			gpv.setHeadingName("Location");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("feed_brief");
			gpv.setAlign("center");
			gpv.setHeadingName("Feedback/Complaint");
			feedbackColumnNames.add(gpv);
			
				gpv=new GridDataPropertyView();
				gpv.setColomnName("feed_registerby");
				gpv.setHeadingName("Register By");
				gpv.setAlign("center");
				feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("feedback_by_dept");
			gpv.setHeadingName("By Dept");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("feedback_by_mobno");
			gpv.setHeadingName("Mobile No");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("feedback_by_emailid");
			gpv.setHeadingName("Email Id");
			gpv.setHideOrShow("true");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("feedback_to_dept");
			gpv.setHeadingName("To Department");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("feedback_allot_to");
			gpv.setHeadingName("Allot To");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("feedback_catg");
			gpv.setHeadingName("Category");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("feedback_subcatg");
			gpv.setHeadingName("Sub Category");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
				gpv=new GridDataPropertyView();
				gpv.setColomnName("resolve_date");
				gpv.setHeadingName("Resolved On");
				gpv.setAlign("center");
				gpv.setWidth(100);
				feedbackColumnNames.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("resolve_time");
				gpv.setHeadingName("Resolved At");
				gpv.setAlign("center");
				gpv.setWidth(100);
				feedbackColumnNames.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("resolve_duration");
				gpv.setHeadingName("Res. Duration");
				gpv.setAlign("center");
				gpv.setWidth(100);
				feedbackColumnNames.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("resolve_by");
				gpv.setHeadingName("Resolve By");
				gpv.setAlign("center");
				gpv.setWidth(100);
				feedbackColumnNames.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("resolve_remark");
				gpv.setHeadingName("CAPA");
				gpv.setAlign("center");
				gpv.setWidth(100);
				feedbackColumnNames.add(gpv);
				
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("spare_used");
				gpv.setHeadingName("RCA");
				gpv.setAlign("center");
				gpv.setWidth(100);
				feedbackColumnNames.add(gpv);
			
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("level");
			gpv.setHeadingName("Level");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("via_from");
			gpv.setHeadingName("Via From");
			gpv.setAlign("center");
			gpv.setWidth(100);
			feedbackColumnNames.add(gpv);
			
			gpv=new GridDataPropertyView();
			gpv.setColomnName("action_by");
			gpv.setHeadingName("Action By");
			gpv.setAlign("center");
			feedbackColumnNames.add(gpv);
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Hello ");
	}
	
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<GridDataPropertyView> getFeedbackColumnNames() {
		return feedbackColumnNames;
	}
	public void setFeedbackColumnNames(
			List<GridDataPropertyView> feedbackColumnNames) {
		this.feedbackColumnNames = feedbackColumnNames;
	}
	public List<FeedbackPojo> getFeedbackList() {
		return feedbackList;
	}
	public void setFeedbackList(List<FeedbackPojo> feedbackList) {
		this.feedbackList = feedbackList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getPatName() {
		return patName;
	}

	public void setPatName(String patName) {
		this.patName = patName;
	}

	public String getCrNo() {
		return crNo;
	}

	public void setCrNo(String crNo) {
		this.crNo = crNo;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}
	
}
