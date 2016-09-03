package com.Over2Cloud.ctrl.helpdesk.feeddraft;

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
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackDraftPojo;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;

public class FeedbackExcelDownload{
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(FeedbackExcelDownload.class);
	
	
	@SuppressWarnings("static-access")
	public String writeDataInExcel(List<FeedbackDraftPojo> fbDraftList)
	{ 
		 List orgData= new LoginImp().getUserInfomation("1", "IN");
			String titleString="";
			
			if (orgData!=null && orgData.size()>0) {
				for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
					Object[] object = (Object[]) iterator.next();
					titleString=object[0].toString();
				}
			}
		String excelFileName =null;
		String mergeDateTime=new DateUtil().mergeDateTime();
		String sheetname = "Feedback Draft Detail" ;
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetname);
		Map<String, CellStyle> styles = createStyles(wb);
		
		Row headerRow = sheet.createRow(2);
		headerRow.setHeightInPoints(15);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
		
	
		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(20);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(titleString);
		titleCell.setCellStyle(styles.get("title"));
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,9));
		
		// Sub Title Row
		Row subTitleRow = sheet.createRow(1);
		subTitleRow.setHeightInPoints(18);
		Cell subTitleCell = subTitleRow.createCell(0);
		subTitleCell.setCellValue("Feedback Draft Detail");
		subTitleCell.setCellStyle(styles.get("subTitle"));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,9));
		
		
		// Code Start for showing the data for current day Resolved Tickets
		HSSFRow rowHead = sheet.createRow((int) 2);
		rowHead.createCell((int) 0).setCellValue(" Feedback Type ");
		rowHead.createCell((int) 1).setCellValue(" Category ");
		rowHead.createCell((int) 2).setCellValue(" Sub Category");
		rowHead.createCell((int) 3).setCellValue(" Feedback Brief ");
		rowHead.createCell((int) 4).setCellValue(" Addressing Time ");
		rowHead.createCell((int) 5).setCellValue(" Resolution Time ");
		rowHead.createCell((int) 6).setCellValue(" Escalation Duration ");
		rowHead.createCell((int) 7).setCellValue(" Escalation Level ");
		rowHead.createCell((int) 8).setCellValue(" Need Escalation ");
		rowHead.createCell((int) 9).setCellValue(" Severity ");
		
		// Creating the folder for holding the Excel files with the defined name
		excelFileName = new CreateFolderOs().createUserDir("Feedback Draft_Download")+ "/" + mergeDateTime+".xls";
		int index=3;
		if(fbDraftList!=null && fbDraftList.size()>0)
		{
 			for (FeedbackDraftPojo CDRD:fbDraftList) {
 				HSSFRow rowData=sheet.createRow((int)index);
				//Data Insertion as Ticket Lodged By Department Name
 				rowData.createCell((int) 0).setCellValue(CDRD.getFeedtype());
				rowData.createCell((int) 1).setCellValue(CDRD.getCategory());
				rowData.createCell((int) 2).setCellValue(CDRD.getSub_category());
				rowData.createCell((int) 3).setCellValue(CDRD.getFeed_msg());
				rowData.createCell((int) 4).setCellValue(CDRD.getAddressing_time());
				rowData.createCell((int) 5).setCellValue(CDRD.getResolution_time());
				rowData.createCell((int) 6).setCellValue(CDRD.getEscDuration());
				rowData.createCell((int) 7).setCellValue(CDRD.getEsclevel());
				rowData.createCell((int) 8).setCellValue(CDRD.getNeedesc());
				rowData.createCell((int) 9).setCellValue(CDRD.getSeverity());
				index++;
 				sheet.autoSizeColumn(index);
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
