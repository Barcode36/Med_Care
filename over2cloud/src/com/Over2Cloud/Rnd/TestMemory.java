package com.Over2Cloud.Rnd;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestMemory {

	public static void main(String[] args) 
	{
		String name1="KHUSHAL";
		String name2="KHUSHAL";
		System.out.println("Normal 1111   Overridden hashCode()--->>>"+name1.hashCode());
		System.out.println("Normal 2222   Overridden hashCode()--->>>"+name2.hashCode());
		int originalHashCode1 = System.identityHashCode(name1);
		int originalHashCode2 = System.identityHashCode(name2);
		System.out.println("Modified 111 Original hashCode of Emp---->>>"+originalHashCode1);
		System.out.println("Modified 222  Original hashCode of Emp---->>>"+originalHashCode2);
		Map<String, String> abc=new LinkedHashMap<String, String>();
		abc.put(name1, "KHUSHAL");
		abc.put(name2, "KHUSHAL");
		System.out.println("Size of  Map is "+abc.size());
	}
}
