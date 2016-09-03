package com.Over2Cloud.ctrl.order.pharmacy;
import java.util.List;
public class MedicinePojo {

	private String id;
	private String name;
	private String orderAt;
	private String dispatchAt;
	private String status;
	private String quantity;
	private String dispatchQuantity;
	private String remainingQuantity;
	private String receivedQuantity;
	private List<MedicinePojo> dashList=null;
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	 
	public List<MedicinePojo> getDashList() {
		return dashList;
	}
	public void setDashList(List<MedicinePojo> dashList) {
		this.dashList = dashList;
	}
   
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getOrderAt() {
		return orderAt;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public void setOrderAt(String orderAt) {
		this.orderAt = orderAt;
	}
	public String getDispatchAt() {
		return dispatchAt;
	}
	public void setDispatchAt(String dispatchAt) {
		this.dispatchAt = dispatchAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDispatchQuantity() {
		return dispatchQuantity;
	}
	public void setDispatchQuantity(String dispatchQuantity) {
		this.dispatchQuantity = dispatchQuantity;
	}
	public String getRemainingQuantity() {
		return remainingQuantity;
	}
	public void setRemainingQuantity(String remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}
	public String getReceivedQuantity() {
		return receivedQuantity;
	}
	public void setReceivedQuantity(String receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	 
	 
	 
}