package com.Over2Cloud.CommonClasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;

public class CommonWork {

	public static Map<Integer, String> allEmpList(SessionFactory connectionSpace) {
		Map<Integer, String> empDataList = new HashMap<Integer, String>();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select id,empName from employee_basic");
		List data = cbt
				.executeAllSelectQuery(query.toString(), connectionSpace);
		if (data != null) {
			for (Iterator it = data.iterator(); it.hasNext();) {
				Object[] obdata = (Object[]) it.next();
				empDataList.put((Integer) obdata[0], obdata[1].toString());
			}
		}
		return empDataList;
	}

	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	public static Map<Integer, String> getTargetOfEmployee(String userID,
			SessionFactory connectionSpace) {
		Map<Integer, String> targetKpiLiist = new HashMap<Integer, String>();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");

		try {
			userID = Cryptography.encrypt(userID, DES_ENCRYPTION_KEY);
		} catch (Exception ew) {

		}
		query
				.append("select eb.id from employee_basic as eb inner join useraccount ua on ua.id=eb.useraccountid where ua.userID='"
						+ userID + "'");

		List data = cbt
				.executeAllSelectQuery(query.toString(), connectionSpace);
		int empId = 0;
		if (data != null) {
			for (Iterator it = data.iterator(); it.hasNext();) {
				Object obdata = (Object) it.next();
				if (obdata != null)
					empId = (Integer) obdata;
			}
		}

		if (empId != 0) {
			query.delete(0, query.length());
			query
					.append("select KPIId from target where empID='" + empId
							+ "'");
			data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			String kpiIds[] = null;
			StringBuilder tempKPId = new StringBuilder();
			if (data != null) {
				for (Iterator it = data.iterator(); it.hasNext();) {
					Object obdata = (Object) it.next();
					kpiIds = obdata.toString().split("#");
				}
				if (kpiIds != null) {
					int i = 1;
					for (String h : kpiIds) {
						if (i < kpiIds.length)
							tempKPId.append("'" + h + "',");
						else
							tempKPId.append("'" + h + "'");
						i++;
					}
				}
				query.delete(0, query.length());
				query.append("select id,kpi from krakpicollection where id in("
						+ tempKPId.toString() + ")");
				data = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				if (data != null) {
					for (Iterator it = data.iterator(); it.hasNext();) {
						Object[] obdata = (Object[]) it.next();
						targetKpiLiist.put((Integer) obdata[0], obdata[1]
								.toString());
					}
				}
			}
		}
		return targetKpiLiist;
	}

