package com.Over2Cloud.ctrl.vaults;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.group.GroupActionCtrl;
import com.Over2Cloud.ctrl.homepage.HomePageActionCtrlHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class VaultsCtrl extends ActionSupport{
	static final Log log = LogFactory.getLog(GroupActionCtrl.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String empIdofuser=(String)session.get("empIdofuser");//o-100000
	private String moduleFlag;
	private String headingName;
	private File file;
	private String fileContentType;
    private String fileFileName;
    private List<VaultsPojo>filesOfuser;
    private String postId;
    private Map<Integer, String>empNamesForShare;
    private String pid;
    private String empName=(String)session.get("empName");
    private String tempFalg;
    private String searchedData;
    
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
	
	public String beforevauts()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			filesOfuser=new ArrayList<VaultsPojo>();
			VaultsCtrlHelper vch=new VaultsCtrlHelper();
			if(getModuleFlag().equalsIgnoreCase("1"))
			{
				headingName="All files I own";
				if(getTempFalg()!=null)
					filesOfuser=vch.getUserUploadedFiles(cbt, connectionSpace, userName,getSearchedData());
			}
			else if(getModuleFlag().equalsIgnoreCase("2"))
			{
				String tempempIdofuser[]=empIdofuser.split("-");
				headingName="All files";
				if(getTempFalg()!=null)
				{
					List<VaultsPojo>filesOfuserTemp=vch.getUserUploadedFiles(cbt, connectionSpace, userName,getSearchedData());
					setFilesOfuser(filesOfuserTemp);
					filesOfuserTemp=vch.getUserSharedFiles(cbt, connectionSpace, tempempIdofuser[1],userName,getSearchedData());
					for(VaultsPojo cp:filesOfuserTemp)
					{
						filesOfuser.add(cp);
					}
					setFilesOfuser(getFilesOfuser());
				}
			}
			else if(getModuleFlag().equalsIgnoreCase("3"))
			{
				headingName="All files Shared with me";
				if(getTempFalg()!=null)
				{
					String tempempIdofuser[]=empIdofuser.split("-");
					filesOfuser=vch.getUserSharedFiles(cbt, connectionSpace, tempempIdofuser[1],userName,getSearchedData());
				}
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method beforevauts of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String uploadFile()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(file!=null & getFileContentType()!=null && getFileFileName()!=null)
			{
				String storeFilePath =new CreateFolderOs().createUserDir(userName+"FileUpload")+"//"+getFileFileName() ;
		    	File theFile = new File(storeFilePath);
		    	 try {
			            FileUtils.copyFile(file, theFile);
			        } catch (IOException e) {
			        	 addActionMessage("OOpss there is a error please try again with correct data format!!!!!!!!!!");
			        	 return ERROR;
			        }
			        CommonOperInterface cbt=new CommonConFactory().createInterface();
			        VaultsCtrlHelper vch=new VaultsCtrlHelper();
			        boolean status=vch.uploadFilesCreate(cbt, connectionSpace);
			        if(status)
			        {
			        	status=vch.uploadFilesInsert(cbt, connectionSpace, storeFilePath, userName);
			        	if(status)
			        	{
							addActionMessage("File uploaded successfully!");
			        	}
						else
						{
							addActionError("Their is some problem in file uploading!!!");
							return ERROR;
						}
			        }
			        else
			        {
			        	addActionError("Their is some problem in file uploading!!!");
			        	return ERROR;
			        }
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method uploadFile of class "+getClass(), e);
			 e.printStackTrace();
			 addActionError("Their is some problem in file uploading!!!");
			 return ERROR;
		}
		return SUCCESS;
	}

	
	public String deleteUserFile()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			cbt.deleteAllRecordForId("uploadedfiles", "id", getPostId(), connectionSpace);
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method deleteUserFile of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String getSharingData()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			empNamesForShare=new HashMap<Integer, String>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			if(getModuleFlag()!=null && getModuleFlag().equalsIgnoreCase("1") || getModuleFlag().equalsIgnoreCase("3"))
			{
				StringBuilder query=new StringBuilder("select id,empName from employee_basic order by empName");
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						Object[] obdata=(Object[])it.next();
						if(obdata!=null && obdata[0]!=null && obdata[1]!=null)
						{
							empNamesForShare.put((Integer)obdata[0], obdata[1].toString());
						}
					}
				}
				setModuleFlag(getModuleFlag());
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method getSharingData of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String shareWith()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			if(getPid()!=null)
			{
				String temp[]=getPid().split(", ");
				int k=1;
				StringBuilder sharedWith=new StringBuilder();
				StringBuilder querryIds=new StringBuilder();
				for(String H:temp)
				{
					if(H!=null && !H.equalsIgnoreCase(""))
					{
						if(k<temp.length)
						{
							sharedWith.append(H+",");
						}
						else
						{
							sharedWith.append(H);
						}
						k++;
						
					}
				}
				if(getModuleFlag().equalsIgnoreCase("1"))//from user upload files
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					String storeFilePath=null;
					StringBuilder  query=new StringBuilder("select filePath from uploadedfiles where id="+getPostId());
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null)
					{
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object obdata=(Object)it.next();
							if(obdata!=null)
							{
								storeFilePath=obdata.toString();
							}
						}
					}
					if(storeFilePath!=null)
					{
						HomePageActionCtrlHelper hact=new HomePageActionCtrlHelper();
						hact.createTable(cbt, connectionSpace);
						String tempempIdofuser[]=empIdofuser.split("-");
						boolean staus=hact.insertData(cbt, connectionSpace, tempempIdofuser[1], empName, "File shared by "+empName, sharedWith.toString().trim(), storeFilePath, DateUtil.getCurrentDateUSFormat(), DateUtil.getTime(DateUtil.getCurrentTime()), userName,"2",null,null,null);
						if(staus)
							addActionMessage("File shared!");
						else
						{
							addActionError("Oops there is some problem!!!");
							return ERROR;
						}
					}
				
				}
				else// from user shared files
				{
					CommonOperInterface cbt=new CommonConFactory().createInterface();
					String shareWithEmpid=null;
					StringBuilder  query=new StringBuilder("select shareWithEmpid from submitpost where id="+getPostId());
					List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if(data!=null)
					{
						for(Iterator it=data.iterator(); it.hasNext();)
						{
							Object obdata=(Object)it.next();
							if(obdata!=null)
							{
								shareWithEmpid=obdata.toString();
							}
						}
					}
					if(shareWithEmpid!=null)
					{
						shareWithEmpid=shareWithEmpid+","+sharedWith.toString();
						Map<String, Object>wherClause=new HashMap<String, Object>();
						Map<String, Object>condtnBlock=new HashMap<String, Object>();
						wherClause.put("shareWithEmpid", shareWithEmpid);
						condtnBlock.put("id",getPostId());
						boolean staus=cbt.updateTableColomn("submitpost", wherClause, condtnBlock,connectionSpace);
						if(staus)
							addActionMessage("File shared!");
						else
						{
							addActionError("Oops there is some problem!!!");
							return ERROR;
						}
					}
				}
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method shareWith of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getModuleFlag() {
		return moduleFlag;
	}

	public void setModuleFlag(String moduleFlag) {
		this.moduleFlag = moduleFlag;
	}

	public String getEmpIdofuser() {
		return empIdofuser;
	}

	public void setEmpIdofuser(String empIdofuser) {
		this.empIdofuser = empIdofuser;
	}

	public String getHeadingName() {
		return headingName;
	}

	public void setHeadingName(String headingName) {
		this.headingName = headingName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public List<VaultsPojo> getFilesOfuser() {
		return filesOfuser;
	}

	public void setFilesOfuser(List<VaultsPojo> filesOfuser) {
		this.filesOfuser = filesOfuser;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Map<Integer, String> getEmpNamesForShare() {
		return empNamesForShare;
	}

	public void setEmpNamesForShare(Map<Integer, String> empNamesForShare) {
		this.empNamesForShare = empNamesForShare;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTempFalg() {
		return tempFalg;
	}

	public void setTempFalg(String tempFalg) {
		this.tempFalg = tempFalg;
	}

	public String getSearchedData() {
		return searchedData;
	}

	public void setSearchedData(String searchedData) {
		this.searchedData = searchedData;
	}
	
}
