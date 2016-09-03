package com.Over2Cloud.ctrl.Severity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

public class SeverityAction extends GridPropertyBean
{
	private static final long serialVersionUID = 1L;
	private Map<String, String> serviceDeptMap;
	private String moduleName;
	SeverityActionHelper SAH = new SeverityActionHelper();
	private String severityLevel;
	private String fromTime;
	private String toTime;
	private String deptName;
	private String severityCheckOn;
	private List<Object> masterViewList;
	
	
	@SuppressWarnings("rawtypes")
	public String getColumn4Severity()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				serviceDeptMap = new LinkedHashMap<String, String>();
				List dataList = SAH.getServiceDeptByModule(connectionSpace,moduleName);
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && !object[0].toString().equals(""))
							serviceDeptMap.put(object[0].toString(), object[1].toString());
					}
				}
				
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String addSeverityLevel()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				boolean status = SAH.createTable4Severity(connectionSpace);
				if(status)
				{
					status = false;
					Map<String, String> dataMap = new HashMap<String, String>();
					dataMap.put("deptName", deptName);
					dataMap.put("severityCheckOn", severityCheckOn);
					dataMap.put("moduleName", moduleName);
					dataMap.put("userName", userName);
					dataMap.put("date", DateUtil.getCurrentDateUSFormat());
					dataMap.put("time", DateUtil.getCurrentTimeHourMin());
					if(severityLevel!=null)
					{
						String[] severityLevelArr = severityLevel.split(",");
						String[] fromTimeArr = fromTime.split(",");
						String[] toTimeArr = toTime.split(",");
						status = SAH.saveData4Severity(dataMap,connectionSpace,severityLevelArr,fromTimeArr,toTimeArr,deptName);
					}
					
				}
				addActionMessage("Data saved sucessfully.");
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				addActionMessage("Opps. There are some problem.");
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String beforeViewSeverityInGrid()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				viewPageColumns = new ArrayList<GridDataPropertyView>();
				GridDataPropertyView gpv = new GridDataPropertyView();
				gpv.setColomnName("id");
				gpv.setHeadingName("Id");
				gpv.setHideOrShow("true");
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("deptName");
				gpv.setHeadingName("Department");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(140);
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("severityCheckOn");
				gpv.setHeadingName("Time Consider");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(150);
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("severityLevel");
				gpv.setHeadingName("Level");
				gpv.setEditable("false");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(160);
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("fromTime");
				gpv.setHeadingName("From Time");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(130);
				viewPageColumns.add(gpv);
				
				gpv=new GridDataPropertyView();
				gpv.setColomnName("toTime");
				gpv.setHeadingName("To Time");
				gpv.setEditable("true");
				gpv.setSearch("true");
				gpv.setHideOrShow("false");
				gpv.setWidth(140);
				viewPageColumns.add(gpv);
				
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String viewSeverity()
	{
		System.out.println("sdvndsdvbjbsjbdvj");
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				List<Object> Listhb=new ArrayList<Object>();
				System.out.println(moduleName);
				if(moduleName!=null)
				{
					StringBuilder query = new StringBuilder();
					query.append("SELECT severity.id,dept.deptName,severity.severityCheckOn,severity.severityLevel,");
					query.append("severity.fromTime,severity.toTime FROM severity_detail AS severity");
					query.append(" INNER JOIN department AS dept ON dept.id = severity.deptName");
					query.append(" WHERE severity.moduleName='"+moduleName+"' ORDER BY dept.deptName");
					System.out.println(query.toString());
					List dataList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						ComplianceUniversalAction CUA = new ComplianceUniversalAction();
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
						{
							Object[] object = (Object[]) iterator.next();
							Map<String, Object> one = new HashMap<String, Object>();
							one.put("id", CUA.getValueWithNullCheck(object[0]));
							one.put("deptName", CUA.getValueWithNullCheck(object[1]));
							if(CUA.getValueWithNullCheck(object[2]).equalsIgnoreCase("AT"))
								one.put("severityCheckOn", "Addressing Time");
							else if(CUA.getValueWithNullCheck(object[2]).equalsIgnoreCase("RT"))
								one.put("severityCheckOn", "Resolution Time");
							else if(CUA.getValueWithNullCheck(object[2]).equalsIgnoreCase("ET"))
								one.put("severityCheckOn", "Escalation Time");
							
							
							one.put("severityLevel", CUA.getValueWithNullCheck(object[3])+" Level");
							one.put("fromTime", CUA.getValueWithNullCheck(object[4]));
							one.put("toTime", CUA.getValueWithNullCheck(object[5]));
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
					}
				}
				if (masterViewList!= null && masterViewList.size() > 0)
				{
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String beforeViewSeverity()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				return SUCCESS;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	
	
	public Map<String, String> getServiceDeptMap() {
		return serviceDeptMap;
	}

	public void setServiceDeptMap(Map<String, String> serviceDeptMap) {
		this.serviceDeptMap = serviceDeptMap;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getSeverityLevel() {
		return severityLevel;
	}

	public void setSeverityLevel(String severityLevel) {
		this.severityLevel = severityLevel;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSeverityCheckOn() {
		return severityCheckOn;
	}

	public void setSeverityCheckOn(String severityCheckOn) {
		this.severityCheckOn = severityCheckOn;
	}

	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}
	
	
}
