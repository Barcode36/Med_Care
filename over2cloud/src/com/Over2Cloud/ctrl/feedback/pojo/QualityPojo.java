package com.Over2Cloud.ctrl.feedback.pojo;

public class QualityPojo
{
	private int id;
	private String catName;
	private String catColName;
	private String rat1,rat2,rat3,rat4,rat5;
	private boolean ratingFlag=false;
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getCatName()
	{
		return catName;
	}
	public void setCatName(String catName)
	{
		this.catName = catName;
	}
	
	public String getRat1()
	{
		return rat1;
	}
	public void setRat1(String rat1)
	{
		this.rat1 = rat1;
	}
	public String getRat2()
	{
		return rat2;
	}
	public void setRat2(String rat2)
	{
		this.rat2 = rat2;
	}
	public String getRat3()
	{
		return rat3;
	}
	public void setRat3(String rat3)
	{
		this.rat3 = rat3;
	}
	public String getRat4()
	{
		return rat4;
	}
	public void setRat4(String rat4)
	{
		this.rat4 = rat4;
	}
	public String getRat5()
	{
		return rat5;
	}
	public void setRat5(String rat5)
	{
		this.rat5 = rat5;
	}
	public boolean isRatingFlag()
	{
		return ratingFlag;
	}
	public void setRatingFlag(boolean ratingFlag)
	{
		this.ratingFlag = ratingFlag;
	}
	public String getCatColName()
	{
		return catColName;
	}
	public void setCatColName(String catColName)
	{
		this.catColName = catColName;
	}
}
