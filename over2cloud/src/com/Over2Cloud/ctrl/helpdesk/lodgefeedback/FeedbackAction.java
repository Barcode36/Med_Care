package com.Over2Cloud.ctrl.helpdesk.lodgefeedback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

@SuppressWarnings("serial")
public class FeedbackAction extends GridPropertyBean{
	
	private String feedStatus="";
	private String fromDate="";
	private String toDate="";
	private String d_subdept_id="";
	private String dataFlag = "";
	List<FeedbackPojo> feedbackList = null;
	private String backData;
	
	
	
	@SuppressWarnings("unchecked")
	public String getMoreFeedbackDetail()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				feedbackList = new ArrayList<FeedbackPojo>();

				// int count = 0 ;
				List data = null;
				String dept_subdept_id = "",emp_Name="",userType="";

				List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						emp_Name = object[0].toString();
						dept_subdept_id = object[3].toString();
						userType = object[7].toString();
					}
				}

				if (dept_subdept_id != null && !dept_subdept_id.equals(""))
				 {
					
						if (dataFlag.equals("M"))
						{
							//System.out.println("Inside If");
							toDate = DateUtil.convertDateToUSFormat(toDate);
							fromDate = DateUtil.convertDateToUSFormat(fromDate);
						}
						else
						{
							//System.out.println("Inside Else");
							toDate = toDate;
							fromDate = fromDate;
						}
						if (fromDate!=null && !fromDate.equals("") && !fromDate.equals("undefined")) {
							String []frmDateArr = fromDate.split("-");
							if (frmDateArr[0].length()<3) {
								fromDate=DateUtil.convertDateToUSFormat(fromDate);
								toDate=DateUtil.convertDateToUSFormat(toDate);
							}
						}
						
						
					//	data = HUA.getFeedbackDetail("feedback_status", getFeedStatus(), fromDate, toDate,dept_subdept_id, d_subdept_id, deptLevel, "feedback.id", "DESC", "HDM",emp_Name,userType, searchField, searchString, searchOper, connectionSpace);
						
						if (backData!=null && !backData.equals("") && backData.equalsIgnoreCase("Y")) {
							data = HUA.getFeedbackDetail("feedback_status_15072014", getFeedStatus(), fromDate, toDate, dept_subdept_id,"", deptLevel, "feedback.id", "DESC", "HDM",emp_Name,userType, searchField, searchString, searchOper, connectionSpace);
						}
						else {
							data = HUA.getFeedbackDetail("feedback_status", getFeedStatus(), fromDate, toDate,dept_subdept_id, d_subdept_id, deptLevel, "feedback.id", "DESC", "HDM",emp_Name,userType, searchField, searchString, searchOper, connectionSpace);
						}	
						
				 }
				if (data != null && data.size() > 0)
				{
					setRecords(data.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

				    feedbackList = new HelpdeskUniversalHelper().setFeedbackValues(data, deptLevel, getFeedStatus());
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				addActionError("Ooops!!! There is some problem in getting Feedback Data");
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	
	
	public String getFeedStatus() {
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus) {
		this.feedStatus = feedStatus;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getD_subdept_id() {
		return d_subdept_id;
	}

	public void setD_subdept_id(String d_subdept_id) {
		this.d_subdept_id = d_subdept_id;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public List<FeedbackPojo> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<FeedbackPojo> feedbackList) {
		this.feedbackList = feedbackList;
	}
	
	public String getBackData()
	{
		return backData;
	}

	public void setBackData(String backData)
	{
		this.backData = backData;
	}
	
}
