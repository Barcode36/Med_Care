package com.Over2Cloud.ctrl.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import com.Over2Cloud.BeanUtil.BlockUserBean;
import com.Over2Cloud.BeanUtil.ChunkAccountId;
import com.Over2Cloud.CommonClasses.ObjectFactory;
import com.Over2Cloud.CommonClasses.TableInfo;
import com.Over2Cloud.CommonInterface.Addmethod;
import com.Over2Cloud.CommonInterface.commanOperation;
import com.Over2Cloud.modal.db.Setting.UserChunkMapping;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BlockCtrlServer extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final Log log = LogFactory.getLog(BlockCtrlServer.class);
	@SuppressWarnings("unchecked")
	Map session = ActionContext.getContext().getSession();
	String uName = (String) session.get("uName");
	String accountid = (String) session.get("accountid");
	String countryiso = (String) session.get("countryid");
	SessionFactory connectionSpace = (SessionFactory) session
			.get("connectionSpace");
	private List<UserChunkMapping> blockChunkGridmodel = new ArrayList<UserChunkMapping>();
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
	private int id;
	private String idList;
	private String spaceAddress;

	public String blockChunkSpaceConfig() {
		if (uName == null) {
			return LOGIN;
		}
		if (uName.equalsIgnoreCase("")) {
			return LOGIN;
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String BlockChunkSapceConfiguration() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			Addmethod AddDetails = new ObjectFactory(new UserChunkMapping());
			commanOperation ASD = AddDetails
					.AddDetailsOfAll("UserChunkMapping");
			setRecords(ASD.getRecordStatus());
			int to = (getRows() * getPage());
			int from = to - getRows();
			if (to > getRecords())
				to = getRecords();
			if (isLoadonce()) {
				blockChunkGridmodel = (List<UserChunkMapping>) ASD
						.ServicesviewData(to, from, getSearchField(),
								getSearchString(), getSearchOper(), getSord(),
								getSidx());
				setBlockChunkGridmodel(blockChunkGridmodel);
			} else {
				blockChunkGridmodel = (List<UserChunkMapping>) ASD
						.ServicesviewData(to, from, getSearchField(),
								getSearchString(), getSearchOper(), getSord(),
								getSidx());
				setBlockChunkGridmodel(blockChunkGridmodel);
			}
			setTotal((int) Math
					.ceil((double) getRecords() / (double) getRows()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String allBlockChunkClient() {
		try {
			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			if (getIdList().equals(null) || getIdList() == null
					|| getIdList().equals("") || getIdList() == "") {
				return SUCCESS;
			} else {
				List<String> Spacelist = Arrays.asList(getIdList().split(","));
				for (String h : Spacelist) {
					List<String> PerSpaceList = Arrays.asList(h.split(":"));
					UserChunkMapping ob1 = new UserChunkMapping();
					ob1.setId(Integer.parseInt(PerSpaceList.get(0).toString()));
					ob1.setUserFromchunk(PerSpaceList.get(1).toString());
					ob1.setUserTochunk(PerSpaceList.get(2).toString());
					ob1.setCountry(PerSpaceList.get(3).toString());
					ob1.setCombinekey(PerSpaceList.get(0).toString().trim()
							+ ":" + PerSpaceList.get(1).toString().trim() + ":"
							+ PerSpaceList.get(2).toString().trim() + ":"
							+ PerSpaceList.get(4).toString().trim());
					blockChunkGridmodel.add(ob1);
				}
				setBlockChunkGridmodel(blockChunkGridmodel);
				return SUCCESS;
			}
		} catch (Exception e) {
		}
		return ERROR;
	}

	public String blockChunkAllData() {
		try {

			if (uName == null) {
				return LOGIN;
			}
			if (uName.equalsIgnoreCase("")) {
				return LOGIN;
			}
			List<String> blockSpaceallotlist = Arrays.asList(getSpaceAddress()
					.split(","));
			for (String h : blockSpaceallotlist) {

				List<String> PerSpaceList = Arrays.asList(h.trim().split(":"));
				Map<String, Object> wherClause = new HashMap<String, Object>();
				Map<String, Object> condtnBlock = new HashMap<String, Object>();
				if (PerSpaceList.get(3).toString().equals("BlockChunk")) {
					wherClause.put("iscity_iscountry_Blocking", "B");
				}
				if (PerSpaceList.get(3).toString().equals("LiveChunk")) {
					wherClause.put("iscity_iscountry_Blocking", "L");
				}
				condtnBlock.put("id", PerSpaceList.get(0));
				boolean data = new TableInfo().updateTableColumn(
						"user_chunk_mapping", wherClause, condtnBlock,
						connectionSpace);
				if (data) {
					Thread.sleep(200);
					addActionMessage("Server updated!!!");
				} else {
					log
							.info("Over2Cloud::>> Class:BlockCtrlServer >> Method::>> blockChunkAllData ::::: Block Chunk Space Is not Allot The Specific Server"
									+ PerSpaceList.get(3));
					addActionError("Ooops there is some problem!");
					return ERROR;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<UserChunkMapping> getBlockChunkGridmodel() {
		return blockChunkGridmodel;
	}

	public void setBlockChunkGridmodel(
			List<UserChunkMapping> blockChunkGridmodel) {
		this.blockChunkGridmodel = blockChunkGridmodel;
	}

	public String getIdList() {
		return idList;
	}

	public void setIdList(String idList) {
		this.idList = idList;
	}

	public String getSpaceAddress() {
		return spaceAddress;
	}

	public void setSpaceAddress(String spaceAddress) {
		this.spaceAddress = spaceAddress;
	}

}
