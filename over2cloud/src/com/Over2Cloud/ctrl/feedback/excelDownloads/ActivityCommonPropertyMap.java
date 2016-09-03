package com.Over2Cloud.ctrl.feedback.excelDownloads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.opensymphony.xwork2.ActionContext;

public class ActivityCommonPropertyMap
{
	private String key;
	public  Map<String,String> valueMap;
	public Map<String, List<ConfigurationUtilBean>> commonMap = null;
	public List<ConfigurationUtilBean> activityDetails;

	{
		commonMap = new HashMap<String, List<ConfigurationUtilBean>>();
		activityDetails = new ArrayList<ConfigurationUtilBean>();
		Map session = ActionContext.getContext().getSession();
		String accountID = (String) session.get("accountid");
		SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
		List<GridDataPropertyView> activityColHeaders = Configuration.getConfigurationData("mapped_feedback_activity_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_activity_configuration");
		int l = 0;
		for (GridDataPropertyView col : activityColHeaders)
		{
			ConfigurationUtilBean objdata = new ConfigurationUtilBean();
			if (col != null)
			{
				if (l < activityColHeaders.size() - 1)
				{
					objdata.setKey(col.getColomnName());
					objdata.setValue(col.getHeadingName());
					if (col.getMandatroy() == null)
					{
						objdata.setMandatory(false);
					}
					else if (col.getMandatroy().equalsIgnoreCase("0"))
					{
						objdata.setMandatory(false);
					}
					else if (col.getMandatroy().equalsIgnoreCase("1"))
					{
						objdata.setMandatory(true);
					}
					activityDetails.add(objdata);
				}
				else
				{
				}
			}
			l++;
		}
		ConfigurationUtilBean objdata = new ConfigurationUtilBean();
		objdata.setKey("dateTime");
		objdata.setValue("Date");
		objdata.setMandatory(true);
		activityDetails.add(objdata);
		
		objdata = new ConfigurationUtilBean();
		objdata.setKey("dept");
		objdata.setValue("Department");
		objdata.setMandatory(true);
		activityDetails.add(objdata);
		
		objdata = new ConfigurationUtilBean();
		objdata.setKey("cat");
		objdata.setValue("Category");
		objdata.setMandatory(true);
		activityDetails.add(objdata);
		
		objdata = new ConfigurationUtilBean();
		objdata.setKey("subCat");
		objdata.setValue("Sub Category");
		objdata.setMandatory(true);
		activityDetails.add(objdata);

		objdata = new ConfigurationUtilBean();
		objdata.setKey("brief");
		objdata.setValue("Brief");
		objdata.setMandatory(true);
		activityDetails.add(objdata);
		
		objdata = new ConfigurationUtilBean();
		objdata.setKey("stage");
		objdata.setValue("Stage");
		objdata.setMandatory(true);
		activityDetails.add(objdata);

		objdata = new ConfigurationUtilBean();
		objdata.setKey("fstatus");
		objdata.setValue("Status");
		objdata.setMandatory(true);
		activityDetails.add(objdata);

		objdata = new ConfigurationUtilBean();
		objdata.setKey("level");
		objdata.setValue("TAT");
		objdata.setMandatory(true);
		activityDetails.add(objdata);
		
		objdata = new ConfigurationUtilBean();
		objdata.setKey("ticket_no");
		objdata.setValue("Ticket No.");
		objdata.setMandatory(true);
		activityDetails.add(objdata);
		
		objdata = new ConfigurationUtilBean();
		objdata.setKey("reopenedon");
		objdata.setValue("Reopened On");
		objdata.setMandatory(true);
		activityDetails.add(objdata);
		
		objdata = new ConfigurationUtilBean();
		objdata.setKey("reopenstatus");
		objdata.setValue("Reopened Status");
		objdata.setMandatory(true);
		activityDetails.add(objdata);
		
		objdata = new ConfigurationUtilBean();
		objdata.setKey("reOpenLevel");
		objdata.setValue("Reopened TAT");
		objdata.setMandatory(true);
		activityDetails.add(objdata);

		objdata = new ConfigurationUtilBean();
		objdata.setKey("allot_to");
		objdata.setValue("Alloted To");
		objdata.setMandatory(true);
		activityDetails.add(objdata);
		
		objdata = new ConfigurationUtilBean();
		objdata.setKey("feedRegBy");
		objdata.setValue("Open By");
		objdata.setMandatory(true);
		activityDetails.add(objdata);

		
		commonMap.put("activity", activityDetails);
	}
	public String getKey()
	{
		return key;
	}
	public void setKey(String key)
	{
		this.key = key;
	}
	public List<ConfigurationUtilBean> getValueMap()
	{
		System.out.println("Key is as "+key);
		return commonMap.get(key);
	}
	public List<ConfigurationUtilBean> getTitles(String key)
	{
		return commonMap.get(key);
	}
}
