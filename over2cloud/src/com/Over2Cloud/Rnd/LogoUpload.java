package com.Over2Cloud.Rnd;


/**
 * @author Rahul Srivastava 8010717140
 * this class is written for managing all the common operation which are based on the level of organization which we select in the organization configuration.
 * Getting all the mapped level of hierarachy with an organization based on the selected level of organization
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import com.opensymphony.xwork2.ActionSupport;
import com.Over2Cloud.Rnd.TableColumes;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LogoUpload extends ActionSupport implements ServletRequestAware{

	static final Log log = LogFactory.getLog(LogoUpload.class);
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
	private HttpServletRequest request;
	private File userImage;
    private String userImageContentType;
    private String userImageFileName;
 
    private HttpServletRequest servletRequest; 
 
    public String execute() {
        try {
        	System.out.println("rahullllllllllllllll"+userImage);
            String filePath = servletRequest.getSession().getServletContext().getRealPath("/");
            System.out.println("Server path:" + filePath);
            File fileToCreate = new File(filePath, this.userImageFileName);
 
            FileUtils.copyFile(this.userImage, fileToCreate);
        } catch (Exception e) {
            e.printStackTrace();
            addActionError(e.getMessage());
 
            return INPUT;
        }
        return SUCCESS;
    }
	

	public File getUserImage() {
		return userImage;
	}
	public void setUserImage(File userImage) {
		this.userImage = userImage;
	}

	public String getUserImageContentType() {
		return userImageContentType;
	}

	public void setUserImageContentType(String userImageContentType) {
		this.userImageContentType = userImageContentType;
	}

	public String getUserImageFileName() {
		return userImageFileName;
	}
	public void setUserImageFileName(String userImageFileName) {
		this.userImageFileName = userImageFileName;
	}

	@Override
	
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
		
	}

}
