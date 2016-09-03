package com.Over2Cloud.ctrl.feedback.action;

import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.feedback.activity.ActivityPojo;
import com.opensymphony.xwork2.ActionSupport;

public class PaperFeedbackCtrl extends ActionSupport 
{
	private String clientId;
	private String patType;
	private ActivityPojo pojo;
	
	public String getPatientDetailsForAdd()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				pojo=new ActivityPojo();
				
				return SUCCESS;
			}
			catch(Exception e)
			{
				return ERROR;
			}
		}
		else
		{
			return ERROR;
		}
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getPatType() {
		return patType;
	}

	public void setPatType(String patType) {
		this.patType = patType;
	}

	public ActivityPojo getPojo() {
		return pojo;
	}

	public void setPojo(ActivityPojo pojo) {
		this.pojo = pojo;
	}
}
