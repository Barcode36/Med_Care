package com.Over2Cloud.ctrl.feedback.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.TreeMap;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.CustomerPojo;
import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.ctrl.WorkingHrs.WorkingHourAction;
import com.Over2Cloud.ctrl.feedback.EscalationActionControlDao;
import com.Over2Cloud.ctrl.feedback.LodgeFeedbackHelper;
import com.Over2Cloud.ctrl.feedback.OpenTicketForFeedbackModes;
import com.Over2Cloud.ctrl.feedback.OpenTicketsForDept;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojo;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;

class ValueComparator implements Comparator<String>
{

	Map<String, String> base;

	public ValueComparator(Map<String, String> base)
	{
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with
	// equals.
	public int compare(String a, String b)
	{
		return a.compareTo(b);

	}
}

public class ActivityBoardHelper
{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
	private static FeedbackUniversalAction universalAction = new FeedbackUniversalAction();
	private static FeedbackUniversalHelper FUH = new FeedbackUniversalHelper();
	private static StringBuilder ratingBuilder = new StringBuilder();

	public List<ActivityPojo> getActivityDetailsRating(String feedBack, String subCat, String catg, String dept, String feedBy, String ticket_no, String mode, String status, String fromDate, String toDate, String wildsearch, String clientId, String userName, String dept_subdept_id, String loggedEmpId, String userType, int count, String feedDataId, SessionFactory connectionSpace)
	{
		List<ActivityPojo> pojoList = new ArrayList<ActivityPojo>();
		ratingBuilder.delete(0, ratingBuilder.length());
		ratingBuilder.append("select feedback.clientId,feedback.feed_by,feedback.location, " + "feedback.via_from,feedback.open_date,feedback.open_time,feedback.status as fstatus, ");
		ratingBuilder.append("feedback.ticket_no as ticket_no, ");
		ratingBuilder.append("dept2.deptName as todept,emp.empName as allot_to,feedtype.fbType,catg.categoryName, ");
		ratingBuilder.append("subcatg.subCategoryName,feedback.feed_brief,");
		ratingBuilder.append("feedback.level as ropen_tat,subcatg.needEsc,feedback.feed_registerby,feedback.id,subcatg.id as subCatId,feedback.patientId,feedback.stage ");
		ratingBuilder.append(" from feedback_status_feed_paperRating as feedback");
		ratingBuilder.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
		ratingBuilder.append(" inner join department dept2 on feedback.to_dept_subdept= dept2.id");
		ratingBuilder.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id");
		ratingBuilder.append(" inner join feedback_category catg on subcatg.categoryName = catg.id");
		ratingBuilder.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id");
		ratingBuilder.append(" where feedback.id !='0' and feedback.moduleName='FM' ");

		// code to show hide rating
		ratingBuilder.append(" and (feedback.feed_brief not like '%Good')"); // for
																				// 3
		ratingBuilder.append(" and (feedback.feed_brief not like '%Very Good')"); // for
																					// 4
		ratingBuilder.append(" and (feedback.feed_brief not like '%Excellent')"); // for
																					// 5
		ratingBuilder.append(" and (feedback.feed_brief not like '%yes')");

		if (userType != null && userType.equalsIgnoreCase("M"))
		{

		}
		else if (userType != null && userType.equalsIgnoreCase("H"))
		{
			List departList = universalAction.getLoggedInEmpForDept(loggedEmpId, dept_subdept_id, "FM", connectionSpace);
			if (departList.size() > 0)
			{
				ratingBuilder.append(" and feedback.to_dept_subdept in " + departList.toString().replace("[", "(").replace("]", ")") + "");
			}
		}
		else
		{
			if (userName != null)
			{
				ratingBuilder.append(" and (feedback.feed_registerby='" + userName + "' or feedback.allot_to='" + loggedEmpId + "')");
			}
		}
		if (clientId != null && !clientId.equalsIgnoreCase(""))
		{
			ratingBuilder.append(" and feedback.clientId='" + clientId + "'");
		}
		else if (wildsearch != null && !wildsearch.equalsIgnoreCase(""))
		{

			ratingBuilder.append(" and feedback.feed_by like '" + wildsearch + "%'");
		}
		else
		{

			if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
			{
				String str[] = fromDate.split("-");
				if (str[0] != null && str[0].length() > 3)
				{
					ratingBuilder.append(" and feedback.open_date between '" + ((fromDate)) + "' and '" + ((toDate)) + "'");
				}
				else
				{
					ratingBuilder.append(" and feedback.open_date between '" + (DateUtil.convertDateToUSFormat(fromDate)) + "' and '" + (DateUtil.convertDateToUSFormat(toDate)) + "'");
				}
			}
			if (status != null && !status.equalsIgnoreCase("-1"))
			{
				if(status != null && !status.equalsIgnoreCase("All"))
				{
					ratingBuilder.append(" and feedback.status= '" + status + "'");
				}
			}
			else
			{
				ratingBuilder.append(" and feedback.status='Pending'");
			}
			/*
			 * if(getPatType()!=null && !getPatType().equalsIgnoreCase("-1")) {
			 * query.append(" and compType= '"+getPatType()+" '"); }
			 */
			if (mode != null && !mode.equalsIgnoreCase("-1"))
			{
				ratingBuilder.append(" and feedback.via_from= '" + mode + " '");
			}
			if (ticket_no != null && !ticket_no.equalsIgnoreCase("-1"))
			{
				ratingBuilder.append(" and feedback.ticket_no= '" + ticket_no + " '");
			}

			if (feedBy != null && !feedBy.equalsIgnoreCase("-1"))
			{
				ratingBuilder.append(" and feedback.feed_by= '" + feedBy + " '");
			}

			if (dept != null && !dept.equalsIgnoreCase("-1"))
			{
				ratingBuilder.append(" and feedback.to_dept_subdept='" + dept + " '");
			}

			if (catg != null && !catg.equalsIgnoreCase("-1"))
			{
				ratingBuilder.append(" and catg.id='" + catg + " '");
			}

			if (subCat != null && !subCat.equalsIgnoreCase("-1"))
			{
				ratingBuilder.append(" and subcatg.id='" + subCat + " '");
			}

			if (feedBack != null && !feedBack.equalsIgnoreCase("-1"))
			{
				if (feedBack.equalsIgnoreCase("Negative"))
				{
					ratingBuilder.append(" and subcatg.needEsc= 'Y'");
				}
				else if (feedBack.equalsIgnoreCase("Positive"))
				{
					ratingBuilder.append(" and subcatg.needEsc= 'N'");
				}
			}
		}
	//	System.out.println("query>>>>>is as <<<<<>" + ratingBuilder);
		List feedList = cbt.executeAllSelectQuery(ratingBuilder.toString(), connectionSpace);
		if (feedList != null && feedList.size() > 0)
		{
			for (Iterator iterator = feedList.iterator(); iterator.hasNext();)
			{
				ActivityPojo pojo = new ActivityPojo();
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					pojo.setId(count++);
					if (object[0] != null)
					{
						pojo.setFeedDataId(feedDataId);
						// FeedbackDataID
						// pojo.setId(Integer.parseInt(object[0].toString()));
						pojo.setClientId(object[0].toString());
				//		pojo.setPatientId(getPatientIdByClientId(pojo.getClientId(),connectionSpace));
						pojo.setHistory(getFeedbackCount(pojo.getClientId(), connectionSpace));
					}
					else
					{
						pojo.setClientId("NA");
					}
					if (object[1] != null)
					{
						pojo.setClientName(object[1].toString());
					}
					else
					{
						pojo.setClientName("NA");
					}
					if (object[2] != null)
					{
						pojo.setCenterCode(object[2].toString());
					}
					else
					{
						pojo.setCenterCode("NA");
					}
					if (object[3] != null)
					{
						pojo.setMode(object[3].toString());
					}
					else
					{
						pojo.setMode("NA");
					}
					if (object[4] != null && object[5] != null)
					{
						pojo.setDateTime(DateUtil.convertDateToIndianFormat(object[4].toString()) + ", " + object[5].toString().substring(0, 5));
					}
					else
					{
						pojo.setDateTime("NA");
					}
					if (object[6] != null)
					{
						pojo.setFstatus(object[6].toString());
					}
					else
					{
						pojo.setFstatus("NA");
					}
					if (object[7] != null)
					{
						pojo.setTicket_no(object[7].toString());
					}
					else
					{
						pojo.setTicket_no("NA");
					}
					if (object[8] != null)
					{
						pojo.setDept(object[8].toString());
					}
					else
					{
						pojo.setDept("NA");
					}
					if (object[9] != null)
					{
						pojo.setAllot_to(object[9].toString());
					}
					else
					{
						pojo.setAllot_to("NA");
					}
					if (object[10] != null)
					{
						pojo.setFeedType(object[10].toString());
					}
					else
					{
						pojo.setFeedType("NA");
					}
					if (object[11] != null)
					{
						pojo.setCat(object[11].toString());
					}
					else
					{
						pojo.setCat("NA");
					}
					if (object[12] != null)
					{
						pojo.setSubCat(object[12].toString());
					}
					else
					{
						pojo.setSubCat("NA");
					}
					if (object[13] != null)
					{
						pojo.setBrief(object[13].toString());
					}
					else
					{
						pojo.setBrief("NA");
					}
					if (object[14] != null)
					{
						pojo.setLevel(object[14].toString());
					}
					else
					{
						pojo.setLevel("NA");
					}
					if (object[15] != null)
					{
						if (object[15].toString().equalsIgnoreCase("Y"))
						{
							pojo.setTargetOn("Negative");
						}
						else
						{
							pojo.setTargetOn("Positive");
						}
					}
					else
					{
						pojo.setTargetOn("Positive");
					}
					if (object[16] != null)
					{
						String name = getEmpNameByUserId(object[16].toString(), connectionSpace);
						if (name != null)
						{
							pojo.setFeedRegBy(name);
						}
						else
						{
							pojo.setFeedRegBy(object[16].toString());
						}
					}
					else
					{
						pojo.setFeedRegBy("NA");
					}
					if (object[17] != null)
					{
						pojo.setFeedStatId(object[17].toString());
						pojo = getFeedbackReopenedDate(pojo, connectionSpace);
					}
					else
					{
						pojo.setFeedStatId("0");
					}
					if (object[18] != null)
					{
						pojo.setSubCatId(object[18].toString());
					}
					else
					{
						pojo.setSubCatId("NA");
					}

					if (object[19] != null)
					{
						pojo.setPatientId(object[19].toString());
					}
					else
					{
						pojo.setPatientId("NA");
					}
					
					if (object[20] != null)
					{
						pojo.setStage(object[20].toString());
					}
					else
					{
						pojo.setStage("NA");
					}
					
					pojo.setRatingFlag("1");
					pojoList.add(pojo);
				}
			}
		}
		return pojoList;
	}

