package com.Over2Cloud.ctrl.Signup;

import hibernate.common.HibernateSessionFactory;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.DistributedTableInfo;
import com.Over2Cloud.CommonClasses.IPAddress;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.CommonClasses.RandomNumberGenerator;
import com.Over2Cloud.CommonClasses.RandomStringGenerator;
import com.Over2Cloud.CommonClasses.TableInfo;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.modal.dao.imp.Setting.SingleSpaceImp;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
import com.Over2Cloud.modal.dao.imp.signup.BeanApps;
import com.Over2Cloud.modal.dao.imp.signup.SendHttpSMS;
import com.Over2Cloud.modal.dao.imp.signup.signupImp;
import com.Over2Cloud.modal.db.commom.Smtp;
import com.Over2Cloud.modal.db.signup.ClientUserAccount;
import com.Over2Cloud.modal.db.signup.ProductRegistation;
import com.Over2Cloud.modal.db.signup.Registation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("deprecation")
public class VarificationLink extends ActionSupport {
	/**
	 * 
	 */
	static final Log log = LogFactory.getLog(VarificationLink.class);
	private static final long serialVersionUID = 1L;
	private List<BeanApps> appslist = new ArrayList<BeanApps>();
	public String um;
	public String kcf;
	public String id;
	public String uid;
	private String contryid;
	private String appcheckname;
	private String noOfUser;
	private String valid_from;
	private String valid_to;
	private String totalUserCount;
	private String accountid;
	private String username;
	private String password;
	private String newusername;
	private String newpassword;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private Map<String, String> appNames = null;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	private String pageFalg;
	HttpServletRequest req = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
	private InputStream inputStream;


	
	@SuppressWarnings("unchecked")
	public String LinkVarification()
	{
		try {
			List IsVarified = new signupImp().isVerified(um, kcf, id, uid);
			if (IsVarified != null && IsVarified.size() > 0)
			{
				List Isvarifiedstep1 = new signupImp().isVerifiedstep1(um, kcf,id, uid);
				if (Isvarifiedstep1 != null && Isvarifiedstep1.size() > 0) 
				{
					for (Iterator iterator = IsVarified.iterator(); iterator.hasNext();) 
					{
						Object[] objectCol = (Object[]) iterator.next();
						setContryid(objectCol[4].toString());
					}
					// Application List to be added on
					signupImp ob1 = new signupImp();
					List AppList = ob1.getAllApplication();
					if (AppList != null && AppList.size() > 0) 
					{
						for (Iterator iterator = AppList.iterator(); iterator.hasNext();) 
						{
							Object[] objectCol = (Object[]) iterator.next();
							BeanApps beanApps = new BeanApps();
							if (objectCol[0] == null)
							{
								beanApps.setApp_name("NA");
							}
							else
							{
								beanApps.setApp_name(objectCol[0].toString());
							}
							if (objectCol[1] == null)
							{
								beanApps.setApp_code("NA");
							}
							else
							{
								beanApps.setApp_code((objectCol[1]).toString());
								// setting the package as per application
								List pckList = ob1.getAllPackageWithPCode(objectCol[1].toString());
								if (pckList != null && pckList.size() > 0)
								{
									Map<Integer, String> packList = new HashMap<Integer, String>();
									for (Iterator iteratorPack = pckList.iterator(); iteratorPack.hasNext();) 
									{
										Object[] objectPack = (Object[]) iteratorPack.next();
										packList.put((Integer) objectPack[0],objectPack[3].toString()+ " User");
									}
									beanApps.setPackageValue(packList);
								}
								else 
								{
									Map<Integer, String> packList = new HashMap<Integer, String>();
									packList.put(-2, "Reuqest For Pack");
									beanApps.setPackageValue(packList);
								}

							}
							appslist.add(beanApps);
						}
						Collections.sort(appslist, new BeanApps());
						setAppslist(appslist);
					}

					// mapped package based on product

					return SUCCESS;
				} else {
					return "signupprocesscompleted";
				}

			} else {
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Over2Cloud::>> Class:VarificationLink >> Method::>> LinkVarification For User varification Problem Occure In Click button",
							e);
			
		}
		return ERROR;

	}

