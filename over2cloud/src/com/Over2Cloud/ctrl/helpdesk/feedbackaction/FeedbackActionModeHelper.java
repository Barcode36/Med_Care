package com.Over2Cloud.ctrl.helpdesk.feedbackaction;

import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class FeedbackActionModeHelper 
{
	public List getTicketDetails(SessionFactory connection,String ticketId)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List dataList = null;
		try
		{
			String query = "SELECT feed_by,feed_by_mobno,feed_by_emailid,open_date,open_time,status,id,moduleName,deptHierarchy,feed_brief FROM feedback_status WHERE ticket_no = '"+ticketId+"'";
			dataList = cbt.executeAllSelectQuery(query, connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
	public List getEmpId(SessionFactory connection,String mobileNo)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List dataList = null;
		try
		{
			String query = "SELECT id FROM employee_basic WHERE mobOne = '"+mobileNo+"'";
			dataList = cbt.executeAllSelectQuery(query, connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}
}