package com.Over2Cloud.ctrl.Registation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.BeanUtil.AppsInformation;
import com.Over2Cloud.BeanUtil.RegHieInformation;
import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GenerateUUID;
import com.Over2Cloud.CommonClasses.IPAddress;
import com.Over2Cloud.CommonClasses.ObjectFactory;
import com.Over2Cloud.CommonClasses.RandomNumberGenerator;
import com.Over2Cloud.CommonInterface.Addmethod;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.CommonInterface.commanOperation;
import com.Over2Cloud.modal.dao.imp.PartnerRegistation.PartnerImpRegistation;
import com.Over2Cloud.modal.dao.imp.signup.BeanCountry;
import com.Over2Cloud.modal.dao.imp.signup.Industry;
import com.Over2Cloud.modal.dao.imp.signup.signupImp;
import com.Over2Cloud.modal.db.Setting.ApplicationSetting;
import com.Over2Cloud.modal.db.Setting.UserChunkMapping;
import com.Over2Cloud.modal.db.signup.JobFunctionalArea;
import com.Over2Cloud.modal.db.signup.Registation;
import com.Over2Cloud.modal.db.signup.SignupMailProcess;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class PartnerRegistation extends ActionSupport implements Preparable,
		ServletRequestAware {
	/**
	 * 
	 */
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	static final Log log = LogFactory.getLog(PartnerRegistation.class);
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String uName = (String) session.get("uName");
	String accountid = (String) session.get("accountid");
	String countryiso = (String) session.get("countryid");
	// private List<ApplicationSetting> appConfigList = null;
	private Integer rows = 0;

	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;

	// sorting order - asc or desc
	private String sord;

	// get index row - i.e. user click to sort.
	private String sidx;

	// Search Field
	private String searchField;

	// The Search String
	private String searchString;

	// Limit the result when using local data, value form attribute rowTotal
	private Integer totalrows;

	// he Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper;

	// Your Total Pages
	private Integer total = 0;

	// All Records
	private Integer records = 0;

	private boolean loadonce = false;
	private String oper;
	private String id;
	private String regName, emailaddress, orgnizationName, accountType,
			companyType, industry, regAddress, city, country, brief,
			j_captcha_response;
	private boolean ageryterm;
	private String mobile = "";
	private String pincode = "";
	public HttpServletRequest request;
	private String companysize;
	private String jobfunctionalArea;
	private String jobtitle;
	private String bPhonenumber;

	@Override
	public String execute() throws Exception {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String addRegistationAssociate() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			String UnqueMessageid = new GenerateUUID().UUID().toString();
			int uniqueid = new RandomNumberGenerator().getNDigitRandomNumber(8);
			CommonforClass eventDao = new CommanOper();
			Registation ob1 = new Registation(getCity(), true,
					getJ_captcha_response(), getMobile(), getEmailaddress(),
					getCountry(), DateUtil.currentdatetime(), getIndustry(),
					getOrgnizationName(), getCompanyType(), getPincode(),
					getRegAddress(), getRegName(), getBrief(),
					getAccountType(), Integer.toString(uniqueid),
					UnqueMessageid, "D", uName, accountid, getCompanysize(),
					getJobfunctionalArea(), getJobtitle(), getBPhonenumber());
			if (eventDao.addDetails(ob1)) {
				String ip = new IPAddress().getIPAddress();
				String UrlMail = "http://" + ip + request.getContextPath()
						+ "/linkverify?um=" + UnqueMessageid + "&kcf="
						+ getJ_captcha_response() + "&id=" + uniqueid + "&uid="
						+ ob1.getId();
				SignupMailProcess Ob2 = new SignupMailProcess(UrlMail, "N",
						getOrgnizationName(), getRegName(), ob1.getId());
				if (eventDao.addDetails(Ob2)) {
					log
							.info("Over2Cloud::>> Class:PartnerRegistation >> Method::>> addRegistationAssociate ::::: Sucessfully registered 1st Step");
				}
			} else {
				addActionMessage("!! OOPS there is a problem In Signup Please Try Again!!!!!!!!!!!!!11");
				return ERROR;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	private List<BeanCountry> countrylist = new ArrayList<BeanCountry>();
	private List<Industry> industrylist = new ArrayList<Industry>();

	public List<Industry> getIndustrylist() {
		return industrylist;
	}

	public void setIndustrylist(List<Industry> industrylist) {
		this.industrylist = industrylist;
	}

	public List<BeanCountry> getCountrylist() {
		return countrylist;
	}

	public void setCountrylist(List<BeanCountry> countrylist) {
		this.countrylist = countrylist;
	}

	private List<JobFunctionalArea> jobFunctionalArea = new ArrayList<JobFunctionalArea>();

	public List<JobFunctionalArea> getJobFunctionalArea() {
		return jobFunctionalArea;
	}

	public void setJobFunctionalArea(List<JobFunctionalArea> jobFunctionalArea) {
		this.jobFunctionalArea = jobFunctionalArea;
	}

	public void prepare() throws Exception {

		// Find out the Value Of Industry Master
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

	private List<SignupMailProcess> SignupMailOb1 = new ArrayList<SignupMailProcess>();

	public List<SignupMailProcess> getSignupMailOb1() {
		return SignupMailOb1;
	}

	public void setSignupMailOb1(List<SignupMailProcess> signupMailOb1) {
		SignupMailOb1 = signupMailOb1;
	}

	public String viewProductInfomation()
	{
		try
		{
			if (uName == null)
			{
				return LOGIN;
			}
			if (uName.equalsIgnoreCase(""))
			{
				return LOGIN;
			}
			List signupProcess = new signupImp().getAllProduct_Signup();
			if (signupProcess != null && signupProcess.size() > 0) 
			{
				for (Iterator iterator = signupProcess.iterator(); iterator.hasNext();)
				{
					Object[] objectCol = (Object[]) iterator.next();
					SignupMailProcess Signup = new SignupMailProcess();
					if (objectCol[0] == null)
					{
						Signup.setOrgnizationName("NA");
					} 
					else 
					{
						Signup.setOrgnizationName(objectCol[0].toString());
					}
					if (objectCol[1] == null) 
					{
						Signup.setRegUserName("NA");
					} 
					else 
					{
						Signup.setRegUserName(objectCol[1].toString());
					}
					if (objectCol[2] == null)
					{
						Signup.setURL("NA");
					} 
					else 
					{
						Signup.setURL(objectCol[2].toString());
					}
					SignupMailOb1.add(Signup);
				}
				setSignupMailOb1(SignupMailOb1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String ViewOurClient() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private String productid;
	private List<AppsInformation> applicationUser = new ArrayList<AppsInformation>();
	

	public String FindProductlist() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			String Productinfo[] = getProductid().split("_");
			/*System.out.println(Productinfo[0]);
			System.out.println(Productinfo[1]);
			System.out.println(Productinfo[2]);*/
			String asd[] = Productinfo[1].split(",");
			String asd1[] = Productinfo[2].split(",");
			for (int j = 0; j < asd.length; j++) {
				String AppName = new PartnerImpRegistation()
						.GetAllApplicationName(asd[j].trim());
				AppsInformation Obj = new AppsInformation();
				Obj.setApplicationName(AppName);
				Obj.setApplicationuser(asd1[j].trim());
				applicationUser.add(Obj);

			}
			setApplicationUser(applicationUser);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public String getUName() {
		return uName;
	}

	public void setUName(String name) {
		uName = name;
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

	public Integer getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
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

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
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

	public String getJobtitle() {
		return jobtitle;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public String getCountryiso() {
		return countryiso;
	}

	public void setCountryiso(String countryiso) {
		this.countryiso = countryiso;
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

	public String getBPhonenumber() {
		return bPhonenumber;
	}

	public void setBPhonenumber(String phonenumber) {
		bPhonenumber = phonenumber;
	}
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	

	public List<AppsInformation> getApplicationUser() {
		return applicationUser;
	}

	public void setApplicationUser(List<AppsInformation> applicationUser) {
		this.applicationUser = applicationUser;
	}
}