	public String ProductIntrest()
	{
		try
		{
			String AppCheck[] = getAppcheckname().split(", ");
			StringBuffer appscode = new StringBuffer();
			for (String h : AppCheck) {
				if (h.trim() != "" && !h.equalsIgnoreCase("")) {
					appscode.append(h.trim());
					appscode.append("#");
				}
			}
			StringBuffer pckdId = new StringBuffer();
			String Usercount[] = getNoOfUser().split(", ");
			int i = 1;
			for (String h : Usercount) {
				if (h.trim() != "" && !h.equalsIgnoreCase("")) {
					if (i < Usercount.length)
						pckdId.append("'" + h + "',");
					else
						pckdId.append("'" + h + "'");
					i++;
				}
			}
			List verificationList = new signupImp().getAllPackageWith(pckdId
					.toString());
			pckdId.delete(0, pckdId.length());
			for (Iterator itrt = verificationList.iterator(); itrt.hasNext();) {
				Object objectCol = (Object) itrt.next();
				pckdId.append(objectCol.toString() + "#");
			}

			StringBuffer validFrom = new StringBuffer();
			String validFromDate[] = getValid_from().split(", ");
			for (String h : validFromDate) {
				if (h.trim() != "" && !h.equalsIgnoreCase("")) {
					validFrom.append(h.trim());
					validFrom.append("#");
				}
			}
			StringBuffer validTo = new StringBuffer();
			String validToDate[] = getValid_to().split(", ");
			for (String h : validToDate) {
				if (h.trim() != "" && !h.equalsIgnoreCase("")) {
					validTo.append(h.trim());
					validTo.append("#");
				}
			}
			boolean status=false;
			CommonforClass eventDao = new CommanOper();
			ProductRegistation ob1 = new ProductRegistation(id, kcf, DateUtil
					.currentdatetime(), appscode.toString(), pckdId.toString(),
					uid, um, validFrom.toString(), validTo.toString(), contryid);
			if (eventDao.addDetails(ob1)) {
				setUm(um);
				setKcf(kcf);
				setId(id);
				setContryid(contryid);
				String Randompwd = new RandomNumberGenerator().Random8CharPwd();
				short type = 'k';
				String randomuser = RandomStringGenerator.generateRandomString(
						type, 5);
				setPassword(Randompwd);
				ClientUserAccount CUA =null;
				if(getPageFalg()!=null && getPageFalg().equalsIgnoreCase("1"))
				{
					CUA = new ClientUserAccount(id, kcf, contryid,
							Randompwd, um, randomuser,getPageFalg());
					status=eventDao.addDetails(CUA);
					signupImp.updateDemoAccountFlag(getUid(), "3");
					//updateing the client space id so it will available for generation
					int demoCLientID=CUA.getId();
					//System.out.println("demoCLientID  "+demoCLientID);
					List ChunkInfo = new LoginImp().chunkDomainName(Integer.toString(demoCLientID), CUA.getCountryid());
					
					if (ChunkInfo != null && ChunkInfo.size() > 0) {
						for (Iterator iterator1 = ChunkInfo.iterator(); iterator1
								.hasNext();) {
							Object[] objectCol1 = (Object[]) iterator1
									.next();
							Map<String, Object> wherClause = new HashMap<String, Object>();
							Map<String, Object> condtnBlock = new HashMap<String, Object>();
							// Which Value Update
							wherClause.put("isSpace", "Yes");
							// Condition For Update
							// 10001:localhost:10001_10050
							condtnBlock.put("accountid", demoCLientID);
							String dbbname = "clouddb", ipAddress = null, username = null, paassword = null, port = null;
							ipAddress = objectCol1[2].toString();
							port = objectCol1[3].toString();
							username = objectCol1[4].toString();
							paassword = objectCol1[5].toString();
							AllConnection Conn = new ConnectionFactory(dbbname, ipAddress, username,paassword, port);
							AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
							SessionFactory sessfactNew = Ob1.getSession();
							new TableInfo().updateTableColumn(objectCol1[0].toString().trim()+ "_"+ objectCol1[1].toString().trim()
											+ "_space_configuration",wherClause, condtnBlock,sessfactNew);
						}
					}
				}
				else
				{
					CUA = new ClientUserAccount(id, kcf, contryid,
							Randompwd, um, randomuser);
					status=eventDao.addDetails(CUA);
				}
					
				if (status) {
					setAccountid(CUA.getCountryid() + "-" + CUA.getId());
					setUsername(randomuser);
					setPassword(Randompwd);
				} else {
					return ERROR;
				}
				if(getPageFalg()!=null && getPageFalg().equalsIgnoreCase("1") && status)
				{
					return "demoCompleted";
				}
				return SUCCESS;
			}
			return SUCCESS;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
	}

	@SuppressWarnings( { "unchecked", "static-access" })
	public String clientinformationDone()
	{
		System.out.println("Inside clientinformationDone");
		try {
			String displayClientid = getAccountid();
			String asd[] = getAccountid().split("-");
			setAccountid(asd[1].toString());
			setContryid(asd[0].toString());
			if (getNewusername() == null) {
				setUsername(Cryptography.encrypt(getUsername(),
						DES_ENCRYPTION_KEY));
			} else {
				setUsername(Cryptography.encrypt(getNewusername(),
						DES_ENCRYPTION_KEY));
			}
			if (getNewpassword() == null) {
				setPassword(Cryptography.encrypt(getPassword(),
						DES_ENCRYPTION_KEY));
			} else {
				setPassword(Cryptography.encrypt(getNewpassword(),
						DES_ENCRYPTION_KEY));
			}
			ClientUserAccount OBJ = new signupImp().getClientUserAccountObject(
					Integer.parseInt(asd[1].toString()), asd[0].toString());
			if (OBJ != null) {
				OBJ.setPwd(getPassword());
				OBJ.setUsername(getUsername());
				CommonforClass eventDao = new CommanOper();
				if (eventDao.UpdateDetails(OBJ)) {
					// Send to mail of User.....
					Registation RegistObj = new signupImp()
							.getRegistationObject(um, id, kcf);
					if (RegistObj != null) {
						RegistObj.setIscomplited("C");
						if (eventDao.UpdateDetails(RegistObj)) {
							Mailtest mt = new Mailtest();
							StringBuilder mailtext = new StringBuilder("");
							List<Smtp> Ob3 = (List<Smtp>) eventDao
									.cloudgetDropdownvalue("id", Smtp.class);
							mailtext
									.append("<font face ='TIMESROMAN' size='3'><b>Dear "
											+ RegistObj
													.getOrg_Registation_name()
											+ ",<br><b><br>Your Login Details is given below ."
											+ "<br>Account Id: "
											+ displayClientid
											+ "<br>User Name: "
											+ Cryptography.decrypt(
													getUsername(),
													DES_ENCRYPTION_KEY)
											+ "<br>Password: "
											+ Cryptography.decrypt(
													getPassword(),
													DES_ENCRYPTION_KEY)
											+ "<br>URL: www.over2cloud.com"
											+ "<br><b><br>Thanks</b><br>Team Over2cloud.com<br>"
											+ "</FONT>");
							mailtext
									.append("<font face ='TIMESROMAN' size='2'>"
											+ "<b>Disclaimer:- </b> This is a system generated email. Please do not reply to this email."
											+ "*** This message is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged information. If you have received this message in error, please notify the sender immediately and delete this message from your system *** "
											+ "</FONT>");
							String SMSClient = "Dear "
									+ RegistObj.getOrg_Registation_name()
									+ ", Thanks for signing on Over2Cloud.com. Your registration process is completed; it will take 24/48 Hr to activate your requested account. For any issues call 9250400311. Team Over2Cloud.com";
							String Dreamsolsms = "Hi, "
									+ RegistObj.getOrg_Registation_name()
									+ ", Org. Name: " + RegistObj.getOrgname()
									+ " Mobile No. " + RegistObj.getContactNo()
									+ ", Email ID: "
									+ RegistObj.getContact_emailid()
									+ " has cleared Step 2.";
							for (Smtp h : Ob3) {
								mt
										.confMailHTML(
												h.getServer(),
												h.getPort(),
												h.getEmailid(),
												h.getPwd(),
												RegistObj.getContact_emailid(),
												"Congratulations !!!! Sign up Process is Completed "
														+ DateUtil
																.getCurrentDateIndianFormat(),
												mailtext.toString());
								// DreamSol Mail Go to Support Table
								mt
										.confMailHTML(
												h.getServer(),
												h.getPort(),
												h.getEmailid(),
												h.getPwd(),
												"support@dreamsol.biz",
												"Sign Up Process is Completed Organization: "
														+ RegistObj
																.getOrgname()
														+ " On "
														+ DateUtil
																.getCurrentDateIndianFormat(),
												Dreamsolsms);
							}
							// SMS Client
							new SendHttpSMS().sendSMS(RegistObj.getContactNo()
									.trim().toString(), SMSClient);
							// DreamSol SMS Go
					//		new SendHttpSMS().sendSMS("9250400311", Dreamsolsms);
						//	new SendHttpSMS().sendSMS("9250400314", Dreamsolsms);
					//		new SendHttpSMS().sendSMS("9250400315", Dreamsolsms);
							
							log.info("Over2Cloud::>> Class:SiupAction >> Method::>> Signupregistation ::::: Sucessfully Mail Go to User");
							addActionMessage("Registation is Sucessfully Please Check the Account to Activated");
							String accountid = displayClientid.substring(3,displayClientid.length()).trim();
							String isoCode = displayClientid.substring(0, 2)
									.trim();
							
							List ChunkInfo = new LoginImp().chunkDomainName(
									accountid, isoCode);
							if (ChunkInfo != null && ChunkInfo.size() > 0) {
								for (Iterator iterator1 = ChunkInfo.iterator(); iterator1
										.hasNext();) {
									Object[] objectCol1 = (Object[]) iterator1
											.next();
									Map<String, Object> wherClause = new HashMap<String, Object>();
									Map<String, Object> condtnBlock = new HashMap<String, Object>();
									// Which Value Update
									wherClause.put("isSpace", "Yes");
									// Condition For Update
									// 10001:localhost:10001_10050
									condtnBlock.put("accountid", accountid);

									String dbbname = "clouddb", ipAddress = null, username = null, paassword = null, port = null;
									ipAddress = objectCol1[2].toString();
									port = objectCol1[3].toString();
									username = objectCol1[4].toString();
									paassword = objectCol1[5].toString();
									AllConnection Conn = new ConnectionFactory(
											dbbname, ipAddress, username,
											paassword, port);
									AllConnectionEntry Ob1 = Conn
											.GetAllCollectionwithArg();
									SessionFactory sessfactNew = Ob1
											.getSession();

									boolean data = new TableInfo()
											.updateTableColumn(objectCol1[0]
													.toString().trim()
													+ "_"
													+ objectCol1[1].toString()
															.trim()
													+ "_space_configuration",
													wherClause, condtnBlock,
													sessfactNew);
									if (data) {
										if (RegistObj.getReguser()
												.equals("WEB")) {
											return SUCCESS;
										} else {
											Map<String, Object> wherClause1 = new HashMap<String, Object>();
											Map<String, Object> condtnBlock1 = new HashMap<String, Object>();
											// Which Value Update
											wherClause1.put("isStatus", "Yes");
											// Condition For Update
											// 10001:localhost:10001_10050
											condtnBlock1.put("reg_id",
													RegistObj.getId());
											AllConnection Conn1 = new ConnectionFactory(
													"localhost");
											AllConnectionEntry Ob = Conn1
													.GetAllCollectionwithArg();
											SessionFactory sessfactLocal = Ob
													.getSession();
											boolean data1 = new TableInfo()
													.updateTableColumn(
															"signup_product",
															wherClause1,
															condtnBlock1,
															sessfactLocal);
											if (data1) {
												return SUCCESS;
											} else {
												return ERROR;
											}
										}

									} else {
										return ERROR;
									}
								}
							}
							return SUCCESS;
						}
					}
				} else {
					return ERROR;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log
					.error(
							"Over2Cloud::>> Class:VarificationLink >> Method::>> clientinformationDone The User name and Pwd is Not Encript ",
							e);
		}
		return ERROR;
	}



	public String getpackageInfo() {
		String returnResult = ERROR;
		try {

			StringBuilder outputString = new StringBuilder();
			String PID = req.getParameter("PID");
			signupImp ob1 = new signupImp();
			List pckList = ob1.getPackageInfo(Integer.parseInt(PID));
			if (pckList != null && pckList.size() > 0) {
				Map<Integer, String> packList = new HashMap<Integer, String>();
				for (Iterator iteratorPack = pckList.iterator(); iteratorPack
						.hasNext();) {
					Object[] objectPack = (Object[]) iteratorPack.next();
					outputString.append(DateUtil.getCurrentDateUSFormat()
							+ ","
							+ DateUtil
									.getNextDateAfter((Integer) objectPack[0])
							+ "," + objectPack[1].toString() + " "
							+ objectPack[2].toString());
				}

				returnResult = SUCCESS;
			}
			inputStream = new StringBufferInputStream(outputString.toString());
		} catch (Exception e) {
			addActionError("Oopss there is some problem!!!");
			return ERROR;
		}

		return returnResult;
	}

	public String newPackageRequest() {
		try {
			// um="+um+"&kcf="+kcf+"&id="+id+"&uid="+uid+"&contryid="+contryid
			setUm(getUm());
			setKcf(getKcf());
			setId(getId());
			setUid(getUid());
			setContryid(getContryid());
			appNames = new LinkedHashMap<String, String>();
			signupImp ob1 = new signupImp();
			List appList = ob1.appList();
			if (appList != null && appList.size() > 0) {
				for (Iterator iteratorPack = appList.iterator(); iteratorPack
						.hasNext();) {
					Object[] objectPack = (Object[]) iteratorPack.next();
					appNames.put(objectPack[0].toString(), objectPack[1]
							.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String linkverifyForDemo() {
		try {
			
			List IsVarified = signupImp.isVerifiedDemo(um, kcf, id, uid);
			//System.out.println("IsVarified "+IsVarified.size());
			if (IsVarified != null && IsVarified.size() > 0) {
				//if the linked is not clicked then update the demoAccount flag=1 so no further use will remain for the link
				//org_reg_name,contactNo,contact_emailid
				String name=null,mob=null,email=null;
				for(Iterator it=IsVarified.iterator();it.hasNext();)
				{
					Object obj[]=(Object[])it.next();
					name=obj[0].toString();
					mob=obj[1].toString();
					email=obj[2].toString();
				}
				CommonforClass eventDao = new CommanOper();
				Mailtest mt = new Mailtest();
				StringBuilder mailtext = new StringBuilder("");
				String ip = new IPAddress().getIPAddress();
				List<Smtp> Ob3 = (List<Smtp>) eventDao.cloudgetDropdownvalue("id", Smtp.class);
				mailtext.append("<font face ='TIMESROMAN' size='3'><b>Dear "+ name+ ",<br><b><br>These are your login details."
								+ "<br>"
								+"Client ID:demo"
								+"<br>Login ID:"+email
								+"<br>Password:"+email
								+ "<br><b><br>Thanks</b><br>Team Over2cloud.com<br>"
								+ "</FONT>");
				mailtext
						.append("<font face ='TIMESROMAN' size='2'>"
								+ "<b>Disclaimer:- </b> This is a system generated email. Please do not reply to this email."
								+ "*** This message is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged information. If you have received this message in error, please notify the sender immediately and delete this message from your system *** "
								+ "</FONT>");
				for (Smtp h : Ob3) {
					// Mail Go for Client
					mt.confMailHTML(h.getServer(),h.getPort(),h.getEmailid(),h.getPwd(),email,
									"Over2cloud.com Demo Completion "+ DateUtil.getCurrentDateIndianFormat(),mailtext.toString());
				}
				signupImp.updateDemoAccountFlag(um, kcf, id, uid,"1");
				return SUCCESS;
			}
			else
			{
				return "signupprocesscompleted";
			}
		}
		catch(Exception e)
		{
			return ERROR;
		}
	}
	
	
	
	/**
	 * 
	 * ************************************DEMO ACCOUNT WORK STARTS FROM HERE
		um, kcf, id, uid
		(String uuid ,String confirm_key,String accountid,String id)
		
		um=uuid;
		kcf=confirm_key;
		id=accountid;
		uid=id;
		uuid,accountid,confirm_key,id,country
	 */
	
	public String demoToRegular() {
		try {
			
			String emailIds=userName;
			List IsVarified = new signupImp().isVerified(emailIds);//uuid,accountid,confirm_key,id,country
			if (IsVarified != null && IsVarified.size() > 0) {
				List Isvarifiedstep1 = new signupImp().isVerifiedstep1(emailIds);
				if (Isvarifiedstep1 != null && Isvarifiedstep1.size() > 0) {
					for (Iterator iterator = IsVarified.iterator(); iterator
							.hasNext();) {
						Object[] objectCol = (Object[]) iterator.next();
						setContryid(objectCol[4].toString());
						setUm(objectCol[0].toString());
						setId(objectCol[1].toString());
						setKcf(objectCol[2].toString());
						setUid(objectCol[3].toString());
						
					}
					// Application List to be added on
					signupImp ob1 = new signupImp();
					List AppList = ob1.getAllApplication();
					if (AppList != null && AppList.size() > 0) {
						for (Iterator iterator = AppList.iterator(); iterator
								.hasNext();) {
							Object[] objectCol = (Object[]) iterator.next();
							BeanApps beanApps = new BeanApps();
							if (objectCol[0] == null) {
								beanApps.setApp_name("NA");
							} else {
								beanApps.setApp_name(objectCol[0].toString());
							}
							if (objectCol[1] == null) {
								beanApps.setApp_code("NA");
							} else {
								beanApps.setApp_code((objectCol[1]).toString());
								// setting the package as per application
								List pckList = ob1
										.getAllPackageWithPCode(objectCol[1]
												.toString());
								if (pckList != null && pckList.size() > 0) {
									Map<Integer, String> packList = new HashMap<Integer, String>();
									for (Iterator iteratorPack = pckList
											.iterator(); iteratorPack.hasNext();) {
										Object[] objectPack = (Object[]) iteratorPack
												.next();
										packList.put((Integer) objectPack[0],
												objectPack[3].toString()
														+ " User");
									}
									beanApps.setPackageValue(packList);
								} else {
									Map<Integer, String> packList = new HashMap<Integer, String>();
									packList.put(-2, "Reuqest For Pack");
									beanApps.setPackageValue(packList);
								}

							}
							appslist.add(beanApps);
						}
						Collections.sort(appslist, new BeanApps());
						setAppslist(appslist);
					}

					// mapped package based on product

					return SUCCESS;
				} else {
					return "signupprocesscompleted";
				}

			} else {
				return ERROR;
			}
		} catch (Exception e) {
			log
					.error(
							"Over2Cloud::>> Class:VarificationLink >> Method::>> LinkVarification For User varification Problem Occure In Click button",
							e);
			e.printStackTrace();
		}
		return ERROR;

	}
	
	
	private String tempLAAOAAIAAD;  //encyploginID
	private String tempAAPAAWAAD;   //encypPwd
	private String tempAAAAACAAOAAUAANAANOAA;  //accountID
	
	public String beforelinkverifyDoneForDemoPwdUser()
	{
		try
		{
			String encyploginID=Cryptography.decrypt(getTempLAAOAAIAAD(), DES_ENCRYPTION_KEY);
			String encypPwd=Cryptography.decrypt(getTempAAPAAWAAD(), DES_ENCRYPTION_KEY);
			setTempLAAOAAIAAD(encyploginID);
			setTempAAPAAWAAD(encypPwd);
			setTempAAAAACAAOAAUAANAANOAA(getTempAAAAACAAOAAUAANAANOAA());
			
		}
		catch(Exception e)
		{
		
			return ERROR;
		}
		return SUCCESS;
	}
	public String linkverifyDoneForDemoPwdUser() {
		try {
			
			/*System.out.println("acount id"+getTempAAAAACAAOAAUAANAANOAA());
			System.out.println("username"+getNewusername());
			System.out.println("pwd"+getNewpassword());*/
			String asd[] = getTempAAAAACAAOAAUAANAANOAA().split("-");
			setAccountid(asd[1].toString());
			setContryid(asd[0].toString());
			
			if (getNewusername() == null) {
				setUsername(Cryptography.encrypt(getTempLAAOAAIAAD(),
						DES_ENCRYPTION_KEY));
			} else {
				setUsername(Cryptography.encrypt(getNewusername(),
						DES_ENCRYPTION_KEY));
			}
			if (getNewpassword() == null) {
				setPassword(Cryptography.encrypt(getTempAAPAAWAAD(),
						DES_ENCRYPTION_KEY));
			} else {
				setPassword(Cryptography.encrypt(getNewpassword(),
						DES_ENCRYPTION_KEY));
			}
			
			
			
			ClientUserAccount OBJ = new signupImp().getClientUserAccountObject(
					Integer.parseInt(asd[1].toString()), asd[0].toString());
			if (OBJ != null) {
				 LoginImp ob1=new LoginImp();
				OBJ.setPwd(getPassword());
				OBJ.setUsername(getUsername());
				CommonforClass eventDao = new CommanOper();
				if (eventDao.UpdateDetails(OBJ)) {
					DistributedTableInfo tdi = new DistributedTableInfo();
					String uid=OBJ.getUid();
					String accountid=OBJ.getAccountid();
					Registation tempRegsitarationAccount = new signupImp().getRegistrationAccount(uid,accountid);
					tempRegsitarationAccount.setAccountType("COA");
					eventDao.UpdateDetails(tempRegsitarationAccount);
					
					
					//inserting client purchase product details in client product table
					SessionFactory sessfactNew=null;
					List AllDataOfParticularDbUser = new SingleSpaceImp().AllRecordDataInformationCome(asd[1].toString());
					if (AllDataOfParticularDbUser != null&& AllDataOfParticularDbUser.size() > 0) {
						for (Iterator iterator = AllDataOfParticularDbUser.iterator(); iterator.hasNext();) {
							Object[] objectCol = (Object[]) iterator.next();
							
							List<InsertDataTable> Tablecolumesaaa = new ArrayList<InsertDataTable>();
							InsertDataTable InsOb = new InsertDataTable();
							InsOb.setColName("productCategory");
							InsOb.setDataName(objectCol[12].toString());
							Tablecolumesaaa.add(InsOb);

							InsOb = new InsertDataTable();
							InsOb.setColName("productuser");
							InsOb.setDataName(objectCol[13].toString());
							Tablecolumesaaa.add(InsOb);

							InsOb = new InsertDataTable();
							InsOb.setColName("DateTime");
							InsOb.setDataName(DateUtil.currentdatetime());
							Tablecolumesaaa.add(InsOb);

							InsOb = new InsertDataTable();
							InsOb.setColName("validityFrom");
							InsOb.setDataName(objectCol[10].toString());
							Tablecolumesaaa.add(InsOb);

							InsOb = new InsertDataTable();
							InsOb.setColName("validityto");
							InsOb.setDataName(objectCol[11].toString());
							Tablecolumesaaa.add(InsOb);
							
							/*
							 * 
							 * Getting client chunk space details
							 */
							 List ChunkSpace=ob1.chunkDomainName(asd[1].toString(), asd[0].toString());
							// System.out.println("ChunkSpace"+ChunkSpace);
							 if(ChunkSpace != null && !ChunkSpace.isEmpty() &&  ChunkSpace.size()>0)
		    		    	  {
		    		    		  for (Iterator iterator1 = ChunkSpace.iterator(); iterator1.hasNext();)
		    		    		   {
		    		    			        Object[] objectCol1 = (Object[]) iterator1.next();
			    		    			    List<String> colmName=new  ArrayList<String>();
			    					        colmName.add("Souce_ip_Domain_address");
			    					        colmName.add("id");
			    					        Map<String, String> wherClause=new HashMap<String, String>();
			    					        wherClause.put("accountid",asd[1].toString() );
			    					        wherClause.put("country_iso_code",asd[0].toString());
			    					        // Single Spce Configuration Come
			    					        
			    					        String dbbname1="clouddb";
			    					        String ipAddress1=objectCol1[2].toString();
			    					        String port1=objectCol1[3].toString();
			    					        String username1=objectCol1[4].toString();
			    					        String paassword1=objectCol1[5].toString();
			    					        AllConnection Conn1=new ConnectionFactory(dbbname1,ipAddress1,username1,paassword1,port1);
	    			    			        AllConnectionEntry Ob=Conn1.GetAllCollectionwithArg();
	    			    			        SessionFactory chunkSession=Ob.getSession();
	    			    			        
	    			    			        //System.out.println("chunkSession  "+chunkSession);
	    			    			        /**
	    			    			         * getting client DB space
	    			    			         */
	    			    			        List ClientSpace=new TableInfo().viewAllDataEitherSelectOrAll(objectCol1[0].toString().trim()+"_"+objectCol1[1].toString().trim()+"_space_configuration", colmName,wherClause,chunkSession);
			    					       // System.out.println("ClientSpace  "+ClientSpace);
	    			    			        if(ClientSpace != null && ClientSpace.size()>0)
			    					        {
			    					        	 for (Iterator iterator2 = ClientSpace.iterator(); iterator2.hasNext();)
			    					        	 {
			    					        		 String dbbname=null,ipAddress=null,username=null,paassword=null,port=null;
			    					        		 Object[] objectCol2 = (Object[]) iterator2.next();
			    					        		 List singleSpceServer=ob1.GetClientspace(objectCol2[0].toString().trim());
			    					        		 
			    					        		 for (Object c : singleSpceServer) {
			    					     				Object[] dataC = (Object[]) c;
			    					     				
			    					     				
			    					     				dbbname=asd[1].toString().trim()+"_clouddb";
			    					     				ipAddress=dataC[0].toString();
			    					     				username=dataC[2].toString();
			    					     				paassword=dataC[3].toString();
			    					     				port=dataC[1].toString();
			    					     				AllConnection Conn=new ConnectionFactory(dbbname,ipAddress,username,paassword,port);
				    			    			        AllConnectionEntry Ob1=Conn.GetAllCollectionwithArg();
				    			    			        sessfactNew=Ob1.getSession();
			    					        		 }
			    					        	 }
			    					        }
	    			    			        
		    		    		   }
		    		    	  }
							
							 //System.out.println("sessfactNew  "+sessfactNew);
							tdi.insertDataInDistributedTable(asd[1].toString()+ "_clouddb", "client_product",Tablecolumesaaa, sessfactNew);
							Thread.sleep(300);
							//insert user name , name and password in user account
							new LoginImp().insertUser(sessfactNew, tempRegsitarationAccount.getContactNo(), tempRegsitarationAccount.getOrg_Registation_name(), getUsername(), getPassword(), "1");
						}
					}
					addActionMessage("User and password is updated!");
					return SUCCESS;
					
				} else {
					return ERROR;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log
					.error(
							"Over2Cloud::>> Class:VarificationLink >> Method::>> clientinformationDone The User name and Pwd is Not Encript ",
							e);
		}
		return ERROR;
	}

	
	
	
	public String getTempAAAAACAAOAAUAANAANOAA() {
		return tempAAAAACAAOAAUAANAANOAA;
	}

	public void setTempAAAAACAAOAAUAANAANOAA(String tempAAAAACAAOAAUAANAANOAA) {
		this.tempAAAAACAAOAAUAANAANOAA = tempAAAAACAAOAAUAANAANOAA;
	}

	public String getTempLAAOAAIAAD() {
		return tempLAAOAAIAAD;
	}

	public void setTempLAAOAAIAAD(String tempLAAOAAIAAD) {
		this.tempLAAOAAIAAD = tempLAAOAAIAAD;
	}

	public String getTempAAPAAWAAD() {
		return tempAAPAAWAAD;
	}

	public void setTempAAPAAWAAD(String tempAAPAAWAAD) {
		this.tempAAPAAWAAD = tempAAPAAWAAD;
	}

	public Map<String, String> getAppNames() {
		return appNames;
	}

	public void setAppNames(Map<String, String> appNames) {
		this.appNames = appNames;
	}

	public String getKcf() {
		return kcf;
	}

	public void setKcf(String kcf) {
		this.kcf = kcf;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<BeanApps> getAppslist() {
		return appslist;
	}

	public void setAppslist(List<BeanApps> appslist) {
		this.appslist = appslist;
	}

	public String getContryid() {
		return contryid;
	}

	public void setContryid(String contryid) {
		this.contryid = contryid;
	}

	public String getAppcheckname() {
		return appcheckname;
	}

	public void setAppcheckname(String appcheckname) {
		this.appcheckname = appcheckname;
	}

	public String getNoOfUser() {
		return noOfUser;
	}

	public void setNoOfUser(String noOfUser) {
		this.noOfUser = noOfUser;
	}

	public String getValid_from() {
		return valid_from;
	}

	public void setValid_from(String valid_from) {
		this.valid_from = valid_from;
	}

	public String getValid_to() {
		return valid_to;
	}

	public void setValid_to(String valid_to) {
		this.valid_to = valid_to;
	}

	public String getTotalUserCount() {
		return totalUserCount;
	}

	public void setTotalUserCount(String totalUserCount) {
		this.totalUserCount = totalUserCount;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewusername() {
		return newusername;
	}

	public void setNewusername(String newusername) {
		this.newusername = newusername;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getPageFalg() {
		return pageFalg;
	}

	public void setPageFalg(String pageFalg) {
		this.pageFalg = pageFalg;
	}

}