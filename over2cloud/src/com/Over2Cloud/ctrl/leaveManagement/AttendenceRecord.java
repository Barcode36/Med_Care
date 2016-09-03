package com.Over2Cloud.ctrl.leaveManagement;

import org.hibernate.SessionFactory;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;

public class AttendenceRecord extends Thread 
{
	SessionFactory connection=null;
	public AttendenceRecord(SessionFactory connection)
	 {
		this.connection=connection;
	 }
	AttendanceHelper RH = new AttendanceHelper();
		
	 //Start Point on calling a run method from main method
	  public void run()
		 {
			try {
				while (true) {
					try {
						RH.attendanceData(connection);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		
	  public static void main(String args[])
		{
			try {
				  SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-8");
			      Thread esc=new Thread(new AttendenceRecord(connection));
			      esc.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
} 