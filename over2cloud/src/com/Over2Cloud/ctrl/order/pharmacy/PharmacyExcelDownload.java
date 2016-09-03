package com.Over2Cloud.ctrl.order.pharmacy;
 
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
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

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
public class PharmacyExcelDownload 
{

	public String writeDataInExcel(List dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,SessionFactory connection)
	{

		String excelFileName = null;
		String mergeDateTime = new DateUtil().mergeDateTime();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(excelName);
		Map<String, CellStyle> styles = createStyles(wb);
		String resolutionTime= "NA";
		String priority = "NA";

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

		 
		excelFileName = "Pharma_Medicine_Data" + mergeDateTime + ".xls";

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
		if (dataList != null && dataList.size() > 0)
		{
			
			String ord_date=null, ord_time=null, dis_date=null, dis_time=null, encounter=null, closeDateinfo= "NA";
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				HSSFRow rowData=sheet.createRow((int)index);
				for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
				{
					System.out.println("KEY LIST "+keyList.get(cellIndex).toString());
					
					
					if(keyList.get(cellIndex).toString().equalsIgnoreCase("ORDER_DATE")){
						ord_date = object[cellIndex].toString();
					}
					
					if(keyList.get(cellIndex).toString().equalsIgnoreCase("ORDER_TIME")){
						ord_time = object[cellIndex].toString();
						ord_time = object[cellIndex].toString().split(" ")[0]+ ":"+object[cellIndex].toString().split(" ")[1];
					}
					
					if(keyList.get(cellIndex).toString().equalsIgnoreCase("DISPENSED_DATE")){
						dis_date = object[cellIndex].toString();
						 System.out.println("dis_date <<<<<<<<< "+dis_date+"");
					}
					if(keyList.get(cellIndex).toString().equalsIgnoreCase("DISPENSED_TIME")){
						dis_time = object[cellIndex].toString();
						 System.out.println("dis_time >>>>>>>.. "+dis_time);
					}
					
					if(keyList.get(cellIndex).toString().equalsIgnoreCase("encounter_id")){
						
						encounter = object[cellIndex].toString();
						closeDateinfo = fetchCloseData(encounter, connection);
						 System.out.println("encounter_id  encounter "+encounter+"");
						 System.out.println("dis_date "+dis_date);
					}
					if(keyList.get(cellIndex).toString().equalsIgnoreCase("DISP_QTY as dispatch_tot") && dis_date!=null && !dis_date.equalsIgnoreCase("NA") && !dis_time.equalsIgnoreCase("NA"))
					{
						Cell cell = rowData.createCell((int) cellIndex);
						cell.setCellValue(DateUtil.time_difference(ord_date, ord_time, dis_date, dis_time));
						cell.setCellStyle(cellStyle);
					}
					
					//REC_QTY as resolution_time
					
					if(keyList.get(cellIndex).toString().equalsIgnoreCase("REC_QTY as resolution_time") && closeDateinfo!=null && closeDateinfo!="NA" && dis_date!=null && !dis_date.equalsIgnoreCase("NA") && !dis_time.equalsIgnoreCase("NA"))
					{
						System.out.println("1 "+dis_date);
						System.out.println("1 "+dis_time);
						System.out.println("1 "+closeDateinfo);
						
						resolutionTime = DateUtil.time_difference(dis_date, dis_time, closeDateinfo.split("#")[0].toString(), closeDateinfo.split("#")[1].toString());
						Cell cell = rowData.createCell((int) cellIndex);
						cell.setCellValue(resolutionTime);
						cell.setCellStyle(cellStyle);
						System.out.println("cc    "+resolutionTime);
					}
					
					if (keyList.get(cellIndex).toString().equalsIgnoreCase("PRIORITY")){
						priority = object[cellIndex].toString();
						System.out.println("priority "+priority);
					}
					
					//sms_flag
					
					if(keyList.get(cellIndex).toString().equalsIgnoreCase("sms_flag as test"))
					{
						System.out.println("priority sms_flag as test "+priority);
						System.out.println("resolutionTime sms_flag as test "+resolutionTime);
						if(priority.equalsIgnoreCase("Urgent") && !resolutionTime.equalsIgnoreCase("NA")){
							System.out.println("resolutionTime "+resolutionTime);
							resolutionTime = resolutionTime.split(":")[0];
							int i = Integer.parseInt(resolutionTime.toString());
							if(i < 1){
								resolutionTime = "With In TAT";
							}
							else{
								resolutionTime="Out Of TAT";
							}
						}
						else if(priority.equalsIgnoreCase("Routine") && !resolutionTime.equalsIgnoreCase("NA")) {
							System.out.println("resolutionTime "+resolutionTime);
							resolutionTime = resolutionTime.split(":")[0];
							int i = Integer.parseInt(resolutionTime.toString());
							if(i < 2){
								resolutionTime = "With In TAT";
							}
							else{
								resolutionTime="Out Of TAT";
							}
						}
						else{
							resolutionTime ="NA";
						}
						Cell cell = rowData.createCell((int) cellIndex);
						cell.setCellValue(resolutionTime);
						cell.setCellStyle(	cellStyle);
						resolutionTime = "NA";
					}
					
					//sms_flag as close_bi_id
					
					
					//sms_flag as close_bi_name
					
					
					else
					{
						if(object[cellIndex]!=null)
						{
							if(object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
							{
								rowData.createCell((int) cellIndex).setCellValue(DateUtil.convertDateToIndianFormat(object[cellIndex].toString()));
							}
							else
							{
								if(!keyList.get(cellIndex).toString().equalsIgnoreCase("REC_QTY as resolution_time"))
								{
							 
									rowData.createCell((int) cellIndex).setCellValue(object[cellIndex].toString());
								}
								 
							}
						}
						else
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

	private String fetchCloseData(String encounter, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		String str = "NA";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			//System.out.println(" select action_date, action_time, close_by_id, close_by_name from pharma_patient_data_history where status='Close' and encounter_id='"+encounter+"'".toString());
			List lastIdList = cbt.executeAllSelectQuery(" select action_date, action_time, close_by_id, close_by_name from pharma_patient_data_history where status='Close' and encounter_id='"+encounter+"'".toString(), connection);
			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				String closeID = "NA";
				String closeName = "NA";
				
				if (object[2].toString().length()<3 && object[3].toString().length()<3){
					closeID="NA";
					closeName = "NA";
				}
				else{
					closeID=object[2].toString();
					closeName = object[3].toString();
				}
				str =  object[0].toString()+"#"+object[1].toString()+"#"+closeID+"#"+closeName;
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		return str;
	}

	public List getEscData(SessionFactory conn, String limit, String id)
	{
		String str = null;
		str = "Select osh.action_date,osh.action_time,osh.escalate_to from order_status_history as osh where osh.feedId='" + id + "' and osh.status like 'Escalate%' and osh.dept!='17' order by osh.id asc limit " + limit + ",1";
		// //System.out.println(str);
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List data4escalate = cbt.executeAllSelectQuery(str, conn);
		cbt = null;
		return data4escalate;
	}

	public List getSeenData(SessionFactory conn, String id, String status)
	{
		List data4escalate = null;
		try
		{
			String str = null;
			if (status.equalsIgnoreCase("Resolved"))
			{
				str = "Select osh.action_date,osh.action_time,md.finalActionBy,emp.empName from machine_order_data as md " + "inner join order_status_history as osh on md.id=osh.feedId " + "left join employee_basic as emp on emp.id=osh.action_by " + "where osh.feedId='" + id + "' and osh.status='Resolved' ";
			}

			else if (status.equalsIgnoreCase("Cart Assigned"))
			{
				str = "Select emp.empName as Allot_To,emp1.empName,his_ref.action_date,his_ref.action_time from order_status_history as his_ref" + " left join employee_basic as emp on emp.id=his_ref.allocate_to " + " left join employee_basic as emp1 on emp1.id = his_ref.action_by " + " where his_ref.feedId='" + id + "' and his_ref.status='Cart Assigned' order by his_ref.id asc limit 0,1";
			} else if (status.equalsIgnoreCase("Resolved by cart"))
			{
				str = "Select osh.action_date,osh.action_time,md.finalActionBy,emp.empName from machine_order_data as md " + "inner join order_status_history as osh on md.id=osh.feedId " + "left join employee_basic as emp on emp.id=osh.action_by " + "where osh.feedId='" + id + "' and osh.status='Resolved by cart' ";
			} else
			{
				str = "Select emp.empName,md.parkedTill,md.parkedTillTime,osh.action_reason from machine_order_data as md " + "inner join order_status_history as osh on md.id=osh.feedId inner join employee_basic as emp on " + "emp.id = osh.action_by where osh.status='Snooze' and osh.feedId='" + id + "'";
			}
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			data4escalate = cbt.executeAllSelectQuery(str, conn);
			cbt = null;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return data4escalate;
	}

	public List getInformedData(SessionFactory conn, String id)
	{
		String str = "Select his_ref.action_date,his_ref.action_time,emp.empName as Allot_To,emp1.empName from order_status_history as his_ref" + " left join employee_basic as emp on emp.id=his_ref.allocate_to " + " left join employee_basic as emp1 on emp1.id = his_ref.action_by " + " where his_ref.feedId='" + id + "' and his_ref.status='Assign' order by his_ref.id asc limit 0,1 ";

		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List data4escalate = cbt.executeAllSelectQuery(str, conn);
		cbt = null;
		return data4escalate;
	}

	public List getResolveData(SessionFactory conn, String id)
	{
		String str = "Select emp.empName from order_status_history as osh inner join employee_basic as emp on emp.id=osh.action_by where osh.feedId='" + id + "' and osh.status!='Resolved' or osh.status!='Un-assigned'";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List data4escalate = cbt.executeAllSelectQuery(str, conn);
		cbt = null;
		return data4escalate;
	}

	@SuppressWarnings("unused")
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


}