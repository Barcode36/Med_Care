package com.Over2Cloud.service.clientdata.integration;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;
import org.apache.commons.logging.Log;

import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.order.feedbackaction.OrderServiceFileHelper;

/*
 * This class is for fetch New Order & Update of previous order from HIS server
 * Use JDBC connection to connect Oracle db in HIS
 * Author: Manab  15/07/2015
 */
public class ClientDataIntegrationRedirectServer
{

	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	// for get the new order from HIS Server
	public void datafetchForOrder(Log log, SessionFactory clientConnection, Session sessionHis, CommunicationHelper ch)
	{
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String pri = "Routine";
		String ordNameChange = null;
System.out.println("This is new file 66 server");
		try
		{
			
			String lastORDDateTime = lastORDdateTimeget(clientConnection);
			CommonOperInterface cot = new CommonConFactory().createInterface();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol", "Dream_1$3");
			st = con.createStatement();

			System.out.println("lastORDDateTime>> " + lastORDDateTime);
			//15-12-2015 by aarif 
			rs = st.executeQuery("select * from Dreamsol_Mc_ord_VW where ORD_DATE_TIME > TO_TIMESTAMP('" + lastORDDateTime + "','YYYY-MM-DD HH24:MI:SS.FF') AND ORDER_TYPE_CODE IN('MXR','MUSG', 'MUCT','HSME')");
			
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>ULTRA ORDER FETCH>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
			InsertDataTable ob = new InsertDataTable();

			ResultSetMetaData rsmd = rs.getMetaData();

			int columnCount = rsmd.getColumnCount();


			while (rs.next())
			{
				insertData.clear();
				ob.setColName("orderid");
				if (rs.getString("ORDER_ID") != null && !rs.getString("ORDER_ID").toString().equals(""))
				{
					ob.setDataName(rs.getString("ORDER_ID").toString());
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("orderName");
				if (rs.getString("ORDER_TYPE_CODE") != null && !rs.getString("ORDER_TYPE_CODE").toString().equals(""))
				{

					if (rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("CARD"))
					{

						ordNameChange = "CARD";
					}
					if (rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("MUSG"))
					{

						ordNameChange = "ULTRA";
					}
					if (rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("MXR"))
					{

						ordNameChange = "XR";
					}
					//15-12-2015 by aarif
					if (rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("HSME"))
					{

						ordNameChange = "HSME";
					}
					ob.setDataName(ordNameChange);
					//System.out.println("orderName : " + rs.getString("ORDER_TYPE_CODE").toString());
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("orderType");
				if (rs.getString("CATALOG_DESC") != null && !rs.getString("CATALOG_DESC").toString().equals(""))
				{
					ob.setDataName(rs.getString("CATALOG_DESC").toString());
					// System.out.println("orderType : "+rs.getString("CATALOG_DESC").toString());

				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				if (rs.getString("ORDER_STATUS") != null && !rs.getString("ORDER_STATUS").toString().equals(""))
				{
					// ob.setDataName(rs.getString("ORDER_CATALOG_CODE").toString());
					// System.out.println("ORDER_STATUS : "+rs.getString("ORDER_STATUS").toString());

				}

				ob = new InsertDataTable();
				ob.setColName("uhid");
				if (rs.getString("PATIENT_ID") != null && !rs.getString("PATIENT_ID").toString().equals(""))
				{
					ob.setDataName(rs.getString("PATIENT_ID").toString());
					// System.out.println("uhid : "+rs.getString("PATIENT_ID").toString());
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("pName");
				if (rs.getString("PATIENT_NAME") != null && !rs.getString("PATIENT_NAME").toString().equals(""))
				{
					ob.setDataName(rs.getString("PATIENT_NAME").toString());
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("roomNo");
				if (rs.getString("BED_NUM") != null && !rs.getString("BED_NUM").toString().equals(""))
				{
					ob.setDataName(rs.getString("BED_NUM").toString());
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("orderBy");
				if (rs.getString("ADDED_BY_ID") != null && !rs.getString("ADDED_BY_ID").toString().equals(""))
				{
					ob.setDataName(rs.getString("ADDED_BY_ID").toString());
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("orderDate");
				if (rs.getString("ORD_DATE_TIME") != null && !rs.getString("ORD_DATE_TIME").toString().equals(""))
				{
					ob.setDataName(rs.getString("ORD_DATE_TIME").toString().split(" ")[0]);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("orderTime");
				if (rs.getString("ORD_DATE_TIME") != null && !rs.getString("ORD_DATE_TIME").toString().equals(""))
				{
					ob.setDataName(rs.getString("ORD_DATE_TIME").toString().split(" ")[1]);
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("treatingDoc");
				if (rs.getString("PRACTITIONER_NAME") != null && !rs.getString("PRACTITIONER_NAME").toString().equals(""))
				{
					ob.setDataName(rs.getString("PRACTITIONER_NAME").toString());
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("treatingSpec");
				if (rs.getString("DOCTOR_SPECIALITY") != null && !rs.getString("DOCTOR_SPECIALITY").toString().equals(""))
				{
					ob.setDataName(rs.getString("DOCTOR_SPECIALITY").toString());
				} else
				{
					ob.setDataName("NA");
				}
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("openDate");
				ob.setDataName(DateUtil.getCurrentDateUSFormat());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("openTime");
				ob.setDataName(DateUtil.getCurrentTime());
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("assignMchn");
				ob.setDataName("NA-NA");
				insertData.add(ob);

				ob = new InsertDataTable();
				ob.setColName("priority");
				if (rs.getString("PRIORITY") != null && !rs.getString("PRIORITY").toString().equals(""))
				{

					if (rs.getString("PRIORITY").toString().equalsIgnoreCase("R"))
					{
						pri = "Routine";
					} else if (rs.getString("PRIORITY").toString().equalsIgnoreCase("U"))
					{
						pri = "Urgent";
					}

					else if (rs.getString("PRIORITY").toString().equalsIgnoreCase("S"))
					{
						pri = "Stat";
					}

					ob.setDataName(pri);
				} else
				{
					ob.setDataName("Routine");
				}
				insertData.add(ob);

				CommonOperInterface cbt = new CommonConFactory().createInterface();
				try
				{
					List dataListOCCEsc = cbt.executeAllSelectQuery("SELECT  l1Tol2 FROM escalation_order_detail  WHERE escDept=(SELECT id FROM department WHERE deptName LIKE '%OCC%' ) AND priority LIKE '%" + pri + "%'".toString(), clientConnection);
					String esctm = null;
					if (dataListOCCEsc != null && dataListOCCEsc.size() > 0)
					{
						Object object = (Object) dataListOCCEsc.get(0);
						esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), object.toString(), "Y");

					}

					else
					{
						esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:15", "Y");

					}
					dataListOCCEsc.clear();
					ob = new InsertDataTable();
					ob.setColName("escalation_date");
					ob.setDataName(esctm.split("#")[0]);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("escalation_time");
					ob.setDataName(esctm.split("#")[1]);
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("department");
					ob.setDataName("17");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("level");
					ob.setDataName("level1");
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("nursingUnit");
					if (rs.getString("NURSING_UNIT_CODE") != null && !rs.getString("NURSING_UNIT_CODE").toString().equals(""))
					{
						ob.setDataName(rs.getString("NURSING_UNIT_CODE").toString());
						//System.out.println("nursingUnit : " + rs.getString("LONG_DESC").toString());
					} else
					{
						ob.setDataName("NA");
					}
					insertData.add(ob);

					ob = new InsertDataTable();
					ob.setColName("status");
					ob.setDataName("Un-assigned");
					insertData.add(ob);

				} catch (Exception e)
				{
					e.printStackTrace();
				}

				boolean chkRepeateOrd = chkRepeatOrder(rs.getString("ORDER_ID").toString(), clientConnection);

				if (chkRepeateOrd == true)
				{
					if (rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("CARD") || rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("MUSG") || rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("MXR") ) 
					{
						int dataId = cot.insertDataReturnId("machine_order_data", insertData, clientConnection);
						//15-12-2015 by aarif
						if (ordNameChange.equalsIgnoreCase("XR") || ordNameChange.equalsIgnoreCase("ULTRA") && ordNameChange.equalsIgnoreCase("CT") && dataId > 0)
						{
							//System.out.println(">>>jkshdkhj>>>>kjsdh>>");
							int id = 0;
							// Routine Cart Order Auto Sync
							if (pri.equalsIgnoreCase("Routine") && ordNameChange.equalsIgnoreCase("XR") )
							{
								id = addCartConfig(dataId, rs.getString("NURSING_UNIT_CODE").toString(), ordNameChange, rs.getString("ORDER_ID").toString(), pri, clientConnection);

							}
							if (id == 0)
							{
								sendOccAlertOnSync("17", "1", "ORD", rs.getString("ORDER_ID").toString(), ordNameChange, pri, clientConnection);
							}

						}

					}
				} else
				{
					System.out.println("ord reapet");

				}

			}
				//15-12-2015 by aarif
			rs = st.executeQuery("select * from Dreamsol_Mc_ord_VW where TRUNC (CAN_DATE_TIME) = TRUNC (SYSDATE) AND ORDER_TYPE_CODE IN('MXR','MUSG', 'MUCT','HSME')  ");

			while (rs.next())
			{

				//System.out.println("CAN_DATE" + rs.getString("CAN_DATE_TIME") + " orderID " + rs.getString("ORDER_ID") + " Reason " + rs.getString("CAN_LINE_REASON"));
				boolean chkFlag = checkCancelOrder(rs.getString("ORDER_ID"), clientConnection);
				if (chkFlag)
				{

					StringBuilder qry = new StringBuilder();
					qry.append("Update machine_order_data set status='CancelHIS' where orderid='" + rs.getString("ORDER_ID") + "'");

					int chk = cot.executeAllUpdateQuery(qry.toString(), clientConnection);

					if (chk > 0)
					{
						qry.setLength(0);
						qry.append("select id from machine_order_data where orderid='" + rs.getString("ORDER_ID") + "'");
						List temp = cot.executeAllSelectQuery(qry.toString(), clientConnection);
						if (temp != null && !temp.isEmpty())
						{
							Object obj = temp.get(0);
							List<InsertDataTable> ulist = new ArrayList<InsertDataTable>();
							ob = new InsertDataTable();
							ob.setColName("status");
							ob.setDataName("CancelHIS");
							ulist.add(ob);

							ob = new InsertDataTable();
							ob.setColName("action_date");
							ob.setDataName(rs.getString("CAN_DATE_TIME").split(" ")[0]);
							ulist.add(ob);

							ob = new InsertDataTable();
							ob.setColName("action_time");
							ob.setDataName(rs.getString("CAN_DATE_TIME").split(" ")[1]);
							ulist.add(ob);

							ob = new InsertDataTable();
							ob.setColName("action_reason");
							if (rs.getString("CAN_LINE_REASON") != null && !rs.getString("CAN_LINE_REASON").equalsIgnoreCase(""))
							{
								ob.setDataName(rs.getString("CAN_LINE_REASON"));
							} else
							{
								ob.setDataName(rs.getString("CancelHIS"));
							}
							ulist.add(ob);

							ob = new InsertDataTable();
							ob.setColName("feedId");
							ob.setDataName(obj.toString());
							ulist.add(ob);

							boolean i = cot.insertIntoTable("order_status_history", ulist, clientConnection);
							if (rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("MXR") && i)
							{
								cot.deleteAllRecordForId("machine_order_cart", "orderId", obj.toString(), clientConnection);
								String namemob = getAllotToOfCancel(rs.getString("ORDER_ID"), clientConnection);
								if (!namemob.equalsIgnoreCase(""))
								{
									
									qry.setLength(0);
									qry.append("Cancel Order Alert: " + rs.getString("ORDER_TYPE_CODE").toString() + "/" + rs.getString("CATALOG_DESC").toString() + ", Location: " + rs.getString("BED_NUM").toString() + "/" + rs.getString("PATIENT_ID").toString() + "/" + rs.getString("ORDER_ID").toString() + " Order Date/Time: " + rs.getString("ORD_DATE_TIME").toString() + ".");
									ch.addMessage(namemob.split("#")[0], "AUTO", namemob.split("#")[1], "", qry.toString(), "Pending", "0", "ORD", clientConnection);
									qry.setLength(0);
								}
							} else if (rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("MUSG") && i)
							{
								String namemob = getAllotToOfCancel(rs.getString("ORDER_ID"), clientConnection);
								if (!namemob.equalsIgnoreCase(""))
								{
									qry.setLength(0);
									qry.append("Cancel Order Alert: " + rs.getString("ORDER_TYPE_CODE").toString() + "/" + rs.getString("CATALOG_DESC").toString() + ", Location: " + rs.getString("BED_NUM").toString() + "/" + rs.getString("PATIENT_ID").toString() + "/" + rs.getString("ORDER_ID").toString() + " Order Date/Time: " + rs.getString("ORD_DATE_TIME").toString() + ".");
									ch.addMessage(namemob.split("#")[0], "AUTO", namemob.split("#")[1], "", qry.toString(), "Pending", "0", "ORD", clientConnection);
									qry.setLength(0);
								}
							}
							//15-12-2015 by aarif
							 else if (rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("HSME") && i)
								{
									String namemob = getAllotToOfCancel(rs.getString("ORDER_ID"), clientConnection);
									if (!namemob.equalsIgnoreCase(""))
									{
										qry.setLength(0);
										qry.append("Cancel Order Alert: " + rs.getString("ORDER_TYPE_CODE").toString() + "/" + rs.getString("CATALOG_DESC").toString() + ", Location: " + rs.getString("BED_NUM").toString() + "/" + rs.getString("PATIENT_ID").toString() + "/" + rs.getString("ORDER_ID").toString() + " Order Date/Time: " + rs.getString("ORD_DATE_TIME").toString() + ".");
										ch.addMessage(namemob.split("#")[0], "AUTO", namemob.split("#")[1], "", qry.toString(), "Pending", "0", "ORD", clientConnection);
										qry.setLength(0);
									}
								}

						}

					}
				}
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			//System.out.println("Inner Calling Trace");
			log.info("Error in ClientDataIntegrationHelper.java \n " + e.getStackTrace());
			ch.addMessage("Manab", "Alert", "8882572103", "Order fetche File IPD 88 Server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
			ch.addMessage("Chnadrabhan", "Alert", "8826336111", "Order fetche File IPD 88 Server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
			ch.addMessage("Prabhat", "Alert", "9250400315", "Order fetche File IPD 88 Server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
			ch.addMessage("Sanjay", "Alert", "8130772488", "Order fetche File IPD 88 Server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
			ch.addMessage("Kamlesh", "Alert", "8010797571", "Order fetche File IPD 88 Server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
			//ch.addMessage("Abhay", "Alert", "9873325443", "Order fetche File IPD 88 Server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
			//ch.addMessage("Manab", "Alert", "8882572103", "Order fetche File IPD 66 server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
		} finally
		{
			try
			{
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// for get the new Emergency order from HIS Server 31/08/2015 by Kamlesh And
	// manab
	  /*public void datafetchForOrderEM(Log log, SessionFactory clientConnection, Session sessionHis)
	    {}*/
	public void datafetchForOrderEM(Log log, SessionFactory clientConnection, Session sessionHis, CommunicationHelper ch)
	{
		java.sql.Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String pri = "Routine";
		String ordNameChange = null;

		try
		{
            String lastORDDateTime = lastORDdateTimeEmergencyget(clientConnection);
            CommonOperInterface cot = (new CommonConFactory()).createInterface();
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.62.66:1521:HISDR", "dreamsol", "Dream_1$3");
            st = con.createStatement();
            System.out.println((new StringBuilder("test con ")).append(con.isClosed()).toString());
            System.out.println((new StringBuilder("lastORDDateTime>> ")).append(lastORDDateTime).toString());
            rs = st.executeQuery((new StringBuilder("select * from Dreamsol_Mc_em_ord_VW where ORD_DATE_TIME > TO_TIMESTAMP('")).append(lastORDDateTime).append("','YYYY-MM-DD HH24:MI:SS.FF') AND ORDER_TYPE_CODE IN('MXR','MUSG','MUCT','HSME') ").toString()); 	
          //15-12-2015 by aarif	add condition HSME in where clouse																																																															
            System.out.println("Emergency patient Fetch from 66 server>>>>>>>>>>>>>>>");
            List insertData = new ArrayList();
            InsertDataTable ob = new InsertDataTable();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while(rs.next()) 
            {
                insertData.clear();
                System.out.println("Emergency patient Fetch >>>>>>>>>>>>>>");
                System.out.println((new StringBuilder("ORDER_STATUS : ")).append(rs.getString("ORDER_STATUS").toString()).toString());
                ob.setColName("orderid");
                if(rs.getString("ORDER_ID") != null && !rs.getString("ORDER_ID").toString().equals(""))
                {
                    ob.setDataName(rs.getString("ORDER_ID").toString());
                } else
                {
                    ob.setDataName("NA");
                }
                insertData.add(ob);
                ob = new InsertDataTable();
                ob.setColName("orderName");
                if(rs.getString("ORDER_TYPE_CODE") != null && !rs.getString("ORDER_TYPE_CODE").toString().equals(""))
                {
                    if(rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("CARD"))
                    {
                        ordNameChange = "CARD";
                    }
                    if(rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("MUSG"))
                    {
                        ordNameChange = "ULTRA";
                    }
                    if(rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("MXR"))
                    {
                        ordNameChange = "XR";
                    }
                    //15-12-2015 by aarif
                    if(rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("HSME"))
                    {
                        ordNameChange = "HSME";
                    }
                    ob.setDataName(ordNameChange);
                    System.out.println((new StringBuilder("orderName : ")).append(rs.getString("ORDER_TYPE_CODE").toString()).toString());
                } else
                {
                    ob.setDataName("NA");
                }
                insertData.add(ob);
                ob = new InsertDataTable();
                ob.setColName("orderType");
                if(rs.getString("CATALOG_DESC") != null && !rs.getString("CATALOG_DESC").toString().equals(""))
                {
                    ob.setDataName(rs.getString("CATALOG_DESC").toString());
                } else
                {
                    ob.setDataName("NA");
                }
                insertData.add(ob);
                if(rs.getString("ORDER_STATUS") != null)
                {
                    rs.getString("ORDER_STATUS").toString().equals("");
                }
                ob = new InsertDataTable();
                ob.setColName("uhid");
                if(rs.getString("PATIENT_ID") != null && !rs.getString("PATIENT_ID").toString().equals(""))
                {
                    ob.setDataName(rs.getString("PATIENT_ID").toString());
                } else
                {
                    ob.setDataName("NA");
                }
                insertData.add(ob);
                ob = new InsertDataTable();
                ob.setColName("pName");
                if(rs.getString("PATIENT_NAME") != null && !rs.getString("PATIENT_NAME").toString().equals(""))
                {
                    ob.setDataName(rs.getString("PATIENT_NAME").toString());
                } else
                {
                    ob.setDataName("NA");
                }
                insertData.add(ob);
                ob = new InsertDataTable();
                ob.setColName("roomNo");
                if(rs.getString("BED_NO") != null && !rs.getString("BED_NO").toString().equals(""))
                {
                    ob.setDataName("Emergency");
                } else
                {
                    ob.setDataName("NA");
                }
                insertData.add(ob);
                ob = new InsertDataTable();
                ob.setColName("orderBy");
                if(rs.getString("ADDED_BY_ID") != null && !rs.getString("ADDED_BY_ID").toString().equals(""))
                {
                    ob.setDataName(rs.getString("ADDED_BY_ID").toString());
                } else
                {
                    ob.setDataName("NA");
                }
                insertData.add(ob);
                ob = new InsertDataTable();
                ob.setColName("orderDate");
                if(rs.getString("ORD_DATE_TIME") != null && !rs.getString("ORD_DATE_TIME").toString().equals(""))
                {
                    ob.setDataName(rs.getString("ORD_DATE_TIME").toString().split(" ")[0]);
                } else
                {
                    ob.setDataName("NA");
                }
                insertData.add(ob);
                ob = new InsertDataTable();
                ob.setColName("orderTime");
                if(rs.getString("ORD_DATE_TIME") != null && !rs.getString("ORD_DATE_TIME").toString().equals(""))
                {
                    ob.setDataName(rs.getString("ORD_DATE_TIME").toString().split(" ")[1]);
                } else
                {
                    ob.setDataName("NA");
                }
                insertData.add(ob);
                ob = new InsertDataTable();
                ob.setColName("treatingDoc");
                if(rs.getString("PRACTITIONER_NAME") != null && !rs.getString("PRACTITIONER_NAME").toString().equals(""))
                {
                    ob.setDataName(rs.getString("PRACTITIONER_NAME").toString());
                } else
                {
                    ob.setDataName("NA");
                }
                insertData.add(ob);
                ob = new InsertDataTable();
                ob.setColName("treatingSpec");
                if(rs.getString("DOCTOR_SPECIALITY") != null && !rs.getString("DOCTOR_SPECIALITY").toString().equals(""))
                {
                    ob.setDataName(rs.getString("DOCTOR_SPECIALITY").toString());
                    System.out.println((new StringBuilder("treatingSpec : ")).append(rs.getString("DOCTOR_SPECIALITY").toString()).toString());
                } else
                {
                    ob.setDataName("NA");
                }
                insertData.add(ob);
                ob = new InsertDataTable();
                ob.setColName("openDate");
                ob.setDataName(DateUtil.getCurrentDateUSFormat());
                insertData.add(ob);
                ob = new InsertDataTable();
                ob.setColName("openTime");
                ob.setDataName(DateUtil.getCurrentTime());
                insertData.add(ob);
                ob = new InsertDataTable();
                ob.setColName("assignMchn");
                ob.setDataName("NA-NA");
                insertData.add(ob);
                
                
                // as per request from OCC Mr. Chandrabhan arr em order priority is Stat change 17-06-2016
                ob = new InsertDataTable();
                ob.setColName("priority");
                if(rs.getString("PRIORITY") != null && !rs.getString("PRIORITY").toString().equals(""))
                {
                   /* if(rs.getString("PRIORITY").toString().equalsIgnoreCase("R"))
                    {
                        pri = "Routine";
                    } else
                    if(rs.getString("PRIORITY").toString().equalsIgnoreCase("U"))
                    {
                        pri = "Urgent";
                    } else
                    if(rs.getString("PRIORITY").toString().equalsIgnoreCase("S"))
                    {*/
                        pri = "Stat";
                    //}
                    ob.setDataName(pri);
                } else
                {
                    ob.setDataName("Stat");
                }
                insertData.add(ob);
                CommonOperInterface cbt = (new CommonConFactory()).createInterface();
                try
                {
                    List dataListOCCEsc = cbt.executeAllSelectQuery((new StringBuilder("SELECT  l1Tol2 FROM escalation_order_detail  WHERE escDept=(SELECT id FROM depar" +
"tment WHERE deptName LIKE '%OCC%' ) AND priority LIKE '%"
)).append(pri).append("%'".toString()).toString(), clientConnection);
                    String esctm = null;
                    if(dataListOCCEsc != null && dataListOCCEsc.size() > 0)
                    {
                        Object object = dataListOCCEsc.get(0);
                        esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), object.toString(), "Y");
                    } else
                    {
                        esctm = DateUtil.newdate_AfterEscTime(DateUtil.getCurrentDateUSFormat(), DateUtil.getCurrentTime(), "00:15", "Y");
                    }
                    dataListOCCEsc.clear();
                    ob = new InsertDataTable();
                    ob.setColName("escalation_date");
                    ob.setDataName(esctm.split("#")[0]);
                    insertData.add(ob);
                    ob = new InsertDataTable();
                    ob.setColName("escalation_time");
                    ob.setDataName(esctm.split("#")[1]);
                    insertData.add(ob);
                    ob = new InsertDataTable();
                    ob.setColName("department");
                    ob.setDataName("17");
                    insertData.add(ob);
                    ob = new InsertDataTable();
                    ob.setColName("level");
                    ob.setDataName("level1");
                    insertData.add(ob);
                    ob = new InsertDataTable();
                    ob.setColName("nursingUnit");
                    ob.setDataName("Emergency");
                    insertData.add(ob);
                    ob = new InsertDataTable();
                    ob.setColName("status");
                    ob.setDataName("Un-assigned");
                    insertData.add(ob);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                boolean chkRepeateOrd = chkRepeatOrder(rs.getString("ORDER_ID").toString(), clientConnection);
                System.out.println((new StringBuilder("chkRepeateOrd ")).append(chkRepeateOrd).toString());
                System.out.println((new StringBuilder("ordNameChange >>> ")).append(ordNameChange).toString());
                System.out.println((new StringBuilder("jshgfjsf >>> ")).append(rs.getString("ORDER_TYPE_CODE").toString()).toString());
                if(chkRepeateOrd)
                {
                	
                    if(rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("CARD") || rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("MUSG") || rs.getString("ORDER_TYPE_CODE").toString().equalsIgnoreCase("MXR"))
                    {
                        System.out.println((new StringBuilder("sts :>>>>>>>>>>>. ")).append(ordNameChange).toString());
                        int dataId = cot.insertDataReturnId("machine_order_data", insertData, clientConnection);
                        if(dataId > 0)
                        {
                            int id = 0;
                            if(pri.equalsIgnoreCase("Routine"))
                            {
                                id = addCartConfig(dataId, "", ordNameChange, rs.getString("ORDER_ID").toString(), pri, clientConnection);
                            }
                            if(id == 0)
                            {
                                sendOccAlertOnSync("17", "1", "ORD", rs.getString("ORDER_ID").toString(), ordNameChange, pri, clientConnection);
                            }
                        }
                    }
                } else
                {
                    System.out.println("ord reapet");
                }
            }
            for(rs = st.executeQuery("select * from Dreamsol_Mc_em_ord_VW where TRUNC (CAN_DATE_TIME) = TRUNC (SYSDATE" +
") AND ORDER_TYPE_CODE IN('MXR','MUSG', 'MUCT','HSME')  "
); rs.next();)
            {
                System.out.println((new StringBuilder("EM_CAN_DATE")).append(rs.getString("CAN_DATE_TIME")).append(" orderID ").append(rs.getString("ORDER_ID")).append(" Reason ").append(rs.getString("CAN_LINE_REASON")).toString());
                boolean chkFlag = checkCancelOrder(rs.getString("ORDER_ID"), clientConnection);
                if(chkFlag)
                {
                    StringBuilder qry = new StringBuilder();
                    qry.append((new StringBuilder("Update machine_order_data set status='CancelHIS' where orderid='")).append(rs.getString("ORDER_ID")).append("'").toString());
                    int chk = cot.executeAllUpdateQuery(qry.toString(), clientConnection);
                    if(chk > 0)
                    {
                        qry.setLength(0);
                        qry.append((new StringBuilder("select id from machine_order_data where orderid='")).append(rs.getString("ORDER_ID")).append("'").toString());
                        List temp = cot.executeAllSelectQuery(qry.toString(), clientConnection);
                        if(temp != null && !temp.isEmpty())
                        {
                            Object obj = temp.get(0);
                            List ulist = new ArrayList();
                            ob = new InsertDataTable();
                            ob.setColName("status");
                            ob.setDataName("CancelHIS");
                            ulist.add(ob);
                            ob = new InsertDataTable();
                            ob.setColName("action_date");
                            ob.setDataName(rs.getString("CAN_DATE_TIME").split(" ")[0]);
                            ulist.add(ob);
                            ob = new InsertDataTable();
                            ob.setColName("action_time");
                            ob.setDataName(rs.getString("CAN_DATE_TIME").split(" ")[1]);
                            ulist.add(ob);
                            ob = new InsertDataTable();
                            ob.setColName("action_reason");
                            if(rs.getString("CAN_LINE_REASON") != null && !rs.getString("CAN_LINE_REASON").equalsIgnoreCase(""))
                            {
                                ob.setDataName(rs.getString("CAN_LINE_REASON"));
                            } else
                            {
                                ob.setDataName(rs.getString("CancelHIS"));
                            }
                            ulist.add(ob);
                            ob = new InsertDataTable();
                            ob.setColName("feedId");
                            ob.setDataName(obj.toString());
                            ulist.add(ob);
                            boolean i = cot.insertIntoTable("order_status_history", ulist, clientConnection);
                            if(i)
                            {
                                cot.deleteAllRecordForId("machine_order_cart", "orderId", obj.toString(), clientConnection);
                                String namemob = getAllotToOfCancel(rs.getString("ORDER_ID"), clientConnection);
                                if(!namemob.equalsIgnoreCase(""))
                                {
                                    
                                    qry.setLength(0);
                                    qry.append((new StringBuilder("Cancel Order Alert: ")).append(rs.getString("ORDER_TYPE_CODE").toString()).append("/").append(rs.getString("CATALOG_DESC").toString()).append(", Location: ").append(rs.getString("BED_NUM").toString()).append("/").append(rs.getString("PATIENT_ID").toString()).append("/").append(rs.getString("ORDER_ID").toString()).append(" Order Date/Time: ").append(rs.getString("ORD_DATE_TIME").toString()).append(".").toString());
                                    ch.addMessage(namemob.split("#")[0], "AUTO", namemob.split("#")[1], "", qry.toString(), "Pending", "0", "ORD", clientConnection);
                                    qry.setLength(0);
                                }
                            }
                        }
                    }
                }
            }

           // break MISSING_BLOCK_LABEL_3158;
        } catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			log.info("Error in ClientDataIntegrationHelper.java \n " + e.getStackTrace());
			ch.addMessage("Manab", "Alert", "8882572103", "Order fetche File IPD 88 Server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
			ch.addMessage("Chandrabhan", "Alert", "8826336111", "Order fetche File IPD 88 Server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
			ch.addMessage("Prabhat", "Alert", "9250400315", "Order fetche File IPD 88 Server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
			ch.addMessage("Sanjay", "Alert", "8130772488", "Order fetche File IPD 88 Server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
			ch.addMessage("Kamlesh", "Alert", "8010797571", "Order fetche File IPD 88 Server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
			//ch.addMessage("Abhay", "Alert", "9873325443", "Order fetche File IPD 88 Server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
			//ch.addMessage("Manab", "Alert", "8882572103", "Order fetche File EM 66 server: "+e.toString().substring(0, 30) , "", "Pending", "0", "ORD", clientConnection);
		} finally
		{
			try
			{
				rs.close();
				st.close();
				con.close();
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private String getAllotToOfCancel(String ordId, SessionFactory connection)
	{
		String str = "";
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataListEcho = cbt.executeAllSelectQuery("SELECT emp.empName,emp.mobOne from machine_order_data as modi INNER JOIN employee_basic AS emp ON emp.id=modi.assignTech where modi.orderid='" + ordId + "' ", connection);

			if (dataListEcho != null && !dataListEcho.isEmpty())
			{
				System.out.println("CancelOrder>>>>  " + dataListEcho.size());
				Object[] obj = (Object[]) dataListEcho.get(0);
				if (obj[0] != null && obj[1] != null)
				{
					str = obj[0].toString() + "#" + obj[1].toString();
				}
				dataListEcho.clear();
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		return str;
	}

	private void sendOccAlertOnSync(String department, String level, String module, String OrderNo, String orderType, String Priority, SessionFactory connection)
	{
		System.out.println("44");
		String machineId = machineID(orderType, connection);
		List nameMob = new OrderServiceFileHelper().selectRendomTechni(machineId, department, level, module, connection);
		CommunicationHelper ch = new CommunicationHelper();
		if (nameMob != null && !nameMob.isEmpty())
		{
			System.out.println("55");
			Object object = (Object) nameMob.get(0);
			System.out.println("SYNC Detials: " + object.toString());

			ch.addMessage(object.toString().split("#")[1], "AUTO", object.toString().split("#")[2], "Un-Assigned Order: " + OrderNo + ", " + orderType + "/" + Priority, "", "Pending", "0", module, connection);
		}

	}

	private String  machineID(String orderType, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		String machineId = "0";
		 
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			//qry.append(");
			String machineName=" ";
			if("XR".equalsIgnoreCase(orderType)){
				machineName="Xray";
			}
			if("ULTRA".equalsIgnoreCase(orderType)){
				machineName="ultrasound";
			}
			if("CT".equalsIgnoreCase(orderType)){
				machineName="CTscan";
			}
			//15-12-2015 by aarif
			if("HSME".equalsIgnoreCase(orderType)){
				machineName="Echo";
			}
			List machineIDList=	cbt.executeAllSelectQuery(" select id from machine_master where machine = '"+machineName+"'".toString(), connection);
			//dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);

			if (machineIDList != null && machineIDList.size() > 0)
			{
				
				machineId = machineIDList.get(0).toString();

			}

		}
		catch (SQLGrammarException e)
		{
			e.printStackTrace();
		}
		
		
		
		return machineId;
		
	}

	private boolean checkCancelOrder(String orderId, SessionFactory clientConnection)
	{
		boolean check = false;
		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataListEcho = cbt.executeAllSelectQuery("SELECT status from machine_order_data  where orderid='" + orderId + "' ", clientConnection);

			if (dataListEcho != null && !dataListEcho.isEmpty())
			{
				System.out.println("CancelOrder>>>>  " + dataListEcho.size());
				Object obj = (Object) dataListEcho.get(0);
				if (obj != null && !obj.toString().equalsIgnoreCase("CancelHIS"))
				{
					check = true;
				}
				dataListEcho.clear();
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return check;
	}

	@SuppressWarnings("rawtypes")
	private int addCartConfig(final int dataId, final String nCode, final String orderName, final String orderId, String priority, final SessionFactory clientConnection)
	{
		int chk = 0;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List data = cbt.executeAllSelectQuery("SELECT mocs.cart_id  FROM machine_order_cart_settings AS mocs INNER JOIN machine_order_nursing_details AS mond ON mond.id=mocs.nursing_unit where mond.nursing_code='" + nCode + "'  and order_name='" + orderName + "' and mocs.status='Active'", clientConnection);
		if (data != null && !data.isEmpty())
		{

			List<InsertDataTable> insertCardData = new ArrayList<InsertDataTable>();
			InsertDataTable ob;
			Object object = (Object) data.get(0);
			data.clear();
			if (object != null)
			{
				data = cbt.executeAllSelectQuery("select to_time from machine_cart_time where cart_name='" + object.toString() + "' and from_time<='" + DateUtil.getCurrentTime() + "' and to_time  >'" + DateUtil.getCurrentTime() + "'", clientConnection);
				if (data != null && !data.isEmpty())
				{
					Object object1 = (Object) data.get(0);
					if (object1 != null)
					{
						ob = new InsertDataTable();
						ob.setColName("cartId");
						ob.setDataName(object.toString());
						insertCardData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("orderId");
						ob.setDataName(dataId);
						insertCardData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("orderName");
						ob.setDataName(orderName);
						insertCardData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("orderUID");
						ob.setDataName(orderId);
						insertCardData.add(ob);

						ob = new InsertDataTable();
						ob.setColName("allotFalg");
						ob.setDataName("0");
						insertCardData.add(ob);

						boolean chck = cbt.insertIntoTable("machine_order_cart", insertCardData, clientConnection);

						insertCardData.clear();
						// ob=null;
						// udating machine_order_data status
						if (chck)
						{

							String currDate = DateUtil.getCurrentDateUSFormat();
							String timediff = DateUtil.time_difference(currDate, DateUtil.getCurrentTime(), currDate, object1.toString());

							String esctm = DateUtil.newdate_AfterEscTime(currDate, DateUtil.getCurrentTime(), DateUtil.addTwoTime(timediff, "00:15"), "Y");
							int i = cbt.executeAllUpdateQuery("update machine_order_data set status='Unassigned Cart-" + object.toString() + "',cart_name='Unassigned Cart-" + object.toString() + "',escalation_date='" + esctm.split("#")[0] + "',escalation_time='" + esctm.split("#")[1] + "' where id='" + dataId + "'", clientConnection);
							if (i > 0)
							{
								chk = 1;
							}
						}
					}
				}

			}

		}
		return chk;

	}

	private String lastORDdateTimeget(SessionFactory clientConnection)
	{
		// TODO Auto-generated method stub
		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select orderDate, orderTime from machine_order_data where roomNo!='Emergency' and treatingDoc!='Emergency Team' order by id desc limit 10 ".toString(), clientConnection);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				lastDataTime = object[0].toString() + " " + object[1].toString();
				 System.out.println("Last Order Normal Time  >>>>>>  " + lastDataTime);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return lastDataTime;
	}
	
	// fetch last order time for emergency order
	private String lastORDdateTimeEmergencyget(SessionFactory clientConnection)
	{
		// TODO Auto-generated method stub
		String lastDataTime = null;
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		try
		{
			List lastIdList = cbt.executeAllSelectQuery(" select orderDate, orderTime from machine_order_data where roomNo='Emergency' and treatingDoc='Emergency Team' order by id desc limit 1 ".toString(), clientConnection);

			for (Iterator it = lastIdList.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				lastDataTime = object[0].toString() + " " + object[1].toString();
				 System.out.println("last Emergency Time >>>>>>  " + lastDataTime);
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		return lastDataTime;
	}

	private boolean chkRepeatOrder(String orderID, SessionFactory connection)
	{
		// TODO Auto-generated method stub
		boolean flag = false;

		try
		{
			CommonOperInterface cbt = new CommonConFactory().createInterface();
			List dataListEcho = cbt.executeAllSelectQuery("SELECT orderid from machine_order_data where orderid = '" + orderID + "'".toString(), connection);
			System.out.println("dataListEcho.size()>>>>  " + dataListEcho.size());
			if (dataListEcho.size() == 0)
			{
				flag = true;

			}
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println();
		return flag;
	}

}
