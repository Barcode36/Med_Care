package com.Over2Cloud.ctrl.AllCommonModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.opensymphony.xwork2.ActionContext;

public class PrdouctCommonPropertyMap {
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
		public List<ConfigurationUtilBean> producttdetails = null;
		
		public Map<String, List<ConfigurationUtilBean>> commonMap = null;
		{	
			commonMap = new HashMap<String, List<ConfigurationUtilBean>>();
			producttdetails = new ArrayList<ConfigurationUtilBean>();
			try{
			List<GridDataPropertyView> basicfieldNames=Configuration.getColomnfieldList("mappedproductsetting_configuration", accountID, connectionSpace,"configuration_productmgmt");
			int l=0;
			for (GridDataPropertyView col : basicfieldNames) {
				ConfigurationUtilBean objdata=new ConfigurationUtilBean();
			    if(col!=null) {
				    if(l<basicfieldNames.size()-1){
				    	if(col.getMandatroy()!="0"){
				    		objdata.setKey("product."+col.getColomnName());
				    		objdata.setValue(col.getHeadingName());
				    		if(col.getMandatroy()==null)
				    			objdata.setMandatory(false);
							else if(col.getMandatroy().equalsIgnoreCase("0"))
								objdata.setMandatory(false);
							else if(col.getMandatroy().equalsIgnoreCase("1"))
								objdata.setMandatory(true);
				    		   producttdetails.add(objdata);
				    		}
					    	else{
					    		objdata.setKey("product."+col.getColomnName());
					    		objdata.setValue(col.getHeadingName());
					    		if(col.getMandatroy()==null)
					    			objdata.setMandatory(false);
								else if(col.getMandatroy().equalsIgnoreCase("0"))
									objdata.setMandatory(false);
								else if(col.getMandatroy().equalsIgnoreCase("1"))
									objdata.setMandatory(true);
					    		   producttdetails.add(objdata);
					    		}
					        }
				    else{
				    	objdata.setKey("product."+col.getColomnName());
			    		objdata.setValue(col.getHeadingName());
			    		if(col.getMandatroy()==null)
			    			objdata.setMandatory(false);
						else if(col.getMandatroy().equalsIgnoreCase("0"))
							objdata.setMandatory(false);
						else if(col.getMandatroy().equalsIgnoreCase("1"))
							objdata.setMandatory(true);
			    		   producttdetails.add(objdata);
				    	 }}
			    l++;
		      }}catch (Exception e) {
				// TODO: handle exception
			}
		   
			commonMap.put("productdetails", producttdetails);
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
