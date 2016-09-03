package com.Over2Cloud.ctrl.msg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.homepage.HomePageActionCtrl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PrivateMsg  extends ActionSupport{
	static final Log log = LogFactory.getLog(HomePageActionCtrl.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private Map<Integer, String>employeeNames=null;
	private String msgTO;
	private String msgCont;
	private String empIdofuser=(String)session.get("empIdofuser");//o-100000
	private List<PrivateMsgPojo>uniqueMsgData=null;
	private String to;
	private String userNameMsg;
	private String empIdForDelete;
	private String msgID;
	private String searchedData;
	
	public String getSearchedData() {
		return searchedData;
	}

	public void setSearchedData(String searchedData) {
		this.searchedData = searchedData;
	}

	public String execute()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeCreateMsg()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			employeeNames=new HashMap<Integer, String>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();

			String tempempIdofuser[]=empIdofuser.split("-");
				StringBuilder query=new StringBuilder("select id,empName from employee_basic");
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						Object[] obdata=(Object[])it.next();
						if(!tempempIdofuser[1].equalsIgnoreCase(Integer.toString((Integer)obdata[0])))
							employeeNames.put((Integer)obdata[0], obdata[1].toString());
					}
				}
				if(!tempempIdofuser[1].equalsIgnoreCase("100000"))
					employeeNames.put(100000, "Admin");//hard code for admin or demo account
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeCreateMsg of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String sendPrivateMsg()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			PrivateMsgHelpder pmh=new PrivateMsgHelpder();
			boolean status=pmh.createGroup(cbt, connectionSpace);
			if(status)
			{
				if(getMsgTO()!=null && !getMsgTO().equalsIgnoreCase("-1") && !getMsgTO().equalsIgnoreCase("") 
						&& getMsgCont()!=null && !getMsgCont().equalsIgnoreCase(""))
				{
					status=pmh.insertGroup(cbt, connectionSpace, getMsgTO(), getMsgCont(), userName);
					if(status)
						addActionMessage("Message sent successfully!");
					else
						addActionError("Their is some problem in message sending!!!");
				}
				else
				{
					addActionError("Fill all mandatory fields!!!");
				}
			}
			else
			{
				addActionError("Their is some problem in message sending!!!");
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method sendPrivateMsg of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String beforeViewMsg()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			String tempempIdofuser[]=empIdofuser.split("-");
			PrivateMsgHelpder pmh=new PrivateMsgHelpder();
			uniqueMsgData=pmh.getUniquePrivateMessage(connectionSpace, tempempIdofuser[1], userName,getTo(),getSearchedData());
			setSearchedData(getSearchedData());
			int temp=Integer.parseInt(getTo())+5;
			setTo(Integer.toString(temp));
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforeViewMsg of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}

	
	public String deleteFullConversasion()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			PrivateMsgHelpder pmh=new PrivateMsgHelpder();
			pmh.deleteFullConv(connectionSpace, getEmpIdForDelete(), getUserNameMsg());
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method deleteFullConversasion of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String viewFullConvOfSingle()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			//System.out.println("userNameMsg   "+getUserNameMsg()+"   empIdForDelete   "+getEmpIdForDelete());
			PrivateMsgHelpder pmh=new PrivateMsgHelpder();
			uniqueMsgData=pmh.getFullConversasion(connectionSpace, getEmpIdForDelete(), getUserNameMsg());
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method viewFullConvOfSingle of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String deleteMsg()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			cbt.deleteAllRecordForId("privatemsg", "id", getMsgID(), connectionSpace);
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method deleteMsg of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public Map<Integer, String> getEmployeeNames() {
		return employeeNames;
	}

	public void setEmployeeNames(Map<Integer, String> employeeNames) {
		this.employeeNames = employeeNames;
	}

	public String getMsgTO() {
		return msgTO;
	}

	public void setMsgTO(String msgTO) {
		this.msgTO = msgTO;
	}

	public String getMsgCont() {
		return msgCont;
	}

	public void setMsgCont(String msgCont) {
		this.msgCont = msgCont;
	}

	public List<PrivateMsgPojo> getUniqueMsgData() {
		return uniqueMsgData;
	}

	public void setUniqueMsgData(List<PrivateMsgPojo> uniqueMsgData) {
		this.uniqueMsgData = uniqueMsgData;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getEmpIdofuser() {
		return empIdofuser;
	}

	public void setEmpIdofuser(String empIdofuser) {
		this.empIdofuser = empIdofuser;
	}

	public String getEmpIdForDelete() {
		return empIdForDelete;
	}

	public void setEmpIdForDelete(String empIdForDelete) {
		this.empIdForDelete = empIdForDelete;
	}

	public String getUserNameMsg() {
		return userNameMsg;
	}

	public void setUserNameMsg(String userNameMsg) {
		this.userNameMsg = userNameMsg;
	}

	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}
	
	
}
