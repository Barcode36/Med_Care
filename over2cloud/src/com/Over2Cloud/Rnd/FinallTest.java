package com.Over2Cloud.Rnd;

public class FinallTest {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		for(int i=0;i<10;i++)
		{
			try
			{
				System.out.println("I am try"+i);
				if(i==5)
				{
					break;
				}
				else if(i>6)
				{
					continue;
				}
			}
			catch (Exception e) {
				System.out.println("I am catch");
			}
			finally
			{
				System.out.println("I am finally");
			}
		}
		 

	}

}
