package com.Over2Cloud.ctrl.newsEvents;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import org.hibernate.SessionFactory;
import com.Over2Cloud.Rnd.createTable;

public class NewsAlertsHelper {

	
	public String getDeptNameFromId(SessionFactory connection,String deptId)
	{
		String deptName="";
		List dataList=new createTable().executeAllSelectQuery("select deptName from department where id='"+deptId+"' ", connection);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object object = (Object) iterator.next();
				if(object!=null)
				{
					deptName=object.toString();
				}
			}
		}
		return deptName;
	}
	
	public Map<Integer,String> getAllDepts(SessionFactory connection)
	{
		Map<Integer,String> deptMap=new HashMap<Integer,String>();
		List dataList=new createTable().executeAllSelectQuery("select id,deptName from department where flag !='1'", connection);
		if(dataList!=null && dataList.size()>0)
		{
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null )
				{
					deptMap.put(Integer.parseInt(object[0].toString()),object[1].toString());
				}
			}
		}
		return deptMap;
	}
}
