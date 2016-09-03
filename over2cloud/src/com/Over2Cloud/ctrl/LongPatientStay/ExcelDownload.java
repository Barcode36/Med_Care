package com.Over2Cloud.ctrl.LongPatientStay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;

public class ExcelDownload extends GridPropertyBean
{
	private Map<String, String> columnMap;
	private String status;
	private FileInputStream excelStream;
	private String userType = (String) session.get("userTpe");
	private String excelFileName, searchPeriodOn;
	private String[] columnNames;
	private String sponserType, excel_name;
	private String fromDate, toDate, dataFor, countFor = null;
	private String location, nursing, recordSize,doctor,speciality;
	private String fromDays, toDays, range, patDays,rangeForDate,patDaysForDate,sdate,edate;
	private String searching;
	private String type;
	private String taskName;
	private String fileName, frequency;
	private String rangeForDoenload;
	private String doneDoc;
	private String docName;

	public String longDataDownload()
	{
		try
		{
			if (session.containsKey("columnMap"))
				session.remove("columnMap");

			columnMap = new LinkedHashMap<String, String>();

			List<GridDataPropertyView> statusColName = Configuration.getConfigurationData("mapped_long_patient_stay_configuration", accountID, connectionSpace, "", 0, "table_name", "long_patient_stay_configuration");
			if (statusColName != null && statusColName.size() > 0)
			{
				for (GridDataPropertyView gdp : statusColName)
				{
					if (!gdp.getColomnName().equalsIgnoreCase("id"))
					{
						columnMap.put("divw." + gdp.getColomnName(), gdp.getHeadingName());
					}

				}
				if (columnMap != null && columnMap.size() > 0)
				{
					session.put("columnMap", columnMap);
				}
			}

		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String downloadlongPatentExcel()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		excelFileName = "Long Time Patient Stay";

		if (sessionFlag)
		{
			try
			{

				List keyList = new ArrayList();
				List titleList = new ArrayList();
				String startDate=null,endDate=null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String empIdofuser = (String) session.get("empIdofuser");
				if (range != null && range.equalsIgnoreCase("Other"))
				{

					if (fromDays != null && toDays != null && !fromDays.equalsIgnoreCase("-1") && !toDays.equalsIgnoreCase("-1"))
					{

						startDate = DateUtil.getDateAfterDays(-Integer.parseInt(toDays));
						endDate = DateUtil.getDateAfterDays(-Integer.parseInt(fromDays));

					}
				}
				else if(sdate!=null && edate!=null && rangeForDate!=null && patDaysForDate!=null && !patDaysForDate.equalsIgnoreCase("") && !sdate.equalsIgnoreCase("") && !edate.equalsIgnoreCase(""))
				{
					
					if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Equal"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
						endDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Greater"))
					{
						startDate=DateUtil.convertDateToUSFormat(sdate);
						endDate=DateUtil.getNextDateFromDate(DateUtil.getCurrentDateIndianFormat(), -(Integer.parseInt(patDaysForDate)+1));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("Less"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)-1));
						endDate=DateUtil.convertDateToUSFormat(edate);
					}
					else if(rangeForDate!=null && rangeForDate.equalsIgnoreCase("GreaterAndEqual"))
					{
						startDate=DateUtil.convertDateToUSFormat(sdate);
						endDate=DateUtil.getDateAfterDays(-Integer.parseInt(patDaysForDate));
					}
					else if(rangeForDate!=null  && rangeForDate.equalsIgnoreCase("LessAndEqual"))
					{
						startDate=DateUtil.getNextDateFromDate(edate, -(Integer.parseInt(patDaysForDate)));
						endDate=DateUtil.convertDateToUSFormat(edate);
					}
				}
				else if (range != null && !range.equalsIgnoreCase("Other"))
				{
					if (patDays != null && !patDays.equalsIgnoreCase(""))
					{
						startDate = DateUtil.getDateAfterDays(-Integer.parseInt(patDays));
					}
				}

				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;

				StringBuilder query = new StringBuilder();

				if (columnNames != null && columnNames.length > 0)
				{
					keyList = Arrays.asList(columnNames);
					Map<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap = (Map<String, String>) session.get("columnMap");
					if (session.containsKey("columnMap"))
						session.remove("columnMap");

					List dataList = null;
					for (int index = 0; index < keyList.size(); index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
					if (keyList != null && keyList.size() > 0)
					{
						query.append("SELECT ");
						int i = 0;
						for (Iterator it = keyList.iterator(); it.hasNext();)
						{
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (!obdata.toString().equalsIgnoreCase("divw.id"))
								{

									if (i < keyList.size() - 1)
									{
										query.append(obdata.toString() + ",");
									}
									else
									{
										query.append(obdata.toString() + " ");
									}
								}
							}
							i++;
						}
						query.append("  FROM dreamsol_ip_vw as divw  ");
						if (range != null && range.equalsIgnoreCase("Other"))
						{
							query.append(" where divw.ADMISSION_DATE Between  '" + startDate + "'  and  '" + endDate + "' ");
						}
						else if (range != null && range.equalsIgnoreCase("Greater"))
						{
							query.append(" where divw.ADMISSION_DATE < '" + startDate + "' ");
						}
						else if (range != null && range.equalsIgnoreCase("GreaterAndEqual"))
						{
							query.append(" where divw.ADMISSION_DATE <= '" + startDate + "' ");
						}
						else if (range != null && range.equalsIgnoreCase("Less"))
						{
							query.append(" and divw.ADMISSION_DATE > '" + startDate + "' ");
						}
						else if (range != null && range.equalsIgnoreCase("LessAndEqual"))
						{
							query.append(" where divw.ADMISSION_DATE >= '" + startDate + "' ");
						}
						else if (range != null && range.equalsIgnoreCase("Equal"))
						{
							query.append(" where divw.ADMISSION_DATE Between  '" + startDate + "'  and  '" + startDate + "' ");
						}
						else if(rangeForDate!=null && endDate!=null && startDate !=null )
						{
							query.append(" where divw.ADMISSION_DATE Between  '"+startDate+"'  and  '"+endDate+"' ");
						}
						else
						{
							query.append(" where divw.ADMISSION_DATE <= '"+DateUtil.getCurrentDateUSFormat()+"' ");
						}
						if (nursing != null && !nursing.equalsIgnoreCase("-1") && !nursing.equalsIgnoreCase("null"))
							query.append(" and divw.nursing_code In ("+nursing+") ");
						if (location != null && !location.equalsIgnoreCase("-1") && !location.equalsIgnoreCase("null"))
							query.append(" and divw.floor In("+location+") ");
						if(doctor !=null && !doctor.equalsIgnoreCase("-1") && !doctor.equalsIgnoreCase("null"))
							query.append(" and divw.ADMITTING_PRACTITIONER_NAME In ("+doctor+") ");
						if(speciality !=null && !speciality.equalsIgnoreCase("-1") && !speciality.equalsIgnoreCase("null"))
							query.append(" and divw.SPECIALITY In("+speciality+") ");
				
						String empid=(String)session.get("empName").toString();
						String[] subModuleRightsArray = new viewLongPatientStayActivityBoard().getSubModuleRights(empid);
						if(subModuleRightsArray != null && subModuleRightsArray.length > 0)
						{
							for(String s:subModuleRightsArray)
							{
								if(s.equals("longPatlocation_VIEW"))
								{
									String nursingUnit =new viewLongPatientStayActivityBoard().getNursingUnitByEmpId((String)session.get("empIdofuser").toString().trim().split("-")[1], connectionSpace, cbt);
									if(nursingUnit!=null && !nursingUnit.contains("All"))
										query.append(" and divw.nursing_code In ( "+nursingUnit+" )");
								}
								 
							}
						}
						
						query.append(" order by divw.ADMISSION_DATE ");
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						String orgDetail = (String) session.get("orgDetail");
						String orgName = "";
						if (orgDetail != null && !orgDetail.equals(""))
						{
							orgName = orgDetail.split("#")[0];
						}
						if (dataList != null && titleList != null && keyList != null)

						{
							excelFileName = writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, connectionSpace);
						}
						if (excelFileName != null)
						{
							excelStream = new FileInputStream(excelFileName);
						}
					}
					returnResult = SUCCESS;
				}
				else
				{
					addActionMessage("There are some error in data!!!!");
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
			returnResult = LOGIN;
		}
		return returnResult;

	}

