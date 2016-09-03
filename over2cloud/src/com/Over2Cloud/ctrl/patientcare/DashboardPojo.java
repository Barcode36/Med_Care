package com.Over2Cloud.ctrl.patientcare;
import java.util.List;
public class DashboardPojo {

	private String id;
	private String name;
	private String counter;
	private String appointment="0";
	private String scheduled="0";
	private String serviceIn="0";
	private String serviceOut="0";
	private String cancel="0";
	private String onTime="0";
	private String offTime="0";
	private String total="0"; 
	private List<DashboardPojo> dashList=null;
	public String getAppointment()
	{
		return appointment;
	}
	public void setAppointment(String appointment)
	{
		this.appointment = appointment;
	}
	public String getScheduled()
	{
		return scheduled;
	}
	public void setScheduled(String scheduled)
	{
		this.scheduled = scheduled;
	}
	public String getServiceIn()
	{
		return serviceIn;
	}
	public void setServiceIn(String serviceIn)
	{
		this.serviceIn = serviceIn;
	}
	public String getServiceOut()
	{
		return serviceOut;
	}
	public void setServiceOut(String serviceOut)
	{
		this.serviceOut = serviceOut;
	}
	public String getCancel()
	{
		return cancel;
	}
	public void setCancel(String cancel)
	{
		this.cancel = cancel;
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
	public String getOnTime() {
		return onTime;
	}
	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}
	public String getOffTime() {
		return offTime;
	}
	public void setOffTime(String offTime) {
		this.offTime = offTime;
	}
	
}