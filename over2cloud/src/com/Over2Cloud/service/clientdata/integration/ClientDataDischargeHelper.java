package com.Over2Cloud.service.clientdata.integration;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.EscalationHelper;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;

public class ClientDataDischargeHelper
{

	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	String new_escalation_datetime = DateUtil.getCurrentDateUSFormat() + "#" + DateUtil.getCurrentTimeHourMin(), non_working_timing = "00:00";

	// for get the new order from HIS Server

	public void datafetchForOrder(SessionFactory clientConnection)
	{
		System.out.println("Calleed Manab..");
		/**
		 * BLOCK TYPE IS USED HERE FOR BOOKING TYPE BY AARIF
		 * 
		 * */
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		/*
		 * String pri="Routine"; String ordNameChange = null;
		 */

		try
		{

			CommonOperInterface cot = new CommonConFactory().createInterface();
			String lastORDDateTime = lastORDdateTimeget(clientConnection);
			String day = DateUtil.getCurrentDateUSFormat().split("-")[2].toString();
			System.out.println("day  "+day);

			// we are asuming discharge_process_data table is HIS table
			// on live code we change in JDBC

			// *******************************
			Class.forName("oracle.jdbc.driver.OracleDriver");

			if (DateUtil.getCurrentDay() == 0 && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "09:45", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin()) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "16:30"))

