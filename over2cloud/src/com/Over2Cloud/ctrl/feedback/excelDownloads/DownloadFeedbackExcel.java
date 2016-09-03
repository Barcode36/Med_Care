package com.Over2Cloud.ctrl.feedback.excelDownloads;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.ctrl.feedback.activity.ActivityPojo;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.FileOutputStream;
import java.util.HashMap;

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

public class DownloadFeedbackExcel extends ActionSupport
{
	private String downloadType, fromDate, toDate, status, feedBack, mode, ticket_no, feedBy, dept, catg, subCat, clientId, wildsearch;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private String downloadFile;
	private String titles;
	private InputStream fileInputStream;
	private Map<String, String> columnMap;
	private String excelFileName;
	private String selectedId;
	private FileInputStream excelStream;
	private InputStream inputStream;
	private String contentType;
	private String[] columnNames;
	Map session = ActionContext.getContext().getSession();
	String accountID = (String) session.get("accountid");
	String userName = (String) session.get("uName");
	String deptLevel = (String) session.get("userDeptLevel");
	String userType = (String) session.get("userTpe");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

	private List<ActivityPojo> feedbackList;
	public String beforeSelectFieldsToDownload()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// Add fields Accordingly for new fields
				
				columnMap = new LinkedHashMap<String, String>();
				columnMap.put("feedback.clientId", "MRD No");
				columnMap.put("feedback.patientId", "Patient Id");
				columnMap.put("feedback.feed_by", "Patient Name");
				columnMap.put("feedback.location", "Room No");
				columnMap.put("feedback.via_from", "Mode");
				columnMap.put("feedback.open_date", "Open On");
				columnMap.put("feedback.open_time", "Open At");
				columnMap.put("feedback.status as fstatus", " Status");
				columnMap.put("feedback.ticket_no as ticket_no", "Ticket No");
				columnMap.put("dept2.deptName as todept", " To Department");
				columnMap.put("emp.empName as allot_to", "Alloted To");
				columnMap.put("feedtype.fbType", "Type");
				columnMap.put("catg.categoryName", "Category");
				columnMap.put("subcatg.subCategoryName", "Sub Category");
				columnMap.put("feedback.feed_brief", "Brief");
				columnMap.put("feedback.resolve_remark","RCA");
				columnMap.put("feedback.spare_used", "CAPA");
				columnMap.put("feedback.feedback_remarks", "Admin Comments");
				
				columnMap.put("feedback.level as ropen_tat", "Level");
				columnMap.put("feedback.feed_registerby", "Registered By");

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

	@SuppressWarnings("unchecked")
	public String downloadFeedbackExcel()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		excelFileName = "Feedback Details";

		if (sessionFlag)
		{
			try
			{
				HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
				String searchField = null, alias = null;
				feedbackList = new ArrayList<ActivityPojo>();
				List keyList = new ArrayList();
				List titleList = new ArrayList();
				List data = null;
				String dept_id = "";
				String emp_id = "";
				String userType = "";

				if (toDate != null && fromDate != null)
				{
					String str = toDate.split("-")[2];
					if (str.length() > 2)
					{
						toDate = DateUtil.convertDateToUSFormat(toDate);
						fromDate = DateUtil.convertDateToUSFormat(fromDate);
					}
				}

				List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						dept_id = object[3].toString();
						emp_id = object[5].toString();
						userType = object[7].toString();
					}
				}

				DownloadFeedbackHelper downloadHelper=new DownloadFeedbackHelper();
				if (columnNames != null && columnNames.length > 0)
				{
					keyList = Arrays.asList(columnNames);
					Map<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap = (Map<String, String>) session.get("columnMap");
					if (session.containsKey("columnMap"))
						session.remove("columnMap");

					for (int index = 0; index < keyList.size(); index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
					if (keyList != null && keyList.size() > 0)
					{
						data = downloadHelper.getQuerryList(keyList, getFeedBack(), getTicket_no(), getMode(), getFeedBy(), getDept(), getCatg(), getSubCat(), getStatus(), getFromDate(), getToDate(), getWildsearch(), getClientId(), userType, emp_id, dept_id, userName, connectionSpace);
						data.addAll(downloadHelper.getQuerryListRating(keyList, getFeedBack(), getTicket_no(), getMode(), getFeedBy(), getDept(), getCatg(), getSubCat(), getStatus(), getFromDate(), getToDate(), getWildsearch(), getClientId(), userType, emp_id, dept_id, userName, connectionSpace));
						if (data != null && data.size() > 0 && titleList != null && keyList != null)
						{
							String orgDetail = (String) session.get("orgDetail");
							String orgName = "";
							if (orgDetail != null && !orgDetail.equals(""))
							{
								orgName = orgDetail.split("#")[0];
							}
							excelFileName = writeDataInExcel(data, titleList, keyList, excelFileName, orgName, true, connectionSpace);
							if (excelFileName != null)
							{
								excelStream = new FileInputStream(excelFileName);
							}
						}
					}
				}
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String writeDataInExcel(List dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,SessionFactory connection)
	{ 
		String excelFileName =null;
		String mergeDateTime=new DateUtil().mergeDateTime();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(excelName);
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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,titleList.size()-1));
		
