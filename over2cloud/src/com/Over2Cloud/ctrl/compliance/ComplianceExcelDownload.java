package com.Over2Cloud.ctrl.compliance;

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
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.createTable;


public class ComplianceExcelDownload 
{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	@SuppressWarnings({ "static-access", "rawtypes"})
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
		if(needToStore)
			excelFileName = new CreateFolderOs().createUserDir("Compliance_Data")+ "/" +"Operation Task"+ mergeDateTime+".xls";
		else
			excelFileName=excelName;
		
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
							if(keyList.get(cellIndex).toString().equalsIgnoreCase("comp.reminder_for") || keyList.get(cellIndex).toString().equalsIgnoreCase("comp.escalation_level_1") || keyList.get(cellIndex).toString().equalsIgnoreCase("comp.escalation_level_2") || keyList.get(cellIndex).toString().equalsIgnoreCase("comp.escalation_level_3") || keyList.get(cellIndex).toString().equalsIgnoreCase("comp.escalation_level_4"))
							{
								String empId=object[cellIndex].toString().replace("#", ",").substring(0,(object[cellIndex].toString().toString().length()-1));
								String query="SELECT emp.empName FROM employee_basic AS emp INNER JOIN compliance_contacts AS cc ON cc.emp_id=emp.id WHERE cc.id IN("+empId+")";
								List data=new createTable().executeAllSelectQuery(query,connection);
								if(data!=null && data.size()>0)
								{
									StringBuilder builder=new StringBuilder();
									for(int i=0;i<data.size();i++)
									{
										builder.append(data.get(i).toString()+", ");
									}
									rowData.createCell((int) cellIndex).setCellValue(builder.toString().substring(0, builder.toString().length()-2));
								}
							}
							else if(keyList.get(cellIndex).toString().equalsIgnoreCase("comp.frequency"))
							{
								if(object[cellIndex].toString().equalsIgnoreCase("W"))
									rowData.createCell((int) cellIndex).setCellValue("Weekly");
								else if(object[cellIndex].toString().equalsIgnoreCase("OT"))
									rowData.createCell((int) cellIndex).setCellValue("One Time");
								else if(object[cellIndex].toString().equalsIgnoreCase("D"))
									rowData.createCell((int) cellIndex).setCellValue("Daily");
								else if(object[cellIndex].toString().equalsIgnoreCase("M"))
									rowData.createCell((int) cellIndex).setCellValue("Monthly");
								else if(object[cellIndex].toString().equalsIgnoreCase("BM"))
									rowData.createCell((int) cellIndex).setCellValue("Bi-Monthly");
								else if(object[cellIndex].toString().equalsIgnoreCase("Q"))
									rowData.createCell((int) cellIndex).setCellValue("Quaterly");
								else if(object[cellIndex].toString().equalsIgnoreCase("HY"))
									rowData.createCell((int) cellIndex).setCellValue("Half Yearly");
								else if(object[cellIndex].toString().equalsIgnoreCase("Y"))
									rowData.createCell((int) cellIndex).setCellValue("Yearly");
								else if(object[cellIndex].toString().equalsIgnoreCase("O"))
									rowData.createCell((int) cellIndex).setCellValue("Other");
								
							}
							else if(keyList.get(cellIndex).toString().equalsIgnoreCase("compReport.document_takeaction") || keyList.get(cellIndex).toString().equalsIgnoreCase("compReport.document_config_1") || keyList.get(cellIndex).toString().equalsIgnoreCase("compReport.document_config_2"))
							{
								String str=object[cellIndex].toString().substring(object[cellIndex].toString().lastIndexOf("//")+2, object[cellIndex].toString().length());
								rowData.createCell((int) cellIndex).setCellValue(str);
							}
							else if(keyList.get(cellIndex).toString().equalsIgnoreCase("comp.compid_prefix"))
							{
								rowData.createCell((int) cellIndex).setCellValue(object[cellIndex].toString()+object[cellIndex+1].toString());
							}
							else if(keyList.get(cellIndex).toString().equalsIgnoreCase("comp.action_type"))
							{
								rowData.createCell((int) cellIndex).setCellValue(DateUtil.makeTitle(object[cellIndex].toString()));
							}
							else if(keyList.get(cellIndex).toString().equalsIgnoreCase("compReport.userName"))
							{
								try
								{
									rowData.createCell((int) cellIndex).setCellValue(DateUtil.makeTitle((Cryptography.decrypt(object[cellIndex].toString(),DES_ENCRYPTION_KEY))));
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
							}
							else
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
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
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
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
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

}
