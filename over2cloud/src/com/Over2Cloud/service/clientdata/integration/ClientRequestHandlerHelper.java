package com.Over2Cloud.service.clientdata.integration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class ClientRequestHandlerHelper {


	/**
	 * @param mobNo
	 * @return Mobile No
	 * 
	 * 0-9269300756
		8802774951 / 9990047311
	 */

	public String correctMobileNo(String mobNo){
		
		List<String> mobileno = new ArrayList<String>();
		String mobileOne = "NA";
		try {
						
			//Check for specifier 
			if(mobNo != null && mobNo != "NA") {
		
			  if(mobNo.indexOf('-') > 0) {
				  mobileno = Arrays.asList(mobNo.split("-"));
			  }else if(mobNo.indexOf('/') > 0) {
				  mobileno = Arrays.asList(mobNo.split("/"));
			  }else if(mobNo.indexOf('\\') > 0) {
				  mobileno = Arrays.asList(mobNo.split("\\"));
			  }else if(mobNo.indexOf('.') > 0) {
				  mobileno = Arrays.asList(mobNo.split("\\."));
			  }else if(mobNo.indexOf(' ') > 0) {
				  mobileno = Arrays.asList(mobNo.split(" "));
			  }else if(mobNo.indexOf(':') > 0) {
				  mobileno = Arrays.asList(mobNo.split(":"));
			  } else if(mobNo.indexOf('#') > 0) {
				  mobileno = Arrays.asList(mobNo.split("#"));
			  }else if(mobNo.indexOf('~') > 0) {
				  mobileno = Arrays.asList(mobNo.split("~"));
			  }else if(mobNo.indexOf('`') > 0) {
				  mobileno = Arrays.asList(mobNo.split("`"));
			  }else if(mobNo.indexOf('!') > 0) {
				  mobileno = Arrays.asList(mobNo.split("!"));
			  }else if(mobNo.indexOf('@') > 0) {
				  mobileno = Arrays.asList(mobNo.split("@"));
			  }else if(mobNo.indexOf('$') > 0) {
				  mobileno = Arrays.asList(mobNo.split("$"));
			  }else if(mobNo.indexOf('%') > 0) {
				  mobileno = Arrays.asList(mobNo.split("%"));
			  }else if(mobNo.indexOf('^') > 0) {
				  mobileno = Arrays.asList(mobNo.split("^"));
			  }else if(mobNo.indexOf('&') > 0) {
				  mobileno = Arrays.asList(mobNo.split("&"));
			  }else if(mobNo.indexOf('*') > 0) {
				  mobileno = Arrays.asList(mobNo.split("*"));
			  }else if(mobNo.indexOf('(') > 0) {
				  mobileno = Arrays.asList(mobNo.split("("));
			  }else if(mobNo.indexOf(')') > 0) {
				  mobileno = Arrays.asList(mobNo.split(")"));
			  }else if(mobNo.indexOf('_') > 0) {
				  mobileno = Arrays.asList(mobNo.split("_"));
			  }else if(mobNo.indexOf('=') > 0) {
				  mobileno = Arrays.asList(mobNo.split("="));
			  }else if(mobNo.indexOf('|') > 0) {
				  mobileno = Arrays.asList(mobNo.split("|"));
			  }else if(mobNo.indexOf(";") > 0) {
				  mobileno = Arrays.asList(mobNo.split(";"));
			  }else if(mobNo.indexOf('>') > 0) {
				  mobileno = Arrays.asList(mobNo.split(">"));
			  }else if(mobNo.indexOf('<') > 0) {
				  mobileno = Arrays.asList(mobNo.split("<"));
			  }else if(mobNo.indexOf('?') > 0) {
				  mobileno = Arrays.asList(mobNo.split("?"));
			  }else {
				  mobileno.add(mobNo);
			  }
			}
			if(mobileno != null && mobileno.size() > 0) {
				for(String itr : mobileno) {
					itr = checkDualNumber(itr); 
					
					if(itr != null && itr.trim().length() == 10 &&  itr.trim().indexOf(" ") <  0) {
						mobileOne = itr.trim();
						break;
					}
				}
			}
		}catch(Exception E) {
			
		}
		return mobileOne;
	}
	
	public String checkDualNumber(String itr) {
		
		if(itr != null && itr.startsWith("+91")) {
			itr = itr.substring(3);
		}else if (itr != null && itr.startsWith("0")) {
			itr = itr.substring(1);
		}else if (itr != null && itr.startsWith("+")) {
			itr = itr.substring(1);
		}else if (itr != null && itr.trim().length() == 12) {
			itr = itr.substring(2);
		}
		
		if(itr.startsWith("+")  || itr.startsWith("0")) itr = checkDualNumber(itr);
		
		
		return itr;
	}
	
	
}
