package com.Over2Cloud.ctrl.feedback;


import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.opensymphony.xwork2.ActionContext;

public class DownldeExcelFeedback {
	/**
	 *  Define Constructure
	 */
	public DownldeExcelFeedback() {
	}
	
	@SuppressWarnings({ "static-access", "unchecked"})
	public String writeDataInExcel(List dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,SessionFactory connection)
	{ 
		String excelFileName =null;
		String mergeDateTime=new DateUtil().mergeDateTime();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Feedback");
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
		subTitleCell.setCellValue("Feedback Details");
		subTitleCell.setCellStyle(styles.get("subTitle"));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,titleList.size()-1));
		
		
		// Creating the folder for holding the Excel files with the defined name
		if(needToStore)
			excelFileName = new CreateFolderOs().createUserDir("Feedback_Data")+ "/" +"Feedback"+ mergeDateTime+".xls";
		else
			excelFileName=excelName;
		
		int header_first=2;
		int index=3;
		HSSFRow rowHead = sheet.createRow((int) header_first);
		for(int i=0;i<titleList.size();i++)
		{
			rowHead.createCell((int) i).setCellValue(titleList.get(i).toString());
			rowHead.setRowStyle((styles.get("header")));
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
	
	
	// Define A method for Creating Excel File
	public String createExcel(String titleString, String sheetString,String subTitleString, List data, String[] headerTitles,String titleKey, String filePath){
		String file = null;
		String excelFileName=null;
		Workbook wb;
		int col = 0;
		try{
			// check Header Title  
			if (headerTitles != null && headerTitles.length > 0)
				col = headerTitles.length;

			wb =  (Workbook) new HSSFWorkbook();
            // Hear we are getting whole property 
    		List<ConfigurationUtilBean> titleMap = new FeedbackCommonPropertyMap().getTitles(titleKey);
    	
    		Map session = ActionContext.getContext().getSession();
    		
    		if(session.containsKey("feedType"))
    		{
    			session.remove("feedType");
    		}
    		
			Map<String, CellStyle> styles = createStyles(wb);
			Sheet sheet = wb.createSheet(sheetString);
			PrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setLandscape(true);
			sheet.setFitToPage(true);
			sheet.setHorizontallyCenter(true);

			Header header = sheet.getHeader();
			header.setCenter("Center Header");
			header.setLeft("Left Header");
			header.setRight("Right Footer");
			Footer footer = sheet.getFooter();
			footer.setCenter("center footer");
			footer.setLeft("left footer");
			footer.setRight("right footer");

			// Title Row....
			Row titleRow = sheet.createRow(0);
			titleRow.setHeightInPoints(20);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue(titleString);
			titleCell.setCellStyle(styles.get("title"));
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, col - 1));

			// Sub Title Row.....
			Row subTitleRow = sheet.createRow(1);
			subTitleRow.setHeightInPoints(18);
			Cell subTitleCell = subTitleRow.createCell(0);
			subTitleCell.setCellValue(subTitleString);
			subTitleCell.setCellStyle(styles.get("subTitle"));
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, col - 1));
			// 
			Row headerRow = sheet.createRow(2);
			headerRow.setHeightInPoints(15);
			Cell headerCell = null;
			if (headerTitles != null ){
				for (ConfigurationUtilBean cell : titleMap) {
					int titleIndex=0;
					for (int i = 0; i < headerTitles.length; i++) {
						if(cell.getKey().equalsIgnoreCase(headerTitles[titleIndex].trim())){
							headerCell = headerRow.createCell(titleIndex);	 
							headerCell.setCellValue(cell.getValue());
							headerCell.setCellStyle(styles.get("header"));
								}
							titleIndex++;
					}
				}
			}
			Row dataRow = null;
			Cell dataCell = null;

			int rowIndex = 3;
			  /* List Iteration text */
			  try
			  {
				  if(data!=null && data.size()>0)
				  {

					  for(Iterator it=data.iterator(); it.hasNext();)
					  {
						  Object[] obdata=(Object[])it.next();
						  dataRow = sheet.createRow(rowIndex);
						  	for (int cellIndex = 0; cellIndex < headerTitles.length; cellIndex++)
	     					{
	     						dataCell = dataRow.createCell(cellIndex);
								if(obdata[cellIndex]!=null && !obdata[cellIndex].toString().equalsIgnoreCase(""))
								{
									dataCell.setCellValue(obdata[cellIndex].toString());
								}
								else
								{
									dataCell.setCellValue("NA");
								}
	     					}
	     					rowIndex++;
				   	}
				  }
			   }
			  catch (Exception e) {
				// TODO: handle exception
			}
			

			for (int titleIndex = 0; titleIndex < headerTitles.length; titleIndex++)
				sheet.autoSizeColumn(titleIndex); // adjust width of the column

			

			/*if (wb instanceof XSSFWorkbook)
				file += "x";
			FileOutputStream out = new FileOutputStream(file);
			wb.write(out);
			out.close();*/
			
			String mergeDateTime=new DateUtil().mergeDateTime();
			excelFileName = new CreateFolderOs().createUserDir("Feedback_Data")+ "/" + mergeDateTime+"Feedback.xls";
			
			FileOutputStream out = new FileOutputStream(new File(excelFileName));
	        wb.write(out);
	        out.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		
		}
		return excelFileName;

	}
	
	// Define A method for Creating Excel File
	public String createExcelformate(String titleString, String sheetString, String[] headerTitles,String titleKey, String filePath){

		String file = null;
		Workbook wb;
		int col = 0;
		try{
			// check Header Title  
			if (headerTitles != null && headerTitles.length > 0)
				col = headerTitles.length;

			wb =  (Workbook) new HSSFWorkbook();
            // Hear we are getting whole property 
    		List<ConfigurationUtilBean> titleMap = new FeedbackCommonPropertyMap().getTitles(titleKey);
			Map<String, CellStyle> styles = createStyles(wb);
			Sheet sheet = wb.createSheet(sheetString);
			PrintSetup printSetup = sheet.getPrintSetup();
			printSetup.setLandscape(true);
			sheet.setFitToPage(true);
			sheet.setHorizontallyCenter(true);

			Header header = sheet.getHeader();
			header.setCenter("Center Header");
			header.setLeft("Left Header");
			header.setRight("Right Footer");
			Footer footer = sheet.getFooter();
			footer.setCenter("center footer");
			footer.setLeft("left footer");
			footer.setRight("right footer");

			// Title Row....
			Row titleRow = sheet.createRow(0);
			titleRow.setHeightInPoints(20);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue(titleString);
			titleCell.setCellStyle(styles.get("title"));
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, col - 1));

		
			// 
			Row headerRow = sheet.createRow(1);
			headerRow.setHeightInPoints(15);
			Cell headerCell = null;
			if (headerTitles != null ){
				for (ConfigurationUtilBean cell : titleMap) {
					int titleIndex=0;
					for (int i = 0; i < headerTitles.length; i++) {
						if(cell.getKey().equalsIgnoreCase(headerTitles[titleIndex].trim())){
							headerCell = headerRow.createCell(titleIndex);	 
							headerCell.setCellValue(cell.getValue());
							headerCell.setCellStyle(styles.get("header"));
								}
							titleIndex++;
					}
				}
			}
			
			for (int titleIndex = 0; titleIndex < headerTitles.length; titleIndex++)
				sheet.autoSizeColumn(titleIndex); // adjust width of the column

			file = filePath + File.separator + "ContactReport"
					+ DateUtil.getCurrentDateIndianFormat()
					+ (DateUtil.getCurrentTime()).replaceAll(":", "-") + ".xls";

			if (wb instanceof XSSFWorkbook)
				file += "x";
			FileOutputStream out = new FileOutputStream(file);
			wb.write(out);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		
		}
		return file;

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
		style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
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
		style.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.index);
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
		style.setAlignment(CellStyle.ALIGN_CENTER);
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