package com.Over2Cloud.ctrl.leaveManagement;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.apache.struts2.ServletActionContext;

import com.Over2Cloud.CommonClasses.DateUtil;

public class GenericWriteExcel 
{
	static final Log log = LogFactory.getLog(GenericWriteExcel.class);
	HttpServletResponse response = ServletActionContext.getResponse();
	@SuppressWarnings("rawtypes")
	public String createExcel(String titleString,String subTitleString, List dataList, List headerTitle,String filePath,String fileName)
	{
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			String file = null;
			Workbook wb;
			try {
				wb = (Workbook) new HSSFWorkbook();
				Map<String, CellStyle> styles = createStyles(wb);
				Sheet sheet = wb.createSheet(subTitleString+" On "+DateUtil.getCurrentDateIndianFormat());
				PrintSetup printSetup = sheet.getPrintSetup();
				printSetup.setLandscape(true);
				sheet.setFitToPage(true);
				sheet.setHorizontallyCenter(true);

				Header header = sheet.getHeader();
				header.setLeft("Left Header");
				header.setRight("Right Footer");
				header.setCenter("Center Header");
				
				Footer footer = sheet.getFooter();
				footer.setLeft("left footer");
				footer.setRight("right footer");
				footer.setCenter("center footer");
				
				// Title Row
				Row titleRow = sheet.createRow(0);
				titleRow.setHeightInPoints(20);
				Cell titleCell = titleRow.createCell(0);
				titleCell.setCellValue(titleString);
				titleCell.setCellStyle(styles.get("title"));
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headerTitle.size() - 1));
				//sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10 - 1));

				// Sub Title Row
				Row subTitleRow = sheet.createRow(1);
				subTitleRow.setHeightInPoints(18);
				Cell subTitleCell = subTitleRow.createCell(0);
				subTitleCell.setCellValue(subTitleString);
				subTitleCell.setCellStyle(styles.get("subTitle"));
				sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, headerTitle.size() - 1));
				//sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10 - 1));

				// 
				Row headerRow = sheet.createRow(2);
				headerRow.setHeightInPoints(15);
				Cell headerCell = null;
				int row =0;
				if (headerTitle != null && headerTitle.size()>0)
				{
					for(int i=0 ; i<headerTitle.size() ; i++)
					{
						headerCell = headerRow.createCell(row);
						headerCell.setCellValue(headerTitle.get(i).toString());
						headerCell.setCellStyle(styles.get("header"));
						row++;
					}
				}
				Row dataRow = null;
				Cell dataCell = null;
				int rowIndex = 3;
				if(dataList!=null && dataList.size()>0)
				{
					if (subTitleString.equalsIgnoreCase("Summary Report") || subTitleString.equalsIgnoreCase("Total Report")) 
					{
					    int count=0;
					    for (int i = 0; i < dataList.size()/headerTitle.size(); i++) 
				        {
					    	dataRow = sheet.createRow(rowIndex);
					    	for (int cellIndex = 0; cellIndex < headerTitle.size(); cellIndex++)
							{
								dataCell = dataRow.createCell(cellIndex);
								if(dataList.get(count)!=null)
								{
									if(dataList.get(count).toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										dataCell.setCellValue(DateUtil.convertDateToIndianFormat(dataList.get(count).toString()));
									}
									else
									{
										dataCell.setCellValue(dataList.get(count).toString());
									}
								}
								else
								{
									dataCell.setCellValue("NA");
								}
								count++;
				           }
					    	rowIndex++;	
				        }
					}
					else 
					{
						Object[] object=null;
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
						{
							object = (Object[]) iterator.next();
							dataRow = sheet.createRow(rowIndex);
							for (int cellIndex = 0; cellIndex < headerTitle.size(); cellIndex++)
							{
								dataCell = dataRow.createCell(cellIndex);
								if(object[cellIndex]!=null)
								{
									if(object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
									{
										dataCell.setCellValue(DateUtil.convertDateToIndianFormat(object[cellIndex].toString()));
									}
									else
									{
										dataCell.setCellValue(object[cellIndex].toString());
									}
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
				for (int titleIndex = 0; titleIndex < headerTitle.size(); titleIndex++)
					sheet.autoSizeColumn(titleIndex); // adjust width of the column

				/*response.setHeader("Content-Disposition","attachment; filename="+subTitleString+".xls");
				ServletOutputStream out = response.getOutputStream();
				wb.write(out);
				out.close();*/
				file=subTitleString+".xls";
				FileOutputStream out = new FileOutputStream(new File(file));
		        wb.write(out);
		        out.close();
			} 
			catch (Exception e) 
			{
				log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
						"Problem in Over2Cloud  method createExcel of class "+getClass(), e);
				e.printStackTrace();
			}
			return file;
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
