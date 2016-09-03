package com.Over2Cloud.BeanUtil;

public class ConfigurationUtilBean {
	
	private int id=0;
	private String field_name=null;
	private String field_value=null;
	private String key=null;
	private String value=null;
	private String default_value=null;
	private String actives=null;
	private boolean inactives=false;
	private String queryNames=null;
	private boolean mandatory=false;
	private String validationType=null;
	private String colType=null;
	private boolean editable;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getField_value() {
		return field_value;
	}
	public void setField_value(String field_value) {
		this.field_value = field_value;
	}
	public String getQueryNames() {
		return queryNames;
	}
	public void setQueryNames(String queryNames) {
		this.queryNames = queryNames;
	}

	public String getDefault_value() {
		return default_value;
	}
	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public String getActives() {
		return actives;
	}
	public void setActives(String actives) {
		this.actives = actives;
	}
	public boolean isInactives() {
		return inactives;
	}
	public void setInactives(boolean inactives) {
		this.inactives = inactives;
	}
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	public String getValidationType() {
		return validationType;
	}
	public void setValidationType(String validationType) {
		this.validationType = validationType;
	}
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		this.colType = colType;
	}

	
	
	
	

}
