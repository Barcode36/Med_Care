package com.Over2Cloud.Rnd;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.common.FeedbackUniversalHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalAction;
import com.Over2Cloud.ctrl.krLibrary.KRActionHelper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ProfilePic extends ActionSupport implements ServletRequestAware
{
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	String encryptedID = (String) session.get("encryptedID");
	String accountID = (String) session.get("accountid");
	String deptLevel = (String)session.get("userDeptLevel");
	
	SessionFactory connectionSpace = (SessionFactory) session .get("connectionSpace");
	private HttpServletRequest request;
	private boolean fileexist;
	private String file;
	private String id;
	private InputStream fileInputStream;
	private File browse;
	private File profile;
	private String browseContentType;
	private String profileContentType;
	private String browseFileName;
	private String orgLogoName;
	private String profilePic;
	private String profilePicName;



	// execute method for navigation.
	public String execute()
	{

		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				returnResult = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnResult = ERROR;
			}
		}
		else
		{
			returnResult = LOGIN;
		}
		return returnResult;
	}

	public String uploadPic()
	{
		boolean valid=ValidateSession.checkSession();
		if(valid)
		{
			try
			{
				System.out.println(">>>");
				String empId=(String) session.get("empIdofuser").toString().split("-")[1];
				if(empId!=null)
				{
					String filePath = request.getSession().getServletContext().getRealPath("/images/profilePic");
					if (getBrowseContentType()!=null)
					{
					    profilePicName = empId+""+new KRActionHelper().getFileExtenstion(getBrowseContentType());
			            File fileToCreate = new File(filePath, profilePicName);
			            FileUtils.copyFile(this.browse, fileToCreate);
			           
			            orgLogoName = "images/profilePic/"+ profilePicName;
			            Map<String, Object>wherClause=new HashMap<String, Object>();
						Map<String, Object>condtnBlock=new HashMap<String, Object>();
						
						condtnBlock.put("id",empId);
						
						wherClause.put("empLogo", orgLogoName);
						
						boolean updated=new FeedbackUniversalHelper().updateTableColomn("employee_basic", wherClause, condtnBlock,connectionSpace);
						
						if(updated)
						{
							addActionMessage("Image Uploaded Successfully Kindly Login Again for Changes !!!");
							return SUCCESS;
						}
						else
						{
							addActionMessage("Opps !!! There is Some Problem in Uploading Image");
							return ERROR;
						}
					}
					else
					{
						addActionMessage("There is problem in uploading Image");
						return ERROR;
					}
				}
				else
				{
					addActionMessage("Opps !!! There is Some Problem in Uploading Image");
					return ERROR;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public File getBrowse()
	{
		return browse;
	}

	public void setBrowse(File browse)
	{
		this.browse = browse;
	}

	
	public boolean isFileexist()
	{
		return fileexist;
	}

	public void setFileexist(boolean fileexist)
	{
		this.fileexist = fileexist;
	}

	public String getFile()
	{
		return file;
	}

	public void setFile(String file)
	{
		this.file = file;
	}

	public String getBrowseContentType() {
		return browseContentType;
	}

	public void setBrowseContentType(String browseContentType) {
		this.browseContentType = browseContentType;
	}

	public String getBrowseFileName() {
		return browseFileName;
	}

	public void setBrowseFileName(String browseFileName) {
		this.browseFileName = browseFileName;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getOrgLogoName() {
		return orgLogoName;
	}

	public void setOrgLogoName(String orgLogoName) {
		this.orgLogoName = orgLogoName;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public File getProfile() {
		return profile;
	}

	public void setProfile(File profile) {
		this.profile = profile;
	}

	public String getProfileContentType() {
		return profileContentType;
	}

	public void setProfileContentType(String profileContentType) {
		this.profileContentType = profileContentType;
	}

	public String getProfilePicName() {
		return profilePicName;
	}

	public void setProfilePicName(String profilePicName) {
		this.profilePicName = profilePicName;
	}

}
