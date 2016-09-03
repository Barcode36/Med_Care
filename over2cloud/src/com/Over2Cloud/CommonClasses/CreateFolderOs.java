package com.Over2Cloud.CommonClasses;

import java.io.File;
import java.io.IOException;

/**
 * @author Shailendra Mishra
 * @category Sreate Folder OS Autosence
 */
public class CreateFolderOs {

	public String createUserDir(final String dirName){
		
		String path = "";
		String tempPath=new String();
		boolean result = false;
		//First Check OS...
		if(CheckOs.isWindows()) {
			 path = "C://";
		}else if(CheckOs.isUnix()) {
			 path = "/opt//";
		}else if(CheckOs.isMac()) {
			 path = "/tmp//";
		}else if(CheckOs.isSolaris()) {
			 path = "/tmp//";
		}
		File theDir = new File(path+""+dirName);
		tempPath=path+dirName;
		  // if the directory does not exist, create it
		  if (!theDir.exists())
		  {
		    result = theDir.mkdir();  
		    if(result){    
		     }

		  }
		  return tempPath;
	}

}
