package com.Over2Cloud.ctrl.patientcare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;

public class CPSPackageMapping extends GridPropertyBean implements ServletRequestAware{
	
	
	/*
	 * Author: Manab 24/09/2015
	 * About: This class is for mapping the customer, with billing group and package that come from HIS
	 */
	private HttpServletRequest request;
	private List<GridDataPropertyView> masterViewPropCustomer;
	 private List<Object> customerGridData;
	 private JSONArray jsonArr=null;
	private String customerIds="";
	private String healthPkgID="";
	private String billingGrpID="";
	private String accountManagerID="";
	private String pack = "";
	private String bill = "";
	private String account="";
	private List<Object> masterViewList;
	private String status;
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	private Map<String,String> totalCount;
	private String packagegroup,billinggroup;
	public String mapCustomerwithPackBeforeDataView(){

		String returnString = ERROR;
		if (!ValidateSession.checkSession()) return LOGIN;
		try
		{
				returnString=SUCCESS;                                          				    
		} catch (Exception e)
		{
				returnString = ERROR;
				e.printStackTrace();
		}
			
	
     	return returnString;
	
	}
	public String mapCustomerwithPack()
	{
		String returnString = ERROR;
		if (!ValidateSession.checkSession()) return LOGIN;
		try
		{
				setGridViewProp();
				
				returnString=SUCCESS;                                          				    
		} catch (Exception e)
		{
				returnString = ERROR;
				e.printStackTrace();
		}
			
	
     	return returnString;
	}
	public void setGridViewProp(){
		
		masterViewPropCustomer= new ArrayList<GridDataPropertyView>();
		
		GridDataPropertyView gpv = new GridDataPropertyView();
		gpv.setColomnName("id");
		gpv.setHeadingName("Id");
		gpv.setHideOrShow("true");
		gpv.setWidth(65);
		masterViewPropCustomer.add(gpv);
		 

		gpv = new GridDataPropertyView();
		gpv.setColomnName("customer_name");
		gpv.setHeadingName("Customer Name");
	    gpv.setHideOrShow("false");
	    gpv.setEditable("true");
	    gpv.setWidth(400);
		gpv.setSearch("true");
		masterViewPropCustomer.add(gpv);
	    
	    gpv = new GridDataPropertyView();
		gpv.setColomnName("customer_code");
		gpv.setHeadingName("Customer Code");
	    gpv.setHideOrShow("false");
	    gpv.setEditable("true");
	    gpv.setWidth(400);
		gpv.setSearch("true");
		masterViewPropCustomer.add(gpv);
		
	    
	    gpv = new GridDataPropertyView();
		gpv.setColomnName("discount");
		gpv.setHeadingName("Discount");
	    gpv.setHideOrShow("false");
	    gpv.setEditable("true");
	    gpv.setWidth(400);
		gpv.setSearch("true");
		masterViewPropCustomer.add(gpv);
	    
		    

	}
	
