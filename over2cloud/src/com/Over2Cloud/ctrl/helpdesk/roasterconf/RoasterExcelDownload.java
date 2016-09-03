package com.Over2Cloud.ctrl.helpdesk.roasterconf;
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
import com.Over2Cloud.helpdesk.BeanUtil.RoasterConfPojo;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;

public class RoasterExcelDownload{
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(RoasterExcelDownload.class);
	
	
	public String writeDataInExcel(List<RoasterConfPojo> roasterList)
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
		String mergeDateTime=DateUtil.mergeDateTime();
		String sheetname = "Roaster Download On "+DateUtil.getCurrentDateIndianFormat() ;
		
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
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,32));
		
		// Sub Title Row
		Row subTitleRow = sheet.createRow(1);
		subTitleRow.setHeightInPoints(18);
		Cell subTitleCell = subTitleRow.createCell(0);
		subTitleCell.setCellValue("Roaster Detail");
		subTitleCell.setCellStyle(styles.get("subTitle"));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,32));
		
		
		// Code Start for showing the data for current day Resolved Tickets
		HSSFRow rowHead = sheet.createRow((int) 3);
		rowHead.createCell((int) 0).setCellValue(" Emp Name ");
		rowHead.createCell((int) 1).setCellValue(" Emp ID");
		rowHead.createCell((int) 2).setCellValue(" 01"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 3).setCellValue(" 02"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 4).setCellValue(" 03"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 5).setCellValue(" 04"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 6).setCellValue(" 05"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 7).setCellValue(" 06"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 8).setCellValue(" 07"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 9).setCellValue(" 08"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 10).setCellValue(" 09"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 11).setCellValue(" 10"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 12).setCellValue(" 11"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 13).setCellValue(" 12"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 14).setCellValue(" 13"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 15).setCellValue(" 14"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 16).setCellValue(" 15"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 17).setCellValue(" 16"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 18).setCellValue(" 17"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 19).setCellValue(" 18"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 20).setCellValue(" 19"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 21).setCellValue(" 20"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 22).setCellValue(" 21"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 23).setCellValue(" 22"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 24).setCellValue(" 23"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 25).setCellValue(" 24"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 26).setCellValue(" 25"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 27).setCellValue(" 26"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 28).setCellValue(" 27"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 29).setCellValue(" 28"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 30).setCellValue(" 29"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 31).setCellValue(" 30"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		rowHead.createCell((int) 32).setCellValue(" 31"+DateUtil.getCurrentDateIndianFormat().substring(2, 10)+" ");
		
		// Creating the folder for holding the Excel files with the defined name
		excelFileName = new CreateFolderOs().createUserDir("Roaster_Report")+ "/" + mergeDateTime+".xls";
		int index=4;
		if(roasterList!=null && roasterList.size()>0)
		{
 			for (RoasterConfPojo CDRD:roasterList) {
 				HSSFRow rowData=sheet.createRow((int)index);
				//Data Insertion as Ticket Lodged By Department Name
 				if (CDRD.getEmpName()!=null && !CDRD.getEmpName().equals("")) {
 					rowData.createCell((int) 0).setCellValue(CDRD.getEmpName());
				}
 				else {
 					rowData.createCell((int) 0).setCellValue("NA");
				}
 				if (CDRD.getEmpId()!=null && !CDRD.getEmpId().equals("")) {
 					rowData.createCell((int) 1).setCellValue(CDRD.getEmpId());
				}
 				else {
 					rowData.createCell((int) 1).setCellValue("NA");
				}
				rowData.createCell((int) 2).setCellValue(CDRD.getFirst());
				rowData.createCell((int) 3).setCellValue(CDRD.getSecond());
				rowData.createCell((int) 4).setCellValue(CDRD.getThree());
				rowData.createCell((int) 5).setCellValue(CDRD.getFour());
				rowData.createCell((int) 6).setCellValue(CDRD.getFifth());
				rowData.createCell((int) 7).setCellValue(CDRD.getSix());
				rowData.createCell((int) 8).setCellValue(CDRD.getSeven());
				rowData.createCell((int) 9).setCellValue(CDRD.getEight());
				rowData.createCell((int) 10).setCellValue(CDRD.getNine());
				rowData.createCell((int) 11).setCellValue(CDRD.getTen());
				rowData.createCell((int) 12).setCellValue(CDRD.getEleven());
				rowData.createCell((int) 13).setCellValue(CDRD.getTwelve());
				rowData.createCell((int) 14).setCellValue(CDRD.getThirteen());
				rowData.createCell((int) 15).setCellValue(CDRD.getFourteen());
				rowData.createCell((int) 16).setCellValue(CDRD.getFifteen());
				rowData.createCell((int) 17).setCellValue(CDRD.getSixteen());
				rowData.createCell((int) 18).setCellValue(CDRD.getSeventeen());
				rowData.createCell((int) 19).setCellValue(CDRD.getEighteen());
				rowData.createCell((int) 20).setCellValue(CDRD.getNineteen());
				rowData.createCell((int) 21).setCellValue(CDRD.getTwenty());
				rowData.createCell((int) 22).setCellValue(CDRD.getTwenty_one());
				rowData.createCell((int) 23).setCellValue(CDRD.getTwenty_two());
				rowData.createCell((int) 24).setCellValue(CDRD.getTwenty_three());
				rowData.createCell((int) 25).setCellValue(CDRD.getTwenty_four());
				rowData.createCell((int) 26).setCellValue(CDRD.getTwenty_five());
				rowData.createCell((int) 27).setCellValue(CDRD.getTwenty_six());
				rowData.createCell((int) 28).setCellValue(CDRD.getTwenty_seven());
				rowData.createCell((int) 29).setCellValue(CDRD.getTwenty_eight());
				rowData.createCell((int) 30).setCellValue(CDRD.getTwenty_nine());
				rowData.createCell((int) 31).setCellValue(CDRD.getThirty());
				rowData.createCell((int) 32).setCellValue(CDRD.getThirty_one());
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
