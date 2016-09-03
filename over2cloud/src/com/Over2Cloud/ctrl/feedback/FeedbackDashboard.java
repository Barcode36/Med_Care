package com.Over2Cloud.ctrl.feedback;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.FeedbackDataPojo;
import com.Over2Cloud.BeanUtil.FeedbackOverallSummaryBean;
import com.Over2Cloud.BeanUtil.FeedbackTicketPojo;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.feedback.activity.ActivityBoardHelper;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojoDashboard;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.ctrl.feedback.pojo.DeptActionPojo;
import com.Over2Cloud.ctrl.feedback.pojo.FeedbackAnalysis;
import com.Over2Cloud.ctrl.feedback.pojo.NegativeFeedbackStagePojo;
import com.Over2Cloud.ctrl.feedback.pojo.TicketAllotmentPojo;
import com.Over2Cloud.modal.dao.imp.feedbackDashboard.FeedbackDashboardDao;
import com.opensymphony.xwork2.ActionContext;

public class FeedbackDashboard extends GridPropertyBean
{
	/**
* 
*/
	private static final long serialVersionUID = 326895984327492755L;
	private List<FeedbackPojoDashboard> dashPojoList;
	private List<FeedbackOverallSummaryBean> summaryList;
	private List<FeedbackDataPojo> feedDataDashboardList;
	private List<FeedbackDataPojo> feedDataDashboardType;
	private List<TicketAllotmentPojo> allotPojo;
	private List<FeedbackTicketPojo> feedTicketList;
	private JSONArray jsonArray;
	private String graphHeader;
	private String graphDescription;
	private String yAxisHeader;
	private String xAxisHeader;
	private Map<String, Integer> pieYesNoMap;
	private Map<Integer, String> pieFeedbackCounter;
	private Map<String, Integer> pieNegFeedStatus;
	private Map<String, String> feedbackPieCounters;
	private String totalPending;
	private String totalLevel1;
	private String totalLevel2;
	private String totalLevel3;
	private String totalLevel4;
	private String totalLevel5;
	private String totalSnooze;
	private String totalHighPriority;
	private String totalIgnore;
	private String totalReAssign;
	private String totalResolve;
	private String totalCounter;
	private String totalData;
	private String today;
	private String block;
	private List<GridDataPropertyView> masterViewProp = null;
	private String status;
	private String deptId;
	private String type;
	private List<Object> masterViewList;
	private String feedId;
	private Map<String, String> docMap;
	private String documentName;
	private List<FeedbackAnalysis> analysisPojo;
	private List<DeptActionPojo> deptActionPojo;
	private String subTotalIssue;
	private String SubTotalAction;
	private String SubTotalCapa;
	private String fromDate = null;
	private String toDate = null;
	private String totalNoted;
	private String totalTO;
	private String totalNFA;
	private List<NegativeFeedbackStagePojo> negativeFeedList;

	private static final CommonOperInterface cbt = new CommonConFactory().createInterface();
	private InputStream fileInputStream;
	private String fileName;

	public String getDeptFeedAllotDetails()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				allotPojo = new ArrayList<TicketAllotmentPojo>();
				allotPojo = new FeedbackDashboardDao().getDeptWiseTicketAllotmentDetails(getDeptId(), connectionSpace);
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String beforePositiveNegativeGraph()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				pieYesNoMap = new HashMap<String, Integer>();
				pieYesNoMap = new FeedbackDashboardDao().getYesNoMap(connectionSpace);
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String beforeNegativeFeedbackStatusGraph()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				pieNegFeedStatus = new HashMap<String, Integer>();
				pieNegFeedStatus = new FeedbackDashboardDao().getYesNoMap(connectionSpace);
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("rawtypes")
	public String showFeedbackCountersPie()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map<String, Object> session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