	private String newDateOnCount(String USFDate, Integer days)
	{
		String returnResult = null;
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(USFDate));
			cal.add(Calendar.DATE, days);
			return sdf.format(cal.getTime());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return returnResult;
	}

	String isCommonSeparated(String s)
	{
		if (s.contains(","))
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.split(",").length; i++)
			{
				sb.append("'" + s.split(",")[i] + "'");
				if (i < s.split(",").length - 1)
				{
					sb.append(",");
				}
			}
			return sb.toString();
		}
		else
		{
			return "'" + s + "'";
		}
	}

	public String writeDataInExcel(List dataList, List titleList, List keyList, String excelName, String orgName, boolean needToStore, SessionFactory connection)
	{

		String mergeDateTime = new DateUtil().mergeDateTime();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(excelName);
		Map<String, CellStyle> styles = createStyles(wb);

		CellStyle cellStyle = wb.createCellStyle();
		Calendar calendar = new GregorianCalendar();
		calendar.set(1982, Calendar.NOVEMBER, 25);
		Row headerRow = sheet.createRow(2);
		headerRow.setHeightInPoints(15);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);

		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(22);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(orgName);
		titleCell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titleList.size() - 1));

		// Sub Title Row
		Row subTitleRow = sheet.createRow(1);
		subTitleRow.setHeightInPoints(18);
		Cell subTitleCell = subTitleRow.createCell(0);
		subTitleCell.setCellValue(excelName);
		subTitleCell.setCellStyle(styles.get("subTitle"));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, titleList.size() - 1));
		excel_name = excel_name + mergeDateTime + ".xls";
		int header_first = 2;
		int index = 3;
		HSSFRow rowHead = sheet.createRow((int) header_first);
		for (int i = 0; i < titleList.size(); i++)
		{
			Cell subTitleCell22 = rowHead.createCell((i));
			subTitleCell22.setCellValue(titleList.get(i).toString());
			subTitleCell22.setCellStyle(styles.get("mytittle"));
		}
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				HSSFRow rowData = sheet.createRow((int) index);
				for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
				{
					if (object[cellIndex] != null)
					{
						if (object[cellIndex].toString().trim().matches("\\d{4}-[01]\\d-[0-3]\\d"))
						{
							Cell cell = rowData.createCell((int) cellIndex);
							cell.setCellValue(DateUtil.convertDateToIndianFormat(object[cellIndex].toString()));
							cell.setCellStyle(cellStyle);
						}
						else
						{
							if (keyList.get(cellIndex).toString().trim().equalsIgnoreCase("send_status"))
							{
								Cell cell = rowData.createCell((int) cellIndex);
								cell.setCellValue(checkFlag(object[cellIndex].toString().trim()));
							}
							else
							{
								Cell cell = rowData.createCell((int) cellIndex);
								cell.setCellValue(object[cellIndex].toString());
							}

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
			FileOutputStream out = new FileOutputStream(new File(excel_name));
			wb.write(out);
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return excel_name;
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
		style.setFillForegroundColor(IndexedColors.WHITE.index);
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

		// mytittle
		Font headerFont22 = wb.createFont();
		headerFont.setFontHeightInPoints((short) 11);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("mytittle", style);

		return styles;
	}

	String checkNull(Object obj)
	{
		String returnResult = "NA";
		if (obj != null && !obj.toString().trim().isEmpty() && !obj.toString().trim().equals("-1"))
		{
			returnResult = obj.toString();
		}
		return returnResult;
	}

	String checkFlag(Object object)
	{

		if (object.toString().equalsIgnoreCase("3") || object.toString().equalsIgnoreCase("311") || object.toString().equalsIgnoreCase("322") || object.toString().equalsIgnoreCase("333") || object.toString().equalsIgnoreCase("344") || object.toString().equalsIgnoreCase("355") || object.toString().equalsIgnoreCase("366"))
		{
			return "Sent";
		}
		else if (object.toString().equals("5") || object.toString().equals("55555"))
		{
			return "Wrong Email";
		}
		else
		{
			return "Not Sent";
		}
	}

/** Download Excel for Ticketing Code Start here*/
	
	public String longDataDownloadTicket()
	{


		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				columnMap = new LinkedHashMap<String, String>();

				
				
				columnMap.put("cd.ticket_no","Ticket ID");
				columnMap.put("cd.UHIID","UHID");
				columnMap.put("cd.PATIENT_NAME","Patient Name");
				columnMap.put("cd.ASSIGN_BED_NUM","Assign Bed No");
				columnMap.put("cd.ATTENDING_PRACTITIONER_NAME","Attending Practitioner Name");
				columnMap.put("cd.ADMITTING_PRACTITIONER_NAME","ADMITTING PRACTITIONER NAME");
				columnMap.put("cd.SPECIALITY","SPECIALITY");
				columnMap.put("cd.ADMISSION_DATE","ADMISSION_DATE");
				columnMap.put("cd.ADMISSION_TIME","ADMISSION TIME");
				columnMap.put("cd.nursing_unit","Nursing Unit");
				columnMap.put("cd.nursing_code","Nursing Code");
				columnMap.put("cd.floor","Floor");
				columnMap.put("cd.allot_to","Allot To");   
				columnMap.put("cd.Day_count","Day Count");
				columnMap.put("cd.status","Current Status");
				columnMap.put("cd.escalation_date","Escalation Date");
				columnMap.put("cd.escalation_time","Escalation Time");  
				columnMap.put("cd.level","Level");
				
				// history Coloumn Add
				columnMap.put("cdh.action_date as first_action_time","Escalate 1 At");
				columnMap.put("cdh.comment as first_escalate_to","Escalate 1 To");
				
				columnMap.put("cdh.action_date as second_action_time","Escalate 2 At");
				columnMap.put("cdh.comment as second_escalate_to","Escalate 2 To ");
				
				columnMap.put("cdh.action_date as third_action_time","Escalate 3 At");
				columnMap.put("cdh.comment as third_escalate_to","Escalate 3 To");
				
				columnMap.put("cdh.action_date as four_action_time","Escalate 4 At");
				columnMap.put("cdh.comment as four_escalate_to","Escalate 4 To");
				
				columnMap.put("cdh.action_date as five_action_time","Escalate 5 At");
				columnMap.put("cdh.comment as five_escalate_to","Escalate 5 To");
				
				columnMap.put("cdh.action_date as close_time","Close At");
				columnMap.put("cdh.comment as close_by","Close By");
				
				if (columnMap != null && columnMap.size() > 0)
				{
					session.put("columnMap", columnMap);
				}
				return SUCCESS;
			} catch (Exception ex)
			{
				ex.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}

	
	}

	
	public String excelDownload()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		excelFileName = "Long Time Stay Ticketing";

		if (sessionFlag)
		{
			try
			{
				List keyList = new ArrayList();
				List titleList = new ArrayList();
				String patient_id=null;
				com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction CUACtrl = new com.Over2Cloud.ctrl.compliance.ComplianceUniversalAction();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String empIdofuser = (String) session.get("empIdofuser");
				if (empIdofuser == null || empIdofuser.split("-")[1].trim().equals(""))
					return ERROR;

				StringBuilder query = new StringBuilder();
				if (fromDays != null && toDays != null && !fromDays.equalsIgnoreCase("") && !toDays.equalsIgnoreCase(""))
				{
					String str[] = getFromDays().split("-");
					if (str[0] != null && str[0].length() > 3)
					{
						patient_id=fetchPatientID(fromDays,toDays,status);
					} else
					{
						patient_id=fetchPatientID((DateUtil.convertDateToUSFormat(fromDays)),(DateUtil.convertDateToUSFormat(toDays)),status);
					}
					
				}
				if (columnNames != null && columnNames.length > 0)
				{
					keyList = Arrays.asList(columnNames);
					Map<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap = (Map<String, String>) session.get("columnMap");
					//tempMap.put("cd.id", "ID");
					if (session.containsKey("columnMap"))
						session.remove("columnMap");

					List dataList = null;
					for (int index = 0; index < keyList.size(); index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
					if (keyList != null && keyList.size() > 0)
					{
						query.append(" select cdh.dream_id,cdh.action_date,cdh.action_time,cdh.comment,cdh.status from dreamsol_ip_vw_ticket_history as cdh  where cdh.dream_id in("+patient_id+") order by cdh.id asc");
						System.out.println(">>>>history<<<<<<    "+query.toString());
						List historyNotInfor = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						query = null;
						
						query = new StringBuilder();
						
						 
						query.append("SELECT DISTINCT ");
						int i = 0;
						for (Iterator it = keyList.iterator(); it.hasNext();)
						{
							Object obdata = (Object) it.next();
							if (obdata != null) 
							{
							/*	if (!obdata.toString().equalsIgnoreCase("cdh.comments as not_inform") && !obdata.toString().equalsIgnoreCase("cdh.comments as inform") && !obdata.toString().equalsIgnoreCase("cdh.comments as close") && !obdata.toString().equalsIgnoreCase("cdh.comments as close-p"))
								{*/
										if (i < keyList.size() - 1)
										{
											query.append(obdata.toString() + ",");
										}
										 else
										{
											query.append(obdata.toString()+", ");
										}
							}
							i++;
						}
						query.append("cd.id ");
						query.append(" FROM dreamsol_ip_vw_ticket as cd ");
						query.append(" inner join dreamsol_ip_vw_ticket_history as cdh on cdh.dream_id=cd.id ");
						query.append(" where cd.id >'0' ");
						/*
						if(status!=null && !status.equalsIgnoreCase("-1"))
						{
							if(!status.equalsIgnoreCase("All Status"))
							{
								query.append(" AND cd.status='"+status+"'");
							}
							
						}*/
						if(location!=null && !location.equalsIgnoreCase("-1"))
						{
							if(!location.equalsIgnoreCase("null"))
							{
								query.append(" AND cd.floor IN ("+location+")");
							}
							
						}
						
						if(nursing!=null && !nursing.equalsIgnoreCase("-1"))
						{
							if(!nursing.equalsIgnoreCase("null"))
							{
								query.append(" AND cd.nursing_code IN ("+nursing+")");
							}
							
						}
						
						if (fromDays != null && toDays != null && !fromDays.equalsIgnoreCase("") && !toDays.equalsIgnoreCase(""))
						{
							
							String str[] = getFromDays().split("-");
						
							if (str[0] != null && str[0].length() > 3)
							{
								query.append(" and cd.ADMISSION_DATE BETWEEN '" + fromDate + "' AND '" + toDate + "'");
							} else
							{
								query.append(" and cd.ADMISSION_DATE BETWEEN '" + (DateUtil.convertDateToUSFormat(fromDays)) + "' AND '" + (DateUtil.convertDateToUSFormat(toDays)) + "'");
							}
							
						}
						
						
						
						query.append(" GROUP BY cd.id ORDER BY cd.id DESC ");
						System.out.println("<<<<>>>>>>>>>>>>>> "+query);
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						System.out.println("<<<<>>>>>>>>>>>>>> "+dataList.size());
						String orgDetail = (String) session.get("orgDetail");
						String orgName = "";
						if (orgDetail != null && !orgDetail.equals(""))
						{
							orgName = orgDetail.split("#")[0];
						}
						if (dataList != null && titleList != null && keyList != null)
							
						{
							excelFileName = writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, historyNotInfor, connectionSpace);
						}
						if (excelFileName != null)
						{
							excelStream = new FileInputStream(excelFileName);
						}
					}
					returnResult = SUCCESS;
				} else
				{
					addActionMessage("There are some error in data!!!!");
					returnResult = ERROR;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;


	}
	

	public String fetchPatientID(String fromDate,String toDate, String status)
	{
		String patientId="";
		String temp=null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query=new StringBuilder(" select id from dreamsol_ip_vw_ticket where ADMISSION_DATE BETWEEN '" + fromDate + "' AND '" + toDate + "'");
		if(status!=null && !status.equalsIgnoreCase("-1"))
		{
			if(!status.equalsIgnoreCase("All Status"))
			{
				query.append(" AND status='"+status+"'");
			}
			
		}
		/*else
		{
			query.append(" AND status='Pending'");
		}*/
		System.out.println("......patient id query>>>>>>>...."+query);
		List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		if (data != null && data.size() > 0)
		{
			patientId = data.get(0).toString();
			for(Object s:data)
			{
				temp=temp+"'"+s.toString()+"'"+",";
			}
			temp=temp.substring(4,temp.length()-1);
		//	System.out.println("temptemptemptemptemp "+temp);
		}
		System.out.println("return set of id is>>>>>>>>>>> "+temp );
		return temp;
	}
	
	public String writeDataInExcel(List dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,List  historyNotInfor,SessionFactory connection)
	{

		String excelFileName = null;
		String mergeDateTime = new DateUtil().mergeDateTime();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(excelName);
		Map<String, CellStyle> styles = createStyles(wb);

		CellStyle cellStyle = wb.createCellStyle();
		Calendar calendar = new GregorianCalendar();
		calendar.set(1982, Calendar.NOVEMBER, 25);
		Row headerRow = sheet.createRow(2);
		headerRow.setHeightInPoints(15);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);

		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(22);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(orgName);
		titleCell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, titleList.size() - 1));

		// Sub Title Row
		Row subTitleRow = sheet.createRow(1);
		subTitleRow.setHeightInPoints(18);
		Cell subTitleCell = subTitleRow.createCell(0);
		subTitleCell.setCellValue(excelName);
		subTitleCell.setCellStyle(styles.get("subTitle"));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, titleList.size() - 1));

		 
		excelFileName = "AdmissionTicket_" + mergeDateTime + ".xls";

		int header_first = 2;
		int index = 3;
		HSSFRow rowHead = sheet.createRow((int) header_first);
		String time = null;
		for (int i = 0; i < titleList.size(); i++)
		{
			Cell subTitleCell22 = rowHead.createCell((i));
			/*
			 * if(i!=0) {
			 */
			subTitleCell22.setCellValue(titleList.get(i).toString());
			subTitleCell22.setCellStyle(styles.get("mytittle"));
			/* } */
		}
		String actiondate = null, actiontime = null;
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				HSSFRow rowData = sheet.createRow((int) index);

				for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
				{
					
					//System.out.println(keyList.get(cellIndex).toString());
					 if (keyList.get(cellIndex).toString() != null && keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as first_action_time") 
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as first_escalate_to")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as second_action_time")
							  || keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as second_escalate_to") 
								|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as third_action_time")
								|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as third_escalate_to")
								  || keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as four_action_time") 
									|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as four_escalate_to")
									|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as five_action_time")
									|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as five_escalate_to") 
									|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as close_time")
									|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as close_by")
									)
					{
						// System.out.println("A");
						
						if (historyNotInfor != null && historyNotInfor.size() > 0)//
						{
							int in=0;
						//	System.out.println("B");
							for (Iterator ite = historyNotInfor.iterator() ; ite.hasNext();)
							{
								Object[] obje = (Object[]) ite.next();
								//System.out.println(obje[0]+"...."+object[31]);     //29,30
								if (obje[0].toString().equalsIgnoreCase(object[30].toString()))
								{
									if(in==0 )
										{
											if(obje[4].toString().equalsIgnoreCase("Close"))
											{
												if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as first_action_time"))
												{
													if (obje[1] != null && obje[4] != null )
													{
														Cell cell = rowData.createCell((int) 28);
														cell.setCellValue(getValueWithNullCheck(obje[4].toString())+" On "+getValueWithNullCheck(obje[1].toString())+", "+getValueWithNullCheck(obje[2].toString()));
														cell.setCellStyle(cellStyle);
													} else
													{
														rowData.createCell((int) 28).setCellValue("NA");
													}
												}
												else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as first_escalate_to"))
												{
													if (obje[3] != null )
													{
														Cell cell = rowData.createCell((int) 29);
														cell.setCellValue(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(obje[3].toString())));
														cell.setCellStyle(cellStyle);
													} else
													{
														rowData.createCell((int) 29).setCellValue("NA");
													}
												}
												
											}
											else
											{
												if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as first_action_time"))
												{
													if (obje[1] != null && obje[4] != null )
													{
														Cell cell = rowData.createCell((int) cellIndex);
														cell.setCellValue(getValueWithNullCheck(obje[4].toString())+" On "+getValueWithNullCheck(obje[1].toString())+", "+getValueWithNullCheck(obje[2].toString()));
														cell.setCellStyle(cellStyle);
													} else
													{
														rowData.createCell((int) cellIndex).setCellValue("NA");
													}
												}
												else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as first_escalate_to"))
												{
													if (obje[3] != null )
													{
														Cell cell = rowData.createCell((int) cellIndex);
														cell.setCellValue(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(obje[3].toString())));
														cell.setCellStyle(cellStyle);
													} else
													{
														rowData.createCell((int) cellIndex).setCellValue("NA");
													}
												}
												
												
											}
										
										}
									if(in==1  )
									{
										if(obje[4].toString().equalsIgnoreCase("Close"))
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as second_action_time"))
											{
												if (obje[1] != null && obje[4] != null )
												{
													Cell cell = rowData.createCell((int) 28);
													cell.setCellValue(getValueWithNullCheck(obje[4].toString())+" On "+getValueWithNullCheck(obje[1].toString())+", "+getValueWithNullCheck(obje[2].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) 28).setCellValue("NA");
												}
											}
											else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as second_escalate_to"))
											{
												if (obje[3] != null )
												{
													Cell cell = rowData.createCell((int) 29);
													cell.setCellValue(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(obje[3].toString())));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) 29).setCellValue("NA");
												}
											}
											
										}
										else
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as second_action_time"))
											{
												if (obje[1] != null && obje[4] != null )
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(getValueWithNullCheck(obje[4].toString())+" On "+getValueWithNullCheck(obje[1].toString())+", "+getValueWithNullCheck(obje[2].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}
										else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as second_escalate_to"))   
										{
											if (obje[3] != null )
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[3].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}
										}
										}
										
									
									}
									if(in==2 )
									 
									{
										if(obje[4].toString().equalsIgnoreCase("Close"))
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as third_action_time"))
											{
												if (obje[1] != null && obje[4] != null )
												{
													Cell cell = rowData.createCell((int) 28);
													cell.setCellValue(getValueWithNullCheck(obje[4].toString())+" On "+getValueWithNullCheck(obje[1].toString())+", "+getValueWithNullCheck(obje[2].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) 28).setCellValue("NA");
												}
											}
											else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as third_escalate_to"))
											{
												if (obje[3] != null )
												{
													Cell cell = rowData.createCell((int) 29);
													cell.setCellValue(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(obje[3].toString())));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) 29).setCellValue("NA");
												}
											}
											
										}
										else
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as third_action_time"))  
											{
												if (obje[1] != null && obje[4] != null )
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(getValueWithNullCheck(obje[4].toString())+" On "+getValueWithNullCheck(obje[1].toString())+", "+getValueWithNullCheck(obje[2].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}
											else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as third_escalate_to")) 
											{
												if (obje[3] != null )
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(obje[3].toString());
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}
										}
									
									}
									if(in==3 )
										
									{
										
										if(obje[4].toString().equalsIgnoreCase("Close"))
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as four_action_time"))
											{
												if (obje[1] != null && obje[4] != null )
												{
													Cell cell = rowData.createCell((int) 28);
													cell.setCellValue(getValueWithNullCheck(obje[4].toString())+" On "+getValueWithNullCheck(obje[1].toString())+", "+getValueWithNullCheck(obje[2].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) 28).setCellValue("NA");
												}
											}
											else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as four_escalate_to"))
											{
												if (obje[3] != null )
												{
													Cell cell = rowData.createCell((int) 29);
													cell.setCellValue(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(obje[3].toString())));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) 29).setCellValue("NA");
												}
											}
											
										}
										else
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as four_action_time"))  
											{
												if (obje[1] != null && obje[4] != null )
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(getValueWithNullCheck(obje[4].toString())+" On "+getValueWithNullCheck(obje[1].toString())+", "+getValueWithNullCheck(obje[2].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}
										
											
											else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as four_escalate_to"))
											{
												if (obje[3] != null )
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(getValueWithNullCheck(obje[3].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}
											
											
										}
										
									 
									}
									if(in==4 )
									{
										

										if(obje[4].toString().equalsIgnoreCase("Close"))
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as five_action_time"))
											{
												if (obje[1] != null && obje[4] != null )
												{
													Cell cell = rowData.createCell((int) 28);
													cell.setCellValue(getValueWithNullCheck(obje[4].toString())+" On "+getValueWithNullCheck(obje[1].toString())+", "+getValueWithNullCheck(obje[2].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) 28).setCellValue("NA");
												}
											}
											else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as five_escalate_to"))
											{
												if (obje[3] != null )
												{
													Cell cell = rowData.createCell((int) 29);
													cell.setCellValue(DateUtil.convertDateToIndianFormat(getValueWithNullCheck(obje[3].toString())));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) 29).setCellValue("NA");
												}
											}
											
										}
										else
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as five_action_time"))
											{
												if (obje[1] != null && obje[4] != null )
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(getValueWithNullCheck(obje[4].toString())+" On "+getValueWithNullCheck(obje[1].toString())+", "+getValueWithNullCheck(obje[2].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}
											 else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as five_escalate_to"))
										{
											if (obje[3] != null )
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[3].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}
										}
											
										}
										 
									}
									if(in==5)
									{
										if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_date as close_time"))
										{
											if (obje[1] != null && obje[4] != null )
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(getValueWithNullCheck(obje[4].toString())+" On "+getValueWithNullCheck(obje[1].toString())+", "+getValueWithNullCheck(obje[2].toString()));
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}
										}
										else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comment as close_by"))
										{
											if (obje[3] != null )
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[3].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}
										}
									}
									
									
								in++;
								}
							
							}	
							
						} else{
							
							rowData.createCell((int) cellIndex).setCellValue("NA");
						}
						
					} 
		
					else
					{
					//	System.out.println("C");
						if (object[cellIndex] != null)
						{
							Cell cell = rowData.createCell((int) cellIndex);
							cell.setCellValue(object[cellIndex].toString());
						} else
						{
							rowData.createCell((int) cellIndex).setCellValue("NA");
						}
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
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return excelFileName;
	}
	
	//test null value by ths method
	public String getValueWithNullCheck(Object value)
	{
		return ((value==null || value.toString().equals("") ) ?  "NA" : value.toString());
	}
	
	
	
	
	
	/** Download Excel for Ticketing Code END here*/
	
	
	
	// setter/ getter

	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap)
	{
		this.columnMap = columnMap;
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

	public FileInputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public String[] getColumnNames()
	{
		return columnNames;
	}

	public void setColumnNames(String[] columnNames)
	{
		this.columnNames = columnNames;
	}

	public String getSponserType()
	{
		return sponserType;
	}

	public void setSponserType(String sponserType)
	{
		this.sponserType = sponserType;
	}

	public String getExcel_name()
	{
		return excel_name;
	}

	public void setExcel_name(String excelName)
	{
		excel_name = excelName;
	}

	public String getDataFor()
	{
		return dataFor;
	}

	public void setDataFor(String dataFor)
	{
		this.dataFor = dataFor;
	}

	public String getCountFor()
	{
		return countFor;
	}

	public void setCountFor(String countFor)
	{
		this.countFor = countFor;
	}

	public String getUserType()
	{
		return userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getNursing()
	{
		return nursing;
	}

	public void setNursing(String nursing)
	{
		this.nursing = nursing;
	}

	public String getRecordSize()
	{
		return recordSize;
	}

	public void setRecordSize(String recordSize)
	{
		this.recordSize = recordSize;
	}

	public String getFromDays()
	{
		return fromDays;
	}

	public void setFromDays(String fromDays)
	{
		this.fromDays = fromDays;
	}

	public String getToDays()
	{
		return toDays;
	}

	public void setToDays(String toDays)
	{
		this.toDays = toDays;
	}

	public String getRange()
	{
		return range;
	}

	public void setRange(String range)
	{
		this.range = range;
	}

	public String getPatDays()
	{
		return patDays;
	}

	public void setPatDays(String patDays)
	{
		this.patDays = patDays;
	}

	public String getSearching()
	{
		return searching;
	}

	public void setSearching(String searching)
	{
		this.searching = searching;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getTaskName()
	{
		return taskName;
	}

	public void setTaskName(String taskName)
	{
		this.taskName = taskName;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getDoneDoc()
	{
		return doneDoc;
	}

	public void setDoneDoc(String doneDoc)
	{
		this.doneDoc = doneDoc;
	}

	public String getDocName()
	{
		return docName;
	}

	public void setDocName(String docName)
	{
		this.docName = docName;
	}

	public String getSearchPeriodOn()
	{
		return searchPeriodOn;
	}

	public void setSearchPeriodOn(String searchPeriodOn)
	{
		this.searchPeriodOn = searchPeriodOn;
	}

	public String getFrequency()
	{
		return frequency;
	}

	public void setFrequency(String frequency)
	{
		this.frequency = frequency;
	}

	public String getRangeForDoenload()
	{
		return rangeForDoenload;
	}

	public void setRangeForDoenload(String rangeForDoenload)
	{
		this.rangeForDoenload = rangeForDoenload;
	}

	public String getRangeForDate()
	{
		return rangeForDate;
	}

	public void setRangeForDate(String rangeForDate)
	{
		this.rangeForDate = rangeForDate;
	}

	public String getPatDaysForDate()
	{
		return patDaysForDate;
	}

	public void setPatDaysForDate(String patDaysForDate)
	{
		this.patDaysForDate = patDaysForDate;
	}

	public String getSdate()
	{
		return sdate;
	}

	public void setSdate(String sdate)
	{
		this.sdate = sdate;
	}

	public String getEdate()
	{
		return edate;
	}

	public void setEdate(String edate)
	{
		this.edate = edate;
	}

	public String getDoctor()
	{
		return doctor;
	}

	public void setDoctor(String doctor)
	{
		this.doctor = doctor;
	}

	public String getSpeciality()
	{
		return speciality;
	}

	public void setSpeciality(String speciality)
	{
		this.speciality = speciality;
	}

}