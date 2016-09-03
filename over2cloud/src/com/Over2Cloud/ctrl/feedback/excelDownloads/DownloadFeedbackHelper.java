package com.Over2Cloud.ctrl.feedback.excelDownloads;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.activity.ActivityBoardHelper;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;

public class DownloadFeedbackHelper
{
	private static FeedbackUniversalAction FUA = new FeedbackUniversalAction();
	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
	public List getQuerryList(List keyList, String feedBack,String ticket_no,String mode,String feedBy,String dept,String catg,String subCat,String status,String fromDate,String toDate,String wildSearch,String clientId,String userType,String loggedEmpId,String dept_subdept_id,String userName,SessionFactory connectionSpace)
	{
		ActivityBoardHelper helper = new ActivityBoardHelper();
		int adminDept=helper.getAdminDeptId(connectionSpace);
		StringBuilder query = new StringBuilder();
		if (keyList != null && keyList.size() > 0)
		{

			query.append("SELECT ");
			int i = 0;
			for (Iterator it = keyList.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();
				if (obdata != null)
				{
					if (i < keyList.size() - 1)
					{
						query.append(obdata.toString() + ",");
					}
					else
					{
						query.append(obdata.toString());
					}
				}
				i++;
			}
		}
		
		/*query.append("select feedback.clientId,feedback.feed_by,feedback.location, " + "feedback.via_from,feedback.open_date,feedback.open_time,feedback.status as fstatus, ");
		query.append("feedback.ticket_no as ticket_no, ");
		query.append("dept2.deptName as todept,emp.empName as allot_to,feedtype.fbType,catg.categoryName, ");
		query.append("subcatg.subCategoryName,feedback.feed_brief,");
		query.append("feedback.level as ropen_tat,subcatg.needEsc,feedback.feed_registerby,feedback.id,subcatg.id as subCatId");*/
		query.append(" from feedback_status as feedback");
		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
		query.append(" inner join department dept2 on feedback.to_dept_subdept= dept2.id");
		query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
		// query.append(
		// " inner join feedback_ticket as feedtkt on feedback.ticket_no=feedtkt.feedTicketNo"
		// );
		query.append(" where feedback.id !='0' and feedback.moduleName='FM'");

		if (userType != null && userType.equalsIgnoreCase("M"))
		{

		}
		else if (userType != null && userType.equalsIgnoreCase("H"))
		{
			List departList = FUA.getLoggedInEmpForDept(loggedEmpId, dept_subdept_id, "FM", connectionSpace);
			if (departList.size() > 0)
			{
				query.append(" and feedback.to_dept_subdept in " + departList.toString().replace("[", "(").replace("]", ")") + "");
			}
		}
		else
		{
			if (userName != null)
			{
				query.append(" and (feedback.feed_registerby='" + userName + "' or feedback.allot_to='" + loggedEmpId + "')");
			}
		}
		if (clientId != null && !clientId.equalsIgnoreCase(""))
		{
			query.append(" and feedback.clientId='" + clientId + "'");
		}
		else if (wildSearch != null && !wildSearch.equalsIgnoreCase(""))
		{

			query.append(" and feedback.feed_by like '" + wildSearch + "%'");
		}
		else
		{

			if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
			{
				String str[] = fromDate.split("-");
				if (str[0] != null && str[0].length() > 3)
				{
					query.append(" and feedback.open_date between '" + ((fromDate)) + "' and '" + ((toDate)) + "'");
				}
				else
				{
					query.append(" and feedback.open_date between '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' and '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
				}
			}
			if (status != null && !status.equalsIgnoreCase("-1"))
			{
				if(status != null && !status.equalsIgnoreCase("All"))
				{
					query.append(" and feedback.status= '" + status + "'");
				}
			}
			else
			{
				query.append(" and feedback.status='Pending'");
			}
			/*
			 * if(getPatType()!=null &&
			 * !getPatType().equalsIgnoreCase("-1")) {
			 * query.append(" and compType= '"+getPatType()+" '"); }
			 */
			if (mode != null && !mode.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.via_from= '" + mode + " '");
			}
			if (ticket_no != null && !ticket_no.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.ticket_no= '" + ticket_no + " '");
			}

			if (feedBy != null && !feedBy.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.feed_by= '" + feedBy + " '");
			}

			if (dept != null && !dept.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.to_dept_subdept='" + dept + " '");
			}

			if (catg != null && !catg.equalsIgnoreCase("-1") && !catg.equalsIgnoreCase(""))
			{
				query.append(" and catg.id='" + catg + " '");
			}

			if (subCat != null && !subCat.equalsIgnoreCase("-1"))
			{
				query.append(" and subcatg.id='" + subCat + " '");
			}

			if (feedBack != null && !feedBack.equalsIgnoreCase("-1"))
			{
				if (feedBack.equalsIgnoreCase("Negative"))
				{
					query.append(" and subcatg.needEsc= 'Y'");
				}
				else if (feedBack.equalsIgnoreCase("Positive"))
				{
					query.append(" and subcatg.needEsc= 'N'");
				}
			}
			else
			{
				if(adminDept!=0 && adminDept>0)
				{
					query.append(" and feedback.to_dept_subdept!='"+adminDept+"'");
				}
			}
		}
//		System.out.println("QUERRY is as "+query);
		List feedList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		return feedList;
	}
	
	public List getQuerryListRating4Download(List keyList, String feedBack,String ticket_no,String mode,String feedBy,String dept,String catg,String subCat,String status,String fromDate,String toDate,String wildSearch,String clientId,String userType,String loggedEmpId,String dept_subdept_id,String userName,SessionFactory connectionSpace)
	{
		ActivityBoardHelper helper = new ActivityBoardHelper();
		int adminDept=helper.getAdminDeptId(connectionSpace);
		StringBuilder query = new StringBuilder();
		if (keyList != null && keyList.size() > 0)
		{

			query.append("SELECT ");
			int i = 0;
			for (Iterator it = keyList.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();
				if (obdata != null)
				{
					if (i < keyList.size() - 1)
					{
						query.append(obdata.toString() + ",");
					}
					else
					{
						query.append(obdata.toString());
					}
				}
				i++;
			}
		}
		
		/*query.append("select feedback.clientId,feedback.feed_by,feedback.location, " + "feedback.via_from,feedback.open_date,feedback.open_time,feedback.status as fstatus, ");
		query.append("feedback.ticket_no as ticket_no, ");
		query.append("dept2.deptName as todept,emp.empName as allot_to,feedtype.fbType,catg.categoryName, ");
		query.append("subcatg.subCategoryName,feedback.feed_brief,");
		query.append("feedback.level as ropen_tat,subcatg.needEsc,feedback.feed_registerby,feedback.id,subcatg.id as subCatId");*/
		query.append(" from feedbackdata as feedback");
		query.append(" inner join feedback_paper_rating ratingIpd on feedback.id= ratingIpd.max_id_feeddbackdata");
		// query.append(
		// " inner join feedback_ticket as feedtkt on feedback.ticket_no=feedtkt.feedTicketNo"
		// );
		query.append(" where feedback.id !='0' ");

		
		if (clientId != null && !clientId.equalsIgnoreCase(""))
		{
			query.append(" and feedback.clientId='" + clientId + "'");
		}
		else if (wildSearch != null && !wildSearch.equalsIgnoreCase(""))
		{

			query.append(" and feedback.feed_by like '" + wildSearch + "%'");
		}
		else
		{
			if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
			{
				String str[] = fromDate.split("-");
				if (str[0] != null && str[0].length() > 3)
				{
					query.append(" and feedback.openDate between '" + ((fromDate)) + "' and '" + ((toDate)) + "'");
				}
				else
				{
					query.append(" and feedback.openDate between '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' and '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
				}
			}
			if (status != null && !status.equalsIgnoreCase("-1"))
			{
				if(status != null && !status.equalsIgnoreCase("All"))
				{
					query.append(" and feedback.status= '" + status + "'");
				}
			}
			else
			{
				query.append(" and feedback.status='Pending'");
			}
			
			if (mode != null && !mode.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.mode= '" + mode + " '");
			}

			if (feedBy != null && !feedBy.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.clientName= '" + feedBy + " '");
			}

			if (feedBack != null && !feedBack.equalsIgnoreCase("-1"))
			{
				if (feedBack.equalsIgnoreCase("Negative"))
				{
					query.append(" and feedback.targetOn= 'No'");
				}
				else if (feedBack.equalsIgnoreCase("Positive"))
				{
					query.append(" and feedback.targetOn= 'Yes'");
				}
			}
		}
	//System.out.println("Rating QUERRY is as "+query);
		List feedList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		return feedList;
	}
	
	public List getQuerryListRating(List keyList, String feedBack,String ticket_no,String mode,String feedBy,String dept,String catg,String subCat,String status,String fromDate,String toDate,String wildSearch,String clientId,String userType,String loggedEmpId,String dept_subdept_id,String userName,SessionFactory connectionSpace)
	{
		ActivityBoardHelper helper = new ActivityBoardHelper();
		int adminDept=helper.getAdminDeptId(connectionSpace);
		StringBuilder query = new StringBuilder();
		if (keyList != null && keyList.size() > 0)
		{

			query.append("SELECT ");
			int i = 0;
			for (Iterator it = keyList.iterator(); it.hasNext();)
			{
				Object obdata = (Object) it.next();
				if (obdata != null)
				{
					if (i < keyList.size() - 1)
					{
						query.append(obdata.toString() + ",");
					}
					else
					{
						query.append(obdata.toString());
					}
				}
				i++;
			}
		}
		
		/*query.append("select feedback.clientId,feedback.feed_by,feedback.location, " + "feedback.via_from,feedback.open_date,feedback.open_time,feedback.status as fstatus, ");
		query.append("feedback.ticket_no as ticket_no, ");
		query.append("dept2.deptName as todept,emp.empName as allot_to,feedtype.fbType,catg.categoryName, ");
		query.append("subcatg.subCategoryName,feedback.feed_brief,");
		query.append("feedback.level as ropen_tat,subcatg.needEsc,feedback.feed_registerby,feedback.id,subcatg.id as subCatId");*/
		query.append(" from feedback_status_feed_paperRating as feedback");
		query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
		query.append(" inner join department dept2 on feedback.to_dept_subdept= dept2.id");
		query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
		query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
		query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
		// query.append(
		// " inner join feedback_ticket as feedtkt on feedback.ticket_no=feedtkt.feedTicketNo"
		// );
		query.append(" where feedback.id !='0' and feedback.moduleName='FM'");

		if (userType != null && userType.equalsIgnoreCase("M"))
		{

		}
		else if (userType != null && userType.equalsIgnoreCase("H"))
		{
			List departList = FUA.getLoggedInEmpForDept(loggedEmpId, dept_subdept_id, "FM", connectionSpace);
			if (departList.size() > 0)
			{
				query.append(" and feedback.to_dept_subdept in " + departList.toString().replace("[", "(").replace("]", ")") + "");
			}
		}
		else
		{
			if (userName != null)
			{
				query.append(" and (feedback.feed_registerby='" + userName + "' or feedback.allot_to='" + loggedEmpId + "')");
			}
		}
		if (clientId != null && !clientId.equalsIgnoreCase(""))
		{
			query.append(" and feedback.clientId='" + clientId + "'");
		}
		else if (wildSearch != null && !wildSearch.equalsIgnoreCase(""))
		{

			query.append(" and feedback.feed_by like '" + wildSearch + "%'");
		}
		else
		{

			if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
			{
				String str[] = fromDate.split("-");
				if (str[0] != null && str[0].length() > 3)
				{
					query.append(" and feedback.open_date between '" + ((fromDate)) + "' and '" + ((toDate)) + "'");
				}
				else
				{
					query.append(" and feedback.open_date between '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' and '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
				}
			}
			
			if (status != null && !status.equalsIgnoreCase("-1"))
			{
				if(status != null && !status.equalsIgnoreCase("All"))
				{
					query.append(" and feedback.status= '" + status + "'");
				}
			}
			else
			{
				query.append(" and feedback.status='Pending'");
			}
			
			/*
			 * if(getPatType()!=null &&
			 * !getPatType().equalsIgnoreCase("-1")) {
			 * query.append(" and compType= '"+getPatType()+" '"); }
			 */
			if (mode != null && !mode.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.via_from= '" + mode + " '");
			}
			if (ticket_no != null && !ticket_no.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.ticket_no= '" + ticket_no + " '");
			}

			if (feedBy != null && !feedBy.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.feed_by= '" + feedBy + " '");
			}

			if (dept != null && !dept.equalsIgnoreCase("-1"))
			{
				query.append(" and feedback.to_dept_subdept='" + dept + " '");
			}

			if (catg != null && !catg.equalsIgnoreCase("-1") && !catg.equalsIgnoreCase(""))
			{
				query.append(" and catg.id='" + catg + " '");
			}

			if (subCat != null && !subCat.equalsIgnoreCase("-1"))
			{
				query.append(" and subcatg.id='" + subCat + " '");
			}

			if (feedBack != null && !feedBack.equalsIgnoreCase("-1"))
			{
				if (feedBack.equalsIgnoreCase("Negative"))
				{
					query.append(" and subcatg.needEsc= 'Y'");
				}
				else if (feedBack.equalsIgnoreCase("Positive"))
				{
					query.append(" and subcatg.needEsc= 'N'");
				}
			}
			else
			{
				if(adminDept!=0 && adminDept>0)
				{
					query.append(" and feedback.to_dept_subdept!='"+adminDept+"'");
				}
			}
		}
	//	System.out.println("QUERRY is as "+query);
		List feedList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		return feedList;
	}
}
