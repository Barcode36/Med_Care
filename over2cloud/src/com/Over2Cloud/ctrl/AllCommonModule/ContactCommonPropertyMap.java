package com.Over2Cloud.ctrl.AllCommonModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ContactCommonPropertyMap{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map session = ActionContext.getContext().getSession();
	String userName=(String)session.get("uName");
	String accountID=(String)session.get("accountid");
	SessionFactory connectionSpace=(SessionFactory)session.get("connectionSpace");
		private String key;
		public  Map<String,String> valueMap;
		public List<ConfigurationUtilBean> contactdetails = null;
		private String xyzz=null;
		private String basicdetails_table=null;
		private String addressdetails_table=null;
		private String customdetails_table=null;
		private String descriptive_table=null;
		
		public Map<String, List<ConfigurationUtilBean>> commonMap = null;
		{	
			commonMap = new HashMap<String, List<ConfigurationUtilBean>>();
			contactdetails = new ArrayList<ConfigurationUtilBean>();
			try{
			List<GridDataPropertyView> basicfieldNames=Configuration.getColomnfieldList("mapped_contactdetails_configuration", accountID, connectionSpace,"contactbasicdetail_configuration ");
			int l=0;
			for (GridDataPropertyView col : basicfieldNames) {
				ConfigurationUtilBean objdata=new ConfigurationUtilBean();
			    if(col!=null) {
				    if(l<basicfieldNames.size()-1){
				    	if(col.getMandatroy()!="0"){
				    		objdata.setKey("contactms."+col.getColomnName());
				    		objdata.setValue(col.getHeadingName());
				    		if(col.getMandatroy()==null)
				    			objdata.setMandatory(false);
							else if(col.getMandatroy().equalsIgnoreCase("0"))
								objdata.setMandatory(false);
							else if(col.getMandatroy().equalsIgnoreCase("1"))
								objdata.setMandatory(true);
				    		contactdetails.add(objdata);}
					    	else{
					    		objdata.setKey("contactms."+col.getColomnName());
					    		objdata.setValue(col.getHeadingName());
					    		if(col.getMandatroy()==null)
					    			objdata.setMandatory(false);
								else if(col.getMandatroy().equalsIgnoreCase("0"))
									objdata.setMandatory(false);
								else if(col.getMandatroy().equalsIgnoreCase("1"))
									objdata.setMandatory(true);
					    		contactdetails.add(objdata);
					    		}
					        }
				    else{
				    	objdata.setKey("contactms."+col.getColomnName());
			    		objdata.setValue(col.getHeadingName());
			    		if(col.getMandatroy()==null)
			    			objdata.setMandatory(false);
						else if(col.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if(col.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
			    		contactdetails.add(objdata);
				    	 }}
			    l++;
		      }}catch (Exception e) {
				// TODO: handle exception
			}
			try{
			List<GridDataPropertyView> addressfieldNames=Configuration.getColomnfieldList("mapped_contactdetails_configuration", accountID, connectionSpace,"contactaddressdetail_configuration ");
			int J=0;
			for (GridDataPropertyView col : addressfieldNames) {
				ConfigurationUtilBean objdata=new ConfigurationUtilBean();
			    if(col!=null) {
				    if(J<addressfieldNames.size()-1){
				    	if(col.getMandatroy()!="0"){
				    		objdata.setKey("contacadrss."+col.getColomnName());
				    		objdata.setValue(col.getHeadingName());
				    		if(col.getMandatroy()==null)
				    			objdata.setMandatory(false);
							else if(col.getMandatroy().equalsIgnoreCase("0"))
								objdata.setMandatory(false);
							else if(col.getMandatroy().equalsIgnoreCase("1"))
								objdata.setMandatory(true);
				    		contactdetails.add(objdata);
				    	 }
				    	else{
				    		objdata.setKey("contacadrss."+col.getColomnName());
				    		objdata.setValue(col.getHeadingName());
				    		if(col.getMandatroy()==null)
				    			objdata.setMandatory(false);
							else if(col.getMandatroy().equalsIgnoreCase("0"))
								objdata.setMandatory(false);
							else if(col.getMandatroy().equalsIgnoreCase("1"))
								objdata.setMandatory(true);
				    		contactdetails.add(objdata);
				    	   }
				        }
				    else{
				    	objdata.setKey("contacadrss."+col.getColomnName());
			    		objdata.setValue(col.getHeadingName());
			    		if(col.getMandatroy()==null)
			    			objdata.setMandatory(false);
						else if(col.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if(col.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
			    		contactdetails.add(objdata);
				    	 }}
			    J++;
		}}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		    }
			commonMap.put("contactdetails", contactdetails);
      		}
	
	
		public String getBasicdetails_table() {
			return basicdetails_table;
		}

		public void setBasicdetails_table(String basicdetails_table) {
			this.basicdetails_table = basicdetails_table;
		}

		public String getAddressdetails_table() {
			return addressdetails_table;
		}

		public void setAddressdetails_table(String addressdetails_table) {
			this.addressdetails_table = addressdetails_table;
		}

		public String getCustomdetails_table() {
			return customdetails_table;
		}

		public void setCustomdetails_table(String customdetails_table) {
			this.customdetails_table = customdetails_table;
		}

		public String getDescriptive_table() {
			return descriptive_table;
		}

		public void setDescriptive_table(String descriptive_table) {
			this.descriptive_table = descriptive_table;
		}

		public String getXyzz() {
			
			return xyzz;
		}

		public void setXyzz(String xyzz) {
			this.xyzz = xyzz;
		}

		   public String getKey() {
			   
	            return key;
	    }

	    public void setKey(String key) {
	            this.key = key;
	    }

	    public List<ConfigurationUtilBean> getValueMap() {
	 
            return commonMap.get(key);
            
         }

         public List<ConfigurationUtilBean> getTitles(String key) {
            return commonMap.get(key);
         }





}
