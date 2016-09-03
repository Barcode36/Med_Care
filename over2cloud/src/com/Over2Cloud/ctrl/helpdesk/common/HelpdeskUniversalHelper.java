package com.Over2Cloud.ctrl.helpdesk.common;

import hibernate.common.HibernateSessionFactory;
 



import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Properties;


import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
 

 
 
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;
import com.Over2Cloud.Rnd.createTable;
import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.opensymphony.xwork2.ActionSupport;
import com.Over2Cloud.CommonClasses.IPAddress;

import org.hibernate.exception.SQLGrammarException;
import org.hibernate.internal.SessionFactoryImpl;

import com.Over2Cloud.helpdesk.BeanUtil.FeedbackPojo;
import com.Over2Cloud.helpdesk.BeanUtil.HelpdeskDashboardPojo;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.CommonClasses.Cryptography;

@SuppressWarnings("serial")
public class HelpdeskUniversalHelper extends ActionSupport{
	//private AtomicInteger  AN=new AtomicInteger();
	private static int  AN=0;
	
	
	public static final String DES_ENCRYPTION_KEY = "ankitsin";

	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, List>wherClauseList,Map<String, Object> order,SessionFactory connection)
	  {
	List Data=null;
	StringBuilder selectTableData = new StringBuilder("");  
	selectTableData.append("select ");
	
	// Set the columns name of a table
	if(colmName!=null && !colmName.equals("") && !colmName.isEmpty())
	  {
	int i=1;
	for(String H:colmName)
	 {
	if(i<colmName.size())
	selectTableData.append(H+" ,");
	else
	selectTableData.append(H+" from " +tableName);
	i++;
	 }
	 }
	else
	 {
	selectTableData.append("* from " +tableName);
	 }
	if(wherClause!=null && !wherClause.isEmpty())
	   {
	 if(wherClause.size()>0)
	{
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClause.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(size<wherClause.size())
	selectTableData.append((String)me.getKey()+"='"+me.getValue()+"' and ");
	else
	selectTableData.append((String)me.getKey()+"='"+me.getValue()+"'");
	size++;
	} 
	}
	if(wherClauseList!=null && !wherClauseList.isEmpty())
	   {
	 if(wherClauseList.size()>0)
	{
	selectTableData.append(" and ");
	}
	int size=1;
	Set set =wherClause.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(size<wherClause.size())
	selectTableData.append((String)me.getKey()+" = "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
	else
	selectTableData.append((String)me.getKey()+" = '"+me.getValue().toString().replace("[", "(").replace("]", ")")+"'");
	size++;
	} 
	}
	
	 if(order!=null && !order.isEmpty())
	   {
	Set set = order.entrySet();
	Iterator it = set.iterator();
	while (it.hasNext()) {
	Map.Entry me = (Map.Entry)it.next();
	selectTableData.append(" ORDER BY "+me.getKey()+" "+me.getValue()+"");
	    }
	   }
	selectTableData.append(";");
	//System.out.println("Querry is as "+selectTableData);
	Session session = null;  
	Transaction transaction = null;  
	try {  
	            session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(selectTableData.toString()).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	return Data;
	}
	
	@SuppressWarnings("rawtypes")
	public List getEmp4Escalation(String dept_subDept, String deptLevel,String module, String level,String floor_status,String floor, SessionFactory connectionSpace,String depTid) {
	List empList = new ArrayList();
	//String qry = null;
	StringBuilder query = new StringBuilder();
	try {
	query.append("select distinct emp.id, emp.empName, emp.mobOne, emp.emailIdOne,dept.deptName from employee_basic as emp");
	query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
	if (deptLevel.equalsIgnoreCase("SD")) 
	{
	//String shiftname = DateUtil.getCurrentDateUSFormat().substring(8,10)+ "_date";
	query.append(" inner join subdepartment sub_dept on contacts.forDept_id = sub_dept.id ");
	query.append(" inner join feedback_type fb on fb.dept_subdept = sub_dept.id");
	query.append(" inner join feedback_category catt on catt.fbType = fb.id");
	query.append(" inner join feedback_subcategory subCat on subCat.categoryName = catt.id");
	query.append(" inner join department dept on sub_dept.deptid = dept.id ");
	query.append(" inner join emp_wing_mapping esc on esc.empName =contacts.emp_id  ");
	query.append(" inner join wings_detail wing on wing.id = esc.wingsname ");
	query.append(" inner join floor_room_detail room on room.wingsname = wing.id ");
	query.append(" inner join shift_with_emp_wing shift on shift.id = esc.shiftId ");
	query.append(" where  contacts.level='"+level+"' and contacts.work_status='0' and contacts.moduleName='"+module+"'");
	query.append(" and shift.fromShift<='" + DateUtil.getCurrentTime() + "' and shift.toShift >'" + DateUtil.getCurrentTime() + "'");
	if (floor_status.equalsIgnoreCase("Y"))
	{
	query.append(" and room.id='" + floor + "'   and  fb.dept_subdept='" + dept_subDept + "' and shift.dept_id='" + depTid + "'  ");
	}
	else {
	query.append(" and sub_dept.id='"+ dept_subDept+ "'");
	}
	query.append(" GROUP BY emp.id ");
	}
	else {
	query.append(" inner join department dept on contacts.forDept_id = dept.id ");
	query.append(" where contacts.level='"+level+"' and contacts.moduleName='"+module+"'");
	query.append(" and dept.id='"+ dept_subDept+ "'");
	}
	//System.out.println("Escalation Query  ::::: "+query.toString());
	empList = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return empList;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, List>wherClauseList,Map<String, Object> order,String searchField,String searchString,String searchOper,SessionFactory connection)
	  {
	List Data=null;
	StringBuilder selectTableData = new StringBuilder("");  
	selectTableData.append("select ");
	if(colmName!=null && !colmName.equals("") && !colmName.isEmpty())
	  {
	int i=1;
	for(String H:colmName)
	 {
	if(i<colmName.size())
	selectTableData.append(H+" ,");
	else
	selectTableData.append(H+" from " +tableName);
	i++;
	 }
	 }
	// Here we get the whole data of a table
	else
	 {
	selectTableData.append("* from " +tableName);
	 }
	    
	// Set the values for where clause
	if(wherClause!=null && !wherClause.isEmpty())
	   {
	 if(wherClause.size()>0)
	{
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClause.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(size<wherClause.size())
	selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"' and ");
	else
	selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
	size++;
	} 
	}
	
	// Set the values for where clause List
	if(wherClauseList!=null && !wherClauseList.isEmpty())
	   {
	 if (wherClause!=null) {
	 selectTableData.append(" and ");
	 }
	 else
	    {
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClauseList.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(me!=null && (String)me.getKey()!=null && me.getValue()!=null)
	{
	if(size<wherClauseList.size())
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
	else
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+"");
	size++;
	}
	} 
	}
	
	 if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	{
	 selectTableData.append(" and");
     	  
     	if(searchOper.equalsIgnoreCase("eq"))
	{
     	 selectTableData.append(" "+searchField+" = '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("cn"))
	{
	 selectTableData.append(" "+searchField+" like '%"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("bw"))
	{
	 selectTableData.append(" "+searchField+" like '"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("ne"))
	{
	selectTableData.append(" "+searchField+" <> '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("ew"))
	{
	selectTableData.append(" "+searchField+" like '%"+searchString+"'");
	}
	}
	
	 // Set the value for type of order for getting data in specific order
	 if(order!=null && !order.isEmpty())
	   {
	Set set = order.entrySet();
	Iterator it = set.iterator();
	while (it.hasNext()) {
	Map.Entry me = (Map.Entry)it.next();
	selectTableData.append(" ORDER BY "+me.getKey()+" "+me.getValue()+"");
	    }
	   }
	selectTableData.append(";");
	//System.out.println("Qry For data::"+selectTableData);
	Session session = null;  
	Transaction transaction = null;  
	try {  
	            session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(selectTableData.toString()).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	return Data;
	}
	
	

	@SuppressWarnings("unchecked")
	/*public String writeDataInExcel(List dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,SessionFactory connection)
	{ 
	String excelFileName =null;
	String mergeDateTime=new DateUtil().mergeDateTime();
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet(excelName);
	Map<String, CellStyle> styles = createStyles(wb);
	
	Row headerRow = sheet.createRow(2);
	headerRow.setHeightInPoints(15);
	sheet.setFitToPage(true);
	sheet.setHorizontallyCenter(true);
	
	Row titleRow = sheet.createRow(0);
	titleRow.setHeightInPoints(22);
	Cell titleCell = titleRow.createCell(0);
	titleCell.setCellValue(orgName);
	titleCell.setCellStyle(styles.get("title"));
	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,titleList.size()-1));
	
	// Sub Title Row
	Row subTitleRow = sheet.createRow(1);
	subTitleRow.setHeightInPoints(18);
	Cell subTitleCell = subTitleRow.createCell(0);
	subTitleCell.setCellValue(excelName);
	subTitleCell.setCellStyle(styles.get("subTitle"));
	sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,titleList.size()-1));
	
	// Creating the folder for holding the Excel files with the defined name
	if(needToStore)
	excelFileName = new CreateFolderOs().createUserDir("Feedback_Status")+ "/" +"Feedback"+ mergeDateTime+".xls";
	else
	excelFileName="Feedback"+ mergeDateTime;
	
	int header_first=2;
	int index=3;
	HSSFRow rowHead = sheet.createRow((int) header_first);
	for(int i=0;i<titleList.size();i++)
	{
	rowHead.createCell((int) i).setCellValue(titleList.get(i).toString());
	}
	if(dataList!=null && dataList.size()>0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	HSSFRow rowData=sheet.createRow((int)index);
	for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
	{
	if(object[cellIndex]!=null)
	{
	if(object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
	{
	rowData.createCell((int) cellIndex).setCellValue(DateUtil.convertDateToIndianFormat(object[cellIndex].toString()));
	}
	else
	{
	 
	 
	rowData.createCell((int) cellIndex).setCellValue(object[cellIndex].toString());
	 
	}
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	sheet.autoSizeColumn(index);
	index++;	
	}
	}
	
	
	try
	{
	FileOutputStream out = new FileOutputStream(new File(excelFileName));
	        wb.write(out);
	        out.close();
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return excelFileName;
	}
 */
	
	/*public String writeDataInExcel(List dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,SessionFactory connection)
	{ 
	
	String excelFileName =null;
	String mergeDateTime=new DateUtil().mergeDateTime();
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet(excelName);
	Map<String, CellStyle> styles = createStyles(wb);
	
	CellStyle cellStyle = wb.createCellStyle();
	  Calendar calendar = new GregorianCalendar();
          calendar.set(1982,Calendar.NOVEMBER,25);
	Row headerRow = sheet.createRow(2);
	headerRow.setHeightInPoints(15);
	sheet.setFitToPage(true);
	sheet.setHorizontallyCenter(true);
	
	Row titleRow = sheet.createRow(0);
	titleRow.setHeightInPoints(22);
	Cell titleCell = titleRow.createCell(0);
	titleCell.setCellValue(orgName);
	titleCell.setCellStyle(styles.get("title"));
	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,titleList.size()-1));
	
	// Sub Title Row
	Row subTitleRow = sheet.createRow(1);
	subTitleRow.setHeightInPoints(18);
	Cell subTitleCell = subTitleRow.createCell(0);
	subTitleCell.setCellValue(excelName);
	subTitleCell.setCellStyle(styles.get("subTitle"));
	sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,titleList.size()-1));
	
	// Creating the folder for holding the Excel files with the defined name
	if(needToStore)
	excelFileName = new CreateFolderOs().createUserDir("Feedback_Status")+ "/" +"Feedback"+ mergeDateTime+".xls";
	else
	excelFileName="Feedback"+ mergeDateTime+".xls";
	
	int header_first=2;
	int index=3;
	HSSFRow rowHead = sheet.createRow((int) header_first);
	for(int i=0;i<titleList.size();i++)
	{
	Cell subTitleCell22 = rowHead.createCell((i));
	subTitleCell22.setCellValue(titleList.get(i).toString());
	subTitleCell22.setCellStyle(styles.get("mytittle"));
	}
	if(dataList!=null && dataList.size()>0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	HSSFRow rowData=sheet.createRow((int)index);
	for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
	{
	if(object[cellIndex]!=null)
	{
	
	cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));
	
	if(object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
	{
	System.out.println(object[cellIndex].toString());
	System.out.println(object[cellIndex].toString().split("-")[1]);
	 calendar.set(Integer.parseInt(object[cellIndex].toString().split("-")[0]),DateUtil.getMonthFromDay(object[cellIndex].toString().split("-")[1]),Integer.parseInt(object[cellIndex].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue( calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	 
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(object[cellIndex].toString());
	 
	}
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	sheet.autoSizeColumn(index);
	index++;	
	}
	}
	
	
	try
	{
	FileOutputStream out = new FileOutputStream(new File(excelFileName));
	        wb.write(out);
	        out.close();
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return excelFileName;
	}*/
	public String writeDataInExcel(List dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,SessionFactory connection,List escdatalist,List resdatalist,List snoozedataList,List reopdataList)
	{ 
	
	String excelFileName =null;
	String mergeDateTime=new DateUtil().mergeDateTime();
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet(excelName);
	Map<String, CellStyle> styles = createStyles(wb);
	
	CellStyle cellStyle = wb.createCellStyle();
	  Calendar calendar = new GregorianCalendar();
          calendar.set(1982,Calendar.NOVEMBER,25);
	Row headerRow = sheet.createRow(2);
	headerRow.setHeightInPoints(15);
	sheet.setFitToPage(true);
	sheet.setHorizontallyCenter(true);
	
	Row titleRow = sheet.createRow(0);
	titleRow.setHeightInPoints(22);
	Cell titleCell = titleRow.createCell(0);
	titleCell.setCellValue(orgName);
	titleCell.setCellStyle(styles.get("title"));
	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,titleList.size()-1));
	
	// Sub Title Row
	Row subTitleRow = sheet.createRow(1);
	subTitleRow.setHeightInPoints(18);
	Cell subTitleCell = subTitleRow.createCell(0);
	subTitleCell.setCellValue(excelName);
	subTitleCell.setCellStyle(styles.get("subTitle"));
	sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,titleList.size()-1));
	
	// Creating the folder for holding the Excel files with the defined name
	/*if(needToStore)
	excelFileName = new CreateFolderOs().createUserDir("Feedback_Status")+ "/" +"Feedback"+ mergeDateTime+".xls";
	else*/
	excelFileName="HDM_Feedback"+ mergeDateTime+".xls";
	
	int header_first=2;
	int index=3;
	try
	{
	HSSFRow rowHead = sheet.createRow((int) header_first);
	for(int i=0;i<titleList.size();i++)
	{
	//rowHead.createCell((int) i).setCellValue(titleList.get(i).toString());
	Cell subTitleCell22 = rowHead.createCell((i));
	subTitleCell22.setCellValue(titleList.get(i).toString());
	subTitleCell22.setCellStyle(styles.get("mytittle"));	
	}
	if(dataList!=null && dataList.size()>0)
	{
	Map<String, List> mapdata = new HashMap<String, List>();
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	HSSFRow rowData=sheet.createRow((int)index);
	 
	for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
	{
	
	if(
	keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as First_actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Second_actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fourth_actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Third_actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fifth_actDate")
	|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as First_actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Second_actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Third_actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fourth_actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fifth_actTime")	
	|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as First_escalate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Second_escalate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Third_escalate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Fourth_escalate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Fifth_escalate_to")
	|| keyList.get(cellIndex).toString().equalsIgnoreCase("resolution_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as First_Action_Date") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as First_Action_Time") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as First_Action_Duration") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_date as First_Parked_Upto_Date") 
	   ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_time as First_Parked_Upto_Time") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Second_Action_Date") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Second_Action_Time") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Second_Action_Duration") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_date as Second_Parked_Upto_Date") 
	   ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_time as Second_Parked_Upto_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as First_Re-Opened_action_by") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as First_Re-Opened_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as First_Re-Opened_Action_Time") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Second_Re-Opened_action_by") 
	   ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Second_Re-Opened_Action_Date") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Second_Re-Opened_Action_Time")
	   ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Third_Re-Opened_action_by") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Third_Re-Opened_Action_Date")  ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Third_Re-Opened_Action_Time")
	   ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Fourth_Re-Opened_action_by") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fourth_Re-Opened_Action_Date") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fourth_Re-Opened_Action_Time")
	   ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Fifth_Re-Opened_action_by") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fifth_Re-Opened_Action_Date")  ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fifth_Re-Opened_Action_Time")
	   
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve1_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve1_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve1_Action_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve1_Action_Duration")	
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve2_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve2_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve2_Action_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve2_Action_Duration")	
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve3_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve3_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve3_Action_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve3_Action_Duration")	
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve4_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve4_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve4_Action_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve4_Action_Duration")	
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve5_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve5_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve5_Action_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve5_Action_Duration")	
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve6_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve6_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve6_Action_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve6_Action_Duration")
	)
	{
	//System.out.println(keyList.get(cellIndex).toString()+" #################### "+cellIndex );
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as First_actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Second_actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fourth_actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Third_actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fifth_actDate")
	|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as First_actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Second_actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Third_actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fourth_actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fifth_actTime")	
	|| keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as First_escalate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Second_escalate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Third_escalate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Fourth_escalate_to") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Fifth_escalate_to"))
	{
	if(escdatalist!=null && escdatalist.size()>0)
	{
	int in=0;
	for (Iterator ite = escdatalist.iterator(); ite.hasNext();) 
	{
	Object[] obje = (Object[]) ite.next();
	if(obje[3].equals(object[0]))
	{
	if(in==0)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as First_actDate"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),
	Integer.parseInt(obje[0].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as First_actTime"))
	{
	//System.out.println(keyList.get(cellIndex).toString());
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[1].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as First_escalate_to"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==1)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Second_actDate"))
	{
	if(obje[0]!=null)
	{
	calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),Integer.parseInt(obje[0].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Second_actTime"))
	{
	if(obje[1]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[1].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Second_escalate_to"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==2)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Third_actDate"))
	{
	if(obje[0]!=null)
	{
	calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),Integer.parseInt(obje[0].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Third_actTime"))
	{
	if(obje[1]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[1].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Third_escalate_to"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==3)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fourth_actDate"))
	{
	if(obje[0]!=null)
	{
	calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),Integer.parseInt(obje[0].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fourth_actTime"))
	{
	if(obje[1]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[1].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Fourth_escalate_to"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==4)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fifth_actDate"))
	{
	if(obje[0]!=null)
	{
	calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),Integer.parseInt(obje[0].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fifth_actTime"))
	{
	if(obje[1]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[1].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Fifth_escalate_to"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	  }
	
	in++;
	}
	/*else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}*/
	  }
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as First_Action_Date") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as First_Action_Time") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as First_Action_Duration") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_date as First_Parked_Upto_Date") 
	   ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_time as First_Parked_Upto_Time") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Second_Action_Date") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Second_Action_Time") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Second_Action_Duration") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_date as Second_Parked_Upto_Date") 
	   ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_time as Second_Parked_Upto_Time")  
	   )
	{
	if(object[15]!=null)
	{
	if(snoozedataList!=null && snoozedataList.size()>0)
	{
	int in=0;
	for (Iterator ite = snoozedataList.iterator(); ite.hasNext();) 
	{
	Object[] obje = (Object[]) ite.next();
	if(obje[5].equals(object[0]))
	{
	if(in==0)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as First_Action_Date"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),
	Integer.parseInt(obje[0].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as First_Action_Time"))
	{
	//System.out.println(keyList.get(cellIndex).toString());
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[1].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as First_Action_Duration"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_date as First_Parked_Upto_Date"))
	{
	//System.out.println(keyList.get(cellIndex).toString());
	if(obje[3]!=null)
	{
	//System.out.println(obje[0]);	
	calendar.set(Integer.parseInt(obje[3].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[3].toString().split("-")[1]),
	Integer.parseInt(obje[3].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_time as First_Parked_Upto_Time"))
	{
	if(obje[4]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[4].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==1)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Second_Action_Date"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	calendar.set(Integer.parseInt(obje[0].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[0].toString().split("-")[1]),
	Integer.parseInt(obje[0].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Second_Action_Time"))
	{
	//System.out.println(keyList.get(cellIndex).toString());
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[1].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Second_Action_Duration"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_date as Second_Parked_Upto_Date"))
	{
	//System.out.println(keyList.get(cellIndex).toString());
	if(obje[3]!=null)
	{
	//System.out.println(obje[0]);	
	calendar.set(Integer.parseInt(obje[3].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[3].toString().split("-")[1]),
	Integer.parseInt(obje[3].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_time as Second_Parked_Upto_Time"))
	{
	if(obje[4]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[4].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	in++;
	}
	  }
	}
	else{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	  }
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as First_Re-Opened_action_by") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as First_Re-Opened_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as First_Re-Opened_Action_Time") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Second_Re-Opened_action_by") 
	   ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Second_Re-Opened_Action_Date") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Second_Re-Opened_Action_Time")
	   
	    ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Third_Re-Opened_action_by") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Third_Re-Opened_Action_Date")  ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Third_Re-Opened_Action_Time")
	   ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Fourth_Re-Opened_action_by") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fourth_Re-Opened_Action_Date") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fourth_Re-Opened_Action_Time")
	   ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Fifth_Re-Opened_action_by") ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fifth_Re-Opened_Action_Date")  ||  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fifth_Re-Opened_Action_Time")
	   )
	{
	if(reopdataList!=null && reopdataList.size()>0)
	{
	int in=0;
	for (Iterator ite = reopdataList.iterator(); ite.hasNext();) 
	{
	Object[] obje = (Object[]) ite.next();
	if(obje[3].equals(object[0]))
	{
	if(in==0)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as First_Re-Opened_action_by"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[0].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as First_Re-Opened_Action_Date"))
	{
	//System.out.println(keyList.get(cellIndex).toString());
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	calendar.set(Integer.parseInt(obje[1].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[1].toString().split("-")[1]),
	Integer.parseInt(obje[1].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as First_Re-Opened_Action_Time"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==1)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Second_Re-Opened_action_by"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[0].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Second_Re-Opened_Action_Date"))
	{
	//System.out.println(keyList.get(cellIndex).toString());
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	calendar.set(Integer.parseInt(obje[1].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[1].toString().split("-")[1]),
	Integer.parseInt(obje[1].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Second_Re-Opened_Action_Time"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==2)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Third_Re-Opened_action_by"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[0].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Third_Re-Opened_Action_Date"))
	{
	//System.out.println(keyList.get(cellIndex).toString());
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	calendar.set(Integer.parseInt(obje[1].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[1].toString().split("-")[1]),
	Integer.parseInt(obje[1].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Third_Re-Opened_Action_Time"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==3)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Fourth_Re-Opened_action_by"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[0].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fourth_Re-Opened_Action_Date"))
	{
	//System.out.println(keyList.get(cellIndex).toString());
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	calendar.set(Integer.parseInt(obje[1].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[1].toString().split("-")[1]),
	Integer.parseInt(obje[1].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fourth_Re-Opened_Action_Time"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==4)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Fifth_Re-Opened_action_by"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[0].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fifth_Re-Opened_Action_Date"))
	{
	//System.out.println(keyList.get(cellIndex).toString());
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	calendar.set(Integer.parseInt(obje[1].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[1].toString().split("-")[1]),
	Integer.parseInt(obje[1].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fifth_Re-Opened_Action_Time"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	in++;
	}
	/*else{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}*/
	  }
	}
	else{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	  }
	else if(  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve1_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve1_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve1_Action_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve1_Action_Duration")	
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve2_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve2_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve2_Action_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve2_Action_Duration")	
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve3_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve3_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve3_Action_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve3_Action_Duration")	
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve4_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve4_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve4_Action_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve4_Action_Duration")	
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve5_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve5_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve5_Action_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve5_Action_Duration")	
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve6_Action_By") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve6_Action_Date") 
	   || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve6_Action_Time") || keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve6_Action_Duration")
        	   || keyList.get(cellIndex).toString().equalsIgnoreCase("resolution_time")
        	   )
	{
	if(object[15]!=null)
	{
	if(resdatalist!=null && resdatalist.size()>0)
	{
	int in=0;
	for (Iterator ite = resdatalist.iterator(); ite.hasNext();) 
	{
	Object[] obje = (Object[]) ite.next();
	if(obje[4].equals(object[0]))
	{
	if(in==0)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve1_Action_By"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[0].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve1_Action_Date"))
	{
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	calendar.set(Integer.parseInt(obje[1].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[1].toString().split("-")[1]),
	Integer.parseInt(obje[1].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve1_Action_Time"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve1_Action_Duration"))
	{
	if(obje[3]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[3].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==1)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve2_Action_By"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[0].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve2_Action_Date"))
	{
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	calendar.set(Integer.parseInt(obje[1].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[1].toString().split("-")[1]),
	Integer.parseInt(obje[1].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve2_Action_Time"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve2_Action_Duration"))
	{
	if(obje[3]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[3].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==2)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve3_Action_By"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[0].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve3_Action_Date"))
	{
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	calendar.set(Integer.parseInt(obje[1].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[1].toString().split("-")[1]),
	Integer.parseInt(obje[1].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve3_Action_Time"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve3_Action_Duration"))
	{
	if(obje[3]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[3].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==3)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve4_Action_By"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[0].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve4_Action_Date"))
	{
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	calendar.set(Integer.parseInt(obje[1].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[1].toString().split("-")[1]),
	Integer.parseInt(obje[1].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve4_Action_Time"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve4_Action_Duration"))
	{
	if(obje[3]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[3].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}else if(in==4)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve5_Action_By"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[0].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve5_Action_Date"))
	{
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	calendar.set(Integer.parseInt(obje[1].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[1].toString().split("-")[1]),
	Integer.parseInt(obje[1].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve5_Action_Time"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve5_Action_Duration"))
	{
	if(obje[3]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[3].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else if(in==5)
	{
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve6_Action_By"))
	{
	if(obje[0]!=null)
	{
	//System.out.println(obje[0]);	
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[0].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve6_Action_Date"))
	{
	if(obje[1]!=null)
	{
	//	System.out.println(obje[1]);	
	calendar.set(Integer.parseInt(obje[1].toString().split("-")[0]),
	DateUtil.getMonthFromDay(obje[1].toString().split("-")[1]),
	Integer.parseInt(obje[1].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve6_Action_Time"))
	{
	if(obje[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[2].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve6_Action_Duration"))
	{
	if(obje[3]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obje[3].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	in++;
	}
	/*else{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}*/
	}	
	/*for (Iterator ite = resdatalist.iterator(); ite.hasNext();) 
	{
	Object[] obje = (Object[]) ite.next();
	if(obje[3].equals(object[0]))
	{
	  if(obje[0]!=null && obje[1]!=null )
	    	{
	  	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(DateUtil.time_difference(object[7].toString(),object[8].toString(),obje[0].toString(),obje[1].toString()));
	cell.setCellStyle(cellStyle);
	}
	  }
	}*/
	}
	else{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else{
	if( !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as First_actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Second_actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fourth_actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Third_actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fifth_actDate")
	&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as First_actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Second_actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Third_actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fourth_actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fifth_actTime")	
	&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as First_escalate_to") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Second_escalate_to") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Third_escalate_to") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Fourth_escalate_to") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Fifth_escalate_to")
	&& !keyList.get(cellIndex).toString().equalsIgnoreCase("resolution_time") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as First_Action_Date") &&  !  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as First_Action_Time") 
	&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as First_Action_Duration") &&  !keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_date as First_Parked_Upto_Date") 
	&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_time as First_Parked_Upto_Time") &&  !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Second_Action_Date") &&  !  keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Second_Action_Time") 
	&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Second_Action_Duration") &&  !keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_date as Second_Parked_Upto_Date") 
	&& !keyList.get(cellIndex).toString().equalsIgnoreCase("history.sn_upto_time as Second_Parked_Upto_Time") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as First_Re-Opened_action_by") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as First_Re-Opened_Action_Date") 
	    && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as First_Re-Opened_Action_Time") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Second_Re-Opened_action_by") 
	    && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Second_Re-Opened_Action_Date") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Second_Re-Opened_Action_Time") 
	    && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Third_Re-Opened_action_by") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Third_Re-Opened_Action_Date")  && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Third_Re-Opened_Action_Time")
	    && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Fourth_Re-Opened_action_by") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fourth_Re-Opened_Action_Date") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fourth_Re-Opened_Action_Time")
	    && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by   as Fifth_Re-Opened_action_by") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Fifth_Re-Opened_Action_Date")  && ! keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Fifth_Re-Opened_Action_Time")
	   
	    
	     && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve1_Action_By") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve1_Action_Date") 
	   && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve1_Action_Time") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve1_Action_Duration")	
	   && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve2_Action_By") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve2_Action_Date") 
	   && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve2_Action_Time") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve2_Action_Duration")	
	   && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve3_Action_By") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve3_Action_Date") 
	   && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve3_Action_Time") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve3_Action_Duration")	
	   && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve4_Action_By") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve4_Action_Date") 
	   && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve4_Action_Time") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve4_Action_Duration")	
	   && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve5_Action_By") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve5_Action_Date") 
	   && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve5_Action_Time") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve5_Action_Duration")	
	   && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_by as Resolve6_Action_By") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_date as Resolve6_Action_Date") 
	   && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_time as Resolve6_Action_Time") && !keyList.get(cellIndex).toString().equalsIgnoreCase("history.action_duration as Resolve6_Action_Duration")
	    && object[cellIndex]!=null
	    )
	{
	cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));
	if(object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
	{
	calendar.set(Integer.parseInt(object[cellIndex].toString().split("-")[0]),DateUtil.getMonthFromDay(object[cellIndex].toString().split("-")[1]),Integer.parseInt(object[cellIndex].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue( calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(object[cellIndex].toString());
	}
	}
	else
	{
	if(!keyList.get(cellIndex).toString().equalsIgnoreCase("resolution_time"))
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	sheet.autoSizeColumn(index);
	index++;	
	}
	}
	
	
	
	FileOutputStream out = new FileOutputStream(new File(excelFileName));
	        wb.write(out);
	        out.close();
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return excelFileName;
	}
	public String writeDataInExcel(List dataList, List titleList,List keyList,String excelName,String orgName,boolean needToStore,SessionFactory connection)
	{ 
	
	String excelFileName =null;
	String mergeDateTime=new DateUtil().mergeDateTime();
	HSSFWorkbook wb = new HSSFWorkbook();
	HSSFSheet sheet = wb.createSheet(excelName);
	Map<String, CellStyle> styles = createStyles(wb);
	
	CellStyle cellStyle = wb.createCellStyle();
	  Calendar calendar = new GregorianCalendar();
          calendar.set(1982,Calendar.NOVEMBER,25);
	Row headerRow = sheet.createRow(2);
	headerRow.setHeightInPoints(15);
	sheet.setFitToPage(true);
	sheet.setHorizontallyCenter(true);
	
	Row titleRow = sheet.createRow(0);
	titleRow.setHeightInPoints(22);
	Cell titleCell = titleRow.createCell(0);
	titleCell.setCellValue(orgName);
	titleCell.setCellStyle(styles.get("title"));
	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,titleList.size()-1));
	
	// Sub Title Row
	Row subTitleRow = sheet.createRow(1);
	subTitleRow.setHeightInPoints(18);
	Cell subTitleCell = subTitleRow.createCell(0);
	subTitleCell.setCellValue(excelName);
	subTitleCell.setCellStyle(styles.get("subTitle"));
	sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,titleList.size()-1));
	
	// Creating the folder for holding the Excel files with the defined name
	/*if(needToStore)
	excelFileName = new CreateFolderOs().createUserDir("Feedback_Status")+ "/" +"Feedback"+ mergeDateTime+".xls";
	else*/
	excelFileName="Feedback"+ mergeDateTime+".xls";
	
	int header_first=2;
	int index=3;
	HSSFRow rowHead = sheet.createRow((int) header_first);
	String time=null;
	for(int i=0;i<titleList.size();i++)
	{
	Cell subTitleCell22 = rowHead.createCell((i));
	subTitleCell22.setCellValue(titleList.get(i).toString());
	subTitleCell22.setCellStyle(styles.get("mytittle"));
	}
	String actiondate=null,actiontime=null;
	if(dataList!=null && dataList.size()>0)
	{
	for (Iterator iterator = dataList.iterator(); iterator.hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	HSSFRow rowData=sheet.createRow((int)index);
	
	for (int cellIndex = 0; cellIndex < titleList.size(); cellIndex++)
	{
	
	//System.out.println(object[cellIndex]); 
	//System.out.println("bbb "+keyList.get(cellIndex).toString());
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.status as no_response") ||keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_date as actDate")
	|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_time as actTime") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_time as actTime")	
	|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.level") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.level") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.level") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.level") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.level")
	|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.comments")
	|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_by")
	|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.resolution_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_id as ID") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_name as asName") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_desig")
	|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_close_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_date") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh8.action_by")
	)
	{
	List datalist=null;
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_time as actTime")|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.level")|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_by"))
	{
	
	datalist=getEscData(connection,"0",object[0].toString());
	if(datalist!=null && datalist.size()>0)
	{
	for(Iterator itr = datalist.iterator(); itr.hasNext();) 
	  {
	Object[] obj = (Object[]) itr.next();
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_date as actDate"))
	{
	if(obj[0]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(DateUtil.convertDateToIndianFormat(obj[0].toString()));
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_time as actTime"))
	{
	if(obj[1]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[1].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.level"))
	{
	if(obj[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	if(obj[3]!=null && !obj[3].toString().trim().equalsIgnoreCase(""))
	{
	cell.setCellValue(obj[2].toString()+"("+obj[3].toString()+")");
	}
	else{
	cell.setCellValue(obj[2].toString()+"(NA)");	
	}
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.comments"))
	{
	if(obj[4]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[4].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_by"))
	{
	if(obj[5]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[5].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	  }
	   }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_time as actTime")|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.level")|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_by"))
	{
	datalist=getEscData(connection,"1",object[0].toString());
	if(datalist!=null && datalist.size()>0)
	{
	for(Iterator itr = datalist.iterator(); itr.hasNext();) 
	  {
	Object[] obj = (Object[]) itr.next();
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_date as actDate"))
	{
	if(obj[0]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(DateUtil.convertDateToIndianFormat(obj[0].toString()));
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_time as actTime"))
	{
	if(obj[1]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[1].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.level"))
	{
	if(obj[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	if(obj[3]!=null && !obj[3].toString().trim().equalsIgnoreCase(""))
	{
	cell.setCellValue(obj[2].toString()+"("+obj[3].toString()+")");
	}
	else{
	cell.setCellValue(obj[2].toString()+"(NA)");	
	}
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.comments"))
	{
	if(obj[4]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[4].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_by"))
	{
	if(obj[5]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[5].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	  }
	   }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_time as actTime")|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.level")|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_by"))
	{
	datalist=getEscData(connection,"2",object[0].toString());
	if(datalist!=null && datalist.size()>0)
	{
	for(Iterator itr = datalist.iterator(); itr.hasNext();) 
	  {
	Object[] obj = (Object[]) itr.next();
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_date as actDate"))
	{
	if(obj[0]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(DateUtil.convertDateToIndianFormat(obj[0].toString()));
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_time as actTime"))
	{
	if(obj[1]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[1].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.level"))
	{
	if(obj[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	if(obj[3]!=null && !obj[3].toString().trim().equalsIgnoreCase(""))
	{
	cell.setCellValue(obj[2].toString()+"("+obj[3].toString()+")");
	}
	else{
	cell.setCellValue(obj[2].toString()+"(NA)");	
	}
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.comments"))
	{
	if(obj[4]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[4].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_by"))
	{
	if(obj[5]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[5].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	  }
	   }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_time as actTime")|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.level")|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_by"))
	{
	datalist=getEscData(connection,"3",object[0].toString());
	if(datalist!=null && datalist.size()>0)
	{
	for(Iterator itr = datalist.iterator(); itr.hasNext();) 
	  {
	Object[] obj = (Object[]) itr.next();
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_date as actDate"))
	{
	if(obj[0]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(DateUtil.convertDateToIndianFormat(obj[0].toString()));
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_time as actTime"))
	{
	if(obj[1]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[1].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.level"))
	{
	if(obj[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	if(obj[3]!=null && !obj[3].toString().trim().equalsIgnoreCase(""))
	{
	cell.setCellValue(obj[2].toString()+"("+obj[3].toString()+")");
	}
	else{
	cell.setCellValue(obj[2].toString()+"(NA)");	
	}
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.comments"))
	{
	if(obj[4]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[4].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_by"))
	{
	if(obj[5]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[5].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	  }
	   }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_date as actDate") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_time as actTime")|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.level")|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.comments") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_by"))
	{
	datalist=getEscData(connection,"4",object[0].toString());
	if(datalist!=null && datalist.size()>0)
	{
	for(Iterator itr = datalist.iterator(); itr.hasNext();) 
	  {
	Object[] obj = (Object[]) itr.next();
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_date as actDate"))
	{
	if(obj[0]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(DateUtil.convertDateToIndianFormat(obj[0].toString()));
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_time as actTime"))
	{
	if(obj[1]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[1].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.level"))
	{
	if(obj[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	if(obj[3]!=null && !obj[3].toString().trim().equalsIgnoreCase(""))
	{
	cell.setCellValue(obj[2].toString()+"("+obj[3].toString()+")");
	}
	else{
	cell.setCellValue(obj[2].toString()+"(NA)");	
	}
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.comments"))
	{
	if(obj[4]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[4].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_by"))
	{
	if(obj[5]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[5].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	  }
	   }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.status as no_response"))
	{

	datalist=getNoResData(connection,"4",object[0].toString());
	if(datalist!=null && datalist.size()>0)
	{
	for(Iterator itr = datalist.iterator(); itr.hasNext();) 
	  {
	Object[] obj = (Object[]) itr.next();
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.status as no_response"))
	{
	if(obj[0]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(DateUtil.convertDateToIndianFormat(obj[0].toString()));
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	  }
	   }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.resolution_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_id as ID") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_name as asName") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_desig")
	|| keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_close_by") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_date") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh8.action_by"))
	{
	//System.out.println(actiondate+"**************************"+actiontime);
	//getSeenData
	if(object[28]!=null)
	{
	if(object[28].toString().equalsIgnoreCase("Seen"))
	{
	datalist=getSeenData(connection,object[0].toString());
	if(datalist!=null && datalist.size()>0)
	{
	
	for(Iterator itr = datalist.iterator(); itr.hasNext();) 
	  {
	Object[] obj = (Object[]) itr.next();
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_id as ID"))
	{
	if(obj[0]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[0].toString());
	cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_name as asName"))
	{
	if(obj[1]!=null)
	{
	 Cell cell=rowData.createCell((int) cellIndex);
	 cell.setCellValue(obj[1].toString());
	 cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_desig"))
	{
	if(obj[2]!=null)
	{
	 Cell cell=rowData.createCell((int) cellIndex);
	 cell.setCellValue(obj[2].toString());
	 cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_close_by"))
	{
	if(obj[3]!=null)
	{
	 Cell cell=rowData.createCell((int) cellIndex);
	 cell.setCellValue(obj[3].toString());
	 cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_date"))
	{
	if(obj[4]!=null)
	{
	  calendar.set(Integer.parseInt(obj[4].toString().split("-")[0]),DateUtil.getMonthFromDay(obj[4].toString().split("-")[1]),Integer.parseInt(obj[4].toString().split("-")[2]));
	  Cell cell=rowData.createCell((int) cellIndex);
	  cell.setCellValue(calendar);
	  actiondate=obj[4].toString();
	  cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_time"))
	{
	if(obj[5]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[5].toString());
	cell.setCellStyle(cellStyle);
	actiontime=obj[5].toString();
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh8.action_by"))
	{
	if(obj[6]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[6].toString());
	cell.setCellStyle(cellStyle);
	actiontime=obj[6].toString();
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.resolution_time"))
	{
	datalist=getInformedData(connection,object[0].toString());
	if(datalist!=null && datalist.size()>0)
	{
	for(Iterator itr1 = datalist.iterator(); itr1.hasNext();) 
	  {
	Object[] obj1 = (Object[]) itr1.next();
	if(obj1[0]!=null && obj1[1]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(DateUtil.time_difference(obj1[0].toString(),obj1[1].toString(),obj[4].toString(),obj[5].toString()));
	cell.setCellStyle(cellStyle);
	actiontime=obj[5].toString();
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	  }
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	         }
	  }
	}
	}
	else{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	
	}
	else{
	if(!keyList.get(cellIndex).toString().equalsIgnoreCase("refdata.id") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_date as actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_date as actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_date as actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_date as actDate") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_date as actDate")
	&& !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_time as actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_time as actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_time as actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_time as actTime") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_time as actTime")	
	&& !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.level") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.level") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.level") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.level") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.level")
	&& !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.comments") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.comments") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.comments") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.comments") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.comments")
	&& !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.action_by") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh2.action_by") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh3.action_by") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh4.action_by") && !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh5.action_by")
	|| !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.resolution_time") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_id as ID") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_to_name as asName") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_desig")
	|| !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.status as no_response") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.assign_close_by") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_date") || !keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.sn_upto_time")
	)
	{
	      //System.out.println(keyList.get(cellIndex).toString());  
	cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));
	if(object[cellIndex]!=null && object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.assign_to_id") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.assign_to_name")  || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_by"))
	{
	//	System.out.println(object[cellIndex].toString());
	//	System.out.println(object[cellIndex].toString().split("-")[1]);
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_date") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_time") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.assign_to_id") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.assign_to_name") || keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_by"))
	{
	if(object[28]!=null)
	{
	List datalist=getInformedData(connection,object[0].toString());
	if(datalist!=null && datalist.size()>0)
	{
	for(Iterator itr = datalist.iterator(); itr.hasNext();) 
	  {
	Object[] obj = (Object[]) itr.next();
	if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_date"))
	{
	if(obj[0]!=null)
	{
	  calendar.set(Integer.parseInt(obj[0].toString().split("-")[0]),DateUtil.getMonthFromDay(obj[0].toString().split("-")[1]),Integer.parseInt(obj[0].toString().split("-")[2]));
	  Cell cell=rowData.createCell((int) cellIndex);
	  cell.setCellValue(calendar);
	  actiondate=obj[0].toString();
	  cell.setCellStyle(cellStyle);
	    }
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_time"))
	{
	if(obj[1]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[1].toString());
	cell.setCellStyle(cellStyle);
	actiontime=obj[1].toString();
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.assign_to_id"))
	{
	if(obj[2]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[2].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh1.assign_to_name"))
	{
	if(obj[3]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[3].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	
	else if(keyList.get(cellIndex).toString().equalsIgnoreCase("rdh.action_by"))
	{
	if(obj[4]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(obj[4].toString());
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	  }
	}
	   }
	else{
	if(object[cellIndex].toString().matches("\\d{4}-[01]\\d-[0-3]\\d"))
	{
	if(object[cellIndex]!=null)
	{
	calendar.set(Integer.parseInt(object[cellIndex].toString().split("-")[0]),DateUtil.getMonthFromDay(object[cellIndex].toString().split("-")[1]),Integer.parseInt(object[cellIndex].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue( calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	else{
	if(object[cellIndex]!=null)
	{
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(object[cellIndex].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	}	
	else{
	if(object[cellIndex]!=null)
	{
	calendar.set(Integer.parseInt(object[cellIndex].toString().split("-")[0]),DateUtil.getMonthFromDay(object[cellIndex].toString().split("-")[1]),Integer.parseInt(object[cellIndex].toString().split("-")[2]));
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue( calendar);
	cell.setCellStyle(cellStyle);
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	else
	{
	if(object[cellIndex]!=null)
	{
	//System.out.println(cellIndex+"    "+object[cellIndex].toString());
	Cell cell=rowData.createCell((int) cellIndex);
	cell.setCellValue(object[cellIndex].toString());	
	}
	else
	{
	rowData.createCell((int) cellIndex).setCellValue("NA");
	}
	}
	}
	}
	}
	sheet.autoSizeColumn(index);
	index++;	
	}
	}
	
	
	try
	{
	FileOutputStream out = new FileOutputStream(new File(excelFileName));
	        wb.write(out);
	        out.close();
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return excelFileName;
	}
	public List getNoResData(SessionFactory conn,String limit,String id)
	{
	String str="Select rdh1.status,rdh1.id from referral_data_history as rdh1 INNER JOIN employee_basic AS emp ON emp.id=rdh1.action_by  where rdh1.rid='"+id+"' and rdh1.status='No Response' order by rdh1.id asc ";
	//System.out.println(str);
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	List data4escalate = cbt.executeAllSelectQuery(str,conn);
	cbt=null;
	return data4escalate;
	}
	public List getEscData(SessionFactory conn,String limit,String id)
	{
	String str="Select rdh1.action_date,rdh1.action_time,rdh1.escalate_to,rdh1.escalate_to_mob,rdh1.comments,emp.empName from referral_data_history as rdh1 INNER JOIN employee_basic AS emp ON emp.id=rdh1.action_by  where rdh1.rid='"+id+"' and rdh1.status='Escalate-D' order by rdh1.id asc limit "+limit+",1";
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	List data4escalate = cbt.executeAllSelectQuery(str,conn);
	cbt=null;
	return data4escalate;
	}
	
	public List getSeenData(SessionFactory conn,String id)
	{
	String str=" Select his.assign_to_id,his.assign_to_name,his.assign_desig,his.assign_close_by,his.action_date, his.action_time, emp.empName  from referral_data_history as his  inner join employee_basic as emp on emp.id = his.action_by where his.rid='"+id+"' and his.status='Seen' order by his.id asc  ";
	//System.out.println(str);
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	List data4escalate = cbt.executeAllSelectQuery(str,conn);
	cbt=null;
	return data4escalate;
	}
	
	public List getInformedData(SessionFactory conn,String id)
	{
	String str="Select his_ref.action_date,his_ref.action_time,his_ref.assign_to_id,his_ref.assign_to_name, emp.empName from referral_data_history as his_ref inner join employee_basic as emp on emp.id=his_ref.action_by where his_ref.rid='"+id+"' and his_ref.status='Informed'";
	CommonOperInterface cbt = new CommonConFactory().createInterface();
	List data4escalate = cbt.executeAllSelectQuery(str,conn);
	cbt=null;
	return data4escalate;	
	}
	
	@SuppressWarnings("unused")
	private Map<String, CellStyle> createStyles(Workbook wb) {
	Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
	CellStyle style;

	// Title Style
	Font titleFont = wb.createFont();
	titleFont.setFontHeightInPoints((short) 16);
	titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(titleFont);
	style.setWrapText(true);
	styles.put("title", style);

	// Sub Title Style
	Font subTitleFont = wb.createFont();
	subTitleFont.setFontHeightInPoints((short) 14);
	subTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.WHITE.index);
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(subTitleFont);
	style.setWrapText(true);
	styles.put("subTitle", style);
	
	// Sub Title Style for Pending Tickets
	Font subTitleFont_PN = wb.createFont();
	subTitleFont_PN.setFontHeightInPoints((short) 14);
	subTitleFont_PN.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.RED.index);
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(subTitleFont_PN);
	style.setWrapText(true);
	styles.put("subTitle_PN", style);
	
	// Sub Title Style for Resolved Tickets
	Font subTitleFont_RS = wb.createFont();
	subTitleFont_RS.setFontHeightInPoints((short) 14);
	subTitleFont_RS.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.GREEN.index);
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(subTitleFont_RS);
	style.setWrapText(true);
	styles.put("subTitle_RS", style);
	
	// Sub Title Style for High Priority Tickets
	Font subTitleFont_HP = wb.createFont();
	subTitleFont_HP.setFontHeightInPoints((short) 14);
	subTitleFont_HP.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.ORANGE.index);
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(subTitleFont_HP);
	style.setWrapText(true);
	styles.put("subTitle_HP", style);
	
	// Sub Title Style for High Priority Tickets
	Font subTitleFont_SN = wb.createFont();
	subTitleFont_SN.setFontHeightInPoints((short) 14);
	subTitleFont_SN.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.YELLOW.index);
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(subTitleFont_SN);
	style.setWrapText(true);
	styles.put("subTitle_SN", style);
	
	// Sub Title Style for High Priority Tickets
	Font subTitleFont_IG = wb.createFont();
	subTitleFont_IG.setFontHeightInPoints((short) 14);
	subTitleFont_IG.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.LAVENDER.index);
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(subTitleFont_IG);
	style.setWrapText(true);
	styles.put("subTitle_IG", style);
	
	// Sub Title Style for High Priority Tickets
	Font subTitleFont_SH = wb.createFont();
	subTitleFont_SH.setFontHeightInPoints((short) 10);
	subTitleFont_SH.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFont(subTitleFont_SH);
	style.setWrapText(true);
	styles.put("subTitle_SH", style);

	Font headerFont = wb.createFont();
	headerFont.setFontHeightInPoints((short) 11);
	headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(headerFont);
	styles.put("header", style);

	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_LEFT);
	style.setWrapText(true);
	style.setBorderRight(CellStyle.BORDER_THIN);
	style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	style.setBorderLeft(CellStyle.BORDER_THIN);
	style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	style.setBorderTop(CellStyle.BORDER_THIN);
	style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	style.setBorderBottom(CellStyle.BORDER_THIN);
	style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	styles.put("cell", style);
	
	
	
	// mytittle
	Font headerFont22 = wb.createFont();
	headerFont.setFontHeightInPoints((short) 11);
	headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	style = wb.createCellStyle();
	style.setAlignment(CellStyle.ALIGN_CENTER);
	style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	style.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
	style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	style.setFont(headerFont);
	styles.put("mytittle", style);
	
	return styles;
	}
 
	
	@SuppressWarnings("unchecked")
	public List getDataForRoasterApply(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, List>wherClauseList,Map<String, Object> order,String searchField,String searchString,String searchOper,String viewtype,SessionFactory connection)
	  {
	List Data=null;
	StringBuilder selectTableData = new StringBuilder("");  
	selectTableData.append("select ");
	if(colmName!=null && !colmName.equals("") && !colmName.isEmpty())
	  {
	int i=1;
	for(String H:colmName)
	 {
	if(i<colmName.size())
	selectTableData.append(H+" ,");
	else
	selectTableData.append(H+" from " +tableName+"  as roaster");
	i++;
	 }
	 }
	// Here we get the whole data of a table
	else
	 {
	selectTableData.append("* from "+tableName+" as roaster");
	 }
	selectTableData.append(" inner join compliance_contacts as comp on comp.id=roaster.contactId ");
	if (viewtype!=null && !viewtype.equals("") && viewtype.equals("SD")) {
	selectTableData.append(" inner join subdepartment on roaster.dept_subdept=subdepartment.id ");
	selectTableData.append(" inner join department on subdepartment.deptid=department.id ");
	}
	else if (viewtype!=null && !viewtype.equals("") && viewtype.equals("D")) {
	selectTableData.append(" inner join department on roaster.dept_subdept=department.id ");
	}
	selectTableData.append(" inner join employee_basic on employee_basic.id=comp.emp_id "); 
	selectTableData.append(" left join floor_detail as flr on flr.id=roaster.floor "); 

	// Set the values for where clause
	if(wherClause!=null && !wherClause.isEmpty())
	   {
	 if(wherClause.size()>0)
	{
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClause.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(size<wherClause.size())
	selectTableData.append((String)me.getKey()+" = "+me.getValue()+" and ");
	else
	selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
	size++;
	} 
	}
	
	// Set the values for where clause List
	if(wherClauseList!=null && !wherClauseList.isEmpty())
	   {
	 if (wherClause!=null) {
	 selectTableData.append(" and ");
	 }
	 else
	    {
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClauseList.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(me!=null && (String)me.getKey()!=null && me.getValue()!=null)
	{
	if(size<wherClauseList.size())
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
	else
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+"");
	size++;
	}
	} 
	}
	
	 if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	{
	 selectTableData.append(" and");
     	  
     	if(searchOper.equalsIgnoreCase("eq"))
	{
     	 selectTableData.append(" "+searchField+" = '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("cn"))
	{
	 selectTableData.append(" "+searchField+" like '%"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("bw"))
	{
	 selectTableData.append(" "+searchField+" like '"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("ne"))
	{
	selectTableData.append(" "+searchField+" <> '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("ew"))
	{
	selectTableData.append(" "+searchField+" like '%"+searchString+"'");
	}
	}
	
	 // Set the value for type of order for getting data in specific order
	 if(order!=null && !order.isEmpty())
	   {
	Set set = order.entrySet();
	Iterator it = set.iterator();
	while (it.hasNext()) {
	Map.Entry me = (Map.Entry)it.next();
	selectTableData.append(" ORDER BY "+me.getKey()+" "+me.getValue()+"");
	    }
	   }
	//System.out.println("Roaster ViewQry::"+selectTableData);
	selectTableData.append(";");
	Session session = null;  
	Transaction transaction = null;  
	try {  
	            session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(selectTableData.toString()).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	return Data;
	}
	 
	@SuppressWarnings("unchecked")
	public List getDataFromTable(String tableName, List<String> colmName,Map<String, Object>wherClause,Map<String, List>wherClauseList,Map<String, Object> order,String searchField,String searchString,String searchOper,Session session)
	  {
	List Data=null;
	StringBuilder selectTableData = new StringBuilder("");  
	selectTableData.append("select ");
	
	// Set the columns name of a table
	if(colmName!=null && !colmName.equals("") && !colmName.isEmpty())
	  {
	int i=1;
	for(String H:colmName)
	 {
	if(i<colmName.size())
	selectTableData.append(H+" ,");
	else
	selectTableData.append(H+" from " +tableName);
	i++;
	 }
	 }
	
	// Here we get the whole data of a table
	else
	 {
	selectTableData.append("* from " +tableName);
	 }
	    
	// Set the values for where clause
	if(wherClause!=null && !wherClause.isEmpty())
	   {
	 if(wherClause.size()>0)
	{
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClause.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(size<wherClause.size())
	selectTableData.append((String)me.getKey()+" = "+me.getValue()+" and ");
	else
	selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
	size++;
	} 
	}
	
	// Set the values for where clause List
	if(wherClauseList!=null && !wherClauseList.isEmpty())
	   {
	 if (wherClause!=null) {
	 selectTableData.append(" and ");
	 }
	 else
	    {
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClauseList.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(me!=null && (String)me.getKey()!=null && me.getValue()!=null)
	{
	if(size<wherClauseList.size())
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
	else
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+"");
	size++;
	}
	} 
	}
	
	 if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	{
	 selectTableData.append(" and");
     	  
     	if(searchOper.equalsIgnoreCase("eq"))
	{
     	 selectTableData.append(" "+searchField+" = '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("cn"))
	{
	 selectTableData.append(" "+searchField+" like '%"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("bw"))
	{
	 selectTableData.append(" "+searchField+" like '"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("ne"))
	{
	selectTableData.append(" "+searchField+" <> '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("ew"))
	{
	selectTableData.append(" "+searchField+" like '%"+searchString+"'");
	}
	}
	
	 // Set the value for type of order for getting data in specific order
	 if(order!=null && !order.isEmpty())
	   {
	Set set = order.entrySet();
	Iterator it = set.iterator();
	while (it.hasNext()) {
	Map.Entry me = (Map.Entry)it.next();
	selectTableData.append(" ORDER BY "+me.getKey()+" "+me.getValue()+"");
	    }
	   }
	selectTableData.append(";");
	Transaction transaction = null;  
	try {  
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(selectTableData.toString()).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	return Data;
	}
	
	@SuppressWarnings("unchecked")
	public List getData(String query, SessionFactory connection)
	  {
	List Data=null;
	Session session=null;
	Transaction transaction = null;  
	try {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(query).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	    ex.printStackTrace();
	transaction.rollback();
	 } 
	if(Data!=null)
	{
	}
	else
	{
	}
	return Data;
	}
	
	public boolean updateData(String query, SessionFactory connection)
	  {
	boolean flag=false;
	int count;
	Session session=null;
	Transaction transaction = null;  
	try {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	count=session.createSQLQuery(query).executeUpdate();  
	if (count>0) {
	flag=true;
	}
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	return flag;
	}
	
	@SuppressWarnings("unchecked")
	public List getEscalationData(Session session)
	  {
	List Data=null;
	String str = "select id ,ticket_no ,feed_by ,deptHierarchy ,feed_brief ,allot_to ,location ,subCatg ,sn_upto_date ,sn_upto_time from feedback_status where status='Snooze' and sn_upto_date<='"+DateUtil.getCurrentDateUSFormat()+"'";
	Transaction transaction = null;  
	try {  
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(str).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	return Data;
	}
	
	@SuppressWarnings("unchecked")
	public List getTransferTicketData(String id, SessionFactory connection)
	  {
	List Data=null;
	String str = "select location,to_dept_subdept,subCatg,allot_to,open_date,open_time from feedback_status_new where id='"+id+"'";
	//System.out.println("**********"+str);
	Session session=null;
	Transaction transaction = null;  
	try {  
	    session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(str).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	return Data;
	}
	
	@SuppressWarnings("unchecked")
	public int getDataCountFromTable(String tableName,Map<String, Object>wherClause, Map<String, List> wherClauseList,SessionFactory connection)
	  {
	int countSize =0;
	List Data=null;
	StringBuilder selectTableData = new StringBuilder("");  

	selectTableData.append("select count(*) from " +tableName);
	    
	// Set the values for where clause
	if(wherClause!=null && !wherClause.isEmpty())
	   {
	 if(wherClause.size()>0)
	{
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClause.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(size<wherClause.size())
	selectTableData.append((String)me.getKey()+" = "+me.getValue()+" and ");
	else
	selectTableData.append((String)me.getKey()+" = '"+me.getValue()+"'");
	size++;
	} 
	}
	
	// Set the values for where clause
	if(wherClauseList!=null && !wherClauseList.isEmpty())
	   {
	 if (wherClause!=null) {
	 selectTableData.append(" and ");
	 }
	 else
	    {
	selectTableData.append(" where ");
	}
	int size=1;
	Set set =wherClauseList.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(size<wherClauseList.size())
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+" and ");
	else
	selectTableData.append((String)me.getKey()+" in "+me.getValue().toString().replace("[", "(").replace("]", ")")+"");
	size++;
	} 
	}
	selectTableData.append(";");
	Session session = null;  
	Transaction transaction = null;  
	try {  
	            session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	Data=session.createSQLQuery(selectTableData.toString()).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	if (Data!=null && Data.size()>0) {
	countSize  = Integer.parseInt(Data.get(0).toString());
	}
	return countSize;
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateTableColomn(String tableName,Map<String, Object>parameterClause,Map<String, Object>condtnBlock,SessionFactory connection)
	 {
	boolean Data=false;
	StringBuilder selectTableData = new StringBuilder("");  
	
	selectTableData.append("update " + tableName+" set ");
	int size=1;
	Set set =parameterClause.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(size<parameterClause.size())
	selectTableData.append(me.getKey()+" = '"+me.getValue()+"' , ");
	else
	selectTableData.append(me.getKey()+" = '"+me.getValue()+"'");
	size++;
	} 
	
	if(condtnBlock.size()>0)
	{
	selectTableData.append(" where ");
	}
	size=1;
	set =condtnBlock.entrySet(); 
	i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(size<condtnBlock.size())
	selectTableData.append(me.getKey()+" = '"+me.getValue()+"' and ");
	else
	selectTableData.append(me.getKey()+" = '"+me.getValue()+"' ");
	size++;
	} 
	selectTableData.append(";");
	Session session = null;  
	Transaction transaction = null;  
	try {  
	            session=connection.getCurrentSession();
	transaction = session.beginTransaction();  
	int count=session.createSQLQuery(selectTableData.toString()).executeUpdate();
	if(count>0)
	Data=true;
	transaction.commit(); 
	}catch (Exception ex) {transaction.rollback();} 
	return Data;
	}
	
	@SuppressWarnings("unchecked")
	public boolean updateTableColomnWithSession(String tableName,Map<String, Object>parameterClause,Map<String, Object>condtnBlock)
	 {
	boolean Data=false;
	StringBuilder selectTableData = new StringBuilder("");  
	
	selectTableData.append("update " + tableName+" set ");
	int size=1;
	Set set =parameterClause.entrySet(); 
	Iterator i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(size<parameterClause.size())
	selectTableData.append(me.getKey()+" = '"+me.getValue()+"' , ");
	else
	selectTableData.append(me.getKey()+" = '"+me.getValue()+"'");
	size++;
	} 
	
	if(condtnBlock.size()>0)
	{
	selectTableData.append(" where ");
	}
	size=1;
	set =condtnBlock.entrySet(); 
	i = set.iterator();
	while(i.hasNext())
	{ 
	Map.Entry me = (Map.Entry)i.next();
	if(size<condtnBlock.size())
	selectTableData.append(me.getKey()+" = '"+me.getValue()+"' and ");
	else
	selectTableData.append(me.getKey()+" = '"+me.getValue()+"'");
	size++;
	} 
	selectTableData.append(";");
	
	Transaction transaction = null;  
	try {  
	    Session session = HibernateSessionFactory.getSession();
	transaction = session.beginTransaction();  
	int count=session.createSQLQuery(selectTableData.toString()).executeUpdate();
	if(count>0)
	Data=true;
	transaction.commit(); 
	}catch (Exception ex) {transaction.rollback();} 
	return Data;
	}
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setFeedbackValues(List feedValue, String deptLevel, String feedStatus)
	  {
	

	List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
	if(feedValue!=null && feedValue.size()>0) 
	 {
	for (Iterator iterator = feedValue.iterator(); iterator.hasNext();) {
	Object[] obdata = (Object[]) iterator.next();
	 FeedbackPojo  fbp = new FeedbackPojo();
	 fbp.setId((Integer)obdata[0]);
	 fbp.setTicket_no(obdata[1].toString());
	 fbp.setFeedback_by_dept(obdata[9].toString());
	
	if (obdata[10]!=null && !obdata[10].toString().equals("")) {
	fbp.setFeed_by(DateUtil.makeTitle(obdata[10].toString()));
	}
	else {
	 fbp.setFeed_by("NA");
	}
	 
	if (obdata[11]!=null && !obdata[11].toString().equals("") && (obdata[11].toString().startsWith("9") || obdata[11].toString().startsWith("8") || obdata[11].toString().startsWith("7")) && obdata[11].toString().length()==10) {
	fbp.setFeedback_by_mobno(obdata[11].toString());
	    }
	else {
	 fbp.setFeedback_by_mobno("NA");
	}
	
	if (obdata[12]!=null && !obdata[12].toString().equals("")) {
	fbp.setFeedback_by_emailid(obdata[12].toString());
	        }
	    else {
	    	fbp.setFeedback_by_emailid("NA");
	    }
	 
	fbp.setFeedback_to_dept(obdata[2].toString());
	fbp.setFeedback_to_subdept(obdata[3].toString());
	fbp.setFeedback_allot_to(DateUtil.makeTitle(obdata[4].toString()));
	fbp.setFeedtype(obdata[5].toString());
	fbp.setFeedback_catg(obdata[5].toString());
	fbp.setFeedback_subcatg(obdata[6].toString());
	if (obdata[7]!=null && !obdata[7].toString().equals("")) {
	fbp.setFeed_brief(obdata[7].toString());
	        }
	    else {
	    	fbp.setFeed_brief("NA");
	    }
	
	
	
	if (obdata[8]!=null && !obdata[8].toString().equals("")) {
	fbp.setLocation(obdata[8].toString());
	        }
	    else {
	    	fbp.setLocation("NA");
	    }
	fbp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[13].toString()));
	fbp.setOpen_time(obdata[14].toString().substring(0, 6));
	if (obdata[15]!=null && !obdata[15].toString().equals("")) {
	 fbp.setAddressingTime(DateUtil.convertDateToIndianFormat(obdata[15].toString().substring(0, 10))+" / "+ obdata[15].toString().substring(11, 16));
	 }
	 else {
	 fbp.setAction_by("NA");
	 } 
	fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[16].toString())+" / "+obdata[17].toString());
	  
	fbp.setLevel(obdata[18].toString());
	fbp.setStatus(obdata[19].toString());
	/*if (obdata[20]!=null && !obdata[20].toString().equals("")) {
	fbp.setVia_from(DateUtil.makeTitle(obdata[20].toString()));
	}
	else {
	fbp.setVia_from("NA");
	}*/
	
	fbp.setFeed_registerby(DateUtil.makeTitle(obdata[20].toString()));
	
	 if (feedStatus.equalsIgnoreCase("High Priority")) {
	 fbp.setHp_date(DateUtil.convertDateToIndianFormat(obdata[22].toString()));
	 fbp.setHp_time(obdata[23].toString().substring(0, 5));
	 fbp.setHp_reason(obdata[24].toString());
	}
	
	 if (feedStatus.equalsIgnoreCase("Snooze")) {
	 if (obdata[25]!=null && !obdata[25].toString().equals("")) {
	 fbp.setSn_reason(obdata[25].toString());
	}
	 else {
	 fbp.setSn_reason("NA");
	}
	 if (obdata[26]!=null && !obdata[26].toString().equals("")) {
	 fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(obdata[26].toString()));
	}
	 else {
	 fbp.setSn_on_date("NA");
	}
	 if (obdata[27]!=null && !obdata[27].toString().equals("")) {
	 fbp.setSn_on_time(obdata[27].toString().toString().substring(0, 5));
	}
	 else {
	 fbp.setSn_on_time("NA");
	}
	 if (obdata[28]!=null && !obdata[28].toString().equals("")) {
	 fbp.setSn_upto_date(DateUtil.convertDateToIndianFormat(obdata[28].toString()));
	}
	 else {
	 fbp.setSn_upto_date("NA");
	}
	 if (obdata[29]!=null && !obdata[29].toString().equals("")) {
	 fbp.setSn_upto_time(obdata[29].toString().substring(0, 5));
	}
	 else {
	 fbp.setSn_upto_time("NA");
	} 
	 if (obdata[30]!=null && !obdata[30].toString().equals("")) {
	 fbp.setSn_duration(obdata[30].toString());
	}
	 else {
	 fbp.setSn_duration("NA");
	}  
	}
	 /*
	 if (obdata[31]!=null && !obdata[31].toString().equals("")) {
	 fbp.setIg_date(obdata[31].toString());
	 }
	 else {
	 fbp.setIg_date("NA");
	 } 
	 if (obdata[32]!=null && !obdata[32].toString().equals("")) {
	 fbp.setIg_time(obdata[32].toString());
	 }
	 else {
	 fbp.setIg_time("NA");
	 } 
	 
	 if (obdata[33]!=null && !obdata[33].toString().equals("")) {
	 fbp.setIg_reason(obdata[33].toString());
	 }
	 else {
	 fbp.setIg_reason("NA");
	 } 
	 
	 if (obdata[34]!=null && !obdata[34].toString().equals("")) {
	 fbp.setTransfer_date(obdata[34].toString());
	 }
	 else {
	 fbp.setTransfer_date("NA");
	 } 
	 
	 if (obdata[35]!=null && !obdata[35].toString().equals("")) {
	 fbp.setTransfer_time(obdata[35].toString());
	 }
	 else {
	 fbp.setTransfer_time("NA");
	 } 
	
	 if (obdata[36]!=null && !obdata[36].toString().equals("")) {
	 fbp.setTransfer_reason(obdata[36].toString());
	 }
	 else {
	 fbp.setTransfer_reason("NA");
	 } 
	
	 if (obdata[37]!=null && !obdata[37].toString().equals("")) {
	 fbp.setAction_by(DateUtil.makeTitle(obdata[37].toString()));
	 }
	 else {
	 fbp.setAction_by("NA");
	 } 
	
	 if (obdata[38]!=null && !obdata[38].toString().equals("")) {
	 fbp.setAddressingTime(DateUtil.convertDateToIndianFormat(obdata[38].toString().substring(0, 10))+" / "+ obdata[38].toString().substring(11, 16));
	 }
	 else {
	 fbp.setAction_by("NA");
	 } 
	 
	 if (feedStatus.equalsIgnoreCase("Resolved")) {
	 if (obdata[39]!=null && !obdata[39].toString().equals("")) {
	 fbp.setResolve_date(DateUtil.convertDateToIndianFormat(obdata[39].toString()));
	 }
	 else {
	 fbp.setResolve_date("NA");
	}
	if (obdata[40]!=null && !obdata[40].toString().equals("")) {
	 fbp.setResolve_time(obdata[40].toString().substring(0, 5));
	}
	else {
	 fbp.setResolve_time("NA");
	 }
	
	 if (obdata[41]!=null && !obdata[41].equals("")) {
	 fbp.setResolve_duration(obdata[41].toString());
	  }
	 else {
	   if (obdata[17]!=null && !obdata[17].toString().equals("") && obdata[18]!=null && !obdata[18].toString().equals("") && obdata[40]!=null && !obdata[40].toString().equals("") && obdata[41]!=null && !obdata[41].toString().equals("")) {
	   String dura1=DateUtil.time_difference(obdata[17].toString(), obdata[18].toString(), obdata[41].toString(), obdata[42].toString());
	   fbp.setResolve_duration(dura1);
	   }
	   else {
	   fbp.setResolve_duration("NA");
	   }
	   fbp.setResolve_duration("NA");
	 }
	 
	 if (obdata[41]!=null && !obdata[42].toString().equals("")) {
	 fbp.setResolve_remark(obdata[42].toString());
	}
	 else {
	 fbp.setResolve_remark("NA");
	}
	 fbp.setResolve_by(DateUtil.makeTitle(obdata[43].toString()));
	 fbp.setSpare_used(obdata[44].toString());
	}*/
	feedList.add(fbp);
	}
	}
	return feedList;
	 
	/*
	List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
	if(feedValue!=null && feedValue.size()>0) 
	 {
	for (Iterator iterator = feedValue.iterator(); iterator.hasNext();) {
	Object[] obdata = (Object[]) iterator.next();
	 FeedbackPojo  fbp = new FeedbackPojo();
	 fbp.setId((Integer)obdata[0]);
	 fbp.setTicket_no(obdata[1].toString());
	 fbp.setFeedback_by_dept(obdata[2].toString());
	
	if (obdata[3]!=null && !obdata[3].toString().equals("")) {
	fbp.setFeed_by(DateUtil.makeTitle(obdata[3].toString()));
	}
	else {
	 fbp.setFeed_by("NA");
	}
	 
	if (obdata[4]!=null && !obdata[4].toString().equals("") && (obdata[4].toString().startsWith("9") || obdata[4].toString().startsWith("8") || obdata[4].toString().startsWith("7")) && obdata[4].toString().length()==10) {
	fbp.setFeedback_by_mobno(obdata[4].toString());
	    }
	else {
	 fbp.setFeedback_by_mobno("NA");
	}
	
	if (obdata[5]!=null && !obdata[5].toString().equals("")) {
	fbp.setFeedback_by_emailid(obdata[5].toString());
	        }
	    else {
	    	fbp.setFeedback_by_emailid("NA");
	    }
	 
	fbp.setFeedback_to_dept(obdata[6].toString());
	fbp.setFeedback_to_subdept(obdata[7].toString());
	fbp.setFeedback_allot_to(DateUtil.makeTitle(obdata[8].toString()));
	fbp.setFeedtype(obdata[9].toString());
	fbp.setFeedback_catg(obdata[10].toString());
	fbp.setFeedback_subcatg(obdata[11].toString());
	if (obdata[12]!=null && !obdata[12].toString().equals("")) {
	fbp.setFeed_brief(obdata[12].toString());
	        }
	    else {
	    	fbp.setFeed_brief("NA");
	    }
	
	
	
	if (obdata[13]!=null && !obdata[13].toString().equals("")) {
	fbp.setLocation(obdata[13].toString());
	        }
	    else {
	    	fbp.setLocation("NA");
	    }
	fbp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[14].toString()));
	fbp.setOpen_time(obdata[15].toString().substring(0, 5));
	
	
	if (feedStatus.equalsIgnoreCase("Pending") || feedStatus.equalsIgnoreCase("High Priority")) {
	fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[16].toString())+" / "+obdata[17].toString());
	}
	
	  
	fbp.setLevel(obdata[18].toString());
	fbp.setStatus(obdata[19].toString());
	if (obdata[20]!=null && !obdata[20].toString().equals("")) {
	fbp.setVia_from(DateUtil.makeTitle(obdata[20].toString()));
	}
	else {
	fbp.setVia_from("NA");
	}
	
	fbp.setFeed_registerby(DateUtil.makeTitle(obdata[21].toString()));
	
	 if (feedStatus.equalsIgnoreCase("High Priority")) {
	 fbp.setHp_date(DateUtil.convertDateToIndianFormat(obdata[22].toString()));
	 fbp.setHp_time(obdata[23].toString().substring(0, 5));
	 fbp.setHp_reason(obdata[24].toString());
	}
	
	 if (feedStatus.equalsIgnoreCase("Snooze")) {
	 if (obdata[25]!=null && !obdata[25].toString().equals("")) {
	 fbp.setSn_reason(obdata[25].toString());
	}
	 else {
	 fbp.setSn_reason("NA");
	}
	 if (obdata[26]!=null && !obdata[26].toString().equals("")) {
	 fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(obdata[26].toString()));
	}
	 else {
	 fbp.setSn_on_date("NA");
	}
	 if (obdata[27]!=null && !obdata[27].toString().equals("")) {
	 fbp.setSn_on_time(obdata[27].toString().toString().substring(0, 5));
	}
	 else {
	 fbp.setSn_on_time("NA");
	}
	 if (obdata[28]!=null && !obdata[28].toString().equals("")) {
	 fbp.setSn_upto_date(DateUtil.convertDateToIndianFormat(obdata[28].toString()));
	}
	 else {
	 fbp.setSn_upto_date("NA");
	}
	 if (obdata[29]!=null && !obdata[29].toString().equals("")) {
	 fbp.setSn_upto_time(obdata[29].toString().substring(0, 5));
	}
	 else {
	 fbp.setSn_upto_time("NA");
	} 
	 if (obdata[30]!=null && !obdata[30].toString().equals("")) {
	 fbp.setSn_duration(obdata[30].toString());
	}
	 else {
	 fbp.setSn_duration("NA");
	}  
	}
	 
	 if (obdata[31]!=null && !obdata[31].toString().equals("")) {
	 fbp.setIg_date(obdata[31].toString());
	 }
	 else {
	 fbp.setIg_date("NA");
	 } 
	 if (obdata[32]!=null && !obdata[32].toString().equals("")) {
	 fbp.setIg_time(obdata[32].toString());
	 }
	 else {
	 fbp.setIg_time("NA");
	 } 
	 
	 if (obdata[33]!=null && !obdata[33].toString().equals("")) {
	 fbp.setIg_reason(obdata[33].toString());
	 }
	 else {
	 fbp.setIg_reason("NA");
	 } 
	 
	 if (obdata[34]!=null && !obdata[34].toString().equals("")) {
	 fbp.setTransfer_date(obdata[34].toString());
	 }
	 else {
	 fbp.setTransfer_date("NA");
	 } 
	 
	 if (obdata[35]!=null && !obdata[35].toString().equals("")) {
	 fbp.setTransfer_time(obdata[35].toString());
	 }
	 else {
	 fbp.setTransfer_time("NA");
	 } 
	
	 if (obdata[36]!=null && !obdata[36].toString().equals("")) {
	 fbp.setTransfer_reason(obdata[36].toString());
	 }
	 else {
	 fbp.setTransfer_reason("NA");
	 } 
	
	 if (obdata[37]!=null && !obdata[37].toString().equals("")) {
	 fbp.setAction_by(DateUtil.makeTitle(obdata[37].toString()));
	 }
	 else {
	 fbp.setAction_by("NA");
	 } 
	
	 if (obdata[38]!=null && !obdata[38].toString().equals("")) {
	 fbp.setAddressingTime(DateUtil.convertDateToIndianFormat(obdata[38].toString().substring(0, 10))+" / "+ obdata[38].toString().substring(11, 16));
	 }
	 else {
	 fbp.setAction_by("NA");
	 } 
	 
	
	 if (feedStatus.equalsIgnoreCase("Resolved")) {
	 if (obdata[38]!=null && !obdata[38].toString().equals("")) {
	 fbp.setResolve_date(DateUtil.convertDateToIndianFormat(obdata[38].toString()));
	 }
	 else {
	 fbp.setResolve_date("NA");
	}
	if (obdata[39]!=null && !obdata[39].toString().equals("")) {
	 fbp.setResolve_time(obdata[39].toString().substring(0, 5));
	}
	else {
	 fbp.setResolve_time("NA");
	 }
	
	 if (obdata[40]!=null && !obdata[40].equals("")) {
	 fbp.setResolve_duration(obdata[40].toString());
	  }
	 else {
	   if (obdata[17]!=null && !obdata[17].toString().equals("") && obdata[18]!=null && !obdata[18].toString().equals("") && obdata[41]!=null && !obdata[41].toString().equals("") && obdata[42]!=null && !obdata[42].toString().equals("")) {
	   String dura1=DateUtil.time_difference(obdata[17].toString(), obdata[18].toString(), obdata[41].toString(), obdata[42].toString());
	   fbp.setResolve_duration(dura1);
	   }
	   else {
	   fbp.setResolve_duration("NA");
	   }
	   fbp.setResolve_duration("NA");
	 }
	 
	 if (obdata[41]!=null && !obdata[42].toString().equals("")) {
	 fbp.setResolve_remark(obdata[42].toString());
	}
	 else {
	 fbp.setResolve_remark("NA");
	}
	 fbp.setResolve_by(DateUtil.makeTitle(obdata[43].toString()));
	 fbp.setSpare_used(obdata[44].toString());
	}
	feedList.add(fbp);
	}
	}
	return feedList;
	 */}
	
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setCategoryDetail(List feedValue)
	 {
	List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
	if (feedValue!=null && feedValue.size()>0) {
	for (Iterator iterator = feedValue.iterator(); iterator
	.hasNext();) {
	Object[] obdata = (Object[]) iterator.next();
	FeedbackPojo  fbp = new FeedbackPojo();
	 fbp.setId((Integer)obdata[0]);
	 fbp.setTicket_no(obdata[1].toString());
	 fbp.setFeedback_by_dept(obdata[2].toString());
	 if (obdata[3]!=null && !obdata[3].toString().equals("")) {
	fbp.setFeed_by(DateUtil.makeTitle(obdata[3].toString()));
	 }
	 else {
	 fbp.setFeed_by("NA");
	 }
	 
	if (obdata[4]!=null && !obdata[4].toString().equals("") && (obdata[4].toString().startsWith("9") || obdata[4].toString().startsWith("8") || obdata[4].toString().startsWith("7")) && obdata[4].toString().length()==10) {
	fbp.setFeedback_by_mobno(obdata[4].toString());
	    }
	else {
	 fbp.setFeedback_by_mobno("NA");
	}
	
	if (obdata[5]!=null && !obdata[5].toString().equals("")) {
	fbp.setFeedback_by_emailid(obdata[5].toString());
	        }
	    else {
	    	fbp.setFeedback_by_emailid("NA");
	    }
	 
	fbp.setFeedback_to_dept(obdata[6].toString());
	fbp.setFeedback_to_subdept(obdata[7].toString());
	fbp.setFeedback_allot_to(obdata[8].toString());
	fbp.setFeedtype(obdata[9].toString());
	fbp.setFeedback_catg(obdata[10].toString());
	fbp.setFeedback_subcatg(obdata[11].toString());
	if (obdata[12]!=null && !obdata[12].toString().equals("")) {
	fbp.setFeed_brief(obdata[12].toString());
	        }
	    else {
	    	fbp.setFeed_brief("NA");
	    }
	
	if (obdata[13]!=null && !obdata[13].toString().equals("")) {
	fbp.setLocation(obdata[13].toString());
	        }
	    else {
	    	fbp.setLocation("NA");
	    }
	fbp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[14].toString()));
	fbp.setOpen_time(obdata[15].toString().substring(0, 5));
	fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[16].toString()));
	if (obdata[17].toString()!=null && !obdata[17].toString().equals("") && !obdata[17].toString().equals("NA")) {
	fbp.setEscalation_time(obdata[17].toString().substring(0, 5));
	}
	else {
	fbp.setEscalation_time("NA");
	}
	  
	fbp.setLevel(obdata[18].toString());
	fbp.setStatus(obdata[19].toString());
	if (obdata[20]!=null && !obdata[20].toString().equals("")) {
	fbp.setVia_from(DateUtil.makeTitle(obdata[20].toString()));
	}
	else {
	fbp.setVia_from("NA");
	}
	
	if (obdata[38]!=null && !obdata[38].toString().equals("")) {
	fbp.setFeedaddressing_time(DateUtil.convertDateToIndianFormat(obdata[38].toString().substring(0, 11)+" "+obdata[38].toString().substring(11, 16)));
	        }
	    else {
	    	fbp.setFeedaddressing_time("NA");
	    }
	
	fbp.setFeed_registerby(DateUtil.makeTitle(obdata[21].toString()));
	
	feedList.add(fbp);
	}
	}
	return feedList;
	 }
	
	

@SuppressWarnings("unchecked")
	public List<HelpdeskDashboardPojo> setFeedbackValue(List feedValue, String deptLevel, String feedStatus)
	  {
	

	List<HelpdeskDashboardPojo> feedList = new ArrayList<HelpdeskDashboardPojo>();
	if(feedValue!=null && feedValue.size()>0) 
	 {
	for (Iterator iterator = feedValue.iterator(); iterator.hasNext();) {
	Object[] obdata = (Object[]) iterator.next();
	HelpdeskDashboardPojo  fbp = new HelpdeskDashboardPojo();
	 fbp.setId((Integer)obdata[0]);
	 fbp.setTicket_no(obdata[1].toString());
	 fbp.setFeedback_by_dept(obdata[9].toString());
	
	if (obdata[10]!=null && !obdata[10].toString().equals("")) {
	fbp.setFeed_by(DateUtil.makeTitle(obdata[10].toString()));
	}
	else {
	 fbp.setFeed_by("NA");
	}
	 
	if (obdata[11]!=null && !obdata[11].toString().equals("") && (obdata[11].toString().startsWith("9") || obdata[11].toString().startsWith("8") || obdata[11].toString().startsWith("7")) && obdata[11].toString().length()==10) {
	fbp.setFeedback_by_mobno(obdata[11].toString());
	    }
	else {
	 fbp.setFeedback_by_mobno("NA");
	}
	
	if (obdata[12]!=null && !obdata[12].toString().equals("")) {
	fbp.setFeedback_by_emailid(obdata[12].toString());
	        }
	    else {
	    	fbp.setFeedback_by_emailid("NA");
	    }
	 
	fbp.setFeedback_to_dept(obdata[2].toString());
	fbp.setFeedback_to_subdept(obdata[3].toString());
	fbp.setFeedback_allot_to(DateUtil.makeTitle(obdata[4].toString()));
	fbp.setFeedtype(obdata[5].toString());
	fbp.setFeedback_catg(obdata[5].toString());
	fbp.setFeedback_subcatg(obdata[6].toString());
	if (obdata[7]!=null && !obdata[7].toString().equals("")) {
	fbp.setFeed_brief(obdata[7].toString());
	        }
	    else {
	    	fbp.setFeed_brief("NA");
	    }
	
	if (obdata[8]!=null && !obdata[8].toString().equals("")) {
	fbp.setLocation(obdata[8].toString());
	        }
	    else {
	    	fbp.setLocation("NA");
	    }
	fbp.setOpen_date(DateUtil.convertDateToIndianFormat(obdata[13].toString()));
	fbp.setOpen_time(obdata[14].toString().substring(0, 6));
	if (obdata[15]!=null && !obdata[15].toString().equals("")) {
	 fbp.setAddressingTime(DateUtil.convertDateToIndianFormat(obdata[15].toString().substring(0, 10))+" / "+ obdata[15].toString().substring(11, 16));
	 }
	 else {
	 fbp.setAddressingTime("NA");
	 } 
	fbp.setEscalation_date(DateUtil.convertDateToIndianFormat(obdata[16].toString())+" / "+obdata[17].toString());
	  
	fbp.setLevel(obdata[18].toString());
	fbp.setStatus(obdata[19].toString());
	fbp.setFeed_registerby(DateUtil.makeTitle(obdata[20].toString()));
	
	
	 if (!feedStatus.equalsIgnoreCase("Pending")) {
	 if (obdata[21]!=null && !obdata[21].toString().equals("")) {
	 fbp.setAction_date(DateUtil.convertDateToIndianFormat(obdata[21].toString())+"/"+obdata[22]);
	}
	 else {
	 fbp.setAction_date("NA");
	}
	 
	if (obdata[23]!=null && !obdata[23].toString().equals("")) {
	 fbp.setAction_duration(obdata[23].toString());
	}
	 else {
	 fbp.setAction_duration("NA");
	} 
	if (obdata[24]!=null && !obdata[24].toString().equals("")) {
	 fbp.setAction_remark(obdata[24].toString());
	}
	 else {
	 fbp.setAction_remark("NA");
	} 
	if (obdata[25]!=null && !obdata[25].toString().equals("")) {
	 fbp.setAction_by(obdata[25].toString());
	}
	 else {
	 fbp.setAction_by("NA");
	} 
	 
	
	 if (feedStatus.equalsIgnoreCase("Snooze"))
	 {
	 if (obdata[26]!=null && !obdata[26].toString().equals("")) {
	 fbp.setSn_upto_date(DateUtil.convertDateToIndianFormat(obdata[26].toString())+"/"+obdata[27]);
	}
	 else {
	 fbp.setSn_upto_date("NA");
	}
	 }
	}
	 
	feedList.add(fbp);
	}
	}
	return feedList;
	  }	


	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setAnalyticalReportValues(List TotalTicket,List onTime,List offTime,List snooze,List missed,List ignore, String reportFor)
	 {
	List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
	if (TotalTicket!=null && TotalTicket.size()>0) 
	{
	Object[] obdata=null;
	for (Iterator iterator = TotalTicket.iterator(); iterator.hasNext();) 
	{
	 obdata = (Object[]) iterator.next();
	 FeedbackPojo  fbp = new FeedbackPojo();
	 if (reportFor.equalsIgnoreCase("Employee"))
	 {
	 fbp.setEmpId(obdata[0].toString());
	 fbp.setEmpName(obdata[1].toString());
	 fbp.setFeedback_by_subdept(obdata[3].toString());
	 }
	 else if(reportFor.equalsIgnoreCase("Category"))
	 {
	 fbp.setFeedId(obdata[0].toString());
	 fbp.setFeedback_catg(obdata[1].toString());
	 fbp.setFeedback_subcatg(obdata[3].toString());
	 }
	 fbp.setCounter(obdata[2].toString());
	 if (onTime!=null && onTime.size()>0)
	 {
	 Object[]  obdata1=null;
	 for (Iterator iterator2 = onTime.iterator(); iterator2.hasNext();)
	 {
	 obdata1 = (Object[]) iterator2.next();
	if (obdata1!=null && !obdata.equals(""))
	{
	 if (obdata[0].toString().equalsIgnoreCase(obdata1[0].toString()))
	 {
	fbp.setOnTime(obdata1[2].toString());
	int perOnTime = (Integer.parseInt(obdata1[2].toString())*100)/Integer.parseInt(obdata[2].toString());
	fbp.setPerOnTime(String.valueOf(perOnTime));
	 }
	 }
	 }
	 }
	 else
	 {
	fbp.setOnTime("0");
	 }
	 if (offTime!=null && offTime.size()>0)
	 {
	 Object[] obdata2=null;
	 for (Iterator iterator3 = offTime.iterator(); iterator3.hasNext();)
	 {
	obdata2 = (Object[]) iterator3.next();
	if (obdata2!=null && !obdata.equals(""))
	{
	 if (obdata[0].toString().equalsIgnoreCase(obdata2[0].toString()))
	 {
	fbp.setOffTime(obdata2[2].toString());
	int perOffTime = (Integer.parseInt(obdata2[2].toString())*100)/Integer.parseInt(obdata[2].toString());
	fbp.setPerOffTime(String.valueOf(perOffTime));
	 }
	}
	 }
	 }
	 else
	 {
	fbp.setOffTime("0");
	 }
	 if (missed!=null && missed.size()>0)
	 {
	 Object[] obdata3=null;
	 for (Iterator iterator4 = missed.iterator(); iterator4.hasNext();)
	 {
	 obdata3= (Object[]) iterator4.next();
	if (obdata3!=null && !obdata.equals(""))
	{
	 if (obdata[0].toString().equalsIgnoreCase(obdata3[0].toString()))
	 {
	fbp.setMissed(obdata3[2].toString());
	int perMissed = (Integer.parseInt(obdata3[2].toString())*100)/Integer.parseInt(obdata[2].toString());
	fbp.setPerMissed(String.valueOf(perMissed));
	 }
	}
	 }
	 }
	 else
	 {
	fbp.setMissed("0");
	 }
	 if (snooze!=null && snooze.size()>0)
	 {
	 Object[] obdata4=null;
	 for (Iterator iterator5 = snooze.iterator(); iterator5.hasNext();)
	 {
	 obdata4 = (Object[]) iterator5.next();
	if (obdata4!=null && !obdata.equals(""))
	{
	 if (obdata[0].toString().equalsIgnoreCase(obdata4[0].toString()))
	 {
	    fbp.setSnooze(obdata4[2].toString());
	 }
	}
	 }
	 }
	 else
	 {
	fbp.setSnooze("0");
	 }
	 if (ignore!=null && ignore.size()>0)
	 {
	 Object[] ob=null;
	for (Iterator iterator2 = ignore.iterator(); iterator2.hasNext();)
	{
	ob = (Object[]) iterator2.next();
	if (ob!=null && !obdata.equals(""))
	{
	 if (obdata[0].toString().equalsIgnoreCase(ob[0].toString()))
	 {
	    fbp.setIgnore(ob[2].toString());
	 }
	}
	}
	 }
	 else
	 {
	 fbp.setIgnore("0");
	 }
	 feedList.add(fbp);
	}
	}
	return feedList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getFeedbackDetail(String ticketno, String deptLevel,String status,String id, SessionFactory connectionSpace)
     {
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
            {
            	if (deptLevel.equals("SD")) {
            	query.append("select feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,");
            	query.append("feedback.feed_by_emailid,emp.empName as alloto,feedtype.fbType,catg.categoryName,");
            	query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
            	query.append("feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
            	query.append("feedback.level,feedback.status,emp1.empName as actionBy,feedhistory.action_date,feedhistory.action_time,feedhistory.action_duration");
            	query.append(",feedhistory.status as histStatus,feedhistory.action_remark,feedhistory.sn_upto_date,feedhistory.sn_upto_time");
            	query.append(",emp.mobOne as allotto_mobno,emp.emailIdOne  as allotto_emailid,dept1.deptName as bydept,dept2.deptName as todept,feedback.id as feedid");
            	query.append(" ,feedback.Addr_date_time,room.roomno,emp1.mobOne as actionbymob,emp2.empName as pre_allot_to,emp2.mobOne as PreMob");
            	query.append(" from feedback_status_new as feedback");
            	query.append(" left join feedback_status_history feedhistory on feedhistory.feedId= feedback.id");
            	query.append(" left join employee_basic emp on feedback.allot_to= emp.id");
            	 	query.append(" left join department dept1 on feedback.by_dept_subdept= dept1.id ");
            	query.append(" left join department dept2 on feedback.to_dept_subdept= dept2.id");
            	query.append(" left join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
            	query.append(" left join feedback_category catg on subcatg.categoryName = catg.id"); 	
            	query.append(" left join feedback_type feedtype on catg.fbType = feedtype.id"); 
            	query.append(" left join floor_room_detail room on room.id = feedback.location"); 
            	query.append(" left join employee_basic emp1 on feedhistory.action_by= emp1.id"); 
            	query.append(" left join employee_basic emp2 on feedhistory.previous_allot_to= emp2.id"); 
            	
            	 	if (id!=null && !id.equals("") && status!=null && !status.equals("") && !status.equals("Re-Assign")) {
            	query.append(" where feedback.status='"+status+"' and feedback.id='"+id+"' ");	
	}
            	else if (id!=null && !id.equals("") && status!=null && !status.equals("") && status.equals("Re-Assign")) {
            	query.append(" where feedback.id='"+id+"' ");	
	}
            	else {
            	//query.append(" where feedback.ticket_no='"+ticketno+"' and feedback.status='"+status+"' and feedback.id=(select max(id) from feedback_status)");
            	query.append(" where feedback.ticket_no='"+ticketno+"' and feedback.status='"+status+"'");
            	}
            	}
            //	System.out.println("All multiple data query is as ::::: "+query.toString());
                    list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
    }
	
	
	@SuppressWarnings("unchecked")
	public List getFeedbackDetailForReOpened(String ticketno, String deptLevel,String status,String id, SessionFactory connectionSpace)
     {
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
            {
            	if (deptLevel.equals("SD")) {
            	query.append("select feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,");
            	query.append("feedback.feed_by_emailid,emp.empName as alloto,feedtype.fbType,catg.categoryName,");
            	query.append("subcatg.subCategoryName,feedback.feed_brief,subcatg.addressingTime,subcatg.escalateTime as esc_time,");
            	query.append("feedback.location,feedback.open_date,feedback.open_time,feedback.escalation_date,feedback.escalation_time,");
            	query.append("feedback.level,feedback.status,feedback.via_from,feedback.hp_date,feedback.hp_time,");
            	query.append("feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration,");
            	query.append("feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,feedback.action_by,");
            	query.append("emp.mobOne as allotto_mobno,emp.emailIdOne  as allotto_emailid,dept1.deptName as bydept,dept2.deptName as todept,subdept2.subdeptname as tosubdept,feedback.id as feedid");
            	if (status.equalsIgnoreCase("Resolved")) {
            	query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark,feedback.spare_used, emp1.empName as resolve_by, emp1.mobOne as resolve_by_mobno, emp1.emailIdOne as resolve_by_emailid");
	}
            	query.append(" ,feedback.Addr_date_time,emp1.mobOne as reallotto_mobno,emp1.emailIdOne  as reallotto_emailid,feedback.reallotedby,emp1.empName  as reallotto_empName");
            	query.append(" from feedback_status as feedback");
            	query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
            	query.append(" inner join employee_basic emp1 on feedback.reallotedto= emp1.id");
                	
            	query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id ");
            	query.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
            	query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
            	query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
            	query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
            	query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id"); 
            	if (status.equalsIgnoreCase("Resolved")) {
            	query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
	}
            	if (id!=null && !id.equals("") && status!=null && !status.equals("") && !status.equals("Re-Assign")) {
            	query.append(" where feedback.status='"+status+"' and feedback.id='"+id+"' ");	
	}
            	else if (id!=null && !id.equals("") && status!=null && !status.equals("") && status.equals("Re-Assign")) {
            	query.append(" where feedback.id='"+id+"' ");	
	}
            	else {
            	//query.append(" where feedback.ticket_no='"+ticketno+"' and feedback.status='"+status+"' and feedback.id=(select max(id) from feedback_status)");
            	query.append(" where feedback.ticket_no='"+ticketno+"' and feedback.status='"+status+"'");
        	
            	}
            	}
            	
            	//System.out.println("QQQqqqqqqQQQQ"+query.toString());
                    list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
    }
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> getFeedbackDetail4ReportDownload(String by_dept,String to_dept,String tosubdept,String feedType,String categoryId,String subCategory,String from_date,String to_date,String from_time,String to_time,String status, String deptLevel,String pageType, SessionFactory connectionSpace)
     {
	List<FeedbackPojo> feedList = new ArrayList<FeedbackPojo>();
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
            {
        	query.append("select feedback.ticket_no,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,emp.empName as alloto,feedtype.fbType,catg.categoryName,");
        	query.append("subcatg.subCategoryName,feedback.feed_brief,feedback.Addr_date_time,feedback.location,feedback.open_date,");
        	query.append("feedback.open_time,feedback.escalation_date,feedback.escalation_time,feedback.level,feedback.status,feedback.via_from,feedback.hp_date,");
        	query.append("feedback.hp_time,feedback.hp_reason,feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,");
        	query.append("feedback.sn_duration,feedback.ig_date,feedback.ig_time,feedback.ig_reason,feedback.transfer_date,feedback.transfer_time,feedback.transfer_reason,");
        	query.append("feedback.action_by,emp.mobOne,emp.emailIdOne,dept1.deptName as bydept,dept2.deptName as todept,subdept2.subdeptname as tosubdept,feedback.non_working_time");
        	if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("All")) {
        	    query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
	}
	        	if (pageType!=null && !pageType.equalsIgnoreCase("") && pageType.equalsIgnoreCase("H")) {
	        	query.append(" from feedback_status_15072014 as feedback");
	}
	        	else {
	        	query.append(" from feedback_status as feedback");	
	}
	        
        	if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Snooze") || status.equalsIgnoreCase("High Priority") || status.equalsIgnoreCase("Ignore")) {
	        	query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
	        	query.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id");
	        	query.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
	        	query.append(" inner join department dept1 on subdept1.deptid= dept1.id");
	        	query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
	        	query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
	        	query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
	        	query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id"); 
            	if (status.equalsIgnoreCase("Resolved")) {
            	    query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
	}
        	}
        	else if (status.equalsIgnoreCase("All")) {
        	query.append(" left join employee_basic emp on feedback.allot_to= emp.id");
            	query.append(" left join department dept1 on feedback.by_dept_subdept= dept1.id");
            	query.append(" left join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
            	query.append(" left join department dept2 on subdept2.deptid= dept2.id");
            	query.append(" left join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
            	query.append(" left join feedback_category catg on subcatg.categoryName = catg.id"); 	
            	query.append(" left join feedback_type feedtype on catg.fbType = feedtype.id"); 
            	query.append(" left join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
        	}
        	
        	query.append(" where ");
        	
        	
        	if (by_dept!=null && !by_dept.equals("") && !by_dept.equals("-1")) {
        	query.append(" dept1.id='"+by_dept+"'");	
	}
        	
        	if (to_dept!=null && !to_dept.equals("") && !to_dept.equals("-1") && !by_dept.equals("-1")) {
        	query.append(" and dept2.id='"+to_dept+"'");	
	}
        	else if (to_dept!=null && !to_dept.equals("-1") && by_dept.equals("-1")) {
        	query.append(" dept2.id='"+to_dept+"'");	
	}
        	
        	if (tosubdept!=null && !tosubdept.equals("-1")) {
        	query.append(" and feedback.to_dept_subdept='"+tosubdept+"'");	
	}
        	
        	if (feedType!=null  && !feedType.equals("-1")) {
        	query.append(" and feedtype.id='"+feedType+"'");	
	}
        	
        	if (categoryId!=null && !categoryId.equals("-1")) {
        	query.append(" and catg.id='"+categoryId+"'");	
	}
        	
        	if (subCategory!=null && !subCategory.equals("-1")) {
        	query.append(" and subcatg.id='"+subCategory+"'");	
	}
        	
        	if (from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("") && (!by_dept.equals("-1") || !to_dept.equals("-1"))) {
        	query.append(" and feedback.open_date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"' ");	
	}
        	else if (from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("") && (by_dept.equals("-1") || to_dept.equals("-1"))) {
        	query.append(" feedback.open_date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"' ");	
	}
        	
        	/*if (from_time!=null && !from_time.equals("") && to_time!=null && !to_time.equals("") && from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("")) {
        	query.append(" and feedback.open_time between '"+from_time+"' and '"+to_time+"' ");	
	}
        	else if (from_time!=null && !from_time.equals("") && to_time!=null && !to_time.equals("") && (from_date==null || from_date.equals("") || to_date==null || to_date.equals(""))) {
        	query.append(" and feedback.open_time between '"+from_time+"' and '"+to_time+"' ");	
	}*/
        	
        	if (status!=null && !status.equals("") && (status.equals("Pending") || status.equals("Snooze") || status.equals("High Priority") || status.equals("Resolved")  || status.equals("Ignore"))) {
        	query.append(" and feedback.status='"+status+"' ");	
	}
        	
        	query.append(" and feedback.moduleName='HDM' ");	
            	
                list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
                
                if (list!=null && list.size()>0) {
            	int i=1;
            	if (list!=null && list.size()>0) {
            	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            	Object[] object = (Object[]) iterator.next();
            	FeedbackPojo fp =new FeedbackPojo();
            	fp.setId(i);
            	if (object[0]==null || object[0].toString().equals("")) {
            	fp.setTicket_no("NA");
            	}
            	else {
            	fp.setTicket_no(object[0].toString());
            	}
            	
            	if (object[1]==null || object[1].toString().equals("")) {
            	fp.setFeed_by("NA");
            	}
            	else {
            	fp.setFeed_by(DateUtil.makeTitle(object[1].toString()));
            	}
            	
            	if (object[2]==null || object[2].toString().equals("")) {
            	fp.setFeedback_by_mobno("NA");
            	}
            	else {
            	fp.setFeedback_by_mobno(object[2].toString());
            	}
            	
            	if (object[3]==null || object[3].toString().equals("")) {
            	fp.setFeedback_by_emailid("NA");
            	}
            	else {
            	fp.setFeedback_by_emailid(object[3].toString());
            	}
            	
            	if (object[4]==null || object[4].toString().equals("")) {
            	fp.setFeedback_allot_to("NA");
            	}
            	else {
            	fp.setFeedback_allot_to(DateUtil.makeTitle(object[4].toString()));
            	}
            	
            	if (object[5]==null || object[5].toString().equals("")) {
            	fp.setFeedtype("NA");
            	}
            	else {
            	fp.setFeedtype(DateUtil.makeTitle(object[5].toString()));
            	}
            	
            	if (object[6]==null || object[6].toString().equals("")) {
            	fp.setFeedback_catg("NA");
            	}
            	else {
            	fp.setFeedback_catg(object[6].toString());
            	}
            	
            	if (object[7]==null || object[7].toString().equals("")) {
            	fp.setFeedback_subcatg("NA");
            	}
            	else {
            	fp.setFeedback_subcatg(object[7].toString());
            	}
            	
            	if (object[8]==null || object[8].toString().equals("")) {
            	fp.setFeed_brief("NA");
            	}
            	else {
            	fp.setFeed_brief(object[8].toString());
            	}
            	
            	if (object[9]!=null && !object[9].toString().equals("")) {
            	fp.setAddressingTime(DateUtil.convertDateToIndianFormat(object[9].toString().substring(0, 10))+"/"+object[9].toString().substring(11, 16));
            	}
            	else {
            	fp.setAddressingTime("NA");
            	}
            	
            	if (object[10]==null || object[10].toString().equals("")) {
            	fp.setLocation("NA");
            	}
            	else {
            	fp.setLocation(object[10].toString());
            	}
            	
            	if (object[11]==null || object[11].toString().equals("")) {
            	fp.setOpen_date("NA");
            	}
            	else {
            	fp.setOpen_date(DateUtil.convertDateToIndianFormat(object[11].toString()));
            	}
            	
            	if (object[12]==null || object[12].toString().equals("")) {
            	fp.setOpen_time("NA");
            	}
            	else {
            	fp.setOpen_time(object[12].toString().substring(0, 5));
            	}
            	
            	if (object[13]==null || object[13].toString().equals("")) {
            	fp.setEscalation_date("NA");
            	}
            	else {
            	fp.setEscalation_date(DateUtil.convertDateToIndianFormat(object[13].toString()));
            	}
            	
            	
            	if (object[14]==null || object[14].toString().equals("")) {
            	fp.setEscalation_time("NA");
            	}
            	else if(object[14].toString().length()>5){
            	fp.setEscalation_time(object[14].toString().substring(0, 5));
            	}else {
            	object[14].toString();
            	}
            	
            	if (object[15]==null || object[15].toString().equals("")) {
            	fp.setLevel("NA");
            	}
            	else {
            	fp.setLevel(object[15].toString());
            	}
            	
            	if (object[16]==null || object[16].toString().equals("")) {
            	fp.setStatus("NA");
            	}
            	else {
            	fp.setStatus(object[16].toString());
            	}
            	
            	if (object[17]==null || object[17].toString().equals("")) {
            	fp.setVia_from("NA");
            	}
            	else {
            	fp.setVia_from(DateUtil.makeTitle(object[17].toString()));
            	}
            	
            	if (object[18]==null || object[18].toString().equals("")) {
            	fp.setHp_date("NA");
            	}
            	else {
            	fp.setHp_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
            	}
            	
            	if (object[19]==null || object[19].toString().equals("")) {
            	fp.setHp_time("NA");
            	}
            	else {
            	fp.setHp_time(object[19].toString().substring(0, 5));
            	}
            	
            	if (object[20]==null || object[20].toString().equals("")) {
            	fp.setHp_reason("NA");
            	}
            	else {
            	fp.setHp_reason(object[20].toString());
            	}
            	
            	if (object[21]==null || object[21].toString().equals("")) {
            	fp.setSn_reason("NA");
            	}
            	else {
            	fp.setSn_reason(object[21].toString());
            	}
            	
            	if (object[22]==null || object[22].toString().equals("")) {
            	fp.setSn_on_date("NA");
            	}
            	else {
            	fp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[22].toString()));
            	}
            	
            	if (object[23]==null || object[23].toString().equals("")) {
            	fp.setSn_on_time("NA");
            	}
            	else {
            	fp.setSn_on_time(object[23].toString().substring(0, 5));
            	}
            	
            	if (object[24]==null || object[24].toString().equals("")) {
            	fp.setSn_date("NA");
            	}
            	else {
            	fp.setSn_date(DateUtil.convertDateToIndianFormat(object[24].toString()));
            	}
            	
            	if (object[25]==null || object[25].toString().equals("")) {
            	fp.setSn_time("NA");
            	}
            	else {
            	fp.setSn_time(object[25].toString().substring(0,5));
            	}
            	
            	if (object[26]==null || object[26].toString().equals("")) {
            	fp.setSn_duration("NA");
            	}
            	else {
            	fp.setSn_duration(object[26].toString());
            	}
            	
            	if (object[27]==null || object[27].toString().equals("")) {
            	fp.setIg_date("NA");
            	}
            	else {
            	fp.setIg_date(DateUtil.convertDateToIndianFormat(object[27].toString()));
            	}
            	
            	if (object[28]==null || object[28].toString().equals("")) {
            	fp.setIg_time("NA");
            	}
            	else {
            	fp.setIg_time(object[28].toString().substring(0, 5));
            	}
            	
            	if (object[29]==null || object[29].toString().equals("")) {
            	fp.setIg_reason("NA");
            	}
            	else {
            	fp.setIg_reason(object[29].toString());
            	}
            	
            	if (object[30]==null || object[30].toString().equals("")) {
            	fp.setTransfer_date("NA");
            	}
            	else {
            	fp.setTransfer_date(DateUtil.convertDateToIndianFormat(object[30].toString()));
            	}
            	
            	if (object[31]==null || object[31].toString().equals("")) {
            	fp.setTransfer_time("NA");
            	}
            	else {
            	fp.setTransfer_time(object[31].toString().substring(0, 5));
            	}
            	
            	if (object[32]==null || object[32].toString().equals("")) {
            	fp.setTransfer_reason("NA");
            	}
            	else {
            	fp.setTransfer_reason(object[32].toString());
            	}
            	
            	if (object[33]==null || object[33].toString().equals("")) {
            	fp.setAction_by("NA");
            	}
            	else {
            	fp.setAction_by(DateUtil.makeTitle(object[33].toString()));
            	}
            	
            	if (object[36]==null || object[36].toString().equals("")) {
            	fp.setFeedback_by_dept("NA");
            	}
            	else {
            	fp.setFeedback_by_dept(object[36].toString());
            	}
            	
            	if (object[37]==null || object[37].toString().equals("")) {
            	fp.setFeedback_to_dept("NA");
            	}
            	else {
            	fp.setFeedback_to_dept(object[37].toString());
            	}
            	
            	if (object[38]==null || object[38].toString().equals("")) {
            	fp.setFeedback_to_subdept("NA");
            	}
            	else {
            	fp.setFeedback_to_subdept(object[38].toString());
            	}
            	if (object[39]==null || object[39].toString().equals("")) {
            	fp.setNon_working_time("NA");
            	}
            	else {
            	fp.setNon_working_time(object[39].toString());
            	}
            	
            	// Add By Padam In 13 Nov
            	if (status.equalsIgnoreCase("Resolved") || status.equalsIgnoreCase("All")) {
            	if (object[40]==null || object[40].toString().equals("")) {
                	fp.setResolve_date("NA");
                	}
                	else {
                	fp.setResolve_date(DateUtil.convertDateToIndianFormat(object[40].toString()));
                	}
            	
            	if (object[41]==null || object[41].toString().equals("")) {
                	fp.setResolve_time("NA");
                	}
                	else {
                	fp.setResolve_time(object[41].toString());
                	}
            	
            	if (object[42]==null || object[42].toString().equals("")) {
                	fp.setResolve_duration("NA");
                	}
                	else {
                	fp.setResolve_duration(object[42].toString());
                	}
            	
            	if (object[43]==null || object[43].toString().equals("")) {
                	fp.setResolve_remark("NA");
                	}
                	else {
                	fp.setResolve_remark(object[43].toString());
                	}
            	
            	if (object[44]==null || object[44].toString().equals("")) {
                	fp.setResolve_by("NA");
                	}
                	else {
                	fp.setResolve_by(DateUtil.makeTitle(object[44].toString()));
                	}
            	
            	if (object[45]==null || object[45].toString().equals("")) {
                	fp.setSpare_used("NA");
                	}
                	else {
                	fp.setSpare_used(object[45].toString());
                	}
            	}
            	
            	feedList.add(fp);
            	i++;
            	}
            	}
	}
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return feedList;
    }
	
	@SuppressWarnings("unchecked")
	public List getFeedbackStatusCount(String by_dept,String to_dept,String tosubdept,String feedType,String categoryId,String subCategory,String from_date,String to_date,String from_time,String to_time,String status, String deptLevel,String pageType, SessionFactory connectionSpace)
     {
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
            {
            	query.append("select feedback.status,count(*)");
            	if (pageType!=null && !pageType.equals("") && pageType.equals("H")) {
            	query.append(" from feedback_status_15072014 as feedback");
	}
            	else {
            	query.append(" from feedback_status as feedback");
	}
            	query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
            	query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id");
            	query.append(" inner join subdepartment subdept2 on feedback.to_dept_subdept= subdept2.id");
            	query.append(" inner join department dept2 on subdept2.deptid= dept2.id");
            	query.append(" inner join feedback_subcategory subcatg on feedback.subcatg = subcatg.id"); 	
            	query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id"); 	
            	query.append(" inner join feedback_type feedtype on catg.fbType = feedtype.id"); 
            	query.append(" where ");
            	if (by_dept!=null  && !by_dept.equals("-1")) {
            	query.append(" dept1.id='"+by_dept+"'");	
	}
            	
            	if (to_dept!=null  && !to_dept.equals("-1") && !by_dept.equals("-1")) {
            	query.append(" and dept2.id='"+to_dept+"'");	
	}
            	
            	if (to_dept!=null  && !to_dept.equals("-1") && by_dept.equals("-1")) {
            	query.append(" dept2.id='"+to_dept+"'");	
	}
            	
            	if (tosubdept!=null  && !tosubdept.equals("-1")) {
            	query.append(" and feedback.to_dept_subdept='"+tosubdept+"'");	
	}
            	
            	if (feedType!=null  && !feedType.equals("-1")) {
            	query.append(" and feedtype.id='"+feedType+"'");	
	}
            	
            	if (categoryId!=null && !categoryId.equals("-1")) {
            	query.append(" and catg.id='"+categoryId+"'");	
	}
            	
            	if (subCategory!=null  && !subCategory.equals("-1")) {
            	query.append(" and subcatg.id='"+subCategory+"'");	
	}
            	
            	if (status!=null && !status.equals("") && (status.equals("Pending") || status.equals("Snooze") || status.equals("High Priority") || status.equals("Resolved"))) {
            	query.append(" and feedback.status='"+status+"'");	
	}
            	
            	if (from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("") && (!by_dept.equals("-1") || !to_dept.equals("-1"))) {
            	query.append(" and feedback.open_date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"' ");	
	}
            	
            	else if (from_date!=null && !from_date.equals("") && to_date!=null && !to_date.equals("") && (by_dept.equals("-1") || to_dept.equals("-1"))) {
            	query.append(" feedback.open_date between '"+DateUtil.convertDateToUSFormat(from_date)+"' and '"+DateUtil.convertDateToUSFormat(to_date)+"' ");	
	}
            	
            	if (status!=null && !status.equals("") && !status.equals("All")) {
            	query.append(" and feedback.status='"+status+"'");
	}
            	
            	query.append(" and feedback.moduleName='HDM' ");	
            	
            	query.append(" group by feedback.status");
                list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
    }
	
	@SuppressWarnings("unchecked")
	public List getloginReportData(String searchField,String searchString,SessionFactory connectionSpace)
     {
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
            {
            	query.append("select acc.id,emp.empName,emp.mobOne,dept.deptName,acc.loginStatus from employee_basic as emp ");
            	query.append("INNER JOIN useraccount acc on acc.id=emp.useraccountid ");
            	query.append("INNER JOIN department as dept on dept.id=emp.deptname ");
            	if(searchField!=null && !searchField.equalsIgnoreCase("")  && !searchField.equalsIgnoreCase("null")  && searchString!=null && !searchString.equalsIgnoreCase("") && !searchString.equalsIgnoreCase("-1"))
            	query.append("where dept."+searchField+"="+searchString+" and acc.active='1'");
            	else if(searchString!=null && !searchField.equalsIgnoreCase("null") && !searchString.equalsIgnoreCase("") && !searchString.equalsIgnoreCase("null"))
                	
	{
	query.append(" Where emp.empName  like '%" +searchString + "%'");
	query.append(" OR emp.mobOne  like '%" + searchString + "%'");
	query.append(" OR dept.deptName  like '%" + searchString + "%'");
	}
            	else  
            	query.append("where acc.active='1' ");
            	list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
    }
	@SuppressWarnings("unchecked")
	public boolean checkTable(String table, SessionFactory connection)
	 {
	boolean flag = false;
	List list = null;
	String value = null;
	try {
	SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) connection;
	Properties props = sessionFactoryImpl.getProperties();
	String url = props.get("hibernate.connection.url").toString();
	String[] urlArray = url.split(":");
	String db_name = urlArray[urlArray.length - 1];
	String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema ='"+db_name.substring(5)+"' AND table_name = '"+table+"'";
	Session session = null;  
	Transaction transaction = null;  
	    session=connection.getCurrentSession(); 
	    transaction = session.beginTransaction(); 
	    list=session.createSQLQuery(query).list();  
	transaction.commit();
	if (list!=null && list.size()>0) {
	value = list.get(0).toString();
	if (value.equalsIgnoreCase("1")) {
	flag=true;
	}
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return flag;
	 }
	
	@SuppressWarnings("unchecked")
	public boolean check_createTable(String table, SessionFactory connection)
	 {
	boolean flag = false;
	List list = null;
	String value = null;
	Session session = null;  
	Transaction transaction = null;  
	try {
	SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) connection;
	Properties props = sessionFactoryImpl.getProperties();
	String url = props.get("hibernate.connection.url").toString();
	String[] urlArray = url.split(":");
	String db_name = urlArray[urlArray.length - 1];
	String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema ='"+db_name.substring(5)+"' AND table_name = '"+table+"'";
	
	    session=connection.getCurrentSession(); 
	    transaction = session.beginTransaction(); 
	    list=session.createSQLQuery(query).list();  
	transaction.commit();
	}catch (Exception e) {
	e.printStackTrace();
	}
	
	if (list!=null && list.size()>0) {
	value = list.get(0).toString();
	if (value.equalsIgnoreCase("1")) {
	flag=true;
	}
	else { 
	CommonOperInterface cot = new CommonConFactory().createInterface();
	 if (table.equalsIgnoreCase("feedback_type")) {
	      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_type_configuration", "", connection, "", 0, "table_name", "feedback_type_configuration");
	      if (colName!= null && colName.size()>0) {
	    	  //Create Table Query Based On Configuration
	  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
	  for (GridDataPropertyView tableColumes : colName) {
	TableColumes tc = new TableColumes();
	tc.setColumnname(tableColumes.getColomnName());
	tc.setDatatype("varchar(255)");
	tc.setConstraint("default NULL");
	tableColumn.add(tc);
	   }
	  TableColumes tc = new TableColumes();
	  
	
	  tc.setColumnname("dept_subdept");
	  tc.setDatatype("varchar(5)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("hide_show");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("userName");
	  tc.setDatatype("varchar(15)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("date");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("time");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  cot.createTable22("feedback_type", tableColumn, connection);
	}
	}
	   else if (table.equalsIgnoreCase("feedback_category")) {
	      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_category_configuration", "", connection, "", 0, "table_name", "feedback_category_configuration");
	      if (colName!= null && colName.size()>0) {
	    	  
	  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
	  for (GridDataPropertyView tableColumes : colName) {
	TableColumes tc = new TableColumes();
	tc.setColumnname(tableColumes.getColomnName());
	tc.setDatatype("varchar(255)");
	tc.setConstraint("default NULL");
	tableColumn.add(tc);
	   }
	  TableColumes tc = new TableColumes();
	  
	  tc.setColumnname("hide_show");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("userName");
	  tc.setDatatype("varchar(15)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("date");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("time");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	 cot.createTable22("feedback_category", tableColumn, connection);
	}
	} 
	   else if (table.equalsIgnoreCase("feedback_subcategory")) {
	      List<GridDataPropertyView> colName = Configuration.getConfigurationData("mapped_feedback_subcategory_configuration", "", connection, "", 0, "table_name", "feedback_subcategory_configuration");
	      if (colName!= null && colName.size()>0) {
	
	  List <TableColumes> tableColumn=new ArrayList<TableColumes>();
	  for (GridDataPropertyView tableColumes : colName) {
	  if (!tableColumes.getColomnName().equals("fbType")) {
	  TableColumes tc = new TableColumes();
	tc.setColumnname(tableColumes.getColomnName());
	tc.setDatatype("varchar(255)");
	tc.setConstraint("default NULL");
	tableColumn.add(tc);
	}
	
	   }
	  TableColumes tc = new TableColumes();
	  
	  
	  tc.setColumnname("hide_show");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	 
	  tc = new TableColumes();
	  tc.setColumnname("userName");
	  tc.setDatatype("varchar(15)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("date");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  tc = new TableColumes();
	  tc.setColumnname("time");
	  tc.setDatatype("varchar(10)");
	  tc.setConstraint("default NULL");
	  tableColumn.add(tc);
	  
	  cot.createTable22("feedback_subcategory", tableColumn, connection);
	   }
	}
	flag=true;
	}
	}
	
	return flag;
	 }
	
	@SuppressWarnings("unchecked")
	public List getClientConf(SessionFactory connection)
	 {
	List list = null;
	try {
	 String query = "SELECT * FROM client_conf";
	 Session session = null;  
	 Transaction transaction = null;  
	     session=connection.getCurrentSession(); 
	     transaction = session.beginTransaction(); 
	     list=session.createSQLQuery(query).list();  
	 transaction.commit();
	} catch (Exception e) {
	e.printStackTrace();
	}
	return list;
	 }
	
	@SuppressWarnings("unchecked")
	public FeedbackPojo setFeedbackDataValues(List data, String status,String deptLevel)
	{
	FeedbackPojo fbp =null;
	if (data!=null && data.size()>0) {
	for (Iterator iterator = data.iterator(); iterator.hasNext();) 
	{
	Object[] object = (Object[]) iterator.next();
	fbp = new FeedbackPojo();
	fbp.setTicket_no(object[0].toString());
	if (object[1]!=null && !object[1].toString().equals("")) {
	fbp.setFeed_registerby(DateUtil.makeTitle(object[1].toString()));
	} else {
	fbp.setFeed_registerby("NA");
	}
	if (object[2]!=null && !object[2].toString().equals("")) {
	fbp.setFeedback_by_mobno(object[2].toString());
	} else {
	fbp.setFeedback_by_mobno("NA");
	}
	if (object[3]!=null && !object[3].toString().equals("")) {
	fbp.setFeedback_by_emailid(object[3].toString());
	} else {
	fbp.setFeedback_by_emailid("NA");
	}
	fbp.setFeedback_allot_to(object[4].toString());
	fbp.setFeedtype(object[5].toString());
	fbp.setFeedback_catg(object[6].toString());
	fbp.setFeedback_subcatg(object[7].toString());
	if (object[8]!=null && !object[8].toString().equals("")) {
	fbp.setFeed_brief(object[8].toString());
	} else {
	fbp.setFeed_brief("NA");
	}
	
	fbp.setFeedaddressing_time(object[9].toString());
	fbp.setOpen_date(object[11].toString());
	fbp.setOpen_time(object[12].toString());
	fbp.setEscalation_date(object[13].toString());
	fbp.setEscalation_time(object[14].toString());
	fbp.setLevel(object[15].toString());
	fbp.setStatus(object[16].toString());
	
         	if (object[17]!=null && !object[17].toString().equals("")) {
	fbp.setAction_by(object[17].toString());	
	}
	else {
	fbp.setAction_by("NA");
	}
         	
         	if (object[18]!=null && !object[18].toString().equals("")) {
	fbp.setResolve_date(object[18].toString());	
	}
	else {
	fbp.setResolve_date("NA");
	}
         	if (object[19]!=null && !object[19].toString().equals("")) {
	fbp.setResolve_time(object[19].toString());	
	}
	else {
	fbp.setResolve_time("NA");
	}
         	if (object[20]!=null && !object[20].toString().equals("")) {
	fbp.setResolve_duration(object[20].toString());	
	}
	else {
	fbp.setResolve_duration("NA");
	}
         	if (object[21]!=null && !object[21].toString().equals("")) {
	fbp.setResolve_rca(object[21].toString());	
	}
	else {
	fbp.setResolve_rca("NA");
	}
         	if (object[22]!=null && !object[22].toString().equals("")) {
	fbp.setResolve_remark(object[22].toString());	
	}
	else {
	fbp.setResolve_remark("NA");
	}
         	if (object[23]!=null && !object[23].toString().equals("")) {
	fbp.setSn_upto_date(object[23].toString());	
	}
	else {
	fbp.setSn_upto_date("NA");
	}
         	if (object[24]!=null && !object[24].toString().equals("")) {
	fbp.setSn_upto_time(object[24].toString());	
	}
	else {
	fbp.setSn_upto_time("NA");
	}
	if (object[25]!=null && !object[25].toString().equals("")) {
	fbp.setMobOne(object[25].toString());	
	}
	else {
	fbp.setMobOne("NA");
	}
	if (object[26]!=null && !object[26].toString().equals("")) {
	fbp.setEmailIdOne(object[26].toString());
	} else {
	fbp.setEmailIdOne("NA");
	}
	 
	fbp.setFeedback_by_dept(object[27].toString());
             	fbp.setFeedback_to_dept(object[28].toString());	
             	fbp.setId(Integer.parseInt(object[29].toString()));
               
         	
         	
	if (object[30]!=null && !object[30].toString().equals("")) 
	{
	fbp.setAddr_date_time(DateUtil.convertDateToIndianFormat(object[30].toString().substring(0, 10))+" "+object[30].toString().substring(11, 16));
	}
	if (object[31]!=null && !object[31].toString().equals("")) 
	{
	fbp.setRoomNo(object[31].toString());
	}
	if (object[32]!=null) 
	{
	fbp.setAction_by_mob(object[32].toString());
	}
	if (object[33]!=null) 
	{
	fbp.setPre_allot_to(object[33].toString());
	}
	if (object[34]!=null) 
	{
	fbp.setPre_allot_to_mob(object[34].toString());
	}
	
	}
	}
	return fbp;
	
}
	
	
	@SuppressWarnings("unchecked")
	public FeedbackPojo setFeedbackDataValuesForReOpened(List data, String status,String deptLevel)
	{
	FeedbackPojo fbp =null;
	if (data!=null && data.size()>0) {
	for (Iterator iterator = data.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	fbp = new FeedbackPojo();
	fbp.setTicket_no(object[0].toString());
	if (object[1]!=null && !object[1].toString().equals("")) {
	fbp.setFeed_registerby(DateUtil.makeTitle(object[1].toString()));
	} else {
	fbp.setFeed_registerby("NA");
	}
	if (object[2]!=null && !object[2].toString().equals("")) {
	fbp.setFeedback_by_mobno(object[2].toString());
	} else {
	fbp.setFeedback_by_mobno("NA");
	}
	if (object[3]!=null && !object[3].toString().equals("")) {
	fbp.setFeedback_by_emailid(object[3].toString());
	} else {
	fbp.setFeedback_by_emailid("NA");
	}
	
	
	fbp.setFeedback_allot_to(object[4].toString());
	fbp.setFeedtype(object[5].toString());
	fbp.setFeedback_catg(object[6].toString());
	fbp.setFeedback_subcatg(object[7].toString());
	fbp.setFeed_brief(object[8].toString());
	fbp.setFeedaddressing_time(object[9].toString());
	fbp.setLocation(object[11].toString());
	fbp.setOpen_date(object[12].toString());
	fbp.setOpen_time(object[13].toString());
	fbp.setEscalation_date(object[14].toString());
	fbp.setEscalation_time(object[15].toString());
	fbp.setLevel(object[16].toString());
	fbp.setStatus(object[17].toString());
	fbp.setVia_from(object[18].toString());
	if (status.equalsIgnoreCase("High Priority")) {
	fbp.setHp_date(object[19].toString());	
	fbp.setHp_time(object[20].toString());	
	fbp.setHp_reason(object[21].toString());
	}
	else if (status.equalsIgnoreCase("Snooze")) {
	    fbp.setSn_reason(object[22].toString());
	    fbp.setSn_on_date(object[23].toString());
	    fbp.setSn_on_time(object[24].toString());
	    fbp.setSn_date(object[25].toString());
	    fbp.setSn_time(object[26].toString());
	    fbp.setSn_duration(object[27].toString());
	}
	else if (status.equalsIgnoreCase("Ignore")) {
	                fbp.setIg_date(object[28].toString());
	     	fbp.setIg_time(object[29].toString());
	     	fbp.setIg_reason(object[30].toString());
	}
	else if (status.equalsIgnoreCase("Transfer")) {
	                fbp.setTransfer_date(object[31].toString());
	         	fbp.setTransfer_time(object[32].toString());
	         	fbp.setTransfer_reason(object[33].toString());	
	}
         	if (object[34]!=null && !object[34].toString().equals("")) {
	fbp.setAction_by(object[34].toString());	
	}
	else {
	fbp.setAction_by("NA");
	}
	
	if (object[35]!=null && !object[35].toString().equals("")) {
	fbp.setMobOne(object[35].toString());	
	}
	else {
	fbp.setMobOne("NA");
	}
	if (object[36]!=null && !object[36].toString().equals("")) {
	fbp.setEmailIdOne(object[36].toString());
	} else {
	fbp.setEmailIdOne("NA");
	}
	if (object[42]!=null && !object[42].toString().equals("")) {
	fbp.setReMobOne(object[42].toString());
	} else {
	fbp.setReMobOne("NA");
	}
	if (object[43]!=null && !object[43].toString().equals("")) {
	fbp.setReEmailIdOne(object[43].toString());
	} else {
	fbp.setReEmailIdOne("NA");
	}
	if (object[44]!=null && !object[44].toString().equals("")) {
	fbp.setReAllotedBy(object[44].toString());
	} else {
	fbp.setReAllotedBy("NA");
	}
	if (object[45]!=null && !object[45].toString().equals("")) {
	fbp.setReAllotedTo(object[45].toString());
	} else {
	fbp.setReAllotedTo("NA");
	}
	
	fbp.setFeedback_by_dept(object[37].toString());
             	fbp.setFeedback_to_dept(object[38].toString());	
             	fbp.setFeedback_to_subdept(object[39].toString());
             	fbp.setId(Integer.parseInt(object[40].toString()));
               
         	if (status.equals("Resolved")) {
	                fbp.setResolve_date(object[41].toString());
	                fbp.setResolve_time(object[42].toString());
	                fbp.setResolve_duration(object[43].toString());
	                fbp.setResolve_remark(object[44].toString());
	                fbp.setSpare_used(object[45].toString());
	                fbp.setResolve_by(object[46].toString());
	                if (object[47]!=null && !object[47].toString().equals("")) {
	fbp.setResolve_by_mobno(object[47].toString());
	} else {
	fbp.setResolve_by_mobno("NA");
	}
	                if (object[48]!=null && !object[48].toString().equals("")) {
	fbp.setResolve_by_emailid(object[48].toString());
	} else {
	fbp.setResolve_by_emailid("NA");
	}
	}
         	
         	if (!status.equals("Resolved")) {
	if (object[41]!=null && !object[41].toString().equals("")) {
	fbp.setAddr_date_time(DateUtil.convertDateToIndianFormat(object[41].toString().substring(0, 10))+" "+object[41].toString().substring(11, 16));
	}
	}
	}
	}
	return fbp;
	}
	

	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> setFeedbackDataforReport(List data,String status,String reportFor,String reportRange)
	{
	List<FeedbackPojo> fbpList = new ArrayList<FeedbackPojo>();
	FeedbackPojo fbp =null;
	if (data!=null && data.size()>0) {
	for (Iterator iterator = data.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	fbp = new FeedbackPojo();
	
	if (reportFor!=null) {
	fbp.setReportFor(reportFor);
	}
	else {
	fbp.setReportFor("NA");
	}
	
	if (reportRange!=null) {
	fbp.setReportRange(reportRange);
	}
	else {
	fbp.setReportRange("NA");
	}
	if (object[0]!=null) {
	fbp.setTicket_no(object[0].toString());
	}
	else {
	fbp.setTicket_no("NA");
	}
	
	if (object[1]!=null) {
	fbp.setFeedback_by_dept(object[1].toString());
	}
	else {
	fbp.setFeedback_by_dept("NA");
	}
	
	if (object[2]!=null) {
	fbp.setFeed_by(object[2].toString());
	}
	else {
	fbp.setFeed_by("NA");
	}
	
	if (object[3]!=null) {
	fbp.setFeedback_by_mobno(object[3].toString());
	}
	else {
	fbp.setFeedback_by_mobno("NA");
	}
	
	if (object[4]!=null) {
	fbp.setFeedback_by_emailid(object[4].toString());
	}
	else {
	fbp.setFeedback_by_emailid("NA");
	}
	
	if (object[5]!=null) {
	fbp.setFeedback_to_dept(object[5].toString());
	}
	else {
	fbp.setFeedback_to_dept("NA");
	}
	
	if (object[6]!=null) {
	fbp.setFeedback_allot_to(object[6].toString());
	}
	else {
	fbp.setFeedback_allot_to("NA");
	}
	
	if (object[7]!=null) {
	fbp.setFeedtype(object[7].toString());
	}
	else {
	fbp.setFeedtype("NA");
	}
	
	if (object[8]!=null) {
	fbp.setFeedback_catg(object[8].toString());
	}
	else {
	fbp.setFeedback_catg("NA");
	}
	
	if (object[9]!=null) {
	fbp.setFeedback_subcatg(object[9].toString());
	}
	else {
	fbp.setFeedback_subcatg("NA");
	}
	
	if (object[10]!=null) {
	fbp.setFeed_brief(object[10].toString());
	}
	else {
	fbp.setFeed_brief("NA");
	}
	
	if (object[11]!=null) {
	fbp.setLocation(object[11].toString());
	}
	else {
	fbp.setLocation("NA");
	}
	
	if (object[12]!=null) {
	fbp.setOpen_date(DateUtil.convertDateToIndianFormat(object[12].toString()));
	}
	else {
	fbp.setOpen_date("NA");
	}
	
	if (object[13]!=null) {
	if(object[13].toString().length()>6)
	fbp.setOpen_time(object[13].toString().substring(0, 5));
	 	else
	fbp.setOpen_time(object[13].toString());
	}
	else {
	fbp.setOpen_time("NA");
	}
	
	if (object[14]!=null) {
	fbp.setLevel(object[14].toString());
	}
	else {
	fbp.setLevel("NA");
	}
	
	if (object[15]!=null) {
	if(object[15].toString().equalsIgnoreCase("Snooze"))
	fbp.setStatus("Parked");
	else
	
	fbp.setStatus(object[15].toString());
	}
	else {
	fbp.setStatus("NA");
	}
	
	 	
	if (object[16]!=null) {
	fbp.setFeed_registerby(object[16].toString());
	}
	else {
	fbp.setFeed_registerby("NA");
	}
	
	 	
	if (status.equalsIgnoreCase("Pending")) {
	
	if (object[17]!=null) {
	fbp.setPending_date(DateUtil.convertDateToIndianFormat(object[17].toString()));
	}
	else {
	fbp.setPending_date("NA");
	}
	
	if (object[18]!=null) {
	if(object[18].toString().length()>6)
	fbp.setPending_time(object[18].toString().substring(0, 5));
	else
	fbp.setPending_time(object[18].toString());
	}
	else {
	fbp.setPending_time("NA");
	}
	
	
	if (object[19]==null) {
	if(object[20].toString().equalsIgnoreCase("Escalated"))
	fbp.setPending_reason(object[26].toString()+":"+object[21].toString());
	}
	else {
	fbp.setPending_reason(object[19].toString());
	}
	 
	
	
	if (object[20]!=null) {
	if(object[20].toString().equalsIgnoreCase("Snooze"))
	fbp.setHistoryStatus("Parked");
	else if(object[20].toString().equalsIgnoreCase("Transfer"))
	fbp.setHistoryStatus("Re-assign");
	else if(object[20].toString().equalsIgnoreCase("Re-OpenedR"))
	fbp.setHistoryStatus("Re-open");
	else
	fbp.setHistoryStatus(object[20].toString());
	}
	else {
	fbp.setHistoryStatus("NA");
	}
	
	
	if (object[21]!=null) {
	if(!object[20].toString().equalsIgnoreCase("Escalated"))
	fbp.setResolve_by(object[21].toString());
	else
	fbp.setResolve_by("");
	}
	else {
	fbp.setResolve_by("NA");
	}
	
	 
    	if (object[22]!=null && !object[22].toString().equals("")) {
    	
    	fbp.setAddressingTime(object[22].toString());
    	}
    	else {
    	fbp.setAddressingTime("NA");
    	}
	
	if (object[23]!=null) {
	fbp.setEscalation_date(object[23].toString());
	}
	else {
	fbp.setEscalation_date("NA");
	}
	
	if (object[24]!=null) {
	fbp.setEscalation_time(object[24].toString());
	}
	else {
	fbp.setEscalation_time("NA");
	}
	
	if (object[25]!=null) {
	fbp.setFeedback_to_subdept(object[25].toString());
	}
	else {
	fbp.setFeedback_to_subdept("NA");
	}
	if (object[26]!=null) {
	fbp.setEscalation_level(object[26].toString());
	}
	else {
	fbp.setEscalation_level("NA");
	}
	}
	else if (status.equalsIgnoreCase("Resolved")) {
	if (object[17]!=null) {
	fbp.setResolve_date(DateUtil.convertDateToIndianFormat(object[17].toString()));
	}
	else {
	fbp.setResolve_date("NA");
	}
	
	if (object[18]!=null) {
	if((object[18].toString().length()>6))
	fbp.setResolve_time(object[18].toString().substring(0, 5));
	else
	fbp.setResolve_time(object[18].toString());
	}
	else {
	fbp.setResolve_time("NA");
	}
	
	if (object[19]!=null) {
	fbp.setResolve_duration(object[19].toString());
	}
	else {
	fbp.setResolve_duration("NA");
	}
	
	if (object[20]==null) {
	if(object[22].toString().equalsIgnoreCase("Escalated"))
	fbp.setResolve_remark(object[27].toString()+":"+object[21].toString());
	 
	}
	else {
	fbp.setResolve_remark(object[20].toString());
	}
	
	if (object[21]!=null) {
	if(!object[22].toString().equalsIgnoreCase("Escalated"))
	fbp.setResolve_by(object[21].toString());
	else
	fbp.setResolve_by("");
	
	}
	else {
	fbp.setResolve_by("NA");
	}
	if (object[22]!=null) {
	if(object[22].toString().equalsIgnoreCase("Snooze"))
	fbp.setHistoryStatus("Parked");
	else if(object[22].toString().equalsIgnoreCase("Transfer"))
	fbp.setHistoryStatus("Re-assign");
	else if(object[22].toString().equalsIgnoreCase("Re-OpenedR"))
	fbp.setHistoryStatus("Re-open");
	else
	fbp.setHistoryStatus(object[22].toString());
	}
	else {
	fbp.setHistoryStatus("NA");
	}
	
	 	if (object[23]!=null && !object[23].toString().equals("")) {
    	
    	fbp.setAddressingTime(object[23].toString());
    	}
    	else {
    	fbp.setAddressingTime("NA");
    	}
	
	if (object[24]!=null) {
	fbp.setEscalation_date(object[24].toString());
	}
	else {
	fbp.setEscalation_date("NA");
	}
	
	if (object[25]!=null) {
	fbp.setEscalation_time(object[25].toString());
	}
	else {
	fbp.setEscalation_time("NA");
	}
	
	if (object[26]!=null) {
	fbp.setFeedback_to_subdept(object[26].toString());
	}
	else {
	fbp.setFeedback_to_subdept("NA");
	}
	if (object[27]!=null) {
	fbp.setEscalation_level(object[27].toString());
	}
	else {
	fbp.setEscalation_level("NA");
	}
	}
	else if (status.equalsIgnoreCase("Snooze")) {
	
	 	if (object[17]==null) {
	 	if(object[23].toString().equalsIgnoreCase("Escalated"))
	fbp.setSn_reason(object[29].toString()+":"+object[24].toString());
	}
	else {
	fbp.setSn_reason(object[17].toString());
	}
	 	
	 	 
	
	if (object[18]!=null) {
	fbp.setSn_on_date(DateUtil.convertDateToIndianFormat(object[18].toString()));
	}
	else {
	fbp.setSn_on_date("NA");
	}

	if (object[19]!=null ) {
	if((object[19].toString().length()>6))
	fbp.setSn_on_time(object[19].toString().substring(0, 5));
	else
	fbp.setSn_on_time(object[19].toString());
	 
	}
	else {
	fbp.setSn_on_time("NA");
	}
	if (object[20]!=null) {
	fbp.setSn_upto_date(DateUtil.convertDateToIndianFormat(object[20].toString()));
	}
	else {
	fbp.setSn_upto_date("NA");
	}

	if (object[21]!=null) {
	fbp.setSn_upto_time(object[21].toString().substring(0, 5));
	}
	else {
	fbp.setSn_upto_time("NA");
	}
	if (object[22]!=null) {
	fbp.setSn_duration(object[22].toString());
	}
	else {
	fbp.setSn_duration("NA");
	}
	if (object[23]!=null) {
	if(object[23].toString().equalsIgnoreCase("Snooze"))
	fbp.setHistoryStatus("Parked");
	else if(object[23].toString().equalsIgnoreCase("Transfer"))
	fbp.setHistoryStatus("Re-assign");
	else if(object[23].toString().equalsIgnoreCase("Re-OpenedR"))
	fbp.setHistoryStatus("Re-open");
	else
	fbp.setHistoryStatus(object[23].toString());
	}
	else {
	fbp.setHistoryStatus("NA");
	}
	
	if (object[24]!=null) {
	if(!object[23].toString().equalsIgnoreCase("Escalated"))
	fbp.setResolve_by(object[24].toString());
	else
	fbp.setResolve_by("");
	
	}
	else {
	fbp.setResolve_by("NA");
	}
	if (object[29]!=null) {
	fbp.setEscalation_level(object[29].toString());
	}
	else {
	fbp.setEscalation_level("NA");
	}
	 
	
	}
	else if (status.equalsIgnoreCase("High Priority")) {
	if (object[17]!=null) {
	fbp.setHp_date(DateUtil.convertDateToIndianFormat(object[17].toString()));
	}
	else {
	fbp.setHp_date("NA");
	}
	
	if (object[18]!=null) {
	if((object[18].toString().length()>6))
	fbp.setHp_time(object[18].toString().substring(0, 5));
	else
	fbp.setHp_time(object[18].toString());
	
	}
	else {
	fbp.setHp_time("NA");
	}
	
	
	if (object[19]==null) {
	if(object[20].toString().equalsIgnoreCase("Escalated"))
	fbp.setHp_reason(object[26].toString()+":"+object[21].toString());
	}
	else {
	fbp.setHp_reason(object[19].toString());
	}
	 
	
	
	if (object[20]!=null) {
	if(object[20].toString().equalsIgnoreCase("Snooze"))
	fbp.setHistoryStatus("Parked");
	else if(object[20].toString().equalsIgnoreCase("Transfer"))
	fbp.setHistoryStatus("Re-assign");
	else if(object[20].toString().equalsIgnoreCase("Re-OpenedR"))
	fbp.setHistoryStatus("Re-open");
	else
	fbp.setHistoryStatus(object[20].toString());
	}
	else {
	fbp.setHistoryStatus("NA");
	}
	
	
	if (object[21]!=null) {
	if(!object[20].toString().equalsIgnoreCase("Escalated"))
	fbp.setResolve_by(object[21].toString());
	else
	fbp.setResolve_by("");
	}
	else {
	fbp.setResolve_by("NA");
	}
	
	 
	
	
	}
	else if (status.equalsIgnoreCase("Ignore")) {
	if (object[17]!=null) {
	fbp.setIg_date(DateUtil.convertDateToIndianFormat(object[17].toString()));
	}
	else {
	fbp.setIg_date("NA");
	}
	
	if (object[18]!=null) {
	if((object[18].toString().length()>6))
	fbp.setIg_time(object[18].toString().substring(0, 5));
	else
	fbp.setIg_time(object[18].toString());
	
	}
	else {
	fbp.setIg_time("NA");
	}
	

	if (object[19]==null) {
	if(object[20].toString().equalsIgnoreCase("Escalated"))
	fbp.setIg_reason(object[26].toString()+":"+object[21].toString());
	}
	else {
	fbp.setIg_reason(object[19].toString());
	}
	 
	if (object[20]!=null) {
	if(object[20].toString().equalsIgnoreCase("Snooze"))
	fbp.setHistoryStatus("Parked");
	else
	fbp.setHistoryStatus(object[20].toString());
	}
	else {
	fbp.setHistoryStatus("NA");
	}
	
	if (object[21]!=null) {
	if(!object[20].toString().equalsIgnoreCase("Escalated"))
	fbp.setResolve_by(object[21].toString());
	else
	fbp.setResolve_by("");
	}
	else {
	fbp.setResolve_by("NA");
	}
	 
	
	}
	else if (status.equalsIgnoreCase("Re-assign")) {
	if (object[17]!=null) {
	fbp.setReassign_date(DateUtil.convertDateToIndianFormat(object[17].toString()));
	}
	else {
	fbp.setReassign_date("NA");
	}
	
	if (object[18]!=null) {
	if((object[18].toString().length()>6))
	fbp.setReassign_time(object[18].toString().substring(0, 5));
	else
	fbp.setReassign_time(object[18].toString());
	
	}
	else {
	fbp.setReassign_time("NA");
	}
	

	if (object[19]==null) {
	if(object[20].toString().equalsIgnoreCase("Escalated"))
	fbp.setReassign_reason(object[26].toString()+":"+object[21].toString());
	}
	else {
	fbp.setReassign_reason(object[19].toString());
	}
	 
	if (object[20]!=null) {
	if(object[20].toString().equalsIgnoreCase("Snooze"))
	fbp.setHistoryStatus("Parked");
	else if(object[20].toString().equalsIgnoreCase("Transfer"))
	fbp.setHistoryStatus("Re-assign");
	else if(object[20].toString().equalsIgnoreCase("Re-OpenedR"))
	fbp.setHistoryStatus("Re-open");
	else
	fbp.setHistoryStatus(object[20].toString());
	}
	else {
	fbp.setHistoryStatus("NA");
	}
	if (object[21]!=null) {
	if(!object[20].toString().equalsIgnoreCase("Escalated"))
	fbp.setResolve_by(object[21].toString());
	else
	fbp.setResolve_by("");
	}
	else {
	fbp.setResolve_by("NA");
	}
	}
	else if (status.equalsIgnoreCase("Re-Opened") || status.equalsIgnoreCase("Re-OpenedR")) {
	if (object[17]!=null) {
	 	  	fbp.setReopen_date(DateUtil.convertDateToIndianFormat(object[17].toString()));
	 	//System.out.println("Status : "+object[20].toString()+"  Date : "+DateUtil.convertDateToIndianFormat(object[17].toString()));
	 	}
	else {
	fbp.setReopen_date("NA");
	}
	
	if (object[18]!=null) {
	if((object[18].toString().length()>6))
	{
	  	fbp.setReopen_time(object[18].toString().substring(0, 5));
	//System.out.println("Status : "+object[20].toString()+"  Time : "+object[18].toString().substring(0, 5));
	 	}
	
	else 
	{
	
	fbp.setReopen_time(object[18].toString());
	//System.out.println(object[18].toString());
	 	}
	 
	}
	else {
	fbp.setReopen_time("NA");
	}
	

	if (object[19]==null) {
	if(object[20].toString().equalsIgnoreCase("Escalated"))
	fbp.setReopen_reason(object[26].toString()+":"+object[21].toString());
	}
	else {
	fbp.setReopen_reason(object[19].toString());
	}
	 
	if (object[20]!=null) {
	if(object[20].toString().equalsIgnoreCase("Snooze"))
	fbp.setHistoryStatus("Parked");
	else if(object[20].toString().equalsIgnoreCase("Transfer"))
	fbp.setHistoryStatus("Re-assign");
	else if(object[20].toString().equalsIgnoreCase("Re-OpenedR"))
	fbp.setHistoryStatus("Re-open");
	else
	
	fbp.setHistoryStatus(object[20].toString());
	}
	else {
	fbp.setHistoryStatus("NA");
	}
	if (object[21]!=null) {
	if(!object[20].toString().equalsIgnoreCase("Escalated"))
	fbp.setResolve_by(object[21].toString());
	else
	fbp.setResolve_by("");
	}
	else {
	fbp.setResolve_by("NA");
	}
	}
	
	if (status.equalsIgnoreCase("High Priority") || status.equalsIgnoreCase("Ignore") || status.equalsIgnoreCase("Re-assign") || status.equalsIgnoreCase("Re-Opened") || status.equalsIgnoreCase("Re-OpenedR")) {
                    if (object[22]!=null && !object[22].toString().equals("")) {
    	
    	fbp.setAddressingTime(object[22].toString());
    	}
    	else {
    	fbp.setAddressingTime("NA");
    	}
	
	if (object[23]!=null) {
	fbp.setEscalation_date(object[23].toString());
	}
	else {
	fbp.setEscalation_date("NA");
	}
	
	if (object[24]!=null) {
	fbp.setEscalation_time(object[24].toString());
	}
	else {
	fbp.setEscalation_time("NA");
	}
	
	if (object[25]!=null) {
	fbp.setFeedback_to_subdept(object[25].toString());
	}
	else {
	fbp.setFeedback_to_subdept("NA");
	}
	if (object[26]!=null) {
	fbp.setEscalation_level(object[26].toString());
	}
	else {
	fbp.setEscalation_level("NA");
	}
	}
	fbpList.add(fbp);	
	}
	}
	return fbpList;
	}
	@SuppressWarnings("unchecked")
	public List getReportToData(String date, String time, SessionFactory connection)
	{

	List reportdatalist = new ArrayList();
	StringBuffer query = new StringBuffer();
	query.append("select report_conf.id,report_conf.report_level,report_conf.dept,report_conf.sms,report_conf.mail,report_conf.report_type,report_conf.module,emp.empName,emp.mobOne,emp.emailIdOne,report_conf.email_subject,dept.deptName,report_conf.allot_to,report_conf.register_by,report_conf.ticket_status from report_configuration as report_conf ");
	query.append(" left join employee_basic as emp on report_conf.empId=emp.id ");
	query.append(" left join department as dept on report_conf.dept=dept.id ");
	query.append(" where report_conf.report_date='" + date + "' and report_conf.report_time<='" + time + "'");
	// System.out.println(""+query);
	Session session = null;
	Transaction transaction = null;
	try
	{
	session = connection.openSession();
	transaction = session.beginTransaction();
	reportdatalist = session.createSQLQuery(query.toString()).list();
	transaction.commit();
	} catch (Exception ex)
	{
	transaction.rollback();
	} finally
	{
	try
	{
	session.flush();
	session.close();
	} catch (Exception e)
	{

	}
	}
	return reportdatalist;
	}
	@SuppressWarnings("unchecked")
	public String getFloorStatus( String deptid,SessionFactory connection)
	 {
	String  flrStatus = "";
	StringBuffer query= new StringBuffer();
	List flrList = new ArrayList();
	query.append("select floor_status from dept_ticket_series_conf where deptName='"+deptid+"'");
	Session session = null;  
	    Transaction transaction = null;  
	try 
	  {  
	 session=connection.openSession(); 
	 transaction = session.beginTransaction(); 
	 flrList=session.createSQLQuery(query.toString()).list();  
	 transaction.commit(); 
	  }
	    catch (Exception ex)
	  {
	 transaction.rollback();
	  } 
	finally{
	try {
	session.flush();
	session.close();
	} catch (Exception e) {
	
	}
	}
	if(flrList!=null && flrList.size()>0)
        {
	flrStatus=flrList.get(0).toString();
        }
	return flrStatus;
	 }
	
	
	@SuppressWarnings("unchecked")
	public String getDeptId( String subdept,SessionFactory connection)
	 {
	String  dept = "";
	StringBuffer query= new StringBuffer();
	List flrList = new ArrayList();
	query.append("select deptid from subdepartment where id='"+subdept+"'");
	
	//System.out.println("DEPIID : "+query.toString());
	Session session = null;  
	    Transaction transaction = null;  
	try 
	  {  
	 session=connection.openSession(); 
	 transaction = session.beginTransaction(); 
	 flrList=session.createSQLQuery(query.toString()).list();  
	 transaction.commit(); 
	  }
	    catch (Exception ex)
	  {
	 transaction.rollback();
	  } 
	finally{
	try {
	session.flush();
	session.close();
	} catch (Exception e) {
	
	}
	}
	if(flrList!=null && flrList.size()>0)
        {
	dept=flrList.get(0).toString();
        }
	return dept;
	 }
	
	@SuppressWarnings("unchecked")
	public List getFeedbackList(String deptId,String subDeptId, String feedTypeId, String categoryId,String viewtype,SessionFactory connection)
	 {
	List  categorylist = new ArrayList();
	StringBuffer str  =new StringBuffer();
	str.append("select feedtype.fbType,catg.categoryName,subcatg.subCategoryName,subcatg.feedBreif,subcatg.addressingTime,subcatg.resolutionTime,subcatg.escalationDuration,subcatg.escalationLevel,subcatg.needEsc,subcatg.severity from feedback_type as feedtype");
	if (viewtype.equals("SD")) {
	str.append(" inner join subdepartment as subdept on feedtype.dept_subdept=subdept.id ");
	}
	else if (viewtype.equals("D")) {
	str.append(" inner join department as dept on feedtype.dept_subdept=dept.id ");
	}
	str.append(" inner join feedback_category as catg on feedtype.id=catg.fbType");
	str.append(" inner join feedback_subcategory as subcatg on catg.id=subcatg.categoryName");
	
	str.append(" where ");
	
	if (viewtype.equalsIgnoreCase("D")) {
	if (feedTypeId!=null && !feedTypeId.equals("") && !feedTypeId.equals("-1")) {
	str.append(" feedtype.id ="+feedTypeId+" ");
	}
	if (categoryId!=null && !categoryId.equals("") && !categoryId.equals("-1")) {
	str.append(" and catg.id ="+categoryId+"");
	}
	}
	else if (viewtype.equalsIgnoreCase("SD")) {
	if (subDeptId!=null && !subDeptId.equals("") && !subDeptId.equals("-1")) {
	str.append(" subdept.id ="+subDeptId+"");
	}
	if (feedTypeId!=null && !feedTypeId.equals("") && !feedTypeId.equals("-1")) {
	str.append(" and feedtype.id ="+feedTypeId+" ");
	}
	if (categoryId!=null && !categoryId.equals("") && !categoryId.equals("-1")) {
	str.append(" and catg.id ="+categoryId+"");
	}
	}
	Session session = null;  
	    Transaction transaction = null;  
	try 
	  {  
	 session=connection.getCurrentSession(); 
	 transaction = session.beginTransaction(); 
	 categorylist=session.createSQLQuery(str.toString()).list();  
	 transaction.commit(); 
	  }
	    catch (Exception ex)
	  {
	 transaction.rollback();
	  } 
	
	    
	return categorylist;
	 }
	
	@SuppressWarnings("unchecked")
	public List getEscalation( String deptLevel, String dept, String levelname,SessionFactory connection)
	 {
	String shiftname =DateUtil.getCurrentDateUSFormat().substring(8, 10)+"_date";
	List  escalation = new ArrayList();
	String query="select emp.empName,emp.mobOne,emp.emailIdOne,dept.deptName from employee_basic as emp"
	+" inner join designation desg on emp.designation = desg.id"
	+" inner join subdepartment sub_dept on emp.subdept = sub_dept.id"
	+" inner join department dept on sub_dept.deptid = dept.id"
                    +" inner join roaster_conf roaster on emp.empId = roaster.empId"
                    +" inner join shift_conf shift on sub_dept.id = shift.dept_subdept"
	            +" where shift.shiftName="+shiftname+" and emp.status='0' and roaster.attendance='Present' and roaster.status='Active' and desg.levelofhierarchy='"+levelname+"' and dept.id='"+dept+"' and roaster.deptHierarchy='"+deptLevel+"'";
	            Session session = null;  
	    Transaction transaction = null;  
	    try 
	     {  
	            session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	escalation=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	    catch (Exception ex)
	 {
	transaction.rollback();
	 } 
	
	return escalation;
	    }
	
	public String getNewDate(String newDateType)
	 {
	String data="";
	 try 
	{
	 if (newDateType.equals("D")) {
	 data = DateUtil.getNewDate("day", 1, DateUtil.getCurrentDateUSFormat());
	}
	else if (newDateType.equals("W")) {
	data = DateUtil.getNewDate("day", 7, DateUtil.getCurrentDateUSFormat());
	}
	else if (newDateType.equals("M")) {
	data = DateUtil.getNewDate("month", 1, DateUtil.getCurrentDateUSFormat());
	}
	else if (newDateType.equals("Q")) {
	data = DateUtil.getNewDate("month", 3, DateUtil.getCurrentDateUSFormat());
	}
	else if (newDateType.equals("H")) {
	data = DateUtil.getNewDate("month", 6, DateUtil.getCurrentDateUSFormat());
	}
             else if (newDateType.equals("Y")) {
            	 data = DateUtil.getNewDate("year", 1, DateUtil.getCurrentDateUSFormat());
	}
	} catch (Exception e) {
	e.printStackTrace();
	} 
	return data;
	 }
	
	@SuppressWarnings("unchecked")
	public String getDeptName(String subdept_dept, String deptLevel, SessionFactory connection)
	 {
	 String data="",query="";
	 List list =null;
	
	 if (deptLevel.equals("2")) {
	 query = "select deptName from department where id=(select deptid from subdepartment where id="+subdept_dept+")";
	}
	
            else if (deptLevel.equals("1")) {
            	query = "select deptName from department where id="+subdept_dept+"";
	}
	    Session session = null;  
	Transaction transaction = null;  
	 try 
	{
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	list=session.createSQLQuery(query).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	{
	   transaction.rollback();
	} 
	
	 if(list!=null && list.size()>0)
	            {
	 data=list.get(0).toString();
	            }
	return data;
	 }
	
	@SuppressWarnings("unchecked")
	public List getTicketCounters(String reportLevel, String reportType, boolean cd_pd, String subdept_dept, String deptLevel, String allotTo, String registerBy, SessionFactory connection)
	{
	List counterList = new ArrayList();
	StringBuilder qry = new StringBuilder();
	qry.append("select count(*),feedback.status from feedback_status_new as feedback");
	if (reportLevel.equals("2"))
	{
	if (cd_pd)
	{
	if (reportType != null && !reportType.equals("") && reportType.equals("D"))
	{
	qry.append(" where feedback.open_date='" + DateUtil.getCurrentDateUSFormat() + "' ");
	} else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("W"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' ");
	} else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("M"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'  ");
	} else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("Q"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'  ");
	} else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("H"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' ");
	}
	} else
	{
	qry.append(" where feedback.open_date<'" + DateUtil.getCurrentDateUSFormat() + "' ");
	}
	} else if (reportLevel.equals("1"))
	{
	qry.append(" inner join department as dept on feedback.to_dept_subdept=dept.id");
	if (cd_pd)
	{
	if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("D"))
	{
	qry.append(" where feedback.open_date='" + DateUtil.getCurrentDateUSFormat() + "' and dept.id=" + subdept_dept + " ");
	} else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("W"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' and dept.id=" + subdept_dept + " ");
	} else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("M"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' and dept.id=" + subdept_dept + " ");
	} else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("Q"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' and dept.id=" + subdept_dept + " ");
	} else if (reportType != null && !reportType.equals("") && reportType.equalsIgnoreCase("H"))
	{
	qry.append(" where feedback.open_date between '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "' and dept.id=" + subdept_dept + " ");
	}
	} else
	{
	qry.append(" where feedback.open_date<'" + DateUtil.getCurrentDateUSFormat() + "' and dept.id=" + subdept_dept + " ");
	}
	}
	if (allotTo != null && !allotTo.equalsIgnoreCase("") && registerBy != null && !registerBy.equalsIgnoreCase(""))
	{
	qry.append(" AND (feedback.allot_to IN(" + allotTo + ") || feedback.feed_registerby IN(" + registerBy + "))");
	}
	else if(allotTo != null && !allotTo.equalsIgnoreCase(""))
	{
	qry.append(" AND feedback.allot_to IN(" + allotTo + ")");
	}
	else if(registerBy != null && !registerBy.equalsIgnoreCase(""))
	{
	qry.append(" AND feedback.feed_registerby IN(" + registerBy + ")");
	}
	qry.append(" group by feedback.status ");
	
	//System.out.println("current day:::::::" + qry);
	Session session = null;
	Transaction transaction = null;
	try
	{
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	counterList = session.createSQLQuery(qry.toString()).list();
	transaction.commit();
	} catch (Exception ex)
	{
	transaction.rollback();
	}

	return counterList;
	}
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> getTicketData(String reportLevel, String reportType, boolean cd_pd, String status, String subdept_dept, String deptLevel, String allotTo, String registerBy, SessionFactory connection)
	{
	List dataList = new ArrayList();
	List<FeedbackPojo> report = new ArrayList<FeedbackPojo>();
	StringBuilder query = new StringBuilder("");
	String reportFor = "", reportRange = "";
	query.append("select distinct feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,floor.roomno,history.action_date as opendate,history.action_time as opentime,feedback.level,feedback.status as crntstatus,feedback.feed_registerby");

	// Block for get resolved data
	if (status.equalsIgnoreCase("Resolved"))
	{
	query.append(",history.action_date as ad,history.action_time as at,history.action_duration,history.action_remark, emp1.empName as resolve_by,history.status as hisstatus");
	}
	// Block for get Snooze data
	else if (status.equalsIgnoreCase("Snooze"))
	{
	query.append(",history.action_remark,history.action_date as ad,history.action_time as at,history.sn_upto_date,history.sn_upto_time,history.action_duration,history.status as hisstatus, emp1.empName as resolve_by");
	}
	// Block for get High Priority data
	else if (status.equalsIgnoreCase("High Priority"))
	{
	query.append(",history.action_date as ad,history.action_time as at,history.action_remark,history.status as hisstatus, emp1.empName as resolve_by");
	}
	// Block for get ignore data
	else if (status.equalsIgnoreCase("Ignore"))
	{
	query.append(",history.action_date as ad,history.action_time as at,history.action_remark,history.status as hisstatus, emp1.empName as resolve_by");
	} else if (status.equalsIgnoreCase("Re-assign"))
	{
	query.append(",history.action_date as ad,history.action_time as at,history.action_remark,history.status as hisstatus, emp1.empName as resolve_by");
	} else if (status.equalsIgnoreCase("Re-Opened"))
	{
	query.append(",history.action_date as ad,history.action_time as at,history.action_remark,history.status as hisstatus, emp1.empName as resolve_by");
	} else if (status.equalsIgnoreCase("Pending"))
	{
	query.append(",history.action_date as ad,history.action_time as at,history.action_remark,history.status as hisstatus, emp1.empName as resolve_by");
	} else if (status.equalsIgnoreCase("Re-OpenedR"))
	{
	query.append(",history.action_date as ad,history.action_time as at,history.action_remark,history.status as hisstatus, emp1.empName as resolve_by");
	}

	/*
	 * if (status.equalsIgnoreCase("Resolved") ||
	 * status.equalsIgnoreCase("Pending")) { query.append(
	 * ",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration"
	 * ); }
	 */query.append(",feedback.Addr_date_time,feedback.escalation_date,feedback.escalation_time,subdept2.subdeptname as tosubdept,history.escalation_level");
	query.append(" from feedback_status_new as feedback");

	query.append(" left join employee_basic emp on feedback.allot_to= emp.id");
	// Applying inner join at sub department level
	query.append(" left join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
	query.append(" left join feedback_category catg on subcatg.categoryName = catg.id ");
	query.append(" left join feedback_type feedtype on  catg.fbType = feedtype.id");
	query.append(" left join subdepartment subdept2 on feedtype.dept_subdept= subdept2.id ");
	query.append(" left join department dept2 on subdept2.deptid= dept2.id  ");
	query.append(" left join department dept1 on feedback.by_dept_subdept= dept1.id ");
	query.append(" left join feedback_status_history history on history.feedId= feedback.id ");
	query.append(" left join floor_room_detail AS floor ON floor.id = feedback.location");
	query.append(" left join employee_basic emp1 on history.action_by= emp1.id");

	// getting data of current day
	if (cd_pd)
	{
	 if( status.equalsIgnoreCase("Re-OpenedR"))
	 {
	 	// query.append("  where feedback.status in ('Resolved','Pending','ignore','Re-assign','High-Priority','Snooze')");
	 query.append("  where history.status='Re-OpenedR'");
	 
	 }
	 else
	query.append(" where feedback.status='" + status + "' ");
	if (reportType != null && !reportType.equals("") && reportType.equals("D"))
	{
	query.append(" and feedback.open_date='" + DateUtil.getCurrentDateUSFormat() + "'");
	reportFor = "Daily";

	} else if (reportType != null && !reportType.equals("") && reportType.equals("W"))
	{
	query.append(" and feedback.open_date between '" + DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	reportFor = "Weekly";
	reportRange = DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat());
	} else if (reportType != null && !reportType.equals("") && reportType.equals("M"))
	{
	query.append(" and feedback.open_date between '" + DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	reportFor = "Monthly";
	reportRange = DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat());

	} else if (reportType != null && !reportType.equals("") && reportType.equals("Q"))
	{
	query.append(" and feedback.open_date between '" + DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	reportFor = " Quater Monthly";
	reportRange = DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat());

	} else if (reportType != null && !reportType.equals("") && reportType.equals("H"))
	{
	query.append(" and feedback.open_date between '" + DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat()) + "' and '" + DateUtil.getCurrentDateUSFormat() + "'");
	reportFor = " Half Yearly";
	reportRange = DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat());

	}

	if (reportLevel.equals("1"))
	{
	query.append(" and dept2.id=" + subdept_dept + "");
	}
	}
	// End of If Block for getting the data for only the current date in
	// both case for Resolved OR All

	// Else Block for getting the data for only the previous date
	else
	{
	if (reportLevel.equals("2"))
	{
	query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'" + DateUtil.getCurrentDateUSFormat() + "'");
	} else if (reportLevel.equals("1"))
	{
	query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'" + DateUtil.getCurrentDateUSFormat() + "' and dept2.id=" + subdept_dept + "");
	}
	}
	if (allotTo != null && !allotTo.equalsIgnoreCase("") && registerBy != null && !registerBy.equalsIgnoreCase(""))
	{
	query.append(" AND (feedback.allot_to IN(" + allotTo + ") || feedback.feed_registerby IN(" + registerBy + "))");
	}
	else if(allotTo != null && !allotTo.equalsIgnoreCase(""))
	{
	query.append(" AND feedback.allot_to IN(" + allotTo + ")");
	}
	else if(registerBy != null && !registerBy.equalsIgnoreCase(""))
	{
	query.append(" AND feedback.feed_registerby IN(" + registerBy + ")");
	}

	query.append(" AND feedback.id=history.feedId order by feedback.ticket_no asc ");

	//System.out.println("CFD Querry >>> " + query);

	Session session = null;
	Transaction transaction = null;
	try
	{
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	dataList = session.createSQLQuery(query.toString()).list();
	transaction.commit();
	} catch (Exception ex)
	{
	transaction.rollback();
	ex.printStackTrace();
	}

	if (dataList != null && dataList.size() > 0)
	{
	report = new HelpdeskUniversalHelper().setFeedbackDataforReport(dataList, status, reportFor, reportRange);
	}
	return report;
	}
	/*
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List<FeedbackPojo> getTicketData4Report( String reportLevel,String reportType, boolean cd_pd, String  status,String subdept_dept,String deptLevel,SessionFactory connection)
	 {
	  List  dataList = new ArrayList();
	  List<FeedbackPojo> report = new ArrayList<FeedbackPojo>();
	  StringBuilder query = new StringBuilder("");
	query.append("select feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,feedback.location,feedback.open_date,feedback.open_time,feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby");
        	
        	        // Block for get resolved data
        	        if (status.equalsIgnoreCase("Resolved")) {
        	            query.append(",feedback.resolve_date,feedback.resolve_time,feedback.resolve_duration,feedback.resolve_remark, emp1.empName as resolve_by,feedback.spare_used");
	        }
        	        // Block for get Snooze data
        	        else if (status.equalsIgnoreCase("Snooze")) {
        	        	query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
	}
        	        // Block for get High Priority data
        	        else if (status.equalsIgnoreCase("High Priority")) {
        	        	query.append(",feedback.hp_date,feedback.hp_time,feedback.hp_reason");
	}
        	        // Block for get ignore data
        	        else if (status.equalsIgnoreCase("Ignore")) {
        	        	query.append(",feedback.ig_date,feedback.ig_time,feedback.ig_reason");
	}
        	        // getting the data regarding Sub Department at Level 2
	        	if (deptLevel.equals("2")) {
        	            query.append(",subdept1.subdeptname as bysubdept,subdept2.subdeptname as tosubdept");
        	        }
	        	
        	        query.append(" from feedback_status as feedback");
        	
        	        query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
        	        // Applying inner join at sub department level
        	        if (deptLevel.equals("2")) {
	            	query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
	            	query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
	            	query.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
	            	query.append(" inner join subdepartment subdept2 on feedtype.dept_subdept= subdept2.id "); 
	            	query.append(" inner join department dept2 on subdept2.deptid= dept2.id  ");
	            	query.append(" inner join subdepartment subdept1 on feedback.by_dept_subdept= subdept1.id ");
	            	query.append(" inner join department dept1 on subdept1.deptid= dept1.id  "); 
            	        }
        	        // Applying inner join at Department level
        	       else if (deptLevel.equals("1")) {
	            	query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
	            	query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
	            	query.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
	            	query.append(" inner join department dept2 on subdept2.deptid= dept2.id  ");
	            	query.append(" inner join department dept1 on subdept1.deptid= dept1.id  "); 
	          }
        	
        	        // Apply inner join for getting the data for Resolved Ticket
	        	if (status.equalsIgnoreCase("Resolved")) {
	        	query.append(" inner join employee_basic emp1 on feedback.resolve_by= emp1.id"); 
	}
	
	        	// getting data of current day
	if (cd_pd) {
	query.append(" where feedback.status='"+status+"' ");
	if (reportType!=null && !reportType.equals("") && reportType.equals("D")) {
	query.append(" and feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"'");
	}
	else if (reportType!=null && !reportType.equals("") && reportType.equals("W")) {
	query.append(" and feedback.open_date between '"+DateUtil.getNewDate("week", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");
	}
	else if (reportType!=null && !reportType.equals("") && reportType.equals("M")) {
	query.append(" and feedback.open_date between '"+DateUtil.getNewDate("month", -1, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");	
	}
	else if (reportType!=null && !reportType.equals("") && reportType.equals("Q")) {
	query.append(" and feedback.open_date between '"+DateUtil.getNewDate("month", -3, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");
	}
	else if (reportType!=null && !reportType.equals("") && reportType.equals("H")) {
	query.append(" and feedback.open_date between '"+DateUtil.getNewDate("month", -6, DateUtil.getCurrentDateUSFormat())+"' and '"+DateUtil.getCurrentDateUSFormat()+"'");
	}
	
	if (reportLevel.equals("1")) {
	 query.append(" and feedback.to_dept_subdept in (select id from subdepartment where deptid=(select deptid from subdepartment where id="+subdept_dept+")) and feedback.deptHierarchy="+deptLevel+""); 
	}
	}
	        // End of If Block for getting the data for only the current date in both case for Resolved OR All
	
	// Else Block for getting the data for only the previous date
	else {
	if (reportLevel.equals("2")) {
	  query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'"+DateUtil.getCurrentDateUSFormat()+"'");
	}
	else if (reportLevel.equals("1")) {
	 query.append(" where feedback.status in ('Snooze', 'Pending', 'High Priority') and feedback.open_date<'"+DateUtil.getCurrentDateUSFormat()+"' and feedback.to_dept_subdept in (select id from subdepartment where deptid=(select deptid from subdepartment where id="+subdept_dept+")) and feedback.deptHierarchy="+deptLevel+""); 
	}
	}
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	dataList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{
	   transaction.rollback();
	} 
	
	if (dataList!=null && dataList.size()>0) {
	report = new HelpdeskUniversalHelper().setFeedbackDataforReport(dataList, status, deptLevel);
	}
	return report;
	 }
	
	
	
	
	
	*/
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getTicketData( String id,SessionFactory connection)
	 {
	    List  dataList = new ArrayList();
	    StringBuilder query = new StringBuilder("");
	query.append("select feedback.ticket_no,dept1.deptName as bydept,feedback.feed_by,feedback.feed_by_mobno,feedback.feed_by_emailid,dept2.deptName as todept,emp.empName,feedtype.fbType,catg.categoryName,subcatg.subCategoryName,feedback.feed_brief,feedback.location,feedback.open_date,feedback.open_time,feedback.level,feedback.status,feedback.via_from,feedback.feed_registerby");
        query.append(",feedback.sn_reason,feedback.sn_on_date,feedback.sn_on_time,feedback.sn_upto_date,feedback.sn_upto_time,feedback.sn_duration");
        query.append(",feedback.hp_date,feedback.hp_time,feedback.hp_reason");
        query.append(",subdept2.subdeptname as tosubdept,feedback.escalation_date,feedback.escalation_time,feedback.Addr_date_time");
        query.append(" from feedback_status as feedback");
        query.append(" inner join employee_basic emp on feedback.allot_to= emp.id");
	query.append(" inner join feedback_subcategory subcatg on feedback.subCatg = subcatg.id");
	query.append(" inner join feedback_category catg on subcatg.categoryName = catg.id ");
	query.append(" inner join feedback_type feedtype on  catg.fbType = feedtype.id");
	query.append(" inner join subdepartment subdept2 on feedtype.dept_subdept= subdept2.id "); 
	query.append(" inner join department dept2 on subdept2.deptid= dept2.id  ");
	query.append(" inner join department dept1 on feedback.by_dept_subdept= dept1.id ");
	query.append(" where  feedback.id="+id+""); 
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	dataList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{
	   transaction.rollback();
	} 
	
	return dataList;
	 }
	
	 @SuppressWarnings("unchecked")
	public boolean isExist(String table,String field,String value,SessionFactory ConnectionName) {
	List  existList = null;
	boolean flag=false;
	    try 
	{
	    	StringBuilder query1=new StringBuilder("");
	query1.append("select * from "+table+" where "+field+"='"+value+"'");
	existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
	if(existList.size()>0)
	{
	flag=true;
	}
	}catch (Exception e) {
	flag=false;
	} 
	 return flag;
	}
	 
	 @SuppressWarnings("unchecked")
	public boolean isExist(String table,String field1,String value1,String field2,String value2,String field3,String value3,SessionFactory ConnectionName) {
	List  existList = null;
	boolean flag=false;
	    try 
	{
	    	StringBuilder query1=new StringBuilder("");
	    	query1.append("select * from "+table+" where "+field1+"='"+value1+"'");
	    	if (value2!=null && !value2.equals("")) {
	    	query1.append(" and "+field2+"='"+value2+"'");
	}
	    	if (value3!=null && !value3.equals("")) {
	    	query1.append(" and "+field3+"='"+value3+"'");
	}
	    	//System.out.println("AAAADDDDDDD : "+query1);
	existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
	if(existList.size()>0)
	{
	flag=true;
	}
	}catch (Exception e) {
	flag=false;
	} 
	 return flag;
	}
	 
	@SuppressWarnings("unchecked")
	public boolean isExist(String table,String field1,String value1,String field2,String value2,String field3,String value3,String field4,String value4,SessionFactory ConnectionName) {
	List  existList = null;
	boolean flag=false;
	    try 
	{
	    	StringBuilder query1=new StringBuilder("");
	    	query1.append("select * from "+table+" where "+field1+"='"+value1+"'");
	    	if (value2!=null && !value2.equals("")) {
	    	query1.append(" and "+field2+"='"+value2+"'");
	}
	    	if (value3!=null && !value3.equals("")) {
	    	query1.append(" and "+field3+"='"+value3+"'");
	}
	    	if (value4!=null && !value4.equals("")) {
	    	query1.append(" and "+field4+"='"+value4+"'");
	}
	existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
	if(existList.size()>0)
	{
	flag=true;
	}
	}catch (Exception e) {
	flag=false;
	} 
	 return flag;
	}
	
	@SuppressWarnings("unchecked")
	public String isExist(String table,String field1,String value1,String field2,String value2,String field3,String value3,String field4,String value4,String field5,String value5,String field6,String value6,String field7,String value7,String field8,String value8,SessionFactory ConnectionName) {
	List  existList = null;
	//boolean flag=false;
	String ticket="NA";
	    try 
	{
	    	StringBuilder query1=new StringBuilder("");
	    	query1.append("select ticket_no from "+table+" where "+field1+"='"+value1+"'");
	    	if (value2!=null && !value2.equals("")) {
	    	query1.append(" and "+field2+"='"+value2+"'");
	}
	    	if (value3!=null && !value3.equals("")) {
	    	query1.append(" and "+field3+"='"+value3+"'");
	}
	    	if (value4!=null && !value4.equals("")) {
	    	query1.append(" and "+field4+"='"+value4+"'");
	}
	    	if (value5!=null && !value5.equals("")) {
	    	query1.append(" and "+field5+"='"+value5+"'");
	}
	    	if (value6!=null && !value6.equals("")) {
	    	query1.append(" and "+field6+"='"+value6+"'");
	}
	    	if (value7!=null && !value7.equals("") && field7.equalsIgnoreCase("open_time"))
	    	{
	    	query1.append(" and "+field7+" like '"+value7+"%'");
	}
	    	else if(value7!=null && !value7.equals(""))
	    	{
	    	query1.append(" and "+field7+"='"+value7+"'");
	    	}
	    	if (value8!=null && !value8.equals("")) {
	    	query1.append(" and "+field8+"='"+value8+"'");
	}
	existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
	if(existList.size()>0)
	{
	ticket=existList.get(0).toString();
	}
	}catch (Exception e) {
	//System.out.println("Error in checking exist record!!! ");
	//flag=false;
	} 
	 return ticket;
	}
	 
	 @SuppressWarnings("unchecked")
	public boolean checkDept_SubDept(String uid,SessionFactory ConnectionName) {
	List  existList = null;
	boolean flag=false;
	    try 
	{
	    	StringBuilder query1=new StringBuilder("");
	query1.append("select count(*) from feedback_type where dept_subdept=(select subdept from employee_basic where useraccountid="+uid+")");
	existList=new createTable().executeAllSelectQuery(query1.toString(),ConnectionName);
	if(existList.size()>0)
	{flag=true;}
	}catch (Exception e) {
	flag=false;
	} 
	 return flag;
	}
	
	@SuppressWarnings("unchecked")
	public List getSubDeptList( String subdeptid,SessionFactory connection)
	 {
	List  subdeptList = new ArrayList();
	String query="";
	    query="select id from subdepartment where deptid=(select deptid from subdepartment where id="+subdeptid+"";
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	subdeptList=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return subdeptList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getSubDeptListByUID(String loginType,String dept_id,String empName,SessionFactory connection)
	 {
	List  subdeptList = new ArrayList();
	StringBuffer query=new StringBuffer();
	if (loginType.equalsIgnoreCase("H")) {
	query.append("select distinct subdept.id,subdept.subdeptname from subdepartment as subdept ");
	query.append(" inner join feedback_type fbtype on subdept.id= fbtype.dept_subdept ");
	query.append(" inner join department dept on subdept.deptid= dept.id  ");
	query.append(" where dept.id="+dept_id+" order by subdept.subdeptname ");
	}
	else if (loginType.equalsIgnoreCase("M")) 
	{
	query.append("select distinct dept.id,dept.deptName from department as dept");
	query.append(" inner join subdepartment as subdept on dept.id=subdept.deptid");
	query.append(" inner join feedback_type as feedtype on subdept.id=feedtype.dept_subdept");
	query.append(" where feedtype.hide_show='Active' and dept.deptName!='OCC' and  feedtype.moduleName='HDM' order by dept.deptName ");
	}
	else if (loginType.equalsIgnoreCase("N")) {
	query.append("select distinct subdept.id,subdept.subdeptname from feedback_status as feedstatus");
	query.append(" inner join subdepartment subdept on feedstatus.to_dept_subdept=subdept.id ");
	query.append(" where feedstatus.feed_by='"+empName+"'");
	}
	else if (loginType.equalsIgnoreCase("C")) {
	query.append("select distinct subcatg.id,subcatg.subCategoryName from feedback_subcategory as subcatg");
	query.append(" inner join  feedback_category catg on  catg.id=subcatg.categoryName");
	query.append(" inner join feedback_type fbtype on fbtype.id= catg.fbType");
	query.append(" where fbtype.dept_subdept='"+dept_id+"'");
	}
	//System.out.println("User wise Query Dept Subdept::"+query);
	    Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	subdeptList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return subdeptList;
	 }
	
	
	@SuppressWarnings("unchecked")
	public List getSubDeptListByUIDRetId(String loginType,String dept_id,String empName,SessionFactory connection)
	 {
	List  subdeptList = null;
	StringBuffer query=new StringBuffer();
	if (loginType.equalsIgnoreCase("H")) {
	query.append("select distinct subdept.id,subdept.subdeptname from subdepartment as subdept ");
	query.append(" inner join feedback_type fbtype on subdept.id= fbtype.dept_subdept ");
	query.append(" inner join department dept on subdept.deptid= dept.id  ");
	query.append(" where dept.id="+dept_id+" order by subdept.subdeptname ");
	}
	else if (loginType.equalsIgnoreCase("M")) 
	{
	query.append("select distinct subdept.id,subdept.subdeptname from subdepartment as subdept ");
	query.append(" inner join feedback_type as feedtype on subdept.id=feedtype.dept_subdept");
	query.append(" where feedtype.hide_show='Active' and feedtype.moduleName='HDM' order by subdept.subdeptname ");
	}
	else if (loginType.equalsIgnoreCase("N")) {
	query.append("select distinct subdept.id,subdept.subdeptname from feedback_status as feedstatus");
	query.append(" inner join subdepartment subdept on feedstatus.to_dept_subdept=subdept.id ");
	query.append(" where feedstatus.feed_by='"+empName+"'");
	}
	//System.out.println("User wise Query Dept Subdept::"+query);
	    Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	subdeptList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return subdeptList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getDistinctData(String table,String field,SessionFactory connection)
	 {
	List  distinctDataList = new ArrayList();
	StringBuffer query=new StringBuffer();
	query.append("select distinct "+field+" from "+table+" order by "+field+"");
	
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	distinctDataList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return distinctDataList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getEmployeeData( String dept_subdeptid,String deptLevel,String searchField, String searchString, String searchOper,SessionFactory connection)
	 {
	List  subdeptList = new ArrayList();
	StringBuffer str = new StringBuffer();
	if (deptLevel.equals("2")) {
	str.append("select emp.id,emp.empId,emp.empName,emp.mobOne,emp.emailIdOne,desg.levelofhierarchy,subdept.subdeptname from employee_basic as emp");
	str.append(" inner join designation as desg on desg.id=emp.designation");
	str.append(" inner join subdepartment as subdept on subdept.id=emp.subdept");
	str.append(" where emp.subdept in(select id from subdepartment where deptid=(select deptid from subdepartment where id="+dept_subdeptid+")) and emp.escalationId=0 and emp.status=0");
	  if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	{
	  str.append(" and");
          	  
	  if(searchOper.equalsIgnoreCase("eq"))
	{
	  str.append(" "+searchField+" = '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("cn"))
	{
	str.append(" "+searchField+" like '%"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("bw"))
	{
	str.append(" "+searchField+" like '"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("ne"))
	{
	str.append(" "+searchField+" <> '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("ew"))
	{
	str.append(" "+searchField+" like '%"+searchString+"'");
	}
	}
	  str.append(";");
	}
	else if (deptLevel.equals("1")) {
	str.append("select emp.id,emp.empId,emp.empName,emp.mobOne,emp.emailIdOne,desg.levelofhierarchy from employee_basic as emp");
	str.append(" inner join designation as desg on desg.id=emp.designation");
	str.append(" inner join department as dept on dept.id=emp.dept");
	str.append(" where emp.dept="+dept_subdeptid+" and emp.escalationId=0");
	 if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	{
	  str.append(" and");
       	  
	  if(searchOper.equalsIgnoreCase("eq"))
	{
	  str.append(" "+searchField+" = '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("cn"))
	{
	str.append(" "+searchField+" like '%"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("bw"))
	{
	str.append(" "+searchField+" like '"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("ne"))
	{
	str.append(" "+searchField+" <> '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("ew"))
	{
	str.append(" "+searchField+" like '%"+searchString+"'");
	}
	}
	  str.append(";");
	}
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	subdeptList=session.createSQLQuery(str.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return subdeptList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getContacts4Roaster( String dept_id,String deptLevel,String searchField, String searchString, String searchOper,String module ,String viewType,SessionFactory connection,String usert)
	 {
	List  empList = new ArrayList();
	StringBuffer str = new StringBuffer();
	str.append("select comp.id,comp.level");
	if (viewType!=null && !viewType.equals("") && viewType.equals("SD")) {
	str.append(",subdept.subdeptname");
	}
	else if (viewType!=null && !viewType.equals("") && viewType.equals("D")) {
	str.append(",dept.deptName");
	}
	
	str.append(",emp.empName,emp.mobOne,emp.emailIdOne");
	str.append(" from employee_basic as emp");
	
	str.append(" inner join compliance_contacts as comp on comp.emp_id=emp.id");
	if (viewType!=null && !viewType.equals("") && viewType.equals("SD")) {
	str.append(" inner join subdepartment as subdept on subdept.id=comp.forDept_id");
	str.append(" inner join department as dept on dept.id=subdept.deptid");
	}
	else if (viewType!=null && !viewType.equals("") && viewType.equals("D")) {
	str.append(" inner join department as dept on comp.forDept_id=dept.id");
	}
	str.append(" where ");
	
	if(usert!=null && !usert.equalsIgnoreCase("M"))
	{
	str.append(" dept.id='"+dept_id+"' and ");
	}
	
	str.append("  comp.work_status=0 and comp.moduleName='"+module+"' order by comp.level ");
	
	  if(searchField!=null && searchString!=null && !searchField.equalsIgnoreCase("") && !searchString.equalsIgnoreCase(""))
	{
	  str.append(" and");
          	  
	  if(searchOper.equalsIgnoreCase("eq"))
	{
	  str.append(" "+searchField+" = '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("cn"))
	{
	str.append(" "+searchField+" like '%"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("bw"))
	{
	str.append(" "+searchField+" like '"+searchString+"%'");
	}
	else if(searchOper.equalsIgnoreCase("ne"))
	{
	str.append(" "+searchField+" <> '"+searchString+"'");
	}
	else if(searchOper.equalsIgnoreCase("ew"))
	{
	str.append(" "+searchField+" like '%"+searchString+"'");
	}
	}
	  str.append(";");
	Session session = null;  
	Transaction transaction = null; 
	//System.out.println("str.toString() ::::::: "+str.toString());
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	empList=session.createSQLQuery(str.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return empList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getRoasterList(boolean flag, String id, String deptLevel, SessionFactory connection)
	 {
	List roasterList = new ArrayList();
	String query="";
	if (flag) {
	query="select * from roaster_conf where deptHierarchy="+deptLevel+" and dept_subdept in(select id from subdepartment where deptid="+id+")";
	}
	else {
	query="select * from roaster_conf where deptHierarchy="+deptLevel+" and dept_subdept="+id+"";
	}
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	roasterList=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return roasterList;
	 }
	
	@SuppressWarnings("unchecked")
	public String getValueId( String table,String fieldName1, String fieldvalue1,String fieldName2, String fieldvalue2, String fieldvalue3,String moduleName,SessionFactory connection)
	 {
	boolean flag =false;
	String Id=null,query="";
	List  list = new ArrayList();
	query="select id from "+table+" where "+fieldName1+"='"+fieldvalue1+"' and "+fieldName2+"='"+fieldvalue2+"'";
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	list=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	
	if (list!=null && list.size()<1) {
	list.clear();
	if (table.equalsIgnoreCase("feedback_type")) {
	query ="insert ignore into "+table+"("+fieldName1+","+fieldName2+",hide_show,userName,date,time,moduleName) values ("+fieldvalue1+",'"+fieldvalue2+"','Active','"+fieldvalue3+"','"+DateUtil.getCurrentDateUSFormat()+"', '"+DateUtil.getCurrentTime()+"','"+moduleName+"');";
	}
	else if (table.equalsIgnoreCase("feedback_category")) {
	query ="insert ignore into "+table+"("+fieldName1+","+fieldName2+",hide_show,userName,date,time) values ("+fieldvalue1+",'"+fieldvalue2+"','Active','"+fieldvalue3+"','"+DateUtil.getCurrentDateUSFormat()+"', '"+DateUtil.getCurrentTime()+"');";
	}
	    
	Session session1 = null;  
	Transaction transaction1 = null;  
	try 
	 {  
	session1=connection.getCurrentSession(); 
	transaction1 = session1.beginTransaction(); 
	Query qry=session1.createSQLQuery(query);
	    qry.executeUpdate();
	flag = true;
	transaction1.commit(); 
	 }
	catch (Exception ex)
	{transaction1.rollback();} 
	    if (flag) {
	    query="select max(id) from "+table+" where "+fieldName1+"="+fieldvalue1+"";
	Session session2 = null;  
	Transaction transaction2 = null;  
	try 
	 {  
	session2=connection.getCurrentSession(); 
	transaction2 = session2.beginTransaction(); 
	list=session2.createSQLQuery(query).list();  
	transaction2.commit(); 
	 }
	catch (Exception ex)
	{transaction2.rollback();} 
	   }
	}
	if (list!=null && list.size()>0) {
	Id=list.get(0).toString();
	}
	return Id;
	 }
	
	
	@SuppressWarnings("unchecked")
	public String getEmpValueId( String table,String fieldName1, String fieldvalue1,String fieldName2, String fieldvalue2, String fieldvalue3,String moduleName,SessionFactory connection)
	 {
	boolean flag =false;
	String Id=null,query="";
	List  list = new ArrayList();
	query="select id from "+table+" where "+fieldName1+"='"+fieldvalue1+"' and "+fieldName2+"='"+fieldvalue2+"'";
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	list=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	if (list!=null && list.size()>0) {
	Id=list.get(0).toString();
	}
	return Id;
	 }
	
	
	@SuppressWarnings("unchecked")
	public String getValueId4Asset( String table,String fieldName1, String fieldvalue1,String fieldName2, String fieldvalue2,String fieldName3, String fieldvalue3, String fieldvalue4,String moduleName,SessionFactory connection)
	 {
	boolean flag =false;
	String Id=null,query="";
	List  list = new ArrayList();
	query="select id from "+table+" where "+fieldName1+"='"+fieldvalue1+"' and "+fieldName2+"='"+fieldvalue2+"'  and "+fieldName3+"='"+fieldvalue3+"'";
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	list=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	
	if (list!=null && list.size()<1) {
	list.clear();
	if (table.equalsIgnoreCase("feedback_type")) {
	query ="insert ignore into "+table+"("+fieldName1+","+fieldName2+",hide_show,userName,date,time,moduleName,asset) values ("+fieldvalue1+",'"+fieldvalue2+"','Active','"+fieldvalue4+"','"+DateUtil.getCurrentDateUSFormat()+"', '"+DateUtil.getCurrentTime()+"','"+moduleName+"','"+fieldvalue3+"');";
	}
	else if (table.equalsIgnoreCase("feedback_category")) {
	query ="insert ignore into "+table+"("+fieldName1+","+fieldName2+",hide_show,userName,date,time) values ("+fieldvalue1+",'"+fieldvalue2+"','Active','"+fieldvalue3+"','"+DateUtil.getCurrentDateUSFormat()+"', '"+DateUtil.getCurrentTime()+"');";
	}
	    
	Session session1 = null;  
	Transaction transaction1 = null;  
	try 
	 {  
	session1=connection.getCurrentSession(); 
	transaction1 = session1.beginTransaction(); 
	Query qry=session1.createSQLQuery(query);
	    qry.executeUpdate();
	flag = true;
	transaction1.commit(); 
	 }
	catch (Exception ex)
	{transaction1.rollback();} 
	    if (flag) {
	    query="select max(id) from "+table+" where "+fieldName1+"="+fieldvalue1+"";
	Session session2 = null;  
	Transaction transaction2 = null;  
	try 
	 {  
	session2=connection.getCurrentSession(); 
	transaction2 = session2.beginTransaction(); 
	list=session2.createSQLQuery(query).list();  
	transaction2.commit(); 
	 }
	catch (Exception ex)
	{transaction2.rollback();} 
	   }
	}
	if (list!=null && list.size()>0) {
	Id=list.get(0).toString();
	}
	return Id;
	 }
	
	public SessionFactory session4File()
	{
	SessionFactory connection = null;
	String dbbname = "1_clouddb", ipAddress = null, username = null, paassword = null, port = null;
	try {
	  String dbDetails = new IPAddress().getDBDetails();
	  String db[]=dbDetails.split(",");
	  ipAddress=db[0];
	  username=db[1];
	  paassword=db[2];
	  port=db[3];
	
	  AllConnection Conn = new ConnectionFactory(dbbname,ipAddress, username, paassword, port);
	  AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
	  connection = Ob1.getSession();
	} catch (Exception e) {
	e.printStackTrace();
	}
	return connection;
	}
	
	@SuppressWarnings("unchecked")
	public List getNormalDashboardData(String status,String fromDate, String toDate,SessionFactory connection)
	 {
	List  dashList = new ArrayList();
	StringBuffer query= new StringBuffer();
	query.append("select status,count(*) from feedback_status group by status order by status");
	if (status.equals("CL")) {
	query.append("inner join employee_basic as emp on emp.mobOne=feedback.feed_by_mobno"); 
	}
	else if (status.equals("CR")) {
	query.append(" inner join employee_basic as emp on emp.id=feedback.allot_to");
	}
	query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
	query.append("where feedback.open_date between '"+fromDate+"' and '"+toDate+"'");
	query.append("group by feedback.status order by feedback.status");
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	dashList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return dashList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getEmployeeData(String uid, String deptLevel,SessionFactory connectionSpace) {
	List empList = new ArrayList();
	String empall = "";
	try {

	empall = " select emp.empName,emp.mobOne,emp.deptname,emp.empId,emp.emailIdOne,dept.deptName from employee_basic as emp"
	+ " inner join department as dept on emp.deptname=dept.id"
	    + " where emp.id='"+ uid + "'";
	empList = new createTable().executeAllSelectQuery(empall,
	connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return empList;
	}
	
	@SuppressWarnings("unchecked")
	public List getData4EmailConfig(SessionFactory connectionSpace) {
	List list = new ArrayList();
	StringBuilder query = new StringBuilder();
	try {
	  query.append("select email_register.emailid,email_register.password,email_register.mode,emp.id as empid,emp.empName,emp.mobOne,emp.emailIdOne,subdept.id as subdeptid,subdept.subdeptname,dept.id as deptid,dept.deptName,subcatg.id as subcatgid,subcatg.feedBreif,subcatg.escalateTime,subcatg.escalationLevel,subcatg.needEsc from email_registration as email_register");
	  query.append(" inner join employee_basic as emp on emp.id=email_register.empid");
	  query.append(" inner join subdepartment as subdept on subdept.id=emp.subdept");
	  query.append(" inner join department as dept on dept.id=subdept.deptid");
	  query.append(" inner join feedback_subcategory as subcatg on subcatg.id=email_register.subcatg");
	  list = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return list;
	}
	
	@SuppressWarnings("unchecked")
	public List getDeptDashboardData(String qryfor,SessionFactory connection)
	 {
	List  dashDeptList = new ArrayList();
	String query="";
	    if (qryfor.equalsIgnoreCase("dept")) {
	    	query="select distinct dept.deptName,dept.id from feedback_status as feedback" 
	              +" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id"
	              +" inner join department dept on subdept.deptid= dept.id"
	              +" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' order by feedback.status";
	}
	    else if (qryfor.equalsIgnoreCase("subdept")) {
	    	query="select distinct subdept.subdeptname,subdept.id from feedback_status as feedback" 
	              +" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id"
	              +" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by status order by status";
	}
	    else if (qryfor.equalsIgnoreCase("counter")) {
	    	query="select distinct dept.deptName,dept.id,feedback.status,count(*) from feedback_status as feedback" 
	              +" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id"
	              +" inner join department dept on subdept.deptid= dept.id"
	              +" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by status order by status";
	}
	    else if (qryfor.equalsIgnoreCase("subdept_counter")) {
	    	query="select distinct subdept.subdeptname,subdept.id,feedback.status,count(*) from feedback_status as feedback" 
	              +" inner join subdepartment subdept on feedback.to_dept_subdept= subdept.id"
	              +" where feedback.open_date='"+DateUtil.getCurrentDateUSFormat()+"' group by status order by status";
	}
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	dashDeptList=session.createSQLQuery(query).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return dashDeptList;
	 }
	
	@SuppressWarnings("unchecked")
	public List getSubDeptList4FbType(String deptid,String module,String status, SessionFactory connectionSpace)
     {
            List list=new ArrayList();
            StringBuilder query = new StringBuilder("");
            try
              {
            	query.append("select distinct subdept.id,subdept.subdeptname from subdepartment as subdept");
            	query.append(" inner join feedback_type as fbtype on subdept.id=fbtype.dept_subdept");
            	query.append(" inner join department as dept on subdept.deptid=dept.id");
            	query.append(" where dept.id='"+deptid+"' and fbtype.moduleName='"+module+"' order by subdept.subdeptname;");
                list=new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
              }
            catch (Exception e) {
                    e.printStackTrace();
            }
            return list;
     }
	

	
	public List getAllFloors(SessionFactory connection){
	List dept_subdeptList = null;
	StringBuilder qry= new StringBuilder();
	try {
	
	qry.append(" SELECT id,floorname from floor_detail");
	Session session = null;
	Transaction transaction = null;
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	dept_subdeptList = session.createSQLQuery(qry.toString()).list();
	transaction.commit();

	} catch (SQLGrammarException e) {
	e.printStackTrace();
	}
	return dept_subdeptList;
	}
	
	
 
	
	// get counts of tickets on the basis of status
	@SuppressWarnings("unchecked")
	public List getTicketsCounts(String countsfor,String UID,String fromDate,String toDate,SessionFactory connection)
	 {
	List  dashList = new ArrayList();
	StringBuffer query= new StringBuffer();
	// Query for getting SubDept List
	query.append("select feed_stats.status,count(*) from feedback_status as feed_stats");
	if (countsfor.equals("CL")) {
	      query.append(" inner join employee_basic as emp on emp.mobOne=feed_stats.feed_by_mobno");
	      query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
	      query.append(" where userac.id='"+UID+"' and");
	}
	else if (countsfor.equals("CR")) {
	  query.append(" inner join employee_basic as emp on emp.id=feed_stats.allot_to");
	  query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
	  query.append(" where userac.id='"+UID+"' and");
	}
	else if (countsfor.equals("HOD")) {
	  query.append(" inner join employee_basic as emp on emp.id=feed_stats.allot_to");
	  query.append(" inner join useraccount as userac on userac.id=emp.useraccountid");
	  query.append(" where userac.id='"+UID+"' and feed_stats.to_dept_subdept in(select id from subdepartment where deptid=(select deptid from subdepartment where id= emp.subdept)) and");
	}
	else if (countsfor.equals("MGMT")) {
	 query.append(" where ");
	}
	 query.append("  open_date between '"+fromDate+"' and '"+toDate+"'");
	 query.append(" group by status order by status");
	   
	Session session = null;  
	Transaction transaction = null;  
	try 
	 {  
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	dashList=session.createSQLQuery(query.toString()).list();  
	transaction.commit(); 
	 }
	catch (Exception ex)
	{transaction.rollback();} 
	
	return dashList;
	 }
	
	@SuppressWarnings("unchecked")
	public String getLatestTicket(String seriesType,String deptid,String moduleName, SessionFactory connection)
	 {
	 String ticketno="",query="";
	 List list =null;
	 if (seriesType.equals("N")) {
	 query = "select feed_status.ticket_no from feedback_status as feed_status"
                 +" where feed_status.id=(select max(id) from feedback_status where moduleName='"+moduleName+"')";
	}
	 else if (seriesType.equals("D")) {
	 if(moduleName.equalsIgnoreCase("FM"))
	 {
	 query = "select feed_status.ticket_no from feedback_status as feed_status"
	                 +" where feed_status.id=(select max(id) from feedback_status where moduleName='"+moduleName+"' and to_dept_subdept in (select id from department where id="+deptid+"))";
	 }
	 else if(moduleName.equalsIgnoreCase("HDM"))
	 {
	 query = "select feed_status.ticket_no from feedback_status as feed_status"
	                 +" where feed_status.id=(select max(id) from feedback_status where moduleName='"+moduleName+"' and to_dept_subdept in (select id from subdepartment where deptid="+deptid+"))";
	
	}
	 }
	  Session session = null;  
	Transaction transaction = null;  
	 try 
	{
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	list=session.createSQLQuery(query).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	{transaction.rollback();} 
	
	
	 if(list!=null && list.size()>0)
	            {
	  ticketno=list.get(0).toString();
	            }
	return ticketno;
	 }
	
	@SuppressWarnings("unchecked")
	public String getLatestTicket4Email(SessionFactory connection)
	 {
	 String ticketno="",query="";
	 List list =null;
	 query = "select feed_status.ticket_no from feedback_status as feed_status"
                 +" where feed_status.id=(select max(id) from feedback_status where via_from='email')";
	  Session session = null;  
	Transaction transaction = null;  
	 try 
	{
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	list=session.createSQLQuery(query).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	{transaction.rollback();} 
	
	
	 if(list!=null && list.size()>0)
	            {
	  ticketno=list.get(0).toString();
	            }
	return ticketno;
	 }
	
	@SuppressWarnings("unchecked")
	public String getTicketLevel(String empid,SessionFactory connection)
	 {
	 String ticketlevel="";
	 StringBuilder sb =new StringBuilder();
	 List list =null;
	 sb.append("select contact.level from employee_basic as emp");
	 sb.append(" inner join compliance_contacts as contact on emp.id=contact.emp_id");
	 sb.append(" where emp.id='"+empid+"'");
	// System.out.println(">>>> Ticket Level ::: "+sb.toString());
	  Session session = null;  
	Transaction transaction = null;  
	 try 
	{
	session=connection.getCurrentSession(); 
	transaction = session.beginTransaction(); 
	list=session.createSQLQuery(sb.toString()).list();  
	transaction.commit(); 
	}
	catch (Exception ex)
	{transaction.rollback();} 
	
	
	 if(list!=null && list.size()>0)
	            {
	 ticketlevel="Level"+list.get(0).toString();
	            }
	return ticketlevel;
	 }
	
	@SuppressWarnings("unchecked")
	public String getComplTicketSeries(String seriesType,String deptId,SessionFactory connectionSpace)
	{
	String finalString=null;
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder builder=new StringBuilder();
	if(seriesType!=null && seriesType.equalsIgnoreCase("D"))
	{
	builder.append("SELECT MAX(compid_suffix) FROM compliance_details AS cd ");	
	builder.append("INNER JOIN compliance_task AS ct ON cd.taskName=ct.id WHERE ct.departName="+deptId);
	}
	else if(seriesType!=null && seriesType.equalsIgnoreCase("N"))
	{
	builder.append("SELECT MAX(compid_suffix) FROM compliance_details");	
	}
	
	List maxSuffixList=cbt.executeAllSelectQuery(builder.toString(),connectionSpace);
	if(maxSuffixList!=null && maxSuffixList.size()>0)
	{
	String suffix=null;
	if (maxSuffixList.get(0)!=null && !maxSuffixList.get(0).toString().equals("")) {
	 suffix=maxSuffixList.get(0).toString();
	}
	if(suffix!=null && !suffix.equalsIgnoreCase("0"))
	{
	String prefix=getComplPrefix(deptId,seriesType,connectionSpace);
	if(prefix!=null)
	{
	finalString=prefix+suffix;
	}
	
	}
	}
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return finalString;
	}
	


@SuppressWarnings("rawtypes")
	public List getLevelForEscalation(String dept_subDept, SessionFactory connectionSpace) {
	List empList = new ArrayList();
	//String qry = null;
	StringBuilder query = new StringBuilder();
	try {
	query.append("select distinct contacts.level from employee_basic as emp");
	query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
	query.append(" inner join subdepartment sub_dept on contacts.forDept_id = sub_dept.id ");
	query.append(" where  contacts.level In (1,2,3,4,5,6) and contacts.work_status='0' and contacts.moduleName='HDM'");
	if (dept_subDept!=null && !dept_subDept.equalsIgnoreCase("")) 
	{
	query.append(" and sub_dept.id='"+ dept_subDept+ "'");
	}
	query.append("  order by contacts.level desc limit 1");
	empList = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return empList;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getAssetTicketSeries(String seriesType,String deptid,SessionFactory connectionSpace)
	{
	String finalString=null;
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder builder=new StringBuilder();
	if(seriesType!=null && seriesType.equalsIgnoreCase("D"))
	{
	builder.append("select feed_status.ticket_no from asset_complaint_status as feed_status");	
	builder.append(" where feed_status.id=(select max(id) from asset_complaint_status  where to_dept="+deptid+")");
	}
	else if(seriesType!=null && seriesType.equalsIgnoreCase("N"))
	{
	builder.append("select feed_status.ticket_no from asset_complaint_status as feed_status");
	builder.append(" where feed_status.id=(select max(id) from asset_complaint_status)");	
	}
	List maxSuffixList=cbt.executeAllSelectQuery(builder.toString(),connectionSpace);
	if(maxSuffixList!=null && maxSuffixList.size()>0)
	{
	finalString=maxSuffixList.get(0).toString();
	}
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return finalString;
	}
	
	
	@SuppressWarnings("unchecked")
	public String getAssetSNOSeries(String seriesType,String deptid,SessionFactory connectionSpace)
	{
	String finalString=null;
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder builder=new StringBuilder();
	if(seriesType!=null && seriesType.equalsIgnoreCase("D"))
	{
	builder.append("select asset.serialno from asset_detail as asset");	
	builder.append(" where asset.id=(select max(id) from asset_detail  where dept_subdept="+deptid+")");
	}
	else if(seriesType!=null && seriesType.equalsIgnoreCase("N"))
	{
	builder.append("select asset.serialno from asset_detail as asset");	
	builder.append(" where asset.id=(select max(id) from asset_detail)");
	}
	List maxSuffixList=cbt.executeAllSelectQuery(builder.toString(),connectionSpace);
	if(maxSuffixList!=null && maxSuffixList.size()>0)
	{
	finalString=maxSuffixList.get(0).toString();
	}
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return finalString;
	}
	
	@SuppressWarnings("unchecked")
	public String getComplPrefix(String deptId,String seriesType,SessionFactory connectionSpace)
	{
	String prefix=null;
	try
	{
	CommonOperInterface cbt=new CommonConFactory().createInterface();
	StringBuilder builder=new StringBuilder();
	if(seriesType!=null && seriesType.equalsIgnoreCase("D"))
	{
	builder.append("SELECT compid_prefix FROM compliance_details AS cd ");	
	builder.append("INNER JOIN compliance_task AS ct ON cd.taskName=ct.id WHERE ct.departName="+deptId+" LIMIT 1");
	}
	else if(seriesType!=null && seriesType.equalsIgnoreCase("N"))
	{
	builder.append("SELECT compid_prefix FROM compliance_details LIMIT 1");	
	}
	List maxPrefixList=cbt.executeAllSelectQuery(builder.toString(),connectionSpace);
	if(maxPrefixList!=null && maxPrefixList.size()>0)
	{
	prefix=maxPrefixList.get(0).toString();
	}
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return prefix;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getEmp4Escalation(String dept_subDept, String deptLevel,String module, String level,String floor_status,String floor, SessionFactory connectionSpace,boolean roasterFlag) {
	List empList = new ArrayList();
	//String qry = null;
	StringBuilder query = new StringBuilder();
	try 
	{
	query.append("select distinct emp.id, emp.empName, emp.mobOne, emp.emailIdOne,dept.deptName from employee_basic as emp");
	query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
	if (deptLevel.equalsIgnoreCase("SD"))
	{
	if(roasterFlag)
	{
	String shiftname = DateUtil.getCurrentDateUSFormat().substring(8,10)+ "_date";
	query.append(" inner join roaster_conf roaster on contacts.id = roaster.contactId");
	query.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
	query.append(" inner join department dept on sub_dept.deptid = dept.id ");
	query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
	query.append(" where shift.shiftName="+ shiftname+ " and roaster.status='Active' and roaster.attendance='Present' and contacts.level='"+level+"' and contacts.work_status='3' and contacts.moduleName='"+module+"'");
	query.append(" and shift.shiftFrom<='"+ DateUtil.getCurrentTime()+ "' and shift.shiftTo >'"+ DateUtil.getCurrentTime() + "'");
	if (floor_status.equalsIgnoreCase("Y")) 
	{
	query.append(" and roaster.floor='"+floor+"' and dept.id=(select deptid from subdepartment where id='"+ dept_subDept+ "') ");
	}
	else
	{
	query.append(" and sub_dept.id='"+ dept_subDept+ "'");
	}
	}
	else
	{
	query.append(" inner join subdepartment sub_dept on contacts.forDept_id = sub_dept.id ");
	query.append(" inner join department dept on sub_dept.deptid = dept.id ");
	query.append(" where contacts.level='"+level+"' and contacts.work_status='0' and contacts.moduleName='"+module+"' AND contacts.forDept_id="+dept_subDept);
	}
	
	}
	else 
	{
	query.append(" inner join department dept on contacts.forDept_id = dept.id ");
	query.append(" where contacts.level='"+level+"' and contacts.moduleName='"+module+"'");
	query.append(" and dept.id='"+ dept_subDept+ "'");
	}
	empList = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
	} 
	catch (Exception e) 
	{
	e.printStackTrace();
	}
	return empList;
	}
	
	
	public List getEmp4Escalation(String deptId, String roomNo, String level,String escCondition, SessionFactory connectionSpace) 
	{
	List empList = new ArrayList();
	StringBuilder query = new StringBuilder();
	try 
	{
	query.append("SELECT distinct emp.id, emp.empName, emp.mobOne, emp.emailIdOne,dept.deptName, esc.wingsname");
	query.append(" FROM escalation_contact_config_detail AS esc");
	query.append(" INNER JOIN compliance_contacts AS contact ON contact.id = esc.empName");
	query.append(" INNER JOIN employee_basic AS emp ON emp.id = contact.emp_id");
	query.append(" INNER JOIN department AS dept ON dept.id = esc.escDept");
	
	if(escCondition!=null && escCondition.equalsIgnoreCase("floor"))
	{
	query.append(" INNER JOIN floor_room_detail AS floor ON floor.floorname=esc.floorname");
	query.append(" WHERE esc.status='Active' AND esc.escDept="+deptId+"  AND floor.roomno="+roomNo+" AND esc.level ="+level);
	}
	else if(escCondition!=null && escCondition.equalsIgnoreCase("wings"))
	{
	query.append(" INNER JOIN floor_room_detail AS wings ON wings.wingsname=esc.wingsname");
	query.append(" WHERE esc.status='Active' AND esc.escDept="+deptId+" AND wings.roomno="+roomNo+" AND esc.level ="+level);
	}
	empList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return empList;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getEmp4Escalation(String dept_subDept,String module, String level,String flrCondi, SessionFactory connectionSpace,String roomNo) 
	{
	List empList = new ArrayList();
	//String qry = null;
	StringBuilder query = new StringBuilder();
	try 
	{
	String shiftname = DateUtil.getCurrentDateUSFormat().substring(8,10)+ "_date";
	query.append("SELECT DISTINCT emp.id, emp.empName, emp.mobOne, emp.emailIdOne,dept.deptName");
	query.append(" FROM escalation_contact_config_detail  AS esc");
	query.append(" INNER JOIN compliance_contacts AS contact ON contact.id = esc.empName");
	query.append(" INNER JOIN employee_basic AS emp ON emp.id = contact.emp_id");
	query.append(" INNER JOIN roaster_conf AS roaster ON contact.id = roaster.contactId");
	query.append(" INNER JOIN subdepartment AS sub_dept ON roaster.dept_subdept = sub_dept.id");
	query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept");
	query.append(" inner join department dept on sub_dept.deptid = dept.id");
	if(flrCondi!=null && flrCondi.equalsIgnoreCase("floor"))
	query.append(" INNER JOIN floor_room_detail AS floor ON floor.floorname=esc.floorname");
	else if(flrCondi!=null && flrCondi.equalsIgnoreCase("wings"))
	query.append(" INNER JOIN floor_room_detail AS wing ON wing.wingsname=esc.wingsname");
	
	query.append(" where shift.shiftName="+ shiftname+ " and esc.status='Active' AND floor.roomno="+roomNo+" AND esc.level ="+level+" AND roaster.status='Active' and roaster.attendance='Present' and contact.level='"+level+"' and contact.work_status='3' and contact.moduleName='"+module+"'");
	query.append(" and sub_dept.id='"+ dept_subDept+ "' and shift.shiftFrom<='"+ DateUtil.getCurrentTime()+ "' and shift.shiftTo >'"+ DateUtil.getCurrentTime() + "'");
	empList = new createTable().executeAllSelectQuery(query.toString(),connectionSpace);
	} 
	catch (Exception e) 
	{
	e.printStackTrace();
	}
	return empList;
	}
	
	@SuppressWarnings("unchecked")
	public String getMailConfig(String name,String title,String status,String deptLevel,boolean flag)
	 {
	List orgData= new LoginImp().getUserInfomation("1", "IN");
	String orgName="";
	if (orgData!=null && orgData.size()>0) {
	for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	orgName=object[0].toString();
	}
	}
	    StringBuilder mailtext = new StringBuilder("");
	    mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+orgName+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+title+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	  /*  if (flag) {
	        mailtext.append("<b>Dear " +DateUtil.makeTitle(name)+ ",</b>");
	    }
	    else if (!flag) {*/
	    mailtext.append("<b>Dear " +DateUtil.makeTitle(name)+ ",</b>");	
	//}
	    mailtext.append("<br><br><b>Hello !!!</b><br>");
	    if (flag) {
	mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
	    }
	    else if (!flag) {
	    mailtext.append("Here is a feedback detail in mail body as suggested by you:-<br>");	
	}
	mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
	mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	    return mailtext.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String getConfigMessage(FeedbackPojo feedbackObj,String title,String status,String deptLevel,boolean flag) {
	 List orgData= new LoginImp().getUserInfomation("1", "IN");
	String orgName="";
	if (orgData!=null && orgData.size()>0) {
	for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	orgName=object[0].toString();
	}
	}
	    StringBuilder mailtext = new StringBuilder("");
	   // mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+orgName+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+title+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    if (flag) {
	        mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeedback_allot_to())+ ",</b>");
	    }
	    else if (!flag) {
	    	mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeed_registerby())+ ",</b>");	
	}
	    mailtext.append("<br><br><b>Hello !!!</b><br>");
	    if (flag) {
	mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
	    }
	    else if (!flag) {
	    mailtext.append("Here is a feedback detail in mail body as suggested by you:-<br>");	
	}
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getTicket_no()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Date/Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(feedbackObj.getOpen_date())+ ", "+feedbackObj.getOpen_time().substring(0, 5)+"Hrs</td></tr>");
        //mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getOpen_time().substring(0, 5) + " Hrs</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_registerby() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_dept() + "</td></tr>");
	if (deptLevel.equals("SD")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Sub-Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_subdept() + "</td></tr>");
	}
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Location:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getRoomNo()+ " "+ feedbackObj.getLocation() + " </td></tr>");
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_catg() + "</td></tr>");
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Sub-Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_subcatg() + "</td></tr>");
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_brief() + "</td></tr>");
	
	
	if (flag) {
	 if (status.equals("Pending") && !feedbackObj.getEscalation_date().equals("NA")) {
	     mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next&nbsp;Escalation&nbsp;Date&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.convertDateToIndianFormat(feedbackObj.getEscalation_date())+"  "+feedbackObj.getEscalation_time().substring(0, 5)+" Hrs</td></tr>");
	 }
	}
	else
	 {
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Alloted To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_allot_to() + "</td></tr>");
	   if (status.equals("Pending") && !feedbackObj.getEscalation_date().equals("NA")) {
	      mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Resolve&nbsp;Up&nbsp;To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+feedbackObj.getEscalation_date()+"  "+feedbackObj.getEscalation_time().substring(0, 5)+" Hrs</td></tr>");
	   }
	  }
	
	if (status.equalsIgnoreCase("High Priority")) {
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> High Priority Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getHp_reason() + " </td></tr>");
	}
	else if (status.equalsIgnoreCase("Snooze")) {
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Up To Date/Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(feedbackObj.getSn_date()) + ", "+ feedbackObj.getSn_time() + " Hrs</td></tr>");
	//mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Up To Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSn_time() + " Hrs</td></tr>");
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSn_reason() + "</td></tr>");
	}
        else if (status.equalsIgnoreCase("Ignore")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ignore Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getIg_reason() + "</td></tr>");
	}
        else if (status.equalsIgnoreCase("Resolved")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> TAT:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getResolve_duration() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Spare Used:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSpare_used() + "</td></tr>");
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Resolve Remark:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getResolve_remark()+ "</td></tr>");
	}
	mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
	mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	    return mailtext.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String configMessage4Escalation(FeedbackPojo feedbackObj,String title,String escalateEmp,String deptLevel,boolean flag) {
	 List orgData= new LoginImp().getUserInfomation("1", "IN");
	String orgName="";
	if (orgData!=null && orgData.size()>0) {
	for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	orgName=object[0].toString();
	}
	}
	    StringBuilder mailtext = new StringBuilder("");
	 //   mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+title+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<b>Dear " +DateUtil.makeTitle(escalateEmp)+ ",</b>");
	   
	    mailtext.append("<br><br><b>Hello !!!</b><br>");
	mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
	   
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getTicket_no()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Date/Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(feedbackObj.getOpen_date())+ ", "+ feedbackObj.getOpen_time().substring(0, 5) + " Hrs</td></tr>");
        //mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getOpen_time().substring(0, 5) + " Hrs</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_registerby() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_dept() + "</td></tr>");
	if (deptLevel.equals("2")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Sub-Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_subdept() + "</td></tr>");
	}
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;Allot&nbsp;To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.makeTitle(feedbackObj.getFeedback_allot_to()) + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Location:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getLocation() + " </td></tr>");
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_catg() + "</td></tr>");
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Sub-Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_subcatg() + "</td></tr>");
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_brief() + "</td></tr>");
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next&nbsp;Escalation&nbsp;Date&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.convertDateToIndianFormat(feedbackObj.getEscalation_date())+"  "+feedbackObj.getEscalation_time()+" Hrs</td></tr>");
	mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
	mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	    return mailtext.toString();
	}

	@SuppressWarnings("unchecked")
	public String getConfigMailForReport(int pc,int hc,int sc,int ic,int rc,int re,int reo,int total,int cfpc,int cfsc,int cfhc,int cftotal,String empname, List<FeedbackPojo> currentDayResolvedData,List<FeedbackPojo> currentDayPendingData,List<FeedbackPojo> currentDaySnoozeData,List<FeedbackPojo> currentDayHPData,List<FeedbackPojo> currentDayIgData,List<FeedbackPojo> currentDayReOpenData,List<FeedbackPojo> currentDayReAssignData,List<FeedbackPojo> currentDayAllReOpenData,List<FeedbackPojo> currentDayRange,List<FeedbackPojo> CFData,String SubDept)
	{ 

	String Ticket_No = "";
	List orgData = new LoginImp().getUserInfomation("4", "IN");
	String orgName = "";
	if (orgData != null && orgData.size() > 0)
	{
	for (Iterator iterator = orgData.iterator(); iterator.hasNext();)
	{
	Object[] object = (Object[]) iterator.next();
	orgName = object[0].toString();
	}
	}
	StringBuilder mailtext = new StringBuilder("");
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + orgName + "</b></td></tr></tbody></table>");
	mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Help Desk Manager</b></td></tr></tbody></table>");
	mailtext.append("<b>Dear " + DateUtil.makeTitle(empname) + ",</b>");
	mailtext.append("<br><br><b>Hello!!!</b>");
	if (currentDayRange != null && currentDayRange.size() > 0)
	{
	int i = 0;
	for (FeedbackPojo FBP : currentDayRange)
	{
	int k = ++i;
	if (k == 1)
	{
	if (FBP.getReportFor().equalsIgnoreCase("Daily"))
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + FBP.getReportFor() + " Ticket Summary Status For " + FBP.getFeedback_to_dept() + " on " + DateUtil.getCurrentDateIndianFormat() + " At " + DateUtil.getCurrentTime().substring(0, 5) + "</b></td></tr></tbody></table>");
	else
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>" + FBP.getReportFor() + " Ticket Summary Status For " + FBP.getFeedback_to_dept() + " From " + DateUtil.convertDateToIndianFormat(FBP.getReportRange()) + " to " + DateUtil.getCurrentDateIndianFormat() + " At "
	+ DateUtil.getCurrentTime().substring(0, 5) + "</b></td></tr></tbody></table>");

	} else
	break;
	}
	}
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Pending</b></td></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>High Priority</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Parked</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ignore</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Resolved</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Re-Assign</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Re-Opened</b></td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Total</b></td></tr>");
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + pc + "</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + hc + "</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + sc
	+ "</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + ic + "</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + rc + "</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + re
	+ "</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + reo + "</td> <td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + total + "</td></tr></tbody></table>");

	if (currentDayPendingData != null && currentDayPendingData.size() > 0)
	{
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Details-Current Status:Pending</b></td></tr></tbody></table>");
	int i = 0, a = 1;
	;
	for (FeedbackPojo FBP : currentDayPendingData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	if (!Ticket_No.equalsIgnoreCase(FBP.getTicket_no()))
	{
	a = 1;
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current&nbsp;Status</strong></td></tr>");

	mailtext.append("<tr  bgcolor='#ffffff'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' rowspan='12'> " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>"
	+ "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	Ticket_No = FBP.getTicket_no();
	} else
	{
	if (a == 1)
	{

	mailtext.append("<tr  bgcolor='#0066CC' align='center'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Date&nbsp;&&nbsp;Time</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allot&nbsp;To</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;By</strong></td>");

	}
	a++;
	mailtext.append("<tr  bgcolor='#99CCFF' align='center'>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getPending_date() + " " + FBP.getPending_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getHistoryStatus() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getPending_reason() + "</td>"
	+ "<td align='center' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_by() + "</td></tr>");

	}

	} else
	{
	if (!Ticket_No.equalsIgnoreCase(FBP.getTicket_no()))
	{
	a = 1;
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current&nbsp;Status</strong></td></tr>");

	mailtext.append("<tr  bgcolor='#e8e7e8'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' rowspan='12'> " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>"
	+ "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	Ticket_No = FBP.getTicket_no();
	} else
	{
	if (a == 1)
	{
	mailtext.append("<tr  bgcolor='#0066CC' align='center'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Date&nbsp;&&nbsp;Time</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allot&nbsp;To</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;By</strong></td>");

	}
	a++;
	mailtext.append("<tr  bgcolor='#99CCFF' align='center'>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getPending_date() + " " + FBP.getPending_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getHistoryStatus() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getPending_reason() + "</td>"
	+ "<td align='center' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_by() + "</td></tr>");

	}

	}

	}
	mailtext.append("</tbody></table>");

	}

	if (currentDaySnoozeData != null && currentDaySnoozeData.size() > 0)
	{
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Details-Current Status:Parked</b></td></tr></tbody></table>");
	int i = 0, a = 1;
	;
	for (FeedbackPojo FBP : currentDaySnoozeData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	if (!Ticket_No.equalsIgnoreCase(FBP.getTicket_no()))
	{
	a = 1;
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current&nbsp;Status</strong></td></tr>");

	mailtext.append("<tr  bgcolor='#ffffff'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' rowspan='12'> " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>"
	+ "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	Ticket_No = FBP.getTicket_no();
	} else
	{
	if (a == 1)
	{

	mailtext.append("<tr  bgcolor='#0066CC' align='center'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Date&nbsp;&&nbsp;Time</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Parked&nbsp;Upto&nbsp;Date&nbsp;&&nbsp;Time</strong></td>"

	+ "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action</strong></td>" + "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allot&nbsp;To</strong></td>"
	+ "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td> " + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;By</strong></td>");

	}
	a++;
	mailtext.append("<tr  bgcolor='#99CCFF' align='center'>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getSn_on_date() + " " + FBP.getSn_on_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getSn_upto_date() + " " + FBP.getSn_upto_time() + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getHistoryStatus() + "</td> " + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>"
	+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getSn_reason() + "</td>" + "<td align='center' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_by() + "</td></tr>");

	}

	} else
	{
	if (!Ticket_No.equalsIgnoreCase(FBP.getTicket_no()))
	{
	a = 1;
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current&nbsp;Status</strong></td></tr>");

	mailtext.append("<tr  bgcolor='#e8e7e8'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' rowspan='12'> " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>"
	+ "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	Ticket_No = FBP.getTicket_no();
	} else
	{
	if (a == 1)
	{
	mailtext.append("<tr  bgcolor='#0066CC' align='center'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Date&nbsp;&&nbsp;Time</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Parked&nbsp;Upto&nbsp;Date&nbsp;&&nbsp;Time</strong></td>"

	+ "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action</strong></td>" + "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allot&nbsp;To</strong></td>"
	+ "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td> " + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;By</strong></td>");

	}
	a++;
	mailtext.append("<tr  bgcolor='#99CCFF' align='center'>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getSn_on_date() + " " + FBP.getSn_on_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getSn_upto_date() + " " + FBP.getSn_upto_time() + "</td>"

	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getHistoryStatus() + "</td> " + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"
	+ FBP.getSn_reason() + "</td>" + "<td align='center' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_by() + "</td></tr>");

	}

	}

	}
	mailtext.append("</tbody></table>");

	}

	if (currentDayReOpenData != null && currentDayReOpenData.size() > 0)
	{
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Details-Current Status:Re-Opened</b></td></tr></tbody></table>");
	int i = 0, a = 1;
	;
	for (FeedbackPojo FBP : currentDayReOpenData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	if (!Ticket_No.equalsIgnoreCase(FBP.getTicket_no()))
	{
	a = 1;
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current&nbsp;Status</strong></td></tr>");

	mailtext.append("<tr  bgcolor='#ffffff'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' rowspan='12'> " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>"
	+ "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	Ticket_No = FBP.getTicket_no();
	} else
	{
	if (a == 1)
	{

	mailtext.append("<tr  bgcolor='#0066CC' align='center'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Date&nbsp;&&nbsp;Time</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allot&nbsp;To</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;By</strong></td>");

	}
	a++;
	mailtext.append("<tr  bgcolor='#99CCFF' align='center'>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getReopen_date() + " " + FBP.getReopen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getHistoryStatus() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getReopen_reason() + "</td>"
	+ "<td align='center' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_by() + "</td></tr>");

	}

	} else
	{
	if (!Ticket_No.equalsIgnoreCase(FBP.getTicket_no()))
	{
	a = 1;
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current&nbsp;Status</strong></td></tr>");

	mailtext.append("<tr  bgcolor='#e8e7e8'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' rowspan='12'> " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>"
	+ "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	Ticket_No = FBP.getTicket_no();
	} else
	{
	if (a == 1)
	{
	mailtext.append("<tr  bgcolor='#0066CC' align='center'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Date&nbsp;&&nbsp;Time</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allot&nbsp;To</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;By</strong></td>");

	}
	a++;
	mailtext.append("<tr  bgcolor='#99CCFF' align='center'>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getReopen_date() + " " + FBP.getReopen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getHistoryStatus() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getReopen_reason() + "</td>"
	+ "<td align='center' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_by() + "</td></tr>");

	}

	}

	}
	mailtext.append("</tbody></table>");

	}

	if (currentDayAllReOpenData != null && currentDayAllReOpenData.size() > 0)
	{
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>All Re-Opened Ticket Details</b></td></tr></tbody></table>");
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	 + "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Re-Opened&nbsp;At</strong></td>"

	+ "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> " + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>"
	+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> " + "<td align='center' width='1%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current Status</strong></td></tr>");

	int i = 0;
	 	for (FeedbackPojo FBP : currentDayAllReOpenData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	 
	 	mailtext.append("<tr  bgcolor='#ffffff'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' > " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	 + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> " + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>"
	+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>" + "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");

	} else
	{
	mailtext.append("<tr  bgcolor='#e8e7e8'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' > " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	 + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() +  "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> " + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>"
	+ "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>" + "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");

	}
	 
	}
	}
	mailtext.append("</tbody></table>");

	if (currentDayReAssignData != null && currentDayReAssignData.size() > 0)
	{
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Details-Current Status:Re-Assign</b></td></tr></tbody></table>");
	int i = 0, a = 1;
	;
	for (FeedbackPojo FBP : currentDayReAssignData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	if (!Ticket_No.equalsIgnoreCase(FBP.getTicket_no()))
	{
	a = 1;
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current&nbsp;Status</strong></td></tr>");

	mailtext.append("<tr  bgcolor='#ffffff'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' rowspan='6'> " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>"
	+ "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	Ticket_No = FBP.getTicket_no();
	} else
	{
	if (a == 1)
	{

	mailtext.append("<tr  bgcolor='#0066CC' align='center'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Date&nbsp;&&nbsp;Time</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allot&nbsp;To</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;By</strong></td>");

	}
	a++;
	mailtext.append("<tr  bgcolor='#99CCFF' align='center'>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getReassign_date() + " " + FBP.getReassign_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getHistoryStatus() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getReassign_reason() + "</td>"
	+ "<td align='center' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_by() + "</td></tr>");

	}

	} else
	{
	if (!Ticket_No.equalsIgnoreCase(FBP.getTicket_no()))
	{
	a = 1;
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current&nbsp;Status</strong></td></tr>");

	mailtext.append("<tr  bgcolor='#e8e7e8'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' rowspan='6'> " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>"
	+ "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	Ticket_No = FBP.getTicket_no();
	} else
	{
	if (a == 1)
	{
	mailtext.append("<tr  bgcolor='#0066CC' align='center'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Date&nbsp;&&nbsp;Time</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allot&nbsp;To</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;By</strong></td>");

	}
	a++;
	mailtext.append("<tr  bgcolor='#99CCFF' align='center'>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getReassign_date() + " " + FBP.getReassign_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getHistoryStatus() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getReassign_reason() + "</td>"
	+ "<td align='center' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_by() + "</td></tr>");

	}
	}
	}
	mailtext.append("</tbody></table>");
	}
	mailtext.append("</tbody></table>");
	if (currentDayIgData != null && currentDayIgData.size() > 0)
	{
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Details-Current Status:Ignore</b></td></tr></tbody></table>");
	int i = 0, a = 1;
	;
	for (FeedbackPojo FBP : currentDayIgData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	if (!Ticket_No.equalsIgnoreCase(FBP.getTicket_no()))
	{
	a = 1;
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current&nbsp;Status</strong></td></tr>");

	mailtext.append("<tr  bgcolor='#ffffff'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' rowspan='6'> " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>"
	+ "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	Ticket_No = FBP.getTicket_no();
	} else
	{
	if (a == 1)
	{

	mailtext.append("<tr  bgcolor='#0066CC' align='center'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Date&nbsp;&&nbsp;Time</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allot&nbsp;To</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;By</strong></td>");

	}
	a++;
	mailtext.append("<tr  bgcolor='#99CCFF' align='center'>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getIg_date() + " " + FBP.getIg_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getHistoryStatus() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getIg_reason() + "</td>"
	+ "<td align='center' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_by() + "</td></tr>");

	}

	} else
	{
	if (!Ticket_No.equalsIgnoreCase(FBP.getTicket_no()))
	{
	a = 1;
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current&nbsp;Status</strong></td></tr>");

	mailtext.append("<tr  bgcolor='#e8e7e8'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' rowspan='6'> " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>"
	+ "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	Ticket_No = FBP.getTicket_no();
	} else
	{
	if (a == 1)
	{
	mailtext.append("<tr  bgcolor='#0066CC' align='center'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Date&nbsp;&&nbsp;Time</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allot&nbsp;To</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;By</strong></td>");

	}
	a++;
	mailtext.append("<tr  bgcolor='#99CCFF' align='center'>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getIg_date() + " " + FBP.getIg_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getHistoryStatus() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getIg_reason() + "</td>"
	+ "<td align='center' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_by() + "</td></tr>");

	}

	}
	}
	mailtext.append("</tbody></table>");
	}
	if (currentDayResolvedData != null && currentDayResolvedData.size() > 0)
	{
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket Details-Current Status:Resolved</b></td></tr></tbody></table>");
	int i = 0, a = 1;
	;
	for (FeedbackPojo FBP : currentDayResolvedData)
	{
	int k = ++i;
	if (k % 2 != 0)
	{
	if (!Ticket_No.equalsIgnoreCase(FBP.getTicket_no()))
	{
	a = 1;
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current&nbsp;Status</strong></td></tr>");

	mailtext.append("<tr  bgcolor='#ffffff'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' rowspan='6'> " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>"
	+ "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	Ticket_No = FBP.getTicket_no();
	} else
	{
	if (a == 1)
	{

	mailtext.append("<tr  bgcolor='#0066CC' align='center'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Date&nbsp;&&nbsp;Time</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allot&nbsp;To</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;By</strong></td>");

	}
	a++;
	mailtext.append("<tr  bgcolor='#99CCFF' align='center'>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_date() + " " + FBP.getResolve_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getHistoryStatus() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_remark() + "</td>"
	+ "<td align='center' width='12%'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_by() + "</td></tr>");

	}

	} else
	{
	if (!Ticket_No.equalsIgnoreCase(FBP.getTicket_no()))
	{
	a = 1;
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
	mailtext.append("<tr  bgcolor='#8db7d6'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Ticket&nbsp;No</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Lodge&nbsp;By</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Opened&nbsp;At</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>For</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allotted&nbsp;To</strong></td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Location</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Current&nbsp;Status</strong></td></tr>");

	mailtext.append("<tr  bgcolor='#e8e7e8'>" + "<td align='center' width='10%' style=' color:#111111; font-size:13px; font-family:Verdana, Arial, Helvetica, sans-serif;' rowspan='6'> " + FBP.getTicket_no() + "</td>" + "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + DateUtil.makeTitle(FBP.getFeed_by()) + "</td>"
	+ "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getOpen_date() + " " + FBP.getOpen_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_subcatg() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getLocation() + "</td>"
	+ "<td align='center' width='12%'  bgcolor='#53c156'  style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getStatus() + "</td></tr>");
	Ticket_No = FBP.getTicket_no();
	} else
	{
	if (a == 1)
	{
	mailtext.append("<tr  bgcolor='#0066CC' align='center'>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;Date&nbsp;&&nbsp;Time</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action</strong></td>"
	+ "<td align='center' width='15%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Allot&nbsp;To</strong></td>" + "<td align='center' width='20%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Remarks</strong></td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><strong>Action&nbsp;By</strong></td>");

	}
	a++;
	mailtext.append("<tr  bgcolor='#99CCFF' align='center'>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_date() + " " + FBP.getResolve_time() + "</td>" + "<td align='center' width='9%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getHistoryStatus() + "</td> "
	+ "<td align='center' width='12%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getFeedback_allot_to() + "</td>" + "<td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_remark() + "</td>"
	+ "<td align='center' width='12%'   style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>" + FBP.getResolve_by() + "</td></tr>");

	}

	}
	}
	mailtext.append("</tbody></table>");
	}

	mailtext.append("</tbody></table>");
	mailtext.append("</tbody></table>");
	mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
	mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
	return mailtext.toString();

	
	}
	
	@SuppressWarnings("unchecked")
	public synchronized static String getTicketNo(String deptid,String moduleName,SessionFactory connectionSpace)
	 {
	String ticketno="NA",ticket_type="",ticket_series="",prefix="",sufix="",ticket_no="";
	List ticketSeries = new ArrayList();
	List deptTicketSeries =new ArrayList();
	try {
	   // Code for getting the Ticket Type from table
	  // ticketSeries = new HelpdeskUniversalHelper().getDataFromTable("ticket_series_conf", null, null, null, null, connectionSpace);
	   ticketSeries = new HelpdeskUniversalHelper().getAllData("ticket_series_conf","moduleName",moduleName, "", "",connectionSpace);
	   if (ticketSeries!=null && ticketSeries.size()==1) {
	for (Iterator iterator = ticketSeries.iterator(); iterator
	.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	ticket_type=object[1].toString();
	ticket_series=object[2].toString();
	}
	  // Code for getting the first time counters of Feedback Status table, If get ticket counts greater than Zero than go in If block  and if get 0 than go in else block 	
	if (moduleName!=null && !moduleName.equals("") && moduleName.equals("COMPL")) {
	ticket_no= new HelpdeskUniversalHelper().getComplTicketSeries(ticket_type,deptid,connectionSpace);
	}
	else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("CASTM")) {
	ticket_no= new HelpdeskUniversalHelper().getAssetTicketSeries(ticket_type,deptid,connectionSpace);
	}
	else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("ASTM")) {
	ticket_no= new HelpdeskUniversalHelper().getAssetSNOSeries(ticket_type,deptid,connectionSpace);
	}
	else {
	ticket_no= new HelpdeskUniversalHelper().getLatestTicket(ticket_type,deptid,moduleName, connectionSpace);
	}
	  
	  if (ticket_no!=null && !ticket_no.equals("")) {
	  if (ticket_no!=null && !ticket_no.equals("") && ticket_type.equalsIgnoreCase("N")) {
	  if (moduleName!=null && !moduleName.equals("") && moduleName.equals("COMPL")) {
	 prefix=ticket_no.substring(0, 4);
	 sufix=ticket_no.substring(4, ticket_no.length());
	}
	  else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("FM")) {
	 prefix=ticket_no.substring(ticket_no.length()-6, ticket_no.length()-4);
	 sufix=ticket_no.substring(ticket_no.length()-4, ticket_no.length());
	}
	  else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("CASTM")) {
	     prefix=ticket_no.substring(0, 3);
	 sufix=ticket_no.substring(3, ticket_no.length());
	}
	  else {
	 prefix=ticket_no.substring(0, 2);
	 sufix=ticket_no.substring(2, ticket_no.length());
	}
	  setAN(Integer.parseInt(sufix));
	  ticketno=prefix+ ++AN;
	 }
	 else if (ticket_no!=null && !ticket_no.equals("") && ticket_type.equalsIgnoreCase("D")) {
	 if (moduleName!=null && !moduleName.equals("") && moduleName.equals("COMPL")) {
	 prefix=ticket_no.substring(0, 4);
	 sufix=ticket_no.substring(4, ticket_no.length());
	}
	 else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("FM")) {
	 prefix=ticket_no.substring(ticket_no.length()-6, ticket_no.length()-4);
	 sufix=ticket_no.substring(ticket_no.length()-4, ticket_no.length());
	}
	 else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("CASTM")) {
	     prefix=ticket_no.substring(0, 3);
	 sufix=ticket_no.substring(3, ticket_no.length());
	}
	 else if (moduleName!=null && !moduleName.equals("") && moduleName.equals("ASTM")) {
	     prefix=ticket_no.substring(0,11);
	 sufix=ticket_no.substring(11, ticket_no.length());
	}
	 else {
	 prefix=ticket_no.substring(0, 2);
	 sufix=ticket_no.substring(2, ticket_no.length());
	}
	 //System.out.println("suff  "+sufix);
	  setAN(Integer.parseInt(sufix));
	  ticketno=prefix+ ++AN;
	 }
	  }
	  else {
	if (ticket_type.equalsIgnoreCase("N")) {
	if (ticket_series!=null && !ticket_series.equals("") && !ticket_series.equals("NA")) {
	ticketno=ticket_series;
	}
	}
	else if (ticket_type.equalsIgnoreCase("D")) {
	deptTicketSeries = new HelpdeskUniversalHelper().getAllData("dept_ticket_series_conf", "deptName", deptid,"moduleName",moduleName, "prefix", "ASC",connectionSpace);
	                        if (deptTicketSeries!=null && deptTicketSeries.size()==1) {
	for (Iterator iterator2 = deptTicketSeries.iterator(); iterator2.hasNext();) {
	Object[] object1 = (Object[]) iterator2.next();
	if (object1[2]!=null && !object1[2].toString().equalsIgnoreCase("") && !object1[2].toString().equalsIgnoreCase("NA")) {
	ticketno=object1[2].toString()+object1[3].toString();
	}
	else {
	ticketno="NA";
	}
	}
	                      }
	}
	 }
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return ticketno;
	}
	
	@SuppressWarnings("unchecked")
	public List getAllData(String table, String searchfield,String searchfieldvalue, String orderfield, String order,SessionFactory connectionSpace) {
	List SubdeptallList = new ArrayList();
	StringBuilder query = new StringBuilder();
	try {
	query.append("select * from " + table + " where "+ searchfield + "='" + searchfieldvalue + "'");
	if (orderfield!=null && !orderfield.equals("") && order!=null && !order.equals("")) {
	query.append(" ORDER BY "+ orderfield + " " + order + "");
	}
	//System.out.println("Getting subcat details: "+query.toString());
	SubdeptallList = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return SubdeptallList;
	}
	
	@SuppressWarnings("unchecked")
	public List getAllData(String table, String searchfield,String searchfieldvalue,String searchfield1,String searchfieldvalue1, String orderfield, String order,SessionFactory connectionSpace) {
	List SubdeptallList = new ArrayList();
	try {
	String query = "select * from " + table + " where "+ searchfield + "='" + searchfieldvalue + "' and "+ searchfield1 + "='" + searchfieldvalue1 + "' ORDER BY "+ orderfield + " " + order + "";
	SubdeptallList = new createTable().executeAllSelectQuery(query, connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return SubdeptallList;
	}
	
	@SuppressWarnings("unchecked")
	public String getDesignationLevel(String userName,SessionFactory connectionSpace)
	{
	String level=null;
	try
	{ 
	String encptUid=Cryptography.encrypt(userName,DES_ENCRYPTION_KEY);
	StringBuilder query1=new StringBuilder("");
	query1.append("SELECT design.levelofhierarchy FROM designation AS design INNER JOIN employee_basic AS emp ON emp.designation=design.id " +
	"INNER JOIN useraccount AS ac ON ac.id=emp.useraccountid WHERE ac.userID='"+encptUid+"'");
	List dataList=new createTable().executeAllSelectQuery(query1.toString(),connectionSpace);
	if(dataList!=null && dataList.size()>0)
	{
	level=dataList.get(0).toString();
	}
	}
	catch(Exception e)
	{
	e.printStackTrace();
	}
	return level;
	}
	
	
	
	
	// Get Service Department List
	@SuppressWarnings("unchecked")
	public List getServiceDept_SubDept(String orgLevel,String orgId,String module, SessionFactory connection) {

	List dept_subdeptList = null;
	StringBuilder qry= new StringBuilder();
	try {
	if (module.equalsIgnoreCase("COMPL")) {
	qry.append("select distinct dept.id,dept.deptName from compliance_task as compl");
	qry.append(" inner join department as dept on compl.departName=dept.id");
	qry.append(" where dept.orgnztnlevel='"+ orgLevel+ "' and dept.mappedOrgnztnId='"+ orgId+ "'");
	}
	else if (module.equalsIgnoreCase("HDM")) {
	    qry.append(" select distinct dept.id,dept.deptName from department as dept");
	    qry.append(" inner join subdepartment as subdept on dept.id=subdept.deptid");
	    qry.append(" inner join feedback_type as feed on subdept.id=feed.dept_subdept");
	        qry.append(" where dept.orgnztnlevel='"+ orgLevel+ "' and dept.mappedOrgnztnId='"+ orgId+ "' and feed.hide_show='Active' and feed.moduleName='"+module+"' ORDER BY dept.deptName ASC");
	}
	else if (module.equalsIgnoreCase("HDM") || module.equalsIgnoreCase("CASTM")) {
	    qry.append(" select distinct dept.id,dept.deptName from feedback_type as feed");
 	qry.append(" inner join department as dept on feed.dept_subdept=dept.id");
	        qry.append(" where dept.orgnztnlevel='"+ orgLevel+ "' and dept.mappedOrgnztnId='"+ orgId+ "' and feed.hide_show='Active' and feed.moduleName='"+module+"' ORDER BY dept.deptName ASC");
	    }
	else if(module.equalsIgnoreCase("FM") )
	{
	// Added by Avinash for FM 
	    qry.append(" select distinct dept.id,dept.deptName from feedback_type as feed");
	qry.append(" inner join department as dept on feed.dept_subdept=dept.id");
	        qry.append(" where dept.orgnztnlevel='"+ orgLevel+ "' and dept.mappedOrgnztnId='"+ orgId+ "' and feed.hide_show='Active' and feed.moduleName='"+module+"' ORDER BY dept.deptName ASC");
	}
	else if(module.equalsIgnoreCase("ASTM") )
	{}
	else {
	    qry.append(" select distinct dept.id,dept.deptName from department as dept");
	        qry.append(" where orgnztnlevel='"+ orgLevel+ "' and mappedOrgnztnId='"+ orgId+ "' ORDER BY dept.deptName ASC");
	}
	Session session = null;
	Transaction transaction = null;
	session = connection.getCurrentSession();
	transaction = session.beginTransaction();
	dept_subdeptList = session.createSQLQuery(qry.toString()).list();
	transaction.commit();

	} catch (SQLGrammarException e) {
	e.printStackTrace();
	}
	return dept_subdeptList;
	
	}
	
	@SuppressWarnings("unchecked")
	public boolean check_Table(String table, SessionFactory connection)
	 {
	boolean flag = false;
	List list = null;
	String value = null;
	Session session = null;  
	Transaction transaction = null;  
	try {
	SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) connection;
	Properties props = sessionFactoryImpl.getProperties();
	String url = props.get("hibernate.connection.url").toString();
	String[] urlArray = url.split(":");
	String db_name = urlArray[urlArray.length - 1];
	String query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema ='"+db_name.substring(5)+"' AND table_name = '"+table+"'";
	    session=connection.getCurrentSession(); 
	    transaction = session.beginTransaction(); 
	    list=session.createSQLQuery(query).list();  
	transaction.commit();
	}catch (Exception e) {
	e.printStackTrace();
	}
	if (list!=null && list.size()>0) {
	value = list.get(0).toString();
	if (value.equalsIgnoreCase("1")) {
	flag=true;
	}
	}
	return flag;
	 }
	
	
	
	public synchronized static String getReAssignedTicketNo(String ticket_no)
	 {
	String ticketno="NA",prefix="",sufix="",start="";
	try {
	   // Code for getting the Ticket Type from table
	if (ticket_no!=null && !ticket_no.equals("") && !ticket_no.equals("NA")) {
	 
	 start= ticket_no.substring(0, 5);
	 prefix=ticket_no.substring(ticket_no.length()-6, ticket_no.length()-4);
	 sufix=ticket_no.substring(ticket_no.length()-4, ticket_no.length());
	
	  setAN(Integer.parseInt(sufix));
	  ticketno=start+prefix+ ++AN;
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	return ticketno;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmp4Escalation(String dept_subDept,String module, String level,String floor_status,String floor,SessionFactory connectionSpace) {
	List<Integer> list = new ArrayList<Integer>();
	//String qry = null;
	StringBuilder query =new StringBuilder();
	try {
	String shiftname = DateUtil.getCurrentDateUSFormat().substring(8,10)+ "_date";
	query.append("select distinct emp.id from employee_basic as emp");
	query.append(" inner join compliance_contacts contacts on contacts.emp_id = emp.id");
	query.append(" inner join roaster_conf roaster on contacts.id = roaster.contactId");
	if (module!=null && !module.equals("") && module.equals("HDM")) {
	query.append(" inner join subdepartment sub_dept on roaster.dept_subdept = sub_dept.id ");
	query.append(" inner join department dept on sub_dept.deptid = dept.id ");
	query.append(" inner join shift_conf shift on sub_dept.id = shift.dept_subdept ");
	}
	else if (module!=null && !module.equals("") && module.equals("FM")) {
	query.append(" inner join department dept on roaster.dept_subdept = dept.id ");
	query.append(" inner join shift_conf shift on dept.id = shift.dept_subdept ");
	}
	query.append(" where shift.shiftName="+ shiftname+ " and roaster.status='Active' and roaster.attendance='Present' and contacts.level='"+level+"' and contacts.work_status='3' and contacts.moduleName='"+module+"'");
	query.append(" and shift.shiftFrom<='"+ DateUtil.getCurrentTime()+ "' and shift.shiftTo >'"+ DateUtil.getCurrentTime() + "'");
	if (module!=null && !module.equals("") && module.equals("HDM")) {
	if (floor_status.equalsIgnoreCase("Y")) {
	query.append(" and roaster.floor='"+floor+"'  and dept.id=(select deptid from subdepartment where id='"+ dept_subDept+ "')");
	}
	else if (floor_status.equalsIgnoreCase("N")) {
	query.append(" and sub_dept.id='"+ dept_subDept+ "'");
	}
	}
	else if (module!=null && !module.equals("") && module.equals("FM")) {
	query.append(" and dept.id='"+ dept_subDept+ "'");
	}
	list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRandomEmployee(String dept_subDept,
	String deptLevel, String toLevel,List empId,String module, SessionFactory connectionSpace) {
	List<Integer> list = new ArrayList<Integer>();
	StringBuilder query = new StringBuilder();
	String arr =empId.toString().replace("[", "(").replace("]", ")");
	try {
	query.append("select distinct allot_to from feedback_status as feed_status");
	if (module!=null && !module.equals("") && module.equalsIgnoreCase("HDM")) {
	query.append(" inner join subdepartment sub_dept on feed_status.to_dept_subdept = sub_dept.id ");
	query.append(" inner join compliance_contacts contacts on contacts.emp_id  = feed_status.allot_to ");
	query.append(" where sub_dept.id='"+dept_subDept+"' and contacts.level='"+toLevel+"' and allot_to in "+arr+" and feed_status.open_date='"+ DateUtil.getCurrentDateUSFormat() + "'");
	}
	else if (module!=null && !module.equals("") && module.equalsIgnoreCase("FM")) {
	query.append(" inner join department dept on feed_status.to_dept_subdept = dept.id ");
	query.append(" inner join compliance_contacts contacts on contacts.emp_id  = feed_status.allot_to ");
	query.append(" where dept.id='"+dept_subDept+"' and contacts.level='"+toLevel+"' and allot_to in "+arr+" and feed_status.open_date='"+ DateUtil.getCurrentDateUSFormat() + "'");
	}
	query.append(" and feed_status.moduleName='"+ module + "'");
	list = new createTable().executeAllSelectQuery(query.toString(), connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	}
	return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getPendingAllotto(String subdept,String module,SessionFactory connectionSpace) {
	/**
	 * need to work on this problem
	 */
	Session session = null;
	List list = new ArrayList();
	String qry = null;
	try {
	session = HibernateSessionFactory.getSession();
	qry = "select distinct allot_to from feedback_status where open_date='"
	+ DateUtil.getCurrentDateUSFormat()
	+ "' and to_dept_subdept="+subdept+" and status='Pending' and moduleName='"+module+"'";
	list = new createTable()
	.executeAllSelectQuery(qry, connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	} finally {
	session.flush();
	session.close();
	}
	return list;
	}
	
	@SuppressWarnings("unchecked")
	public String getRandomEmployee(String module, List empId,SessionFactory connectionSpace) {
	/**
	 * need to work on this problem
	 */
	Session session = null;
	List list = new ArrayList();
	String allotmentId = "";
	StringBuilder sb = new StringBuilder();
	try {
	session = HibernateSessionFactory.getSession();
	sb.append("select allot_to from feedback_status where open_date='"+ DateUtil.getCurrentDateUSFormat()+ "' and moduleName='"+module+"'");
	sb.append(" group by allot_to having allot_to in "+ empId.toString().replace("[", "(").replace("]", ")")+ " order by count(allot_to) limit 1 ");
	
	list = new createTable()
	.executeAllSelectQuery(sb.toString(), connectionSpace);
	} catch (Exception e) {
	e.printStackTrace();
	} finally {
	session.flush();
	session.close();
	}
	if (list != null && list.size() > 0) {
	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	Object object = (Object) iterator.next();
	allotmentId = object.toString();
	}
	}
	return allotmentId;
	}
	@SuppressWarnings("unchecked")
	public String getConfigMessage4Asset(FeedbackPojo feedbackObj,String title,String status,String deptLevel,boolean flag) {
	 List orgData= new LoginImp().getUserInfomation("1", "IN");
	String orgName="";
	if (orgData!=null && orgData.size()>0) {
	for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	orgName=object[0].toString();
	}
	}
	    StringBuilder mailtext = new StringBuilder("");
	    mailtext.append("<br><br><table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+orgName+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+title+"</b></td></tr><tr><td>&nbsp;</td></tr></tbody></table>");
	    if (flag) {
	        mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeedback_allot_to())+ ",</b>");
	    }
	    else if (!flag) {
	    	mailtext.append("<b>Dear " +DateUtil.makeTitle(feedbackObj.getFeed_registerby())+ ",</b>");	
	}
	    mailtext.append("<br><br><b>Hello !!!</b><br>");
	    if (flag) {
	mailtext.append("Here is a ticket mapped for you as per the Escalation Matrix. Kindly resolve the following to avoid escalating it to next level:- <br>");
	    }
	    else if (!flag) {
	    mailtext.append("Here is a feedback detail in mail body as suggested by you:-<br>");	
	}
	mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='80%' align='center'><tbody>");
	    mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ticket&nbsp;No:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getTicket_no()+ "</td></tr>");
	    mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(feedbackObj.getOpen_date())+ "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Lodge&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getOpen_time().substring(0, 5) + " Hrs</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;By:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_registerby() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;To&nbsp;Dept:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_to_dept() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Location:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getLocation() + " </td></tr>");
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_catg() + "</td></tr>");
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Sub-Category:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeedback_subcatg() + "</td></tr>");
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Feedback&nbsp;Brief:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getFeed_brief() + "</td></tr>");
	
	
	if (flag) {
	 if (status.equals("Pending") && !feedbackObj.getEscalation_date().equals("NA")) {
	     mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Next&nbsp;Escalation&nbsp;Date&nbsp;Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.convertDateToIndianFormat(feedbackObj.getEscalation_date())+"  "+feedbackObj.getEscalation_time().substring(0, 5)+" Hrs</td></tr>");
	 }
	}
	else
	 {
	   if (status.equals("Pending") && !feedbackObj.getEscalation_date().equals("NA")) {
	      mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Resolve&nbsp;Up&nbsp;To:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+DateUtil.convertDateToIndianFormat(feedbackObj.getEscalation_date())+"  "+feedbackObj.getEscalation_time().substring(0, 5)+" Hrs</td></tr>");
	   }
	  }
	
	if (status.equalsIgnoreCase("High Priority")) {
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> High Priority Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getHp_reason() + " </td></tr>");
	}
	else if (status.equalsIgnoreCase("Snooze")) {
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Up To Date:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ DateUtil.convertDateToIndianFormat(feedbackObj.getSn_date()) + "</td></tr>");
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Up To Time:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSn_time() + " Hrs</td></tr>");
	mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Snooze Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSn_reason() + "</td></tr>");
	}
        else if (status.equalsIgnoreCase("Ignore")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Ignore Reason:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getIg_reason() + "</td></tr>");
	}
        else if (status.equalsIgnoreCase("Resolved")) {
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> TAT:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getResolve_duration() + "</td></tr>");
        mailtext.append("<tr  bgcolor='#ffffff'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Spare Used:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getSpare_used() + "</td></tr>");
	mailtext.append("<tr  bgcolor='#e8e7e8'><td align='left' width='30%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b> Resolve Remark:</b></td><td align='center' width='50%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+ feedbackObj.getResolve_remark()+ "</td></tr>");
	}
	mailtext.append("</tbody></table><font face ='verdana' size='2'><br>Thanks !!!</FONT>");
	mailtext.append("<br><div align='justify'><font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT></div>");
	    return mailtext.toString();
	}

	public static int getAN()
	{
	return AN;
	}

	public static void setAN(int an)
	{
	AN = an;
	}
	
	@SuppressWarnings("unchecked")
	public String getConfigMailForMorningReport(List morninglist,String empName)
	  {
	  List orgData= new LoginImp().getUserInfomation("1", "IN");
	String orgName="";
	if (orgData!=null && orgData.size()>0) {
	for (Iterator iterator = orgData.iterator(); iterator.hasNext();) {
	Object[] object = (Object[]) iterator.next();
	orgName=object[0].toString();
	}
	}
	StringBuilder mailtext=new StringBuilder("");
	mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>"+orgName+"</b></td></tr></tbody></table>");
	    mailtext.append("<table width='100%' align='center' style='border: 0'><tbody><tr><td align='center' style=' color:#111111; font-size:12px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Help Desk Manager</b></td></tr></tbody></table>");
        mailtext.append("<b>Dear "+DateUtil.makeTitle(empName)+",</b>");
        mailtext.append("<br><br><b>Hello!!!</b>");
        mailtext.append("<br><br><table width='100%' align='center' style='border:0' cellpadding='4' cellspacing='0'><tbody><tr><td align='center' style=' color:#111111; font-size:14px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Open Ticket Detail</b></td></tr></tbody></table>");
	
        
        mailtext.append("<br><table border='1px solid #5b5a5a' cellpadding='4' cellspacing='0' style='border-collapse: collapse' width='100%' align='center'><tbody>");
        mailtext.append("<tr bgcolor='#8db7d6'><td align='center'  width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Ticket No</b></td></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Lodge By</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Open At</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>For</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Alloted To</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Loaction</b></td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'><b>Status</b></td></tr>");
      //System.out.println("******Sized : "+morninglist.size());
        for (Iterator iterator1 = morninglist.iterator(); iterator1.hasNext();)
	{
         
        	 try{FeedbackPojo object1 =  (FeedbackPojo) iterator1.next();
        	 if(object1.getStatus().equalsIgnoreCase("Pending"))
	 mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getTicket_no()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeed_by()+",       "+object1.getFeedback_by_dept()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getOpen_date()+",       "+object1.getOpen_time()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_subcatg()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getEmpName()+",       "+object1.getFeedback_to_dept()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getLocation()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getStatus()+"</td></tr>");
        	}catch(Exception e){e.printStackTrace();}
	}  
       // mailtext.append("<tr colspan='10' width='10%' ><td colspan='10' ></td></tr>");
        for (Iterator iterator1 = morninglist.iterator(); iterator1.hasNext();)
	{
         
        	 try{FeedbackPojo object1 =  (FeedbackPojo) iterator1.next();
        	 if(object1.getStatus().equalsIgnoreCase("High Priority"))
        	 mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getTicket_no()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeed_by()+",   "+object1.getFeedback_by_dept()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getOpen_date()+",   "+object1.getOpen_time()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_subcatg()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_to_dept()+",   "+object1.getEmpName()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getLocation()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getStatus()+"</td></tr>");
                	}catch(Exception e){e.printStackTrace();}
	}  
        
     //   mailtext.append("<tr colspan='10' width='10%' ><td colspan='10' ></td></tr>");
        for (Iterator iterator1 = morninglist.iterator(); iterator1.hasNext();)
	{
         
        	 try{FeedbackPojo object1 =  (FeedbackPojo) iterator1.next();
        	 if(object1.getStatus().equalsIgnoreCase("Snooze"))
        	 mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getTicket_no()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeed_by()+",   "+object1.getFeedback_by_dept()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getOpen_date()+",   "+object1.getOpen_time()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_subcatg()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_to_dept()+",   "+object1.getEmpName()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getLocation()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getStatus()+"</td></tr>");
                 	}catch(Exception e){e.printStackTrace();}
	}  
       // mailtext.append("<tr colspan='10' width='10%' ><td colspan='10' ></td></tr>");
        for (Iterator iterator1 = morninglist.iterator(); iterator1.hasNext();)
	{
         
        	 try{FeedbackPojo object1 =  (FeedbackPojo) iterator1.next();
        	 if(object1.getStatus().equalsIgnoreCase("Hold"))
        	 mailtext.append("<tr  bgcolor='#e8e7e8'><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getTicket_no()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeed_by()+",   "+object1.getFeedback_by_dept()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getOpen_date()+",   "+object1.getOpen_time()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_subcatg()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getFeedback_to_dept()+",   "+object1.getEmpName()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getLocation()+"</td><td align='center' width='10%' style=' color:#111111; font-size:11px; font-family:Verdana, Arial, Helvetica, sans-serif;'>"+object1.getStatus()+"</td></tr>");
              	}catch(Exception e){e.printStackTrace();}
	}  
        
         mailtext.append("<table><tbody></tbody></table><font face ='verdana' size='2'><br><brThanks !!!<br><br></FONT>");
       	mailtext.append("<font face ='TIMESROMAN' size='2'>This message was sent  to you because your Email ID has been mapped by Admin User of the Automated Software \n provided by DreamSol TeleSolutions Pvt. Ltd. based on certain event or trigger generated by the system as required \n by the client. \nIn case, if you find the data mentioned in the mail is incorrect or if you prefer not to receive the further E-mails then <BR>please do not reply to this mail instead contact to your administrator or for any support related to the software service \n provided, visit contact details over 'http://www.dreamsol.biz/contact_us.html' or you may kindly mail your feedback to <br>support@dreamsol.biz</FONT>");
        	return mailtext.toString() ; 
	  }

	
	
	
	/*public AtomicInteger getAN() {
	return AN;
	}
	public void setAN(AtomicInteger an) {
	AN = an;
	}*/
	
	
	
}