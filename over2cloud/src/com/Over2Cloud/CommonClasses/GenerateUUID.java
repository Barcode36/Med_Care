package com.Over2Cloud.CommonClasses;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
public class GenerateUUID {
	  
	  public static final Object UUID(){
	    UUID idOne = UUID.randomUUID();
	    return idOne;
	  }
	  
	  public static String rendomnumber()
	  {
		  String randomNum="NA";
		  try {
		      //Initialize SecureRandom
		      //This is a lengthy operation, to be done only upon
		      //initialization of the application
		      SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
		      //generate a random number
		      randomNum = new Integer( prng.nextInt() ).toString();
		    
		    }
		    catch ( NoSuchAlgorithmException ex ) {
		    } 
		    return randomNum;
	  }
	  
	  public static void main(String[] args) {
	}
	} 