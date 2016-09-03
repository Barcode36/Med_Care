package com.Over2Cloud.ctrl.common.communication;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;







import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.SmsShowBean;
import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.common.excel.GenericReadBinaryExcel;
import com.Over2Cloud.common.excel.GenericReadExcel7;
import com.Over2Cloud.ctrl.communication.instantMsg.InstantMessageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UploadSmsExceldata extends ActionSupport {
	
	Map session = ActionContext.getContext().getSession();
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	static final Log log = LogFactory.getLog(UploadSmsExceldata.class);
	private int id = 1;
	private int sheetNo = 0;
	private String cellno = null;
	private File aSExcel;
	private String aSExcelContentType;
	private String aSExcelFileName;
	private Integer rows = 0;
	private Integer page = 0;
	private String sord;
	private String sidx;
	private Integer total = 0;
	private Integer records = 0;
	private List<SmsShowBean> gridSmsConfirmExcelData;
	private List<SmsShowBean> gridSSMSExcelData;
	private List<SmsShowBean> gridSMSExcelData;
	private List<SmsShowBean> gridsmsinvdataList;
	private List<SmsShowBean> gridsmsbdataList;
	private List<SmsShowBean> gridsmsdudataList;
	private List<SmsShowBean> gridsmsvdataList;
	
	private List<String> tempDuplicateList;
	private String scheduleMsg;
	private String scheduleLink;
	private String option = null;
	private String date = null;
	private String day = null;
	private String hours = null;
	private String month = null;
	private String yearly = null;
	private String msg = null;
	private String titles = null;
	private String messagebody=null;
	private String transmessagebody=null;
	
	List<SmsShowBean> invdataList = null;
	List<SmsShowBean> bdataList = null;
	List<SmsShowBean> dudataList = null;
	List<SmsShowBean> vdataList = null;
	Set<SmsShowBean> set = null;
	private String mobileNosss= null;
	private String rootnames= null;
	String checkMobile[] = { "9", "8", "7", "6" };
	private String select_id;
	private String duplicatesel_id;
	private InputStream fileInputStream;
	
	public String UploadExceldata() {
		
		String returnResult = ERROR;
		try {
			String user = session.get("uName").toString();
			if (user == null || user.equals("")) {
				session.clear();
				returnResult = LOGIN;

			} else {
				//activity log work starts here
		   	   //activity log work ends here
				// Check for null
				if (aSExcel == null) {
					addActionError("Select a file to upload!");
					addFieldError("ExcelUplaod", "Select a file to upload!");
					returnResult = SUCCESS;
					return returnResult;
				}
				if (aSExcel != null) {
					InputStream inputStream = new FileInputStream(aSExcel);

					if (aSExcelFileName.contains(".xlsx")) {
						GenericReadExcel7 GRE7 = new GenericReadExcel7();
						// call method for getting excel sheet
						XSSFSheet excelSheet = GRE7.readExcel(inputStream);

						gridSMSExcelData = new ArrayList<SmsShowBean>();
						for (int rowIndex = 1; rowIndex < excelSheet
								.getPhysicalNumberOfRows(); rowIndex++) {
							XSSFRow row = excelSheet.getRow(rowIndex);
							int cellNo = row.getLastCellNum();
							if (cellNo > 0) {
								SmsShowBean MsgObj = new SmsShowBean();
								MsgObj.setId(id);
								boolean flagc = false;
								for (int cellIndex = 0; cellIndex < cellNo; cellIndex++) {

									MsgObj.setUser(user);

									switch (cellIndex) {
									case 0:
										if (GRE7.readExcelSheet(row, cellIndex) == null
												|| GRE7.readExcelSheet(row,
														cellIndex)
														.equalsIgnoreCase("")) {
											MsgObj.setClient_name("NA");
										} else {
											MsgObj.setClient_name(GRE7
													.readExcelSheet(row,
															cellIndex));
										}
										break;
									case 1:
										if (!GRE7.readExcelSheet(row, cellIndex).equalsIgnoreCase(null)&& !GRE7.readExcelSheet(row,cellIndex).equalsIgnoreCase("")) {
											flagc = true;
											MsgObj.setContact_no(GRE7.readExcelSheet(row,cellIndex));
										}
										break;
									case 2:
										if (!GRE7.readExcelSheet(row, cellIndex).equalsIgnoreCase(null)&& !GRE7.readExcelSheet(row,cellIndex).equalsIgnoreCase("")) {
											MsgObj.setMsg(GRE7.readExcelSheet(row, cellIndex));
										}else{
											MsgObj.setMsg(GRE7.readExcelSheet(row, cellIndex));
										}
										break;
									}
									if(getMessagebody()!=null && !getMessagebody().equalsIgnoreCase("")){
										MsgObj.setMsg(getMessagebody());
										}if(getTransmessagebody()!=null && !getTransmessagebody().equalsIgnoreCase("")){
											MsgObj.setMsg(getTransmessagebody());
										}
									MsgObj.setDate(DateUtil
											.getCurrentDateUSFormat());
									MsgObj.setTime(DateUtil
											.getCurrentTime());
									id++;
								}
								MsgObj.setDate(DateUtil
										.getCurrentDateUSFormat());
								MsgObj.setTime(DateUtil.getCurrentTime());
								if (flagc) {
									gridSMSExcelData.add(MsgObj);
								} else {
									addActionError("Oops there is some problem!!!");
									return ERROR;
								}
							 } 
								if (gridSMSExcelData.size() > 0) {
									invdataList = new ArrayList<SmsShowBean>();
									bdataList = new ArrayList<SmsShowBean>();
									dudataList = new ArrayList<SmsShowBean>();
									vdataList = new ArrayList<SmsShowBean>();
									set = new HashSet<SmsShowBean>();
									ArrayList<String> tempDuplicateList = new ArrayList<String>();
									StringBuilder mobile = new StringBuilder();
									boolean isValid = false;
									for (SmsShowBean Kl : gridSMSExcelData) {
										if (Pattern.matches("[0-9]+", Kl.getContact_no())&& Kl.getContact_no().length() == 10&& Kl.getContact_no().charAt(0) == '9'|| Kl.getContact_no().charAt(0) == '8'|| Kl.getContact_no().charAt(0) == '7') {
											isValid = true;
										}else{
											SmsShowBean MsgObj = new SmsShowBean();
											MsgObj.setId(Kl.getId());
											MsgObj.setClient_name(Kl.getClient_name());
											MsgObj.setContact_no(Kl.getContact_no());
											MsgObj.setMsg(Kl.getMsg());
											MsgObj.setDate(Kl.getDate());
											MsgObj.setTime(Kl.getTime());
											invdataList.add(MsgObj);
										}
										if (isValid)
										{
											if (isMobileBlacklisted(Kl.getContact_no())) {
												SmsShowBean MsgObj = new SmsShowBean();
												MsgObj.setId(Kl.getId());
												MsgObj.setClient_name(Kl.getClient_name());
												MsgObj.setContact_no(Kl.getContact_no());
												MsgObj.setMsg(Kl.getMsg());
												MsgObj.setDate(Kl.getDate());
												MsgObj.setTime(Kl.getTime());
												bdataList.add(MsgObj);

											} else 
												if (
												tempDuplicateList.contains(Kl.getContact_no()))
												{
													SmsShowBean MsgObj = new SmsShowBean();
													MsgObj.setId(Kl.getId());
													MsgObj.setClient_name(Kl.getClient_name());
													MsgObj.setContact_no(Kl.getContact_no());
													MsgObj.setMsg(Kl.getMsg());
													MsgObj.setDate(Kl.getDate());
													MsgObj.setTime(Kl.getTime());
												    dudataList.add(MsgObj);
											} else {
												SmsShowBean MsgObj = new SmsShowBean();
												MsgObj.setId(Kl.getId());
												MsgObj.setClient_name(Kl.getClient_name());
												MsgObj.setContact_no(Kl.getContact_no());
												MsgObj.setMsg(Kl.getMsg());
												MsgObj.setDate(Kl.getDate());
												MsgObj.setTime(Kl.getTime());
												mobile.append(Kl.getContact_no()+",");
												vdataList.add(MsgObj);
												set.add(MsgObj);
												tempDuplicateList.add(Kl.getContact_no());

											}
										
											
										}
									}
									if (mobile!=null) 
									{
										mobileNosss = mobile.toString();
										
									}
									session.put("invdataList", invdataList);
									session.put("bdataList", bdataList);
									session.put("dudataList", dudataList);
									session.put("vdataList", vdataList);
									session.put("dudataList", dudataList);
								}
								// Eevntdao.addDetails(CubObj);
						}
						
						returnResult = SUCCESS;
					}

					else if (aSExcelFileName.contains(".xls")) {
						GenericReadBinaryExcel GRBE = new GenericReadBinaryExcel();
						gridSMSExcelData = new ArrayList<SmsShowBean>();
						HSSFWorkbook workBook = GRBE.multireadExcel(inputStream);
						for (int sheetNO = 0; sheetNO < workBook.getNumberOfSheets(); sheetNO++) {
							if (workBook.getSheetAt(sheetNO) != null) {
								HSSFSheet excelSheet = workBook.getSheetAt(sheetNO);
								for (int rowIndex = 1; rowIndex <= excelSheet
										.getLastRowNum(); rowIndex++) {
									System.out.println(">>>>>>>>>Row index"+excelSheet.getLastRowNum());
									SmsShowBean MsgObj = new SmsShowBean();
									MsgObj.setId(id);
									HSSFRow row = excelSheet.getRow(rowIndex);
									System.out.println(">>>>>>>>>Cell index"+row.getLastCellNum());
									int cellNo = row.getLastCellNum();
									if (cellNo > 0) {

										boolean flagc2 = false;
										for (int cellIndex = 0; cellIndex < cellNo; cellIndex++) {

											MsgObj.setUser(user);

											switch (cellIndex) {

											case 0:
												if (GRBE.readExcelSheet(row,cellIndex) == null|| GRBE.readExcelSheet(row,cellIndex).equalsIgnoreCase("")) {
													MsgObj.setClient_name("NA");
												} else {
													MsgObj.setClient_name(GRBE.readExcelSheet(row,cellIndex));
												}
												break;
											case 1:
												if (GRBE.readExcelSheet(row,cellIndex) == null|| GRBE.readExcelSheet(row,cellIndex).equalsIgnoreCase("")) {
													flagc2 = true;
													MsgObj.setContact_no("NA");
												} else {
													MsgObj.setContact_no(GRBE.readExcelSheet(row,cellIndex));
												}
												break;
											case 2:
												// for getting Id
												if (GRBE.readExcelSheet(row,cellIndex) == null|| GRBE.readExcelSheet(row,cellIndex).equalsIgnoreCase("")) {
													MsgObj.setMsg(GRBE.readExcelSheet(row,cellIndex));
												}else{
													Pattern pattern = Pattern.compile("<.+?>", Pattern.CASE_INSENSITIVE);
													// matching String from matcher
												    Matcher matcher = pattern.matcher(GRBE.readExcelSheet(row,cellIndex));
												    int temp=0;
												    int endIndex=0;
												    String msg="";
												    while(matcher.find())
												    {
												    	String str=GRBE.readExcelSheet(row,cellIndex).substring(temp, matcher.start());
												    	String strTemp=GRBE.readExcelSheet(row,cellIndex).substring(matcher.start(), matcher.end());
												    	msg = msg.concat(str+GRBE.readExcelSheet(row,Integer.parseInt(strTemp.substring(1, strTemp.indexOf(">")))));
												    	temp=matcher.end();
												    	endIndex=matcher.end();
												    }
											 	    if(msg== null || msg.equalsIgnoreCase("")){
													MsgObj.setMsg(GRBE.readExcelSheet(row,cellIndex));
											 	    }else{
											 	   	MsgObj.setMsg(msg);
											 	    }
												}
												break;
											}
										}
										if(getMessagebody()!=null && !getMessagebody().equalsIgnoreCase("")){
											MsgObj.setMsg(getMessagebody());
											}if(getTransmessagebody()!=null && !getTransmessagebody().equalsIgnoreCase("")){
												MsgObj.setMsg(getTransmessagebody());
											}
										MsgObj.setDate(DateUtil
												.getCurrentDateUSFormat());
										MsgObj.setTime(DateUtil
												.getCurrentTime());
										id++;
										//Eevntdao.addDetails(asoociateObjectBean
										// );
									}
									gridSMSExcelData.add(MsgObj);
								}

							}
							if (gridSMSExcelData.size() > 0) {
								invdataList = new ArrayList<SmsShowBean>();
								bdataList = new ArrayList<SmsShowBean>();
								dudataList = new ArrayList<SmsShowBean>();
								vdataList = new ArrayList<SmsShowBean>();
								set = new HashSet<SmsShowBean>();
							    tempDuplicateList = new ArrayList<String>();
								StringBuilder mobile = new StringBuilder();
								boolean isValid = false;
								for (SmsShowBean Kl : gridSMSExcelData) {
									if (Pattern.matches("[0-9]+", Kl.getContact_no())&& Kl.getContact_no().length() == 10&& Kl.getContact_no().charAt(0) == '9'|| Kl.getContact_no().charAt(0) == '8'|| Kl.getContact_no().charAt(0) == '7') {
										isValid = true;
									}else{
										SmsShowBean MsgObj = new SmsShowBean();
										MsgObj.setId(Kl.getId());
										MsgObj.setClient_name(Kl.getClient_name());
										MsgObj.setContact_no(Kl.getContact_no());
										MsgObj.setMsg(Kl.getMsg());
										MsgObj.setDate(Kl.getDate());
										MsgObj.setTime(Kl.getTime());
										invdataList.add(MsgObj);
									}
									if (isValid)
									{
										if (isMobileBlacklisted(Kl.getContact_no())) {
											
											SmsShowBean MsgObj = new SmsShowBean();
											MsgObj.setId(Kl.getId());
											MsgObj.setClient_name(Kl.getClient_name());
											MsgObj.setContact_no(Kl.getContact_no());
											MsgObj.setMsg(Kl.getMsg());
											MsgObj.setDate(Kl.getDate());
											MsgObj.setTime(Kl.getTime());
											bdataList.add(MsgObj);

										} else 
											if (
											tempDuplicateList.contains(Kl.getContact_no()))
											{
												SmsShowBean MsgObj = new SmsShowBean();
												MsgObj.setId(Kl.getId());
												MsgObj.setClient_name(Kl.getClient_name());
												MsgObj.setContact_no(Kl.getContact_no());
												MsgObj.setMsg(Kl.getMsg());
												MsgObj.setDate(Kl.getDate());
												MsgObj.setTime(Kl.getTime());
											    dudataList.add(MsgObj);
										} else {
											SmsShowBean MsgObj = new SmsShowBean();
											MsgObj.setId(Kl.getId());
											MsgObj.setClient_name(Kl.getClient_name());
											MsgObj.setContact_no(Kl.getContact_no());
											MsgObj.setMsg(Kl.getMsg());
											MsgObj.setDate(Kl.getDate());
											MsgObj.setTime(Kl.getTime());
											mobile.append(Kl.getContact_no()+",");
											vdataList.add(MsgObj);
											set.add(MsgObj);
											tempDuplicateList.add(Kl.getContact_no());

										}
									
										
									}
								}
								if (mobile!=null) 
								{
									mobileNosss = mobile.toString();
									
								}
								session.put("invdataList", invdataList);
								session.put("bdataList", bdataList);
								session.put("dudataList", dudataList);
								session.put("vdataList", vdataList);
								session.put("dudataList", dudataList);
							}
						}
						returnResult = SUCCESS;
					}

				}
			}

		} catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in WORK FORCE method UploadExceldata of class "+getClass(), e);
			addActionError("Oops there is some problem!!!");
			return ERROR;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String ShowConformExceldata() {
		
		String returnResult = ERROR;
		try {
			String user = session.get("uName").toString();
	
			if (user == null || user.equals("")) {
				session.clear();
				returnResult = LOGIN;

			} else {
				invdataList = (List<SmsShowBean>) session.get("invdataList");
				bdataList = (List<SmsShowBean>) session.get("bdataList");
				dudataList = (List<SmsShowBean>) session.get("dudataList");
				vdataList = (List<SmsShowBean>) session.get("vdataList");
				dudataList = (List<SmsShowBean>) session.get("dudataList");
				if(dudataList != null && dudataList.size() > 0){
					 setRecords(dudataList.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
						setGridsmsdudataList(dudataList.subList(from,
								to));

						// calculate the total pages for the query

						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
				 }
				 if(invdataList != null && invdataList.size() > 0){
					 setRecords(invdataList.size());
						int to = (getRows() * getPage());
						int from = to - getRows();
						if (to > getRecords())
							to = getRecords();
						setGridsmsinvdataList(invdataList.subList(from,
								to));

						// calculate the total pages for the query

						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
				 }
                 if(bdataList != null && bdataList.size() > 0){
                	 setRecords(bdataList.size());
 					int to = (getRows() * getPage());
 					int from = to - getRows();
 					if (to > getRecords())
 						to = getRecords();
 					setGridsmsbdataList(bdataList.subList(from,
 							to));

 					// calculate the total pages for the query

 					setTotal((int) Math.ceil((double) getRecords()
 							/ (double) getRows()));
				 }
                 if(dudataList != null && dudataList.size() > 0){
                	 setRecords(dudataList.size());
 					int to = (getRows() * getPage());
 					int from = to - getRows();
 					if (to > getRecords())
 						to = getRecords();
 					setGridsmsdudataList(dudataList.subList(from,
 							to));

 					// calculate the total pages for the query

 					setTotal((int) Math.ceil((double) getRecords()
 							/ (double) getRows()));
				 }
                 if(vdataList != null && vdataList.size() > 0){
                	 setRecords(vdataList.size());
 					int to = (getRows() * getPage());
 					int from = to - getRows();
 					if (to > getRecords())
 						to = getRecords();
 					setGridsmsvdataList(vdataList.subList(from,
 							to));

 					// calculate the total pages for the query

 					setTotal((int) Math.ceil((double) getRecords()
 							/ (double) getRows()));
				 }
				returnResult = SUCCESS;

			}
		} catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in WORK FORCE method ShowConformExceldata of class "+getClass(), e);
			addActionError("Oops there is some problem!!!");
			return ERROR;
		}

		return returnResult;
	}

	public String UploadTextFiledata() {
		String returnResult = ERROR;
		try {
			String user = session.get("uName").toString();

			if (user == null || user.equals("")) {
				session.clear();
				returnResult = LOGIN;

			} else {
				//activity log work starts here
				CommonforClass eventDao = new CommanOper();
		   	   //activity log work ends here
				// Check for null
				if (aSExcel == null) {
					addActionError("Select a file to upload!");
					addFieldError("ExcelUplaod", "Select a file to upload");
					returnResult = SUCCESS;
					return returnResult;
				}

				if (aSExcel != null) {
					InputStream inputStream = new FileInputStream(aSExcel);
					if (aSExcelFileName.contains(".txt")) {

						DataInputStream in = new DataInputStream(inputStream);
						// BufferReader ..
						BufferedReader br = new BufferedReader(
								new InputStreamReader(in));

						try {
							String strLine;
							String[] DataArray = null;
							gridSMSExcelData = new ArrayList<SmsShowBean>();
							// Read File Line By Line
							while ((strLine = br.readLine()) != null) {
								if (strLine != null
										&& !strLine.equalsIgnoreCase("")) {
									// System.out.println("br.readLine()"+br.
									// readLine());
									DataArray = strLine.split(",");
									SmsShowBean MsgObj = new SmsShowBean();
									for (int i = 0; i < DataArray.length; i++) {

										if (DataArray.length == 2) {
											if ((!(DataArray[0].equals("") && DataArray[0]
													.length() < 10)
													|| DataArray[0].length() > 10 || !(DataArray[0]
													.startsWith(checkMobile[0])
													|| DataArray[0]
															.startsWith(checkMobile[1])
													|| DataArray[0]
															.startsWith(checkMobile[2]) || DataArray[1]
													.startsWith(checkMobile[3])))) {
												MsgObj
														.setContact_no(DataArray[0]);
											}
											MsgObj.setMsg(DataArray[1]);
										} else {
											if ((!(DataArray[1].equals("") && DataArray[1]
													.length() < 10)
													|| DataArray[1].length() > 10 || !(DataArray[1]
													.startsWith(checkMobile[0])
													|| DataArray[0]
															.startsWith(checkMobile[1])
													|| DataArray[1]
															.startsWith(checkMobile[2]) || DataArray[1]
													.startsWith(checkMobile[3])))) {
												MsgObj
														.setContact_no(DataArray[1]);
											}
											MsgObj.setGrp_name(DataArray[0]);
											MsgObj.setMsg(DataArray[2]);
										}
									}
									MsgObj.setId(id);
									MsgObj.setUser(user);
									MsgObj.setDate(DateUtil
											.getCurrentDateUSFormat());
									MsgObj.setTime(DateUtil.getCurrentTime());
									id++;
									gridSMSExcelData.add(MsgObj);

									// Print the content on the console
								}
							}
							if (gridSMSExcelData.size() > 0) {
								invdataList = new ArrayList<SmsShowBean>();
								bdataList = new ArrayList<SmsShowBean>();
								dudataList = new ArrayList<SmsShowBean>();
								vdataList = new ArrayList<SmsShowBean>();
								set = new HashSet<SmsShowBean>();
							    tempDuplicateList = new ArrayList<String>();
								StringBuilder mobile = new StringBuilder();
								boolean isValid = false;
								for (SmsShowBean Kl : gridSMSExcelData) {
									if (Pattern.matches("[0-9]+", Kl.getContact_no())&& Kl.getContact_no().length() == 10&& Kl.getContact_no().charAt(0) == '9'|| Kl.getContact_no().charAt(0) == '8'|| Kl.getContact_no().charAt(0) == '7') {
										isValid = true;
									}else{
										SmsShowBean MsgObj = new SmsShowBean();
										MsgObj.setId(Kl.getId());
										MsgObj.setClient_name(Kl.getClient_name());
										MsgObj.setContact_no(Kl.getContact_no());
										MsgObj.setMsg(Kl.getMsg());
										MsgObj.setDate(Kl.getDate());
										MsgObj.setTime(Kl.getTime());
										invdataList.add(MsgObj);
									}
									if (isValid)
									{
										if (isMobileBlacklisted(Kl.getContact_no())) {
											
											SmsShowBean MsgObj = new SmsShowBean();
											MsgObj.setId(Kl.getId());
											MsgObj.setClient_name(Kl.getClient_name());
											MsgObj.setContact_no(Kl.getContact_no());
											MsgObj.setMsg(Kl.getMsg());
											MsgObj.setDate(Kl.getDate());
											MsgObj.setTime(Kl.getTime());
											bdataList.add(MsgObj);

										} else 
											if (
											tempDuplicateList.contains(Kl.getContact_no()))
											{
												SmsShowBean MsgObj = new SmsShowBean();
												MsgObj.setId(Kl.getId());
												MsgObj.setClient_name(Kl.getClient_name());
												MsgObj.setContact_no(Kl.getContact_no());
												MsgObj.setMsg(Kl.getMsg());
												MsgObj.setDate(Kl.getDate());
												MsgObj.setTime(Kl.getTime());
											    dudataList.add(MsgObj);
										} else {
											SmsShowBean MsgObj = new SmsShowBean();
											MsgObj.setId(Kl.getId());
											MsgObj.setClient_name(Kl.getClient_name());
											MsgObj.setContact_no(Kl.getContact_no());
											MsgObj.setMsg(Kl.getMsg());
											MsgObj.setDate(Kl.getDate());
											MsgObj.setTime(Kl.getTime());
											mobile.append(Kl.getContact_no()+",");
											vdataList.add(MsgObj);
											set.add(MsgObj);
											tempDuplicateList.add(Kl.getContact_no());

										}
									
										
									}
								}
								if (mobile!=null) 
								{
									mobileNosss = mobile.toString();
									
								}
								session.put("invdataList", invdataList);
								session.put("bdataList", bdataList);
								session.put("dudataList", dudataList);
								session.put("vdataList", vdataList);
								session.put("dudataList", dudataList);
							}

							addActionMessage("SMS Data Added Successfully Now You Happy");

							// Close the input stream
							in.close();
						} catch (Exception e) {// Catch exception if any
							log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in WORK FORCE method UploadTextFiledata of class "+getClass(), e);
							addActionError("Oops there is some problem!!!");
							return ERROR;
						}
					}
                    System.out.println(">>>>>>>>>>>>>"+gridSMSExcelData.size());
					returnResult = SUCCESS;
				}

			}

		} catch (Exception e) {
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in WORK FORCE method UploadTextFiledata of class "+getClass(), e);
			addActionError("Oops there is some problem!!!");
			return ERROR;
		}
		return returnResult;

	}

	@SuppressWarnings("unchecked")
	public String SaveSMS() {
		String returnResult = ERROR;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try{
			String user=  session.get("uName").toString();
			if(user==null || user.equalsIgnoreCase("") )
			{
				session.clear();
				addActionMessage("Youe Session has been Finished!");
				returnResult = LOGIN;
			}
	    	else{
	    		     InsertDataTable ob = null;
	    		     String[] selectedvalidIdArray=null;
	    		     String[] selectedduplicatIdArray=null;
	    		      if(select_id!=null && !select_id.equalsIgnoreCase("") && duplicatesel_id!=null && !duplicatesel_id.equalsIgnoreCase("")) {
	    		    	    vdataList = (List<SmsShowBean>) session.get("vdataList");
		    		    	dudataList = (List<SmsShowBean>) session.get("dudataList");
		    		    	session.remove("vdataList");
		    		    	session.remove("dudataList");
		    		    	selectedvalidIdArray=select_id.trim().split(",");
		    		    	selectedduplicatIdArray=duplicatesel_id.trim().split(",");
		    		    
	    		       }
	    		       else if (select_id!=null && !select_id.equalsIgnoreCase("")) {
	    		    	 vdataList = (List<SmsShowBean>) session.get("vdataList");
	    		    	 session.remove("vdataList");
	    		    	 selectedvalidIdArray=select_id.trim().split(",");
	    		      }
	    		      else if(duplicatesel_id!=null && !duplicatesel_id.equalsIgnoreCase("")){
	    		    	 dudataList = (List<SmsShowBean>) session.get("dudataList");
	    		    	 session.remove("dudataList");
	    		    	 selectedduplicatIdArray=duplicatesel_id.trim().split(",");
	    		      }
	    				 boolean msgflag = false;
	    				if(vdataList!=null)
	    				{
		    					 for (Iterator SMSdata = vdataList.iterator(); SMSdata.hasNext();) {
		    					  SmsShowBean smsObjS = (SmsShowBean) SMSdata.next();
		    					  
		    						  for (int i = 0; i < selectedvalidIdArray.length; i++) {
				    				      if(selectedvalidIdArray[i].contains(Integer.toString(smsObjS.getId()))) {
				    				    	  List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
				    				    	    ob = new InsertDataTable();
					   	    					
					   	    					ob = new InsertDataTable();
					   							ob.setColName("msisdn");
					   							ob.setDataName(smsObjS.getContact_no());
					   							insertData.add(ob);
					   							
					   							ob = new InsertDataTable();
					   							ob.setColName("msg");
					   							ob.setDataName(smsObjS.getMsg());
					   							insertData.add(ob);

					   							ob = new InsertDataTable();
					   							ob.setColName("client_id");
					   							ob.setDataName("dredst22");
					   							insertData.add(ob);
					   							
					   							ob = new InsertDataTable();
					   							ob.setColName("status");
					   							ob.setDataName(3);
					   							insertData.add(ob);
					   							
					   							ob = new InsertDataTable();
					   							ob.setColName("msg_date");
					   							ob.setDataName(DateUtil.getCurrentDateUSFormat());
					   							insertData.add(ob);

					   							ob = new InsertDataTable();
					   							ob.setColName("msg_time");
					   							ob.setDataName(DateUtil.getCurrentTime());
					   							insertData.add(ob);
					   							
					   							ob = new InsertDataTable();
					   							ob.setColName("msgType");
					   							ob.setDataName("Instant");
					   							insertData.add(ob);
					   							msgflag = cbt.insertIntoTable("msg_stats", insertData,connectionSpace);
				    				          }	    
				    				       }
		    				        }
	    				}
	    				if(dudataList!=null)
	    				{
	    					 for (Iterator SMSdata = dudataList.iterator(); SMSdata.hasNext();) {
	    					  SmsShowBean smsObjS = (SmsShowBean) SMSdata.next();
	    						  for (int i = 0; i < selectedduplicatIdArray.length; i++) {
			    				      if(selectedduplicatIdArray[i].contains(Integer.toString(smsObjS.getId()))) {
			    				    	  List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			    				    	    ob = new InsertDataTable();
				   	    					ob = new InsertDataTable();
				   							ob.setColName("msisdn");
				   							ob.setDataName(smsObjS.getContact_no());
				   							insertData.add(ob);
				   							
				   							ob = new InsertDataTable();
				   							ob.setColName("msg");
				   							ob.setDataName(smsObjS.getMsg());
				   							insertData.add(ob);

				   							ob = new InsertDataTable();
				   							ob.setColName("client_id");
				   							ob.setDataName("dredst22");
				   							insertData.add(ob);
				   							
				   							ob = new InsertDataTable();
				   							ob.setColName("status");
				   							ob.setDataName(3);
				   							insertData.add(ob);
				   							
				   							ob = new InsertDataTable();
				   							ob.setColName("msg_date");
				   							ob.setDataName(DateUtil.getCurrentDateUSFormat());
				   							insertData.add(ob);

				   							ob = new InsertDataTable();
				   							ob.setColName("msg_time");
				   							ob.setDataName(DateUtil.getCurrentTime());
				   							insertData.add(ob);
				   							
				   							ob = new InsertDataTable();
				   							ob.setColName("msgType");
				   							ob.setDataName("Instant");
				   							insertData.add(ob);
				   							msgflag = cbt.insertIntoTable("msg_stats", insertData,connectionSpace);
			    						 }	    
			    				       }
	    				          }
	    				}
	    				if(msgflag){
	    					addActionMessage("Data Added Successfully.");
	    				}else{
	    					addActionMessage("You Must be Upload Data Again.");
	    				}
	    		    }
			       
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in WORK FORCE method updateAssociatStatus of class "+getClass(), e);
			addActionError("Oops there is some problem!!!");
			return ERROR;
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private boolean isMobileBlacklisted(String mobileNo2) {
		boolean flag = false;
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			SessionFactory connectionSpace = (SessionFactory) session
					.get("connectionSpace");
			String query = "SELECT mobileno FROM blacklist";
			List data = cbt.executeAllSelectQuery(query, connectionSpace);

			if (data != null && data.size() > 0) {
				for (Iterator iterator = data.iterator(); iterator.hasNext();) {
					Object object = (Object) iterator.next();
					if (object != null) {
						if (object.toString().equalsIgnoreCase(mobileNo2)) {
							flag = true;
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return flag;
	}
	
	public String dowloadformate(){
		String retunrString =ERROR;
		try{
			ServletContext servletContext=ServletActionContext.getServletContext();
			fileInputStream = servletContext.getResourceAsStream("file.txt");
			retunrString=SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return retunrString;
	}
	public String dowloadexcelformate(){
		String retunrString =ERROR;
		try{
			ServletContext servletContext=ServletActionContext.getServletContext();
			fileInputStream = servletContext.getResourceAsStream("xlfile.xls");
			retunrString=SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return retunrString;
	}
	
	public String dowloadblacklistexcelformate(){
		String retunrString =ERROR;
		try{
			ServletContext servletContext=ServletActionContext.getServletContext();
			fileInputStream = servletContext.getResourceAsStream("blackList.xlsx");
			retunrString=SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return retunrString;
	}
	
	public String 	dowloadblacklisttextformate(){
		String retunrString =ERROR;
		try{
			ServletContext servletContext=ServletActionContext.getServletContext();
			fileInputStream = servletContext.getResourceAsStream("blacklist.txt");
			retunrString=SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return retunrString;
	}
	
	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public List<SmsShowBean> getGridSmsConfirmExcelData() {
		return gridSmsConfirmExcelData;
	}
	public String getSelect_id() {
		return select_id;
	}
	public void setSelect_id(String select_id) {
		this.select_id = select_id;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getRecords() {
		return records;
	}
	public void setRecords(Integer records) {
		this.records = records;
	}
	public int getSheetNo() {
		return sheetNo;
	}
	public void setSheetNo(int sheetNo) {
		this.sheetNo = sheetNo;
	}
	public String getCellno() {
		return cellno;
	}
	public void setCellno(String cellno) {
		this.cellno = cellno;
	}

	public File getASExcel() {
		return aSExcel;
	}
	public void setASExcel(File excel) {
		aSExcel = excel;
	}
	public String getASExcelContentType() {
		return aSExcelContentType;
	}
	public void setASExcelContentType(String excelContentType) {
		aSExcelContentType = excelContentType;
	}
	public String getASExcelFileName() {
		return aSExcelFileName;
	}
	public String getYearly() {
		return yearly;
	}
	public void setYearly(String yearly) {
		this.yearly = yearly;
	}
	public List<SmsShowBean> getGridSSMSExcelData() {
		return gridSSMSExcelData;
	}
	public void setGridSSMSExcelData(List<SmsShowBean> gridSSMSExcelData) {
		this.gridSSMSExcelData = gridSSMSExcelData;
	}
	public List<SmsShowBean> getGridSMSExcelData() {
		return gridSMSExcelData;
	}
	public void setGridSMSExcelData(List<SmsShowBean> gridSMSExcelData) {
		this.gridSMSExcelData = gridSMSExcelData;
	}
	public void setGridSmsConfirmExcelData(
			List<SmsShowBean> gridSmsConfirmExcelData) {
		this.gridSmsConfirmExcelData = gridSmsConfirmExcelData;
	}
	public void setASExcelFileName(String excelFileName) {
		aSExcelFileName = excelFileName;
	}
	public String getScheduleMsg() {
		return scheduleMsg;
	}
	public void setScheduleMsg(String scheduleMsg) {
		this.scheduleMsg = scheduleMsg;
	}
	public String getScheduleLink() {
		return scheduleLink;
	}
	public void setScheduleLink(String scheduleLink) {
		this.scheduleLink = scheduleLink;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTitles() {
		return titles;
	}
	public void setTitles(String titles) {
		this.titles = titles;
	}
	public String getMobileNosss() {
		return mobileNosss;
	}

	public void setMobileNosss(String mobileNosss) {
		this.mobileNosss = mobileNosss;
	}

	public List<SmsShowBean> getGridsmsinvdataList() {
		return gridsmsinvdataList;
	}

	public void setGridsmsinvdataList(List<SmsShowBean> gridsmsinvdataList) {
		this.gridsmsinvdataList = gridsmsinvdataList;
	}

	public List<SmsShowBean> getGridsmsbdataList() {
		return gridsmsbdataList;
	}

	public void setGridsmsbdataList(List<SmsShowBean> gridsmsbdataList) {
		this.gridsmsbdataList = gridsmsbdataList;
	}

	public List<SmsShowBean> getGridsmsdudataList() {
		return gridsmsdudataList;
	}

	public void setGridsmsdudataList(List<SmsShowBean> gridsmsdudataList) {
		this.gridsmsdudataList = gridsmsdudataList;
	}

	public List<SmsShowBean> getGridsmsvdataList() {
		return gridsmsvdataList;
	}

	public void setGridsmsvdataList(List<SmsShowBean> gridsmsvdataList) {
		this.gridsmsvdataList = gridsmsvdataList;
	}

	public String getDuplicatesel_id() {
		return duplicatesel_id;
	}

	public void setDuplicatesel_id(String duplicatesel_id) {
		this.duplicatesel_id = duplicatesel_id;
	}

	public String getRootnames() {
		return rootnames;
	}

	public void setRootnames(String rootname) {
		this.rootnames = rootname;
	}

	public String getMessagebody() {
		return messagebody;
	}

	
	public String getTransmessagebody() {
		return transmessagebody;
	}

	public void setTransmessagebody(String transmessagebody) {
		this.transmessagebody = transmessagebody;
	}

	public void setMessagebody(String messagebody) {
		this.messagebody = messagebody;
	}

	
	
	
}
