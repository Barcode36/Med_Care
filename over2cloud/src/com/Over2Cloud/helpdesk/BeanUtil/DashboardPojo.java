package com.Over2Cloud.helpdesk.BeanUtil;
import java.util.List;
public class DashboardPojo {

	private String id;
	private String pc="0",pct="0";
	private String sc="0",sct="0";
	private String hpc="0",hpct="0";
	private String igc="0",ignt="0";
	private String rc="0",rct="0";
	private String rac="0",ract="0";
	private String reopc="0",reopct="0",ht="0",grand="0";
	private String deptName;
	private String subDeptName;
	private String level;
	private String Location;
	private String locId;
	private String total="0";
	private String time;
	private String empName;
	private String empId;
	private String  wingId,wingsname;
	private List<DashboardPojo> dashList=null;
	private List<DashboardPojo> specList=null;
	private List dashListSpec=null;
	private String pcPer="0";
	private String rcPer="0";
	private String hpcPer="0";
	private String totalPer="0";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPc() {
		return pc;
	}
	public void setPc(String pc) {
		this.pc = pc;
	}
	public String getSc() {
		return sc;
	}
	public void setSc(String sc) {
		this.sc = sc;
	}
	public String getHpc() {
		return hpc;
	}
	public void setHpc(String hpc) {
		this.hpc = hpc;
	}
	public String getIgc() {
		return igc;
	}
	public void setIgc(String igc) {
		this.igc = igc;
	}
	public String getRc() {
		return rc;
	}
	public void setRc(String rc) {
		this.rc = rc;
	}
	public List<DashboardPojo> getDashList() {
		return dashList;
	}
	public void setDashList(List<DashboardPojo> dashList) {
		this.dashList = dashList;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getSubDeptName() {
		return subDeptName;
	}
	public void setSubDeptName(String subDeptName) {
		this.subDeptName = subDeptName;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getRac()
	{
		return rac;
	}
	public void setRac(String rac)
	{
		this.rac = rac;
	}
	public String getReopc()
	{
		return reopc;
	}
	public void setReopc(String reopc)
	{
		this.reopc = reopc;
	}
	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String location)
	{
		Location = location;
	}
	public String getLocId()
	{
		return locId;
	}
	public void setLocId(String locId)
	{
		this.locId = locId;
	}
	public String getTotal()
	{
		return total;
	}
	public void setTotal(String total)
	{
		this.total = total;
	}
	public String getTime()
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getWingsname() {
		return wingsname;
	}
	public void setWingsname(String wingsname) {
		this.wingsname = wingsname;
	}
	public String getWingId() {
		return wingId;
	}
	public void setWingId(String wingId) {
		this.wingId = wingId;
	}
	public String getPcPer() {
		return pcPer;
	}
	public void setPcPer(String pcPer) {
		this.pcPer = pcPer;
	}
	public String getRcPer() {
		return rcPer;
	}
	public void setRcPer(String rcPer) {
		this.rcPer = rcPer;
	}
	public String getHpcPer() {
		return hpcPer;
	}
	public void setHpcPer(String hpcPer) {
		this.hpcPer = hpcPer;
	}
	public String getTotalPer() {
		return totalPer;
	}
	public void setTotalPer(String totalPer) {
		this.totalPer = totalPer;
	}
	public List getDashListSpec() {
		return dashListSpec;
	}
	public void setDashListSpec(List dashListSpec) {
		this.dashListSpec = dashListSpec;
	}
	public List<DashboardPojo> getSpecList() {
		return specList;
	}
	public void setSpecList(List<DashboardPojo> specList) {
		this.specList = specList;
	}
	
	public String getPct() {
		return pct;
	}
	public void setPct(String pct) {
		this.pct = pct;
	}
	public String getSct() {
		return sct;
	}
	public void setSct(String sct) {
		this.sct = sct;
	}
	public String getHpct() {
		return hpct;
	}
	public void setHpct(String hpct) {
		this.hpct = hpct;
	}
	public String getIgnt() {
		return ignt;
	}
	public void setIgnt(String ignt) {
		this.ignt = ignt;
	}
	public String getRct() {
		return rct;
	}
	public void setRct(String rct) {
		this.rct = rct;
	}
	public String getRact() {
		return ract;
	}
	public void setRact(String ract) {
		this.ract = ract;
	}
	public String getReopct() {
		return reopct;
	}
	public void setReopct(String reopct) {
		this.reopct = reopct;
	}
	public void setHt(String ht) {
		this.ht = ht;
	}
	public String getHt() {
		return ht;
	}
	public void setGrand(String grand) {
		this.grand = grand;
	}
	public String getGrand() {
		return grand;
	}
	
	
}