package com.Over2Cloud.CommonClasses;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class CommonLogic {

	
	public static List<Integer> convertSArraytoInt(String stringArray[])
	{
		List<Integer>  intList=new  ArrayList<Integer>();
		
		for(String stringObj:stringArray)
			intList.add(Integer.parseInt(stringObj));
		return intList;
	}
	
	public static void getFileName(String filePath,String contentType)
	{
             if(filePath!=null && !filePath.equals("")) {
            	 File folder=new File(filePath);
            	 
            	 if(folder.isDirectory())
            	 {
            	String[] allfiles=folder.list(); 
            		 if(allfiles!=null && allfiles.length>0)
            		 {
            			for (int i = 0; i < allfiles.length; i++) {
						} 
            		 }
            		 
            		 
            		 
            	 }
            	 
            	 
             } 

	
	}
	
	
	
		
	
	public static void main(String[] args) {
		//getFileName("E:\\Project\\erp\\WebContent\\document\\admin\\company_admin");
	}
	
}
