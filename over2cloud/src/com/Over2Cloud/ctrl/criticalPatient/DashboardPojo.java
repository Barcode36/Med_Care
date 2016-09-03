package com.Over2Cloud.ctrl.criticalPatient;
import java.util.List;
public class DashboardPojo {

	private String id;
	private String name;
	private String counter="0";
	private String open="0";
	private String close="0";
	private String offTime="0";
	private String onTime="0";
	private String total="0"; 
 	private String informed="0";
	private String firstTAT="0";
	private String secondTAT="0";
	private String grand_open="0";
	private String grand_close="0";
	private String grand_inform="0"; 
 	 
	private List<DashboardPojo> dashList=null;
	 
	 
	 
	public String getInformed() {
		return informed;
	}
	public void setInformed(String informed) {
		this.informed = informed;
	}
	public String getFirstTAT() {
		return firstTAT;
	}
	public void setFirstTAT(String firstTAT) {
		this.firstTAT = firstTAT;
	}
	public String getSecondTAT() {
		return secondTAT;
	}
	public void setSecondTAT(String secondTAT) {
		this.secondTAT = secondTAT;
	}
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
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getOffTime() {
		return offTime;
	}
	public void setOffTime(String offTime) {
		this.offTime = offTime;
	}
	public String getOnTime() {
		return onTime;
	}
	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}
	public String getGrand_open() {
		return grand_open;
	}
	public void setGrand_open(String grandOpen) {
		grand_open = grandOpen;
	}
	public String getGrand_close() {
		return grand_close;
	}
	public void setGrand_close(String grandClose) {
		grand_close = grandClose;
	}
	public String getGrand_inform() {
		return grand_inform;
	}
	public void setGrand_inform(String grandInform) {
		grand_inform = grandInform;
	}
	 
	
}