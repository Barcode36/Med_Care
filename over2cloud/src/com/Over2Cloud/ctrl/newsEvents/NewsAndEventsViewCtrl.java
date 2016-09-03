package com.Over2Cloud.ctrl.newsEvents;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.NewsAlertsPojo;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.modal.dao.imp.feedbackDashboard.FeedbackDashboardDao;
import com.opensymphony.xwork2.ActionContext;

public class NewsAndEventsViewCtrl {
	private List<NewsAlertsPojo> newsAlertsList;
	private String mainHeaderName;
	public String viewAlertsHomePage()
	{
		boolean validFlag=ValidateSession.checkSession();
		if(validFlag)
		{
			try
			{
				
				setMainHeaderName(DateUtil.getCurrentDateIndianFormat());
				
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session .get("connectionSpace");
				newsAlertsList=new FeedbackDashboardDao().getDashboardAlertsList("News", connectionSpace,null);
			    System.out.println("List Size is as "+newsAlertsList.size());
				return "success";
			}
			catch (Exception e) {
				return "error";
			}
		}
		else
		{
			return "login";
		}
	}
	public List<NewsAlertsPojo> getNewsAlertsList() {
		return newsAlertsList;
	}
	public void setNewsAlertsList(List<NewsAlertsPojo> newsAlertsList) {
		this.newsAlertsList = newsAlertsList;
	}
	public String getMainHeaderName() {
		return mainHeaderName;
	}
	public void setMainHeaderName(String mainHeaderName) {
		this.mainHeaderName = mainHeaderName;
	}
	
}
