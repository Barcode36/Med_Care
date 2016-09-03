package com.Over2Cloud.ctrl.feedback.dashboard;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.FeedbackOverallSummaryBean;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.FeedbackDashboard;
import com.Over2Cloud.ctrl.feedback.beanUtil.FeedbackPojoDashboard;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalAction;
import com.Over2Cloud.modal.dao.imp.feedbackDashboard.FeedbackDashboardDao;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class FeedbackDashboardBarGraph extends ActionSupport 
{
/**
	 * 
	 */
	private static final long serialVersionUID = 2058382163048738300L;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private JSONArray jsonArray;


private String graphHeader;
private String graphDescription;
private String yAxisHeader;
private String xAxisHeader;
private String block;
private Map<String, Integer>  actionMapGraph;
private String fromDate=null,toDate=null;


private final static CommonOperInterface cbt=new CommonConFactory().createInterface();
//to be merged 31-10-2014
@SuppressWarnings("rawtypes")
public String showBarChartFeedback()
{
	if (ValidateSession.checkSession()) 
	{
		try
		{
			
			FeedbackDashboard FDB = new FeedbackDashboard();
			FDB.setBlock("block2");
			FDB.setFromDate(fromDate);
			FDB.setToDate(toDate);
			FDB.getblock1counter();
			JSONObject jsonObject = new JSONObject();
			jsonArray = new JSONArray();
		
			if(FDB.getSummaryList()!=null && FDB.getSummaryList().size()>0)
			{
				for (FeedbackOverallSummaryBean summary : FDB.getSummaryList() )
				{
					jsonObject.put("Mode",summary.getMode());
					jsonObject.put("Positive", summary.getPositive());
					jsonObject.put("Negative", summary.getNegative());
					jsonObject.put("Pending", summary.getPending());
					jsonObject.put("NoAct", summary.getNoFurtherAction());
					jsonObject.put("TicketOpened", summary.getTicketOpened());
					
					
					jsonArray.add(jsonObject);
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

public String showBarChart()
{
	if (ValidateSession.checkSession()) 
	{
		try
		{
			setGraphHeader("Department wise Ticket Status From "+DateUtil.convertDateToIndianFormat(DateUtil.getNextDateAfter(-30))+" to "+DateUtil.getCurrentDateIndianFormat());
			setYAxisHeader("Ticket Counters");
			setXAxisHeader("Ticket Counters");
			List<FeedbackPojoDashboard> dashPojoList=new ArrayList<FeedbackPojoDashboard>();
			Map<String,Object> session=ActionContext.getContext().getSession();
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			if(fromDate!=null||!fromDate.equalsIgnoreCase(" ")&&toDate!=null||!toDate.equalsIgnoreCase(" ")){
						dashPojoList=new FeedbackDashboardDao().getDashboardTicketsDataShow(connectionSpace,"",false,false,fromDate,toDate);
						
					    JSONObject jsonObject = new JSONObject();
						jsonArray = new JSONArray();
						
						for (FeedbackPojoDashboard pojo : dashPojoList)
						{
							jsonObject.put("dept",pojo.getDeptName());
							jsonObject.put("Pending", pojo.getPending());
							jsonObject.put("High Priority", pojo.getHp());
							jsonObject.put("Snooze", pojo.getSz());
							jsonObject.put("Ignore", pojo.getIg());
							jsonObject.put("Re-Assign", pojo.getRAs());
							jsonObject.put("Resolved", pojo.getResolved());
							jsonObject.put("Noted", pojo.getNoted());
							
							jsonObject.put("Level 1", pojo.getPendingL1());
							jsonObject.put("Level 2", pojo.getPendingL2());
							jsonObject.put("Level 3", pojo.getPendingL3());
							jsonObject.put("Level 4", pojo.getPendingL4());
							jsonObject.put("Level 5", pojo.getPendingL5());
							
							jsonArray.add(jsonObject);
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

@SuppressWarnings("rawtypes")
public String beforePieChartForFeedback()
{
	try
	{
		actionMapGraph = new LinkedHashMap<String, Integer>();
		Map<String,Object> session=ActionContext.getContext().getSession();
		SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		String loggedEmpId="";
		String dept_subdept_id="";
		String userName=(String)session.get("uName");
		String userType=(String)session.get("userTpe");
		String deptLevel = (String)session.get("userDeptLevel");
		
		List empData = new FeedbackUniversalAction().getEmpDataByUserName(Cryptography.encrypt(userName,DES_ENCRYPTION_KEY), deptLevel);
		if (empData!=null && empData.size()>0)
		{
			for (Iterator iterator = empData.iterator(); iterator.hasNext();) 
			{
				Object[] object = (Object[]) iterator.next();
				loggedEmpId=object[5].toString();
				dept_subdept_id = object[3].toString();
			}
		}
		List<String> deptId=new ArrayList<String>();
		FeedbackUniversalAction FUA=new FeedbackUniversalAction();
		String empIdLoggedUSer=(String) session.get("empIdofuser");
		boolean mFlag=false,hFlag=false,nFlag=false;
		if(userType!=null && userType.equalsIgnoreCase("M"))
		{
			mFlag=true;
		}
		else if(userType!=null && userType.equalsIgnoreCase("D"))
		{
			deptId = FUA.getLoggedInEmpForDept(empIdLoggedUSer.split("-")[1], "", "FM", connectionSpace);
			hFlag=true;
		}
		else
		{
			if(userName!=null && loggedEmpId!=null)
			{
				nFlag=true;
			}
		}
		
		List dataList=null;
		if (block!=null && block.equalsIgnoreCase("2ndBlock")) 
		{
			dataList=cbt.executeAllSelectQuery(" select distinct status,count(*) from feedbackdata WHERE status!='openTicket' group by status ORDER BY status DESC ", connectionSpace);
		}
		else if(block!=null && block.equalsIgnoreCase("7rdBlock"))
		{
			dataList=cbt.executeAllSelectQuery(" SELECT ft.fbType,COUNT(*) FROM feedback_status AS fs   INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' GROUP BY fbType", connectionSpace);
		}
		else if(block!=null && block.equalsIgnoreCase("4rdBlock"))
		{
			if(mFlag)
			{
				dataList=cbt.executeAllSelectQuery(" SELECT ft.fbType,COUNT(*) FROM feedback_status AS fs   INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' GROUP BY fbType", connectionSpace);
			}
			else if(hFlag)
			{
				dataList=cbt.executeAllSelectQuery(" SELECT ft.fbType,COUNT(*) FROM feedback_status AS fs   INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' AND fs.to_dept_subdept IN "+deptId.toString().replace("[", "(").replace("]",")")+" GROUP BY fbType", connectionSpace);
			}
			else if(nFlag)
			{
				StringBuilder builder=new StringBuilder("select ft.fbType,COUNT(*) FROM feedback_status AS fs" +
						"  INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg" +
						"  INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName" +
						"  INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' " +
						"  and (fs.feed_registerby='"+userName+ "' or fs.allot_to='"+loggedEmpId+"')" +
						"  GROUP BY fbType");
				dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
			}
		}
		else if(block!=null && block.equalsIgnoreCase("block5"))
		{
			if(mFlag)
			{
				dataList=cbt.executeAllSelectQuery(" SELECT fc.categoryName,COUNT(*) FROM feedback_status AS fs   INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' AND ft.fbType='Complaint' GROUP BY fc.categoryName", connectionSpace);
			//	dataList=cbt.executeAllSelectQuery(" SELECT ft.fbType,COUNT(*) FROM feedback_status AS fs   INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' GROUP BY fbType", connectionSpace);
			}
			else if(hFlag)
			{
				dataList=cbt.executeAllSelectQuery(" SELECT fc.categoryName,COUNT(*) FROM feedback_status AS fs   INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' AND ft.fbType='Complaint' AND fs.to_dept_subdept IN "+deptId.toString().replace("[", "(").replace("]",")")+" GROUP BY fc.categoryName", connectionSpace);
				//dataList=cbt.executeAllSelectQuery(" SELECT ft.fbType,COUNT(*) FROM feedback_status AS fs   INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' AND fs.to_dept_subdept IN "+deptId.toString().replace("[", "(").replace("]",")")+" GROUP BY fbType", connectionSpace);
			}
			else if(nFlag)
			{
				StringBuilder builder=new StringBuilder("select fc.categoryName,COUNT(*) FROM feedback_status AS fs" +
						"  INNER JOIN feedback_subcategory AS subc ON subc.id=fs.subCatg" +
						"  INNER JOIN feedback_category AS fc ON fc.id=subc.categoryName" +
						"  INNER JOIN feedback_type AS ft ON ft.id=fc.fbType WHERE fs.moduleName='FM' " +
						"  and (fs.feed_registerby='"+userName+ "' or fs.allot_to='"+loggedEmpId+"')" +
						"  GROUP BY fc.categoryName");
				dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
			}
		}
		else if(block!=null && block.equalsIgnoreCase("block6"))
		{
			if(mFlag)
			{
				dataList=cbt.executeAllSelectQuery(" SELECT status,count(*) FROM feedback_status WHERE moduleName='FM' GROUP BY status", connectionSpace);
			//	dataList=cbt.executeAllSelectQuery(" SELECT status,COUNT(*) FROM feedback_status WHERE moduleName='FM'  AND to_dept_subdept IN "+deptId.toString().replace("[", "(").replace("]",")")+" GROUP BY status", connectionSpace);
			}
			else if(hFlag)
			{
				dataList=cbt.executeAllSelectQuery(" SELECT status,count(*) FROM feedback_status WHERE moduleName='FM'  AND to_dept_subdept IN "+deptId.toString().replace("[", "(").replace("]",")")+" GROUP BY status", connectionSpace);
			}
			else if(nFlag)
			{
				dataList=cbt.executeAllSelectQuery(" SELECT status,count(*) FROM feedback_status WHERE moduleName='FM'  AND (feed_registerby='"+userName+ "' or allot_to='"+loggedEmpId+"') GROUP BY status", connectionSpace);
			}
		}
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
				{
					actionMapGraph.put(object[0].toString(),Integer.parseInt(object[1].toString()));
				}
				
			}
		}
		return SUCCESS;
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return ERROR;
	}
}

public JSONArray getJsonArray() {
	return jsonArray;
}
public void setJsonArray(JSONArray jsonArray) {
	this.jsonArray = jsonArray;
}

public String getGraphHeader() {
	return graphHeader;
}
public void setGraphHeader(String graphHeader) {
	this.graphHeader = graphHeader;
}
public String getYAxisHeader() {
	return yAxisHeader;
}
public void setYAxisHeader(String axisHeader) {
	yAxisHeader = axisHeader;
}
public String getXAxisHeader() {
	return xAxisHeader;
}
public void setXAxisHeader(String axisHeader) {
	xAxisHeader = axisHeader;
}
public String getGraphDescription() {
	return graphDescription;
}
public void setGraphDescription(String graphDescription) {
	this.graphDescription = graphDescription;
}

public Map<String, Integer> getActionMapGraph() {
	return actionMapGraph;
}

public void setActionMapGraph(Map<String, Integer> actionMapGraph) {
	this.actionMapGraph = actionMapGraph;
}

public String getBlock() {
	return block;
}

public void setBlock(String block) {
	this.block = block;
}

public String getFromDate() {
	return fromDate;
}

public void setFromDate(String fromDate) {
	this.fromDate = fromDate;
}

public String getToDate() {
	return toDate;
}

public void setToDate(String toDate) {
	this.toDate = toDate;
}


}