package com.Over2Cloud.ctrl.order.pharmacy;
import java.util.List;
public class DashboardPojo {

	private String id;
	private String name;
	private String status;
	private String tableFor;
	private String counter="0";
  	private String close="0";
  	private String forceClose="0";
  	private String autoClose="0";
	private String closeP="0";
	private String dispatch="0";
	private String dispatchP="0";
	private String ordered="0";
	private String total="0"; 
 	private String dispatchError="0";
	private String level1="0";
	private String level2="0";
	private String level3="0";
	private String level4="0";
	private String level5="0"; 
	private String withInTat;
	private String outOfTat;
 	 
	private List<DashboardPojo> dashList=null;
	 
	 
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	 
	public List<DashboardPojo> getDashList() {
		return dashList;
	}
	public void setDashList(List<DashboardPojo> dashList) {
		this.dashList = dashList;
	}
 	 
	public String getTotal()
	{
		return total;
	}
	public void setTotal(String total)
	{
		this.total = total;
	}
 	 
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getCounter()
	{
		return counter;
	}
	public void setCounter(String counter)
	{
		this.counter = counter;
	}
	 
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getDispatch() {
		return dispatch;
	}
	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}
	public String getOrdered() {
		return ordered;
	}
	public void setOrdered(String ordered) {
		this.ordered = ordered;
	}
	public String getDispatchError() {
		return dispatchError;
	}
	public void setDispatchError(String dispatchError) {
		this.dispatchError = dispatchError;
	}
	public String getLevel1() {
		return level1;
	}
	public void setLevel1(String level1) {
		this.level1 = level1;
	}
	public String getLevel2() {
		return level2;
	}
	public void setLevel2(String level2) {
		this.level2 = level2;
	}
	public String getLevel3() {
		return level3;
	}
	public void setLevel3(String level3) {
		this.level3 = level3;
	}
	public String getLevel4() {
		return level4;
	}
	public void setLevel4(String level4) {
		this.level4 = level4;
	}
	public String getLevel5() {
		return level5;
	}
	public void setLevel5(String level5) {
		this.level5 = level5;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTableFor() {
		return tableFor;
	}
	public void setTableFor(String tableFor) {
		this.tableFor = tableFor;
	}
	public String getWithInTat()
	{
		return withInTat;
	}
	public void setWithInTat(String withInTat)
	{
		this.withInTat = withInTat;
	}
	public String getOutOfTat()
	{
		return outOfTat;
	}
	public void setOutOfTat(String outOfTat)
	{
		this.outOfTat = outOfTat;
	}
	public String getCloseP()
	{
		return closeP;
	}
	public void setCloseP(String closeP)
	{
		this.closeP = closeP;
	}
	public String getDispatchP()
	{
		return dispatchP;
	}
	public void setDispatchP(String dispatchP)
	{
		this.dispatchP = dispatchP;
	}
	public String getForceClose()
	{
		return forceClose;
	}
	public void setForceClose(String forceClose)
	{
		this.forceClose = forceClose;
	}
	public String getAutoClose()
	{
		return autoClose;
	}
	public void setAutoClose(String autoClose)
	{
		this.autoClose = autoClose;
	}
	 
}