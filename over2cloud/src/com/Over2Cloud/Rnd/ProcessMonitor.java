package com.Over2Cloud.Rnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessMonitor {
	private static final String TASKLIST = "//C:/Users/User/Desktop/Tomcat Restart.bat";
	private static final String KILL = "taskkill /F /IM ";
	public static boolean isProcessRunning(String serviceName) throws Exception {

		 Process p = Runtime.getRuntime().exec(TASKLIST);
		 BufferedReader reader = new BufferedReader(new InputStreamReader(
		   p.getInputStream()));
		 String line;
		 while ((line = reader.readLine()) != null) {

		 // System.out.println(line);
		  if (line.contains(serviceName)) {
		   return true;
		  }
		 }

		 return false;

		}

		public static void killProcess(String serviceName) throws Exception {

		  Runtime.getRuntime().exec("taskkill /IM " + serviceName);

		 }
	public static void main(String args[]) throws Exception{
		 Process process;
		try {
			//process = Runtime.getRuntime ().exec ("//C:/Users/User/Desktop/Tomcat Restart.bat");
			
			
			
			
			String processName = "notepad.EXE";
			 killProcess(processName);
			 //System.out.print(isProcessRunning(processName));

			/* if (isProcessRunning(processName)) {

			 
			 }*/
			 Thread.sleep(2000);
			 
			
			//Runtime.getRuntime().exec("taskkill /IM notepad.exe");
			//process.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //your application
		 
	}

}
