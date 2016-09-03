package com.Over2Cloud.ctrl.text2mail;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.Child;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.Parent;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.excel.GenericWriteExcel;
import com.Over2Cloud.ctrl.compliance.ComplianceExcelDownload;
import com.Over2Cloud.helpdesk.BeanUtil.DashboardPojo;
import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
import com.opensymphony.xwork2.ActionContext;

public class PullReport extends GridPropertyBean implements ServletRequestAware
{

	
	private static final long serialVersionUID = -617082085272148301L;
	/**
	 * this class is for cross referral activity board 
	 * Author: Manab 11-06-2015
	 */
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String empId = (String) session.get("empIdofuser");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");

	private HttpServletRequest request;
	private Map<String, String> uploadKRMap;
	private String startDate;
	private String endDate;
	private String type;
	private String keyword;
	private List<Parent> parentTakeaction = null;
	private String emailId;
	private String emailText;
	private String emailID;
	private String password;
	private String server;
	private String port;
	private String[] columnNames;
	private String downloadType = null;
	private String download4 = null;
	private Map<String, String> columnMap = null;
	private Map<String, String> columnMap1 = null;
	private List<GridDataPropertyView> viewPullReportGrid = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewPullReportGrid1 = new ArrayList<GridDataPropertyView>();
	private List<GridDataPropertyView> viewSendPullReportGrid = new ArrayList<GridDataPropertyView>();
	private String tableName = null;
	private String tableAllis = null;
	private String excelName = null;
	private String mainHeaderName;
	private List<Object> gridDataModel = null;
	private List<Object> viewPullData = null;
	private String toDate;
	private String fromDate;
	private Map<Integer, String> locationMap;
	private Map<Integer, String> SpecialityMap;
	private Map<Integer, String> consultantMap;
	private Map<Integer, String> ExcutiveMap1;
	private String fromDate12, toDate12, fromDate122, toDate122;
	private String location;
	private String spc;
	private String cons;
	private Integer kw;
	private List keywrd;
	private String sts, date, autoAck, excecutive;

	private int sCounter, dCounter;
	private String c;
	private int a1;
	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// sorting order - asc or desc
	private String sord = "";
	// get index row - i.e. user click to sort.
	private String sidx = "";
	// Search Field
	private String searchField = "";
	// The Search String
	private String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper = "";
	// Your Total Pages
	private Integer total = 0;
	// All Record
	private Integer records = 0;
	private boolean loadonce = false;
	// Grid colomn view
	private String oper;
	private String id;
	private String type2;
	private String kws;
	private String exe;
	private String chExc;
	GridPropertyBean gb = new GridPropertyBean();
	private String mNoh;
	private String kwh;
	private String idH, idv, idA;
	Map<String, Object> oneH;
	private String excelFileName;
	private FileInputStream excelStream;
	private JSONArray jsonArray;
	private JSONObject jsonObject;
	private JSONArray commonJSONArray = new JSONArray();
	private JSONArray RefMapSource = new JSONArray();
	private String name, speciality, mobileNo, msg;

	private List<Object> gridModelHis = null;
	private List<Object> gridModelHis1 = new ArrayList<Object>();
	private String autoAckMassage;
	Map<String, Integer> graphMap = null;
	DashboardPojo dashObj;

	List<DashboardPojo> dept_subdeptcounterList = null;
	Map<String, Integer> graphCatgMap = null;
	List<FeedbackPojo> catgCountList = null;
	private Map<String, String> allDeptName1 = null;
	private String dept;
	private String emp;
	private String idCons;
	Map<String, String> consData = null;
	private String actionStatus;

	private Map<Integer, String> serviceConsList = null;

