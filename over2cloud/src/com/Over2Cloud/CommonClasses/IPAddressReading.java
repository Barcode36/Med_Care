package com.Over2Cloud.CommonClasses;

public class IPAddressReading {
	 public static void main(String[] args) {
	       new IPAddressReading().getIP();
	       }
	 public String getIP() {
		 String IPAddress=null;
	      try {
	    	  
	     java.net.InetAddress i = java.net.InetAddress.getLocalHost();
	     // String i = java.net.Inet4Address.getLocalHost().toString();
	   //   System.out.println(i);                  // name and IP address
	    //  System.out.println(i.getHostName());    // name
	  // System.out.println(i); // IP address only
	      IPAddress=i.getHostAddress();
	      }
	      catch(Exception e){e.printStackTrace();}
	      return IPAddress;
	    }
	 
}