package com.Over2Cloud.ctrl.feedback.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.modal.dao.imp.feedbackDashboard.FeedbackDashboardDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class ActionForFeedbackCtrl extends ActionSupport
{
	private String feedDataId;
	private String actionName;
	private String comments;
	private static CommonOperInterface cbt=new CommonConFactory().createInterface();
	
	public String takeActionOnFeedback()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				
				Map session=ActionContext.getContext().getSession();
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				System.out.println("Action Name is as "+getActionName());
				boolean openTicket=false;
				String actionName=null;
				StringBuilder builder=new StringBuilder("select name from actioninfo where id='"+getActionName()+"'");
				List data=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
				if(data!=null && data.size()>0 && data.get(0)!=null)
				{
					if(data.get(0).toString().equalsIgnoreCase("Take Action"))
					{
						actionName="openTicket";
					}
					else if(data.get(0).toString().equalsIgnoreCase("Snooze"))
					{
						actionName="snooze";
					}
					else if(data.get(0).toString().equalsIgnoreCase("No Further Action Required"))
					{
						openTicket=false;
						actionName=data.get(0).toString();
					}
				}
				
				
				if(getFeedDataId()!=null && getActionName()!=null && getComments()!=null && !openTicket)
				{
					System.out.println("ActionName :::::"+actionName);
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					
					wherClause.put("status",actionName);
					wherClause.put("actionComments",getComments());
					
					condtnBlock.put("id",getFeedDataId());
					openTicket=	new FeedbackUniversalHelper().updateTableColomn("feedbackdata", wherClause, condtnBlock,connectionSpace);
					if(openTicket)
					{
						wherClause.clear();
						condtnBlock.clear();
						String feedStatId=new FeedbackDashboardDao().getFeedStatusIdForFeedback(getFeedDataId(),connectionSpace);
						String openDate="";
						String openTime="";
						System.out.println("feedStatIdis as "+feedStatId);
						if(feedStatId!=null && !feedStatId.equalsIgnoreCase(""))
						{
							openDate=new FeedbackDashboardDao().getTableColumn("open_date","feedback_status","id",feedStatId,connectionSpace);
							openTime=new FeedbackDashboardDao().getTableColumn("open_time","feedback_status","id",feedStatId,connectionSpace);
							System.out.println(openDate+">>>>>>>>>>>>>>>>>>>>>>>>>>>"+openTime);
							openTicket=updateFeedStatTableForFeedback(openDate,openTime,feedStatId,actionName,getComments(),connectionSpace);
							if(openTicket)
							{
								System.out.println("Action Taken Sucessfully");
								addActionMessage("Action Taken Successfully !!! ");
							}
							else
							{
								System.out.println("Problem in Action Taken");
								addActionMessage("Problem in Taking Action !!! ");
							}
						}
					}
					return SUCCESS;
				}
				return SUCCESS;
			}
			catch (Exception e) {
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public boolean updateFeedStatTableForFeedback(String open_date,String open_time,String feedid,String actionName,String comments,SessionFactory connectionSpace)
	{
		Map session = ActionContext.getContext().getSession();
		boolean updated=false;
		try
		{
			System.out.println("ActionName Method:::::"+actionName);
			
			
			String userName=(String)session.get("uName");
			String duration="";
			String snDate="",snTime="",snUpToDate="",snUpToTime="",snDuration="";
			List ticketData = new HelpdeskUniversalAction().getMultipleColumns("feedback_status", "sn_on_date", "sn_on_time", "sn_upto_date","sn_upto_time","sn_duration", "id",feedid, "", "", connectionSpace);
			if (ticketData!=null && ticketData.size()>0)
			{
				for (Iterator iterator = ticketData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0]!=null && !object[0].toString().equals(""))
					{
						snDate=object[0].toString();
					}
					else 
					{
				      snDate="NA";
					}
					if (object[1]!=null && !object[1].toString().equals(""))
					{
						snTime=object[1].toString();
					}
					else
					{
						snTime="NA";
					}
					if (object[2]!=null && !object[2].toString().equals(""))
					{
						snUpToDate=object[2].toString();
					}
					else
					{
						snUpToDate="NA";
					}
					if (object[3]!=null && !object[3].toString().equals(""))
					{
						snUpToTime=object[3].toString();
					}
					else 
					{
						snUpToTime="NA";
					}
					if (object[4]!=null && !object[4].toString().equals(""))
					{
						snDuration=object[4].toString();
					}
					else 
					{
						snDuration="NA";
					}
				}
			}
			

			   //Get resolver Id which is already a alloted person
			   String resolver=new FeedbackUniversalAction().getField("feedback_status", "allot_to", "id", feedid,connectionSpace);
			   System.out.println("Resolver :::::"+resolver);
			   String cal_duration="";
			   if (open_date != null && !open_date.equals("") && open_time != null && !open_time.equals(""))
			   {
				   duration = DateUtil.time_difference(DateUtil.convertDateToUSFormat(open_date), open_time, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
			   }
			   if (!snDuration.equals("") && !snDuration.equals("NA"))
			   {
				   boolean flag = DateUtil.time_update(snUpToDate, snUpToTime);
				   if (flag)
				   {
					   //System.out.println("Inside If Block");
					   cal_duration= DateUtil.getTimeDifference(duration, snDuration);
					   if (cal_duration!=null && !cal_duration.equals("") && !cal_duration.contains("-"))
					   {
						   duration=cal_duration;
					   }
				   }
				   else
				   {
					   //System.out.println("Inside Else Block");
					   String newduration = DateUtil.time_difference(snDate, snTime, DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
					   if (newduration!=null && !newduration.equals("") && !newduration.equals("NA"))
					   {
						   String new_cal_duration= DateUtil.getTimeDifference(duration, newduration);
						   if (new_cal_duration!=null && !new_cal_duration.equals("") && !new_cal_duration.contains("-"))
						   {
							   duration=new_cal_duration;
						   }
					   }
				   }
			   }
			   System.out.println(actionName+""+duration);
			   Map<String, Object>wherClause=new HashMap<String, Object>();
			   Map<String, Object>condtnBlock=new HashMap<String, Object>();
				
			   wherClause.put("status", "Resolved");
			   wherClause.put("action_by", userName);
			   wherClause.put("resolve_date", DateUtil.getCurrentDateUSFormat());
			   wherClause.put("resolve_time", DateUtil.getCurrentTime());
			   wherClause.put("resolve_duration", duration);
			   wherClause.put("resolve_remark", DateUtil.makeTitle(actionName));
			   wherClause.put("resolve_by", resolver);
			   wherClause.put("spare_used", "No");
			   condtnBlock.put("id",feedid);
			
			   updated=	new FeedbackUniversalHelper().updateTableColomn("feedback_status", wherClause, condtnBlock,connectionSpace);
			   
		}
		  catch (Exception e) {
			  e.printStackTrace();
			// TODO: handle exception
		}
		return updated;
	}
	
	public String getFeedDataId() {
		return feedDataId;
	}
	public void setFeedDataId(String feedDataId) {
		this.feedDataId = feedDataId;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
