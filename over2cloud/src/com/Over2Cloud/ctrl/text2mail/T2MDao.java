package com.Over2Cloud.ctrl.text2mail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.Configuration;
import com.Over2Cloud.CommonClasses.GridDataPropertyView;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.InsertDataTable;
import com.Over2Cloud.Rnd.TableColumes;

public class T2MDao
{



	public void createSpecilityMasterTable(String accountID,
			SessionFactory connectionSpace) throws Exception
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		List<GridDataPropertyView> statusColName = Configuration
				.getConfigurationData("mapped_speciality_configuration", accountID,
						connectionSpace, "", 0, "table_name", "speciality_configuration");
		if (statusColName != null)
		{
			// create table query based on configuration
			
			List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
			for (GridDataPropertyView gdp : statusColName)
			{
				TableColumes ob1 = new TableColumes();
				ob1 = new TableColumes();
				ob1.setColumnname(gdp.getColomnName());
				ob1.setDatatype("varchar(255)");
				ob1.setConstraint("default NULL");
				Tablecolumesaaa.add(ob1);
			}
			
	boolean table = cbt.createTable22("speciality", Tablecolumesaaa, connectionSpace);
	if(table)
	{
		System.out.println("Table Created Successfully");
	}
		}
	}

}
