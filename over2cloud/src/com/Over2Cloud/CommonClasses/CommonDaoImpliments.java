package com.Over2Cloud.CommonClasses;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.type.ClassType;

import com.Over2Cloud.CommonClasses.AbstractCommonOperation;
import com.Over2Cloud.CommonInterface.CommonforClass;
import com.Over2Cloud.CommonInterface.commanOperation;

public class CommonDaoImpliments extends AbstractCommonOperation<ClassType> implements commanOperation
{	
	static final Log log = LogFactory.getLog(CommonDaoImpliments.class);
	public String uName;
	public String superName;
	Object objct;
	Class cCls;

	
	public CommonDaoImpliments(){super();}
	// This Constructors Tell the Object Of Class and Class Name Of Object
	public CommonDaoImpliments(Object objct1){
		this.objct=objct1;
		this.cCls = objct1.getClass();
	}
	/*  1
	 * Add Data All Record In User(non-Javadoc)
	 * @see com.dreamsol.modal.dao.standard.assets.commanOperation#addDetails()
	 */
	public boolean addDetails(){
		 return super.addDetails(objct);
	}
	/* 2:::2
	 * Update Of The Record Of the Data(non-Javadoc)
	 * @see com.dreamsol.modal.dao.standard.assets.commanOperation#UpdateDetails(java.lang.Object)
	 */
	public boolean UpdateDetails(Object UpdateObject) {
		return super.UpdateDetails(UpdateObject);
	}
    
	/* 
	 * Delete Record All Of the Data(non-Javadoc)
	 * @see com.dreamsol.modal.dao.standard.assets.commanOperation#DeleteRecord(java.lang.Object)
	 */
	public boolean DeleteRecord(Object ObjectOfRecord) {
		return super.DeleteRecord(ObjectOfRecord,cCls);
	}
	/* 
	 * View All Record In generic type
	 * @see com.dreamsol.modal.dao.standard.assets.commanOperation#ServicesviewData(java.lang.Object)
	 */
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting) {
		return super.ServicesviewData(to,  from,  SearchField,
	    		 SearchString,  SearchOperation,  SortType, GetRowTypeSorting, cCls);
	}   
	/* 4
	 * Get Record Of the data in DB.....(non-Javadoc)
	 * @see com.dreamsol.modal.dao.standard.assets.commanOperation#getRecordStatus()
	 */
    public int getRecordStatus()
    {
    	return super.getRecordStatus(cCls);
    }
    /*
     * For Particular Id Basic record find.....
     * (non-Javadoc)
     * @see com.dreamsol.modal.dao.standard.assets.commanOperation#findRecordForPk(int)
     */
    public Object findRecordForPk(int id) {
    	return super.findRecordForPk(id, cCls);
    } 
    // Pick Drop Down Value in condition wise
    public List getDropdownvalue(String ParameterPass, String ID)
    {
    	return super.getDropdownvalue(ParameterPass, ID, cCls);
    }
 // Pick Drop Down Value in Pk wise
    public List getDropdownvalue(String ParameterPass)
    {
    	return super.getDropdownvalue(ParameterPass, cCls);
    }

    // Select Any Class In for nay value
    public List getSelectedvaluesInAnyClass(String PassSelectFieldValue[],HashMap<String, String> conditionForKeyvalue)
    {
    	return super.getSelectedvalues( PassSelectFieldValue, conditionForKeyvalue,cCls);
    }
 
	@Override
	public List getDropdownvalue(String ParameterPass, List ID, Class cls,
			String oper, boolean isadmin, String superuserparameter,
			String superusername) {
		CommonforClass asd=new CommanOper();return asd.getDropdownvalue(ParameterPass, ID, cCls, oper,isadmin,superuserparameter,superusername);}
	
	public List ServicesviewData(int to, int from, String SearchField,
    		String SearchString, String SearchOperation, String SortType,String GetRowTypeSorting,Class cCls,String UserYes,String Superyes){
	  return super.ServicesviewData(to,  from,  SearchField,
   		 SearchString,  SearchOperation,  SortType, GetRowTypeSorting, cCls ,UserYes,Superyes);
	}
	public List ServicesviewData(int to, int from, String SearchField,
			String SearchString, String SearchOperation, String SortType,
			String GetRowTypeSorting, String currentmonth) {
		// TODO Auto-generated method stub
		return super.ServicesviewData(to,  from,  SearchField,
		   		 SearchString,  SearchOperation,  SortType, GetRowTypeSorting, cCls ,currentmonth);
	}

	@Override
	public List ServicesviewforuserData(int to, int from, String SearchField,
			String SearchString, String SearchOperation, String SortType,
			String GetRowTypeSorting, String user, String superuser) {
		return super.ServicesviewforuserData(to,  from,  SearchField,
		   		 SearchString,  SearchOperation,  SortType, GetRowTypeSorting, cCls ,user,superuser);
	}

	public List multiSearchViewdata(int to, int from, JSONObject jsonFilter,
			String SortType, String GetRowTypeSorting) {
		// TODO Auto-generated method stub
		return super.multiSearchViewdata(to, from, jsonFilter, SortType, GetRowTypeSorting, cCls);
	}
	public List multiSearchViewdataforalluser(int to, int from, JSONObject jsonFilter,
			String SortType, String GetRowTypeSorting) {
		// TODO Auto-generated method stub
		return super.multiSearchViewdataforalluser(to, from, jsonFilter, SortType, GetRowTypeSorting, cCls);
	}
	@Override
	public List ServicesviewforuserDataWithoutUser(int to, int from,
			String SearchField, String SearchString, String SearchOperation,
			String SortType, String GetRowTypeSorting, String currentmonth) {
		// TODO Auto-generated method stub
		return super.ServicesviewforuserDataWithoutUser(to,  from,  SearchField,
		   		 SearchString,  SearchOperation,  SortType, GetRowTypeSorting,currentmonth, cCls);
	}
}