	public String getTicketAllotToEmpId(String toDept, String tolevel, String location, String deptLevel, SessionFactory connectionSpace)
	{
		String allottoId = "";

		if (toDept != null)
		{
			try
			{
				List allotto = null;
				List allotto1 = null;

				List<String> one = new ArrayList<String>();
				List<String> two = new ArrayList<String>();
				List<String> two_new = new ArrayList<String>();

				boolean bedMapping = FUH.checkTable("bed_mapping", connectionSpace);
			//	System.out.println("Bedmap[ping " + bedMapping);
				if (bedMapping && toDept != null && !toDept.equals(""))
				{
					String floor_status = FUH.getFloorStatus(toDept, connectionSpace);
					//System.out.println(">>>>>>>>>" + floor_status);
					if (floor_status != null && !floor_status.equalsIgnoreCase("") && floor_status.equalsIgnoreCase("B") && location != null && !location.equalsIgnoreCase(""))
					{
						allotto = FUH.getRandomEmp4BedSpecific(toDept, location, tolevel.substring(5), connectionSpace);
					}
					else
					{
						allotto = universalAction.getRandomEmp4Escalation(toDept, "FM", tolevel.substring(5), "", "", connectionSpace);
					}
				}
				else
				{
					allotto = universalAction.getRandomEmp4Escalation(toDept, "FM", tolevel.substring(5), "", "", connectionSpace);
				}

				if (allotto == null || allotto.size() == 0)
				{
					String newToLevel = "";
					newToLevel = "" + (Integer.parseInt(tolevel.substring(5)) + 1);
					allotto = universalAction.getRandomEmp4Escalation(toDept, "FM", newToLevel, "", "", connectionSpace);
					if (allotto == null || allotto.size() == 0)
					{
						newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
						allotto = universalAction.getRandomEmp4Escalation(toDept, "FM", newToLevel, "", "", connectionSpace);
						if (allotto == null || allotto.size() == 0)
						{
							newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
							allotto = universalAction.getRandomEmp4Escalation(toDept, "FM", newToLevel, "", "", connectionSpace);
						}
					}
				}
				allotto1 = FUH.getRandomEmployee(toDept, deptLevel, tolevel.substring(5), allotto, "FM", connectionSpace);
				if (allotto != null && allotto.size() > 0)
				{
					for (Object object : allotto)
					{
						one.add(object.toString());
					}
				}
				if (allotto1 != null && allotto1.size() > 0)
				{
					for (Object object : allotto1)
					{
						two.add(object.toString());
					}
				}

				if (one != null && one.size() > two.size())
				{
					one.removeAll(two);
					if (one.size() > 0)
					{
						for (Iterator iterator = one.iterator(); iterator.hasNext();)
						{
							Object object = (Object) iterator.next();
							allottoId = object.toString();
							break;
						}
					}
				}
				else
				{
					List pending_alloted = universalAction.getPendingAllotto(toDept, "FM");
					if (pending_alloted != null && pending_alloted.size() > 0)
					{
						for (Object object : pending_alloted)
						{
							two_new.add(object.toString());
						}
					}
					if (two_new != null && two_new.size() > 0)
					{
						one.removeAll(two_new);
					}
					if (one != null && one.size() > 0)
					{
						allottoId = universalAction.getRandomEmployee("FM", one);
					}
					else
					{
						allottoId = universalAction.getRandomEmployee("FM", allotto);
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return allottoId;
	}

	public String getLatestTicketNoForRating(SessionFactory connectionSpace,String ticketNo)
	{
		String pre=ticketNo.substring(0,1);
		String post=ticketNo.substring(1,ticketNo.length());
		post=String.valueOf(Integer.parseInt(post)+1);
		String newTicketNo=pre+post;
		return newTicketNo;
	}
	
	public boolean addFeedbackRating(String ticketno,int feedDataId, String location, String userName, String viaFrom, String allottoId, String toDept, String subCategoryId, String by_dept_subdept, String emailId, String clientId, String feedby, String openByMobNo, String mobileno, String openByEmailId,String patientId, SessionFactory connectionSpace)
	{
		List subCategoryList = FUH.getAllData("feedback_subcategory", "id", subCategoryId, "subCategoryName", "ASC", connectionSpace);
		String feed_brief = null;
		String adrr_time = "", tolevel = "", needesc = "", res_time = "";
		if (subCategoryList != null && subCategoryList.size() > 0)
		{
			for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();)
			{
				Object[] objectCol = (Object[]) iterator.next();
				if (objectCol[3] == null)
				{
					feed_brief = "NA";
				}
				else
				{
					feed_brief = objectCol[3].toString();
				}
				if (objectCol[4] == null)
				{
					adrr_time = "06:00";
				}
				else
				{
					adrr_time = objectCol[4].toString();
				}
				if (objectCol[5] == null)
				{
					res_time = "10:00";
				}
				else
				{
					res_time = objectCol[5].toString();
				}

				if (objectCol[8] == null)
				{
					tolevel = "Level1";
				}
				else
				{
					tolevel = objectCol[8].toString();
				}
				if (objectCol[9] == null)
				{
					needesc = "Y";
				}
				else
				{
					needesc = objectCol[9].toString();
				}
			}
		}
		String escalation_date = "NA", escalation_time = "NA", resolution_date = "NA", resolution_time = "NA", level_resolution_date = "NA", level_resolution_time = "NA", non_working_timing = "00:00", Address_Date_Time = "NA";
		WorkingHourAction WHA = new WorkingHourAction();
		String date = DateUtil.getCurrentDateUSFormat();
		String time = DateUtil.getCurrentTimeHourMin();
		if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
		{
			List<String> dateTime = WHA.getAddressingEscTime(connectionSpace, cbt, adrr_time, res_time, needesc, date, time, "FM");
			Address_Date_Time = dateTime.get(0) + " " + dateTime.get(1);
			escalation_date = dateTime.get(2);
			escalation_time = dateTime.get(3);
			// System.out.println(dateTime.get(0) + " " + dateTime.get(1) + " "
			// + dateTime.get(2) + " " + dateTime.get(3));
		}

		boolean added = false;

		InsertDataTable ob = new InsertDataTable();

		List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
		ob.setColName("ticket_no");
		ob.setDataName(ticketno);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("feedDataId");
		ob.setDataName(feedDataId);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("clientId");
		ob.setDataName(clientId);
		insertData.add(ob);
		
		ob = new InsertDataTable();
		ob.setColName("patientId");
		ob.setDataName(patientId);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("feed_by");
		ob.setDataName(feedby);
		insertData.add(ob);
		// System.out.println(
		// "FeedBy is as <>>>>>>>>>>>>>>>>>>>>>>>>>>."+feedby);

		ob = new InsertDataTable();
		ob.setColName("feed_by_mobno");
		ob.setDataName(openByMobNo);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("patMobNo");
		ob.setDataName(mobileno);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("feed_by_emailid");
		ob.setDataName(openByEmailId);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("patEmailId");
		ob.setDataName(emailId);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("deptHierarchy");
		ob.setDataName("2");
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("by_dept_subdept");
		ob.setDataName(by_dept_subdept);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("subcatg");
		ob.setDataName(subCategoryId);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("feed_brief");
		ob.setDataName(feed_brief);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("to_dept_subdept");
		ob.setDataName(toDept);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("allot_to");
		ob.setDataName(allottoId);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("escalation_date");
		ob.setDataName(escalation_date);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("escalation_time");
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
		ob.setColName("level");
		ob.setDataName("Level1");
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("status");
		ob.setDataName("Pending");
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("via_from");
		ob.setDataName(viaFrom);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("feed_registerby");
		ob.setDataName(userName);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("location");
		ob.setDataName(location);
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("moduleName");
		ob.setDataName("FM");
		insertData.add(ob);

		ob = new InsertDataTable();
		ob.setColName("non_working_time");
		ob.setDataName(non_working_timing);
		insertData.add(ob);
		added = cbt.insertIntoTable("feedback_status_feed_paperRating", insertData, connectionSpace);
		return added;
	}

	public String getRatingDetails(SessionFactory connectionSpace, String feedValue)
	{
		ratingBuilder.delete(0, ratingBuilder.length());
		String str = null;
		ratingBuilder.append("select field_name from feedback_paperform_rating_configuration where field_value='" + feedValue + "' order by id desc limit 1");
	//	System.out.println("For "+ratingBuilder);
		List dataList = cbt.executeAllSelectQuery(ratingBuilder.toString(), connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			return dataList.get(0).toString();
		}
		return str;
	}

	public String[] getDeptDetailsForSubCat(String subCat, String rating, SessionFactory connectionSpace)
	{
		String str[] = new String[2];
		ratingBuilder.setLength(0);
		ratingBuilder.append(" select dpt.id,subCat.id as subCatId from department as dpt  inner join feedback_type as type on dpt.id=type.dept_subdept  inner join feedback_category as cat on type.id=cat.fbType  inner join feedback_subcategory subCat on cat.id=subCat.categoryName  where type.id!='0' ");
		if (rating != "NA" && rating != "NA")
		{
			if (rating.equalsIgnoreCase("Yes"))
			{
				ratingBuilder.append("and subCat.subCategoryName='" + subCat.trim() + " is yes' limit 1 ");
			}
			else if (rating.equalsIgnoreCase("No"))
			{
				ratingBuilder.append("and subCat.subCategoryName='" + subCat.trim() + " is no' limit 1 ");
			}
			else if (Integer.parseInt(rating) == 1)
			{
				ratingBuilder.append("and subCat.subCategoryName='" + subCat.trim() + " is Poor' limit 1 ");
			}
			else if (Integer.parseInt(rating) == 2)
			{
				ratingBuilder.append("and subCat.subCategoryName='" + subCat.trim() + " is Average' limit 1 ");
			}
			else if (Integer.parseInt(rating) == 3)
			{
				ratingBuilder.append("and subCat.subCategoryName='" + subCat.trim() + " is Good' limit 1 ");
			}
			else if (Integer.parseInt(rating) == 4)
			{
				ratingBuilder.append("and subCat.subCategoryName='" + subCat.trim() + " is Very Good' limit 1 ");
			}
			else if (Integer.parseInt(rating) == 5)
			{
				ratingBuilder.append("and subCat.subCategoryName='" + subCat.trim() + " is Excellent' limit 1 ");
			}
		//	System.out.println("Before Add >>>" + ratingBuilder);
			List data = cbt.executeAllSelectQuery(ratingBuilder.toString(), connectionSpace);
			if (data != null && data.size() > 0)
			{
				for (Iterator iterator = data.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						str[0] = object[0].toString();
						str[1] = object[1].toString();
						return str;
					}
				}
			}
		}
		return str;
	}

	public Map<String, String> getAllTicketStatus(SessionFactory connectionSpace)
	{
		Map<String, String> map = new HashMap<String, String>();
		List dataList = cbt.executeAllSelectQuery("SELECT distinct status from feedback_status where moduleName='FM' order by status", connectionSpace);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				String str = (String) iterator.next();
				if (str != null)
				{
					map.put(str, str);
				}
			}
		}
		return map;
	}

