package com.Over2Cloud.ctrl.compliance;

public class AbstractExtendCLass extends AbstractClass
{
	public void test1()
	{
		System.out.println("Inisde Test2 method");
	}
	public static void main(String[] args)
	{
		AbstractExtendCLass obAbstractClass=new AbstractExtendCLass();
		obAbstractClass.test1();
		System.out.println("this is main mathod");
	}
}
