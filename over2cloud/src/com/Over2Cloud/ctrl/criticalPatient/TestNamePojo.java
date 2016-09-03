package com.Over2Cloud.ctrl.criticalPatient;

public class TestNamePojo 
{
	private int id;
	private String test_type;
	private String test_name;
	private String brief;
	private String test_unit;
	//aarif start
	private String min;
	private String max;
	//end by aarif
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTest_type() {
		return test_type;
	}
	public void setTest_type(String testType) {
		test_type = testType;
	}
	public String getTest_name() {
		return test_name;
	}
	public void setTest_name(String testName) {
		test_name = testName;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getTest_unit() {
		return test_unit;
	}
	public void setTest_unit(String test_unit) {
		this.test_unit = test_unit;
	}
	public void getTest_unit(String readExcelSheet) {
		// TODO Auto-generated method stub
		
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	
	
	
	
	
}