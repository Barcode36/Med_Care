package com.Over2Cloud.ctrl.text2mail;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;

public class T2MActionControlDao {
	
	
	public void createKeywordTable(String accountID,
			SessionFactory connectionSpace) throws Exception
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<GridDataPropertyView> statusColName = Configuration
				.getConfigurationData("mapped_configKeyword_configuration", accountID,
						connectionSpace, "", 0, "table_name", "configKeyword_configuration");
		if (statusColName != null)
		{
			// create table query based on configuration
			
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			for (GridDataPropertyView gdp : statusColName)
			{
				TableColumes ob1 = new TableColumes();
				ob1 = new TableColumes();
				ob1.setColumnname(gdp.getColomnName());
				if(gdp.getColomnName().equalsIgnoreCase("mailIds"))
				{ob1.setDatatype("text");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);}	
				else{
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);}
			}


			// create table 'configurekeyword' to Keyword data
			cbt.createTable22("configurekeyword", Tablecolumesaaa, connectionSpace);
		}
	}
	
	
	
	public void createConsultantsTable(String accountID,
			SessionFactory connectionSpace) throws Exception
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<GridDataPropertyView> statusColName = Configuration
				.getConfigurationData("mapped_consultants_configuration", accountID,
						connectionSpace, "", 0, "table_name", "consultants_configuration");
		if (statusColName != null)
		{
			// create table query based on configuration
			
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			for (GridDataPropertyView gdp : statusColName)
			{
				TableColumes ob1 = new TableColumes();
				ob1 = new TableColumes();
				ob1.setColumnname(gdp.getColomnName());
				if(gdp.getColomnName().equalsIgnoreCase("emailId"))
				{ob1.setDatatype("text");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);}	
				else{
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);}
			}


			// create table 'configurekeyword' to Keyword data
			cbt.createTable22("consultants", Tablecolumesaaa, connectionSpace);
		}
	}
	
	
	
	
	public void createTemplateTable(String accountID,
			SessionFactory connectionSpace) throws Exception
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<GridDataPropertyView> statusColName = Configuration
				.getConfigurationData("mapped_template_configuration", accountID,
						connectionSpace, "", 0, "table_name", "template_configuration");
		if (statusColName != null)
		{
			// create table query based on configuration
			
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			for (GridDataPropertyView gdp : statusColName)
			{
				TableColumes ob1 = new TableColumes();
				ob1 = new TableColumes();
				ob1.setColumnname(gdp.getColomnName());
				if(gdp.getColomnName().equalsIgnoreCase("template"))
				{ob1.setDatatype("text");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);}	
				else{
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);}
			}
			boolean table=cbt.createTable22("template", Tablecolumesaaa, connectionSpace);
			if(table)
			{System.out.println("Table Created Successfully");}	
		}
	}
	
	
	
	
	

}
