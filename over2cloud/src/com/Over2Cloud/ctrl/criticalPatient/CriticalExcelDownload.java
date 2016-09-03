package com.Over2Cloud.ctrl.criticalPatient;

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
public class CriticalExcelDownload 
{

	public String writeDataInExcel(List dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,List  historyNotInfor,List historyInform,List historyEsc,List historyClose,List historyClosep,List historyescl,List historySendSms,SessionFactory connection)
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

		 
		excelFileName = "Critical_Result_Till" + mergeDateTime + ".xls";

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
					if (keyList.get(cellIndex).toString() != null && keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as not_inform") 
							||keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_name as doctor_not")
							||keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time as notinform"))
					{
						if (historyNotInfor != null && historyNotInfor.size() > 0)//
						{
							for (Iterator ite = historyNotInfor.iterator(); ite.hasNext();)
							{
								Object[] obje = (Object[]) ite.next();
								if (obje[0].equals(object[15]))
								{
									actiondate=obje[3].toString();
									actiontime=obje[4].toString();
									
									if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as not_inform"))
									{
										if (obje[1] != null )
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[1].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_name as doctor_not"))
									{
										if (obje[2] != null )
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[2].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time as notinform"))
									{
										if (obje[3] != null )
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(DateUtil.convertDateToIndianFormat(actiondate)+" "+actiontime);
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time") 
											|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh1.action_date"))
									{
										if (obje[4] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[4].toString());
											cell.setCellStyle(cellStyle);
										} else
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
						} 
						 
					} 
					else if (keyList.get(cellIndex).toString() != null && keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as inform") 
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_name as doctor_in")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time as informT")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.nursing_name")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.nursing_comment")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_name as docName")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_comment")
					)
					{
						if (historyInform != null && historyInform.size() > 0)//
						{
							for (Iterator ite = historyInform.iterator(); ite.hasNext();)
							{
								Object[] obje = (Object[]) ite.next();
								if (obje[0].equals(object[15]))
								{
									actiondate=obje[3].toString();
									actiontime=obje[4].toString();
									if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as inform"))
									{
										if (obje[1] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[1].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_name as doctor_in"))
									{
										if (obje[2] != null )
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[2].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time"))
									{
										if (obje[3] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[3].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time as informT"))
									{
										if (obje[3] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(DateUtil.convertDateToIndianFormat(actiondate)+" "+actiontime);
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.nursing_name"))
									{
										if (obje[3] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[5].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.nursing_comment"))
									{
										if (obje[3] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[6].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_name as docName"))
									{
										if (obje[3] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[7].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_comment"))
									{
										if (obje[3] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[8].toString());
											cell.setCellStyle(cellStyle);
										} else
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

						}  
						 
					} 
					else if (keyList.get(cellIndex).toString() != null && keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as close") 
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_name as doctor_close")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time as closeT")
					|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.close_by_id")
					|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.close_by_name")
					|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.assign_to_id")
					|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.close_by_name as emp")
					)
					{
						if (historyClose != null && historyClose.size() > 0)
						{
							for (Iterator ite = historyClose.iterator(); ite.hasNext();)
							{
								Object[] obje = (Object[]) ite.next();
								
								if (obje[0].equals(object[15]))
								{
									actiondate=obje[3].toString();
									actiontime=obje[4].toString();
									if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as close"))
									{
										if (obje[1] != null )
										{
											
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[1].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_name as doctor_close"))
									{
										if (obje[2] != null )
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[2].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time"))
									{
										if (obje[4] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[4].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time as closeT"))
									{
										if (obje[3] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(DateUtil.convertDateToIndianFormat(actiondate)+" "+actiontime);
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.close_by_id"))
									{
										if (obje[1] != null )
										{
											
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[5].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.close_by_name as emp"))
									{
										if (obje[1] != null && !obje[8].toString().equalsIgnoreCase("NA") && !obje[8].toString().contains("#"))
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[8].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.close_by_name"))
									{
										if (obje[1] != null && !obje[6].toString().equalsIgnoreCase("NA") && obje[6].toString().contains("#") )
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[6].toString().split("#")[2]);
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.assign_to_id"))
									{
										if (obje[1] != null )
										{
											
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[7].toString());
											cell.setCellStyle(cellStyle);
										} else
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
						}  
						 
					}  
					else if (keyList.get(cellIndex).toString() != null && keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as closep") 
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_name as doctor_p")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time as closePT"))
					{
						if (historyClosep != null && historyClosep.size() > 0)
						{
							for (Iterator ite = historyClosep.iterator(); ite.hasNext();)
							{
								Object[] obje = (Object[]) ite.next();
								
								if (obje[0].equals(object[15]))
								{
									actiondate=obje[3].toString();
									actiontime=obje[4].toString();
									if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as closep"))
									{
										if (obje[1] != null )
										{
											
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[1].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_name as doctor_p"))
									{
										if (obje[2] != null )
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[2].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time"))
									{
										if (obje[3] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[3].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time as closePT"))
									{
										if (obje[3] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(DateUtil.convertDateToIndianFormat(actiondate)+" "+actiontime);
											cell.setCellStyle(cellStyle);
										} else
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
						} 
						 
					} 
					else if (keyList.get(cellIndex).toString() != null && keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as escalate")
							||keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time as escT"))
					{
						if (historyEsc != null && historyEsc.size() > 0)
						{
							for (Iterator ite = historyEsc.iterator(); ite.hasNext();)
							{
								Object[] obje = (Object[]) ite.next();
								
								if (obje[0].equals(object[15]))
								{
									
									if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as escalate"))
									{
										if (obje[1] != null )
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[1].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.doc_name as doctor_esc"))
									{
										if (obje[2] != null )
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[2].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time"))
									{
										if (obje[3] != null)
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[3].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time as escT"))
									{
										if (obje[3] != null)
										{
											actiondate=obje[3].toString();
											actiontime=obje[4].toString();
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(DateUtil.convertDateToIndianFormat(actiondate)+" "+actiontime);
											cell.setCellStyle(cellStyle);
										} else
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
						} 
						 
					} 
					else if (keyList.get(cellIndex).toString() != null && keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as sendSMS")
							||keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time as smsT"))
					{
						if (historySendSms != null && historySendSms.size() > 0)
						{
							for (Iterator ite = historySendSms.iterator(); ite.hasNext();)
							{
								Object[] obje = (Object[]) ite.next();
								
								if (obje[0].equals(object[15]))
								{
									
									if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as sendSMS"))
									{
										if (obje[1] != null )
										{
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(obje[1].toString());
											cell.setCellStyle(cellStyle);
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.action_time as smsT"))
									{
										if (obje[3] != null)
										{
											actiondate=obje[3].toString();
											actiontime=obje[4].toString();
											Cell cell = rowData.createCell((int) cellIndex);
											cell.setCellValue(DateUtil.convertDateToIndianFormat(actiondate)+" "+actiontime);
											cell.setCellStyle(cellStyle);
										} else
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
						} 
						 
					} 
					else if (keyList.get(cellIndex).toString() != null && 
					keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to as l2") || keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to_mob as l2m")
					||keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to as l3") || keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to_mob as l3m")
					||keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to as l4") || keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to_mob as l4m")
					||keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to as l5") || keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to_mob as l5m"))
					{
						if (historyescl != null && historyescl.size() > 0)
						{
							for (Iterator ite = historyescl.iterator(); ite.hasNext();)
							{
								Object[] obje = (Object[]) ite.next();
								if (obje[0].equals(object[15]))
								{
									if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to as l1"))
									{
										if (object[5] != null && object[5].toString().equalsIgnoreCase("Level1"))
										{
											if (obje[1] != null && !obje[1].toString().equalsIgnoreCase("NA"))
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[1].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}	
										}
										else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}	
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to_mob as l1m"))
									{
										if (object[5] != null && object[5].toString().equalsIgnoreCase("Level1"))
										{
											if (obje[2] != null && !obje[2].toString().equalsIgnoreCase("NA"))
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[2].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}	
										}
										else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}	
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to as l2"))
									{
										
										if (object[5] != null && object[5].toString().equalsIgnoreCase("Level2"))
										{
											if (obje[1] != null && !obje[1].toString().equalsIgnoreCase("NA"))
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[1].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}	
										}
										else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}	
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to_mob as l2m"))
									{
										if (object[5] != null && object[5].toString().equalsIgnoreCase("Level2"))
										{
											if (obje[2] != null && !obje[2].toString().equalsIgnoreCase("NA"))
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[2].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}	
										}
										else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}	
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to as l3"))
									{
										if (object[5] != null && object[5].toString().equalsIgnoreCase("Level3"))
										{
											if (obje[1] != null && !obje[1].toString().equalsIgnoreCase("NA"))
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[1].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}	
										}
										else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}	
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to_mob as l3m"))
									{
										if (object[5] != null && object[5].toString().equalsIgnoreCase("Level3"))
										{
											if (obje[2] != null && !obje[2].toString().equalsIgnoreCase("NA"))
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[2].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}	
										}
										else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}	
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to as l4"))
									{
										if (object[5] != null && object[5].toString().equalsIgnoreCase("Level4"))
										{
											if (obje[1] != null && !obje[1].toString().equalsIgnoreCase("NA"))
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[1].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}	
										}
										else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}	
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to_mob as l4m"))
									{
										if (object[5] != null && object[5].toString().equalsIgnoreCase("Level4"))
										{
											if (obje[2] != null && !obje[2].toString().equalsIgnoreCase("NA"))
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[2].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}	
										}
										else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}	
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to as l5"))
									{
										if (object[5] != null && object[5].toString().equalsIgnoreCase("Level5"))
										{
											if (obje[1] != null && !obje[1].toString().equalsIgnoreCase("NA"))
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[1].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}	
										}
										else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}	
									}
									else if (keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.escalate_to_mob as l5m"))
									{
										if (object[5] != null && object[5].toString().equalsIgnoreCase("Level5"))
										{
											if (obje[2] != null && !obje[2].toString().equalsIgnoreCase("NA"))
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(obje[2].toString());
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}	
										}
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
						} 
						 
					
					}
					else if (keyList.get(cellIndex).toString() != null && keyList.get(cellIndex).toString().equalsIgnoreCase("cdh.comments as resolutiontime"))
					{
						if (historyClose != null && historyClose.size() > 0)
						{
							for (Iterator ite = historyClose.iterator(); ite.hasNext();)
							{
								Object[] obje1 = (Object[]) ite.next();
								if (obje1[0].equals(object[15]))
								{
									if (obje1[1] != null && !obje1[1].toString().equalsIgnoreCase("NA"))
									{
										Cell cell = rowData.createCell((int) cellIndex);
										cell.setCellValue(DateUtil.time_difference(actiondate, actiontime,obje1[3].toString(), obje1[4].toString()));
										cell.setCellStyle(cellStyle);
									} else
									{
										rowData.createCell((int) cellIndex).setCellValue("NA");
									}	
								
								}
							}
						}
					}
					else
					{
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