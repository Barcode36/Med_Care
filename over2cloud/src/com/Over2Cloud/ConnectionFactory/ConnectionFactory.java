package com.Over2Cloud.ConnectionFactory;
public class ConnectionFactory implements AllConnection {
	    private String connection;
	    private String dbbname;
	    private String ipAddress;
	    private String username;
	    private String paassword;
	    private String port;
		public ConnectionFactory(String connection){this.connection=connection;}
		
		public ConnectionFactory(String dbbname,String ipAddress,String username,String paassword,String port){
			    this.dbbname=dbbname;
			   this.ipAddress=ipAddress;
			   this.username=username;
			   this.paassword=paassword;
			   this.port=port;
			}

		@Override
		public AllConnectionEntry GetAllCollectionwithArg() {
			AllConnectionEntry Conobj=null;
			if(connection!=null && connection.equalsIgnoreCase("localhost"))
				Conobj= new AllConnectionClass(connection);
			else
				Conobj= new AllConnectionClass( dbbname, ipAddress, username, paassword,port);
			return Conobj;
		}
		
}
