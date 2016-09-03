package com.Over2Cloud.ctrl.MyCloud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.Over2Cloud.BeanUtil.RegHieInformation;
import com.Over2Cloud.modal.dao.impl.common.onlineWebImp;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OnlineWebCtrl extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	static final Log log = LogFactory.getLog(OnlineWebCtrl.class);
	Map session = ActionContext.getContext().getSession();
	String uName = (String) session.get("uName");
	String accountid = (String) session.get("accountid");
	String countryiso = (String) session.get("countryid");
	private List<RegHieInformation> regHries = new ArrayList<RegHieInformation>();
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
	private Integer id;

	@Override
	public String execute() throws Exception {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		return SUCCESS;
	}

	public String GetOnlineRegistationViewDetails() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			List GetallListforUser = new onlineWebImp().GetallOnlineWeb("Web");
			if (GetallListforUser != null && GetallListforUser.size() > 0) {
				for (Iterator iterator = GetallListforUser.iterator(); iterator
						.hasNext();) {
					RegHieInformation ob1 = new RegHieInformation();
					Object[] objectCol = (Object[]) iterator.next();
					if (objectCol[0] == null) {
						objectCol[0] = "NA";
					} else {
						ob1.setAccountid(objectCol[4].toString() + "-"
								+ objectCol[0].toString());
						/*System.out.println("Account Id::"
								+ objectCol[0].toString());*/
					}
					if (objectCol[1] == null) {
						objectCol[1] = "NA";
					} else {
						ob1.setOrgname(objectCol[1].toString());
						/*System.out.println("Orgnization Name:"
								+ objectCol[1].toString());*/
					}
					if (objectCol[2] == null) {
						objectCol[2] = "NA";
					} else {
						ob1.setOrgusername(objectCol[2].toString());
						/*System.out.println("Name Of Regis::"
								+ objectCol[2].toString());*/
					}
					if (objectCol[3] == null) {
						objectCol[3] = "NA";
					} else {
						ob1.setCity(objectCol[3].toString());
						/*System.out.println("City::" + objectCol[3].toString());*/
					}
					if (objectCol[4] == null) {
						objectCol[4] = "NA";
					} else {
						ob1.setCountry(objectCol[4].toString());
						/*System.out.println("Country::"
								+ objectCol[4].toString());*/
					}
					if (objectCol[5] == null) {
						objectCol[5] = "NA";
					} else {
						ob1.setSignupstatus(objectCol[5].toString());
						/*System.out.println("Signup Status::"
								+ objectCol[5].toString());*/
					}
					if (objectCol[6] == null) {
						objectCol[6] = "NA";
					} else {
						ob1.setAccounttype(objectCol[6].toString());
						/*System.out.println("Account Type::"
								+ objectCol[6].toString());*/
					}
					if (objectCol[0] == null) {
						objectCol[0] = "NA";
					} else {
						ob1.setAccountids(objectCol[0].toString());
						/*System.out.println("Account Id::"
								+ objectCol[0].toString());*/
					}
					if (objectCol[7] == null) {
						objectCol[7] = "NA";
					} else {
						ob1.setIslive(objectCol[7].toString());
						/*System.out.println("Is Live::"
								+ objectCol[7].toString());*/
					}
					regHries.add(ob1);
				}
			}
			setRegHries(regHries);
		} catch (Exception e) {
			log.error("" + e.getMessage());
		}
		return SUCCESS;

	}

	public String GetAssociateRegistationViewDetails() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			List GetallListforUser = new onlineWebImp().GetallOnlineWeb("Asso");
			if (GetallListforUser != null && GetallListforUser.size() > 0) {
				for (Iterator iterator = GetallListforUser.iterator(); iterator
						.hasNext();) {
					RegHieInformation ob1 = new RegHieInformation();
					Object[] objectCol = (Object[]) iterator.next();
					if (objectCol[0] == null) {
						objectCol[0] = "NA";
					} else {
						ob1.setAccountid(objectCol[4].toString() + "-"
								+ objectCol[0].toString());
						/*System.out.println("Account Id::"
								+ objectCol[0].toString());*/
					}
					if (objectCol[1] == null) {
						objectCol[1] = "NA";
					} else {
						ob1.setOrgname(objectCol[1].toString());
						/*System.out.println("Orgnization Name:"
								+ objectCol[1].toString());*/
					}
					if (objectCol[2] == null) {
						objectCol[2] = "NA";
					} else {
						ob1.setOrgusername(objectCol[2].toString());
						/*System.out.println("Name Of Regis::"
								+ objectCol[2].toString());*/
					}
					if (objectCol[3] == null) {
						objectCol[3] = "NA";
					} else {
						ob1.setCity(objectCol[3].toString());
						/*System.out.println("City::" + objectCol[3].toString());*/
					}
					if (objectCol[4] == null) {
						objectCol[4] = "NA";
					} else {
						ob1.setCountry(objectCol[4].toString());
						/*System.out.println("Country::"
								+ objectCol[4].toString());*/
					}
					if (objectCol[5] == null) {
						objectCol[5] = "NA";
					} else {
						ob1.setSignupstatus(objectCol[5].toString());
						/*System.out.println("Signup Status::"
								+ objectCol[5].toString());*/
					}
					if (objectCol[6] == null) {
						objectCol[6] = "NA";
					} else {
						ob1.setAccounttype(objectCol[6].toString());
						/*System.out.println("Account Type::"
								+ objectCol[6].toString());*/
					}
					if (objectCol[0] == null) {
						objectCol[0] = "NA";
					} else {
						ob1.setAccountids(objectCol[0].toString());
						/*System.out.println("Account Id::"
								+ objectCol[0].toString());*/
					}
					if (objectCol[7] == null) {
						objectCol[7] = "NA";
					} else {
						ob1.setIslive(objectCol[7].toString());
						/*System.out.println("Is Live::"
								+ objectCol[7].toString());*/
					}
					regHries.add(ob1);
				}
			}
			setRegHries(regHries);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return SUCCESS;
	}

	public String GetOrganizationalRegistationViewDetails() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			List GetallListforUser = new onlineWebImp()
					.GetallOnlineWeb("orgdetails");
			if (GetallListforUser != null && GetallListforUser.size() > 0) {
				for (Iterator iterator = GetallListforUser.iterator(); iterator
						.hasNext();) {
					RegHieInformation ob1 = new RegHieInformation();
					Object[] objectCol = (Object[]) iterator.next();
					if (objectCol[0] == null) {
						objectCol[0] = "NA";
					} else {
						ob1.setAccountid(objectCol[4].toString() + "-"
								+ objectCol[0].toString());
						/*System.out.println("Account Id::"
								+ objectCol[0].toString());*/
					}
					if (objectCol[1] == null) {
						objectCol[1] = "NA";
					} else {
						ob1.setOrgname(objectCol[1].toString());
						/*System.out.println("Orgnization Name:"
								+ objectCol[1].toString());*/
					}
					if (objectCol[2] == null) {
						objectCol[2] = "NA";
					} else {
						ob1.setOrgusername(objectCol[2].toString());
						/*System.out.println("Name Of Regis::"
								+ objectCol[2].toString());*/
					}
					if (objectCol[3] == null) {
						objectCol[3] = "NA";
					} else {
						ob1.setCity(objectCol[3].toString());
						/*System.out.println("City::" + objectCol[3].toString());*/
					}
					if (objectCol[4] == null) {
						objectCol[4] = "NA";
					} else {
						ob1.setCountry(objectCol[4].toString());
						/*System.out.println("Country::"
								+ objectCol[4].toString());*/
					}
					if (objectCol[5] == null) {
						objectCol[5] = "NA";
					} else {
						ob1.setSignupstatus(objectCol[5].toString());
						/*System.out.println("Signup Status::"
								+ objectCol[5].toString());*/
					}
					if (objectCol[6] == null) {
						objectCol[6] = "NA";
					} else {
						ob1.setAccounttype(objectCol[6].toString());
						/*System.out.println("Account Type::"
								+ objectCol[6].toString());*/
					}
					if (objectCol[0] == null) {
						objectCol[0] = "NA";
					} else {
						ob1.setAccountids(objectCol[0].toString());
						/*System.out.println("Account Id::"
								+ objectCol[0].toString());*/
					}
					if (objectCol[7] == null) {
						objectCol[7] = "NA";
					} else {
						ob1.setIslive(objectCol[7].toString());
						/*System.out.println("Is Live::"
								+ objectCol[7].toString());*/
					}
					regHries.add(ob1);
				}
			}
			setRegHries(regHries);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return SUCCESS;

	}

	public List<RegHieInformation> getRegHries() {
		return regHries;
	}

	public void setRegHries(List<RegHieInformation> regHries) {
		this.regHries = regHries;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
