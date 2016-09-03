package com.Over2Cloud.modal.db.Setting;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.Over2Cloud.modal.dao.imp.Setting.SingleSpaceImp;
@Entity
@Table(name="packconfiguration")
@Proxy(lazy = false)
public class PackConfiguration 
{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO) private int id;
	@Column(name="countryIsoCode")  private String countryIsoCode;
	@Column(name="applicationcode") private String applicationcode;
	@Column(name="timeperiod")      private String timeperiod;
	@Column(name="timein")          private int timein;
	@Column(name="usercounter")     private int usercounter;
	@Column(name="currency")        private String currency;
	@Column(name="cost")            private double cost;
	@Temporal(TemporalType.DATE)
	@Column(name="offerFrom")       private java.util.Date offerFrom;
	@Temporal(TemporalType.DATE)
	@Column(name="offerTo")         private java.util.Date  offerTo;
	@Column(name="insertTimeDate")  private String insertTimeDate;
	@Transient
	private String period;

	public String getPeriod() {
		if(timeperiod.equals("D"))
		{
			period="Day";
			
		}
		else if(timeperiod.equals("M"))
		{
			period="Month";
		}
		else if(timeperiod.equals("Y"))
		{
			period="Year";
		}
		else 
		{
			period="Nothing";
			
		}
		return getTimein()+" "+period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountryIsoCode() {
		if(countryIsoCode==null){countryIsoCode="NA";}else{countryIsoCode=new SingleSpaceImp().ContryName(countryIsoCode);}
		return countryIsoCode;
	}
	public void setCountryIsoCode(String countryIsoCode) {
		this.countryIsoCode = countryIsoCode;
	}
	public String getApplicationcode() {
		if(applicationcode==null){applicationcode="NA";}else{applicationcode=new SingleSpaceImp().ApplicationName(applicationcode);}
		return applicationcode;
	}
	public void setApplicationcode(String applicationcode) {
		this.applicationcode = applicationcode;
	}

	public String getTimeperiod() {
		if(timeperiod.equals("D"))
		{
			timeperiod="In Day";
			
		}
		else if(timeperiod.equals("M"))
		{
			timeperiod="In Month";
		}
		else if(timeperiod.equals("Y"))
		{
			timeperiod="In Year";
		}
		else 
		{
			timeperiod="NA";
			
		}
		return timeperiod;
	}

	public void setTimeperiod(String timeperiod) {
		this.timeperiod = timeperiod;
	}

	public int getTimein() {
		return timein;
	}

	public void setTimein(int timein) {
		this.timein = timein;
	}

	public int getUsercounter() {
		return usercounter;
	}

	public void setUsercounter(int usercounter) {
		this.usercounter = usercounter;
	}

	public String getCurrency() {
		if(currency.equals("RS"))
		{
			currency="Indian Rupee";
		}
		else if(currency.equals("EU"))
		{
			currency="Euro";
		}
		else if(currency.equals("$"))
		{
			currency="Dollar";
		}
		else 
		{
			currency="NA";
		}
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	public java.util.Date getOfferFrom() {
		return offerFrom;
	}
	public void setOfferFrom(java.util.Date offerFrom) {
		this.offerFrom = offerFrom;
	}

	public java.util.Date getOfferTo() {
		return offerTo;
	}

	public void setOfferTo(java.util.Date offerTo) {
		this.offerTo = offerTo;
	}

	public String getInsertTimeDate() {
		return insertTimeDate;
	}

	public void setInsertTimeDate(String insertTimeDate) {
		this.insertTimeDate = insertTimeDate;
	}
}
