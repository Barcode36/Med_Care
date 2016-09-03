package com.Over2Cloud.ctrl.wfpm.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.loader.custom.Return;

import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.opensymphony.xwork2.ActionContext;

public class CommonHelper
{

	Map													session							= ActionContext.getContext().getSession();
	private String							offeringLevel				= session.get("offeringLevel").toString();
	final static String					DES_ENCRYPTION_KEY	= "ankitsin";
	public static final String	moduleName					= "WFPM";
	SessionFactory							connectionSpace			= (SessionFactory) session.get("connectionSpace");
	CommonOperInterface					coi									= new CommonConFactory().createInterface();

	/*
	 * Anoop 19-5-2014 Using Singleton design pattern
	 */
	private static CommonHelper	ch									= null;

	public static CommonHelper getInstance()
	{
		if (ch == null) return new CommonHelper();
		else return ch;
	}

	@SuppressWarnings("unchecked")
	public Map FetchMomDetails(SessionFactory factory, String id, String momType)
	{
		Map<String, Object> tempMap = null;
		try
		{

			// //System.out.println("momType>>>>>" + momType);
			CommonOperInterface coi = new CommonConFactory().createInterface();
			StringBuilder query = new StringBuilder();
			if (momType.equalsIgnoreCase("Client"))
			{
				query.append("select id,momDate,fromTime,toTime,agenda,entityType ");
				query.append("from entity_takeaction_mom ");
				query.append("where entity_takeaction_id='");
				query.append(id);
				query.append("' ");
				query.append("and entityType=0 ");
			}
			else
			{
				query.append("select id,momDate,fromTime,toTime,agenda,entityType ");
				query.append("from entity_takeaction_mom ");
				query.append("where entity_takeaction_id='");
				query.append(id);
				query.append("' ");
				query.append("and entityType=1 ");
			}

			List list = coi.executeAllSelectQuery(query.toString(), factory);
			tempMap = new LinkedHashMap<String, Object>();
			String momId = "";
			if (list != null && list.size() > 0)
			{

				Object[] data = (Object[]) list.get(0);

				momId = getFieldValue(data[0]);

				tempMap.put("MOM Date", getFieldValue(data[1]));
				tempMap.put("From Time", getFieldValue(data[2]));
				tempMap.put("To Time", getFieldValue(data[3]));
				tempMap.put("Agenda", getFieldValue(data[4]));

				if (getFieldValue(data[5]).equalsIgnoreCase("0"))
				{
					tempMap.put("Type", "Client");
				}
				else if (getFieldValue(data[5]).equalsIgnoreCase("1"))
				{
					tempMap.put("Type", "Associate");
				}
			}
			// Cilent Contact
			StringBuilder query1 = new StringBuilder();
			StringBuilder query2 = new StringBuilder();
			StringBuilder query3 = new StringBuilder();
			StringBuilder query4 = new StringBuilder();
			StringBuilder query5 = new StringBuilder();
			if (momType.equalsIgnoreCase("Client"))
			{
				query1.append("select ccd.personName ");
				query1.append("from entity_takeaction_mom_clientcontact as ctmc ");
				query1.append("inner join client_contact_data as ccd ");
				query1.append("where ctmc.clientContactId=ccd.id ");
				query1.append("and ctmc.client_takeaction_mom_id='");
				query1.append(momId);
				query1.append("' ");
				System.out.println("query1 MOM :" + query1);
			}
			else
			{
				query1.append("select acd.name ");
				query1.append("from entity_takeaction_mom_clientcontact as etmc ");
				query1.append("inner join associate_contact_data as acd ");
				query1.append("where etmc.clientContactId=acd.id  ");
				query1.append("and etmc.client_takeaction_mom_id='");
				query1.append(momId);
				query1.append("' ");
				System.out.println("query1 not clientMOM:" + query1);

			}
			// Employee
			query2.append("select eb.empName ");
			query2.append("from entity_takeaction_mom_employee as ctme ");
			query2.append("inner join employee_basic as eb ");
			query2.append("where ctme.empId=eb.id ");
			query2.append("and ctme.client_takeaction_mom_id='");
			query2.append(momId);
			query2.append("' ");
			// System.out.println("query2 :" + query2);

			// Disscussion
			query3.append("select discussion from entity_takeaction_mom_discussion ");
			query3.append("where client_takeaction_mom_id='");
			query3.append(momId);
			query3.append("' ");
			// System.out.println("query3 :" + query3);

			// Future action
			query4.append("select futureAction from entity_takeaction_mom_futureaction ");
			query4.append("where client_takeaction_mom_id='");
			query4.append(momId);
			query4.append("' ");
			// System.out.println("query4 :" + query4);

			// attached doc
			if (momType.equalsIgnoreCase("Client"))
			{
				query5.append("select docPath from entity_takeaction_attacheddoc eta ");
				query5.append("inner join entity_takeaction_mom as etm ");
				query5.append("where eta.client_takeaction_id=etm.entity_takeaction_id ");
				query5.append("and entityType='0' ");
				query5.append("and etm.entity_takeaction_id = '");
				query5.append(id);
				query5.append("' ");
			}
			else if (momType.equalsIgnoreCase("Associate"))
			{
				query5.append("select docPath from entity_takeaction_attacheddoc ");
				query5.append("inner join entity_takeaction_mom as etm ");
				query5.append("where client_takeaction_id=etm.entity_takeaction_id ");
				query5.append("and entityType='1' ");
				query5.append("and etm.entity_takeaction_id = '");
				query5.append(id);
				query5.append("' ");
			}
			// System.out.println("	query5 :" + query5);
			List list1 = coi.executeAllSelectQuery(query1.toString(), factory);
			List list2 = coi.executeAllSelectQuery(query2.toString(), factory);
			List list3 = coi.executeAllSelectQuery(query3.toString(), factory);
			List list4 = coi.executeAllSelectQuery(query4.toString(), factory);
			List list5 = coi.executeAllSelectQuery(query5.toString(), factory);
			tempMap.put("Client Contact", (list1 == null || list1.size() < 1) ? "NA" : list1);
			tempMap.put("Employee", (list2 == null || list2.size() < 1) ? "NA" : list2);
			tempMap.put("FutureAction", (list3 == null || list3.size() < 1) ? "NA" : list3);
			tempMap.put("Discussion", (list4 == null || list4.size() < 1) ? "NA" : list4);
			tempMap.put("Attached Doc", (list5 == null || list5.size() < 1) ? "NA" : list5);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return tempMap;
	}

	public static String getFieldValue(Object object)
	{
		return (object == null || object.toString().trim().length() < 1) ? "NA" : object.toString().trim();
	}

	// Start: Fetch Offering names and client name
	public String[] getOfferingName()
	{
		int level = 0;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String[] oLevels = null;

		if (offeringLevel != null && !offeringLevel.equalsIgnoreCase(""))
		{
			oLevels = offeringLevel.split("#");
			level = Integer.parseInt(oLevels[0]);
		}
		String[] myArray = new String[3];
		String tableName = "", colName = "";
		if (level == 1)
		{
			tableName = "offeringlevel1";
			colName = "verticalname";
		}
		if (level == 2)
		{
			tableName = "offeringlevel2";
			colName = "offeringname";
		}
		if (level == 3)
		{
			tableName = "offeringlevel3";
			colName = "subofferingname";
		}
		if (level == 4)
		{
			tableName = "offeringlevel4";
			colName = "variantname";
		}
		if (level == 5)
		{
			tableName = "offeringlevel5";
			colName = "subvariantname";
		}
		myArray[0] = colName;
		myArray[1] = tableName;
		myArray[2] = String.valueOf(level);

		return myArray;
	}

	// to get userID from user account
	
	public String fetchUserID(String empId, SessionFactory connection)
	{
		String userid = null;
		List dataList = new ArrayList();
		try {
			StringBuilder query = new StringBuilder();
			query.append("select uac.userID from useraccount as uac");
			query.append(" left join employee_basic as emp on emp.useraccountid=uac.id ");
			query.append(" where empId= ");
			query.append(empId);
			query.append("");
			System.out.println("query.toString()     actid "+query.toString());
			dataList = coi.executeAllSelectQuery(query.toString(), connection);
			if(dataList!=null && dataList.size()>0)
			{
				userid = dataList.get(0).toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userid;
	}
	
	@SuppressWarnings("unchecked")
	// Get contactId by userID for the department employee belongs to
	public String[] getEmpDetailsByUserId(String moduleName, String userName, SessionFactory connection)
	{
		// //System.out.println(moduleName + "   " + userName + "   " + connection);
		List dataList = new ArrayList();
		String[] values = new String[8];
		try
		{
			
			//CommonOperInterface coi = new CommonConFactory().createInterface();
			//userName = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));

			StringBuilder query = new StringBuilder();
			query.append("select contact.id,emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid, uac.userType from employee_basic as emp ");
			query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
			query.append(" inner join department as dept on emp.deptname=dept.id");
			query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='" + moduleName + "' and uac.userID='");
			query.append(userName + "' and contact.forDept_id=dept.id");
			dataList = coi.executeAllSelectQuery(query.toString(), connection);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] obj = (Object[]) dataList.get(0);
				values[0] = getFieldValue(obj[0]);
				values[1] = getFieldValue(obj[1]);
				values[2] = getFieldValue(obj[2]);
				values[3] = getFieldValue(obj[3]);
				values[4] = getFieldValue(obj[4]);
				values[5] = getFieldValue(obj[5]);
				values[6] = getFieldValue(obj[6]);
				if(getFieldValue(obj[7]).toString().equalsIgnoreCase("") && getFieldValue(obj[7]).toString() != null){
					values[7] = getFieldValue(obj[7]);
				}
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// //System.out.println(moduleName + "   " + userName + "   " + connection +
		// "  " + values);
		return values;
	}
	
	@SuppressWarnings("unchecked")
	// Get contactId by userName for the department employee belongs to
	public String[] getEmpDetailsByUserName(String moduleName, String userName, SessionFactory connection)
	{
		// //System.out.println(moduleName + "   " + userName + "   " + connection);
		List dataList = new ArrayList();
		String[] values = new String[8];
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			userName = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));

			StringBuilder query = new StringBuilder();
			query.append("select contact.id,emp.empname,emp.mobone,emp.emailidone,emp.deptname as deptid, dept.deptName,emp.id as empid, uac.userType from employee_basic as emp ");
			query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
			query.append(" inner join department as dept on emp.deptname=dept.id");
			query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='" + moduleName + "' and uac.userID='");
			query.append(userName + "' and contact.forDept_id=dept.id");
			//System.out.println("query.toString()  "+query.toString());
			dataList = coi.executeAllSelectQuery(query.toString(), connection);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] obj = (Object[]) dataList.get(0);
				values[0] = getFieldValue(obj[0]);
				values[1] = getFieldValue(obj[1]);
				values[2] = getFieldValue(obj[2]);
				values[3] = getFieldValue(obj[3]);
				values[4] = getFieldValue(obj[4]);
				values[5] = getFieldValue(obj[5]);
				values[6] = getFieldValue(obj[6]);
				if(getFieldValue(obj[7]).toString().equalsIgnoreCase("") && getFieldValue(obj[7]).toString() != null){
					values[7] = getFieldValue(obj[7]);
				}
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// //System.out.println(moduleName + "   " + userName + "   " + connection +
		// "  " + values);
		return values;
	}

	
	// for geeting employee type(M, H , N)
	public String getEmpTypeByUserName(String moduleName, String userName, SessionFactory connection)
	{

		// //System.out.println(moduleName + "   " + userName + "   " + connection);
		List dataList = new ArrayList();
		String value = null;
		try
		{
			CommonOperInterface coi = new CommonConFactory().createInterface();
			userName = (Cryptography.encrypt(userName, DES_ENCRYPTION_KEY));

			StringBuilder query = new StringBuilder();
			query.append("select contact.id, uac.userType from employee_basic as emp ");
			query.append(" inner join compliance_contacts as contact on contact.emp_id=emp.id");
			query.append(" inner join department as dept on emp.deptname=dept.id");
			query.append(" inner join useraccount as uac on emp.useraccountid=uac.id where contact.moduleName='" + moduleName + "' and uac.userID='");
			query.append(userName+"'");
			dataList = coi.executeAllSelectQuery(query.toString(), connection);
			if (dataList != null && dataList.size() > 0)
			{
				Object[] obj = (Object[]) dataList.get(0);
				value = getFieldValue(obj[1]);
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// //System.out.println(moduleName + "   " + userName + "   " + connection +
		// "  " + values);
		return value;
	
	}
	
	public String[] fetchEmpDetailsByContactId(String contactId)
	{
		String[] emp = null;

		try
		{
			String query = "select emp.empName, emp.mobOne from compliance_contacts as cc inner join employee_basic as emp on emp.id = cc.emp_id where cc.id = "
					+ contactId;
			List list = coi.executeAllSelectQuery(query, connectionSpace);
			if (list != null && !list.isEmpty())
			{
				Object[] data = (Object[]) list.get(0);
				emp = new String[data.length];
				emp[0] = getFieldValue(data[0]);
				emp[1] = getFieldValue(data[1]);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return emp;
	}

	@SuppressWarnings("rawtypes")
	public String getLevelHierarchyOfContId(String moduleName, String empId)
	{
		String contactId = null;
		try
		{
			if (moduleName != null && empId != null)
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder mappedContId = new StringBuilder();
				List contForDeptId = cbt.executeAllSelectQuery("SELECT forDept_id FROM compliance_contacts WHERE emp_id=" + empId + " AND moduleName='" + moduleName
						+ "'", connectionSpace);
				if (contForDeptId != null && contForDeptId.size() > 0)
				{
					StringBuilder strBuilder = new StringBuilder();
					for (int deptCount = 0; deptCount < contForDeptId.size(); deptCount++)
					{
						StringBuilder query5 = new StringBuilder();
						query5.append("SELECT distinct mapping.mapped_with FROM compliance_contacts as contacts ");
						query5.append("inner join contact_mapping_detail as mapping on contacts.id=mapping.mapped_with ");
						query5.append("where contacts.forDept_id in(" + contForDeptId.get(deptCount) + ") and contacts.moduleName='" + moduleName
								+ "' and contacts.emp_id=" + empId);
						List mappedData5 = cbt.executeAllSelectQuery(query5.toString(), connectionSpace);
						if (mappedData5 != null && mappedData5.size() > 0)
						{
							strBuilder = new StringBuilder();
							for (int count = 0; count < mappedData5.size(); count++)
							{
								strBuilder.append(mappedData5.get(0) + ",");
							}
							String mappedWith = strBuilder.toString().substring(0, strBuilder.length() - 1);
							if (mappedWith != null)
							{
								List mappedData4 = executeInnerQuery(mappedWith);
								if (mappedData4 != null && mappedData4.size() > 0)
								{
									for (int i = 0; i < 1; i++)
									{
										mappedContId.append(mappedData4.get(0).toString() + ",");

										if (mappedData4.get(0) != null)
										{
											List mappedData3 = executeInnerQuery(mappedData4.get(0).toString());
											if (mappedData3 != null && mappedData3.size() > 0)
											{
												for (int j = 0; j < 1; j++)
												{
													mappedContId.append(mappedData3.get(0).toString() + ",");
													if (mappedData3.get(0) != null)
													{
														List mappedData2 = executeInnerQuery(mappedData3.get(0).toString());
														if (mappedData2 != null && mappedData2.size() > 0)
														{
															for (int k = 0; k < 1; k++)
															{
																mappedContId.append(mappedData2.get(0).toString() + ",");
																if (mappedData2.get(0) != null)
																{
																	List mappedData1 = executeInnerQuery(mappedData2.get(0).toString());
																	if (mappedData1 != null && mappedData1.size() > 0)
																	{
																		mappedContId.append(mappedData1.get(0).toString() + ",");
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						if (mappedContId != null && !mappedContId.toString().equalsIgnoreCase("")) contactId = mappedContId.toString().substring(0,
								mappedContId.toString().length() - 1);
					}

				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return contactId;
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	public List executeInnerQuery(String ids)
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		StringBuilder mappedEmpId = new StringBuilder();
		StringBuilder nextMappedId = new StringBuilder();
		List dataList = new ArrayList();
		StringBuilder query = new StringBuilder();
		query.append("SELECT distinct contacts.id as contId,emp.id as empId FROM compliance_contacts as contacts ");
		query.append("inner join contact_mapping_detail as mapping on contacts.id=mapping.contact_id ");
		query.append("inner join employee_basic as emp on contacts.emp_id=emp.id ");
		query.append("where mapping.mapped_with IN(" + ids + ") order by emp.empName");
		List mappedData = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
		if (mappedData != null && mappedData.size() > 0)
		{
			for (Iterator iterator1 = mappedData.iterator(); iterator1.hasNext();)
			{
				Object object1[] = (Object[]) iterator1.next();
				nextMappedId.append(object1[0].toString() + ",");
				mappedEmpId.append(object1[1].toString() + ",");
			}
		}
		if (nextMappedId != null && mappedEmpId != null && !mappedEmpId.toString().equalsIgnoreCase("") && !nextMappedId.toString().equalsIgnoreCase(""))
		{
			dataList.add(nextMappedId.toString().substring(0, nextMappedId.toString().length() - 1));
			dataList.add(mappedEmpId.toString().substring(0, mappedEmpId.toString().length() - 1));
		}
		return dataList;
	}

	@SuppressWarnings("rawtypes")
	public String getLevelHierarchy(String moduleName, String empId)
	{
		String contactId = null;
		try
		{
			if (moduleName != null && empId != null)
			{
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder mappedEmpId = new StringBuilder();
				List contForDeptId = cbt.executeAllSelectQuery("SELECT forDept_id FROM compliance_contacts WHERE emp_id=" + empId + " AND moduleName='" + moduleName
						+ "'", connectionSpace);
				if (contForDeptId != null && contForDeptId.size() > 0)
				{
					StringBuilder strBuilder = new StringBuilder();
					for (int deptCount = 0; deptCount < contForDeptId.size(); deptCount++)
					{
						StringBuilder query5 = new StringBuilder();
						query5.append("SELECT distinct mapping.mapped_with FROM compliance_contacts as contacts ");
						query5.append("inner join contact_mapping_detail as mapping on contacts.id=mapping.mapped_with ");
						query5.append("where contacts.forDept_id in(" + contForDeptId.get(deptCount) + ") and contacts.moduleName='" + moduleName
								+ "' and contacts.emp_id=" + empId);
						List mappedData5 = cbt.executeAllSelectQuery(query5.toString(), connectionSpace);
						if (mappedData5 != null && mappedData5.size() > 0)
						{
							strBuilder = new StringBuilder();
							for (int count = 0; count < mappedData5.size(); count++)
							{
								strBuilder.append(mappedData5.get(0) + ",");
							}
							String mappedWith = strBuilder.toString().substring(0, strBuilder.length() - 1);
							if (mappedWith != null)
							{
								List mappedData4 = executeInnerQuery(mappedWith);
								if (mappedData4 != null && mappedData4.size() > 0)
								{
									for (int i = 0; i < 1; i++)
									{
										mappedEmpId.append(mappedData4.get(1).toString() + ",");

										if (mappedData4.get(0) != null)
										{
											List mappedData3 = executeInnerQuery(mappedData4.get(0).toString());
											if (mappedData3 != null && mappedData3.size() > 0)
											{
												for (int j = 0; j < 1; j++)
												{
													mappedEmpId.append(mappedData3.get(1).toString() + ",");
													if (mappedData3.get(0) != null)
													{
														List mappedData2 = executeInnerQuery(mappedData3.get(0).toString());
														if (mappedData2 != null && mappedData2.size() > 0)
														{
															for (int k = 0; k < 1; k++)
															{
																mappedEmpId.append(mappedData2.get(1).toString() + ",");
																if (mappedData2.get(0) != null)
																{
																	List mappedData1 = executeInnerQuery(mappedData2.get(0).toString());
																	if (mappedData1 != null && mappedData1.size() > 0)
																	{
																		mappedEmpId.append(mappedData1.get(1).toString() + ",");
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						if (mappedEmpId != null && !mappedEmpId.toString().equalsIgnoreCase("")) contactId = mappedEmpId.toString().substring(0,
								mappedEmpId.toString().length() - 1);
					}

				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return contactId;
	}

	public String getHierarchyContactIdByEmpId(String empId)
	{
		String allIds = null;
		try
		{
			CommonHelper ch = new CommonHelper();
			String empIdAll = ch.getLevelHierarchy(CommonHelper.moduleName, empId);
			empIdAll += "," + empId;

			StringBuilder query = new StringBuilder();
			query.append("select id from compliance_contacts where emp_id IN(");
			query.append(empIdAll);
			query.append(") ");
			List list = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if (list != null && !list.isEmpty())
			{
				allIds = "";
				for (Object obj : list)
				{
					allIds += obj + ",";
				}
				allIds = allIds.substring(0, allIds.lastIndexOf(","));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return allIds;
	}

	public static String[] getCommaSeparatedToArray(String value)
	{
		String[] array = null;
		try
		{
			if (value != null && !value.equals(""))
			{
				String[] tempArray = value.split(",");
				int i = 0;
				String[] arrayOld = new String[tempArray.length];
				for (String s : tempArray)
				{
					if (!s.trim().equals(""))
					{
						arrayOld[i++] = CommonHelper.getFieldValue(s);
					}
				}
				array = new String[i];
				for (int j = 0; j < i; j++)
				{
					array[j] = CommonHelper.getFieldValue(arrayOld[j]);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return array;
	}

	/*
	 * Check if there any data exists in a table for the specified column value
	 */
	public int checkForRecordExistenceInTable(String tableName, Map<String, String> conditionFields)
	{
		int count = 0;
		try
		{
			StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM ");
			String finalQuery = "";
			query.append(tableName);
			if (conditionFields != null && !conditionFields.isEmpty())
			{
				query.append(" WHERE ");
				for (Map.Entry<String, String> entry : conditionFields.entrySet())
				{
					query.append(entry.getKey());
					query.append(" = '");
					query.append(entry.getValue());
					query.append("', ");
				}
				finalQuery = query.substring(0, query.toString().lastIndexOf(","));
			}
			else
			{
				finalQuery = query.toString();
			}

			List list = coi.executeAllSelectQuery(finalQuery, connectionSpace);
			if (list != null && !list.isEmpty())
			{
				count = Integer.parseInt(list.get(0).toString().trim());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return count;
	}

	/*
	 * Anoop, 24-'8-2013 Create or update record
	 */
	public String insertRecordAndGetKey(String tableName, String fieldName, String fieldValue, String condField, String condValue, boolean needToInsert)
	{
		String id = null;
		String selectQuery = "select id from " + tableName + " where " + fieldName + "='" + fieldValue + "' ";
		if (condField != null && !condField.equalsIgnoreCase("")) selectQuery += " and " + condField + "='" + condValue + "' ";
		// ////System.out.println("selectQuery:"+selectQuery);
		String insertQuery = "insert into " + tableName + "(" + fieldName + ") values('" + fieldValue + "')";
		// ////System.out.println("insertQuery:"+insertQuery);

		Session session = null;
		Transaction transaction = null;
		try
		{
			session = connectionSpace.openSession();
			transaction = session.beginTransaction();
			Query query1 = session.createSQLQuery(selectQuery);
			List list = query1.list();
			transaction.commit();
			if (list != null && list.size() > 0)
			{
				id = list.get(0).toString();
			}
			else if (needToInsert)
			{
				// insert record
				session = connectionSpace.openSession();
				transaction = session.beginTransaction();
				query1 = session.createSQLQuery(insertQuery);
				id = String.valueOf(query1.executeUpdate());
				transaction.commit();

				// fetch record
				session = connectionSpace.openSession();
				transaction = session.beginTransaction();
				query1 = session.createSQLQuery(selectQuery);
				list = query1.list();
				transaction.commit();
				if (list != null && list.size() > 0)
				{
					id = list.get(0).toString();
				}
			}
			// session.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return id;
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> convertListToMap(List list, boolean needToReverseKeyValue)
	{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		if (list != null && !list.isEmpty())
		{
			for (Object obj : list)
			{
				Object[] data = (Object[]) obj;
				if (needToReverseKeyValue) map.put(data[1].toString(), CommonHelper.getFieldValue(data[0]));
				else map.put(data[0].toString(), CommonHelper.getFieldValue(data[1]));
			}
		}
		return map;
	}

	/*
	 * Anoop 20-5-2014 Convert any data list to ArrayList of ArrayList
	 */
	// parameter 'dateIndexToConvertFormat' is used to convert US format date to
	// Indian format
	// if list contains any date, just give its index otherwise give it to '-1'
	@SuppressWarnings("unchecked")
	public static ArrayList<ArrayList<String>> convertDBRecordsToNestedArrayList(List list, int dateIndexToConvertFormat, boolean doesContainDateTimeBoth)
	{
		ArrayList<ArrayList<String>> mainList = new ArrayList<ArrayList<String>>();
		if (list != null && !list.isEmpty())
		{
			int index = 0;
			ArrayList<String> temp = null;
			for (Object obj : list)
			{
				Object[] data = (Object[]) obj;
				temp = new ArrayList<String>();
				index = 0;
				for (Object val : data)
				{
					if (dateIndexToConvertFormat != -1 && index == dateIndexToConvertFormat)
					{
						try
						{
							if (doesContainDateTimeBoth)
							{
								String[] tempDate = CommonHelper.getFieldValue(val).split(" ");
								temp.add(DateUtil.convertDateToIndianFormat(tempDate[0]) + " " + tempDate[1]);
							}
							else
							{
								temp.add(DateUtil.convertDateToIndianFormat(CommonHelper.getFieldValue(val)));
							}
						}
						catch (Exception e)
						{
							temp.add(CommonHelper.getFieldValue(val));
						}
					}
					else
					{
						temp.add(CommonHelper.getFieldValue(val));
					}
					index++;
				}
				mainList.add(temp);
			}
		}
		return mainList;
	}

	
	public String fetchEmailIdOfEmp(String name)
	{
		String emailid = null;
		List dataList = new ArrayList();
		try {
			StringBuilder query = new StringBuilder();
			query.append("select emailIdOne from employee_basic");
			query.append("  where id = '");
			query.append(name);
			query.append("'");
			//System.out.println("query     emailid "+query.toString());
			dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				emailid = dataList.get(0).toString();
				if(emailid == null){emailid = "NA";}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emailid;
	}
	
	public String fetchDeptNameById(String deptname)
	{
		String deptnameval = null;
		List dataList = new ArrayList();
		try {
			StringBuilder query = new StringBuilder();
			query.append("select deptName from department");
			query.append("  where id = '");
			query.append(deptname);
			query.append("'");
			//System.out.println("query     emailid "+query.toString());
			dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				deptnameval = dataList.get(0).toString();
				if(deptnameval == null){deptnameval = "NA";}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deptnameval;
	}
	
	public String fetchMobileOfEmp(String name)
	{
		String emailid = null;
		List dataList = new ArrayList();
		try {
			StringBuilder query = new StringBuilder();
			query.append("select mobOne, empName from employee_basic");
			query.append("  where id = '");
			query.append(name);
			query.append("'");
			//System.out.println("query     emailid "+query.toString());
			dataList = coi.executeAllSelectQuery(query.toString(), connectionSpace);
			if(dataList!=null && dataList.size()>0)
			{
				for(Iterator it=dataList.iterator(); it.hasNext();)
				{
					 Object[] obdata=(Object[])it.next();
					 if(obdata[0] == null){obdata[0] = "NA";}
					 if(obdata[1] == null){obdata[1] = "NA";}
					
					 emailid = obdata[0].toString()+"#"+obdata[1].toString();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emailid;
	}
}
