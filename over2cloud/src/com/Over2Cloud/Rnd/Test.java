package com.Over2Cloud.Rnd;

import com.Over2Cloud.CommonClasses.DateUtil;

interface X
{
   public void myMethod();
   //int a=0;
}
interface Y
{
	int a=9;
   public void myMethod();
}

public  class Test 
{
	
	  
	 public static void main(String[]args){
		 
		System.out.println(DateUtil.convertDateToIndianFormat("2016-08-09"));
		System.out.println(DateUtil.convertDateToIndianFormat("09-08-2016"));
		 
	 }
	   
	
	
	
	
}
