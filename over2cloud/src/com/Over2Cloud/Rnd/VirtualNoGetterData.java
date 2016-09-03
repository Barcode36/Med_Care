package com.Over2Cloud.Rnd;

public class VirtualNoGetterData extends Thread
{

	public void run()
	{
		try
		{
			System.out.println("Before While Loop");
			while(true)
			{
				System.out.println("Connecting Virtual No...");
				new VirtualNoDataReceiver().getVirtualNoData();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		try
		{
			Thread.sleep(1000*1);
			  Thread esc = new Thread(new VirtualNoGetterData());
			  esc.start();
			  esc.join();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
