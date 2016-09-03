package com.Over2Cloud.CommonClasses;

/**
 * 
 */

/**
 * @author dharmvir.singh
 *
 */
public class TestCrypto {
	public static final String DES_ENCRYPTION_KEY = "ankitsin";
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		try{
		String input  = "ankits1234";
		String encrypted = Cryptography.encrypt(input, DES_ENCRYPTION_KEY);
		String userName = Cryptography.decrypt("S3p66ZIKPv8=", DES_ENCRYPTION_KEY);
		System.out.println("UserName:"+userName);
		String pass = Cryptography.decrypt("P6UaVD+vrcM=", DES_ENCRYPTION_KEY);
		System.out.println("Pass:"+pass);
		}catch(Exception e){
			
		}
	}

}
