package com.Over2Cloud.ctrl.patientcare;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.hibernate.SessionFactory;
import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.CommonClasses.ValidateSession;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.action.GridPropertyBean;
import com.Over2Cloud.common.compliance.ComplianceUniversalAction;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class PrintActivityBoardData extends GridPropertyBean implements ServletRequestAware
{

	
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	SessionFactory connectionSpace = (SessionFactory) session.get("connectionSpace");
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	private String id;
	private String dashFor = "";
	private String complaintid;
	private String downloadID;
	@SuppressWarnings("unchecked")
	public List uidPatirnt = null;
	private ArrayList<ArrayList<String>> pUHID;
	private ArrayList<ArrayList<String>> pDataCart;
	private HttpServletRequest request;
	private Map<String, String> columnMap;
	private String excelFileName;
	private String[] columnNames;
	private FileInputStream excelStream;
	private String maxDateValue,minDateValue,feed_status,corp_type,corp_name,services,ac_manager,med_location,patienceSearch,wildSearch;
	// for redirect print cart page

	@SuppressWarnings("unchecked")
	public String printCartPage()
	{
		ComplianceUniversalAction CUA = new ComplianceUniversalAction();
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		String ordids = "";
		if ("gridPrint".equalsIgnoreCase(dashFor))
		{
			ordids = request.getParameter("complaintid").toString();
			if ((ordids.lastIndexOf(",") == ordids.length() - 1))
			{
				ordids = ordids.substring(0, (ordids.length() - 1));
			}

		} else
		{
			String[] ordIdCart = request.getParameter("complaintid").toString().split(",");
			for (int i = 0; i < ordIdCart.length; i++)
			{
				String[] ordUIdCart = ordIdCart[i].split("kk");
				if (ordids.length() > 1)
				{
					ordids += ", " + ordUIdCart[1].toString();
				} else
				{

					ordids = ordUIdCart[1].toString();
				}
			}
		}

		uidPatirnt = new ArrayList();
		StringBuilder qry = new StringBuilder("");
		qry.append(" select cpd.ticket,cpd.patient_name,cm.corp_name,cpd.status,csm.serv_loc,cpd.preferred_time,csm.service_name,csd.EHC_packages_widget ");
		qry.append(" from corporate_patience_data as cpd ");
		qry.append(" left join corporate_master as cm on cm.id=cpd.corp_name ");
		qry.append(" left join cps_service as csm on csm.id=cpd.services ");
		qry.append(" left join cps_services_details as csd on csd.cps_id=cpd.id ");
		qry.append("  WHERE cpd.id IN (" + ordids + ") group by cpd.id ORDER BY  cpd.ticket");
		//////System.out.println("vvvv "+qry);
		List dataList = cbt.executeAllSelectQuery(qry.toString(), connectionSpace);
		ArrayList<String> tempList = null;
		ArrayList<String> tempList1 = null;
		pUHID = new ArrayList<ArrayList<String>>();
		if (dataList != null && dataList.size() > 0)
		{

			for (Iterator iterator1 = dataList.iterator(); iterator1.hasNext();)
			{
				Object[] object = (Object[]) iterator1.next();

				// make list for uhid and room no
				tempList1 = new ArrayList<String>();

				tempList1.add(CUA.getValueWithNullCheck(object[0]));
				tempList1.add(CUA.getValueWithNullCheck(object[1]));
				tempList1.add(CUA.getValueWithNullCheck(object[2]));
				tempList1.add(CUA.getValueWithNullCheck(object[3]));
				tempList1.add(CUA.getValueWithNullCheck(object[4]));
				tempList1.add(CUA.getValueWithNullCheck(object[5]));
				tempList1.add(CUA.getValueWithNullCheck(object[6]));
				tempList1.add(CUA.getValueWithNullCheck(object[7]));
				pUHID.add(tempList1);
			}

			pDataCart = new ArrayList<ArrayList<String>>();
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();

				tempList = new ArrayList<String>();
				tempList.add(CUA.getValueWithNullCheck(object[0]));
				tempList.add(CUA.getValueWithNullCheck(object[1]));
				tempList.add(CUA.getValueWithNullCheck(object[2]));
				tempList.add(CUA.getValueWithNullCheck(object[3]));
				tempList.add(CUA.getValueWithNullCheck(object[4]));
				tempList.add(CUA.getValueWithNullCheck(object[5]));
				tempList.add(CUA.getValueWithNullCheck(object[6]));
				tempList.add(CUA.getValueWithNullCheck(object[7]));

				pDataCart.add(tempList);

			}

		}
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String currentDataCPS()
	{

		boolean sessionFlag = ValidateSession.checkSession();
		if (sessionFlag)
		{
			try
			{
				columnMap = new LinkedHashMap<String, String>();// 
				columnMap.put("cpd.status", "Action");
				columnMap.put("cpd.ticket", "Ticket No");
				columnMap.put("cpd.level", "Level");
				columnMap.put("cpd.uhid", "UHID");
				columnMap.put("cpd.patient_name", "Patient Name");
				columnMap.put("csm.service_name", "Service");
				columnMap.put("cpd.med_location", "Location");
				columnMap.put("cpd.preferred_time", "Prefeerred Schedule");
				columnMap.put("cpd.service_book_time", "Book Schedule");
				columnMap.put("cpd.remarks", "Remark");
				columnMap.put("cpd.feed_status", "Patient Type");
				columnMap.put("cmh.CUSTOMER_NAME", "Corporate Name");
				columnMap.put("cpd.ac_manager", "Account Manager");
				columnMap.put("cpd.userName", "Action By");
				columnMap.put("cpd.next_level_esc_date", "Next Level ESC Date");
				columnMap.put("cpd.next_level_esc_time", "Next Level ESC Time");
				columnMap.put("emp.empName", "Service Manager");
				columnMap.put("cpd.service_book_time", "Service Booked Time");
				

				if (columnMap != null && columnMap.size() > 0)
				{
					session.put("columnMap", columnMap);
				}
				return SUCCESS;
			} catch (Exception ex)
			{
				ex.printStackTrace();
				return ERROR;
			}
		} else
		{
			return LOGIN;
		}
	
	}
	@SuppressWarnings("unchecked")
	public String excelDownload()
	{
		String returnResult = ERROR;
		boolean sessionFlag = ValidateSession.checkSession();
		excelFileName = "Corporate Patient Detail";
		if (sessionFlag)
		{
			try
			{
				List keyList = new ArrayList();
				List titleList = new ArrayList();
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				StringBuilder query = new StringBuilder();
				if (columnNames != null && columnNames.length > 0)
				{
					keyList = Arrays.asList(columnNames);
					Map<String, String> tempMap = new LinkedHashMap<String, String>();
					tempMap = (Map<String, String>) session.get("columnMap");
					if (session.containsKey("columnMap"))
						session.remove("columnMap");

					List dataList = null;
					for (int index = 0; index < keyList.size(); index++)
					{
						titleList.add(tempMap.get(keyList.get(index)));
					}
					if (keyList != null && keyList.size() > 0)
					{
						query.append("SELECT DISTINCT ");
						int i = 0;
						for (Iterator it = keyList.iterator(); it.hasNext();)
						{
							Object obdata = (Object) it.next();
							if (obdata != null)
							{
								if (i < keyList.size() - 1)
								{
									query.append(obdata.toString() + ",");
								} else
								{
									query.append(obdata.toString());
								}
							}
							i++;
						}
						query.append(" from corporate_patience_data as cpd  ");
						query.append(" left join corporate_master as cm on cm.id=cpd.corp_name ");
						query.append(" left join cps_service as csm on csm.id=cpd.services ");
						query.append(" left join employee_basic as emp on emp.id=cpd.service_manager ");//maxDateValue  minDateValue
						query.append(" left join dreamsol_bl_corp_hc_pkg as cmh on cmh.id=cpd.corp_name ");
						query.append(" WHERE cpd.id!=0 and cpd.id IN (" + downloadID + ") ");
						if ((minDateValue != null && maxDateValue != null))
						{
							String str[] = maxDateValue.split("-");
							if (str[0].length() < 4)
								query.append("  AND cpd.date BETWEEN '" + DateUtil.convertDateToUSFormat(minDateValue) + "' AND '" + DateUtil.convertDateToUSFormat(maxDateValue) + "'");
							else
								query.append("  AND cpd.date BETWEEN '" + minDateValue + "' AND '" + maxDateValue + "'");
						}
						/*if (feed_status != null && !"-1".equalsIgnoreCase(feed_status) && !"All".equalsIgnoreCase(feed_status))
						{
							query.append(" AND cpd.feed_status='" + getFeed_status() + "' ");
						}
						if(corp_name!=null  && !"-1".equalsIgnoreCase(corp_name) && !"All".equalsIgnoreCase(corp_name))
						{
							query.append(" AND cm.corp_name='"+getCorp_name()+"' ");
						}
						if(services!=null  && !"-1".equalsIgnoreCase(services) && !"All".equalsIgnoreCase(services))
						{
							query.append(" AND csm.service_name='"+getServices()+"' ");
						}
				    	if(med_location!=null  && !"-1".equalsIgnoreCase(med_location) && !"All".equalsIgnoreCase(med_location))
						{
							query.append(" AND csm.serv_loc='"+getMed_location()+"' ");
						}
				    	if(ac_manager!=null  && !"-1".equalsIgnoreCase(ac_manager) && !"All".equalsIgnoreCase(ac_manager))
						{
							query.append(" AND emp.empName='"+getAc_manager()+"' ");
						}
				    	if (patienceSearch != null && !patienceSearch.equalsIgnoreCase("") && !patienceSearch.contains("undefined") )
						{
							query.append(" AND cpd.patient_name LIKE '%" + patienceSearch + "%'");
						}
				    	if (wildSearch != null && !wildSearch.equalsIgnoreCase(""))
						{ 
							query.append(" AND cpd.uhid LIKE '%" + wildSearch + "%'");
						}*/
						query.append(" GROUP BY cpd.ticket ORDER BY cpd.ticket DESC ");
						System.out.println("bbbbbbbb   "+query);
						dataList = cbt.executeAllSelectQuery(query.toString(), connectionSpace);
						String orgDetail = (String) session.get("orgDetail");
						String orgName = "";
						if (orgDetail != null && !orgDetail.equals(""))
						{
							orgName = orgDetail.split("#")[0];
						}
						if (dataList != null && titleList != null && keyList != null)
						{
							excelFileName = new HelpdeskUniversalHelper().writeDataInExcel(dataList, titleList, keyList, excelFileName, orgName, true, connectionSpace);
						}
						if (excelFileName != null)
						{
							excelStream = new FileInputStream(excelFileName);
						}
					}
					returnResult = SUCCESS;
				} else
				{
					addActionMessage("There are some error in data!!!!");
					returnResult = ERROR;
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else
		{
			returnResult = LOGIN;
		}
		return returnResult;

	
	}
	public String getValueWithNullCheck(Object value)
	{
		return (value == null || value.toString().equals("")) ? "NA" : value.toString();
	}
	public String getDashFor()
	{
		return dashFor;
	}

	public void setDashFor(String dashFor)
	{
		this.dashFor = dashFor;
	}
	public String getComplaintid()
	{
		return complaintid;
	}

	public void setComplaintid(String complaintid)
	{
		this.complaintid = complaintid;
	}
	@Override
	public void setServletRequest(HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		this.request = request;
	}
	public ArrayList<ArrayList<String>> getPUHID()
	{
		return pUHID;
	}

	public void setPUHID(ArrayList<ArrayList<String>> puhid)
	{
		pUHID = puhid;
	}

	public ArrayList<ArrayList<String>> getPDataCart()
	{
		return pDataCart;
	}

	public void setPDataCart(ArrayList<ArrayList<String>> dataCart)
	{
		pDataCart = dataCart;
	}
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	public Map<String, String> getColumnMap() {
		return columnMap;
	}
	public void setColumnMap(Map<String, String> columnMap) {
		this.columnMap = columnMap;
	}
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	public String[] getColumnNames() {
		return columnNames;
	}
	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	public FileInputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(FileInputStream excelStream) {
		this.excelStream = excelStream;
	}
	public ArrayList<ArrayList<String>> getpUHID() {
		return pUHID;
	}
	public void setpUHID(ArrayList<ArrayList<String>> pUHID) {
		this.pUHID = pUHID;
	}
	public ArrayList<ArrayList<String>> getpDataCart() {
		return pDataCart;
	}
	public void setpDataCart(ArrayList<ArrayList<String>> pDataCart) {
		this.pDataCart = pDataCart;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public String getMaxDateValue() {
		return maxDateValue;
	}
	public void setMaxDateValue(String maxDateValue) {
		this.maxDateValue = maxDateValue;
	}
	public String getMinDateValue() {
		return minDateValue;
	}
	public void setMinDateValue(String minDateValue) {
		this.minDateValue = minDateValue;
	}
	public String getFeed_status() {
		return feed_status;
	}
	public void setFeed_status(String feedStatus) {
		feed_status = feedStatus;
	}
	public String getCorp_type() {
		return corp_type;
	}
	public void setCorp_type(String corpType) {
		corp_type = corpType;
	}
	public String getCorp_name() {
		return corp_name;
	}
	public void setCorp_name(String corpName) {
		corp_name = corpName;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getAc_manager() {
		return ac_manager;
	}
	public void setAc_manager(String acManager) {
		ac_manager = acManager;
	}
	public String getMed_location() {
		return med_location;
	}
	public void setMed_location(String medLocation) {
		med_location = medLocation;
	}
	public String getPatienceSearch() {
		return patienceSearch;
	}
	public void setPatienceSearch(String patienceSearch) {
		this.patienceSearch = patienceSearch;
	}
	public String getWildSearch() {
		return wildSearch;
	}
	public void setWildSearch(String wildSearch) {
		this.wildSearch = wildSearch;
	}
	public String getDownloadID() {
		return downloadID;
	}
	public void setDownloadID(String downloadID) {
		this.downloadID = downloadID;
	}

	

}
