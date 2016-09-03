package com.Over2Cloud.ctrl.ServiceListener;

public class CronExpression {
	
	public String getCronExpression(String startTime, String interval) {
		String cronExpression = "";
        try{		
		if ("D".equals(startTime) || "D".equals(interval)) {
			 // default trigger runs at 10AM & 10PM			
				 cronExpression = "0 0 10/12 * * ?";
				// Fire at 12pm (noon) every day	
				 cronExpression = "0 0 12 * * ?";
				// Fire at 10:15am every day
				 cronExpression = "0 15 10 ? * *";
				 // Fire at 10:15am every day
				 cronExpression = "0 15 10 * * ?";
				 // Fire at 10:15am every day
				 cronExpression = "0 15 10 * * ? *";
		    	 // Fire every minute starting at 2pm and ending at 2:59pm, every day
				 cronExpression = "0 15 10 * * ? *";
		    	 // Fire every 5 minutes starting at 2pm and ending at 2:55pm, every day
				 cronExpression = "0 0/5 14 * * ?";
				 // Fire every 5 minutes starting at 2pm and ending at 2:55pm, AND fire every 5 minutes starting at 6pm and ending at 6:55pm, every day
				 cronExpression = "0 0/5 14,18 * * ?";
		    	 // Fire every minute starting at 2pm and ending at 2:05pm, every day
				 cronExpression = "0 0-5 14 * * ?";
		} 
		else if("M".equals(startTime) || "M".equals(interval)){
			// Fire at 2:10pm and at 2:44pm every Wednesday in the month of March.			
			cronExpression = "0 10,44 14 ? 3 WED";
			// Fire at 10:15am every Monday, Tuesday, Wednesday, Thursday and Friday.			
			cronExpression = "0 15 10 ? * MON-FRI";
			// Fire at 10:15am on the 15th day of every month.			
			cronExpression = "0 15 10 15 * ?";
			// Fire at 10:15am on the last day of every month			
			cronExpression = "0 15 10 L * ?";
			// Fire at 10:15am on the 2nd-to-last last day of every month.			
			cronExpression = "0 15 10 L-2 * ?";
			// Fire at 10:15am on the last Friday of every month			
			cronExpression = "0 15 10 ? * 6L";
			// Fire at 10:15am on the last Friday of every month			
			cronExpression = "0 15 10 ? * 6L";
			// Fire at 10:15am on the third Friday of every month
			cronExpression = "0 15 10 ? * 6#3";
			// Fire at 12pm (noon) every 5 days every month, starting on the first day of the month.
			cronExpression = "0 0 12 1/5 * ?";
			//Fire every November 11th at 11:11am
			cronExpression = "0 11 11 11 11 ?";
	
		}
		else if("Y".equals(startTime) || "Y".equals(interval)){
			// Fire at 10:15am every day during the year 2005		
			 cronExpression = "0 15 10 * * ? 2005";
			// Fire at 10:15am on every last friday of every month during the years 2002, 2003, 2004 and 2005	
			 cronExpression = "0 15 10 ? * 6L 2002-2005";
			 
		}
		else {
			cronExpression = "0 0 " + startTime + "/" + interval + " * * ?";
			}
        }catch (Exception e) {
        	e.printStackTrace();
			// TODO: handle exception
		}
		return cronExpression;
		
	}
}
