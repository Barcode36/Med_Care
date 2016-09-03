package com.Over2Cloud.ctrl.leaveManagement;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.common.mail.GenericMailSender;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourHelper;

public class HrVirtualNoDataReceiver

{

	private final static CommonOperInterface cbt = new CommonConFactory()
			.createInterface();
	private static final MsgMailCommunication MMC = new MsgMailCommunication();
	private static List<AttendancePojo> holidayList = new ArrayList<AttendancePojo>();
	private static List<AttendancePojo> HRKeywordList = new ArrayList<AttendancePojo>();
	private double empLeaveBalance;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	public String[] getClientAccountData(String keyWord) {
		String AccountDetails = null;
		String[] keyWords = null;

		try {
			// Creating URL
			String url = "http://95.172.28.178:8080/VMMServer/virtualPoll?Keyword="
					+ keyWord;
			if (url != null) {
				URL codedURL = new URL(url.toString());
				HttpURLConnection HURLC = (HttpURLConnection) codedURL
						.openConnection();
				HURLC.setRequestMethod("GET");
				HURLC.setDoOutput(true);
				HURLC.setDoInput(true);
				HURLC.setUseCaches(false);
				HURLC.connect();
				AccountDetails = HURLC.getHeaderField("1708");
				HURLC.disconnect();

				System.out.println(AccountDetails);
				if (AccountDetails != null) {
					keyWords = AccountDetails.split("_");

				}
			}
		} catch (Exception E) {
			E.printStackTrace();
		}
		return keyWords;
	}

