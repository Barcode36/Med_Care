package com.Over2Cloud.ctrl.helpdesk.lodgefeedback;

/**
 * @author Khushal Singh
 * Date (08-07-2013)
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.BeanUtil.EmpBasicPojoBean;
import com.Over2Cloud.CommonClasses.CommonWork;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.EscalationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.activityboard.ActivityBoardHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.feedbackaction.ActionOnFeedback;
import com.Over2Cloud.ctrl.helpdesk.ticketconfiguration.TicketConfiguration;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.aspose.slides.p883e881b.bll;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class LodgeFeedbackAction extends GridPropertyBean
	{
		private static Logger logger = Logger.getLogger(LodgeFeedbackAction.class);
		private String uid;
		private String bydept_subdept;
		private JSONArray commonJSONArray = new JSONArray();
		private String conditionVar_Value = "";
		private String selectedId;
		private String feedbackBy;
		private String todept;
		private String tosubdept;
		private String feedType;
		private String subCategory;
		private String feed_brief;
		private String location;
		private String recvSMS;
		private String recvEmail;
		private String escTime;
		private String allotedto;
		private String ticket_no;
		private String open_date;
		private String open_time;
		private String allotto;
		private String subCatg;
		private String addrTime;
		private String addrDate;
		private String feedStatus;
		private String feedid;
		private String allot_to_mobno;
		private String floor = "";
		private String Addressing_Time = "";
		private Map<Integer, String> serviceDeptList = null;
		private EmpBasicPojoBean empObj = null;
		private List<Object> empData4Escalation = null;
		private Map<String, String> allRoomNo = null;
		private String dataFor;
		private String pageFor;
		String new_escalation_datetime = DateUtil.getCurrentDateUSFormat() + "#" + DateUtil.getCurrentTimeHourMin(), non_working_timing = "00:00";// changed
																																					// by
																																					// Karniaka
																																					// Due
																																					// to
																																					// Escalation(First
																																					// escalation
																																					// time
																																					// entry
																																					// was
																																					// wrong)
																																					// Issue
																																					// in
																																					// Medanta
		private String columnName;
		private FeedbackPojo fstatus;
		private String orgImgPath;
		private String roomNo;
		private Map<String, String> subCategList = null;
		private String caseManual;
		private Map<String, String> manualMap;
		private boolean exist_ticket;
private static final Lock lock = new ReentrantLock();
		@SuppressWarnings("rawtypes")
		public String firstAction4Complaint()
			{
				String returnResult = ERROR;
				boolean sessionFlag = ValidateSession.checkSession();
				if (sessionFlag)
					{
						try
							{
								List dataList = null;
								ActivityBoardHelper ABH = new ActivityBoardHelper();

								allRoomNo = new LinkedHashMap<String, String>();
								dataList = ABH.getAllRoomNo(connectionSpace);
								if (dataList != null && dataList.size() > 0)
									{
										for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
											{
												Object[] object = (Object[]) iterator.next();
												if (object[0] != null && object[1] != null)
													{
														allRoomNo.put(object[0].toString(), object[1].toString());
													}
											}
										dataList.clear();
									}

								subCategList = new LinkedHashMap<String, String>();
								dataList = ABH.getAllSubCategHDM(connectionSpace);
								if (dataList != null && dataList.size() > 0)
									{
										for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
											{
												Object[] object = (Object[]) iterator.next();
												if (object[0] != null && object[1] != null)
													{
														subCategList.put(object[0].toString(), object[1].toString());
													}
											}
										dataList.clear();
									}

								// Start Code for getting Service Department
								// list
								serviceDeptList = new LinkedHashMap<Integer, String>();
								List departmentlist = new HelpdeskUniversalAction().getServiceDept_SubDept("2", orgLevel, orgId, dataFor, connectionSpace);
								if (departmentlist != null && departmentlist.size() > 0)
									{
										for (Iterator iterator = departmentlist.iterator(); iterator.hasNext();)
											{
												Object[] object1 = (Object[]) iterator.next();
												serviceDeptList.put((Integer) object1[0], object1[1].toString());
											}
									}
								// End Code for getting Service Department list
								returnResult = SUCCESS;
							}
						catch (Exception e)
							{
								e.printStackTrace();
							}
					}
				else
					{
						returnResult = LOGIN;
					}
				return returnResult;
			}

		public String firstAction4ReAllot()
			{
				String returnResult = ERROR;
				boolean sessionFlag = ValidateSession.checkSession();
				if (sessionFlag)
					{
						try
							{
								String qry = "SELECT subCatg ,location FROM feedback_status_new WHERE id=" + feedid;
								List data = new createTable().executeAllSelectQuery(qry, connectionSpace);
								if (data != null && data.size() > 0)
									{
										Object[] object = (Object[]) data.get(0);
										subCategory = object[0].toString();
										location = object[1].toString();
									}

								returnResult = SUCCESS;
							}
						catch (Exception e)
							{
								e.printStackTrace();
							}
					}
				else
					{
						returnResult = LOGIN;
					}
				return returnResult;
			}

		public void setComplaintPageFields()
			{
				ConfigurationUtilBean obj;
				pageFieldsColumns = new ArrayList<ConfigurationUtilBean>();

				// List for Department Data
				List<GridDataPropertyView> deptList = Configuration.getConfigurationData("mapped_dept_config_param", accountID, connectionSpace, "", 0, "table_name", "dept_config_param");
				if (deptList != null && deptList.size() > 0)
					{
						for (GridDataPropertyView gdv : deptList)
							{
								obj = new ConfigurationUtilBean();
								if (gdv.getColomnName().equalsIgnoreCase("deptName"))
									{
										obj.setKey(gdv.getColomnName());
										obj.setValue(gdv.getHeadingName());
										obj.setValidationType(gdv.getValidationType());
										obj.setColType("D");
										obj.setMandatory(true);
										pageFieldsColumns.add(obj);
									}
							}
					}
				// List for Sub Department Data
				List<GridDataPropertyView> subdept_deptList = Configuration.getConfigurationData("mapped_subdeprtmentconf", accountID, connectionSpace, "", 0, "table_name", "subdeprtmentconf");
				if (subdept_deptList != null && subdept_deptList.size() > 0)
					{
						for (GridDataPropertyView gdv : subdept_deptList)
							{
								obj = new ConfigurationUtilBean();
								if (gdv.getColomnName().equalsIgnoreCase("subdeptname"))
									{
										obj.setKey(gdv.getColomnName());
										obj.setValue(gdv.getHeadingName());
										obj.setValidationType(gdv.getValidationType());
										obj.setColType("D");
										obj.setMandatory(true);
										pageFieldsColumns.add(obj);
									}

							}
					}

				// List for Complaint to Data
				List<GridDataPropertyView> feedLodgeTags = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_lodge_configuration");
				if (feedLodgeTags != null && feedLodgeTags.size() > 0)
					{
						for (GridDataPropertyView gdv : feedLodgeTags)
							{
								obj = new ConfigurationUtilBean();
								if (!gdv.getColomnName().equals("ticket_no") && !gdv.getColomnName().equals("deptHierarchy") && !gdv.getColomnName().equals("by_dept_subdept") && !gdv.getColomnName().equals("to_dept_subdept") && !gdv.getColomnName().equals("allot_to") && !gdv.getColomnName().equals("escalation_date") && !gdv.getColomnName().equals("escalation_time") && !gdv.getColomnName().equals("open_date")
										&& !gdv.getColomnName().equals("open_time") && !gdv.getColomnName().equals("level") && !gdv.getColomnName().equals("status") && !gdv.getColomnName().equals("via_from") && !gdv.getColomnName().equals("feed_registerby") && !gdv.getColomnName().equals("action_by") && !gdv.getColomnName().equals("resolve_date") && !gdv.getColomnName().equals("resolve_time")
										&& !gdv.getColomnName().equals("resolve_duration") && !gdv.getColomnName().equals("resolve_remark") && !gdv.getColomnName().equals("resolve_by") && !gdv.getColomnName().equals("spare_used") && !gdv.getColomnName().equals("hp_date") && !gdv.getColomnName().equals("hp_time") && !gdv.getColomnName().equals("hp_reason") && !gdv.getColomnName().equals("sn_reason")
										&& !gdv.getColomnName().equals("sn_on_date") && !gdv.getColomnName().equals("sn_on_time") && !gdv.getColomnName().equals("sn_upto_date") && !gdv.getColomnName().equals("sn_upto_time") && !gdv.getColomnName().equals("sn_duration") && !gdv.getColomnName().equals("ig_date") && !gdv.getColomnName().equals("ig_time") && !gdv.getColomnName().equals("ig_reason")
										&& !gdv.getColomnName().equals("transfer_date") && !gdv.getColomnName().equals("transfer_time") && !gdv.getColomnName().equals("transfer_reason") && !gdv.getColomnName().equals("moduleName") && !gdv.getColomnName().equals("feedback_remarks") && !gdv.getColomnName().equals("transferId") && !gdv.getColomnName().equals("non_working_time"))
									{
										obj.setKey(gdv.getColomnName());
										obj.setValue(gdv.getHeadingName());
										obj.setValidationType(gdv.getValidationType());
										obj.setColType(gdv.getColType());
										if (gdv.getMandatroy().toString().equals("1"))
											{
												obj.setMandatory(true);
											}
										else
											{
												obj.setMandatory(false);
											}
										pageFieldsColumns.add(obj);
									}
							}
					}
				// //System.out.println("Finally The List Size is  "+pageFieldsColumns.size());
			}

		public String fetchSubCategoryOnline()
		{
		

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
			{
				StringBuilder qry = new StringBuilder();
				try
					{
						if (userName == null || userName.equalsIgnoreCase(""))
							return LOGIN;

						List resultList = new ArrayList<String>();
						qry.append(" SELECT subCate.id,subCate.subCategoryName FROM feedback_subcategory AS subCate ");
						qry.append(" LEFT JOIN feedback_category as cate on cate.id=subCate.categoryName ");
						qry.append(" LEFT JOIN feedback_type AS ftype on ftype.id=cate.fbType ");
						qry.append(" WHERE ftype.dept_subdept IN ('"+bydept_subdept+"') AND moduleName='HDM' ");
						qry.append(" ORDER BY subCate.subCategoryName ");
						// //System.out.println(" ********Query  " +
						// qry.toString());
						resultList = new HelpdeskUniversalHelper().getData(qry.toString(), connectionSpace);

						if (resultList.size() > 0)
							{
								for (Object c : resultList)
									{
										Object[] objVar = (Object[]) c;
										JSONObject listObject = new JSONObject();
										listObject.put("id", objVar[0].toString());
										listObject.put("name", objVar[1].toString());
										commonJSONArray.add(listObject);
									}
							}
						returnResult = SUCCESS;
					}
				catch (Exception e)
					{
						e.printStackTrace();
					}
			}
		else
			{
				returnResult = LOGIN;
			}
		return returnResult;
		}
	
	public String fetchSubCategory()
	{
	

	String returnResult = ERROR;
	boolean sessionFlag = ValidateSession.checkSession();
	if (sessionFlag)
		{
			StringBuilder qry = new StringBuilder();
			try
				{
					if (userName == null || userName.equalsIgnoreCase(""))
						return LOGIN;

					List resultList = new ArrayList<String>();
					qry.append(" SELECT subCate.id,subCate.subCategoryName FROM feedback_subcategory AS subCate ");
					qry.append(" LEFT JOIN feedback_category as cate on cate.id=subCate.categoryName ");
					qry.append(" LEFT JOIN feedback_type AS ftype on ftype.id=cate.fbType ");
					qry.append(" WHERE ftype.dept_subdept IN (select id from subdepartment where deptid='"+bydept_subdept+"') AND moduleName='HDM' ");
					qry.append(" ORDER BY subCate.subCategoryName ");
					// //System.out.println(" ********Query  " +
					// qry.toString());
					resultList = new HelpdeskUniversalHelper().getData(qry.toString(), connectionSpace);

					if (resultList.size() > 0)
						{
							for (Object c : resultList)
								{
									Object[] objVar = (Object[]) c;
									JSONObject listObject = new JSONObject();
									listObject.put("id", objVar[0].toString());
									listObject.put("name", objVar[1].toString());
									commonJSONArray.add(listObject);
								}
						}
					returnResult = SUCCESS;
				}
			catch (Exception e)
				{
					e.printStackTrace();
				}
		}
	else
		{
			returnResult = LOGIN;
		}
	return returnResult;
	}

		@SuppressWarnings("rawtypes")
		public String EmpForResendSMS()
			{
				String feedId = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String ticketno = null;
				String qry = "select ticket_no from feedback_status where id=" + feedId;
				List dataList = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
				if (dataList.get(0) != null)
					ticketno = dataList.get(0).toString();

				// //System.out.println("REsent TNO*****"+ticketno);
				MsgMailCommunication MMC = new MsgMailCommunication();
				HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
				FeedbackPojo fbp = null;
				List data = HUH.getFeedbackDetail(ticketno, "SD", "Pending", "", connectionSpace);
				fbp = HUH.setFeedbackDataValues(data, "Pending", deptLevel);
				// //System.out.println("FBP   "+fbp);
				if (fbp != null)
					{
						mapEscalation("" + fbp.getId(), fbp.getLevel(), fbp.getEscalation_date(), fbp.getEscalation_time(), connectionSpace);
						// Block for sending sms for Level1 Engineer
						if (fbp.getMobOne() != null && fbp.getMobOne() != "" && fbp.getMobOne().trim().length() == 10 && !fbp.getMobOne().startsWith("NA") && (fbp.getMobOne().startsWith("9") || fbp.getMobOne().startsWith("8") || fbp.getMobOne().startsWith("7")))
							{
								String levelMsg = "Open Feedback Alert: Ticket No: " + fbp.getTicket_no() + ", To Be Closed By: " + fbp.getAddr_date_time() + ", Reg. By: " + DateUtil.makeTitle(fbp.getFeed_registerby()) + ", Location: " + fbp.getLocation() + ", Brief: " + fbp.getFeed_brief() + ".";
								MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticketno, "Pending", "0", "HDM");
							}
					}
				return SUCCESS;
			}

		@SuppressWarnings("rawtypes")
		public String getReAllotedData()
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query = "SELECT feed.subCatg,feed.location FROM feedback_status_new as feed LEFT JOIN employee_basic AS emp ON emp.id = feed.allot_to INNER JOIN feedback_subcategory subCat on subCat.id = feed.subCatg INNER JOIN feedback_category catt on catt.id = subCat.categoryName  INNER JOIN feedback_type fb on fb.id = catt.fbtype INNER JOIN subdepartment subdept on subdept.id=fb.dept_subdept WHERE feed.id="
						+ feedid;
				List dataList = cbt.executeAllSelectQuery(query, connectionSpace);
				if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (object[0] != null)
									setSubCategory(object[0].toString());
								if (object[1] != null)
									setFloor(object[1].toString());
							}
					}
				fetchAllotTo();

				return SUCCESS;
			}

		@SuppressWarnings("rawtypes")
		public String fetchReasonData()
			{
				String returnResult = ERROR;
				boolean sessionFlag = ValidateSession.checkSession();
				if (sessionFlag)
					{
						StringBuilder qry = new StringBuilder();
						try
							{
								if (userName == null || userName.equalsIgnoreCase(""))
									return LOGIN;
								CommonOperInterface cbt = new CommonConFactory().createInterface();
								qry.append(" SELECT id,tasktype FROM task_type  ");
								qry.append(" WHERE status ='" + feedStatus + "'");
								List resultList = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
								// //System.out.println("QQQQ*********QQQQQ" +
								// qry.toString());
								if (resultList != null && resultList.size() > 0)
									{
										for (Object c : resultList)
											{
												Object[] objVar = (Object[]) c;
												JSONObject listObject = new JSONObject();
												listObject.put("id", objVar[0].toString());
												listObject.put("name", objVar[1].toString());
												commonJSONArray.add(listObject);
											}
									}
								returnResult = SUCCESS;
							}
						catch (Exception e)
							{
								e.printStackTrace();
							}
					}
				else
					{
						returnResult = LOGIN;
					}
				return returnResult;
			}

		@SuppressWarnings({ "rawtypes" })
		public String fetchAllotTo()
			{
				String returnResult = ERROR;
				boolean sessionFlag = ValidateSession.checkSession();
				if (sessionFlag)
					{
						StringBuilder qry = new StringBuilder();
						try
							{
								if (userName == null || userName.equalsIgnoreCase(""))
									return LOGIN;
								HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
								HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
								String tolevel = null;
								// //System.out.println("Sub category :::: "+subCategory);
								if (subCategory != null && !subCategory.equalsIgnoreCase(""))
									{
										StringBuilder query = new StringBuilder();
										query.append(" Select subdept.id,subCat.escalationLevel from subdepartment as subdept ");
										query.append(" inner join feedback_type fb on fb.dept_subdept = subdept.id ");
										query.append(" inner join feedback_category catt on catt.fbType = fb.id ");
										query.append(" inner join feedback_subcategory subCat on subCat.categoryName = catt.id ");
										query.append(" WHERE subCat.id= " + subCategory);
										// //System.out.println(">>>>>> subCategory  >>>>> "+query.toString());
										List subCategoryList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
										if (subCategoryList != null && subCategoryList.size() > 0)
											{
												Object[] objectCol = (Object[]) subCategoryList.get(0);
												if (objectCol[1] == null)
													{
														tolevel = "Level1";
													}
												else
													{
														tolevel = objectCol[1].toString();
													}
												tosubdept = objectCol[0].toString();
											}
									}
								// //System.out.println("Level 1 ::::"+tolevel);
								List resultList = new ArrayList<String>();
								// //System.out.println("getTosubdept()  ::: "+getTosubdept());
								// //System.out.println("florr ::: "+floor);
								if (tosubdept != null && !tosubdept.equalsIgnoreCase("") && floor != null && !floor.equalsIgnoreCase("") && subCategory != null && !subCategory.equalsIgnoreCase(""))
									{
										List allotto = HUA.getRandomEmp4Escalation(tosubdept, deptLevel, tolevel.substring(5), "Y", floor, connectionSpace, subCategory);

										// //System.out.println(" allotto ::: "+allotto);
										if (allotto != null && allotto.size() > 0)
											{
												// //System.out.println("allted To :: "+allottoId);
												qry.append(" SELECT emp.id,emp.empName FROM employee_basic AS emp ");
												qry.append(" WHERE emp.id IN " + allotto.toString().replace("[", "(").replace("]", ")") + " ");
												resultList = HUH.getData(qry.toString(), connectionSpace);
												if (resultList != null && resultList.size() > 0)
													{
														for (Object c : resultList)
															{
																Object[] objVar = (Object[]) c;
																JSONObject listObject = new JSONObject();
																listObject.put("id", objVar[0].toString());
																listObject.put("name", objVar[1].toString());
																commonJSONArray.add(listObject);
															}
														resultList.clear();
													}
											}
									}
								returnResult = SUCCESS;
							}
						catch (Exception e)
							{
								e.printStackTrace();
							}
					}
				else
					{
						returnResult = LOGIN;
					}
				return returnResult;
			}

		@SuppressWarnings("rawtypes")
		public String manualReasign()
			{
				if (ValidateSession.checkSession())
					{
						try
							{
								HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
								String tolevel = null;
								// //System.out.println("Sub category :::: "+subCategory);
								if (subCategory != null && !subCategory.equalsIgnoreCase(""))
									{
										StringBuilder query = new StringBuilder();
										query.append(" Select subdept.id,subCat.escalationLevel from subdepartment as subdept ");
										query.append(" inner join feedback_type fb on fb.dept_subdept = subdept.id ");
										query.append(" inner join feedback_category catt on catt.fbType = fb.id ");
										query.append(" inner join feedback_subcategory subCat on subCat.categoryName = catt.id ");
										query.append(" WHERE subCat.id= " + subCategory);
										// //System.out.println(">>>>>> subCategory  >>>>> "+query.toString());
										List subCategoryList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
										if (subCategoryList != null && subCategoryList.size() > 0)
											{
												Object[] objectCol = (Object[]) subCategoryList.get(0);
												if (objectCol[1] == null)
													{
														tolevel = "Level1";
													}
												else
													{
														tolevel = objectCol[1].toString();
													}
												tosubdept = objectCol[0].toString();
											}
									}
								// //System.out.println("Level 1 ::::"+tolevel);
								List resultList = new ArrayList<String>();
								// //System.out.println("FLoor Statsu :::: "+floor_status);
								StringBuilder qry = new StringBuilder();
								manualMap = new LinkedHashMap<String, String>();
								if (caseManual != null && !caseManual.equalsIgnoreCase(""))
									{
										List data = getRandomEmp4Escalation(tosubdept, deptLevel, tolevel.substring(5), "Y", floor, caseManual);
										if (data != null && data.size() > 0)
											{
												qry.append(" SELECT emp.id,emp.empName FROM employee_basic AS emp ");
												qry.append(" WHERE emp.id IN " + data.toString().replace("[", "(").replace("]", ")") + " ");

												// //System.out.println("QQQQ*********QQQQQ"+qry.toString());
												resultList = HUH.getData(qry.toString(), connectionSpace);
												if (resultList != null && resultList.size() > 0)
													{
														for (Object c : resultList)
															{
																Object[] objVar = (Object[]) c;
																manualMap.put(objVar[0].toString(), objVar[1].toString());
															}
													}
											}
									}
								return SUCCESS;
							}
						catch (Exception e)
							{
								e.printStackTrace();
								return ERROR;
							}
					}
				else
					{
						return LOGIN;
					}
			}

		@SuppressWarnings("unchecked")
		public List<Integer> getRandomEmp4Escalation(String dept_subDept, String deptLevel, String level, String floor_status, String floor, String cases)
			{
				List<Integer> list = new ArrayList<Integer>();
				// String qry = null;
				StringBuilder query = new StringBuilder();
				try
					{
						// //System.out.println("cases::"+cases);
						if (cases.equalsIgnoreCase("q1Case1"))
							{
								query.append("select distinct emp.id from employee_basic as emp");
							}
						else
							{
								query.append("select distinct emp.id from employee_basic as emp");
								query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
								query.append(" inner join emp_wing_mapping ewm on contacts.emp_id = ewm.empName");
								query.append(" inner join subdepartment sub_dept on contacts.forDept_id = sub_dept.id ");
								// query.append(" inner join department dept on sub_dept.deptid = dept.id ");
								query.append(" inner join shift_with_emp_wing shift on shift.id = ewm.shiftId ");
								query.append(" inner join wings_detail wing on wing.id = ewm.wingsname ");
								query.append(" inner join floor_room_detail room on room.wingsname = wing.id ");

								query.append(" where  contacts.level='" + level + "' and contacts.work_status='0' and contacts.moduleName='HDM'");
								query.append(" and shift.fromShift<='" + DateUtil.getCurrentTime() + "' and shift.toShift >'" + DateUtil.getCurrentTime() + "'");
								if ("Y".equalsIgnoreCase(floor_status))
									{
										if (cases.equalsIgnoreCase("Case4"))
											{
												query.append(" and room.id='" + floor + "'  and contacts.forDept_id IN (" + dept_subDept + " )");
											}
										else if (cases.equalsIgnoreCase("q2Case2"))
											{
												query.append(" and room.id='" + floor + "'");
											}
										else if (cases.equalsIgnoreCase("q3Case3"))
											{
												query.append(" and contacts.forDept_id IN (" + dept_subDept + " )");
											}
									}
								else
									{
										query.append(" dept.id IN (SELECT deptid FROM subdepartment WHERE id = '" + dept_subDept + "' )");
									}

							}
						// //System.out.println("Manual Resign *****" +
						// query.toString());
						list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
					}
				catch (Exception e)
					{
						e.printStackTrace();
					}
				return list;
			}

		@SuppressWarnings("rawtypes")
		public String userListByDept()
			{
				String returnResult = ERROR;
				boolean sessionFlag = ValidateSession.checkSession();
				if (sessionFlag)
					{
						StringBuilder qry = new StringBuilder();
						try
							{
								if (userName == null || userName.equalsIgnoreCase(""))
									return LOGIN;

								List resultList = new ArrayList<String>();
								qry.append(" select emp.id,emp.empName from employee_basic as emp");
								qry.append(" where emp.deptName=" + conditionVar_Value + " order by emp.empName");
								// //System.out.println(" ********Query  " +
								// qry.toString());
								resultList = new HelpdeskUniversalHelper().getData(qry.toString(), connectionSpace);

								// //System.out.println("****List Size : " +
								// resultList.size());
								if (resultList.size() > 0)
									{
										for (Object c : resultList)
											{
												Object[] objVar = (Object[]) c;
												JSONObject listObject = new JSONObject();
												listObject.put("id", objVar[0].toString());
												listObject.put("name", objVar[1].toString());
												commonJSONArray.add(listObject);
											}
									}
								returnResult = SUCCESS;
							}
						catch (Exception e)
							{
								e.printStackTrace();
							}
					}
				else
					{
						returnResult = LOGIN;
					}
				return returnResult;
			}

		// Method for getting Contact detail from emp_basic table
		public String getContactDetail()
			{
				String returnResult = ERROR;
				try
					{
						empObj = new EmpBasicPojoBean();
						List empList = new HelpdeskUniversalAction().getEmpData(uid, pageFor, columnName);
						if (empList != null && empList.size() > 0)
							{
								for (Iterator iterator = empList.iterator(); iterator.hasNext();)
									{
										Object[] objectCol = (Object[]) iterator.next();

										// Set the value of Employee Name
										if (objectCol[0] == null || objectCol[0].toString().equals(""))
											{
												empObj.setEmpName("NA");
											}
										else
											{
												empObj.setEmpName(objectCol[0].toString());
											}

										// Set the value of Employee Mobile No
										if (objectCol[1] == null || objectCol[1].toString().equals(""))
											{
												empObj.setMobOne("NA");
											}
										else
											{
												empObj.setMobOne(objectCol[1].toString());
											}

										// Set The value of Employee Email ID
										if (objectCol[2] == null || objectCol[2].toString().equals(""))
											{
												empObj.setEmailIdOne("helpdesk@blkhospital.com");
											}
										else
											{
												empObj.setEmailIdOne(objectCol[2].toString());
											}

										if (objectCol[3] == null || objectCol[3].toString().equals(""))
											{
												empObj.setOther2("-1");
											}
										else
											{
												empObj.setOther2(objectCol[3].toString());
											}

										if (objectCol[4] == null || objectCol[4].toString().equals(""))
											{
												empObj.setDeptName("NA");
											}
										else
											{
												empObj.setDeptName(objectCol[4].toString());
											}

										if (objectCol[5] == null || objectCol[5].toString().equals(""))
											{
												empObj.setCity("NA");
											}
										else
											{
												empObj.setCity(objectCol[5].toString());
											}
										if (objectCol[6] == null || objectCol[6].toString().equals(""))
											{
												empObj.setEmpId("NA");
											}
										else
											{
												empObj.setEmpId(objectCol[6].toString());
											}

										if (pageFor.equalsIgnoreCase("SD"))
											{/*
											 * if (objectCol[5] == null ||
											 * objectCol[5].toString
											 * ().equals("")) {
											 * empObj.setId(-1); } else {
											 * empObj.setId(Integer .parseInt
											 * (objectCol[5] .toString())); }
											 * 
											 * if (objectCol[6] == null ||
											 * objectCol[6].toString
											 * ().equals("")) { empObj
											 * .setSubdept("NA"); } else {
											 * empObj.setSubdept (objectCol
											 * [6].toString()); }
											 */
											}

										else if (pageFor.equalsIgnoreCase("SD"))
											{/*
											 * if (objectCol[5] == null ||
											 * objectCol [5].toString
											 * ().equals("")) { empObj
											 * .setCity("NA"); } else {
											 * empObj.setCity (objectCol
											 * [5].toString()); }
											 */
											}
									}
							}

						returnResult = SUCCESS;
					}
				catch (Exception e)
					{
						e.printStackTrace();
					}
				return returnResult;
			}

		// Method for getting Employee data for ticket allocation
		@SuppressWarnings("unchecked")
		public String getEmp4Escalation()
			{
				String returnResult = ERROR;
				boolean sessionFlag = ValidateSession.checkSession();
				if (sessionFlag)
					{
						try
							{
								HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
								empData4Escalation = new ArrayList<Object>();
								String tolevel = "";
								// Code for getting the Escalation Date and
								// Escalation Time
								List subCategoryList = HUH.getAllData("feedback_subcategory", "id", subCategory, "subCategoryName", "ASC", connectionSpace);
								if (subCategoryList != null && subCategoryList.size() > 0)
									{
										for (Iterator iterator = subCategoryList.iterator(); iterator.hasNext();)
											{
												Object[] objectCol = (Object[]) iterator.next();

												if (objectCol[8] == null)
													{
														tolevel = "1";
													}
												else
													{
														tolevel = objectCol[8].toString().substring(5);
													}
											}
									}

								String dept_id = HUH.getDeptId(getTosubdept(), connectionSpace);
								String floor_status = HUH.getFloorStatus(dept_id, connectionSpace);

								List emp4Escalation = new ArrayList();
								String newToLevel = "";
								emp4Escalation = HUH.getEmp4Escalation(getTosubdept(), pageFor, dataFor, tolevel, floor_status, floor, connectionSpace, subCategory);
								if (emp4Escalation == null || emp4Escalation.size() == 0)
									{
										newToLevel = "" + (Integer.parseInt(tolevel) + 1);
										emp4Escalation = HUH.getEmp4Escalation(getTosubdept(), pageFor, dataFor, newToLevel, floor_status, floor, connectionSpace, subCategory);
										if (emp4Escalation == null || emp4Escalation.size() == 0)
											{
												newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
												emp4Escalation = HUH.getEmp4Escalation(getTosubdept(), pageFor, dataFor, newToLevel, floor_status, floor, connectionSpace, subCategory);
												if (emp4Escalation == null || emp4Escalation.size() == 0)
													{
														newToLevel = "" + (Integer.parseInt(newToLevel) + 1);
														emp4Escalation = HUH.getEmp4Escalation(getTosubdept(), pageFor, dataFor, newToLevel, floor_status, floor, connectionSpace, subCategory);
													}
											}
									}

								if (emp4Escalation != null && emp4Escalation.size() > 0)
									{
										setRecords(emp4Escalation.size());
										int to = (getRows() * getPage());
										@SuppressWarnings("unused")
										int from = to - getRows();
										if (to > getRecords())
											to = getRecords();

										List<Object> Listhb = new ArrayList<Object>();

										List fieldNames = new ArrayList();
										fieldNames.add("id");
										fieldNames.add("empName");
										fieldNames.add("mobOne");
										fieldNames.add("emailIdOne");

										for (Iterator it = emp4Escalation.iterator(); it.hasNext();)
											{
												Object[] obdata = (Object[]) it.next();
												Map<String, Object> one = new HashMap<String, Object>();
												for (int k = 0; k < fieldNames.size(); k++)
													{
														if (obdata[k] != null)
															{
																if (k == 0)
																	{
																		one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
																	}
																else
																	{
																		one.put(fieldNames.get(k).toString(), obdata[k].toString());
																	}
															}
													}
												Listhb.add(one);
											}
										setEmpData4Escalation(Listhb);
										setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
										returnResult = SUCCESS;
									}
							}
						catch (Exception e)
							{
								e.printStackTrace();
							}
					}
				else
					{
						returnResult = LOGIN;
					}
				return returnResult;
			}

		@SuppressWarnings({ "rawtypes" })
		public synchronized String registerFeedbackViaCall()
			{
			////System.out.println("call 11");
				String returnResult = ERROR;
				boolean sessionFlag = ValidateSession.checkSession();
				if (sessionFlag)
					{
						lock.lock();
						try
							{
							
							
								HelpdeskUniversalHelper HUH = new HelpdeskUniversalHelper();
								HelpdeskUniversalAction HUA = new HelpdeskUniversalAction();
								MsgMailCommunication MMC = new MsgMailCommunication();
								CommonOperInterface cot = new CommonConFactory().createInterface();
								List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_lodge_new_configuration", "", connectionSpace, "", 0, "table_name", "feedback_lodge_new_configuration");

								if (colName != null && colName.size() > 0)
									{
										@SuppressWarnings("unused")
										boolean status = false;
										List<TableColumes> TableColumnName = new ArrayList<TableColumes>();
										for (GridDataPropertyView tableColumes : colName)
											{
												TableColumes ob1 = new TableColumes();
												ob1.setColumnname(tableColumes.getColomnName());
												ob1.setDatatype("varchar(" + tableColumes.getWidth() + ")");
												ob1.setConstraint("default NULL");
												TableColumnName.add(ob1);

											}
										cot.createTable22("feedback_status_new", TableColumnName, connectionSpace);
										createHistoryFeedback(cot);

										FeedbackPojo fbp = null;
										String escalation_date = "NA", escalation_time = "NA", adrr_time = "", res_time = "", allottoId = "", to_dept_subdept = "", by_dept_subdept = "", ticketno = "", feedby = "", feedbymob = "", feedbyemailid = "", mailText = "", tolevel = "", needesc = "", esc_time = "";
										StringBuilder query1 = new StringBuilder();
										query1.append(" Select subdept.id,subCat.escalationLevel,subCat.addressingTime,subCat.resolutionTime,subCat.escalateTime,subCat.needEsc from subdepartment as subdept ");
										query1.append(" inner join feedback_type fb on fb.dept_subdept = subdept.id ");
										query1.append(" inner join feedback_category catt on catt.fbType = fb.id ");
										query1.append(" inner join feedback_subcategory subCat on subCat.categoryName = catt.id ");
										query1.append(" WHERE subCat.id= " + subCategory);
										// //System.out.println(">>>>>> subCategory  >>>>> "+query1.toString());
										List subCategoryList = new createTable().executeAllSelectQuery(query1.toString(), connectionSpace);
										if (subCategoryList != null && subCategoryList.size() > 0)
											{
												Object[] objectCol = (Object[]) subCategoryList.get(0);
												if (objectCol[1] == null)
													{
														tolevel = "Level1";
													}
												else
													{
														tolevel = objectCol[1].toString();
													}
												tosubdept = objectCol[0].toString();
												if (objectCol[2] == null)
													{
														adrr_time = "06:00";
													}
												else
													{
														adrr_time = objectCol[2].toString();
													}
												if (objectCol[3] == null)
													{
														res_time = "10:00";
													}
												else
													{
														res_time = objectCol[3].toString();
													}
												if (objectCol[4] == null)
													{
														esc_time = "10:00";
													}
												else
													{
														esc_time = objectCol[4].toString();
													}
												if (objectCol[5] == null)
													{
														needesc = "Y";
													}
												else
													{
														needesc = objectCol[5].toString();
													}
											}
										to_dept_subdept = tosubdept;
										String[] date_time_arr = null;
										String[] adddate_time = null;
										String Address_Date_Time = "NA";
										String deptid = HUH.getDeptId(to_dept_subdept, connectionSpace);
										EscalationHelper EH = new EscalationHelper();
										// //System.out.println("Location :::: " +
										// location);
										if (needesc != null && !needesc.equals("") && needesc.equals("Y"))
											{
												logger.info("Next Escalation DateTime Initially: " + new_escalation_datetime);
												getNextEscalationDateTime(adrr_time, res_time, deptid, "HDM", connectionSpace);
												logger.info("Next Escalation DateTime After GetNextEsclationDateTime: " + new_escalation_datetime);
												String[] newdate_time = null;
												if (new_escalation_datetime != null && new_escalation_datetime != "")
													{
														newdate_time = new_escalation_datetime.split("#");
														if (newdate_time.length > 0)
															{
																escalation_date = newdate_time[0];
																escalation_time = newdate_time[1];
															}
													}
												Address_Date_Time = DateUtil.newdate_BeforeTime(escalation_date, escalation_time, res_time);
												// //System.out.println("escalation date::: "+escalation_date);
												// //System.out.println("escalation time::: "+escalation_time);
												if (Address_Date_Time != null && !Address_Date_Time.equals("") && !Address_Date_Time.equals("NA"))
													{
														String esc_start_time = "00:00";
														String esc_end_time = "24:00";
														String esc_working_day = "";
														esc_working_day = DateUtil.getDayofDate(escalation_date);
														// List of working
														// timing data
														List workingTimingData = null;
														workingTimingData = EH.getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
														if (workingTimingData != null && workingTimingData.size() > 0)
															{
																String time_status = "", time_diff = "", working_hrs = "";
																adddate_time = Address_Date_Time.split("#");
																esc_start_time = workingTimingData.get(1).toString();
																esc_end_time = workingTimingData.get(2).toString();
																time_status = DateUtil.timeInBetween(escalation_date, esc_start_time, esc_end_time, adddate_time[0], adddate_time[1]);
																if (time_status != null && !time_status.equals("") && time_status.equals("before"))
																	{
																		time_diff = DateUtil.time_difference(adddate_time[0], adddate_time[1], escalation_date, esc_start_time);
																		String previous_working_date = "";
																		previous_working_date = EH.getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(escalation_date), -1), connectionSpace);
																		esc_working_day = DateUtil.getDayofDate(previous_working_date);
																		workingTimingData.clear();
																		workingTimingData = EH.getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
																		if (workingTimingData != null && workingTimingData.size() > 0)
																			{
																				esc_start_time = workingTimingData.get(1).toString();
																				esc_end_time = workingTimingData.get(2).toString();
																				working_hrs = workingTimingData.get(3).toString();
																				if (DateUtil.checkTwoTime(working_hrs, time_diff))
																					{
																						Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
																					}
																				else
																					{
																						time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
																						previous_working_date = EH.getNextOrPreviousWorkingDate("P", DateUtil.getNewDate("day", -1, previous_working_date), connectionSpace);

																						esc_working_day = DateUtil.getDayofDate(previous_working_date);
																						workingTimingData.clear();
																						workingTimingData = EH.getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
																						if (workingTimingData != null && workingTimingData.size() > 0)
																							{
																								esc_start_time = workingTimingData.get(1).toString();
																								esc_end_time = workingTimingData.get(2).toString();
																								working_hrs = workingTimingData.get(3).toString();
																								if (DateUtil.checkTwoTime(working_hrs, time_diff))
																									{
																										Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
																									}
																								else
																									{
																										time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
																									}
																							}
																					}
																			}
																	}
															}
													}
											}
										else
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
												workingTimingData = EH.getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
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
																previous_working_date = EH.getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(DateUtil.convertDateToIndianFormat(adddate_time[0]), -1), connectionSpace);
																esc_working_day = DateUtil.getDayofDate(previous_working_date);
																workingTimingData.clear();
																workingTimingData = EH.getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
																if (workingTimingData != null && workingTimingData.size() > 0)
																	{
																		esc_start_time = workingTimingData.get(2).toString();
																		esc_end_time = workingTimingData.get(3).toString();
																		working_hrs = workingTimingData.get(4).toString();
																		if (DateUtil.checkTwoTime(working_hrs, time_diff))
																			{
																				Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
																			}
																		else
																			{
																				time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
																				previous_working_date = EH.getNextOrPreviousWorkingDate("P", DateUtil.getNextDateFromDate(previous_working_date, -1), connectionSpace);
																				esc_working_day = DateUtil.getDayofDate(previous_working_date);
																				workingTimingData.clear();
																				workingTimingData = EH.getWorkingTimeData("HDM", esc_working_day, deptid, connectionSpace);
																				if (workingTimingData != null && workingTimingData.size() > 0)
																					{

																						esc_start_time = workingTimingData.get(2).toString();
																						esc_end_time = workingTimingData.get(3).toString();
																						working_hrs = workingTimingData.get(4).toString();
																						if (DateUtil.checkTwoTime(working_hrs, time_diff))
																							{
																								Address_Date_Time = DateUtil.newdate_BeforeTime(previous_working_date, esc_end_time, time_diff);
																							}
																						else
																							{
																								time_diff = DateUtil.getTimeDifference(time_diff, working_hrs);
																							}
																					}
																			}
																	}
															}
													}
											}
										List empData = HUA.getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
										if (empData != null && empData.size() > 0)
											{
												for (Iterator iterator = empData.iterator(); iterator.hasNext();)
													{
														Object[] object = (Object[]) iterator.next();
														if (object[0] != null && !object[0].toString().equals(""))
															{
																feedby = object[0].toString();
															}
														else
															{
																feedby = "NA";
															}

														if (object[1] != null && !object[1].toString().equals(""))
															{
																feedbymob = object[1].toString();
															}
														else
															{
																feedbymob = "NA";
															}

														if (object[2] != null && !object[2].toString().equals(""))
															{
																feedbyemailid = object[2].toString();
															}
														else
															{
																feedbyemailid = "NA";
															}

														if (object[3] != null && !object[3].toString().equals(""))
															{
																by_dept_subdept = object[3].toString();
															}
														else
															{
																by_dept_subdept = "-1";
															}
													}
											}
										allottoId = allotto;
										// String ticketLevel = "";
										// //System.out.println("allottoId  ::: "
										// + allottoId);
										// if (allottoId != null &&
										// !allottoId.equals(""))
										// {
										// ticketLevel =
										// HUH.getTicketLevel(allottoId,
										// connectionSpace);
										// }
										String demoTicketSeries = new TicketConfiguration().getTicketSeries("HDM", deptid);
										if (demoTicketSeries != null)
											{
												ticketno = demoTicketSeries.split("#")[0];
											}
										// //System.out.println("ticketno :::::: "
										// + ticketno);
										if (ticketno != null && !ticketno.equalsIgnoreCase("") && !ticketno.equalsIgnoreCase("NA") && allottoId != null && !allottoId.equals(""))
											{
												exist_ticket = true;
												boolean chkDoubleHIT = false;
												int ckhTicket = 0;
												String qry = "SELECT ticket_no,emp.empName,emp.mobOne,feed.open_date,feed.open_time,feed.id,feed.status FROM feedback_status_new as feed LEFT JOIN employee_basic as emp on emp.id=feed.allot_to WHERE feed.subCatg='" + subCategory + "' AND feed.location='" + location.split(",")[0].trim() + "' AND feed.status IN('Pending','Snooze')";
												List existFlag = cot.executeAllSelectQuery(qry, connectionSpace);
												if (existFlag != null && existFlag.size() > 0)
													{
													
														
														Object[] obj = (Object[]) existFlag.get(0);
														
													String tempTimeDiff = 	DateUtil.time_difference(obj[3].toString(), obj[4].toString(), DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime());
														System.out.println("tempTimeDiff  "+tempTimeDiff);
														if(Integer.parseInt(tempTimeDiff.split(":")[1])<1 && Integer.parseInt(tempTimeDiff.split(":")[0])<1){
															exist_ticket = true;
															chkDoubleHIT = true;
														}
														else{
															exist_ticket = false;
														}
														feedbackBy = DateUtil.makeTitle(userName);
														ticket_no = obj[0].toString();
														allotto = obj[1].toString();
														open_date = DateUtil.convertDateToIndianFormat(obj[3].toString());
														open_time = obj[4].toString().substring(0, 5);
														allot_to_mobno = obj[2].toString();
														feedStatus = obj[6].toString();
														id = obj[5].toString();
													}
												
												if(chkDoubleHIT==false){
													String qryCHK = "select id, ticket_no from feedback_status_new order by id desc limit 1 ";
													List existFlagTicket = cot.executeAllSelectQuery(qryCHK, connectionSpace);
													if (existFlagTicket != null && existFlagTicket.size() > 0)
														{
														
															
															Object[] obj1 = (Object[]) existFlagTicket.get(0);
															
															System.out.println("ticketno "+ticketno);
															System.out.println("HIS LAST TICKET "+obj1[1].toString());
															if(obj1[1].toString().equalsIgnoreCase(ticketno)){
																ckhTicket = 1; 
															}
															else{
																ckhTicket = 0;
															}
														
														}
												}
												if (exist_ticket && chkDoubleHIT==false)
													{
													if( ckhTicket==0){
														
														System.out.println("inserteed new ticket");
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
														ob.setDataName(subCategory);
														insertData.add(ob);

														ob = new InsertDataTable();
														ob.setColName("feed_brief");
														ob.setDataName(feed_brief);
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
														logger.info("Escalation_date TO database: " + escalation_date);
														ob.setColName("escalation_date");
														ob.setDataName(escalation_date);
														insertData.add(ob);

														ob = new InsertDataTable();
														ob.setColName("escalation_time");
														logger.info("Escalation_time TO database: " + escalation_time);
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
														ob.setDataName(location.split(",")[0]);
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
														ob.setDataName(userName);
														insertData.add(ob);

														int status1 = 0;
														status1 = cot.insertDataReturnId("feedback_status_new", insertData, connectionSpace);
														insertData.clear();
														if (status1 > 0)
															{
																ob = new InsertDataTable();
																ob.setColName("feedId");
																ob.setDataName(status1);
																insertData.add(ob);

																ob = new InsertDataTable();
																ob.setColName("action_by");
																ob.setDataName(session.get("empIdofuser").toString().split("-")[1]);
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

																cot.insertIntoTable("feedback_status_history", insertData, connectionSpace);

																List data = HUH.getFeedbackDetail("", "SD", "Pending", String.valueOf(status1), connectionSpace);
																if (data != null && !data.isEmpty())
																	{
																		fbp = HUH.setFeedbackDataValues(data, "Pending", deptLevel);
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
																				String levelMsg = "Open: " + fbp.getTicket_no() + ", Assign To: " + fbp.getFeedback_allot_to() + ", To Be Closed By: " + DateUtil.convertDateToIndianFormat(fbp.getEscalation_date()) + " " + fbp.getEscalation_time() + ", Call Desc: " + fbp.getFeedback_subcatg() + ", Loc: " + fbp.getRoomNo() + ", Brief: " + fbp.getFeed_brief() + ".";
																				MMC.addMessage(fbp.getFeedback_allot_to(), fbp.getFeedback_to_dept(), fbp.getMobOne(), levelMsg, ticketno, "Pending", "0", "HDM");
																				if(fbp.getFeedback_to_dept().equalsIgnoreCase("Food & Beverages")){
																					//MMC.addMessage("Admin FandB", fbp.getFeedback_to_dept(), "9873659528", levelMsg, ticketno, "Pending", "0", "HDM");
																				}
																			}
																		if (fbp.getEmailIdOne() != null && !fbp.getEmailIdOne().equals("") && !fbp.getEmailIdOne().equals("NA"))
																			{
																				mailText = HUH.getConfigMessage(fbp, "Open Feedback Alert", "Pending", deptLevel, true);
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
														return SUCCESS;
													}
												else
													{
														return SUCCESS;
													}
											}
										else
											{
												if (ticketno == null || ticketno.equalsIgnoreCase("") || ticketno.equalsIgnoreCase("NA"))
													{
														addActionMessage("Please Check Your Ticket Series!!!");
													}
												else if (allottoId == null || allottoId.equalsIgnoreCase("") || allottoId.equalsIgnoreCase("-1"))
													{
														addActionMessage("May be Someone is not mapped for taking the complaint. Please contact to concern service department !!!");
													}
												returnResult = "TicketError";
												// The Ticket for this location and Sub Category is Already in
											}
									}
							}
						catch (Exception e)
							{
								e.printStackTrace();
							}
						finally
							{
								lock.unlock();
							}
					}
				else
					{
						returnResult = LOGIN;
					}
				return returnResult;
			}

		private boolean createHistoryFeedback(CommonOperInterface cot)
			{
				boolean flag = false;
				try
					{
						List<TableColumes> tableColume = new ArrayList<TableColumes>();

						TableColumes ob1 = new TableColumes();

						ob1 = new TableColumes();
						ob1.setColumnname("feedId");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("status");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("action_date");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("action_time");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("action_duration");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("action_by");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("action_reason");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("action_remark");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("sn_upto_date");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("sn_upto_time");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("previous_allot_to");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("escalation_level");
						ob1.setDatatype("varchar(255)");
						ob1.setConstraint("default NULL");
						tableColume.add(ob1);

						ob1 = new TableColumes();
						ob1.setColumnname("snooze_status");
						ob1.setDatatype("varchar(2)");
						ob1.setConstraint("default '0'");
						tableColume.add(ob1);

						flag = cot.createTable22("feedback_status_history", tableColume, connectionSpace);

					}
				catch (Exception e)
					{
						e.printStackTrace();
					}
				return flag;
			}

		@SuppressWarnings("rawtypes")
		public void getNextEscalationDateTime(String adrr_time, String res_time, String todept, String module, SessionFactory connectionSpace)
			{
				String startTime = "", endTime = "", callflag = "";
				String dept_id = todept;
				EscalationHelper EH = new EscalationHelper();
				//String working_date = EH.getNextOrPreviousWorkingDate("N", DateUtil.getCurrentDateUSFormat(), connectionSpace);
				String working_date=DateUtil.getCurrentDateUSFormat();
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
						logger.info("working_date: "+working_date+"startTime: "+startTime+" endTime: "+endTime);
						callflag = DateUtil.timeInBetween(working_date, startTime, endTime, DateUtil.getCurrentTime());
						if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("before"))
							{

								new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, startTime, adrr_time, res_time);
								logger.info("before: " + new_escalation_datetime);
								String newdatetime[] = new_escalation_datetime.split("#");
								// Check the date time is lying inside the time
								// block
								boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
								if (flag)
									{
										non_working_timing = DateUtil.time_difference(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), working_date, startTime);
									}
								else
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
							}
						else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("middle"))
							{
								new_escalation_datetime = DateUtil.newdate_AfterEscInRoaster(working_date, DateUtil.getCurrentTime(), adrr_time, res_time);
								logger.info("middle newdate_AfterEscRoaster: " + new_escalation_datetime);
								String newdatetime[] = new_escalation_datetime.split("#");
								boolean flag = DateUtil.comparebetweenTwodateTime(newdatetime[0], newdatetime[1], working_date, endTime);
								if (flag)
									{
										non_working_timing = "00:00";
									}
								else
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
							}
						else if (callflag != null && !callflag.equals("") && callflag.equalsIgnoreCase("after"))
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

		public boolean mapEscalation(String id, String level, String date, String time, SessionFactory connection)
			{
				boolean flag = false;

				try
					{
						CommonOperInterface cot = new CommonConFactory().createInterface();
						List<TableColumes> TableColumnName = new ArrayList<TableColumes>();

						TableColumes tc = new TableColumes();
						tc.setColumnname("feed_status_id");
						tc.setDatatype("varchar(10)");
						tc.setConstraint("default NULL");
						TableColumnName.add(tc);

						tc = new TableColumes();
						tc.setColumnname("allot_to_level");
						tc.setDatatype("varchar(10)");
						tc.setConstraint("default NULL");
						TableColumnName.add(tc);

						tc = new TableColumes();
						tc.setColumnname("nextEscDateTime");
						tc.setDatatype("varchar(20)");
						tc.setConstraint("default NULL");
						TableColumnName.add(tc);

						tc = new TableColumes();
						tc.setColumnname("nextEscLevel_2");
						tc.setDatatype("varchar(10)");
						tc.setConstraint("default NULL");
						TableColumnName.add(tc);

						tc = new TableColumes();
						tc.setColumnname("EscLevel_2_DateTime");
						tc.setDatatype("varchar(20)");
						tc.setConstraint("default NULL");
						TableColumnName.add(tc);

						tc = new TableColumes();
						tc.setColumnname("nextEscLevel_3");
						tc.setDatatype("varchar(10)");
						tc.setConstraint("default NULL");
						TableColumnName.add(tc);

						tc = new TableColumes();
						tc.setColumnname("EscLevel_3_DateTime");
						tc.setDatatype("varchar(20)");
						tc.setConstraint("default NULL");
						TableColumnName.add(tc);

						tc = new TableColumes();
						tc.setColumnname("nextEscLevel_4");
						tc.setDatatype("varchar(10)");
						tc.setConstraint("default NULL");
						TableColumnName.add(tc);

						tc = new TableColumes();
						tc.setColumnname("EscLevel_4_DateTime");
						tc.setDatatype("varchar(20)");
						tc.setConstraint("default NULL");
						TableColumnName.add(tc);

						tc = new TableColumes();
						tc.setColumnname("nextEscLevel_5");
						tc.setDatatype("varchar(10)");
						tc.setConstraint("default NULL");
						TableColumnName.add(tc);

						tc = new TableColumes();
						tc.setColumnname("EscLevel_5_DateTime");
						tc.setDatatype("varchar(20)");
						tc.setConstraint("default NULL");
						TableColumnName.add(tc);

						// Method calling for creating table on the basis of
						// above
						cot.createTable22("escalation_mapping", TableColumnName, connection);

						InsertDataTable ob = new InsertDataTable();
						List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
						ob.setColName("feed_status_id");
						ob.setDataName(id);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("allot_to_level");
						ob.setDataName(level);
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("nextEscDateTime");
						ob.setDataName(DateUtil.convertDateToIndianFormat(date) + "(" + time + ")");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("nextEscLevel_2");
						ob.setDataName("NA");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("EscLevel_2_DateTime");
						ob.setDataName("NA");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("nextEscLevel_3");
						ob.setDataName("NA");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("EscLevel_3_DateTime");
						ob.setDataName("NA");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("nextEscLevel_4");
						ob.setDataName("NA");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("EscLevel_4_DateTime");
						ob.setDataName("NA");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("nextEscLevel_5");
						ob.setDataName("NA");
						insertData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("EscLevel_5_DateTime");
						ob.setDataName("NA");
						insertData.add(ob);

						// Method calling for inserting the values in the
						// feedback_status table
						@SuppressWarnings("unused")
						boolean status1 = cot.insertIntoTable("escalation_mapping", insertData, connection);
						insertData.clear();

					}
				catch (Exception e)
					{
						// TODO: handle exception
					}
				return flag;
			}

		public String fetchCategoryOnline()
			{
				// //System.out.println("INSIDE METHOD");
				String returnResult = ERROR;
				boolean sessionFlag = ValidateSession.checkSession();
				if (sessionFlag)
					{
						StringBuilder qry = new StringBuilder();
						try
							{
								if (userName == null || userName.equalsIgnoreCase(""))
									return LOGIN;

								List resultList = new ArrayList<String>();
								qry.append(" SELECT subdept.id,subdept.subdeptname FROM feedback_type AS fbtype    ");
								qry.append(" INNER JOIN subdepartment AS subdept ON subdept.id=fbtype.dept_subdept    ");
								qry.append("  WHERE fbtype.dept_subdept IN (SELECT id FROM subdepartment WHERE deptid='" + bydept_subdept + "') and  moduleName='HDM' ");
								resultList = new HelpdeskUniversalHelper().getData(qry.toString(), connectionSpace);
								// //System.out.println("Category Query ::::: "+qry.toString());
								if (resultList.size() > 0)
									{
										for (Object c : resultList)
											{
												Object[] objVar = (Object[]) c;
												JSONObject listObject = new JSONObject();
												listObject.put("id", objVar[0].toString());
												listObject.put("name", objVar[1].toString());
												commonJSONArray.add(listObject);
											}
									}
								returnResult = SUCCESS;
							}
						catch (Exception e)
							{
								e.printStackTrace();
							}
					}
				else
					{
						returnResult = LOGIN;
					}
				return returnResult;
			}

		public void printTicket(String ticketNo, String flag)
			{
				fstatus = new FeedbackPojo();
				orgImgPath = new CommonWork().getOrganizationImage((SessionFactory) session.get("connectionSpace"));
				fstatus = new ActionOnFeedback().getTicketDetail(ticketNo, flag);
			}

		public String getSeverityLevel(String deptid, String adrr_time, String esc_time, String res_time)
			{
				String severityLevel = null;
				try
					{
						String query = "SELECT severityCheckOn,fromTime,toTime,severityLevel FROM severity_detail WHERE deptName=" + deptid;
						// //System.out.println("QQQQQQQQ***********"+query);
						List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
						if (dataList != null && dataList.size() > 0)
							{
								for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										if (object[0] != null)
											{
												if (object[0].toString().equalsIgnoreCase("AT"))
													{
														if (object[1] != null && object[2] != null)
															{
																boolean checkStatus = DateUtil.checkTimeBetween2Times(object[1].toString(), object[2].toString(), adrr_time);
																if (checkStatus)
																	{
																		severityLevel = object[3].toString();
																		break;
																	}
															}
													}
												else if (object[0].toString().equalsIgnoreCase("RT"))
													{
														if (object[1] != null && object[2] != null)
															{
																boolean checkStatus = DateUtil.checkTimeBetween2Times(object[1].toString(), object[2].toString(), res_time);
																if (checkStatus)
																	{
																		severityLevel = object[3].toString();
																		break;
																	}
															}
													}
												else if (object[0].toString().equalsIgnoreCase("ET"))
													{
														if (object[1] != null && object[2] != null)
															{
																boolean checkStatus = DateUtil.checkTimeBetween2Times(object[1].toString(), object[2].toString(), esc_time);
																if (checkStatus)
																	{
																		severityLevel = object[3].toString();
																		break;
																	}
															}
													}
											}
									}
							}
					}
				catch (Exception e)
					{
						e.printStackTrace();
					}
				return severityLevel;
			}

		public String getUid()
			{
				return uid;
			}

		public void setUid(String uid)
			{
				this.uid = uid;
			}

		public EmpBasicPojoBean getEmpObj()
			{
				return empObj;
			}

		public void setEmpObj(EmpBasicPojoBean empObj)
			{
				this.empObj = empObj;
			}

		public String getFeedStatus()
			{
				return feedStatus;
			}

		public void setFeedStatus(String feedStatus)
			{
				this.feedStatus = feedStatus;
			}

		public List<Object> getEmpData4Escalation()
			{
				return empData4Escalation;
			}

		public void setEmpData4Escalation(List<Object> empData4Escalation)
			{
				this.empData4Escalation = empData4Escalation;
			}

		public String getSelectedId()
			{
				return selectedId;
			}

		public void setSelectedId(String selectedId)
			{
				this.selectedId = selectedId;
			}

		public String getFeedbackBy()
			{
				return feedbackBy;
			}

		public void setFeedbackBy(String feedbackBy)
			{
				this.feedbackBy = feedbackBy;
			}

		public String getTodept()
			{
				return todept;
			}

		public void setTodept(String todept)
			{
				this.todept = todept;
			}

		public String getSubCategory()
			{
				return subCategory;
			}

		public void setSubCategory(String subCategory)
			{
				this.subCategory = subCategory;
			}

		public String getFeed_brief()
			{
				return feed_brief;
			}

		public void setFeed_brief(String feed_brief)
			{
				this.feed_brief = feed_brief;
			}

		public String getLocation()
			{
				return location;
			}

		public void setLocation(String location)
			{
				this.location = location;
			}

		public String getRecvSMS()
			{
				return recvSMS;
			}

		public void setRecvSMS(String recvSMS)
			{
				this.recvSMS = recvSMS;
			}

		public String getTosubdept()
			{
				return tosubdept;
			}

		public void setTosubdept(String tosubdept)
			{
				this.tosubdept = tosubdept;
			}

		public String getEscTime()
			{
				return escTime;
			}

		public void setEscTime(String escTime)
			{
				this.escTime = escTime;
			}

		public String getOpen_date()
			{
				return open_date;
			}

		public void setOpen_date(String open_date)
			{
				this.open_date = open_date;
			}

		public String getOpen_time()
			{
				return open_time;
			}

		public void setOpen_time(String open_time)
			{
				this.open_time = open_time;
			}

		public String getAllotto()
			{
				return allotto;
			}

		public void setAllotto(String allotto)
			{
				this.allotto = allotto;
			}

		public String getSubCatg()
			{
				return subCatg;
			}

		public void setSubCatg(String subCatg)
			{
				this.subCatg = subCatg;
			}

		public String getAddrTime()
			{
				return addrTime;
			}

		public void setAddrTime(String addrTime)
			{
				this.addrTime = addrTime;
			}

		public String getAddrDate()
			{
				return addrDate;
			}

		public void setAddrDate(String addrDate)
			{
				this.addrDate = addrDate;
			}

		public String getTicket_no()
			{
				return ticket_no;
			}

		public void setTicket_no(String ticket_no)
			{
				this.ticket_no = ticket_no;
			}

		public String getBydept_subdept()
			{
				return bydept_subdept;
			}

		public void setBydept_subdept(String bydept_subdept)
			{
				this.bydept_subdept = bydept_subdept;
			}

		public String getAllot_to_mobno()
			{
				return allot_to_mobno;
			}

		public void setAllot_to_mobno(String allot_to_mobno)
			{
				this.allot_to_mobno = allot_to_mobno;
			}

		public String getRecvEmail()
			{
				return recvEmail;
			}

		public void setRecvEmail(String recvEmail)
			{
				this.recvEmail = recvEmail;
			}

		public String getFeedType()
			{
				return feedType;
			}

		public void setFeedType(String feedType)
			{
				this.feedType = feedType;
			}

		public String getFloor()
			{
				return floor;
			}

		public void setFloor(String floor)
			{
				this.floor = floor;
			}

		public String getDataFor()
			{
				return dataFor;
			}

		public void setDataFor(String dataFor)
			{
				this.dataFor = dataFor;
			}

		public Map<Integer, String> getServiceDeptList()
			{
				return serviceDeptList;
			}

		public void setServiceDeptList(Map<Integer, String> serviceDeptList)
			{
				this.serviceDeptList = serviceDeptList;
			}

		public String getPageFor()
			{
				return pageFor;
			}

		public void setPageFor(String pageFor)
			{
				this.pageFor = pageFor;
			}

		public String getAddressing_Time()
			{
				return Addressing_Time;
			}

		public void setAddressing_Time(String addressing_Time)
			{
				Addressing_Time = addressing_Time;
			}

		public JSONArray getCommonJSONArray()
			{
				return commonJSONArray;
			}

		public void setCommonJSONArray(JSONArray commonJSONArray)
			{
				this.commonJSONArray = commonJSONArray;
			}

		public String getConditionVar_Value()
			{
				return conditionVar_Value;
			}

		public void setConditionVar_Value(String conditionVar_Value)
			{
				this.conditionVar_Value = conditionVar_Value;
			}

		public String getColumnName()
			{
				return columnName;
			}

		public void setColumnName(String columnName)
			{
				this.columnName = columnName;
			}

		public FeedbackPojo getFstatus()
			{
				return fstatus;
			}

		public void setFstatus(FeedbackPojo fstatus)
			{
				this.fstatus = fstatus;
			}

		public String getOrgImgPath()
			{
				return orgImgPath;
			}

		public void setOrgImgPath(String orgImgPath)
			{
				this.orgImgPath = orgImgPath;
			}

		public String getRoomNo()
			{
				return roomNo;
			}

		public void setRoomNo(String roomNo)
			{
				this.roomNo = roomNo;
			}

		public Map<String, String> getAllRoomNo()
			{
				return allRoomNo;
			}

		public void setAllRoomNo(Map<String, String> allRoomNo)
			{
				this.allRoomNo = allRoomNo;
			}

		public String getFeedid()
			{
				return feedid;
			}

		public void setFeedid(String feedid)
			{
				this.feedid = feedid;
			}

		public String getAllotedto()
			{
				return allotedto;
			}

		public void setAllotedto(String allotedto)
			{
				this.allotedto = allotedto;
			}

		public Map<String, String> getSubCategList()
			{
				return subCategList;
			}

		public void setSubCategList(Map<String, String> subCategList)
			{
				this.subCategList = subCategList;
			}

		public String getCaseManual()
			{
				return caseManual;
			}

		public void setCaseManual(String caseManual)
			{
				this.caseManual = caseManual;
			}

		public Map<String, String> getManualMap()
			{
				return manualMap;
			}

		public void setManualMap(Map<String, String> manualMap)
			{
				this.manualMap = manualMap;
			}

		public boolean isExist_ticket()
			{
				return exist_ticket;
			}

		public void setExist_ticket(boolean exist_ticket)
			{
				this.exist_ticket = exist_ticket;
			}

	}