package com.Over2Cloud.ctrl.homepage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class HomePageActionCtrl extends ActionSupport{

	static final Log log = LogFactory.getLog(HomePageActionCtrl.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private String empIdofuser=(String)session.get("empIdofuser");//o-100000
	private String empName=(String)session.get("empName");
	private String textData;
	private String divID;
    private String actionTime=null; //Today at 09:08 AM
	private List<PojoBeanForHomePosts> userPostView=null;
	private String to;
	private String from;
	private String submitPostID=null;
	private String cmnData;
	private String postDBID;
	private String postId;
	private String id;
	private String fileName;
	private String fileText;
	private File file;
    private String fileContentType;
    private String fileFileName;
    private InputStream fileInputStream;
	private String tableName;
	private String moduleFlag;
	private Map<String, String> userFilesName=null;
	private String moreResultflag;
	private String pagination;
	private String postType;
	private List<String>likeList=null;
	private String linkURL;
	private String linkName;
	private String c1;
	private String c2;
	private String commentTo="0";
	private List<CommentPojo>commentDatapagination=null;
	private String names;
	private String orgnlnames;
	private Map<Integer, String>empList=null;
	private String fieldID;
	private String selectEmpIds;
	private String selectEmpIdsTemp;
	private Map<Integer, String> mappedGrops;
	private String grouptext;
	private String groupfile;
	
	
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
	
	
	public String autoComplete()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			empList=new HashMap<Integer, String>();
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			String namesTemp=names.substring(1,names.length()).toLowerCase();
			if(!namesTemp.equalsIgnoreCase(""))
			{

				StringBuilder query=new StringBuilder("select id,empName from employee_basic where empName like '%"+namesTemp+"%'");
				List  data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						Object[] obdata=(Object[])it.next();
						empList.put((Integer)obdata[0], obdata[1].toString());
					}
				}
				empList.put(100000, "Admin");
				setNames(getNames());
				setOrgnlnames(getOrgnlnames());
				setFieldID(getFieldID());
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method autoComplete of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String beforeHomePage()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			HomePageActionCtrlHelper hact=new HomePageActionCtrlHelper();
			String tempempIdofuser[]=empIdofuser.split("-");
			setPostType(getPostType());
			userPostView=hact.getAllDataMappedWithCurrentLoginUser(connectionSpace, tempempIdofuser[1], userName,getTo(),getFrom(),getPostType());
			setMoreResultflag(getMoreResultflag());
			int tempTo=Integer.parseInt(getTo())+10;
			setTo(Integer.toString(tempTo));
			mappedGrops=hact.getGroupsmapped(connectionSpace,userName);			
			int tempCommentTo=Integer.parseInt(getCommentTo())+5;
			setCommentTo(Integer.toString(tempCommentTo));
			if(getPagination()!=null && getPagination().equalsIgnoreCase("1"))
			{
				return "paginationWorkAjax";
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String commentPagination()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			HomePageActionCtrlHelper hact=new HomePageActionCtrlHelper();
			//data view part here
			commentDatapagination=new ArrayList<CommentPojo>();
			commentDatapagination=hact.getCommentForPagination(getPostId(), connectionSpace, userName, getCommentTo());
			int tempCommentTo=Integer.parseInt(getCommentTo())+5;
			setCommentTo(Integer.toString(tempCommentTo));
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String submitTextPost()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			String postFlag=null;
			String linkPostId=null;
			String pollPostPostId=null;
			setDivID(getDivID());
			setTextData(getTextData());
			actionTime="Today at "+DateUtil.getTime(DateUtil.getCurrentTime());
			
			String tempempIdofuser[]=empIdofuser.split("-");
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			HomePageActionCtrlHelper hact=new HomePageActionCtrlHelper();
			hact.createTable(cbt, connectionSpace);
			if(getPostType()!=null && getPostType().equalsIgnoreCase("linkPost"))
			{
				//create Link post table and insert it
				hact.createTableOfLink(cbt, connectionSpace);
				hact.insertDataInLink(cbt, connectionSpace, getLinkURL(), getLinkName());
				postFlag="3";
				int idMAx=cbt.getMaxId("submitlinkpost",connectionSpace);
				linkPostId=Integer.toString(idMAx);
				setLinkURL(getLinkURL());
				setLinkName(getLinkName());
			}
			else if(getPostType()!=null && getPostType().equalsIgnoreCase("pollPost"))
			{
				//create poll post table and insert it
				hact.createTableOfPoll(cbt, connectionSpace);
				hact.insertDataInPoll(cbt, connectionSpace, getC1(), getC2());
				postFlag="4";
				int idMAx=cbt.getMaxId("submitpollpost",connectionSpace);
				pollPostPostId=Integer.toString(idMAx);
				setC1(getC1());
				setC2(getC2());
			}
			else
			{
				postFlag="1";
			}
			hact.insertData(cbt, connectionSpace, tempempIdofuser[1], empName, getTextData(), getSelectEmpIds().trim(), null, DateUtil.getCurrentDateUSFormat(), DateUtil.getTime(DateUtil.getCurrentTime()), userName,postFlag,linkPostId,pollPostPostId,getGrouptext());
			int idMAx=cbt.getMaxId("submitpost",connectionSpace);
			submitPostID=Integer.toString(idMAx);
			if(postFlag.equalsIgnoreCase("3"))
			{
				//return on link post page
				return "linkPostResult";
			}
			else if(postFlag.equalsIgnoreCase("4"))
			{
				//return on poll post page
				return "pollPostResult";
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method submitTextPost of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String submitFilePost()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			setDivID(getDivID());
			setTextData(getTextData());
	    	String storeFilePath =new CreateFolderOs().createUserDir(userName+"FileUpload")+"//"+getFileFileName() ;
	    	File theFile = new File(storeFilePath);
	    	 try {
		            FileUtils.copyFile(file, theFile);
		        } catch (IOException e) {
		        	 addActionMessage("OOpss there is a error please try again with correct data format!!!!!!!!!!");
		        }
			//setDivID(getDivID());
			//setTextData(getTextData());selectEmpIdsTemp
			actionTime="Today at "+DateUtil.getTime(DateUtil.getCurrentTime());
			String tempempIdofuser[]=empIdofuser.split("-");
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			HomePageActionCtrlHelper hact=new HomePageActionCtrlHelper();
			hact.createTable(cbt, connectionSpace);
			hact.insertData(cbt, connectionSpace, tempempIdofuser[1], empName, getFileText(), getSelectEmpIdsTemp().trim(), storeFilePath, DateUtil.getCurrentDateUSFormat(), DateUtil.getTime(DateUtil.getCurrentTime()), userName,"2",null,null,getGroupfile());
			int idMAx=cbt.getMaxId("submitpost",connectionSpace);
			submitPostID=Integer.toString(idMAx);
			addActionMessage("Post updated successfully!");
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method submitTextPost of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}

	
	public String submitComment()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
            actionTime="Today at "+DateUtil.getTime(DateUtil.getCurrentTime());
			String tempempIdofuser[]=empIdofuser.split("-");
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			HomePageActionCtrlHelper hact=new HomePageActionCtrlHelper();
			hact.createTableForComment(cbt, connectionSpace);
			hact.insertDataForComment(cbt, connectionSpace, tempempIdofuser[1], empName, getPostDBID(),getCmnData(),DateUtil.getCurrentDateUSFormat(), DateUtil.getTime(DateUtil.getCurrentTime()), userName);
			setCmnData(getCmnData());
			setEmpName(getEmpName());
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method submitTextPost of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String submitLike()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			String tempempIdofuser[]=empIdofuser.split("-");
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			HomePageActionCtrlHelper hact=new HomePageActionCtrlHelper();
			hact.createTableForLike(cbt, connectionSpace);
			//check for existance of any like on a post if exist for a user then delete it
			boolean existStatus=hact.likeExistDelete(cbt, connectionSpace, tempempIdofuser[1], getPostId());
			if(existStatus)
				hact.insertDataForLike(cbt, connectionSpace, tempempIdofuser[1], empName, getPostId(),DateUtil.getCurrentDateUSFormat(), DateUtil.getTime(DateUtil.getCurrentTime()), userName);
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method submitTextPost of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String deletePost()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			/**
			 * moduleFlag==1 means delete all post and their mapped likes and comments
			 * 
			 */
			StringBuilder queryForUploadedFile=new StringBuilder("");
			queryForUploadedFile.append("select attachedDocPath,pollPost,linkPost from submitpost where id="+getPostId());
			List  data=cbt.executeAllSelectQuery(queryForUploadedFile.toString(),connectionSpace);
			if(data!=null)
			{
				for(Iterator it=data.iterator(); it.hasNext();)
				{
					Object[] obdata=(Object[])it.next();
					if(obdata!=null && !obdata.toString().equalsIgnoreCase(""))
					{
						try
						{
							if(obdata[0]!=null)
							{
								//(new File(obdata[0].toString())).delete(); code comment by sandeep on 5-6-2013
							}
							if(obdata[1]!=null)
							{
								cbt.deleteAllRecordForId("submitpollpost", "id", obdata[1].toString(), connectionSpace);
							}
							if(obdata[2]!=null)
							{
								cbt.deleteAllRecordForId("submitlinkpost", "id", obdata[2].toString(), connectionSpace);
							}
						}
						catch(Exception e)
						{
							
						}
					}
				}
			}
			cbt.deleteAllRecordForId(getTableName(), "id", getPostId(), connectionSpace);
			if(getModuleFlag()!=null && !getModuleFlag().equalsIgnoreCase("") && getModuleFlag().equalsIgnoreCase("1"))
			{
				String deleteIds="'"+getPostId()+"'";
				cbt.deleteAllRecordForId("submitpostcomments", "postId", deleteIds, connectionSpace);
				cbt.deleteAllRecordForId("submitpostlike", "postId", deleteIds, connectionSpace);
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method submitTextPost of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String download() {
        try
        {
                fileName=getId();
                File file=new File(fileName);
                if(file.exists())
                {
		            fileInputStream = new FileInputStream(file);
		            return "download";
                }
                 else
                 {
                         addActionError("File dose not exist");
                         return ERROR;
                  }
        }
		catch (Exception e) {
	    	log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" Problem in Over2Cloud method download of class "+getClass(), e);
	    	addActionError("Oopss there is some problem!!!");
	    	return ERROR;
		}
   
   }
	
	
	public String uploadedFilesViewOfUser()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			CommonOperInterface cbt=new CommonConFactory().createInterface();
			StringBuilder query=new StringBuilder("");
			try
			{
				//Search uploaded files in KR
				userFilesName=new LinkedHashMap<String, String>();
				query.append("select krName,krUpload from krcollection where userName='"+userName+"'");
				List data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						Object[] obdata=(Object[])it.next();
						userFilesName.put(obdata[0].toString(), obdata[1].toString());
					}
				}
			}
			catch(Exception e)
			{
				
			}
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String totalLikesView()
	{
		try
		{
			if(userName==null || userName.equalsIgnoreCase(""))
				return LOGIN;
			
			likeList=new ArrayList<String>();
			if(getPostId()!=null && !getPostId().equalsIgnoreCase(""))
			{
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				StringBuilder query=new StringBuilder("select empName from submitpostlike where postId='"+getPostId()+"'");
				List data=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(data!=null)
				{
					for(Iterator it=data.iterator(); it.hasNext();)
					{
						Object obdata=(Object)it.next();
						if(obdata!=null)
						{
							likeList.add(obdata.toString());
						}
					}
				}
			}
			
		}
		catch(Exception e)
		{
			log.error("Date : "+DateUtil.getCurrentDateIndianFormat()+" Time: "+DateUtil.getCurrentTime()+" " +
					"Problem in Over2Cloud  method execute of class "+getClass(), e);
			 e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String getFileText() {
		return fileText;
	}

	public void setFileText(String fileText) {
		this.fileText = fileText;
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

	public String getCmnData() {
		return cmnData;
	}

	public void setCmnData(String cmnData) {
		this.cmnData = cmnData;
	}

	public String getPostDBID() {
		return postDBID;
	}

	public void setPostDBID(String postDBID) {
		this.postDBID = postDBID;
	}

	public String getEmpIdofuser() {
		return empIdofuser;
	}

	public void setEmpIdofuser(String empIdofuser) {
		this.empIdofuser = empIdofuser;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getTextData() {
		return textData;
	}

	public void setTextData(String textData) {
		this.textData = textData;
	}

	public String getDivID() {
		return divID;
	}

	public void setDivID(String divID) {
		this.divID = divID;
	}

	public String getActionTime() {
		return actionTime;
	}

	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}

	public List<PojoBeanForHomePosts> getUserPostView() {
		return userPostView;
	}

	public void setUserPostView(List<PojoBeanForHomePosts> userPostView) {
		this.userPostView = userPostView;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubmitPostID() {
		return submitPostID;
	}

	public void setSubmitPostID(String submitPostID) {
		this.submitPostID = submitPostID;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getModuleFlag() {
		return moduleFlag;
	}

	public void setModuleFlag(String moduleFlag) {
		this.moduleFlag = moduleFlag;
	}

	public Map<String, String> getUserFilesName() {
		return userFilesName;
	}

	public void setUserFilesName(Map<String, String> userFilesName) {
		this.userFilesName = userFilesName;
	}

	public String getMoreResultflag() {
		return moreResultflag;
	}

	public void setMoreResultflag(String moreResultflag) {
		this.moreResultflag = moreResultflag;
	}

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public List<String> getLikeList() {
		return likeList;
	}

	public void setLikeList(List<String> likeList) {
		this.likeList = likeList;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getC1() {
		return c1;
	}

	public void setC1(String c1) {
		this.c1 = c1;
	}

	public String getC2() {
		return c2;
	}

	public void setC2(String c2) {
		this.c2 = c2;
	}

	public String getCommentTo() {
		return commentTo;
	}

	public void setCommentTo(String commentTo) {
		this.commentTo = commentTo;
	}

	public List<CommentPojo> getCommentDatapagination() {
		return commentDatapagination;
	}

	public void setCommentDatapagination(List<CommentPojo> commentDatapagination) {
		this.commentDatapagination = commentDatapagination;
	}


	public String getNames() {
		return names;
	}


	public void setNames(String names) {
		this.names = names;
	}


	public String getOrgnlnames() {
		return orgnlnames;
	}


	public void setOrgnlnames(String orgnlnames) {
		this.orgnlnames = orgnlnames;
	}


	public Map<Integer, String> getEmpList() {
		return empList;
	}


	public void setEmpList(Map<Integer, String> empList) {
		this.empList = empList;
	}


	public String getFieldID() {
		return fieldID;
	}


	public void setFieldID(String fieldID) {
		this.fieldID = fieldID;
	}


	public String getSelectEmpIds() {
		return selectEmpIds;
	}


	public void setSelectEmpIds(String selectEmpIds) {
		this.selectEmpIds = selectEmpIds;
	}


	public String getSelectEmpIdsTemp() {
		return selectEmpIdsTemp;
	}


	public void setSelectEmpIdsTemp(String selectEmpIdsTemp) {
		this.selectEmpIdsTemp = selectEmpIdsTemp;
	}


	public Map<Integer, String> getMappedGrops() {
		return mappedGrops;
	}


	public void setMappedGrops(Map<Integer, String> mappedGrops) {
		this.mappedGrops = mappedGrops;
	}


	public String getGrouptext() {
		return grouptext;
	}


	public void setGrouptext(String grouptext) {
		this.grouptext = grouptext;
	}


	public String getGroupfile() {
		return groupfile;
	}


	public void setGroupfile(String groupfile) {
		this.groupfile = groupfile;
	}

	
}
