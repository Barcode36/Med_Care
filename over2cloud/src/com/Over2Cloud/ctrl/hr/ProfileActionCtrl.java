package com.Over2Cloud.ctrl.hr;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.action.UserHistoryAction;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProfileActionCtrl extends ActionSupport implements ServletRequestAware{
	
	static final Log log = LogFactory.getLog(ProfileActionCtrl.class);
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	String encryptedID=(String)session.get("encryptedID");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private HttpServletRequest request;
	private String empName;
	private String comName;
	private String mobOne;
	private String emailIdOne;
	private String empId;
	private String id;
	String profilePicName;
	/**
	 * EMployee other view work starts from here 
	 */
	
	public String beforeProfilePage()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
	        
				List dataList = cbt.executeAllSelectQuery( "select empName,comName,mobOne,emailIdOne,empId,id,empLogo from employee_basic where useraccountid in (select id from useraccount where userID='"+encryptedID+"')", connectionSpace);
				for (Iterator iterator = dataList.iterator(); iterator .hasNext();)
				{
					Object[] object = (Object[]) iterator.next();
					
					if(object[0]!=null)
					{
						empName=object[0].toString();
					}
					
					if(object[1]!=null)
					{
						comName=object[1].toString();
					}
					
					if(object[2]!=null)
					{
						mobOne=object[2].toString();
					}
					
					if(object[3]!=null)
					{
						emailIdOne=object[3].toString();
					}
					
					if(object[4]!=null)
					{
						empId=object[4].toString();
					}
					
					if(object[5]!=null)
					{
						id=object[5].toString();
					}
					
					if(object[6]!=null)
					{
						profilePicName=object[6].toString();
					}
				}
			
			
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	@SuppressWarnings("rawtypes")
	public String updatePersonalInfo()
	{
		
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				boolean updated=false;
				
					Map<String, Object>wherClause=new HashMap<String, Object>();
					Map<String, Object>condtnBlock=new HashMap<String, Object>();
					
					condtnBlock.put("id",id);
					
					wherClause.put("empName", empName);
					wherClause.put("comName", comName);
					wherClause.put("emailIdOne", emailIdOne);
					wherClause.put("empId", empId);
					wherClause.put("mobOne", mobOne);
					
				/*	StringBuffer buffer=new StringBuffer("select * from employee_basic where mobOne='"+mobOne+"'");
					List dataList=cbt.executeAllSelectQuery(buffer.toString(), connectionSpace);
					if(dataList!=null && dataList.size()>0)
					{
						
					}*/
					updated=new FeedbackUniversalHelper().updateTableColomn("employee_basic", wherClause, condtnBlock,connectionSpace);
					/*if(updated && dataList!=null && dataList.size()>0)
					{
						addActionMessage("Details Updated Successfully Except Mobile No !!!");
					}*/
					 if(updated)
					{
					 	StringBuilder fieldsNames=new StringBuilder();
	            		StringBuilder dataStore=new StringBuilder();
	            		StringBuilder dataField=new StringBuilder();
	            		if (wherClause!=null && !wherClause.isEmpty())
						{
							int i=1;
							for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
							{
							    if (i < wherClause.size())
									fieldsNames.append("'"+entry.getKey() + "', ");
								else
									fieldsNames.append("'"+entry.getKey() + "' ");
								i++;
							}
						}
	            		UserHistoryAction UA=new UserHistoryAction();
	            		List fieldValue=UA.fetchFields(fieldsNames.toString(),cbt,connectionSpace,"emp_contact_configuration");
	            		if (fieldValue!=null && fieldValue.size()>0)
						{
	            			for (Iterator iterator = fieldValue.iterator(); iterator.hasNext();)
							{
								Object[] object = (Object[]) iterator.next();
								if (wherClause!=null && !wherClause.isEmpty())
								{
									int i=1;
									for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
									{
										if (object[1].toString().equalsIgnoreCase(entry.getKey()))
										{
											  if (i < fieldValue.size())
											  {
												  dataStore.append(entry.getValue() + ", ");
											      dataField.append(object[0].toString() +", ");
											  }
											  else
											  {
												  dataStore.append(entry.getValue());
											      dataField.append(object[0].toString());
											  }
										}
										i++;
									}
								}
							}
	            			String empIdofuser = (String) session.get("empIdofuser").toString().split("-")[1];
	            			UA.userHistoryAdd(empIdofuser, "Edit", "Admin", "My Account",dataStore.toString(), dataField.toString(), Integer.parseInt(getId()), connectionSpace);
						}
						addActionMessage("Details Updated Successfully !!!");
					}
					else
					{
						addActionMessage("There is some problem in data !!!");
					}
					
				return SUCCESS;
			}
			catch(Exception e)
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
	


	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getMobOne() {
		return mobOne;
	}

	public void setMobOne(String mobOne) {
		this.mobOne = mobOne;
	}

	public String getEmailIdOne() {
		return emailIdOne;
	}

	public void setEmailIdOne(String emailIdOne) {
		this.emailIdOne = emailIdOne;
	}
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getProfilePicName() {
		return profilePicName;
	}
	public void setProfilePicName(String profilePicName) {
		this.profilePicName = profilePicName;
	}
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}
}
