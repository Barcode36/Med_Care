package com.Over2Cloud.ctrl.feedback;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class FeedbackCommonAction extends ActionSupport
{
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");

	private InputStream fileInputStream;
	
	
	// Instance Variables For MIS Querry
	private String fromDate;
	private String toDate;
	private String level;
	private String tableName;
	private String fbtype;
	
	private String feedBack;
	private String docName;
	private String spec;
	private String patType;
	
	
	
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	 private String downloadType;
	 private String downloadFile;
	 private String titles;
	
	 
	 
	 public String beforeDownload()
	 {
		 boolean validSession=ValidateSession.checkSession();
		 if(validSession)
		 {
			 System.out.println("From Date >>>>"+getFromDate()+">>><<<To Date <<<>>>"+getToDate());
			 
			 if(getFromDate()!=null)
			 {
				 setFromDate(getFromDate());
			 }
			 
			 if(getToDate()!=null)
			 {
				setToDate(getToDate()) ;
			 }
			 
			 if(getLevel()!=null)
			 {
				 setLevel(getLevel());
			 }
			 
			 if(getTableName()!=null)
			 {
				 setTableName(getTableName());
			 }
			 
			// if(getFbtype()!=null)
			 {
				setFbtype(getFbtype()); 
				session.put("feedType",getFbtype());
				
			 }
			 return SUCCESS;
		 }
		 else
		 {
			 return LOGIN;
		 }
	 }
	 
	 public String downloadFeedbackReportNeg()throws Exception
	 {


		 CommonOperInterface cbt=new CommonConFactory().createInterface();
			   List  Contactdata=null;
			   String[] headerdata=null;
	    	try{
	    	if(titles!=null){	
	    	StringBuilder StringObj=new StringBuilder();
	    	
	    	if(session.containsKey("feedType"))
			{
	    		fbtype=(String)session.get("feedType");
			}
		    if(fbtype!=null && fbtype.equalsIgnoreCase("No"))
		    {
		    	StringObj.append("select feedbt.feedTicketNo,feedData.status,feedData.openDate,feedData.openTime," +
                	 	" feedbck.escalation_date,feedbck.escalation_time,feedData.clientId,feedData.clientName,feedData.centerCode,feedData.problem,feedData.mobNo," +
                	 	" feedData.emailId,feedData.refNo,feedData.centreName,feedData.level," +
                	 	" feedData.compType,feedData.mode,feedData.remarks from feedback_ticket as feedbt" +
                	 	" inner join feedback_status as feedbck on feedbt.feed_stat_id=feedbck.id " +
                	 	" inner join feedbackdata as feedData on feedbt.feed_data_id=feedData.id where feedData.targetOn='No' and  feedbck.status!='Resolved' order by feedData.openDate DESC");
		    }
		    
	    	try
	    	{
				 Contactdata=cbt.executeAllSelectQuery(StringObj.toString(),connectionSpace);
				}
				catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
	    	}
	           if (downloadType != null && downloadType.equals("downloadExcel")) {
				  try {
					  if(getTitles()!=null)
				    	{
				    		headerdata=titles.split(",");
				    	}
					 /* DownldeExcelFeedback writerexcel = new DownldeExcelFeedback();
					  CreateFolderOs crtobjos=new CreateFolderOs();
						downloadFile = writerexcel.createExcel(getText("Fedback Details"), getText("downloadSheet"), getText("Feedback Excel Report"), Contactdata, headerdata, "productdetails", crtobjos.createUserDir("downloadR"));	
						
						fileInputStream=new FileInputStream(new File(downloadFile));*/
						
						
						 DownldeExcelFeedback writerexcel = new DownldeExcelFeedback();
						  CreateFolderOs crtobjos=new CreateFolderOs();
							downloadFile = writerexcel.createExcel(getText("Feedback Details"), getText("downloadSheet"), getText("Negative Feedback Excel Report"), Contactdata, headerdata, "productdetails", crtobjos.createUserDir("downloadR"));	
							
							fileInputStream=new FileInputStream(new File(downloadFile));
							
							downloadFile=downloadFile.substring(downloadFile.lastIndexOf("//")+2, downloadFile.length());
						
						
						
						
						
						
			         }catch (Exception e){
			        	 e.printStackTrace();
			          }
	    	}else{addActionMessage("No Result Found! due to Fields selection");}}
	    	catch (Exception e) {
	    		e.printStackTrace();
				// TODO: handle exception
			}
         return SUCCESS;
	 }
	 
	 
	 public String downloadFeedbackReportPositive() throws Exception
	 {

		 CommonOperInterface cbt=new CommonConFactory().createInterface();
			   List  Contactdata=null;
			   String[] headerdata=null;
	    	try{
	    	if(titles!=null){	
	    	StringBuilder StringObj=new StringBuilder();
	    	
	    	if(session.containsKey("feedType"))
			{
	    		fbtype=(String)session.get("feedType");
				session.remove("feedType");
			}
		    if(getTableName()!=null && (getTableName().equalsIgnoreCase("feedbackdata")))
		    {
		    	StringObj.append("select ");
		    	 List fieldNames=Configuration.getColomnList("mapped_feedback_configuration", accountID, connectionSpace, "feedback_data_configuration");
	                List<Object> Listhb=new ArrayList<Object>();
	                int i=0;
	                for(Iterator it=fieldNames.iterator(); it.hasNext();)
	                {
	                    //generating the dyanamic query based on selected fields
	                        Object obdata=(Object)it.next();
	                        if(obdata!=null && !obdata.toString().equalsIgnoreCase("id"))
	                        {
	                            if(i<fieldNames.size()-1)
	                            	StringObj.append(obdata.toString()+",");
	                            else
	                            	StringObj.append(obdata.toString());
	                        }
	                        i++;
	                    
	                }
	                StringObj.append(" from feedbackdata where id !=0 ");
		 
		    	if(getTitles()!=null)
		    	{
		    		headerdata=titles.split(",");
		    	}
		    }
		    else if(getTableName()!=null && !(getTableName().equalsIgnoreCase("-1")))
		    {
		    	headerdata=titles.split(",");
		    	StringObj.append("select ");
			    StringObj.append(titles);
		    	StringObj.append(" "+" from"+ " "+getTableName()+" where id !=0 "); 
		    }
		    else
		    {
		    	headerdata=titles.split(",");
		    	StringObj.append("select ");
			    StringObj.append(titles);
		    	StringObj.append(" "+" from"+ " "+" feedbackdata where id != 0"); 
		    }
	    	
		    
		    if(getLevel()!=null)
			{
				if((getLevel().equalsIgnoreCase("Yes") || getLevel().equalsIgnoreCase("No")))
				{
					StringObj.append(" && targetOn='"+getLevel()+"'");
				}
			}
		    
		    if(getFromDate()!=null && getToDate()!=null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase(""))
			{
		    	StringObj.append(" && openDate between '"+DateUtil.convertDateToUSFormat(getFromDate())+"' and '"+DateUtil.convertDateToUSFormat(getToDate())+"'");
			}
		    
	    	try
	    	{
				 Contactdata=cbt.executeAllSelectQuery(StringObj.toString(),connectionSpace);
				}
				catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
	    	}
	           if (downloadType != null && downloadType.equals("downloadExcel")) {
				  try {/*
					  DownldeExcelFeedback writerexcel = new DownldeExcelFeedback();
					  CreateFolderOs crtobjos=new CreateFolderOs();
						downloadFile = writerexcel.createExcel(getText("Fedback Details"), getText("downloadSheet"), getText("Feedback Excel Report"), Contactdata, headerdata, "productdetails", crtobjos.createUserDir("downloadR"));	
						
						fileInputStream=new FileInputStream(new File(downloadFile));
						
			         */
					  

					  DownldeExcelFeedback writerexcel = new DownldeExcelFeedback();
					  CreateFolderOs crtobjos=new CreateFolderOs();
						downloadFile = writerexcel.createExcel(getText("Feedback Details"), getText("downloadSheet"), getText("Positive Feedback Excel Report"), Contactdata, headerdata, "productdetails", crtobjos.createUserDir("downloadR"));	
						
						fileInputStream=new FileInputStream(new File(downloadFile));
						
						downloadFile=downloadFile.substring(downloadFile.lastIndexOf("//")+2, downloadFile.length());
						
						
			         
					  
					  
				  }catch (Exception e){
			        	 e.printStackTrace();
			          }
	    	}else{addActionMessage("No Result Found! due to Fields selection");}}
	    	catch (Exception e) {
	    		e.printStackTrace();
				// TODO: handle exception
			}
         return SUCCESS;
	    
	 }
	 
	 
	 public String downloadFeedbackreportPaper() throws Exception 
	 {
		 CommonOperInterface cbt=new CommonConFactory().createInterface();
			   List  Contactdata=null;
			   String[] headerdata=null;
	    	try{
	    	if(titles!=null){	
	    	StringBuilder StringObj=new StringBuilder();
	    	
	    	if(session.containsKey("feedType"))
			{
	    		fbtype=(String)session.get("feedType");
				session.remove("feedType");
			}
		    if(getFbtype()!=null && getTableName()!=null && (getTableName().equalsIgnoreCase("feedbackdata")))
		    {
		    	StringObj.append("select ");
		    	 List fieldNames=Configuration.getColomnList("mapped_feedback_configuration", accountID, connectionSpace, "feedback_data_configuration");
		    	 List fieldNamesIpd=Configuration.getColomnList("mapped_feedback_paperform_rating_configuration", accountID, connectionSpace, "feedback_paperform_rating_configuration");
		    	 List fieldNamesOpd=Configuration.getColomnList("mapped_feedback_paperform_rating_configuration_opd", accountID, connectionSpace, "feedback_paperform_rating_configuration_opd");
	                int i=0;
	                for(Iterator it=fieldNames.iterator(); it.hasNext();)
	                {
	                    //generating the dyanamic query based on selected fields
	                        Object obdata=(Object)it.next();
	                        if(obdata!=null && !obdata.toString().equalsIgnoreCase("id"))
	                        {
	                            if(i<fieldNames.size()-1)
	                            	StringObj.append("feed."+obdata.toString()+",");
	                            else
	                            	StringObj.append("feed."+obdata.toString());
	                        }
	                        i++;
	                    
	                }
	                if(fieldNamesIpd.size()>0)
	                {
	                	i=0;
	                	StringObj.append(",");
		                for(Iterator it=fieldNamesIpd.iterator(); it.hasNext();)
		                {
		                    //generating the dyanamic query based on selected fields
		                        Object obdata=(Object)it.next();
		                        if(obdata!=null && !obdata.toString().equalsIgnoreCase("id"))
		                        {
		                            if(i<fieldNamesIpd.size()-1)
		                            	StringObj.append("rating."+obdata.toString()+",");
		                            else
		                            	StringObj.append("rating."+obdata.toString());
		                        }
		                        i++;
		                    
		                }
	                }
	             //   StringObj.append(b);
	                StringObj.append(" from feedbackdata as feed");
	                
	                StringObj.append(" inner join feedback_paper_rating as rating on rating.max_id_feeddbackdata=feed.id");
	                
	                
	                
	                StringObj.append(" where mode ='Paper'  ");
		 
		    	if(getTitles()!=null)
		    	{
		    		headerdata=titles.split(",");
		    	}
		    }
		    else if(getTableName()!=null && !(getTableName().equalsIgnoreCase("-1")))
		    {
		    	headerdata=titles.split(",");
		    	StringObj.append("select ");
			    StringObj.append(titles);
		    	StringObj.append(" "+" from"+ " "+getTableName()+" where id !=0 "); 
		    }
		    else
		    {
		    	headerdata=titles.split(",");
		    	StringObj.append("select ");
			    StringObj.append(titles);
		    	StringObj.append(" "+" from"+ " "+" feedbackdata where id != 0"); 
		    }
	    	
		    
		    
		    if(getLevel()!=null)
			{
				if((getLevel().equalsIgnoreCase("Yes") || getLevel().equalsIgnoreCase("No")))
				{
					StringObj.append(" && targetOn='"+getLevel()+"'");
				}
			}
		    
		    if(getFromDate()!=null && getToDate()!=null && !getFromDate().equalsIgnoreCase("") && !getToDate().equalsIgnoreCase(""))
			{
		    	StringObj.append(" && openDate between '"+DateUtil.convertDateToUSFormat(getFromDate())+"' and '"+DateUtil.convertDateToUSFormat(getToDate())+"'");
			}
		    
		    if(getPatType()!=null && !getPatType().equalsIgnoreCase("-1"))
		    {
		    	StringObj.append(" && compType='"+getPatType()+"'");
		    }
		    
		    if(getSpec()!=null && !getSpec().equalsIgnoreCase("-1"))
		    {
		    	StringObj.append(" && centreName='"+getSpec()+"'");
		    }
		    
		    if(getDocName()!=null && !getDocName().equalsIgnoreCase("-1"))
		    {
		    	StringObj.append(" && serviceBy='"+getDocName()+"'");
		    }
		    
		    if(getFeedBack()!=null && !getFeedBack().equalsIgnoreCase("-1"))
		    {
		    	if(getFeedBack().equalsIgnoreCase("Positive"))
		    	{
		    		StringObj.append(" && targetOn='Yes'");
		    	}
		    	else if(getFeedBack().equalsIgnoreCase("Negative"))
		    	{
		    		StringObj.append(" && targetOn='No'");
		    	}
		    }
		    
		    System.out.println("Download Querry is as "+StringObj);
		    
		    
	    	try
	    	{
				 Contactdata=cbt.executeAllSelectQuery(StringObj.toString(),connectionSpace);
				}
				catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
	    	}
	           if (downloadType != null && downloadType.equals("downloadExcel")) {
				  try {
					  DownldeExcelFeedback writerexcel = new DownldeExcelFeedback();
					  CreateFolderOs crtobjos=new CreateFolderOs();
						downloadFile = writerexcel.createExcel(getText("Feedback Details"), getText("downloadSheet"), getText("Paper Feedback Excel Report"), Contactdata, headerdata, "productdetails", crtobjos.createUserDir("downloadR"));	
						
						fileInputStream=new FileInputStream(new File(downloadFile));
						
						downloadFile=downloadFile.substring(downloadFile.lastIndexOf("//")+2, downloadFile.length());
						
						
			         }catch (Exception e){
			        	 e.printStackTrace();
			          }
	    	}else{addActionMessage("No Result Found! due to Fields selection");}}
	    	catch (Exception e) {
	    		e.printStackTrace();
				// TODO: handle exception
			}
         return SUCCESS;
	    }
	    
	    public String downloadContactformate(){
			   String[] titelheader=null;
			   String returnResult=ERROR;
			   String[] headerdata=null;
	    	try{
	    	if(titles!=null){	
	    		headerdata=titles.split(",");
	    		DownldeExcelFeedback excelformate = new DownldeExcelFeedback();
						downloadFile = excelformate.createExcelformate(getText("Conatct Details"), getText("downloadSheet"), headerdata, "producttdetails", getText("C:/excelreport"));	
						fileInputStream = new FileInputStream(downloadFile);
						 if (downloadFile != null && !downloadFile.equals("")) {
								downloadFile = downloadFile.substring(downloadFile.lastIndexOf(File.separator));
								returnResult = SUCCESS;
							}else{
								addActionError("No Result Found! due to Fields selection");
							}
						 }
	    	     }
	    	catch (Exception e) {
				// TODO: handle exception
			}
 		return returnResult;
	    }
	    	
	    
		public String getTitles() {
			return titles;
		}

		public void setTitles(String titles) {
			this.titles = titles;
		}
		public String getDownloadType() {
			return downloadType;
		}
		public void setDownloadType(String downloadType) {
			this.downloadType = downloadType;
		}
		public String getDownloadFile() {
			return downloadFile;
		}
		public void setDownloadFile(String downloadFile) {
			this.downloadFile = downloadFile;
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

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		public String getFbtype() {
			return fbtype;
		}
		public void setFbtype(String fbtype) {
			this.fbtype = fbtype;
		}

		public String getFeedBack() {
			return feedBack;
		}

		public void setFeedBack(String feedBack) {
			this.feedBack = feedBack;
		}

		public String getDocName() {
			return docName;
		}

		public void setDocName(String docName) {
			this.docName = docName;
		}

		public String getSpec() {
			return spec;
		}

		public void setSpec(String spec) {
			this.spec = spec;
		}

		public String getPatType() {
			return patType;
		}

		public void setPatType(String patType) {
			this.patType = patType;
		}
		
}
