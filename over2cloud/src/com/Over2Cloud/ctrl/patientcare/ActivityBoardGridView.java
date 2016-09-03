package com.Over2Cloud.ctrl.patientcare;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;

@SuppressWarnings("serial")
public class ActivityBoardGridView extends GridPropertyBean
{
	private String fromTime;
	private String toTime;
	private String maxDateValue;
	private String minDateValue;
	private String fromDepart;
	private String taskType;
	private String feedStatus;
	private String escLevel;
	private String escTAT;
	private String complianId;
	private String dept;
	private String category;
	private String advncSearch;
	private Map<String, String> dataCountMap;
	private List<Object> masterViewProp = new ArrayList<Object>();



	
	@SuppressWarnings("rawtypes")
	public String viewActivityBoardDataCPS()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{

			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
			 	List dataCount = cbt.executeAllSelectQuery(" select count(*) from feedback_corporate_patient ", connectionSpace);
				// ////////System.out.println("dataCount:"+dataCount);
				 	BigInteger count = new BigInteger("3");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to < getRecords())
						to = getRecords(); 
					
					StringBuilder query = new StringBuilder();
					List<Object> tempList = new ArrayList<Object>();
					
				  	  	query.append("SELECT SQL_CACHE feedback.id,feedback.ticket_no,feedback.acc_manager,feedback.corp_name,feedback.corp_type,");
						query.append("feedback.status,feedback.pat_name,feedback.uhid, feedback.service, feedback.location,");
						query.append(" feedback.preffered_date,");
					 	query.append(" feedback.preffered_time,feedback.remark,feedback.allot_to_phase1,feedback.openDate_phase1,feedback.openTime_phase1,feedback.timer,feedback.tat,feedback.feed_status");
						query.append(" FROM feedback_corporate_patient as feedback");
					 	query.append(" WHERE feedback.id!=0 ");
						query.append(" ORDER BY feedback.id DESC limit " + from + "," + to);
					
					List dataList = null;
					dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						Session session = null;
						Transaction transaction = null;
						try
						{
							session = connectionSpace.getCurrentSession();
							transaction = session.beginTransaction();
							dataList = session.createSQLQuery(query.toString())
							.setCacheable(true).setCacheRegion("gridActivityDataCPS").setCacheMode(CacheMode.REFRESH).list();
							transaction.commit();

						}
						catch (Exception ex)
						{
							transaction.rollback();
						}
					
					String unloack = "UNLOCK TABLES";
					cbt.executeAllUpdateQuery(unloack, connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{

						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object[] object = (Object[]) iterator.next();
							Map<String, Object> tempMap = new LinkedHashMap<String, Object>();
							tempMap.put("id", getValueWithNullCheck(object[0]));
							tempMap.put("ticketno", getValueWithNullCheck(object[1]));
							tempMap.put("acc_manager", getValueWithNullCheck(object[2]));
							tempMap.put("corporate_name", getValueWithNullCheck(object[3]));
							tempMap.put("corp_type", getValueWithNullCheck(object[4]));
							tempMap.put("feed_status", getValueWithNullCheck(object[18]));
							tempMap.put("status", getValueWithNullCheck(object[5]));
							tempMap.put("pat_name", getValueWithNullCheck(object[6]));
							tempMap.put("uhid", getValueWithNullCheck(object[7]));
							tempMap.put("service_name", getValueWithNullCheck(object[8]));
							tempMap.put("location", getValueWithNullCheck(object[9]));
							tempMap.put("preffered", getValueWithNullCheck(object[10])+"/"+getValueWithNullCheck(object[11]));
							tempMap.put("phase1allot", getValueWithNullCheck(object[13]));
							tempMap.put("occdatetime",  getValueWithNullCheck(object[14])+"/"+getValueWithNullCheck(object[15]));
							tempMap.put("timer", getValueWithNullCheck(object[16]));
							tempMap.put("TATOCC", getValueWithNullCheck(object[17]));
							tempMap.put("remark", getValueWithNullCheck(object[12]));
						    tempList.add(tempMap);

						}
					}
					 
					setMasterViewProp(tempList);
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					returnResult = SUCCESS;
				 
			}
			catch (Exception e)
			{
				returnResult = ERROR;
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	 

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	public String getFromTime()
	{
		return fromTime;
	}

	public void setFromTime(String fromTime)
	{
		this.fromTime = fromTime;
	}

	public String getToTime()
	{
		return toTime;
	}

	public void setToTime(String toTime)
	{
		this.toTime = toTime;
	}

	public String getMaxDateValue()
	{
		return maxDateValue;
	}

	public void setMaxDateValue(String maxDateValue)
	{
		this.maxDateValue = maxDateValue;
	}

	public String getMinDateValue()
	{
		return minDateValue;
	}

	public void setMinDateValue(String minDateValue)
	{
		this.minDateValue = minDateValue;
	}

	public String getFromDepart()
	{
		return fromDepart;
	}

	public void setFromDepart(String fromDepart)
	{
		this.fromDepart = fromDepart;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
	}

	public String getFeedStatus()
	{
		return feedStatus;
	}

	public void setFeedStatus(String feedStatus)
	{
		this.feedStatus = feedStatus;
	}

	public String getEscLevel()
	{
		return escLevel;
	}

	public void setEscLevel(String escLevel)
	{
		this.escLevel = escLevel;
	}

	public String getEscTAT()
	{
		return escTAT;
	}

	public void setEscTAT(String escTAT)
	{
		this.escTAT = escTAT;
	}

	public String getComplianId()
	{
		return complianId;
	}

	public void setComplianId(String complianId)
	{
		this.complianId = complianId;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public Map<String, String> getDataCountMap()
	{
		return dataCountMap;
	}

	public void setDataCountMap(Map<String, String> dataCountMap)
	{
		this.dataCountMap = dataCountMap;
	}

	public List<Object> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<Object> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

   public String getAdvncSearch() {
		return advncSearch;
	}

   public void setAdvncSearch(String advncSearch) {
		this.advncSearch = advncSearch;
	}

}