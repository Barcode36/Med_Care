package com.Over2Cloud.CommonInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public interface CommonforClass 
{
	public boolean addDetails(Object objct);
	public boolean UpdateDetails(Object UpdateObject);
	public boolean DeleteRecord(Object ObjectOfRecord,Class cCls );
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls);
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,int flag,Class cCls);
	public List ServicesviewData(int to, int from, String SearchField,
    String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,String ParId,int id,Class cCls);
	public List ServicesviewData(int to, int from, String SearchField,
		    String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,String ParId,int id,Class cCls,String user,String superuser);
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,HashMap<String, Object> conditionForKeyvalue,Class cCls);
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String UserYes,String Superyes);
	public List ServicesviewforuserData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String user,String superuser);
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String currentmonth);
	public Object getUniqueDataByField(String searchField,
			Object searchString,Class clszz);
	 public Object getUniqueDataByField(String searchField,
				Object searchString,String parameter,Object paramValue,Class clszz) ;
	  public Object getUniqueDataByField(String searchField,
				Object searchString,String parameter,Object paramValue,String user,Class clszz);
	public int getRecordStatus(Class cCls );
	public List getDropdownvalueAsIn(String ParameterPass, int ID,List UserList,Class cCls);
    public Object findRecordForPk(int id,Class cCls) ;
    public List getDropdownvalue(String ParameterPass, int ID,Class cCls);
    public List getDropdownvalue(String ParameterPass, String ID,Class cCls);
    public List getDropdownvalue(String ParameterPass,Class cCls);
    public List getDropdownvalueWithOutUser(String ParameterPass, int ID, String currentmonth, Class cCls);
    public List getDropdownvalue(String ParameterPass, String user,String superuser,Class cCls);
    public List getDropdownvalue(String ParameterPass, List ID,Class cCls,String oper,boolean isadmin,String superuserparameter,String superusername);
    public List getSelectedvalues(String PassSelectFieldValue[],HashMap<String, String> conditionForKeyvalue, Class cCls);
    public List getMaxdateValues(String ParameterPass,Class cCls);
    public List getJoingTableId(String Param,int Id, String name,Class clss,Class class1);
    public List getinnerjoindata(String Param,int Id, String name,Class clss,Class class1);
    public List getNonUniqueDataFromSqlProcedure(String query, Map paramMap);
    public List ServicesviewforuserDataNewUser(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String user,String superuser);
    public List getDropdownvalueEKA(String ParameterPass, String ID,Class cCls);
    public List getDropdownvalueEKAName(String ParameterPass, Object ID,Class cCls);
    public Object findRecordForObject(String id,Class cCls);
    public List getDropdownvalueWithOutUser(String ParameterPass, int ID,Class cCls);
    public List ServicesviewforuserDataWithoutUser(int to, int from, String SearchField,
            String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String user,String superuser);
   public List viewDataByLike(String tag);
   public List multiSearchViewdata(int to, int from, JSONObject jsonFilter, String SortType,String GetRowTypeSorting, Class clazz);
   /**
    * by sandeep
    * @param ParameterPass
    * @param ID
    * @param cCls
    * @return
    */
   public List getDropdownvalueForHierarchywithsuperuser(String ParameterPass, String ID,Class cCls);
   public List getDesignationLevelName(String ParameterPass, int ID,Class cCls);
   public List ServicesviewforuserDataWithoutUserWithParam(int to, int from, String SearchField,
   		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String param,String val,String superuser);
   public List getDropdownvalue(String ParameterPass, int ID,Class cCls,String superUser);
   public List cloudgetDropdownvalue(String ParameterPass,Class cCls);

}
