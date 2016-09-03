package com.Over2Cloud.Rnd;

import java.util.ArrayList;
import java.util.List;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.Post;
import com.restfb.types.User;

import hibernate.common.HisHibernateSessionFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;










public class FacebookIntegration  extends Thread  {

	private static final Log log = LogFactory.getLog(FacebookIntegration.class);
	

	private static SessionFactory connection=null;
	private static Session sessionHis = null;
	private HISTESTHelper CDIH =  new HISTESTHelper();
	CommunicationHelper ch = new CommunicationHelper();
	
	//private final T2mVirtualNoDataReceiver t2mvirtual=new T2mVirtualNoDataReceiver();
	
	public FacebookIntegration(SessionFactory connection, Session sessionHis ) {
		this.connection = connection;
	}
	
	public void run()
	{
			
				try{
					while(true){
					
						String ftokon = "EAAPt6cDjZCKYBAIvXcmGVePcR9zjRBgZB92ZAUfW7OTGzRC5ZBiKo2F9NZBXisNoKNBBSZAdXzxPdq3c8Ica9e1TvfEPZAU4q5TVZANV5wWviU8pPJZAZCZBL6O7FmyEJ2QqjnrUTuobBJbMaxWZCBgGdCTog1i6s0OT8LuHkGVPajGFhTYyjBdMzyJL";
						FacebookClient cl = new DefaultFacebookClient(ftokon);
						//User user = cl.fetchObject("me", User.class);
						
					//	System.out.println(user.getName());
						
						AccessToken extendedaccessTokon = cl.obtainExtendedAccessToken("1106013149461670", "8635ccde9f4e23bdeaef70fc9b029dd4");
						System.out.println("tokon "+extendedaccessTokon.getAccessToken());
						System.out.println("tokon "+extendedaccessTokon.getExpires());
						User user = cl.fetchObject("me", User.class);
						System.out.println(user.getName());
						Connection<Post> result = cl.fetchConnection("me/feed", Post.class);
						
						
						CommonOperInterface cot = new CommonConFactory().createInterface();
						for(List<Post> page : result)
						{
							for(Post apost : page){
								InsertDataTable ob = new InsertDataTable();
								List<InsertDataTable> insertData = new ArrayList<InsertDataTable>();
								System.out.println(apost.getMessage());
								ob = new InsertDataTable();
								ob.setColName("post");
								
									ob.setDataName(apost.getMessage());
								insertData.add(ob);
								int id = cot.insertDataReturnId("facebook_post", insertData, connection);
							}
							
							
						}
						
						System.out.println("Sleeping......................"+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());
						Thread.sleep(1000* 60 );
						System.out.println("Woke Up......................."+DateUtil.getCurrentDateIndianFormat()+" at "+DateUtil.getCurrentTime());

						Runtime rt = Runtime.getRuntime();
						rt.gc();
	
					}
									}
				 catch (Exception e)
				 {
					 e.printStackTrace();
					 }
		
	}
	
	
	

	public static void main(String[] args) {
		Thread.State state ;
		try{
			
			System.out.println(">>>>>>>>>>>>>>>>>....Before Local DB connection");
			connection = new ConnectionHelper().getSessionFactory("IN-4");
			System.out.println("#@@@@@@@@@@@@@@@@@@@@@@@@After Local DB connection");
			System.out.println("Before HIS DB connection");
			sessionHis = HisHibernateSessionFactory.getSession();
			System.out.println("After HIS DB connection"+sessionHis);
			
				Thread uclient=new Thread(new FacebookIntegration(connection, sessionHis));
				System.out.println("Thread Created Successfuly!!");
				state = uclient.getState(); 
				System.out.println("Thread's State:: "+state);
			
				if(!state.toString().equalsIgnoreCase("RUNNABLE"))uclient.start();
				System.out.println("Thread Started Successfuly!!");
			
		}catch(Exception E){
			try {
				OutputStream out = new FileOutputStream(new File("c://output.txt"));
				byte[] b=E.toString().getBytes();
				out.write(b);
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			E.printStackTrace();
		}
	}






}
