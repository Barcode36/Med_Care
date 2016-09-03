package com.Over2Cloud.ctrl.referral.services;

import org.hibernate.SessionFactory;

import com.Over2Cloud.CommonClasses.DateUtil;
import com.Over2Cloud.InstantCommunication.CommunicationHelper;
import com.Over2Cloud.InstantCommunication.ConnectionHelper;
import com.Over2Cloud.ctrl.helpdesk.common.HelpdeskUniversalHelper;

public class ReferralServiceFile extends Thread
{
	SessionFactory connection = null;
	CommunicationHelper CH = null;
	HelpdeskUniversalHelper HUH=null;

	public ReferralServiceFile(SessionFactory connection, CommunicationHelper CHObj,HelpdeskUniversalHelper HUH)
	{
		this.connection = connection;
		this.HUH=HUH;
		this.CH = CHObj;
	}

	ReferralServiceFileHelper RSFH = new ReferralServiceFileHelper();

	public void run()
	{
		try
		{
			while (true)
			{
				try
				{
					// Method calling for escalating a ticket
					RSFH.escalateToNextLevel(connection,HUH, CH);
					
					RSFH.snoozeToPending(connection,HUH, CH);

					Runtime rt = Runtime.getRuntime();
					rt.gc();
					System.out.println("Sleeping......................" + DateUtil.getCurrentDateIndianFormat() + " at " + DateUtil.getCurrentTime());
					Thread.sleep(1000 * 60);
					System.out.println("Woke Up......................." + DateUtil.getCurrentDateIndianFormat() + " at " + DateUtil.getCurrentTime());
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		try
		{
			SessionFactory connection = new ConnectionHelper().getSessionFactory("IN-4");
			CommunicationHelper CH = new CommunicationHelper();
			HelpdeskUniversalHelper HUH=new HelpdeskUniversalHelper();
			Thread esc = new Thread(new ReferralServiceFile(connection, CH,HUH));
			esc.start();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