		// Sub Title Row
		Row subTitleRow = sheet.createRow(1);
		subTitleRow.setHeightInPoints(18);
		Cell subTitleCell = subTitleRow.createCell(0);
		subTitleCell.setCellValue(excelName);
		subTitleCell.setCellStyle(styles.get("subTitle"));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,titleList.size()-1));
		
		// Creating the folder for holding the Excel files with the defined name
		/*if(needToStore)
			excelFileName = new CreateFolderOs().createUserDir("Feedback_Status")+ "/" +"Feedback"+ mergeDateTime+".xls";
		else*/
			excelFileName="Feedback"+ mergeDateTime;
		
		int header_first=2;
		int index=3;
		HSSFRow rowHead = sheet.createRow((int) header_first);
		for(int i=0;i<titleList.size();i++)
		{
			rowHead.createCell((int) i).setCellValue(titleList.get(i).toString());
		}
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				HSSFRow rowData=sheet.createRow((int)index);
				for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
				{
					if(object[cellIndex]!=null)
					{
						if(object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
						{
							rowData.createCell((int) cellIndex).setCellValue(DateUtil.convertDateToIndianFormat(object[cellIndex].toString()));
						}
						else
						{
							 
						 
								rowData.createCell((int) cellIndex).setCellValue(object[cellIndex].toString());
							 
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
 
	
	@SuppressWarnings("unused")
	private Map<String, CellStyle> createStyles(Workbook wb) {
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
	
	
	/*
	 * public String downloadFeedbackExcel() throws Exception { boolean valid =
	 * ValidateSession.checkSession(); if (valid) { try {
	 * System.out.println("To Dwnload Excel..."+titles);
	 * 
	 * Map session = ActionContext.getContext().getSession(); String accountID =
	 * (String) session.get("accountid"); String userName = (String)
	 * session.get("uName"); String deptLevel = (String)
	 * session.get("userDeptLevel"); String userType = (String)
	 * session.get("userTpe"); SessionFactory connectionSpace = (SessionFactory)
	 * session.get("connectionSpace"); String[] headerdata=null;
	 * 
	 * String dept_subdept_id = ""; String loggedEmpId = ""; String
	 * loggedEmpName = ""; List empData = new
	 * FeedbackUniversalAction().getEmpDataByUserName
	 * (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel); if
	 * (empData != null && empData.size() > 0) { for (Iterator iterator =
	 * empData.iterator(); iterator.hasNext();) { Object[] object = (Object[])
	 * iterator.next(); loggedEmpName = object[0].toString(); dept_subdept_id =
	 * object[3].toString(); loggedEmpId = object[5].toString(); } } List data =
	 * new DownloadFeedbackHelper().getQuerryList(getFeedBack(), getTicket_no(),
	 * getMode(), getFeedBy(), getDept(), getCatg(), getSubCat(), getStatus(),
	 * getFromDate(), getToDate(), getWildsearch(), getClientId(), userType,
	 * loggedEmpId, dept_subdept_id, userName, connectionSpace);
	 * 
	 * if(getTitles()!=null) { headerdata=titles.split(","); }
	 * 
	 * System.out.println("headerdata is as "+headerdata);
	 * 
	 * //System.out.println("Download Type "+getDownloadType()); // if
	 * (getDownloadType() != null &&
	 * getDownloadType().equalsIgnoreCase("downloadExcel")) {
	 * DownldeExcelFeedback writerexcel = new DownldeExcelFeedback();
	 * CreateFolderOs crtobjos = new CreateFolderOs(); downloadFile =
	 * writerexcel.createExcel(getText("Feedback Details"),
	 * getText("downloadSheet"), getText("Feedback Excel Report"), data,
	 * headerdata, "productdetails", crtobjos.createUserDir("downloadR"));
	 * 
	 * fileInputStream = new FileInputStream(new File(downloadFile));
	 * 
	 * downloadFile = downloadFile.substring(downloadFile.lastIndexOf("//") + 2,
	 * downloadFile.length()); }
	 * 
	 * return "success"; } catch (Exception e) { e.printStackTrace(); return
	 * "error"; } } else { return "login"; } }
	 */

	public String getDownloadType()
	{
		return downloadType;
	}

	public void setDownloadType(String downloadType)
	{
		this.downloadType = downloadType;
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

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getFeedBack()
	{
		return feedBack;
	}

	public void setFeedBack(String feedBack)
	{
		this.feedBack = feedBack;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public String getTicket_no()
	{
		return ticket_no;
	}

	public void setTicket_no(String ticket_no)
	{
		this.ticket_no = ticket_no;
	}

	public String getFeedBy()
	{
		return feedBy;
	}

	public void setFeedBy(String feedBy)
	{
		this.feedBy = feedBy;
	}

	public String getDept()
	{
		return dept;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getSubCat()
	{
		return subCat;
	}

	public void setSubCat(String subCat)
	{
		this.subCat = subCat;
	}

	public String getWildsearch()
	{
		return wildsearch;
	}

	public void setWildsearch(String wildsearch)
	{
		this.wildsearch = wildsearch;
	}

	public String getDownloadFile()
	{
		return downloadFile;
	}

	public void setDownloadFile(String downloadFile)
	{
		this.downloadFile = downloadFile;
	}

	public String getTitles()
	{
		return titles;
	}

	public void setTitles(String titles)
	{
		this.titles = titles;
	}

	public InputStream getFileInputStream()
	{
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream)
	{
		this.fileInputStream = fileInputStream;
	}

	public String getCatg()
	{
		return catg;
	}

	public void setCatg(String catg)
	{
		this.catg = catg;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap)
	{
		this.columnMap = columnMap;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public String getSelectedId()
	{
		return selectedId;
	}

	public void setSelectedId(String selectedId)
	{
		this.selectedId = selectedId;
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

	public List<ActivityPojo> getFeedbackList()
	{
		return feedbackList;
	}

	public void setFeedbackList(List<ActivityPojo> feedbackList)
	{
		this.feedbackList = feedbackList;
	}
}