				feedbackPieCounters = new TreeMap<String, String>();
				List dataList = cbt.executeAllSelectQuery(" select mode,count(*) from feedbackdata group by mode ", connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();

						if (object != null)
						{
							if (object[0] != null && object[1] != null)
							{
								feedbackPieCounters.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String getNegativeFeedCounter()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				negativeFeedList = new ArrayList<NegativeFeedbackStagePojo>();
				Map<String, Object> session = ActionContext.getContext().getSession();
				EscalationActionControlDao EAC = new EscalationActionControlDao();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				StringBuffer buffer = null;

				jsonArray = new JSONArray();

				int count = 0;
				int count1 = 0;
				List<String> modesList = new ArrayList<String>();
				// if(tableExists)
				{
					// modesList=new
					// EscalationActionControlDao().getGraphData
					// (connectionSpace,"feedbackdata","mode","select");
					modesList.add("Paper");
					modesList.add("SMS");
					modesList.add("IPD");
					modesList.add("OPD");
					modesList.add("Admin Round");
					modesList.add("Ward Rounds");

					// modesList.add("Voice");
					// modesList.add("Call");

				}
				if (status != null && !status.equalsIgnoreCase(""))
				{
					modesList.clear();
					modesList.add(status);
				}
				if (fromDate != null && toDate != null)
				{

					String str1 = " and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' and moduleName='FM'";
					for (String str : modesList)
					{
						NegativeFeedbackStagePojo pojo = new NegativeFeedbackStagePojo();
						pojo.setMode(str);
						if (str.equalsIgnoreCase("Paper") || str.equalsIgnoreCase("SMS"))
						{

							buffer = new StringBuffer("select count(*) from feedback_status where via_from ='" + str + "' and status ='pending' and subCatg='469'");

							buffer.append(str1);
							count = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
							pojo.setTotalSeek(String.valueOf(count));
							count = 0;

							// Stage I total
							if (str.equalsIgnoreCase("Paper"))
							{
								buffer = new StringBuffer("select count(*) from feedback_status_feed_paperRating where via_from ='" + str + "'  and (feed_brief  like '%Poor' or feed_brief  like '%Average') ");
								buffer.append(str1);
								// System.out.println("stage I total from new="+buffer);
								count1 = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
							}
							buffer = new StringBuffer("select count(*) from feedback_status where via_from ='" + str + "' and escalation_date!='NA' and stage='1' ");
							buffer.append(str1);
							count = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
							pojo.setStage1Total(String.valueOf(count + count1));
							if (block != null && block.equalsIgnoreCase("Stage Ist"))
							{
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("mode", "Total");
								jsonObject.put("counter", count + count1);
								count1 = 0;
								count = 0;
								jsonArray.add(jsonObject);
							}

							// Stage I pending
							if (str.equalsIgnoreCase("Paper"))
							{
								buffer = new StringBuffer("select count(*) from feedback_status_feed_paperRating where via_from ='" + str + "' and status ='pending' and stage='1' and (feed_brief  like '%Poor' or feed_brief  like '%Average') ");
								buffer.append(str1);
								count1 = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
							}
							buffer = new StringBuffer("select count(*) from feedback_status where via_from ='" + str + "' and status ='pending' and escalation_date!='NA' and stage='1' ");
							buffer.append(str1);
							count = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
							pojo.setStage1Pending(String.valueOf(count + count1));
							if (block != null && block.equalsIgnoreCase("Stage Ist"))
							{
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("mode", "Pending");
								jsonObject.put("counter", count + count1);
								count1 = 0;
								count = 0;
								jsonArray.add(jsonObject);
							}
							// Stage I Action Taken
							if (str.equalsIgnoreCase("Paper"))
							{
								buffer = new StringBuffer("select count(*) from feedback_status_feed_paperRating where via_from ='" + str + "' and stage='2'");
								buffer.append(str1);
								count1 = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
							}
							buffer = new StringBuffer("select count(*) from feedback_status where via_from ='" + str + "' and status IN('Ticket Opened','No Further Action Required') and escalation_date!='NA' ");
							buffer.append(str1);
							count = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
							pojo.setStage1ActionTaken(String.valueOf(count + count1));
							if (block != null && block.equalsIgnoreCase("Stage Ist"))
							{
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("mode", "Action Taken");
								jsonObject.put("counter", count + count1);
								count1 = 0;
								count = 0;
								jsonArray.add(jsonObject);
							}

							// Stage I No Further action
							if (str.equalsIgnoreCase("Paper"))
							{
								buffer = new StringBuffer("select count(*) from feedback_status_feed_paperRating where via_from='" + str + "' and status='No Further Action Required' ");
								buffer.append(str1);
								count1 = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
							}
							buffer = new StringBuffer("select count(*) from feedback_status where via_from='" + str + "' and status='No Further Action Required' and escalation_date!='NA' ");
							buffer.append(str1);
							count = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
							pojo.setStage1NoFurtherAction(String.valueOf(count + count1));
							if (block != null && block.equalsIgnoreCase("Stage Ist"))
							{
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("mode", "No Further Action");
								jsonObject.put("counter", count + count1);
								count1 = 0;
								count = 0;
								jsonArray.add(jsonObject);
							}
						}
						// stage-II ticket opened
						buffer = new StringBuffer("select count(*) from feedback_status where via_from ='" + str + "' and status NOT IN('Ticket Opened','No Further Action Required')  and escalation_date!='NA' and stage='2' ");
						buffer.append(str1);
						count = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
						pojo.setStage2TicketOpened(String.valueOf(count));
						if (block != null && block.equalsIgnoreCase("Stage IInd"))
						{
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("mode", "Ticket Opened");
							jsonObject.put("counter", count);
							count = 0;
							jsonArray.add(jsonObject);
						}

						// Stage-II Pending
						buffer = new StringBuffer("select count(*) from feedback_status where via_from='" + str + "' and escalation_date!='NA' and status='Pending' and stage='2' ");
						buffer.append(str1);
						count = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
						pojo.setStage2Pending(String.valueOf(count));
						if (block != null && block.equalsIgnoreCase("Stage IInd"))
						{
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("mode", "Pending");
							jsonObject.put("counter", count);
							count = 0;
							jsonArray.add(jsonObject);
						}

						// Stage-II Action Taken
						buffer = new StringBuffer("select count(*) from feedback_status where via_from='" + str + "' and escalation_date!='NA' and status NOT IN('Pending','Ticket Opened','No Further Action Required')  and stage='2' ");
						buffer.append(str1);
						count = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, buffer.toString());
						pojo.setStage2ActionTaken(String.valueOf(count));
						if (block != null && block.equalsIgnoreCase("Stage IInd"))
						{
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("mode", "Action Taken");
							jsonObject.put("counter", count);
							count = 0;
							jsonArray.add(jsonObject);
						}

						if (block != null && block.equalsIgnoreCase("Stage 1st"))
						{
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("mode", str);
							jsonObject.put("Total", pojo.getStage1Total());
							jsonObject.put("Pending", pojo.getStage1Pending());
							jsonObject.put("ActionTaken", pojo.getStage1ActionTaken());
							jsonObject.put("NFA", pojo.getStage1NoFurtherAction());
							jsonArray.add(jsonObject);
						}
						if (block != null && block.equalsIgnoreCase("Stage 2nd"))
						{
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("mode", str);
							jsonObject.put("TO", pojo.getStage2TicketOpened());
							jsonObject.put("Pending", pojo.getStage2Pending());
							jsonObject.put("ActionTaken", pojo.getStage2ActionTaken());
							jsonArray.add(jsonObject);
						}
						negativeFeedList.add(pojo);
						count1 = 0;
						count = 0;
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String showFeedbackCounters()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				Map<String, Object> session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				if (block != null && block.equalsIgnoreCase("block2"))
				{
					summaryList = new ArrayList<FeedbackOverallSummaryBean>();
					int i = 1;
					int counter = 0;
					List<String> modesList = new ArrayList<String>();
					// if(tableExists)
					{
						// modesList=new
						// EscalationActionControlDao().getGraphData
						// (connectionSpace,"feedbackdata","mode","select");
						modesList.add("Paper");
						modesList.add("SMS");
						modesList.add("Mob Tab");
						modesList.add("Kiosk");
						modesList.add("IVRS");
						modesList.add("Online");
						// modesList.add("Voice");
						// modesList.add("Call");

					}
					// System.out.println(
					// "Modes List is as <>>>>>>>>>>>>>>>>>>>>>>>>>>>>>."
					// +modesList.size());
					StringBuffer bufer = null;
					for (String str : modesList)
					{
						FeedbackOverallSummaryBean summary = new FeedbackOverallSummaryBean();
						summary.setId(i++);
						// For Yesterdays Yes and No
						bufer = new StringBuffer("select count(*) from feedbackdata where openDate='" + DateUtil.getNextDateAfter(-1) + "' and mode='" + str + "' && targetOn='Yes'");
						counter = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setYesterdayYes(String.valueOf(counter));
						bufer = new StringBuffer("select count(*) from feedbackdata where openDate='" + DateUtil.getNextDateAfter(-1) + "' and mode='" + str + "' && targetOn='No'");
						counter = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setYesterdayNo(String.valueOf(counter));

						// For Todays Yes and No
						bufer = new StringBuffer("select count(*) from feedbackdata where openDate='" + DateUtil.getCurrentDateUSFormat() + "' and mode='" + str + "' && targetOn='Yes'");
						counter = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setTodayYes(String.valueOf(counter));
						bufer = new StringBuffer("select count(*) from feedbackdata where openDate='" + DateUtil.getCurrentDateUSFormat() + "' and mode='" + str + "' && targetOn='No'");
						counter = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setTodayNo(String.valueOf(counter));

						// For Total Yes and No
						bufer = new StringBuffer("select count(*) from feedbackdata where mode='" + str + "' && targetOn='Yes'");
						counter = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setTotalYes(String.valueOf(counter));
						bufer = new StringBuffer("select count(*) from feedbackdata where mode='" + str + "' && targetOn='No'");
						counter = new EscalationActionControlDao().getAllSingleCounter(connectionSpace, bufer.toString());
						summary.setTotalNo(String.valueOf(counter));
						if (str != null && str.equalsIgnoreCase("Mob Tab"))
						{
							str = "Mobile App";
						}
						summary.setMode(str);
						summaryList.add(summary);
					}
				} else if (block != null && block.equalsIgnoreCase("block5"))
				{
					FeedbackUniversalAction FUA = new FeedbackUniversalAction();
					FeedbackDashboardDao FDD = new FeedbackDashboardDao();
					String empIdLoggedUSer = (String) session.get("empIdofuser");
					List<String> deptId = FUA.getLoggedInEmpForDept(empIdLoggedUSer.split("-")[1], "", "FM", connectionSpace);
					feedDataDashboardList = new ArrayList<FeedbackDataPojo>();
					if (deptId != null && deptId.size() > 0)
					{
						feedDataDashboardList = FDD.getFeedbackCategoryCounters(connectionSpace, deptId);
						if (feedDataDashboardList != null && feedDataDashboardList.size() > 0)
						{
							int temp = 0;
							for (FeedbackDataPojo fp : feedDataDashboardList)
							{
								temp = temp + fp.getActionCounter();
							}
							totalPending = String.valueOf(temp);
						}
					}
				} else if (block != null && block.equalsIgnoreCase("block6"))
				{
					FeedbackUniversalAction FUA = new FeedbackUniversalAction();
					String empIdLoggedUSer = (String) session.get("empIdofuser");
					List<String> deptId = FUA.getLoggedInEmpForDept(empIdLoggedUSer.split("-")[1], "", "FM", connectionSpace);
					dashPojoList = new ArrayList<FeedbackPojoDashboard>();
					if (deptId != null && deptId.size() > 0)
					{
						dashPojoList = new FeedbackDashboardDao().getDashboardLevelStatus(connectionSpace, deptId);
						if (dashPojoList != null && dashPojoList.size() > 0)
						{
							int temp5 = 0, temp1 = 0, temp2 = 0, temp3 = 0, temp4 = 0;
							for (FeedbackPojoDashboard fpd : dashPojoList)
							{
								temp1 = temp1 + Integer.parseInt(fpd.getPendingL1());
								temp2 = temp2 + Integer.parseInt(fpd.getPendingL2());
								temp3 = temp3 + Integer.parseInt(fpd.getPendingL3());
								temp4 = temp4 + Integer.parseInt(fpd.getPendingL4());
								temp5 = temp5 + Integer.parseInt(fpd.getPendingL5());
							}
							totalLevel1 = String.valueOf(temp1);
							totalLevel2 = String.valueOf(temp2);
							totalLevel3 = String.valueOf(temp3);
							totalLevel4 = String.valueOf(temp4);
							totalLevel5 = String.valueOf(temp5);
						}
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String getDashboardTicketCountersShow()
	{

		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				dashPojoList = new ArrayList<FeedbackPojoDashboard>();
				Map<String, Object> session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				// change by kamlesh 29-10-2014
				// System.out.println(getFromDate()+" TO "+getToDate());
				if (fromDate == null || fromDate.equalsIgnoreCase(" ") && toDate == null || toDate.equalsIgnoreCase(" "))
				{

					setFromDate(DateUtil.convertDateToIndianFormat(DateUtil.getNextDateAfter(-6)));
					setToDate(DateUtil.getCurrentDateIndianFormat());

				}// change end
				setGraphHeader("Department wise Ticket Status From " + fromDate + " to " + toDate);
				dashPojoList = new FeedbackDashboardDao().getDashboardTicketsDataShow(connectionSpace, "", true, true, getFromDate(), getToDate());
				if (dashPojoList != null && dashPojoList.size() > 0)
				{
					int temp = 0, temp1 = 0, temp2 = 0, temp3 = 0, temp4 = 0, temp5 = 0, temp6 = 0, temp7 = 0, temp8 = 0, temp9 = 0, temp10 = 0, temp11 = 0, temp12 = 0, temp13 = 0, temp14 = 0;
					for (FeedbackPojoDashboard fpd : dashPojoList)
					{
						temp = temp + Integer.parseInt(fpd.getPending());
						temp1 = temp1 + Integer.parseInt(fpd.getPendingL1());
						temp2 = temp2 + Integer.parseInt(fpd.getPendingL2());
						temp3 = temp3 + Integer.parseInt(fpd.getPendingL3());
						temp4 = temp4 + Integer.parseInt(fpd.getPendingL4());
						temp5 = temp5 + Integer.parseInt(fpd.getPendingL5());
						temp6 = temp6 + Integer.parseInt(fpd.getHp());
						temp7 = temp7 + Integer.parseInt(fpd.getSz());
						temp8 = temp8 + Integer.parseInt(fpd.getIg());
						temp9 = temp9 + Integer.parseInt(fpd.getRAs());
						temp10 = temp10 + Integer.parseInt(fpd.getResolved());
						temp11 = temp11 + Integer.parseInt(fpd.getTotalCounter());
						temp12 = temp12 + Integer.parseInt(fpd.getNoted());
						temp13+=Integer.parseInt(fpd.getTicketOpened());
						temp14+=Integer.parseInt(fpd.getNfa());
					}
					totalPending = String.valueOf(temp);
					totalLevel1 = String.valueOf(temp1);
					totalLevel2 = String.valueOf(temp2);
					totalLevel3 = String.valueOf(temp3);
					totalLevel4 = String.valueOf(temp4);
					totalLevel5 = String.valueOf(temp5);
					totalSnooze = String.valueOf(temp7);
					totalHighPriority = String.valueOf(temp6);
					totalIgnore = String.valueOf(temp8);
					totalReAssign = String.valueOf(temp9);
					totalResolve = String.valueOf(temp10);
					totalCounter = String.valueOf(temp11);
					totalNoted = String.valueOf(temp12);
					totalTO=String.valueOf(temp13);
					totalNFA=String.valueOf(temp14);

					if (dashPojoList != null && dashPojoList.size() > 0)
					{
						int tempp=0,tempp1=0;
						DecimalFormat df = new DecimalFormat();
						df.setMaximumFractionDigits(2);
						for (FeedbackPojoDashboard fpd : dashPojoList)
						{
							tempp = tempp + Integer.parseInt(fpd.getTotalAction());
							tempp1 = tempp1 + Integer.parseInt(fpd.getTotalCapa());
							fpd.setPercent(String.valueOf(df.format((Float.parseFloat(fpd.getTotalCounter()) / Float.parseFloat(totalCounter) * 100))));
						}
						setSubTotalAction(String.valueOf(tempp));
						setSubTotalCapa(String.valueOf(tempp1));
					}
					// getblock2counter();
				}

				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return ERROR;
		}
	}

	// to be merged 31-10-2014
	@SuppressWarnings("rawtypes")
	public String getDashboardShow()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				// For Bar Graph
				// change by kamlesh 29-10-2014
				if (fromDate == null && toDate == null)
				{

					setFromDate(DateUtil.convertDateToIndianFormat(DateUtil.getNextDateAfter(-6)));
					setToDate(DateUtil.getCurrentDateIndianFormat());

				}// change end

				// System.out.println(getFromDate()+" TO "+getToDate());
				// setGraphHeader("Department wise Ticket Status From " +
				// fromDate + " to " + toDate);
				setYAxisHeader("Ticket Counters");
				setXAxisHeader("Ticket Counters");
				List<FeedbackPojoDashboard> dashPojoList = new ArrayList<FeedbackPojoDashboard>();
				Map<String, Object> session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				FeedbackDashboardDao fd = new FeedbackDashboardDao();
				// change by kamlesh 29-10-2014
				dashPojoList = fd.getDashboardTicketsDataShow(connectionSpace, "", false, false, getFromDate(), getToDate());
				JSONObject jsonObject = new JSONObject();
				jsonArray = new JSONArray();

				for (FeedbackPojoDashboard pojo : dashPojoList)
				{
					jsonObject.put("dept", pojo.getDeptName());
					jsonObject.put("Pending", pojo.getPending());
					jsonObject.put("High Priority", pojo.getHp());
					jsonObject.put("Snooze", pojo.getSz());
					jsonObject.put("Ignore", pojo.getIg());
					jsonObject.put("Re-Assign", pojo.getRAs());
					jsonObject.put("Resolved", pojo.getResolved());

					jsonObject.put("Level 1", pojo.getPendingL1());
					jsonObject.put("Level 2", pojo.getPendingL2());
					jsonObject.put("Level 3", pojo.getPendingL3());
					jsonObject.put("Level 4", pojo.getPendingL4());
					jsonObject.put("Level 5", pojo.getPendingL5());

					jsonArray.add(jsonObject);
				}

				// Code Starts For 1 Quadrant
				/*
				 * dashPojoList=new ArrayList<FeedbackPojoDashboard>();
				 * Map<String,Object>
				 * session=ActionContext.getContext().getSession();
				 * SessionFactory
				 * connectionSpace=(SessionFactory)session.get("connectionSpace"
				 * ); dashPojoList=new
				 * FeedbackDashboardDao().getDashboardTicketsDataShow
				 * (connectionSpace,"",true);
				 */
				getblock1counter();

				feedbackPieCounters = new TreeMap<String, String>();
				List dataList = cbt.executeAllSelectQuery(" select mode,count(*) from feedbackdata group by mode ", connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object != null)
						{
							if (object[0] != null && object[1] != null)
							{
								feedbackPieCounters.put(object[0].toString(), object[1].toString());
							}
						}
					}
				}

				// Third Quadrant Starts From Here

				/*
				 * feedDataDashboardList = new ArrayList<FeedbackDataPojo>(); if
				 * (true) { feedDataDashboardList = fd
				 * .getFeedbackCounters(connectionSpace); if
				 * (feedDataDashboardList != null &&
				 * feedDataDashboardList.size() > 0) { int temp = 0, temp1 = 0;
				 * for (FeedbackDataPojo fp : feedDataDashboardList) { temp =
				 * temp + fp.getActionCounter(); temp1 = temp1 +
				 * Integer.parseInt(fp.getActionToday()); }
				 * 
				 * totalPending = String.valueOf(temp); totalCounter =
				 * String.valueOf(temp1); } }
				 * 
				 * feedDataDashboardType = new ArrayList<FeedbackDataPojo>();
				 * feedDataDashboardType =
				 * fd.getfeedbackTypelist(connectionSpace, null);
				 * 
				 * if (feedDataDashboardType != null &&
				 * feedDataDashboardType.size() > 0) { int temp = 0, temp1 = 0;
				 * for (FeedbackDataPojo fp : feedDataDashboardType) { temp =
				 * temp + fp.getFeedTypeTotal(); temp1 = temp1 +
				 * fp.getFeedTypeToday(); }
				 * 
				 * totalData = String.valueOf(temp); today =
				 * String.valueOf(temp1); }
				 */

				// Middle Quadrant to be merged 27-10-2014
				// getblock2counter();
				/*
				 * deptActionPojo = new
				 * FeedbackDashboardDao().getDepartmentDetails(connectionSpace);
				 * setDeptActionPojo(deptActionPojo); if (deptActionPojo != null
				 * && deptActionPojo.size() > 0) { int temp = 0, temp1 = 0,
				 * temp2 = 0; for (DeptActionPojo fpd : deptActionPojo) { temp =
				 * temp + Integer.parseInt(fpd.getTotalAction()); temp1 = temp1
				 * + Integer.parseInt(fpd.getTotalIsues()); temp2 = temp2 +
				 * Integer.parseInt(fpd.getTotalCapa());
				 * 
				 * } setSubTotalAction(String.valueOf(temp));
				 * setSubTotalIssue(String.valueOf(temp1));
				 * setSubTotalCapa(String.valueOf(temp2));
				 * 
				 * float total=(temp+temp1)+temp2; int temp3,temp4,temp5;
				 * 
				 * for (DeptActionPojo fpd : deptActionPojo) { float temp6;
				 * float temp7; temp3=Integer.parseInt(fpd.getTotalAction());
				 * temp4=Integer.parseInt(fpd.getTotalIsues());
				 * temp5=Integer.parseInt(fpd.getTotalCapa());
				 * temp6=(temp3+temp4)+temp5;
				 * temp7=Math.round(((float)(temp6/total)100)); fpd.setPercent(
				 * String.valueOf((int)temp7));
				 * //System.out.println(temp6+"/"+total+"="+fpd.getPercent()); }
				 * }
				 */

				// Fourth Quadrant
				// System.out.println("Date:::::::::"+fromDate+"  to  "+toDate);
				analysisPojo=new ArrayList<FeedbackAnalysis>();
				//analysisPojo = new FeedbackDashboardDao().getAnalysisData(connectionSpace, null, fromDate, toDate);
				analysisPojo.addAll(new FeedbackDashboardDao().getAnalysisData(connectionSpace, "Appreciation", fromDate, toDate));
				analysisPojo.addAll(new FeedbackDashboardDao().getAnalysisData(connectionSpace, "Suggestion", fromDate, toDate));
				setAnalysisPojo(analysisPojo);

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	// merged on 3-11-2014 by kamlesh
	@SuppressWarnings("unchecked")
	public String getblock1counter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				// for pie chart
				JSONObject jsonObject = new JSONObject();
				jsonArray = new JSONArray();
				// Code for Second Quadrant Starts From Here
				summaryList = new ArrayList<FeedbackOverallSummaryBean>();
				int i = 1;
				int counter = 0;
				int count = 0;
				List<String> modesList = new ArrayList<String>();
				{
					modesList.add("Paper");
					modesList.add("SMS");
					modesList.add("IPD");
					modesList.add("OPD");
					modesList.add("Admin Round");
					modesList.add("Ward Rounds");
				}

				StringBuffer bufer = null;
				List list = null;
				List dataList = new ArrayList();
				int pending = 0, tktOpened = 0, nfa = 0;
				for (String str : modesList)
				{
					FeedbackOverallSummaryBean summary = new FeedbackOverallSummaryBean();
					EscalationActionControlDao EAC = new EscalationActionControlDao();
					summary.setId(i++);
					// For Pending,Ticket Opened,No Further Action Required
					if (str.equalsIgnoreCase("Paper"))
					{
						bufer = new StringBuffer("select count(*),status from feedback_status_feed_paperRating where open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' " + "and via_from='" + str + "' and status IN('Pending','Ticket Opened','No Further Action Required') and moduleName='FM' ");
						bufer.append(" and (feed_brief  like '%Poor' or feed_brief  like '%Average') group by status");
						dataList.add(cbt.executeAllSelectQuery(bufer.toString(), connectionSpace));
						bufer.delete(0, bufer.length());
					}
					bufer = new StringBuffer("select count(*),status from feedback_status where escalation_date!='NA' and open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' " + "and via_from='" + str + "' and status IN('Pending','Ticket Opened','No Further Action Required') and moduleName='FM' group by status");
					dataList.add(cbt.executeAllSelectQuery(bufer.toString(), connectionSpace));
					if (dataList != null && dataList.size() > 0)
					{
						for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
						{
							list = (List) iterator1.next();
							for (Iterator iterator = list.iterator(); iterator.hasNext();)
							{

								Object[] object = (Object[]) iterator.next();
								if (object[0] != null && object[1] != null)
								{
									if (object[1].equals("Pending"))
									{
										pending += Integer.parseInt(object[0].toString());

									} else if (object[1].equals("Ticket Opened"))
									{
										tktOpened += Integer.parseInt(object[0].toString());
									} else if (object[1].equals("No Further Action Required"))
									{
										nfa += Integer.parseInt(object[0].toString());
									}
								}
							}
						}
					} else
					{
						jsonObject.put("mode", str);
						jsonObject.put("counter", "0");
					}
					summary.setPending("" + pending);
					summary.setTicketOpened("" + tktOpened);
					summary.setNoFurtherAction("" + nfa);
					pending = 0;
					nfa = 0;
					tktOpened = 0;
					dataList.clear();
					bufer.setLength(0);
					// For Positive
					bufer.append("select count(*) from feedback_status where open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' and via_from='" + str + "' and escalation_date='NA' and moduleName='FM'");
					counter = EAC.getAllSingleCounter(connectionSpace, bufer.toString());
					summary.setPositive(String.valueOf(counter));
					count = 0;
					if (status != null && status.equalsIgnoreCase("Positive"))
					{
						jsonObject.put("mode", str);
						jsonObject.put("counter", String.valueOf(counter));
					}

					// total+=Integer.parseInt(summary.getPositive());
					bufer.setLength(0);
					// For Negative
					if (str.equalsIgnoreCase("Paper"))
					{
						bufer = new StringBuffer("select count(*) from feedback_status_feed_paperRating where open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' and via_from='" + str + "' and escalation_date!='NA' and moduleName='FM' ");
						bufer.append(" and (feed_brief  like '%Poor' or feed_brief  like '%Average')");
						count = EAC.getAllSingleCounter(connectionSpace, bufer.toString());
						bufer.delete(0, bufer.length());
					}
					bufer.append("select count(*) from feedback_status where open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "' and via_from='" + str + "' and escalation_date!='NA' and moduleName='FM'");
					counter = EAC.getAllSingleCounter(connectionSpace, bufer.toString());
					summary.setNegative(String.valueOf(counter + count));
					count = 0;
					if (status != null && status.equalsIgnoreCase("Negative"))
					{
						jsonObject.put("mode", str);
						jsonObject.put("counter", String.valueOf(counter));
					}
					// total+=Integer.parseInt(summary.getNegative());
					bufer.setLength(0);

					if (str.equalsIgnoreCase("Mob Tab"))
					{
						str = "Mobile App";
					}
					summary.setMode(str);
					// summary.setTotal(String.valueOf(total));
					total = 0;
					count = 0;
					summaryList.add(summary);
					jsonArray.add(jsonObject);
				}

			}

			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
			return SUCCESS;
		} else
		{
			return ERROR;
		}

	}

	// to be merged 27-10-2014
	public String getblock2counter()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				// Middle Quadrant
				if (block != null && block.equalsIgnoreCase("3rdBlock"))
				{
					analysisPojo=new ArrayList<FeedbackAnalysis>();
					//analysisPojo = new FeedbackDashboardDao().getAnalysisData(connectionSpace, null, fromDate, toDate);
					analysisPojo.addAll(new FeedbackDashboardDao().getAnalysisData(connectionSpace, "Appreciation", fromDate, toDate));
					analysisPojo.addAll(new FeedbackDashboardDao().getAnalysisData(connectionSpace, "Suggestion", fromDate, toDate));
					setAnalysisPojo(analysisPojo);

				}
				deptActionPojo = new FeedbackDashboardDao().getDepartmentDetails(connectionSpace, fromDate, toDate);
				setDeptActionPojo(deptActionPojo);
				if (deptActionPojo != null && deptActionPojo.size() > 0)
				{
					int temp = 0, temp1 = 0, temp2 = 0;
					for (DeptActionPojo fpd : deptActionPojo)
					{
						temp = temp + Integer.parseInt(fpd.getTotalAction());
						temp1 = temp1 + Integer.parseInt(fpd.getTotalIsues());
						temp2 = temp2 + Integer.parseInt(fpd.getTotalCapa());

					}
					setSubTotalAction(String.valueOf(temp));
					setSubTotalIssue(String.valueOf(temp1));
					setSubTotalCapa(String.valueOf(temp2));
					float total = (temp + temp1) + temp2;
					int temp3, temp4, temp5;

					for (DeptActionPojo fpd : deptActionPojo)
					{
						float temp6;
						float temp7;
						temp3 = Integer.parseInt(fpd.getTotalAction());
						temp4 = Integer.parseInt(fpd.getTotalIsues());
						temp5 = Integer.parseInt(fpd.getTotalCapa());
						temp6 = (temp3 + temp4) + temp5;
						temp7 = Math.round(((float) (temp6 / total) * 100));
						fpd.setPercent(String.valueOf((int) temp7));
						// System.out.println(temp6+"/"+total+"="+fpd.getPercent(
						// ));
					}

				}
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
			return SUCCESS;
		} else
		{
			return ERROR;
		}
	}

	// to be merged 2nd block pie 27-10-2014

	public String getJsonDataPieBlock2()
	{
		// System.out.println("FeedbackDashboard.getJsonData()");
		if (ValidateSession.checkSession())
		{
			try
			{
				deptActionPojo = new FeedbackDashboardDao().getDepartmentDetails(connectionSpace, fromDate, toDate);
				if (deptActionPojo != null && deptActionPojo.size() > 0)
				{
					JSONObject jsonObject = new JSONObject();
					jsonArray = new JSONArray();
					float temp1 = 0f;
					for (DeptActionPojo fpd : deptActionPojo)
					{

						temp1 = temp1 + Integer.parseInt(fpd.getTotalIsues());

					}

					float temp6 = 0f;
					float temp7 = 0f;

					for (DeptActionPojo fpd : deptActionPojo)
					{

						temp6 = Integer.parseInt(fpd.getTotalIsues());

						temp7 = ((float) (temp6 / temp1) * 100);
						fpd.setPercent(String.valueOf(temp7));
						// System.out.println(temp6+"/"+total+"="+fpd.getPercent(
						// ));
					}
					for (DeptActionPojo pojo : deptActionPojo)
					{
						jsonObject.put("department", pojo.getDeptName());
						jsonObject.put("percent", pojo.getPercent());
						jsonObject.put("issue", (int) temp1);
						jsonArray.add(jsonObject);
					}
				}

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String getFeedbackAction()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				FeedbackDashboardDao fd = new FeedbackDashboardDao();
				Map<String, Object> session = ActionContext.getContext().getSession();
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				if (block != null && block.equalsIgnoreCase("2ndBlock"))
				{
					feedDataDashboardList = new ArrayList<FeedbackDataPojo>();
					feedDataDashboardList = fd.getFeedbackCounters(connectionSpace);
					if (feedDataDashboardList != null && feedDataDashboardList.size() > 0)
					{
						int temp = 0, temp1 = 0;
						for (FeedbackDataPojo fp : feedDataDashboardList)
						{
							temp = temp + fp.getActionCounter();
							temp1 = temp1 + Integer.parseInt(fp.getActionToday());
						}
						totalPending = String.valueOf(temp);
						totalCounter = String.valueOf(temp1);
					}
				} else if (block != null && block.equalsIgnoreCase("7rdBlock"))
				{
					feedDataDashboardType = new ArrayList<FeedbackDataPojo>();
					feedDataDashboardType = fd.getfeedbackTypelist(connectionSpace, null);
					if (feedDataDashboardType != null && feedDataDashboardType.size() > 0)
					{
						int temp = 0, temp1 = 0;
						for (FeedbackDataPojo fp : feedDataDashboardType)
						{
							temp = temp + fp.getFeedTypeTotal();
							temp1 = temp1 + fp.getFeedTypeToday();
						}

						totalData = String.valueOf(temp);
						today = String.valueOf(temp1);
					}
				} else if (block != null && block.equalsIgnoreCase("4rdBlock"))
				{
					FeedbackUniversalAction FUA = new FeedbackUniversalAction();
					String empIdLoggedUSer = (String) session.get("empIdofuser");
					List<String> deptId = FUA.getLoggedInEmpForDept(empIdLoggedUSer.split("-")[1], "", "FM", connectionSpace);
					feedDataDashboardType = new ArrayList<FeedbackDataPojo>();
					feedDataDashboardType = fd.getfeedbackTypelist(connectionSpace, deptId);
					if (feedDataDashboardType != null && feedDataDashboardType.size() > 0)
					{
						int temp = 0, temp1 = 0;
						for (FeedbackDataPojo fp : feedDataDashboardType)
						{
							temp = temp + fp.getFeedTypeTotal();
							temp1 = temp1 + fp.getFeedTypeToday();
						}

						totalData = String.valueOf(temp);
						today = String.valueOf(temp1);
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String getDataOnClick()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				getGridColumns(status);
				// System.out.println("Columns are set !!!!");
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	@SuppressWarnings("unchecked")
	private void getGridColumns(String status)
	{
		try
		{
			masterViewProp = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			masterViewProp.add(gpv);

			List<GridDataPropertyView> returnResult = Configuration.getConfigurationData("mapped_feedback_lodge_configuration", accountID, connectionSpace, "", 0, "table_name", "feedback_lodge_configuration");
			for (GridDataPropertyView gdp : returnResult)
			{
				if (status != null && status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Level1") || status.equalsIgnoreCase("Level2") || status.equalsIgnoreCase("Level3") || status.equalsIgnoreCase("Level4") || status.equalsIgnoreCase("Level5") || status.equalsIgnoreCase("Re-Assign") || status.equalsIgnoreCase("Total") || status.equalsIgnoreCase("Today") || status.equalsIgnoreCase("Category") || status.equalsIgnoreCase("TotalDept")
						|| status.equalsIgnoreCase("TodayDept"))
				{
					if (!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("sn_on_date") && !gdp.getColomnName().equals("resolve_time") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date") && !gdp.getColomnName().equals("ig_time") && !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_date")
							&& !gdp.getColomnName().equals("hp_time") && !gdp.getColomnName().equals("sn_upto_time") && !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("hp_reason") && !gdp.getColomnName().equals("sn_duration") && !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("ig_date")
							&& !gdp.getColomnName().equals("ig_reason") && !gdp.getColomnName().equals("transfer_date") && !gdp.getColomnName().equals("transfer_reason") && !gdp.getColomnName().equals("moduleName") && !gdp.getColomnName().equals("feedback_remarks") && !gdp.getColomnName().equals("transfer_time") && !gdp.getColomnName().equals("resolutionTime") && !gdp.getColomnName().equals("non_working_time") && !gdp.getColomnName().equals("resolveDoc")
							&& !gdp.getColomnName().equals("resolveDoc1"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				} else if (status != null && status.equalsIgnoreCase("Snooze"))
				{
					if (!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("sn_on_date") && !gdp.getColomnName().equals("resolve_time") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("ig_time") && !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_date") && !gdp.getColomnName().equals("hp_time")
							&& !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("hp_reason") && !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("ig_date") && !gdp.getColomnName().equals("ig_reason") && !gdp.getColomnName().equals("transfer_date") && !gdp.getColomnName().equals("transfer_reason")
							&& !gdp.getColomnName().equals("moduleName") && !gdp.getColomnName().equals("feedback_remarks") && !gdp.getColomnName().equals("transfer_time") && !gdp.getColomnName().equals("resolutionTime") && !gdp.getColomnName().equals("non_working_time") && !gdp.getColomnName().equals("resolveDoc") && !gdp.getColomnName().equals("resolveDoc1"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				} else if (status != null && status.equalsIgnoreCase("High Priority"))
				{
					if (!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("sn_on_date") && !gdp.getColomnName().equals("resolve_time") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date") && !gdp.getColomnName().equals("ig_time") && !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_date")
							&& !gdp.getColomnName().equals("sn_upto_time") && !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("sn_duration") && !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("ig_date") && !gdp.getColomnName().equals("ig_reason") && !gdp.getColomnName().equals("transfer_date")
							&& !gdp.getColomnName().equals("transfer_reason") && !gdp.getColomnName().equals("moduleName") && !gdp.getColomnName().equals("feedback_remarks") && !gdp.getColomnName().equals("transfer_time") && !gdp.getColomnName().equals("resolutionTime") && !gdp.getColomnName().equals("non_working_time") && !gdp.getColomnName().equals("resolveDoc") && !gdp.getColomnName().equals("resolveDoc1"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				} else if (status != null && status.equalsIgnoreCase("Ignore"))
				{
					if (!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("sn_on_date") && !gdp.getColomnName().equals("resolve_time") && !gdp.getColomnName().equals("action_by") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date") && !gdp.getColomnName().equals("resolve_duration") && !gdp.getColomnName().equals("resolve_date") && !gdp.getColomnName().equals("hp_time")
							&& !gdp.getColomnName().equals("sn_upto_time") && !gdp.getColomnName().equals("resolve_remark") && !gdp.getColomnName().equals("spare_used") && !gdp.getColomnName().equals("hp_reason") && !gdp.getColomnName().equals("sn_duration") && !gdp.getColomnName().equals("resolve_by") && !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("transfer_date")
							&& !gdp.getColomnName().equals("transfer_reason") && !gdp.getColomnName().equals("moduleName") && !gdp.getColomnName().equals("feedback_remarks") && !gdp.getColomnName().equals("transfer_time") && !gdp.getColomnName().equals("resolutionTime") && !gdp.getColomnName().equals("non_working_time") && !gdp.getColomnName().equals("resolveDoc") && !gdp.getColomnName().equals("resolveDoc1"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				} else if (status != null && status.equalsIgnoreCase("Resolved"))
				{
					if (!gdp.getColomnName().equals("uniqueid") && !gdp.getColomnName().equals("deptHierarchy") && !gdp.getColomnName().equals("sn_on_date") && !gdp.getColomnName().equals("sn_on_time") && !gdp.getColomnName().equals("sn_upto_date") && !gdp.getColomnName().equals("ig_time") && !gdp.getColomnName().equals("hp_time") && !gdp.getColomnName().equals("sn_upto_time") && !gdp.getColomnName().equals("hp_reason") && !gdp.getColomnName().equals("sn_duration")
							&& !gdp.getColomnName().equals("hp_date") && !gdp.getColomnName().equals("sn_reason") && !gdp.getColomnName().equals("ig_date") && !gdp.getColomnName().equals("ig_reason") && !gdp.getColomnName().equals("transfer_date") && !gdp.getColomnName().equals("transfer_reason") && !gdp.getColomnName().equals("moduleName") && !gdp.getColomnName().equals("feedback_remarks") && !gdp.getColomnName().equals("transfer_time")
							&& !gdp.getColomnName().equals("resolutionTime") && !gdp.getColomnName().equals("non_working_time"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						if (gdp.getColomnName().equalsIgnoreCase("resolveDoc"))
						{
							gpv.setFormatter("resolveDoc");
						} else if (gdp.getColomnName().equalsIgnoreCase("resolveDoc1"))
						{
							gpv.setFormatter("resolveDoc1");
						} else
						{
							gpv.setFormatter(gdp.getFormatter());
						}
						masterViewProp.add(gpv);
					}
				}
				else if (status.equalsIgnoreCase("Ticket Opened"))
				{
					if(gdp.getColomnName().equals("ticket_no") || gdp.getColomnName().equals("clientId") ||  gdp.getColomnName().equals("patientId") || gdp.getColomnName().equals("feed_by") || gdp.getColomnName().equals("subCatg") || 
						 gdp.getColomnName().equals("subCatg") || gdp.getColomnName().equals("feed_brief") || gdp.getColomnName().equals("subCatg") ||
						 gdp.getColomnName().equals("status") || gdp.getColomnName().equals("via_from") || gdp.getColomnName().equals("location") ||
						 gdp.getColomnName().equals("allot_to"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				}
				else if (status.equalsIgnoreCase("No Further Action Required"))
				{
					if(gdp.getColomnName().equals("ticket_no") || gdp.getColomnName().equals("clientId") ||  gdp.getColomnName().equals("patientId") || gdp.getColomnName().equals("feed_by") || gdp.getColomnName().equals("subCatg") || 
						 gdp.getColomnName().equals("subCatg") || gdp.getColomnName().equals("feed_brief") || gdp.getColomnName().equals("subCatg") ||
						 gdp.getColomnName().equals("status") || gdp.getColomnName().equals("via_from") || gdp.getColomnName().equals("location") ||
						 gdp.getColomnName().equals("allot_to")	)
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				}
				else if (status.equalsIgnoreCase(""))
				{
					if(gdp.getColomnName().equals("ticket_no") || gdp.getColomnName().equals("clientId") ||  gdp.getColomnName().equals("patientId") || gdp.getColomnName().equals("feed_by") || gdp.getColomnName().equals("subCatg") || 
						 gdp.getColomnName().equals("subCatg") || gdp.getColomnName().equals("feed_brief") || gdp.getColomnName().equals("subCatg") ||
						 gdp.getColomnName().equals("status") || gdp.getColomnName().equals("via_from") || gdp.getColomnName().equals("location") ||
						 gdp.getColomnName().equals("allot_to"))
					{
						gpv = new GridDataPropertyView();
						gpv.setColomnName(gdp.getColomnName());
						gpv.setHeadingName(gdp.getHeadingName());
						gpv.setEditable(gdp.getEditable());
						gpv.setSearch(gdp.getSearch());
						gpv.setHideOrShow(gdp.getHideOrShow());
						gpv.setWidth(gdp.getWidth());
						masterViewProp.add(gpv);
					}
				}
			}
			session.put("masterViewProp", masterViewProp);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// change by kamlesh 29-10-2014 inside line no 1143
	@SuppressWarnings("unchecked")
	public String dashboardDataInGrid()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				boolean mFlag = false, hFlag = false, nFlag = false;
				String loggedEmpId = "";
				String userType = (String) session.get("userTpe");
				FeedbackUniversalAction FUA = new FeedbackUniversalAction();
				ActivityBoardHelper helper=new ActivityBoardHelper();
				List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName, DES_ENCRYPTION_KEY), deptLevel);
				if (empData != null && empData.size() > 0)
				{
					for (Iterator iterator = empData.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						loggedEmpId = object[5].toString();
					}
				}
				List<String> deptIds = new ArrayList<String>();

				if (userType != null && userType.equalsIgnoreCase("M"))
				{
					mFlag = true;
				} else if (userType != null && userType.equalsIgnoreCase("D"))
				{
					deptIds = FUA.getLoggedInEmpForDept(loggedEmpId.split("-")[1], "", "FM", connectionSpace);
					hFlag = true;
				} else
				{
					if (userName != null && loggedEmpId != null)
					{
						nFlag = true;
					}
				}

				List data = new ArrayList();
				List<Object> Listhb = new ArrayList<Object>();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from feedback_status");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

				if (dataCount != null && dataCount.size() > 0)
				{
					List<GridDataPropertyView> fieldNames = (List<GridDataPropertyView>) session.get("masterViewProp");
					session.remove("masterViewProp");
					StringBuilder query = new StringBuilder("");
					query.append("select ");

					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						if((getDeptId().equalsIgnoreCase(String.valueOf(helper.getAdminDeptId(connectionSpace))) || getDeptId().equalsIgnoreCase("") ) && (getStatus().equalsIgnoreCase("Pending") || getStatus().equalsIgnoreCase("") || getStatus().equalsIgnoreCase("Ticket Opened") || getStatus().equalsIgnoreCase("No Further Action Required")))
						{
							data.add(getDeptData(fieldNames,mFlag,nFlag,hFlag,loggedEmpId,deptIds,FUA,"feedback_status_feed_paperRating","1",connectionSpace));
						}
						data.add(getDeptData(fieldNames,mFlag,nFlag,hFlag,loggedEmpId,deptIds,FUA,"feedback_status","0",connectionSpace));
						if (data != null && data.size() > 0)
						{
							for (Iterator it1 = data.iterator(); it1.hasNext();)
							{
								List list=(List) it1.next();	
								for (Iterator it = list.iterator(); it.hasNext();)
								{
									int k = 0;
									Object[] obdata = (Object[]) it.next();
									Map<String, Object> one = new HashMap<String, Object>();
									for (GridDataPropertyView gpv : fieldNames)
									{
										if (obdata[k] != null)
										{
											if (obdata[k].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
											{
												one.put(gpv.getColomnName(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
											} else
											{
												one.put(gpv.getColomnName(), obdata[k].toString());
											}
										} else
										{
											one.put(gpv.getColomnName().toString(), "NA");
										}
										k++;
									}
									Listhb.add(one);
								}
							}
							setMasterViewList(Listhb);
							setRecords(masterViewList.size());
							int to = (getRows() * getPage());
							if (to > getRecords())
								to = getRecords();
							setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
						}
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}
	
	
	public List getDeptData( List<GridDataPropertyView> fieldNames, boolean mFlag, boolean nFlag, boolean hFlag, String loggedEmpId, List<String> deptIds, FeedbackUniversalAction FUA,String tableName, String flag, SessionFactory connectionSpace)
	{
		int i=0;
		StringBuilder query=new StringBuilder(" Select ");
		for (GridDataPropertyView gpv : fieldNames)
		{
			if (i < (fieldNames.size() - 1))
			{
				if (gpv.getColomnName().equalsIgnoreCase("feedType"))
				{
					query.append("ft.fbType,");
				} else if (gpv.getColomnName().equalsIgnoreCase("category"))
				{
					query.append("cat.categoryName,");
				} else if (gpv.getColomnName().equalsIgnoreCase("subCatg"))
				{
					query.append("scat.subCategoryName,");
				} else if (gpv.getColomnName().equalsIgnoreCase("by_dept_subdept"))
				{
					query.append("dept1.deptName AS byDept,");
				} else if (gpv.getColomnName().equalsIgnoreCase("to_dept_subdept"))
				{
					query.append("dept2.deptName AS toDept,");
				} else if (gpv.getColomnName().equalsIgnoreCase("allot_to"))
				{
					query.append("emp.empName AS allotTo,");
				} else if (gpv.getColomnName().equalsIgnoreCase("resolve_by"))
				{
					query.append("emp1.empName AS resolveBy,");
				} else
				{
					query.append("feed." + gpv.getColomnName().toString() + ",");
				}
			} else
			{
				query.append("feed." + gpv.getColomnName().toString());
			}
			i++;
		}
		query.append(" FROM "+tableName+" AS feed ");
		query.append(" LEFT JOIN feedback_subcategory As scat ON scat.id=feed.subCatg ");
		query.append(" LEFT JOIN feedback_category As cat ON cat.id=scat.categoryName ");
		query.append(" LEFT JOIN feedback_type As ft ON ft.id=cat.fbType ");
		query.append(" LEFT JOIN department As dept1 ON dept1.id=feed.by_dept_subdept ");
		query.append(" LEFT JOIN department As dept2 ON dept2.id=feed.to_dept_subdept ");
		query.append(" LEFT JOIN employee_basic AS emp ON feed.allot_to= emp.id");
		if (status != null && status.equalsIgnoreCase("Resolved") && !status.equalsIgnoreCase(""))
		{
			query.append(" LEFT JOIN employee_basic AS emp1 ON feed.resolve_by= emp1.id");
		}
		query.append(" WHERE feed.moduleName='FM' ");
		if (status != null && !status.equalsIgnoreCase("Level1") && !status.equalsIgnoreCase("Level2") && !status.equalsIgnoreCase("Level3") && !status.equalsIgnoreCase("Level4") && !status.equalsIgnoreCase("Level5") && !status.equalsIgnoreCase("Total") && !status.equalsIgnoreCase("Today") && !status.equalsIgnoreCase("Category") && !status.equalsIgnoreCase("TotalDept") && !status.equalsIgnoreCase("TodayDept") && !status.equalsIgnoreCase(""))
		{
			// query.append(" and feed.escalation_date!='NA' ");
			if (feedId == null)
			{
				query.append(" and feed.status='" + status + "'");
			}
			if (type != null && !type.equalsIgnoreCase(""))
			{
				query.append(" and feed.level='" + type + "'");
			}

			if (mFlag)
			{
			} else if (hFlag)
			{
				query.append(" and feed.to_dept_subdept IN " + deptIds.toString().replace("[", "(").replace("]", ")") + "");
			} else if (nFlag)
			{
				query.append(" and (feed.feed_registerby='" + userName + "' or feed.allot_to='" + loggedEmpId + "')");
			}

		}
		if (status != null && status.equalsIgnoreCase("Level1") || status.equalsIgnoreCase("Level2") || status.equalsIgnoreCase("Level3") || status.equalsIgnoreCase("Level4") || status.equalsIgnoreCase("Level5") && !status.equalsIgnoreCase(""))
		{
			query.append(" and feed.level='" + status + "' AND status IN('Pending','Snooze','High Priority')");
			if (mFlag)
			{
			} else if (hFlag)
			{
				query.append(" and feed.to_dept_subdept IN " + deptIds.toString().replace("[", "(").replace("]", ")") + "");
			} else if (nFlag)
			{
				query.append(" and (feed.feed_registerby='" + userName + "' or feed.allot_to='" + loggedEmpId + "')");
			}
			query.append(" and subcatg.needEsc='Y' ");
		}
		/*
		 * if (deptId!=null && deptId.equalsIgnoreCase("All")) {
		 * query
		 * .append(" AND feed.to_dept_subdept IN ("+deptId+")  "
		 * ); }
		 */
		if (status != null && status.equalsIgnoreCase("Total") || status.equalsIgnoreCase("Today"))
		{
			query.append(" and ft.fbType='" + type + "' ");
			if (status.equalsIgnoreCase("Today"))
			{
				query.append(" AND feed.open_date='" + DateUtil.getCurrentDateUSFormat() + "'");
			}
			if (mFlag)
			{
			} else if (hFlag)
			{
				query.append(" and feed.to_dept_subdept IN " + deptIds.toString().replace("[", "(").replace("]", ")") + "");
			} else if (nFlag)
			{
				query.append(" and (feed.feed_registerby='" + userName + "' or feed.allot_to='" + loggedEmpId + "')");
			}
		}
		if (status != null && status.equalsIgnoreCase("TotalDept") || status.equalsIgnoreCase("TodayDept") && !status.equalsIgnoreCase(""))
		{
			List<String> deptId = FUA.getLoggedInEmpForDept(loggedEmpId, "", "FM", connectionSpace);

			query.append(" and ft.fbType='" + type + "' ");
			// query.append(" and ft.fbType='"+type+
			// "' AND feed.moduleName='FM'  AND feed.to_dept_subdept IN "
			// +deptId.toString().replace("[", "(").replace("]",
			// ")")+" ");
			if (status.equalsIgnoreCase("TodayDept"))
			{
				query.append(" AND feed.open_date='" + DateUtil.getCurrentDateUSFormat() + "'");
			}
			if (mFlag)
			{
			} else if (hFlag)
			{
				query.append(" and feed.to_dept_subdept IN " + deptIds.toString().replace("[", "(").replace("]", ")") + "");
			} else if (nFlag)
			{
				query.append(" and (feed.feed_registerby='" + userName + "' or feed.allot_to='" + loggedEmpId + "')");
			}
		}
		if (status != null && status.equalsIgnoreCase("Category") && !status.equalsIgnoreCase(""))
		{
			query.append("and cat.id IN (select id from feedback_category where id='" + type + "')");
			List<String> deptId = FUA.getLoggedInEmpForDept(loggedEmpId, "", "FM", connectionSpace);

			if (hFlag)
			{
				query.append(" and fbtype.dept_subdept in " + deptId.toString().replace("[", "(").replace("]", ")") + "");
			} else if (nFlag)
			{
				query.append(" and (feed.feed_registerby='" + userName + "' or feed.allot_to='" + loggedEmpId + "')");
			}

		}
		/*if(status.equalsIgnoreCase("") && !tableName.equalsIgnoreCase("feedback_status"))
		{
			query.append(" and feed.status='Pending' ");
		}*/
		if (getDeptId() != null && !getDeptId().equalsIgnoreCase(""))
		{
			if(tableName.equalsIgnoreCase("feedback_status") )
			{
				query.append(" and feed.to_dept_subdept='"+getDeptId()+"'");
			}
			else
			{
				query.append(" and feed.by_dept_subdept='"+getDeptId()+"' and (feed.feed_brief  like '%Poor' or feed.feed_brief  like '%Average') ");
			}
		}
		else if(getDeptId().equalsIgnoreCase("") && !tableName.equalsIgnoreCase("feedback_status"))
		{
			query.append("  and (feed.feed_brief  like '%Poor' or feed.feed_brief  like '%Average') ");
		}

		if (feedId != null)
		{
			query.append(" and feed.id='" + feedId + "'");
		}
		if (fromDate != null || fromDate.equalsIgnoreCase("") && toDate != null || toDate.equalsIgnoreCase(""))
		{
			query.append(" and feed.open_date between '" + DateUtil.convertDateToUSFormat(fromDate) + "' and '" + DateUtil.convertDateToUSFormat(toDate) + "'");
		}
		// System.out.println("QUERY IS AS :::   "+query);
		List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		
		return data;
	}

	@SuppressWarnings("rawtypes")
	public String getDashboardShowNormal()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				FeedbackUniversalAction FUA = new FeedbackUniversalAction();
				FeedbackDashboardDao FDD = new FeedbackDashboardDao();
				String empIdLoggedUSer = (String) session.get("empIdofuser");

				List<String> deptId = FUA.getLoggedInEmpForDept(empIdLoggedUSer.split("-")[1], "", "FM", connectionSpace);

				// First Quadrant ....
				feedTicketList = new ArrayList<FeedbackTicketPojo>();
				feedTicketList = FDD.getFeedTicketDashboardNormal(connectionSpace, deptId);
				/*
				 * if (deptId!=null && deptId.size()>0) {
				 * feedTicketList=FDD.getFeedTicketDashboardNormal
				 * (connectionSpace,deptId); }
				 */

				// Second Quadrant....
				feedDataDashboardType = new ArrayList<FeedbackDataPojo>();
				if (deptId != null && deptId.size() > 0)
				{
					feedDataDashboardType = FDD.getfeedbackTypelist(connectionSpace, deptId);
					// System.out.println("<< feedDataDashboardType >>>> "+
					// feedDataDashboardType.size());
					if (feedDataDashboardType != null && feedDataDashboardType.size() > 0)
					{
						int temp = 0, temp1 = 0;
						for (FeedbackDataPojo fp : feedDataDashboardType)
						{
							temp = temp + fp.getFeedTypeTotal();
							temp1 = temp1 + fp.getFeedTypeToday();
						}

						totalData = String.valueOf(temp);
						today = String.valueOf(temp1);
					}
				}

				// System.out.println("feedDataDashboardType size is as ...."+
				// feedDataDashboardType.size());

				// Third Quadrant....
				feedDataDashboardList = new ArrayList<FeedbackDataPojo>();
				if (deptId != null && deptId.size() > 0)
				{
					feedDataDashboardList = FDD.getFeedbackCategoryCounters(connectionSpace, deptId);
					if (feedDataDashboardList != null && feedDataDashboardList.size() > 0)
					{
						int temp = 0;
						for (FeedbackDataPojo fp : feedDataDashboardList)
						{
							temp = temp + fp.getActionCounter();
						}
						totalPending = String.valueOf(temp);
					}
				}

				/*
				 * // for second QuardrantPie List
				 * dataList=cbt.executeAllSelectQuery(
				 * " SELECT COUNT(*),ft.fbType FROM feedback_status AS fs   INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' AND fs.to_dept_subdept IN "
				 * +deptId.toString().replace("[",
				 * "(").replace("]",")")+" GROUP BY fbType", connectionSpace);
				 * if (dataList!=null && dataList.size()>0) {
				 * pieFeedbackCounter=new LinkedHashMap<Integer, String>(); for
				 * (Iterator iterator = dataList.iterator();
				 * iterator.hasNext();) { Object[] object = (Object[])
				 * iterator.next(); if (object[0]!=null && object[1]!=null) {
				 * pieFeedbackCounter
				 * .put(Integer.parseInt(object[0].toString()),
				 * object[1].toString()); } } }
				 */

				// Fourth Quadrant....
				dashPojoList = new ArrayList<FeedbackPojoDashboard>();
				if (deptId != null && deptId.size() > 0)
				{
					dashPojoList = new FeedbackDashboardDao().getDashboardLevelStatus(connectionSpace, deptId);
					if (dashPojoList != null && dashPojoList.size() > 0)
					{
						int temp5 = 0, temp1 = 0, temp2 = 0, temp3 = 0, temp4 = 0;
						for (FeedbackPojoDashboard fpd : dashPojoList)
						{
							temp1 = temp1 + Integer.parseInt(fpd.getPendingL1());
							temp2 = temp2 + Integer.parseInt(fpd.getPendingL2());
							temp3 = temp3 + Integer.parseInt(fpd.getPendingL3());
							temp4 = temp4 + Integer.parseInt(fpd.getPendingL4());
							temp5 = temp5 + Integer.parseInt(fpd.getPendingL5());
						}
						totalLevel1 = String.valueOf(temp1);
						totalLevel2 = String.valueOf(temp2);
						totalLevel3 = String.valueOf(temp3);
						totalLevel4 = String.valueOf(temp4);
						totalLevel5 = String.valueOf(temp5);
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public String beforeDocumentresolveDownload()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (feedId != null && !feedId.equalsIgnoreCase(""))
				{
					String query = null;
					if (type != null && type.equalsIgnoreCase("1"))
					{
						query = "SELECT resolveDoc FROM feedback_status WHERE id=" + feedId;
					} else
					{
						query = "SELECT resolveDoc1 FROM feedback_status WHERE id=" + feedId;
					}
					// System.out.println("QUERY :::  "+query);
					List dataCount = cbt.executeAllSelectQuery(query, connectionSpace);
					if (dataCount != null && dataCount.size() > 0)
					{
						docMap = new LinkedHashMap<String, String>();
						String str = null;
						Object object = null;
						for (Iterator iterator = dataCount.iterator(); iterator.hasNext();)
						{
							object = (Object) iterator.next();
							if (object != null)
							{
								str = object.toString().substring(object.toString().lastIndexOf("//") + 2, object.toString().length());
								documentName = str.substring(14, str.length());
								docMap.put(object.toString(), documentName);
							}
						}
						returnResult = SUCCESS;
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	public String docDownload()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				File file = new File(fileName);
				String str = fileName.substring(fileName.lastIndexOf("//") + 2, fileName.length());
				documentName = str.substring(14, str.length());
				fileName = documentName;
				if (file.exists())
				{
					fileInputStream = new FileInputStream(file);
					return SUCCESS;
				} else
				{
					addActionError("File dose not exist");
					return ERROR;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	// for bar char 2ndblock to be merged 22-10-2014
	public String getJsonData()
	{
		// System.out.println("FeedbackDashboard.getJsonData()");
		if (ValidateSession.checkSession())
		{
			try
			{

				JSONObject jsonObject = new JSONObject();
				jsonArray = new JSONArray();

				deptActionPojo = new FeedbackDashboardDao().getDepartmentDetails(connectionSpace, fromDate, toDate);

				if (deptActionPojo != null && deptActionPojo.size() > 0)
				{
					for (DeptActionPojo pojo : deptActionPojo)
					{
						jsonObject.put("department", pojo.getDeptName());
						// System.out.println("DEpart:  "+pojo.getDeptName());
						jsonObject.put("Action", pojo.getTotalAction());
						jsonObject.put("CAPA", pojo.getTotalCapa());
						jsonObject.put("Issue", pojo.getTotalIsues());
						jsonArray.add(jsonObject);
					}
				}

				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	public List<FeedbackPojoDashboard> getDashPojoList()
	{
		return dashPojoList;
	}

	public void setDashPojoList(List<FeedbackPojoDashboard> dashPojoList)
	{
		this.dashPojoList = dashPojoList;
	}

	public List<FeedbackOverallSummaryBean> getSummaryList()
	{
		return summaryList;
	}

	public void setSummaryList(List<FeedbackOverallSummaryBean> summaryList)
	{
		this.summaryList = summaryList;
	}

	public List<FeedbackDataPojo> getFeedDataDashboardList()
	{
		return feedDataDashboardList;
	}

	public void setFeedDataDashboardList(List<FeedbackDataPojo> feedDataDashboardList)
	{
		this.feedDataDashboardList = feedDataDashboardList;
	}

	public List<FeedbackTicketPojo> getFeedTicketList()
	{
		return feedTicketList;
	}

	public void setFeedTicketList(List<FeedbackTicketPojo> feedTicketList)
	{
		this.feedTicketList = feedTicketList;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public String getGraphHeader()
	{
		return graphHeader;
	}

	public void setGraphHeader(String graphHeader)
	{
		this.graphHeader = graphHeader;
	}

	public String getGraphDescription()
	{
		return graphDescription;
	}

	public void setGraphDescription(String graphDescription)
	{
		this.graphDescription = graphDescription;
	}

	public String getYAxisHeader()
	{
		return yAxisHeader;
	}

	public void setYAxisHeader(String axisHeader)
	{
		yAxisHeader = axisHeader;
	}

	public String getXAxisHeader()
	{
		return xAxisHeader;
	}

	public void setXAxisHeader(String axisHeader)
	{
		xAxisHeader = axisHeader;
	}

	public Map<Integer, String> getPieFeedbackCounter()
	{
		return pieFeedbackCounter;
	}

	public void setPieFeedbackCounter(Map<Integer, String> pieFeedbackCounter)
	{
		this.pieFeedbackCounter = pieFeedbackCounter;
	}

	public Map<String, Integer> getPieYesNoMap()
	{
		return pieYesNoMap;
	}

	public void setPieYesNoMap(Map<String, Integer> pieYesNoMap)
	{
		this.pieYesNoMap = pieYesNoMap;
	}

	public Map<String, Integer> getPieNegFeedStatus()
	{
		return pieNegFeedStatus;
	}

	public void setPieNegFeedStatus(Map<String, Integer> pieNegFeedStatus)
	{
		this.pieNegFeedStatus = pieNegFeedStatus;
	}

	public Map<String, String> getFeedbackPieCounters()
	{
		return feedbackPieCounters;
	}

	public void setFeedbackPieCounters(Map<String, String> feedbackPieCounters)
	{
		this.feedbackPieCounters = feedbackPieCounters;
	}

	public String getTotalPending()
	{
		return totalPending;
	}

	public void setTotalPending(String totalPending)
	{
		this.totalPending = totalPending;
	}

	public String getTotalLevel1()
	{
		return totalLevel1;
	}

	public void setTotalLevel1(String totalLevel1)
	{
		this.totalLevel1 = totalLevel1;
	}

	public String getTotalLevel2()
	{
		return totalLevel2;
	}

	public void setTotalLevel2(String totalLevel2)
	{
		this.totalLevel2 = totalLevel2;
	}

	public String getTotalLevel3()
	{
		return totalLevel3;
	}

	public void setTotalLevel3(String totalLevel3)
	{
		this.totalLevel3 = totalLevel3;
	}

	public String getTotalLevel4()
	{
		return totalLevel4;
	}

	public void setTotalLevel4(String totalLevel4)
	{
		this.totalLevel4 = totalLevel4;
	}

	public String getTotalLevel5()
	{
		return totalLevel5;
	}

	public void setTotalLevel5(String totalLevel5)
	{
		this.totalLevel5 = totalLevel5;
	}

	public String getTotalSnooze()
	{
		return totalSnooze;
	}

	public void setTotalSnooze(String totalSnooze)
	{
		this.totalSnooze = totalSnooze;
	}

	public String getTotalHighPriority()
	{
		return totalHighPriority;
	}

	public void setTotalHighPriority(String totalHighPriority)
	{
		this.totalHighPriority = totalHighPriority;
	}

	public String getTotalIgnore()
	{
		return totalIgnore;
	}

	public void setTotalIgnore(String totalIgnore)
	{
		this.totalIgnore = totalIgnore;
	}

	public String getTotalReAssign()
	{
		return totalReAssign;
	}

	public void setTotalReAssign(String totalReAssign)
	{
		this.totalReAssign = totalReAssign;
	}

	public String getTotalResolve()
	{
		return totalResolve;
	}

	public void setTotalResolve(String totalResolve)
	{
		this.totalResolve = totalResolve;
	}

	public String getTotalCounter()
	{
		return totalCounter;
	}

	public void setTotalCounter(String totalCounter)
	{
		this.totalCounter = totalCounter;
	}

	public List<FeedbackDataPojo> getFeedDataDashboardType()
	{
		return feedDataDashboardType;
	}

	public void setFeedDataDashboardType(List<FeedbackDataPojo> feedDataDashboardType)
	{
		this.feedDataDashboardType = feedDataDashboardType;
	}

	public String getTotalData()
	{
		return totalData;
	}

	public void setTotalData(String totalData)
	{
		this.totalData = totalData;
	}

	public String getToday()
	{
		return today;
	}

	public void setToday(String today)
	{
		this.today = today;
	}

	public String getBlock()
	{
		return block;
	}

	public void setBlock(String block)
	{
		this.block = block;
	}

	public List<GridDataPropertyView> getMasterViewProp()
	{
		return masterViewProp;
	}

	public void setMasterViewProp(List<GridDataPropertyView> masterViewProp)
	{
		this.masterViewProp = masterViewProp;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public List<Object> getMasterViewList()
	{
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList)
	{
		this.masterViewList = masterViewList;
	}

	public String getDeptId()
	{
		return deptId;
	}

	public void setDeptId(String deptId)
	{
		this.deptId = deptId;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getFeedId()
	{
		return feedId;
	}

	public void setFeedId(String feedId)
	{
		this.feedId = feedId;
	}

	public Map<String, String> getDocMap()
	{
		return docMap;
	}

	public void setDocMap(Map<String, String> docMap)
	{
		this.docMap = docMap;
	}

	public String getDocumentName()
	{
		return documentName;
	}

	public void setDocumentName(String documentName)
	{
		this.documentName = documentName;
	}

	public InputStream getFileInputStream()
	{
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream)
	{
		this.fileInputStream = fileInputStream;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public List<TicketAllotmentPojo> getAllotPojo()
	{
		return allotPojo;
	}

	public void setAllotPojo(List<TicketAllotmentPojo> allotPojo)
	{
		this.allotPojo = allotPojo;
	}

	public List<FeedbackAnalysis> getAnalysisPojo()
	{
		return analysisPojo;
	}

	public void setAnalysisPojo(List<FeedbackAnalysis> analysisPojo)
	{
		this.analysisPojo = analysisPojo;
	}

	public List<DeptActionPojo> getDeptActionPojo()
	{
		return deptActionPojo;
	}

	public void setDeptActionPojo(List<DeptActionPojo> deptActionPojo)
	{
		this.deptActionPojo = deptActionPojo;
	}

	public String getSubTotalIssue()
	{
		return subTotalIssue;
	}

	public void setSubTotalIssue(String subTotalIssue)
	{
		this.subTotalIssue = subTotalIssue;
	}

	public String getSubTotalAction()
	{
		return SubTotalAction;
	}

	public void setSubTotalAction(String subTotalAction)
	{
		SubTotalAction = subTotalAction;
	}

	public String getSubTotalCapa()
	{
		return SubTotalCapa;
	}

	public void setSubTotalCapa(String subTotalCapa)
	{
		SubTotalCapa = subTotalCapa;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getTotalNoted()
	{
		return totalNoted;
	}

	public void setTotalNoted(String totalNoted)
	{
		this.totalNoted = totalNoted;
	}

	public List<NegativeFeedbackStagePojo> getNegativeFeedList()
	{
		return negativeFeedList;
	}

	public void setNegativeFeedList(List<NegativeFeedbackStagePojo> negativeFeedList)
	{
		this.negativeFeedList = negativeFeedList;
	}

	public String getTotalTO()
	{
		return totalTO;
	}

	public void setTotalTO(String totalTO)
	{
		this.totalTO = totalTO;
	}

	public String getTotalNFA()
	{
		return totalNFA;
	}

	public void setTotalNFA(String totalNFA)
	{
		this.totalNFA = totalNFA;
	}
	

}