package com.Over2Cloud.ctrl.feedback.report;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.compliance.ComplianceExcelDownload;
import com.Over2Cloud.ctrl.feedback.DownldeExcelFeedback;
import com.Over2Cloud.ctrl.feedback.common.TicketActionHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FeedbackReportDownload extends ActionSupport
{
	private String mode,type,startDate,endDate;
	private Map<String,String > columnMap;
	private String[] columnNames;
	private String  excelFileName;
	private FileInputStream excelStream;
	private InputStream	inputStream;
	private String contentType;
	private String ticketNo;
	
	
	public String beforeDownloadTicketsData()
	{
		boolean validSession=ValidateSession.checkSession();
		if(validSession)
		{
			Map session=ActionContext.getContext().getSession();
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			List<GridDataPropertyView> columnList=  Configuration.getConfigurationData("mapped_feedback_ticket_lodge_configuration", accountID,connectionSpace, "", 0, "table_name","feedback_ticket_lodge_configuration");
			columnMap = new LinkedHashMap<String, String>();
			
			if (columnList != null && columnList.size() > 0) 
			{
				for (GridDataPropertyView gdp : columnList) 
				{
					if(gdp.getColomnName()!=null && !gdp.getColomnName().equalsIgnoreCase("uniqueid") && !gdp.getColomnName().equalsIgnoreCase("hp_date") && !gdp.getColomnName().equalsIgnoreCase("hp_time") && !gdp.getColomnName().equalsIgnoreCase("hp_reason") && !gdp.getColomnName().equalsIgnoreCase("sn_reason") && !gdp.getColomnName().equalsIgnoreCase("sn_on_date") && !gdp.getColomnName().equalsIgnoreCase("sn_on_time") && !gdp.getColomnName().equalsIgnoreCase("sn_upto_date") && !gdp.getColomnName().equalsIgnoreCase("sn_upto_time") && !gdp.getColomnName().equalsIgnoreCase("sn_duration") && !gdp.getColomnName().equalsIgnoreCase("ig_date") && !gdp.getColomnName().equalsIgnoreCase("ig_time") && !gdp.getColomnName().equalsIgnoreCase("ig_reason") && !gdp.getColomnName().equalsIgnoreCase("transfer_date") && !gdp.getColomnName().equalsIgnoreCase("transfer_time") && !gdp.getColomnName().equalsIgnoreCase("transfer_reason") && !gdp.getColomnName().equalsIgnoreCase("resolutionTime") && !gdp.getColomnName().equalsIgnoreCase("moduleName"))  
					{
						columnMap.put(gdp.getColomnName(),gdp.getHeadingName());
					}
				}
			}
			return SUCCESS;
		}
		else
		{
			return LOGIN;
		}
	}
	
	 public String beforeDownload()
	 {
		 boolean validSession=ValidateSession.checkSession();
		 if(validSession)
		 {
			 Map session=ActionContext.getContext().getSession();
			 String accountID = (String) session.get("accountid");
			 SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			 List<GridDataPropertyView> columnList=null;
			 
			 if(type!=null && type.equalsIgnoreCase("Negative"))
			 {
				 columnList = Configuration.getConfigurationData("mapped_feedback_negative_data_configuration", accountID,connectionSpace, "", 0, "table_name","feedback_negative_data_configuration");
			 }
			 else
			 {
				 columnList = Configuration.getConfigurationData("mapped_feedback_configuration", accountID,connectionSpace, "", 0, "table_name","feedback_data_configuration");
			 }
			 
			 columnMap = new LinkedHashMap<String, String>();
			 
			 if (columnList != null && columnList.size() > 0) 
			 {
				 for (GridDataPropertyView gdp : columnList) 
				 {
					 if(gdp.getColomnName()!=null && !gdp.getColomnName().equalsIgnoreCase("action") && !gdp.getColomnName().equalsIgnoreCase("feedTicketId"))
					 {
						 columnMap.put(gdp.getColomnName(),gdp.getHeadingName());
					 }
				 }
			 }
			 return SUCCESS;
		 }
		 else
		 {
			 return ERROR;
		 }
	 }
	 
	 
	 public String downloadTicktsExcel()
	 {
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();
			if (sessionFlag)
			{
				try 
				{
					Map session=ActionContext.getContext().getSession();
					String accountID = (String) session.get("accountid");
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
					CommunicationHelper cmnHelper=new CommunicationHelper();
					List keyList = new ArrayList();
					List titleList = new ArrayList();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					if (columnNames != null && columnNames.length > 0) 
					{
						keyList = Arrays.asList(columnNames);
						Map<String, String> tempMap = new LinkedHashMap<String, String>();
						List<GridDataPropertyView> columnList=null;
						 columnList = Configuration.getConfigurationData("mapped_feedback_ticket_lodge_configuration", accountID,connectionSpace, "", 0, "table_name","feedback_ticket_lodge_configuration");
						
						 if (columnList != null && columnList.size() > 0) 
						 {
							 for (GridDataPropertyView gdp : columnList) 
							 {
								 tempMap.put(gdp.getColomnName(),gdp.getHeadingName());
							 }
						 }
						
						StringBuilder query = new StringBuilder("");
						for (int index = 0; index < keyList.size(); index++)
						{
							titleList.add(tempMap.get(keyList.get(index)));
						}
						if (keyList != null && keyList.size() > 0) 
						{
							
							List dataList =new TicketActionHelper().getFullDetailsForTicketDownload(getTicketNo(), connectionSpace);
							String orgDetail = (String)session.get("orgDetail");
							String[] orgData = null;
							String orgName="";
							if (orgDetail!=null && !orgDetail.equals("")) 
							{
								orgData = orgDetail.split("#");
								orgName=orgData[0];
							}
							
							if (dataList!=null && titleList!=null && keyList!=null)
							{
								excelFileName=new DownldeExcelFeedback().writeDataInExcel(dataList,titleList,keyList,"Action Details For Ticket No"+getTicketNo(),orgName,true,connectionSpace);
							}
							
							if(excelFileName!=null)
							{
								excelStream=new FileInputStream(excelFileName);
							}
							if (excelFileName!=null) 
							{
								excelFileName=excelFileName.substring(excelFileName.lastIndexOf("//")+2, excelFileName.length());
							}
							returnResult=SUCCESS;
						}
					}
					else
					{
						addActionMessage("There is some error in data!!!!");
						returnResult = ERROR;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				returnResult=LOGIN;
			}
			return returnResult;
	 }
	 
	 
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String downloadExcel()
		{
			String returnResult = ERROR;
			boolean sessionFlag = ValidateSession.checkSession();

			if (sessionFlag)
			{
				try 
				{
					Map session=ActionContext.getContext().getSession();
					String accountID = (String) session.get("accountid");
					SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
					CommunicationHelper cmnHelper=new CommunicationHelper();
					List keyList = new ArrayList();
					List titleList = new ArrayList();
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					if (columnNames != null && columnNames.length > 0) 
					{
						keyList = Arrays.asList(columnNames);
						Map<String, String> tempMap = new LinkedHashMap<String, String>();
						List<GridDataPropertyView> columnList=null;
						if(type!=null && type.equalsIgnoreCase("Negative"))
						 {
							 columnList = Configuration.getConfigurationData("mapped_feedback_negative_data_configuration", accountID,connectionSpace, "", 0, "table_name","feedback_negative_data_configuration");
						 }
						 else
						 {
							 columnList = Configuration.getConfigurationData("mapped_feedback_configuration", accountID,connectionSpace, "", 0, "table_name","feedback_data_configuration");
						 }
						 if (columnList != null && columnList.size() > 0) 
						 {
							 for (GridDataPropertyView gdp : columnList) 
							 {
								 tempMap.put(gdp.getColomnName(),gdp.getHeadingName());
							 }
						 }
						
						StringBuilder query = new StringBuilder("");
						for (int index = 0; index < keyList.size(); index++)
						{
							titleList.add(tempMap.get(keyList.get(index)));
						}
						if (keyList != null && keyList.size() > 0) 
						{
							if(type!=null && type.equalsIgnoreCase("Negative"))
							{
								query.append("select feedbt.feedTicketNo,feedData.status,feedData.openDate,feedData.openTime," +
			                    	 	" feedbck.escalation_date,feedbck.escalation_time,feedData.clientId,feedData.clientName,feedData.centerCode,feedData.problem,feedData.mobNo," +
			                    	 	" feedData.emailId,feedData.refNo,feedData.centreName,feedData.level," +
			                    	 	" feedData.compType,feedData.mode,feedbck.resolve_remark,feedData.handledBy,feedData.remarks ," +
			                    	 	" feedData.kword,feedData.ip,feedData.status,feedbck.spare_used,,feedbck.resolve_time from feedback_ticket as feedbt" +
			                    	 	" inner join feedback_status as feedbck on feedbt.feed_stat_id=feedbck.id " +
			                    	 	" inner join feedbackdata as feedData on feedbt.feed_data_id=feedData.id where feedData.targetOn='No' and  feedbck.status!='Resolved' ");
								if(getStartDate()!=null && getEndDate()!=null)
				                {
				                	query.append(" && feedData.openDate between '"+DateUtil.convertDateToUSFormat(getStartDate())+"' and '"+DateUtil.convertDateToUSFormat(getEndDate())+"' ");
				                }
			                	query.append(" order by feedData.openDate DESC");
							}
							else
							{
								query.append("SELECT ");
								int i = 0;
								for (Iterator it = keyList.iterator(); it.hasNext();) 
								{
									Object obdata = (Object) it.next();
									if (obdata != null) {
										if (i < keyList.size() - 1) 
										{
											query.append(obdata.toString() + ",");
										} else 
										{
											query.append(obdata.toString());	
										}
									}
									i++;
								}
								query.append(" FROM feedbackdata where id!=0");
								
								if(getType()!=null && getType().equalsIgnoreCase("Positive"))
					            {
									query.append(" &&  targetOn='Yes'");
					            }
								
								if(getMode()!=null && !getMode().equalsIgnoreCase("-1"))
					            {
									query.append(" && mode='"+getMode()+"'");
					            }
								
								if(getStartDate()!=null && getEndDate()!=null)
				                {
				                	query.append(" && openDate between '"+DateUtil.convertDateToUSFormat(getStartDate())+"' and '"+DateUtil.convertDateToUSFormat(getEndDate())+"' ");
				                }
								
							}
							System.out.println("QUerry is as ::::::::::::::::::::::::::::::::"+query);
							List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
							String orgDetail = (String)session.get("orgDetail");
							String[] orgData = null;
							String orgName="";
							if (orgDetail!=null && !orgDetail.equals("")) 
							{
								orgData = orgDetail.split("#");
								orgName=orgData[0];
							}
							
							if (dataList!=null && titleList!=null && keyList!=null)
							{
								excelFileName=new DownldeExcelFeedback().writeDataInExcel(dataList,titleList,keyList,"Feedback",orgName,true,connectionSpace);
							}
							
							if(excelFileName!=null)
							{
								excelStream=new FileInputStream(excelFileName);
							}
							if (excelFileName!=null) 
							{
								excelFileName=excelFileName.substring(excelFileName.lastIndexOf("//")+2, excelFileName.length());
							}
							returnResult=SUCCESS;
						}
					}
					else
					{
						addActionMessage("There is some error in data!!!!");
						returnResult = ERROR;
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				returnResult=LOGIN;
			}
			return returnResult;
		}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Map<String, String> getColumnMap() {
		return columnMap;
	}
	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	public FileInputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(FileInputStream excelStream) {
		this.excelStream = excelStream;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
}
