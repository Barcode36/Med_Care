package com.Over2Cloud.ctrl.common.communication;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AutoPushReportAction extends ActionSupport{
	

	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	  private Integer rows=0;
	  private Integer page=0;
	  private String  sord=null;
	  private String  sidx=null;
	  private String  searchField=null;
	  private String  searchString=null;
	  private Integer totalrows=null;
	  private String  searchOper=null;
	  private Integer total=0;
	  private Integer records=0;
	  private boolean loadonce=false;
	  private List<Object> pushreportModel;
	  private Map<String,Object> mapObj;
	  private String from_date=null;
	  private String to_date=null;
	  private String status;
	  private String smsType=null;
	  private String mobileNo;
	public String beforviewAutoPushreport(){
		String returnString=ERROR;
		try{
		    mapObj=new LinkedHashMap<String, Object>();
		    mapObj.put("sms_id","Id");
		    mapObj.put("mobileno","Mobile Number");
		    mapObj.put("message","Message");
		    mapObj.put("msg_date","Date");
		    mapObj.put("msg_time","Time");
		    mapObj.put("status","Status");
		    mapObj.put("client_id","Client Id");
			setMapObj(mapObj);
			returnString=SUCCESS;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return returnString;
	}
	
	public String viewautopushreport(){
		String returnString=ERROR;
		  CommonforClassdata obhj = new CommonOperAtion();
		try{
		    StringBuilder query=new StringBuilder("");
			query.append("select count(*) from"+" "+("msg_stats_trans where status=3"));
		    List  dataCount=obhj.executeAllSelectQuery(query.toString(),connectionSpace);
			if(dataCount!=null){
				BigInteger count=new BigInteger("3");
				for(Iterator it=dataCount.iterator(); it.hasNext();){
					 Object obdata=(Object)it.next();
					 count=(BigInteger)obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
				List<String>reportdataColumn=new ArrayList<String>();
				StringBuilder stringObjt=new StringBuilder("");
				List<Object> Listhb=new ArrayList<Object>();
				
				stringObjt.append("select ");
				
				stringObjt.append("sms_id"+",");
				stringObjt.append("mobileno"+",");
				stringObjt.append("message"+",");
				stringObjt.append("msg_date"+",");
				stringObjt.append("msg_time"+",");
				stringObjt.append("status"+",");
				stringObjt.append("client_id");
				
				
				
				reportdataColumn.add("sms_id");
				reportdataColumn.add("mobileno");
				reportdataColumn.add("message");
				reportdataColumn.add("msg_date");
				reportdataColumn.add("msg_time");
				reportdataColumn.add("status");
				reportdataColumn.add("client_id");
				
				String [] fieldsArray = reportdataColumn.toArray(new String[reportdataColumn.size()]);
		    	stringObjt.append(" from msg_stats_trans");
		    	
		    	
		    	
			if(getSearchField()!=null && getSearchString()!=null && !getSearchField().equalsIgnoreCase("") && !getSearchString().equalsIgnoreCase("")){
				if(getSearchOper().equalsIgnoreCase("eq")){
					stringObjt.append("and "+getSearchField()+" = '"+getSearchString()+"'");}
				else if(getSearchOper().equalsIgnoreCase("cn")){
					stringObjt.append("and "+getSearchField()+" like '%"+getSearchString()+"%'");}
				else if(getSearchOper().equalsIgnoreCase("bw")){
					stringObjt.append("and "+getSearchField()+" like '"+getSearchString()+"%'");}
				else if(getSearchOper().equalsIgnoreCase("ne")){
					stringObjt.append("and "+getSearchField()+" <> '"+getSearchString()+"'");}
				else if(getSearchOper().equalsIgnoreCase("ew")){
					stringObjt.append("and "+getSearchField()+" like '%"+getSearchString()+"'");
				}}
		
			stringObjt.append(" limit "+from+","+to);
			System.out.println(">>>>>>>>>>>"+stringObjt.toString());
			List  processData=obhj.executeAllSelectQuery(stringObjt.toString(),connectionSpace);
			if(processData!=null){
					if(processData.size()>0){
						for(Iterator it=processData.iterator(); it.hasNext();){
							Object[] obdata=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < obdata.length; k++) {
								if(obdata[k]!=null){
								if(k==0){
									one.put(fieldsArray[k], (Integer)obdata[k]);}
								else{
									 if(fieldsArray[k].equalsIgnoreCase("status")){
										  if(obdata[k].toString().equalsIgnoreCase("0")){
											  one.put(fieldsArray[k],
														"Unsent");  
										  }else{
											  one.put(fieldsArray[k].toString(),
												"Sent");
										  }
										
									  } else if(fieldsArray[k].equalsIgnoreCase("msg_date")){
										  one.put(fieldsArray[k].toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));  
								     } 
									 else{
									one.put(fieldsArray[k], obdata[k].toString());
									  }
								
								}
								
								}
							}
							Listhb.add(one);
						}
						setPushreportModel(Listhb);
					}
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					
			}
			returnString=SUCCESS;
		  
	  }}catch (Exception e) {
			// TODO: handle exception
		}
		return returnString;
	}
	
	
	
	public String autupushreportserach(){
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String returnString=ERROR;
		  CommonforClassdata obhj = new CommonOperAtion();
		try{
		    StringBuilder query=new StringBuilder("");
			query.append("select count(*) from"+" "+("msg_stats_trans"));
		    List  dataCount=obhj.executeAllSelectQuery(query.toString(),connectionSpace);
			if(dataCount!=null){
				BigInteger count=new BigInteger("3");
				for(Iterator it=dataCount.iterator(); it.hasNext();){
					 Object obdata=(Object)it.next();
					 count=(BigInteger)obdata;
				}
				setRecords(count.intValue());
				int to = (getRows() * getPage());
				int from = to - getRows();
				if (to > getRecords())
					to = getRecords();
				List<String>reportdataColumn=new ArrayList<String>();
				StringBuilder stringObjt=new StringBuilder("");
				List<Object> Listhb=new ArrayList<Object>();
				
				stringObjt.append("select ");
				
				stringObjt.append("sms_id"+",");
				stringObjt.append("mobileno"+",");
				stringObjt.append("message"+",");
				stringObjt.append("msg_date"+",");
				stringObjt.append("msg_time"+",");
				stringObjt.append("status"+",");
				stringObjt.append("client_id");
				
				
				
				reportdataColumn.add("sms_id");
				reportdataColumn.add("mobileno");
				reportdataColumn.add("message");
				reportdataColumn.add("msg_date");
				reportdataColumn.add("msg_time");
				reportdataColumn.add("status");
				reportdataColumn.add("client_id");
				
				String [] fieldsArray = reportdataColumn.toArray(new String[reportdataColumn.size()]);
		    	stringObjt.append(" from msg_stats_trans");
		    	
		    	stringObjt.append(" where ");
				// add search query based on the search operation
			  if(status!=null && status!=""){
				  stringObjt.append(" status = '"+ getStatus() + "'");
			   }
			   else if( mobileNo!=null && mobileNo!=""){
				   stringObjt.append(" mobileno  like  "+"'"+ getMobileNo() +"%'");
		      }
			   else if( from_date!=null && from_date!=""  &&  to_date!=null &&  to_date!=""){
				   stringObjt.append("msg_date between '"+DateUtil.convertDateToUSFormat(getFrom_date())+"' and  '"+DateUtil.convertDateToUSFormat(getTo_date())+"'");
		      }
			stringObjt.append(" limit "+from+","+to);
			System.out.println(">>>>>>>>>>>"+stringObjt.toString());
			List  processData=obhj.executeAllSelectQuery(stringObjt.toString(),connectionSpace);
			if(processData!=null){
					if(processData.size()>0){
						for(Iterator it=processData.iterator(); it.hasNext();){
							Object[] obdata=(Object[])it.next();
							Map<String, Object> one=new HashMap<String, Object>();
							for (int k = 0; k < obdata.length; k++) {
								if(obdata[k]!=null){
								if(k==0){
									one.put(fieldsArray[k], (Integer)obdata[k]);}
								else{
									 if(fieldsArray[k].equalsIgnoreCase("status")){
										  if(obdata[k].toString().equalsIgnoreCase("0")){
											  one.put(fieldsArray[k],
														"Unsent");  
										  }else{
											  one.put(fieldsArray[k].toString(),
												"Sent");
										  }
										
									  } else if(fieldsArray[k].equalsIgnoreCase("msg_date")){
										  one.put(fieldsArray[k].toString(),DateUtil.convertDateToIndianFormat(obdata[k].toString()));  
								     } 
									 else{
									one.put(fieldsArray[k], obdata[k].toString());
									  }
								
								}
								
								}
							}
							Listhb.add(one);
						}
						setPushreportModel(Listhb);
					}
					setTotal((int) Math.ceil((double) getRecords() / (double) getRows()));
					
			}
			returnString=SUCCESS;
		  
	  }}catch (Exception e) {
			// TODO: handle exception
		}
		return returnString;
	
	}
	
	

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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

	public List<Object> getPushreportModel() {
		return pushreportModel;
	}

	public void setPushreportModel(List<Object> pushreportModel) {
		this.pushreportModel = pushreportModel;
	}

	public Map<String, Object> getMapObj() {
		return mapObj;
	}

	public void setMapObj(Map<String, Object> mapObj) {
		this.mapObj = mapObj;
	}

}
