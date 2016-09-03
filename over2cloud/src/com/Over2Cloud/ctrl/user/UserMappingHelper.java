package com.Over2Cloud.ctrl.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;

public class UserMappingHelper
{
	public ArrayList<String> getAllUsers(String user, boolean isChildUsers,
			SessionFactory connection) throws Exception
	{
		CommonOperInterface cbt = new CommonConFactory().createInterface();
		ArrayList<String> list = null;
		StringBuilder query1 = new StringBuilder();
		if (isChildUsers)//in case of getting all mapped child users of given user 
		{
			query1.append("select mappedWith from user_mapping where mappedBy = '");
			query1.append(user);
			query1.append("'");
		}
		else //in case of getting all users to which given user mapped with (parent users)
		{
			query1.append("select mappedBy from user_mapping where mappedWith = '");
			query1.append(user);
			query1.append("'");
		}

		System.out.println("query1:" + query1);

		List listData = cbt.executeAllSelectQuery(query1.toString(), connection);

		System.out.println("listData.size():" + listData.size());
		if (listData != null && listData.size() > 0)
		{
			list = new ArrayList<String>();
			for (Object obj : listData)
			{
				list.add(obj.toString().trim());
			}
		}

		return list;
	}

}