	public String veiwCustomerDetail()
	{
		String returnString = ERROR;
		String removeID = "'0'";
		if (!ValidateSession.checkSession()) return LOGIN;
		try{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query1 = new StringBuilder("");
			query1.append("select count(*) from corporate_patience_data");
			List dataCount = cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
			List accountManager=cbt.executeAllSelectQuery("select customer_id,acc_manager_id from cps_customer_map where acc_manager_id like '%"+accountManagerID+"%' ", connectionSpace);
			List healthPkg=cbt.executeAllSelectQuery("select customer_id,package_id from cps_customer_map where package_id like '%"+healthPkgID+"%' ", connectionSpace);
			List billingGrp=cbt.executeAllSelectQuery("select customer_id,billing_grp_id from cps_customer_map where billing_grp_id like '%"+billingGrpID+"%' ", connectionSpace);
			if (dataCount != null)
			{
				StringBuilder query = new StringBuilder("select id, customer_name, customer_code, discount ");
				query.append(" from cps_customer_his ");	
				if(pack.length()>0 && !pack.equalsIgnoreCase("-1")){
					StringBuilder qry= new StringBuilder();
					qry.append(" select customer_id, package_id from cps_customer_map ");
					List data=new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
					if(data!=null && !data.isEmpty())
					{    
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{ 	
							Object[] object = (Object[]) iterator.next();
							if(object[0]!=null && object[1]!=null)
							{
								if(object[1].toString().contains(",")){
								String[] packArr = object[1].toString().split(",");
									for(String results : packArr) {
								          if(results.equalsIgnoreCase(pack))
								          {
								        	  removeID=removeID+",'"+object[0]+"'";
								          }
									}
								}
							}
						}
					}
					query.append(" where id not in ( "+removeID+")");	
				}
				else if(accountManager!=null && !accountManager.isEmpty() && accountManager.size()>0 )
				{
					if(accountManager!=null && !accountManager.isEmpty())
					{    
						for (Iterator iterator = accountManager.iterator(); iterator.hasNext();)
						{ 	
							Object[] object = (Object[]) iterator.next();
							if(object[0]!=null && object[1]!=null)
							{
								String[] packArr = object[1].toString().split(",");
									for(String results : packArr) 
									{
										
										removeID=removeID+",'"+object[0]+"'";
									}
							}
						}
					}
					query.append(" where id not in ( "+removeID+")");	
				}
				else if(healthPkg!=null && !healthPkg.isEmpty() && healthPkg.size()>0 )
				{
					if(healthPkg!=null && !healthPkg.isEmpty())
					{    
						for (Iterator iterator = healthPkg.iterator(); iterator.hasNext();)
						{ 	
							Object[] object = (Object[]) iterator.next();
							if(object[0]!=null && object[1]!=null)
							{

								String[] packArr = object[1].toString().split(",");
									for(String results : packArr) 
									{
										
										removeID=removeID+",'"+object[0]+"'";
									}
							}
						}
					}
					query.append(" where id not in ( "+removeID+")");	
					if(packagegroup!=null)
					{
						query.append(" and customer_name LIKE '%" + packagegroup + "%'");
					}
				}
				else if(billingGrp!=null && !billingGrp.isEmpty() && billingGrp.size()>0 )
				{
					if(billingGrp!=null && !billingGrp.isEmpty())
					{    
						for (Iterator iterator = billingGrp.iterator(); iterator.hasNext();)
						{ 	
							Object[] object = (Object[]) iterator.next();
							if(object[0]!=null && object[1]!=null)
							{

								String[] packArr = object[1].toString().split(",");
									for(String results : packArr) 
									{
										
										removeID=removeID+",'"+object[0]+"'";
									}
							}
						}
					}
					query.append(" where id not in ( "+removeID+")");	
					if(billinggroup!=null)
					{
						query.append(" and customer_name LIKE '%" + billinggroup + "%'");
					}
				}
				//System.out.println("query query "+query);
				List<String> fieldNames =new ArrayList<String>();
				     fieldNames.add("id");
				     fieldNames.add("customer_name");
				     fieldNames.add("customer_code");
				     fieldNames.add("discount");
			     if(fieldNames != null && !fieldNames.isEmpty())
			     {
						   List data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						        if(data != null && !data.isEmpty())
						          {    
						        	 List<Object> listdata = new ArrayList<Object>();
						        	 for(Iterator itr = data.iterator(); itr.hasNext();)
						        	    {
						        		   Object[] obdata = (Object[]) itr.next(); 
						        		   
						        		   Map<String, Object> one =new HashMap<String, Object>();
						        		    for(int k=0; k<fieldNames.size(); k++)
						        		       {
						        		    	  if(obdata[k] == null)
						        		    	  {
						        		    		  obdata[k] = "NA"; 
						        		    	  }
						        		    	 if(k==0)
						        		    	   {
						        		    		 one.put(fieldNames.get(k).toString(),  (Integer)obdata[k]);
						        		    	   }
						        		    	 else{
						        		    		 if(fieldNames.get(k).toString().equalsIgnoreCase("date"))
					        		    		     {
					        		    			   one.put(fieldNames.get(k).toString(), DateUtil.convertDateToIndianFormat(obdata[k].toString()));
					        		    		     }else{
					        		    		     one.put(fieldNames.get(k).toString(), obdata[k].toString());
					        		    		     }	 
						        		        	 }
						        		        }
						        		    listdata.add(one);
						        		  }
						        	 setCustomerGridData(listdata);
						        	 }
			    	         }
			       }
			returnString = SUCCESS;
		}
		  catch(Exception e)
		{
			  
	    			e.printStackTrace();	  
		}
		return returnString;		
	
	}
	
