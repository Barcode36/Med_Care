package com.Over2Cloud.ctrl.common.communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.Over2Cloud.helpdesk.BeanUtil.CommunicationPojo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CommunicationAction extends GridPropertyBean{
	private static final long serialVersionUID = 1L;
	private String emailBody;
	private String selectedId;
	private String via_mode;
	private String inTimeDate;
	private String updateTimeDate;
	private String insertDate;
	private String updateDateForMail;
	private String moduleName;
	private String counter;
	
	List<CommunicationPojo> smsList= new ArrayList<CommunicationPojo>();
	List<CommunicationPojo> emailList= new ArrayList<CommunicationPojo>();
	
	public String execute()
	{
		return SUCCESS;
	}
	public String beforeMailSms() 
	{
		if (ValidateSession.checkSession())
		{
		    try 
		    {
		    	StringBuilder qry=new StringBuilder();
		    	qry.append("SELECT COUNT(*) FROM instant_msg ");
		    	if (moduleName!=null && !moduleName.equalsIgnoreCase(""))
				{
		    		qry.append(" WHERE module='"+moduleName+"' ");
				}
		    	List count=new createTable().executeAllSelectQuery(qry.toString(),connectionSpace);
		    	if (count!=null && count.size()>0)
				{
					counter=count.get(0).toString();
				}
		    	inTimeDate=DateUtil.getNextDateAfter(-7);
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
	public String beforeMail() 
	{
		if (ValidateSession.checkSession())
		{
		    try 
		    {
		    	StringBuilder qry=new StringBuilder();
		    	qry.append("SELECT COUNT(*) FROM instant_mail ");
		    	if (moduleName!=null && !moduleName.equalsIgnoreCase(""))
				{
		    		qry.append(" WHERE module='"+moduleName+"' ");
				}
		    	List count=new createTable().executeAllSelectQuery(qry.toString(),connectionSpace);
		    	if (count!=null && count.size()>0)
				{
					counter=count.get(0).toString();
				}
		    	
		    	insertDate=DateUtil.getNextDateAfter(-7);
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
	public String viewSMSData()
	 {
		String returnResult=ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try 
		{
				   SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
				   CommonOperInterface cbt = new CommonConFactory().createInterface();
				   int count = 0 ;
		
			       StringBuilder qryString = new StringBuilder();
				   List dataList = null;
				   qryString.append("select * from instant_msg ");
					
				
					if (getInTimeDate()!=null && getUpdateTimeDate()!=null) 
					{
						qryString.append("WHERE date BETWEEN '"+DateUtil.convertDateToUSFormat(getInTimeDate())+"' AND '"+DateUtil.convertDateToUSFormat(getUpdateTimeDate())+"'");
					}
					else
					{
						qryString.append("WHERE date BETWEEN '"+DateUtil.getNextDateAfter(-7)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
					}
					if (moduleName!=null && !moduleName.equalsIgnoreCase(""))
					{
						qryString.append(" AND module= '"+moduleName+"'");
					}
					System.out.println("Query Strfdfing is "+qryString.toString());
					dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
				setRecords(count);
				int to = (getRows() * getPage());
				@SuppressWarnings("unused")
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
			
			
			smsList=new CommunicationAction().setSMSMailValues(dataList,"SMS");
			
			setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
			returnResult = SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		else {
			returnResult=LOGIN;
		}
		return returnResult;
	 }
	
	
	
	@SuppressWarnings("rawtypes")
	public String viewEmailData()
	 {
		String returnResult=ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try {
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
			   CommonOperInterface cbt = new CommonConFactory().createInterface();
			   int count = 0 ;
	
		       StringBuilder qryString = new StringBuilder();
			   List dataList = null;
			   qryString.append("select * from instant_mail ");
				if (getInsertDate()!=null && getUpdateDateForMail()!=null) 
				{
					qryString.append("WHERE date BETWEEN '"+DateUtil.convertDateToUSFormat(getInsertDate())+"' AND '"+DateUtil.convertDateToUSFormat(getUpdateDateForMail())+"'");
				}
				else
				{
					qryString.append("WHERE date BETWEEN '"+DateUtil.getNextDateAfter(-7)+"' AND '"+DateUtil.getCurrentDateUSFormat()+"'");
				}
				if (moduleName!=null && !moduleName.equalsIgnoreCase(""))
				{
					qryString.append(" AND module= '"+moduleName+"'");
				}
				System.out.println("Query Strfdfing is "+qryString.toString());
				dataList = cbt.executeAllSelectQuery(qryString.toString(), connectionSpace);
		
			setRecords(count);
			int to = (getRows() * getPage());
			@SuppressWarnings("unused")
			int from = to - getRows();
			if (to > getRecords())
				to = getRecords();
		
			if (dataList!=null && !dataList.isEmpty()) 
			{
				emailList=new CommunicationAction().setSMSMailValues(dataList,"MAIL");
			}
			
			
			setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
			returnResult = SUCCESS;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		else {
			returnResult=LOGIN;
		}
		return returnResult;
	 }



	@SuppressWarnings("rawtypes")
	public List<CommunicationPojo> setSMSMailValues(List data, String setFor)
	 {	List<CommunicationPojo> feedList = new ArrayList<CommunicationPojo>();
		if (data!=null && data.size()>0) {
			for (Iterator iterator = data.iterator(); iterator
					.hasNext();) {
				Object[] obdata = (Object[]) iterator.next();
				CommunicationPojo  cp = new CommunicationPojo();
				 cp.setId((Integer)obdata[0]);
				 if (obdata[1]!=null && !obdata[1].toString().equals("")) {
					 cp.setName(obdata[1].toString());
				 }
				 else {
					 cp.setName("NA");
				 }
				 if (obdata[2]!=null && !obdata[2].toString().equals("")) {
					 cp.setDept(obdata[2].toString());
				 }
				 else {
					 cp.setDept("NA");
				 }
				 if (setFor.equalsIgnoreCase("SMS"))
				  {
						cp.setMobOne(obdata[3].toString());
						cp.setSms(obdata[4].toString());
						cp.setStatus(obdata[6].toString());
						cp.setModule(getModuleName(obdata[7].toString()));
						if (obdata[9]!=null && !obdata[9].toString().equals("")&& !obdata[9].toString().equals("0000-00-00") && obdata[10]!=null && !obdata[10].toString().equals("") && !obdata[10].toString().equals("00-00-00")) {
							cp.setInsertdate_time(DateUtil.convertDateToIndianFormat(obdata[9].toString())+" "+obdata[10].toString().substring(0, 5));
						}
						else {
							cp.setInsertdate_time("NA");
						}
						
						if (obdata[11]!=null && !obdata[11].toString().equals("") && !obdata[11].toString().equals("0000-00-00") && obdata[12]!=null && !obdata[12].toString().equals("") && !obdata[12].toString().equals("00-00-00")) {
							cp.setUpdatedate_time(DateUtil.convertDateToIndianFormat(obdata[11].toString())+" "+obdata[12].toString().substring(0, 5));
						}
						else {
							cp.setUpdatedate_time("0000-00-00 00:00");
						}
				   }
				 else if (setFor.equalsIgnoreCase("MAIL")) 
				   {
						 if (obdata[3]!=null) 
						 {
							 cp.setEmailId(obdata[3].toString());
						} else {
							cp.setEmailId("NA");
						}
						
						cp.setSubject(obdata[4].toString());
						cp.setEmail(obdata[5].toString());
						cp.setStatus(obdata[7].toString());
						cp.setModule(getModuleName(obdata[9].toString()));
						if (obdata[11]!=null && !obdata[11].toString().equals("")&& !obdata[11].toString().equals("0000-00-00") && obdata[12]!=null && !obdata[12].toString().equals("") && !obdata[12].toString().equals("00-00-00")) {
							cp.setInsertdate_time(DateUtil.convertDateToIndianFormat(obdata[11].toString())+" "+obdata[12].toString().substring(0, 5));
						}
						else {
							cp.setInsertdate_time("NA");
						}
						
						if (obdata[13]!=null && !obdata[13].toString().equals("") && !obdata[13].toString().equals("0000:00:00") && obdata[14]!=null && !obdata[14].toString().equals("") && !obdata[14].toString().equals("00:00:00")) {
							cp.setUpdatedate_time(DateUtil.convertDateToIndianFormat(obdata[13].toString())+" "+obdata[14].toString().substring(0, 5));
						}
						else {
							cp.setUpdatedate_time("0000-00-00 00:00");
						}
				}
				feedList.add(cp);
			}
	}
	return feedList;}
	
	public String geEmailDetails()
	 {
		try {
			emailBody = new HelpdeskUniversalAction().getData("instant_mail", "mail_text", "id", String.valueOf(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	 }
	public String getModuleName(String moduleName)
	 {
		String module=null;
		try {
		 if (moduleName.equalsIgnoreCase("Common"))
		  {
			 module="Common Space";
		  }
		 else  if (moduleName.equalsIgnoreCase("DAR"))
		  {
			 module="Project";
		  }
		 else  if (moduleName.equalsIgnoreCase("LM"))
		  {
			 module="HR";
		  }
		 else  if (moduleName.equalsIgnoreCase("FM"))
		  {
			 module="Feedback";
		  }
		 else  if (moduleName.equalsIgnoreCase("DREAM_HDM"))
		  {
			 module="Helpdesk Offering";
		  }
		 else  if (moduleName.equalsIgnoreCase("HDM"))
		  {
			 module="Helpdesk";
		  }
		 else  if (moduleName.equalsIgnoreCase("Compliance"))
		  {
			 module="Operations";
		  }
		 else  if (moduleName.equalsIgnoreCase("KR"))
		  {
			 module="Knowledge Resource";
		  }
		 else
		 {
			 module=moduleName;
		 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return module;
	 }
	public String updateCommunication()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag) {
		try
		{
			String userName=(String)session.get("uName");
			SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		    Map<String, Object>wherClause=new HashMap<String, Object>();
			Map<String, Object>condtnBlock=new HashMap<String, Object>();
		    wherClause.put("flag", 0);
		    wherClause.put("status", "Pending");
		    wherClause.put("update_date", DateUtil.getCurrentDateUSFormat());
		    wherClause.put("update_time", DateUtil.getCurrentTime());
			if (via_mode.equalsIgnoreCase("sms")) {
			   if (selectedId.contains(",")) {
				   String[] selectArr= selectedId.split(",");
				for (int i = 0; i < selectArr.length; i++) {
					wherClause.put("user", userName);
					condtnBlock.put("id",selectArr[i]);
					new HelpdeskUniversalHelper().updateTableColomn("instant_msg", wherClause, condtnBlock,connectionSpace);
				}
			   }
			   else {
				   wherClause.put("user", userName);
				   condtnBlock.put("id",selectedId);
				   new HelpdeskUniversalHelper().updateTableColomn("instant_msg", wherClause, condtnBlock,connectionSpace);
			   }
			   addActionMessage("SMS Resend SuccessFully !!!");
			   returnResult=SUCCESS;
			}
			else if (via_mode.equalsIgnoreCase("mail")) {
				   if (selectedId.contains(",")) {
					   String[] selectArr= selectedId.split(",");
					for (int i = 0; i < selectArr.length; i++) {
						 wherClause.put("userName", userName);
						condtnBlock.put("id",selectArr[i]);
						new HelpdeskUniversalHelper().updateTableColomn("instant_mail", wherClause, condtnBlock,connectionSpace);
					}
				   }
				   else {
					   wherClause.put("userName", userName);
					   condtnBlock.put("id",selectedId);
					   new HelpdeskUniversalHelper().updateTableColomn("instant_mail", wherClause, condtnBlock,connectionSpace);
				   }
				   addActionMessage("Mail Resend SuccessFully !!!");
				   returnResult=SUCCESS;
			}
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		}
		else {
			returnResult =LOGIN;
		}
		return returnResult;
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

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
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


	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}

	public List<CommunicationPojo> getSmsList() {
		return smsList;
	}
	public void setSmsList(List<CommunicationPojo> smsList) {
		this.smsList = smsList;
	}

	public List<CommunicationPojo> getEmailList() {
		return emailList;
	}
	public void setEmailList(List<CommunicationPojo> emailList) {
		this.emailList = emailList;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public String getVia_mode() {
		return via_mode;
	}

	public void setVia_mode(String via_mode) {
		this.via_mode = via_mode;
	}
	public String getInTimeDate()
	{
		return inTimeDate;
	}
	public void setInTimeDate(String inTimeDate)
	{
		this.inTimeDate = inTimeDate;
	}
	public String getUpdateTimeDate()
	{
		return updateTimeDate;
	}
	public void setUpdateTimeDate(String updateTimeDate)
	{
		this.updateTimeDate = updateTimeDate;
	}
	public String getInsertDate()
	{
		return insertDate;
	}
	public void setInsertDate(String insertDate)
	{
		this.insertDate = insertDate;
	}
	public String getUpdateDateForMail()
	{
		return updateDateForMail;
	}
	public void setUpdateDateForMail(String updateDateForMail)
	{
		this.updateDateForMail = updateDateForMail;
	}
	public String getModuleName()
	{
		return moduleName;
	}
	public void setModuleName(String moduleName)
	{
		this.moduleName = moduleName;
	}
	public String getCounter()
	{
		return counter;
	}
	public void setCounter(String counter)
	{
		this.counter = counter;
	}
	
}
