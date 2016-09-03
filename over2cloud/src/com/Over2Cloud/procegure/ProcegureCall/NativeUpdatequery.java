package com.Over2Cloud.procegure.ProcegureCall;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.Over2Cloud.annotation.NamedQueries;
import com.Over2Cloud.annotation.NamedQuery;



@NamedQueries(
		{
			
			// select query 
			@NamedQuery( name= "leadbasicdetails", query="select * from leadbasicdetails"),
			@NamedQuery( name= "leadaddressingdetails", query="select * from leadaddressingdetails"),
			@NamedQuery( name= "leadcustomdetails", query="select * from leadcustomdetails"),
			@NamedQuery( name= "selectAlltab", query="select * from allcommontabtable"),
			@NamedQuery( name= "contactconf", query="select * from contactconf"),
			@NamedQuery( name= "oppcontaactconfi", query="select * from oppcontaactconfi"),
			@NamedQuery( name= "opportunityconf", query="select * from opportunityconf"),
			@NamedQuery( name= "organizconf", query="select * from organizconf"),
			@NamedQuery( name= "productconf", query="select * from productconf"),
			@NamedQuery( name= "departconf", query="select * from departconf"),
			// query where clause use only W inn select Query for make diffirent!!
			@NamedQuery( name= "selecttabelforConfig", query="select * from allcommontabtable where id= :id"),
			@NamedQuery( name= "leadbasicdetailsw", query="select * from leadbasicdetails where id= :id")
		}
		
)


public class NativeUpdatequery{

}
