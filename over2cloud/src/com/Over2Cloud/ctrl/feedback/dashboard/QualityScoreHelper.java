package com.Over2Cloud.ctrl.feedback.dashboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;

import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.ctrl.feedback.pojo.QualityPojo;

public class QualityScoreHelper
{
	private final static CommonOperInterface cbt = new CommonConFactory().createInterface();
	
	public QualityPojo getRatingDetails(QualityPojo pojo,SessionFactory connection)
	{
		int rating=0;
		StringBuilder builder=new StringBuilder(" select "+pojo.getCatColName()+" from feedback_paper_rating ");
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connection);
		if(dataList!=null && dataList.size()>0)
		{
			int rat1=0,rat2=0,rat3=0,rat4=0,rat5=0;
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object ob = (Object) iterator.next();
				if(ob!=null && !ob.toString().equalsIgnoreCase("NA"))
				{
					try
					{
						rating=Integer.parseInt(ob.toString());
					}
					catch (Exception e)
					{
						rating=0;
						e.printStackTrace();
					}
					
					if(rating==1)
					{
						rat1+=1;
					}
					else if(rating==2)
					{
						rat2+=1;
					}
					else if(rating==3)
					{
						rat3+=1;
					}
					else if(rating==4)
					{
						rat4+=1;
					}
					else if(rating==5)
					{
						rat5+=1;
					}
					
					pojo.setRat1(String.valueOf(rat1));
					pojo.setRat2(String.valueOf(rat2));
					pojo.setRat3(String.valueOf(rat3));
					pojo.setRat4(String.valueOf(rat4));
					pojo.setRat5(String.valueOf(rat5));
				}
			}
		}
		return pojo;
	}
	
	public List<QualityPojo> getAllQualityDetailsData(String deptId,SessionFactory connection)
	{
		List<QualityPojo> qualityList=new ArrayList<QualityPojo>();
		StringBuilder builder=new StringBuilder(" select field_name,field_value from feedback_paperform_rating_configuration as rating");
		builder.append(" inner join feedback_category as cat on rating.field_name=cat.categoryName ");
		builder.append(" inner join feedback_type as typ on cat.fbType=typ.id ");
		builder.append(" where typ.dept_subdept ="+deptId+"");
		
		List dataList=cbt.executeAllSelectQuery(builder.toString(), connection);
		if(dataList!=null && dataList.size()>0)
		{
			int count=1;
			for (Iterator iterator = dataList.iterator(); iterator.hasNext();)
			{
				Object[] object = (Object[]) iterator.next();
				if(object[0]!=null && object[1]!=null)
				{
					QualityPojo pojo=new QualityPojo();
					pojo.setId(count+=1);
					pojo.setCatName(object[0].toString());
					pojo.setCatColName(object[1].toString());
					pojo=getRatingDetails(pojo,connection);
					qualityList.add(pojo);
				}
			}
		}
		return qualityList;
	}
}
