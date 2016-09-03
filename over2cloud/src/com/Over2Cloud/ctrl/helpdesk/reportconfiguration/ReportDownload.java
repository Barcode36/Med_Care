package com.Over2Cloud.ctrl.helpdesk.reportconfiguration;

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
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;

@SuppressWarnings("serial")
public class ReportDownload extends GridPropertyBean{
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(ReportDownload.class);
	
	@SuppressWarnings({ "static-access", "unchecked" })
	public String writeDataInExcel(List<FeedbackPojo> reportList, List statuslist,String status)
	{ 
	    String excelFileName =null;
	    String mergeDateTime=new DateUtil().mergeDateTime();
	    //String totalFeedback = "Feedback Details On "+DateUtil.getCurrentDateIndianFormat() ;
	    String  sheetName = ""+DateUtil.makeTitle(status)+" Ticket Details";
	   // String sheetHeader = ""+DateUtil.makeTitle(status)+" Ticket Details On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTime();
	    
	    List orgData= new LoginImp().getUserInfomation("1", "IN");
		String orgName="";
		if (orgData!=null && orgData.size()>0) {
			for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				orgName=object[0].toString();
			}
		}
		
	    int feed_pending=0;
	    int feed_sn=0;
	    int feed_hp=0;
	    int feed_rs=0;
	    int feed_ig=0;
	    if (statuslist!=null && statuslist.size()>0) {
           for (Iterator iterator = statuslist.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				if (object[0].toString().equals("Resolved")) {
					feed_rs=Integer.parseInt((object[1].toString()));
				}
				else if (object[0].toString().equals("Snooze")) {
					feed_sn=Integer.parseInt((object[1].toString()));
				}
				else if (object[0].toString().equals("High Priority")) {
					feed_hp=Integer.parseInt((object[1].toString()));
				}
				else if (object[0].toString().equals("Ignore")) {
					feed_ig=Integer.parseInt((object[1].toString()));
				}
				else if (object[0].toString().equals("Pending")) {
					feed_pending=Integer.parseInt((object[1].toString()));
				}
			 }
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
		if (status!=null && !status.equals("") && (status.equals("All") || status.equals("Resolved"))) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,32));
		}
		else if (status!=null && !status.equals("") && (status.equals("Pending"))) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,21));
		}
		else if (status!=null && !status.equals("") && (status.equalsIgnoreCase("Snooze"))) {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,19));
		}
		else {
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,14));
		}
		
		
		// Creating the folder for holding the Excel files with the defined name
		excelFileName = new CreateFolderOs().createUserDir("normal_report")+ "/" + mergeDateTime+".xls";
		int header_first=2;
		int index=3;
 		int header_1=header_first+feed_rs+2;
 		int index1=header_1+1;
 		int header_2=header_1+feed_pending+2;
 		int index2=header_2+1;
 		int header_3=header_2+feed_sn+2;
 		int index3=header_3+1;
 		int header_4=header_3+feed_hp+2;
 		int index4=header_4+1;
 		int header_5=header_4+feed_ig+2;
 		int index5=header_5+1;
 		
 		if (feed_rs<=0) {
			 header_1=3;
	 		 index1=4;
		}
 		else {
 			 header_1=header_first+feed_rs+3;
	 		 index1=header_1+1;
		}
 		
		if (feed_pending<=0) {
			 header_2=header_1;
	 		 index2=index1;
		}
		else {
			 header_2=header_1+feed_pending+3;
	 		 index2=header_2+1;
		}
		
		if (feed_sn<=0) {
			 header_3=header_2;
	 		 index3=index2;
		}
		else {
			 header_3=header_2+feed_sn+3;
	 		 index3=header_3+1;
		}
		
		if (feed_hp<=0) {
			 header_4=header_3;
	 		 index4=index3;
		}
		else {
			 header_4=header_3+feed_hp+3;
	 		 index4=header_4+1;
		}
		
		if (feed_ig<=0) {
			 header_5=header_4;
	 		 index5=index4;
		}
		else {
			 header_5=header_4+feed_ig+3;
	 		 index5=header_5+1;
		}
		
 		if (status!=null && !status.equals("") && (status.equals("All") || status.equals("Resolved")) && feed_rs>0) {
		Row headerRow10 = sheet.createRow(header_first-1);
		headerRow10.setHeightInPoints(27);
		Cell headerTitleCell10 = headerRow10.createCell(0);
		headerTitleCell10.setCellValue("Resolved Ticket Details On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTime());
		headerTitleCell10.setCellStyle(styles.get("subTitle_RS"));
		sheet.addMergedRegion(new CellRangeAddress(header_first-1, header_first-1, 0,32));
		
 		// Code Start for showing the data for current day Resolved Tickets
		HSSFRow rowHead = sheet.createRow((int) header_first);
		rowHead.createCell((int) 0).setCellValue(" From Department ");
		rowHead.createCell((int) 1).setCellValue(" Ticket No ");
		rowHead.createCell((int) 2).setCellValue(" Call Lodged By ");
		rowHead.createCell((int) 3).setCellValue(" Mobile No ");
		rowHead.createCell((int) 4).setCellValue(" Email Id ");
		rowHead.createCell((int) 5).setCellValue(" To Sub Department ");
		rowHead.createCell((int) 6).setCellValue(" Complaint Category ");
		rowHead.createCell((int) 7).setCellValue(" Complaint Sub Category ");
		rowHead.createCell((int) 8).setCellValue(" Feedback Brief ");
		rowHead.createCell((int) 9).setCellValue(" Open On ");
		rowHead.createCell((int) 10).setCellValue(" Open At ");
		rowHead.createCell((int) 11).setCellValue(" Addressing ");
		rowHead.createCell((int) 12).setCellValue(" Escalation On ");
		rowHead.createCell((int) 13).setCellValue(" Escalation At ");
		rowHead.createCell((int) 14).setCellValue(" Level ");
		rowHead.createCell((int) 15).setCellValue(" Status ");
		rowHead.createCell((int) 16).setCellValue(" Close Date ");
		rowHead.createCell((int) 17).setCellValue(" Close Time ");
		rowHead.createCell((int) 18).setCellValue("    TAT    ");
		rowHead.createCell((int) 19).setCellValue(" Action Taken ");
		rowHead.createCell((int) 20).setCellValue(" Engineer Name ");
		rowHead.createCell((int) 21).setCellValue(" Snooze On ");
		rowHead.createCell((int) 22).setCellValue(" Snooze At ");
		rowHead.createCell((int) 23).setCellValue(" Snooze Upto Date ");
		rowHead.createCell((int) 24).setCellValue(" Snooze Upto Time ");
		rowHead.createCell((int) 25).setCellValue(" Snooze Duration ");
		rowHead.createCell((int) 26).setCellValue(" Snooze Reason ");
		rowHead.createCell((int) 27).setCellValue(" High Priority On ");
		rowHead.createCell((int) 28).setCellValue(" High Priority At ");
		rowHead.createCell((int) 29).setCellValue(" High Priority Comment");
		rowHead.createCell((int) 30).setCellValue(" Action By ");
		rowHead.createCell((int) 31).setCellValue(" Location ");
		rowHead.createCell((int) 32).setCellValue(" Complaint Mode ");
		
 		if(reportList!=null && reportList.size()>0)
		{
 			String duration=null;
 			for (FeedbackPojo CDRD:reportList) {
 				if (CDRD.getStatus().equals("Resolved")) {
 				HSSFRow rowData=sheet.createRow((int)index);
				//Data Insertion as Ticket Lodged By Department Name
				rowData.createCell((int) 0).setCellValue(CDRD.getFeedback_by_dept());
				rowData.createCell((int) 1).setCellValue(CDRD.getTicket_no());
				rowData.createCell((int) 2).setCellValue(CDRD.getFeed_by());
				rowData.createCell((int) 3).setCellValue(CDRD.getFeedback_by_mobno());
				rowData.createCell((int) 4).setCellValue(CDRD.getFeedback_by_emailid());
				rowData.createCell((int) 5).setCellValue(CDRD.getFeedback_to_subdept());
				rowData.createCell((int) 6).setCellValue(CDRD.getFeedback_catg());
				rowData.createCell((int) 7).setCellValue(CDRD.getFeedback_subcatg());
				rowData.createCell((int) 8).setCellValue(CDRD.getFeed_brief());
				rowData.createCell((int) 9).setCellValue(CDRD.getOpen_date());
				rowData.createCell((int) 10).setCellValue(CDRD.getOpen_time());
				rowData.createCell((int) 11).setCellValue(CDRD.getAddressingTime());
				rowData.createCell((int) 12).setCellValue(CDRD.getEscalation_date());
				rowData.createCell((int) 13).setCellValue(CDRD.getEscalation_time());
				rowData.createCell((int) 14).setCellValue(CDRD.getLevel());
				rowData.createCell((int) 15).setCellValue(CDRD.getStatus());
				rowData.createCell((int) 16).setCellValue(CDRD.getResolve_date());
				rowData.createCell((int) 17).setCellValue(CDRD.getResolve_time());
				if (CDRD.getOpen_date()!=null && !CDRD.getOpen_date().equals("") && !CDRD.getOpen_date().equals("NA") && CDRD.getOpen_time()!=null && !CDRD.getOpen_time().equals("") && !CDRD.getOpen_time().equals("NA") && CDRD.getResolve_date()!=null && !CDRD.getResolve_date().equals("") && !CDRD.getResolve_date().equals("NA") && CDRD.getResolve_time()!=null && !CDRD.getResolve_time().equals("") && !CDRD.getResolve_time().equals("NA") &&(CDRD.getResolve_duration()==null || CDRD.getResolve_duration().equals("") || CDRD.getResolve_duration().equals("NA"))) {
					duration= DateUtil.time_difference(DateUtil.convertDateToUSFormat(CDRD.getOpen_date()), CDRD.getOpen_time(), DateUtil.convertDateToUSFormat(CDRD.getResolve_date()), CDRD.getResolve_time());
				}
				else {
					duration=CDRD.getResolve_duration();
				}
				
				if (duration!=null && !duration.equals("")) {
					rowData.createCell((int) 18).setCellValue(duration);
				}
				else {
					rowData.createCell((int) 18).setCellValue("NA");
				}
				rowData.createCell((int) 19).setCellValue(CDRD.getResolve_remark());
				rowData.createCell((int) 20).setCellValue(CDRD.getResolve_by());
				rowData.createCell((int) 21).setCellValue(CDRD.getSn_on_date());
				rowData.createCell((int) 22).setCellValue(CDRD.getSn_on_time());
				rowData.createCell((int) 23).setCellValue(CDRD.getSn_date());
				rowData.createCell((int) 24).setCellValue(CDRD.getSn_time());
				rowData.createCell((int) 25).setCellValue(CDRD.getSn_duration());
				rowData.createCell((int) 26).setCellValue(CDRD.getSn_reason());
				rowData.createCell((int) 27).setCellValue(CDRD.getHp_date());
				rowData.createCell((int) 28).setCellValue(CDRD.getHp_time());
				rowData.createCell((int) 29).setCellValue(CDRD.getHp_reason());
				rowData.createCell((int) 30).setCellValue(CDRD.getAction_by());
				rowData.createCell((int) 31).setCellValue(CDRD.getLocation());
				rowData.createCell((int) 32).setCellValue(CDRD.getVia_from());
				index++;
 				sheet.autoSizeColumn(index);
			  }
 			}		
		  }
		}
 		
 		if (status!=null && !status.equals("") && (status.equals("All") || status.equals("Pending")) && feed_pending>0) {
 		// Header Row
			Row headerRow14 = sheet.createRow(header_1-1);
			headerRow14.setHeightInPoints(18);
			Cell headerTitleCell14 = headerRow14.createCell(0);
			headerTitleCell14.setCellValue("Pending Ticket Details On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTime());
			headerTitleCell14.setCellStyle(styles.get("subTitle_PN"));
			sheet.addMergedRegion(new CellRangeAddress(header_1-1, header_1-1, 0,21));
 		  // Code Start for showing the data for current day Snooze Tickets
 		HSSFRow rowHead4 = sheet.createRow(header_1);
		rowHead4.createCell((int) 0).setCellValue(" From Department ");
		rowHead4.createCell((int) 1).setCellValue(" Ticket No ");
		rowHead4.createCell((int) 2).setCellValue(" Open On ");
		rowHead4.createCell((int) 3).setCellValue(" Open At ");
		rowHead4.createCell((int) 4).setCellValue(" Addressing Time ");
		rowHead4.createCell((int) 5).setCellValue(" Escalation Date ");
		rowHead4.createCell((int) 6).setCellValue(" Escalation Time ");
		rowHead4.createCell((int) 7).setCellValue(" Level ");
		rowHead4.createCell((int) 8).setCellValue(" Call Lodged By ");
		rowHead4.createCell((int) 9).setCellValue(" To Sub Department ");
		rowHead4.createCell((int) 10).setCellValue(" Complaint Category ");
		rowHead4.createCell((int) 11).setCellValue(" Complaint Sub Category ");
		rowHead4.createCell((int) 12).setCellValue(" Complaint Description ");
		rowHead4.createCell((int) 13).setCellValue(" Alloted To ");
		rowHead4.createCell((int) 14).setCellValue(" Location ");
		rowHead4.createCell((int) 15).setCellValue(" Complaint Mode ");
		rowHead4.createCell((int) 16).setCellValue(" Snooze On ");
		rowHead4.createCell((int) 17).setCellValue(" Snooze At ");
		rowHead4.createCell((int) 18).setCellValue(" Snooze Upto Date ");
		rowHead4.createCell((int) 19).setCellValue(" Snooze Upto Time ");
		rowHead4.createCell((int) 20).setCellValue(" Snooze Duration ");
		rowHead4.createCell((int) 21).setCellValue(" Snooze Reason ");
		
 		
 		if(reportList!=null)
		{
 			for (FeedbackPojo CDRD:reportList) {
 				if (CDRD.getStatus().equals("Pending")) {
 				HSSFRow rowData=sheet.createRow((int)index1);
				//Data Insertion as Ticket Lodged By Department Name
				rowData.createCell((int) 0).setCellValue(CDRD.getFeedback_by_dept());
				rowData.createCell((int) 1).setCellValue(CDRD.getTicket_no());
				rowData.createCell((int) 2).setCellValue(CDRD.getOpen_date());
				rowData.createCell((int) 3).setCellValue(CDRD.getOpen_time());
				rowData.createCell((int) 4).setCellValue(CDRD.getAddressingTime());
				rowData.createCell((int) 5).setCellValue(CDRD.getEscalation_date());
				rowData.createCell((int) 6).setCellValue(CDRD.getEscalation_time());
				rowData.createCell((int) 7).setCellValue(CDRD.getLevel());
				rowData.createCell((int) 8).setCellValue(CDRD.getFeed_by());
				rowData.createCell((int) 9).setCellValue(CDRD.getFeedback_to_subdept());
				rowData.createCell((int) 10).setCellValue(CDRD.getFeedback_catg());
				rowData.createCell((int) 11).setCellValue(CDRD.getFeedback_subcatg());
				rowData.createCell((int) 12).setCellValue(CDRD.getFeed_brief());
				rowData.createCell((int) 13).setCellValue(CDRD.getFeedback_allot_to());
				rowData.createCell((int) 14).setCellValue(CDRD.getLocation());
				rowData.createCell((int) 15).setCellValue(CDRD.getVia_from());
				rowData.createCell((int) 16).setCellValue(CDRD.getSn_on_date());
				rowData.createCell((int) 17).setCellValue(CDRD.getSn_on_time());
				rowData.createCell((int) 18).setCellValue(CDRD.getSn_date());
				rowData.createCell((int) 19).setCellValue(CDRD.getSn_time());
				rowData.createCell((int) 20).setCellValue(CDRD.getSn_duration());
				rowData.createCell((int) 21).setCellValue(CDRD.getSn_reason());
				
				index1++;
 				sheet.autoSizeColumn(index1);
			}
		   }
 		  }
		}
 		
 			
 			if (status!=null && !status.equals("") && (status.equals("All") || status.equals("Snooze")) && feed_sn>0) {
			Row headerRow11 = sheet.createRow(header_2-1);
			headerRow11.setHeightInPoints(18);
			Cell headerTitleCell11 = headerRow11.createCell(0);
			headerTitleCell11.setCellValue("Snooze Ticket Details On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTime());
			headerTitleCell11.setCellStyle(styles.get("subTitle_SN"));
			sheet.addMergedRegion(new CellRangeAddress(header_2-1, header_2-1, 0,19));
	 	    // Code Start for showing the data for current day Snooze Tickets
	 		HSSFRow rowHead1 = sheet.createRow(header_2);
			rowHead1.createCell((int) 0).setCellValue(" From Department ");
			rowHead1.createCell((int) 1).setCellValue(" Ticket No ");
			rowHead1.createCell((int) 2).setCellValue(" Open On ");
			rowHead1.createCell((int) 3).setCellValue(" Open At ");
			rowHead1.createCell((int) 4).setCellValue(" Addressing Time ");
			rowHead1.createCell((int) 5).setCellValue(" Call Lodged By ");
			rowHead1.createCell((int) 6).setCellValue(" To Sub Department ");
			rowHead1.createCell((int) 7).setCellValue(" Complaint Category ");
			rowHead1.createCell((int) 8).setCellValue(" Complaint Sub Category ");
			rowHead1.createCell((int) 9).setCellValue(" Complaint Description ");
			rowHead1.createCell((int) 10).setCellValue(" Snooze On Date ");
			rowHead1.createCell((int) 11).setCellValue(" Snooze On Time ");
			rowHead1.createCell((int) 12).setCellValue(" Snooze Upto Date ");
			rowHead1.createCell((int) 13).setCellValue(" Snooze Upto Time ");
			rowHead1.createCell((int) 14).setCellValue(" Snooze Duration ");
			rowHead1.createCell((int) 15).setCellValue(" Snooze Reason ");
			rowHead1.createCell((int) 16).setCellValue(" Snooze By ");
			rowHead1.createCell((int) 17).setCellValue(" Level ");
			rowHead1.createCell((int) 18).setCellValue(" Complaint Mode ");
			rowHead1.createCell((int) 19).setCellValue(" Location ");
			
 		   if(reportList!=null)
		    {
 			for (FeedbackPojo CDRD:reportList) {
 				if (CDRD.getStatus().equals("Snooze")) {
 				HSSFRow rowData=sheet.createRow((int)index2);
				//Data Insertion as Ticket Lodged By Department Name
 				rowData.createCell((int) 0).setCellValue(CDRD.getFeedback_by_dept());
				rowData.createCell((int) 1).setCellValue(CDRD.getTicket_no());
				rowData.createCell((int) 2).setCellValue(CDRD.getOpen_date());
				rowData.createCell((int) 3).setCellValue(CDRD.getOpen_time());
				rowData.createCell((int) 4).setCellValue(CDRD.getAddressingTime());
				rowData.createCell((int) 5).setCellValue(CDRD.getFeed_by());
				rowData.createCell((int) 6).setCellValue(CDRD.getFeedback_to_subdept());
				rowData.createCell((int) 7).setCellValue(CDRD.getFeedback_catg());
				rowData.createCell((int) 8).setCellValue(CDRD.getFeedback_subcatg());
				rowData.createCell((int) 9).setCellValue(CDRD.getFeed_brief());
				rowData.createCell((int) 10).setCellValue(CDRD.getSn_on_date());
				rowData.createCell((int) 11).setCellValue(CDRD.getSn_on_time());
				rowData.createCell((int) 12).setCellValue(CDRD.getSn_date());
				rowData.createCell((int) 13).setCellValue(CDRD.getSn_time());
				rowData.createCell((int) 14).setCellValue(CDRD.getSn_duration());
				rowData.createCell((int) 15).setCellValue(CDRD.getSn_reason());
				rowData.createCell((int) 16).setCellValue(CDRD.getAction_by());
				rowData.createCell((int) 17).setCellValue(CDRD.getLevel());
				rowData.createCell((int) 18).setCellValue(CDRD.getVia_from());
				rowData.createCell((int) 19).setCellValue(CDRD.getLocation());
				index2++;
 				sheet.autoSizeColumn(index2);
			}
		   }
 		  }
		}
 		
 			if (status!=null && !status.equals("") && (status.equals("All") || status.equals("High Priority")) && feed_hp>0) {
 			Row headerRow12 = sheet.createRow(header_3-1);
			headerRow12.setHeightInPoints(18);
			Cell headerTitleCell12 = headerRow12.createCell(0);
			headerTitleCell12.setCellValue("High Priority Ticket Details On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTime());
			headerTitleCell12.setCellStyle(styles.get("subTitle_HP"));
			sheet.addMergedRegion(new CellRangeAddress(header_3-1, header_3-1, 0,14));
 		  // Code Start for showing the data for current day Snooze Tickets
	 		HSSFRow rowHead2 = sheet.createRow(header_3);
			rowHead2.createCell((int) 0).setCellValue(" From Dept ");
			rowHead2.createCell((int) 1).setCellValue(" Ticket No ");
			rowHead2.createCell((int) 2).setCellValue(" Open On ");
			rowHead2.createCell((int) 3).setCellValue(" Open At ");
			rowHead2.createCell((int) 4).setCellValue(" Call Lodged By ");
			rowHead2.createCell((int) 5).setCellValue(" To Sub Department ");
			rowHead2.createCell((int) 6).setCellValue(" Complaint Category ");
			rowHead2.createCell((int) 7).setCellValue(" Complaint Description ");
			rowHead2.createCell((int) 8).setCellValue(" High Priority Reason ");
			rowHead2.createCell((int) 9).setCellValue(" High Priority Date ");
			rowHead2.createCell((int) 10).setCellValue(" High Priority Time ");
			rowHead2.createCell((int) 11).setCellValue(" High Priority By ");
			rowHead2.createCell((int) 12).setCellValue(" Level ");
			rowHead2.createCell((int) 13).setCellValue(" Alloted To ");
			rowHead2.createCell((int) 14).setCellValue(" Location ");
		
 		
 		   if(reportList!=null)
		    {
 			for (FeedbackPojo CDRD:reportList) {
 				if (CDRD.getStatus().equals("High Priority")) {
 					HSSFRow rowData=sheet.createRow((int)index3);
					//Data Insertion as Ticket Lodged By Department Name
					rowData.createCell((int) 0).setCellValue(CDRD.getFeedback_by_dept());
					rowData.createCell((int) 1).setCellValue(CDRD.getTicket_no());
					rowData.createCell((int) 2).setCellValue(CDRD.getOpen_date());
					rowData.createCell((int) 3).setCellValue(CDRD.getOpen_time());
					rowData.createCell((int) 4).setCellValue(CDRD.getFeed_by());
					rowData.createCell((int) 5).setCellValue(CDRD.getFeedback_to_subdept());
					rowData.createCell((int) 6).setCellValue(CDRD.getFeedback_catg());
					rowData.createCell((int) 7).setCellValue(CDRD.getFeed_brief());
					rowData.createCell((int) 8).setCellValue(CDRD.getHp_reason());
					rowData.createCell((int) 9).setCellValue(CDRD.getHp_date());
					rowData.createCell((int) 10).setCellValue(CDRD.getHp_time());
					rowData.createCell((int) 11).setCellValue(CDRD.getFeed_registerby());
					rowData.createCell((int) 12).setCellValue(CDRD.getLevel());
					rowData.createCell((int) 13).setCellValue(CDRD.getFeedback_allot_to());
					rowData.createCell((int) 14).setCellValue(CDRD.getLocation());
					index3++;
	 				sheet.autoSizeColumn(index3);
			}
		   }
 		  }
		}
 		
 			if (status!=null && !status.equals("") && (status.equals("All") || status.equals("Ignore")) && feed_ig>0) {
			Row headerRow13 = sheet.createRow(header_4-1);
			headerRow13.setHeightInPoints(18);
			Cell headerTitleCell13 = headerRow13.createCell(0);
			headerTitleCell13.setCellValue("Ignore Ticket Details On "+DateUtil.getCurrentDateIndianFormat()+" At "+DateUtil.getCurrentTime());
			headerTitleCell13.setCellStyle(styles.get("subTitle_IG"));
			sheet.addMergedRegion(new CellRangeAddress(header_4-1, header_4-1, 0,14));
	 		  // Code Start for showing the data for current day Snooze Tickets
	 		HSSFRow rowHead3 = sheet.createRow(header_4);
			rowHead3.createCell((int) 0).setCellValue(" From Dept ");
			rowHead3.createCell((int) 1).setCellValue(" Ticket No ");
			rowHead3.createCell((int) 2).setCellValue(" Open On ");
			rowHead3.createCell((int) 3).setCellValue(" Open At ");
			rowHead3.createCell((int) 4).setCellValue(" Call Lodged By ");
			rowHead3.createCell((int) 5).setCellValue(" To Sub Department ");
			rowHead3.createCell((int) 6).setCellValue(" Complaint Category ");
			rowHead3.createCell((int) 7).setCellValue(" Complaint Description ");
			rowHead3.createCell((int) 8).setCellValue(" Ignore Reason ");
			rowHead3.createCell((int) 9).setCellValue(" Ignore Date ");
			rowHead3.createCell((int) 10).setCellValue(" Ignore Time ");
			rowHead3.createCell((int) 11).setCellValue(" Ignored By ");
			rowHead3.createCell((int) 12).setCellValue(" Level ");
			rowHead3.createCell((int) 13).setCellValue(" Alloted To ");
			rowHead3.createCell((int) 14).setCellValue(" Location ");
		
 		
 		   if(reportList!=null)
		    {
 			for (FeedbackPojo CDRD:reportList) {
 				if (CDRD.getStatus().equals("Ignore")) {
 					HSSFRow rowData=sheet.createRow((int)index4);
					//Data Insertion as Ticket Lodged By Department Name
					rowData.createCell((int) 0).setCellValue(CDRD.getFeedback_by_dept());
					rowData.createCell((int) 1).setCellValue(CDRD.getTicket_no());
					rowData.createCell((int) 2).setCellValue(CDRD.getOpen_date());
					rowData.createCell((int) 3).setCellValue(CDRD.getOpen_time());
					rowData.createCell((int) 4).setCellValue(CDRD.getFeed_by());
					rowData.createCell((int) 5).setCellValue(CDRD.getFeedback_to_subdept());
					rowData.createCell((int) 6).setCellValue(CDRD.getFeedback_catg());
					rowData.createCell((int) 7).setCellValue(CDRD.getFeed_brief());
					rowData.createCell((int) 8).setCellValue(CDRD.getIg_reason());
					rowData.createCell((int) 9).setCellValue(CDRD.getIg_date());
					rowData.createCell((int) 10).setCellValue(CDRD.getIg_time());
					rowData.createCell((int) 11).setCellValue(CDRD.getAction_by());
					rowData.createCell((int) 12).setCellValue(CDRD.getLevel());
					rowData.createCell((int) 13).setCellValue(CDRD.getFeedback_allot_to());
					rowData.createCell((int) 14).setCellValue(CDRD.getLocation());
					index4++;
	 				sheet.autoSizeColumn(index4);
			}
		   }
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