package com.Over2Cloud.ctrl.MachineOrder;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.Over2Cloud.BeanUtil.ConfigurationUtilBean;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.action.GridPropertyBean;

public class HighCostMed extends GridPropertyBean implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	List<ConfigurationUtilBean> HighCostMedTextBox;
	List<ConfigurationUtilBean> HighCostMedDropDown;
	
	public String HighCostMedHeader(){
		return SUCCESS;
		
	}
	
	// for add medicine 
	
	public String beforecreateAdd()
	{
		//System.out.println("check");
		try {
			if (userName == null || userName.equalsIgnoreCase(""))
				return LOGIN;
			addPageCostMedPage();

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	public void addPageCostMedPage() {
		try
		{

		List<GridDataPropertyView> offeringLevel1 = Configuration.getConfigurationData("mapped_reason_configuration", accountID, connectionSpace, "", 0, "table_name", "machine_reason_master_configuration");
		HighCostMedTextBox = new ArrayList<ConfigurationUtilBean>();
		HighCostMedDropDown = new ArrayList<ConfigurationUtilBean>();

		for (GridDataPropertyView gdp : offeringLevel1)
		{
			ConfigurationUtilBean obj = new ConfigurationUtilBean();
			if (gdp.getColType().trim().equalsIgnoreCase("T") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("time") && !gdp.getColomnName().equalsIgnoreCase("user_Name"))
			{
				obj.setValue(gdp.getHeadingName());
				obj.setKey(gdp.getColomnName());
				obj.setValidationType(gdp.getValidationType());
				obj.setColType(gdp.getColType());
					if (gdp.getMandatroy().toString().equals("1"))
						{
							obj.setMandatory(true);
						}
					else
						{
						obj.setMandatory(false);
						}
						corporateTextBox.add(obj);
			}

			if (gdp.getColType().trim().equalsIgnoreCase("D") && !gdp.getColomnName().equalsIgnoreCase("status") && !gdp.getColomnName().equalsIgnoreCase("user_name") && !gdp.getColomnName().equalsIgnoreCase("date") && !gdp.getColomnName().equalsIgnoreCase("time")
					&& !gdp.getColomnName().equalsIgnoreCase("status") )
				{
					//System.out.println("DDDDDDDDDD  "+gdp.getColType().toString());
					obj.setValue(gdp.getHeadingName());
					obj.setKey(gdp.getColomnName());
					//System.out.println("gdp.getColomnName()  "+gdp.getColomnName().toString());
					obj.setValidationType(gdp.getValidationType());
					obj.setColType(gdp.getColType());
						if (gdp.getMandatroy().toString().equals("1"))
							{
								obj.setMandatory(true);
							}
						else
							{
								obj.setMandatory(false);
							}
						corporateDropDown.add(obj);
				}
		}
		}catch (Exception ex)
		{
			
			ex.printStackTrace();
			}
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public List<ConfigurationUtilBean> getHighCostMedTextBox() {
		return HighCostMedTextBox;
	}

	public void setHighCostMedTextBox(List<ConfigurationUtilBean> highCostMedTextBox) {
		HighCostMedTextBox = highCostMedTextBox;
	}

	public List<ConfigurationUtilBean> getHighCostMedDropDown() {
		return HighCostMedDropDown;
	}

	public void setHighCostMedDropDown(
			List<ConfigurationUtilBean> highCostMedDropDown) {
		HighCostMedDropDown = highCostMedDropDown;
	}
	

}
