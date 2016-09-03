package com.Over2Cloud.CommonClasses;

import com.Over2Cloud.CommonInterface.Addmethod;
import com.Over2Cloud.CommonInterface.commanOperation;

public class ObjectFactory implements Addmethod
{   Object objct;
	public ObjectFactory(){}
	public ObjectFactory(Object objct1){this.objct=objct1;}
	
	public commanOperation AddDetailsOfAll(String Classname)
	{ 
		commanOperation obj=null;
		if(Classname.equalsIgnoreCase("assets_services"))
		{
		  obj=new CommonDaoImpliments(objct);
		}
		else if(Classname.equalsIgnoreCase("apcm_ms"))
		{
		  obj=new CommonDaoImpliments(objct);
		}
		else {obj=new CommonDaoImpliments(objct);}
		return obj;
	}
}
