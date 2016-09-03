package com.Over2Cloud.ctrl.helpdesk.common;

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
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class CommonExcelDownload extends ActionSupport{
	private static int  AN=0;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	
	
	public String writeDataInExcel(List dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,SessionFactory connection,List escdatalist,List resdatalist,List snoozedatalist,List cartAdatalist,List rescartdatalist,List informeddatalist)
	{ 
		String excelFileName =null;
		String mergeDateTime=new DateUtil().mergeDateTime();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(excelName);
		Map<String, CellStyle> styles = createStyles(wb);
		
		CellStyle cellStyle = wb.createCellStyle();
		  Calendar calendar = new GregorianCalendar();
	          calendar.set(1982,Calendar.NOVEMBER,25);
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
		excelFileName="Machine Order Feedback"+ mergeDateTime+".xls";
		
		int header_first=2;
		int index=3;
		HSSFRow rowHead = sheet.createRow((int) header_first);
		String time=null;
		for(int i=0;i<titleList.size();i++)
		{
		Cell subTitleCell22 = rowHead.createCell((i));
		/*if(i!=0)
		{*/
		subTitleCell22.setCellValue(titleList.get(i).toString());
		subTitleCell22.setCellStyle(styles.get("mytittle"));
		/*}*/
		}
		String actiondate=null,actiontime=null;
		if(dataList!=null && dataList.size()>0)
		{
		for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
		{
		Object[] object = (Object[]) iterator.next();
		HSSFRow rowData=sheet.createRow((int)index);
		
		for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
		{
		
		//System.out.println(object[cellIndex]);
		//System.out.println(keyList.get(cellIndex).toString());
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_date as actDate")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_time as actTime")	
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.escalate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.escalate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.escalate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.escalate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.escalate_to")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.resolution_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Snooze_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.snooze_date as Snooze_Date") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.snooze_time as Snooze_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_reason as Snooze_Reason")
		
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve_Date") 
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("od.finalActionBy AS Resolved_By") 
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by AS Resolved_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Snooze_By") 
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.snooze_date as Snooze_Date") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.snooze_time as Snooze_Time")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_reason as Snooze_Reason") 
		
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Cart_Allot_By")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.allocate_to as Cart_Allot_To")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Cart_Allot_Date")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Cart_Allot_Time")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Cart_Resolve_Date")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Cart_Resolve_Time")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("od.finalActionBy AS Cart_Resolved_By")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by AS Cart_Resolved_Action_By")
		)
		{
		
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_time as actTime")|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.escalate_to")
		||keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.escalate_to")
		||keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_time as actTime")|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.escalate_to")
		||keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.escalate_to")
		||keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.escalate_to")
		)
		{
		if(escdatalist!=null && escdatalist.size()>0)
		{
		int in=0;
		for (Iterator ite = escdatalist.iterator(); ite.hasNext();) 
		{
		Object[] obje = (Object[]) ite.next();
		if(obje[3].equals(object[0]))
		{
		if(in==0)
		{
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_date as actDate"))
		{
		if(obje[0]!=null)
		{
		calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),Integer.parseInt(obje[0].toString().split("-")[2]));
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(calendar);
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_time as actTime"))
		{
		if(obje[1]!=null)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obje[1].toString());
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.escalate_to"))
		{
		if(obje[2]!=null)
		{
		    Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obje[2].toString()+"(NA)");	
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		}
		else if(in==1)
		{
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_date as actDate"))
		{
		if(obje[0]!=null)
		{
		calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),Integer.parseInt(obje[0].toString().split("-")[2]));
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(calendar);
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_time as actTime"))
		{
		if(obje[1]!=null)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obje[1].toString());
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.escalate_to"))
		{
		if(obje[2]!=null)
		{
		    Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obje[2].toString()+"(NA)");	
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		  }
		else if(in==2)
		{
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_date as actDate"))
		{
		if(obje[0]!=null)
		{
		calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),Integer.parseInt(obje[0].toString().split("-")[2]));
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(calendar);
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_time as actTime"))
		{
		if(obje[1]!=null)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obje[1].toString());
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.escalate_to"))
		{
		if(obje[2]!=null)
		{
		    Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obje[2].toString()+"(NA)");	
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		  
		}
		else if(in==3)
		{
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_date as actDate"))
		{
		if(obje[0]!=null)
		{
		calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),Integer.parseInt(obje[0].toString().split("-")[2]));
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(calendar);
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_time as actTime"))
		{
		if(obje[1]!=null)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obje[1].toString());
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.escalate_to"))
		{
		if(obje[2]!=null)
		{
		    Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obje[2].toString()+"(NA)");	
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		}
		else if(in==4)
		{
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_date as actDate"))
		{
		if(obje[0]!=null)
		{
		calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),Integer.parseInt(obje[0].toString().split("-")[2]));
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(calendar);
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_time as actTime"))
		{
		if(obje[1]!=null)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obje[1].toString());
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.escalate_to"))
		{
		if(obje[2]!=null)
		{
		    Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obje[2].toString()+"(NA)");	
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		}
		
		in++;
		}
		/*else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}*/
		  }
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.resolution_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve_Date") 
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("od.finalActionBy AS Resolved_By") 
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by AS Resolved_Action_By") 
		)
		{
		if(object[21]!=null && object[21].toString().equalsIgnoreCase("Resolved"))
		{
		if(resdatalist!=null && resdatalist.size()>0)
		{
		for (Iterator ite = resdatalist.iterator(); ite.hasNext();) 
		{
		Object[] obje = (Object[]) ite.next();
		if(obje[4].equals(object[0]))
		{
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve_Date"))
		{
		if(obje[0]!=null && obje[0].toString().trim().length()>0)
		{
		calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),Integer.parseInt(obje[0].toString().split("-")[2]));
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(calendar);
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve_Time"))
		{
		if(obje[1]!=null)
		{
		 Cell cell=rowData.createCell((int) cellIndex);
		 cell.setCellValue(obje[1].toString());
		 cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("od.finalActionBy AS Resolved_By"))
		{
		if(obje[2]!=null)
		{
		 Cell cell=rowData.createCell((int) cellIndex);
		 cell.setCellValue(obje[2].toString());
		 cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by AS Resolved_Action_By"))
		{
		if(obje[3]!=null)
		{
		 Cell cell=rowData.createCell((int) cellIndex);
		 cell.setCellValue(obje[3].toString());
		 cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.resolution_time"))
		{
		if(informeddatalist!=null && informeddatalist.size()>0)
		{
		//	System.out.println("Test1111");
		for(Iterator itr1 = informeddatalist.iterator(); itr1.hasNext();) 
		  {
		Object[] obj1 = (Object[]) itr1.next();
		if(obj1[4].equals(object[0]))
		{
		//	System.out.println("Test22222222222");
		if(obj1[0]!=null && obj1[1]!=null)
		{
		//	System.out.println("Test33333333333");
		Cell cell=rowData.createCell((int) cellIndex);
		//System.out.println(DateUtil.time_difference(obj1[0].toString(),obj1[1].toString(),obje[0].toString(),obje[1].toString()));
		cell.setCellValue(DateUtil.time_difference(obj1[0].toString(),obj1[1].toString(),obje[0].toString(),obje[1].toString()));
		cell.setCellStyle(cellStyle);
		//	actiontime=obje[5].toString();
		}
		  }
		  }
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		  }
		
		  }
		/*else{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}*/
		}
		}
		else{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Snooze_By") 
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.snooze_date as Snooze_Date") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.snooze_time as Snooze_Time")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_reason as Snooze_Reason")
		)
		{
		if(object[21]!=null)
		{
		/*if(object[21].toString().equalsIgnoreCase("Snooze"))
		{*/
		if(snoozedatalist!=null && snoozedatalist.size()>0)
		{
		for(Iterator itr = snoozedatalist.iterator(); itr.hasNext();) 
		{
		Object[] obj = (Object[]) itr.next();
		if(obj[4].equals(object[0]))
		{
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Snooze_By"))
		{
		if(obj[0]!=null)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obj[0].toString());
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.snooze_date as Snooze_Date"))
		{
		if(obj[1]!=null)
		{
		calendar.set(Integer.parseInt(obj[1].toString().split("-")[0]),DateUtil.getMonthFromDay(obj[1].toString().split("-")[1]),Integer.parseInt(obj[1].toString().split("-")[2]));
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(calendar);
		 cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.snooze_time as Snooze_Time"))
		{
		if(obj[2]!=null)
		{
		 Cell cell=rowData.createCell((int) cellIndex);
		 cell.setCellValue(obj[2].toString());
		 cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_reason as Snooze_Reason"))
		{
		if(obj[3]!=null)
		{
		 Cell cell=rowData.createCell((int) cellIndex);
		 cell.setCellValue(obj[3].toString());
		 cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		  }
		/*else{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}*/
		}
		}
		
		else{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		/*else{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}*/
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		   }
		else if( keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Cart_Allot_By")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.allocate_to as Cart_Allot_To")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Cart_Allot_Date")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Cart_Allot_Time")
		)
		{
		if(object[21]!=null)
		{
		//	System.out.println(object[21].toString());
		/*if(object[21].toString().equalsIgnoreCase("Cart Assigned"))
		{*/
		if(cartAdatalist!=null && cartAdatalist.size()>0)
		{
		for(Iterator itr = cartAdatalist.iterator(); itr.hasNext();) 
		  {
		Object[] obj = (Object[]) itr.next();
		if(obj[4].equals(object[0]))
		{
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Cart_Allot_By"))
		{
		if(obj[0]!=null)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obj[0].toString());
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.allocate_to as Cart_Allot_To"))
		{
		if(obj[1]!=null)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		 cell.setCellValue(obj[1].toString());
		 cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Cart_Allot_Date"))
		{
		if(obj[2]!=null)
		{
		calendar.set(Integer.parseInt(obj[2].toString().split("-")[0]),DateUtil.getMonthFromDay(obj[2].toString().split("-")[1]),Integer.parseInt(obj[2].toString().split("-")[2]));
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(calendar);
		 cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Cart_Allot_Time"))
		{
		if(obj[3]!=null)
		{
		 Cell cell=rowData.createCell((int) cellIndex);
		 cell.setCellValue(obj[3].toString());
		 cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		}
		/*else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}*/
		  }
		}
		else{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		/*}
		else{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}*/
		}
		/*else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}*/
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Cart_Resolve_Date")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Cart_Resolve_Time")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("od.finalActionBy AS Cart_Resolved_By")
		|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by AS Cart_Resolved_Action_By")
		)
		{
		if(object[21]!=null)
		{
		/*if(object[21].toString().equalsIgnoreCase("Resolved by cart"))
		{*/
		if(rescartdatalist!=null && rescartdatalist.size()>0)
		{
		for(Iterator itr = rescartdatalist.iterator(); itr.hasNext();) 
		  {
		Object[] obj = (Object[]) itr.next();
		if(obj[4].equals(object[0]))
		{
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Cart_Resolve_Date"))
		{
		if(obj[0]!=null)
		{
		calendar.set(Integer.parseInt(obj[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obj[0].toString().split("-")[1]),Integer.parseInt(obj[0].toString().split("-")[2]));
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(calendar);
		cell.setCellStyle(cellStyle);
		    }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Cart_Resolve_Time"))
		{
		if(obj[1]!=null)
		{
		 Cell cell=rowData.createCell((int) cellIndex);
		 cell.setCellValue(obj[1].toString());
		 cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("od.finalActionBy AS Cart_Resolved_By"))
		{
		if(obj[2]!=null)
		{
		 Cell cell=rowData.createCell((int) cellIndex);
		 cell.setCellValue(obj[2].toString());
		 cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by AS Cart_Resolved_Action_By"))
		{
		if(obj[3]!=null)
		{
		 Cell cell=rowData.createCell((int) cellIndex);
		 cell.setCellValue(obj[3].toString());
		 cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		
	     	      }
		/*else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}*/
		    }
		  }
		/*else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}*/
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		  }
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		}
		else{
		if( !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_date as actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_date as actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_date as actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_date as actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_date as actDate")
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_time as actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_time as actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_time as actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_time as actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_time as actTime")	
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.escalate_to") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.escalate_to") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.escalate_to") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.escalate_to") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.escalate_to")
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.resolution_time")  
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve_Date") 
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve_Time") && !keyList.get(cellIndex).toString().equalsIgnoreCase("od.finalActionBy AS Resolved_By") 
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by AS Resolved_Action_By") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Snooze_By") 
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.snooze_date as Snooze_Date") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.snooze_time as Snooze_Time")
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_reason as Snooze_Reason") 
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Cart_Allot_By")
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.allocate_to as Cart_Allot_To")
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Cart_Allot_Date")
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Cart_Allot_Time")
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Cart_Resolve_Date")
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Cart_Resolve_Time")
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("od.finalActionBy AS Cart_Resolved_By")
		&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by AS Cart_Resolved_Action_By") && object[cellIndex]!=null
		)
		{
		/*if(cellIndex<28)
		{*/
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));
		if(object[cellIndex]!=null && object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Allot_Date") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Allot_Time") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.allocate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by"))
		{
		//	System.out.println(object[cellIndex].toString());
		//	System.out.println(object[cellIndex].toString().split("-")[1]);
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Allot_Date") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Allot_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.allocate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by"))
		{
		if(object[21]!=null)
		{	
		if(informeddatalist!=null && informeddatalist.size()>0)
		{
		for(Iterator itr = informeddatalist.iterator(); itr.hasNext();) 
		  {
		Object[] obj = (Object[]) itr.next();
		if(obj[4].equals(object[0]))
		{
		if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Allot_Date"))
		{
		if(obj[0]!=null && obj[0].toString().trim().length()>0)
		{
		  calendar.set(Integer.parseInt(obj[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obj[0].toString().split("-")[1]),Integer.parseInt(obj[0].toString().split("-")[2]));
		  Cell cell=rowData.createCell((int) cellIndex);
		  cell.setCellValue(calendar);
		  actiondate=obj[0].toString();
		  cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Allot_Time"))
		{
		if(obj[1]!=null && obj[1].toString().trim().length()>0)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obj[1].toString());
		cell.setCellStyle(cellStyle);
		actiontime=obj[1].toString();
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.allocate_to"))
		{
		if(obj[2]!=null && obj[2].toString().trim().length()>0)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obj[2].toString());
		cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by"))
		{
		if(obj[3]!=null && obj[3].toString().trim().length()>0)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(obj[3].toString());
		cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		  }
		}
		   }
		else{
		if(object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
		{
		if(object[cellIndex]!=null && object[cellIndex].toString().trim().length()>0)
		{
		calendar.set(Integer.parseInt(object[cellIndex].toString().split("-")[0]),DateUtil.getMonthFromDay(object[cellIndex].toString().split("-")[1]),Integer.parseInt(object[cellIndex].toString().split("-")[2]));
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue( calendar);
		cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		else{
		if(object[cellIndex]!=null && object[cellIndex].toString().trim().length()>0)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(object[cellIndex].toString());	
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		  }
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}	
		else{
		if(object[cellIndex]!=null && object[cellIndex].toString().trim().length()>0)
		{
		calendar.set(Integer.parseInt(object[cellIndex].toString().split("-")[0]),DateUtil.getMonthFromDay(object[cellIndex].toString().split("-")[1]),Integer.parseInt(object[cellIndex].toString().split("-")[2]));
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue( calendar);
		cell.setCellStyle(cellStyle);
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		}
		}
		else
		{
		/*if(keyList.get(cellIndex).toString().equalsIgnoreCase("od.finalActionBy AS Resolved_By"))
		{
		if(object[26]!=null && object[26].toString().equalsIgnoreCase("Resolved") || object[26].toString().equalsIgnoreCase("Un-assigned"))
		{
		//System.out.println(cellIndex+"    "+object[cellIndex].toString());
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(object[cellIndex].toString());	
		}
		else
		{
		List resolve=getResolveData(connection,object[0].toString());
		if(resolve!=null && resolve.size()>0)
		{
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(resolve.get(0).toString());
		}
		else{
		rowData.createCell((int) cellIndex).setCellValue("NA");	
		}
		}
		}*/
		/*else{*/
		if(object[cellIndex]!=null && object[cellIndex].toString().trim().length()>0)
		{
		//System.out.println(cellIndex+"    "+object[cellIndex].toString());
		Cell cell=rowData.createCell((int) cellIndex);
		cell.setCellValue(object[cellIndex].toString());	
		}
		else
		{
		rowData.createCell((int) cellIndex).setCellValue("NA");
		}
		/*}*/
		
		}
		 }
		/*  }*/
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