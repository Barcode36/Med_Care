package com.Over2Cloud.ctrl.patientcare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class CPSRoster extends GridPropertyBean {

	private String excelFileName;
	private FileInputStream excelStream;
	public SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	private File feedbackExcel;
	private String feedbackExcelFileName;
	List<CPSRosterPojo> excelData = null;
	private List<CPSRosterPojo> gridFbDraftExcelModel;
	private JSONArray jsonList;
	private String deptFlag;
	private String LocId;
	private String locName;
	private String shiftId, wingIds, viewFlag;
	private String shiftID, shiftEmpMapSubdept;
	private String trashEmpId;

	public String deleteEmpAllService()
	{
		//402kk01kk2 shiftId 1
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		cbt.executeAllUpdateQuery("delete from cps_roster_config where employee='"+wingIds.split("kk")[0]+"' and location='"+shiftId+"'", connectionSpace);
		addActionMessage("Truncate Successfull");
		return "success";
	}
	public String truncketTable()
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		cbt.executeAllUpdateQuery("truncate table `4_clouddb`.`cps_roster_config`", connectionSpace);
		addActionMessage("Truncate Successfull");
		return "success";
	
	}
	public String beforeUpload() {
		return "success";
	}

	// Download Formate
	public String downloadFormat() {
		boolean validFlag = ValidateSession.checkSession();
		if (validFlag) {
			try {
				List titleList = new ArrayList();

				titleList.add("Location");
				titleList.add("Employee");
				titleList.add("Mobile");
				titleList.add("1");
				titleList.add("2");
				titleList.add("3");
				titleList.add("4");
				titleList.add("5");
				titleList.add("6");
				titleList.add("7");
				titleList.add("8");
				titleList.add("9");
				titleList.add("10");
				titleList.add("11");
				titleList.add("12");
				titleList.add("13");
				titleList.add("14");
				titleList.add("15");
				titleList.add("16");
				titleList.add("17");
				titleList.add("18");
				titleList.add("19");
				titleList.add("20");
				titleList.add("21");
				titleList.add("22");
				titleList.add("23");
				titleList.add("24");
				titleList.add("25");
				titleList.add("26");
				titleList.add("27");
				titleList.add("28");
				titleList.add("29");
				titleList.add("30");
				titleList.add("31");

				String orgName = "Formate Test Name";
				if (titleList != null && titleList.size() > 0) {
					excelFileName = writeDataInExcel(null, titleList, null,
							"Test Name ", orgName + "", true, connectionSpace);
					System.out.println("excelFileName  " + excelFileName);
					if (excelFileName != null) {
						excelStream = new FileInputStream(excelFileName);
					}
					return SUCCESS;
				} else {
					return ERROR;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}

	public String writeDataInExcel(List dataList, List titleList, List keyList,
			String excelName, String orgName, boolean needToStore,
			SessionFactory connection) {

		String excelFileName = null;
		String mergeDateTime = new DateUtil().mergeDateTime();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(excelName);
		Map<String, CellStyle> styles = createStyles(wb);

		if (needToStore) {
			excelFileName = new CreateFolderOs().createUserDir("TestName")
					+ mergeDateTime + ".xls";
		}

		else {
			excelFileName = "TestName" + mergeDateTime + ".xls";
		}
		int index = 0;
		HSSFRow rowHead = sheet.createRow((int) index);
		for (int i = 0; i < titleList.size(); i++) {
			Cell subTitleCell22 = rowHead.createCell((i));
			subTitleCell22.setCellValue(titleList.get(i).toString());
			subTitleCell22.setCellStyle(styles.get("mytittle"));
		}

		try {
			FileOutputStream out = new FileOutputStream(new File(excelFileName));
			wb.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return excelFileName;
	}

	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style;

		// mytittle
		Font headerFont = wb.createFont();
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

	public String readExcel() throws Exception {

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {

				System.out.println("feedbackExcel " + feedbackExcel);
				if (feedbackExcel != null) {
					InputStream inputStream = new FileInputStream(feedbackExcel);

					if (feedbackExcelFileName.contains(".xlsx")) {
						GenericReadExcel7 GRE7 = new GenericReadExcel7();
						XSSFSheet excelSheet = GRE7.readExcel(inputStream);
						excelData = new ArrayList<CPSRosterPojo>();
						for (int rowIndex = 1; rowIndex < excelSheet
								.getPhysicalNumberOfRows(); rowIndex++) {
							XSSFRow row = excelSheet.getRow(rowIndex);
							int cellNo = row.getLastCellNum();
							if (cellNo > 0) {
								CPSRosterPojo FDP = new CPSRosterPojo();
								for (int cellIndex = 0; cellIndex < cellNo; cellIndex++) {

									switch (cellIndex) {
									case 0:

										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											// FDP.setLocation(GRE7.readExcelSheet(row,
											// cellIndex));
											FDP.setLocation(GRE7
													.readExcelSheet(row,
															cellIndex)
													.toString());
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setLocation("NA");
											System.out.println("inside else");
										}
										break;

									case 1:

										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setEmployee(GRE7
													.readExcelSheet(row,
															cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setEmployee("NA");
											System.out.println("inside else");
										}
										break;

									case 2:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setMobile(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setMobile("NA");
											System.out.println("inside else");
										}

										break;
									case 3:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay01(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay01("NA");
											System.out.println("inside else");
										}

										break;
									case 4:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay02(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay02("NA");
											System.out.println("inside else");
										}

										break;
									case 5:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay03(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay03("NA");
											System.out.println("inside else");
										}

										break;

									case 6:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay04(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay04("NA");
											System.out.println("inside else");
										}

										break;
									case 7:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay05(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay05("NA");
											System.out.println("inside else");
										}

										break;
									case 8:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay06(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay06("NA");
											System.out.println("inside else");
										}

										break;

									case 9:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay07(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay07("NA");
											System.out.println("inside else");
										}

										break;
									case 10:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay08(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay08("NA");
											System.out.println("inside else");
										}

										break;

									case 11:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay09(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay09("NA");
											System.out.println("inside else");
										}

										break;

									case 12:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay10(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay10("NA");
											System.out.println("inside else");
										}

										break;
									case 13:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay11(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay11("NA");
											System.out.println("inside else");
										}

										break;
									case 14:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay12(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay12("NA");
											System.out.println("inside else");
										}

										break;
									case 15:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay13(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay13("NA");
											System.out.println("inside else");
										}

										break;
									case 16:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay14(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay14("NA");
											System.out.println("inside else");
										}

										break;
									case 17:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay15(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay15("NA");
											System.out.println("inside else");
										}

										break;
									case 18:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay16(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay16("NA");
											System.out.println("inside else");
										}

										break;
									case 19:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay17(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay17("NA");
											System.out.println("inside else");
										}

										break;
									case 20:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay18(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay18("NA");
											System.out.println("inside else");
										}

										break;
									case 21:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay19(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay19("NA");
											System.out.println("inside else");
										}

										break;
									case 22:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay20(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay20("NA");
											System.out.println("inside else");
										}

										break;
									case 23:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay21(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay21("NA");
											System.out.println("inside else");
										}

										break;
									case 24:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay22(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay22("NA");
											System.out.println("inside else");
										}

										break;
									case 25:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay23(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay23("NA");
											System.out.println("inside else");
										}

										break;
									case 26:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay24(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay24("NA");
											System.out.println("inside else");
										}

										break;

									case 27:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay25(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay25("NA");
											System.out.println("inside else");
										}

										break;
									case 28:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay26(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay26("NA");
											System.out.println("inside else");
										}

										break;

									case 29:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay27(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay27("NA");
											System.out.println("inside else");
										}

										break;

									case 30:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay28(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay28("NA");
											System.out.println("inside else");
										}

										break;
									case 31:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay29(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay29("NA");
											System.out.println("inside else");
										}

										break;
									case 32:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay30(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay30("NA");
											System.out.println("inside else");
										}

										break;
									case 33:
										if (GRE7.readExcelSheet(row, cellIndex) != null
												&& !GRE7.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setDay31(GRE7.readExcelSheet(
													row, cellIndex));
											System.out.println("A coloumn  "
													+ GRE7.readExcelSheet(row,
															cellIndex));
										} else {
											FDP.setDay31("NA");
											System.out.println("inside else");
										}

										break;

									}
								}
								excelData.add(FDP);
							}
						}
					} else if (feedbackExcelFileName.contains(".xls")) {
						GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
						HSSFSheet excelSheet = GRBE.readExcel(inputStream);
						excelData = new ArrayList<CPSRosterPojo>();
						for (int rowIndex = 1; rowIndex < excelSheet
								.getPhysicalNumberOfRows(); rowIndex++) {
							HSSFRow row = excelSheet.getRow(rowIndex);
							int cellNo = row.getLastCellNum();
							if (cellNo > 0) {
								CPSRosterPojo FDP = new CPSRosterPojo();
								System.out.println("Cell No--" + cellNo);

								for (int cellIndex = 0; cellIndex < cellNo; cellIndex++)

								{
									switch (cellIndex) {
									case 0:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {
											FDP.setLocation(GRBE
													.readExcelSheet(row,
															cellIndex));
											System.out.println("setLocation--"
													+ FDP.getLocation());
										} else {
											FDP.setLocation("NA");
											System.out.println("setLocation--"
													+ FDP.getLocation());
										}
										break;

									case 1:
										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setEmployee(GRBE
													.readExcelSheet(row,
															cellIndex));
											System.out.println("setEmployee--"
													+ FDP.getEmployee());
										} else {

											FDP.setEmployee("NA");
											System.out.println("setEmployee--"
													+ FDP.getEmployee());
										}
										break;

									case 2:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setMobile(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setMobile--"
													+ FDP.getMobile());
										} else {

											FDP.setMobile("NA");
											System.out.println("setMobile--"
													+ FDP.getMobile());
										}
										break;

									case 3:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay01(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay1--"
													+ FDP.getDay01());
										} else {

											FDP.setDay01("NA");
											System.out.println("setDay1--"
													+ FDP.getDay01());
										}
										break;

									case 4:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay02(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay02());
										} else {
											FDP.setDay02("NA");
											System.out.println("setDay2--"
													+ FDP.getDay02());
										}
										break;
										
									case 5:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay03(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay03());
										} else {
											FDP.setDay03("NA");
											System.out.println("setDay2--"
													+ FDP.getDay03());
										}
										break;
										
									case 6:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay04(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay04());
										} else {
											FDP.setDay04("NA");
											System.out.println("setDay2--"
													+ FDP.getDay04());
										}
										break;
										
									case 7:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay05(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay05());
										} else {
											FDP.setDay05("NA");
											System.out.println("setDay2--"
													+ FDP.getDay05());
										}
										break;
										
									case 8:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay06(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay06());
										} else {
											FDP.setDay06("NA");
											System.out.println("setDay2--"
													+ FDP.getDay06());
										}
										break;
										
									case 9:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay07(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay07());
										} else {
											FDP.setDay07("NA");
											System.out.println("setDay2--"
													+ FDP.getDay07());
										}
										break;
										
									case 10:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay08(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay08());
										} else {
											FDP.setDay08("NA");
											System.out.println("setDay2--"
													+ FDP.getDay08());
										}
										break;
										
										
									case 11:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay09(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay09());
										} else {
											FDP.setDay09("NA");
											System.out.println("setDay2--"
													+ FDP.getDay09());
										}
										break;
										
										
									case 12:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay10(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay10());
										} else {
											FDP.setDay10("NA");
											System.out.println("setDay2--"
													+ FDP.getDay10());
										}
										break;
										
									case 13:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay11(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay11());
										} else {
											FDP.setDay11("NA");
											System.out.println("setDay2--"
													+ FDP.getDay11());
										}
										break;
										
									case 14:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay12(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay12());
										} else {
											FDP.setDay12("NA");
											System.out.println("setDay2--"
													+ FDP.getDay12());
										}
										break;
										
									case 15:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay13(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay13());
										} else {
											FDP.setDay13("NA");
											System.out.println("setDay2--"
													+ FDP.getDay13());
										}
										break;
										
										
										
									case 16:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay14(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay14());
										} else {
											FDP.setDay14("NA");
											System.out.println("setDay2--"
													+ FDP.getDay14());
										}
										break;
										
										
									case 17:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay15(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay15());
										} else {
											FDP.setDay15("NA");
											System.out.println("setDay2--"
													+ FDP.getDay15());
										}
										break;
										
										
									case 18:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay16(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay16());
										} else {
											FDP.setDay16("NA");
											System.out.println("setDay2--"
													+ FDP.getDay16());
										}
										break;
										
										
										
									case 19:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay17(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay17());
										} else {
											FDP.setDay17("NA");
											System.out.println("setDay2--"
													+ FDP.getDay17());
										}
										break;
										
										
									case 20:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay18(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay18());
										} else {
											FDP.setDay18("NA");
											System.out.println("setDay2--"
													+ FDP.getDay18());
										}
										break;
										
										
									case 21:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay19(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay19());
										} else {
											FDP.setDay19("NA");
											System.out.println("setDay2--"
													+ FDP.getDay19());
										}
										break;
										
										
									case 22:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay20(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay20());
										} else {
											FDP.setDay20("NA");
											System.out.println("setDay2--"
													+ FDP.getDay20());
										}
										break;
										
									case 23:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay21(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay21());
										} else {
											FDP.setDay21("NA");
											System.out.println("setDay2--"
													+ FDP.getDay21());
										}
										break;
										
										
									case 24:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay22(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay22());
										} else {
											FDP.setDay22("NA");
											System.out.println("setDay2--"
													+ FDP.getDay22());
										}
										break;
										
									case 25:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay23(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay23());
										} else {
											FDP.setDay23("NA");
											System.out.println("setDay2--"
													+ FDP.getDay23());
										}
										break;
										
									case 26:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay24(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay24());
										} else {
											FDP.setDay24("NA");
											System.out.println("setDay2--"
													+ FDP.getDay24());
										}
										break;
										
									case 27:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay25(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay25());
										} else {
											FDP.setDay25("NA");
											System.out.println("setDay2--"
													+ FDP.getDay25());
										}
										break;
										
									case 28:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay26(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay26());
										} else {
											FDP.setDay26("NA");
											System.out.println("setDay2--"
													+ FDP.getDay26());
										}
										break;
										
									case 29:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay27(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay27());
										} else {
											FDP.setDay27("NA");
											System.out.println("setDay2--"
													+ FDP.getDay27());
										}
										break;
										
										
									case 30:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay28(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay28());
										} else {
											FDP.setDay28("NA");
											System.out.println("setDay2--"
													+ FDP.getDay28());
										}
										break;
										
									case 31:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay29(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay29());
										} else {
											FDP.setDay29("NA");
											System.out.println("setDay2--"
													+ FDP.getDay29());
										}
										break;
										
									case 32:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay30(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay30());
										} else {
											FDP.setDay30("NA");
											System.out.println("setDay2--"
													+ FDP.getDay30());
										}
										break;
										
									case 33:

										if (GRBE.readExcelSheet(row, cellIndex) != null
												&& !GRBE.readExcelSheet(row,
														cellIndex).equals("")) {

											FDP.setDay31(GRBE.readExcelSheet(
													row, cellIndex));
											System.out.println("setDay2--"
													+ FDP.getDay31());
										} else {
											FDP.setDay31("NA");
											System.out.println("setDay2--"
													+ FDP.getDay31());
										}
										break;
									}
									
								}
								excelData.add(FDP);
							}
						}
					}
					if (excelData.size() > 0) {
						session.put("fbDraftList", excelData);
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnResult;
	}

	public String showUploadFbDraft() {
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				if (userName == null || userName.equals("")) {
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				} else {
					excelData = (List<CPSRosterPojo>) session
							.get("fbDraftList");
					if (excelData != null && excelData.size() > 0) {
						setRecords(excelData.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();

						setGridFbDraftExcelModel(excelData.subList(from, to));

						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
					returnResult = SUCCESS;
				}
			} catch (Exception e) {
			}
			return returnResult;
		} else {
			return LOGIN;
		}
	}

	public String saveExcelData() {
		boolean status = false;
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
			try {
				CommonOperInterface cot = new CommonConFactory()
						.createInterface();
				System.out.println("UserName " + userName);
				if (userName == null || userName.equals("")) {
					session.clear();
					addActionMessage("Your Session has been Finished");
					returnResult = LOGIN;
				} else {
					excelData = (List<CPSRosterPojo>) session
							.get("fbDraftList");
					System.out.println("bbbb " + excelData.size());

					session.remove("fbDraftList");
					boolean flag = false;
					InsertDataTable ob = null;
					for (Iterator<CPSRosterPojo> it = excelData.iterator(); it
							.hasNext();) {
						System.out.println("excelData   " + it);
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						CPSRosterPojo object = new CPSRosterPojo();
						object = it.next();
						if (excelData.size() > 0) {
							ob = new InsertDataTable();
							ob.setColName("location");
							ob.setDataName(fetchId("cps_location",
									"location_name", object.getLocation()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("employee");
							ob.setDataName(fetchId("employee_basic", "mobOne",
									object.getMobile()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("mobile");
							ob.setDataName(object.getMobile());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("userName");
							ob.setDataName(userName);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("date");
							ob.setDataName(DateUtil.getCurrentDateUSFormat());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("time");
							ob.setDataName(DateUtil.getCurrentTimeHourMin());
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day01");
							ob.setDataName(fetchServiceId(object.getDay01()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day02");
							ob.setDataName(fetchServiceId(object.getDay02()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day03");
							ob.setDataName(fetchServiceId(object.getDay03()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day04");
							ob.setDataName(fetchServiceId(object.getDay04()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day05");
							ob.setDataName(fetchServiceId(object.getDay05()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day06");
							ob.setDataName(fetchServiceId(object.getDay06()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day07");
							ob.setDataName(fetchServiceId(object.getDay07()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day08");
							ob.setDataName(fetchServiceId(object.getDay08()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day09");
							ob.setDataName(fetchServiceId(object.getDay09()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day10");
							ob.setDataName(fetchServiceId(object.getDay10()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day11");
							ob.setDataName(fetchServiceId(object.getDay11()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day12");
							ob.setDataName(fetchServiceId(object.getDay12()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day13");
							ob.setDataName(fetchServiceId(object.getDay13()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day14");
							ob.setDataName(fetchServiceId(object.getDay14()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day15");
							ob.setDataName(fetchServiceId(object.getDay15()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day16");
							ob.setDataName(fetchServiceId(object.getDay16()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day17");
							ob.setDataName(fetchServiceId(object.getDay17()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day18");
							ob.setDataName(fetchServiceId(object.getDay18()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day19");
							ob.setDataName(fetchServiceId(object.getDay09()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day20");
							ob.setDataName(fetchServiceId(object.getDay20()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day21");
							ob.setDataName(fetchServiceId(object.getDay21()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day22");
							ob.setDataName(fetchServiceId(object.getDay22()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day23");
							ob.setDataName(fetchServiceId(object.getDay23()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day24");
							ob.setDataName(fetchServiceId(object.getDay24()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day25");
							ob.setDataName(fetchServiceId(object.getDay25()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day26");
							ob.setDataName(fetchServiceId(object.getDay26()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day27");
							ob.setDataName(fetchServiceId(object.getDay27()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day28");
							ob.setDataName(fetchServiceId(object.getDay28()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day29");
							ob.setDataName(fetchServiceId(object.getDay29()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day30");
							ob.setDataName(fetchServiceId(object.getDay30()));
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day31");
							ob.setDataName(fetchServiceId(object.getDay31()));
							insertData.add(ob);

							status = cot.insertIntoTable("cps_roster_config",
									insertData, connectionSpace);
						}
					}
					if (status) {
						flag = true;
					}
				}

				if (true) {
					addActionMessage("Excel Uploaded Successfully, Total data: "
							+ excelData.size());
				}

				else {
					addActionMessage("!!!Opps there is some error in the excel data.");
				}
				returnResult = SUCCESS;
			} catch (Exception e) {
				System.out.println(e);
			}
			return returnResult;
		} else {
			return LOGIN;
		}

	}

	private Object fetchServiceId(String day) {
		String serviceID = "NA";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (day.contains("/")) {
			String[] dayArray = day.split("/");
			for (int i = 0; i < dayArray.length; i++) {

				List dataList = cbt
						.executeAllSelectQuery(
								"Select id from cps_service where service_name = '"
										+ dayArray[i] + "'".toString(),
								connectionSpace);

				if (dataList != null && dataList.size() > 0) {
					id = dataList.get(0).toString();
					if(id.length()==1){
						id= 0+""+id;
					}
					if (serviceID.equalsIgnoreCase("NA")) {
						serviceID = id;
					} else {
						serviceID = serviceID + "#" + id;
					}
				}

			}
		} else if (day.equalsIgnoreCase("NA")) {
			serviceID = day;

		} else {
			List dataList = cbt.executeAllSelectQuery(
					"Select id from cps_service where service_name = '" + day
							+ "'".toString(), connectionSpace);

			if (dataList != null && dataList.size() > 0) {
				id = dataList.get(0).toString();
				if (serviceID.equalsIgnoreCase("NA")) {
					serviceID = id;
				}

			} else {
				serviceID = "NA";
			}
		}
		System.out.println("serviceID " + serviceID);
		return serviceID;
	}

	private Object fetchId(String tableName, String columnName, String data) {
		String id = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try {
			List dataListEcho = cbt.executeAllSelectQuery("Select id from "
					+ tableName + " where " + columnName + " = '" + data
					+ "'".toString(), connectionSpace);
			if (dataListEcho != null && dataListEcho.size() > 0) {
				id = dataListEcho.get(0).toString();
			}
		} catch (SQLGrammarException e) {
			e.printStackTrace();
		}
		System.out.println("idididid >>  " + id);
		return id;
	}

	// get list for location
	public String LocationList() {
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)

		{
			try {
				jsonList = new JSONArray();
				if (deptFlag != null && deptFlag.equalsIgnoreCase("loc")) {

					// SELECT id, location_name FROM `cps_location`
					List loclist = cbt.executeAllSelectQuery(
							"SELECT id, location_name FROM cps_location WHERE STATUS ='Active'"
									.toString(), connectionSpace);
					if (loclist != null && loclist.size() > 0) {
						for (Iterator iterator = loclist.iterator(); iterator
								.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null) {

								JSONObject innerobj = new JSONObject();
								innerobj.put("id", object[0]);
								innerobj.put("loc", object[1].toString());
								jsonList.add(innerobj);
							}
						}
					}
				}

				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}

	}

	public String getEmployeeByLoc() {
		boolean sessionFlag = ValidateSession.checkSession();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (sessionFlag) {
			try {
				jsonList = new JSONArray();
				StringBuilder query = new StringBuilder();
				query
						.append(" SELECT id, empName, empLogo FROM employee_basic WHERE deptname=(SELECT id FROM department WHERE deptName='Patient Care') ");

				// System.out.println("Shift Wise List For employee qry::"+query);
				List dataList = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				if (dataList != null && dataList.size() > 0) {
					for (Iterator iterator = dataList.iterator(); iterator
							.hasNext();) {
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null) {
							JSONObject innerobj = new JSONObject();
							innerobj.put("id", object[0]);
							innerobj.put("name", object[1].toString());
							if (object[2] != null) {
								innerobj.put("logo", object[2].toString());
							} else {
								innerobj.put("logo", "images/noImage.JPG");
							}
							jsonList.add(innerobj);
						}
					}
				}

				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}

	public String getLocationMapDetail() {
		boolean sessionFlag = ValidateSession.checkSession();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		Map<String, String> innerobj2;

		if (sessionFlag) {
			try {
				jsonList = new JSONArray();
				if (LocId != null && !LocId.equalsIgnoreCase("OCC")) {
					StringBuffer query1 = new StringBuffer();
					List<String> dataList1 = new ArrayList<String>();
					String prefix = "day";

					for (int i = 01; i < 10; i++) {

						dataList1.add(prefix + "0" + i);
						System.out.println(prefix + "0" + i);
					}
					for (int i = 10; i < 32; i++) {

						dataList1.add(prefix + i);
						System.out.println(prefix + i);
					}
					if (dataList1 != null && dataList1.size() > 0) {

						for (String dayNum : dataList1) {
							// query1.delete(0, query1.length());
							Map<String, List> innerobj = new HashMap<String, List>();
							// Object[] object = (Object[]) iterator.next();

							System.out.println("iterator   " + dayNum);
							query1.setLength(0);
							query1
									.append("SELECT "
											+ dayNum
											+ " FROM `cps_roster_config` WHERE location=(SELECT id FROM `cps_location` WHERE `location_name` ='"
											+ LocId + "') ");

							List dataList2 = cbt.executeAllSelectQuery(query1
									.toString(), connectionSpace);
							int i = 0;
							if (dataList2 != null && dataList2.size() > 0) {

								String locid = dataList2.get(0).toString();
								if (locid.contains("#")) {
									locid = locid.replace('#', ',');
								}
								List tempList = new ArrayList();
								List dataList = cbt.executeAllSelectQuery(
										"SELECT id, `service_name` FROM `cps_service`"
												.toString(), connectionSpace);
								;
								for (Iterator iterator2 = dataList.iterator(); iterator2
										.hasNext();) {
									innerobj2 = new HashMap<String, String>();
									Object[] object3 = (Object[]) iterator2
											.next();
									if (object3[0] != null
											&& object3[1] != null) {
										String dayNumSec = dayNum.replaceAll(
												"day", "");
										innerobj2.put("wingid", dayNumSec
												+ "kk" + object3[0].toString());
										innerobj2.put("wingname", object3[1]
												.toString());

									}
									tempList.add(innerobj2);
								}

								innerobj.put(dayNum, tempList);
								jsonList.add(innerobj);
							}
						}

					}

				} else {

				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
	}

	public String getMappedEmployee() {
		boolean sessionFlag = ValidateSession.checkSession();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (sessionFlag) {
			try {

				System.out.println("viewFlag  " + viewFlag);
				System.out.println("locName  " + locName);
				System.out.println("shiftId  " + shiftId);
				System.out.println("wingIds  " + wingIds);
				jsonList = new JSONArray();
				StringBuilder query = new StringBuilder();

				String[] tempComa = wingIds.split(",");
				for (int i = 0; i < tempComa.length; i++) {

					String[] tempKK = tempComa[i].toString().split("kk");
					// for (int j = 0; j < tempKK.length; j++)
					if(tempKK[1].length()==1){
						tempKK[1] = 0+""+tempKK[1];
					}
					query.setLength(0);
					query
							.append("select emp.empName, emp.id from employee_basic as emp inner join `cps_roster_config` as ros on ros.employee = emp.id where ros.location=(select id from cps_location where location_name='"
									+ locName
									+ "') and ros.day"
									+ tempKK[0]
									+ " like '%" + tempKK[1] + "%' ");
					// System.out.println("Qyer: " + query);
					List dataList = cbt.executeAllSelectQuery(query.toString(),
							connectionSpace);
					if (dataList != null && dataList.size() > 0) {
						for (Iterator iterator = dataList.iterator(); iterator
								.hasNext();) {
							Object[] object = (Object[]) iterator.next();
							if (object[0] != null && object[1] != null) {
								JSONObject innerobj = new JSONObject();
								innerobj.put("id", object[1]);
								innerobj.put("name", object[0].toString());

								innerobj.put("shiftName", "General Shift");

								innerobj.put("shiftFrom", "00:01");

								innerobj.put("shiftTo", "23:59");

								innerobj.put("shiftid", "2");

								innerobj.put("wingid", tempComa[i]);

								jsonList.add(innerobj);
							}
						}

					} else {

					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}
		return SUCCESS;
	}

	public String addRosterEmpMapSave() {
		boolean sessionFlag = ValidateSession.checkSession();
		String empID = null, day = null, serviceID = null, mobile_no = null;
		if (sessionFlag) {
			try {
				CommonOperInterface cbt = new CommonConFactory()
						.createInterface();
				List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				InsertDataTable ob = null;
				boolean status = true;
				if (status) {
					String mm[] = shiftEmpMapSubdept.trim().split(",");
					for (int i = 0; i < mm.length; i++) {
						if (mm[i] != null
								&& !mm[i].toString().equalsIgnoreCase(" ")) {
							empID = mm[i].split("#")[0];
							day = mm[i].split("kk")[0];
							serviceID = mm[i].split("kk")[1];
						}
						List serMob = cbt.executeAllSelectQuery(
								"select mobOne from employee_basic where  id ='"
										+ empID + "'", connectionSpace);
						if (serMob != null && serMob.size() > 0) {
							mobile_no = serMob.get(0).toString();
						}
						List escData = cbt.executeAllSelectQuery(
								"select * from cps_roster_config where  employee ='"
										+ empID + "' and location='" + shiftID
										+ "'", connectionSpace);
						if (escData != null && escData.size() > 0) {
							List escData1 = cbt
									.executeAllSelectQuery(
											"select day"
													+ day.split("#")[1]
													+ " from cps_roster_config where  employee ='"
													+ empID + "' ",
											connectionSpace);
							if (escData1 != null
									&& escData1.size() > 0
									&& !escData1.get(0).toString()
											.equalsIgnoreCase("NA")) {
								cbt.executeAllUpdateQuery(
										"update cps_roster_config set location='"
												+ shiftID + "',mobile='"
												+ mobile_no + "',day"
												+ day.split("#")[1] + "='"
												+ escData1.get(0).toString()
												+ "#" + serviceID
												+ "' where employee='" + empID
												+ "' ", connectionSpace);
							} else {
								cbt.executeAllUpdateQuery(
										"update cps_roster_config set location='"
												+ shiftID + "',mobile='"
												+ mobile_no + "',day"
												+ day.split("#")[1] + "='"
												+ serviceID
												+ "' where employee='" + empID
												+ "' ", connectionSpace);
							}
						} else {
							ob = new InsertDataTable();
							ob.setColName("employee");
							ob.setDataName(empID);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("location");
							ob.setDataName(shiftID);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("mobile");
							ob.setDataName(mobile_no);
							insertData.add(ob);

							ob = new InsertDataTable();
							ob.setColName("day" + day.split("#")[1]);
							ob.setDataName(serviceID);
							insertData.add(ob);

							status = cbt.insertIntoTable("cps_roster_config",
									insertData, connectionSpace);
							insertData.clear();
						}
					}
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
	}

	// Delete
	@SuppressWarnings("unchecked")
	public String deleteEmpByTrashMethod() {
		System.out.println("MMMMMMMMMMMMM ");
		boolean sessionFlag = ValidateSession.checkSession();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (sessionFlag) {
			try {
				String[] trash = trashEmpId.split(",");
				if (trash.length > 1) {
					String[] temp = trashEmpId.split(",");
					HashMap<String, String> emp = new HashMap<String, String>();
					for (int i = 0; i < temp.length; i++) {
						emp.put(temp[i].split("#")[1], temp[i].split("#")[0]);
					}

					if (emp.size() > 0) {
						StringBuilder query = new StringBuilder();
						Set set = emp.entrySet();
						Iterator i = set.iterator();

						while (i.hasNext()) {
							query.delete(0, query.length());
							Map.Entry me = (Map.Entry) i.next();
							query
									.append("Delete from  cps_roster_config where employee='"
											+ (String) me.getValue() + "'");
							query.append(" and location='" + shiftId + "'");
							// query.append(" and shiftId='" + shiftId + "'");
							cbt.executeAllUpdateQuery(query.toString(),
									connectionSpace);

						}
					}

				} else {
					if (trashEmpId.contains("#")) {
						trashEmpId = trashEmpId.split("#")[0];
					}
					if (trashEmpId != null && !trashEmpId.equalsIgnoreCase("")) {
						StringBuilder query = new StringBuilder();
						query
								.append("Delete from  cps_roster_config where employee='"
										+ getTrashEmpId() + "'");
						if (wingIds != null && !wingIds.equalsIgnoreCase("")) {
							query.append(" and day" + wingIds + " ");
						}
						query.append(" and location='" + shiftId + "'");
						cbt.executeAllUpdateQuery(query.toString(),
								connectionSpace);

					}
				}

				addActionMessage("Employee Unmapped Successfully!!!");
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return SUCCESS;
			}
		} else {
			return LOGIN;
		}
	}

	public String deleteEmployee()
	{
		//wingIds 395kk01kk1
		String removeService;
		String empID=wingIds.split("kk")[0];
		String day=wingIds.split("kk")[1];
		String serviceID=wingIds.split("kk")[2];
		StringBuilder query=new StringBuilder();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		query.append("select day"+day+" from cps_roster_config where employee='"+empID+"' and location='"+shiftId+"'");
		List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		if (dataList != null&& dataList.size() >0) 
		{
			query.setLength(0);
			removeService = dataList.get(0).toString();
			String allservice=checkID(removeService,serviceID);
			query.append(" update cps_roster_config set day"+day+" = '"+allservice+"'  where employee='"+empID+"' and location='"+shiftId+"' ");
			cbt.executeAllUpdateQuery(query.toString(), connectionSpace);
		} 
		return "success";
	}
	
	public String checkID(String removeService,String serviceID)
	{
		String temp="";
		String[] testService=removeService.toString().split("#");
		System.out.println("serviceID"+serviceID+"  SSSSSSSSSS "+removeService.toString());
		if (testService != null)
		{
			for (int i = 0; i < testService.length; i++)
			{
				if(!testService[i].equalsIgnoreCase(""))
				{
					if(!testService[i].equalsIgnoreCase(serviceID))
					{
						temp = temp + "" + testService[i] + "" + "#";
					}
					else
					{
						
					}
				}
			}
		}
	return temp;
	}
	
	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public FileInputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream) {
		this.excelStream = excelStream;
	}

	public File getFeedbackExcel() {
		return feedbackExcel;
	}

	public void setFeedbackExcel(File feedbackExcel) {
		this.feedbackExcel = feedbackExcel;
	}

	public String getFeedbackExcelFileName() {
		return feedbackExcelFileName;
	}

	public void setFeedbackExcelFileName(String feedbackExcelFileName) {
		this.feedbackExcelFileName = feedbackExcelFileName;
	}

	public List<CPSRosterPojo> getExcelData() {
		return excelData;
	}

	public void setExcelData(List<CPSRosterPojo> excelData) {
		this.excelData = excelData;
	}

	public List<CPSRosterPojo> getGridFbDraftExcelModel() {
		return gridFbDraftExcelModel;
	}

	public void setGridFbDraftExcelModel(
			List<CPSRosterPojo> gridFbDraftExcelModel) {
		this.gridFbDraftExcelModel = gridFbDraftExcelModel;
	}

	public JSONArray getJsonList() {
		return jsonList;
	}

	public void setJsonList(JSONArray jsonList) {
		this.jsonList = jsonList;
	}

	public String getDeptFlag() {
		return deptFlag;
	}

	public void setDeptFlag(String deptFlag) {
		this.deptFlag = deptFlag;
	}

	public String getLocId() {
		return LocId;
	}

	public void setLocId(String locId) {
		LocId = locId;
	}

	public String getShiftId() {
		return shiftId;
	}

	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	public String getWingIds() {
		return wingIds;
	}

	public void setWingIds(String wingIds) {
		this.wingIds = wingIds;
	}

	public String getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public String getShiftID() {
		return shiftID;
	}

	public void setShiftID(String shiftID) {
		this.shiftID = shiftID;
	}

	public String getShiftEmpMapSubdept() {
		return shiftEmpMapSubdept;
	}

	public void setShiftEmpMapSubdept(String shiftEmpMapSubdept) {
		this.shiftEmpMapSubdept = shiftEmpMapSubdept;
	}

	public String getTrashEmpId() {
		return trashEmpId;
	}

	public void setTrashEmpId(String trashEmpId) {
		this.trashEmpId = trashEmpId;
	}

}