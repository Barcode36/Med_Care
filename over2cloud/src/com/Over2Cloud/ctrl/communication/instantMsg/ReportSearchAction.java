package com.Over2Cloud.ctrl.communication.instantMsg;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ReportSearchAction extends ActionSupport{
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	
	private Integer rows = 0;
	private Integer page = 0;
	private String sord = "";
	private String sidx = "";
	private String searchField = "";
	private String searchString = "";
	private String searchOper = "";
	private Integer total = 0;
	private Integer records = 0;
	private String from_date=null;
	private String to_date=null;
	private String status;
	
	private String smsType=null;
	private String mobileNo;
	private List<Object> masterViewList = null;
	
	public String searchviewdata(){
		boolean sessionFlag = ValidateSession.checkSession(); 	
		if (sessionFlag) {
			try {
				String accountID = (String) session.get("accountid");
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query1 = new StringBuilder("");
				query1.append("select count(*) from msg_stats");
				List dataCount = cbt.executeAllSelectQuery(query1.toString(),
						connectionSpace);
				if (dataCount != null && dataCount.size() > 0) {
					BigInteger count = new BigInteger("1");
					for (Iterator it = dataCount.iterator(); it.hasNext();) {
						Object obdata = (Object) it.next();
						count = (BigInteger) obdata;
					}
					setRecords(count.intValue());
					int to = (getRows() * getPage());
					int from = to - getRows();
					if (to > getRecords())
						to = getRecords();

					// getting the list of column
					StringBuilder query = new StringBuilder("");
					query.append("select ");
					List fieldNames = Configuration.getColomnList(
							"mapped_instant_msg_configuration", accountID,
							connectionSpace, "instant_msg_configuration");
					int i = 0;
					List<String> listobj=new ArrayList<String>();
					if (fieldNames != null && fieldNames.size() > 0) {
						for (Iterator it = fieldNames.iterator(); it.hasNext();) {
							// generating the dyanamic query based on selected
							// fields
							Object obdata = (Object) it.next();
							if (obdata != null) {
								if (i < fieldNames.size() - 1){
									if(obdata.toString().equalsIgnoreCase("id")){
										listobj.add("id");
										query.append("sms_id" + ",");}
									else if(obdata.toString().equalsIgnoreCase("writeMessage")){
										listobj.add("writeMessage");
										query.append("msg" + ",");}
									else if(obdata.toString().equalsIgnoreCase("messageDraft")){}
									else if(obdata.toString().equalsIgnoreCase("messageName")){}
           					        else{
           					        	listobj.add(obdata.toString());
									query.append(obdata.toString() + ",");
									}
								}
								else{
									query.append(obdata.toString());
									listobj.add(obdata.toString());
								}
							}
							i++;
						}
					}
					query.append(" FROM msg_stats");
					
						
						// add search query based on the search operation
					  if(status!=null && status!=""){
						  if(status.equalsIgnoreCase("-1")){
						  }else{
							  query.append(" where ");
							query.append(" status = '"+ getStatus() + "'");}
					   }else if(smsType!=null && smsType!=""){
						   if(smsType.equalsIgnoreCase("-1")){
						   }else{
							   query.append(" where ");
								query.append(" msgType = '"+ getSmsType() + "'");
					   
						      }
						   }
					   else if( mobileNo!=null && mobileNo!=""){
						   query.append(" where ");
							query.append(" msisdn  like  "+"'"+ getMobileNo() +"%'");
				      }
					   else if( from_date!=null && from_date!=""  &&  to_date!=null &&  to_date!=""){
						   query.append(" where ");
							query.append("msg_date between '"+DateUtil.convertDateToUSFormat(getFrom_date())+"' and  '"+DateUtil.convertDateToUSFormat(getTo_date())+"'");
				      }
						else if (getSearchOper().equalsIgnoreCase("eq")) {
							query.append(" where ");
							query.append(" " + getSearchField() + " = '"
									+ getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("cn")) {
							query.append(" where ");
							query.append(" " + getSearchField() + " like '%"
									+ getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("bw")) {
							query.append(" where ");
							query.append(" " + getSearchField() + " like '"+ getSearchString() + "%'");
						} else if (getSearchOper().equalsIgnoreCase("ne")) {
							query.append(" where ");
							query.append(" " + getSearchField() + " <> '"
									+ getSearchString() + "'");
						} else if (getSearchOper().equalsIgnoreCase("ew")) {
							query.append(" where ");
							query.append(" " + getSearchField() + " like '%"
									+ getSearchString() + "'");
						}
					query.append(" limit " + from + "," + to);
					System.out.println(">>>"+query);
					List data = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
					if (data != null && data.size() > 0) {
						masterViewList = new ArrayList<Object>();
						List<Object> Listhb = new ArrayList<Object>();
						for (Iterator it = data.iterator(); it.hasNext();) {
							Object[] obdata = (Object[]) it.next();
							Map<String, Object> one = new HashMap<String, Object>();
							for (int k = 0; k < obdata.length; k++) {
								if (obdata[k] == null) {
									one.put(listobj.get(k).toString(), "NA");
								} else {
									if (k == 0){
										one.put(listobj.get(k).toString(),
												(Integer) obdata[k]);}
									  else{
										  if(listobj.get(k).toString().equalsIgnoreCase("status")){
											  if(obdata[k].toString().equalsIgnoreCase("0")){
												  one.put(listobj.get(k).toString(),
															"Unsent");  
											  }else{
												  one.put(listobj.get(k).toString(),
													"Sent");
											  }
											
										  }
										  else if(listobj.get(k).toString().equalsIgnoreCase("msg_date")){
											  
												  one.put(listobj.get(k).toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));  
										  } 
										  else{
											  
										   one.put(listobj.get(k).toString(),
												obdata[k].toString());}
								  
								    }
								}
							}
							Listhb.add(one);
						}
						setMasterViewList(Listhb);
						setTotal((int) Math.ceil((double) getRecords()
								/ (double) getRows()));
					}
				}
				return SUCCESS;
			} catch (Exception e) {
				e.printStackTrace();
				return ERROR;
			}
		} else {
			return LOGIN;
		}		
		
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}


	public List<Object> getMasterViewList() {
		return masterViewList;
	}

	public void setMasterViewList(List<Object> masterViewList) {
		this.masterViewList = masterViewList;
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

	
}