	 public String packageData(){
		   
		   String returnResult = "ERROR";
		   boolean sessionFlag = ValidateSession.checkSession();
			if(sessionFlag){
				try{
					jsonArr= new JSONArray();
					JSONObject obj = new JSONObject();
					StringBuilder qry= new StringBuilder();
					qry.append(" select id , health_chkup_name , health_chkup_code from cps_healthchkup_his ");
					List data=new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
					
					if(data!=null && !data.isEmpty())
					{    
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{ 	
							Object[] object = (Object[]) iterator.next();
							if(object[0]!=null && object[1]!=null)
							{
								
								obj.put("id", object[0]);
								obj.put("Name", object[1]+" / "+object[2]);
								jsonArr.add(obj);
							}
						}
						returnResult = SUCCESS;
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					returnResult = ERROR;
				}
			}
			else{
				returnResult = LOGIN;
			}
		   return returnResult;
	   }
	
	public String billingGrpData(){
		   String returnResult = "ERROR";
		   boolean sessionFlag = ValidateSession.checkSession();
			if(sessionFlag){
				try{
					jsonArr= new JSONArray();
					JSONObject obj = new JSONObject();
					StringBuilder qry= new StringBuilder();
					//qry.append(" select id , billing_grp_name , billing_grp_code  from cps_billinggroup_his ");
					qry.append(" select id , billing_grp_name , ACCOUNT_MANAGER_NAME  from cps_billinggroup_his ");
					List data=new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
					if(data!=null && !data.isEmpty())
					{    
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{ 	
							Object[] object = (Object[]) iterator.next();
							if(object[0]!=null && object[1]!=null)
							{
								obj.put("id", object[0]);
								obj.put("Name", object[1]+" / "+object[2]);
								jsonArr.add(obj);
							}
						}
						returnResult = SUCCESS;
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
					returnResult = ERROR;
				}
			}
			else{
				returnResult = LOGIN;
			}
		   return returnResult;
	}
	public String accountManagerData(){
		   String returnResult = "ERROR";
		   boolean sessionFlag = ValidateSession.checkSession();
			if(sessionFlag){
				try{
					jsonArr= new JSONArray();
					JSONObject obj = new JSONObject();
					StringBuilder qry= new StringBuilder();
					qry.append(" select emp.id, emp.empName from employee_basic as emp inner join department as dept on dept.id= emp.deptname where dept.deptName like '%Account Manager%' order by emp.empName");
					//System.out.println(qry);
					List data=new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
					if(data!=null && !data.isEmpty())
					{    
						for (Iterator iterator = data.iterator(); iterator.hasNext();)
						{ 	
							Object[] object = (Object[]) iterator.next();
							if(object[0]!=null && object[1]!=null)
							{
								obj.put("id", object[0]);
								obj.put("Name", object[1]);
								jsonArr.add(obj);
							}
						}
						returnResult = SUCCESS;
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
					returnResult = ERROR;
				}
			}
			else{
				returnResult = LOGIN;
			}
		   return returnResult;
		   
	   
		
	}
	public String mapCustomer()
	{
		String returnResult=ERROR;
		boolean sessionFlag=ValidateSession.checkSession();
		boolean flag = false;
		if(sessionFlag)
		{
			try
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				if(billingGrpID!=null && !billingGrpID.equalsIgnoreCase("") && !billingGrpID.equalsIgnoreCase("-1") && !billingGrpID.equalsIgnoreCase("null") && customerIds.length()>0 )
				{
					      String[] s2 = customerIds.split(",");
					      for(String results : s2) {
					          StringBuilder qry= new StringBuilder();
								qry.append(" select id, customer_id, package_id, billing_grp_id from cps_customer_map where customer_id = '"+results+"' ");
								List data=new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
								if(data!=null && !data.isEmpty())
								{ 
									for (Iterator iterator = data.iterator(); iterator.hasNext();)
									{
										Object[] object = (Object[]) iterator.next();
										if (object[0]!=null && object[1]!=null && object[2]!=null && object[3]!=null) 
										{
											if(object[3].toString().equalsIgnoreCase("NA"))
											{
												flag = updateCustomerMap(object[0].toString(), results , "0" , billingGrpID,"0");
											}
											else{
												flag = updateCustomerMap(object[0].toString(), results , "0" , object[3].toString()+","+billingGrpID,"0");
											}
										}
										else
										{
											flag =  updateCustomerMap(object[0].toString(), results , "0" , billingGrpID,"0");
										}
									}
								}
								else{
					          
									flag = insertCustomerMap(results, "NA", billingGrpID,"NA" );
								}
								
								if (flag){
									String query = "update cps_customer_his set billing_group = '1' where id="+results+"";
							           cbt.executeAllUpdateQuery(query, connectionSpace);
								}
					      }
				}
				else if(healthPkgID!=null && !healthPkgID.equalsIgnoreCase("") && !healthPkgID.equalsIgnoreCase("null") && customerIds.length()>0)
				{
					  String[] s2 = customerIds.split(",");
				      for(String results : s2) {
				          StringBuilder qry= new StringBuilder();
							qry.append(" select id, customer_id, package_id, billing_grp_id from cps_customer_map where customer_id = '"+results+"' ");
							//System.out.println(qry);
							List data=new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
							if(data!=null && !data.isEmpty())
							{ 
								for (Iterator iterator = data.iterator(); iterator.hasNext();)
								{
									Object[] object = (Object[]) iterator.next();
									if (object[0]!=null && object[1]!=null && object[2]!=null && object[3]!=null) 
									{
										if(object[2].toString().equalsIgnoreCase("NA"))
										{
											flag = updateCustomerMap(object[0].toString(), results , healthPkgID , "0","0");
										
										}
										else{
										flag =	updateCustomerMap(object[0].toString(), results, object[2].toString()+","+healthPkgID , "0" ,"0");
										}
										
									}
									else
									{
										flag = updateCustomerMap(object[0].toString(), results , healthPkgID , "0","0");
									}
								}
							}
				          
				          else{
				         flag = insertCustomerMap(results, healthPkgID, "NA","NA");
				          }
							if (flag){
								String query = "update cps_customer_his set heath_chkup = '1' where id="+results+"";
						           cbt.executeAllUpdateQuery(query, connectionSpace);
							}
				      }
				}
				if(billingGrpID!=null && !billingGrpID.equalsIgnoreCase("") && !billingGrpID.equalsIgnoreCase("") && !billingGrpID.equalsIgnoreCase("null") && customerIds.length()>0 )
				{
					//System.out.println("accountManagerID "+accountManagerID);
					List dataList = cbt.executeAllSelectQuery(" select  ACCOUNT_MANAGER_NAME from cps_billinggroup_his  WHERE id = '"+billingGrpID+"' ", connectionSpace);
					if (dataList != null && dataList.size() > 0)
					{
						//val="";
						for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
						{
							Object object1 = (Object) iterator.next();
							accountManagerID=object1.toString();
						}
					}
					      String[] s2 = customerIds.split(",");
					      for(String results : s2) {
					          StringBuilder qry= new StringBuilder();
								qry.append(" select id, customer_id,acc_manager_id from cps_customer_map where customer_id = '"+results+"' ");
								//System.out.println("Account manager >>>>>>>> <<<< "+qry);
								List data=new createTable().executeAllSelectQuery(qry.toString(), connectionSpace);
								//System.out.println("data "+data.size());
								if(data!=null && !data.isEmpty())
								{ 
									//System.out.println("data12 "+data.size());
									for (Iterator iterator = data.iterator(); iterator.hasNext();)
									{
										//System.out.println("datannn "+data.size());
										Object[] object = (Object[]) iterator.next();
										if (object[0]!=null && object[1]!=null && object[2]!=null ) 
										{
											if(object[2].toString().equalsIgnoreCase("NA"))
											{
												updateCustomerMap(object[0].toString(), results , "0","0" , accountManagerID);
											}
											else{
												updateCustomerMap(object[0].toString(), results , "0","0" , object[2].toString()+","+accountManagerID);
											}
											
										}
										else
										{
											updateCustomerMap(object[0].toString(), results , "0","0" , accountManagerID);
										}
									}
								}
								else{
									insertCustomerMap(results, "NA","NA", accountManagerID );
								}
					      }
				}
				addActionMessage("Mapped Successfully !!!");
				returnResult = "success";
			}
			catch(Exception e)
			 {
				returnResult=ERROR;
				 e.printStackTrace();
			 }
		}
		else
		{
			returnResult=LOGIN;
		}
		return returnResult;
	}
	private boolean updateCustomerMap(String id, String cusId,String packageId, String billGrpId,String accountId) {
		// TODO Auto-generated method stub
		int ckhUpdate = 0;
		boolean flag = false;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		if(id!=null && cusId!= null && billGrpId!=null && packageId.equalsIgnoreCase("0") && !billGrpId.equalsIgnoreCase("0") && accountId.equalsIgnoreCase("0"))
		{
			String query = "update cps_customer_map set billing_grp_id = '"+billGrpId+"', date='"+DateUtil.getCurrentDateUSFormat()+"', time='"+DateUtil.getCurrentTime()+"', user='"+userName+"'  where id ="+id;
	           ckhUpdate=  cbt.executeAllUpdateQuery(query, connectionSpace);
		}
		else if(id!=null && cusId!= null && packageId!=null && !packageId.equalsIgnoreCase("0") && billGrpId.equalsIgnoreCase("0") && accountId.equalsIgnoreCase("0"))
		{
			String query = "update cps_customer_map set package_id = '"+packageId+"', date='"+DateUtil.getCurrentDateUSFormat()+"', time='"+DateUtil.getCurrentTime()+"', user='"+userName+"'  where id ="+id;
	           ckhUpdate=  cbt.executeAllUpdateQuery(query, connectionSpace);
		}
		else if(id!=null && cusId!= null && accountId!=null && packageId.equalsIgnoreCase("0") && billGrpId.equalsIgnoreCase("0") && !accountId.equalsIgnoreCase("0"))
		{
			String query = "update cps_customer_map set acc_manager_id = '"+accountId+"', date='"+DateUtil.getCurrentDateUSFormat()+"', time='"+DateUtil.getCurrentTime()+"', user='"+userName+"'  where id ="+id;
	           ckhUpdate=  cbt.executeAllUpdateQuery(query, connectionSpace);
		}
		if(ckhUpdate>0){
			flag = true;
		}
		return flag;
	}
	public boolean insertCustomerMap(String cusId, String packageId, String billGrpId,String accountManagerID ){
		 boolean flag = false;
		 CommonOperInterface cbt = new CommonConFactory().createInterface();
		 Map<String, Object> wherClause = new HashMap<String, Object>();
			// update in history table
			wherClause.put("customer_id", cusId);
			if(!packageId.equalsIgnoreCase("NA"))
			{
				wherClause.put("package_id", packageId);
			}
			if(!billGrpId.equalsIgnoreCase("NA"))
			{
				wherClause.put("billing_grp_id", billGrpId);
			}
			if(!accountManagerID.equalsIgnoreCase("NA"))
			{
				wherClause.put("acc_manager_id", accountManagerID);
			}
			wherClause.put("date", DateUtil.getCurrentDateUSFormat());
			wherClause.put("time", DateUtil.getCurrentTime());
			wherClause.put("user", userName);
			if (wherClause != null && wherClause.size() > 0)
			{
				InsertDataTable obclose = new InsertDataTable();
				List<InsertDataTable> insertDataClose = new ArrayList<InsertDataTable>();
				insertDataClose.clear();
				for (Map.Entry<String, Object> entry : wherClause.entrySet())
				{
					obclose = new InsertDataTable();
					obclose.setColName(entry.getKey());
					obclose.setDataName(entry.getValue().toString());
					insertDataClose.add(obclose);
				}
				flag = cbt.insertIntoTable("cps_customer_map", insertDataClose, connectionSpace);
				insertDataClose.clear();
			}
		return flag;
	}
//for cusomer map with package
	public String viewCustomerMapdata()
	{
		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				masterViewList = new ArrayList<Object>();
				List<Object> Listhb = new ArrayList<Object>();
				List dataList = null;
				ComplianceUniversalAction CUA = new ComplianceUniversalAction();
				StringBuilder query = new StringBuilder();
				query.append("SELECT cps.id,cch.customer_name,cch.customer_code,cch.discount,cps.package_id,cps.billing_grp_id,cps.billing_grp_id as bil_id,cps.date,cps.time,cps.user,cps.status");
				query.append(" from cps_customer_map as cps ");
				query.append(" inner join cps_customer_his as cch on cch.id=cps.customer_id");
				//query.append(" inner join cps_healthchkup_his as chh on chh.id=cps.package_id ");
			//	query.append(" inner join cps_billinggroup_his as cbh on cbh.id=cps.billing_grp_id ");
				//query.append(" where cps.status='Active'");
				//query.append(" group by subdept.subdeptname ");
				if (this.getStatus() != null && !this.getStatus().equalsIgnoreCase("") && !this.getStatus().equals("-1") && !this.getStatus().equalsIgnoreCase("0"))
				{
					query.append(" where cps.status='" + getStatus() + "'");
				}
				else if (this.getStatus() != null && !this.getStatus().equalsIgnoreCase("") && (this.getStatus().equals("-1")  || this.getStatus().equalsIgnoreCase("0")))
				{
					if(!this.getStatus().equalsIgnoreCase("0")){
					query.append(" where cps.status is not null ");
					}
					else{
						query.append(" where cch.billing_group ='0' ");
					}
				}
				else
				{
					query.append(" where cps.status='Active' ");
				}
				
				 System.out.println("####### "+query.toString());
				dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

				if (dataList != null && dataList.size() > 0)
				{
					for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
					{
						Object[] object = (Object[]) iterator.next();
						Map<String, Object> one = new HashMap<String, Object>();
						one.put("id", CUA.getValueWithNullCheck(object[0]));
						one.put("customer_name", CUA.getValueWithNullCheck(object[1]));
						one.put("customer_id", CUA.getValueWithNullCheck(object[2]));
						one.put("discount", CUA.getValueWithNullCheck(object[3]));
						String package_name=fetchPackageName(CUA.getValueWithNullCheck(object[4]));
						one.put("package_id", package_name);
						String billing_name=fetchBillingName(CUA.getValueWithNullCheck(object[5]));
						one.put("billing_grp_id", billing_name);
						String accountManager=fetchAccountManager(CUA.getValueWithNullCheck(object[6]));
						one.put("bil_id", accountManager);
						one.put("date", DateUtil.convertDateToUSFormat(object[7].toString()));
						one.put("time", CUA.getValueWithNullCheck(object[8]));
						one.put("user", CUA.getValueWithNullCheck(object[9]));
						one.put("status", CUA.getValueWithNullCheck(object[10]));
						Listhb.add(one);
					}
				}
				setMasterViewList(Listhb);
				if (masterViewList != null && masterViewList.size() > 0)
				{
					setRecords(masterViewList.size());
					int to = (getRows() * getPage());
					@SuppressWarnings("unused")
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
				}
				return SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			return LOGIN;
		}

	}
	private String fetchPackageName(String val)
	{
		String packageName =" ";
		if(val.contains(","))
		{
		String abc[]=val.split(",");
		for(String results : abc) {
	          if(!" ".equalsIgnoreCase(packageName))
	          {
	        	  results = packageNameGet(results);
	          packageName = packageName +" , "+results;
	          }
	          else{
	        	  results = packageNameGet(results);
	        	  packageName = results;
	          }
		}
		}
		else {
			packageName = packageNameGet(val);
		}
		return packageName;
	}
	public String packageNameGet(String val){
		String str = null;
		if(!val.equalsIgnoreCase("NA"))
		{
			String query="select health_chkup_name from cps_healthchkup_his where id ="+val+"";
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				val="";
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object object1 = (Object) iterator.next();
					str=object1.toString();
				}
			}
		}
		
