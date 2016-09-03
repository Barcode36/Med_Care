package com.Over2Cloud.compliance.serviceFiles;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.ctrl.compliance.ComplianceDashboardBean;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;


public class DailyCompReportExcelWrite4Attach
{
	private static final Log log = LogFactory.getLog(DailyCompReportExcelWrite4Attach.class);
	
	
	@SuppressWarnings({ "static-access", "unchecked" })
	public String writeDataInExcel(List<ComplianceDashboardBean> currentDayResolvedData,List<ComplianceDashboardBean> currentDayPendingData,String deptLevel,String report_type)
	{
		String excelFileName =null;
	    String mergeDateTime="Compliance Report On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTime();
	    mergeDateTime=mergeDateTime.replace(":","");
	    //String mergeDateTime=new DateUtil().mergeDateTime();
	    String sheetName = "Compliance Details On "+DateUtil.getCurrentDateIndianFormat() ;
	    List orgData= new LoginImp().getUserInfomation("8", "IN");
		String orgName="";
		
		if (orgData!=null && orgData.size()>0) {
			for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				orgName=object[0].toString();
			}
		}
	    String reportType="";
	    if (report_type.equals("D")) {
	    	reportType="Daily Compliance Detail";
		}
	    else if (report_type.equals("W")) {
	    	reportType="Weekely Compliance Detail";
		}
	    else if (report_type.equals("M")) {
	    	reportType="Monthly Compliance Detail";
		}
	    else if (report_type.equals("Q")) {
	    	reportType="Quarterly Compliance Detail";
		}
	    else if (report_type.equals("H")) {
	    	reportType="Half Yearly Compliance Detail";
		}
	    
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		Map<String, CellStyle> styles = createStyles(wb);
		
		Row headerRow = sheet.createRow(2);
		headerRow.setHeightInPoints(15);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
		
