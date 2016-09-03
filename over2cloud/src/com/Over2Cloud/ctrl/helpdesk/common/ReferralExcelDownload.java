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

public class ReferralExcelDownload
{
	public String writeDataInExcel(List dataList, List titleList, List keyList, String excelName, String orgName, boolean needToStore, SessionFactory connection, List escdatalist, List resdatalist, List informeddatalist, List noreslist)
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

		// Creating the folder for holding the Excel files with the defined name
		/*
		 * if(needToStore) excelFileName = new
		 * CreateFolderOs().createUserDir("Feedback_Status")+ "/" +"Feedback"+
		 * mergeDateTime+".xls"; else
		 */
		excelFileName = "Referral_Feedback" + mergeDateTime + ".xls";

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

					// //System.out.println(object[cellIndex]);
					// //System.out.println(keyList.get(cellIndex).toString());
					if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.status as no_response") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_date as actDate")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_time as actTime")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.level") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.level") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.level") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.level") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.level")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_by")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.resolution_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_id as ID")
							|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_name as asName") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_desig") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_close_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_date") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh8.action_by"))
					{

						if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.level") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_date as actDate")
								|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.level") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_time as actTime")
								|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.level") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.level")
								|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.level") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.comments")
								|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_by"))
						{
							if (escdatalist != null && escdatalist.size() > 0)
							{

								int in = 0;
								for (Iterator ite = escdatalist.iterator(); ite.hasNext();)
								{
									Object[] obje = (Object[]) ite.next();
									if (obje[6].equals(object[0]))
									{
										if (in == 0)
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_date as actDate"))
											{
												if (obje[0] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(DateUtil.convertDateToIndianFormat(obje[0].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_time as actTime"))
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
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.level"))
											{
												if (obje[2] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													if (obje[3] != null && !obje[3].toString().trim().equalsIgnoreCase(""))
													{
														cell.setCellValue(obje[2].toString() + "(" + obje[3].toString() + ")");
													} else
													{
														cell.setCellValue(obje[2].toString() + "(NA)");
													}
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.comments"))
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
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_by"))
											{
												if (obje[5] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(obje[5].toString());
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}
										} else if (in == 1)
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_date as actDate"))
											{
												if (obje[0] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(DateUtil.convertDateToIndianFormat(obje[0].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_time as actTime"))
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
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.level"))
											{
												if (obje[2] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													if (obje[3] != null && !obje[3].toString().trim().equalsIgnoreCase(""))
													{
														cell.setCellValue(obje[2].toString() + "(" + obje[3].toString() + ")");
													} else
													{
														cell.setCellValue(obje[2].toString() + "(NA)");
													}
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.comments"))
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
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_by"))
											{
												if (obje[5] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(obje[5].toString());
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}
										} else if (in == 2)
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_date as actDate"))
											{
												if (obje[0] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(DateUtil.convertDateToIndianFormat(obje[0].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_time as actTime"))
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
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.level"))
											{
												if (obje[2] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													if (obje[3] != null && !obje[3].toString().trim().equalsIgnoreCase(""))
													{
														cell.setCellValue(obje[2].toString() + "(" + obje[3].toString() + ")");
													} else
													{
														cell.setCellValue(obje[2].toString() + "(NA)");
													}
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.comments"))
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
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_by"))
											{
												if (obje[5] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(obje[5].toString());
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}
										} else if (in == 3)
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_date as actDate"))
											{
												if (obje[0] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(DateUtil.convertDateToIndianFormat(obje[0].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_time as actTime"))
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
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.level"))
											{
												if (obje[2] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													if (obje[3] != null && !obje[3].toString().trim().equalsIgnoreCase(""))
													{
														cell.setCellValue(obje[2].toString() + "(" + obje[3].toString() + ")");
													} else
													{
														cell.setCellValue(obje[2].toString() + "(NA)");
													}
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.comments"))
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
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_by"))
											{
												if (obje[5] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(obje[5].toString());
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}
										} else if (in == 4)
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_date as actDate"))
											{
												if (obje[0] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(DateUtil.convertDateToIndianFormat(obje[0].toString()));
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_time as actTime"))
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
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.level"))
											{
												if (obje[2] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													if (obje[3] != null && !obje[3].toString().trim().equalsIgnoreCase(""))
													{
														cell.setCellValue(obje[2].toString() + "(" + obje[3].toString() + ")");
													} else
													{
														cell.setCellValue(obje[2].toString() + "(NA)");
													}
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.comments"))
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
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_by"))
											{
												if (obje[5] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(obje[5].toString());
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}
										}

										in++;
									}
								}
							}
						} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.resolution_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_id as ID") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_name as asName") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_desig") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_close_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_date")
								|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh8.action_by"))
						{
							if (object[28] != null && object[28].toString().equalsIgnoreCase("Seen"))
							{
								// //System.out.println("TEST1111111111");
								if (resdatalist != null && resdatalist.size() > 0)
								{
									// //System.out.println("**************"+resdatalist.size());
									for (Iterator ite = resdatalist.iterator(); ite.hasNext();)
									{
										Object[] obje = (Object[]) ite.next();
										if (obje[7].equals(object[0]))
										{
											if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_id as ID"))
											{
												if (obje[0] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(obje[0].toString());
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_name as asName"))
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
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_desig"))
											{
												if (obje[2] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(obje[2].toString());
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_close_by"))
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
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_date"))
											{
												if (obje[4] != null)
												{
													calendar.set(Integer.parseInt(obje[4].toString().split("-")[0]), DateUtil.getMonthFromDay(obje[4].toString().split("-")[1]), Integer.parseInt(obje[4].toString().split("-")[2]));
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(calendar);
													actiondate = obje[4].toString();
													cell.setCellStyle(cellStyle);
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_time"))
											{
												if (obje[5] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(obje[5].toString());
													cell.setCellStyle(cellStyle);
													actiontime = obje[5].toString();
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}

											else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh8.action_by"))
											{
												if (obje[6] != null)
												{
													Cell cell = rowData.createCell((int) cellIndex);
													cell.setCellValue(obje[6].toString());
													cell.setCellStyle(cellStyle);
													actiontime = obje[6].toString();
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.resolution_time"))
											{
												if (informeddatalist != null && informeddatalist.size() > 0)
												{
													for (Iterator itr1 = informeddatalist.iterator(); itr1.hasNext();)
													{
														Object[] obj1 = (Object[]) itr1.next();
														if (obj1[5].equals(object[0]))
														{
															if (obj1[0] != null && obj1[1] != null)
															{
																Cell cell = rowData.createCell((int) cellIndex);
																cell.setCellValue(DateUtil.time_difference(obj1[0].toString(), obj1[1].toString(), obje[4].toString(), obje[5].toString()));
																cell.setCellStyle(cellStyle);
																actiontime = obje[5].toString();
															} else
															{
																rowData.createCell((int) cellIndex).setCellValue("NA");
															}
														}
													}
												} else
												{
													rowData.createCell((int) cellIndex).setCellValue("NA");
												}
											}

										}
									}
								} else
								{
									rowData.createCell((int) cellIndex).setCellValue("NA");
								}
							} else
							{
								rowData.createCell((int) cellIndex).setCellValue("NA");
							}
						} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.status as no_response"))
						{
							if (noreslist != null && noreslist.size() > 0)
							{
								for (Iterator itr = noreslist.iterator(); itr.hasNext();)
								{
									Object[] obj = (Object[]) itr.next();
									if (obj[2].equals(object[0]))
									{
										if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.status as no_response"))
										{
											if (obj[0] != null)
											{
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(DateUtil.convertDateToIndianFormat(obj[0].toString()));
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}
										}
									} else
									{
										rowData.createCell((int) cellIndex).setCellValue("NA");
									}
								}
							} else
							{
								rowData.createCell((int) cellIndex).setCellValue("NA");
							}

						}

					} else
					{
						if (!keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_date as actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_date as actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_date as actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_date as actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_date as actDate")
								&& !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_time as actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_time as actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_time as actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_time as actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_time as actTime")
								&& !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.level") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.level") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.level") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.level") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.level") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.comments")
								&& !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.comments") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.comments") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.comments") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.comments") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_by") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_by")
								&& !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_by") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_by") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_by") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.resolution_time") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_id as ID") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_name as asName")
								|| !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_desig") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.status as no_response") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_close_by") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_date") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_time") && object[cellIndex] != null)
						{
							// //System.out.println(keyList.get(cellIndex).toString());
							cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));
							if (object[cellIndex] != null && object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.assign_to_id") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.assign_to_name") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_by"))
							{
								// //System.out.println(object[cellIndex].toString());
								// //System.out.println(object[cellIndex].toString().split("-")[1]);
								if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_date") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.assign_to_id") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.assign_to_name") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_by"))
								{
									// //System.out.println(keyList.get(cellIndex).toString());
									if (object[28] != null)
									{
										if (informeddatalist != null && informeddatalist.size() > 0)
										{
											for (Iterator itr = informeddatalist.iterator(); itr.hasNext();)
											{
												Object[] obj = (Object[]) itr.next();
												if (obj[5].equals(object[0]))
												{
													if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_date"))
													{
														if (obj[0] != null)
														{
															calendar.set(Integer.parseInt(obj[0].toString().split("-")[0]), DateUtil.getMonthFromDay(obj[0].toString().split("-")[1]), Integer.parseInt(obj[0].toString().split("-")[2]));
															Cell cell = rowData.createCell((int) cellIndex);
															cell.setCellValue(calendar);
															actiondate = obj[0].toString();
															cell.setCellStyle(cellStyle);
														} else
														{
															rowData.createCell((int) cellIndex).setCellValue("NA");
														}
													} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_time"))
													{
														if (obj[1] != null)
														{
															Cell cell = rowData.createCell((int) cellIndex);
															cell.setCellValue(obj[1].toString());
															cell.setCellStyle(cellStyle);
															actiontime = obj[1].toString();
														} else
														{
															rowData.createCell((int) cellIndex).setCellValue("NA");
														}
													} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.assign_to_id"))
													{
														if (obj[2] != null)
														{
															// //System.out.println("################111111111111111"+obj[2]);
															Cell cell = rowData.createCell((int) cellIndex);
															cell.setCellValue(obj[2].toString());
															cell.setCellStyle(cellStyle);
														} else
														{
															rowData.createCell((int) cellIndex).setCellValue("NA");
														}
													} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.assign_to_name"))
													{
														if (obj[3] != null)
														{
															// //System.out.println("################"+obj[3]);
															Cell cell = rowData.createCell((int) cellIndex);
															cell.setCellValue(obj[3].toString());
															cell.setCellStyle(cellStyle);
														} else
														{
															rowData.createCell((int) cellIndex).setCellValue("NA");
														}
													} else if (keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_by"))
													{
														if (obj[4] != null)
														{
															// //System.out.println(obj[4].toString());
															Cell cell = rowData.createCell((int) cellIndex);
															cell.setCellValue(obj[4].toString());
															cell.setCellStyle(cellStyle);
														} else
														{
															rowData.createCell((int) cellIndex).setCellValue("NA");
														}
													}
												}
											}
										} else
										{
											rowData.createCell((int) cellIndex).setCellValue("NA");
										}

									} else
									{
										if (object[cellIndex] != null && object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
										{
											if (object[cellIndex] != null)
											{
												calendar.set(Integer.parseInt(object[cellIndex].toString().split("-")[0]), DateUtil.getMonthFromDay(object[cellIndex].toString().split("-")[1]), Integer.parseInt(object[cellIndex].toString().split("-")[2]));
												Cell cell = rowData.createCell((int) cellIndex);
												cell.setCellValue(calendar);
												cell.setCellStyle(cellStyle);
											} else
											{
												rowData.createCell((int) cellIndex).setCellValue("NA");
											}
										} else
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
								} else
								{
									if (object[cellIndex] != null)
									{
										calendar.set(Integer.parseInt(object[cellIndex].toString().split("-")[0]), DateUtil.getMonthFromDay(object[cellIndex].toString().split("-")[1]), Integer.parseInt(object[cellIndex].toString().split("-")[2]));
										Cell cell = rowData.createCell((int) cellIndex);
										cell.setCellValue(calendar);
										cell.setCellStyle(cellStyle);
									} else
									{
										rowData.createCell((int) cellIndex).setCellValue("NA");
									}
								}
							} else
							{
								if (object[cellIndex] != null)
								{
									// //System.out.println(cellIndex+"    "+object[cellIndex].toString());
									Cell cell = rowData.createCell((int) cellIndex);
									cell.setCellValue(object[cellIndex].toString());
								} else
								{
									rowData.createCell((int) cellIndex).setCellValue("NA");
								}
							}

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