	public TreeMap<String, String> getAllServiceDepts(SessionFactory connectionSpace)
	{
		TreeMap<String, String> sorted_map = new TreeMap<String, String>();
		Map<String, String> map = new TreeMap<String, String>();
		List depart = cbt.executeAllSelectQuery("SELECT dept.id,dept.deptName from department as dept inner join feedback_status as stat on dept.id=stat.to_dept_subdept where stat.moduleName='FM' order by dept.deptName desc", connectionSpace);
		if (depart != null && depart.size() > 0)
		{
			ValueComparator bvc = new ValueComparator(map);

			for (Iterator iterator = depart.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					map.put(object[0].toString(), object[1].toString());
				}
			}
			sorted_map = new TreeMap<String, String>(bvc);
			sorted_map.putAll(map);
		}
		return sorted_map;
	}

	public List getLodgerEmployeeDetailByUserName(SessionFactory connection, CommonOperInterface cbt, String userName)
	{
		List dataList = null;
		try
		{
			StringBuilder query = new StringBuilder();
			String encryUserId = Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
			query.append("SELECT emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName FROM employee_basic AS emp ");
			query.append(" INNER JOIN department AS dept ON dept.id = emp.deptname ");
			query.append(" INNER JOIN useraccount As useracc ON useracc.id = emp.useraccountid");
			query.append(" WHERE useracc.userID='" + encryUserId + "'");
			// System.out.println("@###@### " + query.toString());
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

	public List getTicketCycle(SessionFactory connection, CommonOperInterface cbt, String compliantId, String ticketNo,String activityFlag)
	{
		List tempList = new ArrayList();
		List statusDataList=new ArrayList();
		StringBuilder query = new StringBuilder();
		try
		{
			if(activityFlag.equalsIgnoreCase("1"))
			{
				query.append("select status from feedback_status_feed_paperRating where id =" + compliantId);
			}
			else
			{
				query.append("select status from feedback_status where id =" + compliantId);
			}
			
			List list= cbt.executeAllSelectQuery(query.toString(), connection);
			query.delete(0, query.length());
			query.append(" SELECT feedback.status,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration, ");
			query.append(" feedback.sn_reason,feedback.hp_date,feedback.hp_time,feedback.hp_reason, ");
			query.append(" feedback.ig_date,feedback.ig_time,feedback.ig_reason, ");
			query.append(" feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,emp.empName,feedback.resolve_remark,feedback.spare_used, ");
			query.append(" feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason, ");
			query.append(" feedback.action_by,feedback.close_mode,feedback.ignore_mode,feedback.hp_mode, ");
			query.append(" feedback.open_date,feedback.open_time,cat.categoryName,subCat.subCategoryName,feedback.feed_brief,dept.deptName,feedback.ticket_no,feedback.feed_registerby,feedback.subCatg,  ");
			query.append(" feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid ");
			if(activityFlag.equalsIgnoreCase("1"))
			{
				query.append(" FROM feedback_status_feed_paperRating AS feedback ");
			}
			else
			{
				query.append(" FROM feedback_status AS feedback ");
			}
			query.append(" inner join department as dept on feedback.to_dept_subdept=dept.id ");
			query.append(" inner join feedback_subcategory as subCat on feedback.subCatg=subCat.id ");
			query.append(" inner join feedback_category as cat on subCat.categoryName=cat.id ");
			query.append(" LEFT JOIN employee_basic AS emp ON emp.id = feedback.resolve_by where  feedback.moduleName='FM' ");
			query.append(" and feedback.id =" + compliantId);
	//		System.out.println("*******Query: " + query.toString());
			tempList = cbt.executeAllSelectQuery(query.toString(), connection);
			statusDataList.add(tempList);
			if(list!=null && list.size()>0)
			{
				String status=(String) list.get(0);
				if(status.equalsIgnoreCase("Ticket Opened"))
				{
					String subcat=null;
					if (tempList != null && tempList.size() > 0)
					{
						for (Iterator iterator = tempList.iterator(); iterator.hasNext();)
						{
							Object object[] = (Object[]) iterator.next();
							subcat=object[34].toString();
						}
					}
					query.delete(0, query.length());
					query.append(" SELECT feedback.status,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration, ");
					query.append(" feedback.sn_reason,feedback.hp_date,feedback.hp_time,feedback.hp_reason, ");
					query.append(" feedback.ig_date,feedback.ig_time,feedback.ig_reason, ");
					query.append(" feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,emp.empName,feedback.resolve_remark,feedback.spare_used, ");
					query.append(" feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason, ");
					query.append(" feedback.action_by,feedback.close_mode,feedback.ignore_mode,feedback.hp_mode, ");
					query.append(" feedback.open_date,feedback.open_time,cat.categoryName,subCat.subCategoryName,feedback.feed_brief,dept.deptName,feedback.ticket_no,feedback.feed_registerby  ");
					query.append(" FROM feedback_status AS feedback ");
					query.append(" inner join department as dept on feedback.to_dept_subdept=dept.id ");
					query.append(" inner join feedback_subcategory as subCat on feedback.subCatg=subCat.id ");
					query.append(" inner join feedback_category as cat on subCat.categoryName=cat.id ");
					query.append(" LEFT JOIN employee_basic AS emp ON emp.id = feedback.resolve_by where  feedback.moduleName='FM' ");
					query.append(" and feedback.id!=0");
					if(activityFlag.equalsIgnoreCase("0"))
					{
						query.append(" and feedback.ticket_no like '"+ticketNo+"%' and feedback.status not IN ('Ticket Opened')" );
					}
					else
					{
						query.append(" and feedback.subCatg =" +subcat+" and  feedback.ticket_no like '"+ticketNo+"%' " );
					}
					
					//System.out.println("query to>>>>>"+query);
					tempList=cbt.executeAllSelectQuery(query.toString(), connection);
					statusDataList.add(tempList);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusDataList;
	}

	public List getStageData(SessionFactory connection, CommonOperInterface cbt, String compliantId, String ticketNo,String activityFlag)
	{
		List tempList = new ArrayList();
		List statusDataList=new ArrayList();
		StringBuilder query = new StringBuilder();
		try
		{
			if(activityFlag.equalsIgnoreCase("1"))
			{
				query.append("select status from feedback_status_feed_paperRating where id =" + compliantId);
			}
			else
			{
				query.append("select status from feedback_status where id =" + compliantId);
			}
			
			List list= cbt.executeAllSelectQuery(query.toString(), connection);
			query.delete(0, query.length());
			query.append(" SELECT feedback.status,feedback.open_date,feedback.open_time,feedback.feedback_remarks,emp.empName,emp.mobOne, ");
			query.append(" dept.deptName,cat.categoryName,subCat.subCategoryName,feedback.feed_brief,feedback.ticket_no,feedback.feed_registerby,feedback.subCatg,  ");
			query.append(" feedback.feed_by,feedback.patMobNo,feedback.stage ");
			if(activityFlag.equalsIgnoreCase("1"))
			{
				query.append(" FROM feedback_status_feed_paperRating AS feedback ");
			}
			else
			{
				query.append(" FROM feedback_status AS feedback ");
			}
			query.append(" inner join department as dept on feedback.to_dept_subdept=dept.id ");
			query.append(" inner join feedback_subcategory as subCat on feedback.subCatg=subCat.id ");
			query.append(" inner join feedback_category as cat on subCat.categoryName=cat.id ");
			query.append(" LEFT JOIN employee_basic AS emp ON emp.id = feedback.allot_to where  feedback.moduleName='FM' ");
			query.append(" and feedback.id =" + compliantId);
			//System.out.println("*******Query: " + query.toString());
			tempList = cbt.executeAllSelectQuery(query.toString(), connection);
			statusDataList.add(tempList);
			if(list!=null && list.size()>0)
			{
				String status=(String) list.get(0);
				if(status.equalsIgnoreCase("Ticket Opened"))
				{
					query.delete(0, query.length());
					query.append(" SELECT feedback.status,feedback.open_date,feedback.open_time,feedback.feedback_remarks,emp.empName,emp.mobOne, ");
					query.append(" dept.deptName,cat.categoryName,subCat.subCategoryName,feedback.feed_brief,feedback.ticket_no,feedback.feed_registerby,feedback.subCatg,  ");
					query.append(" feedback.feed_by,feedback.patMobNo,feedback.stage ");
					query.append(" FROM feedback_status AS feedback ");
					query.append(" inner join department as dept on feedback.to_dept_subdept=dept.id ");
					query.append(" inner join feedback_subcategory as subCat on feedback.subCatg=subCat.id ");
					query.append(" inner join feedback_category as cat on subCat.categoryName=cat.id ");
					query.append(" LEFT JOIN employee_basic AS emp ON emp.id = feedback.allot_to where  feedback.moduleName='FM' ");
					query.append(" and feedback.id!=0");
					query.append(" and feedback.ticket_no like '"+ticketNo+"%' and feedback.status not IN ('Ticket Opened')" );
					//System.out.println("query Reopen to>>>>>"+query);
					tempList=cbt.executeAllSelectQuery(query.toString(), connection);
					statusDataList.add(tempList);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusDataList;
	}
	
	public String getRatingForCategory(String category,String subCategory,String ticketNo,SessionFactory connectionSpace) {
		// TODO Auto-generated method stub
		String rating=null;
		if(subCategory.contains(" Poor"))
		{
			rating="Rating: 1";
		}
		else if(subCategory.contains("Average"))
		{
			rating="Rating: 2";
		}
		else if(subCategory.contains("Not Satisfied"))
		{
			if(ticketNo.startsWith("S"))
			{
				rating="SMS Revert: No";
			}
			else
			{
				rating ="Patient Not Satisfied";
			}
			
		}
		else if(subCategory.contains("Patient is Satisfied"))
		{
			
			if(ticketNo.startsWith("S"))
			{
				rating="SMS Revert: Yes";
			}
			else
			{
				rating ="Patient is Satisfied";
			}
		}
		else if(subCategory.contains("Yes"))
		{
			rating="Recommendation: Yes";
		}
		else if(subCategory.contains("No"))
		{
			rating="Recommendation: No";
		}
		else if(subCategory.contains("Appreciation"))
		{
			rating="Patient Appreciated the Service";
		}
		else if(subCategory.contains("Suggestion"))
		{
			rating="Patient given the Suggestion";
		}
		else
		{
			rating="Not Satisfied";
		}
		
		return rating;
	}
	
	public ActivityPojo getFeedbackDataDetails4Rating(ActivityPojo pojo, SessionFactory connection)
	{
		StringBuilder builder = new StringBuilder(" select feed.id,feed.compType,feed.targetOn,feed.status,feed.clientId,feed.clientName,feed.centerCode,feed.openDate,feed.openTime from feedbackdata as feed ");
		builder.append(" inner join feedback_ticket as ticket on feed.id=ticket.feed_data_id ");
		builder.append(" where ticket.feed_stat_id='" + pojo.getFeedStatId() + "'");
		// System.out.println("ID of Feedbackdata" + builder);
		List dataList = cbt.executeAllSelectQuery(builder.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0] != null)
				{
					pojo.setFeedDataId(object[0].toString());
				}
				if (object[1] != null)
				{
					pojo.setCompType(object[1].toString());
				}
				if (object[2] != null)
				{
					if (object[2].toString().equalsIgnoreCase("Yes"))
					{
						pojo.setTargetOn("Positive");
					}
					else
					{
						pojo.setTargetOn("Negative");
					}
				}
				if (object[3] != null)
				{
					pojo.setFstatus(object[3].toString());
				}
				if (object[4] != null)
				{
					pojo.setClientId(object[4].toString());
				}
				if (object[5] != null)
				{
					pojo.setClientName(object[5].toString());
				}
				if (object[6] != null)
				{
					pojo.setCenterCode(object[6].toString());
				}
				if (object[7] != null && object[8] != null)
				{
					pojo.setDateTime(DateUtil.convertDateToIndianFormat(object[7].toString()) + ", " + object[8].toString().substring(0, 5));
				}
			}
		}
		return pojo;
	}

	public String getFeedbackDataId4Rating(ActivityPojo pojo, SessionFactory connection)
	{
		String feedDataId = null;
		StringBuilder builder = new StringBuilder(" select feed.id from feedbackdata as feed ");
		builder.append(" inner join feedback_ticket as ticket on feed.id=ticket.feed_data_id ");
		builder.append(" where ticket.feed_stat_id='" + pojo.getFeedStatId() + "'");
		// System.out.println("ID of Feedbackdata" + builder);
		List dataList = cbt.executeAllSelectQuery(builder.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if (object != null)
				{
					feedDataId = object.toString();
				}

			}
		}
		return feedDataId;
	}

	public ActivityPojo getFeedbackReopenedDate(ActivityPojo pojo, SessionFactory connection)
	{
		int adminDeptId = getAdminDeptId(connection);
		String reOpenedDate = null;
		/*
		 * StringBuilder builder = newStringBuilder(
		 * " select t.open_date,t.open_time,t.status,t.level from feedback_status as t where t.to_dept_subdept='"
		 * + adminDeptId +
		 * "' and exists (select feedTicketNo  from feedback_ticket as ft where ft.feed_stat_id='"
		 * + pojo.getFeedStatId() + "' and t.ticket_no like ");
		 * builder.append("'%' || ft.feedTicketNo || '%' ) limit 1;");
		 */

		/*
		 * StringBuilder builder=newStringBuilder(
		 * " select f.open_date,f.open_time,f.status,f.level from feedback_status as f "
		 * );builder.append(
		 * " inner join feedback_ticket as ft on f.id=ft.feed_stat_id ");
		 * builder.append(" where f.id='"+pojo.getFeedStatId()+"'");
		 */

		StringBuilder builder = new StringBuilder(" select f.open_date,f.open_time,f.status,f.level from feedback_status as f ");
		builder.append(" where f.id!='" + pojo.getFeedStatId() + "' and  f.ticket_no like'" + pojo.getTicket_no() + "%'");

		// System.out.println(pojo.getTicket_no()+">>>>>>>>>"+builder);
		List dataList = cbt.executeAllSelectQuery(builder.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object2 = (Object[]) iterator.next();
				if (object2[0] != null && object2[1] != null)
				{
					pojo.setReopenedon(DateUtil.convertDateToIndianFormat(object2[0].toString()) + ", " + object2[1].toString().substring(0, 5));
				}
				if (object2[2] != null)
				{
					pojo.setReopenstatus(object2[2].toString());
				}
				if (object2[3] != null)
				{
					pojo.setReOpenLevel(object2[3].toString());
				}
			}
		}
		return pojo;
	}

	public String getFeedbackCount(String clientId, SessionFactory connection)
	{
		String counter = "0";
		StringBuilder builder = new StringBuilder(" select count(*) from feedback_status where clientId='" + clientId + "'");
		List countList = cbt.executeAllSelectQuery(builder.toString(), connection);
		if (countList != null && countList.size() > 0 && countList.get(0) != null)
		{
			return countList.get(0).toString();
		}
		return "0";
	}
	
	public String getPatientIdByClientId(String clientId,SessionFactory connectionSpace) {
		// TODO Auto-generated method stub
		String patientId=null;
		StringBuilder query=new StringBuilder("");
		query.append(" select patientId from patientinfo where cr_number="+clientId+" order by id desc limit 1");
		List list=cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		if(list!=null && list.size()>0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				patientId = (String) iterator.next();
			}
		}
	
		return patientId;
	}

	public List<ActivityPojo> getAllFeedbacks(String fromDate, String toDate, SessionFactory connection)
	{
		List<ActivityPojo> list = new ArrayList<ActivityPojo>();
		StringBuilder builder = new StringBuilder("select feedback.id,feedback.ticket_no,feedback.feed_by,dept2.deptName as todept,emp.empName,catg.categoryName," + " subcatg.subCategoryName,feedback.feed_brief, " + " feedback.open_date,feedback.open_time," + " feedback.level,feedback.status,feedback.via_from," + "feedback.clientId,subcatg.needEsc from feedback_status as feedback" + " inner join employee_basic emp on feedback.allot_to= emp.id "
				+ " inner join department dept1 on feedback.by_dept_subdept= dept1.id " + " inner join department dept2 on feedback.to_dept_subdept= dept2.id" + " inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id" + " inner join feedback_category catg on subcatg.categoryName = catg.id " + " inner join feedback_type feedtype on catg.fbType = feedtype.id where feedback.id !='0' ");
		if (fromDate != null && toDate != null && !fromDate.equalsIgnoreCase("") && !toDate.equalsIgnoreCase(""))
		{
			builder.append(" and feedback.open_date between '" + fromDate + "' and '" + toDate + "'");
		}
		builder.append(" and feedback.moduleName='FM' ORDER BY feedback.id desc ");
		// System.out.println("New>>>>" + builder);
		list = cbt.executeAllSelectQuery(builder.toString(), connection);
		if (list != null && list.size() > 0)
		{
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				Object[] obj = (Object[]) iterator.next();
				if (obj != null)
				{
					ActivityPojo pojo = new ActivityPojo();
					if (obj[0] != null)
					{
						pojo.setFeedId(Integer.parseInt(obj[0].toString()));
					}
					if (obj[1] != null)
					{
						pojo.setTicket_no(obj[1].toString());
					}
					if (obj[2] != null)
					{
						pojo.setFeed_by(obj[2].toString());
					}
					if (obj[3] != null)
					{
						pojo.setDept(obj[3].toString());
					}
					if (obj[4] != null)
					{
						pojo.setAllot_to(obj[4].toString());
					}
					if (obj[5] != null)
					{
						pojo.setCat(obj[5].toString());
					}
					if (obj[6] != null)
					{
						pojo.setSubCat(obj[6].toString());
					}
					if (obj[7] != null)
					{
						pojo.setBrief(obj[7].toString());
					}

					if (obj[8] != null && obj[9] != null)
					{
						pojo.setDateTime(DateUtil.convertDateToIndianFormat(obj[8].toString()) + ", " + obj[9].toString().substring(0, 5));
					}

					if (obj[10] != null)
					{
						pojo.setTat(obj[10].toString());
					}
					if (obj[11] != null)
					{
						pojo.setFstatus(obj[11].toString());
					}
					if (obj[12] != null)
					{
						pojo.setMode(obj[12].toString());
					}
					if (obj[13] != null)
					{
						pojo.setClientId(obj[13].toString());
					}
					if (obj[14] != null)
					{
						if (obj[14].toString().equalsIgnoreCase("Y"))
						{
							pojo.setTargetOn("Negative");
						}
						else
						{
							pojo.setTargetOn("Positive");
						}
					}
				}
			}
		}
		return list;
	}

	public List<com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo> getDeptFeedTicketData(ActivityPojo pojo, String deptLevel, String uName, String userType, SessionFactory connectionSpace)
	{
		EmpBasicPojoBean userDetails = getLoggedUserDetails(connectionSpace, uName, deptLevel);
		List<ActivityPojo> pojoList = new ArrayList<ActivityPojo>();
		List data = new FeedbackUniversalAction().getFeedbackDetail("feedback_status", "", "", "", userDetails.getDeptName(), userDetails.getEmpId(), "ticket_no", "DESC", "clientId", pojo.getClientId(), "", connectionSpace, "-1", "-1", "-1", "-1", "-1", "Enter MRD No", userType, userDetails.getEmpName());
		List<com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo> feedbackList = universalAction.setFeedbackValues(data, deptLevel, "");
		return feedbackList;
	}

	public List getStatusLevelOfCompliant(SessionFactory connection, CommonOperInterface cbt, String feedId, boolean feedStat, String dataFor, String ticketNo,String activityFlag)
	{
		List dataList =null;
		List levelDataList=new ArrayList();
		String flag=null;
		try
		{
			//System.out.println("Ticket No is as "+ticketNo);
			StringBuilder query = new StringBuilder();
			if(activityFlag.equalsIgnoreCase("1"))
			{
				query.append("select status from feedback_status_feed_paperRating where id =" + feedId);
			}
			else
			{
				query.append("select status from feedback_status where id =" + feedId);
			}
			
			List list= cbt.executeAllSelectQuery(query.toString(), connection);
			query.delete(0, query.length());
			
			query.append("SELECT feedback.status,feedback.level,feedback.open_date,feedback.open_time,subcatg.addressingTime,subcatg.resolutionTime,feedback.to_dept_subdept");
			query.append(",feedback.resolve_remark,feedback.spare_used,feedback.ticket_no,feedback.subCatg");
			if(activityFlag.equalsIgnoreCase("1"))
			{
				query.append(" from feedback_status_feed_paperRating as feedback ");
			}
			else
			{
				query.append(" FROM feedback_status AS feedback");
			}
			
			query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
			query.append(" WHERE feedback.id!=0");
			query.append(" and feedback.id =" + feedId);
			dataList=cbt.executeAllSelectQuery(query.toString(), connection);
			levelDataList.add(dataList);
			if(list!=null && list.size()>0)
			{
				String status=(String) list.get(0);
				if(status.equalsIgnoreCase("Ticket Opened"))
				{
					String subcat=null;
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object object[] = (Object[]) iterator.next();
							subcat=object[10].toString();
						}
					}
					query.delete(0, query.length());
					query.append("SELECT feedback.status,feedback.level,feedback.open_date,feedback.open_time,subcatg.addressingTime,subcatg.resolutionTime,feedback.to_dept_subdept");
					query.append(",feedback.resolve_remark,feedback.spare_used,feedback.ticket_no,feedback.subCatg");
					query.append(" FROM feedback_status AS feedback");
					query.append(" INNER JOIN feedback_subcategory AS subcatg ON subcatg.id = feedback.subCatg");
					query.append(" WHERE feedback.id!=0");
					if(activityFlag.equalsIgnoreCase("0"))
					{
						query.append(" and feedback.ticket_no like '"+ticketNo+"%' and feedback.status not IN ('Ticket Opened')" );
					}
					else
					{
						query.append(" and feedback.subCatg =" +subcat+" and  feedback.ticket_no like '"+ticketNo+"%' " );
					}
					dataList=cbt.executeAllSelectQuery(query.toString(), connection);
					levelDataList.add(dataList);
				}
			}
		}	
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return levelDataList;
	}

	public List getEmp4Escalation(String dept_subDept, String module, String level, SessionFactory connectionSpace, CommonOperInterface cbt)
	{
		List empList = new ArrayList();
		StringBuilder query = new StringBuilder();
		try
		{
			if (module != null && module.equalsIgnoreCase("FM"))
			{
				query.append("select distinct emp.id, emp.empName from employee_basic as emp");
				query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
				query.append("	inner join roaster_conf roaster on contacts.id = roaster.contactId");
				query.append("	inner join department dept on roaster.dept_subdept = dept.id");
				query.append("	inner join shift_conf shift on dept.id = shift.dept_subdept");
				query.append("	where roaster.status='Active' and roaster.attendance='Present'  and contacts.level='" + level + "' and contacts.work_status='3' and contacts.moduleName='" + module + "'");
				query.append(" and dept.id='" + dept_subDept + "'");
			}
			else
			{
				query.append("select distinct emp.id, emp.empName from employee_basic as emp");
				query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
				query.append(" inner join roaster_conf roaster on contacts.id = roaster.contactId");
				query.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
				query.append(" inner join department dept on sub_dept.deptid = dept.id ");
				query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
				query.append(" where roaster.status='Active' and roaster.attendance='Present' and contacts.level='" + level + "' and contacts.work_status='3' and contacts.moduleName='" + module + "'");
				query.append(" and sub_dept.id='" + dept_subDept + "'");
			}
			// System.out.println("EMP" + query);
			empList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return empList;
	}

	public int getAdminDeptId(SessionFactory connection)
	{
		int deptId = 0;
		try
		{
			Properties configProp = new Properties();
			String adminDeptName = null;
			InputStream in = this.getClass().getResourceAsStream("/com/Over2Cloud/ctrl/feedback/adminDept.properties");
			configProp.load(in);
			adminDeptName = configProp.getProperty("admin");
			deptId = new EscalationActionControlDao().getAllSingleCounter(connection, "select id from department where deptName='" + adminDeptName + "'");
		}
		catch (Exception e)
		{
		}
		return deptId;
	}

	public String getFeedIdofFeedbackData(String feedId, SessionFactory connection)
	{
		String feedStatId = null;
		try
		{
			StringBuilder builder = new StringBuilder("select feed_stat_id from feedback_ticket where feed_data_id='" + feedId + "'");
			List data = cbt.executeAllSelectQuery(builder.toString(), connection);
			if (data != null && data.size() > 0)
			{
				feedStatId = data.get(0).toString();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return feedStatId;
	}

	public EmpBasicPojoBean getLoggedUserDetails(SessionFactory connection, String userName, String deptLevel)
	{
		EmpBasicPojoBean empPojo = null;
		List dataList = null;
		try
		{
			empPojo = new EmpBasicPojoBean();
			dataList = new LodgeFeedbackHelper().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					if (object[0] != null)
					{
						empPojo.setEmpName(object[0].toString());
					}
					if (object[1] != null)
					{
						empPojo.setMobOne(object[1].toString());
					}
					if (object[2] != null)
					{
						empPojo.setEmailIdOne(object[2].toString());
					}
					if (object[3] != null)
					{
						empPojo.setDeptName(object[3].toString());
					}
					if (object[4] != null)
					{
						empPojo.setEmpId(object[4].toString());
					}
				}
			}
		}
		catch (Exception e)
		{
		}
		return empPojo;
	}

	public String getTicketNo(SessionFactory connection, String feedId, String toDeptId)
	{
		String ticketNo = new FeedbackUniversalHelper().getTicketNo(toDeptId, "FM", connection);
		return getTicketNoFromFeedTicket(connection, feedId) + ticketNo;
	}

	public String getTicketNoForDept(SessionFactory connection, String ticket_no, String toDeptId)
	{
		String ticketNo = new FeedbackUniversalHelper().getTicketNo(toDeptId, "FM", connection);
		return ticket_no + ticketNo;
	}
	
	public String getTicketNoFromFeedTicket(SessionFactory connection, String feedId)
	{
		String feedTicketNo = null;
		StringBuilder builder = new StringBuilder("select feedTicketNo from feedback_ticket where feed_data_id='" + feedId + "' order by id desc limit 1");
		List data = cbt.executeAllSelectQuery(builder.toString(), connection);
		if (data != null)
		{
			feedTicketNo = data.get(0).toString();
		}
		return feedTicketNo;
	}

	public String getTicketAllotToId(SessionFactory connection, String toDeptId, boolean bedMapping, FeedbackPojo pojo, String tolevel, String deptLevel)
	{
		List allotto = null;
		List allotto1 = null;
		List<String> one = new ArrayList<String>();
		List<String> two = new ArrayList<String>();
		FeedbackUniversalHelper FUH = new FeedbackUniversalHelper();
		FeedbackUniversalAction FUA = new FeedbackUniversalAction();
		if (bedMapping && toDeptId != null && !toDeptId.equals(""))
		{
			String floor_status = FUH.getFloorStatus(toDeptId, connection);
			if (floor_status != null && floor_status.equalsIgnoreCase("B") && pojo.getCentreCode() != null && !pojo.getCentreCode().equalsIgnoreCase(""))
			{
				allotto = FUH.getRandomEmp4BedSpecific(toDeptId, pojo.getCentreCode(), tolevel.substring(5), connection);
			}
			else
			{
				allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", tolevel.substring(5), "", "", connection);
			}
		}
		else
		{
			allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", tolevel.substring(5), "", "", connection);
		}
		if (allotto == null || allotto.size() == 0)
		{
			String newToLevel = "";
			newToLevel = "" + (Integer.parseInt(tolevel.substring(5)) + 1);
			allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", newToLevel, "", "", connection);
			if (allotto == null || allotto.size() == 0)
			{
				newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
				allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", newToLevel, "", "", connection);
				if (allotto == null || allotto.size() == 0)
				{
					newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
					allotto = FUA.getRandomEmp4Escalation(toDeptId, "FM", newToLevel, "", "", connection);
				}
			}
		}
		allotto1 = FUH.getRandomEmployee(toDeptId, deptLevel, tolevel.substring(5), allotto, "FM", connection);
		if (allotto != null && allotto.size() > 0)
		{
			for (Object object : allotto)
			{
				one.add(object.toString());
			}
		}
		if (allotto1 != null && allotto1.size() > 0)
		{
			for (Object object : allotto1)
			{
				two.add(object.toString());
			}
		}
		String allottoId = null;
		List<String> two_new = new ArrayList<String>();
		if (one != null && one.size() > two.size())
		{
			// System.out.println("Inside If Block");
			one.removeAll(two);
			if (one.size() > 0)
			{
				for (Iterator iterator = one.iterator(); iterator.hasNext();)
				{
					Object object = (Object) iterator.next();
					allottoId = object.toString();
					break;
				}
			}
		}
		else
		{
			List pending_alloted = FUA.getPendingAllotto(toDeptId, "FM");
			if (pending_alloted != null && pending_alloted.size() > 0)
			{
				for (Object object : pending_alloted)
				{
					two_new.add(object.toString());
				}
			}

			if (two_new != null && two_new.size() > 0)
			{
				one.removeAll(two_new);
			}
			if (one != null && one.size() > 0)
			{
				allottoId = FUA.getRandomEmployee("FM", one);
			}
			else
			{
				allottoId = FUA.getRandomEmployee("FM", allotto);
			}
		}
		return allottoId;
	}

	public String[] getSubCatDetails(SessionFactory connection, String subCategory)
	{
		FeedbackUniversalHelper FUH = new FeedbackUniversalHelper();
		Queue<String> subCatList = new LinkedList<String>();
		List subCategoryList = FUH.getAllData("feedback_subcategory", "id", subCategory, "subCategoryName", "ASC", connection);
		if (subCategoryList != null && subCategoryList.size() > 0)
		{
			for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();)
			{
				Object[] objectCol = (Object[]) iterator.next();
				if (objectCol[4] == null)
				{
					// adrr_time = "06:00";
					subCatList.add("06:00");
				}
				else
				{
					// / adrr_time = objectCol[4].toString();
					subCatList.add(objectCol[4].toString());
				}
				if (objectCol[5] == null)
				{
					// res_time = "10:00";
					subCatList.add("10:00");
				}
				else
				{
					// res_time = objectCol[5].toString();
					subCatList.add(objectCol[5].toString());
				}

				if (objectCol[8] == null)
				{
					// tolevel = "Level1";
					subCatList.add("Level1");
				}
				else
				{
					// tolevel = objectCol[8].toString();
					subCatList.add(objectCol[8].toString());
				}
				if (objectCol[9] == null)
				{
					// needesc = "Y";
					subCatList.add("Y");
				}
				else
				{
					// needesc = objectCol[9].toString();
					subCatList.add(objectCol[9].toString());
				}

				if (objectCol[3] != null)
				{
					subCatList.add(objectCol[3].toString());
				}
				else
				{
					subCatList.add("NA");
				}
			}
		}
		return subCatList.toArray(new String[subCatList.size()]);
	}

	public boolean createFeedbackStatusTable(SessionFactory connection)
	{
		boolean tableCreated = false;
		List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", "", connection, "", 0, "table_name", "feedback_lodge_configuration");
		if (colName != null && colName.size() > 0)
		{
			List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
			for (GridDataPropertyView tableColumes : colName)
			{
				if (!tableColumes.getColomnName().equals("uniqueid") && !tableColumes.getColomnName().equals("feedType") && !tableColumes.getColomnName().equals("category") && !tableColumes.getColomnName().equals("resolutionTime"))
				{
					TableColumes ob1 = new TableColumes();
					ob1.setColumnname(tableColumes.getColomnName());
					ob1.setDatatype("varchar(" + tableColumes.getWidth() + ")");
					ob1.setConstraint("default NULL");
					TableColumnName.add(ob1);
				}
			}
			tableCreated = cbt.createTable22("feedback_status", TableColumnName, connection);
		}
		return tableCreated;
	}

	public Map<Integer, String> getServiceDeptList(SessionFactory connection, String deptHierarchy, String orgLevel, String orgId)
	{
		Map<Integer, String> serviDepts = new HashMap<Integer, String>();
		List departmentlist = new ArrayList();
		List colmName = new ArrayList();
		Map<String, Object> order = new HashMap<String, Object>();
		Map<String, Object> wherClause = new HashMap<String, Object>();
		List<String> colname = new ArrayList<String>();
		colname.add("orgLevel");
		colname.add("levelName");
		colmName.add("id");
		colmName.add("deptName");
		wherClause.put("orgnztnlevel", orgLevel);
		wherClause.put("mappedOrgnztnId", orgId);
		order.put("deptName", "ASC");
		// Getting Id, Dept Name for Non Service Department
		departmentlist = new OpenTicketsForDept().getServiceDept_SubDept(deptHierarchy, orgLevel, orgId, connection);
		if (departmentlist != null && departmentlist.size() > 0)
		{
			for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
			{
				Object[] object1 = (Object[]) iterator.next();
				serviDepts.put((Integer) object1[0], object1[1].toString());
			}
		}
		return serviDepts;
	}

	public ActivityPojo getPatientOPDRatings(SessionFactory connection, ActivityPojo pojo)
	{
		StringBuilder buffer = new StringBuilder(
				"select fpr.ease_Contact, fpr.res_queries, fpr.polndCourt, fpr.regndBill, fpr.wait4Consltnt, fpr.diagnosis,fpr.treatment,fpr.medication,fpr.sampleColl,fpr.labServices,fpr.radiologyServices,fpr.careAtHome,fpr.neurology,fpr.urology,fpr.preHealth,fpr.anyOther,fpr.cleanWashroom,fpr.unkeepFacility,fpr.locndDirSignages,fpr.chemistShop,fpr.waitingAreas,fpr.cafeteria,fpr.parking,fpr.security, fpr.overallService,fpr.targetOn,fpr.choseHospital,fpr.cardiology,fpr.endoscopy from feedback_paper_rating as fpr where fpr.max_id_feeddbackdata ='"
						+ pojo.getId() + "'");
		List dataList = cbt.executeAllSelectQuery(buffer.toString(), connection);
		if (dataList.size() > 0 && dataList.size() > 0)
		{
		}
		return pojo;
	}

	public ActivityPojo getPatientIPDRatings(SessionFactory connection, ActivityPojo pojo)
	{
		StringBuilder buffer = new StringBuilder(
				"select fpr.responseToQueries, fpr.counselling, fpr.admission, fpr.courtesybehaviour, fpr.attentivePrCaring, fpr.responseCommDoctor,fpr.diagnosis,fpr.treatment,fpr.attentiveprompt,fpr.responseCommunication,fpr.procedures,fpr.medication,fpr.careAtHome,fpr.qualityUpkeep,fpr.behivourResponseTime,fpr.functioningMainenance,fpr.qualityofFoods,fpr.timelineService,fpr.behivourResponse,fpr.assessmentCounseling,fpr.responsetoQuery,fpr.efficiencyBillingdesk,fpr.dischargetime,fpr.pharmacyservices, fpr.cafeteriaDyning,fpr.overallsecurity,fpr.overallservices,fpr.recommend,fpr.choseHospital,fpr.front_ease from feedback_paper_rating as fpr where fpr.max_id_feeddbackdata ='"
						+ pojo.getFeedDataId() + "'");
		// System.out.println("Ratings >>>"+buffer);
		List dataList = cbt.executeAllSelectQuery(buffer.toString(), connection);
		if (dataList.size() > 0 && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0] != null)
				{
					pojo.setResponseToQueries(object[0].toString());
				}
				if (object[1] != null)
				{
					pojo.setCounselling(object[1].toString());
				}
				if (object[2] != null)
				{
					pojo.setAdmission(object[2].toString());
				}
				if (object[3] != null)
				{
					pojo.setCourtesybehaviour(object[3].toString());
				}
				if (object[4] != null)
				{
					pojo.setAttentivePrCaring(object[4].toString());
				}

				if (object[5] != null)
				{
					pojo.setResponseCommDoctor(object[5].toString());
				}
				if (object[6] != null)
				{
					pojo.setDiagnosis(object[6].toString());
				}
				if (object[7] != null)
				{
					pojo.setTreatment(object[7].toString());
				}
				if (object[8] != null)
				{
					pojo.setAttentiveprompt(object[8].toString());
				}
				if (object[9] != null)
				{
					pojo.setResponseCommunication(object[9].toString());
				}
				if (object[10] != null)
				{
					pojo.setProcedures(object[10].toString());
				}
				if (object[11] != null)
				{
					pojo.setMedication(object[11].toString());
				}
				if (object[12] != null)
				{
					pojo.setCareAtHome(object[12].toString());
				}
				if (object[13] != null)
				{
					pojo.setQualityUpkeep(object[13].toString());
				}
				if (object[14] != null)
				{
					pojo.setBehivourResponseTime(object[14].toString());
				}
				if (object[15] != null)
				{
					pojo.setFunctioningMainenance(object[15].toString());
				}
				if (object[16] != null)
				{
					pojo.setQualityofFoods(object[16].toString());
				}
				if (object[17] != null)
				{
					pojo.setTimelineService(object[17].toString());
				}
				if (object[18] != null)
				{
					pojo.setBehivourResponse(object[18].toString());
				}
				if (object[19] != null)
				{
					pojo.setAssessmentCounseling(object[19].toString());
				}
				if (object[20] != null)
				{
					pojo.setResponsetoQuery(object[20].toString());
				}

				if (object[21] != null)
				{
					pojo.setEfficiencyBillingdesk(object[21].toString());
				}
				if (object[22] != null)
				{
					pojo.setDischargetime(object[22].toString());
				}
				if (object[23] != null)
				{
					pojo.setPharmacyservices(object[23].toString());
				}
				if (object[24] != null)
				{
					pojo.setCafeteriaDyning(object[24].toString());
				}
				if (object[25] != null)
				{
					pojo.setOverallsecurity(object[25].toString());
				}
				if (object[26] != null)
				{
					pojo.setOverallservices(object[26].toString());
				}
				if (object[27] != null)
				{
					pojo.setRecommend(object[27].toString());
				}
				if (object[28] != null)
				{
					pojo.setChoseHospital(object[28].toString());
				}
				if (object[29] != null)
				{
					pojo.setFront_ease(object[29].toString());
				}
			}
		}

		return pojo;
	}

	public ActivityPojo getFullDetailsForOpenTicketBySubCat(SessionFactory connection, String subCatId, ActivityPojo pojo)
	{
		StringBuilder builder = new StringBuilder("select subCat.id as subCatId,subCat.subCategoryName,cat.id as catId,cat.categoryName,dept.id as deptId," + " dept.deptName,subCat.addressingTime,subCat.feedBreif from feedback_subcategory as subCat " + "	inner join feedback_category as cat on subCat.categoryName=cat.id " + "	inner join feedback_type as type on cat.fbType=type.id " + " inner join department as dept on type.dept_subdept=dept.id where subCat.id='" + subCatId + "'");
		// System.out.println("query======" + builder.toString());
		List dataList = cbt.executeAllSelectQuery(builder.toString(), connection);
		for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
		{
			Object[] object = (Object[]) iterator.next();
			if (object != null)
			{
				if (object[0] != null)
				{
					pojo.setSubCatId(object[0].toString());
				}
				else
				{
					pojo.setSubCatId("NA");
				}
				if (object[1] != null)
				{
					pojo.setSubCat(object[1].toString());
				}
				else
				{
					pojo.setSubCat("NA");
				}
				if (object[2] != null)
				{
					pojo.setCatId(object[2].toString());
				}
				else
				{
					pojo.setCatId("NA");
				}
				if (object[3] != null)
				{
					pojo.setCat(object[3].toString());
				}
				else
				{
					pojo.setCat("NA");
				}
				if (object[4] != null)
				{
					pojo.setDeptId(object[4].toString());
				}
				else
				{
					pojo.setDeptId("NA");
				}
				if (object[5] != null)
				{
					pojo.setDept(object[5].toString());
				}
				else
				{
					pojo.setDept("NA");
				}
				if (object[6] != null)
				{
					pojo.setAddressingTime(object[6].toString());
				}
				else
				{
					pojo.setAddressingTime("NA");
				}
				if (object[7] != null)
				{
					pojo.setBrief(object[7].toString());
				}
				else
				{
					pojo.setBrief("NA");
				}
			}
		}
		return pojo;
	}

	public ActivityPojo getPatientDetails4Ack(String feedId,String activityFlag,SessionFactory connection)
	{
		ActivityPojo pojo=new ActivityPojo();
		StringBuilder builder=new StringBuilder(" select feed_by,feed_brief,open_date,via_from,resolve_remark,spare_used,clientId,patMobNo,patEmailId,location,resolve_date from ");
		if(activityFlag!=null && activityFlag.equalsIgnoreCase("0"))
		{
			builder.append(" feedback_status");
		}
		else
		{
			builder.append(" feedback_status_feed_paperRating");
		}
		List data=cbt.executeAllSelectQuery(builder.toString(), connection);
		if(data!=null && data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null)
				{
					pojo.setClientName(object[0].toString());
				}
				else
				{
					pojo.setClientName("NA");
				}
				
				if(object[1]!=null)
				{
					pojo.setBrief(object[1].toString());
				}
				else
				{
					pojo.setBrief("NA");
				}
				
				if(object[2]!=null)
				{
					pojo.setDateTime(object[2].toString());
				}
				else
				{
					pojo.setDateTime("NA");
				}
				
				if(object[3]!=null)
				{
					pojo.setMode(object[3].toString());
				}
				else
				{
					pojo.setMode("NA");
				}
				
				if(object[4]!=null)
				{
					pojo.setRca(object[4].toString());
				}
				else
				{
					pojo.setRca("NA");
				}
				
				if(object[5]!=null)
				{
					pojo.setCapa(object[5].toString());
				}
				else
				{
					pojo.setCapa("NA");
				}
				
				if(object[6]!=null)
				{
					pojo.setClientId(object[6].toString());
				}
				else
				{
					pojo.setClientId("NA");
				}
				
				if(object[7]!=null)
				{
					pojo.setMobNo(object[7].toString());
				}
				else
				{
					pojo.setMobNo("NA");
				}
				
				if(object[8]!=null)
				{
					pojo.setEmailId(object[8].toString());
				}
				else
				{
					pojo.setEmailId("NA");
				}
				
				if(object[9]!=null)
				{
					pojo.setCenterCode(object[9].toString());
				}
				else
				{
					pojo.setCenterCode("NA");
				}
				
				if(object[10]!=null)
				{
					pojo.setResolveAt(object[10].toString());
				}
				else
				{
					pojo.setResolveAt("NA");
				}
			}
		}
		return pojo;
	}
	
	
	public ActivityPojo getPatientFullDetails(SessionFactory connection, String clientId)
	{
		ActivityPojo pojo = new ActivityPojo();

		StringBuilder builder = new StringBuilder("select feeddata.id,feeddata.clientName,feeddata.mobNo,feeddata.emailId,feeddata.centerCode,feeddata.openDate,feeddata.openTime,feeddata.targetOn,feeddata.compType,feeddata.mode,feeddata.serviceBy,feeddata.handledBy,feeddata.comments,feeddata.companytype,feeddata.discharge_datetime,feeddata.choseHospital,feeddata.recommend,overAll,feeddata.centreName,emp.empName,patinfo.admission_time "
				+ "from feedbackdata as feeddata inner join employee_basic as emp on feeddata.handledBy=emp.id  " + " inner join patientinfo as patinfo on patinfo.patientId=feeddata.patientId  where clientId='" + clientId + "' group by feeddata.id ");
		// System.out.println("query>>>>>>>>>>>>>>>"+builder);
		List feedList = cbt.executeAllSelectQuery(builder.toString(), connection);
		if (feedList != null && feedList.size() > 0)
		{
			for (Iterator iterator = feedList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					pojo.setClientId(clientId);
					if (object[0] != null)
					{
						pojo.setFeedDataId((object[0].toString()));
					}
					else
					{
						pojo.setId(0);
					}

					if (object[1] != null)
					{
						pojo.setClientName(object[1].toString());
					}
					else
					{
						pojo.setClientName("NA");
					}

					if (object[2] != null)
					{
						pojo.setMobNo(object[2].toString());
					}
					else
					{
						pojo.setMobNo("NA");
					}

					if (object[3] != null)
					{
						pojo.setEmailId(object[3].toString());
					}
					else
					{
						pojo.setEmailId("NA");
					}
					// emailId,centerCode,openDate,openTime,targetOn,compType
					// from feedbackdata where id=
					if (object[4] != null)
					{
						pojo.setCenterCode(object[4].toString());
					}
					else
					{
						pojo.setCenterCode("NA");
					}

					if (object[5] != null && object[6] != null)
					{
						pojo.setDateTime(DateUtil.convertDateToIndianFormat(object[5].toString()) + ", " + object[6].toString().substring(0, 5));
						// System.out.println("Date Time "+pojo.getDateTime());
					}
					else
					{
						pojo.setDateTime("NA");
					}

					if (object[7] != null)
					{
						if (object[7].toString().equalsIgnoreCase("No"))
						{
							pojo.setTargetOn("Negative");
						}
						else
						{
							pojo.setTargetOn("Positive");
						}

					}
					else
					{
						pojo.setTargetOn("NA");
					}

					if (object[8] != null)
					{
						pojo.setCompType(object[8].toString());
					}
					else
					{
						pojo.setCompType("NA");
					}

					if (object[9] != null)
					{
						pojo.setMode(object[9].toString());
					}
					else
					{
						pojo.setMode("NA");
					}

					if (object[10] != null)
					{
						pojo.setDocName(object[10].toString());
					}
					else
					{
						pojo.setDocName("NA");
					}

					if (object[11] != null)
					{
						pojo.setHandledBy(object[19].toString());
					}
					else
					{
						pojo.setHandledBy("NA");
					}

					if (object[12] != null)
					{
						pojo.setComments(object[12].toString());
					}
					else
					{
						pojo.setComments("NA");
					}

					if (object[13] != null)
					{
						pojo.setCompanytype(object[13].toString());
					}
					else
					{
						pojo.setCompanytype("NA");
					}

					if (object[14] != null && !object[14].equals("NA"))
					{
						String dateTime[]=object[14].toString().split(" ");
						pojo.setDischargeDateTime(DateUtil.convertDateToIndianFormat(dateTime[0].toString())+", "+dateTime[1].substring(0,5));
					}
					else
					{
						pojo.setDischargetime("NA");
					}
					if (object[15] != null)
					{
						pojo.setChoseHospital(object[15].toString());
					}
					else
					{
						pojo.setChoseHospital("NA");
					}

					if (object[16] != null)
					{
						pojo.setRecommend(object[16].toString());
					}
					else
					{
						pojo.setRecommend("NA");
					}

					if (object[17] != null)
					{
						pojo.setOverAll(object[17].toString());
					}
					else
					{
						pojo.setOverAll("NA");
					}

					if (object[18] != null)
					{
						pojo.setCentreName(object[18].toString());
					}
					else
					{
						pojo.setCentreName("NA");
					}

					if (object[20] != null)
					{
						String str[] = object[20].toString().split(" ");
						pojo.setAddmissionDate(DateUtil.convertDateToIndianFormat(str[0]) + ", " + str[1].substring(0, 5));
					}
					else
					{
						pojo.setAddmissionDate("NA");
					}
				}
			}
		}
		//System.out.println("admission date>>>>>>>>>>>>>"+pojo.getAddmissionDate
		// ());
		return pojo;
	}

	// 9873907788 Mr. Manohar
	{
		// select fpr.responseToQueries, fpr.counselling, fpr.admission,
		// fpr.courtesybehaviour, fpr.attentivePrCaring,
		//fpr.responseCommDoctor,fpr.diagnosis,fpr.treatment,fpr.attentiveprompt
		// ,
		// fpr.responseCommunication,fpr.procedures,fpr.medication,fpr.careAtHome
		// ,
		// fpr.qualityUpkeep,fpr.behivourResponseTime,fpr.functioningMainenance,
		// fpr.qualityofFoods,fpr.timelineService,fpr.behivourResponse,fpr.
		// assessmentCounseling
		// ,fpr.responsetoQuery,fpr.efficiencyBillingdesk,fpr
		// .dischargetime,fpr.pharmacyservices,
		// fpr.cafeteriaDyning,fpr.overallsecurity,fpr.overallservices,fpr.
		// recommend,fpr.choseHospital,fpr.front_ease
		// from feedback_paper_rating as fpr
	}

	public ActivityPojo getRatingCat(String rating, ActivityPojo pojo, SessionFactory connection)
	{
		StringBuilder builder = new StringBuilder(" select " + rating + " from feedback_paper_rating where max_id_feeddbackdata='" + pojo.getId() + "'");
		// System.out.println("<><>>Rating Cat<<><>"+builder);
		List ratingList = cbt.executeAllSelectQuery(builder.toString(), connection);
		if (ratingList != null && ratingList.size() > 0 && ratingList.get(0) != null)
		{
			pojo.setCatRating(ratingList.get(0).toString());
			pojo.setCatRatingVal(ratingList.get(0).toString());
		}
		else
		{
			pojo.setCatRating("0");
			pojo.setCatRatingVal("0");
		}
		return pojo;
	}

	public String getRatingCounterFeedback(String rating, String feedDataId, SessionFactory connection)
	{
		StringBuilder builder = new StringBuilder(" select " + rating + " from feedback_paper_rating where max_id_feeddbackdata='" + feedDataId + "'");
		// System.out.println("Rating Select >>>>"+builder);
		List ratingList = cbt.executeAllSelectQuery(builder.toString(), connection);
		if (ratingList != null && ratingList.size() > 0 && ratingList.get(0) != null)
		{
			return ratingList.get(0).toString();
		}
		else
		{
			return "0";
		}
	}

	public String getRatingCounter(String rating, ActivityPojo pojo, SessionFactory connection)
	{
		StringBuilder builder = new StringBuilder(" select " + rating + " from feedback_paper_rating where max_id_feeddbackdata='" + pojo.getFeedDataId() + "'");
		// System.out.println("Rating Select >>>>"+builder);
		List ratingList = cbt.executeAllSelectQuery(builder.toString(), connection);
		if (ratingList != null && ratingList.size() > 0 && ratingList.get(0) != null)
		{
			return ratingList.get(0).toString();
		}
		else
		{
			return "0";
		}
	}

	public ActivityPojo getDepartmentWiseRatingCat(String category, String catRating, ActivityPojo pojo, SessionFactory connection)
	{
		if (category != null && pojo.getId() != 0)
		{
			StringBuffer buffer = new StringBuffer(" select dpt.id,dpt.deptName,subCat.subCategoryName,subCat.feedBreif,subCat.id as subCatId,cat.categoryName from department as dpt  inner join feedback_type as type on dpt.id=type.dept_subdept  inner join feedback_category as cat on type.id=cat.fbType  inner join feedback_subcategory subCat on cat.id=subCat.categoryName  where type.id!='0' ");
			if (catRating != "NA" && category != "NA")
			{
				if (Integer.parseInt(catRating) == 1)
				{
					buffer.append("and subCat.subCategoryName='" + category.trim() + " is Poor' limit 1 ");
				}
				else if (Integer.parseInt(catRating) == 2)
				{
					buffer.append("and subCat.subCategoryName='" + category.trim() + " is Average' limit 1 ");
				}
				else if (Integer.parseInt(catRating) == 3)
				{
					buffer.append("and subCat.subCategoryName='" + category.trim() + " is Good' limit 1 ");
				}
				else if (Integer.parseInt(catRating) == 4)
				{
					buffer.append("and subCat.subCategoryName='" + category.trim() + " is Very Good' limit 1 ");
				}
				else if (Integer.parseInt(catRating) == 5)
				{
					buffer.append("and subCat.subCategoryName='" + category.trim() + " is Excellent' limit 1 ");
				}
			}
			// System.out.println("Dept Query>>>>>"+buffer);
			List deptList = cbt.executeAllSelectQuery(buffer.toString(), connection);
			for (Iterator iterator = deptList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					if (object[1] != null)
					{
						pojo.setDept(object[1].toString());
					}
					else
					{
						pojo.setDept("NA");
					}
					if (object[2] != null)
					{
						pojo.setSubCat(object[2].toString());
					}
					else
					{
						pojo.setSubCat("NA");
					}
					if (object[3] != null)
					{
						pojo.setBrief(object[3].toString());
					}
					else
					{
						pojo.setBrief("NA");
					}
					if (object[4] != null)
					{
						pojo.setSubCatId(object[4].toString());
					}
					else
					{
						pojo.setSubCatId("NA");
					}

					if (object[5] != null)
					{
						pojo.setCat(object[5].toString());
					}
					else
					{
						pojo.setCat("NA");
					}
					pojo.setCatExists(true);
				}
			}
		}
		return pojo;
	}

	public ActivityPojo getDepartmentCatNdSubCat(ActivityPojo pojo, SessionFactory connection)
	{
		if (pojo.getCat() != null && pojo.getId() != 0)
		{
			StringBuffer buffer = new StringBuffer(" select dpt.id,dpt.deptName,subCat.subCategoryName,subCat.feedBreif,subCat.id as subCatId from department as dpt  inner join feedback_type as type on dpt.id=type.dept_subdept  inner join feedback_category as cat on type.id=cat.fbType  inner join feedback_subcategory subCat on cat.id=subCat.categoryName  where type.id!='0' ");
			if (pojo.getCatRating() != "NA")
			{
				if (Integer.parseInt(pojo.getCatRatingVal()) == 1)
				{
					buffer.append("and subCat.subCategoryName='" + pojo.getCat() + " is Poor' limit 1 ");
				}
				else if (Integer.parseInt(pojo.getCatRatingVal()) == 2)
				{
					buffer.append("and subCat.subCategoryName='" + pojo.getCat() + " is Average' limit 1 ");
				}
				else if (Integer.parseInt(pojo.getCatRatingVal()) == 3)
				{
					buffer.append("and subCat.subCategoryName='" + pojo.getCat() + " is Good' limit 1 ");
				}
				else if (Integer.parseInt(pojo.getCatRatingVal()) == 4)
				{
					buffer.append("and subCat.subCategoryName='" + pojo.getCat() + " is Very Good' limit 1 ");
				}
				else if (Integer.parseInt(pojo.getCatRatingVal()) == 5)
				{
					buffer.append("and subCat.subCategoryName='" + pojo.getCat() + " is Excellent' limit 1 ");
				}
			}
			// System.out.println("DPT>>"+buffer);
			List deptList = cbt.executeAllSelectQuery(buffer.toString(), connection);
			for (Iterator iterator = deptList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					if (object[1] != null)
					{
						pojo.setDept(object[1].toString());
					}
					else
					{
						pojo.setDept("NA");
					}
					if (object[2] != null)
					{
						pojo.setSubCat(object[2].toString());
					}
					else
					{
						pojo.setSubCat("NA");
					}
					if (object[3] != null)
					{
						pojo.setBrief(object[3].toString());
					}
					else
					{
						pojo.setBrief("NA");
					}
					if (object[4] != null)
					{
						pojo.setSubCatId(object[4].toString());
					}
					else
					{
						pojo.setSubCatId("NA");
					}
				}
			}
		}
		return pojo;
	}

	public ActivityPojo getAllgetPaperRatings(SessionFactory connection, ActivityPojo pojo)
	{
		List<Map<String, String>> subCatList = new ArrayList<Map<String, String>>();
		StringBuffer buffer = new StringBuffer("select field_name,field_value from ");
		if (pojo.getCompType().equalsIgnoreCase("IPD"))
		{
			buffer.append(" feedback_paperform_rating_configuration where field_value not in ('max_id_feeddbackdata','choseHospital','recommend')");
		}
		else if (pojo.getCompType().equalsIgnoreCase("OPD"))
		{
			buffer.append(" feedback_paperform_rating_configuration_opd where field_value not in ('targetOn','choseHospital','max_id_feeddbackdata');");
		}
		// System.out.println("Paper Rating QUerry ?????/"+buffer);
		List ratings = cbt.executeAllSelectQuery(buffer.toString(), connection);
		if (ratings != null && ratings.size() > 0)
		{
			for (Iterator iterator = ratings.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					Map<String, String> tempMap = new HashMap<String, String>();
					if (object[0] != null && object[1] != null)
					{
						// pojo.setCat(object[0].toString());
						// pojo.setCatRating(object[1].toString());
						tempMap.put(object[1].toString(), object[0].toString());
						subCatList.add(tempMap);
					}
				}
			}
		}
		pojo.setCategoryList(subCatList);
		return pojo;
	}

	public List<ConfigurationUtilBean> getAllgetPaperRatingsBeanList(SessionFactory connection, ActivityPojo pojo)
	{
		List<ConfigurationUtilBean> subCatList = new ArrayList<ConfigurationUtilBean>();
		StringBuffer buffer = new StringBuffer("select field_name,field_value from ");
		if (pojo.getCompType().equalsIgnoreCase("IPD"))
		{
			buffer.append(" feedback_paperform_rating_configuration where field_value not in ('max_id_feeddbackdata','choseHospital','recommend')");
		}
		else if (pojo.getCompType().equalsIgnoreCase("OPD"))
		{
			buffer.append(" feedback_paperform_rating_configuration_opd where field_value not in ('targetOn','choseHospital','max_id_feeddbackdata');");
		}
		// System.out.println("Paper Rating QUerry ?????/"+buffer);
		List ratings = cbt.executeAllSelectQuery(buffer.toString(), connection);
		if (ratings != null && ratings.size() > 0)
		{
			for (Iterator iterator = ratings.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					ConfigurationUtilBean temp = new ConfigurationUtilBean();
					if (object[0] != null && object[1] != null)
					{
						// pojo.setCat(object[0].toString());
						// pojo.setCatRating(object[1].toString());

						temp.setField_name(object[0].toString());
						temp.setField_value(object[1].toString());

						subCatList.add(temp);
					}
				}
			}
		}
		return subCatList;
	}

	public List<ConfigurationUtilBean> getAllPaperRatings(SessionFactory connection, String patType)
	{
		List<ConfigurationUtilBean> subCatList = new ArrayList<ConfigurationUtilBean>();
		StringBuffer buffer = new StringBuffer("select field_name,field_value from ");
		if (patType.equalsIgnoreCase("IPD"))
		{
			buffer.append(" feedback_paperform_rating_configuration where field_value not in ('max_id_feeddbackdata','choseHospital','recommend')");
		}
		else if (patType.equalsIgnoreCase("OPD"))
		{
			buffer.append(" feedback_paperform_rating_configuration_opd where field_value not in ('targetOn','choseHospital','max_id_feeddbackdata');");
		}
		// System.out.println("Paper Rating QUerry ?????/"+buffer);
		List ratings = cbt.executeAllSelectQuery(buffer.toString(), connection);
		if (ratings != null && ratings.size() > 0)
		{
			for (Iterator iterator = ratings.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					ConfigurationUtilBean temp = new ConfigurationUtilBean();
					if (object[0] != null && object[1] != null)
					{
						// pojo.setCat(object[0].toString());
						// pojo.setCatRating(object[1].toString());

						temp.setField_name(object[0].toString());
						temp.setField_value(object[1].toString());

						subCatList.add(temp);
					}
				}
			}
		}
		return subCatList;
	}

	public ActivityPojo getPaperRatings(SessionFactory connection, ActivityPojo pojo)
	{
		StringBuffer buffer = new StringBuffer("select field_name,field_value from ");
		if (pojo.getCompType().equalsIgnoreCase("IPD"))
		{
			buffer.append(" feedback_paperform_rating_configuration");
		}
		else if (pojo.getCompType().equalsIgnoreCase("OPD"))
		{
			buffer.append(" feedback_paperform_rating_configuration_opd");
		}
		// System.out.println("QUerry ?????/"+buffer);
		List ratings = cbt.executeAllSelectQuery(buffer.toString(), connection);
		if (ratings != null && ratings.size() > 0)
		{
			for (Iterator iterator = ratings.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					if (object[0] != null)
					{
						pojo.setCat(object[0].toString());
					}
					if (object[1] != null)
					{
						pojo.setCatRating(object[1].toString());
					}
				}
			}
		}
		return pojo;
	}

	public CustomerPojo getPatientsDetails(String id, SessionFactory connection)
	{
		CustomerPojo pojo = new CustomerPojo();
		StringBuilder builder = new StringBuilder(" select clientId,clientName,compType,centerCode,mobNo,emailId,centreName,serviceBy,openDate,openTime,handledBy,mode,targetOn,comments,patientId,id from feedbackdata where id='" + id + "'");
		// System.out.println("Querry is as >>>>"+builder);
		List dataList = cbt.executeAllSelectQuery(builder.toString(), connection);
		if (dataList != null && dataList.size() > 0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					if (object[0] != null)
					{
						pojo.setClientId(object[0].toString());
					}
					if (object[1] != null)
					{
						pojo.setClientName(object[1].toString());
					}
					if (object[2] != null)
					{
						pojo.setCompType(object[2].toString());
					}
					if (object[3] != null)
					{
						pojo.setCenterCode(object[3].toString());
					}
					if (object[4] != null)
					{
						pojo.setMobNo(object[4].toString());
					}
					if (object[5] != null)
					{
						pojo.setEmailId(object[5].toString());
					}
					// For Speciality
					if (object[6] != null)
					{
						pojo.setCentreName(object[6].toString());
					}
					if (object[7] != null)
					{
						pojo.setServiceBy(object[7].toString());
					}
					if (object[8] != null)
					{
						pojo.setOpenDate(DateUtil.convertDateToIndianFormat(object[8].toString()));
					}
					if (object[9] != null)
					{
						pojo.setOpenTime((object[9].toString().substring(0, 5)));
					}
					// 10th left for Handled By
					if (object[10] != null)
					{
						pojo.setMode((object[10].toString()));
					}
					if (object[11] != null)
					{
						if (object[11].toString().equalsIgnoreCase("No"))
						{
							pojo.setFeedback("Negative");
						}
						else
						{
							pojo.setFeedback("Positive");
						}
					}
					if (object[12] != null)
					{
						pojo.setComments(object[12].toString());
					}
					if (object[13] != null)
					{
						pojo.setPatientId(object[13].toString());
					}
					if (id != null)
					{
						pojo.setId(Integer.parseInt(id));
					}
				}
			}
		}
		return pojo;
	}

	public List<Object> getActivityPageData(SessionFactory connection)
	{
		List<Object> data = new ArrayList<Object>();
		data = getQueryResult(connection);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
				}
			}
		}
		return data;
	}

	private List getQueryResult(SessionFactory connection)
	{
		List data = new ArrayList();
		StringBuilder builder = new StringBuilder("select clientId,clientName,compType,centerCode,mode,openDate,openTime,comments from feedbackdata order by id desc");
		data = cbt.executeAllSelectQuery(builder.toString(), connection);
		return data;
	}

	public ActivityPojo getEmpFullDetails(SessionFactory connectionSpace, String id,String activityFlag)
	{
		ActivityPojo pojo = new ActivityPojo();
		StringBuilder builder = new StringBuilder("select emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName from employee_basic as emp");
		if(activityFlag.equalsIgnoreCase("1"))
		{
			builder.append(" inner join feedback_status_feed_paperrating feed on emp.id=feed.allot_to inner join department dept on emp.deptname=dept.id");
		}
		else
		{
			builder.append(" inner join feedback_status feed on emp.id=feed.allot_to inner join department dept on emp.deptname=dept.id");
		}
		builder.append(" where feed.id='" + id + "' group by emp.id;");
		List feedList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if (feedList != null && feedList.size() > 0)
		{
			for (Iterator iterator = feedList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					// pojo.setId(Integer.parseInt(feedId));
					if (object[0] != null)
					{
						pojo.setClientName(object[0].toString());
					}
					else
					{
						pojo.setClientName("NA");
					}
					if (object[1] != null)
					{
						pojo.setMobNo(object[1].toString());
					}
					else
					{
						pojo.setMobNo("NA");
					}
					if (object[2] != null)
					{
						pojo.setEmailId(object[2].toString());
					}
					else
					{
						pojo.setEmailId("NA");
					}
					if (object[3] != null)
					{
						pojo.setDept(object[3].toString());
					}
					else
					{
						pojo.setDept("NA");
					}
				}
			}
		}
		return pojo;
	}

	public ActivityPojo getOpenByDetails(SessionFactory connectionSpace, String id,String activityFlag)
	{
		String uId = null;
		ActivityPojo pojo = new ActivityPojo();
		StringBuilder builder = new StringBuilder("");
		if(activityFlag.equalsIgnoreCase("1"))
		{
			builder.append("select feed_registerby from feedback_status_feed_paperrating where id='"+id+"'");
		}
		else
		{
			builder.append("select feed_registerby from feedback_status where id='"+id+"'");
		}
		
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0)
		{
			String name=(String) dataList.get(0);
			builder.delete(0, builder.length());
			try {
					uId=Cryptography.encrypt(name, DES_ENCRYPTION_KEY);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
				//builder.append("select mobOne,emailIdOne from employee_basic where empName='"+name+"' order by id desc limit 1");
				builder.append("select emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName from employee_basic as emp");
				builder.append(" inner join useraccount as uc on uc.id=emp.useraccountid ");
				builder.append(" inner join department as dept on emp.deptname=dept.id ");
				builder.append("  where uc.userID='"+uId+"'");
				List list=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
				if(list!=null && list.size()>0)
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object!=null)
						{
							//pojo.setClientName(DateUtil.makeTitle(name));
							if(object[0]!=null)
							{
								pojo.setClientName(object[0].toString());
								//pojo.setMobNo(object[0].toString());
							}
							if(object[1]!=null)
							{
								pojo.setMobNo(object[1].toString());
								//pojo.setEmailId(object[1].toString());
							}
							if(object[2]!=null)
							{
								pojo.setEmailId(object[2].toString());
							}
							if(object[3]!=null)
							{
								pojo.setDept(object[3].toString());
								//pojo.setEmailId(object[1].toString());
							}
						}
					}
				return pojo;
			}
			else
			{
				builder.delete(0, builder.length());
				builder.append("select mobile_no,email from patientinfo where patient_name='"+name+"' order by id desc limit 1");
				list=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
				if(list!=null && list.size()>0)
				{
					for (Iterator iterator = list.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if(object!=null)
						{
							pojo.setClientName(DateUtil.makeTitle(name));
							
							if(object[0]!=null)
							{
								pojo.setMobNo(object[0].toString());
							}
							if(object[1]!=null)
							{
								pojo.setEmailId(object[1].toString());
							}
						}
					}
				}
			}
		}
	
		return pojo;
	}

	public ActivityPojo getReopenTATDetails(SessionFactory connectionSpace, String id)
	{
		// TODO Auto-generated method stub
		ActivityPojo pojo = new ActivityPojo();
		StringBuilder builder = new StringBuilder("select status,level,open_date,open_time,Addr_date_time,escalation_date,escalation_time from feedback_status ");
		builder.append(" where id=" + id);
		// System.out.println("query>>>>>>>>>>>>><<<<<<<<<<<<" + builder);
		List feedList = cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if (feedList != null && feedList.size() > 0)
		{
			for (Iterator iterator = feedList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					// pojo.setId(Integer.parseInt(feedId));
					if (object[0] != null)
					{
						pojo.setFstatus(object[0].toString());
					}
					else
					{
						pojo.setFstatus("NA");
					}
					if (object[1] != null)
					{
						pojo.setLevel(object[1].toString());
					}
					else
					{
						pojo.setLevel("NA");
					}
					if (object[2] != null && object[3] != null)
					{
						pojo.setDateTime(DateUtil.convertDateToIndianFormat(object[2].toString()) + ", " + object[3].toString().substring(0, 5));
					}
					else
					{
						pojo.setDateTime("NA");
					}
					if (object[4] != null)
					{
						String str[] = object[4].toString().split("#");
						pojo.setEscDateTime(DateUtil.convertDateToIndianFormat(str[0]) + ", " + str[1].substring(0, 5));
					}
					else
					{
						pojo.setEscDateTime("NA");
					}
					if (object[5] != null && object[6] != null)
					{
						pojo.setAddressingTime(DateUtil.convertDateToIndianFormat(object[5].toString()) + ", " + object[6].toString().substring(0, 5));
					}
					else
					{
						pojo.setAddressingTime("NA");
					}
				}
			}
		}
		return pojo;
	}

	public Map<String, String> getAllCategary(SessionFactory connectionSpace)
	{
		Map<String, String> map = new HashMap<String, String>();
		List categ = cbt.executeAllSelectQuery("SELECT distinct fc.categoryName,fc.id FROM feedback_category AS fc inner join feedback_subcategory as subCat on fc.id=subCat.categoryName inner join feedback_status as feed on subCat.id=feed.subCatg WHERE feed.moduleName='FM' order by fc.categoryName", connectionSpace);
		if (categ != null && categ.size() > 0)
		{
			for (Iterator iterator = categ.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					map.put(object[1].toString(), object[0].toString());
				}
			}
		}
		return map;
	}

	public Map<String, String> getAllSubCategory(SessionFactory connectionSpace)
	{
		Map<String, String> map = new HashMap<String, String>();
		List subCateg = cbt.executeAllSelectQuery("SELECT distinct subcat.subCategoryName,subcat.id FROM feedback_subcategory AS subcat inner join feedback_status as feed on subcat.id=feed.subCatg WHERE feed.moduleName='FM' order by subcat.subCategoryName", connectionSpace);
		if (subCateg != null && subCateg.size() > 0)
		{
			for (Iterator iterator = subCateg.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object != null)
				{
					map.put(object[1].toString(), object[0].toString());
				}
			}
		}
		return map;
	}

	public String getEmpNameByUserId(String string, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		String name = null;
		StringBuilder query = new StringBuilder("");
		try
		{
			String str= Cryptography.encrypt(string, DES_ENCRYPTION_KEY) ;
			query.append("select name from useraccount where userID='" +str+ "'");
			List list = cbt.executeAllSelectQuery(query.toString(), connection);
			if (list != null && list.size() > 0)
			{
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					name = (String) iterator.next();
				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return name;
	}
}