			{
				con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol", "Dream_1$3");
				System.out.println("redirect  66 Server......");

			} 
			else if ((day.equalsIgnoreCase("01") && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), "20:30", DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin() ) && DateUtil.comparebetweenTwodateTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTimeHourMin(), DateUtil.getCurrentDateUSFormat(), "23:30" )))
			{
				
				con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol", "Dream_1$3");
				System.out.println("redirect  66 Server......");
				
			}else
			{

				con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.88:1521:HISOGG", "dreamsol", "Dream_1$3");
				System.out.println("okey  88 Server......");

			}

			st = con.createStatement();
			System.out.println("test con " + con.isClosed());
			String qry1 = "select BED_BOOKING_REF_NO, ADDED_DATE, REQ_BED_NO, BOOKING_TYPE  " + " from dreamsol_dis_vw where ADDED_DATE > TO_TIMESTAMP('" + lastORDDateTime + "','YYYY-MM-DD HH24:MI:SS.FF')";

			// view.........................................................
			// .............................................................
			// ............................................................
			rs = st.executeQuery(qry1);

			// *****************************************

			System.out.println("query to fetch HIS record $$   " + qry1 + "   $$b  time  " + lastORDDateTime);

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			/* List dataListA=cbt.executeAllSelectQuery(qry1, clientConnection); */

			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();

			// Patient_id, Patient_name, admission_date_time, bed_num,
			// block_type_code
			while (rs.next())
			{
				System.out.println("New Data Found for Insert>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
				ob = new InsertDataTable();
				ob.setColName("bed_refer_no");
				ob.setDataName(rs.getString("BED_BOOKING_REF_NO"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("insert_dateTime_client");
				ob.setDataName(rs.getString("ADDED_DATE"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("bed_number");
				ob.setDataName(rs.getString("REQ_BED_NO"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("block_type");
				ob.setDataName(rs.getString("BOOKING_TYPE"));
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("username");
				ob.setDataName("discharge");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("open_date");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("open_time");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);
				int status_ticket_open = openTicketForDischarge(rs.getString("REQ_BED_NO"), rs.getString("BOOKING_TYPE"), clientConnection);
				if (status_ticket_open > 0)
				{

					ob = new InsertDataTable();
					ob.setColName("feed_id");
					ob.setDataName(status_ticket_open);
					insertData.add(ob);

					boolean j = createHISTable(clientConnection, cot);
					boolean i = cot.insertIntoTable("feedback_hdm_his_mapping", insertData, clientConnection);
					System.out.println("record inserted in hdm_his table");
				} else
				{
					System.out.println("OOPS There is a problem in Ticket Lodge");
				}
				insertData.clear();

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (con != null)
					con.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	// to get record max date and time by aarif

	private String lastORDdateTimeget(SessionFactory clientConnection)
	{
		// TODO Auto-generated method stub
		String lastDataTime = DateUtil.getCurrentDateUSFormat() + " " + DateUtil.getCurrentTimewithSeconds() + ".0";
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			// qry.append(");
			List lastIdList = cbt.executeAllSelectQuery("select insert_dateTime_client from feedback_hdm_his_mapping order by id desc", clientConnection);
			// dataList = cbt.executeAllSelectQuery(query.toString(),
			// connectionSpace);

			if (lastIdList != null && lastIdList.size() > 0)
			{
				lastDataTime = (String) lastIdList.get(0);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return lastDataTime;
	}

	public int openTicketForDischarge(String bed_number, String block_type, SessionFactory clientConnection)
	{

		int status1 = 0;
		CommunicationHelper CH = new CommunicationHelper();
		CommonOperInterface cot = new CommonConFactory().createInterface();
		HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
		EscalationHelper EH = new EscalationHelper();

		FeedbackPojo fbp = null;

		// System.out.println("We open ticken in this method");
		System.out.println("#########  blockType +" + block_type + "##### Bedno:::" + bed_number);
		String subCotegory = "", locationA = null, cotegory = ""; // cotegory
		// stand for id
		// of discharge
		// function
		String sub_cot = "";

		List subcot = null;

		if (block_type.equalsIgnoreCase("D")) // we can change as our symbols
		// change
		{
			sub_cot = "Discharge Function";
			String qryy = "select id,categoryName from feedback_subcategory where subCategoryName  like '%" + sub_cot + "%'";
			subcot = cot.executeAllSelectQuery(qryy.toString(), clientConnection);
		}
		if (block_type.equalsIgnoreCase("T"))
		{
			sub_cot = "Transfer Function";
			String qryy = "select id,categoryName from feedback_subcategory where subCategoryName  like '%" + sub_cot + "%'";
			subcot = cot.executeAllSelectQuery(qryy.toString(), clientConnection);
		}

		if (subcot != null && subcot.size() > 0)
		{
			for (Iterator iterator1 = subcot.iterator(); iterator1.hasNext();)
			{
				Object[] object = (Object[]) iterator1.next();
				subCotegory = object[0].toString();
				// System.out.println((String)subcot.get(0).toString());
				cotegory = object[1].toString();
			}
		}

		if (subCotegory != null && !subCotegory.equalsIgnoreCase(""))
		{
			String escTime, escalation_date = "NA", tosubdept = "NA", escalation_time = "NA", adrr_time = "", res_time = "", allottoId = null, to_dept_subdept = "", by_dept_subdept = "", ticketno = "", feedby = "", feedbymob = "", feedbyemailid = "", mailText = "", tolevel = "", needesc = "", esc_time = "";
			StringBuilder query1 = new StringBuilder();
			// on the basis of above information we insert a record into
			// feedback_status_new table
			query1.append(" Select subdept.id,subCat.escalationLevel,subCat.addressingTime,subCat.resolutionTime,subCat.escalateTime,subCat.needEsc,subCat.feedBreif from subdepartment as subdept ");
			query1.append(" inner join feedback_type fb on fb.dept_subdept = subdept.id ");
			query1.append(" inner join feedback_category catt on catt.fbType = fb.id ");
			query1.append(" inner join feedback_subcategory subCat on subCat.categoryName = catt.id ");
			query1.append(" WHERE subCat.id= '" + subCotegory + "'");
			// System.out.println(">>>>>> subCategory  >>>>> A  "+query1.toString());
			List subCategoryList = cot.executeAllSelectQuery(query1.toString(), clientConnection);
			if (subCategoryList != null && subCategoryList.size() > 0)
			{
				Object[] objectCol = (Object[]) subCategoryList.get(0);
				if (objectCol[1] == null)
				{
					tolevel = "Level1";
				} else
				{
					tolevel = objectCol[1].toString();
				}
				tosubdept = objectCol[0].toString();
				// System.out.println("B   "+tosubdept);
				if (objectCol[2] == null)
				{
					adrr_time = "06:00";
				} else
				{
					adrr_time = objectCol[2].toString();
				}
				if (objectCol[3] == null)
				{
					res_time = "10:00";
				} else
				{
					res_time = objectCol[3].toString();
				}
				if (objectCol[4] == null)
				{
					esc_time = "10:00";
				} else
				{
					esc_time = objectCol[4].toString();
				}
				if (objectCol[5] == null)
				{
					needesc = "Y";
				} else
				{
					needesc = objectCol[5].toString();
				}
				if (objectCol[6] == null)
				{
					mailText = "NA";
				} else
				{
					mailText = objectCol[6].toString();
				}
			}
			to_dept_subdept = tosubdept;
			String[] date_time_arr = null;
			String[] adddate_time = null;
			String Address_Date_Time = "NA";

			String deptid = HUH.getDeptId(to_dept_subdept, clientConnection);

			if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
			{
				// System.out.println("needesc true condition value  "+needesc);
				// logger.info("Next Escalation DateTime Initially: " +
				// new_escalation_datetime);
				getNextEscalationDateTime(adrr_time, res_time, deptid, "HDM", clientConnection);
				// logger.info("Next Escalation DateTime After GetNextEsclationDateTime: "
				// + new_escalation_datetime);
				String[] newdate_time = null;
				if (new_escalation_datetime != null && new_escalation_datetime != "")
				{
					newdate_time = new_escalation_datetime.split("#");
					if (newdate_time.length > 0)
					{
						escalation_date = newdate_time[0];
						escalation_time = newdate_time[1];
						// System.out.println("sclation_date and time $$ "+escalation_date+"  and "+escalation_time
						// );
					}
				}
				Address_Date_Time = DateUtil.newdate_BeforeTime(escalation_date, escalation_time, res_time);
				// System.out.println("addressing date_timee::: "+Address_Date_Time);
				// System.out.println("escalation time::: "+escalation_time);
				if (Address_Date_Time != null && !Address_Date_Time.equals("") && !Address_Date_Time.equals("NA"))
				{
					String esc_start_time = "00:00";
					String esc_end_time = "24:00";
					String esc_working_day = "";
					esc_working_day = DateUtil.getDayofDate(escalation_date);
					// List of working
					// timing data
					List workingTimingData = null;
					workingTimingData = EH.getWorkingTimeData("HDM", esc_working_day, deptid, clientConnection);
					// System.out.println("working time data  $$  "+workingTimingData.size());
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						String time_status = "", time_diff = "", working_hrs = "";
						adddate_time = Address_Date_Time.split("#");
						esc_start_time = workingTimingData.get(1).toString();
						esc_end_time = workingTimingData.get(2).toString();
						time_status = DateUtil.timeInBetween(escalation_date, esc_start_time, esc_end_time, adddate_time[0], adddate_time[1]);
						// System.out.println("time Status  $$  "+time_status);
						if (time_status != null && !time_status.equals("") && time_status.equals("before"))
						{
							time_diff = DateUtil.time_difference(adddate_time[0], adddate_time[1], escalation_date, esc_start_time);
							String previous_working_date = "";
							previous_working_date = EH.getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(escalation_date), -1), clientConnection);
							esc_working_day = DateUtil.getDayofDate(previous_working_date);
							workingTimingData.clear();
							workingTimingData = EH.getWorkingTimeData("HDM", esc_working_day, deptid, clientConnection);
							// System.out.println("wirking time B $$  "+workingTimingData.size());
							if (workingTimingData != null && workingTimingData.size() > 0)
							{
								esc_start_time = workingTimingData.get(1).toString();
								esc_end_time = workingTimingData.get(2).toString();
								working_hrs = workingTimingData.get(3).toString();
								if (DateUtil.checkTwoTime(working_hrs, time_diff))
								{
									Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
								} else
								{
									time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
									previous_working_date = EH.getNextOrPreviousWorkingDate("P", DateUtil.getNewDate("day", -1, previous_working_date), clientConnection);

									esc_working_day = DateUtil.getDayofDate(previous_working_date);
									workingTimingData.clear();
									workingTimingData = EH.getWorkingTimeData("HDM", esc_working_day, deptid, clientConnection);
									if (workingTimingData != null && workingTimingData.size() > 0)
									{
										esc_start_time = workingTimingData.get(1).toString();
										esc_end_time = workingTimingData.get(2).toString();
										working_hrs = workingTimingData.get(3).toString();
										if (DateUtil.checkTwoTime(working_hrs, time_diff))
										{
											Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
										} else
										{
											time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
										}
									}
								}
							}
						}
					}
				}

			} else
			{
				escalation_date = "0000-00-00";
				escalation_time = "00:00";
				Address_Date_Time = DateUtil.newdate_AfterTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), adrr_time);
				date_time_arr = Address_Date_Time.split("#");
				String esc_start_time = "00:00";
				String esc_end_time = "24:00";
				String esc_working_day = "";
				esc_working_day = DateUtil.getDayofDate(date_time_arr[0]);
				// List of working timing data
				List workingTimingData = null;
				workingTimingData = EH.getWorkingTimeData("HDM", esc_working_day, deptid, clientConnection);
				if (workingTimingData != null && workingTimingData.size() > 0)
				{
					String time_status = "", time_diff = "", working_hrs = "";
					adddate_time = Address_Date_Time.split("#");
					esc_start_time = workingTimingData.get(2).toString();
					esc_end_time = workingTimingData.get(3).toString();
					time_status = DateUtil.timeInBetween(escalation_date, esc_start_time, esc_end_time, adddate_time[0], adddate_time[1]);
					if (time_status != null && !time_status.equals("") && (time_status.equals("before") || time_status.equals("after")))
					{
						time_diff = DateUtil.time_difference(adddate_time[0], adddate_time[1], adddate_time[0], esc_start_time);
						String previous_working_date = "";
						previous_working_date = EH.getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(adddate_time[0]), -1), clientConnection);
						esc_working_day = DateUtil.getDayofDate(previous_working_date);
						workingTimingData.clear();
						workingTimingData = EH.getWorkingTimeData("HDM", esc_working_day, deptid, clientConnection);
						if (workingTimingData != null && workingTimingData.size() > 0)
						{
							esc_start_time = workingTimingData.get(2).toString();
							esc_end_time = workingTimingData.get(3).toString();
							working_hrs = workingTimingData.get(4).toString();
							if (DateUtil.checkTwoTime(working_hrs, time_diff))
							{
								Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
							} else
							{
								time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
								previous_working_date = EH.getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(previous_working_date, -1), clientConnection);
								esc_working_day = DateUtil.getDayofDate(previous_working_date);
								workingTimingData.clear();
								workingTimingData = EH.getWorkingTimeData("HDM", esc_working_day, deptid, clientConnection);
								if (workingTimingData != null && workingTimingData.size() > 0)
								{

									esc_start_time = workingTimingData.get(2).toString();
									esc_end_time = workingTimingData.get(3).toString();
									working_hrs = workingTimingData.get(4).toString();
									if (DateUtil.checkTwoTime(working_hrs, time_diff))
									{
										Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
									} else
									{
										time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
									}
								}
							}
						}
					}
				}
			}

			List empData = getEmpDataByUserNameA("Discharge Mobile", clientConnection);
			if (empData != null && empData.size() > 0)
			{
				for (Iterator iterator = empData.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && !object[0].toString().equals(""))
					{
						feedby = object[0].toString();
					} else
					{
						feedby = "NA";
					}

					if (object[1] != null && !object[1].toString().equals(""))
					{
						feedbymob = object[1].toString();
					} else
					{
						feedbymob = "NA";
					}

					if (object[2] != null && !object[2].toString().equals(""))
					{
						feedbyemailid = object[2].toString();
					} else
					{
						feedbyemailid = "NA";
					}

					if (object[3] != null && !object[3].toString().equals(""))
					{
						by_dept_subdept = object[3].toString();
					} else
					{
						by_dept_subdept = "-1";
					}
				}
			}
			// aarif @@@@@@@@@@@@@@@@ allottoId = allotto;
			String demoTicketSeries = getTicketSeries("HDM", deptid, clientConnection);
			boolean exist_ticket;
			String feedbackBy, ticket_no, allotto, open_date, open_time, allot_to_mobno, feedStatus, id;
			if (demoTicketSeries != null)
			{
				ticketno = demoTicketSeries.split("#")[0];
			}

			System.out.println("for length:::" + bed_number.length());
			System.out.println("for matches:::" + bed_number.matches("[0-9]+"));
			if (bed_number != null && bed_number.length() == 4 && bed_number.matches("[0-9]+"))
			{
				locationA = getLocation(bed_number, clientConnection);
			} else if (bed_number != null && bed_number.length() == 5)
			{
				if (bed_number.charAt(4) == 'D' || bed_number.charAt(4) == 'd' || bed_number.charAt(4) == 'S' || bed_number.charAt(4) == 's')
				{
					bed_number = bed_number.substring(0, 4);
					locationA = getLocation(bed_number, clientConnection);
				}

			}

			if (locationA != null)
			{
				allottoId = allottoGet(subCotegory, clientConnection, "1", "y", locationA, "14"); // hardcode
			}

			// aarif
			// since request always goes to
			// cleaning department
			// level is harcoded by "1" since first time ticket will lodge in
			// level 1 cotegory by aarif

			// System.out.println("ticket_number  +"+ticketno+"   alootedID  $$ "+allottoId);
			if (ticketno != null && !ticketno.equalsIgnoreCase("") && !ticketno.equalsIgnoreCase("NA") && allottoId != null && !allottoId.equalsIgnoreCase(""))
			{
				exist_ticket = true;
				String qry = "SELECT ticket_no,emp.empName,emp.mobOne,feed.open_date,feed.open_time,feed.id,feed.status FROM feedback_status_new as feed LEFT JOIN employee_basic as emp on emp.id=feed.allot_to WHERE feed.subCatg='" + subCotegory + "' AND feed.location='" + locationA + "' AND feed.status IN('Pending','Snooze')";
				// System.out.println("509  "+qry);
				List existFlag = cot.executeAllSelectQuery(qry, clientConnection);
				if (existFlag != null && existFlag.size() > 0)
				{
					exist_ticket = false;
					Object[] obj = (Object[]) existFlag.get(0);
					feedbackBy = DateUtil.makeTitle("discharge");
					ticket_no = obj[0].toString();
					allotto = obj[1].toString();
					open_date = DateUtil.convertDateToIndianFormat(obj[3].toString());
					open_time = obj[4].toString().substring(0, 5);
					allot_to_mobno = obj[2].toString();
					feedStatus = obj[6].toString();
					id = obj[5].toString();
				}
				// System.out.println("exist_ticket  $$  _"+exist_ticket);
				if (exist_ticket)
				{
					InsertDataTable ob = new InsertDataTable();
					List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
					ob.setColName("ticket_no");
					ob.setDataName(ticketno);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("feed_by");
					ob.setDataName(feedby);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("feed_by_mobno");
					ob.setDataName(feedbymob);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("feed_by_emailid");
					ob.setDataName(feedbyemailid);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("by_dept_subdept");
					ob.setDataName(by_dept_subdept);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("subcatg");
					ob.setDataName(subCotegory);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("feed_brief");
					ob.setDataName(mailText);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("to_dept_subdept");
					ob.setDataName(deptid);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("allot_to");
					ob.setDataName(allottoId.trim().split(",")[0]);
					insertData.add(ob);

					ob = new InsertDataTable();
					// logger.info("Escalation_date TO database: " +
					// escalation_date);
					ob.setColName("escalation_date");
					ob.setDataName(escalation_date);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("escalation_time");
					// logger.info("Escalation_time TO database: " +
					// escalation_time);
					ob.setDataName(escalation_time);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("open_date");
					ob.setDataName(DateUtil.getCurrentDateUSFormat());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("open_time");
					ob.setDataName(DateUtil.getCurrentTime());
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("location");
					ob.setDataName(locationA);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("level");
					ob.setDataName(tolevel);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Pending");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("non_working_time");
					ob.setDataName(non_working_timing);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("Addr_date_time");
					ob.setDataName(Address_Date_Time);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("pending_alert");
					ob.setDataName("unsent");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("resolve_alert");
					ob.setDataName("unsent");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("feed_registerby");
					ob.setDataName("discharge");
					insertData.add(ob);

					status1 = cot.insertDataReturnId("feedback_status_new", insertData, clientConnection);
					insertData.clear();
					if (status1 > 0)
					{
						ob = new InsertDataTable();
						ob.setColName("feedId");
						ob.setDataName(status1);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("action_by");
						ob.setDataName("discharge");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("status");
						ob.setDataName("Pending");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("action_date");
						ob.setDataName(DateUtil.getCurrentDateUSFormat());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("action_time");
						ob.setDataName(DateUtil.getCurrentTime());
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("previous_allot_to");
						ob.setDataName(allottoId.trim().split(",")[0]);
						insertData.add(ob);

						cot.insertIntoTable("feedback_status_history", insertData, clientConnection);

						List data = HUH.getFeedbackDetail("", "SD", "Pending", String.valueOf(status1), clientConnection);
						if (data != null && !data.isEmpty())
						{
							fbp = HUH.setFeedbackDataValues(data, "Pending", "");
							feedbackBy = DateUtil.makeTitle(fbp.getFeed_registerby());
							ticket_no = fbp.getTicket_no();
							allotto = fbp.getFeedback_allot_to();
							allot_to_mobno = fbp.getMobOne();
							escTime = DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + "," + fbp.getEscalation_time().substring(0, 5);

							// printTicket(ticketno,"TicketNo");
						}
						if (fbp != null)
						{
							// mapEscalation(""
							// +
							// fbp.getId(),
							// fbp.getLevel(),
							// fbp.getEscalation_date(),
							// fbp.getEscalation_time(),
							// connectionSpace);
							if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && !fbp.getMobOne().startsWith("NA") && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
							{
								String levelMsg = "Open: " + fbp.getTicket_no() + ", Assign To: " + fbp.getFeedback_allot_to() + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + " " + fbp.getEscalation_time() + ", Call Desc: " + mailText + ", Loc: " + fbp.getRoomNo() + ", Brief: " + mailText + ".";
								// System.out.println("levelMs  $$  "+levelMsg);
								CH.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticketno, "Pending", "0", "HDM", clientConnection);
								System.out.println("<<<<<<<<<<<<<<<<Messege Added!!!>>>>>>>>>>>>");
							}
							if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals("") && !fbp.getEmailIdOne().equals("NA"))
							{
								/*
								 * mailText = HUH.getConfigMessage(fbp,
								 * "Open Feedback Alert", "Pending", deptLevel,
								 * true);
								 */

								// to send email uncomment above aarif
								// MMC.addMailWithCC(fbp.getFeedback_allot_to(),
								// fbp.getFeedback_to_dept(),
								// fbp.getEmailIdOne(),
								// "Open Feedback Alert",
								// mailText,
								// ticketno,
								// "Pending",
								// "0",
								// "",
								// "HDM",ccId);
							}
						}
					}

				}

			}

		}
		return status1;
	}

	public void getNextEscalationDateTime(String adrr_time, String res_time, String todept, String module, SessionFactory connectionSpace)
	{
		String startTime = "", endTime = "", callflag = "";
		String dept_id = todept;
		EscalationHelper EH = new EscalationHelper();
		// String working_date = EH.getNextOrPreviousWorkingDate("N",
		// DateUtil.getCurrentDateUSFormat(), connectionSpace);
		String working_date = DateUtil.getCurrentDateUSFormat();
		String working_day = DateUtil.getDayofDate(working_date);
		// List of working timing data
		List workingTimingData = EH.getWorkingTimeData(module, working_day, dept_id, connectionSpace);
		if (workingTimingData != null && workingTimingData.size() > 0)
		{
			startTime = workingTimingData.get(1).toString();
			endTime = workingTimingData.get(2).toString();
			// Here we know the complaint lodging time is inside the
			// the start
			// and end time or not
			// logger.info("working_date: "+working_date+"startTime: "+startTime+" endTime: "+endTime);
			callflag = DateUtil.timeInBetween(working_date, startTime, endTime, DateUtil.getCurrentTime());
			if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("before"))
			{

				new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, startTime, adrr_time, res_time);
				// logger.info("before: " + new_escalation_datetime);
				String newdatetime[] = new_escalation_datetime.split("#");
				// Check the date time is lying inside the time
				// block
				boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
				if (flag)
				{
					non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
				} else
				{
					non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
					non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
					String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), startTime, newdatetime[0], newdatetime[1]);
					String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), startTime, working_date, endTime);
					String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
					workingTimingData.clear();

					workingTimingData = EH.getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						startTime = workingTimingData.get(1).toString();
						String left_time = workingTimingData.get(4).toString();
						String final_date = workingTimingData.get(5).toString();
						new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
						non_working_timing = workingTimingData.get(3).toString();
					}
				}
			} else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("middle"))
			{
				new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, DateUtil.getCurrentTime(), adrr_time, res_time);
				// logger.info("middle newdate_AfterEscRoaster: " +
				// new_escalation_datetime);
				String newdatetime[] = new_escalation_datetime.split("#");
				boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
				if (flag)
				{
					non_working_timing = "00:00";
				} else
				{
					non_working_timing = DateUtil.addTwoTime(non_working_timing, DateUtil.time_difference(working_date, endTime, working_date, "24:00"));
					String timeDiff1 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), newdatetime[0], newdatetime[1]);
					String timeDiff2 = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, endTime);
					String main_difference = DateUtil.getTimeDifference(timeDiff1, timeDiff2);
					workingTimingData.clear();

					workingTimingData = EH.getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
					if (workingTimingData != null && workingTimingData.size() > 0)
					{
						startTime = workingTimingData.get(1).toString();
						String left_time = workingTimingData.get(4).toString();
						String final_date = workingTimingData.get(5).toString();
						new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
						non_working_timing = workingTimingData.get(3).toString();
					}
				}
			} else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("after"))
			{
				non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, "24:00");

				workingTimingData.clear();
				String main_difference = DateUtil.addTwoTime(adrr_time, res_time);
				workingTimingData = EH.getNextWorkingTimeData(module, DateUtil.getNewDate("day", 1, working_date), non_working_timing, main_difference, dept_id, connectionSpace);
				if (workingTimingData != null && workingTimingData.size() > 0)
				{
					startTime = workingTimingData.get(1).toString();
					String left_time = workingTimingData.get(4).toString();
					String final_date = workingTimingData.get(5).toString();
					new_escalation_datetime = DateUtil.newdate_AfterEscTime(final_date, startTime, left_time, "Y");
					non_working_timing = workingTimingData.get(3).toString();
				}
			}
		}
	}

	public String getLocation(String bed_number, SessionFactory clientConnection)
	{
		CommonOperInterface cot = new CommonConFactory().createInterface();
		String qry = "select id from floor_room_detail where roomno like '%" + bed_number + "%' limit 1";
		List location = cot.executeAllSelectQuery(qry, clientConnection);
		String loc = "";
		if (location.size() > 0 && location != null)
		{
			loc = (String) location.get(0).toString();
		}

		return loc;

	}

	public List getEmpDataByUserNameA(String uName, SessionFactory clientConnection)
	{
		List empList = new ArrayList();
		String empall = null;
		try
		{
			empall = "select emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid,emp.city,uac.userType from employee_basic as emp" + " inner join department as dept on emp.deptname=dept.id" + " inner join useraccount as uac on emp.useraccountid=uac.id where uac.name='" + uName + "'";
			// //System.out.println("Emp Data Query   "+empall);
			// }
			empList = new createTable().executeAllSelectQuery(empall, clientConnection);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}

	// ticket number generate
	public String getTicketSeries(String moduleName, String deptId, SessionFactory clientConnection)
	{
		String finalTicketSeries = "NA";
		int substringLength = 0;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "SELECT ticket_type,n_series,prefix,substring_length FROM ticket_series_conf WHERE moduleName='" + moduleName + "'";
			// System.out.println("Q1 "+query);
			List dataList = cbt.executeAllSelectQuery(query, clientConnection);

			if (dataList != null && dataList.size() > 0)
			{
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[0].toString().equalsIgnoreCase("D"))
					{
						// department wise ticket series
						query = "SELECT prefix,d_series,substring_length FROM dept_ticket_series_conf WHERE moduleName='" + moduleName + "' AND deptName=" + deptId;
						// System.out.println("Q2 "+query);

						List tempDataList = cbt.executeAllSelectQuery(query, clientConnection);
						if (tempDataList != null && tempDataList.size() > 0)
						{
							Object[] obj = (Object[]) tempDataList.get(0);
							substringLength = Integer.valueOf(obj[2].toString());
							finalTicketSeries = getMaxDeptWiseTicketSeries(moduleName, cbt, substringLength, deptId, clientConnection);
							// System.out.println("final   "+finalTicketSeries);

							if (finalTicketSeries == null)
							{

								if (obj[0] != null && !obj[0].equals(""))
								{
									finalTicketSeries = obj[0].toString();
								}
								if (obj[1] != null && !obj[1].equals(""))
								{
									finalTicketSeries = finalTicketSeries + String.valueOf(obj[1].toString());
								}
							}
							/*
							 * else { if(object[1]!=null &&
							 * !object[1].equals("") && object[2]!=null &&
							 * !object[2].equals("")) { finalTicketSeries
							 * =object[2].toString()+object[1].toString(); }
							 * else { finalTicketSeries =object[1].toString(); }
							 * }
							 */
						}
					} else if (object[0] != null && object[0].toString().equalsIgnoreCase("N"))
					{
						// Normal Ticket Series
						if (object[3] != null && !object[3].equals(""))
							substringLength = Integer.valueOf(object[3].toString());

						finalTicketSeries = getMaxNormalTicketSeries(moduleName, cbt, substringLength, clientConnection);
						if (finalTicketSeries == null)
						{
							if (object[2] != null && !object[2].equals(""))
							{
								finalTicketSeries = object[2].toString() + object[1].toString();
							} else
							{
								finalTicketSeries = object[1].toString();
							}
						}
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		// System.out.println("final2  "+finalTicketSeries);
		return finalTicketSeries + "#" + String.valueOf(substringLength);
	}

	public String getMaxDeptWiseTicketSeries(String moduleName, CommonOperInterface cbt, int substringLength, String deptId, SessionFactory clientConnection)
	{
		int maxId = 0;
		String finalString = null;
		try
		{
			if (moduleName != null)
			{
				if (moduleName.equalsIgnoreCase("COMPL"))
				{
					String query = "SELECT compid_suffix,compid_prefix FROM compliance_details  ORDER BY id DESC LIMIT 1";
					List dataList = cbt.executeAllSelectQuery(query, clientConnection);
					if (dataList != null && dataList.size() > 0)
					{
						Object[] obj = (Object[]) dataList.get(0);
						if (obj[1] != null && !obj[1].equals(""))
						{
							finalString = obj[1].toString();
						}
						if (obj[0] != null && !obj[0].equals(""))
						{
							maxId = Integer.parseInt(obj[0].toString()) + 1;
							finalString = finalString + String.valueOf(maxId);
						}
					}
				} else if (moduleName.equalsIgnoreCase("HDM"))
				{
					String ticketNo = "";
					String query = "SELECT ticket_no FROM feedback_status_new WHERE  ticket_no not Like '%R%' AND to_dept_subdept IN(" + deptId + ") ORDER BY id DESC LIMIT 1";
					List dataList = cbt.executeAllSelectQuery(query, clientConnection);
					if (dataList != null && dataList.size() > 0)
					{
						ticketNo = dataList.get(0).toString();
					}
					String prefix = null;
					int suffix = 0;
					if (substringLength > 0 && !ticketNo.equalsIgnoreCase(""))
					{
						prefix = ticketNo.substring(0, substringLength);
						suffix = Integer.valueOf(ticketNo.substring(substringLength, ticketNo.length())) + 1;
						finalString = prefix + String.valueOf(suffix);

					}
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return finalString;
	}

	public String getMaxNormalTicketSeries(String moduleName, CommonOperInterface cbt, int substringLength, SessionFactory clientConnection)
	{
		int maxId = 0;
		String finalString = null;
		try
		{
			if (moduleName != null)
			{
				if (moduleName.equalsIgnoreCase("COMPL"))
				{
					String query = "SELECT compid_suffix,compid_prefix FROM compliance_details  ORDER BY id DESC LIMIT 1";
					List dataList = cbt.executeAllSelectQuery(query, clientConnection);
					if (dataList != null && dataList.size() > 0)
					{
						Object[] obj = (Object[]) dataList.get(0);
						if (obj[1] != null && !obj[1].equals(""))
						{
							finalString = obj[1].toString();
						}
						if (obj[0] != null && !obj[0].equals(""))
						{
							maxId = Integer.parseInt(obj[0].toString()) + 1;
							finalString = finalString + String.valueOf(maxId);
						}
					}
				} else if (moduleName.equalsIgnoreCase("HDM"))
				{
					String ticketNo = "";
					String query = "SELECT ticket_no FROM feedback_status WHERE moduleName='" + moduleName + "' ORDER BY id DESC LIMIT 1";
					List dataList = cbt.executeAllSelectQuery(query, clientConnection);
					if (dataList != null && dataList.size() > 0)
					{
						ticketNo = dataList.get(0).toString();

					}
					String prefix = null;
					int suffix = 0;
					if (substringLength > 0)
					{
						prefix = ticketNo.substring(0, substringLength);
						suffix = Integer.valueOf(ticketNo.substring(substringLength, ticketNo.length())) + 1;
						finalString = prefix + String.valueOf(suffix);
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return finalString;
	}

	public String allottoGet(String subCotegory, SessionFactory clientConnection, String level, String escNeed, String floor_no, String dept_subDept)
	{

		try
		{
			List<Integer> list = new ArrayList<Integer>();

			StringBuilder query = new StringBuilder();
			String allotedto = "";
			query.append("select distinct emp.id from employee_basic as emp");
			query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
			query.append(" inner join subdepartment sub_dept on contacts.forDept_id = sub_dept.id ");
			query.append(" inner join feedback_type fb on fb.dept_subdept = sub_dept.id");
			query.append(" inner join feedback_category catt on catt.fbType = fb.id");
			query.append(" inner join feedback_subcategory subCat on subCat.categoryName = catt.id");
			query.append(" inner join department dept on sub_dept.deptid = dept.id ");
			query.append(" inner join emp_wing_mapping esc on esc.empName =contacts.emp_id  ");
			query.append(" inner join wings_detail wing on wing.id = esc.wingsname ");
			query.append(" inner join floor_room_detail room on room.wingsname = wing.id ");
			query.append(" inner join shift_with_emp_wing shift on shift.id = esc.shiftId ");
			query.append(" where  contacts.level='" + level + "' and contacts.work_status='0' and contacts.moduleName='HDM'");
			query.append(" and shift.fromShift<='" + DateUtil.getCurrentTime() + "' and shift.toShift >'" + DateUtil.getCurrentTime() + "'");
			if ("Y".equalsIgnoreCase(escNeed))
			{
				// query.append(" and roaster.floor='" + floor +
				// "'  and dept.id=(select deptid from subdepartment where id='"
				// + dept_subDept + "')");
				query.append(" and room.id='" + floor_no + "'  and  fb.dept_subdept='" + dept_subDept + "'   ");
			} else
			{
				query.append(" and sub_dept.id='" + dept_subDept + "'");
			}
			// System.out.println("query inside HelpDeskUniversalAction.java and called from allotto() method $$  "+query.toString());
			// //System.out.println("Allot to query :::::  "+query.toString());
			list = new createTable().executeAllSelectQuery(query.toString(), clientConnection);
			if (list != null && list.size() > 0)
			{
				// System.out.println("allted To :: "+allottoId);
				StringBuilder qry = new StringBuilder();
				qry.append(" SELECT emp.id FROM employee_basic AS emp ");
				qry.append(" WHERE emp.id IN " + list.toString().replace("[", "(").replace("]", ")") + " ");
				// System.out.println("query qhile tosubdept and floor is not null   $$  "+qry.toString());
				List listA = new createTable().executeAllSelectQuery(qry.toString(), clientConnection);
				if (listA != null && listA.size() > 0)
				{

					allotedto = listA.get(0).toString();
				}
			}
			return allotedto;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	private boolean createHISTable(SessionFactory clientConnection, CommonOperInterface cot)
	{
		TableColumes ob = null;
		List<TableColumes> create = new ArrayList<TableColumes>();

		ob = new TableColumes();
		ob.setColumnname("bed_refer_no");
		ob.setDatatype("varchar (30)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("insert_dateTime_client");
		ob.setDatatype("varchar (50)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("bed_number");
		ob.setDatatype("varchar (10)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("block_type");
		ob.setDatatype("varchar (50)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("username");
		ob.setDatatype("varchar (20)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("open_date");
		ob.setDatatype("varchar (20)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("open_time");
		ob.setDatatype("varchar (15)");
		ob.setConstraint("default NULL");
		create.add(ob);

		ob = new TableColumes();
		ob.setColumnname("feed_id");
		ob.setDatatype("int (10)");
		ob.setConstraint("default NULL");
		create.add(ob);

		boolean i = cot.createTable22("feedback_hdm_his_mapping", create, clientConnection);
		create.clear();

		return i;
	}

	// all setter getter

	public String getNew_escalation_datetime()
	{
		return new_escalation_datetime;
	}

	public void setNew_escalation_datetime(String new_escalation_datetime)
	{
		this.new_escalation_datetime = new_escalation_datetime;
	}

}