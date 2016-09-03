package com.Over2Cloud.ctrl.common.communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.UserAccountutilBean;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.ctrl.ServiceListener.AccountstatusService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AccountstatusAction extends ActionSupport{
	
	Map session = ActionContext.getContext().getSession();
	String userName = (String) session.get("uName");
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	private String clientid=null;
	private String validfrom=null;
	private String validto=null;
	private String totalcredits=null;
	private String usedcredits=null;
	private String balancecredit=null;
	private  List<UserAccountutilBean> promoorderList =null;
	private  List<UserAccountutilBean> transoorderList =null;
	private  List<UserAccountutilBean> openoorderList =null;
	public String accountstatusView(){
		String returnString =ERROR;
		String objString;
		try{
			UserAccountutilBean useraccountobj=null;
			CommonforClassdata obhj = new CommonOperAtion();
			Map<String, Object> whereClose = new HashMap<String, Object>();
			List<String> listdata = new ArrayList<String>();
		
			listdata.add("id");
			listdata.add("root");
			listdata.add("client_id");
			List datatemp = obhj.getSelectdataFromSelectedFields("mapped_clientid",listdata, whereClose, connectionSpace);
		 	if(datatemp!=null && datatemp.size()>0){
			for (Iterator iterator = datatemp.iterator(); iterator.hasNext();) {
				Object[] object = (Object[]) iterator.next();
				
				if(object[1].toString().equalsIgnoreCase("Promo") ){

				   objString =AccountstatusService.getAccountDetails(object[2].toString());
					 promoorderList = new ArrayList<UserAccountutilBean>();
				   useraccountobj= new UserAccountutilBean();
					if(objString!=null){
						String arryString[]=objString.split("_"); 
						if(arryString[0]!=null){
							useraccountobj.setTotalcredits(arryString[0]);
					    }
						else{
							useraccountobj.setTotalcredits("NA");
						}
						if(arryString[1]!=null){
							useraccountobj.setUsedcredits(arryString[1]);
						}
						else{
							useraccountobj.setUsedcredits("NA");
						}
						if(arryString[2]!=null){
							useraccountobj.setBalancecredit(arryString[2]);
							}
							else{useraccountobj.setBalancecredit("NA");
							}
						if(arryString[4].toString()!=null){
							useraccountobj.setValidto(arryString[4]);
						}else{
							useraccountobj.setValidto("NA");
						}
						if(arryString[3].toString()!=null){
							useraccountobj.setValidfrom(arryString[3]);
						}else{
							useraccountobj.setValidfrom("NA");
						}
						useraccountobj.setClientid(arryString[5]);
						promoorderList.add(useraccountobj);
						setPromoorderList(promoorderList);
				   }
				}
				
				if(object[1].toString().equalsIgnoreCase("Trans") ){

					objString =AccountstatusService.getAccountDetails(object[2].toString());
					  transoorderList = new ArrayList<UserAccountutilBean>();
				  useraccountobj= new UserAccountutilBean();
					if(objString!=null){
						String arryString[]=objString.split("_"); 
						if(arryString[0]!=null){
							useraccountobj.setTotalcredits(arryString[0]);
					    }
						else{
							useraccountobj.setTotalcredits("NA");
						}
						if(arryString[1]!=null){
							useraccountobj.setUsedcredits(arryString[1]);
						}
						else{
							useraccountobj.setUsedcredits("NA");
						}
						if(arryString[2]!=null){
							useraccountobj.setBalancecredit(arryString[2]);
							}
							else{useraccountobj.setBalancecredit("NA");
							}
						if(arryString[4].toString()!=null){
							useraccountobj.setValidto(arryString[4]);
						}else{
							useraccountobj.setValidto("NA");
						}
						if(arryString[3].toString()!=null){
							useraccountobj.setValidfrom(arryString[3]);
						}else{
							useraccountobj.setValidfrom("NA");
						}
						useraccountobj.setClientid(arryString[5]);
						transoorderList.add(useraccountobj);
						setTransoorderList(transoorderList);
				   }
				}
				if(object[1].toString().equalsIgnoreCase("Open") ){
				 objString =AccountstatusService.getAccountDetails(object[2].toString());
					openoorderList = new ArrayList<UserAccountutilBean>();
				  useraccountobj= new UserAccountutilBean();
					if(objString!=null){
						String arryString[]=objString.split("_"); 
						if(arryString[0]!=null){
							useraccountobj.setTotalcredits(arryString[0]);
					    }
						else{
							useraccountobj.setTotalcredits("NA");
						}
						if(arryString[1]!=null){
							useraccountobj.setUsedcredits(arryString[1]);
						}
						else{
							useraccountobj.setUsedcredits("NA");
						}
						if(arryString[2]!=null){
							useraccountobj.setBalancecredit(arryString[2]);
							}
							else{useraccountobj.setBalancecredit("NA");
							}
						if(arryString[4].toString()!=null){
							useraccountobj.setValidto(arryString[4]);
						}else{
							useraccountobj.setValidto("NA");
						}
						if(arryString[3].toString()!=null){
							useraccountobj.setValidfrom(arryString[3]);
						}else{
							useraccountobj.setValidfrom("NA");
						}
						useraccountobj.setClientid(arryString[5]);
						openoorderList.add(useraccountobj);
						setOpenoorderList(openoorderList);
				   }
				}
				
			}
			returnString=SUCCESS;
		}else{
			addActionMessage("Server is Not Respoding .");
			returnString=ERROR;
		}
			}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return returnString;
	}
	
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getValidfrom() {
		return validfrom;
	}
	public void setValidfrom(String validfrom) {
		this.validfrom = validfrom;
	}
	public String getValidto() {
		return validto;
	}
	public void setValidto(String validto) {
		this.validto = validto;
	}
	public String getTotalcredits() {
		return totalcredits;
	}
	public void setTotalcredits(String totalcredits) {
		this.totalcredits = totalcredits;
	}
	public String getUsedcredits() {
		return usedcredits;
	}
	public void setUsedcredits(String usedcredits) {
		this.usedcredits = usedcredits;
	}
	public String getBalancecredit() {
		return balancecredit;
	}
	public void setBalancecredit(String balancecredit) {
		this.balancecredit = balancecredit;
	}

	public List<UserAccountutilBean> getPromoorderList() {
		return promoorderList;
	}

	public void setPromoorderList(List<UserAccountutilBean> promoorderList) {
		this.promoorderList = promoorderList;
	}

	public List<UserAccountutilBean> getTransoorderList() {
		return transoorderList;
	}

	public void setTransoorderList(List<UserAccountutilBean> transoorderList) {
		this.transoorderList = transoorderList;
	}

	public List<UserAccountutilBean> getOpenoorderList() {
		return openoorderList;
	}

	public void setOpenoorderList(List<UserAccountutilBean> openoorderList) {
		this.openoorderList = openoorderList;
	}
	

}