	public String execute()
	{
		try
		{
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
		} catch (Exception e)
		{
			// log.error("Date : " + DateUtil.getCurrentDateIndianFormat() +
			// " Time: " + DateUtil.getCurrentTime() + " " +
			// "Problem in Over2Cloud  method: execute of class " + getClass(),
			// e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String takeActionPull()
	{
		// System.out.println("name "+name);
		// System.out.println("id "+id);
		// System.out.println("exe "+excecutive);
		// System.out.println("dat "+date);
		// System.out.println("infi "+autoAck);
		// System.out.println("loc "+location);
		// System.out.println("mob "+mobileNo);
		// System.out.println("spc "+speciality);

		return SUCCESS;
	}

	public String TakeActionPullFinal()
	{
		// System.out.println("actionStatus "+actionStatus);
		if (actionStatus.length() > 0)
		{
			// System.out.println("actionStatus "+actionStatus);
			// System.out.println("name "+name);
			// System.out.println("id "+id);
			// System.out.println("exe "+excecutive);
			// System.out.println("dat "+date);
			// System.out.println("infi "+autoAck);
			// System.out.println("loc "+location);
			// System.out.println("mob "+mobileNo);
			// System.out.println("spc "+speciality);

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder sb = new StringBuilder();
			sb.append("update pull_report_t2m set history='" + actionStatus + "' where id='" + id + "' ");
			boolean update = cbt.updateTableColomn(connectionSpace, sb);
			// System.out.println("update "+update);
			if (update = true)
			{
				String revertSMS = "";
				if (actionStatus.equalsIgnoreCase("admitted"))
				{
					revertSMS = "Dear " + name + ", the patient you referred on " + date + " is admitted to hospilat. Thanks  ";
				} else
				{
					revertSMS = "Dear " + name + ", the patient you referred on " + date + " is Discharge from hospilat. Thanks for your reference ";
				}
				new CommunicationHelper().addMessage("", "", mobileNo, revertSMS, "", "Pending", "0", "T2M", connectionSpace);
			}
		}
		addActionMessage("This refered patient is updated as " + actionStatus + "  Successfully!!!");
		return SUCCESS;
	}

	public String beforePullReport()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> sourcemasterColname = new ArrayList<String>();
			sourcemasterColname.add("id");
			sourcemasterColname.add("keyword");
			List sourceMasterData = cbt.viewAllDataEitherSelectOrAll("configurekeyword", sourcemasterColname, connectionSpace);
			if (sourceMasterData != null)
			{
				uploadKRMap = new LinkedHashMap<String, String>();
				for (Object c : sourceMasterData)
				{
					Object[] dataC = (Object[]) c;
					uploadKRMap.put(dataC[0].toString(), dataC[1].toString());
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeViewPullReoprt()
	{
		List data3 = null;
		try
		{
			parentTakeaction = new ArrayList<Parent>();

			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			StringBuilder query2 = new StringBuilder("");

			query1.append("select c.first_name,m.mobile_no,m.msg,m.time,m.date from mis_data m left join contactbasicdetails AS c on m.mobile_no=c.mobilenumber  where m.date between '" + DateUtil.convertDateToUSFormat(startDate) + "' and '" + DateUtil.convertDateToUSFormat(endDate) + "' and m.type = 'in' ");
			query2.append("select cd.first_name, ms.msisdn,ms.msg,ms.time,ms.date from msg_stats ms left join contactbasicdetails AS cd on ms.msisdn=cd.mobilenumber  where ms.date between '" + DateUtil.convertDateToUSFormat(startDate) + "' and '" + DateUtil.convertDateToUSFormat(endDate) + "' and ms.emailflag='out'");
			if (keyword != null && !keyword.equals("Select"))
			{
				query1.append(" and m.msg like '" + keyword + "' ");
			}

			if (type.equalsIgnoreCase("Incoming"))
			{
				// //("query1.toString() " + query1.toString());
				data3 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			} else if (type.equalsIgnoreCase("Outgoing"))
			{
				data3 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);

			}
			parentTakeaction = new ArrayList<Parent>();

			if (data3 != null)
			{
				int i = 0;
				for (Iterator it = data3.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					Parent p1 = new Parent();
					if (obdata != null)
					{
						List<Child> child = new ArrayList<Child>();
						for (int j = 0; j < obdata.length; j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								c1.setName(obdata[j].toString());
								// list.add(obdata[j].toString());
								// //(obdata[j].toString());
							} else
							{
								c1.setName("NA");
								// list.add("NA");
							}
							// ////("###"+c1.getName());
							child.add(c1);
						}
						p1.setChildList(child);
					}
					parentTakeaction.add(p1);
				}

			}

		} catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String histiryGrid()
	{
		try
		{
		} catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewhisData()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				List<Object> Listhb = new ArrayList<Object>();
				List<String> fieldNames = new ArrayList<String>();

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder();
				query1.append("select incomingKeyword, mobileNo from pull_report_t2m where id = '" + idv + "'");

				List data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();
						String q1 = "select incomingKeyword, date, time from pull_report_t2m where incomingKeyword='" + obdata[0].toString() + "' and mobileNo='" + obdata[1].toString() + "' ";
						List data1 = cbt.executeAllSelectQuery(q1.toString(), connectionSpace);
						if (data1 != null && data1.size() > 0)
						{

							viewPullData = new ArrayList<Object>();

							for (Iterator it1 = data1.iterator(); it1.hasNext();)
							{
								Object[] obdata1 = (Object[]) it1.next();
								Map<String, Object> one = new HashMap<String, Object>();
								one.put("incomingKeyword", obdata1[0].toString());
								one.put("date", DateUtil.convertDateToIndianFormat(obdata1[1].toString()));
								one.put("time", obdata1[2].toString());
								Listhb.add(one);

							}
						}
					}
				}
				setGridModelHis1(Listhb);
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings(
	{ "unused", "rawtypes" })
	public String abc()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				List data = null;
				// //
				// ("fromDate122 " + fromDate122);
				// ("toDate122 " + toDate122);
				// ("location " + location);
				query1.append(" select  c.id,c.name,c.mobileNo,c.incomingKeyword,c.date,c.time,c.speciality,c.location,c.excecutive,c.autoAck from pull_report_t2m as c");
				if (fromDate12 != null || toDate12 != null || location != null || spc != null || cons != null || kws != null || fromDate122 != null || toDate122 != null)
				{
					query1.append(" where");
					if (fromDate12 != null || toDate12 != null)
					{
						query1.append(" date between '" + DateUtil.convertDateToUSFormat(fromDate12) + "' and '" + DateUtil.convertDateToUSFormat(toDate12) + "'");

					}
					if (fromDate122 != null || toDate122 != null)
					{
						// ("get the clllll >>>>>>>>>");
						query1.append(" date between '" + DateUtil.convertDateToUSFormat(fromDate122) + "' and '" + DateUtil.convertDateToUSFormat(toDate122) + "'");

					}
					if (!location.equalsIgnoreCase("-1"))
					{
						if (fromDate122 != null || toDate122 != null || spc != null || cons != null || kws != null)
						{
							query1.append(" and");
						}
						query1.append("  location = '" + location + "'");
					}
					if (spc != null)
					{
						if (fromDate122 != null || toDate122 != null || location != null || cons != null || kws != null)
						{
							query1.append(" and");
						}
						query1.append("  speciality = '" + spc + "'");
					}
					if (!cons.equalsIgnoreCase("-1"))
					{
						if (fromDate122 != null || toDate122 != null || location != null || spc != null || kws != null)
						{
							query1.append(" and");
						}
						StringBuilder query2 = new StringBuilder("");
						query2 = new StringBuilder();
						query2.append("select name, id from pull_report_t2m where id ='" + cons + "'");
						List data1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
						List<Object> ListName = new ArrayList<Object>();
						for (Iterator it = data1.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							query1.append("  name = '" + obdata[0].toString() + "'");

						}
					}
					if (!kws.equalsIgnoreCase("-1"))
					{
						if (fromDate122 != null || toDate122 != null || location != null || cons != null || cons != null)
						{
							query1.append(" and");
						}
						query1.append("  incomingKeyword = '" + kws + "'");
					}

				}
				// query1.append(" group by incomingKeyword, mobileNo");
				// ("i got that countoiertlowerutouerwotupweoutper?????????? " +
				// query1);
				// System.out.println("query1>>>>    "+query1.toString());
				data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

				a1 = data.size();
				returnResult = SUCCESS;
				// //
			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	}

	// for history view
	public String beforeHistoryView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// //("idH is +++++++++++++++++++++ " + idH);
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				oneH = new LinkedHashMap<String, Object>();
				List data = null;
				query1.append("select id, name, speciality ,location, excecutive from pull_report_t2m where id ='" + idH + "' ");
				data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator it = data.iterator(); it.hasNext();)
					{
						Object[] obdata = (Object[]) it.next();

						oneH.put("id", obdata[0].toString());

						oneH.put("Name ", obdata[1].toString());

						String query2 = "select speciality, brief from speciality  where id = '" + obdata[2] + "'";
						List data2 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
						if (data2 != null)
						{
							for (Iterator it2 = data2.iterator(); it2.hasNext();)
							{
								Object[] obdata2 = (Object[]) it2.next();
								oneH.put("Speciality", obdata2[0].toString());
							}
						}

						String query3 = "select location, brief from location_t2m where id = '" + obdata[3] + "'";
						List data3 = cbt.executeAllSelectQuery(query3.toString(), connectionSpace);
						if (data3 != null)
						{
							for (Iterator it3 = data3.iterator(); it3.hasNext();)
							{
								Object[] obdata3 = (Object[]) it3.next();
								oneH.put("Location", obdata3[0].toString());
							}
						}
						// /
						String query4 = "select id, empName from employee_basic where useraccountid ='" + obdata[4].toString() + "'";

						List data4 = cbt.executeAllSelectQuery(query4.toString(), connectionSpace);
						if (data4 != null)
						{
							for (Iterator it4 = data4.iterator(); it4.hasNext();)
							{
								Object[] obdata4 = (Object[]) it4.next();
								oneH.put("Excecutive", obdata4[1].toString());
							}

						}
					}
				}

				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	// for view AutoAck massage
	public String AutoAckClickView()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{

				// //("idA is " + idA);
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				String query = "select id, autoAck from pull_report_t2m where id ='" + idA + "'";
				List autoAck = cbt.executeAllSelectQuery(query, connectionSpace);
				if (autoAck != null)
				{
					for (Iterator it1 = autoAck.iterator(); it1.hasNext();)
					{
						Object[] obdata1 = (Object[]) it1.next();

						setAutoAckMassage(obdata1[1].toString());

					}
				}

				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public String viewPullReportss()
	{
		// (">>>>>>>>>>>>>>>>>>>"+fromDate122);
		// (">>>>>>>>>>>>>>>>>>>"+toDate122);
		String[] chkFdate = fromDate122.split("-");
		// ("1 st date "+chkFdate[0].length());
		if (chkFdate[0].length() == 4)
		{
			fromDate122 = DateUtil.convertDateToUSFormat(fromDate122);
			toDate122 = DateUtil.convertDateToUSFormat(toDate122);
		}
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				List data = null;
				query1.append("select count(*) from pull_report_t2m where  date between '" + DateUtil.convertDateToUSFormat(fromDate122) + "' and '" + DateUtil.convertDateToUSFormat(toDate122) + "'");
				// ("query1 counter "+query1);
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("1");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					query1 = new StringBuilder();

					query1.append(" select c.id,c.name,c.history,c.mobileNo,c.incomingKeyword,c.date,c.time,c.speciality,c.location,c.excecutive,c.autoAck from pull_report_t2m as c");
					if (fromDate122 != null || toDate122 != null || location != null || spc != null || cons != null || kws != null || chExc != null)
					{
						query1.append(" where");
						if (fromDate122 != null || toDate122 != null)
						{
							query1.append(" date between '" + DateUtil.convertDateToUSFormat(fromDate122) + "' and '" + DateUtil.convertDateToUSFormat(toDate122) + "'");

						}
						if (location != null)
						{
							if (fromDate122 != null || toDate122 != null || spc != null || cons != null || kws != null || chExc != null)
							{
								query1.append(" and");
							}
							query1.append("  location = '" + location + "'");
						}
						if (spc != null)
						{
							if (fromDate122 != null || toDate122 != null || location != null || cons != null || kws != null || chExc != null)
							{
								query1.append(" and");
							}
							query1.append("  speciality = '" + spc + "'");
						}
						if (cons != null)
						{
							if (fromDate122 != null || toDate122 != null || location != null || spc != null || kws != null || chExc != null)
							{
								query1.append(" and");
							}
							StringBuilder query2 = new StringBuilder("");
							query2 = new StringBuilder();
							query2.append("select name, id from pull_report_t2m where id ='" + cons + "'");
							List data1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
							List<Object> ListName = new ArrayList<Object>();
							for (Iterator it = data1.iterator(); it.hasNext();)
							{
								Object[] obdata = (Object[]) it.next();
								query1.append("  name = '" + obdata[0].toString() + "'");

							}
						}
						if (kws != null)
						{
							if (fromDate122 != null || toDate122 != null || location != null || cons != null || cons != null || chExc != null)
							{
								query1.append(" and");
							}

							query1.append("  incomingKeyword = '" + kws + "'");
						}
						if (chExc != null)
						{
							if (fromDate122 != null || toDate122 != null || location != null || cons != null || cons != null || kws != null)
							{
								query1.append(" and");
							}
							String query21 = "select id, name from useraccount where name ='" + getChExc() + "'";
							List data21 = cbt.executeAllSelectQuery(query21, connectionSpace);
							List<Object> ListName = new ArrayList<Object>();
							for (Iterator it = data21.iterator(); it.hasNext();)
							{
								Object[] obdata = (Object[]) it.next();
								query1.append("  excecutive = '" + obdata[0].toString() + "'");
							}
							// query1.append("  incomingKeyword = '"+kws+"'");
						}

					} else
					{
						// DateUtil.getNextDateAfter(-7)
					}

					query1.append(" order by c.date desc, c.time desc");
					// System.out.println("i got that counter?????????? " +
					// query1);

					data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{

						List<String> fieldNames = new ArrayList<String>();
						fieldNames.add("id");
						fieldNames.add("history");
						fieldNames.add("name");
						fieldNames.add("mobileNo");
						fieldNames.add("incomingKeyword");
						fieldNames.add("date");
						fieldNames.add("time");
						fieldNames.add("speciality");
						fieldNames.add("location");
						fieldNames.add("excecutive");
						fieldNames.add("autoAck");
						// fieldNames.add("mail_flag");
						// c.id,c.name,c.mobileNo,c.incomingKeyword,c.date,c.time,c.speciality,c.location,c.excecutive,c.autoAck
						// //("fieldNames  :::: " + fieldNames);
						viewPullData = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							// for searching

							for (int k = 0; k < fieldNames.size(); k++)
							{

								// //("for 2nd times");
								if (obdata[k] != null)
								{

									if (k == 0)
									{
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									} else
									{
										if (fieldNames.get(k).toString().equalsIgnoreCase("date"))
										{

											one.put(fieldNames.get(k).toString(), obdata[k].toString());
										} else
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
									if (fieldNames.get(k).toString().equalsIgnoreCase("autoAck"))
									{

										one.put(fieldNames.get(k).toString(), obdata[k].toString());

									}

									if (fieldNames.get(k).toString().equalsIgnoreCase("location"))
									{

										String query2 = "select location, brief from location_t2m where id = '" + obdata[k] + "'";
										List data1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
										if (data1 != null)
										{
											for (Iterator it1 = data1.iterator(); it1.hasNext();)
											{
												Object[] obdata1 = (Object[]) it1.next();

												one.put(fieldNames.get(k).toString(), obdata1[0].toString());
											}
										}
									}
									if (fieldNames.get(k).toString().equalsIgnoreCase("speciality"))
									{

										String query2 = "select speciality, brief from speciality  where id = '" + obdata[k] + "'";
										List data1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
										if (data1 != null)
										{
											for (Iterator it1 = data1.iterator(); it1.hasNext();)
											{
												Object[] obdata1 = (Object[]) it1.next();
												one.put(fieldNames.get(k).toString(), obdata1[0].toString());

											}
										}
									}
									String mNo = null;
									String kw = null;
									if (fieldNames.get(k).toString().equalsIgnoreCase("history") || fieldNames.get(k).toString().equalsIgnoreCase("mobileNo") || fieldNames.get(k).toString().equalsIgnoreCase("incomingKeyword"))
									{
										if (fieldNames.get(k).toString().equalsIgnoreCase("mobileNo"))
										{
											setmNoh(obdata[k].toString());
											mNo = obdata[k].toString();
										}

										if (fieldNames.get(k).toString().equalsIgnoreCase("incomingKeyword"))
										{
											setKwh("L2");
											kw = "L2";
										}

										// have to change the history
										one.put("history", obdata[2].toString());
									}

									// find for excecutive name
									if (fieldNames.get(k).toString().equalsIgnoreCase("excecutive"))
									{
										String query2 = "select id, empName from employee_basic where id ='" + obdata[k].toString() + "'";
										List data12 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);

										if (data12 != null)
										{
											for (Iterator it12 = data12.iterator(); it12.hasNext();)
											{
												Object[] obdata12 = (Object[]) it12.next();
												one.put(fieldNames.get(k).toString(), obdata12[1].toString());
											}
										}

									}

								}

								else
								{
									one.put(fieldNames.get(k).toString(), "NA");
								}
							}
							one.put("name", obdata[1].toString());

							String obdataDt = DateUtil.convertDateToIndianFormat(obdata[5].toString()) + " , " + obdata[6].toString();
							Object b = obdataDt;
							// //("kkkkkkkkkk " + b.toString());
							one.put("date", b.toString());
							one.put("name", obdata[1].toString());
							one.put("history", "1234");
							one.put("mobileNo", "Not Informed");
							
							
							one.put("excecutive", "L2");
							
							one.put("priority", "Routine");
							
							
							
							one.put("incomingKeyword", "10:00");
							
							Listhb.add(one);
						}
						setViewPullData(Listhb);
						// //(viewPullData);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}

	@SuppressWarnings("rawtypes")
	public String getAnalyticsGraphData()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				List keywordList = null;

				keywordList = getAnalyticalGridReport();
				if (keywordList != null && keywordList.size() > 0)
				{
					graphMap = new LinkedHashMap<String, Integer>();
					Object[] object = null;
					for (Iterator iterator = keywordList.iterator(); iterator.hasNext();)
					{
						object = (Object[]) iterator.next();
						graphMap.put(object[1].toString() + "-" + object[2].toString(), Integer.parseInt(object[3].toString()));
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

	public String history(SessionFactory connection, String mob, String kw)
	{
		List dataList = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("select mobileNo, incomingKeyword from pull_report_t2m group by mobileNo ,incomingKeyword ");
			// //("Count " + query.toString());
			dataList = cbt.executeAllSelectQuery(query.toString(), connection);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (dataList != null && dataList.size() > 0)
			return dataList.get(0).toString();
		else
			return "0";
	}

	public String beforePullSendEmailReport()
	{
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> sourcemasterColname = new ArrayList<String>();
			sourcemasterColname.add("id");
			sourcemasterColname.add("keyword");
			List sourceMasterData = cbt.viewAllDataEitherSelectOrAll("configurekeyword", sourcemasterColname, connectionSpace);
			if (sourceMasterData != null)
			{
				uploadKRMap = new LinkedHashMap<String, String>();
				for (Object c : sourceMasterData)
				{
					Object[] dataC = (Object[]) c;
					uploadKRMap.put(dataC[0].toString(), dataC[1].toString());
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String viewPullEndEmailReoprt()
	{
		try
		{
			parentTakeaction = new ArrayList<Parent>();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			StringBuilder query2 = new StringBuilder("");
			query1.append("select EmailID,EmailText,Date,time,flag from sendemaildata where date between '" + startDate + "' and '" + endDate + "' ");
			List data3 = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
			parentTakeaction = new ArrayList<Parent>();

			if (data3 != null)
			{
				int i = 0;
				for (Iterator it = data3.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					Parent p1 = new Parent();
					if (obdata != null)
					{
						List<Child> child = new ArrayList<Child>();
						for (int j = 0; j < obdata.length; j++)
						{
							Child c1 = new Child();
							if (obdata[j] != null)
							{
								c1.setName(obdata[j].toString());
								// list.add(obdata[j].toString());
								// //(obdata[j].toString());
							} else
							{
								c1.setName("NA");
								// list.add("NA");
							}
							// ////("###"+c1.getName());
							child.add(c1);
						}
						p1.setChildList(child);
					}
					parentTakeaction.add(p1);
				}

			}

		} catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}

	// send mail

	public String sendMailT2M()
	{
		try
		{

			List eveninglist = new ArrayList();
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			// //////System.out.println("Inside If Block");
			StringBuilder getDataQuery = new StringBuilder();
			getDataQuery.append(" select name, mobileNo, incomingKeyword, date, time, excecutive, speciality, location, autoAck from  pull_report_t2m where date between '" + DateUtil.convertDateToUSFormat(fromDate122) + "' and '" + DateUtil.convertDateToUSFormat(toDate122) + "'");

			if (cons != "-1" && cons.length() != 0)
			{
				StringBuilder query2 = new StringBuilder("");
				query2 = new StringBuilder();
				query2.append("select name, id from pull_report_t2m where id ='" + cons + "'");
				List data1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
				List<Object> ListName = new ArrayList<Object>();
				for (Iterator it = data1.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					getDataQuery.append(" and  name = '" + obdata[0].toString() + "'");

				}
			}

			if (!kws.equalsIgnoreCase("-1"))
			{

				getDataQuery.append(" and incomingKeyword = '" + kws + "'");
			}

			// //////System.out.println("*****query 2 : " + getDataQuery);

			List reportdatalist1 = cbt.executeAllSelectQuery(getDataQuery.toString(), connectionSpace);
			for (Iterator iterator1 = reportdatalist1.iterator(); iterator1.hasNext();)
			{
				PullReportPOJO fstatus;

				fstatus = new PullReportPOJO();
				Object[] object1 = (Object[]) iterator1.next();
				fstatus.setName((object1[0]).toString());
				if (object1[1] != null)
					fstatus.setMobileNo(object1[1].toString());
				if (object1[2] != null)
					fstatus.setIncomingKeyword((object1[2]).toString());
				if (object1[3] != null)
					fstatus.setDate((object1[3]).toString() + ", " + (object1[4]).toString());

				if (object1[7] != null)
				{

					if (!object1[7].toString().equalsIgnoreCase("Un Mapped"))
					{

						String query3 = "select location, brief from location_t2m where id = '" + object1[7].toString() + "'";
						List data3 = new createTable().executeAllSelectQuery(query3, connectionSpace);

						if (data3 != null)
						{
							for (Iterator it3 = data3.iterator(); it3.hasNext();)
							{
								Object[] obdata3 = (Object[]) it3.next();
								// oneH.put("Location", obdata3[0].toString());
								// rowData.createCell((int)
								// cellIndex).setCellValue(obdhistoryata3[0].toString());
								fstatus.setLocation(obdata3[0].toString());
							}
						}
					} else
					{
						fstatus.setLocation((object1[7]).toString());
					}

				}
				if (object1[6] != null)
					fstatus.setSpeciality((object1[6]).toString());
				if (object1[5] != null)
				{

					if (!object1[5].toString().equalsIgnoreCase("Un Mapped"))
					{
						String query2 = "select id, empName from employee_basic where id ='" + object1[5].toString() + "'";
						// //////System.out.println("lkjsfgkl " + query2);
						List data12 = new createTable().executeAllSelectQuery(query2.toString(), connectionSpace);

						if (data12 != null)
						{
							for (Iterator it12 = data12.iterator(); it12.hasNext();)
							{
								Object[] obdata12 = (Object[]) it12.next();
								fstatus.setExcecutive(obdata12[1].toString());
							}
						}
					} else
					{
						fstatus.setExcecutive((object1[5]).toString());
					}

				}
				if (object1[8] != null)
					fstatus.setAutoAck((object1[8]).toString());

				eveninglist.add(fstatus);
			}

			// //////System.out.println(" from date " + fromDate122 + " todate "
			// +
			// toDate122 + " emp " + emp + " dept " + dept);
			boolean status;
			String mailtext = null;
			String empname = null;
			if (emp != null)
			{

				String query2 = "select id,  emailIdOne, empName from employee_basic where id =" + emp + "";
				// //////System.out.println("lkjsfgkl " + query2);
				List data12 = new createTable().executeAllSelectQuery(query2.toString(), connectionSpace);

				if (data12 != null)
				{
					for (Iterator it12 = data12.iterator(); it12.hasNext();)
					{
						Object[] obdata12 = (Object[]) it12.next();

						emailId = obdata12[1].toString();
						empname = obdata12[2].toString();
					}
				}
				mailtext = getConfigMailForEveningReportT2M(eveninglist, empname);

			}
			boolean ststus = false;
			// //////System.out.println(emailId.length());
			if (emailId.length() > 5)
			{
				ststus = new MsgMailCommunication().addMailHR(empname, "", emailId, "Text To Mail Report for '" + fromDate122 + " to " + toDate122, mailtext, "", "Pending", "0", "", "REF", connectionSpace);
			}
			// //////System.out.println("ststusmmm " + ststus);
			if (ststus)
			{
				addActionMessage("Mail Sent Successfully!!!");
				// boolean
				// mailFlag=mt.confMailHTML(getServer(),getPort(),getEmailID(),getPassword(),mailIds,"Configure Keyword",autoReplyMsg);
				// //////System.out.println("sattus " + ststus);
			} else
			{
				addActionMessage("ERROR: There is some error !");
				// //////System.out.println("sattus " + ststus);
				return ERROR;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getConfigMailForEveningReportT2M(List eveninglist, String empName)
	{
		List orgData = new LoginImp().getUserInfomation("1", "IN");
		String orgName = "";
		if (orgData != null && orgData.size() > 0)
		{
			for (Iterator iterator = orgData.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				orgName = object[0].toString();
			}
		}
		StringBuilder mailtext = new StringBuilder("");
		mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + orgName + "</b></td></tr></tbody></table>");
		mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b></b></td></tr></tbody></table>");
		mailtext.append("<b>Dear " + DateUtil.makeTitle(empName) + ",</b>");
		mailtext.append("<br><br><b>Hello!!!</b>");
		mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Text To Mail Report</b></td></tr></tbody></table>");

		mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
		mailtext.append("<tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Name</b></td></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Mobile</b></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Incomming Keyword</b></td>"
				+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Massage</b></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Date</b></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Executive</b></td>"
				+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Speciality</b></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Location</b></td></tr>");
		// ////////System.out.println("******Sized : "+morninglist.size());
		for (Iterator iterator1 = eveninglist.iterator(); iterator1.hasNext();)
		{
			try
			{
				PullReportPOJO object1 = (PullReportPOJO) iterator1.next();

				mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + object1.getName() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + object1.getMobileNo() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
						+ object1.getIncomingKeyword() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + object1.getAutoAck() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + object1.getDate() + "</td>"
						+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + object1.getExcecutive() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + object1.getSpeciality() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
						+ object1.getLocation() + "</td></tr>");
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
		mailtext
				.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
		// ////////System.out.println("   " + mailtext);
		return mailtext.toString();
	}

	public String emailResend()
	{
		try
		{
			StringBuilder queryForEmail = new StringBuilder("");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			Mailtest mt = new Mailtest();
			queryForEmail.append("select*from email_configuration_data");
			List dataCountForEmail = cbt.executeAllSelectQuery(queryForEmail.toString(), connectionSpace);
			if (dataCountForEmail != null && dataCountForEmail.size() > 0)
			{
				// emailID=(String) dataCount.get(0);
				for (Iterator it = dataCountForEmail.iterator(); it.hasNext();)
				{
					Object[] obdata = (Object[]) it.next();
					emailID = (String) obdata[3];
					password = (String) obdata[4];
					server = (String) obdata[1];
					port = (String) obdata[2];
				}

			}
			boolean mailFlag = mt.confMailHTML(getServer(), getPort(), getEmailID(), getPassword(), emailId, "Pull Send Email Report", emailText);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String currentColumnForPull()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				if (download4 != null && download4.equals("pullReport"))
				{
					// ("inside the getColumn4Download() method");
					tableAllis = "ast";
					returnResult = getColumnName2("mapped_pullreport_configuration", "pullreport_configuration", tableAllis);
					tableName = "mis_data";
					excelName = "Pull Report";
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

	public String getColumnName2(String mappedTableName, String basicTableName, String allias)
	{
		String returnResult = ERROR;
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			List<GridDataPropertyView> columnList = Configuration.getConfigurationData(mappedTableName, accountID, connectionSpace, "", 0, "table_name", basicTableName);
			columnMap = new LinkedHashMap<String, String>();
			if (columnList != null && columnList.size() > 0)
			{
				if (downloadType != null && downloadType.equals("downloadExcel"))
				{
					for (GridDataPropertyView gdp1 : columnList)
					{

						if (!gdp1.getColomnName().equals("deptHierarchy"))
						{
							columnMap.put(allias + "." + gdp1.getColomnName(), gdp1.getHeadingName());
						}
					}
				} else
				{
					for (GridDataPropertyView gdp1 : columnList)
					{
						if (!gdp1.getColomnName().equals("deptHierarchy") && !gdp1.getColomnName().equals("userName") && !gdp1.getColomnName().equals("date") && !gdp1.getColomnName().equals("time"))
						{
							columnMap.put(gdp1.getColomnName(), gdp1.getHeadingName());
						}
					}
				}
				if (columnMap != null && columnMap.size() > 0)
				{
					session.put("columnMap", columnMap);
				}
			}
			// //("columnMap size " + columnMap.size());
			returnResult = SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return returnResult;
	}

	public String downloadPullReport()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if (sessionFlag)
		{
			try
			{
				String query = "select * from mis_data";
				List dataList = new ArrayList<String>();
				List titleList = Arrays.asList("Name,Mobile No,Message,Time,Date".split(","));

				dataList = cbt.executeAllSelectQuery(query, connectionSpace);
				new GenericWriteExcel().createExcel("Text2Mail", excelName, dataList, titleList, null, excelName);
				returnResult = SUCCESS;
			}

			catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}

		return returnResult;
	}

	public String beforePullReportViewHeader()
	{
		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<String> sourcemasterColname = new ArrayList<String>();
				sourcemasterColname.add("id");
				sourcemasterColname.add("keyword");
				List sourceMasterData = cbt.viewAllDataEitherSelectOrAll("configurekeyword", sourcemasterColname, connectionSpace);
				if (sourceMasterData != null)
				{
					uploadKRMap = new LinkedHashMap<String, String>();
					for (Object c : sourceMasterData)
					{
						Object[] dataC = (Object[]) c;
						uploadKRMap.put(dataC[0].toString(), dataC[1].toString());
					}
				}
				SpecialityMap = new LinkedHashMap<Integer, String>();
				locationMap = new LinkedHashMap<Integer, String>();
				consultantMap = new LinkedHashMap<Integer, String>();
				// ExcutiveMap1 = new LinkedHashMap<Integer, String>();
				List data = cbt.executeAllSelectQuery("select id, " + "speciality from speciality order by speciality  ASC ", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							SpecialityMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
						}
					}
				}
				data.clear();
				data = cbt.executeAllSelectQuery("select id, " + "location from location_t2m order by location  ASC", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							locationMap.put(Integer.parseInt(object[0].toString()), object[1].toString());

						}
					}
				}
				// for consultants show in header DD
				data = cbt.executeAllSelectQuery("select excecutive, name from pull_report_t2m group By name order by name  ASC ", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[0] != null)
						{
							List ExName = cbt.executeAllSelectQuery("select id, empName from employee_basic where id='" + object[0] + "'", connectionSpace);
							// //("ExName " + ExName.size());
							if (ExName != null && ExName.size() > 0)
							{
								for (Iterator iterator1 = ExName.iterator(); iterator1.hasNext();)
								{
									Object[] object11 = (Object[]) iterator1.next();
									if (object11[0] != null && object11[0] != null)
									{

										exe = object11[1].toString();
									}
								}
							}

						}
					}
				}
				// for dd executive
				data = cbt.executeAllSelectQuery("select id, " + "name from pull_report_t2m group By name order by name  ASC ", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							consultantMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
						}
					}
				}
				// for keyword dd in header

				keywrd = cbt.executeAllSelectQuery("SELECT incomingKeyword FROM pull_report_t2m group by incomingKeyword ORDER BY incomingKeyword  ASC ".toString(), connectionSpace);

				setFromDate(DateUtil.getNextDateAfter(-7));

				setToDate(DateUtil.getCurrentDateUSFormat());
				String query = "SELECT COUNT(*) FROM pull_report_t2m where date between '" + getFromDate() + "' and '" + getToDate() + "'";
				kw = countRecord(query.toString());
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

	// for manual entry page call

	public String beforePullReportLodge()
	{
		serviceConsList = new LinkedHashMap<Integer, String>();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List data = cbt.executeAllSelectQuery("select id, empName from consultants ", connectionSpace);
		if (data != null && data.size() > 0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();)
			{
				Object[] object1 = (Object[]) iterator.next();
				serviceConsList.put((Integer) object1[0], object1[1].toString());
			}
		}
		setComplaintPageFields();
		return SUCCESS;
	}

	public void setComplaintPageFields()
	{
		try
		{

			pageFieldsColumns = new ArrayList<ConfigurationUtilBean>();
			ConfigurationUtilBean obj;
			List<GridDataPropertyView> feedLodgeTags = Configuration.getConfigurationData("mapped_pullreport_configuration", accountID, connectionSpace, "", 0, "table_name", "pullreport_configuration");
			// System.out.println("size "+feedLodgeTags.size());
			if (feedLodgeTags != null && feedLodgeTags.size() > 0)
			{
				for (GridDataPropertyView gdv : feedLodgeTags)
				{
					obj = new ConfigurationUtilBean();
					// System.out.println("gdv.getColomnName() history+ "+gdv.getHeadingName());

					obj.setKey(gdv.getColomnName());
					obj.setValue(gdv.getHeadingName());
					obj.setValidationType(gdv.getValidationType());
					obj.setColType(gdv.getColType());
					if (gdv.getMandatroy().toString().equals("1"))
					{
						obj.setMandatory(true);
					} else
					{
						obj.setMandatory(false);
					}
					pageFieldsColumns.add(obj);

					// System.out.println("pageFieldsColumns   "+pageFieldsColumns);
				}

			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// for maual entry pull report
	public String pullReportMaualAdd()
	{

		SessionFactory connection = (SessionFactory) session.get("connectionSpace");
		String[] empdata = new KeyWordRecvModemForT2M().getEmpDetailsByMobileNo(getMobileNo(), connection);

		// System.out.println(getName()+"    "+getMobileNo()+"    "+getSpeciality()+"    "+getLocation());

		try
		{
			new T2mVirtualNoDataReceiver().insertIntoTable(empdata, connection, getMobileNo(), "Manual", getMsg());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addActionMessage("Message Registered Successfully!!!");
		return SUCCESS;
	}

	public String consDataGet()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// System.out.println("jjjj "+idCons);

				String userName = (String) session.get("uName");
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				if (userName == null || userName.equalsIgnoreCase(""))
					return LOGIN;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder queryCountry = new StringBuilder("");
				consData = new LinkedHashMap<String, String>();
				List<Object> Listhb1 = new ArrayList<Object>();
				List<Object> Listhb2 = new ArrayList<Object>();
				queryCountry.append("select cons.mobOne, loc.location, spc.speciality from consultants as cons inner join location_t2m as loc on cons.location=loc.id inner join speciality as spc on cons.speciality=spc.id where cons.id='" + idCons + "'");
				// System.out.println("queryCountry  >>   "+queryCountry.toString());
				List dataCntry = cbt.executeAllSelectQuery(queryCountry.toString(), connectionSpace);
				if (dataCntry != null && dataCntry.size() > 0)
				{
					for (Iterator iterator = dataCntry.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						consData.put("mob", object[0].toString());
						consData.put("loc", object[1].toString());
						consData.put("spc", object[2].toString());
						commonJSONArray.add(consData);
						// System.out.println(commonJSONArray);
					}
				}
				returnResult = SUCCESS;

			} catch (Exception exp)
			{
				// log.error("Date : " + DateUtil.getCurrentDateIndianFormat() +
				// " Time: " + DateUtil.getCurrentTime() + " " +
				// "Problem in Compliance  method addTaskType of class " +
				// getClass(), exp);
				exp.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforePullReportViewHeaderManual()
	{

		if (ValidateSession.checkSession())
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				List<String> sourcemasterColname = new ArrayList<String>();
				sourcemasterColname.add("id");
				sourcemasterColname.add("keyword");
				List sourceMasterData = cbt.viewAllDataEitherSelectOrAll("configurekeyword", sourcemasterColname, connectionSpace);
				if (sourceMasterData != null)
				{
					uploadKRMap = new LinkedHashMap<String, String>();
					for (Object c : sourceMasterData)
					{
						Object[] dataC = (Object[]) c;
						uploadKRMap.put(dataC[0].toString(), dataC[1].toString());
					}
				}
				SpecialityMap = new LinkedHashMap<Integer, String>();
				locationMap = new LinkedHashMap<Integer, String>();
				consultantMap = new LinkedHashMap<Integer, String>();
				// ExcutiveMap1 = new LinkedHashMap<Integer, String>();
				List data = cbt.executeAllSelectQuery("select id, " + "speciality from speciality order by speciality  ASC ", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							SpecialityMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
						}
					}
				}
				data.clear();
				data = cbt.executeAllSelectQuery("select id, " + "location from location_t2m order by location  ASC", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							locationMap.put(Integer.parseInt(object[0].toString()), object[1].toString());

						}
					}
				}
				// for consultants show in header DD
				data = cbt.executeAllSelectQuery("select excecutive, name from pull_report_t2m group By name order by name  ASC ", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[0] != null)
						{
							List ExName = cbt.executeAllSelectQuery("select id, empName from employee_basic where id='" + object[0] + "'", connectionSpace);
							// //("ExName " + ExName.size());
							if (ExName != null && ExName.size() > 0)
							{
								for (Iterator iterator1 = ExName.iterator(); iterator1.hasNext();)
								{
									Object[] object11 = (Object[]) iterator1.next();
									if (object11[0] != null && object11[0] != null)
									{

										exe = object11[1].toString();
									}
								}
							}

						}
					}
				}
				// for dd executive
				data = cbt.executeAllSelectQuery("select id, " + "name from pull_report_t2m group By name order by name  ASC ", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Iterator iterator = data.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						if (object[0] != null && object[1] != null)
						{
							consultantMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
						}
					}
				}
				// for keyword dd in header

				keywrd = cbt.executeAllSelectQuery("SELECT incomingKeyword FROM pull_report_t2m group by incomingKeyword ORDER BY incomingKeyword  ASC ".toString(), connectionSpace);

				setFromDate(DateUtil.getNextDateAfter(-7));

				setToDate(DateUtil.getCurrentDateUSFormat());
				String query = "SELECT COUNT(*) FROM pull_report_t2m where date between '" + getFromDate() + "' and '" + getToDate() + "'";
				kw = countRecord(query.toString());
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

	// function for count
	public Integer countRecord(String query)
	{
		BigInteger totalRecord = new BigInteger("3");
		List listData = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		listData = cbt.executeAllSelectQuery(query, connectionSpace);
		if (listData != null)
		{
			for (Iterator iterator = listData.iterator(); iterator.hasNext();)
			{

				totalRecord = (BigInteger) iterator.next();
			}
		}

		return totalRecord.intValue();
	}

	public String beforePullReportView()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			
			viewPageColumns = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Ticket ID");
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("history");
			gpv.setHeadingName("Ticket ID");
			gpv.setHideOrShow("false");
			gpv.setFormatter("takeAction");
			gpv.setWidth(50);
			viewPageColumns.add(gpv);
			

			gpv = new GridDataPropertyView();
			gpv.setColomnName("date");
			gpv.setHeadingName("Open Date/Time");
			gpv.setHideOrShow("false");
			gpv.setWidth(150);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("incomingKeyword");
			gpv.setHeadingName("TAT");
			gpv.setHideOrShow("false");
			gpv.setWidth(105);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("mobileNo");
			gpv.setHeadingName("Status");
			gpv.setHideOrShow("false");
			gpv.setWidth(120);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("excecutive");
			gpv.setHeadingName("Level");
			gpv.setHideOrShow("false");
			gpv.setWidth(105);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("UHID");
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Bed No");
			gpv.setHideOrShow("false");
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("name");
			gpv.setHeadingName("Ref Doc");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("name");
			gpv.setHeadingName("Referred Doc");
			gpv.setHideOrShow("false");
			gpv.setWidth(130);
			viewPageColumns.add(gpv);
			
			gpv = new GridDataPropertyView();
			gpv.setColomnName("speciality");
			gpv.setHeadingName("Ref. Doc Spec");
			gpv.setHideOrShow("false");
			gpv.setWidth(140);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("priority");
			gpv.setHeadingName("Priority");
			gpv.setHideOrShow("false");
			gpv.setWidth(140);
			viewPageColumns.add(gpv);

			gpv = new GridDataPropertyView();
			gpv.setColomnName("autoAck");
			gpv.setHeadingName("Reason");
			gpv.setHideOrShow("false");
			// gpv.setFormatter("viewAutoAck");
			gpv.setWidth(340);
			viewPageColumns.add(gpv);

			

			/*
			 * gpv = new GridDataPropertyView(); gpv.setColomnName("mail_flag");
			 * gpv.setHeadingName("Mail"); gpv.setHideOrShow("false");
			 * gpv.setWidth(40); gpv.setFormatter("resendSMS");
			 * viewPageColumns.add(gpv);
			 */

			/*
			 * gpv = new GridDataPropertyView(); gpv.setColomnName("history");
			 * gpv.setHeadingName("History"); gpv.setHideOrShow("false");
			 * gpv.setFormatter("viewHistory"); gpv.setWidth(50);
			 * viewPageColumns.add(gpv);
			 */

			// //("viewPageColumns  " + viewPageColumns.size());
			/*
			 * fromDate=DateUtil.getNextDateAfter(-7);
			 * 
			 * toDate=DateUtil.getNextDateAfter(-7);
			 * 
			 * setbeforePullReportView();
			 */

		} catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}

	// ////

	// ////
	private void setbeforePullReportView()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			GridDataPropertyView gpv = new GridDataPropertyView();
			// gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewPullReportGrid.add(gpv);

			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_pullreport_configuration", accountID, connectionSpace, "", 0, "table_name", "pullreport_configuration");
			for (GridDataPropertyView gdp : msg)
			{

				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					viewPullReportGrid.add(gpv);
				}
			}

			viewPullReportGrid1 = new ArrayList<GridDataPropertyView>();
			GridDataPropertyView gpv1 = new GridDataPropertyView();
			gpv1.setColomnName("id");
			gpv1.setHeadingName("Id");
			gpv1.setHideOrShow("true");
			viewPullReportGrid1.add(gpv1);

			gpv1 = new GridDataPropertyView();
			gpv1.setColomnName("history");
			gpv1.setHeadingName("History");
			gpv1.setFormatter("viewHistory");
			gpv1.setHideOrShow("false");
			gpv1.setWidth(50);
			viewPullReportGrid1.add(gpv1);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String viewLeavePullReport()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			// //(" ViewEmfgfgailSetting      ");
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				List data = null;
				query1.append("select count(*) from instant_msg as ins  ");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("1");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of column

					query1 = new StringBuilder();
					query1.append("  Select ins.id,ins.empName,ins.mobno,ins.IncomingSms,ins.reason,ins.description,ins.InComingDate,ins.InComingTime from leave_sms_Report as ins  ");
					/*
					 * if (getTdate()!=null && getFdate()!=null) {
					 * query1.append("where  ins.InComingDate between '" +
					 * getFdate() + "' AND '" + getTdate() + "'"); }
					 * 
					 * if (getFieldser() != null &&
					 * !getFieldser().equalsIgnoreCase("-1") && getFieldval() !=
					 * null && !getFieldval().equalsIgnoreCase("-1")) {
					 * query1.append(" where ins. " + getFieldser() + " like '%"
					 * + getFieldval() + "%'"); ////("query1 ??????????   " +
					 * query1); }
					 */
					query1.append("  group by ins.id asc ");
					data = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
					// //("query1 ??????????   " + query1);
					// //("data >>>" + data);
					if (data != null && data.size() > 0)
					{
						List<String> fieldNames = new ArrayList<String>();
						fieldNames.add("id");
						fieldNames.add("empName");
						fieldNames.add("mobileNo");
						fieldNames.add("keyword");
						fieldNames.add("reason");
						fieldNames.add("description");
						fieldNames.add("date");
						fieldNames.add("time");
						// //("fieldNames  :::: " + fieldNames);
						// viewLeavePullData = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
						Object[] obdata1 = null;
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							obdata1 = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata1[k].equals("-1"))
								{
									one.put(fieldNames.get(k).toString(), "NA");
								} else
								{
									if (k == 0)
										one.put(fieldNames.get(k).toString(), (Integer) obdata1[k]);
									else
									{
										one.put(fieldNames.get(k).toString(), obdata1[k].toString());
									}
								}
							}
							Listhb.add(one);
						}
						// setViewLeavePullData(Listhb);
						// //("fieldNames  :::: " + fieldNames);
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String beforeSendMailPullReportView()
	{
		try
		{
			String userName = (String) session.get("uName");
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;

			setSendMailPullReportView();

		} catch (Exception e)
		{

			e.printStackTrace();
		}
		return SUCCESS;
	}

	private void setSendMailPullReportView()
	{
		try
		{
			String accountID = (String) session.get("accountid");
			SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
			GridDataPropertyView gpv = new GridDataPropertyView();
			gpv.setColomnName("id");
			gpv.setHeadingName("Id");
			gpv.setHideOrShow("true");
			viewSendPullReportGrid.add(gpv);

			List<GridDataPropertyView> msg = Configuration.getConfigurationData("mapped_sendemaildata_configuration", accountID, connectionSpace, "", 0, "table_name", "sendemaildata_configuration");
			for (GridDataPropertyView gdp : msg)
			{

				{
					gpv = new GridDataPropertyView();
					gpv.setColomnName(gdp.getColomnName());
					gpv.setHeadingName(gdp.getHeadingName());
					gpv.setEditable(gdp.getEditable());
					gpv.setSearch(gdp.getSearch());
					gpv.setHideOrShow(gdp.getHideOrShow());
					gpv.setWidth(gdp.getWidth());
					gpv.setAlign(gdp.getAlign());
					viewSendPullReportGrid.add(gpv);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String viewSendMailPullReport()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			// //(" ViewEmfgfgailSetting      ");
			try
			{
				String accountID = (String) session.get("accountid");
				SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from sendemaildata");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(), connectionSpace);
				if (dataCount != null && dataCount.size() > 0)
				{
					BigInteger count = new BigInteger("1");
					for (Iterator it = dataCount.iterator(); it.hasNext();)
					{
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of column
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList("mapped_sendemaildata_configuration", accountID, connectionSpace, "sendemaildata_configuration");
					int i = 0;
					if (fieldNames != null && fieldNames.size() > 0)
					{
						for (Iterator it = fieldNames.iterator(); it.hasNext();)
						{
							// generating the dynamic query based on selected
							// fields
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < fieldNames.size() - 1)
									query.append(obdata.toString() + ",");
								else
									query.append(obdata.toString());
							}
							i++;
						}
					}
					query.append(" from sendemaildata  ");
					// //(" query bvb    " + query);
					if (getSearchField() != null && getSearchString() != null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase(""))
					{
						query.append(" where ");
						// add search query based on the search operation
						if (getSearchOper().equalsIgnoreCase("eq"))
						{
							query.append(" " + getSearchField() + " = '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw"))
						{
							query.append(" " + getSearchField() + " like '" + getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne"))
						{
							query.append(" " + getSearchField() + " <> '" + getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew"))
						{
							query.append(" " + getSearchField() + " like '%" + getSearchString() + "'");
						}
					}

					// //("@@@@@@ " + query.toString());
					List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

					if (data != null && data.size() > 0)
					{
						gridDataModel = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
						for (Iterator it = data.iterator(); it.hasNext();)
						{
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < fieldNames.size(); k++)
							{
								if (obdata[k] != null)
								{
									// ("fieldNames.get(k).toString()      " +
									// fieldNames.get(k).toString());
									if (k == 0)
									{
										one.put(fieldNames.get(k).toString(), (Integer) obdata[k]);
									} else
									{
										if (fieldNames.get(k).toString().equalsIgnoreCase("date"))

											one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
										else
											one.put(fieldNames.get(k).toString(), obdata[k].toString());
									}
								} else
								{
									one.put(fieldNames.get(k).toString(), "NA");
								}
							}
							Listhb.add(one);
						}
						setGridDataModel(Listhb);
						// //("fggfg   " +
						// gridDataModel.size());
						setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					}
				}
				returnResult = SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String exclelColumnNameGet()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				returnResult = getColumnName1("mapped_pullreport_configuration", "pullreport_configuration", "pull");
				tableName = "pull_report_t2m";
				excelName = "Pull Report Details";

				returnResult = SUCCESS;
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		// (returnResult);
		return returnResult;
	}

	public String getColumnName1(String mappedTableName, String basicTableName, String allias)
	{
		try
		{
			List<GridDataPropertyView> columnList = Configuration.getConfigurationData(mappedTableName, accountID, connectionSpace, "", 0, "table_name", basicTableName);
			columnMap = new LinkedHashMap<String, String>();
			if (columnList != null && columnList.size() > 0)
			{
				for (GridDataPropertyView gdp : columnList)
				{

					if (!gdp.getColomnName().equals("msg") && !gdp.getColomnName().equals("reminder1") && !gdp.getColomnName().equals("compid_suffix"))
					{
						columnMap.put(gdp.getColomnName(), gdp.getHeadingName());
						// //System.out.println("columnMap "+columnMap);
					}
				}
			}
			// //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " +
			// columnMap);
			if (columnMap != null && columnMap.size() > 0)
			{
				session.put("columnMap", columnMap);
			}
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	// final download excel sheet
	public String downloadExcel()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();

		if (sessionFlag)
		{
			try
			{
				CommunicationHelper cmnHelper = new CommunicationHelper();
				List keyList = new ArrayList();
				List titleList = new ArrayList();
				StringBuilder emailIds = null;
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if (columnNames != null && columnNames.length > 0)
				{
					keyList = Arrays.asList(columnNames);
					Map<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap = (Map<String, String>) session.get("columnMap");
					if (session.containsKey("columnMap"))
						session.remove("columnMap");

					List dataList = null;
					StringBuilder query = new StringBuilder("");
					for (int index = 0; index < keyList.size(); index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
					if (keyList != null && keyList.size() > 0)
					{
						query.append("SELECT ");
						int i = 0;
						for (Iterator it = keyList.iterator(); it.hasNext();)
						{
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < keyList.size() - 1)
								{
									query.append(obdata.toString() + ",");
								} else
								{
									if (obdata.toString().equalsIgnoreCase("comp.compid_prefix"))
									{
										query.append(obdata.toString() + ",comp.compid_suffix");
									} else
									{
										query.append(obdata.toString());
									}
								}
							}
							i++;
						}

						// fromDate122 toDate122 location cons kws
						query.append(" FROM " + tableName + " ");
						query.append(" where date between '" + DateUtil.convertDateToUSFormat(fromDate122) + "' and '" + DateUtil.convertDateToUSFormat(toDate122) + "'");
						if (cons != "-1" && cons.length() != 0)
						{
							StringBuilder query2 = new StringBuilder("");
							query2 = new StringBuilder();
							query2.append("select name, id from pull_report_t2m where id ='" + cons + "'");
							List data1 = cbt.executeAllSelectQuery(query2.toString(), connectionSpace);
							List<Object> ListName = new ArrayList<Object>();
							for (Iterator it = data1.iterator(); it.hasNext();)
							{
								Object[] obdata = (Object[]) it.next();
								query.append(" and  name = '" + obdata[0].toString() + "'");

							}
						}

						if (!kws.equalsIgnoreCase("-1"))
						{

							query.append(" and incomingKeyword = '" + kws + "'");
						}
						// //////System.out.println("query " + query);

						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						String orgDetail = (String) session.get("orgDetail");
						String[] orgData = null;
						String orgName = "blk super speciality hospital";
						if (orgDetail != null && !orgDetail.equals(""))
						{
							orgData = orgDetail.split("#");
							orgName = orgData[0];
						}

						excelFileName = new ComplianceExcelDownload().writeDataInExcel(dataList, titleList, keyList, excelName, orgName, false, connectionSpace);

						File file = new File(excelFileName);
						if (excelFileName != null)
						{
							setExcelStream(new FileInputStream(file));
						}
						excelFileName = excelFileName.substring(excelFileName.lastIndexOf("//") + 2, excelFileName.length());
						excelFileName = file.getName();

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

	// for send mail

	public String beforeSendMail()
	{

		try
		{
			allDeptName1 = new LinkedHashMap<String, String>();
			String query = "SELECT id,deptName FROM department WHERE flag=0 ORDER BY deptName";
			List dataList = new createTable().executeAllSelectQuery(query, connectionSpace);
			// ////////System.out.println("dept list is : " + dataList);
			if (dataList != null && dataList.size() > 0)
			{
				// ////////System.out.println("dept name " + allDeptName1);
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					// ////////System.out.println("dept name1 " + allDeptName1);
					Object[] object = (Object[]) iterator.next();
					if (object[0] != null && object[1] != null)
					{
						// ////////System.out.println("dept name2 " +
						// allDeptName1);
						// ////////System.out.println("hsakh+   " +
						// object[0].toString()
						// + "    and     " + object[1].toString());
						allDeptName1.put(object[0].toString(), object[1].toString());
						// ////////System.out.println("dept name3 " +
						// allDeptName1);
						// d1.put(object[0].toString(), object[1].toString());
					}
				}
			}
			return SUCCESS;
		} catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}

	}

	// get employee for send mail
	public String empGetbyDept4Mail()
	{
		boolean valid = ValidateSession.checkSession();
		if (valid)
		{
			try
			{
				List data = new createTable().executeAllSelectQuery(" SELECT emp.id,emp.emailIdOne,emp.empName,emp.deptname FROM compliance_contacts AS ctm INNER JOIN employee_basic AS emp ON emp.id=ctm.emp_id WHERE   emp.deptname =" + getDept() + "", connectionSpace);
				if (data != null && data.size() > 0)
				{
					for (Object c : data)
					{
						Object[] objVar = (Object[]) c;
						JSONObject listObject = new JSONObject();
						listObject.put("id", objVar[0].toString());
						listObject.put("name", objVar[2].toString() + " / " + objVar[1].toString());
						commonJSONArray.add(listObject);
						// //////System.out.println("commonJSONArray " +
						// commonJSONArray);
					}
				}
				return SUCCESS;
			} catch (Exception e)
			{
				e.printStackTrace();
				// log.error("Date : " + DateUtil.getCurrentDateIndianFormat() +
				// " Time: " + DateUtil.getCurrentTime() + " " +
				// "Problem in Over2Cloud  method getMappedContacts of class " +
				// getClass(), e);
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	}

	//
	// for deshboard
	@SuppressWarnings("rawtypes")
	public String getAllTypeGraph()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			name = "manab";

			return SUCCESS;
		} else
		{
			return LOGIN;
		}
	}

	public String refMapDataget()
	{

		/**
		 * zoomLevel: 5, scale: 0.5, title: "Madrid", latitude: 40.4167,
		 * longitude: -3.7033
		 */

		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			// //////System.out.println("Date: "+fromDate122+"       "+toDate122);

			query.append("select  l.location, l.latitude, l.longitude from pull_report_t2m as p");
			query.append(" inner join location_t2m as l on p.location=l.id group by l.location");

			// System.out.println("QQQQQQ"+query);
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

			jsonObject = new JSONObject();
			Object[] obdata1 = null;
			for (Iterator it = list.iterator(); it.hasNext();)
			{
				obdata1 = (Object[]) it.next();
				jsonObject.put("zoomLevel", "5");
				jsonObject.put("scale", "0.5");
				jsonObject.put("title", obdata1[0].toString());
				jsonObject.put("latitude", obdata1[1].toString());
				jsonObject.put("longitude", obdata1[2].toString());
			}

			RefMapSource.add(jsonObject);
			// System.out.println("RefMapSource "+RefMapSource);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String referal_dashboard_view()
	{
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String getPieChart4Executivekeyword()
	{

		boolean validSession = ValidateSession.checkSession();
		if (validSession)
		{
			try
			{
				List list = getAnalyticalGridReport();
				catgCountList = new ArrayList<FeedbackPojo>();
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					FeedbackPojo fp = new FeedbackPojo();
					fp.setId(Integer.parseInt(object[4].toString()));
					fp.setFeedback_catg(object[1].toString());
					fp.setCounter(object[3].toString());
					catgCountList.add(fp);

				}
				jsonObject = new JSONObject();
				jsonArray = new JSONArray();

				for (FeedbackPojo pojo : catgCountList)
				{
					jsonObject.put("Category", pojo.getFeedback_catg());
					jsonObject.put("Counter", pojo.getCounter());

					jsonArray.add(jsonObject);
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

	@SuppressWarnings("unchecked")
	public String getTable4Executivekeyword()
	{
		dashObj = new DashboardPojo();
		DashboardPojo DP = null;
		try
		{
			dept_subdeptcounterList = new ArrayList<DashboardPojo>();
			List dept_subdeptNameList = getAnalyticalGridReport();
			List dept_subdeptCounterList = new ArrayList();

			if (dept_subdeptNameList != null && dept_subdeptNameList.size() > 0)
			{
				for (Iterator iterator = dept_subdeptNameList.iterator(); iterator.hasNext();)
				{
					Object[] object1 = (Object[]) iterator.next();
					DP = new DashboardPojo();
					DP.setId(object1[4].toString());
					DP.setDeptName(object1[1].toString());
					DP.setHpc(object1[3].toString());

					dept_subdeptcounterList.add(DP);
					// ////System.out.println("size dept_subdeptcounterList "+dept_subdeptcounterList.size());
					dashObj.setDashList(dept_subdeptcounterList);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String beforeDashboardAction()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				// this.generalMethod();

				// ********************************************//
				// Scrolling Data(First Block)
				// getTicketDetail(loginType,dept_id,empName, connectionSpace);
				// ********************************************//

				// ********************************************//
				// Ticket Counters on the basis of status(Second Block)
				getPieChart4Executivekeyword();
				getTable4Executivekeyword();
				// ********************************************//

				// ********************************************//
				// Level wise Ticket Detail in Table (Fifth Block)
				// getTicketDetailByLevel("T");

				// Level wise Ticket Detail in Graph (Fifth Block)
				// getTicketDetailByLevel("G");
				// ********************************************//

				// ********************************************//
				// Analytics for category in table (Third Block)
				// getAnalytics4SubCategory("T");

				// Analytics for category in graph (Third Block)
				// getAnalytics4SubCategory("G");
				// ********************************************//

				// getDataInBars(connectionSpace);
				returnResult = SUCCESS;
			} catch (Exception e)
			{
				addActionError("Ooops There Is Some Problem In beforeDashboardAction in DashboardAction !!!");
				e.printStackTrace();
				returnResult = ERROR;
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public List getAnalyticalGridReport()
	{
		List list = new ArrayList();
		StringBuilder query = new StringBuilder("");
		try
		{
			// //////System.out.println("Date: "+fromDate122+"       "+toDate122);

			query.append("select pr.incomingKeyword,emp.empName,emp.mobOne,count(*) as counter,emp.id,pr.location  from pull_report_t2m as pr");
			query.append(" inner join employee_basic emp on pr.mobileNo= emp.mobOne");
			query.append(" inner join location_t2m loc on loc.id= pr.location");

			query.append(" where pr.date between '" + DateUtil.convertDateToUSFormat(fromDate122) + "' and '" + DateUtil.convertDateToUSFormat(toDate122) + "' ");

			query.append(" group by emp.empName order by counter desc");

			// ////System.out.println("QQQQQQ"+query);
			list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	//	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public Map<String, String> getUploadKRMap()
	{
		return uploadKRMap;
	}

	public void setUploadKRMap(Map<String, String> uploadKRMap)
	{
		this.uploadKRMap = uploadKRMap;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public List<Parent> getParentTakeaction()
	{
		return parentTakeaction;
	}

	public void setParentTakeaction(List<Parent> parentTakeaction)
	{
		this.parentTakeaction = parentTakeaction;
	}

	public String getEmailId()
	{
		return emailId;
	}

	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}

	public String getEmailText()
	{
		return emailText;
	}

	public void setEmailText(String emailText)
	{
		this.emailText = emailText;
	}

	public String getEmailID()
	{
		return emailID;
	}

	public void setEmailID(String emailID)
	{
		this.emailID = emailID;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getServer()
	{
		return server;
	}

	public void setServer(String server)
	{
		this.server = server;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port = port;
	}

	public String[] getColumnNames()
	{
		return columnNames;
	}

	public void setColumnNames(String[] columnNames)
	{
		this.columnNames = columnNames;
	}

	public String getDownloadType()
	{
		return downloadType;
	}

	public void setDownloadType(String downloadType)
	{
		this.downloadType = downloadType;
	}

	public String getDownload4()
	{
		return download4;
	}

	public void setDownload4(String download4)
	{
		this.download4 = download4;
	}

	public Map<String, String> getColumnMap()
	{
		return columnMap;
	}

	public void setColumnMap(Map<String, String> columnMap)
	{
		this.columnMap = columnMap;
	}

	public Map<String, String> getColumnMap1()
	{
		return columnMap1;
	}

	public void setColumnMap1(Map<String, String> columnMap1)
	{
		this.columnMap1 = columnMap1;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getTableAllis()
	{
		return tableAllis;
	}

	public void setTableAllis(String tableAllis)
	{
		this.tableAllis = tableAllis;
	}

	public String getExcelName()
	{
		return excelName;
	}

	public void setExcelName(String excelName)
	{
		this.excelName = excelName;
	}

	public String getMainHeaderName()
	{
		return mainHeaderName;
	}

	public void setMainHeaderName(String mainHeaderName)
	{
		this.mainHeaderName = mainHeaderName;
	}

	public List<GridDataPropertyView> getViewPullReportGrid()
	{
		return viewPullReportGrid;
	}

	public void setViewPullReportGrid(List<GridDataPropertyView> viewPullReportGrid)
	{
		this.viewPullReportGrid = viewPullReportGrid;
	}

	public List<GridDataPropertyView> getViewSendPullReportGrid()
	{
		return viewSendPullReportGrid;
	}

	public void setViewSendPullReportGrid(List<GridDataPropertyView> viewSendPullReportGrid)
	{
		this.viewSendPullReportGrid = viewSendPullReportGrid;
	}

	public Integer getRows()
	{
		return rows;
	}

	public void setRows(Integer rows)
	{
		this.rows = rows;
	}

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public String getSord()
	{
		return sord;
	}

	public void setSord(String sord)
	{
		this.sord = sord;
	}

	public String getSidx()
	{
		return sidx;
	}

	public void setSidx(String sidx)
	{
		this.sidx = sidx;
	}

	public String getSearchField()
	{
		return searchField;
	}

	public void setSearchField(String searchField)
	{
		this.searchField = searchField;
	}

	public String getSearchString()
	{
		return searchString;
	}

	public void setSearchString(String searchString)
	{
		this.searchString = searchString;
	}

	public String getSearchOper()
	{
		return searchOper;
	}

	public void setSearchOper(String searchOper)
	{
		this.searchOper = searchOper;
	}

	public Integer getTotal()
	{
		return total;
	}

	public void setTotal(Integer total)
	{
		this.total = total;
	}

	public Integer getRecords()
	{
		return records;
	}

	public void setRecords(Integer records)
	{
		this.records = records;
	}

	public boolean isLoadonce()
	{
		return loadonce;
	}

	public void setLoadonce(boolean loadonce)
	{
		this.loadonce = loadonce;
	}

	public String getOper()
	{
		return oper;
	}

	public void setOper(String oper)
	{
		this.oper = oper;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<Object> getGridDataModel()
	{
		return gridDataModel;
	}

	public void setGridDataModel(List<Object> gridDataModel)
	{
		this.gridDataModel = gridDataModel;
	}

	public List<Object> getViewPullData()
	{
		return viewPullData;
	}

	public void setViewPullData(List<Object> viewPullData)
	{
		this.viewPullData = viewPullData;
	}

	public String getToDate()
	{
		return toDate;
	}

	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}

	public String getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}

	public void setType2(String type2)
	{
		this.type2 = type2;
	}

	public String getType2()
	{
		return type2;
	}

	public Map<Integer, String> getLocationMap()
	{
		return locationMap;
	}

	public void setLocationMap(Map<Integer, String> locationMap)
	{
		this.locationMap = locationMap;
	}

	public Map<Integer, String> getSpecialityMap()
	{
		return SpecialityMap;
	}

	public void setSpecialityMap(Map<Integer, String> specialityMap)
	{
		SpecialityMap = specialityMap;
	}

	public Map<Integer, String> getConsultantMap()
	{
		return consultantMap;
	}

	public void setConsultantMap(Map<Integer, String> consultantMap)
	{
		this.consultantMap = consultantMap;
	}

	public String getFromDate12()
	{
		return fromDate12;
	}

	public void setFromDate12(String fromDate12)
	{
		this.fromDate12 = fromDate12;
	}

	public String getToDate12()
	{
		return toDate12;
	}

	public void setToDate12(String toDate12)
	{
		this.toDate12 = toDate12;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getSpc()
	{
		return spc;
	}

	public void setSpc(String spc)
	{
		this.spc = spc;
	}

	public String getCons()
	{
		return cons;
	}

	public void setCons(String cons)
	{
		this.cons = cons;
	}

	public List getKeywrd()
	{
		return keywrd;
	}

	public void setKeywrd(List keywrd)
	{
		this.keywrd = keywrd;
	}

	public int getsCounter()
	{
		return sCounter;
	}

	public void setsCounter(int sCounter)
	{
		this.sCounter = sCounter;
	}

	public int getdCounter()
	{
		return dCounter;
	}

	public void setdCounter(int dCounter)
	{
		this.dCounter = dCounter;
	}

	public Integer getKw()
	{
		return kw;
	}

	public void setKw(Integer kw)
	{
		this.kw = kw;
	}

	public String getC()
	{
		return c;
	}

	public void setC(String c)
	{
		this.c = c;
	}

	public String getFromDate122()
	{
		return fromDate122;
	}

	public void setFromDate122(String fromDate122)
	{
		this.fromDate122 = fromDate122;
	}

	public String getToDate122()
	{
		return toDate122;
	}

	public void setToDate122(String toDate122)
	{
		this.toDate122 = toDate122;
	}

	public int getA1()
	{
		return a1;
	}

	public void setA1(int a1)
	{
		this.a1 = a1;
	}

	public String getKws()
	{
		return kws;
	}

	public void setKws(String kws)
	{
		this.kws = kws;
	}

	public String getExe()
	{
		return exe;
	}

	public void setExe(String exe)
	{
		this.exe = exe;
	}

	public String getChExc()
	{
		return chExc;
	}

	public void setChExc(String chExc)
	{
		this.chExc = chExc;
	}

	public List<GridDataPropertyView> getViewPullReportGrid1()
	{
		return viewPullReportGrid1;
	}

	public void setViewPullReportGrid1(List<GridDataPropertyView> viewPullReportGrid1)
	{
		this.viewPullReportGrid1 = viewPullReportGrid1;
	}

	public String getmNoh()
	{
		return mNoh;
	}

	public void setmNoh(String mNoh)
	{
		this.mNoh = mNoh;
	}

	public String getKwh()
	{
		return kwh;
	}

	public void setKwh(String kwh)
	{
		this.kwh = kwh;
	}

	public String getIdH()
	{
		return idH;
	}

	public void setIdH(String idH)
	{
		this.idH = idH;
	}

	public Map<String, Object> getOneH()
	{
		return oneH;
	}

	public void setOneH(Map<String, Object> oneH)
	{
		this.oneH = oneH;
	}

	public String getIdv()
	{
		return idv;
	}

	public void setIdv(String idv)
	{
		this.idv = idv;
	}

	public List<Object> getGridModelHis()
	{
		return gridModelHis;
	}

	public void setGridModelHis(List<Object> gridModelHis)
	{
		this.gridModelHis = gridModelHis;
	}

	public List<Object> getGridModelHis1()
	{
		return gridModelHis1;
	}

	public void setGridModelHis1(List<Object> gridModelHis1)
	{
		this.gridModelHis1 = gridModelHis1;
	}

	public String getIdA()
	{
		return idA;
	}

	public void setIdA(String idA)
	{
		this.idA = idA;
	}

	public String getAutoAckMassage()
	{
		return autoAckMassage;
	}

	public void setAutoAckMassage(String autoAckMassage)
	{
		this.autoAckMassage = autoAckMassage;
	}

	public String getExcelFileName()
	{
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName)
	{
		this.excelFileName = excelFileName;
	}

	public FileInputStream getExcelStream()
	{
		return excelStream;
	}

	public void setExcelStream(FileInputStream excelStream)
	{
		this.excelStream = excelStream;
	}

	public Map<String, Integer> getGraphMap()
	{
		return graphMap;
	}

	public void setGraphMap(Map<String, Integer> graphMap)
	{
		this.graphMap = graphMap;
	}

	public List<DashboardPojo> getDept_subdeptcounterList()
	{
		return dept_subdeptcounterList;
	}

	public void setDept_subdeptcounterList(List<DashboardPojo> dept_subdeptcounterList)
	{
		this.dept_subdeptcounterList = dept_subdeptcounterList;
	}

	public JSONArray getJsonArray()
	{
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}

	public JSONObject getJsonObject()
	{
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}

	public Map<String, Integer> getGraphCatgMap()
	{
		return graphCatgMap;
	}

	public void setGraphCatgMap(Map<String, Integer> graphCatgMap)
	{
		this.graphCatgMap = graphCatgMap;
	}

	public List<FeedbackPojo> getCatgCountList()
	{
		return catgCountList;
	}

	public void setCatgCountList(List<FeedbackPojo> catgCountList)
	{
		this.catgCountList = catgCountList;
	}

	public Map<String, String> getAllDeptName1()
	{
		return allDeptName1;
	}

	public void setAllDeptName1(Map<String, String> allDeptName1)
	{
		this.allDeptName1 = allDeptName1;
	}

	public void setDept(String dept)
	{
		this.dept = dept;
	}

	public String getDept()
	{
		return dept;
	}

	public void setCommonJSONArray(JSONArray commonJSONArray)
	{
		this.commonJSONArray = commonJSONArray;
	}

	public JSONArray getCommonJSONArray()
	{
		return commonJSONArray;
	}

	public void setEmp(String emp)
	{
		this.emp = emp;
	}

	public String getEmp()
	{
		return emp;
	}

	public DashboardPojo getDashObj()
	{
		return dashObj;
	}

	public void setDashObj(DashboardPojo dashObj)
	{
		this.dashObj = dashObj;
	}

	public Map<Integer, String> getServiceConsList()
	{
		return serviceConsList;
	}

	public void setServiceConsList(Map<Integer, String> serviceConsList)
	{
		this.serviceConsList = serviceConsList;
	}

	public void setIdCons(String idCons)
	{
		this.idCons = idCons;
	}

	public String getIdCons()
	{
		return idCons;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSpeciality()
	{
		return speciality;
	}

	public void setSpeciality(String speciality)
	{
		this.speciality = speciality;
	}

	public String getMobileNo()
	{
		return mobileNo;
	}

	public void setMobileNo(String mobileNo)
	{
		this.mobileNo = mobileNo;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getSts()
	{
		return sts;
	}

	public void setSts(String sts)
	{
		this.sts = sts;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getAutoAck()
	{
		return autoAck;
	}

	public void setAutoAck(String autoAck)
	{
		this.autoAck = autoAck;
	}

	public String getExcecutive()
	{
		return excecutive;
	}

	public void setExcecutive(String excecutive)
	{
		this.excecutive = excecutive;
	}

	public String getActionStatus()
	{
		return actionStatus;
	}

	public void setActionStatus(String actionStatus)
	{
		this.actionStatus = actionStatus;
	}

	public void setRefMapSource(JSONArray refMapSource)
	{
		RefMapSource = refMapSource;
	}

	public JSONArray getRefMapSource()
	{
		return RefMapSource;
	}

}