package com.Over2Cloud.ctrl.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonInterface.CommonOperAtion;
import com.Over2Cloud.CommonInterface.CommonforClassdata;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.modal.dao.imp.signup.BeanApps;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class MainAction extends ActionSupport {
	Map session = ActionContext.getContext().getSession();
	private List<ConfigurationUtilBean> configBean = null;
	String userName = (String) session.get("uName");
	String accountID = (String) session.get("accountid");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	String dbName = (String) session.get("Dbname");
	String validApp = (String) session.get("validApp");
	private String titles = null;
	private Map<Integer, String> processname=null;
	public String execute() {
		if (userName == null || userName.equalsIgnoreCase(""))
			return LOGIN;

		return SUCCESS;
	}

	public String mainFrame() {
		if (userName == null || userName.equalsIgnoreCase(""))
			return LOGIN;
		String returnResult = ERROR;
		List resultdata = null;
		try {
			if(validApp!=null)
			{
			//	System.out.println("Valid App is as "+validApp);
				List<ConfigurationUtilBean> Listdata = new ArrayList<ConfigurationUtilBean>();
				StringBuilder query = new StringBuilder("select id,module_name from allcommontabtable");
				String tempvalidApp[]=validApp.split("#");
				if(tempvalidApp.length>0){
					query.append(" where ");
				}
				int i=1;
				for(String H:tempvalidApp){
					if(H!=null && !H.equalsIgnoreCase("")){
						if(i==1){
							query.append("moduleName='"+H+"'");
							i++;
						}
						else{
							query.append(" or moduleName='"+H+"'");
						}}
				}
				CommonOperInterface cbt=new CommonConFactory().createInterface();
				resultdata=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
				if(resultdata!=null){
					for (Iterator iterator = resultdata.iterator(); iterator.hasNext();) {
						ConfigurationUtilBean ConfigBeanObj = new ConfigurationUtilBean();
						Object[] objectCal = (Object[]) iterator.next();
						ConfigBeanObj.setId(Integer.parseInt(objectCal[0].toString()));
						ConfigBeanObj.setField_value(objectCal[1].toString());
						Listdata.add(ConfigBeanObj);
					}
				}
				setConfigBean(Listdata);
			//	System.out.println("Size of bean is as "+getConfigBean().size());
			}
			returnResult = SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonforClassdata obhj = new CommonOperAtion();
		try{
		// Product Related Work For User
		List<String>customercolname=new ArrayList<String>();
		customercolname.add("id");
		customercolname.add("name");
		List processData=obhj.viewAllDataEitherSelectOrAll("createprocessname",customercolname,connectionSpace);
		if(processData!=null)
		{
			processname=new LinkedHashMap<Integer, String>();
			for (Object c : processData) {
				Object[] dataC = (Object[]) c;
				if(dataC[0]!=null && dataC[1]!=null)
					processname.put((Integer)dataC[0],dataC[1].toString());
			}
			 Map<Integer, String> sortedMap = obhj.sortByComparator(processname);
			 setProcessname(sortedMap);
		 }
	
		}catch (Exception e) {
			e.printStackTrace();
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

	public List<ConfigurationUtilBean> getConfigBean() {
		return configBean;
	}

	public void setConfigBean(List<ConfigurationUtilBean> configBean) {
		this.configBean = configBean;
	}
	public Map<Integer, String> getProcessname() {
		return processname;
	}
	public void setProcessname(Map<Integer, String> processname) {
		this.processname = processname;
	}
}
