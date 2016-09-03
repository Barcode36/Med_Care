package com.Over2Cloud.ctrl.helpdesk.reportconfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AnalyticalDownloadAction extends ActionSupport
{
	@SuppressWarnings("rawtypes")
	Map session = ActionContext.getContext().getSession();

	private String divName;
	private String excelName;
	private String selectedId;
	private Map<String,String > columnMap;
	private String dataFor;
	private String  excelFileName;
	private FileInputStream excelStream;
	private InputStream	inputStream;
	private String contentType;
	private String[] columnNames;
	private String deptId;
	private String subdeptId;
	private String fromDate;
	private String toDate;
	private String byDeptId;
	private Map<Integer,String> deptList=null;
	private JSONArray jsonArr=null;
	private String emp;
	private String moduleName;
	
	
	@SuppressWarnings("unchecked")
	public String getCurrentColumn()
	{
		boolean sessionFlag=ValidateSession.checkSession();
		if(sessionFlag)
		{
			try
			{
				columnMap=new LinkedHashMap<String, String>();
				excelName = "Productivity detail";
				if (dataFor!=null && dataFor.equalsIgnoreCase("Employee"))
				{
					columnMap.put("empName", "Employee Name");
					if (moduleName!=null && moduleName.equalsIgnoreCase("HDM"))
					{
						columnMap.put("feedback_by_subdept", "Sub Department");
					}
				}
				else if(dataFor!=null && dataFor.equalsIgnoreCase("Category"))
				{
					columnMap.put("feedback_catg", "Category");
					columnMap.put("feedback_subcatg", "Sub Category");
				}
				columnMap.put("counter", "Total Task");
				columnMap.put("onTime", "On Time");
				columnMap.put("offTime", "Off Time");
				columnMap.put("missed", "Missed");
				columnMap.put("snooze", "Snooze");
				columnMap.put("ignore", "Ignore");
				columnMap.put("perOnTime", "On Time (%)");
				columnMap.put("perOffTime", "Off Time (%)");
				columnMap.put("perMissed", "Missed (%)");
				if (emp!=null && !emp.equalsIgnoreCase("")) 
				{
					divName = "sendMailDiv";
				}
				else
				{
					divName = "";
				}
				if (columnMap != null && columnMap.size() > 0) 
				{
					session.put("columnMap", columnMap);
				}
				return SUCCESS;
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String downloadExcel()
	{
		boolean sessionFlag = ValidateSession.checkSession();

		if (sessionFlag)
		{
			try 
			{
				SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				CommunicationHelper cmnHelper=new CommunicationHelper();
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder emailIds=null;
				List keyList = new ArrayList();
				List titleList = new ArrayList();
				if (columnNames != null && columnNames.length > 0) 
				{
					keyList = Arrays.asList(columnNames);
					Map<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap = (Map<String, String>) session.get("columnMap");
					if(session.containsKey("columnMap"))
						session.remove("columnMap");
					
					for (int index = 0; index < keyList.size(); index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
					if (keyList != null && keyList.size() > 0) 
					{
						List<FeedbackPojo>	feedbackList = null;
						List totalData=null;
						List onTimedata=null;
						List offTimedata=null;
						List snooze=null;
						List missed=null;
						List ignore=null;
						if (fromDate!=null && !fromDate.equalsIgnoreCase("") && toDate!=null && !toDate.equalsIgnoreCase(""))
						{
							totalData = new HelpdeskUniversalAction().getAnalyticalGridReport(
									dataFor, DateUtil.convertDateToUSFormat(fromDate),
									DateUtil.convertDateToUSFormat(toDate), deptId,
									subdeptId, "", "", "",connectionSpace,"Total",moduleName,byDeptId);
						    
							if (totalData!=null && totalData.size()>0)
							{
								onTimedata = new HelpdeskUniversalAction().getAnalyticalGridReport(
										dataFor, DateUtil.convertDateToUSFormat(fromDate),
										DateUtil.convertDateToUSFormat(toDate), deptId,
										subdeptId, "", "", "",connectionSpace,"Ontime",moduleName,byDeptId);
							
								offTimedata= new HelpdeskUniversalAction().getAnalyticalGridReport(
										dataFor, DateUtil.convertDateToUSFormat(fromDate),
										DateUtil.convertDateToUSFormat(toDate), deptId,
										subdeptId,"", "", "",connectionSpace,"Offtime",moduleName,byDeptId);
								
								snooze= new HelpdeskUniversalAction().getAnalyticalGridReport(
										dataFor, DateUtil.convertDateToUSFormat(fromDate),
										DateUtil.convertDateToUSFormat(toDate), deptId,
										subdeptId,"", "", "",connectionSpace,"Snooze",moduleName,byDeptId);
							
								missed= new HelpdeskUniversalAction().getAnalyticalGridReport(
										dataFor, DateUtil.convertDateToUSFormat(fromDate),
										DateUtil.convertDateToUSFormat(toDate), deptId,
										subdeptId, "", "", "",connectionSpace,"Missed",moduleName,byDeptId);
							    
								ignore= new HelpdeskUniversalAction().getAnalyticalGridReport(
										dataFor, DateUtil.convertDateToUSFormat(fromDate),
										DateUtil.convertDateToUSFormat(toDate), deptId,
										subdeptId,"", "", "",connectionSpace,"Ignore",moduleName,byDeptId);
							}
						}
						if (totalData != null && totalData.size() > 0)
						{
							feedbackList = new ArrayList<FeedbackPojo>();
							if (dataFor.equals("Employee"))
							{
							   feedbackList = HUH.setAnalyticalReportValues(totalData,onTimedata,offTimedata,snooze,missed,ignore,dataFor);
							}
							else if(dataFor.equals("Category"))
							{
								feedbackList = HUH.setAnalyticalReportValues(totalData,onTimedata,offTimedata,snooze,missed,ignore,dataFor);
							}
						}
						String orgDetail = (String)session.get("orgDetail");
						String[] orgData = null;
						String orgName="";
						if (orgDetail!=null && !orgDetail.equals("")) 
						{
							orgData = orgDetail.split("#");
							orgName=orgData[0];
						}
						if (feedbackList!=null && titleList!=null && keyList!=null )
						{
							excelFileName=writeDataInExcel(feedbackList,titleList,keyList,excelName,orgName,true,connectionSpace,dataFor);
							if (emp!=null && !emp.equalsIgnoreCase(""))
							{
								String mailSubject=dataFor +" Wise Productivity Details";
								List emaildata= cbt.executeAllSelectQuery("SELECT emailIdOne FROM employee_basic WHERE id IN("+emp+")", connectionSpace);
								if (emaildata!=null && emaildata.size()>0)
								{
									emailIds=new StringBuilder();
									for (Iterator iterator = emaildata.iterator(); iterator.hasNext();)
									{
										Object object = (Object) iterator.next();
										emailIds.append(object.toString()+",");
									}
								}
								String mailText=null;
								if (feedbackList!=null && feedbackList.size()>0)
								{
									mailText=getConfigMail(feedbackList,keyList,mailSubject,dataFor,moduleName);
								}
								String str[]=emailIds.toString().split(",");
								if (str!=null)
								{
									for(int j=0;j<str.length;j++)
									{
										if(str[j]!=null && !str[j].equalsIgnoreCase(""))
										{
											cmnHelper.addMail("","",str[j],mailSubject, mailText,"","Pending", "0",excelFileName,"HDM",connectionSpace);
										}
									}
								}
							}
							if(excelFileName!=null)
							{
								excelStream=new FileInputStream(excelFileName);
							}
							excelFileName=excelFileName.substring(excelFileName.lastIndexOf("//")+1, excelFileName.length());
							if(emailIds!=null && emailIds.length()>2)
							{
								addActionMessage("Send Mail Successfully ...");
								return "SENDMAILSUCCESS";
							}
							else 
							{
								return SUCCESS;
							}
						}
						else 
						{
							return ERROR;
						}
					}
					else 
					{
						return ERROR;
					}
				}
				else 
				{
					return ERROR;
				}
			}
			catch (Exception e)
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
	@SuppressWarnings({ "static-access", "rawtypes" })
	public String writeDataInExcel(List<FeedbackPojo> dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,SessionFactory connection,String dataFor)
	{ 
		String excelFileName =null;
		String mergeDateTime=new DateUtil().mergeDateTime();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Producvity for "+dataFor);
		Map<String, CellStyle> styles = createStyles(wb);
		
		Row headerRow = sheet.createRow(2);
		headerRow.setHeightInPoints(15);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
		
		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(22);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(orgName);
		titleCell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,17));
		
		// Sub Title Row
		Row subTitleRow = sheet.createRow(1);
		subTitleRow.setHeightInPoints(18);
		Cell subTitleCell = subTitleRow.createCell(0);
		subTitleCell.setCellValue("Productivity Detail");
		subTitleCell.setCellStyle(styles.get("subTitle"));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,17));
		
		// Creating the folder for holding the Excel files with the defined name
		/*if(needToStore)
			excelFileName = new CreateFolderOs().createUserDir("Productivity_Data")+ "/" +"Productivity"+ mergeDateTime+".xls";
		else
			excelFileName=excelName;*/
		excelFileName="Productivity"+mergeDateTime;
		
		int header_first=2;
		int index=3;
		HSSFRow rowHead = sheet.createRow((int) header_first);
		for(int i=0;i<titleList.size();i++)
		{
			rowHead.createCell((int) i).setCellValue(titleList.get(i).toString());
		}
		if(dataList!=null && dataList.size()>0)
		{
			for (FeedbackPojo object : dataList)
			{
				HSSFRow rowData=sheet.createRow((int)index);
				for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
				{
					if(object!=null)
					{
						if (dataFor!=null && dataFor.equalsIgnoreCase("Employee"))
						{
							if(keyList.get(cellIndex).toString().equalsIgnoreCase("empName"))
							{
								rowData.createCell((int) cellIndex).setCellValue(object.getEmpName());
							}
							else if(keyList.get(cellIndex).toString().equalsIgnoreCase("feedback_by_subdept"))
							{
								rowData.createCell((int) cellIndex).setCellValue(object.getFeedback_by_subdept());
							}
						}
						else if(dataFor!=null && dataFor.equalsIgnoreCase("Category"))
						{
							if(keyList.get(cellIndex).toString().equalsIgnoreCase("feedback_catg"))
							{
								rowData.createCell((int) cellIndex).setCellValue(object.getFeedback_catg());
							}
							else if(keyList.get(cellIndex).toString().equalsIgnoreCase("feedback_subcatg"))
							{
								rowData.createCell((int) cellIndex).setCellValue(object.getFeedback_subcatg());
							}
						}
						if(keyList.get(cellIndex).toString().equalsIgnoreCase("counter"))
						{
							rowData.createCell((int) cellIndex).setCellValue(object.getCounter());
						}
						else if(keyList.get(cellIndex).toString().equalsIgnoreCase("onTime"))
						{
							rowData.createCell((int) cellIndex).setCellValue(object.getOnTime());
						}
						else if(keyList.get(cellIndex).toString().equalsIgnoreCase("offTime"))
						{
							rowData.createCell((int) cellIndex).setCellValue(object.getOffTime());
						}
						else if(keyList.get(cellIndex).toString().equalsIgnoreCase("missed"))
						{
							rowData.createCell((int) cellIndex).setCellValue(object.getMissed());
						}
						else if(keyList.get(cellIndex).toString().equalsIgnoreCase("snooze"))
						{
							rowData.createCell((int) cellIndex).setCellValue(object.getSnooze());
						}
						else if(keyList.get(cellIndex).toString().equalsIgnoreCase("ignore"))
						{
							rowData.createCell((int) cellIndex).setCellValue(object.getIgnore());
						}
						else if(keyList.get(cellIndex).toString().equalsIgnoreCase("perOnTime"))
						{
							rowData.createCell((int) cellIndex).setCellValue(object.getPerOnTime());
						}
						else if(keyList.get(cellIndex).toString().equalsIgnoreCase("perOffTime"))
						{
							rowData.createCell((int) cellIndex).setCellValue(object.getPerOffTime());
						}
						else if(keyList.get(cellIndex).toString().equalsIgnoreCase("perMissed"))
						{
							rowData.createCell((int) cellIndex).setCellValue(object.getPerMissed());
						}
					}
					else
					{
						rowData.createCell((int) cellIndex).setCellValue("NA");
					}
				}
				sheet.autoSizeColumn(index);
				index++;	
			}
		}
		try
		{
			FileOutputStream out = new FileOutputStream(new File(excelFileName));
	        wb.write(out);
	        out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return excelFileName;
	}
	private Map<String, CellStyle> createStyles(Workbook wb) 
	{
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style;

		// Title Style
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(titleFont);
		style.setWrapText(true);
		styles.put("title", style);

		// Sub Title Style
		Font subTitleFont = wb.createFont();
		subTitleFont.setFontHeightInPoints((short) 14);
		subTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.BLUE_GREY.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(subTitleFont);
		style.setWrapText(true);
		styles.put("subTitle", style);
		
		// Sub Title Style for Pending Tickets
		Font subTitleFont_PN = wb.createFont();
		subTitleFont_PN.setFontHeightInPoints((short) 14);
		subTitleFont_PN.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.RED.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(subTitleFont_PN);
		style.setWrapText(true);
		styles.put("subTitle_PN", style);
		
		// Sub Title Style for Resolved Tickets
		Font subTitleFont_RS = wb.createFont();
		subTitleFont_RS.setFontHeightInPoints((short) 14);
		subTitleFont_RS.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREEN.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(subTitleFont_RS);
		style.setWrapText(true);
		styles.put("subTitle_RS", style);
		
		// Sub Title Style for High Priority Tickets
		Font subTitleFont_HP = wb.createFont();
		subTitleFont_HP.setFontHeightInPoints((short) 14);
		subTitleFont_HP.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.ORANGE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(subTitleFont_HP);
		style.setWrapText(true);
		styles.put("subTitle_HP", style);
		
		// Sub Title Style for High Priority Tickets
		Font subTitleFont_SN = wb.createFont();
		subTitleFont_SN.setFontHeightInPoints((short) 14);
		subTitleFont_SN.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.YELLOW.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(subTitleFont_SN);
		style.setWrapText(true);
		styles.put("subTitle_SN", style);
		
		// Sub Title Style for High Priority Tickets
		Font subTitleFont_IG = wb.createFont();
		subTitleFont_IG.setFontHeightInPoints((short) 14);
		subTitleFont_IG.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.LAVENDER.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(subTitleFont_IG);
		style.setWrapText(true);
		styles.put("subTitle_IG", style);
		
		// Sub Title Style for High Priority Tickets
		Font subTitleFont_SH = wb.createFont();
		subTitleFont_SH.setFontHeightInPoints((short) 10);
		subTitleFont_SH.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFont(subTitleFont_SH);
		style.setWrapText(true);
		styles.put("subTitle_SH", style);

		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 11);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("header", style);

		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(true);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styles.put("cell", style);
		return styles;
	}
	
	@SuppressWarnings("rawtypes")
	public String getConfigMail(List<FeedbackPojo> dataList,List titleList,String mailTitle,String dataFor,String moduleName) 
    {
    	  StringBuilder mailtext = new StringBuilder("");
		  mailtext.append("<br><b>Hello,</b><br>");
		  mailtext.append("<br>");
		  mailtext.append(mailTitle+" are as Follows : ");
		  mailtext.append("<br><br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='60%' align='center'><tbody>");
		  mailtext.append("<tr  bgcolor='#e8e7e8'>");
		  
		  if (dataFor!=null && dataFor.equalsIgnoreCase("Employee"))
		  {
			 mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Employee Name</b></td>");
			 if (moduleName!=null && moduleName.equalsIgnoreCase("HDM"))
			 {
				 mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Sub Department</b></td>");
			 }
		  }
		  else if (dataFor!=null && dataFor.equalsIgnoreCase("Category"))
		  {
			 mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Category</b></td>");
			 mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Sub&nbsp;-&nbsp;category</b></td>");
		  }
		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total&nbsp;Task</b></td>");
		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>On&nbsp;Time</b></td>");
		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Off&nbsp;Time</b></td>");
		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Missed</b></td>");
		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Snooze</b></td>");
		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ignore</b></td>");
		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>On&nbsp;Time&nbsp;(%)</b></td>");
		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Off&nbsp;Time&nbsp;(%) </b></td>");
		  mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Missed&nbsp;(%)</b></td>");
		  mailtext.append("</tr>");

		  for(FeedbackPojo object : dataList)
		  {
				mailtext.append("<tr  bgcolor='#e8e7e8'>");
				for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
				{
					String dataValue = null;
					if(object!=null)
					{
						if (dataFor!=null && dataFor.equalsIgnoreCase("Employee"))
						{
							if(titleList.get(cellIndex).toString().equalsIgnoreCase("empName") )
							{
								dataValue=object.getEmpName();
							}
							else if(titleList.get(cellIndex).toString().equalsIgnoreCase("feedback_by_subdept"))
							{
								dataValue=object.getFeedback_by_subdept();
							}
						}
						else if(dataFor!=null && dataFor.equalsIgnoreCase("Category"))
						{
							if(titleList.get(cellIndex).toString().equalsIgnoreCase("feedback_catg") )
							{
								dataValue=object.getFeedback_catg();
							}
							else if(titleList.get(cellIndex).toString().equalsIgnoreCase("feedback_subcatg"))
							{
								dataValue=object.getFeedback_subcatg();
							}
						}
					    if(titleList.get(cellIndex).toString().equalsIgnoreCase("counter"))
						{
							dataValue= object.getCounter();
						}
						else if(titleList.get(cellIndex).toString().equalsIgnoreCase("onTime"))
						{
							dataValue= object.getOnTime() ;
						}
						else if(titleList.get(cellIndex).toString().equalsIgnoreCase("offTime"))
						{
							dataValue= object.getOffTime();
						}
						else if(titleList.get(cellIndex).toString().equalsIgnoreCase("missed"))
						{
							dataValue=object.getMissed() ;
						}
						else if(titleList.get(cellIndex).toString().equalsIgnoreCase("snooze"))
						{
							dataValue= object.getSnooze() ;
						}
						else if(titleList.get(cellIndex).toString().equalsIgnoreCase("ignore"))
						{
							dataValue= object.getIgnore() ;
						}
						else if(titleList.get(cellIndex).toString().equalsIgnoreCase("perOnTime"))
						{
							dataValue=object.getPerOnTime() ;
						}
						else if(titleList.get(cellIndex).toString().equalsIgnoreCase("perOffTime"))
						{
							dataValue=object.getPerOffTime() ;
						}
						else if(titleList.get(cellIndex).toString().equalsIgnoreCase("perMissed"))
						{
							dataValue=object.getPerMissed() ;
						}
					}
					else
					{
						dataValue="NA";
					}
					mailtext.append("<td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+dataValue.trim().replace(" ", "&nbsp;")+"</td>");
				}
				mailtext.append("</tr>");
		  }
		  mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
		  mailtext.append("<BR>");
		  mailtext.append("<BR>");
		  mailtext.append("<br>");
		  mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
		  return mailtext.toString();
    }
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String beforeSendMailPage()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				String orgLevel = (String) session.get("orgnztnlevel");
				String orgId = (String) session.get("mappedOrgnztnId");
				List colmName = new ArrayList();
				Map<String, Object> order = new HashMap<String, Object>();
				Map<String, Object> wherClause = new HashMap<String, Object>();
				colmName.add("id");
				colmName.add("deptName");
				wherClause.put("orgnztnlevel", orgLevel);
				wherClause.put("mappedOrgnztnId", orgId);
				order.put("deptName", "ASC");
				HelpdeskUniversalAction HUA= new HelpdeskUniversalAction();
			    deptList= new LinkedHashMap<Integer, String>();
			    List departmentlist=HUA.getDataFromTable("department", colmName,wherClause, order, connectionSpace);
				if (departmentlist!=null && departmentlist.size()>0)
				{
					for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object[0]!=null && object[1]!=null)
						{
							deptList.put ( Integer.parseInt(object[0].toString()),object[1].toString());
						}
					}
				}
			   return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return  LOGIN;
		}
	}
	@SuppressWarnings("rawtypes")
	public String getEmployeeDataList()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
            	CommonOperInterface cbt = new CommonConFactory().createInterface();
            	if (deptId!=null && !deptId.equalsIgnoreCase("")) 
				{
					jsonArr = new JSONArray();
					SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
					StringBuilder query=new StringBuilder();
					query.append(" SELECT emp.id,emp.empName FROM employee_basic AS emp");
					query.append(" INNER JOIN subdepartment AS subDept ON subDept.id=emp.subdept");
					query.append(" INNER JOIN department AS dept ON dept.id=subDept.deptid");
					query.append(" WHERE dept.id IN(" + deptId + ") ORDER BY emp.empName ASC");
					
					List data2=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
					if (data2!=null && data2.size()>0) 
					{
						JSONObject formDetailsJson = new JSONObject();
						Object[] object = null;
						for (Iterator iterator = data2.iterator(); iterator.hasNext();) 
						{
							object = (Object[]) iterator.next();
							if (object!=null) 
							{
								formDetailsJson = new JSONObject();
								formDetailsJson.put("ID", object[0].toString());
								formDetailsJson.put("NAME", object[1].toString());
								jsonArr.add(formDetailsJson);
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
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return  LOGIN;
		}
	}
	
	public String getDivName()
	{
		return divName;
	}
	public void setDivName(String divName)
	{
		this.divName = divName;
	}
	public String getExcelName()
	{
		return excelName;
	}
	public void setExcelName(String excelName)
	{
		this.excelName = excelName;
	}
	public String getSelectedId()
	{
		return selectedId;
	}
	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
	}
	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}
	public void setColumnMap(Map<String, String> columnMap)
	{
		this.columnMap = columnMap;
	}
	public String getDataFor()
	{
		return dataFor;
	}
	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public FileInputStream getExcelStream()
	{
		return excelStream;
	}
	public void setExcelStream(FileInputStream excelStream)
	{
		this.excelStream = excelStream;
	}
	public InputStream getInputStream()
	{
		return inputStream;
	}
	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}
	public String getContentType()
	{
		return contentType;
	}
	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}
	public String[] getColumnNames()
	{
		return columnNames;
	}
	public void setColumnNames(String[] columnNames)
	{
		this.columnNames = columnNames;
	}
	public String getDeptId()
	{
		return deptId;
	}
	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}
	public String getSubdeptId()
	{
		return subdeptId;
	}
	public void setSubdeptId(String subdeptId)
	{
		this.subdeptId = subdeptId;
	}
	public String getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}
	public String getToDate()
	{
		return toDate;
	}
	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}
	public String getByDeptId()
	{
		return byDeptId;
	}
	public void setByDeptId(String byDeptId)
	{
		this.byDeptId = byDeptId;
	}
	public Map<Integer, String> getDeptList()
	{
		return deptList;
	}
	public void setDeptList(Map<Integer, String> deptList)
	{
		this.deptList = deptList;
	}
	public JSONArray getJsonArr()
	{
		return jsonArr;
	}
	public void setJsonArr(JSONArray jsonArr)
	{
		this.jsonArr = jsonArr;
	}
	public String getEmp()
	{
		return emp;
	}
	public void setEmp(String emp)
	{
		this.emp = emp;
	}
	public String getModuleName()
	{
		return moduleName;
	}
	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}
	
}