		// Title Row
		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(22);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(orgName);
		titleCell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,14));
		
		// Sub Title Row
		Row subTitleRow = sheet.createRow(1);
		subTitleRow.setHeightInPoints(18);
		Cell subTitleCell = subTitleRow.createCell(0);
		subTitleCell.setCellValue(reportType);
		subTitleCell.setCellStyle(styles.get("subTitle"));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,14));
		
		// Creating the folder for holding the Excel files with the defined name
		excelFileName = new CreateFolderOs().createUserDir("Compliance_Data")+ "/" + mergeDateTime+".xls";
		int header_first=3;
		int index=4;
 		int header_1=header_first+(currentDayResolvedData.size())+3;
 		int index1=header_1+1;
 		int header_2=header_1+(currentDayPendingData.size())+3;
 		int index2=header_2+1;
 		
 		
 		if (currentDayResolvedData.size()<=0) 
 		{
			 header_1=3;
	 		 index1=4;
		}
 		else 
 		{
 			 header_1=header_first+(currentDayResolvedData.size())+3;
	 		 index1=header_1+1;
		}
 		
		if (currentDayPendingData.size()<=0) 
		{
			 header_2=header_1;
	 		 index2=index1;
		}
		else 
		{
			 header_2=header_1+(currentDayPendingData.size())+3;
	 		 index2=header_2+1;
		}
		
		if(currentDayPendingData!=null && currentDayPendingData.size()>0)
		{
	 	    // Header Row
			Row headerRow10 = sheet.createRow(header_first-1);
			headerRow10.setHeightInPoints(18);
			Cell headerTitleCell10 = headerRow10.createCell(0);
			headerTitleCell10.setCellValue(" Compliances Task Due Today i.e. "+DateUtil.getCurrentDateIndianFormat());
			headerTitleCell10.setCellStyle(styles.get("subTitle_PN"));
			sheet.addMergedRegion(new CellRangeAddress(header_first-1, header_first-1, 0,14));
			
	 		// Code Start for showing the data for current day Resolved Tickets
			HSSFRow rowHead = sheet.createRow((int) header_first);
			rowHead.createCell((int) 0).setCellValue(" Department ");
			rowHead.createCell((int) 1).setCellValue(" Task Type ");
			rowHead.createCell((int) 2).setCellValue(" Task Name ");
			rowHead.createCell((int) 3).setCellValue(" Task Brief ");
			rowHead.createCell((int) 4).setCellValue(" Frequency ");
			rowHead.createCell((int) 5).setCellValue(" Due Date ");
			rowHead.createCell((int) 6).setCellValue(" Mapped Team ");
			rowHead.createCell((int) 7).setCellValue(" Status");
			rowHead.createCell((int) 8).setCellValue(" Last Action Date ");
			rowHead.createCell((int) 9).setCellValue(" Last Action By ");
			rowHead.createCell((int) 10).setCellValue(" Last Action Comment");
		
			for (ComplianceDashboardBean CDRD:currentDayPendingData)
 		   	{
 				HSSFRow rowData=sheet.createRow((int)index);
				//Data Insertion as Ticket Lodged By Department Name
 				rowData.createCell((int) 0).setCellValue(CDRD.getDepartName());
				rowData.createCell((int) 1).setCellValue(CDRD.getTaskType());
				rowData.createCell((int) 2).setCellValue(CDRD.getTaskName());
				rowData.createCell((int) 3).setCellValue(CDRD.getTaskBrief());
				rowData.createCell((int) 4).setCellValue(CDRD.getFrequency());
				rowData.createCell((int) 5).setCellValue(CDRD.getDueDate());
				rowData.createCell((int) 6).setCellValue(CDRD.getMappedTeam());
				rowData.createCell((int) 7).setCellValue(CDRD.getActionStatus());
				rowData.createCell((int) 8).setCellValue(CDRD.getActionTakenOn());
				rowData.createCell((int) 9).setCellValue(CDRD.getActionBy());
				rowData.createCell((int) 10).setCellValue(CDRD.getLastActionComment());
				
				index++;
 				sheet.autoSizeColumn(index);
			}
		}
		// Code End for showing the data for current day Resolved Tickets
		if(currentDayResolvedData!=null && currentDayResolvedData.size()>0)
		{
			Row headerRow14 = sheet.createRow(header_1-1);
			headerRow14.setHeightInPoints(18);
			Cell headerTitleCell14 = headerRow14.createCell(0);
			headerTitleCell14.setCellValue("Compliances Task Action Taken Today i.e. "+DateUtil.getCurrentDateIndianFormat());
			headerTitleCell14.setCellStyle(styles.get("subTitle_RS"));
			sheet.addMergedRegion(new CellRangeAddress(header_1-1, header_1-1, 0,14));
	 		  // Code Start for showing the data for current day Snooze Tickets
	 		HSSFRow rowHead4 = sheet.createRow(header_1);
	 		
	 		rowHead4.createCell((int) 0).setCellValue(" Department ");
			rowHead4.createCell((int) 1).setCellValue(" Task Type ");
			rowHead4.createCell((int) 2).setCellValue(" Task Name ");
			rowHead4.createCell((int) 3).setCellValue(" Task Brief ");
			rowHead4.createCell((int) 4).setCellValue(" Frequency ");
			rowHead4.createCell((int) 5).setCellValue(" Due Date ");
			rowHead4.createCell((int) 6).setCellValue(" Mapped Team ");
			rowHead4.createCell((int) 7).setCellValue(" Action By ");
			rowHead4.createCell((int) 8).setCellValue(" Status ");
			rowHead4.createCell((int) 9).setCellValue(" Comment ");
	
			for (ComplianceDashboardBean CDRD:currentDayResolvedData) 
			{
 				HSSFRow rowData=sheet.createRow((int)index1);
				//Data Insertion as Ticket Lodged By Department Name
				rowData.createCell((int) 0).setCellValue(CDRD.getDepartName());
				rowData.createCell((int) 1).setCellValue(CDRD.getTaskType());
				rowData.createCell((int) 2).setCellValue(CDRD.getTaskName());
				rowData.createCell((int) 3).setCellValue(CDRD.getTaskBrief());
				rowData.createCell((int) 4).setCellValue(CDRD.getFrequency());
				rowData.createCell((int) 5).setCellValue(CDRD.getDueDate());
				rowData.createCell((int) 6).setCellValue(CDRD.getMappedTeam());
				rowData.createCell((int) 7).setCellValue(CDRD.getEmpId());
				rowData.createCell((int) 8).setCellValue(CDRD.getActionStatus());
				rowData.createCell((int) 9).setCellValue(CDRD.getComment());
				
				index1++;
 				sheet.autoSizeColumn(index1);
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
}
