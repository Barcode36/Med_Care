package com.Over2Cloud.ctrl.Signup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.DistributedTableInfo;
import com.Over2Cloud.CommonClasses.GenerateUUID;
import com.Over2Cloud.CommonClasses.IPAddress;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.CommonClasses.RandomNumberGenerator;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.InstantCommunication.MsgMailCommunication;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.Rnd.RandomStringGenerator;
import com.Over2Cloud.ctrl.Setting.SingleSpaceCtrl;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
import com.Over2Cloud.modal.dao.imp.signup.BeanCountry;
import com.Over2Cloud.modal.dao.imp.signup.Industry;
import com.Over2Cloud.modal.dao.imp.signup.SendHttpSMS;
import com.Over2Cloud.modal.dao.imp.signup.signupImp;
import com.Over2Cloud.modal.db.Setting.DemoClientSpace;
import com.Over2Cloud.modal.db.Setting.DemoSetUpHelper;
import com.Over2Cloud.modal.db.commom.Smtp;
import com.Over2Cloud.modal.db.signup.ClientUserAccount;
import com.Over2Cloud.modal.db.signup.JobFunctionalArea;
import com.Over2Cloud.modal.db.signup.Registation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class SiupAction extends ActionSupport implements Preparable,
		ServletRequestAware {
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(SiupAction.class);
	
	Map session = ActionContext.getContext().getSession();
	String uName = (String) session.get("uName");
	String encryptedID=(String)session.get("encryptedID");
	private List<JobFunctionalArea> jobFunctionalArea = new ArrayList<JobFunctionalArea>();
	private List<BeanCountry> countrylist = new ArrayList<BeanCountry>();
	private List<Industry> industrylist = new ArrayList<Industry>();
	public HttpServletRequest request;
	private String regName, emailaddress, orgnizationName, accountType,
			companyType, industry, regAddress, city, country, brief,
			j_captcha_response;
	private boolean ageryterm;
	private String mobile = "";
	private String pincode = "";
	private String companysize;
	private String jobfunctionalArea;
	private String jobtitle;
	private String bPhonenumber;
	private String accountid;
	private String newpasswordc;
	private String confirmpassword;
	private String accountID;
	private String userName;
	private String mobileNo;
	private String id;
	private String name;
	private boolean status;
	private String emailId; 
	private String emailID;
	private String password;
	private String server;
	private String port;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void prepare() throws Exception {
		//System.out.println( ">>>>>>>>>>>>>>>");

		// Find out the Value Of Job Functional Area
		signupImp ob1 = new signupImp();
		List jobfunctionalArea = ob1.getalljobFunctionalArea();
		if (jobfunctionalArea != null && jobfunctionalArea.size() > 0) {
			for (Iterator iterator = jobfunctionalArea.iterator(); iterator
					.hasNext();) {
				Object[] objectCol = (Object[]) iterator.next();
				JobFunctionalArea jobfunction = new JobFunctionalArea();
				if (objectCol[0] == null) {
					jobfunction.setId(0);
				} else {
					jobfunction
							.setId(Integer.parseInt(objectCol[0].toString()));
				}
				if (objectCol[1] == null) {
					jobfunction.setJobfunction("NA");
				} else {
					jobfunction.setJobfunction((objectCol[1]).toString());
				}
				jobFunctionalArea.add(jobfunction);
			}
			Collections.sort(jobFunctionalArea, new JobFunctionalArea());
			setJobFunctionalArea(jobFunctionalArea);
		}

		List countrylist1 = ob1.getallcontry();
		if (countrylist1 != null && countrylist1.size() > 0) {
			for (Iterator iterator = countrylist1.iterator(); iterator
					.hasNext();) {
				Object[] objectCol = (Object[]) iterator.next();
				BeanCountry beancountry = new BeanCountry();
				if (objectCol[0] == null) {
					beancountry.setContryName("NA");
				} else {
					beancountry.setContryName(objectCol[0].toString());
				}
				if (objectCol[1] == null) {
					beancountry.setIso_code("NA");
				} else {
					beancountry.setIso_code((objectCol[1]).toString());
				}
				countrylist.add(beancountry);
			}
			Collections.sort(countrylist, new BeanCountry());
			setCountrylist(countrylist);
		}
		// Find out the value of all Counter.....
		List Industrylist = ob1.getallindustry();
		if (Industrylist != null && Industrylist.size() > 0) {
			for (Iterator iterator = Industrylist.iterator(); iterator
					.hasNext();) {
				Object[] objectCol = (Object[]) iterator.next();
				Industry beanindustry = new Industry();
				if (objectCol[0] == null) {
					beanindustry.setIndustryname("NA");
				} else {
					beanindustry.setIndustryname(objectCol[0].toString());
				}
				if (objectCol[1] == null) {
					beanindustry.setIndustryid("NA");
				} else {
					beanindustry.setIndustryid((objectCol[1]).toString());
				}
				industrylist.add(beanindustry);
			}
			Collections.sort(industrylist, new Industry());
			setIndustrylist(industrylist);
		}
	}

	

	

	

	@SuppressWarnings( { "unchecked", "static-access" })
	public String Signupregistation()
	{
		try
		{
			String UnqueMessageid = new GenerateUUID().UUID().toString();
			int uniqueid = new RandomNumberGenerator().getNDigitRandomNumber(8);
			CommonforClass eventDao = new CommanOper();

			// checking wheather the email id exist or not if email id exist
			// then error message for account exist shown
			boolean existemailid = signupImp.checkEmailID(getEmailaddress());

			
			// if the account type is demo
			if (existemailid)
			{
				if (getAccountType().equalsIgnoreCase("d")) 
				{
					System.out.println("I am demo User ");
					Registation ob1 = new Registation(getCity(), isAgeryterm(),getJ_captcha_response(), getMobile(),getEmailaddress(), getCountry(), DateUtil.currentdatetime(), getIndustry(),getOrgnizationName(), getCompanyType(),getPincode(), getRegAddress(), getRegName(),getBrief(), getAccountType(), Integer.toString(uniqueid), UnqueMessageid, "D","WEB", "over2cloud", getCompanysize(),getJobfunctionalArea(), getJobtitle(),getBPhonenumber());
					if (eventDao.addDetails(ob1)) 
					{
						log.info("Over2Cloud::>> Class:SiupAction >> Method::>> Signupregistation ::::: Sucessfully registered 1st Step");

						Mailtest mt = new Mailtest();
						StringBuilder mailtext = new StringBuilder("");
						String ip = new IPAddress().getIPAddress();
						System.out.println("IP is as "+ip);
						String UrlMail = "http://" + ip+ request.getContextPath()+ "/linkverifyForDemo?um=" + UnqueMessageid+ "&kcf=" + getJ_captcha_response() + "&id="+ uniqueid + "&uid=" + ob1.getId();
						List<Smtp> Ob3 = (List<Smtp>) eventDao.cloudgetDropdownvalue("id", Smtp.class);
						mailtext.append("<font face ='TIMESROMAN' size='3'><b>Dear "
										+ getRegName()
										+ ",<br><b><br>Please Click the below link to activated account."
										+ "<br>"
										+ UrlMail
										+ "<br><b><br>Thanks</b><br>Team Over2cloud.com<br>"
										+ "</FONT>");
						mailtext.append("<font face ='TIMESROMAN' size='2'>"
										+ "<b>Disclaimer:- </b> This is a system generated email. Please do not reply to this email."
										+ "*** This message is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged information. If you have received this message in error, please notify the sender immediately and delete this message from your system *** "
										+ "</FONT>");
						String SMSClient = "Dear "
								+ getRegName()
								+ ", Thanks for signing on Over2Cloud.com. In order complete Ur registration kindly login "
								+ getEmailaddress()
								+ ". For any issues call 9250400311. Team Over2Cloud.com ";
						String SMSDreamsol = "Hi, " + getRegName()
								+ ", Org. Name: " + getOrgnizationName()
								+ " Mobile No. " + getMobile() + ", Email ID: "
								+ getEmailaddress() + " has cleared Step 1.";
						for (Smtp h : Ob3) {
							// Mail Go for Client
							mt
									.confMailHTML(
											h.getServer(),
											h.getPort(),
											h.getEmailid(),
											h.getPwd(),
											getEmailaddress(),
											"Over2cloud.com Demo Account Varification "
													+ DateUtil
															.getCurrentDateIndianFormat(),
											mailtext.toString());
							// Mail Go to DREAMsOL PANNEL
							mt
									.confMailHTML(
											h.getServer(),
											h.getPort(),
											h.getEmailid(),
											h.getPwd(),
											"support@dreamsol.biz",
											"Demo Account Request "
													+ DateUtil
															.getCurrentDateIndianFormat(),
											SMSDreamsol);
						}
						// SMS Client
			//			new SendHttpSMS().sendSMS(getMobile(), SMSClient);
						addActionMessage("Registation is Sucessfully Please Check the Account to Activated");
						
						// Demo Code Starts
						
// 	code starts for Demo Client Automatic DB setup
						
						String regSignUpId=new SignUpHelperForDemo().getMaxIdRegSignUpForDemoUser();
						String domainIpName="1";
						
						System.out.println("regSignUpId is as "+regSignUpId);
						System.out.println("Domain IP is as "+domainIpName);
						
						if(regSignUpId!=null)
						{
							String dbbname="clouddb";
							String ipAddress=null;
							String username=null;
							String paassword=null;
							String port=null;
							DistributedTableInfo tdi = new DistributedTableInfo();
							LoginImp ob2=new LoginImp();
							List singleSpceServer=ob2.GetClientspace("1");
				       		 for (Object c : singleSpceServer) {
				    				Object[] dataC = (Object[]) c;
				    				ipAddress=dataC[0].toString();
				    				username=dataC[2].toString();
				    				paassword=dataC[3].toString();
				    				port=dataC[1].toString();
				       		 }
				       		AllConnection Conn=new ConnectionFactory(dbbname,ipAddress,username,paassword,port);
					        AllConnectionEntry Ob1=Conn.GetAllCollectionwithArg();
					        SessionFactory sessfactNew=Ob1.getSession();
							String data[]=regSignUpId.split(",");
							SingleSpaceCtrl se=new SingleSpaceCtrl();
							Mailtest mtt = new Mailtest();
							List<Smtp> mailList = (List<Smtp>) eventDao.cloudgetDropdownvalue("id", Smtp.class);
							
							for(String H:data)
							{
								if(H!=null && !H.trim().equalsIgnoreCase(""));
								{
									//setting the mapping of client space with demo server for DB
									
									/**
									 * Creating client demo account
									 */
									se.setUpClientDB(H.trim()+"_demo_clouddb", sessfactNew);
									/**
									 * Creating client demo account
									 */
									
									signupImp.updateDemoAccountFlag(H.trim(),"2");//updating flag of demo client from 1 to 2
									// Orgnization lavel Data Done
									//org_name,org_type,industry,city,country,pincode,contactNo,contact_emailid
									List orgData=DemoSetUpHelper.getDemoIdDetails(Integer.parseInt(H.trim()));
									for(Iterator it=orgData.iterator();it.hasNext();)
									{
										Object []objectCol=(Object[])it.next();
										
										List<InsertDataTable> Tablecolumesaaa = new ArrayList<InsertDataTable>();
										InsertDataTable InsOb = new InsertDataTable();
										InsOb.setColName("companyIcon");
										InsOb.setDataName("NA");
										Tablecolumesaaa.add(InsOb);

										if(objectCol[0]!=null)
										{
											InsOb = new InsertDataTable();
											InsOb.setColName("companyName");
											InsOb.setDataName(objectCol[0].toString());
											Tablecolumesaaa.add(InsOb);
										}
										
										if(objectCol[1]!=null)
										{
											InsOb = new InsertDataTable();
											InsOb.setColName("companyRegCity");
											InsOb.setDataName(objectCol[1].toString());
											Tablecolumesaaa.add(InsOb);
										}
										
										if(objectCol[2]!=null)
										{
											InsOb = new InsertDataTable();
											InsOb.setColName("companyRegCountry");
											InsOb.setDataName(objectCol[2].toString());
											Tablecolumesaaa.add(InsOb);
										}
										
										if(objectCol[3]!=null)
										{
											InsOb = new InsertDataTable();
											InsOb.setColName("companyRegPinCode");
											InsOb.setDataName(objectCol[3].toString());
											Tablecolumesaaa.add(InsOb);
										}
										

										if(objectCol[4]!=null)
										{
											InsOb = new InsertDataTable();
											InsOb.setColName("companyRegContact1");
											InsOb.setDataName(objectCol[4].toString());
											Tablecolumesaaa.add(InsOb);
										}

										InsOb = new InsertDataTable();
										InsOb.setColName("datetime");
										InsOb.setDataName(DateUtil.currentdatetime());
										Tablecolumesaaa.add(InsOb);
										
										tdi.insertDataInDistributedTable(H.trim()+"_demo_clouddb", "organization_level1",Tablecolumesaaa, sessfactNew);
										Thread.sleep(300);
										
										/**
										 * Sending mail to the demo client with notification about account creation 
										 */
										if(objectCol[5]!=null)
										{
											String emailID=objectCol[5].toString();
											//System.out.println("emailID  "+emailID);
											StringBuilder mailtext1 = new StringBuilder("");
											mailtext1.append("<font face ='TIMESROMAN' size='3'><b>Dear ,<br><b><br>Your demo account is prepared." +
													"<br>Kindly login into Over2cloud.com with your login detailes received in previous mail."
													+"<b><br>Thanks</b><br>Team Over2cloud.com<br>"+ "</FONT>");
											mailtext1.append("<font face ='TIMESROMAN' size='2'>"
													+ "<b>Disclaimer:- </b> This is a system generated email. Please do not reply to this email."
													+ "*** This message is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged information. If you have received this message in error, please notify the sender immediately and delete this message from your system *** "
													+ "</FONT>");
											if(mailList!=null)
											{
												for (Smtp h : mailList) {
													// Mail Go for Client
													mtt.confMailHTML(h.getServer(),h.getPort(),h.getEmailid(),h.getPwd(),emailID,"Over2cloud.com Demo Account Prepared "+ DateUtil.getCurrentDateIndianFormat(),mailtext1.toString());
												}
											}
										}
										
									}
									
									DemoClientSpace dc=new DemoClientSpace();
									dc.setClienID(H.trim());
									dc.setServerId(domainIpName);
									eventDao.addDetails(dc);
								}
							}
						
						}
						
						
						
						
						
						
						
						
						
						// demo code ends
						
						return SUCCESS;
					} else {
						addActionError("!! OOPS there is a problem In Signup Please Try Again!!!");
						return ERROR;
					}
				} else {
					// other then the demo account
					
					System.out.println("Hello i am in else block");
					
					Registation ob1 = new Registation(getCity(), isAgeryterm(),
							getJ_captcha_response(), getMobile(),
							getEmailaddress(), getCountry(), DateUtil
									.currentdatetime(), getIndustry(),
							getOrgnizationName(), getCompanyType(),
							getPincode(), getRegAddress(), getRegName(),
							getBrief(), getAccountType(), Integer
									.toString(uniqueid), UnqueMessageid, "D",
							"WEB", "over2cloud", getCompanysize(),
							getJobfunctionalArea(), getJobtitle(),
							getBPhonenumber());
					if (eventDao.addDetails(ob1)) {
						log
								.info("Over2Cloud::>> Class:SiupAction >> Method::>> Signupregistation ::::: Sucessfully registered 1st Step");

						Mailtest mt = new Mailtest();
						StringBuilder mailtext = new StringBuilder("");
						String ip = new IPAddress().getIPAddress();
						String UrlMail = "http://" + ip
								+ request.getContextPath() + "/linkverify?um="
								+ UnqueMessageid + "&kcf="
								+ getJ_captcha_response() + "&id=" + uniqueid
								+ "&uid=" + ob1.getId();
						List<Smtp> Ob3 = (List<Smtp>) eventDao
								.cloudgetDropdownvalue("id", Smtp.class);
						mailtext
								.append("<font face ='TIMESROMAN' size='3'><b>Dear "
										+ getRegName()
										+ ",<br><b><br>Please Click the below link to activated account."
										+ "<br>"
										+ UrlMail
										+ "<br><b><br>Thanks</b><br>Team Over2cloud.com<br>"
										+ "</FONT>");
						mailtext
								.append("<font face ='TIMESROMAN' size='2'>"
										+ "<b>Disclaimer:- </b> This is a system generated email. Please do not reply to this email."
										+ "*** This message is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged information. If you have received this message in error, please notify the sender immediately and delete this message from your system *** "
										+ "</FONT>");
					//	System.out.println("Account Confirmation Mail:"+mailtext);
						String SMSClient = "Dear "
								+ getRegName()
								+ ", Thanks for signing on Over2Cloud.com. In order complete Ur registration kindly login "
								+ getEmailaddress()
								+ ". For any issues call 9250400311. Team Over2Cloud.com ";
						String SMSDreamsol = "Hi, " + getRegName()
								+ ", Org. Name: " + getOrgnizationName()
								+ " Mobile No. " + getMobile() + ", Email ID: "
								+ getEmailaddress() + " has cleared Step 1.";
						for (Smtp h : Ob3) {
							// Mail Go for Client
							mt
									.confMailHTML(
											h.getServer(),
											h.getPort(),
											h.getEmailid(),
											h.getPwd(),
											getEmailaddress(),
											"Over2cloud.com Verified Your Account "
													+ DateUtil
															.getCurrentDateIndianFormat(),
											mailtext.toString());
							// Mail Go to DREAMsOL PANNEL
							mt
									.confMailHTML(
											h.getServer(),
											h.getPort(),
											h.getEmailid(),
											h.getPwd(),
											"support@dreamsol.biz",
											"Sign Up Step 1 Process Completed On "
													+ DateUtil
															.getCurrentDateIndianFormat(),
											SMSDreamsol);
						}
						// SMS Client
						new SendHttpSMS().sendSMS(getMobile(), SMSClient);
						// DreamSol SMS Go
						new SendHttpSMS().sendSMS("9250400311", SMSDreamsol);
						new SendHttpSMS().sendSMS("9250400314", SMSDreamsol);
						new SendHttpSMS().sendSMS("9250400315", SMSDreamsol);
						log
								.info("Over2Cloud::>> Class:SiupAction >> Method::>> Signupregistation ::::: Sucessfully Mail Go to User");
						addActionMessage("Registation is Sucessfully Please Check the Account to Activated");
						return SUCCESS;
					} else {
						addActionError("!! OOPS there is a problem In Signup Please Try Again!!!");
						return ERROR;
					}
				}
			} else {
				Mailtest mt = new Mailtest();
				StringBuilder mailtext = new StringBuilder("");
				List<Smtp> Ob3 = (List<Smtp>) eventDao.cloudgetDropdownvalue(
						"id", Smtp.class);
				mailtext
						.append("<font face ='TIMESROMAN' size='3'><b>Dear "
								+ getRegName()
								+ ",<br><b><br>Sorry your email id is already registered for demo or regular account, if you had forgotten your password please click on forget password link."
								+ "<br><b><br>Thanks</b><br>Team Over2cloud.com<br>"
								+ "</FONT>");
				mailtext
						.append("<font face ='TIMESROMAN' size='2'>"
								+ "<b>Disclaimer:- </b> This is a system generated email. Please do not reply to this email."
								+ "*** This message is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged information. If you have received this message in error, please notify the sender immediately and delete this message from your system *** "
								+ "</FONT>");
				for (Smtp h : Ob3) {
					// Mail Go for Client
					mt.confMailHTML(h.getServer(), h.getPort(), h.getEmailid(),
							h.getPwd(), getEmailaddress(),
							"Over2cloud.com Account already exist "
									+ DateUtil.getCurrentDateIndianFormat(),
							mailtext.toString());
					// Mail Go to DREAMsOL PANNEL
				}
				addActionError("Email Id already Exist!!!");
				return ERROR;
			}

		} catch (Exception e) {
			e.printStackTrace();
			log
					.error(
							"Over2Cloud::>> Class:SiupAction >> Method::>> Signupregistation For Registation Purpose",
							e);
			return ERROR;
		}
	}

	@SuppressWarnings("unchecked")
	public String forgetpwd() {
		try {
			String AccountInfo[] = getAccountid().split("-");
			ClientUserAccount CUA = new signupImp().getClientUserAccountObject(
					Integer.parseInt(AccountInfo[1].trim().toString()),
					AccountInfo[0].trim().toString());
			CommonforClass eventDao = new CommanOper();
			if (CUA.getId() != 0) {
				if (getNewpasswordc().equals(getConfirmpassword())) {
					Registation RegistObj = new signupImp()
							.getRegistationObject(CUA.getUid(), CUA
									.getAccountid(), CUA.getConfim_key());
					Mailtest mt = new Mailtest();
					StringBuilder mailtext = new StringBuilder("");
					List<Smtp> Ob3 = (List<Smtp>) eventDao
							.cloudgetDropdownvalue("id", Smtp.class);
					mailtext
							.append("<font face ='TIMESROMAN' size='3'><b>Dear "
									+ RegistObj.getOrg_Registation_name()
									+ ",<br><b><br>Please Click the below link to Authenticate the Account"
									+ "New Password='"+newpasswordc+"'"
									+ "<br><b><br>Thanks</b><br>Team Over2cloud.com<br>"
									+ "</FONT>");
					mailtext
							.append("<font face ='TIMESROMAN' size='2'>"
									+ "<b>Disclaimer:- </b> This is a system generated email. Please do not reply to this email."
									+ "*** This message is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged information. If you have received this message in error, please notify the sender immediately and delete this message from your system *** "
									+ "</FONT>");
					for (Smtp h : Ob3) {
						
						mt
								.confMailHTML(
										h.getServer(),
										h.getPort(),
										h.getEmailid(),
										h.getPwd(),
										RegistObj.getContact_emailid(),
										"Password Varification !!!! Please Click to update Your Password Sucessfully"
												+ DateUtil
														.getCurrentDateIndianFormat(),
										mailtext.toString());
					}
					CUA.setRequestPwd(Cryptography.encrypt(getNewpasswordc(),
							DES_ENCRYPTION_KEY));
					CUA.setIsconfirmlink("Y");
					if (eventDao.UpdateDetails(CUA)) {
						return SUCCESS;
					}
				} else {
					// Doing Work on the JSP For Atentication Logic.
					addActionError("Password is Not Match Please Try Again!!!");
					return "errorMeaasge";
				}

			} else {
				return ERROR;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ERROR;
	}
	
	@SuppressWarnings({ "rawtypes", "static-access" })
	public String newPassReq()
	{
		if(getAccountID()!=null && (mobileNo!=null || getEmailId()!=null ))
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				SessionFactory connection = new ConnectionHelper().getSessionFactory(getAccountID());
				String query="SELECT userAcc.id AS userAccountID,empName FROM employee_basic  AS emp LEFT JOIN useraccount AS userAcc ON  userAcc.id=emp.useraccountid WHERE emp.emailIdOne='"+getEmailId()+"' OR emp.mobOne='"+mobileNo+"'";
				List data=cbt.executeAllSelectQuery(query, connection);
			
				if (data!=null && !data.isEmpty()) 
				{
					Object obj[]=(Object[])data.get(0);
					if (obj[0]!=null && !obj[0].toString().equalsIgnoreCase("")) 
					{
						newpasswordc= new RandomStringGenerator().generateRandomString((short) 'R', 10);
						int updatedPass=cbt.executeAllUpdateQuery("UPDATE useraccount SET password='"+Cryptography.encrypt(getNewpasswordc(),DES_ENCRYPTION_KEY)+"' WHERE id='"+obj[0].toString()+"'",connection);
						
						if(updatedPass!=0 && updatedPass>0)
						{
							CommunicationHelper CH=new CommunicationHelper();
							if (mobileNo!=null)
							{
								String msg="Dear "+obj[1].toString()+", as per your request for login to Application your new password is: "+newpasswordc+". For any more assistance contact our helpdesk. Thanks.";
								CH.addMessage(obj[1].toString(), "", mobileNo, msg, "",  "Pending", "0",  "Common", connection);
							}
							if(getEmailId()!=null && !getEmailId().equalsIgnoreCase(""))
							{
								String mailBody=getMailBodyForResetPass(obj[1].toString(),newpasswordc);
								if(mailBody!=null)
								{
								    CH.addMail(obj[1].toString(), "", getEmailId(), "Forgot Password Details", mailBody, "","Pending", "0", "", "Common", connection);
								}
							}
							addActionMessage("Your New Password sent successfully !!!");
						}
					}
					else
					{
						addActionMessage("No User Record Found Kindly Check You have created your account or not !!!!!");
					}
				}
				else
				{
					addActionMessage("There is some error in Mobile NO Or Email No Search Found !!!!!");
				}
				
				
				/*List dataList = cbt.executeAllSelectQuery( "select id,name  from useraccount where mobileNo='"+mobileNo+"'",connection);
				if(dataList!=null && dataList.size()>0)
				{
					for (Iterator iterator = dataList.iterator(); iterator .hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						id=object[0].toString();
						name=object[1].toString();
					}
					
					if(id!=null && !id.equalsIgnoreCase(""))
					{
						List dataList2 = cbt.executeAllSelectQuery("Select emailIdOne from employee_basic where useraccountid='"+id+"'",connection);
						if(dataList2!=null && dataList2.size()>0)
						{
							newpasswordc= new RandomStringGenerator().generateRandomString((short) 'R', 10);
							int updatedPass=cbt.executeAllUpdateQuery("UPDATE useraccount SET password='"+Cryptography.encrypt(getNewpasswordc(),DES_ENCRYPTION_KEY)+"'WHERE mobileNo='"+mobileNo+"'",connection);
							String msg="Dear "+name+", as per your request for login to www.over2cloud.com your new password is: "+newpasswordc+". For any more assistance contact our helpdesk. Thanks.";
							if(updatedPass!=0 && updatedPass>0)
							{
								MsgMailCommunication communctn=new MsgMailCommunication();
								communctn.addScheduledMessage(name, "Common ",mobileNo, msg, "", "Pending", "0", "Common",DateUtil.getCurrentDateUSFormat(),DateUtil.getCurrentTimewithSeconds(),connection);
								if(getEmailId()!=null && !getEmailId().equalsIgnoreCase(""))
								{
									String mailBody=getMailBodyForResetPass(name,newpasswordc);
									if(mailBody!=null)
									{
										communctn.addScheduleMail(name,"Common",getEmailId(),"Reset-Password Details",mailBody, "","Pending", "0", null,"Common",DateUtil.getCurrentDateUSFormat(),DateUtil.getCurrentTimewithSeconds(),connection);
									}
									addActionMessage("Password sent successfully");
								}
							}
						}
					}*/
				/*}
				else
				{
					addActionMessage("The mobile no entered is not matches with registered mobile no");
				}*/
				return SUCCESS;
			}
			catch (Exception e) 
			{
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}
	}
	
	public String getMailBodyForResetPass(String name,String password)
	{
		StringBuffer mailtext=new StringBuffer();
/*		mailtext.append("<font face ='TIMESROMAN' size='3'><b>"+name+" "
				+ ",<br><b><br>Find your New Password."
				+ "<b><br>New Password='"+password+"'"
				+ "<br><b><br>Thanks</b><br>Team Over2cloud.com<br>"
				+ "</FONT>");
		mailtext.append("<font face ='TIMESROMAN' size='2'>"
				+ "<b>Disclaimer:- </b> This is a system generated email. Please do not reply to this email."
				+ "*** This message is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged information. If you have received this message in error, please notify the sender immediately and delete this message from your system *** "
				+ "</FONT>");*/
		mailtext.append("<html><body><p><strong>Dear "+name+"</strong>,</p><p>Greetings from <strong>"
							+ "Team DreamSol"
							+ "</strong>!<br />Congrats, your password has been reset.</p><p><center><table border='1'>	<tr><th colspan='2'>Here are the new password details for the same</th></tr>"
							+ "	<tr><td width='200px'>New Password</td><td width='200px'>"+newpasswordc+"</td></tr>	</table></center></p><p>We recommend you to please change the password as soon as you get these details for security reasons.</p><p>Please use the application &amp; provide us your valuable feedback at any point of time by clicking on 'Feedback' link provided in the application footer.</p><p>For any issues at any point of time, please contact: <strong>"
							+ "0120-4316414</strong> &amp; also mail with details to <strong>support@dreamsol.biz</strong>.</p><p>&nbsp;</p><p><span style='font-size: smal;"
							+ "color: #993366;'><strong><span style='font-family: 'times new roman', times;'>Thanks &amp; Regards,</span></strong></span><br /><span style='font-size: small; color: #993366;'><strong><span style='font-family: 'times new roman', times;'>Support Team.</span></strong></span></p><p><span style='font-family: 'arial black', 'avant garde'; font-size: small; color: #ff9900;'>DreamSol TeleSolutions Pvt. Ltd.</span><br /><span style='color: #993366;'>An ISO 9001:2000 Certified Company...</span><br />India: C-52, Sector-2, Noida-201301<br /><span style='color: #3366ff;'><a name='www.dreamsol.biz'></a>www.dreamsol.biz | <a name='www.punchsms.com'></a>www.punchsms.com | <a name='www.mgov.in'></a>www.mgov.in | <a name='www.over2cloud.com'></a>www.over2cloud.com</span><br /><span style='color: #000080;'><strong>Innovating Business Processes via automated ICT solutions integrated with unified communications... offered over Public / Private Cloud &amp; Mobile Apps.</strong></span><br />---------------------------------------"
							+ "------------------------------------------------------------------------------<br /><span style='color: #800080;'>This e-mail may contain proprietary and confidential information and is sent for the intended recipient(s) only. If by an addressing or transmission error this mail has been misdirected, you are requested to please notify us by reply e-mail and delete this mail immediately. You are also hereby notified that any use, any form of reproduction, dissemination, copying, disclosure, modification, distribution and / or publication of this e-mail message, contents or its attachment other than by its intended recipient(s) is strictly prohibited. \"Thank you.\"</span><br />\"<span style='color: #99cc00;'>Every 3000 sheets of paper cost us a tree. Let's consider our environmental responsibility before printing this e-mail -</span> <strong><span style='color: #ff0000;'>Save paper</span></strong>.\"<br /><br /></p></body></html>");
		return mailtext.toString();
	}

	public List<JobFunctionalArea> getJobFunctionalArea() {
		return jobFunctionalArea;
	}

	public void setJobFunctionalArea(List<JobFunctionalArea> jobFunctionalArea) {
		this.jobFunctionalArea = jobFunctionalArea;
	}

	public List<BeanCountry> getCountrylist() {
		return countrylist;
	}

	public void setCountrylist(List<BeanCountry> countrylist) {
		this.countrylist = countrylist;
	}

	public List<Industry> getIndustrylist() {
		return industrylist;
	}

	public void setIndustrylist(List<Industry> industrylist) {
		this.industrylist = industrylist;
	}
	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getMobile() {
		if (mobile == null) {
			mobile = "0";
		}
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getOrgnizationName() {
		return orgnizationName;
	}

	public void setOrgnizationName(String orgnizationName) {
		this.orgnizationName = orgnizationName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPincode() {
		if (pincode == null) {
			pincode = "0";
		}
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getJ_captcha_response() {
		return j_captcha_response;
	}

	public void setJ_captcha_response(String j_captcha_response) {
		this.j_captcha_response = j_captcha_response;
	}

	public boolean isAgeryterm() {
		return ageryterm;
	}

	public void setAgeryterm(boolean ageryterm) {
		this.ageryterm = ageryterm;
	}
	public String getCompanysize() {
		return companysize;
	}

	public void setCompanysize(String companysize) {
		this.companysize = companysize;
	}

	public String getJobfunctionalArea() {
		return jobfunctionalArea;
	}

	public void setJobfunctionalArea(String jobfunctionalArea) {
		this.jobfunctionalArea = jobfunctionalArea;
	}

	public String getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public String getBPhonenumber() {
		return bPhonenumber;
	}

	public void setBPhonenumber(String phonenumber) {
		bPhonenumber = phonenumber;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getNewpasswordc() {
		return newpasswordc;
	}

	public void setNewpasswordc(String newpasswordc) {
		this.newpasswordc = newpasswordc;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}







	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}


	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}







	public String getPassword() {
		return password;
	}







	public void setPassword(String password) {
		this.password = password;
	}







	public String getServer() {
		return server;
	}







	public void setServer(String server) {
		this.server = server;
	}







	public String getPort() {
		return port;
	}







	public void setPort(String port) {
		this.port = port;
	}







	public String getEmailID() {
		return emailID;
	}







	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	
	
	
	
	
}
