package com.Over2Cloud.ctrl.Registation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.RegHieInformation;
import com.Over2Cloud.CommonClasses.Cryptography;
import com.Over2Cloud.CommonClasses.TableInfo;
import com.Over2Cloud.ConnectionFactory.AllConnection;
import com.Over2Cloud.ConnectionFactory.AllConnectionEntry;
import com.Over2Cloud.ConnectionFactory.ConnectionFactory;
import com.Over2Cloud.modal.dao.imp.PartnerRegistation.PartnerImpRegistation;
import com.Over2Cloud.modal.dao.imp.login.LoginImp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import freemarker.template.utility.Execute;

public class Reportregistation extends ActionSupport {
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	static final Log log = LogFactory.getLog(PartnerRegistation.class);
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String uName = (String) session.get("uName");
	String accountid = (String) session.get("accountid");
	String countryiso = (String) session.get("countryid");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	private Integer rows = 0;

	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;

	// sorting order - asc or desc
	private String sord;

	// get index row - i.e. user click to sort.
	private String sidx;

	// Search Field
	private String searchField;

	// The Search String
	private String searchString;

	// Limit the result when using local data, value form attribute rowTotal
	private Integer totalrows;

	// he Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper;

	// Your Total Pages
	private Integer total = 0;

	// All Records
	private Integer records = 0;

	private boolean loadonce = false;
	private String oper;
	private String id;
	private List<RegHieInformation> regHries = new ArrayList<RegHieInformation>();
	private String productid;

	@Override
	public String execute() throws Exception {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String ViewOurClienturlGrid1() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			String Clientinfo[] = getId().split("_");
			List ViewAllReportInfo = new PartnerImpRegistation()
					.GetAllUserLevel(Clientinfo[0].toString().trim(),
							Clientinfo[1].toString().trim());
			if (ViewAllReportInfo != null && ViewAllReportInfo.size() > 0) {
				for (Iterator iterator = ViewAllReportInfo.iterator(); iterator
						.hasNext();) {
					RegHieInformation obbb = new RegHieInformation();
					Object[] objectCol = (Object[]) iterator.next();
					obbb.setAccounttype(objectCol[2].toString());
					obbb.setOrgname(objectCol[1].toString());
					obbb.setOrgusername(objectCol[3].toString());
					obbb.setProductcode(objectCol[4].toString());
					obbb.setProductappuser(objectCol[5].toString());
					List<String> Productuser = Arrays.asList(objectCol[5]
							.toString().split("#"));
					int count1 = 0;
					for (String h : Productuser) {
						int count = Integer.parseInt(h);
						count1 = count1 + count;
					}
					obbb.setProductuser(count1);
					obbb.setUsername(Cryptography.decrypt(objectCol[0]
							.toString(), DES_ENCRYPTION_KEY));
					obbb.setValidifyfrom(objectCol[6].toString());
					obbb.setValididyto(objectCol[7].toString());
					obbb
							.setProductid(Integer.parseInt(objectCol[8]
									.toString()));
					obbb.setCombinekey(Cryptography.decrypt(objectCol[0]
							.toString(), DES_ENCRYPTION_KEY)
							+ "_" + objectCol[9].toString());
					obbb.setAccountid(objectCol[10].toString() + "-"
							+ objectCol[9].toString());
					obbb.setIsblock(objectCol[11].toString());
					regHries.add(obbb);

				}
				setRegHries(regHries);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	public String blockClientAll() {
		String result = "";
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			List<String> asd = Arrays.asList(getProductid().split("-"));
			List ChunkInfo = new LoginImp().chunkDomainName(asd.get(2)
					.toString().trim(), asd.get(1).toString().trim());
			if (ChunkInfo != null && ChunkInfo.size() > 0) {
				for (Iterator iterator1 = ChunkInfo.iterator(); iterator1
						.hasNext();) {
					Object[] objectCol1 = (Object[]) iterator1.next();
					Map<String, Object> wherClause = new HashMap<String, Object>();
					Map<String, Object> condtnBlock = new HashMap<String, Object>();
					// Which Value Update
					if (asd.get(3).toString().trim().equals("Block")) {
						wherClause.put("isactive", "A");
					}
					if (asd.get(3).toString().trim().equals("Live")) {
						wherClause.put("isactive", "B");
					}

					// getting evry client session factory load for creating the
					// chunk space data base
					String dbbname = "clouddb", ipAddress = null, username = null, paassword = null, port = null;
					ipAddress = objectCol1[2].toString();
					username = objectCol1[4].toString();
					paassword = objectCol1[5].toString();
					port = objectCol1[3].toString();
					AllConnection Conn = new ConnectionFactory(dbbname,
							ipAddress, username, paassword, port);
					AllConnectionEntry Ob1 = Conn.GetAllCollectionwithArg();
					SessionFactory sessfactNew = Ob1.getSession();
					// Condition For Update 10001:localhost:10001_10050

					condtnBlock.put("accountid", asd.get(2).trim());
					boolean data = new TableInfo().updateTableColumn(
							objectCol1[0].toString().trim() + "_"
									+ objectCol1[1].toString().trim()
									+ "_space_configuration", wherClause,
							condtnBlock, sessfactNew);
					if (data) {
						Map<String, Object> wherClause1 = new HashMap<String, Object>();
						Map<String, Object> condtnBlock1 = new HashMap<String, Object>();
						// Which Value Update
						if (asd.get(3).toString().trim().equals("Block")) {
							wherClause1.put("isLive", "Live");
							result = "live";
						}
						if (asd.get(3).toString().trim().equals("Live")) {
							wherClause1.put("isLive", "Block");
							result = "block";
						}
						// Condition For Update 10001:localhost:10001_10050
						condtnBlock1.put("id", asd.get(2).toString().trim());

						boolean data1 = new TableInfo().updateTableColumn(
								"client_info", wherClause1, condtnBlock1,
								connectionSpace);
						if (data1) {
							Thread.sleep(200);
						}
					}
				}

			} else {

			}
		}

		catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}

	public List<RegHieInformation> getRegHries() {
		return regHries;
	}

	public void setRegHries(List<RegHieInformation> regHries) {
		this.regHries = regHries;
	}

	public Map getSession() {
		return session;
	}

	public void setSession(Map session) {
		this.session = session;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public Integer getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public boolean isLoadonce() {
		return loadonce;
	}

	public void setLoadonce(boolean loadonce) {
		this.loadonce = loadonce;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}
}
