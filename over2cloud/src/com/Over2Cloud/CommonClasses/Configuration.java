package com.Over2Cloud.CommonClasses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class Configuration {

	public static Map<String, String> getConfigurationIds(String mappedMainTableName,List data,String accountID,Map<String, Object> temp,SessionFactory connectionSpace,String paramType)
	{
		String mappedIds=null;
		String mappedTable=null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		Map<String, String>returnResult=new LinkedHashMap<String, String>();
		List getMappedTableForConfg=cbt.viewAllDataEitherSelectOrAll(mappedMainTableName,data,temp,connectionSpace);
		if(getMappedTableForConfg!=null)
		{
			for (Object c1 : getMappedTableForConfg) {
				Object[] dataC1 = (Object[]) c1;
				mappedIds=dataC1[0].toString().trim();
				mappedTable=dataC1[1].toString().trim();
			}
			String tempMappedIds[]=mappedIds.split("#");
			StringBuffer mappedIdsList=new StringBuffer();
			int i=1;
			for(String H:tempMappedIds)
			{
				if(i<tempMappedIds.length)
					mappedIdsList.append("'"+H.trim()+"' ,");
				else
					mappedIdsList.append("'"+H.trim()+"'");
				i++;
			}
			StringBuilder query=new StringBuilder("select field_name,field_value from "+mappedTable.trim() +" where id in ("+mappedIdsList+") ");
			if(paramType!=null && !paramType.equalsIgnoreCase(""))
			{
				query.append("and colType='"+paramType+"'");
			}
			query.append(" and activeType='true'");
			List  mappedDataWithColmAndLevel=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			for (Object c1 : mappedDataWithColmAndLevel) {
				Object[] dataC1 = (Object[]) c1;
				returnResult.put(dataC1[1].toString().trim(), dataC1[0].toString().trim());
			}
		}
		return returnResult;
	}
	public static List getConfigurationIdss(String mappedMainTableName,List data,String accountID,Map<String, Object> temp,SessionFactory connectionSpace)
	{
		String mappedIds=null;
		String mappedTable=null;
		List  resultList=null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		//Map<String, String>returnResult=new HashMap<String, String>();
		try{
		List getMappedTableForConfg=cbt.viewAllDataEitherSelectOrAll(mappedMainTableName,data,temp,connectionSpace);
		for (Object c1 : getMappedTableForConfg) {
			Object[] dataC1 = (Object[]) c1;
			mappedIds=dataC1[0].toString().trim();
			mappedTable=dataC1[1].toString().trim();
		}
		String tempMappedIds[]=mappedIds.split("#");
		StringBuffer mappedIdsList=new StringBuffer();
		int i=1;
		for(String H:tempMappedIds)
		{
			if(i<tempMappedIds.length)
				mappedIdsList.append("'"+H.trim()+"' ,");
			else
				mappedIdsList.append("'"+H.trim()+"'");
			i++;
		}
		StringBuilder query=new StringBuilder("select field_name,field_value,id,colType,activeType,mandatroy,validationType from "+mappedTable.trim() +" where id in ("+mappedIdsList+") and activeType='true'");
		 resultList=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();
	
		}
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return resultList;
	}
	public static List getConfigurationListAppendid(String mappedTable,String mappedIdsList,SessionFactory connectionSpace){
		List  resultList=null;
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		try{
			StringBuilder query=new StringBuilder("select field_name,field_value,id,colType,activeType,mandatroy from "+mappedTable.trim() +" where id in ("+mappedIdsList+") order by field_name");
			 resultList=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return resultList;
	}
	public static List<GridDataPropertyView> getConfigurationData(String mappedMainTableName,String accountID,SessionFactory connectionSpace,String paramType,int limit,String propName,String valueName)
	{
		String mappedIds=null;
		String mappedTable=null;
		StringBuffer mappedIdsList=new StringBuffer();
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		List<GridDataPropertyView>returnResult=new ArrayList<GridDataPropertyView>();
		StringBuilder query1=new StringBuilder("select mappedid ,table_name from "+mappedMainTableName);
		try{
		if(!propName.equalsIgnoreCase("") && !valueName.equalsIgnoreCase("") && valueName!=null && propName!=null)
		{
			query1.append(" where "+propName+"='"+valueName+"'");
		}
		//query1.append(" limit "+limit+",1");
		List  getMappedTableForConfg=cbt.executeAllSelectQuery(query1.toString(),connectionSpace);
		if(getMappedTableForConfg!=null)
		{
			for (Object c1 : getMappedTableForConfg) {
				Object[] dataC1 = (Object[]) c1;
				mappedIds=dataC1[0].toString().trim();
				mappedTable=dataC1[1].toString().trim();
			}
			if(mappedIds!=null)
			{
				String tempMappedIds[]=mappedIds.split("#");
				int i=1;
				for(String H:tempMappedIds)
				{
					if(i<tempMappedIds.length)
						mappedIdsList.append("'"+H.trim()+"' ,");
					else
						mappedIdsList.append("'"+H.trim()+"'");
					i++;
				}
			}
			StringBuilder query=new StringBuilder("select field_name,field_value,hideOrShow,formatter,align,width,editable,search,colType,mandatroy,validationType from "+mappedTable.trim() +" where id in ("+mappedIdsList+") and activeType='true'");
			//query.append("order by field_name");
			//System.out.println("Querry is as "+query);
			List  mappedDataWithColmAndLevel=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
			if(mappedDataWithColmAndLevel!=null)
			{
				for (Object c1 : mappedDataWithColmAndLevel) {
					Object[] dataC1 = (Object[]) c1;
					GridDataPropertyView gpv=new GridDataPropertyView();
					
					if(dataC1[0]!=null)
						gpv.setHeadingName(dataC1[0].toString().trim());
					if(dataC1[1]!=null)
						gpv.setColomnName(dataC1[1].toString().trim());
					if(dataC1[2]!=null)
						gpv.setHideOrShow(dataC1[2].toString().trim());
					if(dataC1[3]!=null)
						gpv.setFormatter(dataC1[3].toString().trim());
					if(dataC1[4]!=null)
						gpv.setAlign(dataC1[4].toString().trim());
					if(dataC1[5]!=null)
						gpv.setWidth(Integer.parseInt(dataC1[5].toString().trim()));
					if(dataC1[6]!=null)
						gpv.setEditable(dataC1[6].toString().trim());
					if(dataC1[7]!=null)
						gpv.setSearch(dataC1[7].toString().trim());
					if(dataC1[8]!=null)
						gpv.setColType(dataC1[8].toString().trim());
					if(dataC1[9]!=null)
						gpv.setMandatroy(dataC1[9].toString().trim());
					if(dataC1[10]!=null)
						gpv.setValidationType(dataC1[10].toString().trim());
					returnResult.add(gpv);
					//returnResult.put(dataC1[1].toString().trim(), dataC1[0].toString().trim());
				}
			}}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}

	@SuppressWarnings("unchecked")
	public static List<GridDataPropertyView> getSelectConfigurationData(String mappedMainTableName,String accountID,SessionFactory connectionSpace){
		CommonOperInterface cbt=new CommonConFactory().createInterface();
		List<GridDataPropertyView>returnResult=new ArrayList<GridDataPropertyView>();
		List ColomnList=new ArrayList<String>();
		try{
		StringBuilder query=new StringBuilder("select field_name,field_value,hideOrShow,formatter,align,width,editable,search,colType,mandatroy from "+mappedMainTableName.trim() +" where activeType='true'");
		//query.append("order by field_name");
		List  mappedDataWithColmAndLevel=cbt.executeAllSelectQuery(query.toString(),connectionSpace);
		if(mappedDataWithColmAndLevel!=null){
			for (Object c1 : mappedDataWithColmAndLevel) {
				Object[] dataC1 = (Object[]) c1;
				GridDataPropertyView gpv=new GridDataPropertyView();
				
				if(dataC1[0]!=null)
					gpv.setHeadingName(dataC1[0].toString().trim());
				if(dataC1[1]!=null)
					gpv.setColomnName(dataC1[1].toString().trim());
				if(dataC1[2]!=null)
					gpv.setHideOrShow(dataC1[2].toString().trim());
				if(dataC1[3]!=null)
					gpv.setFormatter(dataC1[3].toString().trim());
				if(dataC1[4]!=null)
					gpv.setAlign(dataC1[4].toString().trim());
				if(dataC1[5]!=null)
					gpv.setWidth(Integer.parseInt(dataC1[5].toString().trim()));
				if(dataC1[6]!=null)
					gpv.setEditable(dataC1[6].toString().trim());
				if(dataC1[7]!=null)
					gpv.setSearch(dataC1[7].toString().trim());
				if(dataC1[8]!=null)
					gpv.setColType(dataC1[8].toString().trim());
				if(dataC1[9]!=null)
					gpv.setMandatroy(dataC1[9].toString().trim());
				returnResult.add(gpv);
				//returnResult.put(dataC1[1].toString().trim(), dataC1[0].toString().trim());
			}
		  }
		}
		
		catch (Exception e) {
			// TODO: handle exception
		}
	return returnResult;
		
	}
	public static List<String> getColomnList(String mappedTable,String accountID,SessionFactory connectionSpace,String mappedConfTbale)
	{
		List ColomnList=new ArrayList<String>();
		ColomnList.add("id");
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData(mappedTable,accountID,connectionSpace,"",0,"table_name",mappedConfTbale);
		for(GridDataPropertyView gdp:returnResult)
		{
			ColomnList.add(gdp.getColomnName());
			
		}
		return ColomnList;
	}
	@SuppressWarnings("unchecked")
	public static List<GridDataPropertyView> getColomnfieldList(String mappedTable,String accountID,SessionFactory connectionSpace,String mappedConfTbale)
	{
		List ColomnList=new ArrayList<String>();
		ColomnList.add("id");
		List<GridDataPropertyView>returnResult=Configuration.getConfigurationData(mappedTable,accountID,connectionSpace,"",0,"table_name",mappedConfTbale);
	
		return returnResult;
	}
	
	
}
