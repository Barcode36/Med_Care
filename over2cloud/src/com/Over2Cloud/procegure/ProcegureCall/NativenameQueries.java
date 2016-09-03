package com.Over2Cloud.procegure.ProcegureCall;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.Over2Cloud.annotation.NamedQueries;
import com.Over2Cloud.annotation.NamedQuery;
import com.opensymphony.xwork2.ActionSupport;

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
			@NamedQuery( name= "organizconf_level1", query="select * from organizconf_level1"),
			@NamedQuery( name= "productconf", query="select * from productconf"),
			
			//dept configuration
			@NamedQuery( name= "dept_config_param", query="select * from dept_config_param"),

			  //organizconf
			@NamedQuery( name= "organizconf_level1", query="select * from organizconf_level1"),
			@NamedQuery( name= "organizconf_level2", query="select * from organizconf_level2"),
			@NamedQuery( name= "organizconf_level3", query="select * from organizconf_level3"),
			@NamedQuery( name= "organizconf_level4", query="select * from organizconf_level4"),
			@NamedQuery( name= "organizconf_level5", query="select * from organizconf_level5"),
			
			  //subdept
			@NamedQuery( name= "subdeprtmentconf", query="select * from subdeprtmentconf"),
			//designation work
			@NamedQuery( name= "designation_configuration", query="select * from designation_configuration"),
			//emp_baisc work
			@NamedQuery( name= "employee_basic_configuration", query="select * from employee_basic_configuration"),
			@NamedQuery( name= "employee_personal_configuration", query="select * from employee_personal_configuration"),
			@NamedQuery( name= "employee_work_exprience_configuration", query="select * from employee_work_exprience_configuration"),
			@NamedQuery( name= "employee_bank_details_configuration", query="select * from employee_bank_details_configuration"),
			//user account
			@NamedQuery( name= "user_details_configuration", query="select * from user_details_configuration"),
			// query where clause use only W inn select Query for make diffirent!!
			@NamedQuery( name= "allcommontabtable", query="select * from allcommontabtable where id= :id"),
			@NamedQuery( name= "selectcommnmappedtable", query="select * from allcommontabtable where level_congtable= :level_congtable"),
			@NamedQuery( name= "leadbasicdetailsw", query="select * from leadbasicdetails where id= :id"),
			@NamedQuery( name= "leadaddressingdetailsw", query="select * from leadaddressingdetails where id= :id"),
			@NamedQuery( name= "leadaddressingdetailsw", query="select * from leadaddressingdetails where id= :id"),
			@NamedQuery( name= "leadbasicdetailsu", query="update leadaddressingdetails set field_name=:field_name,mendatiry=:mendatiry,actives=:actives,inactive=:inactive  where id= :id"),
			@NamedQuery( name= "leadbasicdetailse", query="insert into leadaddressingdetails (mappedid,table_name,clientid) values(:mappedid,:table_name,:clientid "),
			@NamedQuery( name= "pic", query="from Avatar")
	
		}
		
)


public class NativenameQueries{
	
}
