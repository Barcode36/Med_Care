package com.Over2Cloud.CommonInterface;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

public interface commanOperation 
{
	public boolean addDetails();
	public boolean UpdateDetails(Object UpdateObject);
	public boolean DeleteRecord(Object ObjectOfRecord);
    public int getRecordStatus();
    public List getDropdownvalueWithOutUser(String ParameterPass, int ID, String currentmonth, Class cCls);
    public List ServicesviewData(int to, int from, String SearchField,String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting );
    public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,String currentmonth);
    public Object findRecordForPk(int id);
    public List getDropdownvalue(String ParameterPass, String ID);
    public List getDropdownvalue(String ParameterPass);
    public List getSelectedvaluesInAnyClass(String PassSelectFieldValue[],HashMap<String, String> conditionForKeyvalue);
	public List ServicesviewforuserData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,String user,String superuser);
	  public List multiSearchViewdata(int to, int from, JSONObject jsonFilter, String SortType,String GetRowTypeSorting);
	  public List ServicesviewforuserDataWithoutUser(int to, int from, String SearchField,
	    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,String currentmonth);
	  public List multiSearchViewdataforalluser(int to, int from, JSONObject jsonFilter, String SortType,String GetRowTypeSorting);
}