	@SuppressWarnings("null")
	public void getVirtualNoData(SessionFactory connection) {
		try {

			String arrLr[] = getClientAccountData("ds");//{"9650073051","LC 01:12*test","2014-10-08"};
														

			String requestTime = DateUtil.getCurrentTimeHourMin();

			// Getting DATA Base Session
			// Creating Object Of LeaveHelper Class
			LeaveHelper LH = new LeaveHelper();
			// Creating Object Of LeaveRequest Class

			LeaveRequest LR = new LeaveRequest();
			String keyword = "NA";
			String comment = "NA";
			if (arrLr != null) {

				if (arrLr[1].trim().indexOf("*") > 0) {
					keyword = arrLr[1].substring(0,
							arrLr[1].trim().indexOf("*")).toUpperCase();

					comment = arrLr[1]
							.substring(arrLr[1].trim().indexOf("*") + 1,
									arrLr[1].length());
				} else {
					keyword = arrLr[1].trim().toUpperCase();
				}

				// Getting Employee Records...
				String[] empdata = LH.getEmpDetailsByMobileNo("HR", arrLr[0],
						connection);
				String userName = "NA";

				String dataemp = LH.getContactIdforEmp("HR", connection,
						arrLr[0]);
				String mailBody = "NA";
				String mailBody1 = "NA";
				String revertSMS = "NA";
				StringBuilder revertSMS1 = null;
				String leaveType = "NA";
				String errorRevertString = "";
				String[] tdate2 = null;
				String tTime = "NA";
				String hour = "";
				String addHour = "NA";
				String odate = DateUtil.getCurrentDateIndianFormat();
				String ftime = requestTime;
				String ttime = "NA";
				String fdate = DateUtil.getCurrentDateUSFormat();
				String tdate = "NA";

				String hour1 = "NA";
				String _hour;
				String _min;
				String lodgeId = "115,140,142,126";
				if (keyword.trim().length() == 2) {
					// HR, HC, LB
					if (keyword.trim().equalsIgnoreCase("LB")) {
						System.out.println("keyword LB" + keyword);
						if (empdata != null) {
							empLeaveBalance = LR.getEmpLeaveBalance(connection,
									dataemp);
							revertSMS = "Dear " +empdata[1]+ ", your remaining leaves as on "
									+ DateUtil.getCurrentDateIndianFormat()
									+ " is " + empLeaveBalance
									+ ". Contact Himanshi Malik for more details.";
							System.out.println("revertSMS @@@@@@@@@@@"
									+ revertSMS);
							//Dear <name(60)>, your remaining leaves as on <Date(20)> is <balance leaves(20)>. Contact <HR Name(40)> for more details.
							MMC.addMessageHR(empdata[1], "", empdata[6],
									revertSMS, "", "Pending", "0", "HR",
									connection);
							
							insertIntoTable(empdata,connection,arrLr,keyword,revertSMS,revertSMS1,comment,leaveType,mailBody,mailBody1);
							
							
							
						} else {
							revertSMS = "Hi, you seems to perform this activity from an Unregistered Mobile No. Please use Registered No. or Contact 9250400311. ";
							System.out.println("revertSMS  " + revertSMS);
							MMC.addMessageHR("", "", arrLr[0], revertSMS, "",
									"Pending", "0", "", connection);
						}

					} else if (keyword.trim().equalsIgnoreCase("HC")) {
						
						
						String defaultHoliday = "NA:NA.";
						
						
						System.out.println("keyword HC" + keyword);
						holidayList = holidayCalender(connection);
						System.out.println("holidayList  " + holidayList.size());
						// Comment for Mob No Details in pojo
						if (holidayList != null && holidayList.size() > 0) 
						{
							if (empdata != null) 
							{
								revertSMS1 = new StringBuilder(
										"Remaining Holiday Calendar for "
												+ DateUtil.getCurretnYear()
												+ ": " );
								int commaCounter = 0;
								for (AttendancePojo pojoObj : holidayList)
								{
									if (commaCounter == 0)
										revertSMS1.append(pojoObj.getHolidayname()
												+ ":"
												+ pojoObj.getFdate() );
									else
										revertSMS1.append("." 
												+ pojoObj.getHolidayname() + ":"
												+ pojoObj.getFdate() );
									commaCounter++;
								}
								revertSMS1.append(".");
								
								for(int loopCounter = (holidayList.size()+1) ; loopCounter <11 ; loopCounter++){
									revertSMS1.append(defaultHoliday);	
									
								}
								System.out.println("revertSMS1  " + revertSMS1);
								MMC.addMessageHR(empdata[1], "", empdata[6],
										revertSMS1.toString(), "", "Pending",
										"0", "HR", connection);
							
								insertIntoTable(empdata,connection,arrLr,keyword,revertSMS,revertSMS1,comment,leaveType,mailBody,mailBody1);
								
							} else {
								revertSMS = "Hi, you seems to perform this activity from an Unregistered Mobile No. Please use Registered No. or Contact 9250400311. ";
								System.out.println("revertSMS  " + revertSMS);
								MMC.addMessageHR("", "", arrLr[0], revertSMS,"", "Pending", "0", "", connection);
							}
						}
						if ( holidayList.size()==0) 
						{
							revertSMS = "Hi, There is no holiday available ";
							System.out.println("revertSMS  " + revertSMS);
							MMC.addMessageHR(empdata[1], "", empdata[6],revertSMS.toString(), "", "Pending","0", "HR", connection);
						
						}

					}

				

					else if (keyword.trim().equalsIgnoreCase("HR")) {

						HRKeywordList = hrAllKeywords(connection);
						// Comment for Mob No Details in pojo

						if (HRKeywordList != null && HRKeywordList.size() > 0) {
							if (empdata != null) {
								revertSMS1 = new StringBuilder(
										"Hi, the requested HR Keywords are: Leave Balance: LB, Holiday Calendar: HC, ");
								int commaCounter = 0;
								for (AttendancePojo pojoObj : HRKeywordList) {
									if (pojoObj.getKeyword().equals("LB")
											|| pojoObj.getKeyword()
													.equals("HC"))
										continue;

									if (commaCounter == 0)
										revertSMS1.append(pojoObj.getDescription()
												+ ": "
												+ pojoObj.getKeyword()
												);
									else
										revertSMS1.append(", "
												+ pojoObj.getDescription() + ": "
												+ pojoObj.getKeyword()
								);
									commaCounter++;
								}
								
								
								
								
								revertSMS1.append(".");
								System.out.println("revertSMS1 "
										+ revertSMS1);

								MMC.addMessageHR(empdata[1], "", empdata[6],
										revertSMS1.toString(), "", "Pending",
										"0", "HR", connection);
								
								insertIntoTable(empdata,connection,arrLr,keyword,revertSMS,revertSMS1,comment,leaveType,mailBody,mailBody1);
							}
						} 
						else {
							revertSMS = "Hi, you seems to perform this activity from an Unregistered Mobile No. Please use Registered No. or Contact 9250400311. ";
							System.out.println("revertSMS "
									+ revertSMS);
							MMC.addMessageHR("", "", arrLr[0], revertSMS, "",
									"Pending", "0", "", connection);
						}

					}
				} else {
					
					if ((arrLr[1].toUpperCase().startsWith("LC")
							|| arrLr[1].toUpperCase().startsWith("EG") || arrLr[1]
							.toUpperCase().startsWith("LR"))
							&& arrLr[1].charAt(2) == ' '
							&& arrLr[1].indexOf("*") == 8) {
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>if condition true");

						if (empdata != null) {

							List<String> defaultMailID = new ArrayList<String>();

							//defaultMailID.add("sumitib@dreamsol.biz#Sumiti");
							 defaultMailID.add("shailendram@dreamsol.biz#Shailendra Mishra");
					         defaultMailID.add("prabhatk@dreamsol.biz#Mr. Prabhat");
							 defaultMailID.add("jagpreetm@dreamsol.biz#Ms. Jagpreet Kaur");
							 defaultMailID.add("himanshim@dreamsol.biz#Himanshi Malik");
							for (String email : defaultMailID) {

								String[] emailArr = email.split("#");
								System.out.println("emailArrnnnnnnnnnnnn "
										+ emailArr[0]);
								int maxid = new createTable().getMaxId(
										"leave_request", connection);
								empLeaveBalance = LR.getEmpLeaveBalance(connection,
										dataemp);

								/**
								 * ********************* LC
								 * ****************************************
								 */
								if (keyword.trim().startsWith("LC")
										&& keyword.trim().length() > 2) {////System.out.println("LCCCC");

									// Check For Comment
									//System.out.println("keyword cccccccc" +keyword);
									if (keyword.trim().indexOf("*") > 0)
									{
									//System.out.println("keyword" +keyword.trim().indexOf("*"));
									String subKeyword = keyword.trim().substring(0,
									keyword.trim().indexOf("*"));
									String subcomment = keyword.trim().substring(
									keyword.trim().indexOf("*")+1,
									keyword.trim().length());
									;

									hour = subKeyword.substring(2, subKeyword
									.length());

									if (hour.trim().indexOf(":") >= 0) 
									{
									_hour = hour.substring(0, hour.trim()
									.indexOf(":"));
									_min = hour.substring(hour.trim()
									.indexOf(":"), hour.trim().length());
									int lvReq = Integer.parseInt(hour.trim());
									if (lvReq < 24)
									{

									leaveType = "Hours";
									 keyword="LC";
									ftime = "09:30";
									// reqLeaveHour
									tTime = DateUtil.addTwoTime(ftime,""+_hour+""+_min+"");
									//System.out.println("tTinnnnnnnnnnnnme" +tTime);
									GenericMailSender mail=new GenericMailSender();
									userName = (Cryptography.decrypt(
											empdata[7],
											DES_ENCRYPTION_KEY));
									if(empdata[1]!= null)
									{
									 mailBody1=getMailBody1(emailArr[1],odate,ftime, tTime,
									maxid, empdata[1],"IN-1",comment ,"Casual",empLeaveBalance,"LC",DateUtil.getCurrentTimeHourMin(),"");
									new MsgMailCommunication() .addMailHR (emailArr[1],"",emailArr[0],"Leave Request by "
									+empdata[1]+"  For Late Coming",mailBody1,"", "Pending",
									"0","","HR",connection);

									revertSMS = "Dear "+ empdata[1]+ ",  Thanks! Your request for Late Coming has been registered successfully. Contact Himanshi Malik for more details.";
									}
									
                                   System.out.println("revertSMS >>>>>  " +revertSMS);
									MMC.addMessageHR(empdata[1], "", empdata[6],
									revertSMS, "", "Pending", "0", "HR",connection);
									}
									else 
									{
									revertSMS = "Hi, you seems to perform this activity from an Unregistered Mobile No. Please use Registered No. or Contact 9250400311. ";
									System.out.println("revertSMS  "  +revertSMS);
									new CommunicationHelper().addMessage("","",arrLr[0],revertSMS,"","Pending","0","",connection);
									}
									}
									else 
									{
									// LC 5
									// LC :30
									// LC 5 30
									// LC 5.30 5_30 other case

										revertSMS =	"You have sent wrong keyword. Kindly Send Again. Correct Format is "+keyword+" HH:MM*Reason";
									// revertSMS = "You have sent wrong keyword. Kindly Send Again. Correct Format is LC <HH:MM>*Comment";
									MMC.addMessageHR(empdata[1], "", empdata[6],revertSMS, "", "Pending", "0", "HR",connection);

									}

									} 
									else 
									{
									String subKeyword = keyword.trim().substring(0,
									keyword.trim().length());
									String subcomment = "NA";

									hour = subKeyword.substring(2, subKeyword
									.length());

									if (hour.trim().indexOf(":") >= 0) 
									{
									hour = hour.trim();
									_hour = hour.trim().substring(0, hour.trim()
									.indexOf(":"));
									_min = hour.trim().substring(hour.trim()
									.indexOf(":"), hour.trim().length());
									// NOW DO LOGIC...................

									int lvReq = Integer.parseInt(_hour.trim());
									if (lvReq < 24) {

									leaveType = "Hours";
									//keyword="LC";
									ftime = "09:30";
									// reqLeaveHour
									tTime = DateUtil.addTwoTime(ftime,""+_hour+""+_min+"");
									//System.out.println("tTELSEEEEEEEEEEime" +tTime);
									GenericMailSender mail=new GenericMailSender();
									userName = (Cryptography.decrypt(
											empdata[7],
											DES_ENCRYPTION_KEY));
									mailBody1=getMailBody1(emailArr[1],odate,ftime, tTime,
									maxid, empdata[1],"IN-1",comment ,"Casual",empLeaveBalance,"LC",DateUtil.getCurrentTimeHourMin(),"");
									new MsgMailCommunication() .addMailHR (emailArr[1],"",emailArr[0],"Leave Request by "
									+empdata[1]+" For Late Coming",mailBody1,"", "Pending",
									"0","","HR",connection);
									revertSMS = "Dear "	+ empdata[1]+ ",  Thanks! Your request for Late Coming has been registered successfully. Contact Himanshi Malik for more details.";
									System.out.println("revertSMS >>>>> else " +revertSMS);
									MMC.addMessageHR(empdata[1], "", empdata[6],
									revertSMS, "", "Pending", "0", "HR",
									connection);
									}

									} 
									else {
									// LC 5
									// LC :30
									// LC 5 30
									// LC 5.30 5_30 other case

										revertSMS =	"You have sent wrong keyword. Kindly Send Again. Correct Format is "+keyword+" HH:MM*Reason";
									MMC.addMessageHR(empdata[1], "", empdata[6],
									revertSMS, "", "Pending", "0", "HR",
									connection);

									}

									}}

								if (keyword.trim().startsWith("EG")
										&& keyword.trim().length() > 2) {
									

									// Check For Comment
									if (keyword.trim().indexOf("*") > 0) {
										
										// +keyword.trim().indexOf("*"));
										String subKeyword = keyword
												.trim()
												.substring(0,
														keyword.trim().indexOf("*"));
										String subcomment = keyword
												.trim()
												.substring(
														keyword.trim().indexOf("*"),
														keyword.trim().length());
										;

										hour = subKeyword.substring(2,
												subKeyword.length());

										if (hour.trim().indexOf(":") >= 0) {
											_hour = hour.substring(0, hour.trim()
													.indexOf(":"));
											_min = hour.substring(hour.trim()
													.indexOf(":"), hour.trim()
													.length());

											int lvReq = Integer.parseInt(hour
													.trim());
											if (lvReq < 24) {

												leaveType = "Hours";
												 keyword="EG";
												tTime = "06:30";
												userName = (Cryptography.decrypt(
														empdata[7],
														DES_ENCRYPTION_KEY));
												// reqLeaveHour
												ftime = time_difference(odate, ""
														+ _hour + "" + _min + "",
														tTime);
												
												GenericMailSender mail = new GenericMailSender();
												mailBody1 = getMailBody1(
														emailArr[1],
														odate,
														ftime,
														tTime,
														maxid,
														empdata[1],
														"IN-1",
														comment,
														"Casual",
														empLeaveBalance,
														"EG",
														DateUtil.getCurrentTimeHourMin(),
														"");

												new MsgMailCommunication()
														.addMailHR(
																emailArr[1],
																"",
																emailArr[0],
																"Leave Request by "
																		+ empdata[1]
																		+ " For Early Going",
																mailBody1, "",
																"Pending", "0", "",
																"HR", connection);

												revertSMS = "Dear "
														+ empdata[1]
														+ ",  Thanks! Your request for Early Going has been registered successfully. Contact Himanshi Malik for more details.";
												 System.out.println("revertSMS >>>>>  "
												 +revertSMS);
												MMC.addMessageHR(empdata[1], "",
														empdata[6], revertSMS, "",
														"Pending", "0", "HR",
														connection);

											}

										} else {
											// LC 5
											// LC :30
											// LC 5 30
											// LC 5.30 5_30 other case
											errorRevertString =	"You have sent wrong keyword. Kindly Send Again. Correct Format is "+keyword+" HH:MM*Reason";


										}

									} else {
										String subKeyword = keyword.trim()
												.substring(0,
														keyword.trim().length());
										String subcomment = "NA";

										hour = subKeyword.substring(2,
												subKeyword.length());

										if (hour.trim().indexOf(":") >= 0) {
											hour = hour.trim();

											_hour = hour.trim().substring(0,
													hour.trim().indexOf(":"));
											_min = hour.trim().substring(
													hour.trim().indexOf(":"),
													hour.trim().length());

											int lvReq = Integer.parseInt(_hour
													.trim());
											if (lvReq < 24) {
												leaveType = "Hours";
												// keyword="EG";
												tTime = "06:30";

												// reqLeaveHour
												ftime = time_difference(odate, ""
														+ _hour + "" + _min + "",
														tTime);
												
												GenericMailSender mail = new GenericMailSender();
												userName = (Cryptography.decrypt(
														empdata[7],
														DES_ENCRYPTION_KEY));
												mailBody1 = getMailBody1(
														emailArr[1],
														odate,
														ftime,
														tTime,
														maxid,
														empdata[1],
														"IN-1",
														comment,
														"Casual",
														empLeaveBalance,
														"EG",
														DateUtil.getCurrentTimeHourMin(),
														"");

												new MsgMailCommunication()
														.addMailHR(
																emailArr[1],
																"",
																emailArr[0],
																"Leave Request by "
																		+ empdata[1]
																		+ " For Early Going",
																mailBody1, "",
																"Pending", "0", "",
																"HR", connection);

												revertSMS = "Dear "
														+ empdata[1]
														+ ",  Thanks! Your request for Early Going has been registered successfully. Contact Himanshi Malik for more details.";
												System.out.println("revertSMS >>>>> else  "
												+revertSMS);
												MMC.addMessageHR(empdata[1], "",
														empdata[6], revertSMS, "",
														"Pending", "0", "HR",
														connection);

											}

										} else {
											// LC 5
											// LC :30
											// LC 5 30
											// LC 5.30 5_30 other case

											errorRevertString =	"You have sent wrong keyword. Kindly Send Again. Correct Format is "+keyword+" HH:MM*Reason";

										}

									}

								}

								if (keyword.trim().startsWith("LR")
										&& keyword.trim().length() > 2) {

									System.out.println("LR");

									// Check For Comment
									if (keyword.trim().indexOf("*") > 0) {
										// System.out.println("keyword"
										// +keyword.trim().indexOf("*"));
										String subKeyword = keyword
												.trim()
												.substring(0,
														keyword.trim().indexOf("*"));

										String subcomment = keyword
												.trim()
												.substring(
														keyword.trim().indexOf("*"),
														keyword.trim().length());
										System.out.println("subcomment"
												+ subcomment);
										;

										hour = subKeyword.substring(2,
												subKeyword.length());

										if (hour.trim().indexOf(":") >= 0) {
											_hour = hour.substring(0, hour.trim()
													.indexOf(":"));
											// System.out.println("hTime" +_hour);
											_min = hour.substring(hour.trim()
													.indexOf(":"), hour.trim()
													.length());

											int lvReq = Integer.parseInt(hour
													.trim());
											
											if (lvReq >= 24) {
												leaveType = "Days";

												
												String time_in = "09:30";
												userName = (Cryptography.decrypt(
														empdata[7],
														DES_ENCRYPTION_KEY));
												// reqLeaveDays
												tTime = DateUtil
														.newdate_AfterEscTime(
																fdate, time_in, ""
																		+ _hour
																		+ "" + _min
																		+ "", "y");
												tdate2 = tTime.split("#");
												
												String totalLeaverequest = DateUtil
														.findDaysForDates(
																DateUtil.convertDateToIndianFormat(fdate),
																DateUtil.convertDateToIndianFormat(tdate2[0]));
												int TotalNonWorkingDay = new WorkingHourHelper().getTotalNonWorkingDay(fdate, connection, cbt, "HR",tdate2[0]);
												System.out.println("TotalNonWorkingDay :::::  "+TotalNonWorkingDay);
												@SuppressWarnings("static-access")
												int totalworkingday=new DateUtil().getNoOfDays(tdate2[0], fdate);
												int nototalworkingday=totalworkingday+1;
												int totalcountLeavedays=nototalworkingday-TotalNonWorkingDay;
												

												mailBody = getMailBody(
														emailArr[1],
														DateUtil.convertDateToIndianFormat(fdate),
														DateUtil.convertDateToIndianFormat(tdate2[0]),
														maxid,
														empdata[1],
														"IN-1",
														comment,
														"Casual",
														empLeaveBalance,
														totalcountLeavedays,
														"LR",
														DateUtil.getCurrentTimeHourMin(),
														userName);

												new MsgMailCommunication()
														.addMailHR(
																emailArr[1],
																"",
																emailArr[0],
																"Leave Request by "
																		+ empdata[1]
																		+ " for Emergency Leave Request",
																mailBody, "",
																"Pending", "0", "",
																"HR", connection);
												revertSMS = "Dear "
														+ empdata[1]
														+ ",  Thanks! Your request for Emergency Leave Request has been registered successfully. Contact Himanshi Malik for more details.";
												  System.out.println("revertSMSxxxxxxxxxx >>>>>  "
												  +revertSMS);
												MMC.addMessageHR(empdata[1], "",
														empdata[6], revertSMS, "",
														"Pending", "0", "HR",
														connection);
											}

										} else {
											// LC 5
											// LC :30
											// LC 5 30
											// LC 5.30 5_30 other case

											errorRevertString =	"You have sent wrong keyword. Kindly Send Again. Correct Format is "+keyword+" HH:MM*Reason";
											MMC.addMessageHR(empdata[1], "",
													empdata[6], errorRevertString,
													"", "Pending", "0", "HR",
													connection);

										}

									} else {
										String subKeyword = keyword.trim()
												.substring(0,
														keyword.trim().length());
										
										String subcomment = "NA";
										System.out.println("subcomment"
												+ subcomment);
										hour = subKeyword.substring(2,
												subKeyword.length());

										if (hour.trim().indexOf(":") >= 0) {
											hour = hour.trim();

											_hour = hour.trim().substring(0,
													hour.trim().indexOf(":"));
											
											_min = hour.trim().substring(
													hour.trim().indexOf(":"),
													hour.trim().length());
										
											// NOW DO LOGIC...................

											int lvReq = Integer.parseInt(_hour
													.trim());
											
											if (lvReq >= 24) {
												leaveType = "Days";
												
												String time_in = "09:30";

												// reqLeaveHour
												tTime = DateUtil
														.newdate_AfterEscTime(
																fdate, time_in, ""
																		+ _hour
																		+ "" + _min
																		+ "", "y");
												tdate2 = tTime.split("#");
												
												String totalLeaverequest = DateUtil
														.findDaysForDates(
																DateUtil.convertDateToIndianFormat(fdate),
																DateUtil.convertDateToIndianFormat(tdate2[0]));
												int TotalNonWorkingDay = new WorkingHourHelper().getTotalNonWorkingDay(fdate, connection, cbt, "HR",tdate2[0]);
												System.out.println("TotalNonWorkingDay :::::  "+TotalNonWorkingDay);
												@SuppressWarnings("static-access")
												int totalworkingday=new DateUtil().getNoOfDays(tdate2[0], fdate);
												int nototalworkingday=totalworkingday+1;
												int totalcountLeavedays=nototalworkingday-TotalNonWorkingDay;
												// System.out.println("totalLeaverequest"
												// +totalLeaverequest);
												GenericMailSender mail = new GenericMailSender();
												userName = (Cryptography.decrypt(
														empdata[7],
														DES_ENCRYPTION_KEY));
												mailBody = getMailBody(
														emailArr[1],
														DateUtil.convertDateToIndianFormat(fdate),
														DateUtil.convertDateToIndianFormat(tdate2[0]),
														maxid,
														empdata[1],
														"IN-1",
														comment,
														"Casual",
														empLeaveBalance,
														totalcountLeavedays,
														"LR",
														DateUtil.getCurrentTimeHourMin(),
														userName);

												new MsgMailCommunication()
														.addMailHR(
																emailArr[1],
																"",
																emailArr[0],
																"Leave Request by "
																		+ empdata[1]
																		+ " for Emergency Leave Request ",
																mailBody, "",
																"Pending", "0", "",
																"HR", connection);
												revertSMS = "Dear "
														+ empdata[1]
														+ ",  Thanks! Your request for Emergency Leave Request has been registered successfully. Contact Himanshi Malik for more details.";
												  System.out.println("revertSMSxxxxxxxxxx else >>>>>  "
												  +revertSMS);
												MMC.addMessageHR(empdata[1], "",
														empdata[6], revertSMS, "",
														"Pending", "0", "HR",
														connection);
											}

										} else {
											// LC 5
											// LC :30
											// LC 5 30
											// LC 5.30 5_30 other case

											errorRevertString =	"You have sent wrong keyword. Kindly Send Again. Correct Format is "+keyword+" HH:MM*Reason";

										}

									}

								}

								
								

								if (empdata != null) {

									CommonOperInterface cbt = new CommonConFactory()
											.createInterface();
									LeaveHelper LM = new LeaveHelper();

									List<GridDataPropertyView> statusColName = Configuration
											.getConfigurationData(
													"mapped_leave_request_configuration", "",
													connection, "", 0, "table_name",
													"leave_request_configuration");

									if (statusColName != null && statusColName.size() > 0) {
										// create table query based on configuration
										@SuppressWarnings("unused")
										List<InsertDataTable> insertData1 = new ArrayList<InsertDataTable>();
										InsertDataTable ob = null;
										boolean status = false;
										List<TableColumes> tableColume = new ArrayList<TableColumes>();
										boolean userTrue = false;
										boolean dateTrue = false;
										boolean timeTrue = false;
										boolean flag = false;
										TableColumes ob11 = null;
										for (GridDataPropertyView gdp : statusColName) {
											ob11 = new TableColumes();
											ob11.setColumnname(gdp.getColomnName());
											ob11.setDatatype("varchar(255)");
											ob11.setConstraint("default NULL");
											tableColume.add(ob11);
											if (gdp.getColomnName()
													.equalsIgnoreCase("userName"))
												userTrue = true;
											else if (gdp.getColomnName().equalsIgnoreCase(
													"date1"))
												dateTrue = true;
											else if (gdp.getColomnName().equalsIgnoreCase(
													"time"))
												timeTrue = true;
											else if (gdp.getColomnName().equalsIgnoreCase(
													"flag"))
												flag = true;
										}
										cbt.createTable22("leave_request", tableColume,
												connection);

										List<InsertDataTable> insertData11 = new ArrayList<InsertDataTable>();
										{
											InsertDataTable obj = null;
											if (leaveType.equalsIgnoreCase("Days")) {
												obj = new InsertDataTable();
												obj.setColName("fdate");
												obj.setDataName(fdate);
												insertData11.add(obj);

												obj = new InsertDataTable();
												obj.setColName("tdate");
												obj.setDataName(tdate2[0]);
												insertData11.add(obj);
											} else {

												obj = new InsertDataTable();
												obj.setColName("odate");
												obj.setDataName(DateUtil
														.getCurrentDateUSFormat());
												insertData11.add(obj);

												obj = new InsertDataTable();
												obj.setColName("ftime");
												obj.setDataName(ftime);
												insertData11.add(obj);

												obj = new InsertDataTable();
												obj.setColName("ttime");
												obj.setDataName(tTime);
												insertData11.add(obj);
											}

											obj = new InsertDataTable();
											obj.setColName("empname");
											obj.setDataName(lodgeId + ",");
											insertData11.add(obj);

											obj = new InsertDataTable();
											obj.setColName("leavestatus");
											obj.setDataName("Casual");
											insertData11.add(obj);

											obj = new InsertDataTable();
											obj.setColName("reason");
											obj.setDataName(comment);
											insertData11.add(obj);

											obj = new InsertDataTable();
											obj.setColName("userName");
											obj.setDataName(userName);
											
											insertData11.add(obj);  

											obj = new InsertDataTable();
											obj.setColName("status");
											obj.setDataName("Pending");
											insertData11.add(obj);

											obj = new InsertDataTable();
											obj.setColName("comment");
											obj.setDataName("NA");
											insertData11.add(obj);

											obj = new InsertDataTable();
											obj.setColName("flag");
											obj.setDataName(leaveType);
											insertData11.add(obj);

											obj = new InsertDataTable();
											obj.setColName("name");
											obj.setDataName(empdata[1]);
											insertData11.add(obj);

											obj = new InsertDataTable();
											obj.setColName("mobno");
											obj.setDataName(empdata[6]);
											insertData11.add(obj);

											obj = new InsertDataTable();
											obj.setColName("email");
											obj.setDataName(empdata[2]);
											insertData11.add(obj);

											obj = new InsertDataTable();
											obj.setColName("date1");
											obj.setDataName(DateUtil.getCurrentDateUSFormat());
											insertData11.add(obj);

											obj = new InsertDataTable();
											obj.setColName("leaveunit");
											obj.setDataName(empdata[0]);
											insertData11.add(obj);

											obj = new InsertDataTable();
											obj.setColName("mode");
											obj.setDataName("sms");
											insertData11.add(obj);
											
											obj = new InsertDataTable();
											obj.setColName("approveBy");
											obj.setDataName("NO Action");
											insertData11.add(obj);
											
											ob = new InsertDataTable();
											ob.setColName("approveOn");
											ob.setDataName("NA");
											insertData11.add(ob);

											status = cbt.insertIntoTable("leave_request",
													insertData11, connection);

										}
									}

									insertIntoTable(empdata,connection,arrLr,keyword,revertSMS,revertSMS1,comment,leaveType,mailBody,mailBody1);
								}

								
								
								

							}

						} else {
							revertSMS = "Hi, you seems to perform this activity from an Unregistered Mobile No. Please use Registered No. or Contact 9250400311. ";
							System.out.println("revertSMS  " + revertSMS);
							MMC.addMessageHR("", "", arrLr[0], revertSMS, "",
									"Pending", "0", "", connection);
						}
					
						
						

					} 
					else {
					
						if(keyword.trim().startsWith("EG"))
								{
						errorRevertString = "You have sent wrong keyword. Kindly Send Again. Correct Format is EG <HH:MM>*Reason";
						System.out.println("errorRevertString else " +errorRevertString);
						MMC.addMessageHR(empdata[1], "", empdata[6],
								errorRevertString, "", "Pending", "0",
								"HR", connection);
								}
						else if(keyword.trim().startsWith("LC"))
						{
				errorRevertString = "You have sent wrong keyword. Kindly Send Again. Correct Format is LC <HH:MM>*Reason";
				System.out.println("errorRevertString else " +errorRevertString);
				MMC.addMessageHR(empdata[1], "", empdata[6],
						errorRevertString, "", "Pending", "0",
						"HR", connection);
						
						}	
						else if(keyword.trim().startsWith("LR"))
						{
				errorRevertString = "You have sent wrong keyword. Kindly Send Again. Correct Format is LR <HH:MM>*Reason";
				System.out.println("errorRevertString else " +errorRevertString);
				MMC.addMessageHR(empdata[1], "", empdata[6],
						errorRevertString, "", "Pending", "0",
						"HR", connection);
						
						}	
						
					}
					
					
					
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<AttendancePojo> holidayCalender(SessionFactory connectionSpace) {
		AttendancePojo ap = null;

		CommonOperInterface coi = new CommonConFactory().createInterface();
		List<AttendancePojo> dataList = new ArrayList<AttendancePojo>();
		String CurrentYear = DateUtil.getCurrentDateUSFormat().split("-")[0];

		StringBuilder query = new StringBuilder();
		query.append(" select fdate,holidayname");
		query.append(" from holiday_list   where fdate  between '"
				+ DateUtil.getCurrentDateUSFormat() + "' and '" + CurrentYear
				+ "-12-31' order by fdate asc  ");
		// System.out.println("Excecute Query >>>>  " +query);
		List list = coi
				.executeAllSelectQuery(query.toString(), connectionSpace);
		query.setLength(0);
		if (list != null && list.size() > 0) {
			Object[] object = null;
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {

				object = (Object[]) iterator.next();
				if (object != null) {
					ap = new AttendancePojo();
					ap.setFdate(DateUtil.convertDateToIndianFormat(
							(String) object[0]).split("-")[0]
							+ "-"
							+ DateUtil.convertDateToIndianFormat(
									(String) object[0]).split("-")[1]);
					ap.setHolidayname((String) object[1]);

				}
				dataList.add(ap);
			}

			// System.out.println(" dataList SIZE " +dataList.size());
		}

		return dataList;
	}
	
	public void insertIntoTable(String[] empdata, SessionFactory connection, String[] arrLr, String keyword, String revertSMS, StringBuilder revertSMS1, String comment, String leaveType, String mailBody, String mailBody1)
	{

		boolean tableCreated1 = new HRKeyWordRecvModem()
				.createMSG_StateTable(connection);

		
		if (tableCreated1) {
			System.out.println("Table Crtaetyed>>>");
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			System.out.println("arrLrLENGTh" + arrLr.length);
			if (arrLr != null) {
				// for (int i = 0; i < arrLr.length; i++) {
				insertData.clear();

				InsertDataTable ob = new InsertDataTable();
				ob.setColName("empName");
				ob.setDataName(empdata[1]);
				insertData.add(ob);

				InsertDataTable ob1 = new InsertDataTable();
				ob1.setColName("mobNo");
				ob1.setDataName(arrLr[0]);
				insertData.add(ob1);

				InsertDataTable ob2 = new InsertDataTable();
				ob2.setColName("IncomingSms");
				ob2.setDataName(keyword);
				
				insertData.add(ob2);

				InsertDataTable ob3 = new InsertDataTable();
				ob3.setColName("OutGoingSms");
				if (keyword.trim().equalsIgnoreCase("HC")
						|| keyword.trim().equalsIgnoreCase("HR")) {
					ob3.setDataName(revertSMS1.toString());
				} else {
					ob3.setDataName(revertSMS);
				}
				insertData.add(ob3);

				InsertDataTable ob12 = new InsertDataTable();
				ob12.setColName("reason");
				ob12.setDataName(comment);
				System.out.println("comment" + comment);
				insertData.add(ob12);

				InsertDataTable ob14 = new InsertDataTable();
				ob14.setColName("description");
				if (keyword.trim().equalsIgnoreCase("LB")) {
					ob14.setDataName("Leave Balance");
				} else if (keyword.trim().equalsIgnoreCase("HC")) {
					ob14.setDataName("Holiday Calendar");
				} else if (keyword.trim().startsWith("LC")) {
					ob14.setDataName("Late Coming");
				} else if (keyword.trim().startsWith("EG")) {
					ob14.setDataName("Early Going");
				} else if (keyword.trim().startsWith("LR")) {
					ob14.setDataName("Emergency Leave Request");
				} else if (keyword.trim().equalsIgnoreCase("HR")) {
					ob14.setDataName("HR");
				} else {
					ob14.setDataName("NA");

				}
				insertData.add(ob14);

				InsertDataTable ob4 = new InsertDataTable();
				ob4.setColName("OutGoingMail");
				System.out.println("leaveType" + leaveType);
				if (leaveType.equalsIgnoreCase("Days")) {
					ob4.setDataName(mailBody);
					System.out.println("GetDataName"
							+ ob4.getDataName());
				} else {
					ob4.setDataName(mailBody1);
					System.out.println("GetDataName1"
							+ ob4.getDataName());
				}
				insertData.add(ob4);

				InsertDataTable ob5 = new InsertDataTable();
				ob5.setColName("InComingDate");
				ob5.setDataName(DateUtil
						.getCurrentDateIndianFormat());
				insertData.add(ob5);

				InsertDataTable ob6 = new InsertDataTable();
				ob6.setColName("InComingTime");
				ob6.setDataName(DateUtil.getCurrentTimeHourMin());
				insertData.add(ob6);

				
				if (true) {

					tableCreated1 = cbt.insertIntoTable(
							"leave_sms_Report", insertData,
							connection);

					System.out
							.println("Table Data Inserted Successfully !!! "
									+ tableCreated1);
				}

			}
		}
		
	}

	public List<AttendancePojo> hrAllKeywords(SessionFactory connectionSpace) {
		AttendancePojo ap = null;

		CommonOperInterface coi = new CommonConFactory().createInterface();
		List<AttendancePojo> dataList = new ArrayList<AttendancePojo>();

		StringBuilder query = new StringBuilder();
		query.append(" select keyword,description");
		query.append(" from configurekeyword   ");
		// System.out.println("Excecute Query >>>>  " +query);
		List list = coi
				.executeAllSelectQuery(query.toString(), connectionSpace);
		query.setLength(0);
		if (list != null && list.size() > 0) {
			Object[] object = null;
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {

				object = (Object[]) iterator.next();
				if (object != null) {
					ap = new AttendancePojo();
					ap.setKeyword((String) object[0]);
					ap.setDescription((String) object[1]);

				}
				dataList.add(ap);
			}

			System.out.println(" dataList SIZE " + dataList.size());
		}

		return dataList;
	}

	public String getMailBody(String validate, String fdate, String tdate,
			int maxid, String loggedName, String accounId, String reason,
			String leaveStatus, double totalDeduction,
			int totalLeaverequest, String Keyword, String receivedAt,
			String userName) {

		@SuppressWarnings("unused")
		List<AttendancePojo> dataList = new ArrayList<AttendancePojo>();
		AttendancePojo attP = null;

		StringBuilder mailText = new StringBuilder("");
		String URL = "<a href=http://over2cloud.co.in/over2cloud/view/Over2Cloud/leaveManagement/addResponse4Request.action?id="
				+ maxid
				+ "&userName="
				+ userName
				+ "&accountid="
				+ accounId
				+ "&validate="
				+ validate.replace(" ", "%20")
				+ ">Click Here!!</a>";
		// //System.out.println("URL  " +URL);
		mailText.append("<table width='100%' align='center' style='border: 0'><tr><td align='left'>"
				+ "<font color='#000000' size='3'><b>Dear "
				+ validate
				+ ",</b></font></td></tr></table>"
				+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
				+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'>Hello!</td></tr></table>"
				+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'>"
				+ "<font color='#000000' size='2'>Please refer below a leave request to be approved/ disapproved by you:</font></td></tr></table>"
				+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
				+ "<table  border='1' cellpadding='2' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'>"
				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Requested By :</font></td> "
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ loggedName
				+ "&nbsp;</font></td>"
				+ "</tr>"
				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Leave Left:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ totalDeduction
				+ "&nbsp;</font></td>"
				+ "</tr>"
				+ "</tr>"
				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Total leave Request:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ totalLeaverequest
				+ "&nbsp;</font></td>"
				+ "</tr>"
				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>From Date:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ fdate
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>To Date:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ tdate
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Leave Type:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ DateUtil.makeTitle(leaveStatus)
				+ "&nbsp;</font></td></tr>"

				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Reason:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ DateUtil.makeTitle(reason)
				+ "&nbsp;</font></td></tr>"

				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Keyword:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ Keyword
				+ "&nbsp;</font></td></tr>"

				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Received At:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ receivedAt + "&nbsp;</font></td></tr>"

				+ "   </table>");
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td>You are requested to take an appropriate action by clicking on below link: </td></tr>");
		mailText.append(URL);
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td><font size='2'>Thanks & Regards, </font></td></tr>");
		mailText.append("<tr><td><font size='2'>Team HR </font></td></tr>");
		mailText.append("<tr><td><font size='2'>DreamSol TeleSolutions Pvt. Ltd. </font></td></tr>");
		mailText.append("<tr><td><font size='2'>--------------- </font></td></tr>");
		mailText.append("<tr><td><div align='justify'><font size='1'>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the System as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div></td></tr>");
		return mailText.toString();
	}

	public String getMailBody1(String validate, String odate, String ftime,
			String ttime, int maxid, String username, String accountId,
			String reason, String leaveStatus, Double leaveLeft,
			String keyword, String received, String userName) {

		StringBuilder mailText = new StringBuilder("");

		String URL = "<a href=http://over2cloud.co.in/over2cloud/view/Over2Cloud/leaveManagement/addResponse4Request.action?id="
				+ maxid
				+ "&userName="
				+ userName
				+ "&accountid="
				+ accountId
				+ "&validate="
				+ validate.replace(" ", "%20")
				+ ">Click Here!!</a>";

		mailText.append("<table width='100%' align='center' style='border: 0'><tr><td align='left'>"
				+ "<font color='#000000' size='3'><b>Dear "
				+ validate
				+ ",</b></font></td></tr></table>"
				+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
				+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'>Hello!</td></tr></table>"
				+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'>"
				+ "<font color='#000000' size='2'>Please refer below a leave request to be approved/ disapproved by you:</font></td></tr></table>"
				+ "<table width='100%' align='center' style='border: 0'><tr><td align='left'></td></tr></table>"
				+ "<table  border='1' cellpadding='2' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'>"
				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Requested By :</font></td> "
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ username
				+ "&nbsp;</font></td>"

				+ "</tr><tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Leave Left:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ leaveLeft
				+ "&nbsp;</font></td></tr>"

				+ "</tr><tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>On Date:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ odate
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>From Time:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ ftime
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>To Time:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ ttime
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Leave Type:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ DateUtil.makeTitle(leaveStatus)
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Reason:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ DateUtil.makeTitle(reason)
				+ "&nbsp;</font></td></tr>"
				+ "<tr  bgcolor='#FFFFFF'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Keyword:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ keyword
				+ "&nbsp;</font></td></tr>"

				+ "<tr  bgcolor='#D8D8D8'><td  align='left' width='20%'><font style='font-weight:bold;color:#000000;'>Received At:</font></td>"
				+ "<td  align='left' width='30%'><font style='font-weight:bold;color:#000000;'>&nbsp;"
				+ received + "&nbsp;</font></td></tr>"

				+ "   </table>");
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td>You are requested to take an appropriate action by clicking on below link: </td></tr>");
		mailText.append(URL);
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td></td></tr>");
		mailText.append("<tr><td><font size='2'>Thanks & Regards, </font></td></tr>");
		mailText.append("<tr><td><font size='2'>Team HR </font></td></tr>");
		mailText.append("<tr><td><font size='2'>DreamSol TeleSolutions Pvt. Ltd. </font></td></tr>");
		mailText.append("<tr><td><font size='2'>--------------- </font></td></tr>");
		mailText.append("<tr><td><div align='justify'><font size='1'>This message was sent to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the //System as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div></td></tr>");
		return mailText.toString();
	}

	@SuppressWarnings("static-access")
	public static String time_difference(String date1, String time1,
			String time2) {
		

		String timeString = null;
		StringBuilder sb = new StringBuilder(64);
		String timearr1[] = null;
		timearr1 = time1.split(":");

		String timearr2[] = null;
		timearr2 = time2.split(":");

		try {
			java.util.Date convertedDate = DATE_FORMAT.parse(date1);
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(convertedDate);
			cal1.add(cal1.HOUR, Integer.parseInt(timearr1[0]));
			cal1.add(cal1.MINUTE, Integer.parseInt(timearr1[1]));

			// Date convertedDate2 = DATE_FORMAT.parse(date1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(convertedDate);
			cal2.add(cal2.HOUR, Integer.parseInt(timearr2[0]));
			cal2.add(cal2.MINUTE, Integer.parseInt(timearr2[1]));

			long millisesond1 = cal1.getTimeInMillis();
			long millisesond2 = cal2.getTimeInMillis();
			long diff = millisesond2 - millisesond1;

			if (diff < 0) {
				timeString = "00:00";
			} else {

				long hours = TimeUnit.MILLISECONDS.toHours(diff);
				diff -= TimeUnit.HOURS.toMillis(hours);
				// System.out.println("diff "+diff);
				long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

				sb.append(hours);
				sb.append(":");
				timeString = sb.append(minutes).toString();
				// System.out.println("timeString "+timeString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeString;
	}

	
	
	
	public static void main(String sddss[]) {
		// System.out.println("Connecting Virtual No...");

		System.out
				.println("Response MSG is as ");
	}

	public double getEmpLeaveBalance() {
		return empLeaveBalance;
	}

	public void setEmpLeaveBalance(double empLeaveBalance) {
		this.empLeaveBalance = empLeaveBalance;
	}

}