	public static String offeringLevelWithName(SessionFactory connectionSpace) {

		StringBuilder offeringValue = new StringBuilder();
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> colname = new ArrayList<String>();
			// getting the offering levels for the offering as per configuration
			// and name of the offering levels
			colname.add("orgLevel");
			colname.add("levelName");
			List offeringLevel = cbt.viewAllDataEitherSelectOrAll(
					"mapped_offering_level", colname, connectionSpace);
			if (offeringLevel != null) {
				for (Object c : offeringLevel) {
					Object[] dataC = (Object[]) c;
					offeringValue.append(Integer.parseInt((String) dataC[0])
							+ "#");
					offeringValue.append((String) dataC[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offeringValue.toString();

	}

	public static Map<String, String> getMappedKPIOfEmployee(String empId,
			SessionFactory connectionSpace) {
		Map<String, String> empKpiLiist = new HashMap<String, String>();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		query.append("select KPIId from krakpimap where empID='" + empId + "'");
		List data = cbt
				.executeAllSelectQuery(query.toString(), connectionSpace);
		String kpiIds[] = null;
		StringBuilder tempKPId = new StringBuilder();
		if (data != null) {
			for (Iterator it = data.iterator(); it.hasNext();) {
				Object obdata = (Object) it.next();
				kpiIds = obdata.toString().split(", ");
			}
			if (kpiIds != null) {
				int i = 1;
				for (String h : kpiIds) {
					if (i < kpiIds.length)
						tempKPId.append("'" + h + "',");
					else
						tempKPId.append("'" + h + "'");
					i++;
				}
			}
			query.delete(0, query.length());
			query.append("select kra,kpi from krakpicollection where id in("
					+ tempKPId.toString() + ")");
			data = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
			if (data != null) {
				for (Iterator it = data.iterator(); it.hasNext();) {
					Object[] obdata = (Object[]) it.next();
					empKpiLiist.put(obdata[0].toString(), obdata[1].toString());
				}
			}
		}
		return empKpiLiist;
	}

	public static String organozationLevelWithName(
			SessionFactory connectionSpace) {

		// orglevel,level1#level2#level3#level4#level5#
		StringBuilder offeringValue = new StringBuilder();
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> colname = new ArrayList<String>();
			// getting the offering levels for the offering as per configuration
			// and name of the offering levels
			colname.add("orgLevel");
			colname.add("levelName");
			List offeringLevel = cbt.viewAllDataEitherSelectOrAll(
					"mapped_orgleinfo_level", colname, connectionSpace);
			if (offeringLevel != null) {
				for (Object c : offeringLevel) {
					Object[] dataC = (Object[]) c;
					offeringValue.append(Integer.parseInt((String) dataC[0])
							+ "#");
					offeringValue.append((String) dataC[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offeringValue.toString();

	}

	public static String deptLevelWithName(SessionFactory connectionSpace) {

		// deptlevel,dept1Name#dept2Name#
		StringBuilder offeringValue = new StringBuilder();
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> colname = new ArrayList<String>();
			// getting the offering levels for the offering as per configuration
			// and name of the offering levels
			colname.add("orgLevel");
			colname.add("levelName");
			List offeringLevel = cbt.viewAllDataEitherSelectOrAll(
					"mapped_dept_level_config", colname, connectionSpace);
			if (offeringLevel != null) {
				for (Object c : offeringLevel) {
					Object[] dataC = (Object[]) c;
					offeringValue.append(Integer.parseInt((String) dataC[0])
							+ ",");
					offeringValue.append((String) dataC[1]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return offeringValue.toString();

	}

	public static Map<Integer, String> sortLinkedMap(
			Map<Integer, String> dataMap) {
		List<Map.Entry<Integer, String>> entries = new ArrayList<Map.Entry<Integer, String>>(
				dataMap.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<Integer, String>>() {
			public int compare(Map.Entry<Integer, String> a,
					Map.Entry<Integer, String> b) {
				return a.getValue().compareTo(b.getValue());
			}
		});
		for (Map.Entry<Integer, String> entry : entries) {
			dataMap.put(entry.getKey(), entry.getValue());
		}
		return dataMap;
	}

	public static Map<String, String> sortLinkedMapWithString(
			Map<String, String> dataMap) {
		List<Map.Entry<String, String>> entries = new ArrayList<Map.Entry<String, String>>(
				dataMap.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> a,
					Map.Entry<String, String> b) {
				return a.getValue().compareTo(b.getValue());
			}
		});
		for (Map.Entry<String, String> entry : entries) {
			dataMap.put(entry.getKey(), entry.getValue());
		}
		return dataMap;
	}

	// check wheather a user valid for a product or not if valid then for which
	// application he is valid
	public static String checkUserValidtyWithApp(
			SessionFactory connectionSpace, String userId) {
		StringBuilder data = new StringBuilder();
		try {
			String validityto = null;
			String mappedProduct = null;
			String productCategory = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			userId = Cryptography.encrypt(userId, DES_ENCRYPTION_KEY);
			StringBuilder query = new StringBuilder("");
			query
					.append("select userForProductId from useraccount where userID='"
							+ userId + "'");
			List validtyData = cbt.executeAllSelectQuery(query.toString(),
					connectionSpace);
			for (Iterator it = validtyData.iterator(); it.hasNext();) {
				Object tempData = (Object) it.next();
				mappedProduct = tempData.toString();
			}
			if (mappedProduct != null) {
				int i = 1;
				String tempProduct[] = mappedProduct.split(", ");
				StringBuilder mappedIds = new StringBuilder();
				for (String H : tempProduct) {
					if (i < tempProduct.length) {
						mappedIds.append(H + ",");
					} else {
						mappedIds.append(H);
					}
					i++;
				}
				query.delete(0, query.length());
				// Getting the session of the cloud db for mapping the data
				AllConnection Conn = new ConnectionFactory("localhost");
				AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
				SessionFactory sessfact = Ob1.getSession();
				query.append("select App_code from apps_details where id in("
						+ mappedIds.toString() + ") ORDER BY App_code ASC");
				List dataTempWithCode = cbt.executeAllSelectQuery(query
						.toString(), sessfact);
				if (dataTempWithCode != null) {
					for (Iterator it = dataTempWithCode.iterator(); it
							.hasNext();) {
						// setting the mapped user application for which he is
						// authorized
						Object obdata = (Object) it.next();
						data.append(obdata.toString() + "#");
					}
				}
			}
			query.delete(0, query.length());
			query
					.append("select validityto,productCategory from client_product");
			validtyData = cbt.executeAllSelectQuery(query.toString(),
					connectionSpace);
			for (Iterator it = validtyData.iterator(); it.hasNext();) {
				Object[] tempData = (Object[]) it.next();
				validityto = tempData[0].toString();
				productCategory = tempData[1].toString();
			}
			if (validityto != null && !validityto.equalsIgnoreCase("")) {
				String mappedApp[] = data.toString().split("#");
				String validitytoTem[] = validityto.split("#");
				String productCategoryTemp[] = productCategory.split("#");
				data.delete(0, data.length());
				for (int i = 0; i < mappedApp.length; i++) {
					// compare two app and their date of validation
					if (mappedApp[i] != null
							&& !mappedApp[i].equalsIgnoreCase("")) {
						for (int j = 0; j < productCategoryTemp.length; j++) {
							if (productCategoryTemp[j] != null
									&& !productCategoryTemp[j]
											.equalsIgnoreCase("")) {
								if (productCategoryTemp[j]
										.equalsIgnoreCase(mappedApp[i])) {
									boolean status = DateUtil.comparetwoDates(
											DateUtil.getCurrentDateUSFormat(),
											validitytoTem[j]);
									if (status) {
										data.append(mappedApp[i] + "#");
									}
									break;
								}
							}
						}

					}
				}
			} else {
				data.delete(0, data.length());
			}

		} catch (Exception e) {

		}
		return data.toString();
	}

	public static String checkUserValidtyWithApp(SessionFactory connectionSpace) {
		StringBuilder data = new StringBuilder();
		try {
			String validityto = null;
			String productCategory = null;
			StringBuilder query = new StringBuilder("");
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			query
					.append("select validityto,productCategory from client_product");
			List validtyData = cbt.executeAllSelectQuery(query.toString(),
					connectionSpace);
			for (Iterator it = validtyData.iterator(); it.hasNext();) {
				Object[] tempData = (Object[]) it.next();
				validityto = tempData[0].toString();
				productCategory = tempData[1].toString();
			}
			if (validityto != null && !validityto.equalsIgnoreCase("")) {
				String validitytoTem[] = validityto.split("#");
				String productCategoryTemp[] = productCategory.split("#");
				data.delete(0, data.length());
				for (int i = 0; i < productCategoryTemp.length; i++) {
					// compare two app and their date of validation
					if (productCategoryTemp[i] != null
							&& !productCategoryTemp[i].equalsIgnoreCase("")) {
						boolean status = DateUtil.comparetwoDates(DateUtil
								.getCurrentDateUSFormat(), validitytoTem[i]);
						if (status) {
							data.append(productCategoryTemp[i] + "#");
						}
					}
				}
			} else {
				data.delete(0, data.length());
			}

		} catch (Exception e) {

		}
		return data.toString();
	}

	public static String deptLevel(SessionFactory connectionSpace) {

		// deptlevel,dept1Name#dept2Name#
		String level = null;
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List<String> colname = new ArrayList<String>();
			// getting the offering levels for the offering as per configuration
			// and name of the offering levels
			colname.add("orgLevel");
			List offeringLevel = cbt.viewAllDataEitherSelectOrAll(
					"mapped_dept_level_config", colname, connectionSpace);
			if (offeringLevel != null) {
				for (Object c : offeringLevel) {
					Object dataC = (Object) c;
					level = dataC.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return level;

	}

	public static String userDept(SessionFactory connectionSpace, String userID) {
		String deptName = null;
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder(
					"select dp.deptName from department as dp inner join employee_basic as eb on dp.id=eb.deptname inner join useraccount as ua on ua.id=eb.useraccountid "
							+ "where ua.userID='"
							+ Cryptography.encrypt(userID, DES_ENCRYPTION_KEY)
							+ "'");
			List data = cbt.executeAllSelectQuery(query.toString(),
					connectionSpace);
			if (data != null) {
				for (Iterator it = data.iterator(); it.hasNext();) {
					Object obdata = (Object) it.next();
					deptName = obdata.toString();
				}
			}
		} catch (Exception e) {

		}
		return deptName;
	}

	public static String userDeptID(SessionFactory connectionSpace, String userID) {
		String deptId = null;
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder(
					"select dp.id from department as dp inner join employee_basic as eb on dp.id=eb.deptname inner join useraccount as ua on ua.id=eb.useraccountid "
							+ "where ua.userID='"
							+ Cryptography.encrypt(userID, DES_ENCRYPTION_KEY)
							+ "'");
			List data = cbt.executeAllSelectQuery(query.toString(),
					connectionSpace);
			if (data != null) {
				for (Iterator it = data.iterator(); it.hasNext();) {
					Object obdata = (Object) it.next();
					deptId = obdata.toString();
				}
			}
		} catch (Exception e) {

		}
		return deptId;
	}
	public static int userEmpid(SessionFactory connectionSpace, String userID) {
		int empID = 0;
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder(
					"select eb.id from employee_basic as eb inner join useraccount as ua on ua.id=eb.useraccountid "
							+ "where ua.userID='"
							+ Cryptography.encrypt(userID, DES_ENCRYPTION_KEY)
							+ "'");
			List data = cbt.executeAllSelectQuery(query.toString(),
					connectionSpace);
			if (data != null) {
				for (Iterator it = data.iterator(); it.hasNext();) {
					Object obdata = (Object) it.next();
					empID = (Integer) obdata;
				}
			}
		} catch (Exception e) {

		}
		return empID;
	}

	public static String userEmpName(SessionFactory connectionSpace,
			String userID) {
		String empname = null;
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder(
					"select name from useraccount " + "where userID='"
							+ Cryptography.encrypt(userID, DES_ENCRYPTION_KEY)
							+ "'");
			List data = cbt.executeAllSelectQuery(query.toString(),
					connectionSpace);
			if (data != null) {
				for (Iterator it = data.iterator(); it.hasNext();) {
					Object obdata = (Object) it.next();
					empname = obdata.toString();
				}
			}
		} catch (Exception e) {

		}
		return empname;
	}

	/*
	 * Get employyee Id by providing its username
	 */
	public String getEmpIdByUserName(String userName,
			SessionFactory connectionSpace) {
		String empId = null;
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String uName = Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
			String query = "select emp.id from employee_basic as emp, useraccount as ua where emp.useraccountid = ua.id and ua.userID = '"
					+ uName + "' ";
			List resultdata = cbt.executeAllSelectQuery(query, connectionSpace);
			if (resultdata != null && resultdata.size() > 0) {
				empId = resultdata.get(0).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return empId;
	}

	/*
	 * Get employee userId by providing its employee ID
	 */
	public String getEmpUserIdByEmployeeId(String empId,
			SessionFactory connectionSpace) {
		String userName = null;
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String query = "select userID from useraccount as ua, employee_basic as eb where eb.useraccountid = ua.id and eb.id = "
					+ empId;
			List resultdata = cbt.executeAllSelectQuery(query, connectionSpace);
			if (resultdata != null && resultdata.size() > 0) {
				Object d = resultdata.get(0);
				if (d != null) {
					userName = d.toString();
					userName = Cryptography.decrypt(userName,
							DES_ENCRYPTION_KEY);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userName;
	}

	/*
	 * Get employee Id and Name by providing its username
	 */
	public String[] getEmpIdAndNameByUserName(String userName,
			SessionFactory connectionSpace) {
		String empId[] = new String[2];
		try {
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			String uName = Cryptography.encrypt(userName, DES_ENCRYPTION_KEY);
			String query = "select emp.id, empName from employee_basic as emp, useraccount as ua where emp.useraccountid = ua.id and ua.userID = '"
					+ uName + "' ";
			List resultdata = cbt.executeAllSelectQuery(query, connectionSpace);
			if (resultdata != null && resultdata.size() > 0) {
				Object[] d = (Object[]) resultdata.get(0);
				empId[0] = d[0].toString();
				empId[1] = d[1].toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return empId;
	}

	/**
	 * Get employee mobile by emp-id
	 * */

	public static String getMobileNoByEmpID(String empId,
			SessionFactory connectionSpace) {
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder query = new StringBuilder("");
		String mob = "";
		query.append("select id,mobOne from employee_basic where id='" + empId
				+ "'");
		List data = cbt
				.executeAllSelectQuery(query.toString(), connectionSpace);
		if (data != null) {
			for (Iterator it = data.iterator(); it.hasNext();) {
				Object[] obdata = (Object[]) it.next();
				mob = obdata[1].toString();
			}
		}
		return mob;
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> fetchAppAssignedUser(
			SessionFactory connectionSpace, String userId) 
	{
		Map<String,String> map=new LinkedHashMap<String, String>();
		try 
		{
			String mappedProduct = null;
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			userId = Cryptography.encrypt(userId, DES_ENCRYPTION_KEY);
			StringBuilder query = new StringBuilder("");
			query.append("select userForProductId from useraccount where userID='"+ userId + "'");
			List validtyData = cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			for (Iterator it = validtyData.iterator(); it.hasNext();) 
			{
				Object tempData = (Object) it.next();
				mappedProduct = tempData.toString();
			}
			if (mappedProduct != null) 
			{
				int i = 1;
				String tempProduct[] = mappedProduct.split(", ");
				StringBuilder mappedIds = new StringBuilder();
				for (String H : tempProduct) {
					if (i < tempProduct.length) {
						mappedIds.append(H + ",");
					} else {
						mappedIds.append(H);
					}
					i++;
				}
				query.delete(0, query.length());
				// Getting the session of the cloud db for mapping the data
				AllConnection Conn = new ConnectionFactory("localhost");
				AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
				SessionFactory sessfact = Ob1.getSession();
				query.append("select App_code,app_name from apps_details where id in("+ mappedIds.toString() + ") ORDER BY app_name ASC");
				List dataTempWithCode = cbt.executeAllSelectQuery(query.toString(), sessfact);
				if (dataTempWithCode != null) 
				{
					for (Iterator it = dataTempWithCode.iterator(); it.hasNext();) 
					{
						Object[] obdata = (Object[]) it.next();
						if (obdata[0]!=null && obdata[1]!=null)
						{
							map.put(obdata[0].toString(), obdata[1].toString());
						}
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return map;
	}
	
	public Map<String, String> fetchVendorNames(SessionFactory connectionSpace)
	{
		Map<String, String> vendorname = new LinkedHashMap<String, String>();
	 try
	 {
		 CommonOperInterface cbt = new CommonConFactory().createInterface();
		List vendornameData = cbt.executeAllSelectQuery("select id, vendorname from createvendor_master order by vendorname ", connectionSpace);
		      if (vendornameData != null && !vendornameData.isEmpty())
		         {
			       for (Object c : vendornameData)
			            {
				         Object[] dataC = (Object[]) c;
				         vendorname.put(dataC[0].toString(), dataC[1].toString());
			            }
		         }
	}
	 catch (Exception e)
	      {
		       e.printStackTrace();
	      }
	       return vendorname;
		
		
	}
	/**
	 *  Get Oraganization Image Path 
	 * 
	 */ public String getOrganizationImage(SessionFactory connectionSpace)
	 {
		 String orgImgPath=null;
		try
		{
			
			 CommonOperInterface cbt = new CommonConFactory().createInterface();
				List dataList =cbt.executeAllSelectQuery( "select companyIcon from organization_level1 ",connectionSpace);
				{
					if(dataList!=null && dataList.size()>0 && dataList.get(0)!=null)
					{
						orgImgPath= (String) dataList.get(0);
						
						//System.out.println("CommonWork.getOrganizationImage()669 "+orgImgPath);
					}
					
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
			return orgImgPath;
	 }
	 public static String userDeptId(SessionFactory connectionSpace, String userID) {
			String deptName = null;
			try {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder(
						"select dp.id from department as dp "
						+ "inner join employee_basic as eb on dp.id=eb.deptname "
						+ "inner join compliance_contacts as ua on ua.emp_id=eb.id  "
								+ " where ua.id='"+userID+"'");
				List data = cbt.executeAllSelectQuery(query.toString(),
						connectionSpace);
				if (data != null) {
					for (Iterator it = data.iterator(); it.hasNext();) {
						Object obdata = (Object) it.next();
						deptName = obdata.toString();
					}
				}
			} catch (Exception e) {

			}
			return deptName;
		}
	 public static String[] fetchCommonDetails(String user,SessionFactory connection)
	 {
		 String arr[] =new String[5];
		 try
		 {
			 CommonOperInterface cbt = new CommonConFactory().createInterface();
			 String userId = Cryptography.encrypt(user, DES_ENCRYPTION_KEY);
			 StringBuilder query= new StringBuilder();
			 query.append("select gi.id as groupId,branch.id as branch,head.id as head,country.id as country,gi.groupName as groupN from employee_basic as emp ");
			 query.append(" inner join department as dept on emp.deptname=dept.id ");
			 query.append(" inner join groupinfo as gi on emp.groupId=gi.id ");
			 query.append(" inner join branchoffice_data as branch on branch.id=gi.regLevel ");
			 query.append(" inner join headoffice_data as head on head.id=branch.headOfficeId ");
			 query.append(" inner join country_data as country on country.id=head.countryId ");
			 query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where uac.userID='" + userId + "'");
			 //System.out.println("query is as :::: "+query.toString());
			 List data = cbt.executeAllSelectQuery(query.toString(), connection);
			 if (data!=null && data.size()>0) 
			 {
				for (Iterator iterator = data.iterator(); iterator.hasNext();) 
				{
					Object[] object = (Object[]) iterator.next();
					arr[0] = object[0].toString();
					arr[1] = object[1].toString();
					arr[2] = object[2].toString();
					arr[3] = object[3].toString();
					arr[4] = object[4].toString();
				}
			 }
		 } 
		 catch (Exception e)
		 {
			e.printStackTrace();
		 }
		return arr;
	 }

}