		else
		{
			str="NA";
		}
		return str;
	}
	
	// for billing
	private String fetchBillingName(String bill)
	{
		String billorgName =" ";
		if(bill.contains(","))
		{
			
		String abc[]=bill.split(",");
		for(String results123 : abc) {
	          if(!" ".equalsIgnoreCase(billorgName))
	          {
	        	  results123 = billOrgNameGet(results123);
	        	  billorgName = billorgName +" , "+results123;
	          }
	          else{
	        	  results123 = billOrgNameGet(results123);
	        	  billorgName = results123;
	          }
	          
		}
		}
		else {
			billorgName = billOrgNameGet(bill);
		}
		return billorgName;
	}
	
	public String billOrgNameGet(String bill){
		String str = null;
		String query=null;
		if(bill!=null && !bill.equalsIgnoreCase("NA") && !bill.equals("-1"))
		{
			query="select billing_grp_name from cps_billinggroup_his where id ="+bill+"";
			//System.out.println("query  query "+query);
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				bill="";
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object object1 = (Object) iterator.next();
					str=object1.toString();
				}
			}
			else
			{
				str="NA";
			}
			
		}
		else
		{
			str="NA";
		}
		return str;
	}
	//For AccountManager
	private String fetchAccountManager(String val)
	{
		String accManager =" ";
		if(val.contains(","))
		{
			
		String abc[]=val.split(",");
		for(String resultsAcc : abc) {
	          if(!" ".equalsIgnoreCase(accManager))
	          {
	        	  resultsAcc = accountNameGet(resultsAcc);
	        	  accManager = accManager +" , "+resultsAcc;
	        	  //accManager = resultsAcc;
	          }
	          else{
	        	  resultsAcc = accountNameGet(resultsAcc);
	        	  accManager = resultsAcc;
	          }
		}
		}
		else {
			accManager = accountNameGet(val);
		}
		return accManager;
	}
	public String accountNameGet(String val){
		String str = null;
		if(!val.equalsIgnoreCase("NA"))
		{
			/*String query="select emp.empName from employee_basic as emp "
				+"inner join department as dept on dept.id= emp.deptname"
				+" where dept.deptName like '%Account Manager%' and emp.id ='"+val+"' order by emp.empName";*/
			String query="select ACCOUNT_MANAGER_NAME from cps_billinggroup_his "
				+" where  id like '%"+val+"%' order by ACCOUNT_MANAGER_NAME ";
			//System.out.println("query "+query);
			List dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (dataList != null && dataList.size() > 0)
			{
				val="";
				for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
				{
					Object object1 = (Object) iterator.next();
					str=object1.toString();
				}
			}
		}
		else
		{
			str="NA";
		}
		return str;
	}
	public String ViewCustomerMap(){
		String returnString = ERROR;
		if (!ValidateSession.checkSession()) return LOGIN;
		try
		{
			totalCount=new LinkedHashMap<String, String>();
			
			totalCount=getToatalContactsTypeCounters( connectionSpace);
			System.out.println("totalCount  >>  "+totalCount);
				returnString=SUCCESS;                                          				    
		} catch (Exception e)
		{
				returnString = ERROR;
				e.printStackTrace();
		}
     	return returnString;
	}
	
	public String editCustomerMapdata()
	{
		String returnValue = ERROR;
		if (ValidateSession.checkSession())
		{
			try
			{
				if (getOper().equalsIgnoreCase("edit")) {
					CommonOperInterface cbt = new CommonConFactory().createInterface();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					List<String> requestParameterNames = Collections.list((Enumeration<String>) request.getParameterNames());
					Collections.sort(requestParameterNames);
					Iterator it = requestParameterNames.iterator();
					while (it.hasNext()) {
						String Parmname = it.next().toString();
						String paramValue = request.getParameter(Parmname);
						if (paramValue != null && !paramValue.equalsIgnoreCase("") && Parmname != null && !Parmname.equalsIgnoreCase("") && !Parmname.equalsIgnoreCase("id") && !Parmname.equalsIgnoreCase("oper") && !Parmname.equalsIgnoreCase("rowid"))
							wherClause.put(Parmname, paramValue);
					}
					condtnBlock.put("id", getId());
					cbt.updateTableColomn("cps_customer_map", wherClause, condtnBlock, connectionSpace);
				}
			    else if (getOper().equalsIgnoreCase("del"))
			   {
					 CommonOperInterface cbt = new CommonConFactory().createInterface(); 
					 String tempIds[] = getId().split(",");
					 StringBuilder condtIds = new StringBuilder(); 
					 Map<String, Object> wherClause = new HashMap<String, Object>();
					 Map<String, Object> condtnBlock = new HashMap<String, Object>();
					 int i = 1; 
					 for(String H : tempIds) 
					 { 
						 if (i < tempIds.length)
					          condtIds.append(H + " ,"); 
						 else 
							  condtIds.append(H); 
						 i++; 
					 }
					 wherClause.put("status","Inactive");
					 //wherClause.put("deactiveOn",DateUtil.getCurrentDateUSFormat());
					 condtnBlock.put("id", condtIds.toString());
					 cbt.updateTableColomn("cps_customer_map", wherClause,condtnBlock, connectionSpace);
					 StringBuilder fieldsNames=new StringBuilder();
					 StringBuilder dataStore=new StringBuilder();
                		StringBuilder dataField=new StringBuilder();
                		if (wherClause!=null && !wherClause.isEmpty())
						{
							 i=1;
							for (Map.Entry<String, Object> entry : wherClause.entrySet()) 
							{
							    if (i < wherClause.size())
									fieldsNames.append("'"+entry.getKey() + "', ");
								else
									fieldsNames.append("'"+entry.getKey() + "' ");
								i++;
							}
						}
                		
			   }
				returnValue = SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				returnValue = ERROR;
			}
		}
		else
		{
			returnValue = LOGIN;
		}
		return returnValue;
	}
	public Map<String,String> getToatalContactsTypeCounters(SessionFactory connectionSpace)
	{
		Map<String,String> counter=new LinkedHashMap<String, String>();
		
		StringBuilder builder=new StringBuilder("select count(*), billing_group from cps_customer_his where billing_group = '0' group by billing_group ");
		int total =0;
		 
		
		System.out.println("Builder :::: "+builder.toString());
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connectionSpace);
		if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null )
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if (object[0]!=null && object[1]!=null)
				{
					total=total+Integer.parseInt(object[0].toString());
					//counter.put( "Un-mapped Customer",object[0].toString());
				}
			}
			counter.put("Un-mapped Customer", String.valueOf(total));
		}
		System.out.println("counter mmm  >>  "+counter);
		return counter;
	}
	public JSONArray getJsonArr() {
		return jsonArr;
	}
	public void setJsonArr(JSONArray jsonArr) {
		this.jsonArr = jsonArr;
	}
	public List<Object> getCustomerGridData() {
		return customerGridData;
	}
	public void setCustomerGridData(List<Object> customerGridData) {
		this.customerGridData = customerGridData;
	}
	public List<GridDataPropertyView> getMasterViewPropCustomer() {
		return masterViewPropCustomer;
	}

	public void setMasterViewPropCustomer(
			List<GridDataPropertyView> masterViewPropCustomer) {
		this.masterViewPropCustomer = masterViewPropCustomer;
	}
	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
	}
	public String getCustomerIds() {
		return customerIds;
	}
	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}
	public String getHealthPkgID() {
		return healthPkgID;
	}
	public void setHealthPkgID(String healthPkgID) {
		this.healthPkgID = healthPkgID;
	}
	public String getBillingGrpID() {
		return billingGrpID;
	}
	public void setBillingGrpID(String billingGrpID) {
		this.billingGrpID = billingGrpID;
	}
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public List<Object> getMasterViewList() {
		return masterViewList;
	}
	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountManagerID() {
		return accountManagerID;
	}
	public void setAccountManagerID(String accountManagerID) {
		this.accountManagerID = accountManagerID;
	}
	public Map<String, String> getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Map<String, String> totalCount) {
		this.totalCount = totalCount;
	}
	public String getPackagegroup() {
		return packagegroup;
	}
	public void setPackagegroup(String packagegroup) {
		this.packagegroup = packagegroup;
	}
	public String getBillinggroup() {
		return billinggroup;
	}
	public void setBillinggroup(String billinggroup) {
		this.billinggroup = billinggroup;
	}
	
	

}
