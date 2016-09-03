package com.Over2Cloud.ctrl.Setting;

/**
 * @author Sandeep
 * Class written by sandeep for demo account setup for a demo client on 26-04-2013
 * 
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.InsertDataTable;
import com.Over2Cloud.CommonClasses.CommanOper;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.DistributedTableInfo;
import com.Over2Cloud.CommonClasses.Mailtest;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
import com.Over2Cloud.modal.dao.imp.signup.signupImp;
import com.Over2Cloud.modal.db.Setting.DemoClientSpace;
import com.Over2Cloud.modal.db.Setting.DemoSetUpHelper;
import com.Over2Cloud.modal.db.commom.Smtp;
import com.Over2Cloud.modal.db.signup.Registation;
import com.opensymphony.xwork2.ActionSupport;

public class DemoSetUp extends ActionSupport{
	
	private Integer rows = 0;
	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;
	// sorting order - asc or desc
	private String sord = "";
	// get index row - i.e. user click to sort.
	private String sidx = "";
	// Search Field
	private String searchField = "";
	// The Search String
	private String searchString = "";
	// The Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper = "";
	// Your Total Pages
	private Integer total = 0;
	// All Record
	private Integer records = 0;
	private boolean loadonce = false;
	//Grid colomn view
	private String oper;
	private String id;
	private List<Registation>gridModel=new ArrayList<Registation>();
	private String domainIpName;
	private String spaceAddress;
	
	
	
	public String demoSingleSapceConfiguration()
	{
		try
		{
			//id,org_reg_name,org_name,contact_emailid,country
			List data=DemoSetUpHelper.getUnConfiguredDemoAccount();
			for (Object c : data) {
				Object[] dataC = (Object[]) c;
				Registation rs=new Registation();
				rs.setId((Integer)dataC[0]);
				rs.setOrg_Registation_name(dataC[1].toString());
				rs.setOrgname(dataC[1].toString());
				rs.setContact_emailid(dataC[2].toString());
				rs.setCountry(dataC[3].toString());
				gridModel.add(rs);
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return SUCCESS;
	}
	
	
	public String addAllSingleSpaceForDemo()
	{
		try
		{
			System.out.println("Domain IP is as "+getDomainIpName());
			System.out.println("Space Address is as "+getSpaceAddress());
			if(getSpaceAddress()!=null && !getSpaceAddress().equalsIgnoreCase("") && !getDomainIpName().equalsIgnoreCase("-1") && getDomainIpName()!=null)
			{
				System.out.println(getDomainIpName()+"getDomainIpName <><><>getSpaceAddress() >>"+getSpaceAddress());
				String dbbname="clouddb";
				String ipAddress=null;
				String username=null;
				String paassword=null;
				String port=null;
				LoginImp ob1=new LoginImp();
				DistributedTableInfo tdi = new DistributedTableInfo();
				List singleSpceServer=ob1.GetClientspace(getDomainIpName());
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
				String data[]=getSpaceAddress().split(",");
				SingleSpaceCtrl se=new SingleSpaceCtrl();
				CommonforClass eventDao = new CommanOper();
				Mailtest mt = new Mailtest();
				List<Smtp> Ob3 = (List<Smtp>) eventDao.cloudgetDropdownvalue("id", Smtp.class);
				
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
							if(objectCol[7]!=null)
							{
								String emailID=objectCol[7].toString();
								//System.out.println("emailID  "+emailID);
								StringBuilder mailtext = new StringBuilder("");
								mailtext.append("<font face ='TIMESROMAN' size='3'><b>Dear ,<br><b><br>Your demo account is prepared." +
										"<br>Kindly login into Over2cloud.com with your login detailes received in previous mail."
										+"<b><br>Thanks</b><br>Team Over2cloud.com<br>"+ "</FONT>");
								mailtext.append("<font face ='TIMESROMAN' size='2'>"
										+ "<b>Disclaimer:- </b> This is a system generated email. Please do not reply to this email."
										+ "*** This message is intended only for the person or entity to which it is addressed and may contain confidential and/or privileged information. If you have received this message in error, please notify the sender immediately and delete this message from your system *** "
										+ "</FONT>");
								if(Ob3!=null)
								{
									for (Smtp h : Ob3) {
										// Mail Go for Client
										mt.confMailHTML(h.getServer(),h.getPort(),h.getEmailid(),h.getPwd(),emailID,"Over2cloud.com Demo Account Prepared "+ DateUtil.getCurrentDateIndianFormat(),mailtext.toString());
									}
								}
							}
							
						}
						
						DemoClientSpace dc=new DemoClientSpace();
						dc.setClienID(H.trim());
						dc.setServerId(getDomainIpName());
						eventDao.addDetails(dc);
					}
				}
			}
			else
			{
				addActionError("Oops their is some problem!!!");
				return ERROR;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			addActionError("Oops their is some problem!!!");
			return ERROR;
		}
		addActionMessage("Space Alloted Successfully!");
		return SUCCESS;
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



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public List<Registation> getGridModel() {
		return gridModel;
	}



	public void setGridModel(List<Registation> gridModel) {
		this.gridModel = gridModel;
	}



	public String getDomainIpName() {
		return domainIpName;
	}



	public void setDomainIpName(String domainIpName) {
		this.domainIpName = domainIpName;
	}


	public String getSpaceAddress() {
		return spaceAddress;
	}


	public void setSpaceAddress(String spaceAddress) {
		this.spaceAddress = spaceAddress;
	}

}
